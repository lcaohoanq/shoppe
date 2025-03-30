package com.lcaohoanq.jvservice.config

import com.lcaohoanq.jvservice.annotation.Disabled
import io.swagger.v3.oas.models.*
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
class SwaggerConfig {
    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Shoppe API Services")
                    .version("1.0.0")
                    .description("REST API documentation for Shoppe services")
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
                    .addSchemas(
                        "ApiResponse", Schema<Any>()
                            .type("object")
                            .properties(
                                mapOf(
                                    "message" to StringSchema(),
                                    "reason" to StringSchema(),
                                    "statusCode" to IntegerSchema(),
                                    "isSuccess" to BooleanSchema(),
                                    "data" to Schema<Any>().type("object")
                                )
                            )
                    )
                    .addSchemas(
                        "ApiErrorResponse", Schema<Any>()
                            .type("object")
                            .properties(
                                mapOf(
                                    "message" to StringSchema().example("Error message"),
                                    "reason" to StringSchema().example("Detailed error reason"),
                                    "status_code" to IntegerSchema().example(400),
                                    "is_success" to BooleanSchema().example(false)
                                )
                            )
                    )
            )
            .addServersItem(Server().url("/").description("Local server"))

    }

    @Bean
    @Disabled
    fun publicApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("public")
            .addOpenApiCustomizer { openApi: OpenAPI ->
                // Remove default responses
                openApi.paths.values.forEach { pathItem: PathItem ->
                    pathItem.readOperations().forEach { operation: Operation ->
                        operation.responses.keys.removeIf { statusCode: String ->
                            !(operation.responses[statusCode]?.content?.containsKey("application/json")
                                ?: false)
                        }
                    }
                }
            }
            .build()
    }

}