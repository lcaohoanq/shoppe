package com.lcaohoanq.shoppe.domain.product;

import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.enums.ProductStatus;
import com.lcaohoanq.shoppe.exception.CategoryNotFoundException;
import com.lcaohoanq.shoppe.exception.InvalidParamException;
import com.lcaohoanq.shoppe.exception.MalformBehaviourException;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.metadata.MediaMeta;
import com.lcaohoanq.shoppe.domain.category.Category;
import com.lcaohoanq.shoppe.domain.user.User;
import com.lcaohoanq.shoppe.domain.category.CategoryRepository;
import com.lcaohoanq.shoppe.domain.user.UserRepository;
import com.lcaohoanq.shoppe.util.DTOConverter;
import com.lcaohoanq.shoppe.util.PaginationConverter;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService, DTOConverter, PaginationConverter {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public PageResponse<ProductResponse> getAll(PageRequest pageRequest) {
        Page<Product> productsPage = productRepository.findAll(pageRequest);
        return mapPageResponse(productsPage, pageRequest, this::toProductResponse, "Get all products successfully");
    }

    @Override
    public ProductResponse getById(long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.orElseThrow(() -> new DataNotFoundException("Product not exist")) == null){
            throw new DataNotFoundException("Product not exist");
        }
        return toProductResponse(product.get());
    }

    @Transactional
    @Override
    public void delete(long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.orElseThrow(() -> new DataNotFoundException("Product not exist")) == null){
            throw new DataNotFoundException("Product not exist");
        }
        productRepository.updateProductIsActive(id, false);
    }

    @Override
    public ProductResponse create(ProductDTO productDTO) {

        Optional<Category> category = categoryRepository.findByName(productDTO.category());

        if(category.isEmpty()){
            throw new CategoryNotFoundException("Category not exist");
        }

        Optional<User> shopOwner = userRepository.findById(productDTO.shopOwnerId());
        if(shopOwner.isEmpty()){
            throw new DataNotFoundException("Shop owner not exist");
        }

        Product newProduct = Product.builder()
            .name(productDTO.name())
            .description(productDTO.description())
            .category(category.get())
            .shopOwner(shopOwner.get())
            .price(productDTO.price())
            .priceBeforeDiscount(productDTO.priceBeforeDiscount())
            .quantity(productDTO.quantity())
            .sold(productDTO.sold())
            .view(productDTO.view())
            .rating(productDTO.rating())
            .status(ProductStatus.UNVERIFIED) //default status
            .isActive(true) //default active
            .build();

        return toProductResponse(productRepository.save(newProduct));
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, MediaMeta mediaMeta, ProductImageDTO productImageDTO)
        throws Exception {
        Product existingProduct = productRepository
            .findById(productId)
            .orElseThrow(() ->
                             new DataNotFoundException("Category not found: " + productImageDTO.productId()));

        if(!existingProduct.isActive()) throw new MalformBehaviourException("Product is not active");
        
        ProductImage newProductImage = ProductImage.builder()
            .product(existingProduct)
            .mediaMeta(mediaMeta)
            .build();
        //khong cho insert qua 5 anh cho mot san pham
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Required :" + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }

}