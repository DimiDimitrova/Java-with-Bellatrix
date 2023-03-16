package Pages.accountOrderPage;

import solutions.bellatrix.web.components.Div;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Div orderTable() {
        return create().byXPath(Div.class, "//*[@id='content']//table[contains(@class,'table-bordered')]");
    }

    public Div emptyHistory() {
        return create().byXPath(Div.class, "//div[@id='content']//p");
    }
}