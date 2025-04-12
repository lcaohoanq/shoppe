package com.lcaohoanq.authservice.components

import com.lcaohoanq.authservice.exceptions.UnauthorizedException
import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import jakarta.ws.rs.BadRequestException
import jakarta.ws.rs.ForbiddenException
import jakarta.ws.rs.NotFoundException
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Component
class CustomFeignErrorDecoder : ErrorDecoder {
    private val log = KotlinLogging.logger {}

    override fun decode(methodKey: String, response: Response): Exception {
        val responseBody = try {
            response.body()?.asInputStream()?.bufferedReader()?.use { it.readText() } ?: "No response body"
        } catch (e: Exception) {
            "Error reading response body: ${e.message}"
        }

        log.error("Feign client error for $methodKey: Status ${response.status()}, Body: $responseBody")

        return when (response.status()) {
            400 -> BadRequestException("Bad request to $methodKey: $responseBody")
            401 -> UnauthorizedException("Unauthorized to $methodKey: $responseBody")
            403 -> ForbiddenException("Forbidden to $methodKey: $responseBody")
            404 -> NotFoundException("Not found for $methodKey: $responseBody")
            503 -> Exception("Service unavailable for $methodKey: $responseBody")
            else -> FeignException.errorStatus(methodKey, response)
        }
    }
}