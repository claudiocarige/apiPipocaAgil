package br.com.pipocaagil.apipipocaagil.services.paymentservice.representations;

public record PaymentMethodRepresentation(CardRepresentation card, String type, Integer installments, Boolean capture, String soft_descriptor) {}
