package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "id",
    "product_id",
    "image_url",
    "video_url"
})
@JsonInclude(Include.NON_NULL)
public record ProductImageResponse(
    @JsonProperty("id") Long id,
    @JsonProperty("product_id") Long productId,
    @JsonProperty("image_url") String imageUrl,
    @JsonProperty("video_url") String videoUrl
) {}
