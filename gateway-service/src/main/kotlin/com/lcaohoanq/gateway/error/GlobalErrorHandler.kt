package com.lcaohoanq.gateway.error

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
@Order(-2)
class GlobalErrorHandler(private val objectMapper: ObjectMapper) : ErrorWebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response = exchange.response

        response.statusCode = when (ex) {
            is SecurityException -> HttpStatus.UNAUTHORIZED
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        response.headers.contentType = MediaType.APPLICATION_JSON

        val errorBody = mapOf(
            "message" to (ex.message ?: "An error occurred"),
            "statusCode" to (response.statusCode as HttpStatus).value(),
            "timestamp" to System.currentTimeMillis(),
            "path" to exchange.request.uri.path,
            "isSuccess" to false
        )

        val dataBufferFactory: DataBufferFactory = response.bufferFactory()
        val dataBuffer = dataBufferFactory.wrap(objectMapper.writeValueAsBytes(errorBody))

        return response.writeWith(Mono.just(dataBuffer))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): Mono<ResponseEntity<String>> {
        val errorMessages = ex.bindingResult.allErrors.joinToString(", ") { it.defaultMessage ?: "Unknown error" }
        return Mono.just(ResponseEntity.badRequest().body("Validation failed: $errorMessages"))
    }
}
