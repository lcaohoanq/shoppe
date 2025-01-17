package com.lcaohoanq.shoppe.domain.order;

import com.lcaohoanq.shoppe.domain.user.User;
import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface IOrderMailService {

    void sendOrderCreatedEmailToUser(Order order,
                                     String subject,
                                     String templateName,
                                     Context context) throws MessagingException;
    void sendOrderCancelledToUser(User user,
                                  String subject,
                                  String templateName,
                                  Context context) throws MessagingException;
}
