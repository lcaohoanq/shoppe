package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;

@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "thumbnail",
    "images",
    "category",
    "price",
    "shop_owner_id",
    "price_before_discount",
    "quantity",
    "sold",
    "view",
    "rating",
    "created_at",
    "updated_at"
})
public record ProductResponse(
    Long id,
    String name,
    String description,
    String thumbnail,

    List<ProductImageResponse> images,

    CategoryResponse category,
    Double price,
    @JsonProperty("shop_owner_id") Long shopOwnerId,

    @JsonProperty("price_before_discount")
    Double priceBeforeDiscount,
    Integer quantity,
    Integer sold,
    Integer view,
    Double rating,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {}