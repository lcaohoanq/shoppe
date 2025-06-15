package com.lcaohoanq.productservice.domain.product;

import com.lcaohoanq.productservice.api.PageResponse;
import com.lcaohoanq.productservice.domain.product.ProductPort.ProductImageResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface IProductImageService {

    void create(long productId, String url) throws Exception;

    void update(long id, long productId, String url) throws Exception;

    void delete(long id) throws Exception;

    List<ProductImageResponse> findById(long id) throws Exception;

    PageResponse<ProductImageResponse> getAll(Pageable pageable) throws Exception;
    List<ProductImageResponse> getByProductId(Long productId) throws Exception;

}
