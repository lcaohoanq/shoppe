package com.lcaohoanq.gateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

//Có thể thay đổi giới hạn mà không cần build lại app, hoặc cho phép cấu hình riêng trên môi trường khác
@ConfigurationProperties(prefix = "rate-limiting")
@Configuration
class RateLimitingProperties {
    var rules: List<Rule> = listOf()

    data class Rule(
        val path: String,
        val capacity: Int,
        val refillPerSec: Int
    )
}
