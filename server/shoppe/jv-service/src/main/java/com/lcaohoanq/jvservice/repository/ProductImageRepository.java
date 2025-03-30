package com.lcaohoanq.jvservice.repository;

import com.lcaohoanq.jvservice.domain.product.ProductImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long koiId);

}
