package br.com.pipocaagil.apipipocaagil.services.paymentservice.representations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRepresentation {

    private CustomerRepresentation customer;
    private List<ItemsRepresentation> items;
    private String reference_id;
    private List<ChargeRepresentation> charges;
}
