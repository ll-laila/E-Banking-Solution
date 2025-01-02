package com.example.user.users.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                .csrf(AbstractHttpConfigurer::disable)
                //.cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
                .authorizeHttpRequests(requests -> requests
                        // Public routes
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/login", "/api/v1/users/register").permitAll()

                        // routes
                        .requestMatchers("/api/v1/users/create-client").hasAnyRole("ADMIN","AGENT")
                        .requestMatchers("/api/v1/users/clients").hasAnyRole("ADMIN","AGENT")
                        .requestMatchers("/api/v1/users/create-agent").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/agents").hasRole("ADMIN")
                        .requestMatchers("/api/v1/users/change-password").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/users/delete/{id}").hasRole("ADMIN")

                        // Agent routes
                        .requestMatchers("/api/v1/users/client/**", "/api/v1/users/service/**", "/api/v1/users/serviceByAgent/**")
                        .hasRole("AGENT")

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Update as needed
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

