package pages.changePasswordPage;

import solutions.bellatrix.web.components.Button;
import solutions.bellatrix.web.components.TextField;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public TextField passwordInput() {
        return create().byId(TextField.class, "input-password");
    }

    public TextField confirmPasswordInput() {
        return create().byId(TextField.class, "input-confirm");
    }

    public Button continueButton() {
        return create().byXPath(Button.class, "//*[@class='buttons clearfix']//input");
    }
}