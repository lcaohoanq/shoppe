package com.lcaohoanq.jvservice.domain.order;

import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;
import java.util.List;

public interface IOrderDetailService {
    OrderDetailResponse create(OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetailResponse getById(Long id) throws DataNotFoundException;
    OrderDetailResponse update(Long id, OrderDetailDTO newOrderDetailData) throws DataNotFoundException;
    void delete(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
}
