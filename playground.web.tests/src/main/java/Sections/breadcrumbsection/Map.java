package Sections.breadcrumbsection;

import solutions.bellatrix.web.components.Heading;
import solutions.bellatrix.web.components.Label;
import solutions.bellatrix.web.pages.PageMap;

public class Map extends PageMap {
    public Heading pageTitle() {
        return create().byXPath(Heading.class, "//*[contains(@class,'page-title')]");
    }

    public Label activePageTitle(){
        return create().byXPath(Label.class,"//li[@aria-current='page']");
    }
}