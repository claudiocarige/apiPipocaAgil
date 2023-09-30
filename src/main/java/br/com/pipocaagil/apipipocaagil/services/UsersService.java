package br.com.pipocaagil.apipipocaagil.services;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;

import java.util.List;

public interface UsersService {
    Users findById(Long id);
    Users findByEmail(String email);
    List<Users> findAll();
    Users insert(UsersRepresentation usersRepresentation);
    Users update(Long id, UsersRepresentation usersRepresentation);
    void delete(Long id);
}
