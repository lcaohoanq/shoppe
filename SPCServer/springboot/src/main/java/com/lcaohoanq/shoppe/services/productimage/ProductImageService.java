package com.lcaohoanq.shoppe.services.productimage;

import com.lcaohoanq.shoppe.dtos.responses.ProductImageResponse;
import com.lcaohoanq.shoppe.dtos.responses.ProductResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.models.Product;
import com.lcaohoanq.shoppe.models.ProductImage;
import com.lcaohoanq.shoppe.repositories.ProductImageRepository;
import com.lcaohoanq.shoppe.services.product.IProductService;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import com.lcaohoanq.shoppe.utils.PaginationConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductImageService implements IProductImageService, DTOConverter,
    PaginationConverter {

    private final ProductImageRepository ProductImageRepository;
    private final IProductService productService;

    @Override
    public void create(long productId, String url) throws Exception {
        ProductResponse productResponse =  productService.getById(productId);

        Product product = Product.builder()
            .id(productResponse.id())
            .build();

        ProductImage productImage = ProductImage.builder()
            .product(product)
            .imageUrl(url)
            .build();

        ProductImageRepository.save(productImage);
    }

    @Override
    public void update(long id, long productId, String url) throws Exception {
        ProductImage productImage = ProductImageRepository.findById(id)
            .orElseThrow(() -> new Exception("Product image not found"));
        productImage.setImageUrl(url);
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
        ProductImageRepository.findById(id)
            .orElseThrow(() -> new Exception("Product image not found"));
        return List.of(toProductImageResponse(ProductImageRepository.getById(id)));
    }

    @Override
    public PageResponse<ProductImageResponse> getAll(Pageable pageable) throws Exception {
        Page<ProductImage> productImages = ProductImageRepository.findAll(pageable);
        return mapPageResponse(productImages, pageable, this::toProductImageResponse, "Get All product images successfully");
    }

    @Override
    public List<ProductImageResponse> getByProductId(Long productId) throws Exception {
        List<ProductImage> productImages = ProductImageRepository.findByProductId(productId);
            if (productImages.isEmpty()) {
                throw new Exception("Product images not found");
            }
        return productImages.stream().map(this::toProductImageResponse).toList();

    }


}
