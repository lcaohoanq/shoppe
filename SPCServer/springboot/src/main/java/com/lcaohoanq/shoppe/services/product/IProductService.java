package com.lcaohoanq.shoppe.services.product;

import com.lcaohoanq.shoppe.dtos.request.ProductDTO;
import com.lcaohoanq.shoppe.dtos.request.ProductImageDTO;
import com.lcaohoanq.shoppe.dtos.responses.ProductResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.models.ProductImage;
import java.awt.print.Pageable;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

    PageResponse<ProductResponse> getAllProducts(PageRequest pageRequest);

    ProductResponse createProduct(ProductDTO productDTO);

    ProductResponse getById(long id);

    boolean existsByName(String name);

    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO)
        throws Exception;
}