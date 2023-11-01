package br.com.pipocaagil.apipipocaagil.payments.representations;

public record Address(String street, String number, String complement, String locality, String city, String region_code,
                      String country, String postal_code) {}
