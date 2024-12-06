package com.lcaohoanq.shoppe.utils;

import com.lcaohoanq.shoppe.dtos.responses.CategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.OrderDetailResponse;
import com.lcaohoanq.shoppe.dtos.responses.OrderResponse;
import com.lcaohoanq.shoppe.dtos.responses.ProductImageResponse;
import com.lcaohoanq.shoppe.dtos.responses.ProductResponse;
import com.lcaohoanq.shoppe.dtos.responses.RoleResponse;
import com.lcaohoanq.shoppe.dtos.responses.UserResponse;
import com.lcaohoanq.shoppe.models.Category;
import com.lcaohoanq.shoppe.models.Order;
import com.lcaohoanq.shoppe.models.OrderDetail;
import com.lcaohoanq.shoppe.models.Product;
import com.lcaohoanq.shoppe.models.ProductImage;
import com.lcaohoanq.shoppe.models.Role;
import com.lcaohoanq.shoppe.models.User;

public interface DTOConverter {

    default UserResponse toUserResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            user.getName(),
            user.isActive(),
            user.getStatus(),
            String.valueOf(user.getDateOfBirth()).split(" ")[0],
            user.getPhoneNumber(),
            user.getAddress(),
            user.getAvatar(),
            user.getRole() != null ? user.getRole().getUserRole().name() : null,
            user.getWallet().getId(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    default CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName(),
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }

    default RoleResponse toRoleResponse(Role role) {
        return new RoleResponse(
            role.getId(),
            role.getUserRole(),
            role.getCreatedAt(),
            role.getUpdatedAt()
        );
    }

    default OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
            .id(order.getId())
            .user(toUserResponse(order.getUser()))
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

    default ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(), 
            product.getImages().stream().map(this::toProductImageResponse).toList(),
            toCategoryResponse(product.getCategory()),
            product.getPrice(),
            product.getShopOwner().getId(),
            product.getPriceBeforeDiscount(),
            product.getQuantity(),
            product.getSold(),
            product.getView(),
            product.getRating(),
            product.isActive(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }

    default ProductImageResponse toProductImageResponse(ProductImage productImage){
        return new ProductImageResponse(
            productImage.getId(),
            productImage.getProduct().getId(),
            productImage.getMediaMeta()
        );
    }

    default OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        return OrderDetailResponse
            .builder()
            .id(orderDetail.getId())
            .orderId(orderDetail.getOrder().getId())
            .productId(toProductResponse(orderDetail.getProduct()))
            .price(orderDetail.getPrice())
            .numberOfProducts(orderDetail.getNumberOfProducts())
            .totalMoney(orderDetail.getTotalMoney())
            .build();
    }

}
