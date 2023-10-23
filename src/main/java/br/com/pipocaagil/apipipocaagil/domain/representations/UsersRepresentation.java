package br.com.pipocaagil.apipipocaagil.domain.representations;

import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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

    @Email(message = "Email format is invalid", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    @NotNull(message = "The USERNAME field is required!")
    private String email;//colocar como e-mail e

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

    private UserPermissionType role;

    public UsersRepresentation toRepresentation(Users obj){
        this.id = obj.getId();
        this.firstName = obj.getFirstName();
        this.lastName = obj.getLastName();
        this.email = obj.getUsername();
        this.password = obj.getPassword();
        this.birthday = obj.getBirthday();
        this.createDate = obj.getCreateDate();
        this.role = obj.getRole();
        return this;
    }

    public Users toUser(UsersRepresentation obj){
        Users user = new Users();
        user.setId(obj.getId());
        user.setFirstName(obj.getFirstName());
        user.setLastName(obj.getLastName());
        user.setUsername(obj.getEmail());
        user.setPassword(obj.getPassword());
        user.setBirthday(obj.getBirthday());
        user.setCreateDate(obj.getCreateDate());
        user.setRole(obj.getRole());
        return user;
    }
}
