package com.lcaohoanq.shoppe.services.otp;

import com.lcaohoanq.shoppe.models.Otp;
import com.lcaohoanq.shoppe.repositories.OtpRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OtpService implements IOtpService{

    private final OtpRepository otpRepository;

    @Override
    public Otp createOtp(Otp otp) {
        Otp newOtp = Otp.builder()
                .email(otp.getEmail())
                .otp(otp.getOtp())
                .expiredAt(otp.getExpiredAt())
                .isUsed(otp.isUsed())
                .isExpired(otp.isExpired())
                .build();
        return otpRepository.save(newOtp);
    }

    @Override
    public void disableOtp(long id) {
        Otp existingOtp = otpRepository.findById(id).orElse(null);
        if(existingOtp == null) return;
        existingOtp.setExpired(true);
        otpRepository.save(existingOtp);
    }

    @Override
    public Optional<Otp> getOtpByEmailOtp(String email, String otp) {
        return otpRepository.findByEmailAndOtp(email, otp);
    }

    @Override
    @Transactional
    public void setOtpExpired() {
        LocalDateTime now = LocalDateTime.now();
        // Update OTPs where expired_at < now and is_expired = 0
        otpRepository.updateExpiredOtps(now);
    }

}
