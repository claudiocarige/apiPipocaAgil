package br.com.pipocaagil.apipipocaagil.services.interfaces;

import jakarta.mail.MessagingException;

public interface EmailSendingService {
    void sendEmail(String to, String subject, String body) throws MessagingException;
}
