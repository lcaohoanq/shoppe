package com.lcaohoanq.authservice.extension

import com.lcaohoanq.authservice.domains.otp.Otp
import com.lcaohoanq.common.dto.OtpPort

fun Otp.toOtpResponse(): OtpPort.OtpRes {
    return OtpPort.OtpRes(
        id = this.id!!,
        email = this.email,
        otp = this.otp,
        expiredAt = this.expiredAt,
        isUsed = this.isUsed,
        isExpired = this.isExpired
    )
}