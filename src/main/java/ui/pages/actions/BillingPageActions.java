package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.sikuli.script.FindFailed;
import ui.pages.repo.BillingPageRepo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * Created by skonda on 5/19/2016.
 */
public class BillingPageActions extends BillingPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(BillingPageActions.class);
    public String prodName;
    public String prodSize;
    public String prodColor;
    public String prodQuantity;
    public String prodPrice;

    private String itemsTotal;
    private String shippingTotal;
    private String estTax;
    private String estTotal;

    public String itemsTotalPr;
    public String shipTotalPr;
    public String estTaxPrPr;
    public String estTotalPr;

    public BillingPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public String getItemsTotal() {
        return itemsTotal;
    }

    public void setItemsTotal(String itemsTotal) {
        this.itemsTotal = itemsTotal;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public String getEstTax() {
        return estTax;
    }

    public void setEstTax(String estTax) {
        this.estTax = estTax;
    }

    public String getEstTotal() {
        return estTotal;
    }

    public void setEstTotal(String estTotal) {
        this.estTotal = estTotal;
    }

    public boolean isShippingPresentAtOrdSummary() {
        for (WebElement lineItems : orderSummaryLineItems) {
            return getText(lineItems).toLowerCase().contains("shipping");
        }
        return false;
    }

    public void getOrderAmountDetails() {
        setItemsTotal(getText(itemsTotalPrice));
        itemsTotalPr = getItemsTotal();

        if (isShippingPresentAtOrdSummary()) {
            setShippingTotal(getText(shippingTotalPrice));
            shipTotalPr = getShippingTotal();

        }

        setEstTax(getText(tax_Total));
        estTaxPrPr = getEstTax();

        setEstTotal(getText(estimatedTotal));
        estTotalPr = getEstTotal();

    }

    public boolean enterPaymentDetailsAsGuestAndReg_SameAsShip(ReviewPageActions reviewPageActions, String ccNum, String secCode, String expMonth) {
        /*try {
            waitUntilElementDisplayed(sameAsShippingChkBox, 5);
            if (!isChecked(sameAsShippingChkBoxValue)) {
                click(sameAsShippingChkBox);
            }
        } catch (Throwable t) {
            logger.info("The Same As Shipping Check box is already checked");
        }*/
        select(sameAsShippingChkBox, sameAsShippingChkBoxinput);
        unSelect(saveToAccountCheckBoxNew, saveToAccountCheckBoxNewinput);
        return payWithACreditCard(reviewPageActions, ccNum, secCode, expMonth);

    }

    public boolean enterPLCCDetailsAsGuestAndReg_SameAsShip(ReviewPageActions reviewPageActions, String ccNum) {
        try {
            waitUntilElementDisplayed(sameAsShippingChkBox, 5);
            if (!isChecked(sameAsShippingChkBoxValue)) {
                click(sameAsShippingChkBox);
            }
        } catch (Throwable t) {
            logger.info("The Same As Shipping Check box is already checked");
        }
        return enterPLCCDetails(reviewPageActions, ccNum);

    }

    public boolean clickSaveButton() {
        if (isDisplayed(saveButton)) {
            click(saveButton);
        }
        return waitUntilElementClickable(nextReviewButton, 30);
    }

    public boolean payWithACreditCard(ReviewPageActions reviewPageActions, String ccNum, String secCode, String expMonth) {
        waitUntilElementDisplayed(nextReviewButton, 5);
        enterCardDetails(ccNum, secCode, expMonth);
        getOrderAmountDetails();
        return clickNextReviewButton(reviewPageActions, secCode);
    }

    public void payWithACreditCardAndSaveToAcct(ReviewPageActions reviewPageActions, String ccNum, String secCode, String expMonth) {
        waitUntilElementDisplayed(nextReviewButton, 5);
        enterCardDetails(ccNum, secCode, expMonth);
        getOrderAmountDetails();
        select(saveToAccountCheckBox, saveToAccountCheckBoxNewinput);

    }

    public void enterCardDetails(String ccNum, String secCode, String expMonth) {
        waitUntilElementDisplayed(creditCardRadioBox, 2);
        String expYear = getFutureYearWithCurrentDate("YYYY", 7);
        select(creditCardRadioBox, creditCardRadioBoxinput);

        if (waitUntilElementDisplayed(editLinkOnCard, 1)) {
            click(editLinkOnCard);
            waitUntilElementDisplayed(cardNumField, 10);
            clearAndFillText(cardNumField, ccNum);
            selectDropDownByValue(expMonDropDown, expMonth);
            selectDropDownByVisibleText(expYrDopDown, expYear);

        } else {
            waitUntilElementDisplayed(cardNumField, 5);
            clearAndFillText(cardNumField, ccNum);
            selectDropDownByValue(expMonDropDown, expMonth);
            selectDropDownByVisibleText(expYrDopDown, expYear);

        }
        clickSaveButton();
        waitUntilElementDisplayed(cvvFld, 10);
        clearAndFillText(cvvFld, secCode);
    }

    public boolean clickNextBtnTocheckCvvNResponse(int counter, String secCode, String card, ReviewPageActions reviewPageActions) {
        boolean check = false;
        switch (card) {
            case "Discover":
                for (int i = 0; i < counter; i++) {
                    waitUntilElementDisplayed(nextReviewButton, 25);
                    click(nextReviewButton);
                    if (!waitUntilElementDisplayed(errorBox) && getReturnCode()) {
                        check = false;
                        break;
                    } else {
                        staticWait(2000);
                        check = true;
                    }
                }
                return check;
            case "Visa":
                for (int i = 0; i < counter; i++) {
                    waitUntilElementDisplayed(nextReviewButton, 25);
                    click(nextReviewButton);
                    if (!waitUntilElementDisplayed(errorBox) && getReturnCode()) {
                        check = false;
                        break;
                    } else {
                        staticWait(2000);
                        check = true;
                    }
                }
                return check;

            case "Master Card":
                for (int i = 0; i < counter; i++) {
                    waitUntilElementDisplayed(nextReviewButton, 25);
                    click(nextReviewButton);
                    if (!waitUntilElementDisplayed(errorBox) && getReturnCode()) {
                        check = false;
                        break;
                    } else {
                        staticWait(2000);
                        check = true;
                    }
                }
                return check;


            case "AMEX":

                for (int i = 0; i < counter; i++) {
                    waitUntilElementDisplayed(cvvFld, 5);
                    clearAndFillText(cvvFld, "");
                    clearAndFillText(cvvFld, secCode);
                    click(nextReviewButton);
                    waitUntilElementDisplayed(reviewPageActions.submOrderButton, 25);
                    click(reviewPageActions.submOrderButton);
                    waitUntilElementDisplayed(nextReviewButton, 20);
                    if (!waitUntilElementDisplayed(errorBox) && getReturnCode()) {
                        check = false;
                        break;
                    } else {
                        staticWait(2000);
                        check = true;
                    }
                }
                return check;
        }
        addStepDescription("CVV N Response is not getting displayed properly for" + counter + "times");
        return false;
    }

    public boolean getReturnCode() {
        String response = getValueOfDataElement("response_payment/addPaymentInstruction");
        logger.info("processOLPSResponse of updateShippingMethodSelectionResponse function: " + response);
        String returnCode = "\"errorCode\":\"";
        String errocode = "\",\"errorKey\"";
        int i = response.indexOf(returnCode) + returnCode.length();
        int j = response.indexOf(errocode);
        return response.substring(i, j).trim().equalsIgnoreCase("_TCP_CVV_REQUEST_FAILED_WITH_CREDIT_CARD_AUTH_ERROR");
    }

    public void enterCouponCodeAndApply(String couponCode) {
        clearAndFillText(couponCodeFld, couponCode);
        click(applyCodeButton);
    }


   public boolean isMultipleCouponsApplied(List<String> couponCodes) {
        int appliedCouponsCount = 0;
        for (String couponCode : couponCodes) {
            for (int i = 0; i < discountRowAppliedElements.size(); i++)
                if (waitForTextToPresent(couponCode, discountRowAppliedElements.get(i), 20)) {
                    appliedCouponsCount++;
                }
        }
        if (appliedCouponsCount == couponCodes.size()) {
            return true;
        } else {
            return false;
        }
    }

   public boolean fillPaymentAddrDetailsWithoutSameAsShip(String fName, String lName, String addrLine1, String city_Payment, String state, String zip) {
        if (isDisplayed(editLinkOnCard)) {
            click(editLinkOnCard);
            waitUntilElementDisplayed(cardNumField, 20);
            unSelect(sameAsShippingChkBox, sameAsShippingChkBoxinput);
            waitUntilElementDisplayed(firstName, 10);
            clearAndFillText(firstName, fName);
            clearAndFillText(lastName, lName);
            clearAndFillText(addressLine1, addrLine1);
            clearAndFillText(cityFld, city_Payment);
            clearAndFillText(zipCode, zip);
            selectDropDownByValue(stateDropDown, state);
            if (isDisplayed(saveButton)) {
                // select(saveToAccountCheckBoxNew, saveToAccountCheckBoxNewinput);
                click(saveButton);
            }
            return waitUntilElementDisplayed(nextReviewButton, 30);
        }
        unSelect(sameAsShippingChkBox, sameAsShippingChkBoxinput);
        if (!waitUntilElementDisplayed(firstName, 10)) {
            if (isDisplayed(dropDownLink)) {
                clickAddressDropdown();
                clickAddNewAddress();
            }
        } else
            waitUntilElementDisplayed(firstName, 10);
        clearAndFillText(firstName, fName);
        clearAndFillText(lastName, lName);
        clearAndFillText(addressLine1, addrLine1);
        clearAndFillText(cityFld, city_Payment);
        clearAndFillText(zipCode, zip);
        selectDropDownByValue(stateDropDown, state);
        unSelect(saveToAccountCheckBoxNew, saveToAccountCheckBoxNewinput);
        scrollDownToElement(nextReviewButton);
        return waitUntilElementDisplayed(nextReviewButton, 30);
    }

    public boolean enterPaymentAndAddressDetails_GuestAndRegUser(ReviewPageActions reviewPageActions, String fName, String lName, String addrLine1, String addrLine2, String city_Payment, String state, String zip, String ccNum, String secCode, String expMonth) {
        waitUntilElementDisplayed(nextReviewButton, 20);
        fillPaymentAddrDetailsWithoutSameAsShip(fName, lName, addrLine1, city_Payment, state, zip);
        staticWait();
        return payWithACreditCard(reviewPageActions, ccNum, secCode, expMonth);
    }

    public boolean enterPaymentAndAddress_GuestAndReg_SaveToAcct(ReviewPageActions reviewPageActions, String fName, String lName, String addrLine1, String addrLine2, String city_Payment, String state, String zip, String ccNum, String secCode, String expMonth) {
        waitUntilElementDisplayed(nextReviewButton, 20);
        fillPaymentAddrDetailsWithoutSameAsShip(fName, lName, addrLine1, city_Payment, state, zip);
        staticWait();
        payWithACreditCardAndSaveToAcct(reviewPageActions, ccNum, secCode, expMonth);
        return clickNextReviewButton(reviewPageActions, secCode);
    }

    public boolean editPaymentAndAddressDetails_GuestAndRegUser(ReviewPageActions reviewPageActions, String fName, String lName, String addrLine1, String addrLine2, String city_Payment, String state, String zip, String ccNum, String secCode, String expMonth) {
        waitUntilElementDisplayed(nextReviewButton, 10);
        fillPaymentAddrDetailsWithoutSameAsShip(fName, lName, addrLine1, city_Payment, state, zip);
        staticWait();
        return payWithACreditCard(reviewPageActions, ccNum, secCode, expMonth);
    }

    public boolean clickNextReviewButton(ReviewPageActions reviewPageActions, String secCode) {
        try {
            scrollDownUntilElementDisplayed(nextReviewButton);
            if (waitUntilElementDisplayed(cvvFld, 10)) {
                clearAndFillText(cvvFld, secCode);
            }
            click(nextReviewButton);
            return waitUntilElementDisplayed(reviewPageActions.submOrderButton, 30);
        } catch (Throwable t) {
            scrollUpToElement(creditCardRadioBox);
//            click(nextReviewButton);
            return waitUntilElementDisplayed(reviewPageActions.submOrderButton, 30);
        }

    }

    public boolean clickNextReviewButtonWithoutCVV(ReviewPageActions reviewPageActions) {
        try {
            scrollDownUntilElementDisplayed(nextReviewButton);
            click(nextReviewButton);
            return waitUntilElementDisplayed(reviewPageActions.submOrderButton, 30);
        } catch (Throwable t) {
            addStepDescription("Next review button either element changed or unable to find next review button");
            return false;
        }

    }

    public boolean enterCvvAndClickNextReviewButton(ReviewPageActions reviewPageActions, String secCode) {
        waitUntilElementDisplayed(nextReviewButton, 20);
        clearAndFillText(cvvFld, secCode);
        try {
            scrollDownUntilElementDisplayed(nextReviewButton);
            click(nextReviewButton);
            return waitUntilElementDisplayed(reviewPageActions.submOrderButton,30);
        } catch (Throwable t) {
            return false;
        }
    }

    public boolean enterExpCvvAndClickNextReviewButton(ReviewPageActions reviewPageActions) {
        waitUntilElementDisplayed(nextReviewButton, 20);
        clearAndFillText(cvvExpressFld, "678");
        try {
            scrollDownUntilElementDisplayed(nextReviewButton);
            click(nextReviewButton);
            return waitUntilElementDisplayed(reviewPageActions.submOrderButton);
        } catch (Throwable t) {
            click(nextReviewButton);
            return waitUntilElementDisplayed(reviewPageActions.submOrderButton, 30);
        }
    }

    public boolean enterPLCCDetails(ReviewPageActions reviewPageActions, String placeCardNumber) {
        staticWait(4000);
        clearAndFillText(cardNumField, placeCardNumber);
        unSelect(saveToAccountCheckBoxNew, saveToAccountCheckBoxNewinput);
        return clickNextReviewButton(reviewPageActions, "");
    }

    public boolean payWithPayPal() {
        if (isDisplayed(proceedWithPaPalButton)) {
            click(proceedWithPaPalButton);
            staticWait(2000);
            return waitUntilElementDisplayed(submOrderButton,3);
        } else {
            waitUntilElementDisplayed(paypalRadioButton, 10);
            // jqueryClick(paypalRadioBox);
            click(paypalRadioButton);
            staticWait(2000);
            scrollToBottom();
            click(proceedWithPaPalButton);
            staticWait(2000);
            return switchToPaypalWindow();

        }
    }

    public boolean switchToContinuePayPalFrameIfAvailable() {
        boolean isIFrameAvailable = waitUntilElementDisplayed(continuePayPalFrame, 30);
        if (isIFrameAvailable) {
            switchToFrame(continuePayPalFrame);
        }
        return isIFrameAvailable;
    }

    public boolean validatePayPalText(String text, String text2) {
        waitUntilElementDisplayed(payPalText1, 2);
        String value = getText(payPalText1).replaceAll(",", "");
        String value1 = getText(payPalText2).replaceAll("/n", "");
        if (value.contains(text) && value1.equalsIgnoreCase(text2)) {
            return true;
        } else {
            addStepDescription("Check the text displayed in Billing page");
            return false;
        }
    }

    public boolean switchToPaypalWindow(){
        String currentWindow = driver.getWindowHandle();
        staticWait(5000);
        switchToWindow(currentWindow);
        return waitUntilElementDisplayed(payPalLoginButton,9);
    }

    public boolean clickProceedToPaypalWithAlreadyLogin(ReviewPageActions reviewPageActions) {
        waitUntilElementDisplayed(proceedPaypalBtn, 5);
        click(proceedPaypalBtn);
        return waitUntilElementDisplayed(reviewPageActions.submOrderButton, 20);
    }


    public void clickSameAsShipAddress() {
        if (!isSelected(sameAsShippingChkBox)) {
            click(sameAsShippingChkBox);
        }
    }

     public boolean enterInvalidPromoCode(String promoCode) {
        waitUntilElementDisplayed(couponCodeFld, 10);
        clearAndFillText(couponCodeFld, promoCode);
        applyCodeButton.click();
        return waitUntilElementDisplayed(errorBox, 10);
    }

    public Boolean applyGiftCardToOrder(String giftCardNum, String pin, String isValid) {

        if (verifyElementNotDisplayed(giftCardNumber, 3))
            click(payWithGiftCardElement);
        waitUntilElementDisplayed(giftCardNumber);
        clearAndFillText(giftCardNumber, giftCardNum);
        clearAndFillText(giftCardPin, pin);
        click(checkGiftCardBalanace);
        waitUntilElementDisplayed(availableBalance);

        if (isValid.equalsIgnoreCase("valid")) {

            if (isGiftCardBalanceAvailable()) {
                click(applyToOrder);
                return verifyElementNotDisplayed(applyToOrder);
            } else {
                return false;
            }
        } else {

            return waitUntilElementDisplayed(giftCardError, 5);

        }

    }

    public boolean updateGiftCardBalanceByAmount(String giftCardNum, String pin, String amtBalance) {
        if (verifyElementNotDisplayed(giftCardNumber, 3))
            click(payWithGiftCardElement);
        waitUntilElementDisplayed(giftCardNumber);
        clearAndFillText(giftCardNumber, giftCardNum);
        clearAndFillText(giftCardPin, pin);
        click(checkGiftCardBalanace);
        waitUntilElementDisplayed(availableBalance);
        if (isGiftCardBalanceAvailable()) {
            if (setAttributeValue(balanceElement, "value", amtBalance)) {
                click(applyToOrder);
                verifyElementNotDisplayed(applyToOrder);
            } else {
                throw new IllegalStateException("The GiftCard balance is not set to expected amount " + amtBalance);
            }
            return true;
        } else {
            logger.info("The Gift Card balance is not available");
            return false;

        }
    }

    public String getAvailableBalance() {
        String availBalance = getText(availableBalance);
        int startIndex = availBalance.indexOf("$");
        availBalance = availBalance.substring(startIndex);
        logger.info("The avaialble Balanceis: " + availBalance);
        return availBalance.replace("$", "");
    }

    public boolean isGiftCardBalanceAvailable() {
        double availBalance = Double.parseDouble(getAvailableBalance());
        if (availBalance > 0.00) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFreeShippingApplied() {
        boolean isPromotionLblDisplaying = waitUntilElementDisplayed(promotionsLabel, 1);
        boolean isShipDiscDisplaying = waitUntilElementDisplayed(shippingTotalPrice, 1);

        if (isShipDiscDisplaying && isPromotionLblDisplaying) {
            return getText(promotionsLabel).equalsIgnoreCase("Promotions") && getText(shippingTotalPrice).equalsIgnoreCase("Free");
        }
        return false;


    }
    public boolean enterCVVForExpressAndClickNextReviewBtn(String codeCVV, ReviewPageActions reviewPageActions) {

        waitUntilElementDisplayed(cvvExpressFld, 10);
        clearAndFillText(cvvExpressFld, codeCVV);
        return clickNextReviewButton(reviewPageActions, codeCVV);
    }

    public boolean validateEmptyFieldErrMessages(String errFName, String errLName,
                                                 String errAddrLine1, String errCity,
                                                 String errState, String errZipPostal) {

        waitUntilElementDisplayed(nextReviewButton, 2);
        unSelect(sameAsShippingChkBox, sameAsShippingChkBoxinput);
        clearAndFillText(firstName, "");
        clearAndFillText(lastName, "");
        clearAndFillText(addressLine1, "");
        clearAndFillText(cityFld, "");
        clearAndFillText(zipCode, "");
        click(firstName);

        boolean fNameErr = getText(errNoFirstName).contains(errFName);
        boolean lNameErr = getText(errNoLastName).contains(errLName);
        boolean addressLine1Err = getText(errNoAddr1).contains(errAddrLine1);
        boolean cityErr = getText(errNoCity).contains(errCity);
        boolean zipErr = getText(errNoZipPO).contains(errZipPostal);

        if (fNameErr && lNameErr && addressLine1Err && cityErr && zipErr)
            return true;
        else
            return false;
    }

    public boolean validateSpecialCharacterErrorMessage(String splChar, String errFName, String errLName, String errAdrLine1, String errCity, String errPOZip) {
        clearAndFillText(firstName, splChar);
        clearAndFillText(lastName, splChar);
        clearAndFillText(addressLine1, splChar);
        clearAndFillText(addressLine2, splChar);
        clearAndFillText(cityFld, splChar);
        clearAndFillText(zipCode, splChar);
        click(firstName);

        return getText(errNoFirstName).contains(errFName) && getText(errNoLastName).contains(errLName) && getText(errNoAddr1).contains(errAdrLine1) && getText(errNoAddr2).contains(errAdrLine1)
                && getText(errNoCity).contains(errCity) && getText(errNoZipPO).contains(errPOZip);
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
        return waitUntilElementDisplayed(nextReviewButton, 10);
    }

    public boolean createAccountFromESpot(CreateAccountActions createAccountActions, String email, String fName, String lName, String password, String zip, String phone) {
        clickCreateAccountOnESpot(createAccountActions);
        createAccountActions.createAnAccount_UserInputEmail(email, fName, lName, password, zip, phone);
        return waitUntilElementDisplayed(nextReviewButton, 10);
    }

    public boolean validateEstimatedTotal() {
        String promotions = "0";
        String shippingCharge = "0";
        String item = getText(itemTotal).replaceAll("[^0-9.]", "");
        if (waitUntilElementDisplayed(promotionsTot, 5)) {
            promotions = getText(promotionsTot).replaceAll("[^0-9.]", "");
        }

        if (!getText(shippingTot).equalsIgnoreCase("FREE"))
            shippingCharge = getText(shippingTot).replaceAll("[^0-9.]", "");

        String taxCharge = getText(taxTotal).replaceAll("[^0-9.]", "");
        String finalTotal = getText(estimatedTot).replaceAll("[^0-9.]", "");

        float itemTot = Float.parseFloat(item);
        float promoTot = Float.parseFloat(promotions);
        float shipTot = Float.parseFloat(shippingCharge);
        float tax = Float.parseFloat(taxCharge);
        float estimatedValue = Float.parseFloat(finalTotal);
        float total = (itemTot + shipTot + tax) - promoTot;

        float roundTotal = roundFloat(total, 2);

        if (roundTotal == estimatedValue)
            return true;
        else
            return false;
    }

    public boolean returnToBagPage(ShoppingBagPageActions shoppingBagPageActions) {
        click(returnBagLink);
        waitUntilElementDisplayed(overlayContent, 10);
        click(returnBagBtn);
        staticWait(1000);
        return waitUntilElementDisplayed(shoppingBagPageActions.checkoutBtn, 30);
    }

    public boolean validateTotInShippingAndBillingPage(String estimatedTotal) {
        staticWait(3000);
        String orderTotal = getText(estimatedTot).replaceAll("[^0-9.]", "");

        float orderTot = Float.parseFloat(orderTotal);
        float estimatedCost = Float.parseFloat(estimatedTotal);

        if (orderTot == estimatedCost)
            return true;
        else
            return false;
    }

    public boolean returnToShippingPage(ShippingPageActions shippingPageActions) {
        waitUntilElementDisplayed(rtnToShippingpageLnk, 20);
        click(rtnToShippingpageLnk);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 20);
    }

    public boolean selectCCPaymentMethod() {
        waitUntilElementDisplayed(proceedPaypalBtn, 20);
        click(creditCardRadioBox);
        waitUntilElementDisplayed(changePaymentBtn, 20);
        click(changePaymentBtn);
        return waitUntilElementDisplayed(nextReviewButton, 20);
    }

    public boolean validateBillingWithDefaultAdd() {
        waitUntilElementDisplayed(nextReviewButton, 10);
        if (waitUntilElementDisplayed(paymentMethod, 10) &&
                waitUntilElementDisplayed(cvvExpressFld, 10) &&
                waitUntilElementDisplayed(billingAddress, 10))
            return true;
        else
            return false;
    }

    public boolean validateCardSection() {
        if (waitUntilElementDisplayed(cardNumField, 10) &&
                waitUntilElementDisplayed(cvvFld, 10))
            return true;
        else
            return false;
    }

    public boolean clickCCDropdown() {
        waitUntilElementDisplayed(selectCCDropdown, 20);
        click(selectCCDropdown);
        return waitUntilElementDisplayed(addNewCC, 20);
    }

    public boolean clickAddNewCC() {
        waitUntilElementDisplayed(addNewCC, 20);
        click(addNewCC);
        return waitUntilElementDisplayed(cardNumField, 20);
    }

    public String getEstimateTotal() {

        String estimatedCost = getText(estimatedTot).replaceAll("[^0-9.]", "");
        return estimatedCost;
    }


    public static float roundFloat(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

    public boolean clickAddressDropdown() {
        waitUntilElementDisplayed(dropDownLink, 20);
        click(dropDownLink);
        return waitUntilElementDisplayed(addNewAddress, 10);
    }

   public boolean clickAddNewAddress() {
        waitUntilElementDisplayed(addNewAddress, 20);
        click(addNewAddress);
        return waitUntilElementDisplayed(firstName, 10);
    }

    public boolean checkAirmilesNo() {

        String airMilesDrawer = getAirMilesNo();
        String airMilesBilling = getText(airMilesBill).replaceAll("[^0-9]", "");

        if (airMilesDrawer.equalsIgnoreCase(airMilesBilling))
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

    public boolean enterPaymentAddressWithDiffCountry(ReviewPageActions reviewPageActions, String fName, String lName, String addrLine1, String addrLine2, String city_Payment, String state, String zip, String ccNum, String secCode, String expMonth, String country) {
        scrollDownToElement(countryDropDown);
        unSelect(sameAsShippingChkBox, sameAsShippingChkBoxinput);
        selectDropDownByValue(countryDropDown, country);
        editPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, fName, lName, addrLine1, addrLine2, city_Payment, state, zip, ccNum, secCode, expMonth);
        staticWait();
        if (isDisplayed(saveButton)) {
            click(saveButton);
        }
        return waitUntilElementDisplayed(reviewPageActions.submitOrderBtn, 10);
    }


    public boolean clickApplyOnGiftCard() {
        if (isDisplayed(applyButtonOnGC)) {
            click(applyButtonOnGC);
        }
        return waitUntilElementDisplayed(removeButtonOnGC, 30);
    }

    public boolean isRemainingBalanceNotEditable() {
        String remainBalReadOnly;

        try {
            remainBalReadOnly = getAttributeValue(remainingBalOnGC, "readonly");
            if (remainBalReadOnly == (null) || remainBalReadOnly.isEmpty()) {
                return true;
            }
        } catch (NullPointerException n) {
            return true;
        }
        return false;

    }

    public boolean clickRemoveGCAndVerifyOrderToatl() {
        double orderTotal = Double.valueOf(getEstimateTotal());
        double gcPrice = getGiftCardTotalApplied();
        try {
            click(removeButtonOnGC);
        } catch (Exception e) {
            logger.debug("removeButtonOnGC at billing ino exception: " + e.getMessage());
            addStepDescription("Clicking on Remove Button on GC threw exception");
        }
        waitUntilElementDisplayed(applyButtonOnGC, 30);
        double orderTotalAfterRemoveGC = (double) Math.round((orderTotal + gcPrice) * 100) / 100;
        if(isDisplayed(appliedGCCopy)){
            addStepDescription("Gift card additional copy is not removed from billing page");
            return false;
        }
        return orderTotalAfterRemoveGC == Double.valueOf(getEstimateTotal());
    }

    public boolean verifyPrepopulatedCard(String ccNum) {
        waitUntilElementDisplayed(cardNumField, 4);
        String getLast4Num = getText(cardNumberDisplayed).replaceAll("[^0-9]", "");
        String lastDigit = getLastFourDigitsOfCCNumber(ccNum);

        if (getLast4Num.equals(lastDigit)) {
            return true;
        } else
            return false;
    }

    public boolean verifyAddressPrepopulated(String fNameExp, String lNameExp, String add1Exp, String cityExp, String stateExp, String zipExp) {
        waitUntilElementDisplayed(cardNumber, 4);
        String flName = getText(flNameBillAddress).replaceAll(" ", "").trim();
        add1Exp = add1Exp.replaceAll(" ", "").trim();
        String billingAddr = getText(billAddrDisplay).replaceAll("[^a-zA-Z0-9]", "");
        return flName.equalsIgnoreCase(fNameExp + lNameExp) && billingAddr.equalsIgnoreCase(add1Exp + cityExp + stateExp + zipExp);
    }

    public boolean verifyTheCheckbox() {
        waitUntilElementDisplayed(defaultPaymentUnchecked, 4);
        if (waitUntilElementDisplayed(defaultPaymentUnchecked, 4)) {
            return true;
        } else
            return false;
    }


    public boolean clickOnShippingAccordian(ShippingPageActions shippingPageActions) {
        waitUntilElementDisplayed(shippingAccordian, 4);
        click(shippingAccordian);
        if (waitUntilElementDisplayed(shippingPageActions.nextBillingButton)) {
            return true;
        } else
            return false;
    }

    public boolean validateMoreThan50ErrMsg(String errFName, String errLName,
                                            String errAddrLine1, String errCity,
                                            String errZipPostal, String err) {

        unSelect(sameAsShippingChkBox, sameAsShippingChkBoxinput);
        waitUntilElementDisplayed(firstName);
        clearAndFillText(firstName, err);
        clearAndFillText(lastName, err);
        clearAndFillText(addressLine1, err);
        clearAndFillText(cityFld, err);
        clearAndFillText(zipCode, err);
        click(firstName);
        System.out.println(getText(errNoFirstName).contains(errFName));
        System.out.println(getText(errNoLastName).contains(errLName));
        System.out.println(getText(errNoAddr1).contains(errAddrLine1));
        System.out.println(getText(errNoCity).contains(errCity));
        System.out.println(getText(errNoZipPO).contains(errZipPostal));


        if (getText(errNoFirstName).contains(errFName) && getText(errNoLastName).contains(errLName) && getText(errNoAddr1).contains(errAddrLine1) && getText(errNoCity).contains(errCity) && getText(errNoZipPO).contains(errZipPostal))
            return true;
        else
            return false;
    }


    public boolean addrDropDownDisplayOnSameAsShipUnSelect() {
        if (isSelected(sameAsShippingChkBox)) {
            click(sameAsShippingChkBox);
        }
        return waitUntilElementDisplayed(dropDownLink, 10);
    }

    public boolean selectAddressFromDropDown() {

        waitUntilElementDisplayed(dropDownLink, 20);
        click(dropDownLink);
        waitUntilElementDisplayed(firstAddressFromDropDown, 4);
        click(firstAddressFromDropDown);
        waitUntilElementDisplayed(saveButton, 3);
        click(saveButton);
        return waitUntilElementDisplayed(nextReviewButton, 10);
        //   payWithACreditCard(reviewPageActions, "456", "", "")

    }

    public boolean validateGiftcard() {
        waitUntilElementDisplayed(addNewGC, 4);
        click(addNewGC);
        waitUntilElementDisplayed(gcContent, 4);
        if (isDisplayed(cardNumberField) && isDisplayed(pinNumberField) && isDisplayed(cancelGCButoon)
                && isDisplayed(applyGCButton1)) {
            return true;
        } else
            return false;
    }

    public void enterGiftCardDetails(String gc, String pin) {
        waitUntilElementDisplayed(addNewGC, 4);
        click(addNewGC);
        waitUntilElementDisplayed(gcContent, 4);
        fillText(cardNumberField, gc);
        fillText(pinNumberField, pin);
        click(applyGCButton1);
        waitUntilElementDisplayed(removeButtonOnGC);
    }

    public boolean checkGiftCardCopyMessage(String message) {
        if (isDisplayed(appliedGCCopy)) {
            String textDisplayed = getText(appliedGCCopy);
            if (textDisplayed.contains(message)) {
                return true;
            } else {
                addStepDescription("Check the additional copy displayed in billing page with the error copy");
                return false;
            }
        }
        else{
                addStepDescription("Gift card additional copy is not displayed in billing page");
                return false;
        }
    }
    public boolean verifyCaptchaIsDisplayed() {
        return waitUntilElementDisplayed(captch);
    }

    public boolean verifySaveBalancetoMyAccount() {
        return waitUntilElementDisplayed(saveGiftCardBalance);
    }

    public String getPaymentMethodType() {
        try {
            return getAttributeValue(paymentMethodImg, "alt");
        } catch (Exception e) {
            return "";
        }
    }

    public String getCVVByPaymentType() {
        String cvv = "123";
        String paymentType = getPaymentMethodType();
        if (paymentType.equalsIgnoreCase("MC") || paymentType.equalsIgnoreCase("DISC") || paymentType.equalsIgnoreCase("VISA")) {
            return cvv;
        } else if (paymentType.equalsIgnoreCase("AMEX")) {
            cvv = "1234";
            return cvv;
        } else if (paymentType.equalsIgnoreCase("")) {
            return "";
        }
        return cvv;
    }

    public boolean isRemainingBalanceDispalying() {
        if (getText(remainingBalOnGC).toLowerCase().contains("remaining balance:")) {
            return true;
        } else {
            addStepDescription("The remaining balance is not showing on GC");
            return false;
        }
    }

    public boolean isGCLast_4DigitsDisplaying() {
        int startIndex = getText(remainingBalOnGC).toLowerCase().indexOf("ending in ");
        int endIndex = getText(remainingBalOnGC).indexOf("|");
        String last4DigitsExp = getText(remainingBalOnGC).substring(startIndex, endIndex);
        if (!last4DigitsExp.isEmpty() && last4DigitsExp != null) {
            return true;
        } else {
            addStepDescription("The last 4 digits expected showing on GC section as " + last4DigitsExp);
            return false;
        }
    }

    public boolean isGCAppliedNoticeDisplay() {
        String expectedTxt = "Please keep your Gift Card until you receive the item(s) purchased.";
        String gcAppliedTxt = "GC applied text under applied gift cards section";

        try {
            gcAppliedTxt = getText(gcAppliedNotice);
        } catch (NullPointerException n) {

        }
        if (gcAppliedTxt.contains(expectedTxt)) {
            return true;
        } else {
            addStepDescription("GC applied text under GC section expected: " + expectedTxt + " actual " + gcAppliedTxt);
            return false;
        }

    }

    public boolean orderLedgerAfterAddingGiftWrapping() {
        waitUntilElementDisplayed(orderLedgerSection);
        if (isDisplayed(giftWrapping_OrderLedger)) {
            //       String giftPrice = getText(giftWrapping_OrderLedger).replaceAll("[^0-9.]", "");
            return true;
        } else {
            addStepDescription("Gift Wrapping is not added in Order total");
            return false;
        }
    }

   public boolean enterPaymentAndAddressDetailsBopis_GuestAndRegUser(ReviewPageActions reviewPageActions, String fName, String lName, String addrLine1, String addrLine2, String city_Payment, String state, String zip, String ccNum, String secCode, String expMonth) {
        waitUntilElementDisplayed(nextReviewButton, 20);
        fillPaymentAddrDetailsBopisAlone(fName, lName, addrLine1, city_Payment, state, zip);
        staticWait();
        return payWithACreditCard(reviewPageActions, ccNum, secCode, expMonth);
    }

    public boolean fillPaymentAddrDetailsBopisAlone(String fName, String lName, String addrLine1, String city, String state, String zip) {
        if (isDisplayed(editLinkOnCard)) {
            click(editLinkOnCard);
            waitUntilElementDisplayed(cardNumField, 30);
        }
        if (!waitUntilElementDisplayed(firstName, 10)) {
            if (isDisplayed(dropDownLink)) {
                clickAddressDropdown();
                clickAddNewAddress();
            }
        } else
            waitUntilElementDisplayed(firstName, 10);
        clearAndFillText(firstName, fName);
        clearAndFillText(lastName, lName);
        clearAndFillText(addressLine1, addrLine1);
        clearAndFillText(cityFld, city);
        clearAndFillText(zipCode, zip);
        selectDropDownByValue(stateDropDown, state);
        if (waitUntilElementDisplayed(saveToAccountCheckBox, 10)) {
            unSelect(saveToAccountCheckBoxNew, saveToAccountCheckBoxNewinput);
        }
        scrollDownToElement(nextReviewButton);
        return waitUntilElementDisplayed(nextReviewButton, 30);
    }

    public boolean enterPaymentAndAddressDetails_GuestAndRegUserPLCC(ReviewPageActions reviewPageActions, String fName, String lName, String addrLine1, String addrLine2, String city_Payment, String state, String zip, String ccNum) {
        waitUntilElementDisplayed(nextReviewButton, 20);
        fillPaymentAddrDetailsWithoutSameAsShip(fName, lName, addrLine1, city_Payment, state, zip);
        staticWait();
        return enterPLCCDetails(reviewPageActions, ccNum);
    }

    public boolean payWithACreditCardNResponse(String ccNum, String secCode, String expMonth) {
        waitUntilElementDisplayed(nextReviewButton, 5);
        enterCardDetails(ccNum, secCode, expMonth);
        getOrderAmountDetails();
        return waitUntilElementDisplayed(nextReviewButton, 3);
    }

    public boolean enterPaymentDetailsAsGuestAndReg_SameAsShipNResponse(String ccNum, String secCode, String expMonth) {
        try {
            waitUntilElementDisplayed(sameAsShippingChkBox, 5);
            if (!isChecked(sameAsShippingChkBoxValue)) {
                click(sameAsShippingChkBox);
            }
        } catch (Throwable t) {
            logger.info("The Same As Shipping Check box is already checked");
        }
        return payWithACreditCardNResponse(ccNum, secCode, expMonth);

    }

    public boolean bopisPLCCCouponValidation(double previousTotal) {
        waitUntilElementDisplayed(orderLedgerSection);
        double orderTotal = Double.parseDouble(getEstimateTotal());
        double totalDiscount = previousTotal * 0.10;
        double actualValue = previousTotal - totalDiscount;
        if (orderTotal == actualValue) {
            return true;
        } else {
            addStepDescription("10% discount is not applied to the order total");
            return false;
        }
    }

    public boolean validateMonAndYear() {
        waitUntilElementDisplayed(creditCardRadioBox, 2);
        String expYear = getFutureYearWithCurrentDate("YYYY", 7);
        select(creditCardRadioBox, creditCardRadioBoxinput);
        String year = "2025";
        String expMonth = "12";

        if (waitUntilElementDisplayed(editLinkOnCard, 1)) {
            click(editLinkOnCard);
            waitUntilElementDisplayed(cardNumField, 10);
            selectDropDownByValue(expMonDropDown, expMonth);
            selectDropDownByVisibleText(expYrDopDown, expYear);

        } else {
            waitUntilElementDisplayed(cardNumField, 5);
            selectDropDownByValue(expMonDropDown, expMonth);
            selectDropDownByVisibleText(expYrDopDown, expYear);

        }
        String selectedMon = getText(selectedExpMonth);
        String selectedYear = getText(selectedExpYear);
        if (selectedMon.equalsIgnoreCase("Dec") && selectedYear.equalsIgnoreCase(year)) {
            return true;
        } else {
            addStepDescription("Exp Mon or Year value selected and test displayed is wrong.");
            return false;
        }
    }

    /**
     * Validate progress indicators for all in billing Page
     *
     * @return true if all matches
     */
    public boolean validateProgressbarStatus() {
        waitUntilElementDisplayed(nextReviewButton, 10);
        String shippingProgressBarStatus = getAttributeValue(shippingProgressBar, "class");
        String billingProgressBarStatus = getAttributeValue(billingProgressBar, "class");
        String reviewProgressBarStatus = getAttributeValue(reviewProgressBar, "class");
        String title = getText(checkoutTitle);

        return shippingProgressBarStatus.equalsIgnoreCase("completed") &&
                billingProgressBarStatus.equalsIgnoreCase("active") &&
                reviewProgressBarStatus.equalsIgnoreCase("") &&
                title.equalsIgnoreCase("Billing");
    }
    public boolean validateProgressbarStatusForCombined() {
        waitUntilElementDisplayed(reviewProgressBar, 10);
        String pickupProgressBarStatus = getAttributeValue(pickupProgressBar,"class");
        String shippingProgressBarStatus = getAttributeValue(shippingProgressBar, "class");
        String billingProgressBarStatus = getAttributeValue(billingProgressBar,"class");
        String title = getText(checkoutTitle);
        String reviewProgressBarStatus = getAttributeValue(reviewProgressBar,"class");

        return  pickupProgressBarStatus.equalsIgnoreCase("completed") &&
                shippingProgressBarStatus.equalsIgnoreCase("completed") &&
                billingProgressBarStatus.equalsIgnoreCase("active") &&
                reviewProgressBarStatus.equalsIgnoreCase("") &&
                title.equalsIgnoreCase("Billing");
    }

    /**
     * Validate progress indicators for all in Billingpage Page
     *
     * @return true if all matches
     */
    public boolean validateProgressbarStatusForBopis() {
        waitUntilElementDisplayed(reviewProgressBar, 10);
        String shippingProgressBarStatus = getAttributeValue(pickupProgressBar,"class");
        String billingProgressBarStatus = getAttributeValue(billingProgressBar,"class");
        String title = getText(checkoutTitle);
        String reviewProgressBarStatus = getAttributeValue(reviewProgressBar,"class");

        return  shippingProgressBarStatus.equalsIgnoreCase("completed") &&
                billingProgressBarStatus.equalsIgnoreCase("active") &&
                reviewProgressBarStatus.equalsIgnoreCase("") &&
                title.equalsIgnoreCase("Billing");
    }

}

