package br.com.pipocaagil.apipipocaagil.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfig {

    @Bean
    public void startDB() {
    }
}
