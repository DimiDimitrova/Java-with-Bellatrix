package Pages.quickviewpage;

import solutions.bellatrix.web.components.Button;
import solutions.bellatrix.web.components.TextField;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public TextField quickViewQuantityInput() {
        return create().byXPath(TextField.class, "//*[contains(@class,'content-quantity')]//input");
    }

    public Button decreaseQtyButton() {
        return create().byXPath(Button.class, "//button[contains(@aria-label,'Decrease quantity')]");
    }

    public Button increaseQtyButton() {
        return create().byXPath(Button.class, "//button[contains(@aria-label,'Increase quantity')]");
    }
}