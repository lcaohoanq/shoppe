package com.lcaohoanq.gateway.controller

import com.fasterxml.jackson.annotation.JsonProperty
import com.lcaohoanq.common.dto.UserPort
import com.lcaohoanq.gateway.service.TokenBlacklistService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import jakarta.validation.constraints.NotBlank
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestHeader

@Tag(name = "Keycloak", description = "Keycloak API")
@RestController
@RequestMapping("/keycloak")
class KeycloakGatewayController(
    private val webClient: WebClient.Builder,
    private val tokenBlacklistService: TokenBlacklistService
) {

    @Value("\${keycloak.url}")
    private lateinit var keycloakUrl: String

    @Value("\${keycloak.client-id}")
    private lateinit var clientId: String

    @Value("\${keycloak.realm}")
    private lateinit var realm: String

    data class LoginRequest(
        @field:NotBlank(message = "Username is required")
        val username: String,

        @field:NotBlank(message = "Password is required")
        val password: String
    )

    data class TokenResponse(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("expires_in") val expiresIn: Long,
        @JsonProperty("refresh_token") val refreshToken: String,
        @JsonProperty("refresh_expires_in") val refreshExpiresIn: Long,
        @JsonProperty("token_type") val tokenType: String
    )

    data class RefreshTokenRequest(
        @field:NotBlank(message = "Refresh token is required")
        @JsonProperty("refresh_token") val refreshToken: String
    )

    data class LogoutRequest(
        @field:NotBlank(message = "Refresh token is required")
        @JsonProperty("refresh_token") val refreshToken: String
    )

    data class UserRegistrationRequest(
        @field:NotBlank(message = "Username is required")
        val username: String,

        @field:NotBlank(message = "Email is required")
        val email: String,

        @field:NotBlank(message = "Password is required")
        val password: String,

        val firstName: String?,
        val lastName: String?,
        val attributes: Map<String, List<String>>? = null
    )

    data class UserUpdateRequest(
        val firstName: String?,
        val lastName: String?,
        val email: String?,
        val attributes: Map<String, List<String>>? = null
    )

    data class ResetPasswordRequest(
        @field:NotBlank(message = "Email is required")
        val email: String
    )

    data class ChangePasswordRequest(
        @field:NotBlank(message = "Current password is required")
        val currentPassword: String,

        @field:NotBlank(message = "New password is required")
        val newPassword: String
    )

    @Operation(
        summary = "Get Keycloak token",
        description = "Authenticate user and return Keycloak token",
    )
    @PostMapping("/token", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getToken(
        @Validated @RequestBody request: LoginRequest
    ): Mono<ResponseEntity<TokenResponse>> {
        val formData = mapOf(
            "grant_type" to "password",
            "client_id" to clientId,
            "username" to request.username,
            "password" to request.password
        )

        return webClient.build()
            .post()
            .uri("$keycloakUrl/realms/$realm/protocol/openid-connect/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(formData.entries.joinToString("&") { "${it.key}=${it.value}" })
            .retrieve()
            .bodyToMono(TokenResponse::class.java)
            .map { ResponseEntity.ok(it) }
            .onErrorResume {
                Mono.just(ResponseEntity.status(401).build())
            }
    }

    @Operation(
        summary = "Refresh Keycloak token",
        description = "Refresh an expired token using a refresh token"
    )
    @PostMapping("/token/refresh", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun refreshToken(
        @Validated @RequestBody request: RefreshTokenRequest
    ): Mono<ResponseEntity<TokenResponse>> {
        val formData = mapOf(
            "grant_type" to "refresh_token",
            "client_id" to clientId,
            "refresh_token" to request.refreshToken
        )

        return webClient.build()
            .post()
            .uri("$keycloakUrl/realms/$realm/protocol/openid-connect/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(formData.entries.joinToString("&") { "${it.key}=${it.value}" })
            .retrieve()
            .bodyToMono(TokenResponse::class.java)
            .map { ResponseEntity.ok(it) }
            .onErrorResume {
                Mono.just(ResponseEntity.status(401).build())
            }
    }

    @Operation(
        summary = "Logout user",
        description = "Invalidate the user's session and tokens"
    )
    @PostMapping("/logout", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun logout(
        @RequestHeader("Authorization") authHeader: String,
        @Validated @RequestBody request: LogoutRequest
    ): Mono<ResponseEntity<Void>> {
        val token = authHeader.substring(7) // Remove "Bearer " prefix
        val jwtDecoder = NimbusJwtDecoder.withJwkSetUri("$keycloakUrl/realms/$realm/protocol/openid-connect/certs").build()

        // Parse the JWT to get its expiration time
        return Mono.fromCallable {
            jwtDecoder.decode(token)
        }
            .onErrorResume { Mono.empty() }
            .flatMap { jwt ->
                val expiration = jwt.expiresAt
                if (expiration != null) {
                    // Add the token to blacklist
                    tokenBlacklistService.blacklistToken(token, expiration)
                }

                val formData = mapOf(
                    "client_id" to clientId,
                    "refresh_token" to request.refreshToken
                )

                webClient.build()
                    .post()
                    .uri("$keycloakUrl/realms/$realm/protocol/openid-connect/logout")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .bodyValue(formData.entries.joinToString("&") { "${it.key}=${it.value}" })
                    .retrieve()
                    .toBodilessEntity()
                    .map { ResponseEntity.ok().build<Void>() }
            }
            .onErrorResume {
                Mono.just(ResponseEntity.status(400).build())
            }
    }

    @Operation(
        summary = "Register new user",
        description = "Create a new user in Keycloak"
    )
    @PostMapping("/users", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerUser(
        @RequestHeader("Authorization") authHeader: String,
        @Validated @RequestBody request: UserRegistrationRequest
    ): Mono<ResponseEntity<Void>> {
        val user = mapOf(
            "username" to request.username,
            "email" to request.email,
            "enabled" to true,
            "credentials" to listOf(
                mapOf(
                    "type" to "password",
                    "value" to request.password,
                    "temporary" to false
                )
            ),
            "firstName" to (request.firstName ?: ""),
            "lastName" to (request.lastName ?: ""),
            "attributes" to (request.attributes ?: emptyMap())
        )

        return webClient.build()
            .post()
            .uri("$keycloakUrl/admin/realms/$realm/users")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", authHeader)
            .bodyValue(user)
            .retrieve()
            .toBodilessEntity()
            .map { ResponseEntity.status(201).build<Void>() }
            .onErrorResume {
                Mono.just(ResponseEntity.status(400).build())
            }
    }

    @Operation(
        summary = "Get user details",
        description = "Get details of a user by ID"
    )
    @GetMapping("/users/{userId}")
    fun getUser(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable userId: String
    ): Mono<ResponseEntity<UserPort.UserResponse>> {
        return webClient.build()
            .get()
            .uri("$keycloakUrl/admin/realms/$realm/users/$userId")
            .header("Authorization", authHeader)
            .retrieve()
            .bodyToMono(UserPort.UserResponse::class.java)
            .map { ResponseEntity.ok(it) }
            .onErrorResume {
                Mono.just(ResponseEntity.status(404).build())
            }
    }

    @Operation(
        summary = "Update user details",
        description = "Update an existing user's details"
    )
    @PutMapping("/users/{userId}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateUser(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable userId: String,
        @Validated @RequestBody request: UserUpdateRequest
    ): Mono<ResponseEntity<Void>> {
        val updates = mutableMapOf<String, Any>()

        request.firstName?.let { updates["firstName"] = it }
        request.lastName?.let { updates["lastName"] = it }
        request.email?.let { updates["email"] = it }
        request.attributes?.let { updates["attributes"] = it }

        return webClient.build()
            .put()
            .uri("$keycloakUrl/admin/realms/$realm/users/$userId")
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", authHeader)
            .bodyValue(updates)
            .retrieve()
            .toBodilessEntity()
            .map { ResponseEntity.ok().build<Void>() }
            .onErrorResume {
                Mono.just(ResponseEntity.status(400).build())
            }
    }

    @Operation(
        summary = "Delete user",
        description = "Delete a user by ID"
    )
    @DeleteMapping("/users/{userId}")
    fun deleteUser(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable userId: String
    ): Mono<ResponseEntity<Void>> {
        return webClient.build()
            .delete()
            .uri("$keycloakUrl/admin/realms/$realm/users/$userId")
            .header("Authorization", authHeader)
            .retrieve()
            .toBodilessEntity()
            .map { ResponseEntity.ok().build<Void>() }
            .onErrorResume {
                Mono.just(ResponseEntity.status(404).build())
            }
    }

    @Operation(
        summary = "Reset password",
        description = "Send password reset email to the user"
    )
    @PostMapping("/users/reset-password", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun resetPassword(
        @RequestHeader("Authorization") authHeader: String,
        @Validated @RequestBody request: ResetPasswordRequest
    ): Mono<ResponseEntity<Void>> {
        // First find the user by email
        return webClient.build()
            .get()
            .uri("$keycloakUrl/admin/realms/$realm/users?email=${request.email}")
            .header("Authorization", authHeader)
            .retrieve()
            .bodyToMono(List::class.java)
            .flatMap { users ->
                if (users.isEmpty()) {
                    return@flatMap Mono.just(ResponseEntity.status(404).build<Void>())
                }

                val userId = (users[0] as Map<*, *>)["id"] as String

                // Send password reset email
                webClient.build()
                    .put()
                    .uri("$keycloakUrl/admin/realms/$realm/users/$userId/execute-actions-email")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", authHeader)
                    .bodyValue(listOf("UPDATE_PASSWORD"))
                    .retrieve()
                    .toBodilessEntity()
                    .map { ResponseEntity.ok().build<Void>() }
            }
            .onErrorResume {
                Mono.just(ResponseEntity.status(400).build())
            }
    }

    @Operation(
        summary = "Change password",
        description = "Change the password for the authenticated user"
    )
    @PostMapping("/users/change-password", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun changePassword(
        @RequestHeader("Authorization") authHeader: String,
        @Validated @RequestBody request: ChangePasswordRequest
    ): Mono<ResponseEntity<Void>> {
        // Extract the user ID from the token or get user info endpoint
        return getUserIdFromToken(authHeader)
            .flatMap { userId ->
                val passwordChange = mapOf(
                    "type" to "password",
                    "value" to request.newPassword,
                    "temporary" to false
                )

                webClient.build()
                    .put()
                    .uri("$keycloakUrl/admin/realms/$realm/users/$userId/reset-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", authHeader)
                    .bodyValue(passwordChange)
                    .retrieve()
                    .toBodilessEntity()
                    .map { ResponseEntity.ok().build<Void>() }
            }
            .onErrorResume {
                Mono.just(ResponseEntity.status(400).build())
            }
    }

    @Operation(
        summary = "Get current user info",
        description = "Get information about the currently authenticated user"
    )
    @GetMapping("/users/me")
    fun getCurrentUser(
        @RequestHeader("Authorization") authHeader: String
    ): Mono<ResponseEntity<UserPort.UserResponse>> {
        return webClient.build()
            .get()
            .uri("$keycloakUrl/realms/$realm/protocol/openid-connect/userinfo")
            .header("Authorization", authHeader)
            .retrieve()
            .bodyToMono(UserPort.UserResponse::class.java)
            .map { ResponseEntity.ok(it) }
            .onErrorResume {
                Mono.just(ResponseEntity.status(401).build())
            }
    }

    @Operation(
        summary = "Assign role to user",
        description = "Assign a role to a specific user"
    )
    @PostMapping("/users/{userId}/roles/{roleName}")
    fun assignRoleToUser(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable userId: String,
        @PathVariable roleName: String
    ): Mono<ResponseEntity<Void>> {
        // First get the role by name
        return webClient.build()
            .get()
            .uri("$keycloakUrl/admin/realms/$realm/roles/$roleName")
            .header("Authorization", authHeader)
            .retrieve()
            .bodyToMono(Map::class.java)
            .flatMap { role ->
                // Assign the role to the user
                webClient.build()
                    .post()
                    .uri("$keycloakUrl/admin/realms/$realm/users/$userId/role-mappings/realm")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", authHeader)
                    .bodyValue(listOf(role))
                    .retrieve()
                    .toBodilessEntity()
                    .map { ResponseEntity.ok().build<Void>() }
            }
            .onErrorResume {
                Mono.just(ResponseEntity.status(400).build())
            }
    }

    @Operation(
        summary = "Get user roles",
        description = "Get all roles assigned to a user"
    )
    @GetMapping("/users/{userId}/roles")
    fun getUserRoles(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable userId: String
    ): Mono<ResponseEntity<Any>> {
        return webClient.build()
            .get()
            .uri("$keycloakUrl/admin/realms/$realm/users/$userId/role-mappings/realm")
            .header("Authorization", authHeader)
            .retrieve()
            .bodyToMono(List::class.java as Class<*>)
            .map { ResponseEntity.ok(it) }
            .onErrorResume {
                Mono.just(ResponseEntity.status(404).build())
            }
    }

    @GetMapping("/test-member")
    fun testM(): ResponseEntity<String> {
        return ResponseEntity.ok("Keycloak Gateway member is working!")
    }

    @GetMapping("/test-admin")
    fun testA(): ResponseEntity<String> {
        return ResponseEntity.ok("Keycloak Gateway admin is working!")
    }

    private fun getUserIdFromToken(authHeader: String): Mono<String> {
        return webClient.build()
            .get()
            .uri("$keycloakUrl/realms/$realm/protocol/openid-connect/userinfo")
            .header("Authorization", authHeader)
            .retrieve()
            .bodyToMono(Map::class.java)
            .map { it["sub"] as String }
    }

}
