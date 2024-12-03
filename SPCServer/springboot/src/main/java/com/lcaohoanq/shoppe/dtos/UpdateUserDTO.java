package com.lcaohoanq.shoppe.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
@JsonInclude(Include.NON_NULL)
public record UpdateUserDTO(
    @JsonProperty("name") String name,
    @JsonProperty("email") String email,
    @JsonProperty("phone_number") String phoneNumber,
    @JsonProperty("password") String password,
    @JsonProperty("confirm_password") String confirmPassword, // in case user wants to change password
    @JsonProperty("address") String address,
    @JsonProperty("status")
    @Pattern(regexp = "ACTIVE|INACTIVE|VERIFIED|UNVERIFIED|BANNED", message = "Status must be either ONGOING, INACTIVE, VERIFIED, UNVERIFIED, BANNED")
    String status,
    @JsonProperty("date_of_birth") String dob,
    @JsonProperty("avatar") String avatar
) {}
