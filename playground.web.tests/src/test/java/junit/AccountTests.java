package junit;

import accountorderpage.AccountOrderPage;
import accountpage.AccountPage;
import accountvoucherpage.AccountVoucherPage;
import changepasswordpage.ChangePasswordPage;
import checkoutpage.CheckoutPage;
import confirmpage.ConfirmPage;
import editaccountpage.EditAccountPage;
import enums.*;
import facadepattern.PurchaseVoucherFacade;
import generatefakerdata.FakerDataGenerator;
import loginpage.LoginPage;
import mainnavigationsection.MainNavigationSection;
import myaccountdropdownsection.MyAccountDropDownSection;
import org.junit.jupiter.api.Test;
import registerpage.RegisterPage;
import solutions.bellatrix.web.infrastructure.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import solutions.bellatrix.web.infrastructure.junit.WebTest;
import successpage.SuccessPage;

@ExecutionBrowser(browser = Browser.CHROME, lifecycle = Lifecycle.RESTART_EVERY_TIME)
public class AccountTests extends WebTest {
    private FakerDataGenerator generator;
    private AccountPage accountPage;
    private AccountOrderPage accountOrderPage;
    private AccountVoucherPage accountVoucherPage;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private SuccessPage successPage;
    private ChangePasswordPage changePasswordPage;
    private MainNavigationSection mainNavigationSection;
    private EditAccountPage editAccountPage;
    private PurchaseVoucherFacade purchaseVoucherFacade;
    private CheckoutPage checkoutPage;
    private ConfirmPage confirmPage;
    private MyAccountDropDownSection myAccountDropDownSection;

    public AccountTests() {
        generator = new FakerDataGenerator();
        accountPage = new AccountPage();
        accountOrderPage = new AccountOrderPage();
        accountVoucherPage = new AccountVoucherPage();
        registerPage = new RegisterPage();
        loginPage = new LoginPage();
        mainNavigationSection = new MainNavigationSection();
        successPage = new SuccessPage();
        changePasswordPage = new ChangePasswordPage();
        editAccountPage = new EditAccountPage();
        checkoutPage = new CheckoutPage();
        confirmPage = new ConfirmPage();
        myAccountDropDownSection = new MyAccountDropDownSection();
        purchaseVoucherFacade = new PurchaseVoucherFacade(mainNavigationSection, myAccountDropDownSection,
                accountVoucherPage, successPage, checkoutPage, confirmPage);
    }

    @Test
    public void viewOrderHistorySuccessfully() {
        var registeredUser = generator.getRegisteredUser();
        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());

        accountPage.openMenuFromNavbar(Navbar.ORDER_HISTORY);

        accountOrderPage.asserts().accountOrderHistoryIsDisplayed();
    }

    @Test
    public void logoutSuccessfully() {
        var registeredUser = generator.getRegisteredUser();
        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());

        accountPage.openMenuFromNavbar(Navbar.LOGOUT);

        accountPage.asserts().assertUserIsNotLogged();
    }

    @ParameterizedTest
    @EnumSource(GiftCertificate.class)
    public void addVoucherSuccessfully(GiftCertificate gift) {
        var recipient = generator.createRecipient();
        var person = generator.getRegisteredUser();
        double amount = 50;

        loginPage.logIn(person.getEmail(), person.getPassword());

        purchaseVoucherFacade.purchaseVoucher(recipient, person.getFirstName(), gift, amount, Account.LOGIN);

        successPage.asserts().asserThatPurchaseIsMade();
    }

    @ParameterizedTest
    @ValueSource(ints = 0)
    public void addVoucherFailed_When_RecipientNameIsLess_Than_MinSize(int nameSize) {
        var recipient = generator.createRecipientWithSpecificName(nameSize);
        var registeredUser = generator.getRegisteredUser();
        double amount = 10;

        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        mainNavigationSection.map().selectMenu(MainMenu.MY_ACCOUNT).hover();
        mainNavigationSection.map().selectMenu(MainMenu.VOUCHER).click();

        accountVoucherPage.fillPurchaseGiftData(recipient, registeredUser.getFirstName(), GiftCertificate.BIRTHDAY, amount);
        accountVoucherPage.map().continueButton().click();

        accountVoucherPage.asserts().assertPurchaseVoucherFailedWithIncorrectRecipientName();
    }

    @ParameterizedTest
    @ValueSource(ints = {65, 66})
    public void addVoucherFailed_When_RecipientNameIsMore_Than_MaxSize(int nameSize) {
        var recipient = generator.createRecipientWithSpecificName(nameSize);
        var registeredUser = generator.getRegisteredUser();
        double amount = 10;

        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        mainNavigationSection.map().selectMenu(MainMenu.MY_ACCOUNT).hover();
        mainNavigationSection.map().selectMenu(MainMenu.VOUCHER).click();

        accountVoucherPage.fillPurchaseGiftData(recipient, registeredUser.getLastName(), GiftCertificate.BIRTHDAY, amount);
        accountVoucherPage.map().continueButton().click();

        accountVoucherPage.asserts().assertPurchaseVoucherFailedWithIncorrectRecipientName();
    }

    @ParameterizedTest
    @ValueSource(strings = {"a12sd@", ""})
    public void addVoucherFailed_When_RecipientEmailIsIncorrect(String endEmail) {
        var recipient = generator.createRecipientWithSpecificEmail(endEmail);
        var registeredUser = generator.getRegisteredUser();
        double amount = 10;

        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        mainNavigationSection.map().selectMenu(MainMenu.MY_ACCOUNT).hover();
        mainNavigationSection.map().selectMenu(MainMenu.VOUCHER).click();

        accountVoucherPage.fillPurchaseGiftData(recipient, registeredUser.getFirstName(), GiftCertificate.BIRTHDAY, amount);
        accountVoucherPage.map().continueButton().click();

        accountVoucherPage.asserts().assertPurchaseVoucherFailedWithInvalidEmail();
    }

    @Test
    public void editAccountInformationSuccessfully() {
        var registeredUser = generator.getRegisteredUser();
        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        accountPage.openMenuFromNavbar(Navbar.EDIT_ACCOUNT);

        editAccountPage.fillAllAccountInformation(registeredUser);
        editAccountPage.map().continueButton().click();

        successPage.asserts().assertAccountIsUpdated();
    }

    @Test
    public void changePasswordSuccessfully() {
        var person = generator.createPersonInfo();
        String newPassword = generator.getFaker().lorem().characters(18);

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
        var person = generator.createPersonInfo();
        String newPassword = generator.getFaker().lorem().characters(passwordLength);

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
        var person = generator.createPersonInfo();
        String newPassword = generator.getFaker().lorem().characters(passwordLength);

        registerPage.doRegistration(person);
        accountPage.openMenuFromNavbar(Navbar.LOGOUT);
        loginPage.logIn(person.getEmail(), person.getPassword());
        accountPage.openMenuFromNavbar(Navbar.PASSWORD);

        changePasswordPage.changePassword(newPassword);
        changePasswordPage.map().continueButton().click();

        changePasswordPage.asserts().assertChangePasswordFailedWithIncorrectPassword();
    }
}