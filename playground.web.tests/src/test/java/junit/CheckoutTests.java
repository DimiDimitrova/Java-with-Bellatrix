package junit;

import cartpage.CartPage;
import checkoutpage.CheckoutPage;
import confirmpage.ConfirmPage;
import enums.Account;
import enums.CategoryInSearchBox;
import enums.Item;
import facadepattern.PurchaseFacade;
import generatefakerdata.FakerDataGenerator;
import homepage.HomePage;
import models.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import productpage.ProductPage;
import solutions.bellatrix.web.infrastructure.Browser;
import solutions.bellatrix.web.infrastructure.ExecutionBrowser;
import solutions.bellatrix.web.infrastructure.Lifecycle;
import solutions.bellatrix.web.infrastructure.junit.WebTest;
import successpage.SuccessPage;

import java.util.ArrayList;

@ExecutionBrowser(browser = Browser.CHROME, lifecycle = Lifecycle.RESTART_EVERY_TIME)
public class CheckoutTests extends WebTest {
    private ProductPage productPage;
    private CheckoutPage checkoutPage;
    private ConfirmPage confirmPage;
    private SuccessPage successPage;
    private HomePage homePage;
    private CartPage cartPage;
    private FakerDataGenerator faker;
    private PurchaseFacade purchaseFacade;
    public CheckoutTests() {
        productPage = new ProductPage();
        checkoutPage = new CheckoutPage();
        confirmPage = new ConfirmPage();
        successPage = new SuccessPage();
        homePage = new HomePage();
        cartPage = new CartPage();
        faker = new FakerDataGenerator();
        purchaseFacade = new PurchaseFacade(homePage, cartPage, productPage, checkoutPage, confirmPage, successPage);
    }

    @Test
    public void MadePurchaseSuccessfullyWithLoginOption() {
        var person = faker.getRegisteredUser();
        var paymentAddress = faker.createPaymentAddress();
        purchaseFacade.purchaseItem(Item.PALM_TREO_PRO, person, paymentAddress, Account.LOGIN);
    }

    @Test
    public void MakePurchaseSuccessfullyLikeGuest() {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentAddress();
        purchaseFacade.purchaseItem(Item.PALM_TREO_PRO, person, paymentAddress, Account.GUEST_ACCOUNT);
    }

