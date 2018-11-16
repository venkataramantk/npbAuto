package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileOrderStatusRepo;

/**
 * Created by user on 6/15/2016.
 */
public class MobileOrderStatusActions extends MobileOrderStatusRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileOrderStatusActions.class);

    public MobileOrderStatusActions(WebDriver mobileDriver){
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver,this);
    }

    public boolean validateOrderNumber(String orderNumb) {

        String orderNum = getText(orderNoLink).replaceAll("[^0-9]", "");
        boolean verifyOrderNumber = orderNumb.contentEquals(orderNum);

        if (verifyOrderNumber)
            return true;
        else
            return false;
    }

    public boolean validateItemTotal(String item) {

        String itemTotalPrice = getText(itemsTotal).replace("$", "");
//        int startIndex = itemTotalPrice.indexOf("(");
//        int endIndex = itemTotalPrice.indexOf(")");
//        itemTotalPrice = itemTotalPrice.substring(startIndex + 1, endIndex);

        boolean verifyItemTot = item.contentEquals(itemTotalPrice);

        if (verifyItemTot)
            return true;
        else
            return false;
    }

    public boolean validateEstimatedTotal(String orderTotal) {

        String prodTotal = getText(estimatedTot).replace("$", "");
        boolean verifyProdTot = orderTotal.contentEquals(prodTotal);

        if (verifyProdTot)
            return true;
        else
            return false;
    }


    public boolean validateOrderStatusContentsForSTH() {
        if (isDisplayed(profileInfo) &&
                isDisplayed(cardDetails) &&
                waitUntilElementDisplayed(prodName, 1) &&
                waitUntilElementDisplayed(prodDetails, 1) &&
                waitUntilElementDisplayed(subTotal, 1))
            return true;
        else
            return false;
    }

    public boolean validateOrderStatusContentsForBopis() {
        if (isDisplayed(cardDetails) &&
                waitUntilElementDisplayed(prodName, 1) &&
                waitUntilElementDisplayed(prodDetails, 1) &&
                waitUntilElementDisplayed(subTotal, 1) &&
                isDisplayed(storeData))
            return true;
        else
            return false;
    }
    
}
