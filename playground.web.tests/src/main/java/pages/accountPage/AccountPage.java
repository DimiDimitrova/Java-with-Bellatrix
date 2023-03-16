package pages.accountPage;

import enums.Navbar;
import solutions.bellatrix.web.pages.WebPage;

public class AccountPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io/index.php?route=account/login";
    }

    public void openMenuFromNavbar(Navbar menu) {
        map().accountNavbar(menu).click();
    }
}