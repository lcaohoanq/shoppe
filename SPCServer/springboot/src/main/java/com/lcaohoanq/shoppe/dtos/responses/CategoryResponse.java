package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CategoryResponse(
    @JsonProperty("id")
    Long id,
    @JsonProperty("name")
    String name
) {}
