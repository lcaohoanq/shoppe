package com.lcaohoanq.notificationservice.domains.mail

import com.lcaohoanq.common.dto.AuthPort
import com.lcaohoanq.common.dto.MailPort
import com.lcaohoanq.common.dto.OtpPort
import com.lcaohoanq.common.utils.OtpUtil
import com.lcaohoanq.commonspring.utils.unwrap
import com.lcaohoanq.notificationservice.clients.AuthFeignClient
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.io.FileNotFoundException

@RequestMapping(path = ["\${api.prefix}/mail"])
@RestController
class MailController(
    private val mailService: IMailService,
    private val request: HttpServletRequest,
    private val authFeignClient: AuthFeignClient,
    private val templateEngine: TemplateEngine,
    private val resourceLoader: ResourceLoader
) {

    //api: /otp/send?type=email&recipient=abc@gmail
    @PostMapping("/send-otp")
    fun sendOtp(@RequestParam @Schema(defaultValue = "hoangclw@gmail.com") toEmail: String): ResponseEntity<MailPort.MailResponse> {
        val context: Context = Context()
        val otp: String = OtpUtil.generateOtp()
        context.setVariable("name", toEmail)
        context.setVariable("otp", otp)

        mailService.sendMail(
            toEmail, "Shoppe Corporation- Welcome ${toEmail}, thanks for joining us!",
            "sendOtp",
            context
        )
        val response = MailPort.MailResponse("Mail sent successfully")

        val otpEntity = OtpPort.OtpReq(
            email = toEmail,
            otp = otp,
        )

        //need to call a request to auth service to create otp
        authFeignClient.createOtp(otpEntity)

        return ResponseEntity<MailPort.MailResponse>(response, HttpStatus.OK)
    }

    @PostMapping("/greeting-user-login")
    fun greeting(@RequestParam @Schema(defaultValue = "hoangclw@gmail.com") toEmail: String): ResponseEntity<MailPort.MailResponse> {

        val context = Context()
        context.setVariable("name", toEmail)

        mailService.sendMail(
            toEmail, "Shoppe Corporation- Welcome ${toEmail}, thanks for joining us!",
            "welcomeLoginSuccess",
            context
        )

        val response = MailPort.MailResponse("Mail sent successfully")

        return ResponseEntity<MailPort.MailResponse>(response, HttpStatus.OK)
    }

    @PostMapping("/verify-account")
    fun doVerifyAccount(@RequestBody data: AuthPort.VerifyEmailReq): ResponseEntity<MailPort.MailResponse> {
        val token = unwrap(authFeignClient.generateTokenFromEmail(data))

        val context = Context()
        context.setVariable(
            "verifyLink",
            "http://localhost:4006/api/v1/auth/verify-account?token=$token"
        )

        mailService.sendMail(
            data.email,
            "Shoppe Corporation - Welcome ${data.email}, thanks for joining us!",
            "verifyUser",
            context
        )

        val response = MailPort.MailResponse("Verification mail sent successfully")
        return ResponseEntity(response, HttpStatus.OK)
//        throw ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Mail service is overloaded")
    }

    @PostMapping("/disable-account-confirmation")
    fun sendDisableAccountConfirmationEmail(
        @RequestBody data: AuthPort.VerifyEmailReq
    ): ResponseEntity<MailPort.MailResponse> {

        val token = unwrap(authFeignClient.generateTokenFromEmail(data))

        val context = Context()
        context.setVariable(
            "confirmationLink",
            "http://localhost:4006/api/v1/auth/disable-account?token=$token"
        )

        mailService.sendMail(
            data.email,
            "Shoppe Corporation - Disable account confirmation",
            "disableAccountConfirmation",
            context
        )

        val response = MailPort.MailResponse("Disable account confirmation mail sent successfully")
        return ResponseEntity(response, HttpStatus.OK)
    }

    @GetMapping("/static-mail")
    fun doSendStaticMail(
        @RequestParam toEmail: String,
        @RequestParam templateName: String
    ): ResponseEntity<MailPort.MailResponse> {

        val context = Context()

        mailService.sendMail(
            toEmail,
            "Shoppe Corporation - Welcome ${toEmail}, thanks for joining us!",
            templateName,
            context
        )

        return ResponseEntity.ok(
            MailPort.MailResponse("Static mail sent successfully")
        )

    }

    @GetMapping("/email-templates/{templateName}")
    fun getEmailTemplate(
        @PathVariable templateName: String,
        @RequestParam(required = false) includeThymeleaf: Boolean = false
    ): ResponseEntity<String> {
        return try {
            val context = Context().apply {
                // Provide dummy data if needed
                setVariable("username", "Demo User")
                setVariable("code", "123456")
            }

            val html = if (includeThymeleaf) {
                try {
                    templateEngine.process(templateName, context)
                } catch (ex: Exception) {
                    // Fallback to raw file if Thymeleaf processing fails
                    loadRawHtml(templateName)
                }
            } else {
                loadRawHtml(templateName)
            }

            ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html)

        } catch (ex: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template not found: $templateName")
        }
    }

    private fun loadRawHtml(templateName: String): String {
        val resource = resourceLoader.getResource("classpath:emails/$templateName.html")
        if (!resource.exists()) throw FileNotFoundException("File not found")
        return resource.inputStream.bufferedReader().use { it.readText() }
    }


}