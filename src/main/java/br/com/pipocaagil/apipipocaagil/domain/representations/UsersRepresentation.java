package br.com.pipocaagil.apipipocaagil.domain.representations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersRepresentation {

    private Long id;

    @NotNull(message = "The NAME field is required!")
    @Size(max = 60)
    private String firstName;

    @NotNull(message = "The LAST NAME field is required!")
    @Size(max = 60)
    private String lastName;


    @Pattern(regexp = "^[a-zA-Z0-9._+-]+@[a-z]+\\.[a-z]{2,}(?:\\.[a-z]{2,3})?$", message = "Email format is invalid")
    @NotNull(message = "The EMAIL field is required!")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "The PASSWORD field is required!")
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$", message = "Password format is invalid")
    private String password;

    @NotNull(message = "The BIRTHDAY field is required!")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createDate;

    private String role;
}
