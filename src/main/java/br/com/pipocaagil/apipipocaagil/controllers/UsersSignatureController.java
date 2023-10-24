package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.domain.representations.CardDetailsRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserLoginRepresentation;
import br.com.pipocaagil.apipipocaagil.jwt.JwtToken;
import br.com.pipocaagil.apipipocaagil.jwt.JwtUserDetailsService;
import br.com.pipocaagil.apipipocaagil.services.UsersSignatureService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/signature")
public class UsersSignatureController {

    private final JwtUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    private final UsersSignatureService signatureService;

    @PostMapping
    public ResponseEntity<?> signatureToUser(
                 @RequestBody @Valid CardDetailsRepresentation cardDetailsRepresentation, HttpServletRequest request) {
        UserLoginRepresentation userLoginRepresentation = checkUser();

        //configurar o pagamento com os dados do cartão
        signatureService.signatureToUser(userLoginRepresentation, UserPermissionType.ROLE_SIGNED);
        JwtToken token = userDetailsService.getTokenAuthenticated(userLoginRepresentation.getUsername());
        return ResponseEntity.ok(token);
    }

    private UserLoginRepresentation checkUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var password = authentication.getCredentials().toString();
        if (username == null){
            throw new AccessDeniedException("Access Denied. You must be logged in for this operation!");
        }
        return new UserLoginRepresentation(username, password);
    }
}
