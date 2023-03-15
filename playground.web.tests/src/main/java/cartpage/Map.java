package cartpage;

import enums.Country;
import enums.CartAccordion;
import enums.TablePrice;
import solutions.bellatrix.web.components.*;
import solutions.bellatrix.web.pages.PageMap;

import java.util.List;

public class Map extends PageMap {
    public Button cartButton() {
        return create().byXPath(Button.class, "//a[contains(@href,'checkout/cart')]");
    }

    public Anchor checkoutButton() {
        return create().byXPath(Anchor.class, "//a[contains(text(),'Checkout')]");
    }

    public Anchor checkoutButtonInCart() {
        return create().byXPath(Anchor.class, "//*[text()=' Checkout']//parent::a");
    }

    public Anchor continueShoppingButton() {
        return create().byXPath(Anchor.class, "//a[contains(text(),'Continue Shopping')]");
    }

    public Div shoppingCartIsEmptyMessage() {
        return create().byXPath(Div.class, "//h1[contains(@Class,'page-title')]/following-sibling::p");
    }

    public List<Div> cartRowsContent() {
        return create().allByXPath(Div.class, "//div[@class='table-responsive']//tbody//tr");
    }

    public Anchor viewCartButton() {
        return create().byXPath(Anchor.class, "//a[contains(text(),'View Cart')]");
    }

    public Button countryShippingTaxes() {
        return create().byId(Button.class, "input-country");
    }

    public Button regionStateShippingTaxes() {
        return create().byId(Button.class, "input-zone");
    }

    public TextField postCodeStateShippingTaxes() {
        return create().byId(TextField.class, "input-postcode");
    }

    public Button getQuotesButton() {
        return create().byId(Button.class, "button-quote");
    }

    public Button flatRate() {
        return create().byXPath(Button.class, "//div[@class='form-check']//input");
    }

    public Label flatRateInformation() {
        return create().byXPath(Label.class, "//div[@class='form-check']//label");
    }

    public Button applyShippingButton() {
        return create().byId(Button.class, "button-shipping");
    }

    public Div regionInvalidMessage() {
        return create().byXPath(Div.class, "//*[@id='input-zone']//following::div[@class='invalid-feedback']");
    }

    public Div postCodeInvalidMessage() {
        return create().byXPath(Div.class, "//*[@id='input-postcode']//following::div[@class='invalid-feedback']");
    }

    public TextField voucherInput() {
        return create().byId(TextField.class, "input-voucher");
    }

    public Button applyGiftCertificate() {
        return create().byId(Button.class, "button-voucher");
    }

    public Div giftError() {
        return create().byXPath(Div.class, "//*[contains(@class,'fa-exclamation-circle')]");
    }

    public List<TextField> tableProductQuantity() {
        return create().allByXPath(TextField.class, "//div[@class='table-responsive']//tbody//tr//div/input");
    }

    public List<Div> tableProductUnitPrice() {
        return create().allByXPath(Div.class, "//div[@class='table-responsive']//tbody//tr//td[@class='text-right'][1]");
    }

    public Label ecoTax() {
        return create().byXPath(Label.class, "//table[contains(@class,'m-0')]//td[contains(text(),'Eco')]");
    }

    public Label vatTax() {
        return create().byXPath(Label.class, "//table[contains(@class,'m-0')]//td[contains(text(),'VAT')]");
    }

    public Button regionOption(String option) {
        return create().byXPath(Button.class, String.format("//*[@id='input-zone']/option[contains(@value,'%s')]", option));
    }

    public Button regionOption() {
        return create().byXPath(Button.class, String.format("//*[@id='input-zone']/option[contains(@value,'%s)]", "None"));
    }

    public Button countryOption(Country country) {
        return create().byXPath(Button.class, String.format(
                "//*[@id='input-country']/option[contains(text(),'%s')]", country.toString()));
    }

    public Button updateButton(String productName) {
        return create().byXPath(Button.class, String.format(
                "//tbody//td/a[contains(text(),'%s')]//following::i[contains(@class,'fa-sync-alt')]", productName));
    }

    public Label unitPrice(String product) {
        return create().byXPath(Label.class, String.format(
                "//*[contains(@class,'table-bordered')]//a[contains(text(),'%s')]//following::td[3]", product));
    }

    public Label total(String product) {
        return create().byXPath(Label.class, String.format(
                "//*[contains(@class,'table-bordered')]//a[contains(text(),'%s')]//following::td[4]", product));
    }

    public Button removeButton(String product) {
        return create().byXPath(Button.class, String.format(
                "//tbody//td/a[contains(text(),'%s')]//following::i[contains(@class,'fa-times-circle')]", product));
    }

    public TextField quantityField(String product) {
        return create().byXPath(TextField.class, String.format(
                "//tbody//td/a[contains(text(),'%s')]//following::div[@class = 'input-group flex-nowrap']/input", product));
    }

    public Button openAccordion(CartAccordion cartAccordion) {
        return create().byXPath(Button.class, String.format(
                "//*[contains(text(),'%s')]/*[contains(@Class,'fa-plus')]", cartAccordion.toString()));
    }

    public Label selectCartPriceInformation(TablePrice name) {
        return create().byXPath(Label.class, String.format(
                "//table[contains(@class,'m-0')]//td[contains(text(),'%s')]//following::strong", name.toString()));
    }

    public Label selectCartTotalInfo(TablePrice price) {
        return create().byXPath(Label.class, String.format(
                "//table[contains(@class,'table-bordered')]//td[text()='%s']//following-sibling::td[@class='text-right']/strong",
                price.toString()));
    }
}
