package Pages.productpage;

import enums.ExtraProductContent;
import enums.PageTitle;
import messages.ApplicationMessages;
import org.junit.jupiter.api.Assertions;
import solutions.bellatrix.web.pages.PageAsserts;
import solutions.bellatrix.web.services.App;

public class Asserts extends PageAsserts<Map> {
    public void assertProductPageIsOpen() {
        Assertions.assertFalse(new App().browser().getUrl().endsWith("#"), ApplicationMessages.REFRESH_PAGE_ERROR);

        Assertions.assertTrue(new App().browser().getUrl().endsWith("product"),
                String.format(ApplicationMessages.PAGE_ERROR, PageTitle.PRODUCT.toString()));
    }

    public void assertProductInformationIsDisplayed() {
        map().product().validateIsVisible();
        map().productPrice().validateIsVisible();
        map().productExtraContent(ExtraProductContent.AVAILABILITY).validateIsVisible();
        map().productExtraContent(ExtraProductContent.PRODUCT_CODE).validateIsVisible();
    }
}