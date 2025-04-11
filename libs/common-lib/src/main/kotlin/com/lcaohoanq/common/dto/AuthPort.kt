package com.lcaohoanq.common.dto

import com.fasterxml.jackson.annotation.JsonProperty
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
        val address: String,
        val phoneNumber: String,
        val gender: UserEnum.Gender? = UserEnum.Gender.FEMALE,
        val password: String,
        val status: UserEnum.Status? = UserEnum.Status.UNVERIFIED,
    )

    data class VerifyEmailReq(
        val email: String,
    )

}