package com.lcaohoanq.ktservice.domain.user

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

interface UserPort {

    data class CreateUserDTO(
        val email: String,
        val password: String
    )

    @JsonPropertyOrder("id", "email", "username", "phone", "address", "avatar")
    data class UserResponse(
        val id: Long,
        val address: String,
        val avatar: String,
        val email: String,
        val username: String,
        @JsonIgnore val password: String,
        val phone : String,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") @JsonIgnore val createdAt: LocalDateTime?,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") @JsonIgnore val updatedAt: LocalDateTime?
    )

}