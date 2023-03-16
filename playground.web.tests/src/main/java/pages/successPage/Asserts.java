package pages.successPage;

import pages.accountPage.AccountPage;
import enums.Navbar;
import enums.PageTitle;
import messages.ApplicationMessages;
import models.BaseEShopPage;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    protected AccountPage accountPage;
    protected BaseEShopPage baseEShopPage;

    public Asserts() {
        accountPage = new AccountPage();

        baseEShopPage = new BaseEShopPage();
    }

    public void assertThatRegistrationIsMade() {
        accountPage.map().accountNavbar(Navbar.LOGOUT).validateIsVisible();
    }

    public void asserThatPurchaseIsMade() {
        baseEShopPage.breadcrumbSection().map().pageTitle().validateTextIs(ApplicationMessages.ORDER_IS_MADE);
    }

    public void assertVoucherIsPurchased() {
        baseEShopPage.breadcrumbSection().map().pageTitle().validateTextIs(ApplicationMessages.PURCHASE_GIFT_IS_MADE);
    }

    public void assertAccountIsUpdated() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.ACCOUNT.toString());

        map().alertMessage().validateTextIs(ApplicationMessages.UPDATED_ACCOUNT_MESSAGE);
    }
}