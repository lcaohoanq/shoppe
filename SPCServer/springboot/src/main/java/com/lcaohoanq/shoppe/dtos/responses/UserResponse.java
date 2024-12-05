package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.enums.UserStatus;
import java.time.LocalDateTime;

public record UserResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("email") String email,
    @JsonIgnore @JsonProperty("password") String password,
    @JsonProperty("name") String firstName,
    @JsonProperty("is_active") boolean isActive,
    @JsonProperty("status") UserStatus status,
    @JsonProperty("date_of_birth") String dob,
    @JsonProperty("phoneNumber") String phone,
    @JsonProperty("address") String address,
    @JsonProperty("avatar") String avatar,
    @JsonProperty("role_name") String roleName,
    @JsonIgnore
    @JsonProperty("wallet_id") Long walletId,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {}
