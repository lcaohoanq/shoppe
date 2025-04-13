package com.lcaohoanq.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

interface LoginHistoryPort {

    data class LoginHistoryResponse(
        val id: Long,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh")
        val loginAt: LocalDateTime,
        val ipAddress: String,
        val userAgent: String,
        @JsonFormat(
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "Asia/Ho_Chi_Minh"
        ) val createdAt: LocalDateTime?,
        @JsonFormat(
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "Asia/Ho_Chi_Minh"
        ) val updatedAt: LocalDateTime?
    )

}