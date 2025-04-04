package com.lcaohoanq.jvservice.domain.role;

import jakarta.validation.constraints.NotBlank;

public record UpdateRolePurposeDTO(
    @NotBlank(message = "Update role Purpose is required")
    String purpose
) {}
