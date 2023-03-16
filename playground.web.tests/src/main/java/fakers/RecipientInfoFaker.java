package fakers;


import models.RecipientInfo;
import com.github.javafaker.Faker;

public class RecipientInfoFaker {
    public Faker getFaker() {
        return new Faker();
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
}