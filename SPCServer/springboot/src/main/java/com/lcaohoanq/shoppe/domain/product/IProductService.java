package com.lcaohoanq.shoppe.domain.product;

import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.metadata.MediaMeta;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

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
}
