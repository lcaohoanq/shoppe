package com.lcaohoanq.authservice.domains.loginhistory

import BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import com.lcaohoanq.authservice.domains.user.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "login_history")
class LoginHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    val user: User,

    @Column(nullable = false)
    val loginAt: LocalDateTime = LocalDateTime.now(),

    @Column(length = 100)
    val ipAddress: String? = null,

    @Column(length = 255)
    val userAgent: String? = null,

    @Column(length = 100)
    val location: String? = null,

    @Column(nullable = false)
    val success: Boolean = true
) : BaseEntity()
