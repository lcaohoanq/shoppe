package com.lcaohoanq.ktservice.extension

import com.lcaohoanq.ktservice.domain.user.User
import com.lcaohoanq.ktservice.domain.user.UserPort

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