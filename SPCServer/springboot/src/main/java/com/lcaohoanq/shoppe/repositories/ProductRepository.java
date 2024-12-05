package com.lcaohoanq.shoppe.repositories;

import com.lcaohoanq.shoppe.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

}
