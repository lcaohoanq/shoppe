package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("email") String email,
    @JsonIgnore @JsonProperty("password") String password,
    @JsonProperty("name") String firstName,
    @JsonProperty("date_of_birth") String dob,
    @JsonProperty("phoneNumber") String phone,
    @JsonProperty("address") String address,
    @JsonProperty("avatar") String avatar,
    @JsonProperty("role_name") String roleName,
    @JsonProperty("created_at") String createdAt,
    @JsonProperty("updated_at") String updatedAt
) {}
