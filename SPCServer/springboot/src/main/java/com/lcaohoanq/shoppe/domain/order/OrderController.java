package com.lcaohoanq.shoppe.domain.order;

import com.lcaohoanq.shoppe.api.ApiResponse;
import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.component.JwtTokenUtils;
import com.lcaohoanq.shoppe.component.LocalizationUtils;
import com.lcaohoanq.shoppe.constant.MessageKey;
import com.lcaohoanq.shoppe.domain.user.IUserService;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.enums.OrderStatus;
import com.lcaohoanq.shoppe.exception.InvalidApiPathVariableException;
import com.lcaohoanq.shoppe.exception.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.util.DTOConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<PageResponse<OrderResponse>> getOrders(
        @PathVariable("user_id") Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(
            orderService.findByUserId(
                userId, PageRequest.of(page, limit)));
    }

    // this endpoint will search all order of user retrieve from token (some
    // condition)
    @GetMapping("/search-user-orders-by-keyword")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<PageResponse<OrderResponse>> searchUserOrdersByKeyword(
        @RequestParam(defaultValue = "", required = false) String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(
            orderService.searchUserOrders(keyword,
                                          user.getId(),
                                          PageRequest.of(page, limit)));
    }

    // GET http://localhost:8088/api/v1/orders/2
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(
        @Valid @PathVariable("id") Long orderId
    ) {
        return ResponseEntity.ok(
            ApiResponse.<OrderResponse>builder()
                .message("Get order successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(orderService.getById(orderId))
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF')")
    // PUT http://localhost:8088/api/v1/orders/2
    public ResponseEntity<?> updateOrder(
        @Valid @PathVariable long id,
        @Valid @RequestBody(required = false) OrderDTO orderDTO,
        BindingResult result
    ) {

        if (id <= 0) {
            throw new InvalidApiPathVariableException("Order id must be greater than 0");
        }
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        return ResponseEntity.ok(toOrderResponse(orderService.update(id, orderDTO)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
        // xóa mềm => cập nhật trường active = false
        if (id <= 0) {
            throw new InvalidApiPathVariableException("Order id must be greater than 0");
        }
        orderService.delete(id);
        String result = localizationUtils.getLocalizedMessage(
            MessageKey.DELETE_ORDER_SUCCESSFULLY, id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/get-orders-by-keyword-and-status")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_STAFF')")
    public ResponseEntity<PageResponse<OrderResponse>> getOrdersByKeyword(
        @RequestParam(defaultValue = "", required = false) String keyword,
        @RequestParam(required = false) OrderStatus status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(
            orderService
                .getOrdersByKeywordAndStatus(keyword, status, PageRequest.of(
                    page, limit,
                    // Sort.by("createdAt").descending()
                    Sort.by("id").ascending())));
    }

    @GetMapping("/user/{user_id}/get-active-sorted-orders")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<PageResponse<OrderResponse>> getSortedOrder(
        @PathVariable("user_id") Long userId,
        @RequestParam("keyword") OrderStatus keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(
            orderService.getOrdersByStatus(userId, keyword, PageRequest.of(
                page, limit,
                // Sort.by("createdAt").descending()
                Sort.by("id").ascending())));
    }

    @PutMapping("/{id}/confirm-delivery")
    @PreAuthorize("hasAnyRole('ROLE_MEMBER')")
    public ResponseEntity<?> confirmDelivery(
        @Valid @PathVariable long id,
        @Valid @RequestBody UpdateOrderStatusDTO updateOrderStatusDTO
    ) {
        Order updatedOrder = orderService.updateOrderStatus(
            id,
            OrderStatus.valueOf(updateOrderStatusDTO.status()));
        
        return ResponseEntity.ok(toOrderResponse(updatedOrder));
    }

}
