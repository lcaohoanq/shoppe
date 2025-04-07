package com.lcaohoanq.authservice.domains.otp

import com.lcaohoanq.authservice.repositories.OtpRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class OtpService(
    private val otpRepository: OtpRepository
): IOtpService {

    override fun createOtp(otp: Otp) {
        val newOtp = Otp(
            email = otp.email,
            otp = otp.otp,
            expiredAt = otp.expiredAt,
            isUsed = otp.isUsed,
            isExpired = otp.isExpired
        )
        otpRepository.save(newOtp)
    }

    override fun disableOtp(id: Long) {
        val existingOtp: Otp = otpRepository.findById(id).orElse(null) ?: return
        existingOtp.isExpired = true
        otpRepository.save(existingOtp)
    }

    override fun getOtpByEmailOtp(email: String, otp: String): Otp? {
        return otpRepository.findByEmailAndOtp(email, otp)
    }

    @Transactional
    override fun setOtpExpired() {
        val now = LocalDateTime.now()
        // Update OTPs where expired_at < now and is_expired = 0
        otpRepository.updateExpiredOtps(now)
    }

    override fun generateOtp(): String {
        return ((Math.random() * 9000).toInt() + 1000).toString()
    }
}
