package com.lcaohoanq.shoppe.controllers;

import com.lcaohoanq.shoppe.components.JwtTokenUtils;
import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.dtos.UpdateUserDTO;
import com.lcaohoanq.shoppe.exceptions.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.models.User;
import com.lcaohoanq.shoppe.dtos.responses.UserResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiResponse;
import com.lcaohoanq.shoppe.services.token.TokenService;
import com.lcaohoanq.shoppe.services.user.IUserService;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import com.lcaohoanq.shoppe.utils.MessageKey;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;
    private final LocalizationUtils localizationUtils;
    private final TokenService tokenService;
    private final HttpServletRequest request;
    private final JwtTokenUtils jwtTokenUtils;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(DTOConverter.toUserResponse(userService.getUserById(id)));
    }

    @PostMapping("/details")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_STORE_OWNER')")
    public ResponseEntity<UserResponse> takeUserDetailsFromToken() throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return ResponseEntity.ok(
            DTOConverter.toUserResponse(userService.findByUsername(userDetails.getUsername())));
    }

    // PUT: localhost:4000/api/v1/users/4/deposit/100
    // Header: Authorization Bearer token
//    @PutMapping("/{userId}/deposit/{payment}")
//    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_STORE_OWNER')")
//    public ResponseEntity<String> deposit(
//        @PathVariable long userId,
//        @PathVariable long payment
//    ) {
//
//        if (payment <= 0) {
//            throw new MalformDataException("Payment must be greater than 0.");
//        }
//
//        try {
//            userService.updateAccountBalance(userId, payment);
//            return ResponseEntity.ok().body("Successfully deposited.");
//        } catch (DataNotFoundException e) {
//            return ResponseEntity.badRequest().body("User not found.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PutMapping("/details/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_STORE_OWNER')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserDetails(
        @PathVariable Long userId,
        @Valid @RequestBody UpdateUserDTO updatedUserDTO,
        BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        // Ensure that the user making the request matches the user being updated
        if (!Objects.equals(user.getId(), userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(
            ApiResponse.<UserResponse>builder()
                .message(MessageKey.UPDATE_USER_SUCCESSFULLY)
                .data(DTOConverter.toUserResponse(userService.updateUser(userId, updatedUserDTO)))
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build());
    }

//    @PutMapping("/block/{userId}/{active}")
//    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_STORE_OWNER')")
//    public ResponseEntity<String> blockOrEnable(
//        @Valid @PathVariable long userId,
//        @Valid @PathVariable int active
//    ) {
//        try {
//            userService.blockOrEnable(userId, active > 0);
//            String message =
//                active > 0 ? "Successfully enabled the user." : "Successfully blocked the user.";
//            return ResponseEntity.ok().body(message);
//        } catch (DataNotFoundException e) {
//            return ResponseEntity.badRequest().body("User not found.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

//    @PutMapping("/verify/{otp}")
//    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_STORE_OWNER')")
//    public ResponseEntity<ApiResponse<OtpResponse>> verifiedUser(
//        @PathVariable int otp
//    ) throws Exception {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
//            .getAuthentication().getPrincipal();
//        User user = userService.findByUsername(userDetails.getUsername());
//
//        userService.verifyOtpToVerifyUser(user.getId(), String.valueOf(otp));
//        return ResponseEntity.ok().body(
//            ApiResponse.<OtpResponse>builder()
//                .message(MessageKey.VERIFY_USER_SUCCESSFULLY)
//                .statusCode(HttpStatus.OK.value())
//                .isSuccess(true)
//                .build());
//    }

//    @Timed(
//        value = "custom.verify.requests",
//        extraTags = {"uri", "/api/v1/users/verify"},
//        description = "Track verify request count")
//    @RetryAndBlock(maxAttempts = 3, blockDurationSeconds = 3600, maxDailyAttempts = 6)
//    @PostMapping("/verify")
//    public ResponseEntity<ApiResponse<OtpResponse>> verifiedUserNotLogin(
//        @Valid @RequestBody VerifyUserDTO verifyUserDTO,
//        BindingResult result
//    ) throws Exception {
//        if (result.hasErrors()) {
//            throw new MethodArgumentNotValidException(result);
//        }
//
//        User user = userService.getUserByEmail(verifyUserDTO.email());
//        userService.verifyOtpToVerifyUser(user.getId(), verifyUserDTO.otp());
//        return ResponseEntity.ok().body(
//            ApiResponse.<OtpResponse>builder()
//                .message(MessageKey.VERIFY_USER_SUCCESSFULLY)
//                .statusCode(HttpStatus.OK.value())
//                .isSuccess(true)
//                .build());
//    }

    @PutMapping("/{id}/update-role/{roleId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> updateRole(
        @PathVariable long id,
        @PathVariable long roleId
    ) {
        userService.updateRole(id, roleId);
        return ResponseEntity.ok("Update role successfully");
    }

//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_MANAGER')")
//    public ResponseEntity<?> deleteUser(@PathVariable long id) {
//        userService.softDeleteUser(id);
//        return ResponseEntity.ok("Delete user successfully");
//    }
//
//    @PutMapping("/{id}/restore")
//    @PreAuthorize("hasRole('ROLE_MANAGER')")
//    public ResponseEntity<?> restoreUser(@PathVariable long id) {
//        userService.restoreUser(id);
//        return ResponseEntity.ok("Restore user successfully");
//    }

}
