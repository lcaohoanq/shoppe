package com.lcaohoanq.jvservice.domain.product;

import com.lcaohoanq.jvservice.api.PageResponse;
import com.lcaohoanq.jvservice.domain.product.ProductPort.ProductDTO;
import com.lcaohoanq.jvservice.domain.product.ProductPort.ProductImageDTO;
import com.lcaohoanq.jvservice.metadata.MediaMeta;
import java.util.HashSet;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

    PageResponse<ProductPort.ProductResponse> getAll(PageRequest pageRequest);

    ProductPort.ProductResponse create(ProductDTO productDTO);

    ProductPort.ProductResponse getById(long id);

    void delete(long id);
    
    boolean existsByName(String name);

    ProductImage createProductImage(Long productId, MediaMeta mediaMeta, ProductImageDTO productImageDTO)
        throws Exception;
    
    Boolean existsById(Long id);
    
    void updateQuantity(long productId, int quantity, boolean isIncrease);

    HashSet<Product> findByWarehouseId(Long warehouseId);
    
    Long countByWarehouseId(Long warehouseId);
}
