package com.lcaohoanq.shoppe.dtos.responses;

import com.lcaohoanq.shoppe.dtos.responses.base.BaseResponse;
import java.util.Map;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ExceptionResponse extends BaseResponse<Object> {

    private Map<String, Object> details;

}
