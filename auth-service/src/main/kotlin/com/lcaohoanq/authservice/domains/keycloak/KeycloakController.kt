package com.lcaohoanq.authservice.domains.keycloak

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.constraints.NotBlank
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

/**
 * Data class for Keycloak login requests.
 * Explicitly annotate properties to ensure proper Jackson deserialization.
 */
data class KeycloakLoginRequest(
    @field:JsonProperty("username")
    @field:Schema(description = "Username for authentication", example = "user", required = true)
    @field:NotBlank(message = "Username is required")
    val username: String,

    @field:JsonProperty("password")
    @field:Schema(description = "Password for authentication", example = "user", required = true)
    @field:NotBlank(message = "Password is required")
    val password: String
)

/**
 * Data class for Keycloak token responses.
 */
data class KeycloakTokenResponse(
    @field:JsonProperty("access_token")
    val accessToken: String,

    @field:JsonProperty("expires_in")
    val expiresIn: Long,

    @field:JsonProperty("refresh_token")
    val refreshToken: String,

    @field:JsonProperty("refresh_expires_in")
    val refreshExpiresIn: Long,

    @field:JsonProperty("token_type")
    val tokenType: String
)

@RestController
@RequestMapping("/api/v1/keycloak")
@Tag(name = "Keycloak", description = "Keycloak API")
class KeycloakController(
    private val webClientBuilder: WebClient.Builder
) {
    @Value("\${keycloak.url}")
    private lateinit var keycloakUrl: String

    @Value("\${keycloak.client-id}")
    private lateinit var clientId: String

    /**
     * Get a token from Keycloak using the password grant type.
     *
     * @param request The login request containing the username and password.
     * @return A [ResponseEntity] containing the token response.
     */
    @Operation(
        summary = "Get token",
        description = "Get a token from Keycloak using the password grant type.",
        tags = ["Keycloak"]
    )
    @PostMapping("/token", consumes = [MediaType.APPLICATION_JSON_VALUE])
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
