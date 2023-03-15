package mainheadersection;

import enums.MainHeader;
import solutions.bellatrix.web.components.Anchor;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Anchor mainHeaderNavigation(MainHeader link) {
        return create().byXPath(Anchor.class, String.format("//div[@id='main-header']//a[contains(@href,'%s')]", link));
    }
}