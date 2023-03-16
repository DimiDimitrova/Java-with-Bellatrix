package pages.changePasswordPage;

import pages.accountPage.AccountPage;
import messages.ApplicationMessages;
import models.BaseEShopPage;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    private BaseEShopPage baseEShopPage;
    private AccountPage accountPage;

    public Asserts() {
        baseEShopPage = new BaseEShopPage();
        accountPage = new AccountPage();
    }

    public void assertChangePasswordFailedWithIncorrectPassword() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs("Change Password");

        int PASSWORD_MIN_SIZE = 4;
        int PASSWORD_MAX_SIZE = 20;
        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.PASSWORD_ERROR, PASSWORD_MIN_SIZE, PASSWORD_MAX_SIZE));
    }
}