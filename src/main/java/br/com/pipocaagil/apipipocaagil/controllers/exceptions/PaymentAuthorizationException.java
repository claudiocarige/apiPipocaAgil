package br.com.pipocaagil.apipipocaagil.controllers.exceptions;

public class PaymentAuthorizationException extends RuntimeException {
    public PaymentAuthorizationException(String message) {
        super(message);
    }
}
