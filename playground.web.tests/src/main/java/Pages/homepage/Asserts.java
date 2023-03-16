package Pages.homepage;

import Sections.breadcrumbsection.BreadcrumbSection;
import enums.HomePageModuleTitle;
import messages.ApplicationMessages;
import org.junit.jupiter.api.Assertions;
import solutions.bellatrix.web.pages.PageAsserts;
import solutions.bellatrix.web.services.App;

public class Asserts extends PageAsserts<Map> {
    public void assertCorrectPageIsOpen(String page) {
        String temp = page.split(" ")[0];
        var breadcrumb = new BreadcrumbSection();
        breadcrumb.map().activePageTitle().validateTextIs( String.format(ApplicationMessages.PAGE_ERROR, temp));
    }

    public void assertModuleInformationPresent(HomePageModuleTitle module) {
        map().informationForModule(module).validateIsVisible();
    }

    public void assertHomePageIsLoaded() {
        Assertions.assertTrue(new App().browser().getUrl().endsWith("common/home"));
    }
}