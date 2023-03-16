package pages.successPage;

import solutions.bellatrix.web.components.Button;
import solutions.bellatrix.web.components.Div;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Button continueButton() {
        return create().byXPath(Button.class, "//div[@id='content']//a[contains(@href,'common/home')]");
    }

    public Div alertMessage() {
        return create().byXPath(Div.class, "//div[contains(@class,'alert-dismissible')]");
    }
}