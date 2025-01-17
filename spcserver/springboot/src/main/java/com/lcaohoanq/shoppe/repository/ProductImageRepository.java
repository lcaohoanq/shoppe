package com.lcaohoanq.shoppe.repository;

import com.lcaohoanq.shoppe.domain.product.ProductImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long koiId);

}
