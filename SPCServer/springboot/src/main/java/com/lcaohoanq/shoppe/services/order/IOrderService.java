package com.lcaohoanq.shoppe.services.order;

import com.lcaohoanq.shoppe.dtos.OrderDTO;
import com.lcaohoanq.shoppe.enums.OrderStatus;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.Order;
import com.lcaohoanq.shoppe.models.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order createOrder(Order order) throws Exception;
    Order getOrder(Long id);
    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
    void deleteOrder(Long id);
    Page<Order> searchUserOrders(String keyword, long userId, Pageable pageable);
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Page<Order> getOrderByKeyword(String keyword, Pageable pageable);
    Page<Order> getOrdersByKeywordAndStatus(String keyword, OrderStatus status, Pageable pageable);
    Order updateOrderStatus(Long id, OrderStatus orderStatus) throws DataNotFoundException;
    void updateOrderStatusAndShipDate(Long id);
    List<Order> getOrdersByStatus(OrderStatus orderStatus) throws DataNotFoundException;
    Page<Order> getOrdersByStatus(Long userId, OrderStatus keyword, Pageable pageable);
}
