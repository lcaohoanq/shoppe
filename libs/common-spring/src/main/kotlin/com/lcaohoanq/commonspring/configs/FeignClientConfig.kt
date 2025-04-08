package com.lcaohoanq.commonspring.configs

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Configuration

@Configuration
class FeignClientConfig : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        val token = "Bearer " + generateInternalServiceToken() // Token tạm cho internal call
        template.header("Authorization", token)
    }

    fun generateInternalServiceToken(): String {
        // Có thể hardcode, hoặc gọi TokenProvider, hoặc service-to-service secret
        return "your-internal-jwt-token"
    }
}