package com.lcaohoanq.shoppe.domain.cart;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.domain.product.Product;
import com.lcaohoanq.shoppe.domain.product.ProductResponse;
import java.time.LocalDateTime;

public record CartItemResponse(
    @JsonProperty("id")
    Long id,
    @JsonProperty("cart_id")
    Long cartId,
    @JsonProperty("product")
    ProductResponse product,
    @JsonProperty("quantity")
    int quantity,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {}
