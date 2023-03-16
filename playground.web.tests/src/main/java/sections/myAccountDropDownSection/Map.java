package sections.myAccountDropDownSection;

import enums.MyAccountDropDown;
import solutions.bellatrix.web.components.Anchor;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Anchor myAccountMenu(MyAccountDropDown menu) {
        String locator = String.format(
                "//div[@id='main-navigation']//li[contains(@class,'dropdown-hoverable')]//a[contains(@href,'%s')]",
                menu.toString());

        return create().byXPath(Anchor.class, locator);
    }
}