package com.lcaohoanq.common.dto

interface OtpPort {

    data class OtpReq(
        val email: String,
        val otp: String
    )

}