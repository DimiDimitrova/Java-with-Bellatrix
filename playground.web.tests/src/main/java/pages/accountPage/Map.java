package pages.accountPage;

import enums.Navbar;
import solutions.bellatrix.web.components.Anchor;
import solutions.bellatrix.web.components.Div;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Div warningMessage() {
        return create().byXPath(Div.class, "//*[@class='text-danger']");
    }

    public Anchor accountNavbar(Navbar navbar) {
        String locator = String.format("//*[@id='column-right']/div/a[contains(@href,'%s')]", navbar.toString());

        return create().byXPath(Anchor.class, locator);
    }
}