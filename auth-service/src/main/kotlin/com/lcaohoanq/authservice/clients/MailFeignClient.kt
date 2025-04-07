package com.lcaohoanq.authservice.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "notification-service")
interface MailFeignClient {

    @PostMapping("/api/v1/mail/greeting-user-login")
    fun sendGreetingUserLoginEmail(@RequestParam toEmail: String): ResponseEntity<String>

}