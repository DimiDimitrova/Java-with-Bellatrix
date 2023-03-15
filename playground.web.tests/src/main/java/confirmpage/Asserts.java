package confirmpage;

import enums.Item;
import enums.PageTitle;
import extensions.ConvertExtension;
import messages.ApplicationMessages;
import models.PaymentAddressInfo;
import models.Product;
import org.junit.jupiter.api.Assertions;
import solutions.bellatrix.web.pages.PageAsserts;
import solutions.bellatrix.web.services.App;

import java.util.List;

public class Asserts extends PageAsserts<Map> {
    public void assertTotalSum(double expectedTotalSum) {
        Assertions.assertEquals(ConvertExtension.getAmount(map().totalSum().getText()), expectedTotalSum,
                String.format(ApplicationMessages.SUM_ERROR, map().totalSum().getText()));
    }

    public void assertProductQuantityIsEdited(int quantity, Item product) {
        Assertions.assertEquals(quantity, Integer.parseInt(map().productQuantity(product).getText()),
                String.format(ApplicationMessages.NOT_EQUAL_ERROR,
                        Integer.parseInt(map().productQuantity(product).getText(), quantity)));
    }

    public void assertPaymentInfo(PaymentAddressInfo address) {
        String[] content = map().paymentTable().getText().split("\r\n");
        var regionCountry = String.format("%s,%s", address.getRegion(), address.getCountry());
        var postCodeCity = String.format("%s %s", address.getCity(), address.getPostCode());

        Assertions.assertEquals(address.getAddress(), content[1],
                String.format(ApplicationMessages.PAYMENT_ERROR, address.getAddress()));

        Assertions.assertEquals(postCodeCity, content[2]);
        Assertions.assertEquals(regionCountry, content[3],
                String.format(ApplicationMessages.PAYMENT_ERROR, regionCountry));
    }

    public void assertProductContentIsCorrect(List<Product> expectedProductInfo) {
        var actualProductsInfo = new ConfirmPage().getProductsInfo();

        if (actualProductsInfo.size() != expectedProductInfo.size()) {
            throw new IllegalArgumentException("The actual items are different than the expected ones.");
        }

        for (int i = 0; i < actualProductsInfo.size(); i++) {
            Assertions.assertEquals(actualProductsInfo.get(i).getProductName(), expectedProductInfo.get(i).getTitle(),
                    String.format(ApplicationMessages.NOT_EXISTS_IN_PAGE_ERROR, expectedProductInfo.get(i).getTitle()));


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

    public void confirmPageIsLoaded() {
        Assertions.assertTrue(new App().browser().getUrl().endsWith("checkout/confirm"),
                String.format(ApplicationMessages.PAGE_ERROR, PageTitle.CONFIRM.toString()));
    }
}