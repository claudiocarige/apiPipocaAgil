package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.representations.UsersRepresentation;
import br.com.pipocaagil.apipipocaagil.services.EmailSendingService;
import br.com.pipocaagil.apipipocaagil.services.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Contains all operations related to the resources for registering, editing, deleting, and reading a user.")
public class UsersController {

    private final UsersService userService;
    private final EmailSendingService emailSendingService;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Find a User by id", description = "Find a User by Id",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersRepresentation.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<UsersRepresentation> findById(@PathVariable Long id) {
        UsersRepresentation usersRepresentation = new UsersRepresentation();
        return ResponseEntity.ok().body(usersRepresentation.toRepresentation(userService.findById(id)));
    }

    @GetMapping(value = "/email")
    @Operation(summary = "Find a User by E-mail", description = "Find a User by E-mail",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersRepresentation.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<UsersRepresentation> findByEmail(@RequestParam("email") String email) {
        UsersRepresentation usersRepresentation = new UsersRepresentation();
        return ResponseEntity.ok().body(usersRepresentation.toRepresentation(userService.findByUsername(email)));
    }

    @GetMapping()
    @Operation(summary = "Finds All Users", description = "Finds All Users",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UsersRepresentation.class))
                                    )
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<List<UsersRepresentation>> findAll() {
        return ResponseEntity.ok().body(toConvertCollection(userService.findAll()));
    }

    @GetMapping(value = "/name/firstname")
    @Operation(summary = "Find by firstname ignore case ", description = "Find by firstname ignore case",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UsersRepresentation.class))
                                    )
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<List<UsersRepresentation>> findByNameIgnoreCase(@RequestParam("firstName") String firstName) {
        return ResponseEntity.ok().body(toConvertCollection(userService.findByNameIgnoreCase(firstName)));
    }

    @GetMapping(value = "/firtname-lastname")
    @Operation(summary = "Find by firstname and lastname ignore case ", description = "Find by firstname and lastname ignore case",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UsersRepresentation.class))
                                    )
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<List<UsersRepresentation>> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(@RequestParam("firstNamePart") String firstNamePart,
                                                                                                                       @RequestParam("lastNamePart") String lastNamePart) {
        return ResponseEntity.ok().body(toConvertCollection(userService.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstNamePart, lastNamePart)));
    }

    @GetMapping(value = "/birthday")
    @Operation(summary = "Find Users by birthday between two dates", description = "Find Users by birthday between two dates",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = UsersRepresentation.class))
                                    )
                            }),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<List<UsersRepresentation>> findByBirthdayBetween(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        return ResponseEntity.ok().body(toConvertCollection(userService.findByBirthdayBetween(startDate, endDate)));
    }

    @PostMapping()
    @Operation(summary = "Adds a new User",
            description = "Adds a new user by passing a JSON representation of the user to be added.",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = UsersRepresentation.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<UsersRepresentation> insert(@Valid @RequestBody UsersRepresentation usersRepresentation) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
                .buildAndExpand(userService.insert(usersRepresentation).getId()).toUri();

        emailSendingService.sendOrderConfirmationEmail(usersRepresentation.getEmail(),
                "Bem vindo ao Pipoca Ágil", String.format((usersRepresentation.getFirstName() +" "+ usersRepresentation.getLastName())));
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update a User",
            description = "Updates a user by passing a JSON representation of the user to be added.",
            tags = {"Users"},
            responses = {
                    @ApiResponse(description = "Update", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsersRepresentation.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<UsersRepresentation> update(@PathVariable Long id,
                                                      @Valid @RequestBody UsersRepresentation usersRepresentation) {
        return ResponseEntity.ok().body(usersRepresentation.toRepresentation(userService.update(id, usersRepresentation)));
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a User by Id",
            description = "Deletes a User by id.",
            tags = {"Users"},
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<UsersRepresentation> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private List<UsersRepresentation> toConvertCollection(List<Users> users) {
        UsersRepresentation usersRepresentation = new UsersRepresentation();
        return users
                .stream()
                .map(usersRepresentation::toRepresentation)
                .toList();
    }
}
