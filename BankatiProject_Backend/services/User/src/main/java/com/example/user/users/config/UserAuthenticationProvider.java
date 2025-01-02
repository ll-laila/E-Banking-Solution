package com.example.user.users.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.user.users.dto.UserDto;
import com.example.user.users.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserService userService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login) {
        try {
            Date now = new Date();
            Date validity = new Date(now.getTime() + 3600000); // 1 hour

            UserDto user = userService.findByLogin(login);

            log.info("Creating token for user: {}, Role: {}", login, user.getRole());

            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withSubject(login)
                    .withClaim("role", user.getRole() != null ? "ROLE_" + user.getRole().toString() : "UNKNOWN")
                    .withIssuedAt(now)
                    .withExpiresAt(validity)
                    .sign(algorithm);
        } catch (Exception e) {
            log.error("Token creation error", e);
            throw new RuntimeException("Token creation failed", e);
        }
    }

    public Authentication validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .build();

            DecodedJWT decoded = verifier.verify(token);
            String subject = decoded.getSubject();
            String role = decoded.getClaim("role").asString();

            // Enhanced logging with more token details
            log.info("Token Validation Details:");
            log.info("Subject: {}", subject);
            log.info("Role: {}", role);
            log.info("Issued At: {}", decoded.getIssuedAt());
            log.info("Expires At: {}", decoded.getExpiresAt());

            UserDto user = userService.findByLogin(subject);

            // Detailed user logging
            log.info("User Details:");
            log.info("User ID: {}", user.getId());
            log.info("User Phone: {}", user.getPhoneNumber());
            log.info("User Role: {}", user.getRole());

            // Create authentication object
            List<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority(role)
            );

            // Create and return authentication object
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user,           // Principal
                    null,           // Credentials
                    authorities     // Authorities
            );

            log.info("Authentication created successfully: {}", authentication);
            return authentication;

        } catch (JWTVerificationException e) {
            log.error("JWT Verification Failed for token: {}", token, e);
            throw new BadCredentialsException("Invalid token", e);
        } catch (Exception e) {
            log.error("Unexpected Token Validation Error", e);
            throw new BadCredentialsException("Token validation failed", e);
        }
    }
}