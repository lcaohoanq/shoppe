package com.lcaohoanq.shoppe.services.auth;

import com.lcaohoanq.shoppe.dtos.request.UserRegisterDTO;
import com.lcaohoanq.shoppe.models.User;

public interface IAuthService {

    User register(UserRegisterDTO userRegisterDTO) throws Exception;
    String login(String email, String password) throws Exception;
    User getUserDetailsFromToken(String token) throws Exception;
    void logout(String token, User user) throws Exception;

}
