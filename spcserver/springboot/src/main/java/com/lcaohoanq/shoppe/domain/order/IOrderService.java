package com.lcaohoanq.shoppe.domain.order;

import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.domain.order.Order.OrderStatus;
import com.lcaohoanq.shoppe.domain.user.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    OrderResponse create(User user, OrderDTO orderDTO) throws Exception;
    OrderResponse getById(Long id);
    Order update(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void delete(Long id);
    PageResponse<OrderResponse> searchUserOrders(String keyword, long userId, Pageable pageable);
    PageResponse<OrderResponse> findByUserId(Long userId, Pageable pageable);
    PageResponse<OrderResponse> getOrderByKeyword(String keyword, Pageable pageable);
    PageResponse<OrderResponse> getOrdersByKeywordAndStatus(String keyword, OrderStatus status, Pageable pageable);
    Order updateOrderStatus(Long id, OrderStatus orderStatus) throws DataNotFoundException;
    void updateOrderStatusAndShipDate(Long id);
    List<Order> getOrdersByStatus(OrderStatus orderStatus) throws DataNotFoundException;
    PageResponse<OrderResponse> getOrdersByStatus(Long userId, OrderStatus keyword, Pageable pageable);
}
