package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserPasswordRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.repositories.UsersRepository;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
import br.com.pipocaagil.apipipocaagil.services.exceptions.DataIntegrityViolationException;
import br.com.pipocaagil.apipipocaagil.services.exceptions.NoSuchElementException;
import br.com.pipocaagil.apipipocaagil.services.exceptions.PasswordInvalidException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Users findById(Long id) {
        Optional<Users> user = userRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("No Such Element"));
    }

    @Override
    public Users findByUsername(String username) {
        Optional<Users> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new NoSuchElementException("E-mail not registered! Please review your request."));
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<Users> findByNameIgnoreCase(String firstName) {
        return userRepository.findByNameIgnoreCase(firstName);
    }

    @Override
    public List<Users> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstNamePart, String lastNamePart) {
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstNamePart, lastNamePart);
    }

    @Override
    public List<Users> findByBirthdayBetween(LocalDate startDate, LocalDate endDate) {
        return userRepository.findByBirthdayBetween(startDate, endDate);
    }

    @Override
    public UserPermissionType findByRoleFromUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }

    @Override
    public Users insert(UsersRepresentation usersRepresentation) {
        usersRepresentation.setId(null);
        checkEmail(usersRepresentation, "insert");
        cheCkRole(usersRepresentation);
        usersRepresentation.setCreateDate(LocalDate.now());
        usersRepresentation.setPassword(passwordEncoder.encode(usersRepresentation.getPassword()));
        return userRepository.save(mapper.map(usersRepresentation, Users.class));
    }
    @Transactional
    @Override
    public Users update(Long id, UsersRepresentation usersRepresentation) {
        usersRepresentation.setId(id);
        Users user = findById(usersRepresentation.getId());
        checkEmail(usersRepresentation, "update");
        checkPassword(usersRepresentation, user);
        usersRepresentation.setCreateDate(user.getCreateDate());
        usersRepresentation.setRole(user.getRole());
        return userRepository.save(mapper.map(usersRepresentation, Users.class));
    }

    @Transactional
    @Override
    public void updatePassword(Long id, UserPasswordRepresentation pass){
        checkPassword(id, pass);
        Users user = findById(id);
        user.setPassword(passwordEncoder.encode(pass.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void checkEmail(UsersRepresentation usersRepresentation, String test) {
        Optional<Users> user = userRepository.findByUsername(usersRepresentation.getUsername());
        if (test.equals("insert")) {
            if (user.isPresent() && !user.get().getId().equals(usersRepresentation.getId())) {
                throw new DataIntegrityViolationException("E-mail already registered! Please review your request.");
            }
        } else {
            user = userRepository.findById(usersRepresentation.getId());
            if (user.isPresent() && !user.get().getUsername().equals(usersRepresentation.getUsername())) {
                throw new DataIntegrityViolationException("Email cannot be changed.");
            }
        }
    }

    private void cheCkRole(UsersRepresentation usersRepresentation){
        if (usersRepresentation.getRole() == null) {
            usersRepresentation.setRole(UserPermissionType.ROLE_USER);
        }else if(usersRepresentation.getRole().equals(UserPermissionType.ROLE_ADMIN)){
            usersRepresentation.setRole(usersRepresentation.getRole());
        }
    }

    private void checkPassword(UsersRepresentation usersRepresentation, Users user){
        if (!passwordEncoder.matches(usersRepresentation.getPassword(), user.getPassword())){
            usersRepresentation.setPassword(passwordEncoder.encode(usersRepresentation.getPassword()));
        }else{
            usersRepresentation.setPassword(user.getPassword());
        }
    }

    private void checkPassword(Long id, UserPasswordRepresentation pass){
        Users user = findById(id);
        if (!passwordEncoder.matches(pass.getOldPassword(), user.getPassword())){
            throw new PasswordInvalidException("Old password is invalid!");
        }
        if (!pass.getNewPassword().equals(pass.getConfirmPassword())){
            throw new PasswordInvalidException("New password and confirm password are different!");
        }
    }
}
