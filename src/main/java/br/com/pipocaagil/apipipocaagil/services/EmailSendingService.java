package br.com.pipocaagil.apipipocaagil.services;

public interface EmailSendingService {
    void sendOrderConfirmationEmail(String to, String subject, String body);
}
