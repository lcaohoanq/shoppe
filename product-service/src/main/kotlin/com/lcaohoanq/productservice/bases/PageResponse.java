package com.lcaohoanq.productservice.bases;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.lcaohoanq.common.apis.ApiResponse;
import com.lcaohoanq.common.metadata.PaginationMeta;
import java.util.Collection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
    "message",
    "data",
    "pagination_meta",
    "status_code",
    "is_success",
})
@NoArgsConstructor
@Getter
public class PageResponse<T> extends ApiResponse<Collection<T>> {
    @JsonProperty("pagination_meta")
    private PaginationMeta paginationMeta;

    private PageResponse(Integer statusCode, String message, String reason,
                         Boolean isSuccess, Collection<T> data, PaginationMeta paginationMeta) {
        super(statusCode, message, reason, isSuccess, data);
        this.paginationMeta = paginationMeta;
    }

    public static <T> PageResponseBuilder<T> pageBuilder() {
        return new PageResponseBuilder<>();
    }

    public static class PageResponseBuilder<T> {
        private Integer statusCode;
        private String message;
        private String reason;
        private Boolean isSuccess;
        private Collection<T> data;
        private PaginationMeta pagination;

        PageResponseBuilder() {
        }

        public PageResponseBuilder<T> statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public PageResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public PageResponseBuilder<T> reason(String reason) {
            this.reason = reason;
            return this;
        }

        public PageResponseBuilder<T> isSuccess(Boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }

        public PageResponseBuilder<T> data(Collection<T> data) {
            this.data = data;
            return this;
        }

        public PageResponseBuilder<T> pagination(PaginationMeta pagination) {
            this.pagination = pagination;
            return this;
        }

        public PageResponse<T> build() {
            return new PageResponse<>(statusCode, message, reason, isSuccess, data, pagination);
        }
    }
}