package com.lcaohoanq.ktservice.entities

import BaseEntity
import com.fasterxml.jackson.annotation.JsonProperty
import com.lcaohoanq.common.enums.UserStatus
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty("id")
    val id: Long? = null,

    val address: String = "",
    val avatar: String = "",

    @Column(name = "email", unique = true)
    val email: String,

    @Column(name = "username")
    val userName: String = "",

    @Column(name = "password")
    val hashedPassword: String,

    val role: Role? = Role.MEMBER,
    val phone: String = "",
    val name: String = "New User",
    val isActive: Boolean = true,
    val status: UserStatus = UserStatus.UNVERIFIED,

    ) : BaseEntity(), UserDetails {

    enum class Role {
        MEMBER, STAFF, ADMIN
    }

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