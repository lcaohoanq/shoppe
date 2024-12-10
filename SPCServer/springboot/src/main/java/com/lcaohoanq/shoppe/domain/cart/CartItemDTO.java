package com.lcaohoanq.shoppe.domain.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartItemDTO(
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product id must be greater than 0")
    @NotNull(message = "Product id is required") Long productId,
    
    @JsonProperty("quantity")
    @Min(value = 1, message = "Quantity must be greater than 0")
    @NotNull(message = "Quantity is required") Integer quantity
) {}
