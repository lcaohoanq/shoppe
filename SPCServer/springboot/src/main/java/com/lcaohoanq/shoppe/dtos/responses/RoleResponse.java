package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.enums.UserRole;

public record RoleResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("name") UserRole name
) {}
