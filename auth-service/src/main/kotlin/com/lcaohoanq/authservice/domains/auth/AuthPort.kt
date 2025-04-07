package com.lcaohoanq.authservice.domains.auth

import com.fasterxml.jackson.annotation.JsonProperty
import com.lcaohoanq.authservice.dto.TokenPort
import com.lcaohoanq.common.enums.UserEnum
import io.swagger.v3.oas.annotations.media.Schema

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
        val email: String,
        val phoneNumber: String,
        val gender: UserEnum.Gender,
        val password: String
    )

}