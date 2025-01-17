package com.lcaohoanq.shoppe.domain.cart;

import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.domain.user.IUserService;
import com.lcaohoanq.shoppe.domain.user.UserResponse;
import com.lcaohoanq.shoppe.exception.MalformBehaviourException;
import com.lcaohoanq.shoppe.mapper.CartMapper;
import com.lcaohoanq.shoppe.mapper.UserMapper;
import com.lcaohoanq.shoppe.repository.CartRepository;
import com.lcaohoanq.shoppe.util.PaginationConverter;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService, PaginationConverter {

    private final IUserService userService;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final UserMapper userMapper;

    @Override
    public CartResponse create(long userId) {

        if (!userService.existsById(userId)) {
            throw new MalformBehaviourException("User with id: " + userId + " not found");
        }

        if (cartRepository.existsByUserId(userId)) {
            throw new MalformBehaviourException("Cart existed for user with id: " + userId);
        }

        UserResponse existedUser = userService.findUserById(userId);

        Cart newCart = Cart.builder()
            .user(userMapper.toUser(existedUser))
            .totalPrice(0)
            .totalQuantity(0)
            .cartItems(new ArrayList<>())
            .build();

        CartItem cartItem = CartItem.builder()
            .cart(newCart)
            .quantity(0)
            .build();

        newCart.addCartProduct(cartItem);

        return cartMapper.toCartResponse(cartRepository.save(newCart));

    }

    @Override
    public PageResponse<CartResponse> getAllCarts(Pageable pageable) {

        Page<Cart> cartPage = cartRepository.findAll(pageable);

        return mapPageResponse(
            cartPage,
            pageable,
            cartMapper::toCartResponse,
            "Get all carts successfully");
    }

    @Override
    public CartResponse findById(Long cartId) {
        return cartMapper.toCartResponse(cartRepository
                                  .findById(cartId)
                                  .orElseThrow(() -> new DataNotFoundException(
                                      "Cart with id: " + cartId + " not found")));
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
        if (isIncrease) {
            cartRepository.increase(cartId, quantity);
        } else {
            cartRepository.decrease(cartId, quantity);
        }
    }

    @Override
    public CartResponse getCartByUserId(Long userId) {
        Cart existedCart = cartRepository
            .findByUserId(userId)
            .orElseThrow(
                () -> new MalformBehaviourException("Cart not found for user with id: " + userId));
        return cartMapper.toCartResponse(existedCart);
    }
}
