package com.lcaohoanq.shoppe.domain.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.shoppe.domain.category.CategoryPort.CategoryResponse;
import com.lcaohoanq.shoppe.domain.product.Product.ProductStatus;
import com.lcaohoanq.shoppe.metadata.MediaMeta;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductPort {

    record ProductDTO(

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
    ) {

    }

    record ProductImageDTO(
        @JsonProperty("product_id")
        @Min(value = 1, message = "Product ID must be greater than 0")
        Long productId,

        @JsonProperty("image_url")
        @Size(min = 5, max = 300, message = "Image URL must be between 5 and 300 characters")
        String imageUrl
    ) {

    }

    @JsonPropertyOrder({
        "id",
        "product_id",
        "image_url",
        "video_url"
    })
    @JsonInclude(Include.NON_NULL)
    record ProductImageResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("product_id") Long productId,
        @JsonProperty("media_meta") MediaMeta mediaMeta
    ) {

    }

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
    record ProductResponse(
        Long id,
        String name,
        String description,

        List<ProductImageResponse> images,

        CategoryResponse category,
        Double price,

        @JsonIgnore
        @JsonProperty("shop_owner_id") Long shopOwnerId,

        @JsonProperty("price_before_discount")
        Double priceBeforeDiscount,

        @JsonIgnore
        Integer quantity,

        Integer sold,

        @JsonIgnore
        Integer view,

        Double rating,

        @JsonIgnore
        @JsonProperty("status") ProductStatus status,

        @JsonIgnore
        @JsonProperty("is_active") boolean isActive,

        @JsonIgnore
        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
        LocalDateTime createdAt,

        @JsonIgnore
        @JsonProperty("updated_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
        LocalDateTime updatedAt
    ) {

    }

}
