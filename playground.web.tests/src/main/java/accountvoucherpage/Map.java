package accountvoucherpage;

import solutions.bellatrix.web.components.*;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public TextField recipientName() {
        return create().byId(TextField.class, "input-to-name");
    }

    public TextField recipientMail() {
        return create().byId(TextField.class, "input-to-email");
    }

    public CheckBox agreeCheckbox() {
        return create().byXPath(CheckBox.class, "//div[@class='float-right']/input[@type='checkbox']");
    }

    public Button continueButton() {
        return create().byXPath(Button.class, "//input[contains(@class,'btn-primary')]");
    }

    public TextField amount() {
        return create().byId(TextField.class, "input-amount");
    }

    public TextField fromName() {
        return create().byId(TextField.class, "input-from-name");
    }

    public Div errorMessage() {
        return create().byXPath(Div.class, "//*[@class='text-danger']");
    }

    public RadioButton giftCertificateTheme(String theme) {
        return create().byXPath(RadioButton.class,String.format("//label[contains(text(),'%s')]/input", theme));
    }
}