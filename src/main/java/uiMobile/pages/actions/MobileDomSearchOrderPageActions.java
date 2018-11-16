package uiMobile.pages.actions;

import io.appium.java_client.MobileDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileDomSearchOrderPageRepo;

import java.util.*;


/**
 * Created by skonda on 5/26/2016.
 */
public class MobileDomSearchOrderPageActions extends MobileDomSearchOrderPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobileDomSearchOrderPageActions.class);

    public MobileDomSearchOrderPageActions(WebDriver mobileDriver){
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }


    public boolean searchOrder(String orderNumber) {
        String actualOrderNumber = "";
        String actualOrderStatus = "";
        staticWait(5000);
        click(maximizeImg);
        staticWait(5000);
        switchToFrame(0);
        waitUntilElementDisplayed(orderNumTextBox);
        clearAndFillText(orderNumTextBox, orderNumber);
        staticWait();
        orderNumTextBox.sendKeys(Keys.RETURN);
        staticWait();
        int orderNumberCounter = 0;
        long startTime = System.currentTimeMillis();
        while (!waitUntilElementDisplayed(orderNumberElement, 3) && orderNumberCounter <= 25) {
            staticWait(6000);
            click(orderApplyBtn);
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
        actualOrderStatus = orderStatusElement.getText();
        logger.info("Actual OrderNumber: "+actualOrderNumber+" Expecting order Number: "+orderNumber);
        return actualOrderNumber.equals(orderNumber.trim());
    }

    public boolean navigateToOrderDetailsPage(){
        click(viewOrderDetailsButton);
        return waitUntilElementDisplayed(statusTextElement);
    }

    public String getPaymentStatus(){
        return getText(payStatus);
    }

    public List<String> isStatusCompletedInvoicedAndShipped(int maxCounter) {
        List<String> exceptionsList = new ArrayList<>();
        try {

            if (!isOrderStatusDisplayedByStatus("Completed", maxCounter)) {
                exceptionsList.add(getText(statusTextElement));
            }
            if (!getPaymentStatus().equals("Invoiced")) {
                exceptionsList.add(getText(payStatus));
            }
            int rowCount = getRowCountAtOrderDetails();
            for (int i = 0; i < rowCount - 1; i++) {
                if (!waitForTextToAppear(lineStatus(i), "Shipped", 10)) {
                    exceptionsList.add(getText(lineStatus(i)));
                }
            }

        } catch (Exception e) {
            exceptionsList.add(e.getMessage());
        }
        return exceptionsList;
    }

    public List<String> isStatusCompletedAndInvoiced(int maxCounter) {
        List<String> exceptionsList = new ArrayList<>();
        try {

            if (!isOrderStatusDisplayedByStatus("Completed", maxCounter)) {
                exceptionsList.add(getText(statusTextElement));
            }
            if (!getPaymentStatus().equals("Invoiced")) {
                exceptionsList.add(getText(payStatus));
            }
        }catch (Exception e) {
            exceptionsList.add(e.getMessage());
        }
        return exceptionsList;
    }
    public List<String> isStatusCanceledOrderAndLineLevel(int maxCounter) {
        List<String> exceptionsList = new ArrayList<>();
        try {

            if (!isOrderStatusDisplayedByStatus("Canceled", maxCounter)) {
                exceptionsList.add(getText(statusTextElement));
            }
            if (!getPaymentStatus().equals("Authorized")) {
                exceptionsList.add(getText(payStatus));
            }
            int rowCount = getRowCountAtOrderDetails();
            for (int i = 0; i < rowCount - 1; i++) {
                if (!waitForTextToAppear(lineStatus(i), "Canceled", 10)) {
                    exceptionsList.add(getText(lineStatus(i)));
                }
            }

        } catch (Exception e) {
            exceptionsList.add(e.getMessage());
        }
        return exceptionsList;
    }

    public List<String> isStatusPartiallyCompletedAuthorizedAndPartShipped(int maxCounter) {
        List<String> exceptionsList = new ArrayList<>();
        try {

            if (!isOrderStatusDisplayedByStatus("Partially Completed", maxCounter)) {
                exceptionsList.add(getText(statusTextElement));
            }
            if (!getPaymentStatus().equals("Authorized")) {
                exceptionsList.add(getText(payStatus));
            }
            int rowCount = getRowCountAtOrderDetails();
            for (int i = 0; i < rowCount - 1; i++) {
                if (!waitForTextToAppear(lineStatus(i), "Partially Shipped", 10)) {
                    exceptionsList.add(getText(lineStatus(i)));
                }
            }

        } catch (Exception e) {
            exceptionsList.add(e.getMessage());
        }
        return exceptionsList;
    }

    public boolean isExceptionsPresent(List<String> exceptions, String orderNumber) {
        boolean retVal = false;
        for (int k = 0; k < exceptions.size(); k++) {
            if (exceptions.get(k) != null && !exceptions.get(k).isEmpty()) {
                logger.info("The exception has occured for the order: " + orderNumber + " " + exceptions.get(k));
                retVal = true;
            } else {
                retVal = false;
            }
        }
        return retVal;
    }

    public boolean isOrderStatusDisplayedByStatus(String status, int maxCount) {
        boolean isOrderStatus = false;
        try {
            int counter = 0;
            long startTime = System.currentTimeMillis();
            while (!getText(statusTextElement).equalsIgnoreCase(status) && counter <= maxCount) {
                long endTime = System.currentTimeMillis();
                logger.info("time taken to find status is: " + (endTime - startTime) + " milliSeconds");
                switchToDefaultFrame();
                click(refreshImg);
                staticWait(5000);
                switchToFrame(0);
                if (waitForTextToAppearEqual(statusTextElement, status, 5)) {
                    break;
                }
                if (waitUntilElementDisplayed(viewOrderDetailsButton, 5)) {
                    click(viewOrderDetailsButton);
                    waitUntilElementDisplayed(statusTextElement);
                }
                counter++;
            }
            if (getText(statusTextElement).equalsIgnoreCase(status)) {
                return true;
            } else {
                return false;
            }
        } catch (Throwable t) {
            logger.info("Exception thrown: " + t.getMessage());
            return isOrderStatus;
        }

    }


    public boolean releaseOrder(String orderNumber) {
        try {

            boolean b = false;
            staticWait();
            switch (getText(statusTextElement)) {
                case "Allocated":

                    selectAdditionalDetails();
                    clickCreateDOButton();
                    clickViewEditOrderDetailsIfPresent();
                    selectAdditionalDetails();
//                    if (!waitUntilElementDisplayed(releaseDOButton, 15)) {
//                        switchToDefaultFrame();
//                        click(refreshImg);
//                        staticWait(5000);
//                        switchToFrame(0);
//                    }
                    clickReleaseDOButton();
                    int counterForStatus = 0;
                    clickViewEditOrderDetailsIfPresent();
                    while (!getText(statusTextElement).equals("Released") && counterForStatus <= 2) {
                        switchToDefaultFrame();
                        click(refreshImg);
                        switchToFrame(0);
                        counterForStatus++;
                        waitForTextToAppear(statusTextElement, "Released", 10);
                        clickViewEditOrderDetailsIfPresent();
                    }
                    b = getText(statusTextElement).equals("Released");
                    break;

                case "DO Created":
//                    if (!waitUntilElementDisplayed(moreDropDownButton, 15)) {
//                        switchToDefaultFrame();
//                        click(refreshImg);
//                        switchToFrame(0);
//                    }
                    selectAdditionalDetails();
//                    if (waitUntilElementDisplayed(releaseDOButton, 15)) {
//                        switchToDefaultFrame();
//                        click(refreshImg);
//                        switchToFrame(0);
//                    }
                    b = clickReleaseDOButton();
                    break;

                case "Released":
                    b = true;
                    break;

                case "Completed":
                    b = true;
                    break;

                case "Partially Completed":
                    b = true;
                    break;

                default:
                    break;
            }
            return b;
        } catch (Exception e) {
            System.out.println("Release order manually method Exception Message for order number" + e.getMessage());
            logger.info("Release order manually method Exception Message for order number " + orderNumber + " --" + e.getMessage());
            return false;
        }
    }

    public boolean verifyLineItemsStatusAsExpectedBy(String itemStatus){
        staticWait();
        boolean retVal = false;
        int rowCount = getRowCountAtOrderDetails();
        logger.info("Row Count at order Details is: "+rowCount);
        for(int i = 0; i<rowCount - 1; i++){
            if(getText(itemStatusAtOrderDetails(i)).equalsIgnoreCase(itemStatus)){
                 retVal = true;
            }
            else{
                retVal = false;
                break;
            }
        }
        return retVal;
    }

    public int getRowCountAtOrderDetails(){
        return itemList.size();
    }

    public void clickViewEditOrderDetailsIfPresent() {
        if (waitUntilElementDisplayed(viewOrderDetailsButton, 5)) {
            staticWait(10000);
            click(viewOrderDetailsButton);
            waitUntilElementDisplayed(statusTextElement, 30);
        }
    }

    public boolean selectAdditionalDetails(){
        waitUntilElementDisplayed(moreDropDownButton);
        selectDropDownByVisibleText(moreDropDownButton,"Additional Details");
        verifyElementNotDisplayed(pleaseWaitPopUpAfterSaveLineDetails, 30);
        return waitUntilElementDisplayed(orderAdditionalDetailsTab, 10);
    }

    public boolean clickCreateDOButton(){
        click(createDOButton);
        verifyElementNotDisplayed(pleaseWaitPopUpAfterSaveLineDetails);
        switchToDefaultFrame();
        click(refreshImg);
        staticWait(5000);
        switchToFrame(0);
        return waitForTextToAppear(orderStatusElement,"DO Created", 30);

    }

    public boolean clickReleaseDOButton(){
        click(releaseDOButton);
        verifyElementNotDisplayed(pleaseWaitPopUpAfterSaveLineDetails);
        switchToDefaultFrame();
        click(refreshImg);
        staticWait(5000);
        switchToFrame(0);
        return waitForTextToAppear(statusTextElement, "Released", 20);


    }

    public void selectDistributionOrderDetails() {
        waitUntilElementDisplayed(moreDropDownButton);
        selectDropDownByVisibleText(moreDropDownButton, "Distribution Order Details");
        waitUntilElementDisplayed(doNumber);

    }

    public String getDONumFromDistributionOrderDetails() {
        int rowCount = getRowCountDistributionOrderDetails();
        String doNumber = null;
        for (int j = 1; j < rowCount; j++) {
            doNumber = getText(doNum(j));
            break;
        }
        return doNumber;
    }

    public Map<String, String> getOrderedItemsFromDistributionOrderDetails() {
        Map<String,String> orderedItemsAndQty = new TreeMap<>();
        int rowCount = getRowCountDistributionOrderDetails();
        for (int j = 1; j < rowCount; j++) {
            orderedItemsAndQty.put(getText(itemNum(j)), getOrderedQuantity(j));
        }
        return orderedItemsAndQty;
    }
    public String getDOLineNumByOrderedItem(String orderedItem){
        String doLineNumber = "No Line Numbers found by this "+orderedItem;
        int rowCount = getRowCountDistributionOrderDetails();
        for (int j = 1; j < rowCount; j++) {
            if(getText(itemNum(j)).equalsIgnoreCase(orderedItem)){
                doLineNumber = getText(doLineNum(j));
                break;
            }
        }
        return doLineNumber;
    }

    public int getRowCountDistributionOrderDetails() {
        return doLines.size();
    }

    public String getOrderedQuantity(int i){
        String qty = getText(orderedQty(i)).replace("eaches","");
        int endIndex = qty.indexOf(".");
        qty = qty.substring(0,endIndex);
        return qty;
    }

    public String clickShippingInfoImageAndGetShipMethod(){
        click(shippingInfoImg);
        verifyElementNotDisplayed(pleaseWaitPopUpAfterSaveLineDetails);
        waitUntilElementDisplayed(shippingMethod);
        return getText(shippingMethod);
    }

    public Map<String, String> getItemIDWithQtyFromOrderDetailsPage(){
        Map<String, String> itemIDAndQtyOrdered = new HashMap<>();
        int rowSize = getRowCountAtOrderDetails();
        for(int i = 0; i<rowSize - 1; i++){
                String itemId = getText(itemID(i));
                String itemQty = getText(orderedQtyAtOrderDetails(i));

            itemIDAndQtyOrdered.put(itemId,itemQty);

        }
        return itemIDAndQtyOrdered;
    }

    public Map<String, String> getItemIDWithQtyToReturnFromOrderDetailsPage(Map<String,String> qtyOrderedAndQtyToReturn){
        Map<String, String> itemIDAndQtyToReturn = new HashMap<>();
        int rowSize = getRowCountAtOrderDetails();
        for(int i = 0; i<rowSize - 1; i++){
            String itemId = getText(itemID(i));
            String itemQty = getText(orderedQtyAtOrderDetails(i));
            for(Map.Entry<String, String> qtyOrdAndQtyRet : qtyOrderedAndQtyToReturn.entrySet()){
                String qtyOrd = qtyOrdAndQtyRet.getKey();
                String qtyRet = qtyOrdAndQtyRet.getValue();
                if(itemQty.equalsIgnoreCase(qtyOrd)){
                    itemIDAndQtyToReturn.put(itemId,qtyRet);
                    break;
                }
            }



        }
        return itemIDAndQtyToReturn;
    }

    public Map<String, String> getItemIDByProdNameAndQty(Map<String, String> prodNamesAndQtyToShip){
        Map<String, String> itemIDAndQtyToShip = new HashMap<>();
        int rowSize = getRowCountAtOrderDetails();
        for(Map.Entry<String, String> prodNameAndQty : prodNamesAndQtyToShip.entrySet()){
            String prodName = prodNameAndQty.getKey();
            String qtyToShip = prodNameAndQty.getValue();
            for(int i = 0; i<rowSize - 1; i++){
                String actProdName = getText(itemDescription(i));
                if(actProdName.equalsIgnoreCase(prodName)){
                    String itemId = getText(itemID(i));
                    itemIDAndQtyToShip.put(itemId,qtyToShip);

                }
            }
        }
        return itemIDAndQtyToShip;
    }

    public void closeSearchOrdersFrame() {
        switchToDefaultFrame();
        jqueryClick(".x-tool-close");

    }
}
