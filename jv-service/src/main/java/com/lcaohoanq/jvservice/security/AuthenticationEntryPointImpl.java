package com.lcaohoanq.jvservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcaohoanq.jvservice.api.ApiError;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) 
            throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ApiError<Object> errorResponse = ApiError.errorBuilder()
            .message("Authentication Required")
            .reason(ex.getMessage())
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .isSuccess(false)
            .data(Map.of(
                "timestamp", System.currentTimeMillis(),
                "path", request.getRequestURI()
            ))
            .build();

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
} 