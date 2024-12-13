package com.lcaohoanq.shoppe.util;

import com.lcaohoanq.shoppe.domain.cart.Cart;
import com.lcaohoanq.shoppe.domain.cart.CartItem;
import com.lcaohoanq.shoppe.domain.cart.CartItemResponse;
import com.lcaohoanq.shoppe.domain.cart.CartResponse;
import com.lcaohoanq.shoppe.domain.category.Category;
import com.lcaohoanq.shoppe.domain.category.CategoryResponse;
import com.lcaohoanq.shoppe.domain.inventory.Warehouse;
import com.lcaohoanq.shoppe.domain.inventory.WarehouseResponse;
import com.lcaohoanq.shoppe.domain.subcategory.Subcategory;
import com.lcaohoanq.shoppe.domain.order.Order;
import com.lcaohoanq.shoppe.domain.order.OrderDetail;
import com.lcaohoanq.shoppe.domain.order.OrderDetailResponse;
import com.lcaohoanq.shoppe.domain.order.OrderResponse;
import com.lcaohoanq.shoppe.domain.product.Product;
import com.lcaohoanq.shoppe.domain.product.ProductImage;
import com.lcaohoanq.shoppe.domain.product.ProductImageResponse;
import com.lcaohoanq.shoppe.domain.product.ProductResponse;
import com.lcaohoanq.shoppe.domain.role.Role;
import com.lcaohoanq.shoppe.domain.role.RoleResponse;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.user.UserResponse;
import com.lcaohoanq.shoppe.domain.wallet.Wallet;
import com.lcaohoanq.shoppe.domain.wallet.WalletDTO.WalletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

public interface DTOConverter {

    default UserResponse toUserResponse(User user) {
        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getPassword(),
            user.getName(),
            user.getGender(),
            user.isActive(),
            user.getStatus(),
            String.valueOf(user.getDateOfBirth()).split(" ")[0],
            user.getPhoneNumber(),
            user.getAddress(),
            user.getAvatar(),
            user.getRole(),
            user.getWallet().getId(),
            user.getPreferredLanguage(),
            user.getPreferredCurrency(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }

    default CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
            category.getId(),
            category.getName(),
            new TreeSet<>(category.getSubcategories()), 
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
        List<ProductImageResponse> productImageResponses = Optional.ofNullable(product.getImages())
            .orElse(Collections.emptyList())
            .stream()
            .map(this::toProductImageResponse)
            .toList();
        
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            productImageResponses,
            toCategoryResponse(product.getCategory()),
            product.getPrice(),
            product.getShopOwner().getId(),
            product.getPriceBeforeDiscount(),
            product.getQuantity(),
            product.getSold(),
            product.getView(),
            product.getRating(),
            product.getStatus(),
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

    default OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail) {
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
    
    default CartResponse toCartResponse(Cart cart){
        return new CartResponse(
            cart.getId(), 
            cart.getTotalQuantity(),
            cart.getTotalPrice(),
            toUserResponse(cart.getUser()),
            cart.getCartItems().stream().map(this::toCartItemResponse).toList(),
            cart.getCreatedAt(),
            cart.getUpdatedAt()
        );
    }
    
    default CartItemResponse toCartItemResponse(CartItem cartItem){
        return new CartItemResponse(
            cartItem.getId(),
            cartItem.getCart().getId(),
            this.toProductResponse(cartItem.getProduct()),
            cartItem.getQuantity(),
            cartItem.getStatus(),
            cartItem.getCreatedAt(),
            cartItem.getUpdatedAt()
        );
    }
    
    default WalletResponse toWalletResponse(Wallet wallet){
        return new WalletResponse(
            wallet.getId(),
            wallet.getBalance(),
            wallet.getCreatedAt(),
            wallet.getUpdatedAt()
        );
    }

    default WarehouseResponse toWareHouseResponse(Warehouse warehouse){
        return new WarehouseResponse(
            warehouse.getId(),
            warehouse.getName(),
            warehouse.getAddress(),
            warehouse.getCity(),
            warehouse.getCountry(),
            warehouse.getQuantity(),
            warehouse.getReserved(),
            warehouse.getReorderPoint(),
            warehouse.getCreatedAt(),
            warehouse.getUpdatedAt()
        );
    }
    
}
