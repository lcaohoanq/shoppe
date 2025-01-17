package com.lcaohoanq.shoppe.repository;

import com.lcaohoanq.shoppe.domain.cart.CartItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByCartId(Long cartId);

    Optional<CartItem> findByCartIdAndProductId(long cartId, long productId);
    
}
