package Pages.changepasswordpage;

import Pages.accountpage.AccountPage;
import messages.ApplicationMessages;
import models.BaseEShopPage;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    private final int PASSWORD_MIN_SIZE = 4;
    private final int PASSWORD_MAX_SIZE = 20;
    private BaseEShopPage baseEShopPage;
    private AccountPage accountPage;

    public Asserts() {
        baseEShopPage = new BaseEShopPage();
        accountPage = new AccountPage();
    }

    public void assertChangePasswordFailedWithIncorrectPassword() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs("Change Password");

        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.PASSWORD_ERROR, PASSWORD_MIN_SIZE, PASSWORD_MAX_SIZE));
    }
}