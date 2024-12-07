package com.lcaohoanq.shoppe.services.auth;

import com.lcaohoanq.shoppe.dtos.request.AccountRegisterDTO;
import com.lcaohoanq.shoppe.dtos.responses.UserResponse;
import com.lcaohoanq.shoppe.models.User;

public interface IAuthService {

    User register(AccountRegisterDTO accountRegisterDTO) throws Exception;
    String login(String email, String password) throws Exception;
    UserResponse getUserDetailsFromToken(String token) throws Exception;
    void logout(String token, User user) throws Exception;
    void verifyOtpToVerifyUser(Long userId, String otp) throws Exception;
    void verifyOtpIsCorrect(Long userId, String otp) throws Exception;
}
