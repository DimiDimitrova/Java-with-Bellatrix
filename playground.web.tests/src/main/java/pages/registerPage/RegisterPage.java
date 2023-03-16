package pages.registerPage;

import solutions.bellatrix.web.pages.WebPage;

public class RegisterPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io/index.php?route=account/register";
    }

    public void fillRegistrationForm(PersonInfo personInfo) {
        map().firstNameInput().setText(personInfo.getFirstName());
        map().lastNameInput().setText(personInfo.getLastName());
        map().emailInput().setEmail(personInfo.getEmail());
        map().telephoneInput().setPhone(personInfo.getTelephone());
        map().passwordInput().setPassword(personInfo.getPassword());
        map().confirmPassword().setPassword(personInfo.getPassword());
    }

    public void doRegistration(PersonInfo person) {
        open();
        fillRegistrationForm(person);
        map().agreeCheckbox().check();
        map().continueButton().click();
        browser().waitForAjax();
    }
}