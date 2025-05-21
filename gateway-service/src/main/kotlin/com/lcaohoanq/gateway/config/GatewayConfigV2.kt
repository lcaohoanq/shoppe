package com.lcaohoanq.gateway.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@ConditionalOnProperty(name = ["spring.application.gw-config-version"], havingValue = "v2", matchIfMissing = true)
@Configuration
class GatewayConfigV2 {
    // Service and API Constants
    companion object {
        const val API_PREFIX_V1 = "/api/v1"

        // Eureka service instances
        const val AUTH_EUREKA = "lb://auth-service"
        const val CATEGORY_EUREKA = "lb://category-service"
        const val NOTIFICATION_EUREKA = "lb://notification-service"
    }

    @Bean
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            // Public routes for Swagger
            .route("kt_swagger_redirect") {
                it.path("/kt/swagger")
                    .filters { f -> f.redirect(302, "http://localhost:4000/swagger-ui/index.html") }
                    .uri("http://localhost:4000")
            }
            .route("jv_swagger_redirect") {
                it.path("/jv/swagger")
                    .filters { f -> f.redirect(302, "http://localhost:8080/swagger-ui/index.html") }
                    .uri("http://localhost:8080")
            }

            // Routes for user-related services
            // Note: Authentication will be handled by global KeycloakAuthFilter
            .route("auth_service_route") {
                it.path(
                    "${API_PREFIX_V1}/users/**",
                    "${API_PREFIX_V1}/auth/**",
                    "${API_PREFIX_V1}/otp/**"
                )
                    .uri(AUTH_EUREKA)
            }

            // Category Service Route
            .route("category_service_route") {
                it.path("${API_PREFIX_V1}/categories/**")
                    .uri(CATEGORY_EUREKA)
            }

            // Notification Service Route
            .route("notification_service_route") {
                it.path("${API_PREFIX_V1}/mail/**")
                    .uri(NOTIFICATION_EUREKA)
            }

            .build()
    }
}
