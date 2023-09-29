package br.com.pipocaagil.apipipocaagil.services;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service@RequiredArgsConstructor
public class DBService {

    private final UsersRepository usersRepository;

    public void startDB() {
        Users user01 = new Users(null, "Claudio", "ccarige@gmail.com", "123456", LocalDate.of(1974, 5, 9), LocalDate.now(), UserPermissionType.ADMIN);
        Users user02 = new Users(null, "Maria", "maria@gmail.com", "123456", LocalDate.of(1976, 9, 8), LocalDate.now(), UserPermissionType.USER);
        usersRepository.saveAll(Arrays.asList(user01,user02));
    }
}
