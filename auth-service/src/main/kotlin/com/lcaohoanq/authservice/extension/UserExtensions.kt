package com.lcaohoanq.authservice.extension

import com.lcaohoanq.authservice.domains.user.User
import com.lcaohoanq.common.dto.UserPort


fun User.toUserResponse() = UserPort.UserResponse(
    id = this.id!!,
    email = email,
    password = hashedPassword,
    username = userName,
    phone = phone,
    status = status!!,
    totp = totpSecret,
    lastLoginTimeStamp = lastLoginTimeStamp!!,
    role = role!!,
    avatar = avatar,
    createdAt = createdAt,
    updatedAt = updatedAt
)