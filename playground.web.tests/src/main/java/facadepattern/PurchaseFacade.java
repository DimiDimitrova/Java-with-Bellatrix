package facadepattern;

import pages.cartPage.CartPage;
import pages.checkoutPage.CheckoutPage;
import pages.confirmPage.ConfirmPage;
import enums.Account;
import enums.CategoryInSearchBox;
import enums.Item;
import pages.homePage.HomePage;
import models.PaymentAddressInfo;
import pages.productPage.ProductPage;
import pages.registerPage.PersonInfo;
import pages.successPage.SuccessPage;

public class PurchaseFacade {
    protected HomePage homePage;
    protected CheckoutPage checkoutPage;
    protected ConfirmPage confirmPage;
    protected CartPage cartPage;
    protected ProductPage productPage;
    protected SuccessPage successPage;

    public PurchaseFacade() {
        homePage = new HomePage();
        checkoutPage = new CheckoutPage();
        confirmPage = new ConfirmPage();
        cartPage = new CartPage();
        productPage = new ProductPage();
        successPage = new SuccessPage();
    }

    public void purchaseItem(Item product, PersonInfo person, PaymentAddressInfo paymentAddress, Account account) {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.addItemToCart(product);
        cartPage.map().checkoutButton().click();

        checkoutPage.fillAccountDetails(person, paymentAddress, account);
        checkoutPage.asserts().assertTotalPriceIsCorrect();
        checkoutPage.continuePurchase();

        confirmPage.asserts().assertTotalSum(checkoutPage.totalPrice);
        confirmPage.asserts().assertPaymentInfo(paymentAddress);
        confirmPage.map().confirmOrderButton().click();
        successPage.asserts().asserThatPurchaseIsMade();
    }

    public void purchaseValidation(Item product, PersonInfo person, PaymentAddressInfo paymentAddress, Account account) {
        homePage.searchByCategory(CategoryInSearchBox.COMPONENTS);
        productPage.addItemToCart(product);
        cartPage.map().checkoutButton().click();

        checkoutPage.asserts().assertTotalPriceIsCorrect();

        checkoutPage.fillAccountDetails(person, paymentAddress, account);
        checkoutPage.continuePurchase();
    }
}