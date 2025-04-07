package com.lcaohoanq.notificationservice.domains.mail

import com.lcaohoanq.common.dto.OtpPort
import com.lcaohoanq.common.enums.EmailCategoriesEnum
import com.lcaohoanq.common.utils.OtpUtil
import com.lcaohoanq.notificationservice.clients.OtpFeignClient
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.thymeleaf.context.Context

@RequestMapping(path = ["\${api.prefix}/mail"])
@RestController
class MailController(
    private val mailService: IMailService,
    private val request: HttpServletRequest,
    private val otpFeignClient: OtpFeignClient,
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
        otpFeignClient.createOtp(otpEntity)

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

}