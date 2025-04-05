package com.lcaohoanq.gateway.config

import com.lcaohoanq.gateway.filter.AuthenticationFilter
import com.lcaohoanq.gateway.jwt.JwtTokenProvider
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayConfig {

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            // Route for /api/v1/users, which should go to the User Service on localhost:4000
            .route("user_service_route") { r ->
                r.path("/api/v1/users/**") // Match any requests that start with /api/v1/users
                    .uri("http://localhost:4000") // Forward to the User service
            }
            // Route for /api/v1/categories, which should go to the Category Service on localhost:8080
            .route("category_service_route") { r ->
                r.path("/api/v1/categories/**") // Match any requests that start with /api/v1/categories
                    .uri("http://localhost:8080") // Forward to the Category service
            }
            // Route for /api/v1/auth/login (bypassing authentication filter)
            .route("auth_service_route") { r ->
                r.path("/api/v1/auth/login") // Match /api/v1/auth/login path
                    .uri("http://localhost:4000") // Forward to the authentication service
            }
            .build()
    }

    @Bean
    fun authenticationFilter(): AuthenticationFilter {
        return AuthenticationFilter(jwtTokenProvider())
    }

    @Bean
    fun jwtTokenProvider(): JwtTokenProvider {
        return JwtTokenProvider()
    }

}
