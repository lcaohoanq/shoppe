package com.lcaohoanq.shoppe.domain.auth;

import com.lcaohoanq.shoppe.domain.user.User;
import jakarta.mail.MessagingException;

public interface IForgotPasswordService {

    void sendEmailOtp(User existingUser) throws MessagingException;

}
