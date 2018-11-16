package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileDomCustomerTransactionsRepo;

/**
 * Created by skonda on 7/20/2016.
 */
public class MobileDomCustomerTransactionsActions extends MobileDomCustomerTransactionsRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileDomCustomerTransactionsActions.class);

    public MobileDomCustomerTransactionsActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
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
