package com.lcaohoanq.shoppe.repositories;

import com.lcaohoanq.shoppe.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);
 
    @Modifying
    @Query("UPDATE Product p SET p.isActive = :isActive WHERE p.id = :id")
    void updateProductIsActive(Long id, boolean isActive);
}
