package com.lcaohoanq.productservice.domain.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record AddNewCartDTO(
    @JsonProperty("user_id")
    @NotNull(message = "User id is required when creating a new cart")
    Long userId
) {}
