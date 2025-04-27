package com.lcaohoanq.keycloak.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
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

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Auth API")
class AuthController(
    private val webClientBuilder: WebClient.Builder
) {

    @Value("\${keycloak.url}")
    private lateinit var keycloakUrl: String
    private val clientId = "movies-app"

    /**
     * Get a token from Keycloak using the password grant type.
     *
     * @param request The login request containing the username and password.
     * @return A [ResponseEntity] containing the token response.
     */
    @Operation(
        summary = "Get token",
        description = "Get a token from Keycloak using the password grant type.",
        tags = ["Auth"]
    )
    @PostMapping("/token")
    fun getToken(@RequestBody request: KeycloakLoginRequest): Mono<ResponseEntity<KeycloakTokenResponse>> {
        val formData = mapOf(
            "grant_type" to "password",
            "client_id" to clientId,
            "username" to request.username,
            "password" to request.password
        )

        return webClientBuilder.build()
            .post()
            .uri(keycloakUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(formData.entries.joinToString("&") { "${it.key}=${it.value}" })
            .retrieve()
            .bodyToMono(KeycloakTokenResponse::class.java)
            .map { ResponseEntity.ok(it) }
    }
}

data class KeycloakLoginRequest(
    @Schema(defaultValue = "user") val username: String,
    @Schema(defaultValue = "user") val password: String
)

data class KeycloakTokenResponse(
    val access_token: String,
    val expires_in: Long,
    val refresh_token: String,
    val refresh_expires_in: Long,
    val token_type: String
)
