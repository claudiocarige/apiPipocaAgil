package br.com.pipocaagil.apipipocaagil.config;

import br.com.pipocaagil.apipipocaagil.services.DBService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfig {

    private final DBService dbService;

    @Bean
    public void startDB() {
        this.dbService.startDB();
    }
}
