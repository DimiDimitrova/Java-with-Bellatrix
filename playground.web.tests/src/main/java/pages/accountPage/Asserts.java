package pages.accountPage;

import enums.Navbar;
import solutions.bellatrix.web.pages.PageAsserts;

public class Asserts extends PageAsserts<Map> {
    public void assertThatUserIsLogged() {
        map().accountNavbar(Navbar.LOGOUT).validateIsVisible();
    }

    public void assertUserIsNotLogged() {
        map().accountNavbar(Navbar.LOGIN).validateIsVisible();
    }
}