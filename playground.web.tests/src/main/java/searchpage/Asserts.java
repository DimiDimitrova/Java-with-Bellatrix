package searchpage;

import enums.Brand;
import enums.CategoryInSearchBox;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    public void assertBySearchedCategory(CategoryInSearchBox category) {
        map().searchCategoryDropDown(category.categoryId()).validateIsVisible();
    }

    public void assertBySearchedManufacturer(Brand manufacturer) {
        map().searchCriteriaInput(manufacturer.toString()).validateIsVisible();
    }

    public void assertBySearchedInput(String input) {
        map().searchValueInResult().validateTextContains(input);
    }
}