package br.com.pipocaagil.apipipocaagil.payments.exception;

public class HttpClientErrorException extends RuntimeException{
    public HttpClientErrorException(String message) {
        super(message);
    }
}
