package com.lcaohoanq.productservice.repository

import com.lcaohoanq.common.enums.OrderEnum
import com.lcaohoanq.productservice.domain.order.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByUserId(userId: Long): List<Order>

    @Query("SELECT COUNT(o) FROM Order o WHERE o.userId = :userId")
    fun countOrdersByMemberId(userId: Long): Long

    @Query(
        ("SELECT o FROM Order o WHERE o.active = true " +
                "AND (:status IS NULL OR o.status = :status) " +
                "AND (:keyword IS NULL OR :keyword = '' OR " +
                "LOWER(CONCAT(o.firstName, ' ', o.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                "LOWER(o.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                "LOWER(o.note) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                "LOWER(o.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                "LOWER(o.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
                "ORDER BY o.orderDate DESC")
    )
    fun findByKeywordAndStatus(
        @Param("keyword") keyword: String,
        @Param("status") status: OrderEnum.Status,
        pageable: Pageable
    ): Page<Order>

    fun findByStatus(status: OrderEnum.Status): List<Order>

    @Query(
        ("SELECT o FROM Order o WHERE o.active = true " +
                "AND (:status IS NULL OR o.status = :status) " +
                "AND (:userId IS NULL OR o.userId = :userId) " +
                "ORDER BY o.orderDate DESC")
    )
    fun findByUserIdAndStatus(
        userId: Long,
        status: OrderEnum.Status,
        pageable: Pageable
    ): Page<Order>

    fun findOrdersByUserId(userId: Long, pageable: Pageable): Page<Order>

    @Query(
        ("SELECT o FROM Order o WHERE o.active = true " +
                "AND (:keyword IS NULL OR :keyword = '' OR " +
                "LOWER(CONCAT(o.firstName, ' ', o.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(o.address) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(o.note) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "OR LOWER(o.email) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
                "OR LOWER(o.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                "ORDER BY o.orderDate DESC")
    )
    fun findByKeyword(@Param("keyword") keyword: String, pageable: Pageable): Page<Order>
}
