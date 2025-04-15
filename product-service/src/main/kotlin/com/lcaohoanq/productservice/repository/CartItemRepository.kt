package com.lcaohoanq.productservice.repository

import com.lcaohoanq.productservice.domain.cart.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface CartItemRepository : JpaRepository<CartItem, Long> {
    fun findByCartId(cartId: Long): List<CartItem>

    fun findByCartIdAndProductId(cartId: Long, productId: Long): Optional<CartItem>
}
