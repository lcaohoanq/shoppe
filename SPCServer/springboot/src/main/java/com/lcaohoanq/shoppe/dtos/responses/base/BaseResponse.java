package com.lcaohoanq.shoppe.dtos.responses.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T>  {

    protected String message;
    protected String reason;

    @JsonProperty("status_code")
    protected Integer statusCode;

    @JsonProperty("is_success")
    protected Boolean isSuccess;

    @JsonProperty("list_data")
    protected List<T> listData;

    @JsonProperty("single_data")
    protected T singleData;

}
