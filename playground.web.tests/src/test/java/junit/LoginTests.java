package junit;

import accountpage.AccountPage;
import enums.MainMenu;
import generatefakerdata.FakerDataGenerator;
import homepage.HomePage;
import loginpage.LoginPage;
import mainnavigationsection.MainNavigationSection;
import org.junit.jupiter.api.Test;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

public class LoginTests extends WebTest {
    private final int ALLOWED_NUMBER_OF_LOGIN_ATTEMPTS = 5;
    private LoginPage loginPage;
    private AccountPage accountPage;
    private HomePage homePage;
    private MainNavigationSection mainNavigationSections;
    private FakerDataGenerator faker;

    public LoginTests() {
        loginPage = new LoginPage();
        accountPage = new AccountPage();
        homePage = new HomePage();
        mainNavigationSections = new MainNavigationSection();
        faker = new FakerDataGenerator();
    }

    @Test
    public void logInSuccessfully() {
        var registeredUser = faker.getRegisteredUser();
        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());

        accountPage.asserts().assertThatUserIsLogged();
    }

    @Test
    public void logInFailedWithWrongEmailCredential() {
        var data = faker.getFaker().lorem().characters(15);
        var registeredUser = faker.getRegisteredUser();

        loginPage.logIn(data, registeredUser.getPassword());

        loginPage.asserts().assertThatInvalidCredentialsMessageIsDisplayed(data);
    }

    @Test
    public void logInFailedWithEmptyCredentials() {
        app().goTo(HomePage.class);
        mainNavigationSections.map().selectMenu(MainMenu.MY_ACCOUNT).click();

        loginPage.map().loginButton().click();

        loginPage.asserts().assertThatInvalidCredentialsMessageIsDisplayed("");
        accountPage.asserts().assertThatUserIsLogged();
    }

    @Test
    public void logInFailedWithoutRegisteredAccount() {
        var data = faker.getFaker().lorem().characters(10);

        loginPage.logIn(data, data);

        loginPage.asserts().assertThatInvalidCredentialsMessageIsDisplayed(data);
    }

    @Test
    public void logInFailed_When_TryToEnterCredentialsAfterAllowedNumberOfLoginAttemptBeforeOneHour() {
        var registeredUser = faker.getRegisteredUser();
        for (int i = 0; i < ALLOWED_NUMBER_OF_LOGIN_ATTEMPTS; i++) {
            loginPage.logIn(registeredUser.getEmail(), faker.getFaker().lorem().characters(10));
            loginPage.asserts().assertThatInvalidCredentialsMessageIsDisplayed(registeredUser.getEmail());
        }

        loginPage.logIn(registeredUser.getEmail(), faker.getFaker().lorem().characters(10));
        loginPage.asserts().assertThatMessageForExceededAllowedNumberOfLoginIsDisplayed();

        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        loginPage.asserts().assertThatMessageForExceededAllowedNumberOfLoginIsDisplayed();
    }
}