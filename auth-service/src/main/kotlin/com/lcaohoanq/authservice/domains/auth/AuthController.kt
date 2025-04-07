package com.lcaohoanq.authservice.domains.auth

import com.lcaohoanq.authservice.domains.user.IUserService
import com.lcaohoanq.authservice.dto.TokenPort
import com.lcaohoanq.common.apis.MyApiResponse
import com.lcaohoanq.commonspring.components.LocalizationUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "Auth", description = "Auth API")
@ComponentScan(basePackages = ["com.lcaohoanq.commonspring"])
@RestController
@RequestMapping("\${api.prefix}/auth")
class AuthController(
    private val authService: IAuthService,
    private val userService: IUserService,
    private val request: HttpServletRequest,
    private val localizationUtils: LocalizationUtils
) {

    @Operation(
        summary = "Login to the system",
        description = "Authenticate user and return JWT tokens",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Login successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = AuthResponseWrapper::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: AuthPort.AuthRequest): ResponseEntity<MyApiResponse<AuthPort.AuthResponse>> {
        val response = authService.login(loginRequest)
        return ResponseEntity.ok(
            MyApiResponse(
                message = localizationUtils.getLocalizedMessage("auth.login.success"),
                data = response
            )
        )
    }

    @Operation(summary = "Register", description = "Register")
    @PostMapping("/register")
    fun register(@RequestBody user: AuthPort.SignUpReq): ResponseEntity<MyApiResponse<Unit>> =
        ResponseEntity.ok(
            MyApiResponse(
                message = localizationUtils.getLocalizedMessage("user.register.register_successfully"),
                data = authService.register(user)
            )
        )

    @PreAuthorize("permitAll()")
    @PostMapping("/refresh-token")
    @Operation(summary = "Send refresh token to get new access token")
    fun refreshToken(
        @Valid @RequestBody refreshTokenDTO: TokenPort.RefreshTokenDTO,
        result: BindingResult
    ): ResponseEntity<MyApiResponse<AuthPort.AuthResponse>> {
        if (result.hasErrors()) {
            throw com.lcaohoanq.common.exceptions.MethodArgumentNotValidException(result)
        }

        return ResponseEntity.ok(
            MyApiResponse(
                message = "Refresh token successfully",
                data = authService.refreshToken(refreshTokenDTO)
            )
        )
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_STAFF')")
    @PostMapping("/logout")
    @Operation(
        summary = "Logout from the system",
        description = "Invalidate the current JWT token",
        security = [io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "JavaInUseSecurityScheme")]
    )
    fun logout(): ResponseEntity<MyApiResponse<Unit>> {
        val authorizationHeader: String = request.getHeader("Authorization")

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw com.lcaohoanq.common.exceptions.TokenNotFoundException("Token not found")
        }
        val token = authorizationHeader.substring(7)

        val userDetails = SecurityContextHolder.getContext()
            .authentication.principal as UserDetails
        val user = userService.findByEmail(userDetails.username)
            ?: throw com.lcaohoanq.common.exceptions.base.DataNotFoundException("User not found")

        authService.logout(token, user) //revoke token

        return ResponseEntity.ok(
            MyApiResponse(
                message = "Logout successfully"
            )
        )
    }

}