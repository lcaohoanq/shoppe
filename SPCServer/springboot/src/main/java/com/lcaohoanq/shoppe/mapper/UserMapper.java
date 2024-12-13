package com.lcaohoanq.shoppe.mapper;

import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "walletId", source = "wallet.id")
    UserResponse toUserResponse(User user);
}
