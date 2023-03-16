package pages.registerPage;

import pages.accountPage.AccountPage;
import enums.PageTitle;
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

    public void assertThatRegistrationFailedWithExistEmail() {
        map().emailWarningMessage().validateIsVisible();
    }

    public void assertThatRegistrationFailedWithoutData() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        map().emailWarningMessage().validateIsVisible();
    }

    public void assertIncorrectPasswordValidation() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        int PASSWORD_MIN_SIZE = 4;
        int PASSWORD_MAX_SIZE = 20;
        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.PASSWORD_ERROR, PASSWORD_MIN_SIZE, PASSWORD_MAX_SIZE));
    }

    public void assertIncorrectFirstNameValidation() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        int FIRST_NAME_MAX_SIZE = 32;
        int FIRST_NAME_MIN_SIZE = 1;
        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.FIRST_NAME_ERROR, FIRST_NAME_MIN_SIZE, FIRST_NAME_MAX_SIZE));
    }

    public void assertIncorrectLastNameValidation() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        int LAST_NAME_MAX_SIZE = 32;
        int LAST_NAME_MIN_SIZE = 1;
        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.LAST_NAME_ERROR, LAST_NAME_MIN_SIZE, LAST_NAME_MAX_SIZE));

    }

    public void assertIncorrectPhoneValidation() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        int TELEPHONE_MAX_SIZE = 32;
        int TELEPHONE_MIN_SIZE = 3;
        accountPage.map().warningMessage().validateTextIs(String.format(
                ApplicationMessages.TELEPHONE_ERROR, TELEPHONE_MIN_SIZE, TELEPHONE_MAX_SIZE));
    }

    public void assertIncorrectPasswordConfirmationValidation() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());

        accountPage.map().warningMessage().validateTextIs(ApplicationMessages.PASSWORD_CONFIRMATION_ERROR);
    }

    public void assertThatRegistrationFailedWithoutCheckedAgree() {
        map().agreeWithPrivacyMessage().validateIsVisible();

        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.REGISTER.toString());
    }
}