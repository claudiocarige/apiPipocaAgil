package br.com.pipocaagil.apipipocaagil.services.interfaces;

import br.com.pipocaagil.apipipocaagil.domain.representations.UserPasswordRepresentation;

public interface PasswordUserService {

    void updatePassword(Long id, UserPasswordRepresentation pass);
    String passwordRecovery(String email);
    String randomPasswordGenerator();

}
