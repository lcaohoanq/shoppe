package com.lcaohoanq.shoppe.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record SubcategoryDTO(
    @NotNull(message = "Category id is required") @JsonProperty("category_id") Long categoryId,
    @NotEmpty(message = "Sub Category name is required") List<String> name
) {}
