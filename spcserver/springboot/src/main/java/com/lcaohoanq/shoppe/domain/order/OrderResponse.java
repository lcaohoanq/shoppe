package com.lcaohoanq.shoppe.domain.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.shoppe.domain.user.UserResponse;
import com.lcaohoanq.shoppe.enums.OrderStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({
    "order_id",
    "user",
    "first_name",
    "last_name",
    "phone_number",
    "email",
    "address",
    "note",
    "order_date",
    "status",
    "total_money",
    "shipping_method",
    "shipping_address",
    "shipping_date",
    "payment_method",
    "tracking_number",
    "order_details",
    "created_at",
    "updated_at"
})
public class OrderResponse {

    @JsonProperty("order_id")
    private Long id;

    @JsonProperty("user")
    private UserResponse user;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("note")
    private String note;

    @JsonProperty("order_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @JsonProperty("status")
    private OrderStatus status;

    @JsonProperty("total_money")
    private double totalMoney;

    @JsonProperty("shipping_method")
    private String shippingMethod;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("shipping_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate shippingDate;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("order_details")
//    @JsonIgnore
    private List<OrderDetailResponse> orderDetails;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime createdAt;
    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    LocalDateTime updatedAt;
}