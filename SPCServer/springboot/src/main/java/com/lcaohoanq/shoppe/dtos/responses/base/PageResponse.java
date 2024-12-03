package com.lcaohoanq.shoppe.dtos.responses.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lcaohoanq.shoppe.metadata.PaginationMeta;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PageResponse<T> extends ApiResponse<List<T>> {
    @JsonProperty("pagination")
    private PaginationMeta pagination;

    private PageResponse(Integer statusCode, String message, String reason,
                         Boolean isSuccess, List<T> data, PaginationMeta pagination) {
        super(statusCode, message, reason, isSuccess, data);
        this.pagination = pagination;
    }

    public static <T> PageResponseBuilder<T> pageBuilder() {
        return new PageResponseBuilder<>();
    }

    public static class PageResponseBuilder<T> {
        private Integer statusCode;
        private String message;
        private String reason;
        private Boolean isSuccess;
        private List<T> data;
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

        public PageResponseBuilder<T> data(List<T> data) {
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
