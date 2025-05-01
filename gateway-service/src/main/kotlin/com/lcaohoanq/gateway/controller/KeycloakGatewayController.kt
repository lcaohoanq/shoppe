package com.lcaohoanq.gateway.controller

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping

@RestController
@RequestMapping("/keycloak")
class KeycloakGatewayController(private val webClient: WebClient.Builder) {

    @Value("\${keycloak.url}")
    private lateinit var keycloakUrl: String

    @Value("\${keycloak.client-id}")
    private lateinit var clientId: String

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

    @Operation(
        summary = "Get Keycloak token",
        description = "Authenticate user and return Keycloak token",
    )
    @PostMapping("/token", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun getToken(
        @Valid @RequestBody request: LoginRequest,
        result: BindingResult
    ): Mono<ResponseEntity<TokenResponse>> {

        if (result.hasErrors())
            return Mono.just(ResponseEntity.badRequest().build())

        val formData = mapOf(
            "grant_type" to "password",
            "client_id" to clientId,
            "username" to request.username,
            "password" to request.password
        )

        return webClient.build()
            .post()
            .uri(keycloakUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(formData.entries.joinToString("&") { "${it.key}=${it.value}" })
            .retrieve()
            .bodyToMono(TokenResponse::class.java)
            .map { ResponseEntity.ok(it) }
            .onErrorResume {
                Mono.just(ResponseEntity.status(401).build())
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
}
