package br.com.pipocaagil.apipipocaagil.controllers;


import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserLoginRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Authenticate User",
            description = "Authenticate a user by providing credentials.",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersRepresentation.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<String> auth(@RequestBody UserLoginRepresentation userLoginRepresentation) {
        Users user = userService.findByUsername(userLoginRepresentation.getUsername());
        if (user == null || !user.getPassword().equals(userLoginRepresentation.getPassword())) {
            log.info("Invalid credentials");
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
        log.info("Successfully authenticated");
        return ResponseEntity.ok().body("Successfully authenticated");
    }
}
