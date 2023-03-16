package pages.loginPage;

import enums.PageTitle;
import messages.ApplicationMessages;
import models.BaseEShopPage;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    private BaseEShopPage baseEShopPage;
    public Asserts() {
        baseEShopPage = new BaseEShopPage();
    }

    public void assertThatInvalidCredentialsMessageIsDisplayed() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.LOGIN.toString());

        map().loginWarningMessage().validateTextIs(ApplicationMessages.INVALID_CREDENTIALS_MESSAGE);
    }

    public void assertThatMessageForExceededAllowedNumberOfLoginIsDisplayed() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.LOGIN.toString());

        map().loginWarningMessage().validateTextIs(ApplicationMessages.ALLOWED_NUMBER_OF_LOGIN_ERROR);
    }
}