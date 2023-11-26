package br.com.pipocaagil.apipipocaagil.services.paymentservice.representations;

public record ChargeRepresentation(AmountRepresentation amount, PaymentMethodRepresentation payment_method, String reference_id, String description) {}
