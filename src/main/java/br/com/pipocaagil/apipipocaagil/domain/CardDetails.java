package br.com.pipocaagil.apipipocaagil.domain;

import br.com.pipocaagil.apipipocaagil.domain.enums.CardBrand;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CardDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String cardHolderName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String securityCode;
    private String cpf;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String expirationDate;
    @Enumerated(EnumType.STRING)
    private CardBrand cardBrand;
}
