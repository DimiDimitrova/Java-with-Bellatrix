package homepage;

import enums.HomePageModuleTitle;
import solutions.bellatrix.web.components.*;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public TextField searchInput() {
        return create().byXPath(TextField.class, "//div[@id='search']//child::input[@name='search']");
    }

    public Button allCategoriesDropDown() {
        return create().byXPath(Button.class,
                "//*[contains(@id,'search')]//button[contains(@class,'dropdown-toggle')]");
    }

    public Button searchButton() {
        return create().byXPath(Button.class, "//*[@id='search']/child::div[@class='search-button']/button");
    }

    public Button megaMenuButton() {
        return create().byXPath(Button.class, "//span[contains(text(),' Mega Menu')]//parent::div//parent::a");
    }

    public Anchor shopByCategoryButton() {
        return create().byXPath(Anchor.class, "//div[@data-id='217832']/a");
    }

    public Button shopNowAdButton() {
        return create().byXPath(Button.class, "//*[contains(text(),'SHOP NOW')]");
    }

    public Anchor getCategoryById(int id) {
        return create().byXPath(Anchor.class, String.format("//a[@data-category_id='%d']", id));
    }

    public Anchor getTopCategoryByName(String name) {
        return create().byXPath(Anchor.class, String.format("//span[contains(text(),'%s')]//parent::div//parent::a", name));
    }

    public Anchor getMenuName(String section, String menu) {
        return create().byXPath(Anchor.class, String.format(
                "//h3[contains(text(),'%s')]//following-sibling::div/ul/li/a[@title='%s']", section, menu));
    }

    public Heading informationForModule(HomePageModuleTitle title) {
        return create().byXPath(Heading.class, String.format("//h3[@class='module-title' and (contains(text(),'%s'))]",
                title.toString()));
    }
}