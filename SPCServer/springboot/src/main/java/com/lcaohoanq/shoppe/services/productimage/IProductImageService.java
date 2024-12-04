package com.lcaohoanq.shoppe.services.productimage;

import com.lcaohoanq.shoppe.dtos.responses.ProductImageResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface IProductImageService {

    void createProductImage(long productId, String url) throws Exception;

    void updateProductImage(long id, long productId, String url) throws Exception;

    void deleteProductImage(long id) throws Exception;

    List<ProductImageResponse> getProductImage(long id) throws Exception;

    PageResponse<ProductImageResponse> getAllProductImages(Pageable pageable) throws Exception;
    List<ProductImageResponse> getProductImagesByProductId(Long productId) throws Exception;

}
