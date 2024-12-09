package com.lcaohoanq.shoppe.domain.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateOrderStatusDTO(
    @NotBlank(message = "Order status is required")
    @Pattern(regexp = "PROCESSING|SHIPPING|DELIVERED|PENDING|CANCELLED|COMPLETED", message = "Status must be either PROCESSING, SHIPPING, DELIVERED, PENDING, CANCELLED or COMPLETED")
    @JsonProperty("status")
    String status
) {}
