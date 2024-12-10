package com.lcaohoanq.shoppe.domain.cart;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartResponse(
    @JsonProperty("id")
    Long id,
    
    @JsonProperty("total_quantity")
    Integer totalQuantity,

    @JsonProperty("total_price")
    Double totalPrice,

    @JsonProperty("user_id")
    @NotNull(message = "User id is required when creating a new cart")
    Long userId,

    @JsonProperty("cart_items")
    List<CartItem> cartItems,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {}
