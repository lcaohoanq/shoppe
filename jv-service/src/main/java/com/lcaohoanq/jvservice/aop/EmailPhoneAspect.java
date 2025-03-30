package com.lcaohoanq.jvservice.aop;

import com.lcaohoanq.jvservice.enums.UserStatus;
import com.lcaohoanq.jvservice.exception.UserHasBeenBannedException;
import com.lcaohoanq.jvservice.exception.UserHasBeenVerifiedException;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.domain.user.IUserService;
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

    @Before("execution(* com.lcaohoanq.jvservice.domain.mail.MailController.*(..)) && args(toEmail,..) && !@annotation(com.lcaohoanq.jvservice.annotation.SkipEmailValidation)")
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

//    @Before("execution(* com.lcaohoanq.jvservice.domain.auth.AuthController.forgotPassword(..)) && args"
//        + "(toEmail,..)")
//    public void checkValidEmailForgotPassword(JoinPoint joinPoint, String toEmail) {
//        User user = userService.findUserByEmail(toEmail);
//
//        if (user == null) {
//            throw new IllegalArgumentException("Email is not valid"); // Handle invalid email
//        }
//
//        request.setAttribute("validatedEmail", user);
//    }

}