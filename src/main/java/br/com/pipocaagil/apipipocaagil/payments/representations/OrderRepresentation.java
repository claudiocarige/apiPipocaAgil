package br.com.pipocaagil.apipipocaagil.payments.representations;

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
    private ShippingRepresentation shipping;
    private String reference_id;
    private List<ItemsRepresentation> items;
    private List<ChargeRepresentation> charges;
}
