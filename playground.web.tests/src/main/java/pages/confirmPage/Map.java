package pages.confirmPage;

import enums.Item;
import enums.TablePrice;
import solutions.bellatrix.web.components.Button;
import solutions.bellatrix.web.components.Div;
import solutions.bellatrix.web.components.TextField;
import solutions.bellatrix.web.pages.PageMap;

import java.util.List;

public class Map extends PageMap {
    public Button confirmOrderButton() {
        return create().byId(Button.class, "button-confirm");
    }

    public Div paymentTable() {
        return create().byXPath(Div.class, "//*[text()='Payment Address']//following::*[@Class='card-body']");
    }

    public TextField totalSum() {
        return create().byXPath(TextField.class,
                "//div[@id='content']//child::tfoot//strong[text()='Total:']/following::td");
    }

    public Button editButton() {
        return create().byXPath(Button.class, "//*[contains(@class,'fa-caret-left')]//parent::a");
    }

    public List<TextField> productsContent() {
        return create().allByXPath(TextField.class, "//div[@id='content']//table/tbody/tr");
    }

    public TextField productQuantity(Item product) {
        String locator = String.format(
                "//div[@id='content']//table/tbody/tr/td[contains(text(),'%s')]//following::td[2]", product.toString());

        return create().byXPath(TextField.class, locator);
    }
}