package com.lcaohoanq.shoppe.services.product;

import com.lcaohoanq.shoppe.dtos.request.ProductDTO;
import com.lcaohoanq.shoppe.dtos.request.ProductImageDTO;
import com.lcaohoanq.shoppe.dtos.responses.ProductResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.exceptions.CategoryNotFoundException;
import com.lcaohoanq.shoppe.exceptions.InvalidParamException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.Category;
import com.lcaohoanq.shoppe.models.Product;
import com.lcaohoanq.shoppe.models.ProductImage;
import com.lcaohoanq.shoppe.models.User;
import com.lcaohoanq.shoppe.repositories.CategoryRepository;
import com.lcaohoanq.shoppe.repositories.ProductImageRepository;
import com.lcaohoanq.shoppe.repositories.ProductRepository;
import com.lcaohoanq.shoppe.repositories.UserRepository;
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
    private final UserRepository userRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public PageResponse<ProductResponse> getAllProducts(PageRequest pageRequest) {
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

    @Override
    public ProductResponse createProduct(ProductDTO productDTO) {

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
            .thumbnail(productDTO.thumbnail())
            .category(category.get())
            .shopOwner(shopOwner.get())
            .price(productDTO.price())
            .priceBeforeDiscount(productDTO.priceBeforeDiscount())
            .quantity(productDTO.quantity())
            .sold(productDTO.sold())
            .view(productDTO.view())
            .rating(productDTO.rating())
            .build();

        return toProductResponse(productRepository.save(newProduct));
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO)
        throws Exception {
        Product existingProduct = productRepository
            .findById(productId)
            .orElseThrow(() ->
                             new DataNotFoundException("Category not found: " + productImageDTO.productId()));

        ProductImage newProductImage = ProductImage.builder()
            .product(existingProduct)
            .imageUrl(productImageDTO.imageUrl())
            .build();
        //khong cho insert qua 5 anh cho mot san pham
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Required :" + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }

}
