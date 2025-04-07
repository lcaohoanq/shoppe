package com.lcaohoanq.orderservice.repository;

import com.lcaohoanq.jvservice.domain.order.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrderId(Long orderId);

}
