package com.lcaohoanq.authservice.repositories

import com.lcaohoanq.authservice.domains.user.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository: JpaRepository<Address, Long> {
}