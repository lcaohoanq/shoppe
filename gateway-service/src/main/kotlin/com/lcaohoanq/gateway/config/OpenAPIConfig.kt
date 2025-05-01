package com.lcaohoanq.gateway.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Gateway Services")
                    .version("1.0.0")
                    .description("REST API documentation for My services")
                    .contact(
                        Contact()
                            .name("Your Team Name")
                            .email("team@example.com")
                    )
                    .license(
                        License()
                            .name("Private License")
                    )
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("API Documentation")
                    .url("https://your-docs-url.com")
            )
            .addSecurityItem(SecurityRequirement().addList(BEARER_KEY_SECURITY_SCHEME))
            .components(
                Components()
                    .addSecuritySchemes(
                        BEARER_KEY_SECURITY_SCHEME,
                        SecurityScheme()
                            .name("BEARER_KEY_SECURITY_SCHEME")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .description("Please enter JWT token")
                    )
            )
            .addServersItem(Server().url("/").description("Local server"))
    }

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("public")
            .pathsToMatch("/**") // Ensure you're matching the correct paths
            .build()
    }

    companion object {
        const val BEARER_KEY_SECURITY_SCHEME = "bearer-key"
    }

}
