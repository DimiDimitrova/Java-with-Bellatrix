package Pages.confirmpage;

import extensions.ConvertExtension;
import models.ProductInfo;
import solutions.bellatrix.web.components.TextField;
import solutions.bellatrix.web.pages.WebPage;

import java.util.ArrayList;
import java.util.List;

public class ConfirmPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io";
    }

    public List<ProductInfo> getProductsInfo() {
        var products = new ArrayList<ProductInfo>();

        for (var tableRow : map().productsContent()) {
            var currentProductInfo = new ProductInfo();
            var currentRowCells = tableRow.createAllByTag(TextField.class, "td");//tableRow.findElements(By.tagName("td"));
            currentProductInfo.setProductName(currentRowCells.get(0).getText());
            currentProductInfo.setModel(currentRowCells.get(1).getText());
            currentProductInfo.setQuantity(Integer.parseInt(currentRowCells.get(2).getText()));
            currentProductInfo.setUnitPrice(ConvertExtension.getAmount(currentRowCells.get(3).getText()));
            currentProductInfo.setUnitPriceCurrency(ConvertExtension.getCurrencySign(currentRowCells.get(3).getText()));
            currentProductInfo.setTotal(ConvertExtension.getAmount(currentRowCells.get(4).getText()));
            currentProductInfo.setTotalCurrency(ConvertExtension.getCurrencySign(currentRowCells.get(4).getText()));
        }

        return products;
    }
}