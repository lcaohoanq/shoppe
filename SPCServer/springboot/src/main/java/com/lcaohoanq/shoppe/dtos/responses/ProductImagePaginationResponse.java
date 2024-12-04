package com.lcaohoanq.shoppe.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.dtos.responses.base.BasePaginationResponse;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductImagePaginationResponse extends BasePaginationResponse {

    @JsonProperty("items")
    private List<ProductImageResponse> items;

}
