package changepasswordpage;

import solutions.bellatrix.web.pages.WebPage;

public class ChangePasswordPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io/";
    }

    public void changePassword(String newPassword) {
        map().passwordInput().setText(newPassword);
        map().confirmPasswordInput().setText(newPassword);
    }
}