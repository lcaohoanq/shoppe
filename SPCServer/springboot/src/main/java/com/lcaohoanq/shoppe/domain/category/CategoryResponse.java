package com.lcaohoanq.shoppe.domain.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.shoppe.domain.subcategory.Subcategory;
import java.time.LocalDateTime;
import java.util.Set;

@JsonPropertyOrder({
    "id",
    "name",
    "created_at",
    "updated_at"
})
public record CategoryResponse(
    @JsonProperty("id")
    Long id,
    @JsonProperty("name")
    String name,

    @JsonProperty("subcategories") Set<Subcategory> subcategories,

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
