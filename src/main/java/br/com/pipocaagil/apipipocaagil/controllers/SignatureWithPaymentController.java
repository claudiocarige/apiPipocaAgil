package br.com.pipocaagil.apipipocaagil.controllers;

import br.com.pipocaagil.apipipocaagil.domain.representations.CountRepresentation;
import br.com.pipocaagil.apipipocaagil.payments.exception.JsonProcessingException;
import br.com.pipocaagil.apipipocaagil.payments.interfaces.PaymentService;
import br.com.pipocaagil.apipipocaagil.payments.representations.OrderRepresentation;
import br.com.pipocaagil.apipipocaagil.services.emailservice.EmailSendingServiceImpl;
import br.com.pipocaagil.apipipocaagil.services.interfaces.SignatureDataService;
import br.com.pipocaagil.apipipocaagil.services.interfaces.UsersSignatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/signature")
@Tag(name = "Signing users", description = "Contains all operations related to the resources Signing users.")
public class SignatureWithPaymentController {

    private final PaymentService paymentService;
    private final UsersSignatureService signatureService;
    private final SignatureDataService signatureDataService;
    private final EmailSendingServiceImpl emailSendingService;

    @PostMapping("/payment/card")
    @Operation(summary = "Signing users and payment with cred card",
            description = "Performs credit card payment for monthly subscribers.",
            tags = {"Signature"},
            responses = {
                    @ApiResponse(description = "Create", responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderRepresentation.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Users not found", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal Error", content = @Content)
            })
    public ResponseEntity<Object>createPayment(@RequestBody OrderRepresentation order) throws JsonProcessingException,
                                                                                                    MessagingException {
        var payment = paymentService.createPayment(order);
        signatureService.signatureToUser(order.getCustomer().getEmail());
        emailSendingService.sendEmail(order.getCustomer().getEmail(),
                "Você conseguiu sua Assinatura Pipoca Ágil!", "");
        log.info("Payment created", payment);
        return ResponseEntity.ok(payment);
    }

    @PostMapping("/result/response")
    @Operation(summary = "Receive Payment Update Response",
            description = "Receive Payment Update Response.",
            tags = {"Signature"})
    public String paymentResponse(@RequestBody Object bodyResponse){
        log.info("Payment : "+ bodyResponse);
        return "Ok";
    }

    @GetMapping("/counter")
    public ResponseEntity<CountRepresentation> countUsersSignature(){
        CountRepresentation count = new CountRepresentation(signatureDataService.countUsersSignature());
        return ResponseEntity.ok().body(count);
    }
}
