package br.com.pipocaagil.apipipocaagil.controllers;


import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserLoginRepresentation;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AutenticationController {

    private final UsersService userService;

    @PostMapping(value = "/auth")
    public ResponseEntity<String> auth(@RequestBody UserLoginRepresentation userLoginRepresentation) {
        Users user = userService.findByUsername(userLoginRepresentation.getUsername());
        if (user == null || !user.getPassword().equals(userLoginRepresentation.getPassword())) {
            log.info("Credenciais inválidas");
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        }
        log.info("Autenticado com sucesso");
        return ResponseEntity.ok().body("Autenticado com sucesso");
    }
}
