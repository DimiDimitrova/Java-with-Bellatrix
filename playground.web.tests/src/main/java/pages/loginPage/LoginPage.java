package pages.loginPage;

import enums.*;
import sections.mainNavigationSection.MainNavigationSection;
import sections.myAccountDropDownSection.MyAccountDropDownSection;
import solutions.bellatrix.web.pages.WebPage;

public class LoginPage extends WebPage<Map,Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io";
    }

    public void logIn(String email, String password) {
        open();
        browser().waitForAjax();
        var mainNavigationSection = new MainNavigationSection();
        mainNavigationSection.map().selectMenu(MainMenu.MY_ACCOUNT).hover();
        var myAccountDropDownSection = new MyAccountDropDownSection();
        myAccountDropDownSection.map().myAccountMenu(MyAccountDropDown.LOGIN).click();
        browser().waitUntilPageLoadsCompletely();
        map().emailInput().setText(email);
        map().passwordInput().setText(password);
        map().loginButton().click();
        browser().waitForAjax();
    }
}