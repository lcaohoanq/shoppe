package com.lcaohoanq.shoppe.services.product;

import com.lcaohoanq.shoppe.dtos.request.ProductDTO;
import com.lcaohoanq.shoppe.dtos.responses.ProductResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.exceptions.CategoryNotFoundException;
import com.lcaohoanq.shoppe.models.Category;
import com.lcaohoanq.shoppe.models.Product;
import com.lcaohoanq.shoppe.repositories.CategoryRepository;
import com.lcaohoanq.shoppe.repositories.ProductRepository;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import com.lcaohoanq.shoppe.utils.PaginationConverter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService, DTOConverter, PaginationConverter {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public PageResponse<ProductResponse> getAllProducts(PageRequest pageRequest) {
        Page<Product> productsPage = productRepository.findAll(pageRequest);
        return mapPageResponse(productsPage, pageRequest, this::toProductResponse, "Get all products successfully");
    }

    @Override
    public ProductResponse getById(long id) {
        return productRepository.findById(id).map(this::toProductResponse).orElse(null);
    }

    @Override
    public ProductResponse createProduct(ProductDTO productDTO) {

        Optional<Category> category = categoryRepository.findByName(productDTO.category());

        if(category.isEmpty()){
            throw new CategoryNotFoundException("Category not exist");
        }

        Product newProduct = Product.builder()
            .name(productDTO.name())
            .description(productDTO.description())
            .thumbnail(productDTO.thumbnail())
            .category(category.get().getName())
            .price(productDTO.price())
            .priceBeforeDiscount(productDTO.priceBeforeDiscount())
            .quantity(productDTO.quantity())
            .sold(productDTO.sold())
            .view(productDTO.view())
            .rating(productDTO.rating())
            .build();

        return toProductResponse(productRepository.save(newProduct));
    }

}
