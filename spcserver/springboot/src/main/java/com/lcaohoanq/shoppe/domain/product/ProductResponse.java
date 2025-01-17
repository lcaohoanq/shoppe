package com.lcaohoanq.shoppe.domain.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.shoppe.domain.category.CategoryPort.CategoryResponse;
import com.lcaohoanq.shoppe.domain.product.Product.ProductStatus;
import com.lcaohoanq.shoppe.domain.product.ProductPort.ProductImageResponse;
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
    "is_active",
    "created_at",
    "updated_at"
})
public record ProductResponse(
    Long id,
    String name,
    String description,

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
    
    @JsonProperty("status") ProductStatus status,
    @JsonProperty("is_active") boolean isActive,

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {}
