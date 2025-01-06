package com.example.user.users.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

    private final UserAuthenticationProvider userAuthenticationProvider;
    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    public SecurityConfig(
            UserAuthenticationProvider userAuthenticationProvider,
            UserAuthenticationEntryPoint userAuthenticationEntryPoint
    ) {
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults()) // Enable CORS
                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
                .authorizeHttpRequests(requests -> requests
                        // Public routes
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/login", "/api/v1/users/register").permitAll()

                        // routes
                        .requestMatchers("/api/v1/users/create-client").hasAnyRole("ADMIN","AGENT")
                        .requestMatchers("/api/v1/users/clients").hasAnyRole("ADMIN","AGENT")
                        .requestMatchers("/api/v1/users/create-agent").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/agents").hasAnyRole("ADMIN","CLIENT")
                        .requestMatchers("/api/v1/users/change-password").hasRole("CLIENT")
                        .requestMatchers("/api/v1/users/creat-transaction").hasRole("CLIENT")
                        .requestMatchers("/api/v1/users/creat-subscription").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/users/delete/{id}").hasRole("ADMIN")

                        // Agent routes
                        .requestMatchers("/api/v1/users/client/**", "/api/v1/users/service/**", "/api/v1/users/serviceByAgent/**","/api/v1/users/subscriptions/**")
                        .hasAnyRole("AGENT","CLIENT")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        new JwtAuthFilter(userAuthenticationProvider),
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(userAuthenticationEntryPoint)
                )
                .build();
    }


}

