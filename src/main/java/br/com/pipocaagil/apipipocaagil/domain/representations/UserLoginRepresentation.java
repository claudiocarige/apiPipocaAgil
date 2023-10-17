package br.com.pipocaagil.apipipocaagil.domain.representations;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginRepresentation {

    @NotBlank
    @Email(message = "Email format is invalid", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}
