package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.entities.Users;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserPasswordRepresentation;
import br.com.pipocaagil.apipipocaagil.repositories.UsersRepository;
import br.com.pipocaagil.apipipocaagil.services.exceptions.PasswordInvalidException;
import br.com.pipocaagil.apipipocaagil.services.interfaces.PasswordUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class PasswordUserServiceImpl implements PasswordUserService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersServiceImpl userService;

    @Transactional
    @Override
    public void updatePassword(Long id, UserPasswordRepresentation pass){
        Users user = checkPassword(id, pass);
        userService.findById(id);
        user.setPassword(passwordEncoder.encode(pass.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public String passwordRecovery(String email) {
        var password = randomPasswordGenerator();
        userRepository.resetPassword(passwordEncoder.encode(password), email);
        return password;
    }

    private Users checkPassword(Long id, UserPasswordRepresentation pass){
        Users user = userService.findById(id);
        if (!passwordEncoder.matches(pass.getOldPassword(), user.getPassword())){
            throw new PasswordInvalidException("Old password is invalid!");
        }
        if (!pass.getNewPassword().equals(pass.getConfirmPassword())){
            throw new PasswordInvalidException("New password and confirm password are different!");
        }
        return user;
    }

    public String randomPasswordGenerator() {
        Random random = new Random();
        var characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&+=!";
        var regexPattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        while (true) {
            StringBuilder randomPassword = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                var index = random.nextInt(characters.length());
                randomPassword.append(characters.charAt(index));
            }
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(randomPassword);
            if (matcher.matches()) {
                return randomPassword.toString();
            }
        }
    }
}
