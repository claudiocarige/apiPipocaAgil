package br.com.pipocaagil.apipipocaagil.payments.representations;

import java.util.List;

public record Customer(String name, String email, String tax_id, List<Phones> phones) {}
