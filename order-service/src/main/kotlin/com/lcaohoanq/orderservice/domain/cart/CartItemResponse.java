package com.lcaohoanq.orderservice.domain.cart;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.orderservice.domain.cart.CartItem.CartItemStatus;
import com.lcaohoanq.jvservice.domain.product.ProductPort;
import java.time.LocalDateTime;

@JsonPropertyOrder(
    {
        "id",
        "cart_id",
        "product",
        "quantity",
        "created_at",
        "updated_at"
    }
)
public record CartItemResponse(
    @JsonProperty("id")
    Long id,
    @JsonProperty("cart_id")
    Long cartId,
    @JsonProperty("product")
    ProductPort.ProductResponse product,
    @JsonProperty("quantity")
    int quantity,
    CartItemStatus status,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {}
