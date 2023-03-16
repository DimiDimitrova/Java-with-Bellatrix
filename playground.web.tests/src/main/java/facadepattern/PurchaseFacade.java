package facadepattern;

import Pages.cartpage.CartPage;
import Pages.checkoutpage.CheckoutPage;
import Pages.confirmpage.ConfirmPage;
import enums.Account;
import enums.CategoryInSearchBox;
import enums.Item;
import Pages.homepage.HomePage;
import models.PaymentAddressInfo;
import Pages.productpage.ProductPage;
import Pages.registerpage.PersonInfo;
import Pages.successpage.SuccessPage;

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