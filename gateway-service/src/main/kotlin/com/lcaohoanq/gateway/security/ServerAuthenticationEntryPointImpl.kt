package com.lcaohoanq.gateway.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.lcaohoanq.common.apis.ApiError
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class ServerAuthenticationEntryPointImpl(
    private val objectMapper: ObjectMapper
): ServerAuthenticationEntryPoint {

    @Throws(java.io.IOException::class)
    override fun commence(
        exchange: ServerWebExchange,
        e: org.springframework.security.core.AuthenticationException
    ): Mono<Void> {
        val response = exchange.response
        response.statusCode = HttpStatus.UNAUTHORIZED
        response.headers.contentType = MediaType.APPLICATION_JSON

        val errorBody = ApiError(
            message = "Authentication Required",
            reason = e.message,
            statusCode = HttpStatus.UNAUTHORIZED.value(),
            isSuccess = false,
            data = mapOf(
                "timestamp" to System.currentTimeMillis(),
                "path" to exchange.request.uri.path
            )
        )

        return response.writeWith(Mono.just(response.bufferFactory().wrap(objectMapper.writeValueAsBytes(errorBody))))
    }
}
