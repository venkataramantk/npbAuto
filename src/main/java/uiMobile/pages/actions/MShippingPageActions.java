package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MShippingPageRepo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by JKotha on 25/10/2017.
 */
public class MShippingPageActions extends MShippingPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MShippingPageActions.class);
    public String prodName;
    public String prodSize;
    public String prodColor;


    public MShippingPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean isShippingPageDisplayed(String shippingPage) {
        return getText(shippingNote).equalsIgnoreCase(shippingPage);
    }

    /**
     * Enter Shipping address for Guest User adn click on Billing address
     *
     * @param fName      guest user
     * @param lName      last user
     * @param addrLine1  address1
     * @param addrLine2  address2
     * @param city       to select
     * @param state      to state
     * @param zip        code of the state
     * @param phNum      user mobile
     * @param shipMethod shipping method
     */
    public void enterShippingDetailsShipMethodAndContinue_Reg(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        enterShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod, "");
        clickNextBillingButton();
    }

    /**
     * Fill shipping details for INT order
     *
     * @param email
     * @param fn
     * @param ln
     * @param Address
     * @param postalcode
     * @param city
     * @param phone
     */
    public void enterINTShippingDetails(String email, String fn, String ln, String Address, String postalcode, String city, String phone) {
        clearAndFillText(iemailAddressFld, email);
        clearAndFillText(ifirstNameFld, fn);
        clearAndFillText(ilastNameFld, ln);
        clearAndFillText(iaddressLine1Fld, Address);
        clearAndFillText(iPostalCodeFld, postalcode);
        clearAndFillText(icityFld, city);
        clearAndFillText(iphNumFld, phone);
    }

    public void enterINTBillingDetails(String cardNo, String exp, String cvv) {
        clearAndFillText(iCCnumber, cardNo);
        clearAndFillText(iExpDate, exp);
        clearAndFillText(iCvvCode, cvv);
    }

    public boolean enterShippingDetailsShipMethodAndContinue_Guest(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod, String email) {
        enterShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod, email);
        // clearAndFillText(emailAddressFld, randomEmail());
        return clickNextBillingButton();
    }

    public void enterpickUpDetails_Guest(String fName, String lName, String email, String phno) {
        waitUntilElementDisplayed(pickUpFn, 30);
        clearAndFillText(pickUpFn, fName);
        clearAndFillText(pickUpln, lName);
        clearAndFillText(pickUpEmail, email);
        clearAndFillText(pickUpMobile, phno);
        click(nextBillingButton);
    }

    public String getReturnCode() {
        String response = getValueOfDataElement("response_v2/checkout/getOrderDetails");
        logger.info("processOLPSResponse of updateShippingMethodSelectionResponse function: " + response);
        String returnCode = "returnCode=";
        int i = response.indexOf(returnCode) + returnCode.length();
        return response.substring(i, i + 2).trim();
    }

    public String getReturnCodeDesc() {
        String response = getValueOfDataElement("response_payment/updateShippingMethodSelection");
        logger.info("processOLPSResponse of updateShippingMethodSelectionResponse function: " + response);
        String returnCode = "returnCodeDescription=";
        int i = response.indexOf(returnCode) + returnCode.length();
        return response.substring(i, i + 8).trim();
    }

    /**
     * Validate error message for pick up person fields
     *
     * @return true if all the errors displayed
     */
    public boolean validateErrorMessagesinPickUpPage() {
        return waitUntilElementDisplayed(getInlineErrors("First Name"), 30) &&
                waitUntilElementDisplayed(getInlineErrors("Last Name"), 30) &&
                waitUntilElementDisplayed(getInlineErrors("Email Address"), 30) &&
                waitUntilElementDisplayed(getInlineErrors("Mobile Number"), 30);
    }

    /**
     * Validate error messages for alternate person
     *
     * @return true if all the errors displayed
     */
    public boolean validateErrorMessagesForAlteranteFields() {
        return waitUntilElementDisplayed(getInlineErrors("First Name"), 30) &&
                waitUntilElementDisplayed(getInlineErrors("Last Name"), 30) &&
                waitUntilElementDisplayed(getInlineErrors("Email Address"), 30);
    }

    /**
     * Return text in order summary section tile
     *
     * @return text
     */
    public String getPriceFromOrderSummaryText() {
        scrollToBottom();
        return getText(orderSummary).split(" ")[2].replace("(", "").replace(")", "");
    }

    /**
     * Enter Shipping address for Guest User
     *
     * @param fName      guest user
     * @param lName      last user
     * @param addrLine1  address1
     * @param addrLine2  address2
     * @param city       to select
     * @param state      to state
     * @param zip        code of the state
     * @param phNum      user mobile
     * @param shipMethod shipping method
     */
    public void enterShippingDetailsAndShipType(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod, String email) {
        if (waitUntilElementDisplayed(sectionTitle, 20)) {
            waitUntilElementDisplayed(firstNameFld);
            clearAndFillText(firstNameFld, fName);
            clearAndFillText(lastNameFld, lName);
            clearAndFillText(addressLine1Fld, addrLine1);
            clearAndFillText(addressLine2Fld, addrLine2);
            clearAndFillText(cityFld, city);
            selectDropDownByValue(stateDropDown, state);
            clearAndFillText(zipPostalCodeFld, zip);
            clearAndFillText(phNumFld, phNum);
            if (waitUntilElementDisplayed(emailAddressFld, 5))
                clearAndFillText(emailAddressFld, email);
        }
        addStepDescription("Shipping method to be selected on shipping page is::" + shipMethod);
        selectShippingMethodFromRadioButton(shipMethod);
        unSelect(saveToAddressBookCheckBox, saveToAddressBookCheckBoxInput);
        //staticWait();
    }

    /**
     * Enter shipping details
     *
     * @param fName     first name of the address
     * @param lName     last name of the address
     * @param addrLine1 add line1
     * @param city      to send
     * @param state     state code
     * @param zip       postal code
     * @param phNum     mobile no
     */
    public void enterShippingDetails(String fName, String lName, String addrLine1, String city, String state, String zip, String phNum) {
        if (!waitUntilElementDisplayed(editLink_ShipAddress, 20)) {
            waitUntilElementDisplayed(firstNameFld);
            clearAndFillText(firstNameFld, fName);
            clearAndFillText(lastNameFld, lName);
            clearAndFillText(addressLine1Fld, addrLine1);
            clearAndFillText(cityFld, city);
            selectDropDownByValue(stateDropDown, state);
            clearAndFillText(zipPostalCodeFld, zip);
            clearAndFillText(phNumFld, phNum);
        }
    }

    public void selectShippingMethodFromRadioButton(String shipMethod) {
        scrollToBottom();
        if (!shippingMethodRadioButtonByValue(shipMethod).isSelected()) {
            javaScriptClick(mobileDriver, shippingMethodRadioButtonByValue(shipMethod));
        }
        staticWait(2000);
    }

    /**
     * Gets all the shipping methods in shipping Page
     *
     * @return all the available shipping methods
     */
    public List<String> getAvailableShippingMethods() {
        List<String> methods = new ArrayList<>();
        for (int i = 0; i < availableMethods.size(); i++) {
            methods.add(availableMethods.get(i).getText());
        }
        return methods;
    }

    /**
     * Click in edit link for address button
     *
     * @param address1 to edit
     * @return
     */
    public boolean editAddressFromShipping(String address1) {
        waitUntilElementDisplayed(editLink_ShipAddress);
        click(editLink_ShipAddress);
        waitUntilElementDisplayed(selectThisAddress(address1));
        click(selectThisAddress(address1));
        staticWait(3000); //wait to reflect the address change in Shipping methods
        return waitUntilElementDisplayed(nextBillingButton);
    }

    public String selectShippingMethod(String name) {
        scrollDownToElement(shippingMethod(name));
        click(shippingMethod(name));
        return estimatedDays.getText();

    }

    public String getEstimatedDays() {
        waitUntilElementDisplayed(estimatedDays);
        return getText(estimatedDays);
    }

    public boolean clickNextBillingButtonOnEditShipping(MBillingPageActions billingPageActions) {
        if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
            click(addressVerificationContinue);
        }
        staticWait(3000);
        scrollToBottom();
        click(nextBillingButton);
        if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
            click(addressVerificationContinue);
        }
        waitUntilElementDisplayed(billingPageActions.creditCardRadioBox, 20);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);

    }

    /**
     * Clicks on Billing Button
     *
     * @return true if Billing page is displaye
     */
    public boolean clickNextBillingButton() {
        MBillingPageActions billingPageActions = new MBillingPageActions(mobileDriver);
        // scrollDownToElement(nextBillingButton);
        javaScriptClick(mobileDriver, nextBillingButton);
        if (waitUntilElementDisplayed(addressVerificationContinue, 30)) {
            if (waitUntilElementDisplayed(yourEnteredAddBtn, 10))
                click(yourEnteredAddBtn);
            click(addressVerificationContinue);
        }
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 30);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean validateErrOnFNameFld_SplChar(String fName, String lName, String address1, String city, String state, String zipPostal, String phoneNum, String splChar, String errFName) {
        enterShippingDetails(fName, lName, address1, city, state, zipPostal, phoneNum);
        clearAndFillText(firstNameFld, splChar);
        tabFromField(firstNameFld);
        waitUntilElementDisplayed(err_FirstName, 2);
        boolean fNameErr = getText(err_FirstName).contains(errFName);
        return fNameErr;
    }

    public boolean validateErrOnLNameFld_SplChar(String fName, String lName, String address1, String city, String state, String zipPostal, String phoneNum, String splChar, String errLName) {
        enterShippingDetails(fName, lName, address1, city, state, zipPostal, phoneNum);
        clearAndFillText(lastNameFld, splChar);
        tabFromField(lastNameFld);
        waitUntilElementDisplayed(err_LastName, 2);
        boolean lNameErr = getText(err_LastName).contains(errLName);
        return lNameErr;
    }

    public boolean validateErrOnPhoneFld_SplChar(String fName, String lName, String address1, String city, String state, String zipPostal, String phoneNum, String splChar, String errPhone) {
        enterShippingDetails(fName, lName, address1, city, state, zipPostal, phoneNum);
        clearAndFillText(phNumFld, splChar);
        tabFromField(phNumFld);
        waitUntilElementDisplayed(err_PhoneNumber, 2);
        boolean phoneErr = getText(err_PhoneNumber).contains(errPhone);
        return phoneErr;
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

    /**
     * Clicks on button Add new Address button
     *
     * @return true if Add New Address page is displayed
     */
    public boolean clickAddNewAddress() {
        waitUntilElementDisplayed(addNewAddress, 20);
        scrollToBottom();
        click(addNewAddress);
        return waitUntilElementDisplayed(firstNameFld, 10);
    }

    public boolean addNewAddressFromShippingPage_SetAsDefault(String fn, String ln, String add1, String city, String State, String zip, String phone, boolean clickOnAddNewAddr) {
        click(defaultCheckBoxTxt);
        return addNewAddressFromShippingPage(fn, ln, add1, city, State, zip, phone, clickOnAddNewAddr, false);
    }

    /**
     * Add a new address for Shipping page
     *
     * @param fn    FirstName
     * @param ln    LastName
     * @param add1  Address 1
     * @param city  city
     * @param State State
     * @param zip   Postal code
     * @param phone phone no.
     * @return true if Shipping Details Page is displayed
     */
    public boolean addNewAddressFromShippingPage(String fn, String ln, String add1, String city, String State, String zip, String phone, boolean clickOnAddNewAddr) {
        return addNewAddressFromShippingPage(fn, ln, add1, city, State, zip, phone, clickOnAddNewAddr, true);
    }

    /**
     * Add a new address for Shipping page
     *
     * @param fn    FirstName
     * @param ln    LastName
     * @param add1  Address 1
     * @param city  city
     * @param State State
     * @param zip   Postal code
     * @param phone phone no.
     * @return true if Shipping Details Page is displayed
     */
    public boolean addNewAddressFromShippingPage(String fn, String ln, String add1, String city, String State, String zip, String phone, boolean clickOnAddNewAddr, boolean unselectSave) {
        if (clickOnAddNewAddr)
            clickAddNewAddress();
        clearAndFillText(firstNameFld, fn);
        clearAndFillText(lastNameFld, ln);
        clearAndFillText(addressLine1Fld, add1);
        clearAndFillText(cityFld, city);
        selectDropDownByValue(stateDropDown, State);
        clearAndFillText(zipPostalCodeFld, zip);
        scrollDownToElement(phNumFld);
        clearAndFillText(phNumFld, phone);
        if (unselectSave)
            unSelect(saveToAddressBookCheckBox, saveToAddressBookCheckBoxInput);
        click(selectThisShippingAddButton);
       /* if (waitUntilElementDisplayed(verifyAddressPopup, 20)) {
            if (waitUntilElementDisplayed(weSuggestAddress, 20))
                click(weSuggestAddress);
            click(continueBtn);
        }*/
        if (waitUntilElementDisplayed(addressVerificationContinue, 30)) {
            if (waitUntilElementDisplayed(yourEnteredAddBtn, 20))
                click(yourEnteredInputAddBtn);
            click(continueBtn);
        }

        return waitUntilElementDisplayed(addNewAddress, 30);
    }

    public void enterZipCode(String zip) {
        clearAndFillText(zipPostalCodeFld, zip);
    }


    public boolean expandCoupons() {
        click(couponsSection);
        return waitUntilElementDisplayed(couponCodeField) && waitUntilElementDisplayed(couponsContainer);
    }

    public boolean applyACoupon(String couponCode) {
        scrollDownToElement(couponCodeFieldArea);
        if (!isDisplayed(couponCodeFld)) {
            click(couponToggleButton);
        }
        clearAndFillText(couponCodeFld, couponCode);
        click(applyButton);
        return waitUntilElementDisplayed(removeCoupon);
    }


    public boolean validateShippingFields_Guest() {
        return waitUntilElementDisplayed(firstNameFld) &&
                waitUntilElementDisplayed(lastNameFld, 4) &&
                waitUntilElementDisplayed(addressLine1Fld, 4) &&
                waitUntilElementDisplayed(addressLine2Fld, 4) &&
                waitUntilElementDisplayed(cityFld, 4) &&
                waitUntilElementDisplayed(stateDropd, 4) &&
                waitUntilElementDisplayed(zipPostalCodeFld, 4) &&
                waitUntilElementDisplayed(shipToCountryDrop, 4) &&
                waitUntilElementDisplayed(phNumFld, 4);
//                waitUntilElementDisplayed(termsAndConditionText, 4) &&
//                waitUntilElementDisplayed(signUpchkbox, 4);
    }

    public boolean validateGiftReceiptFields() {
        scrollDownToElement(giftService_checkboxInput);
        select(giftService_checkboxInput);
        return waitUntilElementDisplayed(receiptMessageBox);// && waitUntilElementDisplayed(GiftReceiptMessage);
    }

    public void unselectGiftServiceCheckbox() {
        scrollDownToElement(giftService_checkboxInput);
        unSelect(giftService_checkbox, giftService_checkboxInput);
    }

    public boolean validateGiftWrapOptions() {
        waitUntilElementDisplayed(giftwrapDetail);
        click(giftwrapDetail);
        return giftwrapContainers.size() == 3;
    }

    public boolean selectGiftService(String option) {
        waitUntilElementDisplayed(giftwrapDetail);
        click_Selenium(giftwrapDetail);
        click_Selenium(giftserviceOptions(option));
        return waitUntilElementDisplayed(giftserviceOptions(option));
    }

    public boolean verifyPickUpDetailsinShipping(String fName, String lName, String email, String phNum) {
        Boolean isFirstNameMatched = getAttributeValue(firstNameFld, "value").equals(fName);
        Boolean isLastNameMatched = getAttributeValue(lastNameFld, "value").equals(lName);
        Boolean isEmailMatched = getAttributeValue(emailAddressFld, "value").equals(email);
        Boolean isPhoneNoMatched = getAttributeValue(phNumFld, "value").equals(phNum);
        return isFirstNameMatched && isLastNameMatched && isEmailMatched && isPhoneNoMatched;
    }

    public void editAddresslink() {
        waitUntilElementDisplayed(editLink_ShipAddress);
        click(editLink_ShipAddress);
        waitUntilElementDisplayed(editLink);
        click(editLink);
        waitUntilElementDisplayed(selectThisShippingAddButton);
    }

    /**
     * Created by RichaK
     * Click on edit link on shipping info page
     */
    public void clickEditLnkOnShippingInfoPage() {
        waitUntilElementDisplayed(editLink_ShipAddress);
        javaScriptClick(mobileDriver, editLink_ShipAddress);
    }

    /**
     * Created by RichaK
     * Click on the edit link based on index on Edit shipping address page
     *
     * @param index
     */
    public void clickEditLink(int index) {
        click(editLinkList.get(index));
        waitUntilElementDisplayed(selectThisShippingAddButton);
    }

    /**
     * Created by Richa Priya
     * Click on back button displayed on shipping Page
     *
     * @return true if add to Bag page is displayed successfully
     */
    public boolean returnToBagPage(MShoppingBagPageActions shoppingBagPageActions) {
        if (!waitUntilElementDisplayed(returnBagBtn, 10)) {
            javaScriptClick(mobileDriver, backBtn);
        }
        waitUntilElementDisplayed(returnBagBtn, 30);
        javaScriptClick(mobileDriver, returnBagBtn);
        return waitUntilElementDisplayed(shoppingBagPageActions.checkoutBtn, 30);
    }

    /**
     * Created by Richa Priya
     * Click on continue button in case address pop-up is displayed
     *
     * @return true if add to Bag page is displayed successfully
     */
    public boolean clickContinueOnAddressVerificationModal() {
        if (waitUntilElementDisplayed(verifyAddressPopup, 15)) {
            click(weSuggestAddress);
            click(continueBtn);
        }
        return waitUntilElementDisplayed(addNewAddress, 15);
    }

    /**
     * Created by Raman Jha
     * <p>
     * Verify the prepopulated name and address
     */
    public boolean verifyAddressPrepop(String fNameExp, String lNameExp, String add1Exp, String cityExp, String stateExp, String zipExp) {
        waitUntilElementDisplayed(nextBillingButton, 4);
        String fullName = getText(custNameShipping);
        String shipAddr = getText(shipAddrDisplay);

        return fullName.equalsIgnoreCase(fNameExp + " " + lNameExp) && shipAddr.equalsIgnoreCase(add1Exp + "\n" + cityExp + ", " + stateExp + " " + zipExp);
    }

    /**
     * Created by Richa Priya
     * <p>
     * Clicks on Billing Button
     *
     * @return true if Billing page is displaye
     */
    public boolean clickNextBillingButtonWithEnteredAdd(MBillingPageActions mbillingPageActions) {
        scrollDownToElement(nextBillingButton);
        jqueryClick(nextBilling);
        if (waitUntilElementDisplayed(addressVerificationContinue, 30)) {
            if (waitUntilElementDisplayed(yourEnteredAddBtn, 20))
                click(yourEnteredInputAddBtn);
            click(continueBtn);
        }
        return waitUntilElementDisplayed(mbillingPageActions.nextReviewButton, 20);
    }

    /**
     * Created by Richa Priya
     * <p>
     * check if set as default checkbox is present or not
     *
     * @return true checkbox is present
     */
    public boolean defaultFieldValidationOnEditAddrs() {
        scrollDownToElement(editAddress_SetDefaultAddressCheckBoxInput);
        return isDisplayed(editAddress_SetDefaultAddressCheckBoxInput);
        //isDisplayed(defaultCheckBox);

    }

    /**
     * Created by Richa Priya
     * <p>
     * get default text fromt he edit shipping page
     *
     * @return string of default text
     */
    public String getDefaultTextOnEditAdd() {
        return getText(defaultCheckBoxTxt);
    }


    /**
     * Created by RichaK
     * Edit shipping address by passing new values for address
     *
     * @param fn
     * @param ln
     * @param add1
     * @param city
     * @param State
     * @param zip
     * @param phone
     * @return
     */
    public boolean editFromShippingPage(String fn, String ln, String add1, String city, String State, String zip, String phone) {
        clearAndFillText(firstNameFld, fn);
        clearAndFillText(lastNameFld, ln);
        clearAndFillText(addressLine1Fld, add1);
        clearAndFillText(cityFld, city);
        selectDropDownByValue(stateDropDown, State);
        clearAndFillText(zipPostalCodeFld, zip);
        scrollDownToElement(phNumFld);
        clearAndFillText(phNumFld, phone);
        click(selectThisShippingAddButton);
        if (waitUntilElementDisplayed(verifyAddressPopup, 30)) {
            click(weSuggestAddress);
            click(continueBtn);
        }
        return waitUntilElementDisplayed(addNewAddress, 30);
    }

    /**
     * This method will edit from shipping page and will also verify presence of address verification popup.
     *
     * @param fn
     * @param ln
     * @param add1
     * @param city
     * @param State
     * @param zip
     * @param phone
     * @return
     */
    public boolean editFromSPWithAVPopUp(String fn, String ln, String add1, String city, String State, String zip, String phone) {
        clearAndFillText(firstNameFld, fn);
        clearAndFillText(lastNameFld, ln);
        clearAndFillText(addressLine1Fld, add1);
        clearAndFillText(cityFld, city);
        selectDropDownByValue(stateDropDown, State);
        clearAndFillText(zipPostalCodeFld, zip);
        scrollDownToElement(phNumFld);
        clearAndFillText(phNumFld, phone);
        click(selectThisShippingAddButton);
        waitUntilElementDisplayed(addressVerificationOverlay, 30);
        waitUntilElementDisplayed(verifyAddressPopup, 30);
        if (waitUntilElementDisplayed(weSuggestAddress)) {
            click(weSuggestAddress);
            click(continueBtn);
        }
        return waitUntilElementDisplayed(addNewAddress, 30);
    }


    /**
     * Created by RichaK
     * Check if shipping address is prepopulated in form
     *
     * @param fName
     * @param lName
     * @param addLn1
     * @param addLn2
     * @param city
     * @param zip
     * @param phNum
     * @return
     */
    public boolean verifyShippingAddressPrePopulatedInForm(String fName, String lName, String addLn1, String addLn2, String city, String state, String zip, String phNum) {
        Boolean isFirstNameMatched = getAttributeValue(firstNameFld, "value").equalsIgnoreCase(fName);
        Boolean isLastNameMatched = getAttributeValue(lastNameFld, "value").equalsIgnoreCase(lName);
        Boolean isAddressLine1Matched = getAttributeValue(addressLine1Fld, "value").equalsIgnoreCase(addLn1);
        Boolean isAddressLine2Matched = getAttributeValue(addressLine2Fld, "value").equalsIgnoreCase(addLn2);
        Boolean isCityMatched = getAttributeValue(cityFld, "value").equalsIgnoreCase(city);
        Boolean iszipCodeMatched = getAttributeValue(zipPostalCodeFld, "value").equalsIgnoreCase(zip);
        Boolean isPhoneNoMatched = getAttributeValue(phNumFld, "value").equalsIgnoreCase(phNum);
        addStepDescription("state value returned from UI::" +getSelectOptions(stateDropDown));
        Boolean isStateMatched = getSelectOptions(stateDropDown).equalsIgnoreCase(state);

        return isFirstNameMatched && isLastNameMatched && isAddressLine1Matched && isAddressLine2Matched && isCityMatched && isStateMatched && iszipCodeMatched && isPhoneNoMatched;
    }

    /**
     * Created by RichaK
     * Check if shipping address is prepopulated in form for guest user
     *
     * @param fName
     * @param lName
     * @param addLn1
     * @param addLn2
     * @param city
     * @param zip
     * @param phNum
     * @return
     */
    public boolean verifyShippingAddressPrePopulatedInForm_Guest(String fName, String lName, String addLn1, String addLn2, String city, String state, String zip, String phNum, String email) {
        boolean val = verifyShippingAddressPrePopulatedInForm(fName, lName, addLn1, addLn2, city, state, zip, phNum);
        Boolean isEmailMatched = getAttributeValue(emailAddressFld, "value").equals(email);
        return val && isEmailMatched;
    }

    /**
     * Created by RichaK
     * select/unselect 'Set as default address' checkbox and update address/
     *
     * @param isSelect
     */
    public void selectSetAsDefaultCheckboxAndUpdateAddress(boolean isSelect) {
        if (isSelect) {
            if (defaultCheckBoxUnChecked != null) {
                click(defaultCheckBoxTxt);
            }
        } else {
            if (defaultCheckBox != null)
                click(defaultCheckBoxTxt);
        }

        click(selectThisShippingAddButton);
        if (waitUntilElementDisplayed(verifyAddressPopup, 30)) {
            if (waitUntilElementDisplayed(yourEnteredAddBtn, 10))
                click(yourEnteredAddBtn);
            click(continueBtn);
        }
        waitUntilElementDisplayed(addNewAddress, 30);
    }

    /**
     * Created by Raman Jha
     * <p>
     * Clicks Add new address from overlay
     *
     * @return true if new address add page is displayed
     */
    public boolean clickAddNewAddfrmOverlay() {
        waitUntilElementDisplayed(editLink_ShipAddress);
        click(editLink_ShipAddress);
        waitUntilElementDisplayed(addNewAddfrmOverlay);
        scrollToBottom();
        click(addNewAddfrmOverlay);
        return waitUntilElementDisplayed(firstNameFld, 10);
    }

    /**
     * Created by Raman Jha
     *
     * @return true if all the fields in new address is blank by default
     */
    public boolean validateAddNewAddressPage() {
        waitUntilElementDisplayed(firstNameFld, 10);
        return getAttributeValue(firstNameFld, "value").equals("") &&
                getAttributeValue(lastNameFld, "value").equals("") &&
                getAttributeValue(addressLine1Fld, "value").equals("") &&
                getAttributeValue(addressLine2Fld, "value").equals("") &&
                getAttributeValue(cityFld, "value").equals("") &&
                getAttributeValue(zipPostalCodeFld, "value").equals("") &&
                getAttributeValue(phNumFld, "value").equals("") &&
                getAttributeValue(saveToAddressBookCheckBoxInput, "value").equalsIgnoreCase("true") &&
                getAttributeValue(saveDefaultCheckbox, "value").equalsIgnoreCase("");
    }

    /**
     * Created by Richa Priya
     * wait for the address verficatin overlay and click on you entered and continue button
     *
     * @return true if success
     */
    public boolean selectThisShippingAdd() {
        javaScriptClick(mobileDriver, selectThisShippingAddButton);
        if (waitUntilElementDisplayed(verifyAddressPopup, 30)) {
            if (waitUntilElementDisplayed(yourEnteredAddBtn, 10))
                click(yourEnteredAddBtn);
            click(continueBtn);
        }
        staticWait(3000);
        return waitUntilElementDisplayed(shippingNote, 20);
    }

    public void enterAddressLine1(String addressLine1) {
        waitUntilElementDisplayed(addressLine1Fld);
        clearAndFillText(addressLine1Fld, addressLine1);
    }

    public String getGoogleAddressLookupVal(int index) {
        waitUntilElementDisplayed(googleAddressLookUpItem.get(index));
        return getText(googleAddressLookUpItem.get(index));
    }

    public void clickGoogleAddressLookupItemByIndex(int index) {
        click(googleAddressLookUpItem.get(index));
        staticWait(5000);
    }

    /**
     * Updated by Richa Priya
     * Added OR condition in case city comes combined with address line
     */

    public boolean validateAddressIsPopulatedFromLookUp(String googleAddressLookupVal) {
        String[] values = googleAddressLookupVal.split(", ");
        String addressLine1 = values[0];
        addressLine1 = addressLine1.replace(" ", "");
        String city = values[1];
        String state = values[2];

        String actualDisplayedAddLine1 = getAttributeValue(addressLine1Fld, "value");
        actualDisplayedAddLine1 = actualDisplayedAddLine1.replace(" ", "");
        addStepDescription("Displayed value::" +actualDisplayedAddLine1);
        String actualDisplayedCity = getAttributeValue(cityFld, "value");
        String actualDisplayedState = getSelectOptions(stateDropDown);

        return (addressLine1.equals(actualDisplayedAddLine1) || addressLine1.contains(actualDisplayedAddLine1)) && (city.equals(actualDisplayedCity) || addressLine1.contains(actualDisplayedCity)) && state.equals(actualDisplayedState) && !(getAttributeValue(zipPostalCodeFld, "value").equals(""));
    }

    /**
     * Created by Richa Priya
     *
     * @param fieldVal   which need to be edited for eg - firstName field
     * @param updatedVal new value which needs to be inserted in place of existing one
     * @return true if success
     */
    public boolean UpdateFieldOnEditScreen(String fieldVal, String updatedVal) {
        boolean flag = false;
        switch (fieldVal) {

            case "FNAME":
                clearAndFillText(firstNameFld, updatedVal);
                flag = selectThisShippingAdd();
                break;

            case "LNAME":
                clearAndFillText(lastNameFld, updatedVal);
                flag = selectThisShippingAdd();
                break;

            case "ADDRESSLINE1":
                clearAndFillText(addressLine1Fld, updatedVal);
                flag = selectThisShippingAdd();
                break;

            case "ADDRESSLINE2":
                clearAndFillText(addressLine2Fld, updatedVal);
                flag = selectThisShippingAdd();
                break;

            case "CITY":
                clearAndFillText(cityFld, updatedVal);
                flag = selectThisShippingAdd();
                break;

            case "ZIP":
                clearAndFillText(zipPostalCodeFld, updatedVal);
                flag = selectThisShippingAdd();
                break;

            case "PHNUM":
                scrollDownToElement(phNumFld);
                clearAndFillText(phNumFld, updatedVal);
                flag = selectThisShippingAdd();
                break;

            case "STATE":
            case "PROVINCE":
                selectDropDownByValue(stateDropDown, updatedVal);
                flag = selectThisShippingAdd();
                break;

            default:
        }
        return flag;
    }

    /**
     * Created by Richa Priya
     * Enter the details on pick up page and click on next shipping button
     *
     * @param fName first name
     * @param lName last name
     * @param email email id of the user
     * @param phno  phone no of the user
     * @return true if shipping button is clicked successfully
     */
    public void enterpickUpDetails_GuestForMixedBag(String fName, String lName, String email, String phno) {
        waitUntilElementDisplayed(pickUpFn, 30);
        clearAndFillText(pickUpFn, fName);
        clearAndFillText(pickUpln, lName);
        clearAndFillText(pickUpEmail, email);
        clearAndFillText(pickUpMobile, phno);
        click(nextShippingButton);
    }


    /**
     * To Click PayPal Button for int checkout
     *
     * @param mPayPalPageActions
     * @return
     */
    public boolean clickPayPayl_int(MPayPalPageActions mPayPalPageActions) {
        waitUntilElementDisplayed(intPayPalBtn, 120);
        click(intPayPalBtn);
        return waitUntilElementDisplayed(mPayPalPageActions.loginButton);
    }

    public boolean validateCurrentCountry(String country) {
        return getSelectOptions(newAddress_CountryFld_disabled).equalsIgnoreCase(country);
    }

    /**
     * validate different page status in shipping page
     *
     * @param orderType
     */
    public boolean validateAccordiansInShippingPage(String orderType) {
        boolean status = false;
        boolean pickup, shipping, billing, review;
        waitUntilElementDisplayed(checkoutProgressBar);
        switch (orderType.toUpperCase()) {
            case "ECOMM":
                shipping = accordians.get(0).getAttribute("class").equalsIgnoreCase("active");
                billing = accordians.get(1).getAttribute("class").equalsIgnoreCase("");
                review = accordians.get(2).getAttribute("class").equalsIgnoreCase("");
                status = shipping && billing && review;
                break;
            case "BOPIS":
                break;
            case "MIXED":
                pickup = accordians.get(0).getAttribute("class").equalsIgnoreCase("completed");
                shipping = accordians.get(1).getAttribute("class").equalsIgnoreCase("active");
                billing = accordians.get(2).getAttribute("class").equalsIgnoreCase("");
                review = accordians.get(3).getAttribute("class").equalsIgnoreCase("");
                status = pickup && shipping && billing && review;
                break;
        }
        return status;
    }

    public boolean validateStandardFreeBydefault() {
        waitUntilElementDisplayed(standardFree);
        String Class = standardFree.getAttribute("class");

        return Class.contains("checked");
    }

    /**
     * Created by Richa Priya
     * Clicks on back button on add new address form
     *
     * @return true redirected to shipping page
     */
    public boolean clickBackButtonOnAddAddressForm() {
        waitUntilElementDisplayed(backBtnAddressForm);
        click(backBtnAddressForm);
        return waitUntilElementDisplayed(editLink_ShipAddress, 30);
    }


    /**
     * Created by Richa Priya
     * Enter Shipping address for Guest User
     *
     * @param fName     guest user
     * @param lName     last user
     * @param addrLine1 address1
     * @param city      to select
     * @param state     to state
     * @param zip       code of the state
     * @param phNum     user mobile
     * @param email     user email id
     */
    public void enterShippingDetailsForGuest(String fName, String lName, String addrLine1, String city, String state, String zip, String phNum, String email) {
        if (waitUntilElementDisplayed(sectionTitle, 20)) {
            waitUntilElementDisplayed(firstNameFld);
            clearAndFillText(firstNameFld, fName);
            clearAndFillText(lastNameFld, lName);
            clearAndFillText(addressLine1Fld, addrLine1);
            clearAndFillText(cityFld, city);
            selectDropDownByValue(stateDropDown, state);
            clearAndFillText(zipPostalCodeFld, zip);
            clearAndFillText(phNumFld, phNum);
            if (waitUntilElementDisplayed(emailAddressFld, 5))
                clearAndFillText(emailAddressFld, email);
        }
        if (waitUntilElementDisplayed(verifyAddressPopup, 30)) {
            if (waitUntilElementDisplayed(weSuggestAddress, 20))
                click(weSuggestAddress);
            click(continueBtn);
        }
    }


    /**
     * Created by Richa Priya
     * Enter Shipping address for Guest User
     *
     * @param fName     guest user
     * @param lName     last user
     * @param addrLine1 address1
     * @param city      to select
     * @param state     to state
     * @param zip       code of the state
     * @param phNum     user mobile
     * @param email     user email id
     */
    public boolean enterShippingDetailsShipMethodAndContinue_Guest_Reg(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod, String email) {
        if (isDisplayed(editLink_ShipAddress)) {
            return clickNextBillingButton();
        } else {
            enterShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod, email);
            // clearAndFillText(emailAddressFld, randomEmail());
            return clickNextBillingButton();
        }
    }

    public void editEmailAdd_Guest(String emailAdd) {
        waitUntilElementDisplayed(emailAddressFld);
        clearAndFillText(emailAddressFld, emailAdd);
    }


    /**
     * Created by Richa Priya
     * return true if title is EXPRESS CHECK OUT
     *
     * @return
     */
    public boolean verifyExpressCheckoutTitle() {
        return getText(expressCheckout).equals("EXPRESS CHECK OUT");
    }

    /**
     * Created by Richa Priya
     *
     * @return default value of state field
     */
    public boolean validateDefaultValueOfStateField() {
        return getSelectOptions(stateDropDown).equalsIgnoreCase("Select");
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

    /**
     * Created by Richa Priya
     *
     * @return click on edit button of default address from the list
     */
    public void editDefaultAddressLinkFromList() {
        waitUntilElementDisplayed(editLink_ShipAddress);
        javaScriptClick(mobileDriver, editLink_ShipAddress);
        for (int i = 0; i < editLinkList.size(); i++) {
            if (nameOnAddressDetails.getText().equalsIgnoreCase("default"))
                javaScriptClick(mobileDriver, editLinkList.get(i));
            waitUntilElementDisplayed(selectThisShippingAddButton);
        }

    }


}