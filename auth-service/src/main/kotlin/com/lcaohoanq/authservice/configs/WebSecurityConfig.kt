package com.lcaohoanq.authservice.configs

import com.lcaohoanq.authservice.filters.JwtTokenFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@EnableWebMvc
class WebSecurityConfig(
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val accessDeniedHandler: AccessDeniedHandler,
    private val oAuth2LoginHandler: OAuth2LoginHandler,
    private val jwtTokenFilter: JwtTokenFilter
) {
    @Value("\${api.prefix}")
    private lateinit var apiPrefix: String

    // Use a companion object for constants
    companion object {
        private val PUBLIC_ENDPOINTS = arrayOf(
            "/actuator/**",
            "/h2-console/**",
            "/graphiql",
            "/graphql",
            "/error",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html"
        )
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { cors ->
                cors.configurationSource(corsConfigurationSource())
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session
            }
            // .addFilter(jwtTokenFilter)
            // We remove the JWT filter since it's handled by the Gateway now
            .authorizeHttpRequests { auth ->
                // Public authentication endpoints
                auth.requestMatchers(
                    "$apiPrefix/auth/**",
                    "$apiPrefix/users/**",
                    "$apiPrefix/students/**",
                    "$apiPrefix/categories/**",
                    "$apiPrefix/experiments/**",
                    "$apiPrefix/otp/**",
                    "$apiPrefix/tokens/**",
                    "$apiPrefix/oauth2/**",
                    "$apiPrefix/ip/**",
                    "$apiPrefix/user-settings/**",
                ).permitAll()

                // Swagger and public documentation endpoints
                auth.requestMatchers(*PUBLIC_ENDPOINTS).permitAll()

                // Role-based security for specific user roles
//                auth.requestMatchers("$apiPrefix/users/**").hasAnyRole("ADMIN", "STAFF")
//                auth.requestMatchers("$apiPrefix/categories/**").hasAnyRole("ADMIN", "MANAGER")
//                auth.requestMatchers("$apiPrefix/experiments/**").hasRole("USER")

                // All other endpoints require authentication
                auth.anyRequest().authenticated()
            }
            .oauth2Login { oauth2 ->
                oauth2.loginPage("http://localhost:4000/login")
                oauth2.successHandler(oAuth2LoginHandler)
                oauth2.failureUrl("http://localhost:4000/login?error=true")
//                oauth2.userInfoEndpoint { userInfo ->
//                    userInfo.userService(oAuth2LoginHandler)
//                }
                oauth2.authorizationEndpoint { endpoint ->
                    endpoint.baseUri("/api/v1/oauth2/authorize")
                }
            }
            .csrf { it.disable() }
            .exceptionHandling { ex ->
                ex.authenticationEntryPoint(authenticationEntryPoint)
                ex.accessDeniedHandler(accessDeniedHandler)
            }

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = mutableListOf("http://localhost:4000")
        configuration.allowedMethods =
            mutableListOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD")
        configuration.addAllowedHeader("*")
        configuration.allowCredentials = true
        configuration.maxAge = 3600

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
