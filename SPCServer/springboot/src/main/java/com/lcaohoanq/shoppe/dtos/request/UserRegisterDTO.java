package com.lcaohoanq.shoppe.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.constants.Regex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserRegisterDTO(

    @JsonProperty("name")
    @NotBlank(message = "Name is required") String name,

    @JsonProperty("email")
    @Email(message = "Email is invalid") String email,

    @JsonProperty("address")
    String address,

    @JsonProperty("password")
    @Pattern(regexp = Regex.PASSWORD_REGEX, message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
    @NotBlank(message = "Password is required") String password,

    @JsonProperty("confirm_password")
    @NotBlank(message = "Confirm password is required") String confirmPassword,

    @JsonProperty("date_of_birth")
    String dateOfBirth,

    @JsonProperty("avatar")
    String avatar

){}