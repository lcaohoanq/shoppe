package com.lcaohoanq.productservice.repository

import com.lcaohoanq.productservice.domain.product.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductRepository :
    JpaRepository<Product, Long> {
    fun existsByName(name: String): Boolean

    @Modifying
    @Query("UPDATE Product p SET p.isActive = :isActive WHERE p.id = :id")
    fun updateProductIsActive(id: Long, isActive: Boolean)

    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity + :quantity WHERE p.id = :productId")
    fun increaseProductQuantity(
        @Param("productId") productId: Long,
        @Param("quantity") quantity: Int
    )

    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity - :quantity WHERE p.id = :productId")
    fun decreaseProductQuantity(
        @Param("productId") productId: Long,
        @Param("quantity") quantity: Int
    )

    fun findByCategoryId(categoryId: Long, pageable: Pageable): Page<Product>
}
