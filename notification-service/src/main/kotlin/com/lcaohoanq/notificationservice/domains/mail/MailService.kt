package com.lcaohoanq.notificationservice.domains.mail

import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import mu.KotlinLogging
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@Service
class MailService(
    private val mailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine,
//    private val otpService: OtpService
) : IMailService {

    private val log = KotlinLogging.logger {}

    @Async
    override fun sendMail(to: String, subject: String, templateName: String, context: Context) {
        val mimeMessage: MimeMessage = mailSender.createMimeMessage()
        try {
            val helper: MimeMessageHelper = MimeMessageHelper(mimeMessage, true, "UTF-8")
            val htmlContent: String = templateEngine.process(templateName, context)
            helper.setTo(to)
            helper.setSubject(subject)
            helper.setFrom("lvhhoangg1@gmail.com")
            helper.setText(htmlContent, true)
            mailSender.send(mimeMessage)
            log.info("Mail send successfully to {}", to)
        } catch (e: MessagingException) {
            log.error("Failed to send mail to {}: {}", to, e.message)
            throw MessagingException("Failed to send mail to $to")
        }
    }

//    override fun createEmailVerification(user: User): Single<User> {
//        return Single.fromCallable {
//            val otp: String = OtpUtil.generateOtp()
//            // Create email context
//            val context: Context = Context()
//            context.setVariable("name", user.getName())
//            context.setVariable("otp", otp)
//
//            // Send email
//            this.sendMail(
//                user.getEmail(),
//                "Verify your email",
//                EmailCategoriesEnum.OTP.getType(),
//                context
//            )
//
//            // Create OTP record
//            val otpEntity: Otp = Otp.builder()
//                .email(user.getEmail())
//                .otp(otp)
//                .expiredAt(LocalDateTime.now().plusMinutes(5))
//                .isUsed(false)
//                .isExpired(false)
//                .build()
//
//            otpService.createOtp(otpEntity)
//            user // Return the user for chaining
//        }.subscribeOn(Schedulers.io())
//    }
}