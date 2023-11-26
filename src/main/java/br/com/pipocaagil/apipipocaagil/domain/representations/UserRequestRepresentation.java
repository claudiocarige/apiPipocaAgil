package br.com.pipocaagil.apipipocaagil.domain.representations;

public record UserRequestRepresentation(String givenName, String familyName, String email, String password) {

    public String getEmail() {
        return email;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getPassword() {
        return password;
    }
}
