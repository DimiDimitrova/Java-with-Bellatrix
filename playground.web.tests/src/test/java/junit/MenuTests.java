package junit;

import enums.*;
import pages.homePage.HomePage;
import models.BaseEShopPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pages.productPage.ProductPage;
import solutions.bellatrix.web.infrastructure.junit.WebTest;

public class MenuTests extends WebTest {
    protected HomePage homePage;
    protected ProductPage productPage;
    protected BaseEShopPage baseEShopPage;
    @Override
    protected void configure() {
        super.configure();
        homePage = app().create(HomePage.class);
        productPage = app().create(ProductPage.class);
        baseEShopPage = new BaseEShopPage();
    }
    @Test
    public void openMegaMenuSuccessfully() {
        homePage.open();

        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.MEGA_MENU).click();

        homePage.asserts().assertCorrectPageIsOpen(new HomePage().map().megaMenuButton().getText());
    }

    @ParameterizedTest
    @EnumSource(Brand.class)
    public void selectByBrandInMenuSuccessfully(Brand brand) {
        homePage.open();

        homePage.selectByBrandInMegaMenu(brand);

        baseEShopPage.megaMenuSection().asserts().assertMenuIsLoaded(brand.toString());
    }

    @ParameterizedTest
    @EnumSource(SoundSystem.class)
    public void selectMenuInSoundSectionSuccessfully(SoundSystem system) {
        homePage.open();

        homePage.selectBySoundSystemInMegaMenu(system);

        baseEShopPage.megaMenuSection().asserts().assertMenuIsLoaded(system.toString());
    }

    @Test
    public void openSpecialMenuSuccessfully() {
        homePage.open();

        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.SPECIAL).click();

        baseEShopPage.breadcrumbSection().asserts().assertMenuIsOpen(PageTitle.SPECIAL_OFFERS.toString());
    }

    @Test
    public void selectProductFromSpecialMenu() {
        homePage.open();
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.SPECIAL).click();

        productPage.map().imageItem(Item.IPOD_TOUCH).click();

        productPage.asserts().assertProductInformationIsDisplayed();
    }
}