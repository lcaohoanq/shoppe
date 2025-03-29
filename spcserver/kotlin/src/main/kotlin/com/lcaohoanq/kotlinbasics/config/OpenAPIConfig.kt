package com.lcaohoanq.kotlinbasics.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.media.BooleanSchema
import io.swagger.v3.oas.models.media.IntegerSchema
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.media.StringSchema
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
                    .title("My API Services")
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
            .addSecurityItem(SecurityRequirement().addList("JavaInUseSecurityScheme"))
            .components(
                Components()
                    .addSecuritySchemes(
                        "JavaInUseSecurityScheme",
                        SecurityScheme()
                            .name("JavaInUseSecurityScheme")
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")
                            .description("Please enter JWT token")
                    )
                    .addSchemas("ApiResponse", createApiResponseSchema())
                    .addSchemas("ApiErrorResponse", createApiErrorResponseSchema())
            )
            .addServersItem(Server().url("/").description("Local server"))
    }

    private fun createApiResponseSchema(): Schema<*> {
        val schema = Schema<Any>()
            .type("object")
            .description("Standard API response wrapper")

        schema.properties =
            object : HashMap<String?, Schema<*>?>() {
                init {
                    put("message", StringSchema().example("Operation successful"))
                    put("reason", StringSchema().example("The operation completed successfully"))
                    put("statusCode", IntegerSchema().example(200))
                    put("isSuccess", BooleanSchema().example(true))
                    put(
                        "data",
                        Schema<Any>().type("object")
                            .description("Response payload - varies by endpoint")
                    )
                }
            }

        return schema
    }

    private fun createApiErrorResponseSchema(): Schema<*> {
        val schema = Schema<Any>()
            .type("object")
            .description("Error response wrapper")

        schema.properties =
            object : HashMap<String?, Schema<*>?>() {
                init {
                    put("message", StringSchema().example("Error message"))
                    put("reason", StringSchema().example("Detailed error reason"))
                    put("status_code", IntegerSchema().example(400))
                    put("is_success", BooleanSchema().example(false))
                }
            }

        return schema
    }

    @Bean
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("public")
            .pathsToMatch("/**") // Ensure you're matching the correct paths
            .build()
    }
}