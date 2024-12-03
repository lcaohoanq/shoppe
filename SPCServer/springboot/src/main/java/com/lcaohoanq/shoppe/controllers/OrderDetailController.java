package com.lcaohoanq.shoppe.controllers;

import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.dtos.OrderDetailDTO;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.OrderDetail;
import com.lcaohoanq.shoppe.dtos.responses.OrderDetailResponse;
import com.lcaohoanq.shoppe.services.orderdetail.IOrderDetailService;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import com.lcaohoanq.shoppe.utils.MessageKey;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/orders_details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final IOrderDetailService orderDetailService;
    private final LocalizationUtils localizationUtils;

    //Thêm mới 1 order detail
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STORE_OWNER', 'ROLE_STAFF')")
    public ResponseEntity<?> createOrderDetail(
        @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
            return ResponseEntity.ok().body(DTOConverter.fromOrderDetail(newOrderDetail));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_STORE_OWNER')")
    public ResponseEntity<?> getOrderDetail(
        @Valid @PathVariable("id") Long id) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailService.getOrderDetail(id);
        return ResponseEntity.ok().body(DTOConverter.fromOrderDetail(orderDetail));
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_STORE_OWNER')")
    public ResponseEntity<?> getOrderDetails(
        @Valid @PathVariable("orderId") Long orderId
    ) {
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails
            .stream()
            .map(DTOConverter::fromOrderDetail)
            .toList();
        return ResponseEntity.ok(orderDetailResponses);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STORE_OWNER', 'ROLE_STAFF')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> updateOrderDetail(
        @Valid @PathVariable("id") Long id,
        @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, orderDetailDTO);
            return ResponseEntity.ok().body(orderDetail);
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STORE_OWNER', 'ROLE_STAFF')")
    public ResponseEntity<?> deleteOrderDetail(
        @Valid @PathVariable("id") Long id) {
        orderDetailService.deleteById(id);
        return ResponseEntity.ok()
            .body(localizationUtils
                      .getLocalizedMessage(MessageKey.DELETE_ORDER_DETAIL_SUCCESSFULLY));
    }

}
