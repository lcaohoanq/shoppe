package com.lcaohoanq.shoppe.domain.auth;

import com.lcaohoanq.shoppe.domain.user.UserResponse;
import com.lcaohoanq.shoppe.domain.user.User;
import java.util.List;

public interface IAuthService {

    User register(AccountRegisterDTO accountRegisterDTO) throws Exception;
    String login(String email, String password) throws Exception;
    UserResponse getUserDetailsFromToken(String token) throws Exception;
    void logout(String token, User user) throws Exception;
    void verifyOtpToVerifyUser(Long userId, String otp) throws Exception;
    void verifyOtpIsCorrect(Long userId, String otp) throws Exception;
}
