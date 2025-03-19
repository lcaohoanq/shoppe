package com.lcaohoanq.shoppe.domain.cart;

import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.base.exception.OutOfStockException;
import com.lcaohoanq.shoppe.domain.cart.CartItem.CartItemStatus;
import com.lcaohoanq.shoppe.domain.product.IProductService;
import com.lcaohoanq.shoppe.domain.product.Product;
import com.lcaohoanq.shoppe.domain.product.ProductPort;
import com.lcaohoanq.shoppe.domain.product.ProductPort.ProductResponse;
import com.lcaohoanq.shoppe.domain.user.IUserService;
import com.lcaohoanq.shoppe.exception.MalformBehaviourException;
import com.lcaohoanq.shoppe.mapper.CartMapper;
import com.lcaohoanq.shoppe.repository.CartItemRepository;
import com.lcaohoanq.shoppe.repository.CartRepository;
import com.lcaohoanq.shoppe.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final IUserService userService;
    private final ICartService cartService;
    private final IProductService productService;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    public CartItemResponse createCartItem(long userId, CartItemDTO cartItemDTO) {

        // Validate user existence
        if (!userService.existsById(userId)) {
            throw new MalformBehaviourException("User with id " + userId + " does not exist");
        }

        // Get the existing cart for the user
        Cart existedCart = cartRepository
            .findByUserId(userId)
            .orElseThrow(() ->
                             new DataNotFoundException(
                                 "Cart with user id " + userId + " does not exist"));

        // Validate product existence
        Product existProduct = productRepository
            .findById(cartItemDTO.productId())
            .orElseThrow(() ->
                             new DataNotFoundException("Product with id " + cartItemDTO.productId() + " does not exist"));

        ProductPort.ProductResponse product = productService.getById(cartItemDTO.productId());
        
        if (cartItemDTO.quantity() >= product.quantity()) {
            throw new OutOfStockException(String.format(
                "Product with id %d has only %d items left in stock",
                cartItemDTO.productId(), product.quantity()
            ));
        }

        // Check if the product already exists in the cart
        Optional<CartItem> existingCartItemOpt = cartItemRepository.findByCartIdAndProductId(
            existedCart.getId(), cartItemDTO.productId()
        );

        CartItem cartItem;

        if (existingCartItemOpt.isPresent()) {
            // Product already exists in the cart, update the quantity
            cartItem = existingCartItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.quantity());
        } else {
            // Product does not exist, create a new CartItem
            cartItem = CartItem.builder()
                .cart(existedCart)
                .product(existProduct)
                .status(CartItemStatus.IN_CART)
                .quantity(cartItemDTO.quantity())
                .build();
        }

        // Update product stock and cart total quantity
        productService.updateQuantity(cartItemDTO.productId(), cartItemDTO.quantity(), false);
        cartService.updateQuantity(existedCart.getId(), cartItemDTO.quantity(), true);
        
        return cartMapper.toCartItemResponse(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDTO updateCartItem(CartItemDTO cartItemDTO) {
        return null;
    }

    @Override
    public void deleteCartItem(Long cartItemId) {

    }

    @Override
    public CartItemDTO getCartItem(Long cartItemId) {
        return null;
    }

    @Override
    public List<CartItemDTO> getCartItems() {
        return List.of();
    }

    @Override
    public void deleteAllCartItems() {

    }

    @Override
    public void deleteCartItemsByCartId(Long cartId) {

    }

    @Override
    public List<CartItemDTO> getCartItemsByCartId(Long cartId) {
        return List.of();
    }

    @Override
    public void deleteCartItemsByProductId(Long productId) {

    }
}
