package com.lcaohoanq.shoppe.repositories;

import com.lcaohoanq.shoppe.models.ProductImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long koiId);

}
