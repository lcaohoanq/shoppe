package com.lcaohoanq.shoppe.domain.product;

import com.github.javafaker.Faker;
import com.lcaohoanq.shoppe.api.ApiResponse;
import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.component.LocalizationUtils;
import com.lcaohoanq.shoppe.domain.asset.IFileStoreService;
import com.lcaohoanq.shoppe.domain.category.Category;
import com.lcaohoanq.shoppe.domain.category.ICategoryService;
import com.lcaohoanq.shoppe.domain.product.Product.ProductStatus;
import com.lcaohoanq.shoppe.domain.product.ProductPort.ProductDTO;
import com.lcaohoanq.shoppe.domain.product.ProductPort.ProductImageDTO;
import com.lcaohoanq.shoppe.exception.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.metadata.MediaMeta;
import com.lcaohoanq.shoppe.repository.CategoryRepository;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api.prefix}/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    IProductService productService;
    ICategoryService categoryService;
    CategoryRepository categoryRepository;
    LocalizationUtils localizationUtils;
    IFileStoreService fileStoreService;

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PageResponse<ProductResponse>> getAll(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "60") int limit
    ) {
        return ResponseEntity.ok(
            productService.getAll(
                PageRequest.of(page - 1,
                               limit,
                               Sort.by("id").ascending())));
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
        @PathVariable long id
    ) {
        return ResponseEntity.ok(
            ApiResponse.<ProductResponse>builder()
                .message("Get Product success")
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .data(productService.getById(id))
                .build()
        );
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
        @Valid @RequestBody ProductDTO productDTO,
        BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
            ApiResponse.<ProductResponse>builder()
                .message("Create new product success")
                .statusCode(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(productService.create(productDTO))
                .build()
        );

    }

    @PostMapping(
        value = "/uploads/{id}",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_MEMBER', 'ROLE_STAFF', 'ROLE_SHOP_OWNER')")
    public ResponseEntity<ApiResponse<List<ProductImage>>> uploadImages(
        @PathVariable("id") Long productId,
        @ModelAttribute("files") List<MultipartFile> files
    ) throws Exception {
        ProductResponse existingProduct = productService.getById(productId);
        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile file : fileStoreService.validateListProductImage(files)) {
            // Lưu file và cập nhật thumbnail trong DTO
            String filename = fileStoreService
                .storeFile(fileStoreService.validateProductImage(file));

            MediaMeta mediaMeta = MediaMeta.builder()
                .fileName(filename)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .imageUrl(filename)
                .videoUrl(null)
                .build();

            //lưu vào đối tượng product trong DB
            ProductImage productImage = productService.createProductImage(
                existingProduct.id(),
                mediaMeta,
                new ProductImageDTO(existingProduct.id(), filename)
            );
            productImages.add(productImage);
        }

        return ResponseEntity.ok().body(
            ApiResponse.<List<ProductImage>>builder()
                .message("Upload image success")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(productImages).build()
        );
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<ProductResponse>> deleteProduct(
        @PathVariable long id
    ) {
        productService.delete(id);
        return ResponseEntity.ok(
            ApiResponse.<ProductResponse>builder()
                .message("Delete product success")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .build()
        );
    }

    @PostMapping("/generateFakeProducts")
    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
    public ResponseEntity<String> generateFakeProducts() {
        Faker faker = new Faker();
        Random random = new Random();

        List<Category> categoryList = categoryRepository.findAll();

        for (int i = 1; i <= 500; i++) {
//            String productName = faker.commerce().productName();
//            if(productService.existsByName(productName)) {
//                continue;
//            }

            String randomCategory = categoryList.get(random.nextInt(categoryList.size())).getName();

            ProductDTO productDTO = new ProductDTO(
                faker.commerce().productName(),
                faker.lorem().sentence(), //description
                randomCategory, //category
                3L, //shopOwnerId
                (double)faker.number().numberBetween(10, 90_000_000), //price
                (double)faker.number().numberBetween(10, 90_000_000), //priceBeforeDiscount
                faker.number().numberBetween(1, 10000), //quantity
                faker.number().numberBetween(0, 10000), //sold
                faker.number().numberBetween(0, 10000), //view
                faker.number().randomDouble(1, 0, 5), //rating,
                ProductStatus.VERIFIED, //status
                true //isActive
            );
            try{
                productService.create(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Fake products generated");
    }

}
