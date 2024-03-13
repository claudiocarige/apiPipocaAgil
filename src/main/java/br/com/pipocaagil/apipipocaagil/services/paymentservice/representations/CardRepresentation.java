package br.com.pipocaagil.apipipocaagil.services.paymentservice.representations;

public record CardRepresentation(HolderRepresentation holder, String number, Integer exp_month, Integer exp_year, String security_code,
                                 Boolean store) {}
