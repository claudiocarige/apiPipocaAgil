package br.com.pipocaagil.apipipocaagil.domain.representations;

import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginRepresentation {

    @NotBlank
    @Email(message = "Email format is invalid", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String email;
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}
