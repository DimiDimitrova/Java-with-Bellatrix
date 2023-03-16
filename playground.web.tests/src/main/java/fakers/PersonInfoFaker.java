package fakers;

import com.github.javafaker.Faker;
import configuration.LambdaTestSettings;
import Pages.registerpage.PersonInfo;
import solutions.bellatrix.core.configuration.ConfigurationService;
import solutions.bellatrix.core.utilities.SecretsResolver;

public class PersonInfoFaker {
    public Faker getFaker() {
        return new Faker();
    }

    public PersonInfo createPersonInfo() {
        var person = new PersonInfo();
        person.setFirstName(getFaker().name().firstName());
        person.setLastName(getFaker().name().lastName());
        person.setEmail(getFaker().bothify("?????????@gmail.com"));
        person.setTelephone(getFaker().phoneNumber().subscriberNumber(32));
        person.setPassword(getFaker().lorem().characters(20));
        return person;
    }

    public PersonInfo createPersonWithSpecificFirstName(int firstNameSize) {
        var person = new PersonInfo();
        person.setFirstName(getFaker().lorem().characters(firstNameSize));
        person.setLastName(getFaker().name().lastName());
        person.setEmail(getFaker().bothify("???????@gmail.com"));
        person.setTelephone(getFaker().phoneNumber().subscriberNumber(32));
        person.setPassword(getFaker().lorem().characters(20));

        return person;
    }

    public PersonInfo createPersonWithSpecificLastName(int lastNameSize) {
        var person = new PersonInfo();
        person.setFirstName(getFaker().name().firstName());
        person.setLastName(getFaker().lorem().characters(lastNameSize));
        person.setEmail(getFaker().bothify("???????@gmail.com"));
        person.setTelephone(getFaker().phoneNumber().subscriberNumber(32));
        person.setPassword(getFaker().lorem().characters(20));

        return person;
    }

    public PersonInfo createPersonWithSpecificTelephone(int telephoneSize) {
        var person = new PersonInfo();
        person.setFirstName(getFaker().name().firstName());
        person.setLastName(getFaker().name().lastName());
        person.setEmail(getFaker().bothify("???????@gmail.com"));
        person.setTelephone(getFaker().phoneNumber().subscriberNumber(telephoneSize));
        person.setPassword(getFaker().lorem().characters(20));

        return person;
    }

    public PersonInfo createPersonWithSpecificPassword(int passwordSize) {
        var person = new PersonInfo();
        person.setFirstName(getFaker().name().firstName());
        person.setLastName(getFaker().name().lastName());
        person.setEmail(getFaker().bothify("?????????@gmail.com"));
        person.setTelephone(getFaker().phoneNumber().subscriberNumber(32));
        person.setPassword(getFaker().lorem().characters(passwordSize));

        return person;
    }

    public PersonInfo getRegisteredUser() {
        var person = new PersonInfo();
        person.setFirstName("Test");
        person.setLastName("Test");

        var email = SecretsResolver.getSecret(ConfigurationService.get(LambdaTestSettings.class).getRegisteredUserName());
        var password = SecretsResolver.getSecret(ConfigurationService.get(LambdaTestSettings.class).getRegisteredPassword());
        person.setEmail(email);
        person.setTelephone(getFaker().phoneNumber().subscriberNumber(32));
        person.setPassword(password);

        return person;
    }
}