package megamenusection;

import solutions.bellatrix.web.components.Heading;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Heading searchCategoryHeader() {
        return create().byXPath(Heading.class, "//*[contains(@class,'content-title')]/h1");
    }
}