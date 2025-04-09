package org.langchain4j.apithrottling.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "api_quotas")
class ApiQuota(
    @Id
    @SequenceGenerator(name = "api_quotas_seq", sequenceName = "api_quotas_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_quotas_seq")
    @Column(name = "id", unique = true, nullable = false)
    val id: Long? = null,

    @JoinColumn(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "api_endpoint", nullable = false)
    val apiEndpoint: String,

    @Column(name = "request_count", nullable = false)
    var requestCount: Int = 0,

    @Column(name = "max_requests", nullable = false)
    val maxRequests: Int,

    @Column(name = "reset_time", nullable = false)
    var resetTime: LocalDateTime
)
