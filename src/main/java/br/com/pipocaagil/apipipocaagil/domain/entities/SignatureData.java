package br.com.pipocaagil.apipipocaagil.domain.entities;

import br.com.pipocaagil.apipipocaagil.domain.entities.Users;
import br.com.pipocaagil.apipipocaagil.domain.enums.SignatureType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "signature_data")
public class SignatureData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String charId;
    private String orderId;
    private String status;
    private String referenceId;
    private String paymentMethod;
    @Enumerated(EnumType.STRING)
    private SignatureType signatureType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime paidAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private OffsetDateTime  expirationAt;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user;
}
