package junit;

import enums.HomePageModuleTitle;
import homepage.HomePage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import productpage.ProductPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

public class HomePageTests extends WebTest {
    @ParameterizedTest
    @EnumSource(HomePageModuleTitle.class)
    public void homePageContainsInformationForModules(HomePageModuleTitle moduleTitle) {
        app().goTo(HomePage.class);

        new HomePage().asserts().assertModuleInformationPresent(moduleTitle);
    }

    @Test
    public void shopNowFromHomePageSuccessfully() {
        app().goTo(HomePage.class);

        new HomePage().map().shopNowAdButton().click();

        new ProductPage().asserts().assertProductPageIsOpen();
    }
}
