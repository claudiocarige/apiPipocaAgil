package br.com.pipocaagil.apipipocaagil.services.paymentservice.representations;

import java.util.List;

public record CustomerRepresentation(String name, String email, String tax_id, List<PhonesRepresentation> phones) {
}
