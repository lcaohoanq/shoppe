package com.lcaohoanq.authservice.repositories

import com.lcaohoanq.authservice.domains.otp.Otp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface OtpRepository : JpaRepository<Otp, Long> {
    fun findByEmailAndOtp(email: String, otp: String): Otp?

    @Modifying
    @Query("UPDATE Otp o SET o.isExpired = true WHERE o.expiredAt < :now AND o.isExpired = false")
    fun updateExpiredOtps(now: LocalDateTime)
}
