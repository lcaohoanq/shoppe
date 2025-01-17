package com.lcaohoanq.shoppe.domain.cart;

import java.util.List;

public interface ICartItemService {
    
    CartItemResponse createCartItem(long userId, CartItemDTO cartItemDTO);
    
    CartItemDTO updateCartItem(CartItemDTO cartItemDTO);
    
    void deleteCartItem(Long cartItemId);
    
    CartItemDTO getCartItem(Long cartItemId);
    
    List<CartItemDTO> getCartItems();
    
    void deleteAllCartItems();
    
    void deleteCartItemsByCartId(Long cartId);
    
    List<CartItemDTO> getCartItemsByCartId(Long cartId);
    
    void deleteCartItemsByProductId(Long productId);

}
