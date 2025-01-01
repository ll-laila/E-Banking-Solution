package com.example.user.users.config;

import com.example.user.users.dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Component
@Slf4j
public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        try {
            logAuthenticationDetails(request, authException);

            // Prepare standard unauthorized response
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ErrorDto errorResponse = new ErrorDto(
                    "Unauthorized",
                    "Full authentication is required to access this resource",
                    request.getRequestURI()
            );

            // Safely write response
            new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);

        } catch (Exception e) {
            log.error("Error in UserAuthenticationEntryPoint: {}", e.getMessage(), e);

            // Fallback for unexpected errors
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An unexpected error occurred while processing the authentication entry point.");
        }
    }


    private void logAuthenticationDetails(HttpServletRequest request, AuthenticationException authException) {
        // Detailed logging
        log.error("Authentication Entry Point Invoked");
        log.error("Request URI: {}", request.getRequestURI());
        log.error("Request Method: {}", request.getMethod());

        // Thread and context investigation
        log.error("Current Thread: {}", Thread.currentThread().getName());

        // Investigate SecurityContextHolder
        SecurityContext context = SecurityContextHolder.getContext();
        log.error("SecurityContext Object: {}", context);
        log.error("SecurityContext HashCode: {}", context.hashCode());

        // Check authentication in different ways
        Authentication authentication = context.getAuthentication();
        log.error("Authentication from Context: {}", authentication);

        // Additional thread-local context investigation
        try {
            SecurityContext threadLocalContext = SecurityContextHolder.getContext();
            log.error("ThreadLocal Context: {}", threadLocalContext);
            log.error("ThreadLocal Authentication: {}",
                    threadLocalContext.getAuthentication());
        } catch (Exception e) {
            log.error("Error accessing ThreadLocal context", e);
        }

        // Authorization header logging
        String authHeader = request.getHeader("Authorization");
        log.error("Authorization Header: {}",
                authHeader != null ? "Present (length: " + authHeader.length() + ")" : "Not provided");

        // Exception details
        log.error("Authentication Exception: {}",
                authException != null ? authException.getMessage() : "Unknown error",
                authException);
        log.error("Authentication Exception Type: {}", authException.getClass().getSimpleName());
        log.error("Authentication Exception Message: {}", authException.getMessage());
    }
}