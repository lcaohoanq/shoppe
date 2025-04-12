package com.lcaohoanq.gateway

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class GatewayRateLimitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    val loginPayload = mapOf(
        "email" to "string",
        "password" to "string"
    )

    @Test
    fun `should return 429 when exceeding rate limit`() {
        val maxRequests = 5  // Reduce number of requests needed

        // Make requests in quick succession
        for (i in 1..maxRequests) {
            println("📬 Sending request #$i")
            try {
                val response = webTestClient.post()
                    .uri("/api/v1/auth/login")
                    .bodyValue(loginPayload)
                    .header("X-Forwarded-For", "192.168.1.1")  // Use consistent header
                    .exchange()

                if (i <= 3) {  // Only first 3 requests should succeed with our new bucket
                    response.expectStatus().isOk()
                    println("✅ Request #$i allowed")
                } else {
                    // After that, should get 429
                    println("⛔ Request #$i should be rate limited")
                    response.expectStatus().isEqualTo(HttpStatus.TOO_MANY_REQUESTS)
                    return  // Test passes when we get our 429
                }

                // Add a small delay to ensure logs are printed in order
                Thread.sleep(50)

            } catch (e: Exception) {
                if (i > 3 && e.message?.contains("429") == true) {
                    println("✅ Test passed: rate limiting worked")
                    return  // Expected failure
                }
                throw e
            }
        }

        fail("Rate limit was not triggered after $maxRequests requests")
    }

}
