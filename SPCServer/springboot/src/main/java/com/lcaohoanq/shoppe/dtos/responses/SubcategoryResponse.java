package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.models.Subcategory;

public record SubcategoryResponse(
    @JsonProperty("subcategory") Subcategory subcategories
) {}