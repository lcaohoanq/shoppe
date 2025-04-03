package com.lcaohoanq.ktservice.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.lcaohoanq.common.api.ApiError
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class AccessDeniedHandlerImpl : AccessDeniedHandler {
    private val objectMapper: ObjectMapper? = null

    @Throws(java.io.IOException::class)
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: org.springframework.security.access.AccessDeniedException
    ) {
        response.contentType = "application/json"
        response.status = HttpStatus.FORBIDDEN.value()

        objectMapper?.writeValue(response.outputStream, ApiError(
            message = "Access Denied",
            reason = "You don't have permission to access this resource",
            statusCode = HttpStatus.FORBIDDEN.value(),
            isSuccess = false,
            data = mapOf(
                "timestamp" to System.currentTimeMillis(),
                "path" to request.requestURI
            )
        )
        )
    }
}