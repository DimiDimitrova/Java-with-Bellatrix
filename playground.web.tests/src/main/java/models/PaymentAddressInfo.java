package models;

import enums.Country;
import lombok.Getter;
import lombok.Setter;

public class PaymentAddressInfo {
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String address;
    @Getter @Setter private String city;
    @Getter @Setter private String postCode ;
    @Getter @Setter private Country country;
    @Getter @Setter private String region;
}