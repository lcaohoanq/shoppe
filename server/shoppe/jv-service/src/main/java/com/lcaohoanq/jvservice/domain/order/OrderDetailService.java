package com.lcaohoanq.jvservice.domain.order;

import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;
import com.lcaohoanq.jvservice.domain.product.Product;
import com.lcaohoanq.jvservice.repository.OrderDetailRepository;
import com.lcaohoanq.jvservice.repository.OrderRepository;
import com.lcaohoanq.jvservice.repository.ProductRepository;
import com.lcaohoanq.jvservice.mapper.OrderMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    
    @Override
    @Transactional
    public OrderDetailResponse create(OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.orderId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find Order with id : "+orderDetailDTO.orderId()));
        Product product = productRepository.findById(orderDetailDTO.koiId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.koiId()));
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .numberOfProducts(orderDetailDTO.numberOfProducts())
                .price(orderDetailDTO.price())
                .totalMoney(orderDetailDTO.totalMoney())
                .build();
        return orderMapper.toOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }

    @Override
    public OrderDetailResponse getById(Long id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(()->new DataNotFoundException("Cannot find OrderDetail with id: "+id));
        return orderMapper.toOrderDetailResponse(orderDetail);
    }

    @Override
    @Transactional
    public OrderDetailResponse update(Long id, OrderDetailDTO orderDetailDTO)
            throws DataNotFoundException {
        //tìm xem order detail có tồn tại ko đã
        OrderDetail existingOrderDetail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Cannot find order detail with id: "+id));
        Order existingOrder = orderRepository.findById(orderDetailDTO.orderId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find order with id: "+id));
        Product existingProduct = productRepository.findById(orderDetailDTO.koiId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find product with id: " + orderDetailDTO.koiId()));
        existingOrderDetail.setPrice(orderDetailDTO.price());
        existingOrderDetail.setNumberOfProducts(orderDetailDTO.numberOfProducts());
        existingOrderDetail.setTotalMoney(orderDetailDTO.totalMoney());
        existingOrderDetail.setOrder(existingOrder);
        existingOrderDetail.setProduct(existingProduct);
        return orderMapper.toOrderDetailResponse(orderDetailRepository.save(existingOrderDetail));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }
}
