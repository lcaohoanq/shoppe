package com.lcaohoanq.authservice.domains.otp

import BaseEntity
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "otps")
data class Otp(
    @Id
    @SequenceGenerator(
        name = "otps_seq",
        sequenceName = "otps_id_seq",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "otps_seq")
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty("id")
    val id: Long? = null,

    @Column(name = "email")
    val email: String,

    @Column(name = "otp")
    val otp: String,

    @JsonProperty("expired_at")
    @Column(name = "expired_at", nullable = false)
    val expiredAt: java.time.LocalDateTime, // in milliseconds

    @JsonProperty("is_used")
    @Column(name = "is_used")
    var isUsed: Boolean = false,

    @JsonProperty("is_expired")
    @Column(name = "is_expired")
    var isExpired: Boolean = false
) : BaseEntity()
