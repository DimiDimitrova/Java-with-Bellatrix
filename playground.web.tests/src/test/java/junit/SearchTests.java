package junit;

import enums.Brand;
import enums.Categories;
import enums.CategoryInSearchBox;
import homepage.HomePage;
import megamenusection.MegaMenuSection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import searchpage.SearchPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

public class SearchTests extends WebTest {
    @ParameterizedTest
    @EnumSource(CategoryInSearchBox.class)
    public void searchByCategory(CategoryInSearchBox category) {
       // app().goTo(HomePage.class).open();
        new HomePage().searchByCategory(category);

        new SearchPage().asserts().assertBySearchedCategory(category);
    }

    @ParameterizedTest
    @EnumSource(Brand.class)
    public void searchByManufacturer(Brand brand) {
        new HomePage().searchByManufacturer(brand);

        new SearchPage().asserts().assertBySearchedManufacturer(brand);
    }

    @Test
    public void searchWithTopCategoryFilter() {
        new HomePage().searchByTopCategory(Categories.WASHING_MACHINE);

        new MegaMenuSection().asserts().assertThatCategoryPresentInThePage(Categories.WASHING_MACHINE);
    }

    @Test
    public void searchWithSearchInput() {
        String search = "canon";

        new HomePage().search(search);

        new SearchPage().asserts().assertBySearchedInput(search);
    }

    @Test
    public void SearchWithKeywords() {
        String search = "n";
        app().goTo(HomePage.class);
        new HomePage().map().searchButton().click();

        new SearchPage().searchByKeywords(search);

        new SearchPage().asserts().assertBySearchedInput(search);
    }
}