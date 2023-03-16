package Pages.searchpage;

import solutions.bellatrix.web.components.Anchor;
import solutions.bellatrix.web.components.Button;
import solutions.bellatrix.web.components.TextField;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public TextField inputSearch() {
        return create().byId(TextField.class, "input-search");
    }

    public Button searchButton() {
        return create().byId(Button.class, "button-search");
    }

    public Anchor searchValueInResult() {
        return create().byXPath(Anchor.class, "//*[contains(@class,'title')]/a");
    }

    public TextField searchCategoryDropDown(int categoryId) {
        String locator = String.format(
                "//*[@name='category_id']/option[@value='%d' and @selected='selected']", categoryId);

        return create().byXPath(TextField.class, locator);
    }

    public TextField searchCriteriaInput(String searchedCriteria) {
        String locator = String.format(
                "//input[@id='input-search' and @value='%s']", searchedCriteria);

        return create().byXPath(TextField.class, locator);
    }
}