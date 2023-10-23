package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.repositories.UsersRepository;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
import br.com.pipocaagil.apipipocaagil.services.exceptions.DataIntegrityViolationException;
import br.com.pipocaagil.apipipocaagil.services.exceptions.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository userRepository;
    private final ModelMapper mapper;

    @Override
    public Users findById(Long id) {
        Optional<Users> user = userRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("No Such Element"));
    }

    @Override
    public Users findByUsername(String username) {
        Optional<Users> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new NoSuchElementException("E-mail não cadastrado!"));
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
    public Users insert(UsersRepresentation usersRepresentation) {
        usersRepresentation.setId(null);
        checkEmail(usersRepresentation, "insert");
        if (usersRepresentation.getRole() == null) {
            usersRepresentation.setRole(UserPermissionType.ROLE_USER);
        }
        usersRepresentation.setCreateDate(LocalDate.now());
        return userRepository.save(usersRepresentation.toUser(usersRepresentation));
    }

    @Override
    public Users update(Long id, UsersRepresentation usersRepresentation) {
        usersRepresentation.setId(id);
        Users users = findById(usersRepresentation.getId());
        checkEmail(usersRepresentation, "update");
        usersRepresentation.setCreateDate(users.getCreateDate());
        usersRepresentation.setRole(users.getRole());
        return userRepository.save(usersRepresentation.toUser(usersRepresentation));
    }

    @Override
    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void checkEmail(UsersRepresentation usersRepresentation, String test) {
        Optional<Users> user = userRepository.findByUsername(usersRepresentation.getEmail());
        if (test.equals("insert")) {
            if (user.isPresent() && !user.get().getId().equals(usersRepresentation.getId())) {
                throw new DataIntegrityViolationException("E-mail already registered! Please review your request.");
            }
        } else {
            user = userRepository.findById(usersRepresentation.getId());
            if (user.isPresent() && !user.get().getUsername().equals(usersRepresentation.getEmail())) {
                throw new DataIntegrityViolationException("Email cannot be changed.");
            }
        }
    }
}
