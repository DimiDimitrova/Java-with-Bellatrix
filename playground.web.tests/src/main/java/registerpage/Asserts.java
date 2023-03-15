package registerpage;

import accountpage.AccountPage;
import enums.PageTitle;
import breadcrumbsection.BreadcrumbSection;
import messages.ApplicationMessages;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    private final int PASSWORD_MIN_SIZE = 4;
    private final int PASSWORD_MAX_SIZE = 20;
    private final int FIRST_NAME_MIN_SIZE = 1;
    private final int FIRST_NAME_MAX_SIZE = 32;
    private final int LAST_NAME_MIN_SIZE = 1;
    private final int LAST_NAME_MAX_SIZE = 32;
    private final int TELEPHONE_MIN_SIZE = 3;
    private final int TELEPHONE_MAX_SIZE = 32;

    public void assertThatRegistrationFailedWithExistEmail() {
        map().emailWarningMessage().validateIsVisible();
    }

    public void assertThatRegistrationFailedWithoutData() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        map().emailWarningMessage().validateIsVisible();
    }

    public void assertIncorrectPasswordValidation(String password) {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        var accountPage = new AccountPage();
        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.PASSWORD_ERROR, PASSWORD_MIN_SIZE, PASSWORD_MAX_SIZE));
    }

    public void assertIncorrectFirstNameValidation() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        var accountPage = new AccountPage();
        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.FIRST_NAME_ERROR, FIRST_NAME_MIN_SIZE, FIRST_NAME_MAX_SIZE));
    }

    public void assertIncorrectLastNameValidation() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        var accountPage = new AccountPage();
        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.LAST_NAME_ERROR, LAST_NAME_MIN_SIZE, LAST_NAME_MAX_SIZE));

    }

    public void assertIncorrectPhoneValidation() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        var accountPage = new AccountPage();
        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.TELEPHONE_ERROR, TELEPHONE_MIN_SIZE, TELEPHONE_MAX_SIZE));
    }

    public void assertIncorrectPasswordConfirmationValidation() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        var accountPage = new AccountPage();
        accountPage.map().warningMessage().validateTextIs(ApplicationMessages.PASSWORD_CONFIRMATION_ERROR);
    }

    public void assertThatRegistrationFailedWithoutCheckedAgree() {
        map().agreeWithPrivacyMessage().validateIsVisible();
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());
    }
}