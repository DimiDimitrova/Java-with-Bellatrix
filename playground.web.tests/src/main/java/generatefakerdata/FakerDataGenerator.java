package generatefakerdata;


import accountvoucherpage.RecipientInfo;
import enums.Country;
import models.PaymentAddressInfo;
import registerpage.PersonInfo;
import solutions.bellatrix.api.services.App;

public class FakerDataGenerator extends App {
    public PersonInfo createPersonInfo() {
        var faker = getFaker();
        var person = new PersonInfo();
        person.setFirstName(faker.name().firstName());
        person.setLastName(faker.name().lastName());
        person.setEmail(faker.bothify("?????????@gmail.com"));
        person.setTelephone(faker.phoneNumber().subscriberNumber(32));
        person.setPassword(faker.lorem().characters(20));
        return person;
    }

    public PersonInfo createPersonWithSpecificFirstName(int firstNameSize) {
        var person = new PersonInfo();
        var faker = getFaker();
        person.setFirstName(faker.lorem().characters(firstNameSize));
        person.setLastName(faker.name().lastName());
        person.setEmail(faker.bothify("???????@gmail.com"));
        person.setTelephone(faker.phoneNumber().subscriberNumber(32));
        person.setPassword(faker.lorem().characters(20));

        return person;
    }

    public PersonInfo createPersonWithSpecificLastName(int lastNameSize) {
        var person = new PersonInfo();
        var faker = getFaker();
        person.setFirstName(faker.name().firstName());
        person.setLastName(faker.lorem().characters(lastNameSize));
        person.setEmail(faker.bothify("???????@gmail.com"));
        person.setTelephone(faker.phoneNumber().subscriberNumber(32));
        person.setPassword(faker.lorem().characters(20));

        return person;
    }

    public PersonInfo createPersonWithSpecificTelephone(int telephoneSize) {
        var person = new PersonInfo();
        var faker = getFaker();
        person.setFirstName(faker.name().firstName());
        person.setLastName(faker.name().lastName());
        person.setEmail(faker.bothify("???????@gmail.com"));
        person.setTelephone(faker.phoneNumber().subscriberNumber(telephoneSize));
        person.setPassword(faker.lorem().characters(20));

        return person;
    }

    public PersonInfo createPersonWithSpecificPassword(int passwordSize) {
        var person = new PersonInfo();
        var faker = getFaker();
        person.setFirstName(faker.name().firstName());
        person.setLastName(faker.name().lastName());
        person.setEmail(faker.bothify("?????????@gmail.com"));
        person.setTelephone(faker.phoneNumber().subscriberNumber(32));
        person.setPassword(faker.lorem().characters(passwordSize));

        return person;
    }

    public PersonInfo getRegisteredUser() {
        var person = new PersonInfo();
        var faker = getFaker();
        person.setFirstName("Test");
        person.setLastName("Test");
        person.setEmail("abcde@yahoo.com");
        person.setTelephone(faker.phoneNumber().subscriberNumber(32));
        person.setPassword("login");

        return person;
    }

    public RecipientInfo getRegisteredRecipients() {
        var recipient = new RecipientInfo();
        recipient.setRecipientEmail("d@abv.bg");
        recipient.setRecipientName(getFaker().name().firstName());
        recipient.setRecipientPassword("dimi");

        return recipient;
    }

    public RecipientInfo createRecipient() {
        var recipient = new RecipientInfo();
        recipient.setRecipientName(getFaker().name().firstName());
        recipient.setRecipientEmail(getFaker().bothify("?????????@gmail.com"));
        recipient.setRecipientPassword(getFaker().lorem().characters(32));

        return recipient;
    }

    public RecipientInfo createRecipientWithSpecificName(int nameSize) {
        var recipient = new RecipientInfo();
        recipient.setRecipientName(getFaker().lorem().characters(nameSize));
        recipient.setRecipientEmail(getFaker().bothify("?????????@gmail.com"));
        recipient.setRecipientPassword(getFaker().lorem().characters(32));

        return recipient;
    }

    public RecipientInfo createRecipientWithSpecificEmail(String email) {
        var recipient = new RecipientInfo();
        recipient.setRecipientName(getFaker().name().firstName());
        recipient.setRecipientEmail(email);
        recipient.setRecipientPassword(getFaker().lorem().characters(32));

        return recipient;
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