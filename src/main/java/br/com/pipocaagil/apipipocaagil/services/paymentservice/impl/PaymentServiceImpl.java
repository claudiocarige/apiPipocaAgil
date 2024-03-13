package br.com.pipocaagil.apipipocaagil.services.paymentservice.impl;

import br.com.pipocaagil.apipipocaagil.controllers.exceptions.PaymentAuthorizationException;
import br.com.pipocaagil.apipipocaagil.domain.entities.SignatureData;
import br.com.pipocaagil.apipipocaagil.domain.entities.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.SignatureType;
import br.com.pipocaagil.apipipocaagil.services.exceptions.DataIntegrityViolationException;
import br.com.pipocaagil.apipipocaagil.services.interfaces.SignatureDataService;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersService;
import br.com.pipocaagil.apipipocaagil.services.paymentservice.exception.JsonProcessingException;
import br.com.pipocaagil.apipipocaagil.services.paymentservice.interfaces.PaymentService;
import br.com.pipocaagil.apipipocaagil.services.paymentservice.representations.OrderRepresentation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static br.com.pipocaagil.apipipocaagil.services.impl.FormatDate.formatStringToLocalDateTime;


@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    public static final String PAYMENT_NOT_AUTHORIZED = "Payment not authorized!";
    public static final String ERROR_IN_OBJECT_DESERIALIZATION = "Error in object deserialization : ";
    private static final String PAGBANK_URL = "https://sandbox.api.pagseguro.com";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final SignatureDataService signatureDataService;
    private final UsersService usersService;

    @Value("${pagseguro.token}")
    private String keyPagBank;

    @Override
    public Object createPayment(OrderRepresentation order) {

        var headers = createHeader();
        Users user = usersService.findByUsername(order.getCustomer().email());
        isUserAlreadyHasSignature(user);
        order.setReference_id(generateReferenceId());
        HttpEntity<Object> entity = new HttpEntity<>(order, headers);
        var response = sendPaymentRequest(entity);
        var bodyResponse = response.getBody();
        saveResultInDB(bodyResponse, user);
        return bodyResponse;
    }

    private void isUserAlreadyHasSignature(Users user) {
        SignatureData obj = signatureDataService.findSignatureByUserId(user.getId());
        if (obj != null) {
            throw new DataIntegrityViolationException("User already has a signature");
        }
    }

    private String generateReferenceId() {
        var counted = signatureDataService.countUsersSignature() + 1;
        return  String.format("%08d", counted);
    }

    private HttpHeaders createHeader() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json"));
        headers.set("Authorization", "Bearer " + keyPagBank);
        headers.set("accept", "application/json");
        return headers;
    }

    private ResponseEntity<Object> sendPaymentRequest(HttpEntity<Object> entity) {
        try {
            ResponseEntity<Object> response = restTemplate.exchange(PAGBANK_URL+"/orders", HttpMethod.POST, entity, Object.class);
            var statusCode = response.getStatusCode().value();
            if (statusCode < 200 || statusCode > 204) {
                log.warn(PAYMENT_NOT_AUTHORIZED + ";" + response.getStatusCode() + response);
                return ResponseEntity.badRequest().body(new PaymentAuthorizationException(PAYMENT_NOT_AUTHORIZED));
            }
            return response;
        } catch (HttpClientErrorException e) {
            throw new br.com.pipocaagil.apipipocaagil.services.paymentservice.exception.HttpClientErrorException("Verifique as informações do pagamento");
        }
    }

    public void saveResultInDB(Object bodyResponse, Users user) throws JsonProcessingException {
        var json = convertBodyResponseToJson(bodyResponse);
        SignatureData signatureData = deserializeJson(json);
        if (!signatureData.getStatus().equals("PAID")) {
            log.warn(PAYMENT_NOT_AUTHORIZED + bodyResponse);
            throw new PaymentAuthorizationException(PAYMENT_NOT_AUTHORIZED);
        }
        signatureData.setUser(user);
        signatureDataService.save(signatureData);
    }

    private String convertBodyResponseToJson(Object bodyResponse){
        String json;
        try {
            json = objectMapper.writeValueAsString(bodyResponse);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            throw new JsonProcessingException(ERROR_IN_OBJECT_DESERIALIZATION + e);
        }
        return json;
    }

    private SignatureData deserializeJson(String json) throws JsonProcessingException {
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
            throw new JsonProcessingException(ERROR_IN_OBJECT_DESERIALIZATION + e);
        }
    }
}
