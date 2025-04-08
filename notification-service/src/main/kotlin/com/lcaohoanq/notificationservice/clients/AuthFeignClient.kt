package com.lcaohoanq.notificationservice.clients

import com.lcaohoanq.common.apis.MyApiResponse
import com.lcaohoanq.common.dto.OtpPort
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "auth-service")
interface AuthFeignClient {

    @PostMapping("/api/v1/otp")
    fun createOtp(@RequestBody otp: OtpPort.OtpReq): ResponseEntity<String>

    @PostMapping("/generate-token-from-email")
    fun generateTokenFromEmail(@RequestBody data: VerifyEmailReq): ResponseEntity<MyApiResponse<String>>

    data class VerifyEmailReq(
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email should not be blank")
        val email: String,
    )

}
