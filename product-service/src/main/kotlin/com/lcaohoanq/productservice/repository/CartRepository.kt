package com.lcaohoanq.productservice.repository

import com.lcaohoanq.productservice.domain.cart.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CartRepository : JpaRepository<Cart, Long> {
    fun existsByUserId(userId: Long): Boolean

    fun findByUserId(userId: Long): Optional<Cart>

    @Modifying
    @Query("UPDATE Cart c SET c.totalQuantity = c.totalQuantity + :quantity WHERE c.id = :productId")
    fun increase(
        @Param("productId") cartId: Long,
        @Param("quantity") quantity: Int
    )

    @Modifying
    @Query("UPDATE Cart c SET c.totalQuantity = c.totalQuantity - :quantity WHERE c.id = :productId")
    fun decrease(
        @Param("productId") cartId: Long,
        @Param("quantity") quantity: Int
    )
}
