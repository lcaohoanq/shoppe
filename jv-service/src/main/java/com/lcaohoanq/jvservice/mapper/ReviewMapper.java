package com.lcaohoanq.jvservice.mapper;

import com.lcaohoanq.jvservice.domain.order.Order;
import com.lcaohoanq.jvservice.domain.order.OrderResponse;
import com.lcaohoanq.jvservice.domain.review.Review;
import com.lcaohoanq.jvservice.domain.review.ReviewResponse;
import com.lcaohoanq.jvservice.domain.user.User;
import com.lcaohoanq.jvservice.domain.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user", target = "userResponse", qualifiedByName = "mapUserToUserResponse")
    @Mapping(source = "order", target = "orderResponse", qualifiedByName = "mapOrderToOrderResponse")
    ReviewResponse toReviewResponse(Review review);
    
    @Mapping(target = "user", source = "userResponse", qualifiedByName = "mapUser")
    Review toReview(ReviewResponse reviewResponse);

    @Named("mapUserToUserResponse")
    default UserResponse mapUser(User user) {
        return user == null ? null : Mappers.getMapper(UserMapper.class).toUserResponse(user);
    }
    
    @Named("mapUser")
    default User mapUser(UserResponse userResponse){
        return userResponse == null ? null : Mappers.getMapper(UserMapper.class).toUser(userResponse);
    }
    
    @Named("mapOrderToOrderResponse")
    default OrderResponse mapOrder(Order order){
        return order == null ? null : Mappers.getMapper(OrderMapper.class).toOrderResponse(order);
    }
}
