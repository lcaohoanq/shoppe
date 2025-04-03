package com.lcaohoanq.jvservice.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.jvservice.domain.role.Role;
import com.lcaohoanq.jvservice.domain.user.User.Gender;
import com.lcaohoanq.common.enums.UserStatus;
import java.time.LocalDateTime;
import java.util.Set;

@JsonPropertyOrder(
    {
        "id",
        "email",
        "password",
        "name",
        "gender",
        "is_active",
        "status",
        "date_of_birth",
        "phone_number",
        "address",
        "avatar",
        "role",
        "wallet_id",
        "preferred_language",
        "preferred_currency",
        "last_login_timestamp",
        "created_at",
        "updated_at"
    }
)
public record UserResponse(
    Long id,
    String email,
    @JsonIgnore String password,
    String name,
    Gender gender,
    @JsonProperty("is_active") boolean isActive,
    UserStatus status,
    @JsonProperty("date_of_birth") String dateOfBirth,
    @JsonProperty("phone_number") String phoneNumber,
    @JsonProperty("address") Set<Address> address,
    @JsonProperty("avatar") String avatar,
    @JsonProperty("role") Role role,
    @JsonIgnore @JsonProperty("wallet_id") Long walletId,
    @JsonProperty("preferred_language") String preferredLanguage,
    @JsonProperty("preferred_currency") String preferredCurrency,
    
    @JsonProperty("last_login_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime lastLoginTimestamp,
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {

}
