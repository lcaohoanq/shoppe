package com.lcaohoanq.gateway.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun swaggerRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            // Route for Swagger UI resources
            .route("swagger-ui") { r ->
                r.path("/swagger-ui/**")
                    .uri("forward:/swagger-ui/")
            }
            // Route for Swagger UI webjars
            .route("swagger-ui-webjars") { r ->
                r.path("/webjars/**")
                    .uri("forward:/webjars/")
            }
            // Route for OpenAPI spec
            .route("api-docs") { r ->
                r.path("/v3/api-docs/**")
                    .uri("forward:/v3/api-docs")
            }
            .build()
    }
}
