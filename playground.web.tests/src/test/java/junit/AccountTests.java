package junit;

import Pages.accountOrderPage.AccountOrderPage;
import Pages.accountpage.AccountPage;
import Pages.accountvoucherpage.AccountVoucherPage;
import Pages.changepasswordpage.ChangePasswordPage;
import Pages.editaccountpage.EditAccountPage;
import enums.*;
import facadepattern.PurchaseVoucherFacade;
import fakers.PersonInfoFaker;
import fakers.RecipientInfoFaker;
import Pages.loginpage.LoginPage;
import models.BaseEShopPage;
import org.junit.jupiter.api.Test;
import Pages.registerpage.RegisterPage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import solutions.bellatrix.web.infrastructure.junit.WebTest;
import Pages.successpage.SuccessPage;

public class AccountTests extends WebTest {
    protected RecipientInfoFaker recipientInfoFaker;
    protected LoginPage loginPage;
    protected AccountPage accountPage;
    protected AccountOrderPage accountOrderPage;
    protected SuccessPage successPage;
    protected AccountVoucherPage accountVoucherPage;
    protected EditAccountPage editAccountPage;
    protected ChangePasswordPage changePasswordPage;
    protected RegisterPage registerPage;
    protected PersonInfoFaker personInfoFaker;
    protected BaseEShopPage baseEShopPage;

    @Override
    protected void configure() {
        super.configure();
        loginPage = app().create(LoginPage.class);
        accountPage = app().create(AccountPage.class);
        accountOrderPage = app().create(AccountOrderPage.class);
        successPage = app().create(SuccessPage.class);
        accountVoucherPage = app().create(AccountVoucherPage.class);
        editAccountPage = app().create(EditAccountPage.class);
        changePasswordPage = app().create(ChangePasswordPage.class);
        registerPage = app().create(RegisterPage.class);
        personInfoFaker = new PersonInfoFaker();
        recipientInfoFaker = new RecipientInfoFaker();
        baseEShopPage = new BaseEShopPage();
    }

    @Test
    public void viewOrderHistorySuccessfully() {
        var registeredUser = personInfoFaker.getRegisteredUser();
        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());

        accountPage.openMenuFromNavbar(Navbar.ORDER_HISTORY);

