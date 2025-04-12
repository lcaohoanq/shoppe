package com.lcaohoanq.gateway.filter

import com.lcaohoanq.gateway.jwt.JwtTokenProvider
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
@Order(value = -1)
class AuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : GlobalFilter {

    private val log = LoggerFactory.getLogger(AuthenticationFilter::class.java)

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        log.info("🔥 AuthenticationFilter called for: ${exchange.request.uri}")

        val request = exchange.request
        val path = request.uri.path
        val method = request.method

        log.debug("Intercepting request: {} {}", method, path)

        if (isPublicEndpoint(path)) return chain.filter(exchange)

        val authHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header")
        }

        val token = authHeader.substring(7)
        if (!jwtTokenProvider.validateToken(token)) {
            return unauthorized(exchange, "Invalid or expired JWT token")
        }

        val email = jwtTokenProvider.extractEmail(token)
        val mutatedRequest = exchange.request.mutate()
            .header("X-User-Email", email)
            .build()

        return chain.filter(exchange.mutate().request(mutatedRequest).build())
    }

    private fun unauthorized(exchange: ServerWebExchange, message: String): Mono<Void> {
        log.warn("❌ Unauthorized: {}", message)
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        return exchange.response.setComplete()
    }

    private fun isPublicEndpoint(path: String): Boolean {
        return path.contains("/auth") ||
                path.contains("/swagger") ||
                path.contains("/actuator") ||
                path.contains("/public")
    }
}
