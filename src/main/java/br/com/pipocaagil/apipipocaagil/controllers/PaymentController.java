package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.payments.exception.JsonProcessingException;
import br.com.pipocaagil.apipipocaagil.payments.interfaces.PaymentService;
import br.com.pipocaagil.apipipocaagil.payments.representations.OrderRepresentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payment")
@Tag(name = "Signing users", description = "Contains all operations related to the resources Signing users.")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/card")
    @Operation(summary = "Signing users and payment with cred card",
            description = "Performs credit card payment for monthly subscribers.",
            tags = {"Signature"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "204",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderRepresentation.class))
                    ),
                    @ApiResponse(responseCode = "201", description = "Create", content = @Content),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<Object>createPayment(@RequestBody OrderRepresentation order) throws JsonProcessingException {
        var payment = paymentService.createPayment(order);
        log.info("Payment created: {}", payment);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/result")
    @Operation(summary = "Receive Payment Update Response",
            description = "Receive Payment Update Response.",
            tags = {"Signature"})
    public void paymentResponse(@RequestBody Object bodyResponse){
        log.info("Payment : "+ bodyResponse);
    }
}
