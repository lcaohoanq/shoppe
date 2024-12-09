package com.lcaohoanq.shoppe.domain.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.enums.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductDTO(

    @NotNull(message = "Product's name must not be null")
    String name,

    @NotNull(message = "Product's description must not be null")
    String description,

    @NotNull(message = "Product's category must not be null")
    String category,

    @NotNull(message = "Product's owner must not be null")
    Long shopOwnerId,

    @Min(value = 0, message = "Price must be greater than 0")
    Double price,

    @JsonProperty("price_before_discount")
    @Min(value = 0, message = "Price before discount must be greater than 0")
    Double priceBeforeDiscount,

    @Min(value = 0, message = "Quantity must be greater than 0")
    Integer quantity,
    Integer sold,
    Integer view,
    Double rating,
    ProductStatus status,
    @JsonProperty("is_active")
    Boolean isActive
) {}
