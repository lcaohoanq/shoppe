package com.lcaohoanq.authservice.domains.user

import BaseEntity
import com.fasterxml.jackson.annotation.JsonProperty
import com.lcaohoanq.common.enums.UserEnum
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(

    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty("id")
    val id: Long? = null,

    @Column(name = "email", unique = true)
    val email: String,

    @Column(name = "username")
    val userName: String = "",

    @Column(name = "password")
    val hashedPassword: String,

    val role: UserEnum.Role? = UserEnum.Role.MEMBER,
    val phone: String = "",
    val name: String = "New User",

    @Enumerated(EnumType.ORDINAL)
    val gender: UserEnum.Gender? = UserEnum.Gender.FEMALE,
    val isActive: Boolean = true,

    @Enumerated(EnumType.ORDINAL)
    var status: UserEnum.Status? = UserEnum.Status.UNVERIFIED,

    val dateOfBirth: String? = "",

    val avatar: String = "",

    val cartId: String,

    val walletId: String,

    val preferredLanguage: String? = "vi",

    val preferredCurrency: String? = "VND",

    var lastLoginTimeStamp: LocalDateTime? = LocalDateTime.now(),

    ) : BaseEntity(), UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority("ROLE_${role?.name}"))
    }

    override fun getPassword(): String = hashedPassword

    override fun getUsername(): String = email

    // Implement other UserDetails methods
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}