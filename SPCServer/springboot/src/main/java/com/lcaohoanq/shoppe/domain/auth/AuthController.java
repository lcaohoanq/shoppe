package com.lcaohoanq.shoppe.domain.auth;

import com.lcaohoanq.shoppe.annotation.RetryAndBlock;
import com.lcaohoanq.shoppe.api.ApiResponse;
import com.lcaohoanq.shoppe.component.LocalizationUtils;
import com.lcaohoanq.shoppe.constant.MessageKey;
import com.lcaohoanq.shoppe.domain.auth.AuthPort.AccountRegisterDTO;
import com.lcaohoanq.shoppe.domain.auth.AuthPort.LoginResponse;
import com.lcaohoanq.shoppe.domain.auth.AuthPort.VerifyUserDTO;
import com.lcaohoanq.shoppe.domain.token.ITokenService;
import com.lcaohoanq.shoppe.domain.user.IUserService;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.user.UserResponse;
import com.lcaohoanq.shoppe.exception.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.mapper.UserMapper;
import com.lcaohoanq.shoppe.util.Identifiable;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("${api.prefix}/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "APIs for authentication including login, register, logout, and token refresh")
public class AuthController implements Identifiable {

    IUserService userService;
    LocalizationUtils localizationUtils;
    ITokenService tokenService;
    HttpServletRequest request;
    IAuthService authService;
    UserMapper userMapper;

    @PostMapping("/login")
    @Operation(
        summary = "Login to the system",
        description = "Authenticate user and return JWT tokens",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Login successfully",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation =
                        LoginResponse.class),
                    examples = @ExampleObject(
                        value = """
                            {
                               "message": "Login successfully",
                               "data": {
                                 "token": {
                                   "id": 51,
                                   "access_token": "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjIsImVtYWlsIjoiaG9hbmdjbHdAZ21haWwuY29tIiwic3ViIjoiaG9hbmdjbHdAZ21haWwuY29tIiwiZXhwIjoxNzM2NTg5NDU0fQ.gWpYRnR_96toYNNKD8Zdftb5wh8TPucb22pOGp5kmnM",
                                   "refresh_token": "bfde7641-7f59-4dca-ba71-468821b5c511",
                                   "token_type": "Bearer",
                                   "expires": "2025-01-11T16:57:34.853Z",
                                   "expires_refresh_token": "2025-01-12T16:27:34.853Z",
                                   "is_mobile": false
                                 }
                               },
                               "status_code": 200,
                               "is_success": true
                             }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "400",
                description = "Wrong email or password",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = """
                               {
                                  "message": "Wrong email or password",
                                  "reason": "Wrong phone number or password",
                                  "status_code": 400,
                                  "is_success": false
                            }
                            """
                    )
                )
            ),
        })
    public ResponseEntity<ApiResponse<LoginResponse>> login(
        @RequestBody @Valid
        @Parameter(description = "User credentials", required = true,
            content = @Content(examples = @ExampleObject(
                value = """
                    {
                      "email": "pied@team.com",
                      "password": "Piedteam123^^"
                    }
                    """
            ))
        )
        AuthPort.UserLoginDTO userLoginDTO,
        BindingResult result
    ) throws Exception {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        return ResponseEntity.ok(
            ApiResponse.<LoginResponse>builder()
                .message(localizationUtils.getLocalizedMessage(MessageKey.LOGIN_SUCCESSFULLY))
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(
                    authService.login(
                        userLoginDTO.email(),
                        userLoginDTO.password())
                )
                .build());
    }

    @Timed(
        value = "custom.register.requests",
        extraTags = {"uri", "/api/v1/users/register"},
        description = "Track register request count")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
        @RequestBody @Valid AccountRegisterDTO accountRegisterDTO,
        BindingResult result
    ) throws Exception {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        User user = authService.register(accountRegisterDTO);
        log.info("New user registered successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<UserResponse>builder()
                .message(
                    localizationUtils.getLocalizedMessage(MessageKey.REGISTER_SUCCESSFULLY))
                .statusCode(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(userMapper.toUserResponse(user))
                .build());
    }

    @Timed(
        value = "custom.logout.requests",
        extraTags = {"uri", "/api/v1/users/logout"},
        description = "Track logout request count")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Objects>> logout() throws Exception {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername());

            authService.logout(token, user); //revoke token

            return ResponseEntity.ok().body(
                ApiResponse.<Objects>builder()
                    .message(
                        localizationUtils.getLocalizedMessage(
                            MessageKey.LOGOUT_SUCCESSFULLY))
                    .statusCode(HttpStatus.OK.value())
                    .isSuccess(true)
                    .build());
        } else {
            return ResponseEntity.badRequest().body(
                ApiResponse.<Objects>builder()
                    .message(
                        localizationUtils.getLocalizedMessage(
                            MessageKey.LOGOUT_FAILED))
                    .reason("Authorization header is missing")
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .isSuccess(false)
                    .build());
        }
    }

    @PutMapping("/verify/{otp}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<ApiResponse<OtpResponse>> verifiedUser(
        @PathVariable int otp
    ) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        authService.verifyOtpToVerifyUser(user.getId(), String.valueOf(otp));
        return ResponseEntity.ok().body(
            ApiResponse.<OtpResponse>builder()
                .message(MessageKey.VERIFY_USER_SUCCESSFULLY)
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .build());
    }

    @Timed(
        value = "custom.verify.requests",
        extraTags = {"uri", "/api/v1/users/verify"},
        description = "Track verify request count")
    @RetryAndBlock(maxAttempts = 3, blockDurationSeconds = 3600, maxDailyAttempts = 6)
    @PostMapping("/send-verify-otp")
    public ResponseEntity<ApiResponse<OtpResponse>> verifiedUserNotLogin(
        @Valid @RequestBody VerifyUserDTO verifyUserDTO,
        BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        User user = userService.findUserByEmail(verifyUserDTO.email());
        authService.verifyOtpToVerifyUser(user.getId(), verifyUserDTO.otp());
        return ResponseEntity.ok().body(
            ApiResponse.<OtpResponse>builder()
                .message(MessageKey.VERIFY_USER_SUCCESSFULLY)
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .build());
    }

}

