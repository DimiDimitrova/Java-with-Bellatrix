package accountorderpage;

import breadcrumbsection.BreadcrumbSection;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    public void accountOrderHistoryIsDisplayed() {
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().pageTitle().validateTextIs("Order History");

        try {
            map().orderTable().validateIsVisible();
        } catch (Exception exception) {
            map().emptyHistory().validateIsVisible();
        }
    }
}