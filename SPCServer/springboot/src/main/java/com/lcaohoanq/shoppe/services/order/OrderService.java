package com.lcaohoanq.shoppe.services.order;

import com.lcaohoanq.shoppe.dtos.request.CartItemDTO;
import com.lcaohoanq.shoppe.dtos.request.OrderDTO;
import com.lcaohoanq.shoppe.dtos.request.OrderDetailDTO;
import com.lcaohoanq.shoppe.dtos.request.OrderWithDetailsDTO;
import com.lcaohoanq.shoppe.enums.OrderStatus;
import com.lcaohoanq.shoppe.exceptions.MalformDataException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.Order;
import com.lcaohoanq.shoppe.models.OrderDetail;
import com.lcaohoanq.shoppe.models.Product;
import com.lcaohoanq.shoppe.models.User;
import com.lcaohoanq.shoppe.repositories.OrderDetailRepository;
import com.lcaohoanq.shoppe.repositories.OrderRepository;
import com.lcaohoanq.shoppe.repositories.ProductRepository;
import com.lcaohoanq.shoppe.repositories.UserRepository;
import com.lcaohoanq.shoppe.services.orderdetail.IOrderDetailService;
import com.lcaohoanq.shoppe.services.user.IUserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;
    private final IOrderMailService orderMailService;
    private final IOrderDetailService orderDetailService;
    private final IUserService userService;

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        // tìm xem user'id có tồn tại ko
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + orderDTO.getUserId()));
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
            Long koiId = cartItemDTO.koiId();
            int quantity = cartItemDTO.quantity();

            // Tìm thông tin sản phẩm t cơ sở dữ liệu (hoặc sử dụng cache nếu cần)
            Product product = productRepository.findById(koiId)
                    .orElseThrow(() -> new DataNotFoundException("Koi not found with id: " + koiId));

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

        return order;
    }

    @Override
    public Order createOrder(Order order) throws Exception {
        return orderRepository.save(order);
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
        List<OrderDetail> savedOrderDetails = orderDetailRepository.saveAll(order.getOrderDetails());

        // Set the updated order details for the order
        savedOrder.setOrderDetails(savedOrderDetails);

        return savedOrder;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: " + id));

        if(existingOrder.getPhoneNumber() == null || existingOrder.getPhoneNumber().isEmpty())
            throw new MalformDataException("Order phoneNumber number is required when updating order");

        if(existingOrder.getAddress() == null || existingOrder.getAddress().isEmpty())
            throw new MalformDataException("Order address is required when updating order");

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
    public void deleteOrder(Long id) {
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
    public Page<Order> getOrdersByStatus(Long userId, OrderStatus keyword, Pageable pageable) {
        return orderRepository.findByUserIdAndStatus(userId, keyword, pageable);
    }

    @Override
    public Page<Order> searchUserOrders(String keyword, long userId, Pageable pageable) {
        return orderRepository.searchUserOrdersByKeyword(keyword, userId, pageable);
    }

    @Override
    public Page<Order> findByUserId(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id: " + userId));
        return orderRepository.findOrdersByUserId(userId, pageable);
    }

    @Override
    public Page<Order> getOrderByKeyword(String keyword, Pageable pageable) {
        return orderRepository.findByKeyword(keyword, pageable);
    }

    @Override
    public Page<Order> getOrdersByKeywordAndStatus(String keyword, OrderStatus status, Pageable pageable) {
        return orderRepository.findByKeywordAndStatus(keyword, status, pageable);
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
