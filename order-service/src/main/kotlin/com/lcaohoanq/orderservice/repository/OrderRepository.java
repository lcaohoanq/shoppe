package com.lcaohoanq.orderservice.repository;

import com.lcaohoanq.jvservice.domain.order.Order;
import com.lcaohoanq.jvservice.domain.order.Order.OrderStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUserId(Long userId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId")
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
    Page<Order> findByKeywordAndStatus(@Param("keyword") String keyword, @Param("status") OrderStatus status, Pageable pageable);

    List<Order> findByStatus(OrderStatus status);

    @Query(
        "SELECT o FROM Order o WHERE o.active = true " +
            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:userId IS NULL OR o.user.id = :userId) " +
            "ORDER BY o.orderDate DESC"
    )
    Page<Order> findByUserIdAndStatus(Long userId, OrderStatus status, Pageable pageable);

    Page<Order> findOrdersByUserId(Long userId, Pageable pageable);

    @Query(
        "SELECT o FROM Order o LEFT JOIN OrderDetail od on o.id = od.order.id " +
            "WHERE o.user.id = :userId " +
            "OR CAST(o.status as string) LIKE CONCAT('%', :keyword, '%') " + //search by order status
            "OR od.product.name LIKE CONCAT('%', :keyword, '%') " + //search by product name
//                "OR CAST (o.id as string) LIKE LOWER(CONCAT('%', :keyword, '%')) " + //search by any order id
            "ORDER BY o.orderDate DESC"
    )
    Page<Order> searchUserOrdersByKeyword(@Param("keyword") String keyword, @Param("userId") Long userId, Pageable pageable);

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
