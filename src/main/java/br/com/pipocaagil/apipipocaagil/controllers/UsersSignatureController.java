package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.controllers.exceptions.PaymentAuthorizationException;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.domain.representations.CardDetailsRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserLoginRepresentation;
import br.com.pipocaagil.apipipocaagil.jwt.JwtToken;
import br.com.pipocaagil.apipipocaagil.jwt.JwtUserDetailsService;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersService;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersSignatureService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
    private final UsersSignatureService signatureService;
    private final UsersService userService;

    @PostMapping
    public ResponseEntity<?> signatureToUser(
                 @RequestBody @Valid CardDetailsRepresentation cardDetailsRepresentation, HttpServletRequest request) {

        UserLoginRepresentation userLoginRepresentation = checkUser();
        if (!checkCard(cardDetailsRepresentation)){
            log.info("Unauthorized Card!");
            throw new PaymentAuthorizationException("Unauthorized Card!");
        }
        log.info("User {} is trying to sign up", userLoginRepresentation.getEmail());
        signatureService.signatureToUser(userLoginRepresentation, UserPermissionType.ROLE_SIGNED);
        JwtToken token = userDetailsService.getTokenAuthenticated(userLoginRepresentation.getEmail());
        return ResponseEntity.ok(token);
    }

    private UserLoginRepresentation checkUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userService.findByUsername(username);
        if (username == null){
            throw new AccessDeniedException("Access Denied. You must be logged in for this operation!");
        }
        return new UserLoginRepresentation(user.getUsername(), user.getPassword());
    }
    private static boolean checkCard(CardDetailsRepresentation cardDetailsRepresentation){
        var cardNumber = "4929857104078491";
        var cardHolderName = "Pipoca Agil";
        var securityCode = "415";
        var cardCpf = "09199308088";
        var cardExpirationDate = "27/06/2024";
        var cardBrand = "VISA";
        return  cardDetailsRepresentation.getCardNumber().equals(cardNumber) &&
                cardDetailsRepresentation.getCardHolderName().equals(cardHolderName) &&
                cardDetailsRepresentation.getSecurityCode().equals(securityCode) &&
                cardDetailsRepresentation.getExpirationDate().equals(cardExpirationDate) &&
                cardDetailsRepresentation.getCpf().equals(cardCpf) &&
                cardDetailsRepresentation.getCardBrand().equals(cardBrand);
    }
}
