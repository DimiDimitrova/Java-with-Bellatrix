package Pages.productpage;

import enums.ExtraProductContent;
import enums.Item;
import extensions.ConvertExtension;
import messages.ApplicationMessages;
import models.Product;
import solutions.bellatrix.web.pages.WebPage;

public class ProductPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io";
    }

    public void addItemToCart(Item product) {
        selectQuickView(product);

        if (!map().productExtraContent(ExtraProductContent.AVAILABILITY).getText().equals("In Stock")) {
            throw new IllegalArgumentException(ApplicationMessages.NOT_IN_STOCK);
        }

        map().addToCartItemButton().click();
    }

    public Product addProductToCart(Item product) {
        selectQuickView(product);

        if (!map().productExtraContent(ExtraProductContent.AVAILABILITY).getText().equals("In Stock")) {
            throw new IllegalArgumentException(ApplicationMessages.NOT_IN_STOCK);
        }

        var productInfo = setProductInfo();
        map().addToCartItemButton().click();

        return productInfo;
    }

    public void selectQuickView(Item productName) {
        browser().waitUntilPageLoadsCompletely();
        browser().waitForAjax();
        map().imageItem(productName).scrollToVisible();
        map().imageItem(productName).hover();
        browser().waitForAjax();
        map().quickViewButton(productName).click();
    }

    public Product setProductInfo() {
        var product = new Product();
        product.setTitle(map().productName().getText());
        product.setUnitPrice(ConvertExtension.getAmount(map().productPrice().getText()));
        var imageLink = map().productImage().getAttribute("src");
        String imageLinkWithoutSize = imageLink.substring(0, imageLink.lastIndexOf('/'));
        product.setImage(imageLinkWithoutSize);
        product.setModel(map().productExtraContent(ExtraProductContent.PRODUCT_CODE).getText());
        product.setInStock(map().productExtraContent(ExtraProductContent.AVAILABILITY).getText());
        product.setBrand(map().brand().getText());

        try {
            product.setRewardPoints(Integer.parseInt(map().productExtraContent(ExtraProductContent.REWARD_POINTS).getText()));
        } catch (Exception ex) {
            return product;
        }

        return product;
    }

    public Product addProductForCompare(Item product) {
        map().imageItem(product).click();

        var productInfo = setProduct();
        map().compareButton().click();

        return productInfo;
    }

    public Product setProduct() {
        var product = new Product();
        product.setTitle(map().product().getText());
        product.setUnitPrice(ConvertExtension.getAmount(map().productPrice().getText()));
        var imageLink = map().productImage().getAttribute("src");
        String imageLinkWithoutSize = imageLink.substring(0, imageLink.lastIndexOf('/'));
        product.setImage(imageLinkWithoutSize);
        product.setModel(map().productExtraContent(ExtraProductContent.PRODUCT_CODE).getText());
        product.setInStock(map().productExtraContent(ExtraProductContent.AVAILABILITY).getText());
        product.setBrand(map().brand().getText());

        try {
            product.setRewardPoints(Integer.parseInt(map().productExtraContent(ExtraProductContent.REWARD_POINTS).getText()));
        } catch (Exception exception) {
            return product;
        }

        return product;
    }
}