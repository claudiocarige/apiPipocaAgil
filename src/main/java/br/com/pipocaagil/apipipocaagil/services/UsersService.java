package br.com.pipocaagil.apipipocaagil.services;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserLoginRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserPasswordRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;

import java.time.LocalDate;
import java.util.List;

public interface UsersService {
    Users findById(Long id);
    Users findByUsername(String username);
    List<Users> findAll();
    Users insert(UsersRepresentation usersRepresentation);
    Users update(Long id, UsersRepresentation usersRepresentation);
    void delete(Long id);
    List<Users> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstNamePart, String lastNamePart);
    List<Users> findByNameIgnoreCase(String firstName);
    List<Users> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);
    UserPermissionType findByRoleFromUsername(String username);
    void updatePassword(Long id, UserPasswordRepresentation pass);
    void updateRoleToSigned(UserLoginRepresentation userLoginRepresentation, UserPermissionType role);
}

