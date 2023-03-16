package junit;

import enums.MainMenu;
import enums.MyAccountDropDown;
import fakers.PersonInfoFaker;
import models.BaseEShopPage;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pages.registerPage.RegisterPage;
import fakers.RecipientInfoFaker;
import solutions.bellatrix.web.infrastructure.junit.WebTest;
import pages.successPage.SuccessPage;

public class RegistrationTests extends WebTest {
    protected RegisterPage registerPage;
    protected PersonInfoFaker personInfoFaker;
    protected SuccessPage successPage;
    protected BaseEShopPage baseEShopPage;

    @Override
    protected void configure() {
        super.configure();
        registerPage = app().create(RegisterPage.class);
        personInfoFaker = new PersonInfoFaker();
        successPage = app().create(SuccessPage.class);
        baseEShopPage = new BaseEShopPage();
    }

    @Test
    public void registrationFailed_When_UseAlreadyRegisteredEmail() {
        registerPage.doRegistration(personInfoFaker.getRegisteredUser());

        registerPage.asserts().assertThatRegistrationFailedWithExistEmail();
    }

    @Test
    public void makeRegistrationSuccessfully() {
        registerPage.doRegistration(personInfoFaker.createPersonInfo());

        successPage.asserts().assertThatRegistrationIsMade();
    }

    @Test
    public void RegistrationFailed_When_AllFieldsAreEmpty() {
        registerPage.open();
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.MY_ACCOUNT).scrollToVisible();
        baseEShopPage.myAccountDropDownSection().map().myAccountMenu(MyAccountDropDown.REGISTER).click();

        registerPage.map().continueButton().click();

        registerPage.asserts().assertThatRegistrationFailedWithoutData();
    }

    @ParameterizedTest
    @ValueSource(ints = 0)
    public void RegistrationFailed_When_FirstNameFieldIsLessSymbols_Than_MinSize(int firstName) {
        registerPage.doRegistration(personInfoFaker.createPersonWithSpecificFirstName(firstName));

        registerPage.asserts().assertIncorrectFirstNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {33, 34})
    public void RegistrationFailed_When_FirstNameFieldIsMoreSymbols_Than_MaxSize(int sizeFirstName) {
        registerPage.doRegistration(personInfoFaker.createPersonWithSpecificFirstName(sizeFirstName));

        registerPage.asserts().assertIncorrectFirstNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {33, 34})
    public void RegistrationFailed_When_LastNameFieldIsMoreSymbols_Than_MaxSize(int sizeLastName) {
        registerPage.doRegistration(personInfoFaker.createPersonWithSpecificLastName(sizeLastName));

        registerPage.asserts().assertIncorrectLastNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    public void RegistrationFailed_When_LastNameFieldIsLessSymbols_Than_MinSize(int size) {
        registerPage.doRegistration(personInfoFaker.createPersonWithSpecificLastName(size));

        registerPage.asserts().assertIncorrectLastNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void RegistrationFailed_When_TelephoneFieldIsLessSymbols_Than_MinSize(int telephoneSize) {
        registerPage.doRegistration(personInfoFaker.createPersonWithSpecificTelephone(telephoneSize));

        registerPage.asserts().assertIncorrectPhoneValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {33, 34, 35})
    public void RegistrationFailed_When_TelephoneFieldIsMoreSymbols_Than_MaxSize(int telephoneSize) {
        registerPage.doRegistration(personInfoFaker.createPersonWithSpecificTelephone(telephoneSize));

        registerPage.asserts().assertIncorrectPhoneValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void RegistrationFailed_When_PasswordFieldIsLessSymbols_Than_MinSize(int passwordSize) {
        registerPage.doRegistration(personInfoFaker.createPersonWithSpecificPassword(passwordSize));

        registerPage.asserts().assertIncorrectPasswordValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {21, 22, 23})
    public void RegistrationFailed_When_PasswordFieldIsMoreSymbols_Than_MaxSize(int passwordSize) {
        registerPage.doRegistration(personInfoFaker.createPersonWithSpecificPassword(passwordSize));

        registerPage.asserts().assertIncorrectPasswordValidation();
    }

    @Test
    public void RegistrationFailed_When_ConfirmPasswordIsIncorrect() {
        var person = personInfoFaker.createPersonInfo();
        registerPage.open();

        registerPage.map().firstNameInput().setText(person.getFirstName());
        registerPage.map().lastNameInput().setText(person.getLastName());
        registerPage.map().emailInput().setEmail(person.getEmail());
        registerPage.map().telephoneInput().setPhone(person.getTelephone());
        registerPage.map().passwordInput().setPassword(person.getPassword());
        registerPage.map().confirmPassword().setPassword(new RecipientInfoFaker().getFaker().lorem().characters(10));
        registerPage.map().agreeCheckbox().check();
        registerPage.map().continueButton().click();
        app().browser().waitForAjax();

        registerPage.asserts().assertIncorrectPasswordConfirmationValidation();
    }

    @Test
    public void RegistrationFailed_When_AgreeInformationIsNotChecked() {
        registerPage.open();
        registerPage.fillRegistrationForm(personInfoFaker.createPersonInfo());
        registerPage.map().continueButton().click();

        registerPage.asserts().assertThatRegistrationFailedWithoutCheckedAgree();
    }
}