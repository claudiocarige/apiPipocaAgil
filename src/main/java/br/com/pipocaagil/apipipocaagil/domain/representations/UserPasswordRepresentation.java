package br.com.pipocaagil.apipipocaagil.domain.representations;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordRepresentation {

    @NotBlank(message = "OldPassword is required")
    private String oldPassword;
    @NotBlank(message = "NewPassword is required")
    private String newPassword;
    @NotBlank(message = "ConfirmPassword is required")
    private String confirmPassword;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPasswordRepresentation that = (UserPasswordRepresentation) o;
        return Objects.equals(oldPassword, that.oldPassword) && Objects.equals(newPassword, that.newPassword) && Objects.equals(confirmPassword, that.confirmPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(oldPassword, newPassword, confirmPassword);
    }
}
