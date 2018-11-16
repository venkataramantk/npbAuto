package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.DomCustomerTransactionsRepo;

/**
 * Created by skonda on 7/20/2016.
 */
public class DomCustomerTransactionsActions extends DomCustomerTransactionsRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(DomCustomerTransactionsActions.class);

    public DomCustomerTransactionsActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public String getInvoiceStatus(){
        waitUntilElementDisplayed(invoiceStatus);
        return getText(invoiceStatus);
    }

    public boolean searchCustomerOrderNumber(String orderNumber){
        clearAndFillText(orderKeywordSearch, orderNumber);
        staticWait();
        clickOrderSearchButton();
        waitUntilElementDisplayed(orderHighLightedSearchResult(orderNumber));
        click(orderHighLightedSearchResult(orderNumber));
        return waitUntilElementDisplayed(orderDetailsAtCustomerOrdWin);
    }

    public void clickOrderSearchButton(){
        String searchButtonId = getAttributeValue(orderSearchButton,"id");
        clickJavaScriptExecute(searchButtonId);
    }
}
