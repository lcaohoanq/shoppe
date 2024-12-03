package com.lcaohoanq.shoppe.services.forgotpassword;

import com.lcaohoanq.shoppe.constants.EmailSubject;
import com.lcaohoanq.shoppe.enums.EmailCategoriesEnum;
import com.lcaohoanq.shoppe.models.Otp;
import com.lcaohoanq.shoppe.models.User;
import com.lcaohoanq.shoppe.services.mail.IMailService;
import com.lcaohoanq.shoppe.services.otp.IOtpService;
import com.lcaohoanq.shoppe.utils.OtpUtils;
import jakarta.mail.MessagingException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForgotPasswordService implements IForgotPasswordService{

    private final IMailService mailService;
    private final IOtpService otpService;

    @Override
    @Transactional
    public void sendEmailOtp(User existingUser) throws MessagingException {
        Context context = new Context();
        String otp = OtpUtils.generateOtp();
        context.setVariable("name", existingUser.getName());
        context.setVariable("otp", otp);

        mailService.sendMail(existingUser.getEmail(),
                             EmailSubject.subjectForgotPassword(existingUser.getName()),
                             EmailCategoriesEnum.FORGOT_PASSWORD.getType(),
                             context);

        Otp otpEntity = Otp.builder()
            .email(existingUser.getEmail())
            .otp(otp)
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .isUsed(false)
            .isExpired(false)
            .build();

        otpService.createOtp(otpEntity);
    }
}
