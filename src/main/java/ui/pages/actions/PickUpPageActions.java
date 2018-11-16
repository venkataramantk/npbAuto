package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.sikuli.script.Key;
import ui.pages.repo.PickUpPageRepo;

/**
 * Created by AbdulazeezM on 5/11/2017.
 */
public class PickUpPageActions extends PickUpPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(ShippingPageActions.class);
    public String prodName;
    public String prodSize;
    public String prodColor;
    public String prodQuantity;
    public String prodPrice;

    public PickUpPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean validateItemCountWithBagCount(String cartCount) {

        String item = getText(itemTotalText).replaceAll("[^0-9]", "").substring(0, 1);

        int itemCount = Integer.parseInt(item);
        int bagCount = Integer.parseInt(cartCount);
        if (itemCount == bagCount)
            return true;
        else
            return false;
    }

    public boolean validateTotInPickupAndBagPage(String estimatedTot) {
        String orderTotal = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float orderTot = Float.parseFloat(orderTotal);
        float estimatedCost = Float.parseFloat(estimatedTot);
        if (orderTot == estimatedCost)
            return true;
        else
            return false;
    }

    public boolean returnToBagPage(ShoppingBagPageActions shoppingBagPageActions) {
        click(returnBagLink);
        waitUntilElementDisplayed(overlayContent, 10);
        click(returnBagBtn);
        waitUntilElementDisplayed(shoppingBagPageActions.shoppingBagTitle, 10);
        if (isDisplayed(shoppingBagPageActions.shoppingBagTitle)) {
            return true;
        } else {
            addStepDescription("Check the redirected page");
            return false;
        }

    }

    public boolean clickShippingBtn(ShippingPageActions shippingPageActions) {
        waitUntilElementDisplayed(nxtShippingBtn, 20);
        click(nxtShippingBtn);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 20);
    }

    public boolean clickBillingBtn(BillingPageActions billingPageActions) {
        waitUntilElementDisplayed(nxtBillingBtn, 20);
        click(nxtBillingBtn);
        staticWait(3000);
        //scrollDownUntilElementDisplayed(billingPageActions.nextReviewButton);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20) || waitUntilElementDisplayed(billingPageActions.proceedWithPaPalButton,3);
    }

    public boolean validateContentInPickUpPageAsReg() {
        if (waitUntilElementDisplayed(govtText, 5) &&
//                waitUntilElementDisplayed(signUpEmail_CheckBox,5) &&
                waitUntilElementDisplayed(alternatePickupCheckBox, 5))
            return true;
        else
            return false;
    }

    public boolean validateFieldsInPickUpPage() {
        if (waitUntilElementDisplayed(firstNameField, 5) &&
                waitUntilElementDisplayed(lastNameField, 5) &&
                waitUntilElementDisplayed(emailIdField, 5) &&
                waitUntilElementDisplayed(mobileNumField))
            return true;
        else
            return false;
    }

    public boolean clickEditLink() {
        click(editLink);
        return waitUntilElementDisplayed(fn_pickupContactFld);
    }

    public boolean clickAlternateContactCheckBox() {
        waitUntilElementDisplayed(alternatePickupCheckBox, 20);
        select(alternatePickupCheckBox, alternatePickupCheckBoxInput);
        return waitUntilElementDisplayed(altFirstNameFld, 20);
    }

    public boolean enterAltFieldValuesAndClickBilling(BillingPageActions billingPageActions) {
        waitUntilElementDisplayed(altFirstNameFld, 20);
        clearAndFillText(altFirstNameFld, "autofname");
        clearAndFillText(altLastNameField, "autolname");
        clearAndFillText(altEmailIdField, "tcpautomation@yopmail.com");
        click(nxtBillingBtn);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);
    }

    public boolean validateErrorMessagesTab(String fnameErr, String lnameErr, String emailErr, String mobileErr) {
        waitUntilElementDisplayed(firstNameField, 5);
        clearAndFillText(firstNameField, "");
        firstNameField.sendKeys(Key.TAB);
        waitUntilElementDisplayed(firstNameErr, 5);
        boolean validateFnameErr = getText(firstNameErr).contains(fnameErr);

        waitUntilElementDisplayed(lastNameField, 5);
        lastNameField.sendKeys(Key.TAB);
        waitUntilElementDisplayed(lastNameErr, 5);
        boolean validateLnameErr = getText(lastNameErr).contains(lnameErr);

        waitUntilElementDisplayed(emailIdField, 5);
        emailIdField.sendKeys(Key.TAB);
        waitUntilElementDisplayed(emailErrMsg, 5);
        boolean validateEmailErr = getText(emailErrMsg).contains(emailErr);

        waitUntilElementDisplayed(mobileNumField, 5);
        mobileNumField.sendKeys(Key.TAB);
        waitUntilElementDisplayed(mobileFieldErr, 5);
        boolean validateMobileErr = getText(mobileFieldErr).contains(mobileErr);

        if (validateFnameErr && validateLnameErr && validateEmailErr && validateMobileErr)
            return true;
        else
            return false;
    }

    public boolean validateErrorMessagesSplChar(String fnameErr, String lnameErr, String emailErr, String mobileErr) {
        waitUntilElementDisplayed(firstNameField, 5);
        clearAndFillText(firstNameField, "@#$");
        firstNameField.sendKeys(Key.TAB);
        waitUntilElementDisplayed(firstNameErr, 5);
        boolean validateFnameErr = getText(firstNameErr).contains(fnameErr);

        waitUntilElementDisplayed(lastNameField, 5);
        clearAndFillText(lastNameField, "@#$");
        lastNameField.sendKeys(Key.TAB);
        waitUntilElementDisplayed(lastNameErr, 5);
        boolean validateLnameErr = getText(lastNameErr).contains(lnameErr);

        waitUntilElementDisplayed(emailIdField, 5);
        clearAndFillText(emailIdField, "@#$");
        emailIdField.sendKeys(Key.TAB);
        waitUntilElementDisplayed(emailErrMsg, 5);
        boolean validateEmailErr = getText(emailErrMsg).contains(emailErr);

        waitUntilElementDisplayed(mobileNumField, 5);
        clearAndFillText(mobileNumField, "@#$");
        mobileNumField.sendKeys(Key.TAB);
        waitUntilElementDisplayed(mobileFieldErr, 5);
        boolean validateMobileErr = getText(mobileFieldErr).contains(mobileErr);

        if (validateFnameErr && validateLnameErr && validateEmailErr && validateMobileErr)
            return true;
        else
            return false;
    }

    public boolean validateSingleFldEmptyErrMsgs(String fName, String lName, String email, String phoneNum, String errFName, String errLName,
                                                 String errEmail, String errPhoneNum, String emptyChar) {

        boolean fNameVal = validateErrOnFNameFld_SplChar(fName, lName, email, phoneNum, emptyChar, errFName);
        boolean lNameVal = validateErrOnLNameFld_SplChar(fName, lName, email, phoneNum, emptyChar, errLName);
        boolean emailVal = validateErrOnEmailFld_SplChar(fName, lName, email, phoneNum, emptyChar, errEmail);
        boolean phoneVal = validateErrOnPhoneFld_SplChar(fName, lName, email, phoneNum, emptyChar, errPhoneNum);

        if (fNameVal
                && lNameVal
                && emailVal
                && phoneVal)
            return true;

        else
            return false;
    }

    public boolean validateErrOnFNameFld_SplChar(String fName, String lName, String email, String mobile, String splChar, String errFName) {
        enterFieldValuesWithoutNextClick(fName, lName, email, mobile);
        clearAndFillText(firstNameField, splChar);
        tabFromField(firstNameField);
        waitUntilElementDisplayed(firstNameErr, 2);
        boolean fNameErr = getText(firstNameErr).contains(errFName);
        return fNameErr;
    }

    public boolean validateErrOnLNameFld_SplChar(String fName, String lName, String email, String mobile, String splChar, String errLName) {
        enterFieldValuesWithoutNextClick(fName, lName, email, mobile);
        clearAndFillText(firstNameField, "autoFirstName");
        tabFromField(firstNameField);
        waitUntilElementDisplayed(lastNameField, 2);
        clearAndFillText(lastNameField, splChar);
        boolean lNameErr = getText(lastNameErr).contains(errLName);
        return lNameErr;
    }

    public boolean validateErrOnEmailFld_SplChar(String fName, String lName, String email, String mobile, String splChar, String errEmail) {
        enterFieldValuesWithoutNextClick(fName, lName, email, mobile);
        clearAndFillText(firstNameField, "autoFirstName");
        tabFromField(firstNameField);
        clearAndFillText(firstNameField, "autoLastName");
        tabFromField(firstNameField);
        waitUntilElementDisplayed(emailIdField, 2);
        clearAndFillText(emailIdField, splChar);
        boolean emailErr = getText(emailErrMsg).contains(errEmail);
        return emailErr;
    }

    public boolean validateErrOnPhoneFld_SplChar(String fName, String lName, String email, String mobile, String splChar, String errMobile) {
        enterFieldValuesWithoutNextClick(fName, lName, email, mobile);
        clearAndFillText(firstNameField, "autoFirstName");
        tabFromField(firstNameField);
        clearAndFillText(firstNameField, "autoLastName");
        tabFromField(firstNameField);
        clearAndFillText(firstNameField, "tcp@yopmail.com");
        tabFromField(firstNameField);
        waitUntilElementDisplayed(mobileNumField, 2);
        clearAndFillText(mobileNumField, splChar);
        boolean mobileErr = getText(mobileFieldErr).contains(errMobile);
        return mobileErr;
    }

    public boolean enterFieldValuesWithoutNextClick(String fName, String lName, String email, String mobile) {
        waitUntilElementDisplayed(firstNameField, 10);
        clearAndFillText(firstNameField, fName);
        clearAndFillText(lastNameField, lName);
        clearAndFillText(emailIdField, email);
        clearAndFillText(mobileNumField, mobile);
        return waitUntilElementDisplayed(nxtBillingBtn, 10);
    }

    public boolean enterFieldValuesWithoutNextClickMixedCart(String fName, String lName, String email, String mobile) {
        waitUntilElementDisplayed(firstNameField, 10);
        clearAndFillText(firstNameField, fName);
        clearAndFillText(lastNameField, lName);
        clearAndFillText(emailIdField, email);
        clearAndFillText(mobileNumField, mobile);
        return waitUntilElementDisplayed(nxtShippingBtn, 10);
    }

    public boolean clickPickUpInProgressBar() {
        click(pickUplinkInProgressBar);
        return waitUntilElementDisplayed(nxtButton, 30);
    }

    /**
     * Validate progress indicators for all in pickup Page
     *
     * @return true if all matches
     */
    public boolean validateProgressbarStatusForBopis() {
        waitUntilElementDisplayed(nxtBillingBtn, 10);
        String shippingProgressBarStatus = getAttributeValue(pickupProgressBar, "class");
        String billingProgressBarStatus = getAttributeValue(billingProgressBar, "class");
        String title = getText(checkoutTitle);
        String reviewProgressBarStatus = getAttributeValue(reviewProgressBar, "class");

        return shippingProgressBarStatus.equalsIgnoreCase("active") &&
                billingProgressBarStatus.equalsIgnoreCase("") &&
                reviewProgressBarStatus.equalsIgnoreCase("") &&
                title.equalsIgnoreCase("Pickup");
    }

    public boolean getReturnCode() {
        String response = getValueOfDataElement("response_checkout/getOrderDetails");
        logger.info("get order details response in pickup page: " + response);
        String returnCode = "storeHours\":\"";
        int i = response.indexOf(returnCode) + returnCode.length();
        return response.substring(i).trim().equalsIgnoreCase("{\\\"storeHours\\\":[]}");

    }

}
