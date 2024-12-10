package com.lcaohoanq.shoppe.domain.subcategory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.domain.category.CategoryResponse;

public record CreateNewSubcategoryResponse(
    @JsonProperty("category") CategoryResponse categoryResponse
) {}
