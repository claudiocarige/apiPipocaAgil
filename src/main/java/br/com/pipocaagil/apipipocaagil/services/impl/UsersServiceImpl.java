package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.entities.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserUpdateRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.repositories.UsersRepository;
import br.com.pipocaagil.apipipocaagil.services.exceptions.DataIntegrityViolationException;
import br.com.pipocaagil.apipipocaagil.services.exceptions.NoSuchElementException;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;


@Slf4j
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
        Optional<Users> user = userRepository.findByUsername(username.toLowerCase());
        return user.orElseThrow(() -> new NoSuchElementException("E-mail not registered! Please review your request."));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsernameIgnoreCase(username.toLowerCase());
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
        return userRepository.findRoleByUsername(username.toLowerCase());
    }

    @Override
    public Users insert(UsersRepresentation usersRepresentation) {
        usersRepresentation.setId(null);
        usersRepresentation.setEmail(usersRepresentation.getEmail().toLowerCase());
        checkEmail(usersRepresentation);
        usersRepresentation.setRole(UserPermissionType.ROLE_USER.name());
        usersRepresentation.setCreateDate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        usersRepresentation.setPassword(passwordEncoder.encode(usersRepresentation.getPassword()));
        return userRepository.save(mapper.map(usersRepresentation, Users.class));
    }

    @Transactional
    @Override
    public Users update(Long id, UserUpdateRepresentation usersRepresentation) {
        Users user = findById(id);
        user.setFirstName(usersRepresentation.getFirstName());
        user.setLastName(usersRepresentation.getLastName());
        user.setBirthday(usersRepresentation.getBirthday());
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateRoleToSigned(UserPermissionType role, Long id) {
        Users user = findById(id);
        user.setRole(role);
        userRepository.updateRoleToSigned(user.getRole(), user.getId());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void checkEmail(UsersRepresentation usersRepresentation) {
        Optional<Users> user = userRepository.findByUsername(usersRepresentation.getEmail());
        if (user.isPresent() && !user.get().getId().equals(usersRepresentation.getId())) {
            throw new DataIntegrityViolationException("E-mail already registered! Please review your request.");
        }
    }
}
