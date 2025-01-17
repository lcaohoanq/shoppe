package com.lcaohoanq.shoppe.domain.phone;

import com.lcaohoanq.shoppe.enums.UserStatus;
import com.lcaohoanq.shoppe.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PhoneController {

    private final HttpServletRequest request;

    public ResponseEntity<?> sendPhoneOtp(@RequestParam String toPhone) {

        User user = (User) request.getAttribute("validatedPhone");

        if (user.getStatus() == UserStatus.VERIFIED) {
            return ResponseEntity.badRequest().body("Phone number already verified");
        }

        return ResponseEntity.ok("OTP sent successfully");
    }

}