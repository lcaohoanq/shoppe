package com.lcaohoanq.authservice.domains.otp

interface IOtpService {
    fun createOtp(otp: Otp)
    fun disableOtp(id: Long)
    fun getOtpByEmailOtp(email: String, otp: String): Otp?
    fun setOtpExpired()
    fun generateOtp(): String
}
