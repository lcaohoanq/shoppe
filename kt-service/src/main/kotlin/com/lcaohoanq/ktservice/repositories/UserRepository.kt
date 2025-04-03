package com.lcaohoanq.ktservice.repositories

import com.lcaohoanq.ktservice.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

interface UserRepository: JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    fun existsByEmail(email: String): Boolean
    fun findByEmailAndHashedPassword(email: String, password: String): User?
    fun findByEmail(email: String): User?
}