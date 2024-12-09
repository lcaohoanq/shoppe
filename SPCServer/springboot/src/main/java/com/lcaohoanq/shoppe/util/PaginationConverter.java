package com.lcaohoanq.shoppe.util;

import com.lcaohoanq.shoppe.metadata.PaginationMeta;
import com.lcaohoanq.shoppe.api.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import org.springframework.http.HttpStatus;

public interface PaginationConverter {

    default <T> PaginationMeta toPaginationMeta(Page<T> page, Pageable pageable) {
        return PaginationMeta.builder()
            .totalPages(page.getTotalPages())
            .totalItems(page.getTotalElements())
            .currentPage(pageable.getPageNumber())
            .pageSize(pageable.getPageSize())
            .build();
    }

    default <T, R> PageResponse<R> mapPageResponse(Page<T> entityPage, Pageable pageable, Function<T, R> mapper, String message) {
        List<R> responseList = entityPage.getContent().stream()
            .map(mapper)
            .toList();

        return PageResponse.<R>pageBuilder()
            .data(responseList)
            .pagination(toPaginationMeta(entityPage, pageable))
            .statusCode(HttpStatus.OK.value())
            .isSuccess(true)
            .message(message)
            .build();
    }
}
