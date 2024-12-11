package com.lcaohoanq.shoppe.domain.product;

import java.util.HashSet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);
    
    @Modifying
    @Query("UPDATE Product p SET p.isActive = :isActive WHERE p.id = :id")
    void updateProductIsActive(Long id, boolean isActive);

    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity + :quantity WHERE p.id = :productId")
    void increaseProductQuantity(
        @Param("productId") long productId,
        @Param("quantity") int quantity
    );

    @Modifying
    @Query("UPDATE Product p SET p.quantity = p.quantity - :quantity WHERE p.id = :productId")
    void decreaseProductQuantity(
        @Param("productId") long productId,
        @Param("quantity") int quantity
    );
    
    HashSet<Product> findByWarehouseId(Long warehouseId);

    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Product p WHERE p.warehouse.id = :warehouseId")
    Long countTotalQuantityByWarehouseId(@Param("warehouseId") Long warehouseId);

}
