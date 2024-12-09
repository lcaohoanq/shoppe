package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.models.Subcategory;
import java.util.List;

public record CreateNewSubcategoryResponse(
    @JsonProperty("category") CategoryResponse categoryResponse
) {}
