package com.lcaohoanq.kotlinbasics.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.lcaohoanq.kotlinbasics.api.ApiError
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthenticationEntryPointImpl : AuthenticationEntryPoint {
    private val objectMapper: ObjectMapper? = null

    @Throws(java.io.IOException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: org.springframework.security.core.AuthenticationException
    ) {
        response.contentType = "application/json"
        response.status = HttpStatus.UNAUTHORIZED.value()

        objectMapper?.writeValue(response.outputStream, ApiError(
            message = "Authentication Required",
            reason = ex.message,
            statusCode = HttpStatus.UNAUTHORIZED.value(),
            isSuccess = false,
            data = mapOf(
                "timestamp" to System.currentTimeMillis(),
                "path" to request.requestURI
            )
        ))
    }
}