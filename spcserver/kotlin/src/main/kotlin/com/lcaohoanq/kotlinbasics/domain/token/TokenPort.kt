package com.lcaohoanq.kotlinbasics.domain.token

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime
import java.util.*

interface TokenPort {

    data class TokenResponse(
        @JsonIgnore val id: UUID,
        @JsonProperty("accessToken") val token: String,
        @JsonProperty("refreshToken") val refreshToken : String,
        @JsonProperty("refreshExpirationDate") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") val refreshExpirationDate: LocalDateTime,
        @JsonProperty("expirationDate") @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") val expirationDate: LocalDateTime,
        @JsonIgnore val tokenType: String,
        @JsonIgnore val isMobile: Boolean,
        @JsonIgnore val revoked: Boolean,
        @JsonIgnore val expired: Boolean,
    )

    data class RefreshTokenDTO(
        @NotBlank(message = "Refresh token is required") val refreshToken: String
    )

}