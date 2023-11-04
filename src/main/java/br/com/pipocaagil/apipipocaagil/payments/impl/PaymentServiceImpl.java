package br.com.pipocaagil.apipipocaagil.payments.impl;

import br.com.pipocaagil.apipipocaagil.domain.SignatureData;
import br.com.pipocaagil.apipipocaagil.domain.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.SignatureType;
import br.com.pipocaagil.apipipocaagil.payments.exception.JsonProcessingException;
import br.com.pipocaagil.apipipocaagil.payments.interfaces.PaymentService;
import br.com.pipocaagil.apipipocaagil.payments.representations.OrderRepresentation;
import br.com.pipocaagil.apipipocaagil.services.interfaces.SignatureDataService;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static br.com.pipocaagil.apipipocaagil.services.FormatDate.formatStringToLocalDateTime;


@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${pagseguro.token}")
    private String pagBank;

    private static final String PAGBANK_URL = "https://sandbox.api.pagseguro.com";

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    private final SignatureDataService signatureDataService;
    private final UsersService usersService;

    @Override
    public Object createPayment(OrderRepresentation order) {
        var headers = createHeader();
        Users user = usersService.findByUsername(order.getCustomer().getEmail());
        HttpEntity<Object> entity = new HttpEntity<>(order, headers);
        var response = sendPaymentRequest(entity);
        var bodyResponse = response.getBody();
        saveResultInDB(bodyResponse, user);
        return bodyResponse;
    }

    private HttpHeaders createHeader() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json"));
        headers.set("Authorization", "Bearer " + pagBank);
        headers.set("accept", "application/json");
        return headers;
    }

    private ResponseEntity<Object> sendPaymentRequest(HttpEntity<Object> entity) {
        try {
            ResponseEntity<Object> response = restTemplate.exchange(PAGBANK_URL+"/orders", HttpMethod.POST, entity, Object.class);
            var statusCode = response.getStatusCode().value();
            if (statusCode < 200 || statusCode > 204) {
                log.warn("Payment not authorized ; " + response.getStatusCode() + response);
                return ResponseEntity.badRequest().body(new JsonProcessingException("Payment not authorized"));
            }
            return response;
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveResultInDB(Object bodyResponse, Users user) throws JsonProcessingException {
        var json = convertBodyResponseToJson(bodyResponse);
        SignatureData signatureData = desserializeJson(json);
        if (!signatureData.getStatus().equals("PAID")) {
            log.warn("Payment not authorized"+ bodyResponse);
            throw new RuntimeException("Payment not authorized");
        }
        signatureData.setUser(user);
        signatureDataService.save(signatureData);
    }

    private String convertBodyResponseToJson(Object bodyResponse){
        String json;
        try {
            json = objectMapper.writeValueAsString(bodyResponse);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new JsonProcessingException("Error in object deserialization : " + e);
        }
        return json;
    }

    private SignatureData desserializeJson(String json) throws JsonProcessingException {
        SignatureData signatureData = new SignatureData();
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            signatureData.setCharId(rootNode.at("/charges/0/id").asText());
            signatureData.setOrderId(rootNode.at("/id").asText());
            signatureData.setStatus(rootNode.at("/charges/0/status").asText());
            signatureData.setReferenceId(rootNode.at("/reference_id").asText());
            signatureData.setPaymentMethod(rootNode.at("/charges/0/payment_method/type").asText());
            signatureData.setSignatureType(rootNode.at("/items/0/name").asText().equals("STANDARD") ? SignatureType.STANDARD : SignatureType.PREMIUM);
            signatureData.setPaidAt(formatStringToLocalDateTime(rootNode.at("/charges/0/paid_at").asText()));
            signatureData.setExpirationAt(signatureData.getPaidAt().plusMonths(12));
            return signatureData;
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new JsonProcessingException("Error in object deserialization : " + e);
        }
    }
}
