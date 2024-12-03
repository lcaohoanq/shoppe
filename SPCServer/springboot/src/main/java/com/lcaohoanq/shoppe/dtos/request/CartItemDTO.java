package com.lcaohoanq.shoppe.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CartItemDTO(
    @JsonProperty("koi_id")
    Long koiId,
    @JsonProperty("quantity")
    Integer quantity
) {}
