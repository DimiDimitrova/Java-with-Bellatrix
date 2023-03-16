package sections.megaMenuSection;

import enums.Categories;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    public void assertThatCategoryPresentInThePage(Categories category) {
        String temp = category.toString();
        map().searchCategoryHeader().validateIsVisible();

        map().searchCategoryHeader().validateTextIs(temp);
    }

    public void assertMenuIsLoaded(String menu) {
        map().searchCategoryHeader().validateTextIs(menu);
    }
}