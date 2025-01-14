package com.lcaohoanq.shoppe.domain.product;

import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.domain.product.ProductPort.ProductDTO;
import com.lcaohoanq.shoppe.domain.product.ProductPort.ProductImageDTO;
import com.lcaohoanq.shoppe.metadata.MediaMeta;
import java.util.HashSet;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

    PageResponse<ProductResponse> getAll(PageRequest pageRequest);

    ProductResponse create(ProductDTO productDTO);

    ProductResponse getById(long id);

    void delete(long id);
    
    boolean existsByName(String name);

    ProductImage createProductImage(Long productId, MediaMeta mediaMeta, ProductImageDTO productImageDTO)
        throws Exception;
    
    Boolean existsById(Long id);
    
    void updateQuantity(long productId, int quantity, boolean isIncrease);

    HashSet<Product> findByWarehouseId(Long warehouseId);
    
    Long countByWarehouseId(Long warehouseId);
}
