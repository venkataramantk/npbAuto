package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.sikuli.script.Key;
import uiMobile.pages.repo.MobilePickUpPageRepo;


/**
 * Created by AbdulazeezM on 5/11/2017.
 */
public class MobilePickUpPageActions extends MobilePickUpPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MobilePickUpPageActions.class);
    public String prodName;
    public String prodSize;
    public String prodColor;
    public String prodQuantity;
    public String prodPrice;

    public MobilePickUpPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
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

    public boolean validateTotInShippingAndBagPage(String estimatedTot) {
        String orderTotal = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float orderTot = Float.parseFloat(orderTotal);
        float estimatedCost = Float.parseFloat(estimatedTot);
        if (orderTot == estimatedCost)
            return true;
        else
            return false;
    }

    public boolean returnToBagPage(MShoppingBagPageActions shoppingBagPageActions) {
        click(returnBagLink);
        waitUntilElementDisplayed(overlayContent, 10);
        click(returnBagBtn);
        waitUntilElementDisplayed(shoppingBagPageActions.shoppingBagTitle, 30);
        return false;
    }

    public boolean clickShippingBtn(MShippingPageActions shippingPageActions) {
        waitUntilElementDisplayed(nxtShippingBtn, 20);
        click(nxtShippingBtn);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 20);
    }

    public boolean clickBillingBtn(MBillingPageActions billingPageActions) {
        waitUntilElementDisplayed(nxtBillingBtn, 20);
        javaScriptClick(mobileDriver,nxtBillingBtn);
       // scrollDownUntilElementDisplayed(billingPageActions.nextReviewButton);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);
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

    public boolean editPickUpDetails(String fname, String lName, String mobileNo) {
        clickEditLink();
        clearAndFillText(fn_pickupContactFld, fname);
        clearAndFillText(ln_pickupContactFld, lName);
        clearAndFillText(mn_pickupContactFld, mobileNo);
        click(saveButton);
        return waitUntilElementDisplayed(editLink);
    }

    public boolean clickAlternateContactCheckBox() {
        waitUntilElementDisplayed(alternatePickupCheckBox, 20);
        click(alternatePickupCheckBox);
        return waitUntilElementDisplayed(firstNameField, 20);
    }

    public boolean enterFieldValuesAndClickBilling(MBillingPageActions billingPageActions) {
        waitUntilElementDisplayed(altFirstNameFld, 20);
        clearAndFillText(altFirstNameFld, "autofname");
        clearAndFillText(altLastNameField, "autolname");
        clearAndFillText(altEmailIdField, "tcpautomation@yopmail.com");
        click(nxtBillingBtn);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);
    }

    public boolean enterFieldValidationsAndClickShipping(String fname, String lname, String email, String phone) {
        MShippingPageActions shippingPageActions = new MShippingPageActions(mobileDriver);
        waitUntilElementDisplayed(altFirstNameFld, 20);
        clearAndFillText(altFirstNameFld, fname);
        clearAndFillText(altLastNameField, lname);
        clearAndFillText(altEmailIdField, email);
        clearAndFillText(mobileNumField, phone);
        click(nxtShippingBtn);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 20);
    }

    public boolean validateErrorMessagesTab(String fnameErr, String lnameErr, String emailErr, String mobileErr) {
        waitUntilElementDisplayed(firstNameField, 5);
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
        fillPaymentAddrDetailsWithoutSameAsShip(fName, lName, email, mobile);
        clearAndFillText(firstNameField, splChar);
        tabFromField(firstNameField);
        waitUntilElementDisplayed(firstNameErr, 2);
        boolean fNameErr = getText(firstNameErr).contains(errFName);
        return fNameErr;
    }

    public boolean validateErrOnLNameFld_SplChar(String fName, String lName, String email, String mobile, String splChar, String errLName) {
        fillPaymentAddrDetailsWithoutSameAsShip(fName, lName, email, mobile);
        clearAndFillText(firstNameField, "autoFirstName");
        tabFromField(firstNameField);
        waitUntilElementDisplayed(lastNameField, 2);
        clearAndFillText(lastNameField, splChar);
        boolean lNameErr = getText(lastNameErr).contains(errLName);
        return lNameErr;
    }

    public boolean validateErrOnEmailFld_SplChar(String fName, String lName, String email, String mobile, String splChar, String errEmail) {
        fillPaymentAddrDetailsWithoutSameAsShip(fName, lName, email, mobile);
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
        fillPaymentAddrDetailsWithoutSameAsShip(fName, lName, email, mobile);
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

    public boolean fillPaymentAddrDetailsWithoutSameAsShip(String fName, String lName, String email, String mobile) {
        waitUntilElementDisplayed(firstNameField, 10);
        staticWait();
        clearAndFillText(firstNameField, fName);
        staticWait();
        clearAndFillText(lastNameField, lName);
        staticWait();
        clearAndFillText(emailIdField, email);
        staticWait();
        selectDropDownByValue(mobileNumField, mobile);
        staticWait();
        return waitUntilElementDisplayed(nxtBillingBtn, 10);
    }

    /**
     * Validate all the elements in pickp page
     *
     * @return true if all the expected elements displayed
     */
    public boolean validatePickUpPageFields() {
        return waitUntilElementDisplayed(pickUpFn, 30) &&
                waitUntilElementDisplayed(pickUpln, 30) &&
                waitUntilElementDisplayed(pickUpEmail, 30) &&
                waitUntilElementDisplayed(pickUpMobile, 30) &&
                waitUntilElementDisplayed(signUpChkbox, 30) &&
                waitUntilElementDisplayed(orderSummary, 30) &&
                waitUntilElementDisplayed(coupouns, 30) &&
                waitUntilElementDisplayed(pickupNote, 30);
    }

    /**
     * Validate values entered for pickup person are
     *
     * @return
     */
    public boolean validatepickUpDetails() {
        waitUntilElementDisplayed(pickUpFn, 30);
        return getAttributeValue(pickUpFn, "value") != "" &&
                getAttributeValue(pickUpln, "value") != "" &&
                getAttributeValue(pickUpEmail, "value") != "" &&
                getAttributeValue(pickUpMobile, "value") != "";
    }

    public boolean validateAlternateDetails() {
        return getAttributeValue(alternatefirstNameFld, "value") != "" &&
                getAttributeValue(alternateLastNameFld, "value") != "" &&
                getAttributeValue(alternateEmailFld, "value") != "";
    }

    public boolean selectAlternatePerson() {
        select(alternatePersonChkbox, alternatePersonChkboxInput);
        return waitUntilElementDisplayed(alternatefirstNameFld, 5);
    }

    /**
     * Validate all the elements for pick up person
     *
     * @return true if all the expected elements displayed
     */
    public boolean validateAlternatePersonFields() {
        scrollToBottom();
        return waitUntilElementDisplayed(alternatefirstNameFld, 5) &&
                waitUntilElementDisplayed(alternateLastNameFld, 5) &&
                waitUntilElementDisplayed(alternateEmailFld, 5) &&
                waitUntilElementDisplayed(governmentId, 5);
    }

    public boolean validateEditPickUpFields() {
        return waitUntilElementDisplayed(pickUpEditFn, 30) &&
                waitUntilElementDisplayed(pickUpEditLn, 5) &&
                waitUntilElementDisplayed(pickupNote, 5) &&
                waitUntilElementDisplayed(pickUpEditPhone, 5);
    }


    public boolean verifyPickUpUserInfo() {
        return waitUntilElementDisplayed(pickUpUserInfo);
    }

    public boolean verifyNotes() {
        return waitUntilElementDisplayed(alternatePickupNote) &&
                waitUntilElementDisplayed(governmentId);
    }

    public boolean editPickUpPerson() {
        if (waitUntilElementDisplayed(pickUpPersonEditLink, 10)) {
            click(pickUpPersonEditLink);
        }
        return waitUntilElementDisplayed(savePickUpdetails, 10);
    }

    public boolean clickBackBtn() {
        click(backupBtn);
        return waitUntilElementDisplayed(pickUpPersonEditLink);
    }

    public boolean updatePickUpdatails(String fname, String lname, String phno) {
        editPickUpPerson();
        clearAndFillText(pickUpEditFn, fname);
        clearAndFillText(pickUpEditLn, lname);
        clearAndFillText(pickUpEditPhone, phno);
        click(savePickUpdetails);
        return waitUntilElementDisplayed(pickUpPersonEditLink);
    }

    /**
     * Fill data for alternate pickup fields person
     *
     * @return true if all the expected elements displayed
     */
    public void enterAlternate_pickup(String fname, String lName, String email) {
        scrollToBottom();
        clearAndFillText(alternatefirstNameFld, fname);
        clearAndFillText(alternateLastNameFld, lName);
        clearAndFillText(alternateEmailFld, email);
    }

    public boolean unselectAlternate() {
        deselect(alternatePersonChkbox);
        return !waitUntilElementDisplayed(alternatefirstNameFld, 10);
    }


    public void enterpickUpDetails_Guest(String fName, String lName, String email, String phno) {
        waitUntilElementDisplayed(pickUpFn, 30);
        clearAndFillText(pickUpFn, fName);
        clearAndFillText(pickUpln, lName);
        clearAndFillText(pickUpEmail, email);
        clearAndFillText(pickUpMobile, phno);
        scrollDownToElement(nextBillingButton);
        jqueryClick(nextBilling);
    }

    public void enterpickUpDetails(String fName, String lName, String email, String phno) {
        waitUntilElementDisplayed(pickUpFn, 30);
        clearAndFillText(pickUpFn, fName);
        clearAndFillText(pickUpln, lName);
        clearAndFillText(pickUpEmail, email);
        clearAndFillText(pickUpMobile, phno);
    }

    /**
     * validate different page status in Pickup page
     *
     * @param orderType
     */
    public boolean validateAccordiansInPickupPage(String orderType) {
        boolean status = false;
        boolean pickup, shipping, billing, review;
        waitUntilElementDisplayed(checkoutProgressBar);
        switch (orderType.toUpperCase()) {
            case "ECOMM":
                break;
            case "BOPIS":
                pickup = accordians.get(0).getAttribute("class").equalsIgnoreCase("active");
                billing = accordians.get(1).getAttribute("class").equalsIgnoreCase("");
                review = accordians.get(2).getAttribute("class").equalsIgnoreCase("");
                status = pickup && billing && review;
                break;
            case "MIXED":
                pickup = accordians.get(0).getAttribute("class").equalsIgnoreCase("active");
                shipping = accordians.get(1).getAttribute("class").equalsIgnoreCase("");
                billing = accordians.get(2).getAttribute("class").equalsIgnoreCase("");
                review = accordians.get(3).getAttribute("class").equalsIgnoreCase("");
                status = pickup && shipping && billing && review;
                break;
        }
        return status;
    }

    /**
     * @param fName
     * @param lName
     * @param emailAdd
     */
    public void addAnAlternatePickupPerson(String fName, String lName, String emailAdd) {
        selectAlternatePerson();
        clearAndFillText(alternatefirstNameFld, fName);
        clearAndFillText(alternateLastNameFld, lName);
        clearAndFillText(alternateEmailFld, emailAdd);
    }



    public boolean verifyTransactionalSMSCheckBox() {
        return waitUntilElementDisplayed(smsCb, 10) && !isSelected(smsCb, smsCbInput);
    }

    public boolean selectTransactionalSMSCB() {
        select(smsCb, smsCbInput);
        return waitUntilElementDisplayed(smsNumberField, 10) && waitUntilElementDisplayed(smsPrivacyPolicy, 10);
    }

    public String getSmsMobileNo() {
        return getAttributeValue(smsNumberField, "value");
    }

    public void enterSmsMobileNo(String mobileNo) {
        waitUntilElementDisplayed(smsNumberField, 10);
        clearAndFillText(smsNumberField, mobileNo);
    }

    public boolean validateSmsErrorMessage(String error) {
        return getText(smsErrorMessage).contains(error);
    }

    public boolean validateSMSPrivacyPolicyLink() {
        waitUntilElementDisplayed(smsPrivacyPolicy, 10);
        click(smsPrivacyPolicy);
        switchToWindow();
        return getCurrentWindowHandles() == 2 && mobileDriver.getCurrentUrl().contains("privacyPolicy");
    }

    public boolean unselectTransactionalSMSCB() {
        unSelect(smsCb, smsCbInput);
        return !waitUntilElementDisplayed(smsNumberField, 3);
    }

}
