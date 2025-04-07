package com.lcaohoanq.jvservice.mapper;

import com.lcaohoanq.jvservice.domain.cart.Cart;
import com.lcaohoanq.jvservice.domain.cart.CartItem;
import com.lcaohoanq.jvservice.domain.cart.CartItemResponse;
import com.lcaohoanq.jvservice.domain.cart.CartResponse;
import com.lcaohoanq.jvservice.domain.product.Product;
import com.lcaohoanq.jvservice.domain.product.ProductPort;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.domain.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ProductMapper.class})
public interface CartMapper {

    @Mapping(source = "user", target = "user", qualifiedByName = "mapUserToUserResponse")
    @Mapping(source = "cartItems", target = "cartItems", qualifiedByName = "mapCartItemsToCartItemResponses")
    @Mapping(target = "totalQuantity", source = "cartItems", qualifiedByName = "calculateTotalQuantity")
    @Mapping(target = "totalPrice", source = "cartItems", qualifiedByName = "calculateTotalPrice")
    CartResponse toCartResponse(Cart cart);

    @Named("mapUserToUserResponse")
    default UserResponse mapUser(User user) {
        return user == null ? null : Mappers.getMapper(UserMapper.class).toUserResponse(user);
    }

    @Named("mapCartItemsToCartItemResponses")
    default List<CartItemResponse> mapCartItems(List<CartItem> cartItems) {
        if (cartItems == null) {
            return null;
        }
        return cartItems.stream()
            .map(this::toCartItemResponse)
            .collect(Collectors.toList());
    }

    @Named("calculateTotalQuantity")
    default Integer calculateTotalQuantity(List<CartItem> cartItems) {
        if (cartItems == null) {
            return 0;
        }
        return cartItems.stream()
            .mapToInt(CartItem::getQuantity)
            .sum();
    }

    @Named("calculateTotalPrice")
    default Double calculateTotalPrice(List<CartItem> cartItems) {
        if (cartItems == null) {
            return 0.0;
        }
        return cartItems.stream()
            .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
            .sum();
    }

    @Mapping(source = "cart.id", target = "cartId")
    @Mapping(source = "product", target = "product", qualifiedByName = "mapProductToProductResponse")
    CartItemResponse toCartItemResponse(CartItem cartItem);

    @Named("mapProductToProductResponse")
    default ProductPort.ProductResponse mapProduct(Product product) {
        return product == null ? null : Mappers.getMapper(ProductMapper.class).toProductResponse(product);
    }
}