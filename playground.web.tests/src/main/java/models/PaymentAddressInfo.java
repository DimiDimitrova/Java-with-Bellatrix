package models;

import enums.Country;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentAddressInfo {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String postCode;
    private Country country;
    private String region;
}