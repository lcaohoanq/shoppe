package com.lcaohoanq.shoppe.domain.auth;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
    @JsonProperty("email")
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is required")
    String email,

    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    String password
) {}
