package com.lcaohoanq.orderservice.domain.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentStatusUpdateDTO {
    @NotBlank(message = "Payment status is required")
    @Pattern(regexp = "SUCCESS|REFUNDED|PENDING", message = "Status must be either SUCCESS, REFUNDED or PENDING")
    @JsonProperty("status")
    String status;
}
