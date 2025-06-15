package com.lcaohoanq.productservice.repository

import com.lcaohoanq.productservice.domain.order.OrderDetail
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDetailRepository : JpaRepository<OrderDetail, Long> {
    fun findByOrderId(orderId: Long): List<OrderDetail>
}
