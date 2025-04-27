package com.lcaohoanq.keycloak.userextra

import com.lcaohoanq.keycloak.config.SwaggerConfig
import com.lcaohoanq.keycloak.userextra.dto.UserExtraDTO
import com.lcaohoanq.keycloak.userextra.model.UserExtra
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/userextras")
@Tag(name = "Movie API", description = "Movie API")
class UserExtraController(
    private val userExtraService: UserExtraService
) {

    @Operation(
        summary = "Get user extra information for the authenticated user",
        security = [SecurityRequirement(name = SwaggerConfig.BEARER_KEY_SECURITY_SCHEME)])
    @GetMapping("/me")
    fun getUserExtra(principal: Principal): UserExtra {
        return userExtraService.validateAndGetUserExtra(principal.name)
    }

    @Operation(
        summary = "Update user extra information for the authenticated user",
        security = [SecurityRequirement(name = SwaggerConfig.BEARER_KEY_SECURITY_SCHEME)])
    @PostMapping("/me")
    fun saveUserExtra(
        @RequestBody updateUserExtraRequest: @Valid UserExtraDTO.UserExtraRequest,
        principal: Principal
    ): UserExtra {
        val userExtraOptional = userExtraService.getUserExtra(principal.name)
        val userExtra = userExtraOptional.orElseGet { UserExtra(principal.name) }
        userExtra.avatar = updateUserExtraRequest.avatar
        return userExtraService.saveUserExtra(userExtra)
    }
}
