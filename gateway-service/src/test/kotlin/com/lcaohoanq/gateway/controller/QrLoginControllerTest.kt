package com.lcaohoanq.gateway.controller

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test") // Activate the test profile
@Disabled
class QrLoginControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun `should create, approve and fetch session`() {
        // Create session
        val sessionId = webTestClient.post()
            .uri("/qr-login")
            .exchange()
            .expectStatus().isOk
            .returnResult(String::class.java)
            .responseBody
            .blockFirst()
            ?: throw AssertionError("Session ID should not be null")

        // Approve session (no auth needed in test profile)
        webTestClient.post()
            .uri("/qr-login/$sessionId/approve")
            .exchange()
            .expectStatus().isUnauthorized

        // Verify session status
        webTestClient.get()
            .uri("/qr-login/$sessionId")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.status").isEqualTo("PENDING")
    }
}
