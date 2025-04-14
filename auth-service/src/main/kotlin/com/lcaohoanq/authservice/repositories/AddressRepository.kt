package com.lcaohoanq.authservice.repositories

import com.lcaohoanq.authservice.domains.user.Address
import com.lcaohoanq.authservice.domains.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository: JpaRepository<Address, Long> {
    fun user(user: User): MutableList<Address>
}