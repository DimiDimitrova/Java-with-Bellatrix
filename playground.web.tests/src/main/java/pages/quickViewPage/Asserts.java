package pages.quickViewPage;

import messages.ApplicationMessages;
import org.junit.jupiter.api.Assertions;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    public void assertQuantityButtonWork(int expectedQuantity) {
        Assertions.assertEquals(String.valueOf(expectedQuantity), map().quickViewQuantityInput().getAttribute("value"),
                String.format(ApplicationMessages.NOT_EQUAL_ERROR, expectedQuantity,
                        map().quickViewQuantityInput().getAttribute("value")));
    }
}