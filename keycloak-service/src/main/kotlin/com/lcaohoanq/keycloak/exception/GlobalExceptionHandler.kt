package com.lcaohoanq.keycloak.exception

import com.lcaohoanq.keycloak.api.ApiError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleNullPointerException(e: NullPointerException): ApiError<Any> {
        return ApiError(
            message = "Null pointer exception",
            reason = e.message,
            statusCode = HttpStatus.BAD_REQUEST.value(),
        )
    }

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleUnauthorizedException(e: UnauthorizedException): ApiError<Any> {
        return ApiError(
            message = "Unauthorized",
            reason = e.message,
            statusCode = HttpStatus.UNAUTHORIZED.value(),
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): ApiError<Any> {
        return ApiError(
            message = "Internal server error",
            reason = e.message,
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        )
    }

    @ExceptionHandler(PermissionDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handlePermissionDeniedException(e: PermissionDeniedException): ApiError<Any> {
        return ApiError(
            message = "Permission denied",
            reason = e.message,
            statusCode = HttpStatus.FORBIDDEN.value(),
        )
    }

}
