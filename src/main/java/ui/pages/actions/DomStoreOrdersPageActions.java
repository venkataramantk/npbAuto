package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.DomStoreOrdersPageRepo;

/**
 * Created by skonda on 9/20/2016.
 */
public class DomStoreOrdersPageActions extends DomStoreOrdersPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(DomStoreOrdersPageActions.class);

    public DomStoreOrdersPageActions(WebDriver driver){
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public int getPendingAcceptanceCount(){
        return Integer.parseInt(getText(pendingAcceptanceCountElement));
    }

    public int getReferenceOrderOpenStatusesCount(){
        return referenceOrderOpenStatuses.size();
    }

    public String getStatusByOrderNumber(String orderNumber){
        return getText(statusByReferenceOrder(orderNumber));
    }

    public String getStatusByOrderNum_NextPages(String orderNumber){
        String refStatus = "No Status found by Reservation Number";
        boolean isOrdPresent = false;
        do{
            try{
                isOrdPresent = waitUntilElementDisplayed(referenceOrderRowPresent(orderNumber), 1);
            }catch(Throwable t){
                isOrdPresent = false;
            }
            if(isOrdPresent){
                refStatus = getText(statusByReferenceOrder(orderNumber));
                break;

            }
            else if(isEnabled(nextArrow)){
                click(nextArrow);
                staticWait(2000);
            }
//            else{
//                return refStatus;
//            }
            try{
                isOrdPresent = waitUntilElementDisplayed(referenceOrderRowPresent(orderNumber), 1);
            }catch(Throwable t){
                isOrdPresent = false;
            }
        }while(!isOrdPresent);
        if(isOrdPresent){
            refStatus = getText(statusByReferenceOrder(orderNumber));

        }
        return refStatus;
    }

    public boolean acceptPrintPickListByOrderNumber(String orderNumber){
        click(checkBoxByReferenceOrder(orderNumber));
        staticWait();
        click(acceptPrintPickListButton);
        staticWait(3000);
        return waitForTextToAppear(statusByReferenceOrder(orderNumber),"Accepted", 30);
    }

    public boolean submitPickByOrderNumber(String orderNumber){
        click(checkBoxByReferenceOrder(orderNumber));
        staticWait();
        click(submitPickButton);
        return waitUntilElementDisplayed(finishOrderButton);
    }

    public boolean enterQtyAndFinishOrder(String qty){
        clearAndFillText(quantityFld, qty);
        return clickFinishOrder();
    }
    public boolean selectShortReasonAndFinishOrder(String reason){
        selectDropDownByVisibleText(shortReasonDropDown, reason);
        staticWait();
        return clickFinishOrder();
    }
    public boolean clickFinishOrder(){
        click(finishOrderButton);
        return waitUntilElementDisplayed(orderStatus);
    }

    public String getOrderStatus(){
        return getText(orderStatus);
    }

    public String getItemStatus(){
        return getText(itemStatus);
    }

    public String getItemShortReason(){
        return getText(shortReasonElement);
    }

    public String getReferenceOrderNumber(){
        return getText(refOrderNumberElement);
    }
}
