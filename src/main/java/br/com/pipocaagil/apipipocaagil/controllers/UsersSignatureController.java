package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.domain.entities.SignatureData;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.services.exceptions.NoSuchElementException;
import br.com.pipocaagil.apipipocaagil.services.interfaces.SignatureDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user-signature")
@Tag(name = "Signing users", description = "Contains all operations related to the resources Signing users.")
public class UsersSignatureController {

    private final SignatureDataService signatureDataService;
    private final ModelMapper modelMapper;

    @GetMapping
    @Operation(summary = "Find all users Signature ",
            description = "Performs credit card payment for monthly subscribers.",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignatureData.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<List<UsersRepresentation>> findUsersWithSignature() {
        log.info("Find all users Signature" );
        return ResponseEntity.ok().body(
                signatureDataService.findUsersWithSignature().stream().map(
                        users -> modelMapper.map(users, UsersRepresentation.class)
                ).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find user Signature by UserId",
            description = "Find user by id with Subscription in Pipoca Ágil.",
            tags = {"Signature"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignatureData.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<UsersRepresentation> findSignatureByUserId(@PathVariable Long id) {
        log.info("Find user by id: {}", id);
        var signatureData = signatureDataService.findSignatureByUserId(id);
        if (signatureData == null) {
            throw new NoSuchElementException("User not found");
        }
        return ResponseEntity.ok().body(modelMapper.map(signatureData.getUser(), UsersRepresentation.class));
    }
}
