package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.models.Subcategory;
import java.util.List;

public record SubcategoryResponse(
    @JsonProperty("category")
    @JsonIgnore
    CategoryResponse categoryResponse,
    @JsonProperty("subcategories")
    List<Subcategory> subcategories
) {}
