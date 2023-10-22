package br.com.pipocaagil.apipipocaagil.config;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


public class CorsConfig {


    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // Permite todos os domínios (isso deve ser mais restritivo em produção)
        config.addAllowedMethod("*"); // Permite todos os métodos HTTP (GET, POST, PUT, etc.)
        config.addAllowedHeader("*"); // Permite todos os cabeçalhos HTTP

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
