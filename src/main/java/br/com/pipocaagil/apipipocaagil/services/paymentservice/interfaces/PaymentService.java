package br.com.pipocaagil.apipipocaagil.services.paymentservice.interfaces;

import br.com.pipocaagil.apipipocaagil.services.paymentservice.exception.JsonProcessingException;
import br.com.pipocaagil.apipipocaagil.services.paymentservice.representations.OrderRepresentation;

public interface PaymentService {

    Object createPayment(OrderRepresentation order)  throws JsonProcessingException;
}
