package com.lcaohoanq.jvservice.domain.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record ReviewDTO(
    @JsonProperty("content") String content,
    @JsonProperty("rating") int rating,
    @JsonProperty("user_id") @NotNull long userId,
    @JsonProperty("order_id") @NotNull long orderId)
{ }
