package com.lcaohoanq.jvservice.domain.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.jvservice.constant.Regex;
import com.lcaohoanq.jvservice.domain.token.TokenPort.TokenResponse;
import com.lcaohoanq.jvservice.domain.user.Address;
import com.lcaohoanq.jvservice.domain.user.User.Gender;
import com.lcaohoanq.common.enums.Country;
import com.lcaohoanq.common.enums.Currency;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Set;

public interface AuthPort {

    record AccountRegisterDTO(

        @JsonProperty("name")
        @NotBlank(message = "Name is required") String name,

        @JsonProperty("email")
        @Email(message = "Email is invalid") String email,

        @JsonProperty("phone_number")
        @Pattern(regexp = Regex.PHONE_NUMBER_REGEX, message = "Phone number is invalid")
        @NotBlank(message = "Phone number is required") String phoneNumber,

        @JsonProperty("gender") Gender gender,

        @JsonProperty("address")
        Set<Address> address,

        @JsonProperty("password")
        @Pattern(regexp = Regex.PASSWORD_REGEX, message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
        @NotBlank(message = "Password is required") String password,

        @JsonProperty("confirm_password")
        @NotBlank(message = "Confirm password is required") String confirmPassword,

        @JsonProperty("date_of_birth")
        String dateOfBirth,

        @JsonProperty("preferred_language")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Country preferredLanguage,

        @JsonProperty("preferred_currency")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Currency preferredCurrency,

        @JsonProperty("avatar")
        String avatar

    ){}

    record ForgotPasswordResponse(String message) {

    }

    @JsonPropertyOrder({
        "token",
        "user"
    })
    @JsonInclude(Include.NON_NULL)
    record LoginResponse(
        TokenResponse token
        //user's detail
//    UserPort.UserResponse user
    ) {}

    record UpdatePasswordDTO(
        @JsonProperty("email")
        @Email(message = "Email must be a valid email")
        @NotBlank(message = "Email must not be blank")
        String email,

        @JsonProperty("new_password")
        @Pattern(regexp = Regex.PASSWORD_REGEX, message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
        @NotBlank(message = "New password must not be blank")
        String newPassword
    ) {}

    record UserLoginDTO(
        @JsonProperty("email")
        @Email(message = "Email is not valid")
        @NotBlank(message = "Email is required")
        String email,

        @JsonProperty("password")
        @NotBlank(message = "Password is required")
        String password
    ) {}

    record VerifyUserDTO(
        @JsonProperty("email")
        @NotBlank(message = "Email is required") String email,

        @JsonProperty("otp")
        @NotBlank(message = "OTP is required") String otp
    ) {}

}
