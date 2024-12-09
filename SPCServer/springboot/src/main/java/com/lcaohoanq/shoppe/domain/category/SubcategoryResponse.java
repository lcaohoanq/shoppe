package com.lcaohoanq.shoppe.domain.category;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SubcategoryResponse(
    @JsonProperty("subcategory") Subcategory subcategories
) {}
