package br.com.pipocaagil.apipipocaagil.payments.impl;

import br.com.pipocaagil.apipipocaagil.payments.interfaces.PaymentService;
import br.com.pipocaagil.apipipocaagil.payments.representations.OrderRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${pagseguro.token}")
    private String TOKEN_KEY;

    private final RestTemplate restTemplate;

    @Override
    public Object createPayment(OrderRepresentation order) {
        var url = "https://sandbox.api.pagseguro.com/orders";
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + TOKEN_KEY);
        headers.set("accept", "application/json");

        HttpEntity<Object> entity = new HttpEntity<>(order, headers);

        var response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
        log.info("Log info Response:  %s" +  response.getBody().toString());
        return response.getBody();
    }
}
