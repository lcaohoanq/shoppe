package com.lcaohoanq.notificationservice.clients

import com.lcaohoanq.common.apis.MyApiResponse
import com.lcaohoanq.common.dto.AuthPort
import com.lcaohoanq.common.dto.OtpPort
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "auth-service")
interface AuthFeignClient {

    @PostMapping("/api/v1/otp")
    fun createOtp(@RequestBody otp: OtpPort.OtpReq): ResponseEntity<String>

    @PostMapping("/api/v1/auth/generate-token-from-email")
    fun generateTokenFromEmail(@RequestBody data: AuthPort.VerifyEmailReq): ResponseEntity<MyApiResponse<String>>

}