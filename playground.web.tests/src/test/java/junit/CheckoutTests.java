package junit;

import Pages.cartpage.CartPage;
import Pages.checkoutpage.CheckoutPage;
import enums.Account;
import enums.CategoryInSearchBox;
import enums.Item;
import facadepattern.PurchaseFacade;
import fakers.PaymentAddressFaker;
import fakers.PersonInfoFaker;
import Pages.homepage.HomePage;
import models.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import Pages.productpage.ProductPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

import java.util.ArrayList;

public class CheckoutTests extends WebTest {
    protected CheckoutPage checkoutPage;
    protected ProductPage productPage;
    protected CartPage cartPage;
    protected HomePage homePage;
    protected PersonInfoFaker personInfoFaker;
    protected PaymentAddressFaker paymentAddressFaker;

    @Override
    protected void configure() {
        super.configure();
        checkoutPage = app().create(CheckoutPage.class);
        productPage = app().create(ProductPage.class);
        cartPage = app().create(CartPage.class);
        homePage = app().create(HomePage.class);
        personInfoFaker = new PersonInfoFaker();
        paymentAddressFaker = new PaymentAddressFaker();
    }

    @Test
    public void MadePurchaseSuccessfullyWithLoginOption() {
        new PurchaseFacade().purchaseItem(Item.PALM_TREO_PRO, personInfoFaker.getRegisteredUser(),
                paymentAddressFaker.createPaymentAddress(), Account.LOGIN);
    }

    @Test
    public void MakePurchaseSuccessfullyLikeGuest() {
        new PurchaseFacade().purchaseItem(Item.PALM_TREO_PRO, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentAddress(), Account.GUEST_ACCOUNT);
    }

    @Test
    public void MakePurchaseSuccessfullyLikeNewUser() {
        new PurchaseFacade().purchaseItem(Item.PALM_TREO_PRO, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    public void MadePurchaseWithRegisterAccountFailed_When_FirstNameHasLessSymbols_Than_MinSize(int firstNameSize) {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonWithSpecificFirstName(firstNameSize),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectFirstNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {33, 34, 45})
    public void MadePurchaseWithRegisterAccountFailed_When_FirstNameHasMoreSymbols_Than_MaxSize(int sizeFirstName) {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonWithSpecificFirstName(sizeFirstName),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectFirstNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0})
    public void MakePurchaseWithRegisterAccountFailed_When_LastNameHasLessSymbols_Than_MinSize(int lastNameSize) {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonWithSpecificLastName(lastNameSize),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectLastNameValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {34, 33})
    public void MakePurchaseWithRegisterAccountFailed_When_LastNameHasMoreSymbols_Than_MaxSize(int sizeLastName) {
        new PurchaseFacade().purchaseValidation(Item.NIKON_D_300, personInfoFaker.createPersonWithSpecificLastName(sizeLastName),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectLastNameValidation();
    }

    @Test
    public void MakePurchaseWithRegisterAccountFailed_When_UseAlreadyRegisteredEmail() {
        var person = personInfoFaker.createPersonInfo();
        var registeredUser = personInfoFaker.getRegisteredUser();
        person.setEmail(registeredUser.getEmail());
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, person, paymentAddressFaker.createPaymentAddress(),
                Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertOrderFailedWithExistEmail();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void MakePurchaseWithRegisterAccountFailed_When_TelephoneSymbolsAreLess_Than_MinSize(int telephoneSize) {
        new PurchaseFacade().purchaseValidation(Item.IPOD_NANO, personInfoFaker.createPersonWithSpecificTelephone(telephoneSize),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPhoneValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {33, 34, 35})
    public void MakePurchaseWithRegisterAccountFailed_When_TelephoneSymbolsAreMore_Than_MaxSize(int telephoneSize) {
        new PurchaseFacade().purchaseValidation(Item.IPOD_NANO, personInfoFaker.createPersonWithSpecificTelephone(telephoneSize),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPhoneValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3})
    public void MakePurchaseWithRegisterAccountFailed_When_PasswordSymbolsAreLess_Than_MinSize(int password) {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonWithSpecificPassword(password),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPasswordValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {23, 22})
    public void MakePurchaseWithRegisterAccountFailed_When_PasswordSymbolsAreMore_Than_MaxSize(int password) {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonWithSpecificPassword(password),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPasswordValidation();
    }

    @Test
    public void MakePurchaseFailedWithRegisterAccount_When_ConfirmPasswordIsIncorrect() {
        new PurchaseFacade().purchaseValidation(Item.IPOD_NANO, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertPasswordsMismatchValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void MakePurchaseFailedWithRegisterAccount_When_AddressHasSymbolsLess_Than_MinSize(int addressSize) {
        new PurchaseFacade().purchaseValidation(Item.IPOD_SHUFFLE, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentWithSpecificAddress(addressSize), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectAddressValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {129, 130, 200})
    public void MakePurchaseFailedWithRegisterAccount_When_AddressHasSymbolsMore_Than_MaxSize(int addressSize) {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentWithSpecificAddress(addressSize), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectAddressValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void MakePurchaseFailedWithRegisterAccount_When_CityHasSymbolsLess_Than_MinSize(int citySize) {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentWithSpecificCity(citySize), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectCityValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {129, 130})
    public void MakePurchaseFailedWithRegisterAccount_When_CityHasSymbolsMore_Than_MaxSize(int citySize) {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentWithSpecificCity(citySize), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectCityValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    public void MakePurchaseFailedWithRegisterAccount_When_PostCodeHasSymbolsLess_Than_MinSize(int postCodeSize) {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentWithSpecificPostCode(postCodeSize),
                Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPostCodeValidation();
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 12})
    public void MakePurchaseFailedWithRegisterAccount_When_PostCodeHasSymbolsMore_Than_MaxSize(int postCodeSize) {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentWithSpecificPostCode(postCodeSize),
                Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectPostCodeValidation();
    }

    @Test
    public void MakePurchaseFailedWithRegisterAccount_When_Country_Is_PleaseSelect() {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentWithSelectCountryOption(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertIncorrectCountryValidation();
    }

    @Test
    public void MakePurchaseFailedWhenConditionsAreNotCheck() {
        new PurchaseFacade().purchaseValidation(Item.PALM_TREO_PRO, personInfoFaker.createPersonInfo(),
                paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);

        checkoutPage.asserts().assertTermConditionsRequiredAgreementValidation();
    }

    @Test
    public void MakePurchaseFailedWithRegisterAccount_When_ConfirmPasswordIsEmpty() {
        var person = personInfoFaker.createPersonInfo();
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.IPOD_NANO);
        cartPage.map().checkoutButton().click();

        checkoutPage.fillAccountDetails(person, paymentAddressFaker.createPaymentAddress(), Account.REGISTER_ACCOUNT);
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