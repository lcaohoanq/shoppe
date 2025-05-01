package com.lcaohoanq.gateway.config

import com.lcaohoanq.gateway.security.JwtAuthConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val authenticationEntryPoint: AuthenticationEntryPoint,
    private val accessDeniedHandler: AccessDeniedHandler,
    private val jwtAuthConverter: JwtAuthConverter
) {

    companion object {
        private val PUBLIC_ENDPOINTS = arrayOf(
            "/public/**",
            "/actuator/**",
            "/h2-console/**",
            "/graphiql",
            "/graphql",
            "/error",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
        )
        const val SHOPPE_MEMBER = "SHOPPE_MEMBER"
        const val SHOPPE_STAFF = "SHOPPE_STAFF"
        const val SHOPPE_ADMIN = "SHOPPE_ADMIN"
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .authorizeExchange { exchanges ->
                exchanges
                    .pathMatchers(*PUBLIC_ENDPOINTS).permitAll()

                    .pathMatchers("/keycloak/token").permitAll()
                    .pathMatchers("/keycloak/test").hasAnyRole(SHOPPE_MEMBER, SHOPPE_STAFF, SHOPPE_ADMIN)

                    .anyExchange().authenticated()
            }
            .oauth2ResourceServer {
                it.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthConverter)
                }
            }
//            .exceptionHandling { ex ->
//                ex.authenticationEntryPoint(authenticationEntryPoint)
//                ex.accessDeniedHandler(accessDeniedHandler)
//            }
            .csrf { it.disable() }
            .build()
    }
}
