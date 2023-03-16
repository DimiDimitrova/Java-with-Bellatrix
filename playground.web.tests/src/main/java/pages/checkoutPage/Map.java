package pages.checkoutPage;

import enums.Account;
import enums.Country;
import enums.Item;
import enums.TablePrice;
import solutions.bellatrix.web.components.*;
import solutions.bellatrix.web.pages.PageMap;

import java.util.List;

public class Map extends PageMap {
    public TextField firstNameField() {
        return create().byId(TextField.class, "input-payment-firstname");
    }

    public TextField lastNameField() {
        return create().byId(TextField.class, "input-payment-lastname");
    }

    public TextField emailField() {
        return create().byId(TextField.class, "input-payment-email");
    }

    public TextField telephoneField() {
        return create().byId(TextField.class, "input-payment-telephone");
    }

    public TextField billingPaymentFirstAddressField() {
        return create().byId(TextField.class, "input-payment-address-1");
    }

    public TextField billingPaymentCityField() {
        return create().byId(TextField.class, "input-payment-city");
    }

    public TextField billingPaymentPostCodeField() {
        return create().byId(TextField.class, "input-payment-postcode");
    }

    public Button billingPaymentCountryDropDown() {
        return create().byId(Button.class, "input-payment-country");
    }

    public Button billingPaymentRegionField() {
        return create().byId(Button.class, "input-payment-zone");
    }

    public Button continueButton() {
        return create().byId(Button.class, "button-save");
    }

    public TextField paymentPassword() {
        return create().byId(TextField.class, "input-payment-password");
    }

    public TextField paymentConfirmPassword() {
        return create().byId(TextField.class, "input-payment-confirm");
    }

    public CheckBox termsCheckbox() {
        return create().byXPath(CheckBox.class, "//*[@id='input-agree']//parent::div//child::label");
    }

    public CheckBox privacyPoliceCheckbox() {
        return create().byXPath(CheckBox.class, "//*[@id='input-account-agree']//parent::div//child::label");
    }

    public TextField loginEmail() {
        return create().byId(TextField.class, "input-login-email");
    }

    public TextField loginPassword() {
        return create().byId(TextField.class, "input-login-password");
    }

    public Button loginButton() {
        return create().byId(Button.class, "button-login");
    }

    public CheckBox newPaymentAddress() {
        return create().byXPath(CheckBox.class, "//*[@id='input-payment-address-new']//parent::div//child::label");
    }

    public List<TextField> checkoutTableRows() {
        return create().allByXPath(TextField.class, "//div[@class='table-responsive']//tbody//tr");
    }

    public List<TextField> tableProductQuantity() {
        return create().allByXPath(TextField.class, "//div[@class='table-responsive']//tbody//tr//div");
    }

    public TextField total() {
        return create().byXPath(TextField.class,
                "//table[@id='checkout-total']//td[text()='Total:']//following::strong");
    }

    public Label firstNameError() {
        return create().byXPath(Label.class,
                "//input[@id='input-payment-firstname']//following::div[contains(@class,'invalid-feedback')]");
    }

    public Label lastNameError() {
        return create().byXPath(Label.class,
                "//input[@id='input-payment-lastname']//following::div[contains(@class,'invalid-feedback')]");
    }

    public Label emailError() {
        return create().byXPath(Label.class,
                "//input[@id='input-payment-email']//following::div[contains(@class,'invalid-feedback')]");
    }

    public Label telephoneError() {
        return create().byXPath(Label.class,
                "//input[@id='input-payment-telephone']//following::div[contains(@class,'invalid-feedback')]");
    }

    public Label passwordError() {
        return create().byXPath(Label.class,
                "//input[@id='input-payment-password']//following::div[contains(@class,'invalid-feedback')]");
    }

    public Label passwordConfirmationError() {
        return create().byXPath(Label.class,
                "//input[@id='input-payment-confirm']//following::div[contains(@class,'invalid-feedback')]");
    }

    public Label addressError() {
        return create().byXPath(Label.class,
                "//input[@id='input-payment-address-1']//following::div[contains(@class,'invalid-feedback')]");
    }

    public Div termsWarningMessage() {
        return create().byXPath(Div.class, "//*[@id='form-checkout']/div");
    }

    public Div cityError() {
        return create().byXPath(Div.class,
                "//input[@id='input-payment-city']//following::div[contains(@class,'invalid-feedback')]");
    }

    public Div countryError() {
        return create().byXPath(Div.class,
                "//select[@id='input-payment-country']//following::div[contains(@class,'invalid-feedback')]");
    }

    public Div postCodeError() {
        return create().byXPath(Div.class,
                "//input[@id='input-payment-postcode']//following::div[contains(@class,'invalid-feedback')]");
    }

    public CheckBox accountOption(Account option) {
        String locator = String.format(
                "//*[@id='input-account-%s']/following-sibling::label", option.toString());

        return create().byXPath(CheckBox.class, locator);
    }

    public Label selectCheckoutTotalInfo(TablePrice checkout) {
        String locator = String.format(
                "//table[@id='checkout-total']//td[contains(text(),'%s')]//following::strong", checkout.toString());

        return create().byXPath(Label.class, locator);
    }

    public TextField getVatTax() {
        String locator = String.format("//table[@id='checkout-total']//td[contains(text(),'%s')]",
                TablePrice.VAT);

        return create().byXPath(TextField.class, locator);
    }

    public TextField getEcoTax() {
        String locator = String.format("//table[@id='checkout-total']//td[contains(text(),'%s')]",
                TablePrice.ECO_TAX);

        return create().byXPath(TextField.class, locator);
    }

    public TextField unitPrice(Item product) {
        String locator = String.format(
                "//a[contains(text(),'%s')]//following::td//button[@data-original-title='Update']//following::td[1]",
                product.toString());

        return create().byXPath(TextField.class, locator);
    }

    public TextField totalOnProduct(Item product) {
        String locator = String.format(
                "//a[contains(text(),'%s')]//following::td//button[@data-original-title='Update']//following::td[2]",
                product.toString());

        return create().byXPath(TextField.class, locator);
    }

    public TextField quantityField(Item product) {
        String locator = String.format(
                "//div[@id='checkout-cart']//td/a[text()='%s']//following::td[@class='text-left']//input",
                product.toString());

        return create().byXPath(TextField.class, locator);
    }

    public Button updateButton(Item productName) {
        String locator = String.format(
                "//tbody//td/a[contains(text(),'%s')]//following::i[contains(@class,'fa-sync-alt')]",
                productName.toString());

        return create().byXPath(Button.class, locator);
    }

    public Button countryOption(Country country) {
        String locator = String.format(
                "//*[@id='input-payment-country']/option[contains(text(),'%s')]", country.toString());

        return create().byXPath(Button.class, locator);
    }

    public Button regionOption(String option) {
        String locator = String.format(
                "//*[@id='input-payment-zone']/option[contains(@value,'%s')]", option);

        return create().byXPath(Button.class, locator);
    }
}