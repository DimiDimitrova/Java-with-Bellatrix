package pages.checkoutPage;

import enums.Account;
import enums.Item;
import enums.TablePrice;
import extensions.ConvertExtension;
import models.PaymentAddressInfo;
import pages.registerPage.PersonInfo;
import models.ProductInfo;
import solutions.bellatrix.web.components.Anchor;
import solutions.bellatrix.web.components.Image;
import solutions.bellatrix.web.components.TextField;
import solutions.bellatrix.web.pages.WebPage;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io/index.php?route=checkout/checkout";
    }

    public static double totalPrice = 0;

    public void fillPaymentAddress(PaymentAddressInfo billingAddress) {
        map().billingPaymentFirstAddressField().setText(billingAddress.getAddress());
        map().billingPaymentCityField().setText(billingAddress.getCity());
        map().billingPaymentPostCodeField().setText(billingAddress.getPostCode());
        map().billingPaymentCountryDropDown().click();
        map().countryOption(billingAddress.getCountry()).click();
        map().billingPaymentRegionField().click();
        browser().waitForAjax();

        try {
            billingAddress.setRegion(map().regionOption("1").getText());
            map().regionOption("1").click();
        } catch (Exception ex) {
            billingAddress.setRegion(map().regionOption("0").getText());
            map().regionOption("0").click();
        }
    }

    public void checkConditions(Account option) {
        switch (option) {
            case LOGIN -> {
                map().termsCheckbox().check();
            }
            case REGISTER_ACCOUNT -> {
                map().privacyPoliceCheckbox().check();
                map().termsCheckbox().check();
            }
            case GUEST_ACCOUNT -> {
                map().termsCheckbox().check();
            }
        }
    }

    public void continuePurchase() {
        browser().waitUntilPageLoadsCompletely();
        map().continueButton().click();
        browser().waitForAjax();
    }

    public void fillPersonalDetails(PersonInfo personInfo) {
        map().firstNameField().setText(personInfo.getFirstName());
        map().lastNameField().setText(personInfo.getLastName());
        map().emailField().setText(personInfo.getEmail());
        map().telephoneField().setText(personInfo.getTelephone());
    }

    public void fillPasswordFields(String password, String confirmPassword) {
        map().paymentPassword().setText(password);
        map().paymentConfirmPassword().setText(confirmPassword);
    }

    public void fillPasswordFields(String password) {
        map().paymentPassword().setText(password);
        map().paymentConfirmPassword().setText(password);
    }

    public void fillAccountDetails(PersonInfo person, PaymentAddressInfo paymentAddress, Account option) {
        map().accountOption(option).check();
        switch (option) {
            case LOGIN -> {
                fillLoginAccount(person.getEmail(), person.getPassword());
                browser().waitForAjax();
                map().newPaymentAddress().check();
                map().firstNameField().setText(person.getFirstName());
                map().lastNameField().setText(person.getLastName());
                fillPaymentAddress(paymentAddress);
            }
            case REGISTER_ACCOUNT -> {
                fillPersonalDetails(person);
                fillPaymentAddress(paymentAddress);
                fillPasswordFields(person.getPassword());
            }
            case GUEST_ACCOUNT -> {
                fillPersonalDetails(person);
                fillPaymentAddress(paymentAddress);
            }
        }

        checkConditions(option);
        browser().waitForAjax();
    }

    public List<Double> getPrices() {
        List<Double> prices = new ArrayList<Double>();
        prices.add(ConvertExtension.getAmount(map().selectCheckoutTotalInfo(TablePrice.SUB_TOTAL).getText()));
        try {
            prices.add(ConvertExtension.getAmount(map().selectCheckoutTotalInfo(TablePrice.FLAT_SHIPPING_RATE).getText()));
        } catch (Exception ex) {
            prices.add(ConvertExtension.getAmount(map().selectCheckoutTotalInfo(TablePrice.ECO_TAX).getText()));
            prices.add(ConvertExtension.getAmount(map().selectCheckoutTotalInfo(TablePrice.VAT).getText()));
            return prices;
        }
        prices.add(ConvertExtension.getAmount(map().selectCheckoutTotalInfo(TablePrice.ECO_TAX).getText()));
        prices.add(ConvertExtension.getAmount(map().selectCheckoutTotalInfo(TablePrice.VAT).getText()));

        return prices;
    }

    public List<ProductInfo> getProductsInfo() {
        var products = new ArrayList<ProductInfo>();

        for (var tableRow : map().checkoutTableRows()) {
            var currentProductInfo = new ProductInfo();

            var currentRowCells = tableRow.createAllByTag(TextField.class, "td");
            currentProductInfo.setProductLink(currentRowCells.get(0).createByTag(Anchor.class,"a").getHref());
            var imageLink = currentRowCells.get(0).createByTag(Image.class,"img").getSrc();
            String imageLinkWithoutSize = imageLink.substring(0, imageLink.lastIndexOf('/'));
            currentProductInfo.setImage(imageLinkWithoutSize);
            String[] product = currentRowCells.get(1).getText().split("\n");
            currentProductInfo.setProductName(product[0]);
            currentProductInfo.setModel(product[2].replace("Model: ", ""));
            currentProductInfo.setQuantity(Integer.parseInt(currentRowCells.get(2).createByXPath(
                    TextField.class,"//*[contains(@id, 'quantity')]").getAttribute("value")));
            currentProductInfo.setUnitPrice(ConvertExtension.getAmount(currentRowCells.get(3).getText()));
            currentProductInfo.setUnitPriceCurrency(ConvertExtension.getCurrencySign(currentRowCells.get(4).
                    getText()));
            currentProductInfo.setTotal(ConvertExtension.getAmount(currentRowCells.get(4).getText()));
            currentProductInfo.setTotalCurrency(ConvertExtension.getCurrencySign(currentRowCells.get(4).getText()));

            products.add(currentProductInfo);
        }

        return products;
    }

    public void updateProductQuantity(int newQuantity, Item product) {
        map().quantityField(product).setText(String.valueOf(newQuantity));
        browser().waitUntilPageLoadsCompletely();
    }

    public double totalPriceWithTaxes() {
        double totalSum = 0;
        var prices = getPrices();
        for (int i = 0; i < prices.size(); i++) {
            totalSum += prices.get(i);
        }

        return totalSum;
    }

    public double estimateEcoTax() {
        return countOfProducts() * getEco() + getEco();
    }

    private void fillLoginAccount(String email, String password) {
        map().loginEmail().setText(email);
        map().loginPassword().setText(password);
        map().loginButton().click();
    }

    private double getVat() {
        return ConvertExtension.getAmount(map().getVatTax().getText());
    }

    private double getEco() {
        return ConvertExtension.getAmount(map().getEcoTax().getText());
    }

    public double estimateVAT() {
        var flatShippingRate = ConvertExtension.getAmount(
                map().selectCheckoutTotalInfo(TablePrice.FLAT_SHIPPING_RATE).getText());
        var subTotal = ConvertExtension.getAmount(
                map().selectCheckoutTotalInfo(TablePrice.SUB_TOTAL).getText());

        return ((subTotal + flatShippingRate) * getVat()) / 100;
    }

    private int countOfProducts() {
        var count = 0;

        for (var item : productsQuantityCheckout()) {
            count += item;
        }

        return count;
    }

    private List<Integer> productsQuantityCheckout() {
        var list = new ArrayList<Integer>();

        for (var item : map().tableProductQuantity()) {
            for (var cell : item.createAllByTag(TextField.class, "input")) {
                 list.add(Integer.parseInt(cell.getAttribute("value")));
            }

         /*   for (var cell : item.findElement(By.tagName("input")) {
             */   //list.add(Integer.parseInt(cell.getAttribute("value")));
           // }
        }

        return list;
    }
}