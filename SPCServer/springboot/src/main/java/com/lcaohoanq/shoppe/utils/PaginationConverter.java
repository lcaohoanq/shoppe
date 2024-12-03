package com.lcaohoanq.shoppe.utils;

import com.lcaohoanq.shoppe.metadata.PaginationMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PaginationConverter<T> {

    public PaginationMeta toPaginationMeta(Page<T> page, Pageable pageable){
        return PaginationMeta.builder()
            .totalPages(page.getTotalPages())
            .totalItems(page.getTotalElements())
            .currentPage(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .build();
    }

}
