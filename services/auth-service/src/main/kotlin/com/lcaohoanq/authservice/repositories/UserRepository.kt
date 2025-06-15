package com.lcaohoanq.authservice.repositories

import com.lcaohoanq.authservice.domains.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional

interface UserRepository: JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findByUserName(userName: String): Optional<User>
    fun existsByEmail(email: String): Boolean
    fun findByEmailAndHashedPassword(email: String, password: String): User?
    fun findByEmail(email: String): User?
}
