package com.lcaohoanq.gateway.config

import com.lcaohoanq.gateway.provider.JwtTokenProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.support.ipresolver.RemoteAddressResolver
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@ConditionalOnProperty(name = ["spring.application.gw-config-version"], havingValue = "v1", matchIfMissing = true)
@Deprecated(message = "Use GatewayConfigV2 instead")
@Configuration
class GatewayConfig {

    companion object {
        const val API_PREFIX_V1 = "/api/v1"

        // Eureka service instances
        const val AUTH_EUREKA = "lb://auth-service"
        const val CATEGORY_EUREKA = "lb://category-service"
        const val NOTIFICATION_EUREKA = "lb://notification-service"

        // Local development fallbacks
        const val KT_SERVICE = "http://localhost:4000"
        const val JV_SERVICE = "http://localhost:8080"
    }

    /**
     * Configures the routes for the Spring Cloud Gateway.
     * Each route is defined with a path and a URI to forward requests to.
     *
     * @param builder The RouteLocatorBuilder used to build the routes.
     * @return A RouteLocator containing the defined routes.
     */

    @Bean
    fun routeLocator(
        builder: RouteLocatorBuilder
    ): RouteLocator {
        return builder.routes()

            /*
            * Swagger Route
            * */

            // Swagger UI + OpenAPI từ kt-service qua gateway
//            .route("kt_service_swagger") {
//                it.path(
//                    "/swagger-ui.html",
//                    "/swagger-ui/**",
//                    "/v3/api-docs/**"
//                )
//                    .uri("http://localhost:4000")
//            }

            // Nếu bạn dùng nhiều service, có thể route dạng prefix như sau:
            // Route for Swagger UI of KT Service
            // Redirect from /kt/swagger to /kt/swagger-ui/index.html on KT service
            .route("kt_swagger_redirect") {
                it.path("/kt/swagger")
                    .filters { f ->
                        // Redirect to the full Swagger UI index page on the KT service
                        f.redirect(302, "${KT_SERVICE}/swagger-ui/index.html")
                    }
                    .uri(KT_SERVICE) // KT Service URI
            }

            // JV Service Swagger UI
            .route("jv_swagger_redirect") {
                it.path("/jv/swagger")
                    .filters { f ->
                        // Redirect to the full Swagger UI index page on the KT service
                        f.redirect(302, "${JV_SERVICE}/swagger-ui/index.html")
                    }
                    .uri(JV_SERVICE) // URI của JV Service
            }

            // Route for /api/v1/users, which should go to the User Service on localhost:4000

            // Route for /api/v1/categories, which should go to the Category Service on localhost:8080
            .route("category_service_route") { r ->
                r.path("${API_PREFIX_V1}/categories/**") // Match any requests that start with /api/v1/categories
                    .uri(CATEGORY_EUREKA) // Forward to the Category service
            }
            // Route for /api/v1/auth/login (bypassing authentication filter)
            // Before use Eureka as a service discovery (need to explicitly define the service name, hardcoded the URL)
//            .route("auth_service_route") { r ->
//                r.path("${API_PREFIX_V1}/auth/login") // Match /api/v1/auth/login path
//                    .uri(KT_SERVICE) // Forward to the authentication service
//            }

            // After using Eureka, you can use the service name directly
            // Provide the option lb://<service-name> to use load balancing (default is round-robin)
            // When the request to /api/v1/auth/login is received, it will notify the eureka server to find the service instance
            // and forward the request to that instance.
            .route("auth_service_route") { r ->
                r.path("${API_PREFIX_V1}/auth/**", "${API_PREFIX_V1}/otp/**", "${API_PREFIX_V1}/users/**")
                    .uri(AUTH_EUREKA)
            }

            .route("notification_service_route") { r ->
                r.path("${API_PREFIX_V1}/mail/**")
                    .uri(NOTIFICATION_EUREKA)
            }

            .build()
    }

//    @Bean
//    fun authenticationFilter(): AuthenticationFilter {
//        return AuthenticationFilter(jwtTokenProvider())
//    }

    @Bean
    fun jwtTokenProvider(): JwtTokenProvider {
        return JwtTokenProvider()
    }

    @Bean
    fun remoteAddressResolver(): RemoteAddressResolver {
        return XForwardedRemoteAddressResolver.maxTrustedIndex(1)
    }


}
