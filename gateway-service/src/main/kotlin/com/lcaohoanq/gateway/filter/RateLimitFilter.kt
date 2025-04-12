package com.lcaohoanq.gateway.filter

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

@Component
@Order(-2) // Execute before AuthenticationFilter
class RateLimitFilter : GlobalFilter, Ordered {

    private val log = LoggerFactory.getLogger(RateLimitFilter::class.java)

    // Store buckets per IP address
    private val buckets = ConcurrentHashMap<String, Bucket>()

    // Define rate limit: 5 tokens per second with max burst of 10 ( 10 requests maximum, refilling at 5 per second)
    /*
        Greedy refill (adds tokens immediately)
        Refill.greedy(5, Duration.ofSeconds(1))

        Interval refill (adds tokens at fixed intervals)
        Refill.intervally(5, Duration.ofSeconds(1))
    */
    
    private fun createBucket(): Bucket {
        val limit = Bandwidth.classic(10, Refill.greedy(5, Duration.ofSeconds(1)))
        return Bucket.builder().addLimit(limit).build()
    }

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val path = request.uri.path

        // Only rate limit login endpoint
        if (!path.contains("/api/v1/auth/login")) {
            return chain.filter(exchange)
        }

        // Client Identification: Get client IP - properly extract from X-Forwarded-For header
        val clientIp = request.headers.getFirst("X-Forwarded-For") 
            ?: exchange.request.remoteAddress?.address?.hostAddress 
            ?: "unknown"
        
        /*           
            By user ID (for authenticated requests)
            val userId = exchange.getAttribute<String>("userId") ?: "anonymous" 
        */

        log.info("🔑 Rate limiting request from IP: $clientIp for path: $path")

        // Create a more restrictive bucket for testing
        val bucket = buckets.computeIfAbsent(clientIp) { 
            // For tests: 3 initial tokens with 1 token per second refill
            Bucket.builder()
                .addLimit(Bandwidth.classic(3, Refill.greedy(1, Duration.ofSeconds(1))))
                .build()
        }

        // Try to consume a token
        val probe = bucket.tryConsumeAndReturnRemaining(1)
        if (probe.isConsumed) {
            log.info("✅ Request allowed, remaining: ${probe.remainingTokens}")
            return chain.filter(exchange)
        } else {
            log.warn("❌ Rate limit exceeded for IP: $clientIp")
            exchange.response.statusCode = HttpStatus.TOO_MANY_REQUESTS
            return exchange.response.setComplete()
        }
    }

    fun getBucketForTier(clientId: String, tier: ServiceTier): Bucket {
        return when(tier) {
            ServiceTier.FREE -> Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1))))
                .build()
            ServiceTier.PREMIUM -> Bucket.builder()
                .addLimit(Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1))))
                .build()
            ServiceTier.ENTERPRISE -> // No limits for enterprise
                unlimitedBucket()
        }
    }

    override fun getOrder(): Int = -2
}