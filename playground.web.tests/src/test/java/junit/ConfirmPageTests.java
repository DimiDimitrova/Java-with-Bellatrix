package junit;

import pages.checkoutPage.CheckoutPage;
import pages.confirmPage.ConfirmPage;
import extensions.ConvertExtension;
import fakers.PaymentAddressFaker;
import fakers.PersonInfoFaker;
import pages.homePage.HomePage;
import org.junit.jupiter.api.Test;
import pages.productPage.ProductPage;
import solutions.bellatrix.web.infrastructure.DriverService;
import solutions.bellatrix.web.infrastructure.junit.WebTest;
import models.*;
import enums.*;
import pages.successPage.SuccessPage;

import java.util.ArrayList;

public class ConfirmPageTests extends WebTest {
    protected SuccessPage successPage;
    protected HomePage homePage;
    protected ProductPage productPage;
    protected CheckoutPage checkoutPage;
    protected ConfirmPage confirmPage;
    protected PersonInfoFaker personInfoFaker;
    protected PaymentAddressFaker paymentAddressFaker;

    @Override
    protected void configure() {
        super.configure();
        homePage = app().create(HomePage.class);
        productPage = app().create(ProductPage.class);
        checkoutPage = app().create(CheckoutPage.class);
        confirmPage = app().create(ConfirmPage.class);
        successPage = app().create(SuccessPage.class);
        personInfoFaker = new PersonInfoFaker();
        paymentAddressFaker = new PaymentAddressFaker();
    }

    @Test
    public void ProductInformationIsInConfirmPageCorrectly() {
        var paymentAddress = new PaymentAddressInfo();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        var product = productPage.setProduct();
        var products = new ArrayList<Product>();
        products.add(product);
        productPage.map().buyNowButton().click();

        checkoutPage.fillAccountDetails(personInfoFaker.getRegisteredUser(), paymentAddress, Account.LOGIN);
        checkoutPage.continuePurchase();

        confirmPage.asserts().assertProductContentIsCorrect(products);
    }

    @Test
    public void TotalSumIsCorrect() {
        var paymentAddress = new PaymentAddressInfo();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        productPage.map().buyNowButton().click();

        var totalInCheckout = ConvertExtension.getAmount(checkoutPage.map().total().getText());
        checkoutPage.fillAccountDetails(personInfoFaker.getRegisteredUser(), paymentAddress, Account.LOGIN);
        checkoutPage.asserts().assertTotalPriceIsCorrect();
        checkoutPage.continuePurchase();

        confirmPage.asserts().assertTotalSum(totalInCheckout);
    }

    @Test
    public void PaymentAddressIsCorrect() {
        var paymentAddress = paymentAddressFaker.createPaymentAddress();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        productPage.map().buyNowButton().click();

        checkoutPage.fillAccountDetails(personInfoFaker.createPersonInfo(), paymentAddress, Account.GUEST_ACCOUNT);
        checkoutPage.continuePurchase();

        confirmPage.asserts().assertPaymentInfo(paymentAddress);
    }

    @Test
    public void EditOrderSuccessfully() {
        var paymentAddress = new PaymentAddressInfo();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        productPage.map().buyNowButton().click();
        checkoutPage.fillAccountDetails(personInfoFaker.getRegisteredUser(), paymentAddress, Account.LOGIN);
        checkoutPage.continuePurchase();

        confirmPage.asserts().confirmPageIsLoaded();
        confirmPage.map().editButton().click();

        checkoutPage.asserts().checkoutPageIsLoaded();

        checkoutPage.updateProductQuantity(10, Item.MAC_BOOK_PRO);
        checkoutPage.map().termsCheckbox().check();
        checkoutPage.continuePurchase();

        confirmPage.asserts().assertProductQuantityIsEdited(10, Item.MAC_BOOK_PRO);
    }

    @Test
    public void ConfirmOrderSuccessfully() {
        var paymentAddress = new PaymentAddressInfo();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        productPage.map().buyNowButton().click();

        checkoutPage.fillAccountDetails(personInfoFaker.getRegisteredUser(), paymentAddress, Account.LOGIN);
        checkoutPage.continuePurchase();
        confirmPage.map().confirmOrderButton().click();

        successPage.asserts().asserThatPurchaseIsMade();
    }

    @Test
    public void ContinueSuccessfullyAfterConfirmPurchase() {
        var paymentAddress = new PaymentAddressInfo();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        productPage.map().buyNowButton().click();

        checkoutPage.fillAccountDetails(personInfoFaker.getRegisteredUser(), paymentAddress, Account.LOGIN);
        checkoutPage.continuePurchase();
        confirmPage.map().confirmOrderButton().click();

        successPage.asserts().asserThatPurchaseIsMade();
        successPage.map().continueButton().click();
        homePage.asserts().assertHomePageIsLoaded();
    }
}