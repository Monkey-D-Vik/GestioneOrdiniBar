package com.bar.gestioneBar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Permetti credenziali
        config.setAllowCredentials(true);
        
        // Origini permesse
        config.addAllowedOrigin("http://localhost:8000");
        config.addAllowedOrigin("http://localhost:8001");
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost");
        
        // Headers permessi
        config.addAllowedHeader("*");
        
        // Metodi permessi
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        
        // Esponi gli headers necessari
        config.addExposedHeader("Access-Control-Allow-Origin");
        config.addExposedHeader("Access-Control-Allow-Credentials");
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
} 