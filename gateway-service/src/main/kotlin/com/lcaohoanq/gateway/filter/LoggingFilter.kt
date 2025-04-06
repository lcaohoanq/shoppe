package com.lcaohoanq.gateway.filter

import mu.KotlinLogging
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.gateway.route.Route
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

private val log = KotlinLogging.logger {}

@Component
class LoggingFilter : GlobalFilter, Ordered {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        println( "🌐 [Gateway] Incoming request: ${request.method} ${request.uri}")
        log.info { "🌐 [Gateway] Incoming request: ${request.method} ${request.uri}" }

        val route = exchange.getAttribute<Route>(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR)
        if (route != null) {
            println("🛣️ Route ID: ${route.id}")
            log.info { "🛣️ Route ID: ${route.id}" }
        }

        return chain.filter(exchange)
    }

    override fun getOrder(): Int = -2
}
