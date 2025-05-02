//package com.lcaohoanq.gateway.filter
//
//import com.lcaohoanq.gateway.service.TokenBlacklistService
//import org.springframework.cloud.gateway.filter.GatewayFilterChain
//import org.springframework.cloud.gateway.filter.GlobalFilter
//import org.springframework.core.annotation.Order
//import org.springframework.http.HttpStatus
//import org.springframework.stereotype.Component
//import org.springframework.web.server.ServerWebExchange
//import reactor.core.publisher.Mono
//
//@Component
//@Order(-1)
//class TokenValidatorFilter(private val tokenBlacklistService: TokenBlacklistService) :
//    GlobalFilter {
//
//    override fun filter(exchange: ServerWebExchange?, chain: GatewayFilterChain?): Mono<Void> {
//
//        val request = exchange?.request
//        val authHeader = request?.headers?.getFirst("Authorization")
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            val token = authHeader.substring(7)
//
//            // Check if token is blacklisted
//            if (tokenBlacklistService.isBlacklisted(token)) {
//                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
//                return exchange.response.setComplete()
//            }
//        }
//
//        return chain?.filter(exchange) ?: Mono.empty()
//
//    }
//}
