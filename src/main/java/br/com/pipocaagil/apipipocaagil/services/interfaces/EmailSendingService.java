package br.com.pipocaagil.apipipocaagil.services.interfaces;

public interface EmailSendingService {
    void sendOrderConfirmationEmail(String to, String subject, String body);
}
