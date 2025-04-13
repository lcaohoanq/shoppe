package com.lcaohoanq.authservice.repositories

import com.lcaohoanq.authservice.domains.loginhistory.LoginHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface LoginHistoryRepository : JpaRepository<LoginHistory, Long>{
    fun findTop1ByUserIdOrderByLoginAtDesc(userId: Long): LoginHistory?
    fun existsByUserId(userId: Long): Boolean
}
