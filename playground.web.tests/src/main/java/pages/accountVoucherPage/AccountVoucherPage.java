package pages.accountVoucherPage;

import enums.GiftCertificate;
import models.RecipientInfo;
import solutions.bellatrix.web.pages.WebPage;

public class AccountVoucherPage extends WebPage<Map, Asserts> {
    @Override
    protected String getUrl() {
        return "https://ecommerce-playground.lambdatest.io/index.php?route=account/voucher";
    }

    public void fillPurchaseGiftData(RecipientInfo recipient, String fromName, GiftCertificate theme, double amount) {
        map().recipientName().setText(recipient.getRecipientName());
        map().recipientMail().setText(recipient.getRecipientEmail());
        map().fromName().setText(fromName);
        map().giftCertificateTheme(theme.toString()).click();
        map().amount().setText(Double.toString(amount));
        map().agreeCheckbox().check();
    }
}