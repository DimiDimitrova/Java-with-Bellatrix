package models;

import Sections.breadcrumbsection.BreadcrumbSection;
import Sections.mainheadersection.MainHeaderSection;
import Sections.mainnavigationsection.MainNavigationSection;
import Sections.megamenusection.MegaMenuSection;
import Sections.myaccountdropdownsection.MyAccountDropDownSection;

public class BaseEShopPage {
    public BreadcrumbSection breadcrumbSection() {
        return new BreadcrumbSection();
    }

    public MainNavigationSection mainNavigationSection() {
        return new MainNavigationSection();
    }

    public MegaMenuSection megaMenuSection() {
        return new MegaMenuSection();
    }

    public MyAccountDropDownSection myAccountDropDownSection() {
        return new MyAccountDropDownSection();
    }

    public MainHeaderSection mainHeaderSection() {
        return new MainHeaderSection();
    }
}