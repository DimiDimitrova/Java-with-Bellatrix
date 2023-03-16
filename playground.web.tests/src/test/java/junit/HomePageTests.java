package junit;

import enums.HomePageModuleTitle;
import pages.homePage.HomePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pages.productPage.ProductPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

public class HomePageTests extends WebTest {
    protected HomePage homePage;
    protected ProductPage productPage;

    @Override
    protected void configure() {
        super.configure();
        homePage = app().create(HomePage.class);
        productPage = app().create(ProductPage.class);
    }

    @ParameterizedTest
    @EnumSource(HomePageModuleTitle.class)
    public void homePageContainsInformationForModules(HomePageModuleTitle moduleTitle) {
        homePage.open();

        homePage.asserts().assertModuleInformationPresent(moduleTitle);
    }

    @Test
    public void shopNowFromHomePageSuccessfully() {
        homePage.open();

        homePage.map().shopNowAdButton().click();

        productPage.asserts().assertProductPageIsOpen();
    }
}