package Pages.checkoutpage;

import Sections.breadcrumbsection.BreadcrumbSection;
import enums.Item;
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
    private final int PASSWORD_MIN_SIZE = 4;
    private final int PASSWORD_MAX_SIZE = 20;
    private final int FIRST_NAME_MIN_SIZE = 1;
    private final int FIRST_NAME_MAX_SIZE = 32;
    private final int LAST_NAME_MIN_SIZE = 1;
    private final int LAST_NAME_MAX_SIZE = 32;
    private final int TELEPHONE_MIN_SIZE = 3;
    private final int TELEPHONE_MAX_SIZE = 32;
    private final int ADDRESS_MIN_SIZE = 3;
    private final int ADDRESS_MAX_SIZE = 128;
    private final int CITY_MIN_SIZE = 2;
    private final int CITY_MAX_SIZE = 128;
    private final int POST_CODE_MIN_SIZE = 2;
    private final int POST_CODE_MAX_SIZE = 10;

    public void checkoutPageIsLoaded() {
        Assertions.assertTrue(new App().browser().getUrl().endsWith("checkout/checkout"),
                String.format(ApplicationMessages.PAGE_ERROR, PageTitle.CHECKOUT.toString()));
    }

    public void assertProductsInfo(List<Product> expectedProductInfo) {
        var actualProductsInfo = new CheckoutPage().getProductsInfo();

        if (actualProductsInfo.size() != expectedProductInfo.size()) {
            throw new IllegalArgumentException("The actual items are different than the expected ones.");
        }

        for (int i = 0; i < actualProductsInfo.size(); i++) {
            Assertions.assertEquals(actualProductsInfo.get(i).getProductName(), expectedProductInfo.get(i).getTitle(),
                    String.format(ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR, expectedProductInfo.get(i).getTitle()));

            Assertions.assertEquals(actualProductsInfo.get(i).getImage(), expectedProductInfo.get(i).getImage(),
                    String.format(ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR, expectedProductInfo.get(i).getImage()));

            Assertions.assertEquals(actualProductsInfo.get(i).getUnitPrice(), expectedProductInfo.get(i).getUnitPrice(),
                    String.format(ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR, expectedProductInfo.get(i).getUnitPrice()));

            Assertions.assertEquals(actualProductsInfo.get(i).getModel(), expectedProductInfo.get(i).getModel(),
                    String.format(ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR, expectedProductInfo.get(i).getModel()));

            Assertions.assertEquals(actualProductsInfo.get(i).getTotal(),
                    expectedProductInfo.get(i).getUnitPrice() * actualProductsInfo.get(i).getQuantity(),
                    String.format(ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR,
                            expectedProductInfo.get(i).getUnitPrice() * actualProductsInfo.get(i).getQuantity()));
        }
    }

    public void assertQuantityAndTotalAreCorrect(int expectedQty, Item product) {
        Assertions.assertTrue(map().quantityField(product).getAttribute("value").
                        equals(String.valueOf(expectedQty)),
                String.format(ApplicationMessages.QTY_ERROR,
                        map().quantityField(product).getText(), String.valueOf(expectedQty)));

        var unitPriceBeforeUpdate = ConvertExtension.getAmount(map().unitPrice(product).getText());
        var unitPrice = unitPriceBeforeUpdate * expectedQty;
        var totalProductSum = ConvertExtension.getAmount(map().totalOnProduct(product).getText());

        Assertions.assertEquals(totalProductSum, unitPrice,
                String.format(ApplicationMessages.NOT_EQUAL_ERROR, totalProductSum, unitPrice));
    }

    public void assertTotalPriceIsCorrect() {
        var total = ConvertExtension.getAmount(map().total().getText());
        var checkoutTotal = new CheckoutPage().totalPrice = total;

        Assertions.assertEquals(checkoutTotal, new CheckoutPage().totalPriceWithTaxes(),
                String.format(ApplicationMessages.NOT_EQUAL_ERROR, checkoutTotal, new CheckoutPage().totalPriceWithTaxes()));
    }

    public void assertIncorrectFirstNameValidation() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        map().firstNameError().validateTextIs(String.format(
                ApplicationMessages.FIRST_NAME_ERROR, FIRST_NAME_MIN_SIZE, FIRST_NAME_MAX_SIZE));
    }

    public void assertIncorrectLastNameValidation() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        var expectedLastNameValidationMessage = String.format(ApplicationMessages.LAST_NAME_ERROR,
                LAST_NAME_MIN_SIZE, LAST_NAME_MAX_SIZE);
        map().lastNameError().validateTextIs(expectedLastNameValidationMessage);
    }

    public void assertIncorrectPostCodeValidation() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        map().postCodeError().validateTextIs(String.format(
                ApplicationMessages.POSTCODE_ERROR, POST_CODE_MIN_SIZE, POST_CODE_MAX_SIZE));
    }

    public void assertIncorrectAddressValidation() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        map().addressError().validateTextIs(String.format(
                ApplicationMessages.ADDRESS_ERROR, ADDRESS_MIN_SIZE, ADDRESS_MAX_SIZE));
    }

    public void assertIncorrectCityValidation() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        map().cityError().validateTextIs(String.format(ApplicationMessages.CITY_ERROR, CITY_MIN_SIZE, CITY_MAX_SIZE));
    }

    public void assertPasswordsMismatchValidation() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());
    }

    public void assertEmptyConfirmPasswordValidation() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        map().passwordConfirmationError().validateTextIs(ApplicationMessages.PASSWORD_CONFIRMATION_ERROR);
    }

    public void assertOrderFailedWithExistEmail() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        map().emailError().validateIsVisible();
    }

    public void assertIncorrectPhoneValidation() {

        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        map().telephoneError().validateTextIs(
                String.format(ApplicationMessages.TELEPHONE_ERROR, TELEPHONE_MIN_SIZE, TELEPHONE_MAX_SIZE));
    }

    public void assertIncorrectPasswordValidation() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        map().passwordError().validateTextIs(
                String.format(ApplicationMessages.PASSWORD_ERROR, PASSWORD_MIN_SIZE, PASSWORD_MAX_SIZE));
    }

    public void assertIncorrectCountryValidation() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        map().countryError().validateTextIs(ApplicationMessages.COUNTRY_ERROR);
    }

    public void assertTermConditionsRequiredAgreementValidation() {
        new BreadcrumbSection().map().activePageTitle().validateTextIs(PageTitle.CHECKOUT.toString());

        map().termsWarningMessage().validateIsVisible();
    }

    public void assertEcoTaxIsCorrect() {
        var ecoTax = ConvertExtension.getAmount(map().selectCheckoutTotalInfo(TablePrice.ECO_TAX).getText());
        var estimatedEcoTax = new CheckoutPage().estimateEcoTax();

        Assertions.assertEquals(estimatedEcoTax, ecoTax,
                String.format(ApplicationMessages.NOT_EQUAL_ERROR, ecoTax, estimatedEcoTax));
    }

    public void assertVatIsCorrect() {
        var totalVat = ConvertExtension.getAmount(map().selectCheckoutTotalInfo(TablePrice.VAT).getText());
        var estimatedVat = new CheckoutPage().estimateVAT();

        Assertions.assertEquals(totalVat, estimatedVat,
                String.format(ApplicationMessages.NOT_EQUAL_ERROR, totalVat, estimatedVat));
    }
}