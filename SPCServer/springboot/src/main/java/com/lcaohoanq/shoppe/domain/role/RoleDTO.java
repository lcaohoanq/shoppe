package com.lcaohoanq.shoppe.domain.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.enums.UserRole;
import lombok.Builder;

@Builder
public record RoleDTO(
    @JsonProperty("name")
    UserRole userRole
) {}
