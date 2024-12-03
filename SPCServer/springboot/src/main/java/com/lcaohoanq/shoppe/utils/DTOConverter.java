package com.lcaohoanq.shoppe.utils;

import com.lcaohoanq.shoppe.dtos.responses.CategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.OrderDetailResponse;
import com.lcaohoanq.shoppe.dtos.responses.OrderResponse;
import com.lcaohoanq.shoppe.dtos.responses.ProductResponse;
import com.lcaohoanq.shoppe.dtos.responses.RoleResponse;
import com.lcaohoanq.shoppe.dtos.responses.UserResponse;
import com.lcaohoanq.shoppe.models.Category;
import com.lcaohoanq.shoppe.models.Order;
import com.lcaohoanq.shoppe.models.OrderDetail;
import com.lcaohoanq.shoppe.models.Product;
import com.lcaohoanq.shoppe.models.Role;
import com.lcaohoanq.shoppe.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DTOConverter {

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            user.getName(),
            String.valueOf(user.getDateOfBirth()).split(" ")[0],
            user.getPhoneNumber(),
            user.getAddress(),
            user.getAvatar(),
            user.getRole() != null ? user.getRole().getUserRole().name() : null,
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName(),
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }

    public static RoleResponse toRoleResponse(Role role) {
        return new RoleResponse(
            role.getId(),
            role.getUserRole(),
            role.getCreatedAt(),
            role.getUpdatedAt()
        );
    }

    public static OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .user(DTOConverter.toUserResponse(order.getUser()))
                .firstName(order.getFirstName())
                .lastName(order.getLastName())
                .phoneNumber(order.getPhoneNumber())
                .email(order.getEmail())
                .address(order.getAddress())
                .note(order.getNote())
                .orderDate(order.getOrderDate())
                .status(String.valueOf(order.getStatus()))
                .totalMoney(order.getTotalMoney())
                .shippingMethod(order.getShippingMethod())
                .shippingAddress(order.getShippingAddress())
                .shippingDate(order.getShippingDate())
                .paymentMethod(order.getPaymentMethod())
                .orderDetails(order.getOrderDetails())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    public static ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getImages(),
            product.getPrice(),
            product.getQuantity(),
            product.getCategory(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse
                .builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(DTOConverter.toProductResponse(orderDetail.getProduct()))
                .price(orderDetail.getPrice())
                .numberOfProducts(orderDetail.getNumberOfProducts())
                .totalMoney(orderDetail.getTotalMoney())
                .build();
    }

}
