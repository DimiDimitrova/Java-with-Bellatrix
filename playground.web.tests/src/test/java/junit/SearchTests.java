package junit;

import enums.Brand;
import enums.Categories;
import enums.CategoryInSearchBox;
import Pages.homepage.HomePage;
import models.BaseEShopPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import Pages.searchpage.SearchPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

public class SearchTests extends WebTest {
    protected HomePage homePage;
    protected SearchPage searchPage;
    protected BaseEShopPage baseEShopPage;

    @Override
    protected void configure() {
        super.configure();
        homePage = app().create(HomePage.class);
        searchPage = app().create(SearchPage.class);
        baseEShopPage = new BaseEShopPage();
    }

    @ParameterizedTest
    @EnumSource(CategoryInSearchBox.class)
    public void searchByCategory(CategoryInSearchBox category) {
        homePage.searchByCategory(category);

        searchPage.asserts().assertBySearchedCategory(category);
    }

    @ParameterizedTest
    @EnumSource(Brand.class)
    public void searchByManufacturer(Brand brand) {
        homePage.searchByManufacturer(brand);

        searchPage.asserts().assertBySearchedManufacturer(brand);
    }

    @Test
    public void searchWithTopCategoryFilter() {
        homePage.searchByTopCategory(Categories.WASHING_MACHINE);

        baseEShopPage.megaMenuSection().asserts().assertThatCategoryPresentInThePage(Categories.WASHING_MACHINE);
    }

    @Test
    public void searchWithSearchInput() {
        String search = "canon";

        homePage.search(search);

        searchPage.asserts().assertBySearchedInput(search);
    }

    @Test
    public void SearchWithKeywords() {
        String search = "n";
        homePage.open();
        homePage.map().searchButton().click();

        searchPage.searchByKeywords(search);

        searchPage.asserts().assertBySearchedInput(search);
    }
}