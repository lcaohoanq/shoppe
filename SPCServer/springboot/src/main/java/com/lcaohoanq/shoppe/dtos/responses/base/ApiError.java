package com.lcaohoanq.shoppe.dtos.responses.base;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({
    "message",
    "status_code",
    "is_success",
    "reason",
    "data"
})
@NoArgsConstructor
public class ApiError<T> extends ApiResponse<T> {

    private ApiError(Integer statusCode, String message, String reason, Boolean isSuccess, T data) {
        super(statusCode, message, reason, isSuccess, data);
    }

    public static <T> ApiErrorBuilder<T> errorBuilder() {
        return new ApiErrorBuilder<>();
    }

    public static class ApiErrorBuilder<T> {
        private Integer statusCode;
        private String message;
        private String reason;
        private Boolean isSuccess;
        private T data;

        ApiErrorBuilder() {
        }

        public ApiErrorBuilder<T> statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ApiErrorBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ApiErrorBuilder<T> reason(String reason) {
            this.reason = reason;
            return this;
        }

        public ApiErrorBuilder<T> isSuccess(Boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }

        public ApiErrorBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiError<T> build() {
            return new ApiError<>(statusCode, message, reason, isSuccess, data);
        }
    }

}
