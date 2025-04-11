package com.lcaohoanq.authservice.domains.user

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
@Table(name = "user_addresses")
class Address(
    @Id
    @SequenceGenerator(
        name = "user_addresses_seq",
        sequenceName = "user_addresses_id_seq",
        allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_addresses_seq")
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty("id")
    val id: Long? = null,
    val nameOfUser: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val isDefault: Boolean = false,
    val userId: Long,

) {
}