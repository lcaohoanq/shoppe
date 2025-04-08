package com.lcaohoanq.common.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.lcaohoanq.common.enums.UserEnum
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

interface AuthPort {

    data class AuthRequest(
        @Schema(defaultValue = "hoangclw@gmail.com") val email: String,
        @Schema(defaultValue = "string")val password: String
    )

    data class AuthResponse(
        @JsonProperty("token") val token: TokenPort.TokenResponse
    )

    data class SignUpReq(
        val name: String,

        @Email(message = "Email should be valid")
        val email: String,
        val phoneNumber: String,
        val gender: UserEnum.Gender,
        val password: String
    )

    data class VerifyEmailReq(
        @Email(message = "Email should be valid")
        @NotBlank(message = "Email should not be blank")
        val email: String,
    )

}