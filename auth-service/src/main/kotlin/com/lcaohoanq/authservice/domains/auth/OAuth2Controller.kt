package com.lcaohoanq.authservice.domains.auth

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@EnableConfigurationProperties(OAuth2ClientProperties::class)
@RestController
@RequestMapping("/api/v1/oauth2")
class OAuth2Controller(
    private val props: OAuth2ClientProperties
) {

    @GetMapping("/authorize/{provider}")
    fun authorizeOAuth2(@PathVariable provider: String): ResponseEntity<Void> {
        val encodedRedirectUri = URLEncoder.encode(
            props.redirectUri.replace("{provider}", provider),
            StandardCharsets.UTF_8.toString()
        )

        val authorizationUri = when (provider) {
            "google" -> "https://accounts.google.com/o/oauth2/auth" +
                    "?client_id=${props.clientId}" +
                    "&redirect_uri=${encodedRedirectUri}" +
                    "&response_type=code" +
                    "&scope=email%20profile"
            "github" -> "https://github.com/login/oauth/authorize" +
                    "?client_id=${props.clientId}" +
                    "&redirect_uri=$encodedRedirectUri" +
                    "&scope=user:email"

            else -> throw IllegalArgumentException("Unsupported provider: $provider")
        }

        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(authorizationUri))
            .build()
    }
}

@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration")
data class OAuth2ClientProperties(
    val clientId: String = "",
    val clientSecret: String = "",
    val redirectUri: String = "",
)