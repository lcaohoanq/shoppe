package com.lcaohoanq.shoppe.dtos.responses.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BasePaginationResponse {
    @JsonProperty("total_page")
    private int totalPage;

    @JsonProperty("total_item")
    private long totalItem;

//    @JsonProperty("per_page")
//    private int perPage;
}

