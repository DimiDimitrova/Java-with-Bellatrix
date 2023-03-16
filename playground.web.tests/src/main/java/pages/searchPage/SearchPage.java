package pages.searchPage;

import solutions.bellatrix.web.pages.WebPage;

public class SearchPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io";
    }

    public void searchByKeywords(String keywords) {
        map().inputSearch().setText(keywords);
        map().searchButton().click();
    }
}