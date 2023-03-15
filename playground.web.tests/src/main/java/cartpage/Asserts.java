package cartpage;

import breadcrumbsection.BreadcrumbSection;
import enums.PageTitle;
import enums.TablePrice;
import extensions.ConvertExtension;
import messages.ApplicationMessages;
import models.Product;
import org.junit.jupiter.api.Assertions;
import solutions.bellatrix.web.pages.PageAsserts;
import solutions.bellatrix.web.services.App;

import java.util.List;

public class Asserts extends PageAsserts<Map> {
    public void assertProductInfo(List<Product> expectedProductInfo) {
        var actualProductsInfo = new CartPage().shoppingTableContent();

        if (actualProductsInfo.size() != expectedProductInfo.size()) {
            throw new IllegalArgumentException("The actual items are different than the expected ones.");
        }

        for (int i = 0; i < actualProductsInfo.size(); i++) {
            Assertions.assertEquals(expectedProductInfo.get(i).getTitle(), actualProductsInfo.get(i).getProductName(),
                    String.format(
                            ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR, expectedProductInfo.get(i).getTitle()));

            Assertions.assertEquals(expectedProductInfo.get(i).getImage(), actualProductsInfo.get(i).getImage(),
                    String.format(
                            ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR, expectedProductInfo.get(i).getImage()));

            Assertions.assertEquals(expectedProductInfo.get(i).getUnitPrice(), actualProductsInfo.get(i).getUnitPrice(),
                    String.format(
                            ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR, expectedProductInfo.get(i).getUnitPrice()));

            Assertions.assertEquals(expectedProductInfo.get(i).getModel(), actualProductsInfo.get(i).getModel(),
                    String.format(
                            ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR, expectedProductInfo.get(i).getModel()));

            Assertions.assertEquals(expectedProductInfo.get(i).getUnitPrice() * actualProductsInfo.get(i).getQuantity(),
                    actualProductsInfo.get(i).getTotal(),
                    String.format(ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR,
                            expectedProductInfo.get(i).getUnitPrice() * actualProductsInfo.get(i).getQuantity()));
        }
    }

    public void assertCartPageIsOpen() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.asserts().assertMenuIsOpen("Shopping Cart");
    }

    public void assertQuantityAndTotalAreCorrect(int expectedQty, Product product) {

        Assertions.assertEquals(map().quantityField(product.getTitle()).getAttribute("value"),
                Integer.toString(expectedQty),
                String.format(ApplicationMessages.QTY_ERROR,
                        map().quantityField(product.getTitle()).getText(), Integer.toString(expectedQty)));

        var unitPriceBeforeUpdate = ConvertExtension.getAmount(map().unitPrice(product.getTitle()).getText());
        var unitPrice = unitPriceBeforeUpdate * expectedQty;
        var totalProductSum = ConvertExtension.getAmount(map().total(product.getTitle()).getText());

        Assertions.assertEquals(totalProductSum, unitPrice,
                String.format(ApplicationMessages.NOT_EQUAL_ERROR, totalProductSum, unitPrice));
    }

    public void assertMessageForEmptyCartIsDisplayed() {
        map().shoppingCartIsEmptyMessage().validateIsVisible();
    }

    public void assertFlatRateIsApplied(String appliedRate) {
        var cartPage = new CartPage();
        var rate = ConvertExtension.getAmount(appliedRate.substring(21, appliedRate.length()));
        var prices = cartPage.getPrices();

        Assertions.assertEquals(prices.get(1), rate,
                String.format(ApplicationMessages.NOT_EQUAL_ERROR, rate, prices.get(1)));
    }

    public void assertEstimateShippingTaxesErrorsPresent() {
        map().regionInvalidMessage().validateIsVisible();

        map().postCodeInvalidMessage().validateIsVisible();
    }

    public void assertGiftCertificateIsApplied() {
        map().giftError().validateIsVisible();
    }

    public void assertTotalPriceIsCorrect() {
        var total = ConvertExtension.getAmount(map().selectCartPriceInformation(TablePrice.TOTAL).getText());
        var cartPage = new CartPage();

        Assertions.assertEquals(total, cartPage.totalPriceProducts(),
                String.format(ApplicationMessages.NOT_EQUAL_ERROR, total, cartPage.totalPriceProducts()));
    }

    public void assertEcoTaxIsCorrect() {
        var ecoTax = ConvertExtension.getAmount(map().selectCartPriceInformation(TablePrice.ECO_TAX).getText());
        var estimatedEcoTax = new CartPage().estimateEcoTax();

        Assertions.assertEquals((estimatedEcoTax), ecoTax,
                String.format(ApplicationMessages.NOT_EQUAL_ERROR, ecoTax, estimatedEcoTax));
    }

    public void assertVatIsCorrect() {
        var totalVat = ConvertExtension.getAmount(map().selectCartPriceInformation(TablePrice.VAT).getText());
        var estimatedVat = new CartPage().estimateVAT();
        Assertions.assertEquals(totalVat, estimatedVat,
                String.format(ApplicationMessages.NOT_EQUAL_ERROR, totalVat, estimatedVat));
    }

    public void assertShoppingCartIsEmpty() {
        Assertions.assertTrue(new App().browser().getUrl().endsWith("checkout/cart"),
                String.format(ApplicationMessages.PAGE_ERROR, PageTitle.CART.toString()));

        map().shoppingCartIsEmptyMessage().validateIsVisible();
    }
}