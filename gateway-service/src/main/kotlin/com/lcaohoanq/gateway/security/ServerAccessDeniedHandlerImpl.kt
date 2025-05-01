package com.lcaohoanq.gateway.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.lcaohoanq.common.apis.ApiError
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ServerAccessDeniedHandlerImpl(
    private val objectMapper: ObjectMapper
) : ServerAccessDeniedHandler {

    @Throws(java.io.IOException::class)
    override fun handle(
        exchange: org.springframework.web.server.ServerWebExchange,
        denied: org.springframework.security.access.AccessDeniedException
    ): Mono<Void> {
        val response = exchange.response
        response.statusCode = HttpStatus.FORBIDDEN
        response.headers.contentType = MediaType.APPLICATION_JSON

        val errorBody = ApiError(
            message = "Access Denied",
            reason = "You don't have permission to access this resource",
            statusCode = HttpStatus.FORBIDDEN.value(),
            isSuccess = false,
            data = mapOf(
                "timestamp" to System.currentTimeMillis(),
                "path" to exchange.request.uri.path
            )
        )

        return response.writeWith(Mono.just(response.bufferFactory().wrap(objectMapper.writeValueAsBytes(errorBody))))
    }

}
