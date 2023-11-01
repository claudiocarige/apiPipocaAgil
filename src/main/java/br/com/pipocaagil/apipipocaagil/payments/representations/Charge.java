package br.com.pipocaagil.apipipocaagil.payments.representations;

public record Charge(Amount amount,PaymentMethod payment_method, String reference_id, String description) {}
