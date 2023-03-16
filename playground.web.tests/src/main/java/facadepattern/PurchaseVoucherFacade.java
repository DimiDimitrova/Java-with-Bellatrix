package facadepattern;

import pages.accountVoucherPage.AccountVoucherPage;
import models.BaseEShopPage;
import models.RecipientInfo;
import pages.checkoutPage.CheckoutPage;
import pages.confirmPage.ConfirmPage;
import enums.Account;
import enums.GiftCertificate;
import enums.MainMenu;
import enums.MyAccountDropDown;
import solutions.bellatrix.web.services.App;
import pages.successPage.SuccessPage;

public class PurchaseVoucherFacade {
    protected BaseEShopPage baseEShopPage;
    protected CheckoutPage checkoutPage;
    protected ConfirmPage confirmPage;
    protected SuccessPage successPage;
    protected AccountVoucherPage accountVoucherPage;

    public PurchaseVoucherFacade() {
        baseEShopPage = new BaseEShopPage();
        checkoutPage = new CheckoutPage();
        confirmPage = new ConfirmPage();
        successPage = new SuccessPage();
        accountVoucherPage = new AccountVoucherPage();
    }

    public void purchaseVoucher(RecipientInfo recipient, String fromName, GiftCertificate gift, double amount, Account account) {
        new App().browser().waitForAjax();
        baseEShopPage.mainNavigationSection().map().selectMenu(MainMenu.MY_ACCOUNT).hover();
        new App().browser().waitForAjax();
        baseEShopPage.myAccountDropDownSection().map().myAccountMenu(MyAccountDropDown.MY_VOUCHER).click();

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