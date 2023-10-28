package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.domain.representations.UserPasswordRepresentation;
import br.com.pipocaagil.apipipocaagil.services.emailservice.EmailSendingServiceImpl;
import br.com.pipocaagil.apipipocaagil.services.impl.ContextCheckImpl;
import br.com.pipocaagil.apipipocaagil.services.interfaces.PasswordUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/password")
@Tag(name = "Password", description = "Contains all operations related to the resources for reset, editing, deleting a Password.")
public class PasswordUserController {

    private final PasswordUserService passwordUserService;
    private final ContextCheckImpl contextCheck;
    private final EmailSendingServiceImpl emailSendingService;

    @PatchMapping(value = "/{id}")
    @Operation(summary = "Update your password ",
            description = "Updates the user's password by passing a JSON representation of the user's old password and a new password and its confirmation.",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserPasswordRepresentation.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @Valid @RequestBody UserPasswordRepresentation pass) {
        contextCheck.checkUser(id);
        passwordUserService.updatePassword(id, pass);
        var message = "Password Changed Successfully";
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Message", message);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/reset/{email}")
    @Operation(summary = "Reset your password ",
            description = "Reset the user's password by passing a JSON representation of the user's old password and a new password and its confirmation.",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<String> resetPassword(@PathVariable String email) throws MessagingException {
        var password = passwordUserService.resetPassword(email);
        emailSendingService.sendEmail(email,
                "Recuperação de senha!", password);
        return ResponseEntity.ok().body("Password sent to the E-mail!");
    }


}
