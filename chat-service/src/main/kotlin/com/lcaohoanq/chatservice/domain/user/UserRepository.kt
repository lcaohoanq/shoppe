package com.lcaohoanq.chat.domain.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
    fun findAllByActivityStatus(status: User.ActivityStatus): List<User>
}
