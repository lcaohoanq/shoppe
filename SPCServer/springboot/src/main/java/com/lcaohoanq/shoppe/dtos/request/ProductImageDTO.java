package com.lcaohoanq.shoppe.dtos.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ProductImageDTO(
    @JsonProperty("product_id")
    @Min(value = 1, message = "Product ID must be greater than 0")
    Long productId,

    @JsonProperty("image_url")
    @Size(min = 5, max = 300, message = "Image URL must be between 5 and 300 characters")
    String imageUrl
) {}
