package com.lcaohoanq.jvservice.domain.mail;

import com.lcaohoanq.jvservice.annotation.SkipEmailValidation;
import com.lcaohoanq.jvservice.constant.EmailSubject;
import com.lcaohoanq.jvservice.domain.role.UpdateRolePurposeDTO;
import com.lcaohoanq.common.enums.EUpdateRole;
import com.lcaohoanq.common.enums.EmailBlockReasonEnum;
import com.lcaohoanq.common.enums.EmailCategoriesEnum;
import com.lcaohoanq.jvservice.exception.MethodArgumentNotValidException;
import com.lcaohoanq.jvservice.domain.otp.Otp;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.domain.otp.IOtpService;
import com.lcaohoanq.jvservice.domain.user.IUserService;
import com.lcaohoanq.common.utils.OtpUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RequestMapping(path = "${api.prefix}/mail")
@RestController
@RequiredArgsConstructor
public class MailController {

    private final IMailService mailService;
    private final HttpServletRequest request;
    private final IOtpService otpService;
    private final IUserService userService;

    //GET: localhost:4000/api/v1/mail/update-role?updateRole=STAFF
    @PostMapping("/update-role")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    @SkipEmailValidation
    public ResponseEntity<?> sendOtp(
        @RequestParam EUpdateRole updateRole,
        @Valid @RequestBody UpdateRolePurposeDTO updateRolePurposeDTO,
        BindingResult result
    ) throws Exception {
        if(result.hasErrors()) throw new MethodArgumentNotValidException(result);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Context context = new Context();
        context.setVariable("name", user.getName());
        context.setVariable("sendFromEmail", user.getEmail());
        context.setVariable("role", updateRole);
        context.setVariable("purpose", updateRolePurposeDTO.purpose());
        mailService.sendMail("hoangclw@gmail.com", EmailSubject.subjectRequestUpdateRole(),
                             EmailCategoriesEnum.UPDATE_ROLE.getType(), context);
        mailService.sendMail(user.getEmail(), EmailSubject.subjectRequestUpdateRole(),
                             EmailCategoriesEnum.PROCESSING_UPDATE_ROLE.getType(), context);
        MailResponse response = new MailResponse("Mail sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //api: /otp/send?type=email&recipient=abc@gmail
    public ResponseEntity<MailResponse> sendOtp(@RequestParam String toEmail)
        throws MessagingException {
        User user = (User) request.getAttribute("validatedEmail");

        String name = user.getName();
        Context context = new Context();
        String otp = OtpUtil.generateOtp();
        context.setVariable("name", name);
        context.setVariable("otp", otp);
        mailService.sendMail(toEmail, EmailSubject.subjectGreeting(name),
                             EmailCategoriesEnum.OTP.getType(),
                             context);
        MailResponse response = new MailResponse("Mail sent successfully");

        Otp otpEntity = Otp.builder()
            .email(toEmail)
            .otp(otp)
            .expiredAt(LocalDateTime.now().plusMinutes(5))
            .isUsed(false)
            .isExpired(false)
            .build();

        otpService.createOtp(otpEntity);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/block")
    ResponseEntity<MailResponse> sendBlockAccount(@RequestParam String toEmail)
        throws MessagingException {
        User user = (User) request.getAttribute("validatedEmail");
        Context context = new Context();
        context.setVariable("reason", EmailBlockReasonEnum.ABUSE.getReason());
        mailService.sendMail(toEmail, EmailSubject.subjectBlockEmail(user.getName()),
                             EmailCategoriesEnum.BLOCK_ACCOUNT.getType(), context);
        MailResponse response = new MailResponse("Mail sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/forgotPassword")
    ResponseEntity<MailResponse> sendForgotPassword(@RequestParam String toEmail)
        throws MessagingException {
        User user = (User) request.getAttribute("validatedEmail");
        String name = user.getName();
        Context context = new Context();
        String otp = OtpUtil.generateOtp();
        context.setVariable("name", name);
        context.setVariable("otp", otp);
        mailService.sendMail(toEmail, EmailSubject.subjectGreeting(name),
                             EmailCategoriesEnum.FORGOT_PASSWORD.getType(), context);
        MailResponse response = new MailResponse("Mail sent successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}