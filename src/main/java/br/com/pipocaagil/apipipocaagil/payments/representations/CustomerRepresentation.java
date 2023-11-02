package br.com.pipocaagil.apipipocaagil.payments.representations;

import java.util.List;

public record CustomerRepresentation(String name, String email, String tax_id, List<PhonesRepresentation> phones) {
    public String getEmail() {
        return email;
    }
}
