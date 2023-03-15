package successpage;

import accountpage.AccountPage;
import breadcrumbsection.BreadcrumbSection;
import enums.Navbar;
import enums.PageTitle;
import messages.ApplicationMessages;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    public void assertThatRegistrationIsMade() {
        var accountPage = new AccountPage();
        accountPage.map().accountNavbar(Navbar.LOGOUT).validateIsVisible();
    }

    public void asserThatPurchaseIsMade() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().pageTitle().validateTextIs(ApplicationMessages.ORDER_IS_MADE);
    }

    public void assertVoucherIsPurchased() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().pageTitle().validateTextIs(ApplicationMessages.PURCHASE_GIFT_IS_MADE);
    }

    public void assertAccountIsUpdated() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs(PageTitle.ACCOUNT.toString());

        map().alertMessage().validateTextIs(ApplicationMessages.UPDATED_ACCOUNT_MESSAGE);
    }
}