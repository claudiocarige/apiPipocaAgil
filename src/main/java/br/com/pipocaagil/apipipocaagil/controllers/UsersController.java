package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @PostMapping()
    public ResponseEntity<UsersRepresentation> insert(@Valid @RequestBody UsersRepresentation usersRepresentation){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(userService.insert(usersRepresentation).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/email")
    public ResponseEntity<UsersRepresentation> findByEmail(@RequestParam("email") String email){
        return ResponseEntity.ok().body(mapper.map(userService.findByEmail(email), UsersRepresentation.class));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsersRepresentation> update(@PathVariable Long id,
                                                      @Valid @RequestBody UsersRepresentation usersRepresentation){
        return ResponseEntity.ok().body(mapper.map(userService.update(id, usersRepresentation),
                                                                                        UsersRepresentation.class));
    }

}
