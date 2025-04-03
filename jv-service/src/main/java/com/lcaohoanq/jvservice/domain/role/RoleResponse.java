package com.lcaohoanq.jvservice.domain.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.common.enums.UserRole;
import java.time.LocalDateTime;

@JsonPropertyOrder(
    {
        "id",
        "name",
        "created_at",
        "updated_at"
    }
)
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
