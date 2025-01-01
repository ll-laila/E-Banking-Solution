package com.example.user.users.config;

import com.example.user.users.dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthenticationProvider authenticationProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String requestURI = request.getRequestURI();
            log.info("Processing Request - URI: {}, Method: {}", requestURI, request.getMethod());

            // Skip filtering for public endpoints
            if (shouldSkip(request)) {
                log.info("Skipping JWT filter for URI: {}", requestURI);
                filterChain.doFilter(request, response);
                return;
            }

            // Extract token from request
            String token = extractTokenFromRequest(request);

            if (token != null) {
                log.info("Token found. Validating...");
                try {
                    Authentication authentication = authenticationProvider.validateToken(token);
                    if (authentication != null) {
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        log.info("Authentication Successful - Principal: {}, Authorities: {}",
                                authentication.getPrincipal(), authentication.getAuthorities());
                    } else {
                        log.warn("Authentication validation returned null");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Invalid token.");
                        return;
                    }
                } catch (BadCredentialsException e) {
                    log.error("Invalid Credentials Error", e);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid credentials.");
                    return;
                } catch (Exception ex) {
                    log.error("Unexpected Error during token validation", ex);
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("An unexpected error occurred.");
                    return;
                }
            } else {
                log.warn("No token found in request.");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing token.");
                return;
            }

            // Continue with the filter chain
            filterChain.doFilter(request, response);

        } finally {
            // Ensure the SecurityContext is cleared after the request is processed
            SecurityContextHolder.clearContext();
        }
    }

    /*@Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        try {
            // Detailed token extraction logging
            String token = extractTokenFromRequest(request);

            log.debug("Filter Processing Request");
            log.debug("Request URI: {}", request.getRequestURI());
            log.debug("Token Extracted: {}", token != null ? "Yes" : "No");

            if (StringUtils.hasText(token)) {
                // Validate token and get authentication
                Authentication authentication = authenticationProvider.validateToken(token);

                if (authentication != null) {
                    // Explicitly set authentication
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);

                    log.info("Authentication Successfully Set in Context");
                    log.info("Principal: {}", authentication.getPrincipal());
                    log.info("Authorities: {}", authentication.getAuthorities());
                } else {
                    log.warn("Token validation returned null authentication");
                }
            }

            // Continue filter chain
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JWT Authentication Error", e);

            // Clear security context only in case of error
            //SecurityContextHolder.clearContext();

            // Handle error response
            handleAuthenticationError(response, e);
        }
    }

    private void handleAuthenticationError(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorDto errorResponse = new ErrorDto(
                "Authentication Failed",
                e.getMessage(),
                "/error"
        );

        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }*/
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("Authorization Header: {}", bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    private boolean shouldSkip(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/v1/users/login") || path.startsWith("/api/v1/users/register");
    }
}
