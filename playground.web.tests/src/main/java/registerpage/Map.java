package registerpage;

import solutions.bellatrix.web.components.*;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public TextField firstNameInput() {
        return create().byId(TextField.class, "input-firstname");
    }

    public TextField lastNameInput() {
        return create().byId(TextField.class, "input-lastname");
    }

    public EmailField emailInput() {
        return create().byId(EmailField.class, "input-email");
    }

    public PhoneField telephoneInput() {
        return create().byId(PhoneField.class, "input-telephone");
    }

    public PasswordField passwordInput() {
        return create().byId(PasswordField.class, "input-password");
    }

    public PasswordField confirmPassword() {
        return create().byId(PasswordField.class, "input-confirm");
    }

    public CheckBox agreeCheckbox() {
        return create().byXPath(CheckBox.class, "//*[@id='input-agree']//parent::div");
    }

    public Button continueButton() {
        return create().byXPath(Button.class, "//input[@value='Continue']");
    }

    public Div emailWarningMessage() {
        return create().byXPath(Div.class, "//*[contains(@class,'fa-exclamation-circle')]");
    }

    public Div agreeWithPrivacyMessage() {
        return create().byXPath(Div.class,
                "//div[@id='account-register']//div[contains(text(), 'Warning: You must agree to the Privacy Policy!')]");
    }
}