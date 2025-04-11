package com.lcaohoanq.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

interface OtpPort {

    data class OtpReq(
        val email: String,
        val otp: String
    )

    data class OtpRes(
        val id: Long,
        val email: String,
        val otp: String,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh")
        val expiredAt: LocalDateTime,

        var isUsed: Boolean,
        var isExpired: Boolean
    )

}