package homepage;

import enums.*;
import mainnavigationsection.MainNavigationSection;
import solutions.bellatrix.web.pages.WebPage;

public class HomePage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io";
    }

    public void searchByCategory(CategoryInSearchBox category) {
        open();
        selectCategory(category);
        map().searchButton().click();
    }

    public void searchByManufacturer(Brand manufacturer) {
        open();
        map().searchInput().setText(manufacturer.toString());
        map().searchButton().click();
    }

    public void search(String search) {
        open();
        map().searchInput().setText(search);
        map().searchButton().click();
    }

    public void searchByTopCategory(Categories category) {
        open();
        map().shopByCategoryButton().click();
        map().getTopCategoryByName(category.toString()).click();
    }

    public void selectByBrandInMegaMenu(Brand brand) {
        new MainNavigationSection().map().selectMenu(MainMenu.MEGA_MENU).hover();
        var temp = MegaMenu.MOBILES;
        map().getMenuName(temp.toString(), brand.toString()).click();
    }

    public void selectBySoundSystemInMegaMenu(SoundSystem system) {
        new MainNavigationSection().map().selectMenu(MainMenu.MEGA_MENU).hover();

        var temp = MegaMenu.SOUND_SYSTEM;
        map().getMenuName(temp.toString(), system.toString()).click();
    }

    private void selectCategory(CategoryInSearchBox category) {
        map().allCategoriesDropDown().click();
        map().getCategoryById(category.categoryId()).click();
    }
}