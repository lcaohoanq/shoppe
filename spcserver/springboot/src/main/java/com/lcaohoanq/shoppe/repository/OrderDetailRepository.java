package com.lcaohoanq.shoppe.repository;

import com.lcaohoanq.shoppe.domain.order.OrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrderId(Long orderId);

}
