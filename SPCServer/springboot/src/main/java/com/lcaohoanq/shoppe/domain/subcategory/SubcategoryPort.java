package com.lcaohoanq.shoppe.domain.subcategory;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public interface SubcategoryPort {

    record SubcategoryDTO(
        @NotNull(message = "Category id is required") @JsonProperty("category_id") Long categoryId,
        @NotEmpty(message = "Sub Category name is required") Set<String> name
    ) {}

    record SubcategoryResponse(
        @JsonProperty("subcategory") Subcategory subcategories
    ) {}

}
