package com.lcaohoanq.gateway.filter

import mu.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtPayloadForwardingFilter : GlobalFilter, Ordered {

    private val log = KotlinLogging.logger {}

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        return exchange.getPrincipal<JwtAuthenticationToken>()
            .flatMap { auth ->
                // Get the JWT token
                val token = auth.token.tokenValue

                // Add the token as Authorization header for downstream services
                val mutatedRequest = exchange.request.mutate()
                    .header("Authorization", "Bearer $token")
                    .header("X-User-Name", auth.name)
                    .header("X-User-Roles", auth.authorities.joinToString(",") { it.authority })
                    .build()

                // Pass the mutated request with token to downstream service
                log.info("Forwarding request with token: $token")
                chain.filter(exchange.mutate().request(mutatedRequest).build())
            }
            .switchIfEmpty(chain.filter(exchange)) // Fallback if no JWT token present (unauthenticated request)
    }

    override fun getOrder(): Int = -90
}
