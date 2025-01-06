package com.example.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {


    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // Allow Angular frontend origin
        corsConfiguration.addAllowedOrigin("http://localhost:4200");

        // Allow all methods
        corsConfiguration.addAllowedMethod("*");

        // Allow all headers
        corsConfiguration.addAllowedHeader("*");

        // Enable credentials
        corsConfiguration.setAllowCredentials(true);

        // Expose necessary headers
        corsConfiguration.addExposedHeader("Authorization");
        corsConfiguration.addExposedHeader("Access-Control-Allow-Origin");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(source);
    }
}