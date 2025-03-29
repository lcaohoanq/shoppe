package com.lcaohoanq.kotlinbasics.domain.auth

import com.lcaohoanq.kotlinbasics.api.ApiResponse
import com.lcaohoanq.kotlinbasics.component.LocalizationUtils
import com.lcaohoanq.kotlinbasics.domain.token.TokenPort
import com.lcaohoanq.kotlinbasics.domain.user.IUserService
import com.lcaohoanq.kotlinbasics.exceptions.MethodArgumentNotValidException
import com.lcaohoanq.kotlinbasics.exceptions.TokenNotFoundException
import com.lcaohoanq.kotlinbasics.exceptions.base.DataNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
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
@RestController
@RequestMapping("\${api.prefix}/auth")
class AuthController(
    private val authService: IAuthService,
    private val userService: IUserService,
    private val request: HttpServletRequest,
    private val localizationUtils: LocalizationUtils
) {

    @Operation(summary = "Login", description = "Login")
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: AuthPort.AuthRequest): ResponseEntity<ApiResponse<AuthPort.AuthResponse>> {
        val response = authService.login(loginRequest)
        return ResponseEntity.ok(
            ApiResponse(
                message = localizationUtils.getLocalizedMessage("auth.login.success"),
                data = response
            )
        )
    }

    @Operation(summary = "Register", description = "Register")
    @PostMapping("/register")
    fun register(@RequestBody user: AuthPort.SignUpReq): ResponseEntity<ApiResponse<Unit>> =
        ResponseEntity.ok(
            ApiResponse(
                message = "Create user successfully",
                data = authService.register(user)
            )
        )

    @PreAuthorize("permitAll()")
    @PostMapping("/refresh-token")
    @Operation(summary = "Send refresh token to get new access token")
    fun refreshToken(
        @Valid @RequestBody refreshTokenDTO: TokenPort.RefreshTokenDTO,
        result: BindingResult
    ): ResponseEntity<ApiResponse<AuthPort.AuthResponse>> {
        if (result.hasErrors()) {
            throw MethodArgumentNotValidException(result)
        }

        return ResponseEntity.ok(
            ApiResponse(
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
    fun logout(): ResponseEntity<ApiResponse<Unit>> {
        val authorizationHeader: String = request.getHeader("Authorization")

        if (!authorizationHeader.startsWith("Bearer ")) {
            throw TokenNotFoundException("Token not found")
        }
        val token = authorizationHeader.substring(7)

        val userDetails = SecurityContextHolder.getContext()
            .authentication.principal as UserDetails
        val user = userService.findByEmail(userDetails.username)
            ?: throw DataNotFoundException("User not found")

        authService.logout(token, user) //revoke token

        return ResponseEntity.ok(
            ApiResponse(
                message = "Logout successfully"
            )
        )
    }

}