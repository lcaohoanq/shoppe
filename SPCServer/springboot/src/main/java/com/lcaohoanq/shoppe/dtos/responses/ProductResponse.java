package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductResponse(
    Long id,
    String name,
    String description,
    String image,
    Double price,
    Integer quantity,
    String category,
    @JsonProperty("created_at") String createdAt,
    @JsonProperty("updated_at") String updatedAt
) {}
