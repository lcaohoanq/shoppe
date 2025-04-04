package com.lcaohoanq.jvservice.domain.product;

import com.lcaohoanq.jvservice.api.PageResponse;
import com.lcaohoanq.jvservice.domain.product.IProductImageService;import com.lcaohoanq.jvservice.domain.product.ProductPort.ProductImageResponse;
import com.lcaohoanq.jvservice.mapper.ProductMapper;
import com.lcaohoanq.jvservice.metadata.MediaMeta;
import com.lcaohoanq.jvservice.util.PaginationConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService,
    PaginationConverter {

    private final com.lcaohoanq.jvservice.repository.ProductImageRepository ProductImageRepository;
    private final IProductService productService;
    private final ProductMapper productMapper;

    @Override
    public void create(long productId, String url) throws Exception {
        ProductPort.ProductResponse productResponse =  productService.getById(productId);

        Product product = Product.builder()
            .id(productResponse.id())
            .build();

        ProductImage productImage = ProductImage.builder()
            .product(product)
            .mediaMeta(
                MediaMeta.builder()
                    .imageUrl(url)
                    .build()
            )
            .build();

        ProductImageRepository.save(productImage);
    }

    @Override
    public void update(long id, long productId, String url) throws Exception {
        ProductImage productImage = ProductImageRepository.findById(id)
            .orElseThrow(() -> new Exception("Product image not found"));
        productImage.setMediaMeta(
            MediaMeta.builder()
                .imageUrl(url)
                .build()
        );
        ProductImageRepository.save(productImage);
    }

    @Override
    public void delete(long id) throws Exception {
        ProductImage productImage = ProductImageRepository.findById(id)
            .orElseThrow(() -> new Exception("Product image not found"));
        ProductImageRepository.delete(productImage);
    }

    @Override
    public List<ProductImageResponse> findById(long id) throws Exception {
        ProductImage productImage = ProductImageRepository.findById(id)
            .orElseThrow(() -> new Exception("Product image not found"));
        return List.of(productMapper.toProductImageResponse(productImage));
    }

    @Override
    public PageResponse<ProductImageResponse> getAll(Pageable pageable) throws Exception {
        Page<ProductImage> productImages = ProductImageRepository.findAll(pageable);
        return mapPageResponse(productImages, pageable, productMapper::toProductImageResponse, "Get All product images successfully");
    }

    @Override
    public List<ProductImageResponse> getByProductId(Long productId) throws Exception {
        List<ProductImage> productImages = ProductImageRepository.findByProductId(productId);
            if (productImages.isEmpty()) {
                throw new Exception("Product images not found");
            }
        return productImages.stream().map(productMapper::toProductImageResponse).toList();

    }


}
