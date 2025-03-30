package com.lcaohoanq.jvservice.domain.order;

import com.lcaohoanq.jvservice.component.LocalizationUtils;
import com.lcaohoanq.jvservice.api.ApiResponse;
import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;
import com.lcaohoanq.jvservice.mapper.OrderMapper;
import com.lcaohoanq.jvservice.constant.MessageKey;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("${api.prefix}/orders-details")
@RequiredArgsConstructor
public class OrderDetailController {

    private final IOrderDetailService orderDetailService;
    private final LocalizationUtils localizationUtils;
    private final OrderMapper orderMapper;

    //Thêm mới 1 order detail
    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_SHOP_OWNER', 'ROLE_STAFF')")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> createOrderDetail(
        @Valid @RequestBody OrderDetailDTO orderDetailDTO) throws Exception {
        return ResponseEntity.ok().body(
            ApiResponse.<OrderDetailResponse>builder()
                .message("Create order detail successfully")
                .isSuccess(true)
                .statusCode(HttpStatus.CREATED.value())
                .data(orderDetailService.create(orderDetailDTO))
                .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getById(
        @Valid @PathVariable("id") Long id) throws DataNotFoundException {
        return ResponseEntity.ok().body(
            ApiResponse.<OrderDetailResponse>builder()
                .message("Order detail found successfully")
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .data(orderDetailService.getById(id))
                .build()
        );
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<ApiResponse<List<OrderDetailResponse>>> getOrderDetails(
        @Valid @PathVariable("orderId") Long orderId
    ) {
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderDetails
            .stream()
            .map(orderMapper::toOrderDetailResponse)
            .toList();

        return ResponseEntity.ok(
            ApiResponse.<List<OrderDetailResponse>>builder()
                .message("Get order details successfully")
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .data(orderDetailResponses)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_SHOP_OWNER', 'ROLE_STAFF')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<ApiResponse<OrderDetailResponse>> updateOrderDetail(
        @Valid @PathVariable("id") Long id,
        @RequestBody OrderDetailDTO orderDetailDTO
    ) {
        return ResponseEntity.ok().body(
            ApiResponse.<OrderDetailResponse>builder()
                .message("Update order detail successfully")
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .data(orderDetailService.update(id, orderDetailDTO))
                .build());

    }

    @DeleteMapping("/{id}")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")})
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_SHOP_OWNER', 'ROLE_STAFF')")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> deleteOrderDetail(
        @Valid @PathVariable("id") Long id) {
        orderDetailService.delete(id);
        return ResponseEntity.ok().body(
            ApiResponse.<OrderDetailResponse>builder()
                .message(localizationUtils.getLocalizedMessage(
                    MessageKey.DELETE_ORDER_DETAIL_SUCCESSFULLY))
                .statusCode(HttpStatus.OK.value())
                .build());
    }

}
