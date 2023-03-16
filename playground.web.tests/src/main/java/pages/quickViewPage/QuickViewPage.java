package pages.quickViewPage;

import solutions.bellatrix.web.pages.WebPage;

public class QuickViewPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io";
    }
}