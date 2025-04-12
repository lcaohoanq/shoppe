package com.lcaohoanq.authservice.configs

import com.lcaohoanq.authservice.components.CustomFeignErrorDecoder
import feign.RequestInterceptor
import feign.RequestTemplate
import feign.Retryer
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
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

    @Bean
    fun retryer(): Retryer {
        return CustomRetryer() // hoặc truyền giá trị bạn muốn
    }

    @Bean
    fun errorDecoder(): ErrorDecoder = CustomFeignErrorDecoder()
}