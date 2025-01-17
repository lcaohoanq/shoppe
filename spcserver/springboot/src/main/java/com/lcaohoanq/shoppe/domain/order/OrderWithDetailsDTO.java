package com.lcaohoanq.shoppe.domain.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record OrderWithDetailsDTO(
    @JsonProperty("user_id")
    @Min(value = 1, message = "User's ID must be > 0")
    Long userId,

    @JsonProperty("fullname")
    String fullName,

    String email,

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is required")
    @Size(min = 5, message = "Phone number must be at least 5 characters")
    String phoneNumber,

    String address,

    String note,

    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    Float totalMoney,

    @JsonProperty("shipping_method")
    String shippingMethod,

    @JsonProperty("shipping_address")
    String shippingAddress,

    @JsonProperty("shipping_date")
    LocalDate shippingDate,

    @JsonProperty("payment_method")
    String paymentMethod,

    @JsonProperty("order_details")
    List<OrderDetailDTO> orderDetailDTOS
) {}