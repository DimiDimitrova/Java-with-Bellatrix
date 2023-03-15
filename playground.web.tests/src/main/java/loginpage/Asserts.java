package loginpage;

import breadcrumbsection.BreadcrumbSection;
import enums.PageTitle;
import messages.ApplicationMessages;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    public void assertThatInvalidCredentialsMessageIsDisplayed(String email) {
        var breadcrumb = new BreadcrumbSection();

        breadcrumb.map().activePageTitle().validateTextIs(PageTitle.LOGIN.toString());

        map().loginWarningMessage().validateTextIs(ApplicationMessages.INVALID_CREDENTIALS_MESSAGE);
    }

    public void assertThatMessageForExceededAllowedNumberOfLoginIsDisplayed() {
        var breadcrumb = new BreadcrumbSection();

        breadcrumb.map().activePageTitle().validateTextIs(PageTitle.LOGIN.toString());

        map().loginWarningMessage().validateTextIs(ApplicationMessages.ALLOWED_NUMBER_OF_LOGIN_ERROR);
    }
}