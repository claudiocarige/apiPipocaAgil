package br.com.pipocaagil.apipipocaagil.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UsersController {

    @GetMapping
    public ResponseEntity<String> getUser(){
        String str = new String("{Pipoca Àgil}");
        return ResponseEntity.ok().body(str);
    }
}
