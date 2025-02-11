package com.lcaohoanq.shoppe.domain.otp;

import com.lcaohoanq.shoppe.api.ApiResponse;
import com.lcaohoanq.shoppe.component.LocalizationUtils;
import com.lcaohoanq.shoppe.constant.MessageKey;
import com.lcaohoanq.shoppe.domain.auth.AuthPort;
import com.lcaohoanq.shoppe.domain.auth.AuthService;
import com.lcaohoanq.shoppe.domain.auth.OtpResponse;
import com.lcaohoanq.shoppe.domain.mail.MailController;
import com.lcaohoanq.shoppe.domain.user.IUserService;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.exception.MethodArgumentNotValidException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${api.prefix}/otp")
@RequiredArgsConstructor
@Tag(name = "OTP", description = "Operations related to OTP")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OtpController {

    MailController mailController;
    IUserService userService;
    LocalizationUtils localizationUtils;
    AuthService authService;

    @GetMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestParam String type, @RequestParam String recipient)
        throws MessagingException {
        return switch (type.toLowerCase()) {
            case "mail" -> mailController.sendOtp(recipient);
//            case "phone" -> phoneController.sendPhoneOtp(recipient);
            default -> new ResponseEntity<>("Invalid type specified", HttpStatus.BAD_REQUEST);
        };
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<OtpResponse>> verifiedUserNotLogin(
        @Valid @RequestBody AuthPort.VerifyUserDTO verifyUserDTO,
        BindingResult result
    ) throws Exception {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }
        User user = userService.findUserByEmail(verifyUserDTO.email());
        authService.verifyOtpIsCorrect(user.getId(), verifyUserDTO.otp());
        return ResponseEntity.ok().body(
            ApiResponse.<OtpResponse>builder()
                .message(localizationUtils.getLocalizedMessage(MessageKey.OTP_IS_CORRECT))
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build());

    }

}

