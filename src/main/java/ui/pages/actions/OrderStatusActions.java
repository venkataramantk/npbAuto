package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.OrderStatusRepo;

/**
 * Created by user on 6/15/2016.
 */
public class OrderStatusActions extends OrderStatusRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(OrderStatusActions.class);

    public OrderStatusActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean validateOrderNumber(String orderNumb) {

        waitUntilElementDisplayed(orderNoLink);
        String orderNum = getText(orderNoLink).replaceAll("[^0-9]", "");
        boolean verifyOrderNumber = orderNumb.contentEquals(orderNum);
        waitUntilElementDisplayed(order_DetailsHour, 3);
        if (verifyOrderNumber)
            return true;
        else
            return false;
    }

    public boolean refreshForOrderToPresent() {
        int count = 20;
        for (int i = 0; i < count; i++) {
            if (!waitUntilElementDisplayed(orderNoLink, 1)) {
                driver.navigate().refresh();
                staticWait(5000);
                count--;
            } else {
                break;
            }
        }
        return isDisplayed(orderNoLink);
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
        boolean billAddress = isDisplayed(billingAddress);
        boolean cardInfo = isDisplayed(cardDetails);
        boolean shipSection = isDisplayed(shippingDetailsSection);
        boolean orderLedgerSec = isDisplayed(orderSummarySection);
        boolean highLightedOrder = isDisplayed(orderHighlighted);
        boolean productName = isDisplayed(prodName);
        boolean productDetails = isDisplayed(prodDetails);
        boolean subTotalPr = waitUntilElementDisplayed(subTotal, 1);
        boolean prodTableContent = waitUntilElementDisplayed(productTableContent, 1);
        boolean orderStatusDis = waitUntilElementDisplayed(orderStatusDisplay, 1);

        if (billAddress &&
                cardInfo &&
                shipSection &&
                orderLedgerSec &&
                highLightedOrder &&
                productName &&
                productDetails &&
                subTotalPr && prodTableContent && orderStatusDis)
            return true;
        else
            addStepDescription("Billing Address " + billAddress + " card info " + cardInfo + " shipping Address " + shipSection + " Order ledger " + orderLedgerSec +
                    " Order number " + orderHighlighted + " product Name " + productName + " product details like size, color " + productDetails + " sub total " + subTotalPr + " product Table heading " + prodTableContent);
        return false;
    }

    public boolean validateOrderLedger() {
        waitUntilElementDisplayed(orderSummarySection, 2);
        if (isDisplayed(itemCount_Ledger) && isDisplayed(couponAndPromotions_Ledger)
                && isDisplayed(tax_Ledger) && isDisplayed(total_ledger)) {

            return true;
        } else return false;
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

    public boolean billingDetailsValidation() {
        waitUntilElementDisplayed(billingDetailsSection, 3);
        if (isDisplayed(cardNumberEnding) && (isDisplayed(addressDetailsBilling))) {
            return true;
        } else return false;
    }

    public boolean billingDetails(String name, String address) {
        waitUntilElementDisplayed(billingDetailsSection, 3);
        String nameDisplayed = getText(billingSection_Name);
        String addressDisplayed = getText(billingSection_Address).replaceAll(",", "");
        if (nameDisplayed.equalsIgnoreCase(name) && (addressDisplayed.equalsIgnoreCase(address))) {
            waitUntilElementDisplayed(shippingDetailsSection, 2);
            String shipNameDisplayed = getText(shippingSection_Name);
            String shipAddressDisplayed = getText(shippingSection_Address).replaceAll(",", "");
            if (!shipNameDisplayed.equalsIgnoreCase(name) && !(shipAddressDisplayed.equalsIgnoreCase(addressDisplayed))) {
                return true;
            }
        } else
            addStepDescription("Billing address is  not same");
        return false;
    }

    public boolean billingDetailsCombinedOrder() {
        waitUntilElementDisplayed(billingDetailsSection, 3);
        if (isDisplayed(GiftCardPaymentSection) && isDisplayed(cardDetails) && isDisplayed(billingSection_Name)
                && isDisplayed(billingSection_Address)) {
            return true;
        } else
            addStepDescription("Billing address is  not same");
        return false;
    }

    public boolean giftCardOnlyDisplay() {
        boolean isGCDisplay = isDisplayed(gcInfo);
        boolean isBillingIno = isDisplayed(billingAddress);
        if (isGCDisplay && !isBillingIno) {
            return true;
        } else {
            addStepDescription("GC info display " + isGCDisplay + " billing address shouldn't display " + !isBillingIno);
            return false;
        }
    }

    public boolean guestOrderStatusModal(String email, String orderNumber) {
        waitUntilElementDisplayed(emailIDField);
        clearAndFillText(emailIDField, email);
        waitUntilElementDisplayed(orderIdField);
        clearAndFillText(orderIdField, orderNumber);
        click(trackOrderbutton);
        waitUntilElementDisplayed(orderDetailsHeading_Guest, 5);
        String currentUrl = getCurrentURL();
        String shipToAddress = getText(address_Shipping);
        String billingAddress = getText(address_Billing);
        if (currentUrl.contains(orderNumber) /*&& (currentUrl.contains(email)*/) {
            verifyElementNotDisplayed(orderDetails_Breadcrumb);
            shipToAddress.equals(billingAddress);
            return true;
        } else
            return false;
    }

    public boolean guestOrderStatusModalBOPIS(String email, String orderNumber) {
        waitUntilElementDisplayed(emailIDField);
        clearAndFillText(emailIDField, email);
        waitUntilElementDisplayed(orderIdField);
        clearAndFillText(orderIdField, orderNumber);
        click(trackOrderbutton);
        waitUntilElementDisplayed(orderDetailsHeading_Guest, 5);
        String currentUrl = getCurrentURL();
        isDisplayed(address_Billing);
        if (currentUrl.contains(orderNumber) /*&& (currentUrl.contains(email))*/) {
            verifyElementNotDisplayed(orderDetails_Breadcrumb);
            return true;
        } else
            return false;
    }

    public boolean mprBannerCheck() {
        if (!isDisplayed(mprBannerEspt)) {
            return true;
        } else
            addStepDescription("Points earned banner is displayed in Order details page");
        return false;
    }

    public boolean guestErrorMessage() {
        waitUntilElementDisplayed(emailIDField);
        tabFromField(emailIDField);
        waitUntilElementDisplayed(orderIdField);
        tabFromField(orderIdField);
        return closeVisibleModal();
    }

    public boolean closeVisibleModal() {
        waitUntilElementDisplayed(closeModal);
        click(closeModal);
        return !waitUntilElementDisplayed(closeModal, 2);
    }

    public boolean checkLoadingIcon() {
        if (isDisplayed(loadingIcon)) {
            return true;
        } else {
            addStepDescription("Loading icon is not displayed");
            return false;
        }
    }

    /**
     * validate int order redirection in
     *
     * @return true if url matches
     */
    public boolean intOrderRedirection() {
        waitUntilElementDisplayed(intOrder, 20);
        String currentWindow = driver.getWindowHandle();
        click(intOrder);
        switchToWindow(currentWindow);
        String url = "www.borderfree.com/order-status/";
        String currentUrl = getCurrentURL();
        return currentUrl.contains(url);
    }

    public boolean verifyPickupStoreDetails() {
        return waitUntilElementDisplayed(store_Name) &&
                waitUntilElementDisplayed(store_Address) &&
                waitUntilElementDisplayed(store_PhoneNo) && waitUntilElementDisplayed(store_Timings) &&
                waitUntilElementDisplayed(get_DirectionsLink);
    }

    public boolean verifyCardDetailsDisplayedInOrderStatus(String lastDigit) {
        waitUntilElementDisplayed(billingDetailsSection, 2);
        String cardDetails = getText(cardNumberEnding).trim();
        if (cardDetails.contains(lastDigit)) {
            return true;
        } else {
            addStepDescription("The Card Details displayed in Order Details is different");
            return false;
        }
    }

    public boolean clickOnGetDirectionLink() {
        waitUntilElementDisplayed(get_DirectionsLink);
        click(get_DirectionsLink);
        staticWait(2000);
        String currentWindow = driver.getWindowHandle();
        switchToWindow(currentWindow);
        String currentURL = getCurrentURL();
        if (currentURL.contains("/maps")) {

            return true;
        } else {
            addStepDescription("check the redirected page, when the user clicks on Get Direction Link");
            return false;
        }
    }

    @Deprecated // this is legacy requirement
    public boolean checkPhoneNumFormat() {
        String phoneDigits[];
        waitUntilElementDisplayed(store_PhoneNo, 20);
        String phoneNo = getText(store_PhoneNo);
        phoneDigits = phoneNo.split("-");
        return phoneDigits[0].length() == 3 && phoneDigits[1].length() == 3 && phoneDigits[2].length() == 4;
    }

    public boolean storeTimingRelatedValidation() {
        waitUntilElementDisplayed(store_Timings, 2);
        if (getText(store_Timings).contains("am") && (getText(store_Timings).contains("pm"))) {
            return true;
        } else {
            addStepDescription("Store hours are not displayed properly");
            return false;
        }
    }

    public boolean validateItemDetailsSectionForBOPISOrder() {
        waitUntilElementDisplayed(containerName_Product, 2);
        if (isDisplayed(containerName_OriginalPrice) && (isDisplayed(containerName_YouPaid) && (isDisplayed(containerName_Qty))
                && isDisplayed(getContainerName_Subtotal))) {
            return true;
        } else {
            addStepDescription("Some of the Column is missing in the Item details Section");
            return false;
        }
    }

    public boolean validateItemDetailsForSingleLineBOPISOrder(String orderNumber) {

        String currentURL = getCurrentURL();
        if (currentURL.contains(orderNumber)) {
            if (isDisplayed(item_UPC.get(0)) && (isDisplayed(item_Color.get(0)) && (isDisplayed(item_Size.get(0)))
                    && isDisplayed(item_Size.get(0))) && isDisplayed(itemOriginalPrice.get(0)) && isDisplayed(itemPricePaid.get(0))
                    && isDisplayed(quantityItem.get(0)) && isDisplayed(subtotalDisplayInContainer.get(0)) && !isDisplayed(trackingButton)) {
                double qty = Integer.parseInt(getText(quantityItem.get(0)));
                String price1 = (getText(subtotalDisplayInContainer.get(0)).replaceAll("[^0-9.]",""));
               double price = Double.parseDouble(price1);
                String productPrice1 =(getText(itemOriginalPrice.get(0)).replaceAll("[^0-9.]",""));
                double productPrice =  Double.parseDouble(productPrice1);
                double total=0.00;
                 total = qty*productPrice;
                 if(price==total){
                return true;}
                else{
                    addStepDescription("Subtotal and total quantity item price is different");
                    return false;
                 }
            } else {
                addStepDescription("Some of the item details are missing in the Item details Section");
                return false;
            }
        }
        else{
            addStepDescription("Check the Redirected Page URL, it doen't contain the Order#");
            return false;
        }
    }
    public boolean validateBillingSectionForBOPIS() {
        waitUntilElementDisplayed(billingDetailsSection);
        if (isDisplayed(billingSection_Name) && isDisplayed(billingSection_Address)) {
            return true;
        } else {
            addStepDescription("Billing Details are not displayed in Order Status Page");
            return false;
        }
    }
    public boolean validatePayPalDetails(){
        waitUntilElementDisplayed(payPalText);
        if(isDisplayed(payPalText)){
            return true;
        }
        else{
            addStepDescription("PayPal details are not displayed properly");
            return false;
        }
    }
}