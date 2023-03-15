package loginpage;

import solutions.bellatrix.web.components.Button;
import solutions.bellatrix.web.components.Div;
import solutions.bellatrix.web.components.TextField;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Div loginWarningMessage() {
        return create().byXPath(Div.class, "//div[contains(@class,'alert-dismissible')]");
    }

    public TextField emailInput() {
        return create().byId(TextField.class, "input-email");
    }

    public TextField passwordInput() {
        return create().byId(TextField.class, "input-password");
    }

    public Button loginButton() {
        return create().byXPath(Button.class, "//div//input[@value='Login']");
    }
}