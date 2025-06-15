package com.lcaohoanq.authservice.domains.otp

import com.lcaohoanq.authservice.domains.user.User
import com.lcaohoanq.common.dto.OtpPort

interface IOtpService {
    fun createOtpFor(user: User, otpRequest: OtpPort.OtpReq)
    fun disableOtp(id: Long)
    fun getOtpByEmailOtp(email: String, otp: String): Otp?
    fun setOtpExpired()
    fun generateOtp(): String
    fun getAllOtps(): List<OtpPort.OtpRes>

}
