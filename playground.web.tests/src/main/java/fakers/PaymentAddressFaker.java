package fakers;

import com.github.javafaker.Faker;
import enums.Country;
import models.PaymentAddressInfo;

public class PaymentAddressFaker {
    public Faker getFaker() {
        return new Faker();
    }

    public PaymentAddressInfo createPaymentAddress() {
        var paymentAddress = new PaymentAddressInfo();
        paymentAddress.setFirstName(getFaker().name().firstName());
        paymentAddress.setLastName(getFaker().name().lastName());
        paymentAddress.setAddress(getFaker().address().streetAddress());
        paymentAddress.setCity(getFaker().address().city());
        paymentAddress.setPostCode(getFaker().address().zipCode());
        paymentAddress.setCountry(getFaker().options().option(Country.class));
        paymentAddress.setRegion("1");

        return paymentAddress;
    }

    public PaymentAddressInfo createPaymentWithSpecificAddress(int addressSize) {
        var paymentAddress = new PaymentAddressInfo();
        paymentAddress.setFirstName(getFaker().name().firstName());
        paymentAddress.setLastName(getFaker().name().lastName());
        paymentAddress.setAddress(getFaker().lorem().characters(addressSize));
        paymentAddress.setCity(getFaker().address().city());
        paymentAddress.setPostCode(getFaker().address().zipCode());
        paymentAddress.setCountry(getFaker().options().option(Country.class));
        paymentAddress.setRegion("1");

        return paymentAddress;
    }

    public PaymentAddressInfo createPaymentWithSpecificCity(int citySize) {
        var paymentAddress = new PaymentAddressInfo();
        paymentAddress.setFirstName(getFaker().name().firstName());
        paymentAddress.setLastName(getFaker().name().lastName());
        paymentAddress.setAddress(getFaker().address().streetAddress());
        paymentAddress.setCity(getFaker().lorem().characters(citySize));
        paymentAddress.setPostCode(getFaker().address().zipCode());
        paymentAddress.setCountry(getFaker().options().option(Country.class));
        paymentAddress.setRegion("1");

        return paymentAddress;
    }

    public PaymentAddressInfo createPaymentWithSpecificPostCode(int postCodeSize) {
        var paymentAddress = new PaymentAddressInfo();
        paymentAddress.setFirstName(getFaker().name().firstName());
        paymentAddress.setLastName(getFaker().name().lastName());
        paymentAddress.setAddress(getFaker().address().streetAddress());
        paymentAddress.setCity(getFaker().address().city());
        paymentAddress.setPostCode(getFaker().lorem().characters(postCodeSize));
        paymentAddress.setCountry(getFaker().options().option(Country.class));
        paymentAddress.setRegion("1");

        return paymentAddress;
    }

    public PaymentAddressInfo createPaymentWithSelectCountryOption() {
        var paymentAddress = new PaymentAddressInfo();
        paymentAddress.setFirstName(getFaker().name().firstName());
        paymentAddress.setLastName(getFaker().name().lastName());
        paymentAddress.setAddress(getFaker().address().streetAddress());
        paymentAddress.setCity(getFaker().address().city());
        paymentAddress.setPostCode(getFaker().address().zipCode());
        paymentAddress.setCountry(getFaker().options().option(Country.PLEASE_SELECT));
        paymentAddress.setRegion("1");

        return paymentAddress;
    }
}