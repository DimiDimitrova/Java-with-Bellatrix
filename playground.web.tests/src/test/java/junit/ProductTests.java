package junit;

import pages.cartPage.CartPage;
import pages.checkoutPage.CheckoutPage;
import enums.Brand;
import enums.Item;
import pages.homePage.HomePage;
import models.Product;
import org.junit.jupiter.api.Test;
import pages.productPage.ProductPage;
import pages.quickViewPage.QuickViewPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

import java.util.ArrayList;

public class ProductTests extends WebTest {
    protected CheckoutPage checkoutPage;
    protected ProductPage productPage;
    protected HomePage homePage;
    protected CartPage cartPage;
    protected QuickViewPage quickView;

    @Override
    protected void configure() {
        super.configure();
        productPage = app().create(ProductPage.class);
        homePage = app().create(HomePage.class);
        cartPage = app().create(CartPage.class);
        checkoutPage = app().create(CheckoutPage.class);
        quickView = app().create(QuickViewPage.class);
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