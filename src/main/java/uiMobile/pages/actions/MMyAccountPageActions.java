package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MMyAccountPageRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jkotha on 11/14/2017.
 */
public class MMyAccountPageActions extends MMyAccountPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MMyAccountPageActions.class);

    public MMyAccountPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Add a new Shipping Address from My Account Page
     *
     * @param fName    of the Address
     * @param lName    of the address
     * @param address1 Street Name
     * @param city     city of the address
     * @param state    State of the address
     * @param zipCode  Zipcode f the address
     * @param phoneNo  phone of delivery
     */
    public void addNewShippingAddress(String fName, String lName, String address1, String city, String state, String zipCode, String phoneNo, PanCakePageActions panCakePageActions) {
        if (!waitUntilElementDisplayed(btn_AddNewAddress, 10)) {
            panCakePageActions.navigateToMenu("MYACCOUNT");
            clickSection("ADDRESSBOOK");
        }
        click(btn_AddNewAddress);
        waitUntilElementDisplayed(newAddress_FirstName, 10);
        clearAndFillText(newAddress_FirstName, fName);
        clearAndFillText(newAddress_LastName, lName);
        clearAndFillText(newAddress_AddressLine1, address1);
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        clearAndFillText(newAddress_ZipCode, zipCode);
        clearAndFillText(newAddress_PhoneNumeber, phoneNo);

        if (waitUntilElementDisplayed(newAddress_SetDefaultAddressCheckBox)) {
            click(newAddress_SetDefaultAddressCheckBox);
        }
        click(newAddress_AddNewAddressBtn);

        if (waitUntilElementDisplayed(addressVerificationContinue, 30)) {
            if (waitUntilElementDisplayed(yourEnteredAddBtn, 10))
                click(yourEnteredInputAddBtn);
            click(addressVerificationContinue);
        }
        waitUntilElementDisplayed(successMessage, 20);
    }

    public String getDefaultShippingAddress() {
        waitUntilElementDisplayed(defaultshipaddress, 10);
        return getText(defaultshipaddress);
    }

    public String getDefaultcardDetails() {
        waitUntilElementDisplayed(defaultcard);
        return getText(defaultcard);
    }

    /**
     * updated by Richa Priya
     * Delete all address available in Address book
     * added a empty address condtion to return a boolean value for better assertion
     */
    public boolean deleteAllAddress() {
        if (waitUntilElementsAreDisplayed(removeAddress, 10)) {
            for (WebElement remove : removeAddress) {
                click(remove);
                click(okDeleteAdd);
                staticWait(5000);
            }
        }
        return waitUntilElementDisplayed(emptyAddAddressBtn, 10);
    }

    /**
     * Updated by Richa Priya
     * Delete all address available in Address book
     */
    public boolean deleteAllcards() {
        if (waitUntilElementsAreDisplayed(removeCards, 10)) {
            for (WebElement remove : removeAddress) {
                click(remove);
                click(okDeleteAdd);
                waitUntilElementDisplayed(successMessage, 40);
            }
        }
        return waitUntilElementDisplayed(emptyAddCardBtn, 10);
    }

    //Delete address by position no
    public void deleteAddressByPosition(int position) {
        if (waitUntilElementsDisplayed(removeAddress)) {
            click(removeAddress.get(position - 1));
        }
    }

    /**
     * Delete a credit card from My Account page
     *
     * @param lastfourdigitsofCC GC last 4 digits
     */
    public void deleteACreditCard(String lastfourdigitsofCC) {
        click(removeCard(lastfourdigitsofCC));
        click(okDeleteAdd);
        waitUntilElementDisplayed(successMessage, 40);
    }

    public void addACreditCard(String cardNo, String month, String year, Boolean billing, String fName, String lName, String address1, String city, String state, String zipCode, PanCakePageActions panCakePageActions) {

        if (!waitUntilElementDisplayed(addACreditCardBtn, 10)) {
            panCakePageActions.navigateToMenu("MYACCOUNT");
            clickSection("GIFTCARDS");
        }
        javaScriptClick(mobileDriver, addACreditCardBtn);
        waitUntilElementDisplayed(addPayment_CardNumber);
        clearAndFillText(addPayment_CardNumber, cardNo);
        selectDropDownByValue(addPayment_expmonth, month);
        selectDropDownByVisibleText(addPayment_expyr, year);
        if (billing) {
            if (isDisplayed(newAddress_FirstName)) {
                fillBillingAddress(fName, lName, address1, city, state, zipCode);
            }
        }
        click(btnAddCard);
        waitUntilElementDisplayed(successMessage, 20);
    }

    public void addACreditCard(String cardNo, String month, String year) {
        javaScriptClick(mobileDriver, addACreditCardBtn);
        waitUntilElementDisplayed(addPayment_CardNumber);
        clearAndFillText(addPayment_CardNumber, cardNo);
        selectDropDownByValue(addPayment_expmonth, month);
        selectDropDownByVisibleText(addPayment_expyr, year);
        javaScriptClick(mobileDriver, btnAddCard);
        waitUntilElementDisplayed(successMessage, 20);
    }

    public boolean addAPLCCCard(String cardNo) {
        javaScriptClick(mobileDriver, addACreditCardBtn);
        waitUntilElementDisplayed(addPayment_CardNumber);
        clearAndFillText(addPayment_CardNumber, cardNo);
        javaScriptClick(mobileDriver, btnAddCard);
        return waitUntilElementDisplayed(successMessage, 20);
    }

    /**
     * add a gift card from my account -> payments
     *
     * @param cardNo cardno of giftcard
     * @param pin    Pin value to enter
     */
    public void addAGiftCard(String cardNo, String pin) {
        scrollDownToElement(addAGiftCardBtn);
        javaScriptClick(mobileDriver, addAGiftCardBtn);
        waitUntilElementDisplayed(addPayment_CardNumber);
        clearAndFillText(addPayment_CardNumber, cardNo);
        clearAndFillText(pinField, pin);
        javaScriptClick(mobileDriver, giftCardSaveBtn);
        waitUntilElementDisplayed(successMessage, 20);
    }

    /**
     * Select the captcha present in gift card page
     */
    public void selectCaptcha() {
        switchToFrame(captchFrameLoc);
        click(captchaCheckbox);
        staticWait(10000);
        switchToDefaultFrame();
    }

    /**
     * to check gift card balance
     *
     * @param lastfourdigits of the card to check balance
     */
    public void checkGiftCardBalance(String lastfourdigits) {
        switchToFrame(cardCaptch(lastfourdigits));
        click(captchaCheckbox);
    }

    public void fillBillingAddress(String fName, String lName, String address1, String city, String state, String zipCode) {
        waitUntilElementDisplayed(newAddress_FirstName, 10);
        clearAndFillText(newAddress_FirstName, fName);
        clearAndFillText(newAddress_LastName, lName);
        clearAndFillText(newAddress_AddressLine1, address1);
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        clearAndFillText(newAddress_ZipCode, zipCode);
    }

    public boolean verifyDefaultCreditCard() {
        staticWait(3000);
        click(lnk_PaymentGC);
        staticWait(3000);
        while (waitUntilElementDisplayed(paymentInfo_RemoveLink, 10)) {
            click(paymentInfo_RemoveLink);
            staticWait(9000);
            click(btnOverlayConfirmRemove);
            staticWait(5000);
        }

        return waitUntilElementDisplayed(btn_AddNewCard, 10);
    }

    public boolean validateProfileInfoPage(String email, String fn, String ln, String mobile, String store) {
        boolean all = waitUntilElementDisplayed(emailID, 10) &&
                waitUntilElementDisplayed(addressBlock, 10) &&
                waitUntilElementDisplayed(addressBookText, 3) &&
                waitUntilElementDisplayed(changePwdBtn, 3) &&
                waitUntilElementDisplayed(birthdaysavingsbtn, 3) &&
                waitUntilElementDisplayed(defautStoreTitle, 3) &&
                waitUntilElementDisplayed(findStoreBtn, 3) &&
                waitUntilElementDisplayed(completeAddressBtn, 4);
        if (store.equalsIgnoreCase("US")) {
            return all && waitUntilElementDisplayed(myPlaceRewardsLabel);
        }
        return all;
    }


    public boolean valdaiteUserInfoInEditMode(String email, String fn, String ln, String mobile) {
        return getAttributeValue(emailIdField, "value").equalsIgnoreCase(email) &&
                getAttributeValue(firstNameField, "value").equalsIgnoreCase(fn) &&
                getAttributeValue(lastNameField, "value").equalsIgnoreCase(ln) &&
                getAttributeValue(newAddress_PhoneNumeber, "value").equalsIgnoreCase(mobile);
    }

    public boolean validateChagnePwdFields() {
        return waitUntilElementDisplayed(passwordInstructions, 010) &&
                waitUntilElementDisplayed(currentPwdField, 5) &&
                waitUntilElementDisplayed(currentPwdshowHideLink, 5) &&
                waitUntilElementDisplayed(confirmPwdField, 5) &&
                waitUntilElementDisplayed(confirmPwdshowHideLink, 5) &&
                waitUntilElementDisplayed(newPwdField, 5) &&
                waitUntilElementDisplayed(confirmPwdshowHideLink, 5);
    }

    public String getPoints() {
        String points = getText(totalPoints);
        String[] tokens = points.split(":");
        points = tokens[1].trim();
        return points;
    }

    public boolean addAChild(String name, String month, String year, String sex, String fn, String ln) {
        clearAndFillText(childName, name);
        selectDropDownByVisibleText(birthMth, month);
        selectDropDownByVisibleText(birthYear, year);
        selectDropDownByVisibleText(gender, sex);
        clearAndFillText(childFn, fn);
        clearAndFillText(childLn, ln);
        select(addAChildTAndCChecbox, addAChildTAndCChecboxInput);
        click(addChildbtn);
        return waitUntilElementDisplayed(successMessage, 20);
    }

    public boolean addNewShippingAddress(String fname, String lname, String add1, String city, String state, String zip, String country, String phone, PanCakePageActions panCakePageActions) {
        if (!waitUntilElementDisplayed(btn_AddNewAddress, 20)) {
            panCakePageActions.navigateToMenu("MYACCOUNT");
            clickSection("ADDRESSBOOK");
        }
        javaScriptClick(mobileDriver, btn_AddNewAddress);
        waitUntilElementDisplayed(newAddress_FirstName, 30);
        clearAndFillText(newAddress_FirstName, fname);
        clearAndFillText(newAddress_LastName, lname);
        clearAndFillText(newAddress_AddressLine1, add1);
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        selectDropDownByVisibleText(newAddress_CountryFld, country);
        clearAndFillText(newAddress_ZipCode, zip);
        clearAndFillText(newAddress_PhoneNumeber, phone);
        click(newAddress_AddNewAddressBtn);
        if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
            click(addressVerificationContinue);
        }
        return waitUntilElementDisplayed(successMessage);
    }

    /**
     * Created by RichaK
     * This method will edit and update shipping address.
     *
     * @param fname
     * @param lname
     * @param add1
     * @param city
     * @param state
     * @param zip
     * @param country
     * @param phone
     * @return
     */
    public boolean editShippingAddress(String fname, String lname, String add1, String city, String state, String zip, String country, String phone) {
        waitUntilElementDisplayed(newAddress_FirstName, 30);
        clearAndFillText(newAddress_FirstName, fname);
        clearAndFillText(newAddress_LastName, lname);
        clearAndFillText(newAddress_AddressLine1, add1);
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        selectDropDownByVisibleText(newAddress_CountryFld, country);
        clearAndFillText(newAddress_ZipCode, zip);
        clearAndFillText(newAddress_PhoneNumeber, phone);
        click(updateAddressBtn);
        if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
            click(addressVerificationContinue);
        }
        return waitUntilElementDisplayed(successMessage);
    }

    /**
     * Validate all the fields in shipping address page in edit mode
     *
     * @return
     */
    public boolean verifyAlltheFieldsInEditShippingPage() {
        return isDisplayed(editshippingTitle) &&
                isDisplayed(newAddress_FirstName) &&
                isDisplayed(newAddress_LastName) &&
                isDisplayed(newAddress_AddressLine1) &&
                isDisplayed(newAddress_AddressLine2) &&
                isDisplayed(newAddress_City) &&
                isDisplayed(newAddress_Statedd) &&
                isDisplayed(newAddress_ZipCode) &&
                isDisplayed(newAddress_PhoneNumeber) &&
                isDisplayed(newAddress_Countrydd) &&
                isDisplayed(newAddressCancelBtn) &&
                isDisplayed(newAddress_SetDefaultAddressCheckBox);
    }

    /**
     * Validate all fields in Address book
     *
     * @return
     */
    public boolean verifyAllFieldsinAddNewAddress() {
        return isDisplayed(newAddress_FirstName) &&
                isDisplayed(newAddress_LastName) &&
                isDisplayed(newAddress_AddressLine1) &&
                isDisplayed(newAddress_AddressLine2) &&
                isDisplayed(newAddress_City) &&
                isDisplayed(newAddress_Statedd) &&
                isDisplayed(newAddress_ZipCode) &&
                isDisplayed(newAddress_PhoneNumeber) &&
                isDisplayed(newAddress_Countrydd) &&
                isDisplayed(newAddress_AddNewAddressBtn) &&
                isDisplayed(newAddressCancelBtn) &&
                isDisplayed(newAddress_SetDefaultAddressCheckBox);
    }

    /**
     * Change default address
     *
     * @param address1 to select
     */
    public void changeDefaultAddress(String address1) {
        waitUntilElementDisplayed(makeDefaultLink(address1));
        click(makeDefaultLink(address1));
        staticWait(3000);//to reflect changes
    }

    public boolean validateUSStates() {
        waitUntilElementDisplayed(newAddress_State);
        List<String> app = new ArrayList<>();
        List<WebElement> states = newAddress_State.findElements(By.tagName("option"));
        List<String> places = Arrays.asList("AL", "AK", "AS", "AZ", "AR", "AA", "AE", "AP", "CA", "CO", "CT", "DE", "DC", "FM", "FL", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OR", "OK", "OR", "PW", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
                "VI", "VA", "WA", "WV", "WI", "WY");
        for (WebElement state : states) {
            app.add(state.getText());
        }

        return app.equals(places);
    }

    public boolean validateCurrentCountry(String country) {
        waitUntilElementDisplayed(newAddress_Countrydd);
        return getSelectOptions(newAddress_CountryFld).equalsIgnoreCase(country);
    }

    public boolean verifyAllFieldsinAddAddress() {
        return waitUntilElementDisplayed(newAddress_FirstName) &&
                waitUntilElementDisplayed(newAddress_LastName) &&
                waitUntilElementDisplayed(newAddress_AddressLine1) &&
                waitUntilElementDisplayed(newAddress_FirstName) &&
                waitUntilElementDisplayed(newAddress_FirstName) &&
                waitUntilElementDisplayed(newAddress_FirstName) &&
                waitUntilElementDisplayed(newAddress_FirstName) &&
                waitUntilElementDisplayed(newAddress_FirstName) &&
                waitUntilElementDisplayed(newAddress_FirstName) &&
                waitUntilElementDisplayed(newAddress_FirstName) &&
                waitUntilElementDisplayed(newAddress_FirstName) &&
                waitUntilElementDisplayed(newAddress_FirstName);
    }

    public boolean verifyPickupStoreDetails() {
        return waitUntilElementDisplayed(storeName) &&
                waitUntilElementDisplayed(storeAddress) &&
                waitUntilElementDisplayed(storePhoneNo) &&
                waitUntilElementDisplayed(getDirectionsLink);
    }

    public boolean validateAllColumns(String orderNo) {
        return waitUntilElementDisplayed(getOrderDate(orderNo)) &&
                waitUntilElementDisplayed(getOrderNo(orderNo)) &&
                waitUntilElementDisplayed(getOrderTotal(orderNo)) &&
                waitUntilElementDisplayed(getOrderType(orderNo)) &&
                waitUntilElementDisplayed(getOrderStatus(orderNo));
    }

    /**
     * To Enter associate id details
     *
     * @param fName: firstname
     * @param lName: lastName
     * @param id:    associate id
     */
    public boolean enterAssociateDetails(String fName, String lName, String id) {
        if (waitUntilElementDisplayed(editButton, 5)) {
            javaScriptClick(mobileDriver, editButton);
        }
        clearAndFillText(firstNameField, fName);
        clearAndFillText(lastNameField, lName);
        if (!isSelected(tcpEmpChkBox, tcpEmpChkBoxInput))
            select(tcpEmpChkBox, tcpEmpChkBoxInput);
        waitUntilElementDisplayed(associateIDField, 10);
        clearAndFillText(associateIDField, String.valueOf(id));
        staticWait(5000);
        return getAttributeValue(associateIDField, "value").equalsIgnoreCase(id);
    }

    public boolean clickSaveButton() {
        click(saveButton);
        if (waitUntilElementDisplayed(continueBtn, 10))
            click(continueBtn);
        return waitUntilElementDisplayed(successMessage, 10);
    }

    public void enterUserDetailsandSave(String email, String fName, String lName, String phoneno) {
        if (waitUntilElementDisplayed(editButton, 5))
            click(editButton);

        clearAndFillText(emailIdField, email);
        clearAndFillText(firstNameField, fName);
        clearAndFillText(lastNameField, lName);
        clearAndFillText(newAddress_PhoneNumeber, phoneno);

        click(saveButton);
    }

    /**
     * Validate all the fields, buttons and links in add a child page
     *
     * @return
     */
    public boolean validateAddAChildFields() {
        return waitUntilElementDisplayed(childFn, 10) &&
                waitUntilElementDisplayed(childLn, 5) &&
                waitUntilElementDisplayed(timeStamplbl, 5) &&
                waitUntilElementDisplayed(addAChildTAndCChecbox, 5) &&
                waitUntilElementDisplayed(faqLink, 5) &&
                waitUntilElementDisplayed(privacyLink, 5) &&
                waitUntilElementDisplayed(birthCancelBtn, 5) &&
                waitUntilElementDisplayed(addChildbtn, 5) &&
                waitUntilElementDisplayed(childName, 3) &&
                waitUntilElementDisplayed(monthDrpDownDisplay, 3) &&
                waitUntilElementDisplayed(yearDrpDownDisplay, 3) &&
                waitUntilElementDisplayed(genderDrpDownDisplay, 3) &&
                totalYears.size() == 18;
    }

    /**
     * @return true if the action success
     * @Author: JK
     * Clicks on cancel button in add a child page
     */
    public boolean clickCancelInAddaChildPage() {
        if (waitUntilElementDisplayed(birthCancelBtn, 10)) {
            click(birthCancelBtn);
        }
        return addAChildBtns.size() >= 1;
    }

    /**
     * @return true child adding page is displayed
     * @Author: JK
     * clicks on add a child button
     */
    public boolean clickAddAChildBtn() {
        if (waitUntilElementDisplayed(scrollToTop, 10)) {
            click(scrollToTop);
        }
        waitUntilElementDisplayed(addAChildBtn, 10);
        click(addAChildBtn);
        return waitUntilElementDisplayed(childFn, 10);
    }

    /**
     * remove a child
     *
     * @param name: to remove the child
     * @return true if the child is removed
     */
    public boolean removeAChild(String name) {
        waitUntilElementDisplayed(removeChild(name), 10);
        click(removeChild(name));
        waitUntilElementDisplayed(yesRemoveBtn, 10);
        click(yesRemoveBtn);
        return waitUntilElementDisplayed(successMessage, 10);
    }

    /**
     * @return true if Shipping page is displayed
     * @Author:JK Clicks on edit Address link in AVS form
     */
    public boolean editAddressFromAVS() {
        waitUntilElementDisplayed(addressVerificationContinue, 30);
        waitUntilElementDisplayed(avsEditLink, 5);
        click(avsEditLink);
        return waitUntilElementDisplayed(newAddress_AddNewAddressBtn, 10);
    }

    public boolean clickAddAddressBtn() {
        waitUntilElementDisplayed(newAddress_AddNewAddressBtn, 10);
        click(newAddress_AddNewAddressBtn);
        if (waitUntilElementDisplayed(addressVerificationContinue, 30)) {
            click(addressVerificationContinue);
        }
        return waitUntilElementDisplayed(successMessage);
    }

    /* Created by Richa Priya
     **/
    public boolean validateSavedAddressFromShipping(String address) {

        boolean flag = false;
        waitUntilElementDisplayed(addressBlock);
        for (int i = 0; i < savedAddress_MyAccount.size(); i++) {
            String addressInMyAccount = getText(savedAddress_MyAccount.get(i));
            if (addressInMyAccount.equalsIgnoreCase(address)) {
                addStepDescription(address + "is present in my account address book");
                flag = true;
                break;
            }
        }

        return flag;
    }

    /**
     * Created by Richa Priya
     * Add a new Shipping Address from My Account Page
     *
     * @param fName of the Address
     * @param lName of the address
     * @param add1  Street Name
     * @param city  city of the address
     * @param state State of the address
     * @param zip   Zipcode f the address
     * @param phone phone of delivery
     */
    public void addNewShippingAddressFromMyAcc(String fName, String lName, String add1, String add2, String city, String state, String zip, String phone) {
        waitUntilElementDisplayed(btn_AddNewAddress, 20);
        click(btn_AddNewAddress);
        waitUntilElementDisplayed(newAddress_FirstName, 30);
        clearAndFillText(newAddress_FirstName, fName);
        clearAndFillText(newAddress_LastName, lName);
        clearAndFillText(newAddress_AddressLine1, add1);
        clearAndFillText(newAddress_AddressLine2, add2);
        clearAndFillText(newAddress_City, city);
        selectDropDownByVisibleText(newAddress_State, state);
        clearAndFillText(newAddress_ZipCode, zip);
        clearAndFillText(newAddress_PhoneNumeber, phone);
        if (waitUntilElementDisplayed(newAddress_SetDefaultAddressCheckBox)) {
            javaScriptClick(mobileDriver, newAddress_SetDefaultAddressCheckBox);
        }
        click(newAddress_AddNewAddressBtn);
        staticWait(5000);
        if (waitUntilElementDisplayed(addressVerificationContinue, 10)) {
            click(addressVerificationContinue);
            staticWait(5000);
        }
        waitUntilElementDisplayed(successMessage, 20);
    }

    /**
     * JK
     * Select section from My Account page
     *
     * @param section name to select
     */
    public void clickSection(String section) {
        switch (section.toUpperCase()) {
            case "ADDRESSBOOK":
                javaScriptClick(mobileDriver, addressbook); //click() is executing, but the action is not performing
                break;
            case "GIFTCARDS":
                javaScriptClick(mobileDriver, giftcards);
                break;
            case "ORDERS":
                javaScriptClick(mobileDriver, ordersLnk);
                break;
            case "PROFILE":
                javaScriptClick(mobileDriver, profileInfoLnk);
                break;
            case "REWARDS":
                javaScriptClick(mobileDriver, rewardsLnk);
                staticWait(2000);
                break;
            default:
        }
    }
    

    /**
     * Created by Richak
     * This method will click on the toggle which comes on order details page and show the order items.
     */
    public void clickOrderListContainer() {
        javaScriptClick(mobileDriver, orderListContainerToggle);
    }

    /*
     * Raman Jha
     * Check the profileinfopage in my account
     * @return true the profile information is matching with the details entered on billing
     */
    public boolean verifyProfileInfo(String fNameExp, String lNameExp, String email, String phnNum) {
        waitUntilElementDisplayed(profileInfoText);
        String fullName = getText(name);
        String emailPhone = getText(phoneNoLbl);
        return fullName.equalsIgnoreCase(fNameExp + " " + lNameExp) && emailPhone.equalsIgnoreCase(email + "\n" + phnNum);
    }
    
    public boolean applyRewardsAndCoupons() {
        waitUntilElementDisplayed(viewRewardsLink, 10);
        isDisplayed(viewAndPrintCoupon.get(0));
        isDisplayed(expiryDateCoupon.get(0));

        int applyRewardSize = applyToBagButton.size();
        click(viewRewardsLink);
        waitUntilElementsAreDisplayed(applyToBagButton, 5);

        if (applyRewardSize >= 0) {
            waitUntilElementDisplayed(apply10Off, 5);
            click(apply10Off);
        }

        if (waitUntilElementDisplayed(removeButton, 10))
            return true;
        else
            return waitUntilElementDisplayed(couponError, 3);
    }

    public boolean verifyFavStoreIsDisplayed() {
        return waitUntilElementDisplayed(defaultFavStore);
    }

    /**
     * validate all error message type for address book field
     *
     * @param field to verify
     * @return
     */
    public boolean allErrorMessagesType(String field) {
        boolean splCharacter = false;
        boolean emptyChar = false;
        switch (field.toUpperCase()) {
            case "ADDRESS1":
                clearAndFillText(newAddress_AddressLine1, "");
                emptyChar = waitUntilElementDisplayed(address1Err, 10);
                clearAndFillText(newAddress_AddressLine1, "@#$$");
                splCharacter = waitUntilElementDisplayed(address1Err, 10);
                break;
        }
        return splCharacter && emptyChar;
    }

    /**
     * validate all the fields
     *
     * @return
     */
    public boolean verifyGiftCardUi() {
        return waitUntilElementDisplayed(addPayment_CardNumber, 10) &&
                waitUntilElementDisplayed(pinField, 10) &&
                waitUntilElementDisplayed(giftCardMsg, 10) &&
                waitUntilElementDisplayed(giftCardSaveBtn, 10) &&
                waitUntilElementDisplayed(billingCancel, 10);
    }

    /**
     * Click on info icon for MPR
     *
     * @return
     */
    public boolean expandMPRInfo() {
        waitUntilElementDisplayed(rewardsToolTip, 10);
        click(rewardsToolTip);
        String info = getText(rewardsMessage);
        return waitUntilElementDisplayed(toolTipExpand, 10) && info.equalsIgnoreCase("We'll email your rewards to you as you earn them and they'll also appear in your account.");
    }

    /**
     * Close the mpr info
     *
     * @return
     */
    public boolean closeMPRInfo() {
        click(tooltipClose);
        return !waitUntilElementDisplayed(toolTipExpand, 10);
    }

    /**
     * Verify all the fields in order details page for BOPIS order with pickup person only
     *
     * @return
     */
    public boolean validateOrderDetailsForBopisOrderWithPickupPersonOnly() {
        return waitUntilElementDisplayed(orderIdvalue, 10) &&
                waitUntilElementDisplayed(orderStatusLabel, 3) &&
                waitUntilElementDisplayed(orderDateVal, 5) &&
                waitUntilElementDisplayed(orderExpirationDate, 2) &&
                waitUntilElementDisplayed(storeName, 2) &&
                waitUntilElementDisplayed(storeAddress, 2) &&
                waitUntilElementDisplayed(storeTimings, 2) &&
                waitUntilElementDisplayed(storePhoneNo, 2) &&
                waitUntilElementDisplayed(getDirectionsLink, 2) &&
                waitUntilElementDisplayed(pickUpPerson, 2) &&
                !waitUntilElementDisplayed(alternatePickUpPerson, 2) &&
                waitUntilElementDisplayed(cardType, 2) &&
                waitUntilElementDisplayed(cardSuffix, 2) &&
                waitUntilElementDisplayed(address, 2) &&
                waitUntilElementDisplayed(name, 2);
    }

    /**
     * validate all the labels under billing for bopis order
     *
     * @return
     */
    public boolean validateBillingSectionForBopisOrder() {
        return waitUntilElementDisplayed(itemTotalLabel, 10) &&
                waitUntilElementDisplayed(couponsAndPromotionsLabel, 10) &&
                waitUntilElementClickable(storePickUpLabel, 10) &&
                waitUntilElementClickable(taxLabel, 10);
    }

    /**
     * Fetch fav store details
     *
     * @return
     */
    public List<String> getFavStoreNameDetails() {
        List<String> storeDetails = new ArrayList();
        storeDetails.add(getText(storeName).trim());
        storeDetails.add(getText(storeAddress).trim());
        storeDetails.add(getText(storePhoneNo).trim());
        return storeDetails;
    }

    /**
     * Verify all the fields in order details page for BOPIS order with pickup person only
     *
     * @return
     */
    public boolean validateOrderDetailsForBopisOrderWithPickupAndAlternatePerson() {
        return waitUntilElementDisplayed(orderIdvalue, 10) &&
                waitUntilElementDisplayed(orderDateVal, 5) &&
                waitUntilElementDisplayed(orderExpirationDate, 2) &&
                waitUntilElementDisplayed(storeName, 2) &&
                waitUntilElementDisplayed(storeAddress, 2) &&
                waitUntilElementDisplayed(storePhoneNo, 2) &&
                waitUntilElementDisplayed(getDirectionsLink, 2) &&
                waitUntilElementDisplayed(pickUpPerson, 2) &&
                waitUntilElementDisplayed(alternatePickUpPerson, 2) &&
                waitUntilElementDisplayed(cardType, 2) &&
                waitUntilElementDisplayed(cardSuffix, 2) &&
                waitUntilElementDisplayed(address, 2) &&
                waitUntilElementDisplayed(name, 2);
    }

    /**
     * Verify expire date/ use date is displayed or not
     *
     * @return
     */
    public boolean verifyExpireDate() {
        return waitUntilElementDisplayed(couponExpireInfo, 10);
    }

    /**
     * Verify spinner is displayed or not
     *
     * @return
     */
    public boolean verifySpinnerWhileLoadingOrderSetails() {
        return waitUntilElementDisplayed(orderDetailsSpinner, 10);
    }

    /**
     * Click on Show International orders from orders page
     * and verify new UI is displayed
     *
     * @return
     */
    public boolean validateInternationOrdersUi() {
        waitUntilElementDisplayed(intOrders, 10);
        click(intOrders);
        switchToWindow();
        return mobileDriver.getCurrentUrl().equalsIgnoreCase("https://www.borderfree.com/order-status/");
    }

    /**
     * Verify all the fields for online orders
     *
     * @return
     */
    public boolean verifyOrderDetailsFieldsEcomm() {
        javaScriptClick(mobileDriver, itemsToggle);
        return waitUntilElementDisplayed(ordertext, 20) &&
                waitUntilElementDisplayed(orderDateSec, 3) &&
                waitUntilElementDisplayed(orderShippingsec, 3) &&
                waitUntilElementDisplayed(orderBillingsec, 3) &&
                waitUntilElementDisplayed(orderSummaryHeading, 3) &&
                waitUntilElementDisplayed(productDesc, 3);

    }

    /**
     * Created by RichaK
     * Click Edit link on shipping address
     *
     * @return
     */
    public boolean clickEditShippingAddress() {
        waitUntilElementDisplayed(editShipAddressLink, 10);
        click(editShipAddressLink);
        return waitUntilElementDisplayed(editshippingTitle);
    }

    /**
     * Validate store timings for bopis products
     *
     * @return
     */
    public boolean validateStoreTimingFormat() {
        if (waitUntilElementDisplayed(storeTimings, 10)) {
            return getText(storeTimings).contains(("am")) && getText(storeTimings).contains("pm");
        } else {
            addStepDescription("Store Timing is not displayed");
            return false;
        }
    }

    /**
     * Check the phone no format in order details page
     *
     * @return
     */
    public boolean validatePhoneNoFormat() {
        String phoneDigits[];
        waitUntilElementDisplayed(storePhoneNo, 20);
        String phoneNo = getText(storePhoneNo);
        phoneDigits = phoneNo.split("-");
        return phoneDigits[0].length() == 3 && phoneDigits[1].length() == 3 && phoneDigits[2].length() == 4;
    }

    public boolean clickOrderNo(String order) {
        waitUntilElementDisplayed(orderNo(order));
        javaScriptClick(mobileDriver, orderNo(order));
        return waitUntilElementDisplayed(orderDetailsPage);
    }

    public boolean checkTrackingNo() {
        return waitUntilElementDisplayed(trackingNo, 20);
    }

    public String getLast4DigitsOfCC() {
        String text = getText(cardSuffix);
        return text.substring(10, 14).trim();
    }

    public String getLast4DigitsOfGC() {
        String text = getText(giftCardInfo);
        return text.substring(10, 14).trim();
    }

    public boolean validateUserNameInProfileInfoPage(String fName, String lName) {
        if (waitUntilElementDisplayed(name, 10)) {
            return getText(name).equalsIgnoreCase(fName + " " + lName);
        } else {
            addStepDescription("User name is not displayed in Profile info page");
            return false;
        }
    }

    public boolean validatePhoneAndzip(String phoneNo, String zip) {
        boolean match = false;
        if (waitUntilElementDisplayed(phoneNoLbl, 10)) {
            match = getText(phoneNoLbl).contains(phoneNo);
            if (waitUntilElementDisplayed(zipCodeInMailingAddressSection, 10)) {
                match = getText(zipCodeInMailingAddressSection).trim().equalsIgnoreCase(zip);
            } else {
                addStepDescription("Zip code is not displayed in mailing address section");
            }
        } else {
            addStepDescription("phone no is not displayed in profile info page");
        }
        return match;
    }

    public boolean clickEditButton() {
        if (waitUntilElementDisplayed(editButton, 10)) {
            click(editButton);
            return waitUntilElementDisplayed(saveButton, 4) && waitUntilElementDisplayed(cancelButton, 4) &&
                    waitUntilElementDisplayed(emailIdNote, 5);
        } else {
            addStepDescription("Edit button is not displayed in Profile info page");
            return false;
        }
    }

    public boolean clickChangePasswordBtn() {
        if (waitUntilElementDisplayed(changePwdBtn, 10)) {
            javaScriptClick(mobileDriver, changePwdBtn);
            staticWait();
            return waitUntilElementDisplayed(passwordInstructions, 10);
        } else {
            addStepDescription("Change password button is not displayed");
            return false;
        }
    }

    public boolean verifyEmployeeCheckBox() {
        if (waitUntilElementDisplayed(tcpEmpChkBox, 10)) {
            return waitUntilElementDisplayed(tcpEmpChkBox, 1) && !isSelected(tcpEmpChkBox, tcpEmpChkBoxInput);
        } else {
            addStepDescription("Employee Check box is not displayed");
            return false;
        }
    }

    public boolean checkEmpCheckBox() {
        if (waitUntilElementDisplayed(tcpEmpChkBox, 10)) {
            select(tcpEmpChkBox, tcpEmpChkBoxInput);
            return waitUntilElementDisplayed(associateIDField, 10);
        } else {
            addStepDescription("Employee Check box is not displayed");
            return false;
        }
    }

    public boolean clickCancelBtn() {
        if (waitUntilElementDisplayed(billingCancel, 10)) {
            javaScriptClick(mobileDriver, billingCancel);
            return !waitUntilElementDisplayed(saveButton, 2);
        } else {
            addStepDescription("Cancel button is not displayed");
            return false;
        }
    }

    public boolean clickCompleteAddressBtn() {
        if (waitUntilElementDisplayed(completeAddressBtn, 10)) {
            click(completeAddressBtn);
            return waitUntilElementDisplayed(saveButton, 10);
        } else {
            addStepDescription("Completed Address button is not displayed");
            return false;
        }
    }

    public boolean verifyPhoneAndZip(String phoneNo, String zip) {
        return getAttributeValue(phoneNoFld, "value").equalsIgnoreCase(phoneNo)
                && getAttributeValue(zipCodeField, "value").equalsIgnoreCase(zip);
    }

    public void enterMailAddress(String addressLine1, String city, String state) {
        clearAndFillText(billingAdd1, addressLine1);
        clearAndFillText(billingCity, city);
        selectDropDownByVisibleText(billingState, state);
    }

    public void updateEmailAdd(String s) {
        if (waitUntilElementDisplayed(emailIdField, 10)) {
            clearAndFillText(emailIdField, s);
        } else {
            addStepDescription("Unable to update email field");
        }
    }

    public boolean isMyAccountFieldDisplayed(String fieldName) {
        boolean validations = false;
        switch (fieldName.toUpperCase()) {
            case "MPRHEADER":
                validations = waitUntilElementDisplayed(mprRewardsHeader, 10);
                break;
            case "HEADER":
                validations = waitUntilElementDisplayed(addressBookBreadCrumb, 10);
                break;
            case "ADDADDRESS":
                validations = waitUntilElementDisplayed(btn_AddNewAddress, 10);
                break;
            case "SAVEBTN":
                validations = waitUntilElementDisplayed(saveButton, 10);
                break;
            case "CANCELBTN":
                validations = waitUntilElementDisplayed(cancelButton, 10);
                break;
            case "NEWPWDOK":
                validations = waitUntilElementDisplayed(newPwdGreen, 10);
                break;
            case "CONFIRMPWDOK":
                validations = waitUntilElementDisplayed(ConfirmPwdGreen, 10);
                break;
            case "SUCCESSMSG":
                validations = waitUntilElementDisplayed(successMessage, 20);
                break;
            case "PAGELEVELERROR":
                validations = waitUntilElementDisplayed(pageLeverError, 20);
                break;
            case "MPRBANNER":
                validations = waitUntilElementDisplayed(mprBanner, 10);
                break;
            case "CCBTN":
                validations = waitUntilElementDisplayed(addACreditCardBtn, 10);
                break;
            case "GCBTN":
                validations = waitUntilElementDisplayed(addAGiftCardBtn, 10);
                break;
            case "DEFAULTBILLING":
                validations = waitUntilElementDisplayed(defaultBilling, 10);
                break;
            case "SHIPPINGADD":
                validations = waitUntilElementDisplayed(addressBookDDInMailing, 10);
                break;

        }
        return validations;
    }

    public void fillMyAccountField(String field, String value) {
        switch (field.toUpperCase()) {
            case "EMAIL":
                clearAndFillText(emailIdField, value);
                break;
            case "FIRSTNAME":
                clearAndFillText(newAddress_FirstName, value);
                break;
            case "LASTNAME":
                clearAndFillText(newAddress_LastName, value);
                break;
            case "ADDRESS1":
                clearAndFillText(newAddress_AddressLine1, value);
                break;
            case "CURRENTPWD":
                clearAndFillText(currentPwdField, value);
                break;
            case "NEWPWD":
                clearAndFillText(newPwdField, value);
                break;
            case "CONFIRMPWD":
                clearAndFillText(confirmPwdField, value);
                break;
            case "BILLINGFN":
                clearAndFillText(newAddress_FirstName, value);
                break;
            case "BILLINGLN":
                clearAndFillText(newAddress_LastName, value);
                break;
            case "MAILINGADD1":
                clearAndFillText(billingAdd1, value);
                break;
            case "MAILINGCITY":
                clearAndFillText(billingCity, value);
                break;
            case "MAILINGZIP":
                clearAndFillText(billingZip, value);
                break;
            case "GIFTCARDPIN":
                clearAndFillText(addPayment_CardNumber, value);
                break;
            case "GIFTCARDNO":
                clearAndFillText(pinField, value);
                break;
        }
    }

    public boolean isDeleteAddressFieldDisplayed(String fieldName) {
        boolean validations = false;
        switch (fieldName.toUpperCase()) {
            case "POPUP":
                validations = waitUntilElementDisplayed(deleteAddressPage, 10);
                break;
            case "CANCELBTN":
                validations = waitUntilElementDisplayed(cancelDeleteAdd, 10);
                break;
            case "OKBTN":
                validations = waitUntilElementDisplayed(okDeleteAdd, 10);
                break;
            case "CLOSE":
                validations = waitUntilElementDisplayed(closeIcon, 10);
                break;
        }
        return validations;
    }

    public boolean clickButtonInDeleteAddress(String fieldName) {
        boolean validations = false;
        switch (fieldName.toUpperCase()) {
            case "CANCELBTN":
                click(cancelDeleteAdd);
                validations = waitUntilElementDisplayed(defaultshipping, 10);
                break;
            case "OKBTN":
                click(okDeleteAdd);
                validations = !waitUntilElementDisplayed(successMessage, 10);
                break;
            case "CLOSE":
                click(closeIcon);
                validations = waitUntilElementDisplayed(defaultshipping, 10);
                break;
        }
        return validations;
    }


    public boolean clickAddNewAddressBtn() {
        if (waitUntilElementDisplayed(btn_AddNewAddress, 10)) {
            click(btn_AddNewAddress);
            return waitUntilElementDisplayed(newAddress_FirstName, 10);
        }
        return false;
    }

    public boolean validatePhoneNoInEditAddressPage(String phone) {
        if (waitUntilElementDisplayed(newAddress_PhoneNumeber, 10)) {
            return getAttributeValue(newAddress_PhoneNumeber, "value").equalsIgnoreCase(phone);
        }
        return false;
    }

    public boolean validateCountriesInEditAddressPage() {
        if (waitUntilElementDisplayed(newAddress_Countrydd, 10)) {
            return getText(newAddress_Countrydd).contains("United States") && getText(newAddress_Countrydd).contains("Canada");
        }
        return false;
    }

    public boolean validateDefaultShippingAddress() {
        if (waitUntilElementDisplayed(newAddress_SetDefaultAddressCheckBox, 10)) {
            return isSelected(newAddress_SetDefaultAddressCheckBox, newAddress_SetDefaultAddressCheckBoxInput);
        }
        return false;
    }

    public boolean clickCancelBtnInEditBillingPage() {
        if (waitUntilElementDisplayed(newAddressCancelBtn, 10)) {
            click(newAddressCancelBtn);
            return waitUntilElementDisplayed(btn_AddNewAddress, 10);
        }
        return false;
    }

    public String getErrorMsg(String fieldName) {
        String validations = "";
        switch (fieldName.toUpperCase()) {
            case "EMAIL":
                validations = getText(emailError);
                break;
            case "PROFILEFIRST":
                validations = getText(getErrorMessage("First"));
                break;
            case "PROFILELAST":
                validations = getText(getErrorMessage("Last"));
                break;
            case "PROFILEPHONENO":
                validations = getText(getErrorMessage("Mobile Phone Number"));
                break;
            case "FIRSTNAME":
                validations = getText(getErrorMessage("First Name"));
                break;
            case "LASTNAME":
                validations = getText(getErrorMessage("Last Name"));
                break;
            case "ADDRESSLINE1":
                validations = getText(getErrorMessage("Address Line 1"));
                break;
            case "PWDERR":
                validations = getText(getErrorMessage("Current Password"));
                break;
            case "NEWPWDERR":
                validations = getText(getErrorMessage("New Password"));
                break;
            case "CONFIRMPWDERR":
                validations = getText(getErrorMessage("Confirm Password"));
                break;
            case "CCNO":
                validations = getText(ccErr);
                break;
            case "EXPDATE":
                validations = getText(expMotnhErr);
                break;
            case "BILLINGFN":
                validations = getText(fnameErr);
                break;
            case "BILLINGLN":
                validations = getText(lnameErr);
                break;
            case "BILLINGADD1":
                validations = getText(address1Err);
                break;
            case "BILLINGADD2":
                break;
            case "BILLINGCITY":
                validations = getText(cityErr);
                break;
            case "BILLINGSTATE":
                validations = getText(stateErr);
                break;
            case "BILLINGZIP":
                validations = getText(zipErr);
                break;
            case "GIFTCARDNO":
                validations = getText(getInlineErrMsg("Gift Card #"));
                break;
            case "GIFTCARDPIN":
                validations = getText(getInlineErrMsg("Pin #"));
                break;
        }
        return validations;
    }

    public boolean clickEditShippingAddress(String addressLine1) {
        if (waitUntilElementDisplayed(getEditAddressLink(addressLine1), 10)) {
            click(getEditAddressLink(addressLine1));
            return waitUntilElementDisplayed(updateAddressBtn);
        }
        return false;
    }

    public void selectDefaultShippingAddressCheckbox() {
        select(newAddress_SetDefaultAddressCheckBox, newAddress_SetDefaultAddressCheckBoxInput);
    }

    public void clickUpdateButton() {
        if (waitUntilElementDisplayed(updateAddressBtn, 10)) {
            click(updateAddressBtn);
        }
        if (waitUntilElementDisplayed(addressVerificationContinue, 10)) {
            click(addressVerificationContinue);
        }
        waitUntilElementDisplayed(successMessage, 20);
    }

    public int getShippingAddressCount() {
        return shippingAddressContainers.size();
    }

    public void deleteDefaultShippingAddress() {
        if (waitUntilElementDisplayed(defaultshipping, 10)) {
            click(defaultShippingAddressDeleteBtn);
            click(okDeleteAdd);
            waitUntilElementDisplayed(successMessage, 10);
        } else {
            addStepDescription("No Default shipping is available");
        }
    }

    public boolean isDefautShippingAddressAvailable() {
        return waitUntilElementDisplayed(defaultshipping, 10);
    }

    public boolean validateMailingAddressFields() {
        return waitUntilElementDisplayed(billingAdd1, 3) &&
                waitUntilElementDisplayed(billingCity, 3) &&
                waitUntilElementDisplayed(billingAdd2, 3) &&
                waitUntilElementDisplayed(billingCancel, 2);
    }

    public boolean clickAddACreditCard() {
        if (waitUntilElementDisplayed(addACreditCardBtn, 10)) {
            click(addACreditCardBtn);
            return waitUntilElementDisplayed(billingLn);
        }
        return false;
    }

    public boolean validateAddACreditCardPagUI() {
        return waitUntilElementDisplayed(addPayment_CardNumber, 10) &&
                waitUntilElementDisplayed(expMonthDropdown, 2) &&
                waitUntilElementDisplayed(expYearDropdown, 3) &&
                waitUntilElementDisplayed(billingFn, 3) &&
                waitUntilElementDisplayed(billingLn, 3) &&
                waitUntilElementDisplayed(billingAdd1, 3) &&
                waitUntilElementDisplayed(billingAdd2, 3) &&
                waitUntilElementDisplayed(billingCity, 3) &&
                waitUntilElementDisplayed(billingStateDD, 3) &&
                waitUntilElementDisplayed(billingZip, 3) &&
                waitUntilElementDisplayed(billingCancel, 3) &&
                waitUntilElementDisplayed(btnAddCard, 3);
    }

    public String getStateTitle() {
        return getText(billingStatelabel);
    }

    public String getZipTitle() {
        return getText(billingZipGhostText);
    }

    public void clickAddCardButton() {
        if (waitUntilElementDisplayed(btnAddCard, 10))
            javaScriptClick(mobileDriver, btnAddCard);
    }

    public int getCurrentDisplayedCouponIndex() {
        return Integer.parseInt(getAttributeValue(currentDisplayedCoupon, "data-index"));
    }

    public String getCurrentDisplayedCouponValue() {
        int i = getCurrentDisplayedCouponIndex();
        return getText(couponValueList.get(i));
    }

    public boolean clickOffersAndCoupons() {
        javaScriptClick(mobileDriver, offersLnk);
        return waitUntilElementDisplayed(viewAndPrintCoupon.get(0));
    }

    public boolean navigateToCouponValue(String couponValue) {
        for (WebElement webElement : couponValueList) {
            String currentDisplayedCpnValue = getCurrentDisplayedCouponValue();
            if (couponValue.equals(currentDisplayedCpnValue))
                return true;
            else
                javaScriptClick(mobileDriver, nextBtn);
        }

        return false;
    }

    public boolean clickApplyToBagBtn() {
        int index = getCurrentDisplayedCouponIndex();
        click(applyToBagButton.get(index));
        return waitUntilElementDisplayed(removeButton);
    }

    public void selectMailingCountry(String country) {
        selectDropDownByVisibleText(newAddress_CountryFld, country);
        staticWait(1000);
    }

    public boolean clickAddAGiftCardBtn() {
        if (waitUntilElementDisplayed(addAGiftCardBtn, 10)) {
            javaScriptClick(mobileDriver, addAGiftCardBtn);
            return waitUntilElementDisplayed(pinField, 10);
        } else {
            addStepDescription("Add A Gift card button is not displayed");
            return false;
        }
    }

    public boolean clickAddBirthDayInfoBtn() {
        if (waitUntilElementDisplayed(addBdayInfoBtn, 10)) {
            click(addBdayInfoBtn);
            return waitUntilElementDisplayed(birthdaySavingsPage, 10);
        } else {
            addStepDescription("ADD BIRTHDAY INFO BUTTON IS NOT DISPLAYED");
            return false;
        }
    }

    public boolean validateBirthdaySavingsUI() {
        return waitUntilElementDisplayed(birthdaySavingsPageContent, 3) &&
                addAChildBtns.size() == 4;
    }

    public boolean verifyRemoveBirthdayInfo() {
        return waitUntilElementDisplayed(closeIcon, 4) &&
                waitUntilElementDisplayed(yesRemoveBtn, 3) &&
                waitUntilElementDisplayed(noBtn, 10) &&
                waitUntilElementDisplayed(removeBirthDayInfo, 10);

    }

    public void clickAddChildBtn() {
        if (waitUntilElementDisplayed(addChildbtn, 10)) {
            click(addChildbtn);
        } else {
            addStepDescription("Add Child Btn is not displayed");
        }
    }

    public boolean removeODMCoupon() {
        int couponsSize = couponDots.size();
        if (waitUntilElementDisplayed(removeButton, 10)) {
            click(removeButton);
            staticWait(2000);
            return couponDots.size() == couponsSize - 1;
        } else {
            addStepDescription("Remove button is not available to Coupon");
            return false;
        }
    }

    public int getAvailableCouponCount() {
        return couponDots.size();
    }
}

