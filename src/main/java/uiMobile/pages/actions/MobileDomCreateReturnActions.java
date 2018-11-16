package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileDomCreateReturnRepo;

import java.util.Map;

/**
 * Created by skonda on 7/18/2016.
 */
public class MobileDomCreateReturnActions extends MobileDomCreateReturnRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileDomCreateReturnActions.class);

    public MobileDomCreateReturnActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean selectReturnRadioAndQtyOfItemsAndConfirmReturn(Map<String, String> itemsAndQty){
        int itemsAndQtySize = itemsAndQty.size();
        int rowSize = skuElements.size();
        for(int i = 1; i<=rowSize; i++){
            for(Map.Entry<String,String> itemWithQty : itemsAndQty.entrySet()){
                String itemName = itemWithQty.getKey();
                String qty = itemWithQty.getValue();
                String itemNameFromUI = getText(skuLabelElement(i));
                if(itemNameFromUI.equalsIgnoreCase(itemName)){
                    try{
                        click(returnRadioButton(i));
                    }catch (Throwable t){
//                        scrollDownForReturnSection();
                        click(returnRadioButton(i));
                    }
                    click(qtyDropDown(i));
                    staticWait();
                    click(qtyDropDownValue(i,qty));
//                    clearAndFillText(qtyTextBox(i),qty);
                    scrollDownForReturnSection();
                    break;
                }
            }
        }
        clickDoneButton();
        clickProceedReviewButton();
        return clickConfirmReturnButton();
    }

    public void scrollDownForReturnSection(){
        String idValue = getAttributeValue(scrollDownElement,"id");
        scrollBy(idValue);
    }

    public void clickProceedReviewButton(){
        String proceedToReviewId = getAttributeValue(proceedToReview,"id");
        clickJavaScriptExecute(proceedToReviewId);
        waitUntilElementDisplayed(confirmReturn);
    }

    public void clickDoneButton(){
        String idValue = getAttributeValue(doneButton, "id");
        clickJavaScriptExecute(idValue);
        waitUntilElementDisplayed(proceedToReview);
    }

    public boolean clickConfirmReturnButton(){
        String confirmReturnId = getAttributeValue(confirmReturn,"id");
        clickJavaScriptExecute(confirmReturnId);
        return waitUntilElementDisplayed(returnNumCreatedSuccessMsg);
    }

    public String getReturnNumberFromSuccessMsg(){
        String returnNumNsg = getText(returnNumCreatedSuccessMsg);
        int startIndex = returnNumNsg.indexOf("(");
        int endIndex = returnNumNsg.indexOf(")");
        return returnNumNsg.substring(startIndex,endIndex);

    }
}
