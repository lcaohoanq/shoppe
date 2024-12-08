package com.lcaohoanq.shoppe.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CategoryDTO(
    @NotEmpty(message = "Category name is required") List<String> name
) {}
