package com.lcaohoanq.shoppe.controllers;

import com.lcaohoanq.shoppe.components.JwtTokenUtils;
import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.dtos.request.OrderDTO;
import com.lcaohoanq.shoppe.dtos.request.UpdateOrderStatusDTO;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiResponse;
import com.lcaohoanq.shoppe.enums.OrderStatus;
import com.lcaohoanq.shoppe.exceptions.InvalidApiPathVariableException;
import com.lcaohoanq.shoppe.exceptions.MalformDataException;
import com.lcaohoanq.shoppe.exceptions.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.Order;
import com.lcaohoanq.shoppe.models.User;
import com.lcaohoanq.shoppe.dtos.responses.base.BaseResponse;
import com.lcaohoanq.shoppe.dtos.responses.OrderResponse;
import com.lcaohoanq.shoppe.dtos.responses.pagination.OrderPaginationResponse;
import com.lcaohoanq.shoppe.services.order.IOrderService;
import com.lcaohoanq.shoppe.services.user.IUserService;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import com.lcaohoanq.shoppe.utils.MessageKey;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController implements DTOConverter {

    private final IOrderService orderService;
    private final LocalizationUtils localizationUtils;
    private final IUserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
        @Valid @RequestBody OrderDTO orderDTO,
        BindingResult result
    ) throws Exception {
        
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<OrderResponse>builder()
                .message("Create order successfully")
                .statusCode(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(orderService.create(user, orderDTO))
                .build());
    }

    @GetMapping("/user/{user_id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER')")
    public ResponseEntity<OrderPaginationResponse> getOrders(
        @PathVariable("user_id") Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<OrderResponse> orders = orderService.findByUserId(userId, pageRequest)
            .map(this::toOrderResponse);
        OrderPaginationResponse response = new OrderPaginationResponse();
        response.setTotalPage(orders.getTotalPages());
        response.setTotalItem(orders.getTotalElements());
        response.setItem(orders.getContent());
        return ResponseEntity.ok(response);
    }

    // this endpoint will search all order of user retrieve from token (some
    // condition)
    @GetMapping("/search-user-orders-by-keyword")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<OrderPaginationResponse> searchUserOrdersByKeyword(
        @RequestParam(defaultValue = "", required = false) String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        PageRequest pageRequest = PageRequest.of(page, limit);
        Page<OrderResponse> orders = orderService.searchUserOrders(keyword, user.getId(),
                                                                   pageRequest)
            .map(this::toOrderResponse);
        OrderPaginationResponse response = new OrderPaginationResponse();
        response.setTotalPage(orders.getTotalPages());
        response.setTotalItem(orders.getTotalElements());
        response.setItem(orders.getContent());
        return ResponseEntity.ok(response);
    }

    // GET http://localhost:8088/api/v1/orders/2
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<?> getOrder(
        @Valid @PathVariable("id") Long orderId) {
        try {
            Order existingOrder = orderService.getById(orderId);
            OrderResponse orderResponse = toOrderResponse(existingOrder);
            return ResponseEntity.ok(orderResponse);
        } catch (Exception e) {
            BaseResponse<Object> response = new BaseResponse<>();
            response.setMessage("Get orders failed");
            response.setReason(e.toString());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF')")
    // PUT http://localhost:8088/api/v1/orders/2
    public ResponseEntity<?> updateOrder(
        @Valid @PathVariable long id,
        @Valid @RequestBody(required = false) OrderDTO orderDTO,
        BindingResult result) {

        if (id <= 0) {
            throw new InvalidApiPathVariableException("Order id must be greater than 0");
        }
        BaseResponse<Object> response = new BaseResponse<>();
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }
        try {
            Order order;
            order = orderService.update(id, orderDTO);
            return ResponseEntity.ok(toOrderResponse(order));
        } catch (MalformDataException e) {
            response.setMessage("Update order failed");
            response.setReason(e.getMessage());
            response.setIsSuccess(false);
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            response.setReason(e.getMessage());
            response.setMessage("Update order failed");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
        // xóa mềm => cập nhật trường active = false
        if (id <= 0) {
            throw new InvalidApiPathVariableException("Order id must be greater than 0");
        }
        try {
            orderService.delete(id);
            String result = localizationUtils.getLocalizedMessage(
                MessageKey.DELETE_ORDER_SUCCESSFULLY, id);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            if (e instanceof DataNotFoundException) {
                throw e;
            }
            BaseResponse response = new BaseResponse();
            response.setReason(e.getMessage());
            response.setMessage("Delete order failed");
            return ResponseEntity.badRequest().body(response);
        }

    }

    @GetMapping("/get-orders-by-keyword-and-status")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<OrderPaginationResponse> getOrdersByKeyword(
        @RequestParam(defaultValue = "", required = false) String keyword,
        @RequestParam(required = false) OrderStatus status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit) {

        PageRequest pageRequest = PageRequest.of(
            page, limit,
            // Sort.by("createdAt").descending()
            Sort.by("id").ascending());

        Page<OrderResponse> orders;
        OrderPaginationResponse response = new OrderPaginationResponse();

        if (String.valueOf(status).equals("ALL")) {
            orders = orderService.getOrderByKeyword(keyword, pageRequest)
                .map(this::toOrderResponse);
        } else {
            orders = orderService
                .getOrdersByKeywordAndStatus(keyword, status, pageRequest)
                .map(this::toOrderResponse);
        }

        response.setItem(orders.getContent());
        response.setTotalItem(orders.getTotalElements());
        response.setTotalPage(orders.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{user_id}/get-active-sorted-orders")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<OrderPaginationResponse> getSortedOrder(
        @PathVariable("user_id") Long userId,
        @RequestParam("keyword") OrderStatus keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit) {

        PageRequest pageRequest = PageRequest.of(
            page, limit,
            // Sort.by("createdAt").descending()
            Sort.by("id").ascending());

        Page<OrderResponse> orders;
        OrderPaginationResponse response = new OrderPaginationResponse();

        if (String.valueOf(keyword).equals("ALL")) {
            orders = orderService.findByUserId(userId, pageRequest).map(this::toOrderResponse);

        } else {
            orders = orderService
                .getOrdersByStatus(userId, keyword, pageRequest)
                .map(this::toOrderResponse);
        }

        response.setItem(orders.getContent());
        response.setTotalItem(orders.getTotalElements());
        response.setTotalPage(orders.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/confirm-delivery")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    public ResponseEntity<?> confirmDelivery(@Valid @PathVariable long id,
                                             @Valid @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        try {
            Order updatedOrder = orderService.updateOrderStatus(id,
                                                                OrderStatus.valueOf(
                                                                    updateOrderStatusDTO.getStatus()));
            return ResponseEntity.ok(toOrderResponse(updatedOrder));
        } catch (Exception e) {
            BaseResponse response = new BaseResponse();
            response.setReason(e.getMessage());
            response.setMessage("Failed to confirm delivery");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
