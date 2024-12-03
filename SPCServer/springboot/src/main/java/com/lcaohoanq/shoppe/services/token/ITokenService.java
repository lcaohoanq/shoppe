package com.lcaohoanq.shoppe.services.token;

import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.Token;
import com.lcaohoanq.shoppe.models.User;
import org.springframework.stereotype.Service;

@Service

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user) throws Exception;
    void deleteToken(String token, User user) throws DataNotFoundException;
    Token findUserByToken(String token) throws DataNotFoundException;
    void setTokenExpired();
}
