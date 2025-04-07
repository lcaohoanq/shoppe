package com.lcaohoanq.orderservice.domain.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.jvservice.domain.product.ProductPort.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "order_details_id",
    "order_id",
    "product_id",
    "price",
    "number_of_products",
    "total_money",
    "color"
})
public class OrderDetailResponse {
    
    @JsonProperty("order_details_id")
    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_id")
    private ProductResponse productId;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @JsonProperty("total_money")
    private Float totalMoney;
}
