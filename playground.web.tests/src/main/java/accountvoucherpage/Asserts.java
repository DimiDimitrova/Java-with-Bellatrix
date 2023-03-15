package accountvoucherpage;

import breadcrumbsection.BreadcrumbSection;
import messages.ApplicationMessages;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    private final int RECIPIENT_NAME_MIN_SIZE = 1;
    private final int RECIPIENT_NAME_MAX_SIZE = 64;

    public void assertPurchaseVoucherFailedWithIncorrectRecipientName() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs("Gift Certificate");

        map().errorMessage().validateTextIs(String.format(
                ApplicationMessages.RECIPIENT_NAME_ERROR, RECIPIENT_NAME_MIN_SIZE, RECIPIENT_NAME_MAX_SIZE));

    }

    public void assertPurchaseVoucherFailedWithInvalidEmail() {
        map().errorMessage().validateTextIs(ApplicationMessages.EMAIL_ERROR);
    }
}