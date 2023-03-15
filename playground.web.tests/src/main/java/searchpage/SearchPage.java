package searchpage;

import solutions.bellatrix.web.pages.WebPage;

public class SearchPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return null;
    }

    public void searchByKeywords(String keywords) {
        map().inputSearch().setText(keywords);
        map().searchButton().click();
    }
}