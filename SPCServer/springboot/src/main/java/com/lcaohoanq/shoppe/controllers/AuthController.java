package com.lcaohoanq.shoppe.controllers;

import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.dtos.request.UserLoginDTO;
import com.lcaohoanq.shoppe.dtos.request.UserRegisterDTO;
import com.lcaohoanq.shoppe.exceptions.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.models.Token;
import com.lcaohoanq.shoppe.models.User;
import com.lcaohoanq.shoppe.dtos.responses.LoginResponse;
import com.lcaohoanq.shoppe.dtos.responses.UserResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiResponse;
import com.lcaohoanq.shoppe.services.auth.AuthService;
import com.lcaohoanq.shoppe.services.token.TokenService;
import com.lcaohoanq.shoppe.services.user.IUserService;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import com.lcaohoanq.shoppe.utils.Identifiable;
import com.lcaohoanq.shoppe.utils.MessageKey;
import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final AuthService authService;

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
        User userDetail = authService.getUserDetailsFromToken(token);
        Token jwtToken = tokenService.addToken(userDetail, token, isMobileDevice(userAgent));

        log.info("User logged in successfully");

        LoginResponse response = new LoginResponse(
            jwtToken.getToken(),
            jwtToken.getRefreshToken(),
            jwtToken.getTokenType() == null ? "Bearer" : jwtToken.getTokenType(),
            userDetail.getId(),
            userDetail.getUsername(),
            userDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList()
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
        @RequestBody @Valid UserRegisterDTO userRegisterDTO,
        BindingResult result
    ) throws Exception {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        User user = authService.register(userRegisterDTO);
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
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_STORE_OWNER')")
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

}

