package com.lcaohoanq.shoppe.domain.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.enums.UserRole;
import java.time.LocalDateTime;

public record RoleResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("name") UserRole name,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {}
