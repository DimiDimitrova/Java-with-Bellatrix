package junit;

import cartpage.CartPage;
import checkoutpage.CheckoutPage;
import enums.Brand;
import enums.Item;
import homepage.HomePage;
import models.Product;
import org.junit.jupiter.api.Test;
import productpage.ProductPage;
import quickviewpage.QuickViewPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

import java.util.ArrayList;

public class ProductTests extends WebTest {
    private HomePage homePage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private QuickViewPage quickView;

    public ProductTests() {
        homePage = new HomePage();
        productPage = new ProductPage();
        cartPage = new CartPage();
        checkoutPage = new CheckoutPage();
        quickView = new QuickViewPage();
    }


    @Test
    public void viewProductInformation() {
        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.IPOD_NANO).click();

        productPage.asserts().assertProductInformationIsDisplayed();
    }

    @Test
    public void addProductToCartSuccessfully() {
        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.IPOD_NANO).click();
        var product = productPage.setProduct();
        var products = new ArrayList<Product>();
        products.add(product);

        productPage.map().addToCartItemButton().click();
        cartPage.map().viewCartButton().click();

        cartPage.asserts().assertProductInfo(products);
    }

    @Test
    public void buyProductSuccessfully() {
        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().imageItem(Item.MAC_BOOK_PRO).click();
        var product = productPage.setProduct();
        var products = new ArrayList<Product>();
        products.add(product);

        productPage.map().buyNowButton().click();

        checkoutPage.asserts().checkoutPageIsLoaded();
        checkoutPage.asserts().assertProductsInfo(products);
    }

    @Test
    public void increaseQuantitySuccessfully_When_QuantityFieldIsEmpty() {
        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().listViewButton().click();
        productPage.selectQuickView(Item.IPOD_SHUFFLE);

        quickView.map().quickViewQuantityInput().setText("");
        quickView.map().increaseQtyButton().click();

        quickView.asserts().assertQuantityButtonWork(1);
    }

    @Test
    public void DecreaseQuantitySuccessfully() {
        homePage.searchByManufacturer(Brand.APPLE);
        productPage.map().listViewButton().click();
        productPage.selectQuickView(Item.IPOD_SHUFFLE);

        quickView.map().increaseQtyButton().click();
        quickView.map().decreaseQtyButton().click();

        quickView.asserts().assertQuantityButtonWork(1);
    }
}