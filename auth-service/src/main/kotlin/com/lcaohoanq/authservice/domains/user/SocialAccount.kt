package com.lcaohoanq.authservice.domains.user

import BaseEntity
import com.fasterxml.jackson.annotation.JsonProperty
import com.lcaohoanq.common.enums.ProviderName
import jakarta.persistence.*

@Entity
@Table(name = "social_accounts")
class SocialAccount : BaseEntity() {
    @Id
    @SequenceGenerator(
        name = "social_accounts_seq",
        sequenceName = "social_accounts_id_seq",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "social_accounts_seq")
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty("id")
    private var id: Long? = null

    @Column(name = "email", length = 150)
    private var email: String? = null

    @Column(name = "name")
    private var name: String? = null

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "provider_name")
    private var providerName: ProviderName? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    private var user: User? = null
}