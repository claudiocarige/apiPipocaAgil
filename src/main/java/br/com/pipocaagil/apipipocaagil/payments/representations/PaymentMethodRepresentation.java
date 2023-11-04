package br.com.pipocaagil.apipipocaagil.payments.representations;

public record PaymentMethodRepresentation(CardRepresentation card, String type, Integer installments, Boolean capture, String soft_descriptor) {}