    @Test
    public void MakePurchaseSuccessfullyLikeNewUser() {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseItem(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    public void MadePurchaseWithRegisterAccountFailed_When_FirstNameHasLessSymbols_Than_MinSize(int firstNameSize) {
        var person = faker.createPersonWithSpecificFirstName(firstNameSize);
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectFirstNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {33, 34, 45})
    public void MadePurchaseWithRegisterAccountFailed_When_FirstNameHasMoreSymbols_Than_MaxSize(int sizeFirstName) {
        var person = faker.createPersonWithSpecificFirstName(sizeFirstName);
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectFirstNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    public void MakePurchaseWithRegisterAccountFailed_When_LastNameHasLessSymbols_Than_MinSize(int lastNameSize) {
        var person = faker.createPersonWithSpecificLastName(lastNameSize);
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectLastNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {34, 33})
    public void MakePurchaseWithRegisterAccountFailed_When_LastNameHasMoreSymbols_Than_MaxSize(int sizeLastName) {
        var person = faker.createPersonWithSpecificLastName(sizeLastName);
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.NIKON_D_300, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectLastNameValidation();
    }

    @Test
    public void MakePurchaseWithRegisterAccountFailed_When_UseAlreadyRegisteredEmail() {
        var person = faker.createPersonInfo();
        var registeredUser = faker.getRegisteredUser();
        person.setEmail(registeredUser.getEmail());
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertOrderFailedWithExistEmail();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void MakePurchaseWithRegisterAccountFailed_When_TelephoneSymbolsAreLess_Than_MinSize(int telephoneSize) {
        var person = faker.createPersonWithSpecificTelephone(telephoneSize);
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.IPOD_NANO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPhoneValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {33, 34, 35})
    public void MakePurchaseWithRegisterAccountFailed_When_TelephoneSymbolsAreMore_Than_MaxSize(int telephoneSize) {
        var person = faker.createPersonWithSpecificTelephone(telephoneSize);
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.IPOD_NANO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPhoneValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void MakePurchaseWithRegisterAccountFailed_When_PasswordSymbolsAreLess_Than_MinSize(int password) {
        var person = faker.createPersonWithSpecificPassword(password);
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPasswordValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {23, 22})
    public void MakePurchaseWithRegisterAccountFailed_When_PasswordSymbolsAreMore_Than_MaxSize(int password) {
        var person = faker.createPersonWithSpecificPassword(password);
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPasswordValidation();
    }

    @Test
    public void MakePurchaseFailedWithRegisterAccount_When_ConfirmPasswordIsIncorrect() {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.IPOD_NANO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertPasswordsMismatchValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void MakePurchaseFailedWithRegisterAccount_When_AddressHasSymbolsLess_Than_MinSize(int addressSize) {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentWithSpecificAddress(addressSize);

        purchaseFacade.purchaseValidation(Item.IPOD_SHUFFLE, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectAddressValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {129, 130, 200})
    public void MakePurchaseFailedWithRegisterAccount_When_AddressHasSymbolsMore_Than_MaxSize(int addressSize) {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentWithSpecificAddress(addressSize);

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectAddressValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void MakePurchaseFailedWithRegisterAccount_When_CityHasSymbolsLess_Than_MinSize(int citySize) {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentWithSpecificCity(citySize);

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectCityValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {129, 130})
    public void MakePurchaseFailedWithRegisterAccount_When_CityHasSymbolsMore_Than_MaxSize(int citySize) {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentWithSpecificCity(citySize);

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectCityValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void MakePurchaseFailedWithRegisterAccount_When_PostCodeHasSymbolsLess_Than_MinSize(int postCodeSize) {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentWithSpecificPostCode(postCodeSize);

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPostCodeValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 12})
    public void MakePurchaseFailedWithRegisterAccount_When_PostCodeHasSymbolsMore_Than_MaxSize(int postCodeSize) {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentWithSpecificPostCode(postCodeSize);

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPostCodeValidation();
    }

    @Test
    public void MakePurchaseFailedWithRegisterAccount_When_Country_Is_PleaseSelect() {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentWithSelectCountryOption();

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectCountryValidation();
    }

    @Test
    public void MakePurchaseFailedWhenConditionsAreNotCheck() {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentAddress();

        purchaseFacade.purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddress, Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertTermConditionsRequiredAgreementValidation();
    }

    @Test
    public void MakePurchaseFailedWithRegisterAccount_When_ConfirmPasswordIsEmpty() {
        var person = faker.createPersonInfo();
        var paymentAddress = faker.createPaymentAddress();

        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.IPOD_NANO);
        cartPage.map().checkoutButton().click();

        checkoutPage.fillAccountDetails(person, paymentAddress, Account.REGISTER_ACCOUNT);
        checkoutPage.fillPasswordFields(person.getPassword(), "");
        checkoutPage.continuePurchase();

        checkoutPage.asserts().assertEmptyConfirmPasswordValidation();
    }

    @Test
    public void UpdateProductQuantityInCheckoutSuccessfully() {
        var product = Item.IPOD_NANO;
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(product);
        cartPage.map().checkoutButton().click();

        checkoutPage.updateProductQuantity(3, product);
        checkoutPage.map().updateButton(product).click();

        checkoutPage.asserts().assertQuantityAndTotalAreCorrect(3, product);
    }

   /* @Test
    public void RemoveProductSuccessfully() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.PALM_TREO_PRO);

        cartPage.map().checkoutButton().click();
        new App().browser().waitForAjax();
        checkoutPage.map().removeButton(Item.PALM_TREO_PRO).click();

        cartPage.asserts().assertShoppingCartIsEmpty();
    }*/

    @Test
    public void UserHaveCorrectInformationForCheckoutEcoTax() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.HTC_TOUCH_HD);

        cartPage.map().checkoutButton().click();

        checkoutPage.asserts().assertEcoTaxIsCorrect();
    }

    @Test
    public void UserHaveCorrectInformationForCheckoutVAT() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.IPOD_NANO);

        cartPage.map().checkoutButton().click();

        checkoutPage.asserts().assertVatIsCorrect();
    }

    @Test
    public void UserHaveCorrectInformationForCheckoutTotalPrice() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.IPOD_NANO);

        cartPage.map().checkoutButton().click();

        checkoutPage.asserts().assertTotalPriceIsCorrect();
    }

    @Test
    public void AddedProductPresentOnThePage() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        var product = productPage.addProductToCart(Item.PALM_TREO_PRO);
        var products = new ArrayList<Product>();
        products.add(product);

        cartPage.map().checkoutButton().click();

        checkoutPage.asserts().assertProductsInfo(products);
    }
}