package com.lcaohoanq.authservice.domains.auth

import com.lcaohoanq.authservice.bases.BaseController
import com.lcaohoanq.authservice.domains.mfa.CodeGenerator
import com.lcaohoanq.authservice.domains.user.IUserService
import com.lcaohoanq.authservice.exceptions.MethodArgumentNotValidException
import com.lcaohoanq.common.apis.MyApiResponse
import com.lcaohoanq.common.dto.AuthPort
import com.lcaohoanq.common.dto.TokenPort
import dev.turingcomplete.kotlinonetimepassword.GoogleAuthenticator.Companion.createRandomSecret
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.io.File
import javax.imageio.ImageIO


@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("\${api.prefix}/auth")
class AuthController(
    private val authService: IAuthService,
    private val userService: IUserService,
    private val generator: CodeGenerator,
    private val request: HttpServletRequest
) : BaseController() {

    private val log = mu.KotlinLogging.logger {}

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
    fun login(@RequestBody req: AuthPort.AuthRequest): ResponseEntity<MyApiResponse<LoginResult>>
    = ok("Login successfully", authService.login(req))


    @Operation(summary = "Register", description = "Register")
    @PostMapping("/register")
    fun register(
        @Valid @RequestBody user: AuthPort.SignUpReq,
        bindingResult: BindingResult
    ): ResponseEntity<MyApiResponse<Unit>> {

        if (bindingResult.hasErrors()) throw MethodArgumentNotValidException(bindingResult)

        authService.register(user)
        return ok("Register successfully")
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/refresh-token")
    @Operation(summary = "Send refresh token to get new access token")
    fun refreshToken(
        @Valid @RequestBody refreshTokenDTO: TokenPort.RefreshTokenDTO,
        result: BindingResult
    ): ResponseEntity<MyApiResponse<AuthPort.AuthResponse>> {
        if (result.hasErrors()) throw MethodArgumentNotValidException(result)
        return ok(
            message = "Refresh token successfully",
            data = authService.refreshToken(refreshTokenDTO)
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

        return ok("Logout successfully")
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

        if (bindingResult.hasErrors()) throw MethodArgumentNotValidException(bindingResult)

        val response = authService.generateTokenFromEmail(data.email)
        return ok("Generate token successfully", response)
    }

    @PreAuthorize("permitAll()")
    @PatchMapping("/change-password")
    @Operation(
        summary = "Change password",
        description = "Change password",
    )
    fun changePassword(
        @Valid @RequestBody data: AuthPort.ChangePasswordReq,
        bindingResult: BindingResult
    ): ResponseEntity<MyApiResponse<Unit>> {
        if (bindingResult.hasErrors()) throw MethodArgumentNotValidException(bindingResult)

        authService.changePassword(data)
        return ok("Change password successfully")
    }


    @GetMapping("/verify-account")
    @Operation(
        summary = "Verify account",
        description = "This link will sent via email, user press to verify account",
    )
    fun verifyAccount(@RequestParam token: String): ResponseEntity<MyApiResponse<Unit>> {
        authService.verifyAccount(token)
        return ok("Verify account successfully")
    }

    @GetMapping("/disable-account")
    @Operation(
        summary = "Disable account",
        description = "This link will sent via email, user press to disable account",
    )
    fun disableAccount(@RequestParam token: String): ResponseEntity<MyApiResponse<Unit>> {
        authService.disableAccount(token)
        return ok("Disable account successfully")
    }

    private val secret = createRandomSecret()
//    private val secretEncoded = Base64.getEncoder().encodeToString(secret)

    init {
        log.info { "Secret byte array: $secret" }
//        log.info { "Secret encoded: $secretEncoded" }
    }

    @PatchMapping("/2fa/setup/{email}")
    fun generate2FAForUser(@PathVariable email: String): ResponseEntity<MyApiResponse<String>> {
        val qrCodeUrl = authService.setup2FA(email)
        return ok("Setup 2FA successfully", qrCodeUrl)
    }


//    @Scheduled(fixedRate = 1000L)
//    fun ping() {
//        val timestamp = Date(System.currentTimeMillis())
//        val code = GoogleAuthenticator(secret).generate(timestamp)
//
//        log.info("Code: $code")
//    }

    @GetMapping("/code/{secret}")
    fun code(@PathVariable secret: String, @RequestParam email: String): ResponseEntity<Resource> {
        // Generate the QR code image
        val bufferedImage = generator.generate("Shoppe", email, secret)

        // Create a temporary file to store the image
        val tempFile = File.createTempFile("qrcode-", ".png")

        // Write the buffered image to the temp file as PNG
        ImageIO.write(bufferedImage, "PNG", tempFile)

        // Create a resource from the file
        val resource = FileSystemResource(tempFile)

        // Set headers for proper display
        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"qrcode-${secret}.png\"")
        }

        // Auto-delete the temp file when the JVM exits
        tempFile.deleteOnExit()

        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .headers(headers)
            .body(resource)
    }

    @PostMapping("/verify-2fa")
    @Operation(
        summary = "Verify 2FA code",
        description = "Verify 2FA code",
    )
    fun verify2fa(
        @Valid @RequestBody data: AuthPort.Verify2FAReq,
        bindingResult: BindingResult
    ): ResponseEntity<MyApiResponse<Unit>> {
        if (bindingResult.hasErrors()) throw MethodArgumentNotValidException(bindingResult)

        authService.verify2fa(data)
        return ok("Verify 2FA successfully")
    }
}