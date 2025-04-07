package com.lcaohoanq.orderservice.domain.cart;

import com.lcaohoanq.jvservice.api.PageResponse;
import org.springframework.data.domain.Pageable;

public interface ICartService {
    CartResponse create(long userId);
    
    PageResponse<CartResponse> getAllCarts(Pageable pageable);

    CartResponse findById(Long cartId);

    CartResponse addItemToCart(Long cartId, Long productId, int quantity);

    CartResponse removeItemFromCart(Long cartId, Long productId);

    CartResponse updateItemInCart(Long cartId, Long productId, int quantity);

    CartResponse clear(Long cartId);
    
    void purchaseAllCart(Long cartId);

    void purchaseItem(Long cartId, Long productId);
    
    void purchaseItems(Long cartId, Long[] productIds);

    Boolean existsById(Long cartId);
    
    void updateQuantity(Long cartId, Integer quantity, boolean isIncrease);

    CartResponse getCartByUserId(Long userId);
}
