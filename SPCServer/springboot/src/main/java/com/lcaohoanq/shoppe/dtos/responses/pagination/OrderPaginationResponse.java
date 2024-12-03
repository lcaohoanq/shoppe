package com.lcaohoanq.shoppe.dtos.responses.pagination;

import com.lcaohoanq.shoppe.dtos.responses.base.BasePaginationResponse;
import com.lcaohoanq.shoppe.dtos.responses.OrderResponse;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class OrderPaginationResponse extends BasePaginationResponse {
        private List<OrderResponse> item;
}
