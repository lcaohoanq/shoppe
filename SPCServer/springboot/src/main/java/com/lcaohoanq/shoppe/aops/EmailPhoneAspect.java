package com.lcaohoanq.shoppe.aops;

import com.lcaohoanq.shoppe.enums.UserStatus;
import com.lcaohoanq.shoppe.exceptions.UserHasBeenBannedException;
import com.lcaohoanq.shoppe.exceptions.UserHasBeenVerifiedException;
import com.lcaohoanq.shoppe.models.User;
import com.lcaohoanq.shoppe.services.user.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class EmailPhoneAspect {

    private final HttpServletRequest request;
    private final IUserService userService;

    @Before("execution(* com.lcaohoanq.shoppe.controllers.MailController.*(..)) && args(toEmail,..) && !@annotation(com.lcaohoanq.shoppe.annotations.SkipEmailValidation)")
    public void checkValidEmail(JoinPoint joinPoint, String toEmail) {
        User user = userService.findUserByEmail(toEmail);

        if (user.getStatus() == UserStatus.BANNED) {
            throw new UserHasBeenBannedException(String.format("User with email %s has been banned", toEmail));
        }

        if (user.getStatus() == UserStatus.VERIFIED) {
            throw new UserHasBeenVerifiedException(String.format("User with email %s has been verified", toEmail));
        }

        request.setAttribute("validatedEmail", user);
    }

    @Before("execution(* com.lcaohoanq.shoppe.controllers.ForgotPasswordController.*(..)) && args(toEmail,..)")
    public void checkValidEmailForgotPassword(JoinPoint joinPoint, String toEmail) {
        User user = userService.findUserByEmail(toEmail);

        request.setAttribute("validatedEmail", user);
    }

}