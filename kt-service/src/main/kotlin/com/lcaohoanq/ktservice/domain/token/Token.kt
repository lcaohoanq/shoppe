package com.lcaohoanq.ktservice.domain.token

import BaseEntity
import com.fasterxml.jackson.annotation.JsonProperty
import com.lcaohoanq.ktservice.domain.user.User
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "token")
class Token(

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty("id")
    val id: UUID? = UUID.randomUUID(),
    var token: String,
    var refreshToken : String,
    var tokenType: String,
    var expirationDate: LocalDateTime,
    var refreshExpirationDate: LocalDateTime,
    var isMobile: Boolean,
    var revoked: Boolean,
    var expired: Boolean,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User

): BaseEntity() {
}