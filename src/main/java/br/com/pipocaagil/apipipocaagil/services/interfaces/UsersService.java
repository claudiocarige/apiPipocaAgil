package br.com.pipocaagil.apipipocaagil.services.interfaces;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserUpdateRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;

import java.time.LocalDate;
import java.util.List;

public interface UsersService {
    Users findById(Long id);
    Users findByUsername(String username);
    List<Users> findAll();
    Users insert(UsersRepresentation usersRepresentation);
    Users update(Long id, UserUpdateRepresentation usersRepresentation);
    void delete(Long id);
    List<Users> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstNamePart, String lastNamePart);
    List<Users> findByNameIgnoreCase(String firstName);
    List<Users> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);
    UserPermissionType findByRoleFromUsername(String username);
    void updateRoleToSigned(UserPermissionType role, Long id);
}

