package pages.accountOrderPage;

import models.BaseEShopPage;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    protected BaseEShopPage baseEShopPage;
    public Asserts() {
        baseEShopPage = new BaseEShopPage();
    }

    public void accountOrderHistoryIsDisplayed() {
        baseEShopPage.breadcrumbSection().map().pageTitle().validateTextIs("Order History");

        try {
            map().orderTable().validateIsVisible();
        } catch (Exception exception) {
            map().emptyHistory().validateIsVisible();
        }
    }
}