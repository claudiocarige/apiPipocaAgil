package br.com.pipocaagil.apipipocaagil.services;

import br.com.pipocaagil.apipipocaagil.domain.Users;

import java.util.List;

public interface UsersService {
    Users findById(Long id);
    List<Users> findAll();
    Users insert(Users user);
    Users update(Long id, Users user);
    void delete(Long id);
}
