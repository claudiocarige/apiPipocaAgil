package br.com.pipocaagil.apipipocaagil.domain.representations;

import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersRepresentation {

    private Long id;
    @NotNull(message = "O campo NOME é requerido!")
    private String firstName;
    @NotNull(message = "O campo SOBRENOME é requerido!")
    private String lastName;
    @NotNull(message = "O campo EMAIL é requerido!")
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "O campo SENHA é requerido!")
    private String password;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createDate = LocalDate.now();
    private UserPermissionType role;
}
