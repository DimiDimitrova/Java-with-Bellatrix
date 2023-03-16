package junit;

import Pages.accountpage.AccountPage;
import Pages.accountvoucherpage.AccountVoucherPage;
import Pages.cartpage.CartPage;
import Pages.checkoutpage.CheckoutPage;
import Pages.confirmpage.ConfirmPage;
import enums.*;
import fakers.PersonInfoFaker;
import fakers.RecipientInfoFaker;
import Pages.homepage.HomePage;
import Pages.loginpage.LoginPage;
import models.BaseEShopPage;
import models.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import Pages.productpage.ProductPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;
import Pages.successpage.SuccessPage;

import java.util.ArrayList;

public class ShoppingCartTests extends WebTest {
    protected CheckoutPage checkoutPage;
    protected HomePage homePage;
    protected CartPage cartPage;
    protected ProductPage productPage;
    protected LoginPage loginPage;
    protected SuccessPage successPage;
    protected ConfirmPage confirmPage;
    protected AccountPage accountPage;
    protected AccountVoucherPage accountVoucherPage;
    protected BaseEShopPage baseEShopPage;

    @Override
    protected void configure() {
        super.configure();
        homePage = app().create(HomePage.class);
        cartPage = app().create(CartPage.class);
        productPage = app().create(ProductPage.class);
        loginPage = app().create(LoginPage.class);
        checkoutPage = app().create(CheckoutPage.class);
        successPage = app().create(SuccessPage.class);
        confirmPage = app().create(ConfirmPage.class);
        accountPage = app().create(AccountPage.class);
        accountVoucherPage = app().create(AccountVoucherPage.class);
        baseEShopPage = new BaseEShopPage();
    }

    @Test
    public void openShoppingCartSuccessfully() {
        homePage.open();
        baseEShopPage.mainHeaderSection().map().mainHeaderNavigation(MainHeader.CART).click();
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
        var registeredUser = new PersonInfoFaker().getRegisteredUser();
        loginPage.logIn(registeredUser.getEmail(), registeredUser.getPassword());
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.MY_ACCOUNT).hover();
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.VOUCHER).click();
        var recipient = new RecipientInfoFaker().getRegisteredRecipients();

        accountVoucherPage.fillPurchaseGiftData(recipient, registeredUser.getLastName(), gift, amount);
        accountVoucherPage.map().continueButton().click();
        successPage.asserts().assertVoucherIsPurchased();

        baseEShopPage.mainHeaderSection().map().mainHeaderNavigation(MainHeader.CART).click();
        cartPage.map().checkoutButtonInCart().click();
        checkoutPage.map().termsCheckbox().check();
        checkoutPage.map().continueButton().click();
        confirmPage.asserts().confirmPageIsLoaded();
        confirmPage.map().confirmOrderButton().click();

        successPage.asserts().asserThatPurchaseIsMade();

        accountPage.openMenuFromNavbar(Navbar.LOGOUT);

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