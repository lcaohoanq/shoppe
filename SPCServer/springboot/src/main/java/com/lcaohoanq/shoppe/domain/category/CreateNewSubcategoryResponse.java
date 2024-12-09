package com.lcaohoanq.shoppe.domain.category;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateNewSubcategoryResponse(
    @JsonProperty("category") CategoryResponse categoryResponse
) {}
