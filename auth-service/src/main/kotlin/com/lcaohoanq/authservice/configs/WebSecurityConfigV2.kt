package com.lcaohoanq.authservice.configs

import com.lcaohoanq.authservice.security.JwtAuthConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@ConditionalOnProperty(
    name = ["spring.application.security-config-version"],
    havingValue = "v2",
    matchIfMissing = true
)
@Configuration
class WebSecurityConfigV2(
    private val jwtAuthConverter: JwtAuthConverter,
    private val applicationProperties: ApplicationProperties
) {
    @Value("\${api.prefix}")
    private lateinit var apiPrefix: String

    // Use a companion object for constants
    companion object {
        private val PUBLIC_ENDPOINTS = arrayOf(
            "/actuator/**",
            "/api/v1/h2-console/**",
            "/graphiql",
            "/graphql",
            "/error",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
        )
        const val SHOPPE_MEMBER = "SHOPPE_MEMBER"
        const val SHOPPE_STAFF = "SHOPPE_STAFF"
        const val SHOPPE_ADMIN = "SHOPPE_ADMIN"
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors { cors ->
                cors.configurationSource(corsConfigurationSource())
            }
            // .addFilter(jwtTokenFilter)
            // We remove the JWT filter since it's handled by the Gateway now
            .authorizeHttpRequests { auth ->
                // Public authentication endpoints
                auth.requestMatchers(
                    "$apiPrefix/auth/**",
//                    "$apiPrefix/users/**",
                    "$apiPrefix/students/**",
                    "$apiPrefix/categories/**",
                    "$apiPrefix/experiments/**",
                    "$apiPrefix/otp/**",
                    "$apiPrefix/tokens/**",
                    "$apiPrefix/oauth2/**",
                    "$apiPrefix/ip/**",
                    "$apiPrefix/user-settings/**",
                    "$apiPrefix/keycloak/**",
                ).permitAll()

                auth.requestMatchers("$apiPrefix/users/all")
                    .hasAnyRole(SHOPPE_MEMBER, SHOPPE_STAFF, SHOPPE_ADMIN)
                auth.requestMatchers("$apiPrefix/users/details")
                    .hasAnyRole(SHOPPE_MEMBER, SHOPPE_STAFF, SHOPPE_ADMIN)

                // Swagger and public documentation endpoints
                auth.requestMatchers(*PUBLIC_ENDPOINTS).permitAll()

                // Role-based security for specific user roles
//                auth.requestMatchers("$apiPrefix/users/**").hasAnyRole("ADMIN", "STAFF")
//                auth.requestMatchers("$apiPrefix/categories/**").hasAnyRole("ADMIN", "MANAGER")
//                auth.requestMatchers("$apiPrefix/experiments/**").hasRole("USER")

                // All other endpoints require authentication
                auth.anyRequest().authenticated()
            }
            .oauth2ResourceServer {
                it.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthConverter)
                }
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .csrf { it.disable() }
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {

        val configuration = CorsConfiguration().apply {
            allowCredentials = true
            this.allowedOrigins = applicationProperties.cors.allowedOrigins
            addAllowedMethod("*")
            addAllowedHeader("*")
            maxAge = 3600
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}

@Configuration
@ConfigurationProperties(prefix = "app.cors")
class ApplicationProperties {
    var cors: CorsProperties = CorsProperties()

    class CorsProperties {
        var allowedOrigins: List<String> = listOf()
    }
}
