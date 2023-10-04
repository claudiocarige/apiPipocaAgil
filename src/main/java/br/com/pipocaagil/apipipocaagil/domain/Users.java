package br.com.pipocaagil.apipipocaagil.domain;

import br.com.pipocaagil.apipipocaagil.domain.enums.UserPermissionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createDate = LocalDate.now();
    private UserPermissionType role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(id, users.id) && Objects.equals(email, users.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

}
