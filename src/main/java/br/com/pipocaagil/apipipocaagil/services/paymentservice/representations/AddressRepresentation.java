package br.com.pipocaagil.apipipocaagil.services.paymentservice.representations;

public record AddressRepresentation(String street, String number, String complement, String locality, String city, String region_code,
                                    String country, String postal_code) {}
