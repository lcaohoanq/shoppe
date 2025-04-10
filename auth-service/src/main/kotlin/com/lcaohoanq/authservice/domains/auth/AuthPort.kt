package com.lcaohoanq.authservice.domains.auth

import com.fasterxml.jackson.annotation.JsonProperty
import com.lcaohoanq.common.dto.TokenPort
import com.lcaohoanq.common.enums.UserEnum

interface AuthPort {

    data class AuthRequest(
        val email: String,
        val password: String
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

    data class VerifyEmailReq(
        val email: String,
    )

}