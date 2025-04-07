package com.lcaohoanq.jvservice.mapper;

import com.lcaohoanq.jvservice.domain.order.Order;
import com.lcaohoanq.jvservice.domain.order.OrderDetail;
import com.lcaohoanq.jvservice.domain.order.OrderDetailResponse;
import com.lcaohoanq.jvservice.domain.order.OrderResponse;
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
public interface OrderMapper {
    @Mapping(source = "user", target = "user", qualifiedByName = "mapUserToUserResponse")
    @Mapping(source = "orderDetails", target = "orderDetails", qualifiedByName = "mapOrderDetailsToOrderDetailResponses")
    OrderResponse toOrderResponse(Order order);

    @Named("mapUserToUserResponse")
    default UserResponse mapUser(User user) {
        return user == null ? null : Mappers.getMapper(UserMapper.class).toUserResponse(user);
    }

    @Named("mapOrderDetailsToOrderDetailResponses")
    default List<OrderDetailResponse> mapOrderDetails(List<OrderDetail> orderDetails) {
        if (orderDetails == null) {
            return null;
        }
        return orderDetails.stream()
            .map(this::toOrderDetailResponse)
            .collect(Collectors.toList());
    }

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product", target = "productId", qualifiedByName = "mapProductToProductResponse")
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);

    @Named("mapProductToProductResponse")
    default ProductPort.ProductResponse mapProduct(Product product) {
        return product == null ? null : Mappers.getMapper(ProductMapper.class).toProductResponse(product);
    }
}