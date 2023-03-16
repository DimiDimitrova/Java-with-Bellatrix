package pages.accountVoucherPage;

import messages.ApplicationMessages;
import models.BaseEShopPage;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    private BaseEShopPage baseEShopPage;

    public Asserts() {
        baseEShopPage = new BaseEShopPage();
    }

    public void assertPurchaseVoucherFailedWithIncorrectRecipientName() {
        baseEShopPage.breadcrumbSection().map().activePageTitle().validateTextIs("Gift Certificate");

        int RECIPIENT_NAME_MIN_SIZE = 1;
        int RECIPIENT_NAME_MAX_SIZE = 64;
        map().errorMessage().validateTextIs(String.format(
                ApplicationMessages.RECIPIENT_NAME_ERROR, RECIPIENT_NAME_MIN_SIZE, RECIPIENT_NAME_MAX_SIZE));

    }

    public void assertPurchaseVoucherFailedWithInvalidEmail() {
        map().errorMessage().validateTextIs(ApplicationMessages.EMAIL_ERROR);
    }
}