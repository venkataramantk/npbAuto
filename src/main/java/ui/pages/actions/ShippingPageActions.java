package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.ShippingPageRepo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by skonda on 5/19/2016.
 */
public class ShippingPageActions extends ShippingPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(ShippingPageActions.class);
    public String prodName;
    public String prodSize;
    public String prodColor;
    public String prodQuantity;
    public String prodPrice;

    public ShippingPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isShippingPageDisplayed(String shippingPage) {
        return getText(sectionTitle).equalsIgnoreCase(shippingPage);
    }

    public boolean enterShippingDetailsShipMethodAndContinue_Reg(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        enterShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod);
        //staticWait(5000);
        return clickNextBillingButton(billingPageActions);
    }

    public boolean enterShippingDetailsShipMethodAndContinue_andsave(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        enterShippingDetailsAndShipTypeAndSave(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod);
        waitUntilElementDisplayed(nextBillingButton, 6);
        click(nextBillingButton);
        if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
            click(addressVerificationContinue);
        }
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 40);
    }

    public boolean editShippingDetailsShipMethodAndContinue_Reg(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        editShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod);
        staticWait(5000);
        if (waitUntilElementDisplayed(saveButton, 2)) {
            click(saveButton);
        }
        return clickNextBillingButtonOnEditShipping(billingPageActions);
    }

    public boolean enterShippingDetailsShipMethodAndContinueByPos_Reg(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod_Pos) {
        enterShippingDetailsAndShipTypeByPos(fName, lName, addrLine1, city, state, zip, phNum, shipMethod_Pos);
        return clickNextBillingButton(billingPageActions);
    }

    public boolean enterShippingDetailsShipMethodAndContinue_Guest(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        enterShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod);
        if (waitUntilElementDisplayed(emailAddressFld, 10))
            clearAndFillText(emailAddressFld, randomEmail());
        return clickNextBillingButton(billingPageActions);
    }
    public boolean enterShippingDetailsShipMethodAndContinue_GuestWithEmail(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod,String email) {
        enterShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod);
        if (waitUntilElementDisplayed(emailAddressFld, 10))
            clearAndFillText(emailAddressFld, email);
        return clickNextBillingButton(billingPageActions);
    }

    public String enterShippingAndContinue_Guest_GetEmail(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        enterShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod);
        String emailGuest = randomEmail();
        if (waitUntilElementDisplayed(emailAddressFld, 10))
            clearAndFillText(emailAddressFld, emailGuest);
        clickNextBillingButton(billingPageActions);
        return emailGuest;
    }

    public boolean editShippingDetails_Guest_WithoutBuildNum(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        editShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod);
        if (waitUntilElementDisplayed(emailAddressFld, 10))
            clearAndFillText(emailAddressFld, randomEmail());
        if (waitUntilElementDisplayed(saveButton, 2)) {
            click(saveButton);
        }
        return clickNextBilling_AVSErrorWithoutBuildNum();
    }

    public boolean editShippingDetails_Reg_WithoutBuildNum(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        editShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod);
        staticWait(5000);
        if (waitUntilElementDisplayed(saveButton, 2)) {
            click(saveButton);
        }
        return clickNextBilling_AVSErrorWithoutBuildNum();
    }

    public boolean enterShipDetailsAndShipMethodByPosAndContinue_GuestCA(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod_Pos) {
        enterShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod_Pos);
        if (isDisplayed(emailAddressFld)) {
            clearAndFillText(emailAddressFld, randomEmail());
        }
        return clickNextBillingButton(billingPageActions);
    }

    public boolean enterShippingDetailsShipMethodAndContinue_RegExpr(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        enterShippingDetailsAndShipType(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod);
        return clickNextBillingButtonExpress(billingPageActions);
    }

    public boolean enterShippingDetailsBOPISReg(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip) {
        enterBOPISShipDetails(fName, lName, addrLine1, addrLine2, city, state, zip);
        return clickNextBillingButton(billingPageActions);
    }


    public void enterShippingDetailsAndShipType(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        if (!waitUntilElementDisplayed(editLink_ShipAddress, 5)) {
            waitUntilElementDisplayed(firstNameFld, 3);
            clearAndFillText(firstNameFld, fName);
            clearAndFillText(lastNameFld, lName);
            clearAndFillText(addressLine1Fld, addrLine1);
            clearAndFillText(addressLine2Fld, addrLine2);
            clearAndFillText(cityFld, city);
            clearAndFillText(zipPostalCodeFld, zip);
            clearAndFillText(phNumFld, phNum);
            selectDropDownByValue(stateDropDown, state);
        }
        staticWait();
        unSelect(saveToAddrBookChkBoxEnabled, saveToAddrBookChkBoxEnabledinput);
    }

    public void enterShippingDetailsAndShipTypeAndSave(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        if (!waitUntilElementDisplayed(editLink_ShipAddress, 5)) {
            waitUntilElementDisplayed(firstNameFld, 3);
            clearAndFillText(firstNameFld, fName);
            clearAndFillText(lastNameFld, lName);
            clearAndFillText(addressLine1Fld, addrLine1);
            clearAndFillText(addressLine2Fld, addrLine2);
            clearAndFillText(cityFld, city);
            clearAndFillText(zipPostalCodeFld, zip);
            clearAndFillText(phNumFld, phNum);
            selectDropDownByValue(stateDropDown, state);
        }
    }

    public void editShippingDetailsAndShipType(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        if (waitUntilElementDisplayed(editLink_ShipAddress, 5)) {
            click(editLink_ShipAddress);
        }
        waitUntilElementDisplayed(firstNameFld);
        clearAndFillText(firstNameFld, fName);
        clearAndFillText(lastNameFld, lName);
        clearAndFillText(addressLine1Fld, addrLine1);
        clearAndFillText(addressLine2Fld, addrLine2);
        clearAndFillText(cityFld, city);
        clearAndFillText(zipPostalCodeFld, zip);
        clearAndFillText(phNumFld, phNum);
        selectDropDownByValue(stateDropDown, state);
        staticWait();
    }

    public boolean enterShippingAddressByShipMethodPos_Reg(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, BillingPageActions billingPageActions) {
        if (!waitUntilElementDisplayed(editLink_ShipAddress, 20)) {
            waitUntilElementDisplayed(firstNameFld);
            clearAndFillText(firstNameFld, fName);
            staticWait();
            clearAndFillText(lastNameFld, lName);
            staticWait();
            clearAndFillText(addressLine1Fld, addrLine1);
            staticWait();
            clearAndFillText(addressLine2Fld, addrLine2);
            staticWait();
            clearAndFillText(cityFld, city);
            staticWait();
            clearAndFillText(zipPostalCodeFld, zip);
            staticWait();
            clearAndFillText(phNumFld, phNum);
            staticWait();
            selectDropDownByValue(stateDropDown, state);
            staticWait();
//        selectDropDownByValue(shippingMethodDropDown,shipMethod);
        }
        staticWait();
        selectShippingMethodFromRadioButtonByPos("1");
        checkTheFreeShip();
        clickNextBillingButton(billingPageActions);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 10);
    }

    public boolean enterShippingAddressByShipMethodPos_Guest(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, BillingPageActions billingPageActions) {
        if (!waitUntilElementDisplayed(editLink_ShipAddress, 20)) {
            waitUntilElementDisplayed(firstNameFld);
            clearAndFillText(firstNameFld, fName);
            clearAndFillText(lastNameFld, lName);
            clearAndFillText(cityFld, city);
            clearAndFillText(addressLine1Fld, addrLine1);
            clearAndFillText(addressLine2Fld, addrLine2);
            clearAndFillText(zipPostalCodeFld, zip);
            clearAndFillText(phNumFld, phNum);
            if (waitUntilElementDisplayed(emailAddressFld, 10))
                clearAndFillText(emailAddressFld, randomEmail());
            selectDropDownByValue(stateDropDown, state);
        }
        staticWait();
        selectShippingMethodFromRadioButtonByPos("1");
        checkTheFreeShip();
        clickNextBillingButton(billingPageActions);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 10);
    }

    public void enterBOPISShipDetails(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip) {
        waitUntilElementDisplayed(firstNameFld, 5);
        clearAndFillText(firstNameFld, fName);
        staticWait();
        clearAndFillText(lastNameFld, lName);
        staticWait();
        clearAndFillText(addressLine1Fld, addrLine1);
        staticWait();
        /*clearAndFillText(addressLine2Fld,addrLine2);
        staticWait();*/
        clearAndFillText(cityFld, city);
        staticWait();
        clearAndFillText(zipPostalCodeFld, zip);
        staticWait();
        selectDropDownByValue(stateDropDown, state);
        staticWait();
    }

    public void enterShippingDetailsAndShipTypeByPos(String fName, String lName, String addrLine1, String city, String state, String zip, String phNum, String shipMethod_Pos) {
        enterShippingDetails(fName, lName, addrLine1, city, state, zip, phNum);
//        selectDropDownByValue(shippingMethodDropDown,shipMethod);
        selectShippingMethodFromRadioButtonByPos(shipMethod_Pos);
        staticWait();
    }

    public void enterShippingDetails(String fName, String lName, String addrLine1, String city, String state, String zip, String phNum) {
        if (!waitUntilElementDisplayed(editLink_ShipAddress, 20)) {
            waitUntilElementDisplayed(firstNameFld);
            clearAndFillText(firstNameFld, fName);
            staticWait();
            clearAndFillText(lastNameFld, lName);
            staticWait();
            clearAndFillText(addressLine1Fld, addrLine1);
            staticWait();
            clearAndFillText(cityFld, city);
            staticWait();
            clearAndFillText(zipPostalCodeFld, zip);
            staticWait();
            clearAndFillText(phNumFld, phNum);
            staticWait();
            selectDropDownByValue(stateDropDown, state);
            staticWait();
        }
    }

    public void selectShippingMethodFromRadioButton(String shipMethod) {
        if (!isSelected(shippingMethodRadioButtonByValue(shipMethod))) {
            scrollDownUntilElementDisplayed(nextBillingButton);
            click(shippingMethodRadioButtonByValue(shipMethod));
        }
    }

    public void selectShippingMethodFromRadioButtonByPos(String pos) {

        if (!isSelected(shippingMethodRadioButtonByPos(pos)))
            click(shippingMethodRadioButtonByPos(pos));
    }

    public boolean clickNextBillingButtonOnEditShipping(BillingPageActions billingPageActions) {
        staticWait(3000);
        if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
            click(addressVerificationContinue);
        }
