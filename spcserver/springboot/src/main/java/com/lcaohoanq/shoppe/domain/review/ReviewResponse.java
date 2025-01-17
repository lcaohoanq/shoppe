package com.lcaohoanq.shoppe.domain.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.domain.order.OrderResponse;
import com.lcaohoanq.shoppe.domain.user.UserResponse;
import com.lcaohoanq.shoppe.metadata.MediaMeta;
import java.time.LocalDateTime;

public record ReviewResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("content") String content,
    @JsonProperty("rating") Integer rating,
    @JsonProperty("is_hidden") boolean isHidden,
    @JsonProperty("product_quality") String productQuality,
    @JsonProperty("match_description") String matchDescription,
    @JsonProperty("media_meta") MediaMeta mediaMeta,
    @JsonProperty("user") UserResponse userResponse,
    @JsonProperty("order") OrderResponse orderResponse,
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt,
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt
) {}
