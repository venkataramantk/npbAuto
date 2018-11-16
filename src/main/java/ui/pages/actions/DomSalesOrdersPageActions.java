package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.DomSalesOrdersPageRepo;

/**
 * Created by skonda on 9/20/2016.
 */
public class DomSalesOrdersPageActions extends DomSalesOrdersPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(DomSalesOrdersPageActions.class);

    public DomSalesOrdersPageActions(WebDriver driver){
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
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
