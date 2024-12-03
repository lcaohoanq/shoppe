package com.lcaohoanq.shoppe.dtos.responses.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @JsonProperty("status_code")
    protected Integer statusCode;

    @JsonProperty("message")
    protected String message;

    @JsonProperty("reason")
    protected String reason;

    @JsonProperty("is_success")
    protected Boolean isSuccess;

    @JsonProperty("data")
    protected T data;

    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<>();
    }

    public static class ApiResponseBuilder<T> {
        private Integer statusCode;
        private String message;
        private String reason;
        private Boolean isSuccess;
        private T data;

        ApiResponseBuilder() {
        }

        public ApiResponseBuilder<T> statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ApiResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder<T> reason(String reason) {
            this.reason = reason;
            return this;
        }

        public ApiResponseBuilder<T> isSuccess(Boolean isSuccess) {
            this.isSuccess = isSuccess;
            return this;
        }

        public ApiResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponse<T> build() {
            return new ApiResponse<>(statusCode, message, reason, isSuccess, data);
        }
    }
}