//        click(nextBillingButton);
//        staticWait(3000);
//        waitUntilElementDisplayed(billingPageActions.creditCardRadioBox, 20);
//        return waitUntilElementDisplayed(billingPageActions.nextReviewButton,20);
        waitUntilElementDisplayed(nextBillingButton);
        click(nextBillingButton);
        staticWait(3000);
        if (waitUntilElementDisplayed(addressVerificationContinue, 10)) {
            click(addressVerificationContinue);
        }
        waitUntilElementDisplayed(billingPageActions.creditCardRadioBox, 20);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);
    }

    public boolean clickNextBillingButton(BillingPageActions billingPageActions) {
        unSelect(saveToAddrBookChkBoxEnabled, saveToAddrBookChkBoxEnabledinput);
        waitUntilElementDisplayed(nextBillingButton);
        click(nextBillingButton);
        if (waitUntilElementDisplayed(addressVerificationContinue, 30)) {
            click(addressVerificationContinue);
        }
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 30);
    }

    public boolean removeAppliedCoupons() {
        WebElement remove1stButton = null;
        try {
            remove1stButton = removeButtonsOfCoupons.get(0);
        } catch (Exception e) {
            return false;
        }
        scrollToTheTopHeader(removeButtonsOfCoupons.get(0));
        boolean isRemoveButtonDisabled = false;
        for (WebElement removeButton : removeButtonsOfCoupons) {
            click(removeButton);
            isRemoveButtonDisabled = !isEnabled(removeButton);
            verifyElementNotDisplayed(removeButton, 5);
        }
        addStepDescription("Is Remove Button disabled while removing the coupon? " + isRemoveButtonDisabled);
        return isRemoveButtonDisabled && !waitUntilElementsAreDisplayed(removeButtonsOfCoupons, 1);
    }

    public double getCouponDiscount() {
        double couponDiscount = 0.00;
        try {
            couponDiscount = Double.valueOf(getText(couponCodeApplied).replaceAll("[^0-9.]", ""));
        } catch (Throwable t) {
            couponDiscount = 0.00;
        }
        return couponDiscount;
    }

    public boolean editShippingAddress(ShippingPageActions shippingPageActions) {
        staticWait(3000);
        if (waitUntilElementDisplayed(getAddressVerificationEdit, 30)) {
            click(getAddressVerificationEdit);
        }
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 30);
    }

    public boolean clickNextBillingButtonExpress(BillingPageActions billingPageActions) {
        waitUntilElementClickable(nextBillingButton, 20);
        click(nextBillingButton);
        staticWait(3000);
        if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
            click(addressVerificationContinue);
        }
        return waitUntilElementDisplayed(billingPageActions.cvvExpressFld, 10);

    }

    public boolean clickNextBillingButtonOnPaypal(ReviewPageActions reviewPageActions) {
        click(nextBillingButton);
        staticWait(3000);
        if (waitUntilElementDisplayed(addressVerificationContinue, 30)) {
            click(addressVerificationContinue);
        }
        return waitUntilElementDisplayed(reviewPageActions.returnToPayPalButton, 30);

    }

    public boolean selectGiftWrappingAndGiftMessage() {
        scrollToBottom();
        select(giftWrappingCheckbox, giftWrappingCheckboxInput);
       // click(giftWrappingCheckbox);
        waitUntilElementDisplayed(giftWrappingOptions, 5);
        boolean isStdGiftWrap = clickOnGiftWrapOptions();
        if (isStdGiftWrap) {
            click(giftWrappingStandard);
            clearAndFillText(giftWrappingMessage, "enter text message more than 100 characters in the field to check if the text is trimmed to 100 characters");//More than 100 Char
            String actaulText = getText(giftWrappingMessage);
            if (actaulText.length() == 100) {//validate length of the text
                return true;
            } else return false;
        }
        return waitUntilElementDisplayed(nextBillingButton, 30);
    }

    public boolean clickOnGiftWrapOptions() {
        click(giftWrappingOptions);
        return waitUntilElementDisplayed(giftWrappingStandard);
    }

    public boolean validateFieldsInShippingPage() {
        if (waitUntilElementDisplayed(firstNameFld) &&
                waitUntilElementDisplayed(lastNameFld) &&
                waitUntilElementDisplayed(addressLine1Fld) &&
                waitUntilElementDisplayed(addressLine2Fld) &&
                waitUntilElementDisplayed(cityFld) &&
                waitUntilElementDisplayed(zipPostalCodeFld) &&
                waitUntilElementDisplayed(phNumFld) &&
                waitUntilElementDisplayed(couponCodeField))
            return true;
        else
            return false;
    }

    public boolean validateDeptNameInShippingPage() {
        if (!(waitUntilElementDisplayed(link_Girls, 2) &&
                waitUntilElementDisplayed(link_ToddlerGirls, 2) &&
                waitUntilElementDisplayed(link_Boys, 2) &&
                waitUntilElementDisplayed(link_ToddlerBoys, 2) &&
                waitUntilElementDisplayed(link_Baby, 2) &&
                waitUntilElementDisplayed(link_Shoes, 2) &&
                waitUntilElementDisplayed(link_Accessories, 2)))
            return true;
        else
            return false;
    }

    public boolean validateFieldsInShippingPageAsGuest() {
        if (waitUntilElementDisplayed(firstNameFld) &&
                waitUntilElementDisplayed(lastNameFld) &&
                waitUntilElementDisplayed(addressLine1Fld) &&
                waitUntilElementDisplayed(addressLine2Fld) &&
                waitUntilElementDisplayed(cityFld) &&
                waitUntilElementDisplayed(zipPostalCodeFld) &&
                waitUntilElementDisplayed(phNumFld) &&
                waitUntilElementDisplayed(couponCodeField) &&
                waitUntilElementDisplayed(emailAddressFld) &&
                waitUntilElementDisplayed(shipInternationalLnk))
//                && waitUntilElementDisplayed(termsAndConditionText))
            return true;
        else
            return false;
    }

    public boolean validateFieldsInShippingPageAsReg() {
        if (waitUntilElementDisplayed(firstNameFld) &&
                waitUntilElementDisplayed(lastNameFld) &&
                waitUntilElementDisplayed(addressLine1Fld) &&
                waitUntilElementDisplayed(addressLine2Fld) &&
                waitUntilElementDisplayed(cityFld) &&
                waitUntilElementDisplayed(zipPostalCodeFld) &&
                waitUntilElementDisplayed(phNumFld) &&
                waitUntilElementDisplayed(couponCodeField) &&
                waitUntilElementDisplayed(shipInternationalLnk))
            return true;
        else
            return false;
    }

    public boolean shippingPageErrorVerification() {
        if (!(verifyElementNotDisplayed(err_FirstName) && verifyElementNotDisplayed(err_LastName) && verifyElementNotDisplayed(err_AddressLine1) && verifyElementNotDisplayed(err_City) && verifyElementNotDisplayed(err_State) && verifyElementNotDisplayed(err_POZipCode) && verifyElementNotDisplayed(err_PhoneNumber))) {
            return true;
        } else
            return false;
    }

    public boolean shippingPageErrorVerificationAfterEnteringValues() {
        if ((verifyElementNotDisplayed(err_FirstName) && verifyElementNotDisplayed(err_LastName) && verifyElementNotDisplayed(err_AddressLine1) && verifyElementNotDisplayed(err_City) && verifyElementNotDisplayed(err_State) && verifyElementNotDisplayed(err_POZipCode) && verifyElementNotDisplayed(err_PhoneNumber))) {
            return true;
        } else
            return false;
    }

    public WebElement FirstName() {
        return firstNameFld;
    }

    public WebElement LastName() {
        return lastNameFld;
    }

    public WebElement AddressLine1() {
        return addressLine1Fld;
    }

    public WebElement AddressLine2() {
        return addressLine2Fld;
    }

    public WebElement City() {
        return cityFld;
    }

    public WebElement State() {
        return stateDropDown;
    }

    public WebElement Zip() {
        return zipPostalCodeFld;
    }

    public WebElement Country() {
        return shipToCountryDropdown;
    }

    public WebElement Phone() {
        return phNumFld;
    }

    public void tabVerification() {//Created by Aditya
        FirstName().click();
        FirstName().sendKeys(Keys.TAB);
        LastName().sendKeys(Keys.TAB);
        AddressLine1().sendKeys(Keys.TAB);
        AddressLine2().sendKeys(Keys.TAB);
        City().sendKeys(Keys.TAB);
        Zip().sendKeys(Keys.TAB);
        Phone().sendKeys(Keys.TAB);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public boolean checkDiscountIsApplied(double priceInQuickView, double estTotalInShippingPage) {
        int discountPercentage = 30;
        double discountAmount;
        discountAmount = (discountPercentage * priceInQuickView) / 100;
        discountAmount = (double) Math.round(discountAmount * 100) / 100;
        double amountAfterDiscount = priceInQuickView - discountAmount;
//        amountAfterDiscount = round(amountAfterDiscount, 2);
        if (amountAfterDiscount == estTotalInShippingPage)
            return true;
        else
            return false;
    }


    public double txtSubtotalPriceAlone() {
        return Double.parseDouble(getText(itemsTotalPrice).replace("$", ""));//Double.parseDouble(driver.findElement(By.xpath("//*[@id='WC_SingleShipmentOrderTotalsSummary_td_2']")).getText().replace("$", ""));
    }

    public double getEstimatedTotal() {
        return Double.parseDouble(getText(estimatedTotal).replace("$", ""));//Double.parseDouble(driver.findElement(By.xpath("//*[@id='WC_SingleShipmentOrderTotalsSummary_td_2']")).getText().replace("$", ""));
    }

    public boolean isFreeShippingApplied() {
        boolean isShipDiscDisplaying = waitUntilElementDisplayed(discAppliedValueLbl, 5);
        boolean isPromotionLblDisplaying = waitUntilElementDisplayed(promotionsLabel, 5);
        if (isShipDiscDisplaying && isPromotionLblDisplaying) {

            return getText(promotionsLabel).equalsIgnoreCase("Promotions") && getText(discAppliedValueLbl).equalsIgnoreCase("Free");
        }
        return false;

    }

    public boolean validateFreeShipNotApplied() {
        boolean isFreeShipLblDisplaying = waitUntilElementDisplayed(promotionsLabel, 1);

        if (isFreeShipLblDisplaying) {
            return false;
        }

        return true;
    }


    public boolean defaultAddressVerification(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {

        if (isDisplayed(editLink_ShipAddress)) {
            boolean validateFName = getText(defaultShipName).replaceAll("\"", "").contains(fName);
            boolean validateLName = getText(defaultShipName).replaceAll("\"", "").contains(lName);
            boolean validateAddress = getText(defaultShipAddress).replaceAll("\"", "").contains(addrLine1);
            boolean validateCity = getText(defaultShipAddress).replaceAll("\"", "").contains(city);
            boolean validateZIP = getText(defaultShipAddress).replaceAll("\"", "").contains(zip);

            if (validateFName && validateLName &&
                    validateAddress &&
                    validateCity &&
                    validateZIP)
                return true;
            else
                return false;
        }

        else if(firstNameFld.getAttribute("value").contains(fName) &&
                lastNameFld.getAttribute("value").contains(lName) &&
                addressLine1Fld.getAttribute("value").contains(addrLine1) &&
                addressLine2Fld.getAttribute("value").contains(addrLine2) &&
                cityFld.getAttribute("value").contains(city) &&
                zipPostalCodeFld.getAttribute("value").contains(zip) &&
                phNumFld.getAttribute("value").contains(phNum) &&
                stateDropDown.getAttribute("value").contains(state) &&
                shippingMethodDropDown.getAttribute("value").contains(shipMethod))
            return true;
        else{
            return false;
        }
    }


    public boolean validateEmptyFieldErrMessagesReg(String errFName, String errLName,
                                                    String errAddrLine1, String errCity,
                                                    String errState, String errZipPostal,
                                                    String errPhone) {
        if (isDisplayed(editLink_ShipAddress)) {
            clickAddressDropdown();
            clickAddNewAddress();
        } else
            waitUntilElementDisplayed(nextBillingButton, 30);

        tabFromField(firstNameFld);
        tabFromField(lastNameFld);
        tabFromField(addressLine1Fld);
        tabFromField(cityFld);
        tabFromField(stateDropDown);
        tabFromField(zipPostalCodeFld);
        clearAndFillText(phNumFld, "");
        tabFromField(phNumFld);

        boolean fNameErr = getText(err_FirstName).contains(errFName);
        boolean lNameErr = getText(err_LastName).contains(errLName);
        boolean addressLine1Err = getText(err_AddressLine1).contains(errAddrLine1);
        boolean cityErr = getText(err_City).contains(errCity);
        boolean stateErr = getText(err_State).contains(errState);
        boolean zipErr = getText(err_POZipCode).contains(errZipPostal);
        boolean phoneErr = getText(err_PhoneNumber).contains(errPhone);

        return fNameErr && lNameErr && addressLine1Err && cityErr && stateErr && zipErr && phoneErr && phoneErr;
    }

    public boolean validateEmptyCharacterErrorMessage(String splChar, String errFName, String errLName, String errAdrLine1, String errCity, String errPhone, String errPOZip, String errEmail) {
        clearAndFillText(firstNameFld, splChar);
        clearAndFillText(lastNameFld, splChar);
        clearAndFillText(addressLine1Fld, splChar);
        clearAndFillText(cityFld, splChar);
        clearAndFillText(phNumFld, splChar);
        clearAndFillText(zipPostalCodeFld, splChar);
        click(firstNameFld);

        boolean fields = getText(err_FirstName).contains(errFName) && getText(err_LastName).contains(errLName) && getText(err_AddressLine1).contains(errAdrLine1) &&
                getText(err_City).contains(errCity) && getText(err_PhoneNumber).contains(errPhone) && getText(err_POZipCode).contains(errPOZip);

        if (waitUntilElementDisplayed(emailAddressFld, 10)) {
            clearAndFillText(emailAddressFld, splChar);
            return fields && getText(err_Email).contains(errEmail);
        }
        return fields;
    }

    public boolean validateSpecialCharacterErrorMessage(String splChar, String errFName, String errLName, String errAdrLine1, String errCity, String errPhone, String errPOZip, String errEmail) {
        clearAndFillText(firstNameFld, splChar);
        clearAndFillText(lastNameFld, splChar);
        clearAndFillText(addressLine1Fld, splChar);
        clearAndFillText(addressLine2Fld, splChar);
        clearAndFillText(cityFld, splChar);
        clearAndFillText(phNumFld, splChar);
        clearAndFillText(zipPostalCodeFld, splChar);

        boolean fields = getText(err_FirstName).contains(errFName) && getText(err_LastName).contains(errLName) && getText(err_AddressLine1).contains(errAdrLine1) &&
                getText(err_AddressLine2).contains(errAdrLine1) && getText(err_City).contains(errCity) && getText(err_PhoneNumber).contains(errPhone) && getText(err_POZipCode).contains(errPOZip);

        if (waitUntilElementDisplayed(emailAddressFld, 10)) {
            clearAndFillText(emailAddressFld, splChar);
            return fields && getText(err_Email).contains(errEmail);
        }
        return fields;
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

    public boolean validateErrOnAdrLine1Fld_SplChar(String fName, String lName, String address1, String city, String state, String zipPostal, String phoneNum, String splChar, String errAdrLine1) {
        enterShippingDetails(fName, lName, address1, city, state, zipPostal, phoneNum);
        clearAndFillText(addressLine1Fld, splChar);
        tabFromField(addressLine1Fld);
        waitUntilElementDisplayed(err_AddressLine1, 2);
        boolean adrLine1Err = getText(err_AddressLine1).contains(errAdrLine1);
        return adrLine1Err;
    }

    public boolean validateErrOnAdrLine2Fld_SplChar(String fName, String lName, String address1, String city, String state, String zipPostal, String phoneNum, String splChar, String errAdrLine1) {
        enterShippingDetails(fName, lName, address1, city, state, zipPostal, phoneNum);
        clearAndFillText(addressLine2Fld, splChar);
        tabFromField(addressLine2Fld);
        waitUntilElementDisplayed(err_AddressLine2, 2);
        boolean adrLine2Err = getText(err_AddressLine2).contains(errAdrLine1);
        return adrLine2Err;
    }

    public boolean validateErrOnCityFld_SplChar(String fName, String lName, String address1, String city, String state, String zipPostal, String phoneNum, String splChar, String errCity) {
        enterShippingDetails(fName, lName, address1, city, state, zipPostal, phoneNum);
        clearAndFillText(addressLine2Fld, address1);
        clearAndFillText(cityFld, splChar);
        tabFromField(cityFld);
        waitUntilElementDisplayed(err_City, 2);
        boolean cityErr = getText(err_City).contains(errCity);
        return cityErr;
    }

    public boolean validateErrOnPhoneFld_SplChar(String fName, String lName, String address1, String city, String state, String zipPostal, String phoneNum, String splChar, String errPhone) {
        enterShippingDetails(fName, lName, address1, city, state, zipPostal, phoneNum);
        clearAndFillText(phNumFld, splChar);
        tabFromField(phNumFld);
        waitUntilElementDisplayed(err_PhoneNumber, 2);
        boolean phoneErr = getText(err_PhoneNumber).contains(errPhone);
        return phoneErr;
    }

    public boolean validateErrOnPOZipFld_SplChar(String fName, String lName, String address1, String city, String state, String zipPostal, String phoneNum, String splChar, String errPOZip) {
        enterShippingDetails(fName, lName, address1, city, state, zipPostal, phoneNum);
        clearAndFillText(zipPostalCodeFld, splChar);
        tabFromField(zipPostalCodeFld);
        waitUntilElementDisplayed(err_POZipCode, 2);
        boolean poZipErr = getText(err_POZipCode).contains(errPOZip);
        return poZipErr;
    }

    public boolean validateSingleFldEmptyErrMsgReg(String fName, String lName, String address1, String city, String state, String zipPostal, String phoneNum, String errFName, String errLName, String errAdrLine1, String errCity, String errZipPostal, String errPhone, String emptyChar) {

        boolean fNameVal = validateErrOnFNameFld_SplChar(fName, lName, address1, city, state, zipPostal, phoneNum, emptyChar, errFName);
        boolean lNameVal = validateErrOnLNameFld_SplChar(fName, lName, address1, city, state, zipPostal, phoneNum, emptyChar, errLName);
        boolean adrLine1Val = validateErrOnAdrLine1Fld_SplChar(fName, lName, address1, city, state, zipPostal, phoneNum, emptyChar, errAdrLine1);
        boolean cityVal = validateErrOnCityFld_SplChar(fName, lName, address1, city, state, zipPostal, phoneNum, emptyChar, errCity);
        boolean zipVal = validateErrOnPOZipFld_SplChar(fName, lName, address1, city, state, zipPostal, phoneNum, emptyChar, errZipPostal);
        boolean phoneVal = validateErrOnPhoneFld_SplChar(fName, lName, address1, city, state, zipPostal, phoneNum, emptyChar, errPhone);
        return fNameVal && lNameVal && adrLine1Val && cityVal && zipVal && phoneVal;
    }

    public boolean clickLoginOnESpot(LoginPageActions loginPageActions) {
        waitUntilElementDisplayed(eSpotLoginLink, 10);
        click(eSpotLoginLink);
        return waitUntilElementDisplayed(loginPageActions.loginButton, 20);
    }

    public boolean clickCreateAccountOnESpot(CreateAccountActions createAccountActions) {
        waitUntilElementDisplayed(eSpotCreateAccountLink, 10);
        click(eSpotCreateAccountLink);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton, 20);
    }

    public boolean loginFromESpot(LoginPageActions loginPageActions, String email, String password) {
        clickLoginOnESpot(loginPageActions);
        loginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        return waitUntilElementDisplayed(nextBillingButton, 10);
    }

    public boolean createAccountFromESpot(CreateAccountActions createAccountActions, String email, String fName, String lName, String password, String zip, String phone) {
        clickCreateAccountOnESpot(createAccountActions);
        createAccountActions.createAnAccount_UserInputEmail(email, fName, lName, password, zip, phone);
        return waitUntilElementDisplayed(nextBillingButton, 10);
    }

    public boolean closeLoginCreateAccOverlay() {
        waitUntilElementDisplayed(closeOverlayBtn, 10);
        click(closeOverlayBtn);
        return (!waitUntilElementDisplayed(closeOverlayBtn, 5));
    }

    public boolean fillLoginDetailsCloseOverlay(LoginPageActions loginPageActions, String email, String password) {
        clickLoginOnESpot(loginPageActions);
        clearAndFillText(loginPageActions.emailFldOnLoginForm, email);
        clearAndFillText(loginPageActions.pwdFldOnLoginForm, password);
        return closeLoginCreateAccOverlay();
    }

    public boolean fillCreateAccDetailsCloseOverlay(CreateAccountActions createAccountActions, String email, String fName, String lName, String password, String zip, String phone) {
        clickCreateAccountOnESpot(createAccountActions);
        clearAndFillText(createAccountActions.emailAddrField, email);
        clearAndFillText(createAccountActions.confEmailAddrFld, email);
        createAccountActions.enterAccountDetails(fName, lName, password, zip, phone);
        return closeLoginCreateAccOverlay();
    }

    public boolean returnToBagPage(ShoppingBagPageActions shoppingBagPageActions) {
        waitUntilElementDisplayed(returnBagLink, 20);
        click(returnBagLink);
        waitUntilElementDisplayed(returnBagBtn, 20);
        click(returnBagBtn);
        staticWait(2000);
        return waitUntilElementDisplayed(shoppingBagPageActions.checkoutBtn, 30);
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

    public boolean changeShippingMethod() {
        waitUntilElementDisplayed(shippingMethodText, 20);
        int shippingType = shippingMethods.size();
        click(shippingMethods.get(shippingType - 1));
        return true;
    }

    public boolean validateTotAfterShippingMethodChanged() {
        staticWait(3000);
        String promotions = "0";
        String item = getText(itemTotal).replaceAll("[^0-9.]", "");
        if (waitUntilElementDisplayed(promotionsTot, 5)) {
            promotions = getText(promotionsTot).replaceAll("[^0-9.]", "");
        }
        String shippingCharge = getText(shippingTot).replaceAll("[^0-9.]", "");
        String taxCharge = getText(taxTotal).replaceAll("[^0-9.]", "");
        String finalTotal = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float itemTot = Float.parseFloat(item);
        float promoTot = Float.parseFloat(promotions);
        float shipping = Float.parseFloat(shippingCharge);
        float tax = Float.parseFloat(taxCharge);
        float estimatedValue = Float.parseFloat(finalTotal);

        float total = (itemTot + shipping + tax) - promoTot;

        float roundTotal = roundFloat(total, 2);

        if (roundTotal == estimatedValue)
            return true;
        else
            return false;
    }

    public String getEstimateTotal() {

        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");
        return estimatedCost;
    }

    public boolean validateEstimatedTotalBeforeChange() {
        String item = getText(itemTotal).replaceAll("[^0-9.]", "");
        String shippingCharge = getText(shippingTot).replaceAll("[^0-9.]", "");
        String taxCharge = getText(taxTotal).replaceAll("[^0-9.]", "");
        String finalTotal = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float itemTot = Float.parseFloat(item);
        float shipping = Float.parseFloat(shippingCharge);
        float tax = Float.parseFloat(taxCharge);
        float estimatedValue = Float.parseFloat(finalTotal);
        float total = itemTot + shipping + tax;

        if (total == estimatedValue)
            return true;
        else
            return false;
    }

    public boolean editShippingAddressLink() {
        waitUntilElementDisplayed(editLink_ShipAddress, 20);
        click(editLink_ShipAddress);
        return waitUntilElementDisplayed(firstNameFld, 10);
    }

    public boolean clickAddressDropdown() {
        waitUntilElementDisplayed(dropDownLink, 20);
        click(dropDownLink);
        return waitUntilElementDisplayed(addNewAddress, 10);
    }

    public boolean clickAddNewAddress() {
        waitUntilElementDisplayed(addNewAddress, 20);
        click(addNewAddress);
        return waitUntilElementDisplayed(firstNameFld, 10);
    }

    public static float roundFloat(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public boolean checkAirmilesNo() {

        String airMilesDrawer = getAirMilesNo();
        String airMilesShipping = getText(airMilesShip).replaceAll("[^0-9]", "");

        if (airMilesDrawer == airMilesShipping)
            return true;
        else
            return false;
    }

    public String getAirMilesNo() {

        String airMiles = getText(airMilesNumb).replaceAll("[^0-9]", "");
        return airMiles;
    }

    public boolean validateAirmilesText(String airmilesText) {
        waitUntilElementDisplayed(airmiles_Text, 3);
        String text = getText(airmiles_Text);
        if (airmilesText.contains(text)) {
            return true;
        } else {
            addStepDescription("The text is not getting displayed properly in SB page");
            return false;
        }
    }

    public boolean clickNeedHelpLink() {
        waitUntilElementDisplayed(needHelp_Link);
        click(needHelp_Link);
        return waitUntilElementDisplayed(closeModal);
    }

    public boolean clickNextBillingAVS(BillingPageActions billingPageActions, String addressLine2, String error) {
        if (waitUntilElementDisplayed(editLink_ShipAddress, 5)) {
            click(editLink_ShipAddress);
            waitUntilElementDisplayed(saveButton);

            click(saveButton);
            if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
                if (isDisplayed(avsError)) {
                    checkAVSError(error);
                }
                if (waitUntilElementDisplayed(addressLine2AVS, 10)) {
                    click(addressLine2AVS);
                    clearAndFillText(addressLine2AVS, addressLine2);
                }
                click(addressVerificationContinue);
            }
            staticWait(7000); // to wait until save button closed if already clicked
            if (waitUntilElementDisplayed(saveButton, 5)) {
                click(saveButton);
            }
            waitUntilElementDisplayed(nextBillingButton, 30);
            click(nextBillingButton);
            waitUntilElementDisplayed(billingPageActions.creditCardRadioBox, 20);
            return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);
        }
        waitUntilElementDisplayed(nextBillingButton);
        click(nextBillingButton);
        staticWait(3000);
        if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
            if (waitUntilElementDisplayed(addressLine2AVS)) {
                click(addressLine2AVS);
                clearAndFillText(addressLine2AVS, addressLine2);
                staticWait(1000);
                // click(youEnterAddress);

            }
            click(addressVerificationContinue);
        }
        waitUntilElementDisplayed(billingPageActions.creditCardRadioBox, 30);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 30);
    }

    public boolean checkAVSError(String errorcopy) {
        waitUntilElementDisplayed(avsError, 1);
        String dispError = getText(avsError);
        if (dispError.equalsIgnoreCase(errorcopy)) {
            return true;
        } else {
            addStepDescription("error message is not getting displayed");
            return false;
        }

    }

    public boolean clickNextBilling_AVSErrorWithoutBuildNum() {
        if (isDisplayed(nextBillingButton)) {
            waitUntilElementDisplayed(nextBillingButton);
            click(nextBillingButton);
        }
        if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
            String addrVerifErr = "";
            try {
                addrVerifErr = getText(errorAtAddrVerifi);
            } catch (NullPointerException n) {

            }
            if (addrVerifErr.equalsIgnoreCase("The house / building number is not valid. Please review and confirm your address.")) {
                return true;
            } else {
                addStepDescription("<b><font color=#8b0000>The Address verification window is not throwing any error. Showing at AVS window " + addrVerifErr + " </font></b>");
                return false;
            }
        } else {
            addStepDescription("<b><font color=#8b0000>Address verification window is not displaying after clicking on next billing button. </font></b>");
            return false;
        }
    }

    public boolean clickAddressVeriContinue(BillingPageActions billingPageActions) {
        if (waitUntilElementDisplayed(addressVerificationContinue, 20)) {
            click(addressVerificationContinue);
        }
        if (isDisplayed(nextBillingButton)) {
            click(nextBillingButton);
        }
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 30);

    }

    public boolean enterShippingDetailsWithoutCHeckbox_Reg(BillingPageActions billingPageActions, String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        enterShippingDetailsWithoutCheckbox(fName, lName, addrLine1, addrLine2, city, state, zip, phNum, shipMethod);
        staticWait(5000);
        return clickNextBillingButton(billingPageActions);
    }

    public String getRTPSResponseOfUpdateShipping() {
        String response = getValueOfDataElement("response_payment/updateShippingMethodSelection");
        logger.info("processOLPSResponse of updateShippingMethodSelectionResponse function: " + response);
//        Map<String, String> responseCodeAndMessage = getOLPSResponseCodeAndMsg(response);
        return response;
    }

    public String getReturnCode() {
        String response = getValueOfDataElement("response_payment/updateShippingMethodSelection");
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

    public void getErrorCode() {
        String response = getValueOfDataElement("tcpstore/processPreScreenAcceptance");
        String returnCode = "returnCode=";
    }

    public boolean isProperResponseCodeAndMessage(Map<String, String> responseCodeAndMessage) {
        String codeKey = responseCodeAndMessage.get("ErrorCode");
        String msgValue = responseCodeAndMessage.get("ErrMessage");
        Boolean response = (codeKey.equals("10") || codeKey.equals("12")) && (msgValue.equalsIgnoreCase("NOHIT") || msgValue.equalsIgnoreCase("ACKNOWLEDGE"));
        return response;
    }


    public Map<String, String> getOLPSResponseCodeAndMsg(String response) {
        Map<String, String> responseErrCodeAndMsgs = new HashMap<>();
        String olpsKeyName = "processOLPSResponse", errMsg = "errorMessage", errCode = "errorCode";
        int startIndex = response.indexOf(olpsKeyName);
        String responseCodeAndMsg = response.substring(startIndex);
        responseCodeAndMsg = responseCodeAndMsg.replaceAll("\n","").replaceAll(" ","").replaceAll("\"","").replaceAll(":","");

        int errMsgStartIndex = responseCodeAndMsg.indexOf(errMsg) + errMsg.length();
        int errMsgEndIndex = responseCodeAndMsg.lastIndexOf("}}}");

        int errCodeStartIndex = responseCodeAndMsg.indexOf(errCode) + errCode.length();
        int errCodeEndIndex = responseCodeAndMsg.indexOf(",");
        String responseErrMessage = responseCodeAndMsg.substring(errMsgStartIndex, errMsgEndIndex);
        String responseErrCode = responseCodeAndMsg.substring(errCodeStartIndex, errCodeEndIndex);
        responseErrCodeAndMsgs.put("ErrorCode", responseErrCode);
        responseErrCodeAndMsgs.put("ErrMessage", responseErrMessage);
        return responseErrCodeAndMsgs;

    }

    public void enterShippingDetailsWithoutCheckbox(String fName, String lName, String addrLine1, String addrLine2, String city, String state, String zip, String phNum, String shipMethod) {
        if (!waitUntilElementDisplayed(editLink_ShipAddress, 5)) {
            waitUntilElementDisplayed(firstNameFld);
            clearAndFillText(firstNameFld, fName);
            clearAndFillText(lastNameFld, lName);
            clearAndFillText(addressLine1Fld, addrLine1);
            clearAndFillText(addressLine2Fld, addrLine2);
            clearAndFillText(cityFld, city);
            clearAndFillText(zipPostalCodeFld, zip);
            clearAndFillText(phNumFld, phNum);
            selectDropDownByValue(stateDropDown, state);
            staticWait();
            waitUntilElementDisplayed(saveToAddressBookCheckBox, 5);
        }
        staticWait();
    }

    public boolean validateMoreThan50ErrMsg(String errFName, String errLName,
                                            String errAddrLine1, String errCity,
                                            String errZipPostal) {

        waitUntilElementDisplayed(firstNameFld);
        clearAndFillText(firstNameFld, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(lastNameFld, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(addressLine1Fld, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(addressLine2Fld, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(cityFld, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(zipPostalCodeFld, "textmorethanfifetycharacterErrorMessageRelatedValidation");
        clearAndFillText(phNumFld, "9878787896");

        staticWait();
        selectShippingMethodFromRadioButtonByPos("1");
        String fName = getText(err_FirstName);
        String lName = getText(err_LastName);
        String adrLne = getText(err_AddressLine1);
        String city = getText(err_City);
        String zip = getText(err_POZipCode);

        if (fName.contains(errFName) && lName.contains(errLName) && adrLne.contains(errAddrLine1) && city.contains(errCity) && zip.contains(errZipPostal))
            return true;
        else
            return false;
    }

    public boolean applyCouponCode(String couponCode) {
        waitUntilElementDisplayed(couponCodeFld, 20);
        clearAndFillText(couponCodeFld,"");
        clearAndFillText(couponCodeFld, couponCode);
        click(applyCouponButton);
        boolean couponAppliedAtOrderLedger = waitUntilElementDisplayed(couponCodeApplied, 10);
        return couponAppliedAtOrderLedger && waitUntilElementDisplayed(appliedCouponText, 20);
    }

    public boolean applyCouponCodeErr(String couponCode) {
        waitUntilElementDisplayed(couponCodeFld, 20);
        clearAndFillText(couponCodeFld, couponCode);
        click(applyCouponButton);
        return waitUntilElementDisplayed(couponCodeError);
    }

    public boolean shippingInternationValidation() {
        waitUntilElementDisplayed(shipInternationalLnk, 4);
        click(shipInternationalLnk);

        if (isDisplayed(shipToHeader)) {
            return closemodal();
        } else
            return false;
    }

    public boolean closemodal() {
        waitUntilElementDisplayed(shipCloseModal, 4);
        click(shipCloseModal);
        return waitUntilElementDisplayed(nextBillingButton);
    }

    public boolean validatePriorityShippingMethods(ShoppingBagPageActions shoppingBagPageActions) {
        waitUntilElementDisplayed(shippingMethodText);
        String method = getText(shippingMethods.get(0));
        String actualMethod = "Priority - $10";
        String actualMethodFree = "Priority - FREE";
        if (method.contains(actualMethod) || method.contains(actualMethodFree)) {
            return returnToBag(shoppingBagPageActions);
        } else
            return false;
    }

    public boolean validateCAShippingMethods() {
        waitUntilElementDisplayed(shippingMethodText);
        String method = getText(shippingMethods.get(0));
        String actualMethod = "Ground 7 to 10 business days";
        String method1 = getText(shippingMethods.get(1));
        String actualMethod1 = "Express 3 to 4 business days";
        if (method.contains(actualMethod) && method1.contains(actualMethod1)) {
            return true;
        } else {
            return false;
        }
    }

    public int getShippingMethodsCount() {
        return getElementsSize(shippingMethods);
    }

    public boolean validateSplShippingMethods(ShoppingBagPageActions shoppingBagPageActions) {
        waitUntilElementDisplayed(shippingMethodText);
        String method = getText(shippingMethods.get(0));
        String actualMethod = "AK, HI, PR Express - $15";
        String method1 = getText(shippingMethods.get(1));
        String actualMethod1 = "AK, HI, PR Rush - $25";

        if (method.equals(actualMethod) && method1.equals(actualMethod1)) {
            return returnToBag(shoppingBagPageActions);
        } else
            return false;
    }

    public boolean returnToBag(ShoppingBagPageActions shoppingBagPageActions) {
        waitUntilElementDisplayed(tcpLogo, 3);
        click(tcpLogo);
        waitUntilElementDisplayed(returnToBag_Link, 10);
        click(returnToBag_Link);
        return waitUntilElementDisplayed(shoppingBagPageActions.checkoutBtn);
    }

    public boolean isEmailSignupChkBoxCheckedByDef() {
        return isSelected(signupEmail_ChkBox, signupEmail_ChkInput);

    }

    public boolean selectFreeGiftWrappingAndGiftMessage() {
        scrollToBottom();
        select(giftWrappingCheckbox, giftWrappingCheckboxInput);
        waitUntilElementDisplayed(giftWrappingOptions, 5);
        if (waitUntilElementDisplayed(giftWrappingMesBox)) {
            clearAndFillText(giftWrappingMesBox, "enter text message more than 100 characters in the field to check if the text is trimmed to 100 characters");//More than 100 Char
            String actaulText = getText(giftWrappingMesBox);
            if (actaulText.length() == 100) {//validate length of the text
                return true;
            } else return false;
        }
        return waitUntilElementDisplayed(nextBillingButton, 30);
    }

    public boolean uncheckGiftWrapping() {
        waitUntilElementDisplayed(giftWrappingCheckboxChecked);
        click(giftWrappingCheckboxChecked);
        if (!isDisplayed(giftWrappingOptions) && !isDisplayed(giftWrappingMessage)) {
            return true;
        } else {
            addStepDescription("giftWrapping is not removed properly");
            return false;
        }
    }

    public boolean cancelEditShipping() {
        waitUntilElementDisplayed(cancelEditShipping, 3);
        click(cancelEditShipping);
        if (isDisplayed(editLink_ShipAddress)) {
            return true;
        } else {
            addStepDescription("Cancel button is not working properly in Shipping Page");
            return false;
        }
    }

    public boolean storeChangeDefaultAddDisplay() {
        waitUntilElementDisplayed(nextBillingButton, 3);
        if (!isDisplayed(dropDownLink)) {
            return true;
        } else {
            addStepDescription("US address is getting displayed in CA and ViceVersa");
            return false;
        }
    }

    public boolean defaultTextCheck(String firstName, String lastName, String phone, String email) {
        String fName = getAttributeValue(firstNameFld, "value");
        String lName = getAttributeValue(lastNameFld, "value");
        String mob = getAttributeValue(phNumFld, "value");

        boolean fields = fName.equalsIgnoreCase(firstName) && lName.equalsIgnoreCase(lastName) && mob.equalsIgnoreCase(phone);
        if (isDisplayed(emailAddressFld)) {
            String preEmail = getAttributeValue(emailAddressFld, "value");
            fields = fields && preEmail.equalsIgnoreCase(email);
        }

        if(!fields){
            addStepDescription("Fields are not populated with correct data");
            return false;
        }
        return fields;
    }

    /**
     * Validate progress indicators for all in Shipping Page
     *
     * @return true if all matches
     */
    public boolean validateProgressbarStatus() {
        waitUntilElementDisplayed(nextBillingButton, 10);
        String shippingProgressBarStatus = getAttributeValue(shippingProgressBar, "class");
        String billingProgressBarStatus = getAttributeValue(billingProgressBar, "class");
        String title = getText(checkoutTitle);
        String reviewProgressBarStatus = getAttributeValue(reviewProgressBar, "class");

        return shippingProgressBarStatus.equalsIgnoreCase("active") &&
                billingProgressBarStatus.equalsIgnoreCase("") &&
                reviewProgressBarStatus.equalsIgnoreCase("") &&
                title.equalsIgnoreCase("Shipping");
    }

    public boolean checkTheFreeShip() {
        if (isDisplayed(standardShipText)) {
            String standaradShip = getText(standardShipText);
            if (standaradShip.equalsIgnoreCase("Standard - Free")) {
                selectShippingMethodFromRadioButtonByPos("2");
            }
            return waitUntilElementDisplayed(shippingTotalPrice, 2);
        }
        else{
            return true;
        }
    }
    public boolean checkTransactionalSMSCheckbox(){
        if(isDisplayed(transactionalSMSCheckboxUnChecked)){
            return true;
        }
        else{
            addStepDescription("SMS checkbox is not displayed or checked by Default");
            return false;
        }
    }

    public boolean checkSMSFieldsDisplay(){
       select(transactionalSMSCheckbox,transactionalSMSCheckboxInput);
        if(isDisplayed(smsPrivacyPolicy) && isDisplayed(transactionalNumber)){
            return true;
        }
        else{
            addStepDescription("Some of the fields are missing in the Transactional SMS section");
            return false;
        }
    }
    public void enterNumberInSMSField(String number){
        waitUntilElementDisplayed(transactionalNumber);
        clearAndFillText(transactionalNumber,number);
    }

    public boolean checkTheErrorMessageInSMSField(String text,String error){
        clearAndFillText(transactionalNumber,"");
        enterNumberInSMSField(text);
        click(nextBillingButton);
        String errorDisplayed = getText(smsInlineErrorMessage);
        if(errorDisplayed.contains(error)){
            return true;
        }
        else{
            addStepDescription("check the error copy");
            return false;
        }
    }
    public boolean checkShippingMethodSection( String store) {
        if (store.equalsIgnoreCase("US")) {
            if (isDisplayed(rushShippingMethod)) {
                addStepDescription("Rush is displayed in the Shipping method");
                return false;
            } else {
                return true;
            }
        } else {
            if(isDisplayed(ExpressShippingCA)){
                addStepDescription("Express shipping is displayed in the Shipping method");
                return false;
            } else {
                return true;
            }
            }

        }
}

