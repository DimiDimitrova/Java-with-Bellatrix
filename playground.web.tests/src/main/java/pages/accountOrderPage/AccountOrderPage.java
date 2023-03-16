package pages.accountOrderPage;

import solutions.bellatrix.web.pages.WebPage;

public class AccountOrderPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io";
    }
}