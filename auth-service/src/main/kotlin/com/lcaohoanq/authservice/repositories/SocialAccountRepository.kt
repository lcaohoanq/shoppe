package com.lcaohoanq.authservice.repositories

import com.lcaohoanq.authservice.domains.user.SocialAccount
import org.springframework.data.jpa.repository.JpaRepository

interface SocialAccountRepository : JpaRepository<SocialAccount, Long>
