package com.lcaohoanq.jvservice.domain.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.jvservice.enums.UserRole;
import lombok.Builder;

@Builder
public record RoleDTO(
    @JsonProperty("name")
    UserRole userRole
) {}
