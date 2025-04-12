package com.lcaohoanq.authservice.domains.auth

import com.lcaohoanq.authservice.domains.user.IUserService
import com.lcaohoanq.authservice.exceptions.MethodArgumentNotValidException
import com.lcaohoanq.common.apis.MyApiResponse
import com.lcaohoanq.common.dto.AuthPort
import com.lcaohoanq.common.dto.TokenPort
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("\${api.prefix}/auth")
class AuthController(
    private val authService: IAuthService,
    private val userService: IUserService,
    private val request: HttpServletRequest
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
                message = "Login successfully",
                data = response
            )
        )
    }

    @Operation(summary = "Register", description = "Register")
    @PostMapping("/register")
    fun register(
        @Valid @RequestBody user: AuthPort.SignUpReq,
        bindingResult: BindingResult
    ): ResponseEntity<MyApiResponse<Unit>> {

        if (bindingResult.hasErrors()) throw MethodArgumentNotValidException(bindingResult)

        authService.register(user)
        return ResponseEntity.ok(
            MyApiResponse(
                message = "Register successfully",
            )
        )
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/refresh-token")
    @Operation(summary = "Send refresh token to get new access token")
    fun refreshToken(
        @Valid @RequestBody refreshTokenDTO: TokenPort.RefreshTokenDTO,
        result: BindingResult
    ): ResponseEntity<MyApiResponse<AuthPort.AuthResponse>> {
        if (result.hasErrors()) {
            throw MethodArgumentNotValidException(result)
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

    @PreAuthorize("permitAll()")
    @PostMapping("/generate-token-from-email")
    @Operation(
        summary = "Generate token from email",
        description = "Generate token from email",
    )
    fun generateTokenFromEmail(
        @Valid @RequestBody data: AuthPort.VerifyEmailReq,
        bindingResult: BindingResult
    ): ResponseEntity<MyApiResponse<String>> {

        if(bindingResult.hasErrors()) throw MethodArgumentNotValidException(bindingResult)

        val response = authService.generateTokenFromEmail(data.email)
        return ResponseEntity.ok(
            MyApiResponse(
                message = "Token generated successfully",
                data = response
            )
        )
    }


    @PatchMapping("/verify-account")
    @Operation(
        summary = "Verify account",
        description = "This link will sent via email, user press to verify account",
    )
    fun verifyAccount(@RequestParam token: String): ResponseEntity<MyApiResponse<Unit>> {
        authService.verifyAccount(token)
        return ResponseEntity.ok(
            MyApiResponse(
                message = "Verify account successfully",
            )
        )
    }

}