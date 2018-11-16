package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MBillingPageRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JKotha on 23/10/2017.
 */
public class MBillingPageActions extends MBillingPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(MBillingPageActions.class);
    public String prodName;
    public String prodSize;
    public String prodColor;
    public String prodQuantity;
    public String prodPrice;

    public String itemsTotalPr;
    public String shipTotalPr;
    public String estTaxPrPr;
    public String estTotalPr;

    public MBillingPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Continue shipping by selecting same as shipping address
     * and Credit card
     *
     * @param ccNum    Credit card number
     * @param secCode  CVV code
     * @param expMonth expire month
     * @return true if the payment successful and navigate review page
     */
    public void enterPaymentDetailsAsGuestAndReg_SameAsShip(String ccNum, String secCode, String expMonth) {
        if (!isSelected(creditCardRadioBox)) {
            click(creditCardRadioBox);
        }
        try {
            waitUntilElementDisplayed(sameAsShippingChkBox, 5);
            select(sameAsShippingChkBox, sameAsShippingChkBoxinput);
        } catch (Throwable t) {
            logger.info("The Same As Shipping Check box is already checked");
        }
        payWithACreditCard(ccNum, secCode, expMonth);
    }

    /**
     * Click on paypal button in billing
     *
     * @return text displayed on paypal popup
     */
    public boolean selectPayPalAsPayment(MPayPalPageActions mPayPalPageActions) {
        //waitUntilElementDisplayed(paypalRadioBoxElement, 20);
        //click(paypalRadioBoxElement);
        waitUntilElementDisplayed(payPalFrame, 20);
        switchToFrame(payPalFrame);
        waitUntilElementDisplayed(payPalBtn, 20);
        click(payPalBtn);
//        switchToDefaultFrame();
       // return waitUntilElementDisplayed(mPayPalPageActions.cancelLinkInLoginPage, 30);
        return  getCurrentWindowHandles() == 2;
    }

    /**
     * Created by RichaK
     * Click on paypal button in billing when user has already logged in by shopping bag page
     *
     * @return text displayed on paypal popup
     */
    public boolean selectPayPalAsPaymentWhenAlreadyLoggedIn(MPayPalPageActions paypalActions, MReviewPageActions mreviewPageActions) {
        waitUntilElementDisplayed(payPalFrame, 20);
        click(payPalFrame);
        if (getCurrentWindowHandles() == 2)
            switchToWindow();
        return paypalActions.clickContinueButton(mreviewPageActions);
    }


    /**
     * Pay with credit card
     *
     * @param ccNum    Credit card number
     * @param secCode  CVV code
     * @param expMonth expire month
     * @return true if the payment successful and navigate review page
     */
    public void payWithACreditCard(String ccNum, String secCode, String expMonth) {
        enterCardDetails(ccNum, secCode, expMonth);
        scrollDownToElement(sameAsShippingChkBox);
        waitUntilElementDisplayed(sameAsShippingChkBox);
    }

    /**
     * Enter Card Details
     *
     * @param ccNum    Credit card number
     * @param secCode  CVV code
     * @param expMonth expire month
     */
    public void enterCardDetails(String ccNum, String secCode, String expMonth) {
        String expYear = getFutureYearWithCurrentDate("YYYY", 7);
        if (!isSelected(creditCardRadioBox)) {
            click(creditCardRadioBox);
        }
        if (waitUntilElementDisplayed(editLinkOnCard, 1)) {
            click(editLinkOnCard);
            waitUntilElementDisplayed(cardNumField, 10);
        }
        unSelect(saveCardToMyAccount, saveCardToMyAccountInput);
        clearAndFillText(cardNumField, ccNum);
        selectDropDownByValue(expMonDropDown, expMonth);
        selectDropDownByVisibleText(expYrDopDown, expYear);
        waitUntilElementDisplayed(cvvFld, 5);
        clearAndFillText(cvvFld, secCode);
    }

    public void enterCardDetailsFromOverlay(String ccNum, String expMonth) {
        String expYear = getFutureYearWithCurrentDate("YYYY", 7);
        clearAndFillText(cardNumField, ccNum);
        selectDropDownByValue(expMonDropDown, expMonth);
        selectDropDownByVisibleText(expYrDopDown, expYear);
        click(saveBtnOnOverlay);
    }

    public boolean clickApplyOnGiftCard() {
        if (isDisplayed(applyButtonOnGC)) {
            click(applyButtonOnGC);
        }
        return waitUntilElementDisplayed(removeButtonOnGC, 30);
    }

    public boolean isRemainingBalanceDispalying() {
        if (getText(remainingBalOnGC).toLowerCase().contains("remaining balance:")) {
            return true;
        } else {
            addStepDescription("The remaining balance is not showing on GC");
            return false;
        }
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


    /**
     * Author: JK
     *
     * @return true if first month and year selected from drop downs
     */
    public boolean selectFirstMonthAndYearAndVerify() {
        List<String> firstMonth = getAllOptionsText(expMonDropDown);
        List<String> firstYear = getAllOptionsText(expYrDopDown);
        selectDropDownByIndex(expMonDropDown, 1);
        selectDropDownByIndex(expYrDopDown, 1);
        Boolean isFirstMonthSelected = firstMonth.get(1).equalsIgnoreCase(getSelectOptions(expMonDropDown));
        Boolean isFirstYearSelected = firstYear.get(1).equalsIgnoreCase(getSelectOptions(expYrDopDown));
        return isFirstMonthSelected && isFirstYearSelected;
    }

    /**
     * Fill billing Address
     *
     * @param fName        firstName
     * @param lName        LastName
     * @param addrLine1    Address Line
     * @param city_Payment city Name
     * @param state        state
     * @param zip          zip Code
     * @return true if billing address successfully enter
     */
    public boolean fillPaymentAddrDetailsWithoutSameAsShip(String fName, String lName, String addrLine1, String city_Payment, String state, String zip) {
        if (!waitUntilElementDisplayed(firstName, 10)) {
            if (waitUntilElementDisplayed(saveToAccountCheckBox, 10)) {
                click(saveToAccountCheckBox);
            }
            return waitUntilElementDisplayed(nextReviewButton, 10);
        } else
            waitUntilElementDisplayed(firstName, 10);
        clearAndFillText(firstName, fName);
        clearAndFillText(lastName, lName);
        clearAndFillText(addressLine1, addrLine1);
        clearAndFillText(cityFld, city_Payment);
        selectDropDownByValue(stateDropDown, state);
        staticWait();
        unSelect(saveToAccountCheckBox, saveToAccountCheckBoxInput);
        clearAndFillText(zipCode, zip);
        scrollDownToElement(nextReviewButton);
        return waitUntilElementDisplayed(nextReviewButton, 10);
    }

    /**
     * Enter Card and Bill address details
     *
     * @param fName        firstName
     * @param lName        LastName
     * @param addrLine1    Address Line
     * @param city_Payment city Name
     * @param state        state
     * @param zip          zip Code
     * @param ccNum        Credit card number
     * @param secCode      CVV code
     * @param expMonth     expire month
     * @return
     */
    public void enterPaymentAndAddressDetails_GuestAndRegUser(String fName, String lName, String addrLine1, String city_Payment, String state, String zip, String ccNum, String secCode, String expMonth) {
        waitUntilElementDisplayed(nextReviewButton, 20);
        fillPaymentAddrDetailsWithoutSameAsShip(fName, lName, addrLine1, city_Payment, state, zip);
        staticWait();
        payWithACreditCard(ccNum, secCode, expMonth);
    }

    /**
     * Navigate to review page
     *
     * @return true if review page is displayed
     */
    public boolean clickNextReviewButton() {
        MReviewPageActions reviewPageActions = new MReviewPageActions(driver);
        try {
           // scrollToBottom();
            javaScriptClick(driver,nextReviewButton);
        } catch (Throwable t) {
            scrollUpToElement(creditCardRadioBox);
        } finally {
            return waitUntilElementDisplayed(reviewPageActions.editBillingLink, 60);
        }
    }

    public boolean validateBillingPage() {
        return waitUntilElementDisplayed(nextReviewButton, 60);
    }

    public boolean clickReturnToPickup() {
        MobilePickUpPageActions action = new MobilePickUpPageActions(driver);
        click(returnToPickup);
        return waitUntilElementDisplayed(action.pickupNote);
    }

    /**
     * Enter PLCC card details
     *
     * @param placeCardNumber PLCC card number
     * @return true if PLCC card entered successful
     */
    public boolean enterPLCCDetails(String placeCardNumber) {
        staticWait();
        if(isSelected(saveCardToMyAccount)){
            click(saveCardToMyAccountInput);
        }
        clearAndFillText(cardNumField, placeCardNumber);
        return clickNextReviewButton();
    }

    /**
     * Select Same as Shipping Address button
     */
    public void clickSameAsShipAddress() {
        if (!isSelected(sameAsShippingChkBox)) {
            click(sameAsShippingChkBox);
        }
    }

    /**
     * Pay with gift card
     *
     * @param giftCardNum
     * @param pin
     * @param isValid
     * @return
     */
    public Boolean applyGiftCardToOrder(String giftCardNum, String pin, String isValid) {
        if (verifyElementNotDisplayed(giftCardNumber, 3))
            click(payWithGiftCardElement);
        waitUntilElementDisplayed(giftCardNumber);
        clearAndFillText(giftCardNumber, giftCardNum);
        clearAndFillText(giftCardPin, pin);
        click(checkGiftCardBalanace);
        waitUntilElementDisplayed(availableBalance);
        if (isValid.equalsIgnoreCase("valid")) {

            if (Double.parseDouble(getAvailableBalance()) > 0) {
                click(applyToOrder);
                return verifyElementNotDisplayed(applyToOrder);
            } else {
                return false;
            }
        } else {
            return waitUntilElementDisplayed(giftCardError, 5);
        }
    }

    public void enterGiftCardDetails(String gc, String pin) {
        waitUntilElementDisplayed(addNewGC, 4);
        click(addNewGC);
        waitUntilElementDisplayed(gcContent, 4);
        clearAndFillText(cardNumberField, gc);
        clearAndFillText(pinNumberField, pin);
        click(applyGCButton1);
        waitUntilElementDisplayed(removeButtonOnGC);
    }


    public boolean updateGiftCardBalanceByAmount(String giftCardNum, String pin, String amtBalance) {
        if (verifyElementNotDisplayed(giftCardNumber, 3))
            click(payWithGiftCardElement);
        waitUntilElementDisplayed(giftCardNumber);
        clearAndFillText(giftCardNumber, giftCardNum);
        clearAndFillText(giftCardPin, pin);
        click(checkGiftCardBalanace);
        waitUntilElementDisplayed(availableBalance);
        if (Double.parseDouble(getAvailableBalance()) > 0) {
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

    /**
     * Get Available balance for PLCC card
     *
     * @return GetAvailable balance
     */
    public String getAvailableBalance() {
        String availBalance = getText(availableBalance);
        int startIndex = availBalance.indexOf("$");
        availBalance = availBalance.substring(startIndex);
        logger.info("The avaialble Balanceis: " + availBalance);
        return availBalance.replace("$", "");
    }

    /**
     * Enter CVV number for card in Express checkout
     *
     * @param codeCVV cvv for card
     * @return true if cvv entered
     */
    public boolean enterCVVForExpressAndClickNextReviewBtn(String codeCVV) {
        waitUntilElementDisplayed(cvvExpressFld, 10);
        clearAndFillText(cvvExpressFld, codeCVV);
        return clickNextReviewButton();
    }

    public boolean validateItemCountWithBagCount(String cartCount) {
        String item = getText(itemsLbl).replaceAll("[^0-9]", "").substring(0, 1);
        int itemCount = Integer.parseInt(item);
        int bagCount = Integer.parseInt(cartCount);
        if (itemCount == bagCount)
            return true;
        else
            return false;
    }

    /**
     * Verify total order cost
     *
     * @param estimatedTotal expected estimate cost
     * @return true if the expected and actual matches
     */
    public boolean validateTotInShippingAndBillingPage(String estimatedTotal) {
        staticWait(3000);
        String orderTotal = getText(estimatedTot).replaceAll("[^0-9.]", "");

        float orderTot = Float.parseFloat(orderTotal);
        float estimatedCost = Float.parseFloat(estimatedTotal);

        addStepDescription("Estimated total on billing page::" +orderTot);

        if (orderTot == estimatedCost)
            return true;
        else
            return false;
    }

    /**
     * Click on a add a new Card button
     *
     * @return true if Add Payment Method page is displayed
     */
    public boolean clickAddANewCard() {
        click(addACard);
        return waitUntilElementDisplayed(addCardPage);
    }

    /**
     * Created by Richa Priya
     * Click on back button displayed on billing page
     *
     * @return true if shipping page is displayed successfully
     */
    public boolean clickReturnToShippingPage() {
        MShippingPageActions action = new MShippingPageActions(driver);
       // scrollDownToElement(returnToPickup);
        javaScriptClick(driver,returnToPickup);
        return waitUntilElementDisplayed(action.shippingNote);
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


    public String getEstimateTotal() {

        String estimatedCost = getText(estimatedTot).replaceAll("[^0-9.]", "");
        return estimatedCost;
    }

    public double getGiftCardTotalApplied() {
        double gcPrice = 0.00;
        try {
            gcPrice = Double.valueOf(getText(giftCardsTotal).replaceAll("[^0-9.]", ""));
        } catch (NullPointerException e) {
            addStepDescription("Gift Cards total is not showing at order ledger");
            gcPrice = 0.00;
        }
        return gcPrice;
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
        return orderTotalAfterRemoveGC == Double.valueOf(getEstimateTotal());
    }

    /* Created by Raman
     * To click on progress tracker for shipping
     *
     * */
    public boolean clickOnShipping(MShippingPageActions shippingPageActions) {
        waitUntilElementDisplayed(shippingAcc, 20);
        javaScriptClick(driver, shippingAcc);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 30);
    }


    /**
     * validate cvv default error message
     *
     * @return true if cvv error message is displayed
     */
    public boolean validateCVVErrorMessage() {
        boolean cvv = waitUntilElementDisplayed(cvvInlineError, 20);
        if (cvv) {
            cvv = cvvInlineError.getCssValue("color").equalsIgnoreCase("rgba(198, 8, 8, 1)");
        }
        return cvv;
    }

    public boolean isCardImgDisplayed() {
        return isDisplayed(cardImg);
    }

    public String getCardImgAltText() {
        return getAttributeValue(cardImg, "alt");
    }

    public String getCardSuffix() {
        waitUntilElementDisplayed(cardSuffix);
        return getText(cardSuffix).replaceAll("[^0-9.]", "");
    }

    /**
     * Created by Richa Priya
     * Click on back button displayed on shipping Page
     *
     * @return true if add to Bag page is displayed successfully
     */
    public boolean clickBackToReturnToBag(MShoppingBagPageActions shoppingBagPageActions) {

        if (!isDisplayed(returnBagBtn)) {
            javaScriptClick(driver,backBtn);
        }
        waitUntilElementDisplayed(returnBagBtn, 30);
        javaScriptClick(driver,returnBagBtn);
        return waitUntilElementDisplayed(shoppingBagPageActions.checkoutBtn, 30);
    }


    /**
     * Created by Richa Priya
     * Validate estimated total on billing page with tax calculation
     *
     * @param estimatedVal estimated total which user wants to compare
     * @return true estimated total is equal to the provided total
     */
    public boolean validateEstimatedTotalWithTax(String estimatedVal) {
        boolean flag = false;
        String itemCost = getText(itemPrice).replaceAll("[^0-9.]", "");
        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");
        double estimatedTax = getEstimatedTax();

        double itemPrice = Double.parseDouble(itemCost);
        double estimatedTot = Double.parseDouble(estimatedCost);
        double updatedEstVal = Double.parseDouble(estimatedVal);

        double totalItemPrice_Tax = itemPrice + estimatedTax;
        double totalCalAmt_WithTax = (double) Math.round(totalItemPrice_Tax * 100) / 100;

        if (totalCalAmt_WithTax == estimatedTot && estimatedTot == updatedEstVal) {
            return true;
        }
        return flag;
    }

    /**
     * validates same as shipping checkbox is selected or not
     *
     * @return
     */
    public boolean isSameAsShippingSelected() {
        return isSelected(sameAsShippingChkBox, sameAsShippingChkBoxinput);
    }

    /**
     * validate different page status in shipping page
     *
     * @param orderType
     */
    public boolean validateAccordiansInBillingPage(String orderType) {
        boolean status = false;
        boolean pickup, shipping, billing, review;
        waitUntilElementDisplayed(checkoutProgressBar);
        switch (orderType.toUpperCase()) {
            case "ECOMM":
                shipping = accordians.get(0).getAttribute("class").equalsIgnoreCase("completed");
                billing = accordians.get(1).getAttribute("class").equalsIgnoreCase("active");
                review = accordians.get(2).getAttribute("class").equalsIgnoreCase("");
                status = shipping && billing && review;
                break;
            case "BOPIS":
                pickup = accordians.get(0).getAttribute("class").equalsIgnoreCase("completed");
                billing = accordians.get(1).getAttribute("class").equalsIgnoreCase("active");
                review = accordians.get(2).getAttribute("class").equalsIgnoreCase("");
                status = pickup && billing && review;
                break;
            case "MIXED":
                pickup = accordians.get(0).getAttribute("class").equalsIgnoreCase("completed");
                shipping = accordians.get(1).getAttribute("class").equalsIgnoreCase("completed");
                billing = accordians.get(2).getAttribute("class").equalsIgnoreCase("active");
                review = accordians.get(3).getAttribute("class").equalsIgnoreCase("");
                status = pickup && shipping && billing && review;
                break;
        }
        return status;
    }

    public void clickSelectThisOnEditModal(int index) {
        click(selectThisBtnOnEditModal.get(index));
    }

    /**
     * Created by RichaK
     * Click on edit link on payment method section on billing page.
     *
     * @return
     */
    public boolean clickEditLinkOnBillingPage() {
        click(editLinkOnCard);
        return waitUntilElementDisplayed(cardNumField, 10);
    }

    /**
     * Created by RichaK
     * Click on the edit link based on index on Select Card Page
     *
     * @param index
     */
    public boolean clickEditLinkOnSelectCardPage(int index) {
        click(editLinkList.get(index));
        return waitUntilElementDisplayed(editPaymentMethodContainer);
    }

    /**
     * Created by RichaK
     * wait for the address verficatin overlay and click on you entered and continue button
     *
     * @return true if success
     */
    public boolean selectThisBillingAdd() {
        javaScriptClick(driver, selectThisBillingAddBtn);
        if (waitUntilElementDisplayed(verifyAddressPopup, 30)) {
            if (waitUntilElementDisplayed(yourEnteredAddBtn, 10))
                click(yourEnteredAddBtn);
            click(continueBtn);
        }
        staticWait(3000);
        return waitUntilElementDisplayed(editPaymentMethodContainer, 20);
    }

    /**
     * Created by RichaK
     *
     * @param fieldVal   which need to be edited for eg - firstName field
     * @param updatedVal new value which needs to be inserted in place of existing one
     * @return true if success
     */
    public boolean UpdateFieldOnEditScreen(String fieldVal, String updatedVal) {
        boolean flag = false;
        switch (fieldVal) {

            case "FNAME":
                clearAndFillText(firstName, updatedVal);
                flag = selectThisBillingAdd();
                break;

            case "LNAME":
                clearAndFillText(lastName, updatedVal);
                flag = selectThisBillingAdd();
                break;

            case "ADDRESSLINE1":
                clearAndFillText(addressLine1, updatedVal);
                flag = selectThisBillingAdd();
                break;

            case "ADDRESSLINE2":
                clearAndFillText(addressLine1, updatedVal);
                flag = selectThisBillingAdd();
                break;

            case "CITY":
                clearAndFillText(cityFld, updatedVal);
                flag = selectThisBillingAdd();
                break;

            case "ZIP":
                clearAndFillText(zipCode, updatedVal);
                flag = selectThisBillingAdd();
                break;

            case "PHNUM":
                scrollDownToElement(phNumFld);
                clearAndFillText(phNumFld, updatedVal);
                flag = selectThisBillingAdd();
                break;

            case "STATE":
            case "PROVINCE":
                selectDropDownByValue(stateDropDown, updatedVal);
                flag = selectThisBillingAdd();
                break;

            default:
        }
        return flag;
    }

    /**
     * Created by RichaK
     *
     * @param fieldVal   which need to be edited for eg - firstName field
     * @param updatedVal new value which needs to be inserted in place of existing one
     * @return true if success
     */
    public void UpdateFieldOnForm(String fieldVal, String updatedVal) {
        switch (fieldVal) {
            case "FNAME":
                clearAndFillText(firstName, updatedVal);
                break;

            case "LNAME":
                clearAndFillText(lastName, updatedVal);
                break;

            case "ADDRESSLINE1":
                clearAndFillText(addressLine1, updatedVal);
                break;

            case "ADDRESSLINE2":
                clearAndFillText(addressLine1, updatedVal);
                break;

            case "CITY":
                clearAndFillText(cityFld, updatedVal);
                break;

            case "ZIP":
                clearAndFillText(zipCode, updatedVal);
                break;

            case "PHNUM":
                scrollDownToElement(phNumFld);
                clearAndFillText(phNumFld, updatedVal);
                break;

            case "STATE":
            case "PROVINCE":
                selectDropDownByValue(stateDropDown, updatedVal);
                break;

            default:
        }
    }

    public boolean clickSaveBtnOnOverlay() {
        click(saveBtnOnOverlay);
        return waitUntilElementDisplayed(editLinkOnCard);
    }

    /* Created by Raman
     */
    public boolean verifyNewGiftCardAddFields() {
        waitUntilElementDisplayed(addNewGC);
        click(addNewGC);
        return waitUntilElementDisplayed(gcContent, 4) &&
                waitUntilElementDisplayed(cardNumberField) &&
                waitUntilElementDisplayed(pinNumberField) &&
                /*waitUntilElementDisplayed (reCapatchaiFrame) &&*/
                waitUntilElementDisplayed(applyGCButton1);

    }

    public boolean verifyreCaptchaChkbox() {
        waitUntilElementDisplayed(cardNumberField);
        switchToFrame(captchFrameLoc);
        String reCaptchaText = getText(reCaptchatext);
        return getAttributeValue(reCaptchaChkbx, "aria-checked").equalsIgnoreCase("false") &&
                reCaptchaText.contains("I'm not a robot");
    }

    public boolean verifyPaymentMethodPrepopOnForm(String cardNum, String expMonth) {

        String cardNumberInEdit = getAttributeValue(cardNumField, "value");
        String expMonthInEdit = getAttributeValue(expMonDropDown, "value");

        String getLast4Num = cardNumberInEdit.replaceAll("[^0-9]", "");
        String lastDigit = getLastFourDigitsOfCCNumber(cardNum);

        addStepDescription("Last 4 number from UI::" + getLast4Num);
        addStepDescription("Exp Month from UI::" + expMonthInEdit);
        addStepDescription("Last 4 number from parameter::" + lastDigit);
        addStepDescription("Exp Month from parameter::" + expMonth);

        return getLast4Num.trim().equalsIgnoreCase(lastDigit) && expMonthInEdit.trim().equalsIgnoreCase(expMonth);

    }

    /**
     * Created by RichaK
     * return true if cvv field is empty.
     *
     * @return
     */
    public boolean verifyCVVEmpty() {
        String cvv = getAttributeValue(cvvExpressFld, "value");
        return cvv.equals("");
    }

    /**
     * Created by RichaK
     * <p>
     * Verify the prepopulated Billing address
     */
    public boolean verifyAddressPrepop(String fNameExp, String lNameExp, String add1Exp, String cityExp, String stateExp, String zipExp, String phoneNum) {
        String fullName = getText(billingAddnameReadOnly);
        String billingAddr = getText(billingAddressReadOnly);
        String phoneNumDisplayed = getText(phoneNumReadOnly);
        String totalBillingAddress = billingAddr + "\n" + phoneNumDisplayed;

        return fullName.equalsIgnoreCase(fNameExp + " " + lNameExp) && totalBillingAddress.equalsIgnoreCase(add1Exp + "\n" + cityExp + ", " + stateExp + " " + zipExp + "\n" + phoneNum);
    }

    public boolean verifyBillingAddressPrepopOnForm(String fName, String lName, String addrLine1, String city, String state, String zip) {
        Boolean isFirstNameMatched = getAttributeValue(firstName, "value").equalsIgnoreCase(fName);
        Boolean isLastNameMatched = getAttributeValue(lastName, "value").equalsIgnoreCase(lName);
        Boolean isAddressLine1Matched = getAttributeValue(addressLine1, "value").equalsIgnoreCase(addrLine1);
        Boolean isCityMatched = getAttributeValue(cityFld, "value").equalsIgnoreCase(city);
        Boolean iszipCodeMatched = getAttributeValue(zipCode, "value").equalsIgnoreCase(zip);
        Boolean isStateMatched = getSelectOptions(stateDropDown).equalsIgnoreCase(state);

        return isFirstNameMatched && isLastNameMatched && isAddressLine1Matched && isCityMatched && isStateMatched && iszipCodeMatched;
    }

    public boolean isDefaultTextPresentInBillAdd() {
        String fullName = getText(billingAddnameReadOnly);
        return fullName.contains("default");
    }

    /**
     * Created by Richa Priya
     * click on remove GC button
     *
     * @return true if apply GC button is displayed
     */
    public boolean clickRemoveGCButton() {
        waitUntilElementDisplayed(removeButtonOnGC);
        click(removeButtonOnGC);
        return verifyElementNotDisplayed(giftCardSection, 10);
    }

    /**
     * Created by RichaK
     * This method will return all the suffixes at the passed index.
     *
     * @param index
     * @return
     */
    public String getCardSuffix(int index) {
        waitUntilElementDisplayed(selectCardOverlayTitle);
        return getText(cardSuffixList.get(index)).replaceAll("[^0-9.]", "");
    }

    /**
     * Created by Richak
     * This method will verify if the expected cards are present in select card page
     *
     * @param expectedCardNumbers
     * @return
     */
    public boolean verifyExpectedCardsOnSelectCardPage(ArrayList<String> expectedCardNumbers) {
        ArrayList<String> actualCardNumbersDisplayed = new ArrayList<>();
        List<WebElement> list = cardSuffixList;
        for (WebElement webElement : list) {
            String cardNumber = getText(webElement);

            actualCardNumbersDisplayed.add(getLastFourDigitsOfCCNumber(cardNumber));
        }

        for (String expectedCardNum : expectedCardNumbers) {
            String expectedLastFourDigits = getLastFourDigitsOfCCNumber(expectedCardNum);
            if (!actualCardNumbersDisplayed.contains(expectedLastFourDigits))
                return false;
        }

        return true;

    }

    /**
     * Created by Richa Priya
     * click on remove GC button
     *
     * @return true if apply GC button is displayed
     */
    public boolean removeMultipleGC() {
        int gcDisplayed = removeButtonGC.size();
        for (int i = 0; i < gcDisplayed; i++) {
            click(removeButtonGC.get(i));
        }
        return verifyElementNotDisplayed(giftCardSection, 10);
    }

    /**
     * Created by Richa Priya
     * Change Payment method
     *
     * @return true if returned back to billing page
     */
    public boolean changePaymentMethod(String option) {
        if (option.equalsIgnoreCase("CC")) {
            waitUntilElementDisplayed(creditCardRadioBox, 20);
            javaScriptClick(driver, creditCardRadioBox);
            waitUntilElementDisplayed(changePaymentMethodBtn, 20);
            click(changePaymentMethodBtn);
        }
        if (option.equalsIgnoreCase("PayPal")) {

            waitUntilElementDisplayed(creditCardRadioBox, 20);
            click(creditCardRadioBox);
            waitUntilElementDisplayed(continuePayPalBtn, 20);
            javaScriptClick(driver,continuePayPalBtn);
            waitUntilElementDisplayed(payPalFrame, 20);
         //   switchToFrame(payPalFrame);
            //waitUntilElementDisplayed(payPalBtn, 20);
           // click(payPalBtn);
           // switchToDefaultFrame();
           /* waitUntilElementDisplayed(payPalFrame, 10);
            switchToFrame(payPalFrame);*/
        }
        return waitUntilElementDisplayed(payPalContinueBtn, 20) || waitUntilElementDisplayed(nextReviewButton, 20);
    }


    public boolean closeChangePaymentModal() {
        waitUntilElementDisplayed(creditCardRadioBox, 20);
        click(creditCardRadioBox);
        waitUntilElementDisplayed(closeModalChangePayment, 20);
        click(closeModalChangePayment);
        waitUntilElementDisplayed(payPalFrame, 10);
      //  switchToFrame(payPalFrame);
      //  return waitUntilElementDisplayed(paypalCheckoutOnPaypalModal, 10);
         return waitUntilElementDisplayed(payPalContinueBtn);
    }

    public List<String> appliedGiftCardsCount() {
        List<String> count = new ArrayList<>();
        for (int i = 0; i < appliedGiftCards.size(); i++) {
            count.add(appliedGiftCards.get(i).getText());
        }
        return count;
    }

    /**
     * Created by Richa Priya
     *
     * @return true if fields are displayed on billing page
     */
    public boolean verifyBillingDetailsFields() {
        return waitUntilElementDisplayed(cardNumField, 20) &&
                waitUntilElementDisplayed(expMonDropDown, 4) &&
                waitUntilElementDisplayed(expYrDopDown) &&
                waitUntilElementDisplayed(cvvFld) &&
                waitUntilElementDisplayed(sameAsShippingSection) &&
                waitUntilElementDisplayed(billingAddSection);
    }


    /**
     * Created by Richa Priya
     * Enter PLCCCCard and Bill address details
     *
     * @param fName        firstName
     * @param lName        LastName
     * @param addrLine1    Address Line
     * @param city_Payment city Name
     * @param state        state
     * @param zip          zip Code
     * @return
     */
    public void enterPaymentAndAddressDetails_GuestAndRegUser_PLCCC(String fName, String lName, String addrLine1, String city_Payment, String state, String zip, String cardNumber) {
        waitUntilElementDisplayed(nextReviewButton, 20);
        fillPaymentAddrDetailsWithoutSameAsShip(fName, lName, addrLine1, city_Payment, state, zip);
        staticWait();
        enterPLCCDetails(cardNumber);
    }

    /**
     * Created by RichaK
     * return true if title is EXPRESS CHECK OUT
     *
     * @return
     */
    public boolean verifyExpressCheckoutTitle() {
        return getText(expressCheckout).equals("EXPRESS CHECK OUT");
    }
}