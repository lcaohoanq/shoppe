package com.lcaohoanq.shoppe.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record VerifyUserDTO(
   @JsonProperty("email")
   @NotBlank(message = "Email is required") String email,

    @JsonProperty("otp")
    @NotBlank(message = "OTP is required") String otp
) {}
