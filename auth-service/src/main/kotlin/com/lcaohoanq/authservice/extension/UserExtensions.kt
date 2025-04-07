package com.lcaohoanq.authservice.extension

import com.lcaohoanq.authservice.domains.user.User
import com.lcaohoanq.authservice.dto.UserPort


fun User.toUserResponse() = UserPort.UserResponse(
    id = this.id!!,
    email = email,
    password = hashedPassword,
    username = userName,
    address = address,
    phone = phone,
    avatar = avatar,
    createdAt = createdAt,
    updatedAt = updatedAt
)