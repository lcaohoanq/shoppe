package com.lcaohoanq.shoppe.domain.cart;

import com.lcaohoanq.shoppe.api.ApiResponse;
import com.lcaohoanq.shoppe.domain.user.IUserService;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.exception.MethodArgumentNotValidException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/carts")
@Slf4j
@RequiredArgsConstructor
public class CartController {

    private final ICartItemService cartItemService;
    private final IUserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<CartResponse>> getAll(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(
            ApiResponse.<CartResponse>builder()
                .data(null)
                .build()
        );
    }

    @PostMapping("/item")
    @PreAuthorize("hasRole('ROLE_MEMBER')")
    public ResponseEntity<ApiResponse<CartItemResponse>> addItem(
        @Valid @RequestBody CartItemDTO cartItemDTO,
        BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());

        return ResponseEntity.ok(
            ApiResponse.<CartItemResponse>builder()
                .message("Add item to cart successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(cartItemService.createCartItem(user.getId(),cartItemDTO))
                .build()
        );
    }

}
