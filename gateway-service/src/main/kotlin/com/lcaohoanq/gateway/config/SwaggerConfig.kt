package com.lcaohoanq.gateway.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.Configuration


@Configuration
@OpenAPIDefinition(
    info = Info(
        title = "Gateway Services",
        version = "1.0.0",
        description = "Gateway API documentation",
        termsOfService = "Terms and conditions applied",
        contact = Contact(
            name = "Hoang Cao Luu",
            email = "team@gmail.com",
            url = "team@example.com"
        ),
        license = License(name = "Honag License")
    ),
    servers = [Server(
        description = "devServer",
        url = "http://localhost:4003"
    ), Server(description = "testServer", url = "http://localhost:4003")],
    security = [SecurityRequirement(name = "bearer-key")]
)
@SecurityScheme(
    name = "bearer-key",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    description = "JWT Bearer authentication",
    bearerFormat = "JWT"
)
class SwaggerConfig {
    companion object {
        const val BEARER_KEY_SECURITY_SCHEME: String = "bearer-key"
    }
}
