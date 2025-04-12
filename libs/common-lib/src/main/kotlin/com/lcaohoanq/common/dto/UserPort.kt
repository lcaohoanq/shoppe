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
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") val lastLoginTimeStamp: LocalDateTime,
        @JsonIgnore val password: String,
        val phone : String,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") @JsonIgnore val createdAt: LocalDateTime?,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") @JsonIgnore val updatedAt: LocalDateTime?
    )

}