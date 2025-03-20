package com.lcaohoanq.shoppe.domain.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.shoppe.domain.subcategory.Subcategory;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

public interface CategoryPort {
    record CategoryDTO(
        @NotEmpty(message = "Category name is required") List<String> name
    ) {}

    @JsonPropertyOrder({
        "id",
        "name",
        "subcategories",
        "created_at",
        "updated_at"
    })
    record CategoryResponse(
        Long id,
        String name,

        @JsonIgnore
        TreeSet<Subcategory> subcategories,

        @JsonIgnore
        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
        LocalDateTime createdAt,

        @JsonIgnore
        @JsonProperty("updated_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
        LocalDateTime updatedAt
    ) {

    }

    record CreateNewSubcategoryResponse(
        @JsonProperty("category") CategoryResponse categoryResponse
    ) {}
}
