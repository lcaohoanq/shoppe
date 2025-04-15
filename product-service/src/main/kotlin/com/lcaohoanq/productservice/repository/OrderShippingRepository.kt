package com.lcaohoanq.productservice.repository

import com.lcaohoanq.productservice.domain.logistic.OrderShipping
import org.springframework.data.jpa.repository.JpaRepository

interface OrderShippingRepository : JpaRepository<OrderShipping, Long>
