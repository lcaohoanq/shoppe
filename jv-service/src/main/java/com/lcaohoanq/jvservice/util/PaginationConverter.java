package com.lcaohoanq.jvservice.util;

import com.lcaohoanq.jvservice.metadata.PaginationMeta;
import com.lcaohoanq.jvservice.api.PageResponse;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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

    default <T, R, C extends Collection<R>> PageResponse<R> mapPageResponse(
        Page<T> entityPage,
        Pageable pageable,
        Function<T, R> mapper,
        String message,
        Supplier<C> collectionSupplier
    ) {
        // Use the supplier to create a new collection and map the results into it
        C responseCollection = entityPage.getContent().stream()
            .map(mapper)
            .collect(Collectors.toCollection(collectionSupplier));

        return PageResponse.<R>pageBuilder()
            .data(responseCollection)
            .pagination(toPaginationMeta(entityPage, pageable))
            .statusCode(HttpStatus.OK.value())
            .isSuccess(true)
            .message(message)
            .build();
    }
    
}
