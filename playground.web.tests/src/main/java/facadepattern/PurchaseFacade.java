package facadepattern;

import cartpage.CartPage;
import checkoutpage.CheckoutPage;
import confirmpage.ConfirmPage;
import enums.Account;
import enums.CategoryInSearchBox;
import enums.Item;
import homepage.HomePage;
import models.PaymentAddressInfo;
import productpage.ProductPage;
import registerpage.PersonInfo;
import successpage.SuccessPage;

public class PurchaseFacade {
    private HomePage homePage;
    private CartPage cartPage;
    private ProductPage productPage;
    private CheckoutPage checkoutPage;
    private ConfirmPage confirmPage;
    private SuccessPage successPage;

    public PurchaseFacade(HomePage homePage, CartPage cartPage, ProductPage productPage,
                          CheckoutPage checkoutPage, ConfirmPage confirmPage, SuccessPage successPage) {
        this.homePage = homePage;
        this.cartPage = cartPage;
        this.productPage = productPage;
        this.checkoutPage = checkoutPage;
        this.confirmPage = confirmPage;
        this.successPage = successPage;
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
