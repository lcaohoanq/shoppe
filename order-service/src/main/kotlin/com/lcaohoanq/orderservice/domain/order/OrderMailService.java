package com.lcaohoanq.orderservice.domain.order;

import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.domain.mail.IMailService;
import com.lcaohoanq.jvservice.domain.user.IUserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
public class OrderMailService implements IOrderMailService {
    private final IMailService mailService;
    private final IUserService userService;

    @Retryable(
            retryFor = {MessagingException.class},  // Retry only for specific exceptions
            maxAttempts = 3,                       // Maximum retry attempts
            backoff = @Backoff(delay = 2000)       // 2 seconds delay between retries
    )
    @Async
    @Override
    public void sendOrderCreatedEmailToUser(Order order,
                                            String subject,
                                            String templateName,
                                            Context context) throws MessagingException {
        //get the user who created the order
        User user = order.getUser();

        // Create a Thymeleaf context for the email template
        context.setVariable("user_name", user.getName());
        context.setVariable("order_id", order.getId());
        context.setVariable("order_date", order.getOrderDate());
        context.setVariable("order_status", order.getStatus().toString());
        context.setVariable("total_money", order.getTotalMoney());

        mailService.sendMail(user.getEmail(), "Order Created Successfully", "orderCreated", context);
    }

    @Retryable(
            retryFor = {MessagingException.class},  // Retry only for specific exceptions
            maxAttempts = 3,                       // Maximum retry attempts
            backoff = @Backoff(delay = 2000)       // 2 seconds delay between retries
    )
    @Async
    @Override
    public void sendOrderCancelledToUser(User user, String subject, String templateName, Context context) throws MessagingException {
        mailService.sendMail(user.getEmail(), "Order Cancelled", templateName, context);
    }
}
