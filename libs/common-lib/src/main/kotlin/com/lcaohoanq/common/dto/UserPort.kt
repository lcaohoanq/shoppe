package com.lcaohoanq.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.lcaohoanq.common.enums.UserEnum
import java.time.LocalDateTime

interface UserPort {

    data class CreateUserDTO(
        val email: String,
        val password: String
    )

    @JsonPropertyOrder("id", "email", "username", "status", "phone", "address", "avatar")
    data class UserResponse(
        val id: Long,
        val avatar: String,
        val email: String,
        val role: UserEnum.Role,
        val totp: String,
        val username: String,
        val status: UserEnum.Status,
        val loginHistory: MutableList<LoginHistoryPort.LoginHistoryResponse>,
        val settings: UserPort.UserSettingsResponse?,
        @JsonIgnore val password: String,
        val phone: String,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") @JsonIgnore val createdAt: LocalDateTime?,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") @JsonIgnore val updatedAt: LocalDateTime?
    )

    data class UserSettingsResponse(
        val id: Long = 0,
//        val userId: Long,
        val twoFaEnabled: Boolean = false,
        val preferredLanguage: String = "en",
        val darkMode: Boolean = false,
        val emailNotificationsEnabled: Boolean = true,
        val loginAlerts: Boolean = true,
        val requestDisableAccount: Boolean = false,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") @JsonIgnore val createdAt: LocalDateTime? = null,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") @JsonIgnore val updatedAt: LocalDateTime? = null
    )

}