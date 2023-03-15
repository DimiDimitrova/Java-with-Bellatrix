package junit;

import breadcrumbsection.BreadcrumbSection;
import enums.*;
import homepage.HomePage;
import mainnavigationsection.MainNavigationSection;
import megamenusection.MegaMenuSection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import productpage.ProductPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

public class MenuTests extends WebTest {
    @Test
    public void openMegaMenuSuccessfully() {
        app().goTo(HomePage.class);

        new MainNavigationSection().map().selectMenu(MainMenu.MEGA_MENU).click();

        new HomePage().asserts().assertCorrectPageIsOpen(new HomePage().map().megaMenuButton().getText());
    }

    @ParameterizedTest
    @EnumSource(Brand.class)
    public void selectByBrandInMenuSuccessfully(Brand brand) {
        app().goTo(HomePage.class);

        new HomePage().selectByBrandInMegaMenu(brand);

        new MegaMenuSection().asserts().assertMenuIsLoaded(brand.toString());
    }

    @ParameterizedTest
    @EnumSource(SoundSystem.class)
    public void selectMenuInSoundSectionSuccessfully(SoundSystem system) {
        app().goTo(HomePage.class);

        new HomePage().selectBySoundSystemInMegaMenu(system);

        new MegaMenuSection().asserts().assertMenuIsLoaded(system.toString());
    }

    @Test
    public void openSpecialMenuSuccessfully() {
        app().goTo(HomePage.class);

        new MainNavigationSection().map().selectMenu(MainMenu.SPECIAL).click();

        new BreadcrumbSection().asserts().assertMenuIsOpen(PageTitle.SPECIAL_OFFERS.toString());
    }

    @Test
    public void selectProductFromSpecialMenu() {
        app().goTo(HomePage.class);
        new MainNavigationSection().map().selectMenu(MainMenu.SPECIAL).click();
        var productPage = new ProductPage();
        productPage.map().imageItem(Item.IPOD_TOUCH).click();

        productPage.asserts().assertProductInformationIsDisplayed();
    }
}