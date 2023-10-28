package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.controllers.exceptions.PaymentAuthorizationException;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import br.com.pipocaagil.apipipocaagil.domain.representations.CardDetailsRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserLoginRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserPasswordRepresentation;
import br.com.pipocaagil.apipipocaagil.jwt.JwtToken;
import br.com.pipocaagil.apipipocaagil.jwt.JwtUserDetailsService;
import br.com.pipocaagil.apipipocaagil.services.impl.ContextCheckImpl;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersSignatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/users/signature")
@Tag(name = "Signing users", description = "Contains all operations related to the resources Signing users.")
public class UsersSignatureController {

    private final JwtUserDetailsService userDetailsService;
    private final UsersSignatureService signatureService;
    private final ContextCheckImpl contextCheck;

    @PostMapping(value = "/payment")
    @Operation(summary = "Signing users ",
            description = "Updates the user's password by passing a JSON representation of the user's old password and a new password and its confirmation.",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserPasswordRepresentation.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<?> signatureToUser(
                 @RequestBody @Valid CardDetailsRepresentation cardDetailsRepresentation, HttpServletRequest request) {

        UserLoginRepresentation userLoginRepresentation = contextCheck.checkUser();
        if (!checkCard(cardDetailsRepresentation)){
            log.info("Unauthorized Card!");
            throw new PaymentAuthorizationException("Unauthorized Card!");
        }
        log.info("User {} is trying to sign up", userLoginRepresentation.getEmail());
        signatureService.signatureToUser(userLoginRepresentation, UserPermissionType.ROLE_SIGNED);
        JwtToken token = userDetailsService.getTokenAuthenticated(userLoginRepresentation.getEmail());
        return ResponseEntity.ok(token);
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
