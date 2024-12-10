package com.lcaohoanq.shoppe.domain.subcategory;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SubcategoryResponse(
    @JsonProperty("subcategory") Subcategory subcategories
) {}
