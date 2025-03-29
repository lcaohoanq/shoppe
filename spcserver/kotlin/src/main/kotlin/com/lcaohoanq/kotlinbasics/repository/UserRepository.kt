package com.lcaohoanq.kotlinbasics.repository

import com.lcaohoanq.kotlinbasics.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserRepository: JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    fun existsByEmail(email: String): Boolean
    fun findByEmailAndHashedPassword(email: String, password: String): User?
    fun findByEmail(email: String): User?
}