package com.lcaohoanq.shoppe.domain.auth;

import com.lcaohoanq.shoppe.domain.auth.AuthPort.AccountRegisterDTO;
import com.lcaohoanq.shoppe.domain.token.TokenPort.RefreshTokenDTO;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.user.UserResponse;
import jakarta.mail.MessagingException;

public interface IAuthService {

    User register(AccountRegisterDTO accountRegisterDTO) throws Exception;
    AuthPort.LoginResponse login(String email, String password) throws Exception;
    UserResponse getUserDetailsFromToken(String token) throws Exception;
    void logout(String token, User user) throws Exception;
    void verifyOtpToVerifyUser(Long userId, String otp) throws Exception;
    void verifyOtpIsCorrect(Long userId, String otp) throws Exception;
    AuthPort.LoginResponse refreshToken(RefreshTokenDTO refreshTokenDTO) throws Exception;
    void sendEmailOtp(User existingUser) throws MessagingException;
}
