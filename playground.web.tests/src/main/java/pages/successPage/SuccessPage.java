package pages.successPage;

import solutions.bellatrix.web.pages.WebPage;

public class SuccessPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io/";
    }
}