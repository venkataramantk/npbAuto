package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileDomSalesOrdersPageRepo;

/**
 * Created by skonda on 9/20/2016.
 */
public class MobileDomSalesOrdersPageActions extends MobileDomSalesOrdersPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileDomSalesOrdersPageActions.class);

    public MobileDomSalesOrdersPageActions(WebDriver mobileDriver){
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean searchOrderAndApply(String orderNumber){
        String actualOrderNumber = "";
        waitUntilElementDisplayed(orderNumberFld);
        clearAndFillText(orderNumberFld,orderNumber);
        int orderNumberCounter = 0;
        long startTime = System.currentTimeMillis();
        while (!waitUntilElementDisplayed(orderNumberElement, 3) && orderNumberCounter <= 25) {
            staticWait(6000);
            click(applyButton);
            orderNumberCounter++;
            long end = System.currentTimeMillis();
            double waitingPeriod = (double) (end - startTime) / 1000;
            logger.info("Approximate time taking for one search Order Iteration:" + waitingPeriod + "- iteration no" + orderNumberCounter);
            if (System.currentTimeMillis() - startTime > 120000) {
                logger.info("Breaking the while loop after waiting for " + (System.currentTimeMillis() - startTime) / 1000 + " Secs - for the order number" + orderNumber);
                logger.info("No of times search order loop executed -" + orderNumberCounter);
                if (!waitUntilElementDisplayed(orderNumberElement, 5))
                    return false;
            }
        }
        if (!waitUntilElementDisplayed(orderNumberElement, 5)) {
            logger.error("Orders do not appear to be passing to DOM. The order Number: " + orderNumber);
            return false;
        }
        waitForTextToAppear(orderNumberElement, orderNumber, 5);
        actualOrderNumber = orderNumberElement.getText().trim();
        String actualOrderStatus = orderStatusElement.getText();
        logger.info("Actual OrderNumber: "+actualOrderNumber+" Expecting order Number: "+orderNumber);
        return actualOrderNumber.equals(orderNumber.trim());
    }

    public String getOrderStatus(){
        return getText(orderStatusElement);
    }
}
