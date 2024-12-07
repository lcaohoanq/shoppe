package com.lcaohoanq.shoppe.services.orderdetail;

import com.lcaohoanq.shoppe.dtos.request.OrderDetailDTO;
import com.lcaohoanq.shoppe.dtos.responses.OrderDetailResponse;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.OrderDetail;
import java.util.List;

public interface IOrderDetailService {
    OrderDetailResponse create(OrderDetailDTO newOrderDetail) throws Exception;
    OrderDetailResponse getById(Long id) throws DataNotFoundException;
    OrderDetailResponse update(Long id, OrderDetailDTO newOrderDetailData) throws DataNotFoundException;
    void delete(Long id);
    List<OrderDetail> findByOrderId(Long orderId);
}
