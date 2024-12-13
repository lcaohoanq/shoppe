package com.lcaohoanq.shoppe.mapper;

import com.lcaohoanq.shoppe.domain.product.Product;
import com.lcaohoanq.shoppe.domain.product.ProductImage;
import com.lcaohoanq.shoppe.domain.product.ProductImageResponse;
import com.lcaohoanq.shoppe.domain.product.ProductResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {
    @Mapping(target = "shopOwnerId", source = "shopOwner.id")
    @Mapping(target = "images", source = "images", qualifiedByName = "mapProductImages")
    @Mapping(target = "category", source = "category")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "productId", source = "product.id")
    ProductImageResponse toProductImageResponse(ProductImage productImage);

    @Named("mapProductImages")
    default List<ProductImageResponse> mapProductImages(List<ProductImage> images) {
        return Optional.ofNullable(images)
            .orElse(Collections.emptyList())
            .stream()
            .map(this::toProductImageResponse)
            .toList();
    }
}