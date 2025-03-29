package com.lcaohoanq.kotlinbasics.domain.auth

import com.fasterxml.jackson.annotation.JsonProperty
import com.lcaohoanq.kotlinbasics.domain.token.TokenPort
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
        val email: String,
        val password: String
    )

}