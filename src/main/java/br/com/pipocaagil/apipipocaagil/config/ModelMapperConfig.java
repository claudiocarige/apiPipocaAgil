package br.com.pipocaagil.apipipocaagil.config;

import br.com.pipocaagil.apipipocaagil.domain.entities.Users;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(new PropertyMap<Users, UsersRepresentation>() {
            @Override
            protected void configure() {
                map().setEmail(source.getUsername());
            }
        });

        mapper.addMappings(new PropertyMap<UsersRepresentation, Users>() {
            @Override
            protected void configure() {
                map().setUsername(source.getEmail());
            }
        });

        return mapper;
    }
}
