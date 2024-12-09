package com.lcaohoanq.shoppe.security;

import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcaohoanq.shoppe.api.ApiError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) 
            throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.FORBIDDEN.value());

        ApiError<Object> errorResponse = ApiError.errorBuilder()
            .message("Access Denied")
            .reason("You don't have permission to access this resource")
            .statusCode(HttpStatus.FORBIDDEN.value())
            .isSuccess(false)
            .data(Map.of(
                "timestamp", System.currentTimeMillis(),
                "path", request.getRequestURI()
            ))
            .build();

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
} 