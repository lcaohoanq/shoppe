package com.lcaohoanq.shoppe.domain.auth;

import com.lcaohoanq.shoppe.annotation.RetryAndBlock;
import com.lcaohoanq.shoppe.component.LocalizationUtils;
import com.lcaohoanq.shoppe.exception.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.domain.token.Token;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.user.UserResponse;
import com.lcaohoanq.shoppe.api.ApiResponse;
import com.lcaohoanq.shoppe.domain.token.TokenService;
import com.lcaohoanq.shoppe.domain.user.IUserService;
import com.lcaohoanq.shoppe.util.DTOConverter;
import com.lcaohoanq.shoppe.util.Identifiable;
import com.lcaohoanq.shoppe.constant.MessageKey;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController implements Identifiable, DTOConverter {

    private final IUserService userService;
    private final LocalizationUtils localizationUtils;
    private final TokenService tokenService;
    private final HttpServletRequest request;
    private final IAuthService authService;

    @Timed(
        value = "custom.login.requests",
        extraTags = {"uri", "/api/v1/users/login"},
        description = "Track login request count")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
        @RequestBody @Valid UserLoginDTO userLoginDTO,
        BindingResult result,
        HttpServletRequest request
    ) throws Exception {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        String token = authService.login(userLoginDTO.email(), userLoginDTO.password());
        String userAgent = request.getHeader("User-Agent");
        UserResponse userDetail = authService.getUserDetailsFromToken(token);
        Token jwtToken = tokenService.addToken(userDetail.id(), token, isMobileDevice(userAgent));

        log.info("User logged in successfully");

        LoginResponse response = new LoginResponse(
            jwtToken.getToken(),
            jwtToken.getRefreshToken(),
            jwtToken.getRefreshExpirationDate(),
            jwtToken.getExpirationDate(),
            userDetail
        );

        return ResponseEntity.ok(ApiResponse.<LoginResponse>builder()
                                     .message(localizationUtils.getLocalizedMessage(
                                         MessageKey.LOGIN_SUCCESSFULLY))
                                     .statusCode(HttpStatus.OK.value())
                                     .isSuccess(true)
                                     .data(response)
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
                .data(toUserResponse(user))
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

