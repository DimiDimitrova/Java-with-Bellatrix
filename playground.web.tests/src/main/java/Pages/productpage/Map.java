package Pages.productpage;

import enums.ExtraProductContent;
import enums.Item;
import org.openqa.selenium.By;
import solutions.bellatrix.web.components.*;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Heading productName() {
        return create().byXPath(Heading.class, "//div[@id='product-quick-view']//h1");
    }

    public Heading product() {
        return create().byXPath(Heading.class, "//div[@id='product-product']//h1");
    }

    public Image productImage() {
        return create().byXPath(Image.class, "//div[contains(@class,'image-gallery')]//img");
    }

    public Heading productPrice() {
        return create().byXPath(Heading.class, "//div[@class='price']/h3");
    }

    public Button compareButton() {
        return create().byXPath(Button.class, "//button[contains(text(),'Compare this Product')]");
    }

    public Button addToCartItemButton() {
        return create().byXPath(Button.class, "//div[contains(@class,'entry-row')]//button[contains(@class,'button-cart')]");
    }

    public Button buyNowButton() {
        return create().byXPath(Button.class, "//button[contains(@class,'button-buynow')]");
    }

    public Button listViewButton() {
        return create().byId(Button.class, "list-view");
    }

    public Anchor brand() {
        return create().byXPath(Anchor.class, "//span[contains(text(),'Brand')]//following-sibling::a");
    }

    public Button quickViewButton(Item product) {
        var locator = String.format("//*[@class='product-action']//button[contains(@class,'quick-view-%d')]", product.getId());

        return create().byXPath(Button.class, locator);
    }

    public Button imageItem(Item productName) {
        String locator = String.format(
                "//div[@class='carousel-item active']//*[@title='%s']", productName.toString());

        return create().byXPath(Button.class, locator);
    }

    public Span productExtraContent(ExtraProductContent label) {
        String locator = String.format(
                "//*[contains(@class,'content-extra')]//span[contains(text(),'%s')]//following::span", label.toString());

        return create().byXPath(Span.class, locator);
    }
}