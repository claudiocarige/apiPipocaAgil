package br.com.pipocaagil.apipipocaagil.services.interfaces;

import br.com.pipocaagil.apipipocaagil.jwt.JwtToken;

public interface AuthService {
    JwtToken loginUser(String email, String password);
}
