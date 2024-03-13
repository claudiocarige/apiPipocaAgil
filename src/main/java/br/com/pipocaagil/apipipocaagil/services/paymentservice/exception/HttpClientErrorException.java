package br.com.pipocaagil.apipipocaagil.services.paymentservice.exception;

public class HttpClientErrorException extends RuntimeException{
    public HttpClientErrorException(String message) {
        super(message);
    }
}
