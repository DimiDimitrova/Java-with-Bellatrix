package models;

import sections.breadcrumbSection.BreadcrumbSection;
import sections.mainHeaderSection.MainHeaderSection;
import sections.mainNavigationSection.MainNavigationSection;
import sections.megaMenuSection.MegaMenuSection;
import sections.myAccountDropDownSection.MyAccountDropDownSection;

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