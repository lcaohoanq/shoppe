package com.lcaohoanq.orderservice.domain.cart;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.jvservice.domain.user.UserResponse;
import java.time.LocalDateTime;
import java.util.List;

@JsonPropertyOrder({
    "id",
    "total_quantity",
    "total_price",
    "user",
    "cart_items",
    "created_at",
    "updated_at"
})
public record CartResponse(
    @JsonProperty("id")
    Long id,

    @JsonProperty("total_quantity")
    Integer totalQuantity,

    @JsonProperty("total_price")
    Double totalPrice,

    @JsonProperty("user")
    UserResponse user,

    @JsonProperty("cart_items")
    @JsonManagedReference
    List<CartItemResponse> cartItems,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {}
