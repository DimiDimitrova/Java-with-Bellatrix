package editaccountpage;

import registerpage.PersonInfo;
import solutions.bellatrix.web.pages.WebPage;

public class EditAccountPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "";
    }

    public void fillAllAccountInformation(PersonInfo person) {
        map().firstNameInput().setText(person.getFirstName());
        map().lastNameInput().setText(person.getLastName());
        map().emailInput().setText(person.getEmail());
        map().telephoneInput().setText(person.getTelephone());
    }
}