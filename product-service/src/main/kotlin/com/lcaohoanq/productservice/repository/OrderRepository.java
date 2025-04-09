package com.lcaohoanq.productservice.repository;

import com.lcaohoanq.common.enums.OrderEnum;
import com.lcaohoanq.productservice.domain.order.Order;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserId(Long userId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.userId = :userId")
    Long countOrdersByMemberId(Long userId);

    @Query(
        "SELECT o FROM Order o WHERE o.active = true " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(CONCAT(o.firstName, ' ', o.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(o.address) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(o.note) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(o.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(o.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "ORDER BY o.orderDate DESC"
    )
    Page<Order> findByKeywordAndStatus(@Param("keyword") String keyword, @Param("status") OrderEnum.Status status, Pageable pageable);

    List<Order> findByStatus(OrderEnum.Status status);

    @Query(
        "SELECT o FROM Order o WHERE o.active = true " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:userId IS NULL OR o.userId = :userId) " +
            "ORDER BY o.orderDate DESC"
    )
    Page<Order> findByUserIdAndStatus(Long userId, OrderEnum.Status status, Pageable pageable);

    Page<Order> findOrdersByUserId(Long userId, Pageable pageable);

    @Query(
        "SELECT o FROM Order o WHERE o.active = true " +
            "AND (:keyword IS NULL OR :keyword = '' OR " +
            "LOWER(CONCAT(o.firstName, ' ', o.lastName)) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.address) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.note) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(o.email) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
            "OR LOWER(o.phoneNumber) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "ORDER BY o.orderDate DESC"
    )
    Page<Order> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
