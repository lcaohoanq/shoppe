package com.lcaohoanq.shoppe.controllers;

import com.github.javafaker.Faker;
import com.lcaohoanq.shoppe.dtos.request.ProductDTO;
import com.lcaohoanq.shoppe.dtos.responses.ProductResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.exceptions.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.models.Category;
import com.lcaohoanq.shoppe.repositories.CategoryRepository;
import com.lcaohoanq.shoppe.services.category.CategoryService;
import com.lcaohoanq.shoppe.services.product.IProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final IProductService productService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PageResponse<ProductResponse>> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(
            productService.getAllProducts(
                PageRequest.of(page,
                               limit,
                               Sort.by("createdAt").ascending())));
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
                .data(productService.createProduct(productDTO))
                .build()
        );

    }

//    @PostMapping("/generateFakeProducts")
//    @PreAuthorize("hasAnyRole('ROLE_MANAGER')")
//    public ResponseEntity<String> generateFakeProducts() {
//        Faker faker = new Faker();
//        Random random = new Random();
//
//        List<Category> categoryList = categoryRepository.findAll();
//
//        for (int i = 10_001; i <= 1_000_000; i++) {
////            String productName = faker.commerce().productName();
////            if(productService.existsByName(productName)) {
////                continue;
////            }
//
//            String randomCategory = categoryList.get(random.nextInt(categoryList.size())).getName();
//
//            ProductDTO productDTO = new ProductDTO(
//                faker.commerce().productName(),
//                faker.lorem().sentence(), //description
//                faker.internet().image(), //thumbnail
//                randomCategory, //category
//                (double)faker.number().numberBetween(10, 90_000_000), //price
//                (double)faker.number().numberBetween(10, 90_000_000), //priceBeforeDiscount
//                faker.number().numberBetween(1, 10000), //quantity
//                faker.number().numberBetween(0, 10000), //sold
//                faker.number().numberBetween(0, 10000), //view
//                faker.number().randomDouble(1, 0, 5) //rating
//            );
//            try{
//                productService.createProduct(productDTO);
//            } catch (Exception e) {
//                return ResponseEntity.badRequest().body(e.getMessage());
//            }
//        }
//        return ResponseEntity.ok("Fake products generated");
//    }

}
