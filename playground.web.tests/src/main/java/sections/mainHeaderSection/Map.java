package sections.mainHeaderSection;

import enums.MainHeader;
import solutions.bellatrix.web.components.Anchor;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Anchor mainHeaderNavigation(MainHeader link) {
        String locator = String.format("//div[@id='main-header']//a[contains(@href,'%s')]", link);

        return create().byXPath(Anchor.class, locator);
    }
}