        accountOrderPage.asserts().accountOrderHistoryIsDisplayed();
    }

    @Test
    public void logoutSuccessfully() {
        var registeredUser = personInfoFaker.getRegisteredUser();
        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());

        accountPage.openMenuFromNavbar(Navbar.LOGOUT);

        accountPage.asserts().assertUserIsNotLogged();
    }

    @ParameterizedTest
    @EnumSource(GiftCertificate.class)
    public void addVoucherSuccessfully(GiftCertificate gift) {
        var person = personInfoFaker.getRegisteredUser();
        double amount = 50;

        loginPage.logIn(person.getEmail(), person.getPassword());

        new PurchaseVoucherFacade().purchaseVoucher(recipientInfoFaker.createRecipient(), person.getFirstName(),
                gift, amount, Account.LOGIN);

        successPage.asserts().asserThatPurchaseIsMade();
    }

    @ParameterizedTest
    @ValueSource(ints = 0)
    public void addVoucherFailed_When_RecipientNameIsLess_Than_MinSize(int nameSize) {
        var registeredUser = personInfoFaker.getRegisteredUser();
        double amount = 10;

        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.VOUCHER).click();

        accountVoucherPage.fillPurchaseGiftData(recipientInfoFaker.createRecipientWithSpecificName(nameSize),
                registeredUser.getFirstName(), GiftCertificate.BIRTHDAY, amount);
        accountVoucherPage.map().continueButton().click();

        accountVoucherPage.asserts().assertPurchaseVoucherFailedWithIncorrectRecipientName();
    }

    @Test
    public void addVoucherFailed_When_RecipientNameIsMore_Than_MaxSize() {
        var registeredUser = personInfoFaker.getRegisteredUser();
        double amount = 10;

        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.MY_ACCOUNT).hover();
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.VOUCHER).click();

        accountVoucherPage.fillPurchaseGiftData(recipientInfoFaker.createRecipientWithSpecificName(65),
                registeredUser.getLastName(), GiftCertificate.BIRTHDAY, amount);
        accountVoucherPage.map().continueButton().click();

        accountVoucherPage.asserts().assertPurchaseVoucherFailedWithIncorrectRecipientName();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a12sd@", ""})
    public void addVoucherFailed_When_RecipientEmailIsIncorrect(String endEmail) {
        var registeredUser = personInfoFaker.getRegisteredUser();
        double amount = 10;

        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.MY_ACCOUNT).hover();
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.VOUCHER).click();

        accountVoucherPage.fillPurchaseGiftData(recipientInfoFaker.createRecipientWithSpecificEmail(endEmail),
                registeredUser.getFirstName(), GiftCertificate.BIRTHDAY, amount);
        accountVoucherPage.map().continueButton().click();

        accountVoucherPage.asserts().assertPurchaseVoucherFailedWithInvalidEmail();
    }

    @Test
    public void editAccountInformationSuccessfully() {
        var registeredUser = personInfoFaker.getRegisteredUser();
        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        accountPage.openMenuFromNavbar(Navbar.EDIT_ACCOUNT);

        editAccountPage.fillAllAccountInformation(registeredUser);
        editAccountPage.map().continueButton().click();

        successPage.asserts().assertAccountIsUpdated();
    }

    @Test
    public void changePasswordSuccessfully() {
        var person = personInfoFaker.createPersonInfo();
        String newPassword = new RecipientInfoFaker().getFaker().lorem().characters(18);

        registerPage.doRegistration(person);
        accountPage.openMenuFromNavbar(Navbar.LOGOUT);
        loginPage.logIn(person.getEmail(), person.getPassword());
        accountPage.openMenuFromNavbar(Navbar.PASSWORD);

        changePasswordPage.changePassword(newPassword);
        changePasswordPage.map().continueButton().click();
        accountPage.openMenuFromNavbar(Navbar.LOGOUT);
        loginPage.logIn(person.getEmail(), newPassword);

        accountPage.asserts().assertThatUserIsLogged();
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    public void changePasswordFailed_When_NewPasswordLengthHasLessSymbols_Than_MinSize(int passwordLength) {
        var person = personInfoFaker.createPersonInfo();
        String newPassword = new RecipientInfoFaker().getFaker().lorem().characters(passwordLength);

        registerPage.doRegistration(person);
        accountPage.openMenuFromNavbar(Navbar.LOGOUT);
        loginPage.logIn(person.getEmail(), person.getPassword());
        accountPage.openMenuFromNavbar(Navbar.PASSWORD);

        changePasswordPage.changePassword(newPassword);
        changePasswordPage.map().continueButton().click();

        changePasswordPage.asserts().assertChangePasswordFailedWithIncorrectPassword();
    }

    @ParameterizedTest
    @ValueSource(ints = {21, 22})
    public void changePasswordFailed_When_NewPasswordLengthHasMoreSymbols_Than_MaxSize(int passwordLength) {
        var person = personInfoFaker.createPersonInfo();
        String newPassword = new RecipientInfoFaker().getFaker().lorem().characters(passwordLength);

        registerPage.doRegistration(person);
        accountPage.openMenuFromNavbar(Navbar.LOGOUT);
        loginPage.logIn(person.getEmail(), person.getPassword());
        accountPage.openMenuFromNavbar(Navbar.PASSWORD);

        changePasswordPage.changePassword(newPassword);
        changePasswordPage.map().continueButton().click();

        changePasswordPage.asserts().assertChangePasswordFailedWithIncorrectPassword();
    }
}