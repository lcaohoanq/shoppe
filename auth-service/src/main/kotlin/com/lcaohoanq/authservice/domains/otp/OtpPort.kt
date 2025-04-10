package com.lcaohoanq.authservice.domains.otp

interface OtpPort {

    data class OtpReq(
        val email: String,
        val otp: String
    )

}