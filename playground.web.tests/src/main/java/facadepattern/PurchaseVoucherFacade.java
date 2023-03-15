package facadepattern;

import accountvoucherpage.AccountVoucherPage;
import accountvoucherpage.RecipientInfo;
import checkoutpage.CheckoutPage;
import confirmpage.ConfirmPage;
import enums.Account;
import enums.GiftCertificate;
import enums.MainMenu;
import enums.MyAccountDropDown;
import mainnavigationsection.MainNavigationSection;
import myaccountdropdownsection.MyAccountDropDownSection;
import solutions.bellatrix.web.services.App;
import successpage.SuccessPage;

public class PurchaseVoucherFacade {
    private MainNavigationSection mainNavigationSections;
    private MyAccountDropDownSection myAccountDropDownSections;
    private AccountVoucherPage accountVoucherPage;
    private CheckoutPage checkoutPage;
    private ConfirmPage confirmPage;
    private SuccessPage successPage;

    public PurchaseVoucherFacade(MainNavigationSection mainNavigationSections, MyAccountDropDownSection myAccountDropDownSection,
                                 AccountVoucherPage accountVoucherPage, SuccessPage successPage, CheckoutPage checkoutPage,
                                 ConfirmPage confirmPage) {
        this.mainNavigationSections = mainNavigationSections;
        myAccountDropDownSections = myAccountDropDownSection;
        this.successPage = successPage;
        this.accountVoucherPage = accountVoucherPage;
        this.checkoutPage = checkoutPage;
        this.confirmPage = confirmPage;
    }

    public void purchaseVoucher(RecipientInfo recipient, String fromName, GiftCertificate gift, double amount, Account account) {
        new App().browser().waitForAjax();
        mainNavigationSections.map().selectMenu(MainMenu.MY_ACCOUNT).hover();
        new App().browser().waitForAjax();
        myAccountDropDownSections.map().myAccountMenu(MyAccountDropDown.MY_VOUCHER).click();

        accountVoucherPage.fillPurchaseGiftData(recipient, fromName, gift, amount);
        accountVoucherPage.map().continueButton().click();

        successPage.asserts().assertVoucherIsPurchased();

        checkoutPage.open();
        checkoutPage.checkConditions(account);
        checkoutPage.map().continueButton().click();

        confirmPage.asserts().confirmPageIsLoaded();
        confirmPage.map().confirmOrderButton().click();
    }
}