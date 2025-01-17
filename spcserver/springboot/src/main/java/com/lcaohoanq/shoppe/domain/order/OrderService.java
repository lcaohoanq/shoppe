package com.lcaohoanq.shoppe.domain.order;

import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.domain.cart.CartItemDTO;
import com.lcaohoanq.shoppe.enums.OrderStatus;
import com.lcaohoanq.shoppe.exception.MalformDataException;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.domain.product.Product;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.repository.OrderDetailRepository;
import com.lcaohoanq.shoppe.repository.OrderRepository;
import com.lcaohoanq.shoppe.repository.ProductRepository;
import com.lcaohoanq.shoppe.repository.UserRepository;
import com.lcaohoanq.shoppe.mapper.OrderMapper;
import com.lcaohoanq.shoppe.util.PaginationConverter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService, PaginationConverter {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse create(User user, OrderDTO orderDTO) throws Exception {
        // tìm xem user'id có tồn tại ko
        user = userRepository
            .findById(user.getId())
            .orElseThrow(() -> new DataNotFoundException("Cannot find user with provided id"));

        // convert orderDTO => Order
        // dùng thư viện Model Mapper
        // Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
            .addMappings(mapper -> mapper.skip(Order::setId));

        // Cập nhật các trường của đơn hàng từ orderDTO
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());// lấy thời điểm hiện tại
        order.setStatus(OrderStatus.PENDING);

        // Kiểm tra shipping date phải >= ngày hôm nay
        LocalDate shippingDate = orderDTO.getShippingDate() == null
            ? LocalDate.now()
            : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new MalformDataException("Shipping Date must be at least today !");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);// đoạn này nên set sẵn trong sql
        // EAV-Entity-Attribute-Value model
        order.setTotalMoney(orderDTO.getTotalMoney());
        // orderRepository.save(order);
        // Tạo danh sách các đối tượng OrderDetail từ cartItems
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDTO cartItemDTO : orderDTO.getCartItems()) {
            // Tạo một đối tượng OrderDetail từ CartItemDTO
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            // Lấy thông tin sản phẩm từ cartItemDTO
            Long id = cartItemDTO.productId();
            int quantity = cartItemDTO.quantity();

            // Tìm thông tin sản phẩm t cơ sở dữ liệu (hoặc sử dụng cache nếu cần)
            Product product = productRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Product not found with id: " + id));

            // Đặt thông tin cho OrderDetail
            orderDetail.setProduct(product);
            orderDetail.setNumberOfProducts(quantity);
            // Các trường khác của OrderDetail nếu cần
            orderDetail.setPrice((float) product.getPrice());
            orderDetail.setTotalMoney((float) product.getPrice() * quantity); // Tính tổng tiền

            // Thêm OrderDetail vào danh sách
            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);

        // Lưu danh sách OrderDetail vào cơ sở dữ liệu
        // Save the order (cascade will handle saving orderDetails)
        orderRepository.save(order);

        log.info("Order created with ID: {}", order.getId());
        log.info("Number of OrderDetails: {}", order.getOrderDetails().size());

        return orderMapper.toOrderResponse(order);
    }

    @Transactional
    public Order updateOrderWithDetails(OrderWithDetailsDTO orderWithDetailsDTO) {
        modelMapper.typeMap(OrderWithDetailsDTO.class, Order.class)
            .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderWithDetailsDTO, order);
        Order savedOrder = orderRepository.save(order);

        // Set the order for each order detail
        for (OrderDetailDTO orderDetailDTO : orderWithDetailsDTO.orderDetailDTOS()) {
            // orderDetail.setOrder(OrderDetail);
        }

        // Save or update the order details
        List<OrderDetail> savedOrderDetails = orderDetailRepository.saveAll(
            order.getOrderDetails());

        // Set the updated order details for the order
        savedOrder.setOrderDetails(savedOrderDetails);

        return savedOrder;
    }

    @Override
    public OrderResponse getById(Long id) {
        return orderMapper.toOrderResponse(
            orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id)));
    }

    @Override
    @Transactional
    public Order update(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order existingOrder = orderRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        if (existingOrder.getPhoneNumber() == null || existingOrder.getPhoneNumber().isEmpty()) {
            throw new MalformDataException(
                "Order phoneNumber number is required when updating order");
        }

        if (existingOrder.getAddress() == null || existingOrder.getAddress().isEmpty()) {
            throw new MalformDataException("Order address is required when updating order");
        }

        // Update only the fields that are allowed to be changed
        existingOrder.setFirstName(orderDTO.getFirstName());
        existingOrder.setLastName(orderDTO.getLastName());
        existingOrder.setPhoneNumber(orderDTO.getPhoneNumber());
        existingOrder.setAddress(orderDTO.getAddress());
        existingOrder.setShippingMethod(orderDTO.getShippingMethod());
        existingOrder.setShippingAddress(orderDTO.getShippingAddress());
        existingOrder.setPaymentMethod(orderDTO.getPaymentMethod());
        existingOrder.setTotalMoney(orderDTO.getTotalMoney());
        existingOrder.setNote(orderDTO.getNote());

        return orderRepository.save(existingOrder);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Order order = orderRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        // no hard-delete, => please soft-delete
        order.setActive(false);
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void updateOrderStatusAndShipDate(Long id) {
        Order order = orderRepository
            .findById(id)
            .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        // set order status to CANCELLED
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus orderStatus) throws DataNotFoundException {
        return orderRepository.findByStatus(orderStatus);
    }

    @Override
    public PageResponse<OrderResponse> getOrdersByStatus(Long userId, OrderStatus keyword,
                                                         Pageable pageable) {

        if (String.valueOf(keyword).equals("ALL")) {
            return findByUserId(userId, pageable);
        }

        return mapPageResponse(
            orderRepository.findByUserIdAndStatus(userId, keyword, pageable),
            pageable,
            orderMapper::toOrderResponse,
            "Get orders by status successfully"
        );
    }

    @Override
    public PageResponse<OrderResponse> searchUserOrders(String keyword, long userId,
                                                        Pageable pageable) {
        return mapPageResponse(
            orderRepository.searchUserOrdersByKeyword(keyword, userId, pageable),
            pageable,
            orderMapper::toOrderResponse,
            "Search user orders successfully"
        );
    }

    @Override
    public PageResponse<OrderResponse> findByUserId(Long userId, Pageable pageable) {
        userRepository.findById(userId)
            .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + userId));
        return mapPageResponse(
            orderRepository.findOrdersByUserId(userId, pageable),
            pageable,
            orderMapper::toOrderResponse,
            "Get orders by user id successfully"
        );
    }

    @Override
    public PageResponse<OrderResponse> getOrderByKeyword(String keyword, Pageable pageable) {
        return mapPageResponse(
            orderRepository.findByKeyword(keyword, pageable),
            pageable,
            orderMapper::toOrderResponse,
            "Get orders by keyword successfully"
        );
    }

    @Override
    public PageResponse<OrderResponse> getOrdersByKeywordAndStatus(String keyword,
                                                                   OrderStatus status,
                                                                   Pageable pageable) {

        if (String.valueOf(status).equals("ALL")) {
            return getOrderByKeyword(keyword, pageable);
        }

        return mapPageResponse(
            orderRepository.findByKeywordAndStatus(keyword, status, pageable),
            pageable,
            orderMapper::toOrderResponse,
            "Get orders by keyword and status successfully"
        );
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long id, OrderStatus orderStatus) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
        order.setStatus(orderStatus);
        return orderRepository.save(order);
    }
}
