package com.lcaohoanq.shoppe.domain.cart;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CartItemDTO(
    @JsonProperty("product_id")
    Long productId,
    @JsonProperty("quantity")
    Integer quantity
) {}
