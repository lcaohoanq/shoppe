package com.lcaohoanq.shoppe.repository;

import com.lcaohoanq.shoppe.domain.otp.Otp;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByEmailAndOtp(String email, String otp);

    @Modifying
    @Query("UPDATE Otp o SET o.isExpired = true WHERE o.expiredAt < :now AND o.isExpired = false")
    void updateExpiredOtps(LocalDateTime now);

}
