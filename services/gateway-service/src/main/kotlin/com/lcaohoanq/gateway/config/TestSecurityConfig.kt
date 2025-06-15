package com.lcaohoanq.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@Profile("test") // This configuration is only active in the test profile
class TestSecurityConfig {

    @Bean
    @Primary // Make this bean primary to override the existing one
    fun testSecurityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authorizeExchange { exchanges ->
                exchanges.anyExchange().permitAll() // Allow all requests in tests
            }
            .csrf { it.disable() }
            .build()
    }
}
