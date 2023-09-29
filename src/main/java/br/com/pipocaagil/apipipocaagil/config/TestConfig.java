package br.com.pipocaagil.apipipocaagil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
    @Bean
    public void startDB(){
        // TODO document why this method is empty
    }
}
