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
    val userId: Long,
    val apiEndpoint: String,
    var requestCount: Int = 0,
    val maxRequests: Int,
    var resetTime: LocalDateTime
)
