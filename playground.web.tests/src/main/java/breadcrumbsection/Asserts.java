package breadcrumbsection;

import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    public void assertMenuIsOpen(String expectedResult) {
        map().activePageTitle().validateTextIs(expectedResult);
    }
}