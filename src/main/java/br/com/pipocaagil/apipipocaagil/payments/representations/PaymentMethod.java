package br.com.pipocaagil.apipipocaagil.payments.representations;

public record PaymentMethod(Card card, String type, Integer installments, Boolean capture, String soft_descriptor) {}
