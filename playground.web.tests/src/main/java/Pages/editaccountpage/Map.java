package Pages.editaccountpage;

import solutions.bellatrix.web.components.Button;
import solutions.bellatrix.web.components.TextField;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public TextField firstNameInput() {
        return create().byId(TextField.class, "input-firstname");
    }

    public TextField lastNameInput() {
        return create().byId(TextField.class, "input-lastname");
    }

    public TextField emailInput() {
        return create().byId(TextField.class, "input-email");
    }

    public TextField telephoneInput() {
        return create().byId(TextField.class, "input-telephone");
    }

    public Button continueButton() {
        return create().byXPath(Button.class, "//input[@value='Continue']");
    }
}