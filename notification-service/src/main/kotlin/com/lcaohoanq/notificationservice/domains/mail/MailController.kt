package com.lcaohoanq.notificationservice.domains.mail

import com.lcaohoanq.common.dto.OtpPort
import com.lcaohoanq.common.enums.EmailCategoriesEnum
import com.lcaohoanq.common.utils.OtpUtil
import com.lcaohoanq.notificationservice.clients.AuthFeignClient
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.thymeleaf.context.Context

@RequestMapping(path = ["\${api.prefix}/mail"])
@RestController
class MailController(
    private val mailService: IMailService,
    private val request: HttpServletRequest,
    private val authFeignClient: AuthFeignClient,
) {

    //api: /otp/send?type=email&recipient=abc@gmail
    @PostMapping("/send-otp")
    fun sendOtp(@RequestParam @Schema(defaultValue = "hoangclw@gmail.com") toEmail: String): ResponseEntity<MailResponse> {
        val context: Context = Context()
        val otp: String = OtpUtil.generateOtp()
        context.setVariable("name", toEmail)
        context.setVariable("otp", otp)

        mailService.sendMail(
            toEmail, "Shoppe Corporation- Welcome ${toEmail}, thanks for joining us!",
            EmailCategoriesEnum.OTP.type,
            context
        )
        val response = MailResponse("Mail sent successfully")

        val otpEntity= OtpPort.OtpReq(
            email = toEmail,
            otp = otp,
        )

        //need to call a request to auth service to create otp
        authFeignClient.createOtp(otpEntity)

        return ResponseEntity<MailResponse>(response, HttpStatus.OK)
    }

    @PostMapping("/greeting-user-login")
    fun greeting(@RequestParam @Schema(defaultValue = "hoangclw@gmail.com") toEmail: String): ResponseEntity<MailResponse>{

        val context = Context()
        context.setVariable("name", toEmail)

        mailService.sendMail(
            toEmail, "Shoppe Corporation- Welcome ${toEmail}, thanks for joining us!",
            "welcomeLoginSuccess",
            context
        )

        val response = MailResponse("Mail sent successfully")

        return ResponseEntity<MailResponse>(response, HttpStatus.OK)
    }

    @PostMapping("/verify-account")
    fun doVerifyAccount(@RequestBody data: AuthFeignClient.VerifyEmailReq): ResponseEntity<MailResponse> {
        val token = authFeignClient.generateTokenFromEmail(data) // Bạn cần hàm tạo JWT hoặc token ngẫu nhiên có thời hạn

        val verifyLink = "http://localhost:4006/api/v1/auth/verify-account?token=$token"

        val context = Context()
        context.setVariable("verifyLink", verifyLink)

        mailService.sendMail(
            data.email,
            "Shoppe Corporation - Welcome ${data.email}, thanks for joining us!",
            "verifyUser",
            context
        )

        val response = MailResponse("Verification mail sent successfully")
        return ResponseEntity(response, HttpStatus.OK)
    }


}