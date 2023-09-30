package br.com.pipocaagil.apipipocaagil.services.impl;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.repositories.UsersRepository;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
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
        return user.orElseThrow();
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users insert(UsersRepresentation usersRepresentation) {
        usersRepresentation.setId(null);
        return userRepository.save(mapper.map(usersRepresentation,Users.class));
    }

    @Override
    public Users update(Long id, UsersRepresentation usersRepresentation) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

}
