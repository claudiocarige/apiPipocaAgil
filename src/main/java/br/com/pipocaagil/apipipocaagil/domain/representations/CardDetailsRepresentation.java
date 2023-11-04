package br.com.pipocaagil.apipipocaagil.domain.representations;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardDetailsRepresentation {

    @NotNull(message = "The CARDNUMBER field is required!")
    @CreditCardNumber
    private String cardNumber;
    @NotNull(message = "The CARDHOLDERNAME field is required!")
    private String cardHolderName;
    @NotNull(message = "The SECURITYCODE field is required!")
    private String securityCode;
    @NotNull(message = "The CPF field is required!")
    @CPF(message = "CPF format is invalid")
    private String cpf;
    @NotNull(message = "The EXPIRATIONDATE field is required!")
    @JsonFormat(pattern = "MM/yyyy")
    private String expirationDate;
    @NotNull(message = "The CARDBRAND field is required!")
    private String cardBrand;
}
