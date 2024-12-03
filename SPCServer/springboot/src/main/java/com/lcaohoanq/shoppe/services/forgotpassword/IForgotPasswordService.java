package com.lcaohoanq.shoppe.services.forgotpassword;

import com.lcaohoanq.shoppe.models.User;
import jakarta.mail.MessagingException;

public interface IForgotPasswordService {

    void sendEmailOtp(User existingUser) throws MessagingException;

}
