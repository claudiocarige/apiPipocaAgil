package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService userService;
    private final ModelMapper mapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsersRepresentation> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(mapper.map(userService.findById(id), UsersRepresentation.class));
    }

    @GetMapping()
    public ResponseEntity<List<UsersRepresentation>> findAll(){
        return ResponseEntity.ok().body(userService.findAll()
                .stream()
                .map(x -> mapper.map(x, UsersRepresentation.class))
                .collect(Collectors.toList()));
    }
}
