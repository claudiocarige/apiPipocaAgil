package br.com.pipocaagil.apipipocaagil.controllers;


import br.com.pipocaagil.apipipocaagil.controllers.exceptions.StandardError;
import br.com.pipocaagil.apipipocaagil.domain.representations.UserLoginRepresentation;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.jwt.JwtToken;
import br.com.pipocaagil.apipipocaagil.jwt.JwtUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping(value = "/auth")
    @Operation(summary = "Authenticate User",
            description = "Authenticates a user by passing a JSON representation with email credentials and password.",
            tags = {"Authentication"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersRepresentation.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<?> auth(@RequestBody @Valid UserLoginRepresentation userLoginRepresentation,
                                         HttpServletRequest request) {
        log.info("Started authentication process by EMAIL {}", userLoginRepresentation.getEmail());
        try {
            var token = authService.loginUser(userLoginRepresentation.getEmail(), userLoginRepresentation.getPassword());
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.warn("Bad Credentials from username '{}'", userLoginRepresentation.getEmail());
        }
        return ResponseEntity.badRequest().
                body(new StandardError(System.currentTimeMillis(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Credentials. There is an error in the email or password.",
                        request.getRequestURI()));
    }
}
