package com.lcaohoanq.orderservice.domain.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;

public record OrderDetailDTO(
    @JsonProperty("order_id") @Min(value = 1, message = "Order's ID must be > 0") Long orderId,
    @Min(value = 1, message = "Product's ID must be > 0") @JsonProperty("product_id") Long koiId,
    @Min(value = 0, message = "Product's ID must be >= 0") Float price,
    @Min(value = 1, message = "number_of_products must be >= 1") @JsonProperty("number_of_products") int numberOfProducts,
    @Min(value = 0, message = "total_money must be >= 0") @JsonProperty("total_money") Float totalMoney

) {
    public static OrderDetailDTO fromOrderDetail(OrderDetail orderDetail){
        return new OrderDetailDTO(
            orderDetail.getOrder().getId(),
            orderDetail.getProduct().getId(),
            orderDetail.getPrice(),
            orderDetail.getNumberOfProducts(),
            orderDetail.getTotalMoney()
        );
    }
}
