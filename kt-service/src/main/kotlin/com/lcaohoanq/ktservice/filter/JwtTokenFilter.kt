package com.lcaohoanq.ktservice.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.lcaohoanq.common.apis.ApiError
import com.lcaohoanq.ktservice.component.JwtTokenUtils
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Deprecated("We turn to centralized the auth logic on Gateway Service")
@Component
@Slf4j
class JwtTokenFilter(
    private val jwtTokenUtil: JwtTokenUtils,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}

    @Value("\${api.prefix}")
    private val apiPrefix: String? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val servletPath = request.servletPath
            val method = request.method

            log.debug("Processing request: {} {}", method, servletPath)


            // Check if public endpoint
            val isPublic = isPublicEndpoint(servletPath, request)
            log.debug("Is public endpoint? {}", isPublic)

            if (servletPath == "/error" || servletPath == "/favicon.ico" || servletPath == "/h2-console" || isSwaggerEndpoint(
                    servletPath
                ) || isGraphqlEndpoint(servletPath) || isActuatorEndpoint(servletPath) || isPublic
            ) {
                log.debug("Bypassing authentication for: (Method: {} - URL: {})", method, servletPath)
                filterChain.doFilter(request, response)
                return
            }

            // Token validation
            val token = extractToken(request)
            if (token == null) {
                log.debug("No token found for: {} {}", method, servletPath)
                sendErrorResponse(
                    response, "No authentication token provided",
                    HttpStatus.UNAUTHORIZED, request.requestURI
                )
                return
            }

            try {
                // Try to parse the token first - this will catch malformed tokens
                val email = jwtTokenUtil.extractEmail(token)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userDetailsService.loadUserByUsername(email) as User
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        SecurityContextHolder.getContext().authentication =
                            createAuthentication(userDetails, request)
                    }
                }
            } catch (e: com.lcaohoanq.common.exceptions.JwtAuthenticationException) {
                sendErrorResponse(
                    response, e.message,
                    HttpStatus.UNAUTHORIZED, request.requestURI
                )
                return
            } catch (e: MalformedJwtException) {
                // Explicitly catch malformed token exceptions
                sendErrorResponse(
                    response, "Malformed authentication token",
                    HttpStatus.UNAUTHORIZED, request.requestURI
                )
                return
            } catch (e: IllegalArgumentException) {
                sendErrorResponse(
                    response, "Malformed authentication token",
                    HttpStatus.UNAUTHORIZED, request.requestURI
                )
                return
            } catch (e: ExpiredJwtException) {
                sendErrorResponse(
                    response, "Token has expired",
                    HttpStatus.UNAUTHORIZED, request.requestURI
                )
                return
            }

            // Continue to Spring Security's role checking
            filterChain.doFilter(request, response)
        } catch (e: AccessDeniedException) {
            sendErrorResponse(
                response, "Access denied - Insufficient privileges",
                HttpStatus.FORBIDDEN, request.requestURI
            )
        } catch (e: Exception) {
            log.error("Error processing request", e)
            sendErrorResponse(
                response, "Authentication failed",
                HttpStatus.UNAUTHORIZED, request.requestURI
            )
        } finally {
            SecurityContextHolder.clearContext()
        }
    }

    @Throws(IOException::class)
    private fun sendErrorResponse(
        response: HttpServletResponse, message: String?,
        status: HttpStatus, path: String
    ) {
        response.contentType = "application/json"
        response.status = status.value()

        val errorResponse = ApiError(
            message = message,
            statusCode = status.value(),
            isSuccess = false,
            data = mapOf(
                "timestamp" to System.currentTimeMillis(),
                "path" to path
            )
        )

        ObjectMapper().writeValue(response.outputStream, errorResponse)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7)
        }
        return null
    }

    private fun createAuthentication(
        userDetails: User,
        request: HttpServletRequest
    ): Authentication {
        val auth = UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.authorities
        )
        auth.details = WebAuthenticationDetailsSource().buildDetails(request)
        return auth
    }

    private fun isPublicEndpoint(path: String, request: HttpServletRequest): Boolean {
        val method = request.method
        log.debug("Checking public endpoint: {} {}", method, path)


        // Allow all HTTP methods for these endpoints
        if (path == "$apiPrefix/auth/login" ||
            path == "$apiPrefix/auth/register" ||
            path == "$apiPrefix/forgot-password" ||
            path.contains("experiments") ||
            path == "/error"
        ) {
            log.debug("Matched basic public endpoint")
            return true
        }

        // Check roles endpoint
        if (path.startsWith("$apiPrefix/roles")) {
            val isGet = request.method == "GET" || request.method == "POST"
            log.debug("Roles endpoint - Is GET? {}", isGet)
            return isGet
        }

        // Check users endpoint
        if (path.startsWith("$apiPrefix/users")) {
            val isGet = request.method == "GET" && path != "$apiPrefix/users/all"
            log.debug("Users endpoint - Is GET? {}", isGet)
            return isGet
        }

        if (path.startsWith("$apiPrefix/categories")) {
            val isGet = request.method == "GET"
            log.debug("Categories endpoint - Is GET? {}", isGet)
            return isGet
        }

        return false
    }

    private fun isSwaggerEndpoint(path: String): Boolean {
        return path.contains("/swagger-ui") ||
                path.contains("/api-docs") ||
                path.contains("/swagger-resources")
    }

    private fun isGraphqlEndpoint(path: String): Boolean {
        return path.contains("/graphiql") || path.contains("graphql")
    }

    private fun isActuatorEndpoint(path: String): Boolean{
        return path.contains("/actuator")
    }
}
