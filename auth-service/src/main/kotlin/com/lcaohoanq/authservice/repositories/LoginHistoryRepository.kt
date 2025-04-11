package com.lcaohoanq.authservice.repositories

import com.lcaohoanq.authservice.domains.loginhistory.LoginHistory
import org.springframework.data.jpa.repository.JpaRepository

interface LoginHistoryRepository : JpaRepository<LoginHistory, Long>
