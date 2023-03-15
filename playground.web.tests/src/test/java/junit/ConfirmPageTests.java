package junit;

import checkoutpage.CheckoutPage;
import confirmpage.ConfirmPage;
import extensions.ConvertExtension;
import generatefakerdata.FakerDataGenerator;
import homepage.HomePage;
import org.junit.jupiter.api.Test;
import productpage.ProductPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;
import successpage.SuccessPage;
import models.*;
import enums.*;

import java.util.ArrayList;

public class ConfirmPageTests extends WebTest {
    private HomePage homePage;
    private ProductPage productPage;
    private CheckoutPage checkoutPage;
    private ConfirmPage confirmPage;
    private SuccessPage successPage;
    private FakerDataGenerator generator;

    public ConfirmPageTests() {
        successPage = new SuccessPage();
        homePage = new HomePage();
        productPage = new ProductPage();
        checkoutPage = new CheckoutPage();
        confirmPage = new ConfirmPage();
        generator = new FakerDataGenerator();
    }

    @Test
    public void ProductInformationIsInConfirmPageCorrectly() {
        var person = generator.getRegisteredUser();
        var paymentAddress = new PaymentAddressInfo();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        var product = productPage.setProduct();
        var products = new ArrayList<Product>();
        products.add(product);
        productPage.map().buyNowButton().click();

        checkoutPage.fillAccountDetails(person, paymentAddress, Account.LOGIN);
        checkoutPage.continuePurchase();

        confirmPage.asserts().assertProductContentIsCorrect(products);
    }

    @Test
    public void TotalSumIsCorrect() {
        var person = generator.getRegisteredUser();
        var paymentAddress = new PaymentAddressInfo();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        productPage.map().buyNowButton().click();

        var totalInCheckout = ConvertExtension.getAmount(checkoutPage.map().total().getText());
        checkoutPage.fillAccountDetails(person, paymentAddress, Account.LOGIN);
        checkoutPage.asserts().assertTotalPriceIsCorrect();
        checkoutPage.continuePurchase();

        confirmPage.asserts().assertTotalSum(totalInCheckout);
    }

    @Test
    public void PaymentAddressIsCorrect() {
        var person = generator.createPersonInfo();
        var paymentAddress = generator.createPaymentAddress();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        productPage.map().buyNowButton().click();

        checkoutPage.fillAccountDetails(person, paymentAddress, Account.GUEST_ACCOUNT);
        checkoutPage.continuePurchase();

        confirmPage.asserts().assertPaymentInfo(paymentAddress);
    }

    @Test
    public void EditOrderSuccessfully() {
        var person = generator.getRegisteredUser();
        var paymentAddress = new PaymentAddressInfo();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        productPage.map().buyNowButton().click();
        checkoutPage.fillAccountDetails(person, paymentAddress, Account.LOGIN);
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
        var person = generator.getRegisteredUser();
        var paymentAddress = new PaymentAddressInfo();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        productPage.map().buyNowButton().click();

        checkoutPage.fillAccountDetails(person, paymentAddress, Account.LOGIN);
        checkoutPage.continuePurchase();
        confirmPage.map().confirmOrderButton().click();

        successPage.asserts().asserThatPurchaseIsMade();
    }

    @Test
    public void ContinueSuccessfullyAfterConfirmPurchase() {
        var person = generator.getRegisteredUser();
        var paymentAddress = new PaymentAddressInfo();

        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        productPage.map().buyNowButton().click();

        checkoutPage.fillAccountDetails(person, paymentAddress, Account.LOGIN);
        checkoutPage.continuePurchase();
        confirmPage.map().confirmOrderButton().click();

        successPage.asserts().asserThatPurchaseIsMade();
        successPage.map().continueButton().click();
        homePage.asserts().assertHomePageIsLoaded();
    }
}