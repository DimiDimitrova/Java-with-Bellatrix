package junit;

import Pages.accountpage.AccountPage;
import enums.MainMenu;
import fakers.PersonInfoFaker;
import fakers.RecipientInfoFaker;
import Pages.homepage.HomePage;
import Pages.loginpage.LoginPage;
import models.BaseEShopPage;
import org.junit.jupiter.api.Test;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

public class LoginTests extends WebTest {
    private final int ALLOWED_NUMBER_OF_LOGIN_ATTEMPTS = 5;
    protected LoginPage loginPage;
    protected AccountPage accountPage;
    protected HomePage homePage;
    protected PersonInfoFaker personInfoFaker;
    protected BaseEShopPage baseEShopPage;
    @Override
    protected void configure() {
        super.configure();
        loginPage = app().create(LoginPage.class);
        accountPage = app().create(AccountPage.class);
        homePage = app().create(HomePage.class);
        personInfoFaker = new PersonInfoFaker();
        baseEShopPage = new BaseEShopPage();
    }
    @Test
    public void logInSuccessfully() {
        var registeredUser = personInfoFaker.getRegisteredUser();
        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());

        accountPage.asserts().assertThatUserIsLogged();
    }

    @Test
    public void logInFailedWithWrongEmailCredential() {
        var data = new RecipientInfoFaker().getFaker().lorem().characters(15);
        var registeredUser = personInfoFaker.getRegisteredUser();

        loginPage.logIn(data, registeredUser.getPassword());

        loginPage.asserts().assertThatInvalidCredentialsMessageIsDisplayed();
    }

    @Test
    public void logInFailedWithEmptyCredentials() {
        homePage.open();
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.MY_ACCOUNT).click();

        loginPage.map().loginButton().click();

        loginPage.asserts().assertThatInvalidCredentialsMessageIsDisplayed();
        accountPage.asserts().assertThatUserIsLogged();
    }

    @Test
    public void logInFailedWithoutRegisteredAccount() {
        var data = new RecipientInfoFaker().getFaker().lorem().characters(10);

        loginPage.logIn(data, data);

        loginPage.asserts().assertThatInvalidCredentialsMessageIsDisplayed();
    }

    @Test
    public void logInFailed_When_TryToEnterCredentialsAfterAllowedNumberOfLoginAttemptBeforeOneHour() {
        var registeredUser = personInfoFaker.getRegisteredUser();

        for (int i = 0; i < ALLOWED_NUMBER_OF_LOGIN_ATTEMPTS; i++) {
            loginPage.logIn(registeredUser.getEmail(), new RecipientInfoFaker().getFaker().lorem().characters(10));
            loginPage.asserts().assertThatInvalidCredentialsMessageIsDisplayed();
        }

        loginPage.logIn(registeredUser.getEmail(), new RecipientInfoFaker().getFaker().lorem().characters(10));
        loginPage.asserts().assertThatMessageForExceededAllowedNumberOfLoginIsDisplayed();

        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        loginPage.asserts().assertThatMessageForExceededAllowedNumberOfLoginIsDisplayed();
    }
}