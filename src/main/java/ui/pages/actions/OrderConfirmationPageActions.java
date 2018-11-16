package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.OrderConfirmationPageRepo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by skonda on 5/19/2016.
 */
public class OrderConfirmationPageActions extends OrderConfirmationPageRepo {

    WebDriver driver;
    Logger logger = Logger.getLogger(OrderConfirmationPageActions.class);
    public static String NumberOfItems = "";

    public static String Number = "";
    public static String Date = "";
    public static String Points = "";

    public static double ShippingTax;
    public static double ShippingAmount;

    public static double Tax;
    public static double Total;
    public static double SubTotal;

    public OrderConfirmationPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }


    public String getOrderNum() {
        waitUntilElementDisplayed(orderNumber);
        String orderNum = getText(orderNumber);
        return orderNum;
    }

    public String getAnySingleOrderNum() {
        waitUntilElementDisplayed(orderNumber);
        String orderNum = getText(orderNumber);
        return orderNum;
    }

    public double GetShippingAmount() {
        return Double.parseDouble(lbl_ShippingAmount.getText().replace("$", "").replace(" ", "").replace(",", ".").replace("USD", ""));
    }

    public double GetShippingTaxAmount() {
        return Double.parseDouble(lbl_ShippingTaxAmount.getText().replace("$", "").replace(" ", "").replace(",", ".").replace("USD", ""));
    }

    public double GetSubTotalAmount() {
        return Double.parseDouble(lbl_SubTotalAmount.getText().replace("$", "").replace(" ", "").replace(",", ".").replace("USD", ""));
    }

    public double GetTotalAmount() {
        return Double.parseDouble(lbl_TotalAmount.getText().replace("$", "").replace(" ", "").replace(",", ".").replace("USD", ""));
    }

    public String getTotal() {
        String total = lbl_TotalAmount.getText();
        return total;

    }

    public boolean verifyErrorMessagesInCreateAccountEspot() {
        clickOnTheCreateAccount();
        if (isDisplayed(err_password) &&
                isDisplayed(err_ConfirmPassword) &&
                isDisplayed(err_TermsAndConditions)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyBillingAddress(String fName, String lName, String addressLine1, String addressLine2, String city, String stateShort, String zip, String country, String emailAddress) {
        String billingAddress = billingAddressSection.getText().toLowerCase().replaceAll("\n", "").replaceAll(" ", "");
        String testNew = (fName + lName + (addressLine1.replaceAll(" ", "")) + (addressLine2.replaceAll(" ", "")) + city + (",") + stateShort + (zip.replaceAll(" ", "")) + (country.replaceAll(" ", "")) + emailAddress).toLowerCase().replaceAll("\n", "");
        if (billingAddress.contains((fName + lName + (addressLine1.replaceAll(" ", "")) + (addressLine2.replaceAll(" ", "")) + city + (",") + stateShort + (zip.replaceAll(" ", "")) + (country.replaceAll(" ", "")) + emailAddress).toLowerCase().replaceAll("\n", ""))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyShippingAddress(String fName, String lName, String addressLine1, String addressLine2, String city, String stateShort, String zip, String country) {
        try {
            String shippingAddress = shippingInfoSection.getText().toLowerCase().replaceAll("\n", "").replaceAll(" ", "");
            if (shippingAddress.contains((fName + lName + (addressLine1.replaceAll(" ", "")) + (addressLine2.replaceAll(" ", "")) + city + (",") + stateShort + (zip.replaceAll(" ", "")) + (country.replaceAll(" ", ""))).toLowerCase().replaceAll("\n", ""))) {
                return true;
            } else {
                return false;
            }
        } catch (Throwable t) {
            return false;
        }
    }

    public void clickOnTheCreateAccount() {
        click(createAccountButton);
    }


    public String shipMethodSection() {
        return shippingMethodSection.getText().replaceAll(" ", "");
    }

    public String paymentMethodSection() {
        return paymentMethodSection.getText().toUpperCase();
    }

    public String giftOptionsSection() {
        return giftOptionsSection.getText().toLowerCase().replaceAll("\n", "").replaceAll(" ", "");
    }

    public String airMilesRewardsSection() {
        return airMilesRewards.getText().toLowerCase().replaceAll("\n", "").replaceAll(" ", "");
    }

    public boolean verifyAmountBilledDetails(BillingPageActions billingPageActions) {
//        if(getText(paymentPageActions.itemsTotalPrice).contains(getText(itemsTotal)) &&
//           getText(paymentPageActions.shippingTotalPrice).contains(getText(shippingTotal))&&
////           getText(paymentPageActions.payment_shipping_Tax).contains(getText(orderReceipt_shipping_Tax)) &&
//           getText(paymentPageActions.tax_Total).contains(getText(estimatedTax)) &&
//           getText(paymentPageActions.estimatedTotal).contains(getText(totalCharges))){
//
//            return true;
//        }
        if ((billingPageActions.itemsTotalPr.contains(getText(itemsTotalPrice))) &&
                (billingPageActions.shipTotalPr.contains(getText(shippingTotalPrice))) &&
                (billingPageActions.estTaxPrPr.contains(getText(tax_Total))) &&
                (billingPageActions.estTotalPr.contains(getText(estimatedTotal)))) {

            return true;
        } else {
            return false;
        }
    }


    public boolean isFreeShippingApplied() {
        boolean isPromotionLblDisplaying = waitUntilElementDisplayed(promotionsLabel, 1);
        boolean isShipDiscDisplaying = waitUntilElementDisplayed(freeShipPriceLabel, 1);

        if (isShipDiscDisplaying && isPromotionLblDisplaying) {

            return getText(promotionsLabel).equalsIgnoreCase("Promotions") && getText(freeShipPriceLabel).equalsIgnoreCase("Free");
        }
        return false;


    }

    public boolean validateOldPromoNotDisplaying() {
        boolean isOldMPRESpotDisplaying = waitUntilElementDisplayed(mPReSpotOld);
        boolean isOldMPRDetailsDisplaying = waitUntilElementDisplayed(mPRDetails);
        boolean isOldMPRBonusInfoDisplaying = waitUntilElementDisplayed(mPRDetailsBonusInfo);
        boolean isOldMPRBonusInfoDetailsDisplaying = waitUntilElementDisplayed(mPRDetailsBonusInfoDetails);

        if (!(isOldMPRESpotDisplaying && isOldMPRDetailsDisplaying && isOldMPRBonusInfoDisplaying && isOldMPRBonusInfoDetailsDisplaying))
            return true;
        else
            return false;
    }

    public boolean verifyTheItemCountWithBag(String cartCount) {
        String item = getText(itemsCount);
        int startIndex = item.indexOf("(");//Integer.parseInt(item);
        int endIndex = item.indexOf(")");
        int itemCount = Integer.parseInt(item.substring(startIndex + 1, endIndex));
        int bagCount = Integer.parseInt(cartCount);
        if (itemCount == bagCount)
            return true;
        else
            return false;
    }

    public boolean verifyTheEstimatedTotalWithBag(String estimatedTotal) {
        String orderTotal = getText(estimatedTot).replaceAll("[^0-9.]", "");

        float orderTot = Float.parseFloat(orderTotal);
        float estimatedCost = Float.parseFloat(estimatedTotal);

        if (orderTot == estimatedCost)
            return true;
        else
            return false;
    }

    public boolean validateTextInOrderConfirmPageReg() {
        if (waitUntilElementDisplayed(thanksText, 10) &&
                waitUntilElementDisplayed(emailID, 10) &&
                waitUntilElementDisplayed(orderNumber, 10) &&
                waitUntilElementDisplayed(estimatedTot, 10))
            return true;
        else
            return false;
    }

    public boolean validateTextInOrderConfirmPageGuest() {
        if (waitUntilElementDisplayed(thanksText, 10) &&
                waitUntilElementDisplayed(emailID, 10) &&
                waitUntilElementDisplayed(orderNumber, 10) &&
                waitUntilElementDisplayed(createAccountButton, 10) &&
                waitUntilElementDisplayed(enterPasswordField, 10) &&
                waitUntilElementDisplayed(confirmPasswordField, 10) &&
                waitUntilElementDisplayed(termsAndConditionsTextLbl, 10))
            return true;
        else
            return false;
    }

    public boolean validateErrorMessagesTab(String passwordErr, String confirmPwdErr) {

        waitUntilElementDisplayed(enterPasswordField, 5);
        enterPasswordField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(err_password, 5);
        boolean validatePasswordErr = getText(err_password).contains(passwordErr);

        waitUntilElementDisplayed(confirmPasswordField, 5);
        confirmPasswordField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(err_ConfirmPassword, 5);
        boolean validateConfirmPasswordErr = getText(err_ConfirmPassword).contains(confirmPwdErr);

        if (validatePasswordErr && validateConfirmPasswordErr)
            return true;
        else
            return false;
    }

    public boolean showHideLinkPassword(String pwd) {
        waitUntilElementDisplayed(enterPasswordField, 4);
        clearAndFillText(enterPasswordField, pwd);
        click(showHideLink);
        boolean checkShow = getAttributeValue(enterPasswordField, "value").contains(pwd) && getText(showHideLink).equalsIgnoreCase("hide");
        click(showHideLink);
        boolean checkHide = (!(getText(enterPasswordField).contains(pwd))) && getText(showHideLink).equalsIgnoreCase("show");
        return checkShow && checkHide;
    }

    public boolean validateErrPassword(String splChar, String errPassword) {

        clearAndFillText(enterPasswordField, splChar);
        tabFromField(enterPasswordField);
        clearAndFillText(confirmPasswordField, splChar);
        tabFromField(confirmPasswordField);
        boolean pwdInlineErr = waitUntilElementDisplayed(err_password, 5);
        if (pwdInlineErr) {
            pwdInlineErr = getText(err_password).contains(errPassword);
        }
        return pwdInlineErr;
    }

    public boolean validateOrderLedgerSectionGuestReg() {
        if (waitUntilElementDisplayed(itemTotal, 10) &&
                waitUntilElementDisplayed(estimatedTot, 10) &&
                waitUntilElementDisplayed(shippingTot, 10) &&
                waitUntilElementDisplayed(taxTotal, 10))
            return true;
        else
            return false;
    }

    public boolean createAccInOrderConfirmPage() {

        clearAndFillText(enterPasswordField, "P@ssw0rd");
        tabFromField(enterPasswordField);
        clearAndFillText(confirmPasswordField, "P@ssw0rd");
        tabFromField(confirmPasswordField);
        click(termsAndConditionsTextLbl);
        staticWait(2000);
        click(createAccountButton);
        return verifyElementNotDisplayed(createAccountButton, 20);
    }

    public boolean checkOrderIDForHybirdOrder() {
        if (waitUntilElementDisplayed(sthOrderID, 10) &&
                waitUntilElementDisplayed(sthOrderDate, 10) &&
                waitUntilElementDisplayed(sthOrderTotal, 10) &&
                waitUntilElementDisplayed(bopisOrderID, 10) &&
                waitUntilElementDisplayed(bopisOrderDate, 10) &&
                waitUntilElementDisplayed(bopisOrderTotal, 10))
            return true;
        else
            return false;
    }

    public boolean validateHyBridCartTotal() {
        String shippingTotal = "0";

        String itemSubTotal = getText(itemTotal).replaceAll("[^0-9.]", "");
        String taxApplied = getText(taxTotal).replaceAll("[^0-9.]", "");
        String estimatedTotal = getText(estimatedTot).replaceAll("[^0-9.]", "");
        if (!getText(shippingTotalPrice).equalsIgnoreCase("FREE"))
            shippingTotal = getText(shippingTotalPrice).replaceAll("[^0-9.]", "");

        if (waitUntilElementDisplayed(promotion_Cost, 10)) {

            String promoTot = getText(promotion_Cost).replaceAll("[^0-9.]", "");
            float promoCost = Float.parseFloat(promoTot);
            float hybridCartTotal = Float.parseFloat(itemSubTotal);
            float taxTotal = Float.parseFloat(taxApplied);
            float totalCount = Float.parseFloat(estimatedTotal);
            float shippingPri = Float.parseFloat(shippingTotal);

            float total = hybridCartTotal + taxTotal + shippingPri - promoCost;
            float roundTotal = roundFloat(total, 2);

            if (roundTotal == totalCount)
                return true;
            else
                return false;
        }
        if (waitUntilElementDisplayed(coupon_Cost, 10)) {

            String couponTot = getText(coupon_Cost).replaceAll("[^0-9.]", "");
            float couponCost = Float.parseFloat(couponTot);
            float hybridCartTotal = Float.parseFloat(itemSubTotal);
            float taxTotal = Float.parseFloat(taxApplied);
            float totalCount = Float.parseFloat(estimatedTotal);
            float shippingPri = Float.parseFloat(shippingTotal);

            float total = hybridCartTotal + taxTotal + shippingPri - couponCost;
            float roundTotal = roundFloat(total, 2);

            if (roundTotal == totalCount)
                return true;
            else
                return false;
        }


        if (waitUntilElementDisplayed(coupon_Cost, 10)) {

            String couponTot = getText(coupon_Cost).replaceAll("[^0-9.]", "");
            float couponCost = Float.parseFloat(couponTot);
            float hybridCartTotal = Float.parseFloat(itemSubTotal);
            float taxTotal = Float.parseFloat(taxApplied);
            float totalCount = Float.parseFloat(estimatedTotal);
            float shippingPri = Float.parseFloat(shippingTotal);

            float total = hybridCartTotal + taxTotal + shippingPri - couponCost;
            float roundTotal = roundFloat(total, 2);

            if (roundTotal == totalCount)
                return true;
            else
                return false;
        }

        float hybridCartTotal = Float.parseFloat(itemSubTotal);
        float taxTotal = Float.parseFloat(taxApplied);
        float totalCount = Float.parseFloat(estimatedTotal);
        float shippingPri = Float.parseFloat(shippingTotal);

        float total = hybridCartTotal + taxTotal + shippingPri;
        float roundTotal = roundFloat(total, 2);

        if (roundTotal == totalCount)
            return true;
        else
            return false;
    }

    public boolean clickStoreLink() {
        waitUntilElementDisplayed(storeLocator, 10);
        click(storeLocator);
        staticWait(4000);
        boolean compareURL = getCurrentURL().contains("store");
        if (compareURL) return true;
        else return false;
    }

    public boolean clickOnSTHOrderID() {
        scrollDownToElement(sthOrderID);
        String sthID = sthOrderID.getText();
        click(sthOrderID);
        waitUntilElementDisplayed(loadingIcon,1); // checking loading icon
        staticWait(2000);
        boolean compareURL = getCurrentURL().contains(sthID);
        if (compareURL)
            return true;
        else
            return false;
    }

    public boolean clickOnSingleOrderNum(OrderStatusActions orderStatusActions) {
        waitUntilElementDisplayed(orderNumber, 10);
        click(orderNumber);
        waitUntilElementDisplayed(loadingIcon,2);
        return waitUntilElementDisplayed(orderStatusActions.orderDetailsPage);
//        boolean compareURL = getCurrentURL().contains(getOrderNum());
//        if (compareURL) return true;
//        else return false;
    }

    public boolean clickOnBopisOrderID(OrderStatusActions orderStatusActions) {
        waitUntilElementDisplayed(bopisOrderID, 10);
        click(bopisOrderID);
        isDisplayed(loadingIcon);
        staticWait(5000);
        return waitUntilElementDisplayed(orderStatusActions.orderDetailsPage,4) || waitUntilElementDisplayed(orderStatusActions.orderDetailHeader,3);
    }

    public String getBopisOrderID() {
        waitUntilElementDisplayed(bopisOrderID, 10);
        return getText(bopisOrderID);
    }

    public String getSTHOrderID() {
        waitUntilElementDisplayed(sthOrderID, 10);
        return getText(sthOrderID);
    }

    public String getItemCount() {
        String item = getText(itemTotal).replace("$", "");

        return item;
    }

    public String getEstimatedTot() {
        String orderTotal = getText(estimatedTot).replace("$", "");
        return orderTotal;

    }

    public static float roundFloat(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }


    public boolean addProdToFav_Reg() {
        mouseHover(itemImg(3));
        waitUntilElementsAreDisplayed(addToBagIcon, 5);
        waitUntilElementsAreDisplayed(addToFavIcon, 5);
        if (addToFavIcon.size() >= 1) {
            click(addToFavIcon.get(2));
            staticWait(3000);
            return waitUntilElementDisplayed(favIconEnabled.get(2));
        }
        return false;
    }

    public boolean selectRandomSizeAndAddToBag(HeaderMenuActions headerMenuActions, int i) {
        mouseHover(itemImg(i));
        if (addToBagIcon.size() > 1) {
            click(addToBagIcon.get(1));
            staticWait(3000);
        }
        staticWait(2000);

        if (availableColors.size() >= 1) {
            click(availableColors.get(randInt(0, (availableColors.size() - 1))));
            staticWait(1000);
        }
        if (availableFits.size() >= 1) {
            click(availableFits.get(randInt(0, (availableFits.size() - 1))));
            staticWait(1000);
        }
        if (availableSizes.size() >= 1) {
            click(availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(1000);
        }

        String getBagCountBefore = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountBeforeAddToBag = Integer.parseInt(getBagCountBefore);
        staticWait(2000);
        click(addToBagBtn);
        staticWait(2000);
        String getBagCountAfter = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountAfterAddToBag = Integer.parseInt(getBagCountAfter);
        staticWait(2000);
        boolean isProductAddedToBag = (bagCountBeforeAddToBag + 1) == bagCountAfterAddToBag;
        return isProductAddedToBag;
    }

    public boolean validateOrderLedgerSectionGuestRegAppliedCoupon() {
        if (waitUntilElementDisplayed(itemTotal, 10) &&
                waitUntilElementDisplayed(estimatedTot, 2) &&
                waitUntilElementDisplayed(shippingTot, 2) &&
                waitUntilElementDisplayed(taxTotal, 2) &&
                waitUntilElementDisplayed(couponTextTot, 2))
            return true;
        else
            return false;
    }

    public boolean validateTextInOrderConfirmPageBOPIS(String content) {
        if (waitUntilElementDisplayed(thanksText, 10) &&
                waitUntilElementDisplayed(emailID, 10) &&
                waitUntilElementDisplayed(orderNumber, 10) &&
                waitUntilElementDisplayed(estimatedTot, 10) &&
                waitUntilElementDisplayed(whatsNextText, 3) &&
                waitUntilElementDisplayed(storeTooltip, 3)) ;

        String toolTipDisplayed = getText(whatsNextText).replaceAll(",","");
        if (toolTipDisplayed.contains(content)) {
            click(storeTooltip);
            waitUntilElementDisplayed(toolTipContent);
            return true;
        } else {
            addStepDescription("Content is not proper or ensure the element changes");
            return false;
        }
    }
    public boolean validateTextInOrderConfirmPageShipIt(String content) {
        if (waitUntilElementDisplayed(thanksText, 10) &&
                waitUntilElementDisplayed(emailID, 10) &&
                waitUntilElementDisplayed(orderNumber, 10) &&
                waitUntilElementDisplayed(estimatedTot, 10) &&
                waitUntilElementDisplayed(whatsNextText, 3));


        String toolTipDisplayed = getText(whatsNextText).replaceAll(",","");
        if (toolTipDisplayed.contains(content)) {
            return true;
        } else {
            addStepDescription("Content is not proper or ensure the element changes");
            return false;
        }
    }
    public boolean validateTextInOrderConfirmPageCombined(String content) {
        if (waitUntilElementDisplayed(thanksText, 10) &&
                waitUntilElementDisplayed(emailID, 10) &&
                waitUntilElementDisplayed(sthOrderID, 10) &&
                waitUntilElementDisplayed(bopisOrderID, 10)&&
                waitUntilElementDisplayed(estimatedTot, 10) &&
                waitUntilElementDisplayed(whatsNextText, 3) &&
                waitUntilElementDisplayed(storeTooltip, 3)) ;

        String toolTipDisplayed = getText(whatsNextText).replaceAll(",","");
        if (toolTipDisplayed.contains(content)) {
            click(storeTooltip);
            waitUntilElementDisplayed(toolTipContent);
            return true;
        } else {
            addStepDescription("Content is not proper or ensure the element changes");
            return false;
        }
    }
    public boolean validateEspotRedirection() {
        if (isDisplayed(confBannerEspot)) {
            click(confBannerEspot);
            String redirectedURL = getCurrentURL();
            if (redirectedURL.contains("myplace-rewards-page")) ;
            return true;
        } else {
            addStepDescription("PLCC E-spot not displayed");
            return false;
        }
    }
    public boolean checkLoadingIconByClickingOrderId(){
        waitUntilElementDisplayed(orderNumber);
        click(orderNumber);
        if(isDisplayed(loadingIcon)){
        return true;}
        else{
            addStepDescription("Loading icon is not displayed");
            return false;
        }
    }
    public boolean validateTransactionalSMSSignUpCheckbox() {
        return waitUntilElementDisplayed(transactionalSmsCheckBox, 5) && !isSelected(transactionalSmsCheckBox, transactionalSmsCheckBoxInput);
    }

    public boolean checkTransactionalSMSSignUpCheckBox() {
        waitUntilElementDisplayed(transactionalSmsCheckBox, 10);
        select(transactionalSmsCheckBox, transactionalSmsCheckBox);
        return waitUntilElementDisplayed(transactionalSMS_Disclaimer, 10) && waitUntilElementDisplayed(trnasactionalSMS_PhoneField, 10) && waitUntilElementDisplayed(smsPrivacyPolicy, 10);
    }

    public String getSmsPhone() {
        waitUntilElementDisplayed(trnasactionalSMS_PhoneField, 10);
        return getAttributeValue(trnasactionalSMS_PhoneField, "value");
    }

    public void updateSmsPhone(String phone) {
        waitUntilElementDisplayed(trnasactionalSMS_PhoneField, 10);
        clearAndFillText(trnasactionalSMS_PhoneField,"");
        clearAndFillText(trnasactionalSMS_PhoneField, phone);
    }

    public boolean validateSmsErrorMessage(String message) {
        waitUntilElementDisplayed(phoneNoErrMsg, 10);
        return getText(phoneNoErrMsg).contains(message);
    }

    public boolean validatePageLevelError(String message) {
        waitUntilElementDisplayed(pageLeverError, 10);
        return getText(pageLeverError).contains(message);
    }

    public void clickSubmitBtn() {
        waitUntilElementDisplayed(submitBtn, 10);
        click(submitBtn);
    }

    public boolean getSuccessMessage(String msg) {
        waitUntilElementDisplayed(accountSuccess, 20);
        return getText(accountSuccess).contains(msg);
    }

    public boolean uncheckSmsSignUpCB() {
        waitUntilElementDisplayed(transactionalSmsCheckBox, 10);
        staticWait();
        unSelect(transactionalSmsCheckBox, transactionalSmsCheckBoxInput);
        return !waitUntilElementDisplayed(transactionalSMS_Disclaimer, 5);
    }
    public boolean checkSmsSignUpCB() {
        waitUntilElementDisplayed(transactionalSmsCheckBox, 10);
        select(transactionalSmsCheckBox, transactionalSmsCheckBoxInput);
        return waitUntilElementDisplayed(transactionalSMS_Disclaimer, 10) && waitUntilElementDisplayed(trnasactionalSMS_PhoneField, 10) && waitUntilElementDisplayed(smsPrivacyPolicy, 10);
    }
    //ODM
    public boolean validateODMHeader() {
        if (waitUntilElementDisplayed(odmHeaderText, 10)) {
            return waitUntilElementDisplayed(odmHeaderText, 3);
        } else {
            addStepDescription("ODM header is not displayed");
            return false;
        }
    }

    public boolean validateODMCouponFields() {
            if(isDisplayed(couponDescription)){
                return waitUntilElementsAreDisplayed(barCode, 10) &&
                    waitUntilElementsAreDisplayed(promoCode, 2) &&
                    waitUntilElementsAreDisplayed(validityDate, 3) &&
                    waitUntilElementsAreDisplayed(detailsLink, 1);
        } else {
            addStepDescription("ODM Coupon is not displayed");
            return false;
        }
    }

    public boolean validateDetailsLink() {
        if (waitUntilElementsAreDisplayed(detailsLink, 10)) {
            click(detailsLink.get(0));
            return waitUntilElementDisplayed(longDescrption);
        } else {
            addStepDescription("Details Link is not displayed");
            return false;
        }
    }

    public boolean closeDetailsLink() {
        if (waitUntilElementDisplayed(closeDetailsLink, 10)) {
            click(closeDetailsLink);
            return !waitUntilElementDisplayed(longDescrption, 3);
        } else {
            addStepDescription("Close Details CTA is not displayed");
            return false;
        }
    }

    public String getCouponDate() {
        if (waitUntilElementsAreDisplayed(validityDate, 10)) {
            return getText(validityDate.get(0));
        } else {
            addStepDescription("Coupon date is not displayed");
            return "";
        }
    }

    public boolean validateCouponsStartDate() {
        String date = getCouponDate();

        if (getCouponDate().contains("Now through")) {
            addStepDescription("Current Date is equal to or later than start date - Displaying Now through");
            return true;
        } else if (getCouponDate().contains("-")) {
            addStepDescription("Current date is prior to start date - Displaying start date - end date format");
            return true;
        }
        return false;
    }
}
