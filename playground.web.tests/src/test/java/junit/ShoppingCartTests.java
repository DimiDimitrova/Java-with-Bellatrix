package junit;

import accountpage.AccountPage;
import accountvoucherpage.AccountVoucherPage;
import cartpage.CartPage;
import checkoutpage.CheckoutPage;
import confirmpage.ConfirmPage;
import enums.*;
import generatefakerdata.FakerDataGenerator;
import homepage.HomePage;
import loginpage.LoginPage;
import mainheadersection.MainHeaderSection;
import mainnavigationsection.MainNavigationSection;
import models.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import productpage.ProductPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;
import successpage.SuccessPage;

import java.util.ArrayList;

public class ShoppingCartTests extends WebTest {
    private HomePage homePage;
    private ProductPage productPage;
    private LoginPage loginPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private AccountVoucherPage accountVoucherPage;
    private ConfirmPage confirmPage;

    public ShoppingCartTests() {
        homePage = new HomePage();
        productPage = new ProductPage();
        loginPage = new LoginPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        accountVoucherPage = new AccountVoucherPage();
        confirmPage = new ConfirmPage();
    }

    @Test
    public void openShoppingCartSuccessfully() {
        app().goTo(HomePage.class);
        new MainHeaderSection().map().mainHeaderNavigation(MainHeader.CART).click();
        app().browser().waitUntilPageLoadsCompletely();

        cartPage.map().cartButton().click();

        cartPage.asserts().assertCartPageIsOpen();
    }

    @Test
    public void productInformationPresentInShoppingCart() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().imageItem(Item.PALM_TREO_PRO).click();
        var product = productPage.setProduct();
        var products = new ArrayList<Product>();
        products.add(product);
        productPage.map().addToCartItemButton().click();

        cartPage.map().viewCartButton().click();

        cartPage.asserts().assertProductInfo(products);
    }

    @Test
    public void updateProductQuantityInCartSuccessfully() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();

        var product = productPage.addProductToCart(Item.SONY_VAIO);
        cartPage.map().viewCartButton().click();
        cartPage.updateProductQuantityInCart(3, product.getTitle());

        cartPage.asserts().assertQuantityAndTotalAreCorrect(3, product);
    }

    @Test
    public void removeProductSuccessfully() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();

        var product = productPage.addProductToCart(Item.SONY_VAIO);
        cartPage.map().viewCartButton().click();
        cartPage.map().removeButton(product.getTitle()).click();

        cartPage.asserts().assertMessageForEmptyCartIsDisplayed();
    }

    @Test
    public void addItemToShoppingCart() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        var products = new ArrayList<Product>();
        products.add(productPage.addProductToCart(Item.SONY_VAIO));

        cartPage.map().viewCartButton().click();

        cartPage.asserts().assertProductInfo(products);
    }

    @Test
    public void continueShoppingSuccessfully() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.SONY_VAIO);
        cartPage.map().viewCartButton().click();

        cartPage.map().continueShoppingButton().click();

        homePage.asserts().assertHomePageIsLoaded();
    }

    @Test
    public void navigateToCheckoutSuccessfully() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.SONY_VAIO);
        cartPage.map().viewCartButton().click();

        cartPage.map().checkoutButton().click();

        checkoutPage.asserts().checkoutPageIsLoaded();
    }

    @ParameterizedTest
    @EnumSource(value = Country.class, names = {"BULGARIA", "UNITED_KINGDOM"})
    public void estimateShippingTaxesSuccessfully(Country country) {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.SONY_VAIO);
        cartPage.map().viewCartButton().click();

        cartPage.map().openAccordion(CartAccordion.ESTIMATE_SHIPPING_TAXES).click();
        cartPage.fillEstimateShippingTaxes(country, "1000");
        cartPage.map().getQuotesButton().click();
        var flatRate = cartPage.map().flatRateInformation().getText();
        cartPage.map().flatRate().click();
        cartPage.map().applyShippingButton().click();

        cartPage.asserts().assertFlatRateIsApplied(flatRate);
    }

    @Test
    public void estimateShippingTaxesFailed_When_NotFilledData() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.addItemToCart(Item.SONY_VAIO);
        cartPage.map().viewCartButton().click();

        cartPage.map().openAccordion(CartAccordion.ESTIMATE_SHIPPING_TAXES).click();
        cartPage.map().getQuotesButton().click();

        cartPage.asserts().assertEstimateShippingTaxesErrorsPresent();
    }

    @ParameterizedTest
    @EnumSource(GiftCertificate.class)
    @ValueSource(doubles = {2, 3, 5})
    public void useGiftCertificateSuccessfullyLikeRegisteredUser(GiftCertificate gift, double amount) {
        var registeredUser = new FakerDataGenerator().getRegisteredUser();
        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        var mainNavigationSection = new MainNavigationSection();
        mainNavigationSection.map().selectMenu(MainMenu.MY_ACCOUNT).hover();
        mainNavigationSection.map().selectMenu(MainMenu.VOUCHER).click();
        var recipient = new FakerDataGenerator().getRegisteredRecipients();

        accountVoucherPage.fillPurchaseGiftData(recipient, registeredUser.getLastName(), gift, amount);
        accountVoucherPage.map().continueButton().click();
        new SuccessPage().asserts().assertVoucherIsPurchased();

        new MainHeaderSection().map().mainHeaderNavigation(MainHeader.CART).click();
        cartPage.map().checkoutButtonInCart().click();
        checkoutPage.map().termsCheckbox().check();
        checkoutPage.map().continueButton().click();
        confirmPage.asserts().confirmPageIsLoaded();
        confirmPage.map().confirmOrderButton().click();

        new SuccessPage().asserts().asserThatPurchaseIsMade();

        new AccountPage().openMenuFromNavbar(Navbar.LOGOUT);

        loginPage.logIn(recipient.getRecipientEmail(), recipient.getRecipientPassword());
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.addItemToCart(Item.SONY_VAIO);
        cartPage.map().viewCartButton().click();

        cartPage.map().openAccordion(CartAccordion.USE_GIFT_CERTIFICATE).click();
        cartPage.enterGiftCertificate(gift);
        cartPage.map().applyGiftCertificate().click();

        cartPage.asserts().assertGiftCertificateIsApplied();
    }

    @Test
    public void UserHaveCorrectInformationForCartEcoTax() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.NIKON_D_300);

        cartPage.map().viewCartButton().click();

        cartPage.asserts().assertEcoTaxIsCorrect();
    }

    @Test
    public void UserHaveCorrectInformationForCartVAT() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.PALM_TREO_PRO);

        cartPage.map().viewCartButton().click();

        cartPage.asserts().assertVatIsCorrect();
    }

    @Test
    public void UserHaveCorrectInformationForCartTotalPrice() {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.map().listViewButton().click();
        productPage.addItemToCart(Item.PALM_TREO_PRO);

        cartPage.map().viewCartButton().click();

        cartPage.asserts().assertTotalPriceIsCorrect();
    }
}