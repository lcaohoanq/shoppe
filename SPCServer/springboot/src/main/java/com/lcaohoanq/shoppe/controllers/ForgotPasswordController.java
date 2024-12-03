package com.lcaohoanq.shoppe.controllers;

import com.lcaohoanq.shoppe.dtos.request.UpdatePasswordDTO;
import com.lcaohoanq.shoppe.exceptions.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.models.User;
import com.lcaohoanq.shoppe.dtos.responses.ForgotPasswordResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiResponse;
import com.lcaohoanq.shoppe.services.forgotpassword.IForgotPasswordService;
import com.lcaohoanq.shoppe.services.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping(path = "${api.prefix}/forgot-password")
@RestController
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final HttpServletRequest request;
    private final IForgotPasswordService forgotPasswordService;
    private final UserService userService;

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(
        @Validated @RequestParam String toEmail
    ) throws MessagingException {
        User user = (User) request.getAttribute("validatedEmail"); //get from aop

        try {
            forgotPasswordService.sendEmailOtp(user);

            ForgotPasswordResponse response =
                new ForgotPasswordResponse(
                    "Forgot password email sent successfully to " + user.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            if (e instanceof MessagingException) {
                log.error("Error sending email", e.getCause());
                throw e;
            }
            throw e;
        }
    }

    @PutMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<?>> updatePassword(
        @Valid @RequestBody UpdatePasswordDTO updatePasswordDTO,
        BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        userService.updatePassword(updatePasswordDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.builder()
                .message("Password updated successfully")
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }

}
