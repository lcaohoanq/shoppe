package com.lcaohoanq.shoppe.services.mail;

import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface IMailService {
    void sendMail(String to, String subject, String templateName, Context context) throws MessagingException;
}
