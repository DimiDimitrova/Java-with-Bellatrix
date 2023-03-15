package changepasswordpage;

import accountpage.AccountPage;
import breadcrumbsection.BreadcrumbSection;
import messages.ApplicationMessages;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    private final int PASSWORD_MIN_SIZE = 4;
    private final int PASSWORD_MAX_SIZE = 20;

    public void assertChangePasswordFailedWithIncorrectPassword() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs("Change Password");

        var accountPage = new AccountPage();
        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.PASSWORD_ERROR, PASSWORD_MIN_SIZE, PASSWORD_MAX_SIZE));
    }
}