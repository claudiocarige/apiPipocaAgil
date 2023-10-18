package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService userService;
    private final ModelMapper mapper;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsersRepresentation> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(userService.findById(id), UsersRepresentation.class));
    }

    @GetMapping(value = "/username")
    public ResponseEntity<UsersRepresentation> findByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok().body(mapper.map(userService.findByUsername(username), UsersRepresentation.class));
    }

    @GetMapping()
    public ResponseEntity<List<UsersRepresentation>> findAll() {
        return ResponseEntity.ok().body(toRepresentation(userService.findAll()));
    }

    @GetMapping(value = "/name/firstname")
    public ResponseEntity<List<UsersRepresentation>> findByNameIgnoreCase(@RequestParam("firstName") String firstName) {
        return ResponseEntity.ok().body(toRepresentation(userService.findByNameIgnoreCase(firstName)));
    }

    @GetMapping(value = "/firtname-lastname")
    public ResponseEntity<List<UsersRepresentation>> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(@RequestParam("firstNamePart") String firstNamePart,
                                                                                                                       @RequestParam("lastNamePart") String lastNamePart) {
        return ResponseEntity.ok().body(toRepresentation(userService.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstNamePart, lastNamePart)));
    }

    @GetMapping(value = "/birthday")
    public ResponseEntity<List<UsersRepresentation>> findByBirthdayBetween(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate){
        return ResponseEntity.ok().body(toRepresentation(userService.findByBirthdayBetween(startDate, endDate)));
    }

    @PostMapping()
    public ResponseEntity<UsersRepresentation> insert(@Valid @RequestBody UsersRepresentation usersRepresentation) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(userService.insert(usersRepresentation).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsersRepresentation> update(@PathVariable Long id,
                                                      @Valid @RequestBody UsersRepresentation usersRepresentation) {
        return ResponseEntity.ok().body(mapper.map(userService.update(id, usersRepresentation),
                UsersRepresentation.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UsersRepresentation> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private List<UsersRepresentation> toRepresentation(List<Users> users) {
        return users
                .stream()
                .map(x -> mapper.map(x, UsersRepresentation.class))
                .collect(Collectors.toList());
    }
}
