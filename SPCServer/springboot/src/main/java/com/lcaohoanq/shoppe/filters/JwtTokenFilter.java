package com.lcaohoanq.shoppe.filters;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcaohoanq.shoppe.components.JwtTokenUtils;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiError;
import com.lcaohoanq.shoppe.exceptions.JwtAuthenticationException;
import com.lcaohoanq.shoppe.models.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtils jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    @Value("${api.prefix}")
    private String apiPrefix;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        try {
            if (request.getServletPath().equals("/error") || 
                isPublicEndpoint(request.getServletPath()) ||
                isSwaggerEndpoint(request.getServletPath())) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = extractToken(request);
            
            // Check if endpoint requires authentication
            if (!isPublicEndpoint(request.getServletPath())) {
                if (token == null) {
                    sendErrorResponse(response, "No authentication token provided", 
                        HttpStatus.UNAUTHORIZED, request.getRequestURI());
                    return;
                }

                try {
                    // Try to parse the token first - this will catch malformed tokens
                    String email = jwtTokenUtil.extractEmail(token);
                    if (email == null) {
                        throw new JwtAuthenticationException("Invalid token");
                    }

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        User userDetails = (User) userDetailsService.loadUserByUsername(email);
                        if (jwtTokenUtil.validateToken(token, userDetails)) {
                            SecurityContextHolder.getContext().setAuthentication(
                                createAuthentication(userDetails, request)
                            );
                        }
                    }
                } catch (JwtAuthenticationException e) {
                    sendErrorResponse(response, e.getMessage(), 
                        HttpStatus.UNAUTHORIZED, request.getRequestURI());
                    return;
                } catch (MalformedJwtException | IllegalArgumentException e) {
                    // Explicitly catch malformed token exceptions
                    sendErrorResponse(response, "Malformed authentication token", 
                        HttpStatus.UNAUTHORIZED, request.getRequestURI());
                    return;
                } catch (ExpiredJwtException e) {
                    sendErrorResponse(response, "Token has expired", 
                        HttpStatus.UNAUTHORIZED, request.getRequestURI());
                    return;
                }
            }

            // Continue to Spring Security's role checking
            filterChain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            sendErrorResponse(response, "Access denied - Insufficient privileges", 
                HttpStatus.FORBIDDEN, request.getRequestURI());
        } catch (Exception e) {
            log.error("Error processing request", e);
            sendErrorResponse(response, "Authentication failed", 
                HttpStatus.UNAUTHORIZED, request.getRequestURI());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message, 
        HttpStatus status, String path) throws IOException {
        response.setContentType("application/json");
        response.setStatus(status.value());
        
        ApiError<Object> errorResponse = ApiError.errorBuilder()
            .message(message)
            .statusCode(status.value())
            .isSuccess(false)
            .data(Map.of(
                "timestamp", System.currentTimeMillis(),
                "path", path
            ))
            .build();
        
        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private Authentication createAuthentication(User userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities()
        );
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return auth;
    }

    private boolean isPublicEndpoint(String path) {
        return path.equals(apiPrefix + "/auth/login") ||
               path.equals(apiPrefix + "/auth/register") ||
               path.equals(apiPrefix + "/roles") ||
               path.equals(apiPrefix + "/users") ||
               path.equals("/error");
    }

    private boolean isSwaggerEndpoint(String path) {
        return path.contains("/swagger-ui") || 
               path.contains("/api-docs") || 
               path.contains("/swagger-resources");
    }
}
