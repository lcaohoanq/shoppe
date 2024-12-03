package com.lcaohoanq.shoppe.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RoleDTO(
    @JsonProperty("name")
    @NotBlank(message = "Role name is required")
    UserRole userRole
) {}
