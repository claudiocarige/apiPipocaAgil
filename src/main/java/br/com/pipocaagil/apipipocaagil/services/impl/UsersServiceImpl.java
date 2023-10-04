package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.repositories.UsersRepository;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
import br.com.pipocaagil.apipipocaagil.services.exceptions.DataIntegrityViolationException;
import br.com.pipocaagil.apipipocaagil.services.exceptions.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public Users findByEmail(String email) {
        Optional<Users> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new NoSuchElementException("E-mail não cadastrado!"));
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users insert(UsersRepresentation usersRepresentation) {
        usersRepresentation.setId(null);
        checkEmail(usersRepresentation);
        return userRepository.save(mapper.map(usersRepresentation, Users.class));
    }

    @Override
    public Users update(Long id, UsersRepresentation usersRepresentation) {
        usersRepresentation.setId(id);
        findById(usersRepresentation.getId());
        checkEmail(usersRepresentation);
        return userRepository.save(mapper.map(usersRepresentation, Users.class));
    }

    @Override
    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    @Override
    public List<Users> findByNameIgnoreCase(String firstName) {
        return userRepository.findByNameIgnoreCase(firstName);
    }

    @Override
    public List<Users> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstNamePart, String lastNamePart) {
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstNamePart, lastNamePart);
    }

    private void checkEmail(UsersRepresentation usersRepresentation) {
        Optional<Users> user = userRepository.findByEmail(usersRepresentation.getEmail());
        if (user.isPresent() && !user.get().getId().equals(usersRepresentation.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado! Favor revise sua requisição.");
        }
    }
}
