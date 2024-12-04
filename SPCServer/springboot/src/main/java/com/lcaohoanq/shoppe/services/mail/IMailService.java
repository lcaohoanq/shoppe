package com.lcaohoanq.shoppe.services.mail;

import com.lcaohoanq.shoppe.models.User;
import io.reactivex.rxjava3.core.Single;
import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface IMailService {
    void sendMail(String to, String subject, String templateName, Context context) throws MessagingException;
    Single<User> createEmailVerification(User user);
}
