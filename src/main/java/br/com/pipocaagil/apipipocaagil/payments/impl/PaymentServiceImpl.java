package br.com.pipocaagil.apipipocaagil.payments.impl;

import br.com.pipocaagil.apipipocaagil.payments.interfaces.PaymentService;
import br.com.pipocaagil.apipipocaagil.payments.representations.OrderRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${pagseguro.token}")
    private String TOKEN;

    private final RestTemplate restTemplate;

    @Override
    public Object createPayment(OrderRepresentation order) {
        var url = "https://sandbox.api.pagseguro.com/orders";
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + TOKEN);
        headers.set("accept", "application/json");

        HttpEntity<Object> entity = new HttpEntity<>(order, headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);

        return response.getBody();
    }
}
