package sections.mainNavigationSection;

import enums.MainMenu;
import solutions.bellatrix.web.components.Anchor;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Anchor selectMenu(MainMenu menu) {
        String locator = String.format(
                "//ul[@class='navbar-nav horizontal']//a[contains(@href,'%s')]", menu.toString());

        return create().byXPath(Anchor.class, locator);
    }
}