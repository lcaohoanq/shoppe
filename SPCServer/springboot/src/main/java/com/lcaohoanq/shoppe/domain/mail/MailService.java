package com.lcaohoanq.shoppe.domain.mail;

import com.lcaohoanq.shoppe.enums.EmailCategoriesEnum;
import com.lcaohoanq.shoppe.domain.otp.Otp;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.otp.OtpService;
import com.lcaohoanq.shoppe.util.OtpUtil;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService implements IMailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final OtpService otpService;

    @Override
    @Async
    public void sendMail(String to, String subject, String templateName, Context context) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String htmlContent = templateEngine.process(templateName, context);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
            log.info("Mail send successfully to {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send mail to {}: {}", to, e.getMessage());
            throw new MessagingException("Failed to send mail to " + to);
        }
    }

    @Override
    public Single<User> createEmailVerification(User user) {
        return Single.fromCallable(() -> {
            String otp = OtpUtil.generateOtp();

            // Create email context
            Context context = new Context();
            context.setVariable("name", user.getName());
            context.setVariable("otp", otp);

            // Send email
            this.sendMail(
                user.getEmail(),
                "Verify your email",
                EmailCategoriesEnum.OTP.getType(),
                context
            );

            // Create OTP record
            Otp otpEntity = Otp.builder()
                .email(user.getEmail())
                .otp(otp)
                .expiredAt(LocalDateTime.now().plusMinutes(5))
                .isUsed(false)
                .isExpired(false)
                .build();

            otpService.createOtp(otpEntity);
            return user;  // Return the user for chaining
        }).subscribeOn(Schedulers.io());
    }


}