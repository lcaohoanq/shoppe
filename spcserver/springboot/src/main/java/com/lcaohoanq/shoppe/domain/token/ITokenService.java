package com.lcaohoanq.shoppe.domain.token;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.domain.user.User;
import org.springframework.stereotype.Service;

@Service

public interface ITokenService {
    Token addToken(long userId, String token, boolean isMobileDevice);
    Token refreshToken(String refreshToken, User user) throws Exception;
    void deleteToken(String token, User user) throws DataNotFoundException;
    Token findUserByToken(String token) throws DataNotFoundException;
}
