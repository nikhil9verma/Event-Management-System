package com.example.Event_Manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 1. Allow credentials (cookies, auth headers)
        config.setAllowCredentials(true);

        // 2. Allow your Frontend URL (Adjust this if you use Angular/Vue)
        // Use patterns like "http://localhost:3000" for React
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));

        // 3. Allow standard headers (Authorization, Content-Type)
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // 4. Allow standard methods
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
