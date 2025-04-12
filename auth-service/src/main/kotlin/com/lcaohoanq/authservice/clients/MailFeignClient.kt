package com.lcaohoanq.authservice.clients

import com.lcaohoanq.common.dto.AuthPort
import com.lcaohoanq.common.dto.MailPort
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "notification-service")
interface MailFeignClient {

    @PostMapping("/api/v1/mail/greeting-user-login")
    fun sendGreetingUserLoginEmail(@RequestParam toEmail: String): ResponseEntity<String>

    @PostMapping("/api/v1/mail/send-otp")
    fun sendOtp(@RequestParam toEmail: String): ResponseEntity<String>

    ///api/v1/mail/verify-account
    @PostMapping("/api/v1/mail/verify-account")
    fun sendVerifyAccountEmail(@RequestBody email: AuthPort.VerifyEmailReq): ResponseEntity<MailPort.MailResponse>

    @GetMapping("/static-mail")
    fun doSendStaticMail(
        @RequestParam toEmail: String,
        @RequestParam templateName: String
    ): ResponseEntity<MailPort.MailResponse>

}