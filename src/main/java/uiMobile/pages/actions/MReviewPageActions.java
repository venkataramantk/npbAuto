package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import uiMobile.pages.repo.MReviewPageRepo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JKotha on 11/14/2017.
 */
public class MReviewPageActions extends MReviewPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MReviewPageActions.class);

    public MReviewPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean isReviewPageDisplayed() {
        return waitUntilElementDisplayed(isReviewPageActive, 20);
    }

    /**
     * Clicks on submit order button in review page
     *
     * @return
     */
    public boolean clickSubmOrderButton() {
        MReceiptThankYouPageActions mThankYouPageActions = new MReceiptThankYouPageActions(mobileDriver);
        waitUntilElementDisplayed(submitOrderBtn, 30);
        staticWait(1000);
        scrollDownToElement(submitOrderBtn);
        staticWait(1000);
        javaScriptClick(mobileDriver, submitOrderBtn);
        return waitUntilElementDisplayed(mThankYouPageActions.confirmationTitle, 40);
    }

    public boolean verifyPickUpInfirmation() {
        return waitUntilElementDisplayed(pickupInfo);
    }

    public boolean getErrorMessage() {
        return isDisplayed(errorMessage);
    }


    public boolean verifyShippingAddress(String fName, String lName, String addressLine1, String addressLine2, String city, String stateShort, String zip, String country, String phone) {
        String shippingAddress = getText(shippingAddressSection).toLowerCase().replaceAll("\n", "").replaceAll(" ", "");
        String enteredAddress = (fName + lName + (addressLine1.replaceAll(" ", "")) + (addressLine2.replaceAll(" ", "")) + city + (",") + stateShort + (zip.replaceAll(" ", "")) + (country.replaceAll(" ", ""))).replaceAll("\n", "").toLowerCase();
        if (shippingAddress.contains(enteredAddress)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyBillingAddress(String edit, String fName, String lName, String addressLine1, String addressLine2, String city, String stateShort, String zip, String country, String phone, String emailAddress) {
        String billingAddress = billingAddressSection.getText().toLowerCase().replaceAll("\n", "").replaceAll(" ", "").replaceAll(edit, "");
        String enteredAddress = (fName + lName + (addressLine1.replaceAll(" ", "")) + (addressLine2.replaceAll(" ", "")) + city + (",") + stateShort + (zip.replaceAll(" ", "")) + (country.replaceAll(" ", "")) + phone + emailAddress).toLowerCase().replaceAll("\n", "");
        if (billingAddress.contains(enteredAddress)) {
            return true;
        } else {
            return false;
        }
    }


    public String paymentMethodSection() {
        return paymentMethodSection.getText().toUpperCase().replaceAll("\n", "");
    }


    public boolean verifyOrderSummarySection() {
        if (isDisplayed(title_OrderSummary)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyShippingAddressSection() {
        if (isDisplayed(shippingAddressSection)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyBillingAddressSection() {
        if (isDisplayed(billingAddressSection)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyGiftWrappingSection() {
        return isDisplayed(giftWrappingSection);

    }

    public boolean verifyShippingMethodSection() {
        return isDisplayed(shippingMethodSection);

    }

    public boolean verifyPaymentMethodSection() {
        return isDisplayed(paymentMethodSection);

    }

    public boolean verifyPickUpSection() {
        return isDisplayed(pickUpStoreSection);
    }

    public boolean verifyAlternatePickUpSection() {
        return isDisplayed(alternatePickUpStore);
    }



    public boolean verifyOrderReviewPageHybridProd() {
        if (verifyPickUpSection() && verifyBillingAddressSection() && verifyGiftWrappingSection()
                 && verifyPaymentMethodSection() && isDisplayed(submitOrderBtn) && isDisplayed(govtText)) {
            return true;
        } else {
            return false;
        }
    }


    public boolean clickReturnToBillingLink(MBillingPageActions billingPageActions) {
        javaScriptClick(mobileDriver,returnBillingLnk);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);
    }


    public boolean enterExpressCO_CVVAndSubmitOrder(String codeCVV) {

        waitUntilElementDisplayed(cvvTxtField, 10);
        clearAndFillText(cvvTxtField, codeCVV);
        return clickSubmOrderButton();
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

    public boolean clickOnLogo() {
        click(logo);
        if (waitUntilElementDisplayed(overlayContent, 10) &&
                waitUntilElementDisplayed(stayCheckoutBtn, 10) &&
                waitUntilElementDisplayed(returnBagBtn, 10))
            return true;
        else
            return false;
    }

    public boolean verifyShipToHomeProduct() {
        waitUntilElementDisplayed(myBagCount, 20);
        if (waitUntilElementDisplayed(shippingText, 10) &&
                waitUntilElementDisplayed(prodNameSTH, 10) &&
                waitUntilElementDisplayed(prodColorSTH, 10) &&
                waitUntilElementDisplayed(prodSizeSTH, 10) &&
                waitUntilElementDisplayed(prodQuantitySTH, 10))
            return true;
        else
            return false;
    }

    public boolean verifyBopisProduct() {
        waitUntilElementDisplayed(myBagCount, 20);
        if (waitUntilElementDisplayed(pickUpText, 10) &&
                waitUntilElementDisplayed(prodNameBopis, 10) &&
                waitUntilElementDisplayed(prodColorBopis, 10) &&
                waitUntilElementDisplayed(prodSizeBopis, 10) &&
                waitUntilElementDisplayed(prodQuantityBopis, 10))
            return true;
        else
            return false;
    }


    public boolean clickOnPickUpAccordionBopis(MobilePickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(pickUpAccordion, 20);
        click(pickUpAccordion);
        return waitUntilElementDisplayed(pickUpPageActions.nxtBillingBtn, 30);
    }

    public boolean clickOnShippingAccordion(MShippingPageActions shippingPageActions) {
        waitUntilElementDisplayed(shippingAccordion, 20);
        javaScriptClick(mobileDriver,shippingAccordion);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 30);
    }

    public boolean clickOnBillingAccordion(MBillingPageActions billingPageActions) {
        if (waitUntilElementDisplayed(billingAccordion, 20)) {
            click(billingAccordion);
            return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 30);
        }else{
            return false;
        }
        // Assert.fail("Billing Accordion is not displaying");
        // return false;
    }

    public String getReturnToBagMessage() {
        return getText(overlayContent);
    }

    /**
     * Created by Raman
     *
     * @return billing Page
     */
    public boolean clickEditBillingLink(MBillingPageActions billingPageActions) {
        waitUntilElementDisplayed(submOrderButton, 20);
        javaScriptClick(mobileDriver,editBillingLink);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);
    }

    /**
     * Created by RichaK
     * <p>
     * Verify Billing address is displayed on review page
     */
    public boolean verifyAddressPrepop(String fNameExp, String lNameExp, String add1Exp, String cityExp, String stateExp, String zipExp, String country) {
        String fullName = getText(billingAddressCustName);
        String billingAddr = getText(billingAddress);
        String updated_billingAdd = "";

        return fullName.equalsIgnoreCase(fNameExp + " " + lNameExp) && billingAddr.equalsIgnoreCase(add1Exp + "\n" + cityExp + ", " + stateExp + " " + zipExp + "\n" + country);
    }
    
    /* public String getEstimateTotal(){

        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");
        return estimatedCost;
    }*/

    /*Created By Raman
    This method click on return to bag and Shopping Page should appear*/
    public boolean returnToBag(MShoppingBagPageActions shoppingBagPageAction) {
        if (!isDisplayed(returnBagBtn)) {
            javaScriptClick(mobileDriver,backBtn);
        }
        waitUntilElementDisplayed(returnBagBtn, 30);
        javaScriptClick(mobileDriver,returnBagBtn);
        return waitUntilElementDisplayed(shoppingBagPageAction.checkoutBtn, 30);
    }

    public boolean clickBackButton() {
        waitUntilElementDisplayed(backBtn);
        click(backBtn);
        return waitUntilElementDisplayed(overlayContent);
    }

    public boolean clickReturnToBagBtn(MShoppingBagPageActions shoppingBagPageAction) {
        waitUntilElementDisplayed(returnBagBtn, 20);
        click(returnBagBtn);
        return waitUntilElementDisplayed(shoppingBagPageAction.checkoutBtn, 20);
    }

    public boolean validateTotalAfterRemoveCoupon() {
        click(removeCoupon);
        String offerCost = getText(offerPrice).replaceAll("[^0-9.]", "");
        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float offerAmount = Float.parseFloat(offerCost);
        float estimatedTot = Float.parseFloat(estimatedCost);

        if (offerAmount == estimatedTot) {
            return true;
        }
        return waitUntilElementDisplayed(estimatedTotal, 20);
    }


    public boolean verifyOrderReviewPageWithGiftCardPay() {
        if (verifyOrderSummarySection() && verifyShippingAddressSection() && verifyGiftWrappingSection()
                && verifyShippingMethodSection() && verifyGiftCardAppliedSection() && waitUntilElementDisplayed(submitOrderBtn, 10) && !verifyBillingAddressSection() &&
                !verifyPaymentMethodSection()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editShipMethodLink() {
        MShippingPageActions shippingPageActions = new MShippingPageActions(mobileDriver);
        //  scrollUpToElement(editShippingAddress);
        javaScriptClick(mobileDriver,editShippingAddress);
        staticWait(5000);
        scrollDownUntilElementDisplayed(shippingPageActions.nextBillingButton);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 40);
    }

    public boolean verifyGiftCardAppliedSection() {
        if (isDisplayed(giftCardAppliedSection)) {
            return true;
        } else {
            addStepDescription("gift card  section is not displaying at review page");
            return false;
        }
    }

    public boolean isGiftCardDisplayedInOrderSummary() {
        return isDisplayed(giftCardsTotal);
    }

    public String getLast4DigitsGiftCard() {
        return getText(giftCardLast4Digits);
    }

    public String getLast4DigitsGiftCardAppliedSection() {
        return getText(giftCardLast4DigitsAppliedSec);
    }

    public String getLast4DigitsOfGC() {
        String text = getText(giftCardLast4DigitsAppliedSec);
        return text.substring(10, 14).trim();
    }

    public String getLast4DigitsOfCC() {
        String text = getText(cardInfo);
        return text.substring(10, 14).trim();
    }

    public boolean verifyGiftCardAvailableSection() {
        if (isDisplayed(giftcardsAvailableSection)) {
            return true;
        } else {
            addStepDescription("Available gift card section is not displaying on order review page");
            return false;
        }
    }

    public boolean isNewGiftCardBtnDisplayed() {
        return isDisplayed(newGiftCardBtn);
    }

    public boolean clickApplyButton() {
        click(applyGCButton);
        return waitUntilElementDisplayed(removeBtn);
    }

    /**
     * Created by Richa Priya
     * Click on T&C button displayed on review page
     *
     * @return true if url is changed to termsAndCondition url
     */
    public boolean clickOnTermsAndConditionPage() {
        if (!isDisplayed(termsAndCondLink)) {
            scrollToBottom();
        }
        javaScriptClick(mobileDriver,termsAndCondLink);
        return waitUntilUrlChanged("termsAndCondition", 20);
    }

    /**
     * Created by Richa Priya
     * Get the current URL
     *
     * @return true if url is equal to the termsAndCondition url
     */
    public boolean termsAndConditionValidateURL(String urlValue) {
        staticWait(5000);
        String currentUrl = getCurrentURL();
        staticWait(7000);
        return currentUrl.contains(urlValue);

    }

    /**
     * Created by Richa Priya
     * Scroll to the top of the page
     *
     * @return String of the termsAndCondition heading
     */
    public String verifyHelpCentreTextOnTermsAndConditionPageIsDisplayed() {
        scrollToTop();
        return getText(helpCentreText);
    }

    /**
     * Created by RichaK
     *
     * @param numberOfProducts
     * @return true if My Bag is in proper format with count of number of products in bag
     */
    public boolean verifyMyBagAccordionFormat(int numberOfProducts) {
        String myBagCountTxt = getText(myBagCount);
        if (myBagCountTxt.equals("My Bag (" + numberOfProducts + ")"))
            return true;
        else
            return false;
    }

    /**
     * Created By RichaK
     *
     * @param estimatedTotal
     * @return true if Order Summary is in proper format.
     */
    public boolean verifyOrderSummaryToggleFormat(String estimatedTotal) {
        String orderSummaryToggleText = getText(orderSummaryToggleTxt);
        if (orderSummaryToggleText.equals("Order Summary ($" + estimatedTotal + ")"))
            return true;
        else
            return false;
    }

    public String getFulfillmentCenterName(int index) {
        String fullfillmentCenter = getText(fulfilmentCentre.get(index));
        return fullfillmentCenter;
    }

    public String getProductNames(int index) {
        String productName = getText(productNames.get(index));
        return productName;
    }

    public boolean isMyBagToggleBtnDisplayed() {
        return isDisplayed(myBagToggleBtn);
    }

    /**
     * Created by RichaK
     * This method will click on MyBag Toggle button and return true if product details start showing up.
     *
     * @return
     */
    public boolean clickMyBagToggleBtn() {
        waitUntilElementDisplayed(myBagToggleBtn);
        click(myBagToggleBtn);
        return waitUntilElementDisplayed(productNames.get(0));
    }

    public boolean isOrderSummaryToggleBtnDisplayed() {
        return isDisplayed(orderSummaryToggleBtn);
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

    /**
     * validate different page status in Review page
     *
     * @param orderType
     */
    public boolean validateAccordiansInReviewPage(String orderType) {
        boolean status = false;
        boolean pickup, shipping, billing, review;
        waitUntilElementDisplayed(checkoutProgressBar);
        switch (orderType.toUpperCase()) {
            case "ECOMM":
                shipping = accordians.get(0).getAttribute("class").equalsIgnoreCase("completed");
                billing = accordians.get(1).getAttribute("class").equalsIgnoreCase("completed");
                review = accordians.get(2).getAttribute("class").equalsIgnoreCase("active");
                status = shipping && billing && review;
                break;
            case "BOPIS":
                pickup = accordians.get(0).getAttribute("class").equalsIgnoreCase("completed");
                billing = accordians.get(1).getAttribute("class").equalsIgnoreCase("completed");
                review = accordians.get(2).getAttribute("class").equalsIgnoreCase("active");
                status = pickup && billing && review;
                break;
            case "MIXED":
                pickup = accordians.get(0).getAttribute("class").equalsIgnoreCase("completed");
                shipping = accordians.get(1).getAttribute("class").equalsIgnoreCase("completed");
                billing = accordians.get(2).getAttribute("class").equalsIgnoreCase("completed");
                review = accordians.get(3).getAttribute("class").equalsIgnoreCase("active");
                status = pickup && shipping && billing && review;
                break;
        }
        return status;
    }

    /**
     * Created by RichaK
     * validate accordions based on the order type in Review page
     *
     * @param orderType
     */
    public boolean validateAccordianNamesInReviewPage(String orderType) {
        boolean status = false;
        boolean pickup, shipping, billing, review;
        waitUntilElementDisplayed(checkoutProgressBar);
        switch (orderType.toUpperCase()) {
            case "ECOMM":
                shipping = accordians.get(0).getText().equals("Shipping");
                billing = accordians.get(1).getText().equals("Billing");
                review = accordians.get(2).getText().equals("Review");
                status = shipping && billing && review;
                break;
            case "BOPIS":
                pickup = accordians.get(0).getText().equals("Pickup");
                billing = accordians.get(1).getText().equals("Billing");
                review = accordians.get(2).getText().equals("Review");
                status = pickup && billing && review;
                break;
            case "MIXED":
                pickup = accordians.get(0).getText().equals("Pickup");
                shipping = accordians.get(1).getText().equals("Shipping");
                billing = accordians.get(2).getText().equals("Billing");
                review = accordians.get(3).getText().equals("Review");
                status = pickup && shipping && billing && review;
                break;
        }
        return status;
    }

    /**
     * Created by Richak
     * return the attribute value for alt attribute.
     *
     * @return
     */
    public String getCardImgAltText() {
        return getAttributeValue(cardImg, "alt");
    }

    /**
     * Verify CVV field is present
     *
     * @return
     */
    public boolean isCvvFieldIsDisplayed() {
        return waitUntilElementDisplayed(cvvTxtField, 10);
    }

    /**
     * return true if cvv field is empty.
     *
     * @return
     */
    public boolean isCvvEmpty() {
        waitUntilElementDisplayed(cvvTxtField, 10);
        return getAttributeValue(cvvTxtField, "value").equals("");
    }

    /**
     * Created by Raman Jha
     * verify the Alternate pickup section info.
     *
     * @return
     */


    public boolean verifyAltPickUpPrepop(String altFNameExp, String altLNameExp, String altEmail) {
        waitUntilElementDisplayed(submitOrderBtn);
        String fullName = getText(altPickupName);
        String email = getText(altPickupEmail);

        addStepDescription("Expected name details vs returned" +altFNameExp+ "" + altLNameExp+ "::" +fullName );
        addStepDescription("Expected email details vs returned" +altEmail+ "::" +email );

        return fullName.equalsIgnoreCase(altFNameExp.trim() + " " + altLNameExp.trim()) && email.equalsIgnoreCase(altEmail);
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

    public String getEstimatedDays() {
        waitUntilElementDisplayed(estimatedDays);
        return getText(estimatedDays);
    }

    public void selectShippingMethodFromRadioButton(String shipMethod) {
        scrollToBottom();
        if (!shippingMethodRadioButtonByValue(shipMethod).isSelected()) {
            javaScriptClick(mobileDriver, shippingMethodRadioButtonByValue(shipMethod));
        }
        staticWait(2000);
    }

    /**
     * Created by Raman Jha
     * <p>
     * Verify the the shipping details on Express checkout
     */
    public boolean verifyAddressPrepopShipping(String fNameExp, String lNameExp, String add1Exp, String cityExp, String stateExp, String zipExp, String country, String email, String phone) {
        waitUntilElementDisplayed(submitOrderButton, 4);
        String fullName = getText(custShippingName);
        String shipAddr = getText(custShppingAddress);
        String emailPhnNumber = getText(custEmailPhn);

        return fullName.equalsIgnoreCase(fNameExp + " " + lNameExp) && shipAddr.equalsIgnoreCase(add1Exp + "\n" + cityExp + ", " + stateExp + " " + zipExp + "\n" + country) && emailPhnNumber.equalsIgnoreCase(email + "\n" + phone);
    }

    public boolean verifyPickUpDetails(String fNameExp, String lNameExp, String emailExp, String phone) {
        waitUntilElementDisplayed(submitOrderButton, 4);
        String fName = getText(pickUpName);
        String emailPhnNumber = getText(custEmailPhn);

        addStepDescription("Expected email details vs returned" +emailExp + "\n" + phone+ "::" +emailPhnNumber );
        return fName.equalsIgnoreCase(fNameExp.trim() + " " + lNameExp.trim()) && emailPhnNumber.equalsIgnoreCase(emailExp + "\n" + phone);

    }

    /**
     * Created by Richa Priya
     * return the value for billing add.
     *
     * @return
     */
    public String getBillingAdd() {
        return getText(billingAddress);
    }

    /**
     * Created by Richa Priya
     * return last 4 digit of Credit card.
     *
     * @return
     */
    public String getLast4DigitCC() {
        return getText(paymentMethodSection).replaceAll("[^0-9]", "");
    }

    /**
     * Created by Richa Priya
     *
     * @return quantity present in my bag section
     */
    public int getMyBagQty() {
        return Integer.parseInt(getText(myBagCount).replaceAll("[^0-9]", ""));
    }

    /**
     * Created by Richa Priya
     *
     * @return billing Page
     */
    public boolean clickEditPickUp(MobilePickUpPageActions mobilePickUpPageActions) {
        waitUntilElementDisplayed(submOrderButton, 20);
        click(pickUpEditLnk);
        return waitUntilElementDisplayed(mobilePickUpPageActions.pickUpTitle, 20);
    }

    /**
     * Created by Richa Priya
     * <p>
     * click on stay on checkout btn
     */
    public boolean clickStayOnCheckoutBtn() {
        waitUntilElementDisplayed(stayInCheckOut, 30);
        click(stayInCheckOut);
        return waitUntilElementDisplayed(submitOrderBtn, 30);

    }

    /**
     * Created by Richa Priya
     * <p>
     * Enter GC details
     */
    public void enterGiftCardDetails(String gc, String pin) {
        waitUntilElementDisplayed(addNewGC, 4);
        click(addNewGC);
        waitUntilElementDisplayed(gcContent, 4);
        clearAndFillText(cardNumberField, gc);
        clearAndFillText(pinNumberField, pin);
        click(applyGCButton);
        waitUntilElementDisplayed(removeButtonOnGC);
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
        return verifyElementNotDisplayed(addNewGC, 10);
    }

    /* Created by Raman

     */
    public boolean verifyNewGiftCardAddFields() {
        waitUntilElementDisplayed(addNewGC);
        click(addNewGC);
        return waitUntilElementDisplayed(gcContent, 4) &&
                waitUntilElementDisplayed(cardNumberField, 2) &&
                waitUntilElementDisplayed(pinNumberField, 2) &&
                /*waitUntilElementDisplayed (reCapatchaiFrame) &&*/
                waitUntilElementDisplayed(applyGCButton, 2) &&
                waitUntilElementDisplayed(cancelGCButton, 2);

    }

    public List<String> appliedGiftCardsCount() {
        List<String> count = new ArrayList<>();
        for (int i = 0; i < appliedGiftCards.size(); i++) {
            count.add(appliedGiftCards.get(i).getText());
        }
        return count;
    }

    public boolean verifyShippingAddress(String addFromShippingPage) {
        waitUntilElementDisplayed(submitOrderBtn, 4);
        String shipAddr = getText(shippingAddressSection);
        addStepDescription("Shipping address on review page::" +shipAddr);
        return shipAddr.contains(addFromShippingPage.split(",")[0]);
    }

}