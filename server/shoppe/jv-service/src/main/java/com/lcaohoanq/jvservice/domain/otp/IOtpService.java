package com.lcaohoanq.jvservice.domain.otp;

import java.util.Optional;

public interface IOtpService {

    void createOtp(Otp otp);
    void disableOtp(long id);
    Optional<Otp> getOtpByEmailOtp(String email, String otp);
    void setOtpExpired();
    String generateOtp();
}
