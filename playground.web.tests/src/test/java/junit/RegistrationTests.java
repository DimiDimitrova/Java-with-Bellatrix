package junit;

import enums.MainMenu;
import enums.MyAccountDropDown;
import mainnavigationsection.MainNavigationSection;
import myaccountdropdownsection.MyAccountDropDownSection;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import registerpage.RegisterPage;
import generatefakerdata.FakerDataGenerator;
import solutions.bellatrix.web.infrastructure.*;
import solutions.bellatrix.web.infrastructure.junit.WebTest;
import successpage.SuccessPage;

@ExecutionBrowser(browser = Browser.CHROME, lifecycle = Lifecycle.RESTART_EVERY_TIME)
public class RegistrationTests extends WebTest {
    private FakerDataGenerator faker;
    private RegisterPage registerPage;
    private SuccessPage successPage;

    public RegistrationTests() {
        faker = new FakerDataGenerator();
        registerPage = app().goTo(RegisterPage.class);
        successPage = app().goTo(SuccessPage.class);
    }

    @Test
    public void registrationFailed_When_UseAlreadyRegisteredEmail() {
        registerPage.doRegistration(faker.getRegisteredUser());

        registerPage.asserts().assertThatRegistrationFailedWithExistEmail();
    }

    @Test
    public void makeRegistrationSuccessfully() {
        var person = faker.createPersonInfo();

        registerPage.doRegistration(person);

        successPage.asserts().assertThatRegistrationIsMade();
    }

    @Test
    public void RegistrationFailed_When_AllFieldsAreEmpty() {
        var registerPage = app().goTo(RegisterPage.class);
        registerPage.open();
        new MainNavigationSection().map().selectMenu(MainMenu.MY_ACCOUNT).scrollToVisible();
        new MyAccountDropDownSection().map().myAccountMenu(MyAccountDropDown.REGISTER).click();

        registerPage.map().continueButton().click();

        registerPage.asserts().assertThatRegistrationFailedWithoutData();
    }

    @ParameterizedTest
    @ValueSource(ints = 0)
    public void RegistrationFailed_When_FirstNameFieldIsLessSymbols_Than_MinSize(int firstName) {
        var person = faker.createPersonWithSpecificFirstName(firstName);
        var registerPage = app().goTo(RegisterPage.class);

        registerPage.doRegistration(person);

        registerPage.asserts().assertIncorrectFirstNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {33, 34})
    public void RegistrationFailed_When_FirstNameFieldIsMoreSymbols_Than_MaxSize(int sizeFirstName) {
        var person = faker.createPersonWithSpecificFirstName(sizeFirstName);

        registerPage.doRegistration(person);

        registerPage.asserts().assertIncorrectFirstNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {33, 34})
    public void RegistrationFailed_When_LastNameFieldIsMoreSymbols_Than_MaxSize(int sizeLastName) {
        var person = faker.createPersonWithSpecificLastName(sizeLastName);

        registerPage.doRegistration(person);

        registerPage.asserts().assertIncorrectLastNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    public void RegistrationFailed_When_LastNameFieldIsLessSymbols_Than_MinSize(int size) {
        var person = faker.createPersonWithSpecificLastName(size);

        registerPage.doRegistration(person);

        registerPage.asserts().assertIncorrectLastNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void RegistrationFailed_When_TelephoneFieldIsLessSymbols_Than_MinSize(int telephoneSize) {
        var person = faker.createPersonWithSpecificTelephone(telephoneSize);

        registerPage.doRegistration(person);

        registerPage.asserts().assertIncorrectPhoneValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {33, 34, 35})
    public void RegistrationFailed_When_TelephoneFieldIsMoreSymbols_Than_MaxSize(int telephoneSize) {
        var person = faker.createPersonWithSpecificTelephone(telephoneSize);

        registerPage.doRegistration(person);

        registerPage.asserts().assertIncorrectPhoneValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void RegistrationFailed_When_PasswordFieldIsLessSymbols_Than_MinSize(int passwordSize) {
        var person = faker.createPersonWithSpecificPassword(passwordSize);

        registerPage.doRegistration(person);

        registerPage.asserts().assertIncorrectPasswordValidation(person.getPassword());
    }

    @ParameterizedTest
    @ValueSource(ints = {21, 22, 23})
    public void RegistrationFailed_When_PasswordFieldIsMoreSymbols_Than_MaxSize(int passwordSize) {
        var person = faker.createPersonWithSpecificPassword(passwordSize);

        registerPage.doRegistration(person);

        registerPage.asserts().assertIncorrectPasswordValidation(person.getPassword());
    }

    @Test
    public void RegistrationFailed_When_ConfirmPasswordIsIncorrect() {
        var person = faker.createPersonInfo();
        registerPage.open();

        registerPage.map().firstNameInput().setText(person.getFirstName());
        registerPage.map().lastNameInput().setText(person.getLastName());
        registerPage.map().emailInput().setEmail(person.getEmail());
        registerPage.map().telephoneInput().setPhone(person.getTelephone());
        registerPage.map().passwordInput().setPassword(person.getPassword());
        registerPage.map().confirmPassword().setPassword(faker.getFaker().lorem().characters(10));
        registerPage.map().agreeCheckbox().check();
        registerPage.map().continueButton().click();
        app().browser().waitForAjax();

        registerPage.asserts().assertIncorrectPasswordConfirmationValidation();
    }

    @Test
    public void RegistrationFailed_When_AgreeInformationIsNotChecked() {
        var person = faker.createPersonInfo();

        registerPage.open();
        registerPage.fillRegistrationForm(person);
        registerPage.map().continueButton().click();

        registerPage.asserts().assertThatRegistrationFailedWithoutCheckedAgree();
    }
}