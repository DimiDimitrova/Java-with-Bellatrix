package Pages.cartpage;

import enums.Country;
import enums.GiftCertificate;
import enums.TablePrice;
import extensions.ConvertExtension;
import models.ProductInfo;
import solutions.bellatrix.web.components.Anchor;
import solutions.bellatrix.web.components.Image;
import solutions.bellatrix.web.components.TextField;
import solutions.bellatrix.web.pages.WebPage;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io/index.php?route=checkout/cart";
    }

    public double totalPriceProducts() {
        double total = 0;

        for (var quantity : productsQuantity()) {
            for (var price : productsPrice()) {
                total += quantity * price;
            }
        }

        return total;
    }

    public void updateProductQuantityInCart(int newQuantity, String product) {
        map().quantityField(product).setText(Integer.toString(newQuantity));
        browser().waitUntilPageLoadsCompletely();
        map().updateButton(product).click();
    }

    public void fillEstimateShippingTaxes(Country country, String postCode) {
        map().countryShippingTaxes().click();
        map().countryOption(country).click();
        map().regionStateShippingTaxes().click();
        browser().waitForAjax();

        try {
            map().regionOption("8").click();
        } catch (Exception ex) {
            map().regionOption().click();
        }

        map().postCodeStateShippingTaxes().setText(postCode);
    }

    public List<Double> getPrices() {
        var prices = new ArrayList<Double>();
        prices.add(ConvertExtension.getAmount(map().selectCartTotalInfo(TablePrice.SUB_TOTAL).getText()));
        prices.add(ConvertExtension.getAmount(map().selectCartTotalInfo(TablePrice.FLAT_SHIPPING_RATE).getText()));
        prices.add(ConvertExtension.getAmount(map().selectCartTotalInfo(TablePrice.TOTAL).getText()));

        return prices;
    }

    public void enterGiftCertificate(GiftCertificate giftTheme) {
        map().voucherInput().setText(giftTheme.toString());
    }

    public List<ProductInfo> shoppingTableContent() {
        var products = new ArrayList<ProductInfo>();

        for (var tableRow : map().cartRowsContent()) {
            var currentProductInfo = new ProductInfo();

            var currentRowCells = tableRow.createAllByTag(TextField.class, "td");
            currentProductInfo.setProductLink(currentRowCells.get(0).createByTag(Anchor.class, "a").getHref());
            var imageLink = currentRowCells.get(0).createByTag(Image.class, "img").getAttribute("src");
            String imageLinkWithoutSize = imageLink.substring(0, imageLink.lastIndexOf('/'));
            currentProductInfo.setImage(imageLinkWithoutSize);
            currentProductInfo.setProductName(currentRowCells.get(1).getText());
            currentProductInfo.setModel(currentRowCells.get(2).getText());
            currentProductInfo.setQuantity(Integer.parseInt(currentRowCells.get(3).createByXPath(TextField.class,
                    "//*[contains(@name, 'quantity')]").getAttribute("value")));
            currentProductInfo.setUnitPrice(ConvertExtension.getAmount(currentRowCells.get(4).getText()));
            currentProductInfo.setUnitPriceCurrency(ConvertExtension.getCurrencySign(currentRowCells.get(4).getText()));
            currentProductInfo.setTotal(ConvertExtension.getAmount(currentRowCells.get(5).getText()));
            currentProductInfo.setTotalCurrency(ConvertExtension.getCurrencySign(currentRowCells.get(5).getText()));

            products.add(currentProductInfo);
        }

        return products;
    }

    private List<Integer> productsQuantity() {
        var list = new ArrayList<Integer>();

        for (var item : map().tableProductQuantity()) {
            list.add(Integer.parseInt(item.getAttribute("value")));
        }

        return list;
    }

    private List<Double> productsPrice() {
        var list = new ArrayList<Double>();
        for (var item : map().tableProductUnitPrice()) {
            list.add(ConvertExtension.getAmount(item.getText()));
        }

        return list;
    }

    public double estimateEcoTax() {
        var number = ConvertExtension.getTax(map().ecoTax().getText());
        double ecoTax = countOfProducts() * number;

        return ecoTax;
    }

    public double estimateVAT() {
        String subTotal = map().selectCartTotalInfo(TablePrice.SUB_TOTAL).getText();
        double vat = ConvertExtension.getTax(map().vatTax().getText());

        return Math.round((ConvertExtension.getAmount(subTotal) * vat) / 100);
    }

    private int countOfProducts() {
        int count = productsQuantity().stream().mapToInt(item -> item).sum();

        return count;
    }
}