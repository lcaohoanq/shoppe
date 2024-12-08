package com.lcaohoanq.shoppe.domain.cart;

import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.domain.user.IUserService;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.exception.MalformBehaviourException;
import com.lcaohoanq.shoppe.util.DTOConverter;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService, DTOConverter {

    private final IUserService userService;
    private final CartRepository cartRepository;

    @Override
    public CartResponse create(long userId) {
        
        if(!userService.existsById(userId)){
            throw new MalformBehaviourException("User with id: " + userId + " not found");
        }
        
        if(cartRepository.existsByUserId(userId)){
            throw new MalformBehaviourException("Cart existed for user with id: " + userId);
        }

        User existedUser = userService.findUserById(userId);
        
        Cart newCart = Cart.builder()
            .user(existedUser)
            .totalPrice(0)
            .totalQuantity(0)
            .cartItems(new ArrayList<>())
            .build();

        CartItem cartItem = CartItem.builder()
            .cart(newCart)
            .quantity(0)
            .build();

        newCart.addCartProduct(cartItem);
        
        return toCartResponse(cartRepository.save(newCart));
        
    }

    @Override
    public PageResponse<CartResponse> getAllCarts(Pageable pageable) {
        return null;
    }

    @Override
    public CartResponse findById(Long cartId) {
        return toCartResponse(cartRepository
                .findById(cartId)
                .orElseThrow(() -> new MalformBehaviourException("Cart with id: " + cartId + " not found")));
    }

    @Override
    public CartResponse addItemToCart(Long cartId, Long productId, int quantity) {
        return null;
    }

    @Override
    public CartResponse removeItemFromCart(Long cartId, Long productId) {
        return null;
    }

    @Override
    public CartResponse updateItemInCart(Long cartId, Long productId, int quantity) {
        return null;
    }

    @Override
    public CartResponse clear(Long cartId) {
        return null;
    }

    @Override
    public void purchaseAllCart(Long cartId) {

    }

    @Override
    public void purchaseItem(Long cartId, Long productId) {

    }

    @Override
    public void purchaseItems(Long cartId, Long[] productIds) {

    }

    @Override
    public Boolean existsById(Long cartId) {
        return cartRepository.existsById(cartId);
    }

    @Override
    @Transactional
    public void updateQuantity(Long cartId, Integer quantity, boolean isIncrease) {
        if(isIncrease){
            cartRepository.increase(cartId, quantity);
        } else {
            cartRepository.decrease(cartId, quantity);
        }
    }
}
