package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import ui.pages.repo.ReviewPageRepo;
import ui.support.EnvironmentConfig;

/**
 * Created by skonda on 5/19/2016.
 */
public class ReviewPageActions extends ReviewPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(ReviewPageActions.class);

    public ReviewPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isReviewPageDisplayed() {
        if (isDisplayed(submOrderButton)) {
            return true;//getText(isReviewPageActive).equalsIgnoreCase("Review");
        }
        return false;
    }

    public boolean clickSubmOrderButton(OrderConfirmationPageActions orderConfirmationPageActions) {
        waitUntilElementDisplayed(submOrderButton, 30);
        click(submOrderButton);
//        staticWait(10000);
        return waitUntilElementDisplayed(orderConfirmationPageActions.confirmationTitle);
    }

    public String getEmailAddress() {
        return getText(emailID).trim();
    }

    public String getshipAddress() {
        return getText(shippingAddressSection);
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

    public String shipMethodSection() {
        return shippingMethodSection.getText().replaceAll(" ", "");
    }

    public String editShipMethodLink() {
        return editShippingMethod.getText().toLowerCase();
    }

    public WebElement billAddressSection() {
        return billingAddressSection;
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

    public String editPhoneNumberLink() {
        return editPhoneNumberLink.getText().toLowerCase();
    }

    public String editEmailLink() {
        return editEmailLink.getText().toLowerCase();
    }

    public String paymentMethodSection() {
        return getText(paymentMethodSection).replaceAll("\n", "");
    }

    public String editPaymentMethodLink() {
        return editPaymentLink.getText().toLowerCase();
    }

    public String giftOptionsSection() {
        return giftWrappingSection.getText().toLowerCase().replaceAll("\n", "").replaceAll(" ", "");
    }

    public String airMilesRewardsSection() {
        return airMilesRewardsSection.getText().toLowerCase().replaceAll("\n", "").replaceAll(" ", "");
    }


    public String editGiftOptionsSectionCA() {
        return editGiftOptionsCA.getText().toLowerCase();
    }

    public String editGiftOptionsSectionUS() {
        return editGiftOptionsUS.getText().toLowerCase();
    }

    public boolean verifyOrderSummarySection() {
        if (isDisplayed(title_OrderSummary)) {
            return true;
        } else {
            addStepDescription("Order Ledger section is not displaying at review page");
            return false;
        }
    }

    public boolean verifyShippingAddressSection() {
        if (isDisplayed(shippingAddressSection)) {
            return true;
        } else {
            addStepDescription("shipping Address section is not displaying at review page");
            return false;
        }
    }

    public boolean verifyBillingAddressSection() {
        if (isDisplayed(billingAddressSection)) {
            return true;
        } else {
            addStepDescription("billing Address section is not displaying at review page");
            return false;
        }
    }

    public boolean verifyGiftWrappingSection() {
        if (isDisplayed(giftWrappingSection)) {
            return true;
        } else {
            addStepDescription("gift wrapping section is not displaying at review page");
            return false;
        }

    }

    public boolean verifyShippingMethodSection() {
        if (isDisplayed(shippingMethodSection)) {
            return true;
        } else {
            addStepDescription("shipping method section is not displaying at review page");
            return false;
        }

    }

    public boolean verifyExpShippingMethodSection() {
        if (isDisplayed(expShippingMethod)) {
            return true;
        } else {
            addStepDescription("Express shipping method section is not displaying at review page");
            return false;
        }
    }

    public boolean verifyPaymentMethodSection() {
        if (isDisplayed(paymentMethodSection)) {
            return true;
        } else {
            addStepDescription("Payment method section is not displaying at review page");
            return false;
        }

    }

    public boolean verifyGiftCardSection() {
        if (isDisplayed(giftCardSection)) {
            return true;
        } else {
            addStepDescription("gift card  section is not displaying at review page");
            return false;
        }


    }
    public boolean couponSectionDisplay(){
        if(isDisplayed(couponSection)){
            return true;
        }
        else{
            addStepDescription("Coupon section is not displayed in the Review Page");
            return false;
        }
    }

    public boolean verifyPickUpSection() {
        if (isDisplayed(pickUpStoreSection)) {
            return true;
        } else {
            addStepDescription("pickup store section is not displaying at review page");
            return false;
        }
    }

    public boolean verifyAlternatePickUpSection() {
        if (isDisplayed(alternatePickUpStore)) {
            return true;
        } else {
            addStepDescription("alternate pickup store section is not displaying at review page");
            return false;
        }
    }

    public boolean verifyOrderReviewPageBopis() {
        if (verifyOrderSummarySection() && verifyBillingAddressSection() &&
                verifyPaymentMethodSection() && isDisplayed(submitOrderBtn) && isDisplayed(govtText)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyOrderReviewPageBOPISWithAltPickUp() {
        if (verifyOrderSummarySection() && verifyBillingAddressSection() && verifyAlternatePickUpSection()
                && verifyPaymentMethodSection() && isDisplayed(submitOrderBtn) && isDisplayed(govtText)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyOrderReviewPageHybridProd() {
        if (verifyPickUpSection() && verifyAlternatePickUpSection() && verifyBillingAddressSection() && verifyGiftWrappingSection()
                && verifyShippingMethodSection() && verifyPaymentMethodSection() && isDisplayed(submitOrderBtn) && isDisplayed(govtText)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyOrderReviewPageSTH() {
        boolean isCouponSectionDisplay = true;
        if(EnvironmentConfig.getEnvironmentProfile().equalsIgnoreCase("prod") || EnvironmentConfig.getEnvironmentProfile().equalsIgnoreCase("prodstaging")){
            isCouponSectionDisplay = true;
        }
        else{
            isCouponSectionDisplay = couponSectionDisplay();
        }
        if (verifyOrderSummarySection() && verifyShippingAddressSection() && verifyBillingAddressSection() && verifyGiftWrappingSection()
                && verifyShippingMethodSection() && verifyPaymentMethodSection() && waitUntilElementDisplayed(submitOrderBtn, 10) && isCouponSectionDisplay ) {
            return true;
        } else {
            return false;
        }
    }
    public boolean verifyOrderReviewPageSTHProd() {
        if (verifyOrderSummarySection() && verifyShippingAddressSection() && verifyBillingAddressSection() && verifyGiftWrappingSection()
                && verifyShippingMethodSection() && verifyPaymentMethodSection() && waitUntilElementDisplayed(submitOrderBtn, 10)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyOrderExpReviewPageSTH() {
        if (verifyOrderSummarySection() && verifyShippingAddressSection() && verifyBillingAddressSection()
                && verifyExpShippingMethodSection() && verifyPaymentMethodSection() && waitUntilElementDisplayed(submitOrderBtn, 10)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyOrderReviewPageWithGiftCardPay() {
        if (verifyOrderSummarySection() && verifyShippingAddressSection() && verifyGiftWrappingSection()
                && verifyShippingMethodSection() && verifyGiftCardSection() && waitUntilElementDisplayed(submitOrderBtn, 10) && !verifyBillingAddressSection() &&
                !verifyPaymentMethodSection()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyBillingAddressAndPayment(String fName, String lName, String addressLine1, String addressLine2, String city, String stateShort, String zip, String country, String phNum, String emailAddress, String cardType, String edit) {
        verifyBillingAddress(edit, fName, lName, addressLine1, addressLine2, city, stateShort, zip, country, phNum, emailAddress);
        String paymentInfo = paymentMethodSection.getText();
        if (paymentInfo.contains(cardType) && verifyBillingAddress(edit, fName, lName, addressLine1, addressLine2, city, stateShort, zip, country, phNum, emailAddress) == true) {
            return true;
        } else {
            return false;
        }
    }


    public boolean clickOnShippingMethodEdit(ShippingPageActions shippingPageActions) {
        click(editShippingMethod);
        return waitUntilElementDisplayed(shippingPageActions.continueCheckoutButton, 30);
    }


//    public boolean verifyBillingAddressAndPayment_Reg(String fName, String lName,String addressLine1, String addressLine2, String city, String zip, String phNum, String state, String cardType,String edit){
//        String billingAddress = billingAddressSection.getText().toLowerCase().replaceAll("\n", "").replaceAll(" ", "").replaceAll(edit,"");;
//        String paymentInfo = paymentMethodSection.getText();
//        if(billingAddress.contains(fName)&&
//                billingAddress.contains(lName)&&
//                billingAddress.contains(addressLine1)&&
//                billingAddress.contains(addressLine2)&&
//                billingAddress.contains(city)&&
//                billingAddress.contains(zip)&&
//                billingAddress.contains(phNum)&&
//                billingAddress.contains(state) &&
//                paymentInfo.contains(cardType)) {
//            return true;
//        }
//        else{
//            return false;
//        }
//    }

    public boolean verifyShippingAddressAndShippingMethod(String fName, String lName, String addressLine1, String addressLine2, String city, String stateShort, String zip, String country, String phNum, String shipName) {
        boolean verifyShipAdd = verifyShippingAddress(fName, lName, addressLine1, addressLine2, city, stateShort, zip, country, phNum);

        String shippingMethod = getText(shippingMethodSection);
        if (shippingMethod.contains(shipName) && verifyShipAdd) {
            return true;
        } else {
            return false;
        }

    }


    public boolean clickOnBagEdit(ShoppingBagPageActions shoppingBagPageActions) {
        waitUntilElementDisplayed(shopBagContents, 10);
        click(editBagButton);
        return waitUntilElementDisplayed(shoppingBagPageActions.proceedToCheckOutButton, 10);
    }

//    public String productDetailsFirst(){
//        return prodDetailsFirst.getText();
//    }
//
//    public String productDetailsSecond(){
//        return prodDetailsSecond.getText();
//    }

    public String editBagProductDetailsByPosition(int position) {
        String prodDetails = editBagProdDetlsByPosn(position).getText().replaceAll(" ", "").toLowerCase().replaceAll("\n", "");
        return prodDetails;
    }

    public boolean isProductAvailable(int i) {
        if (getDriver().findElements(By.cssSelector("#WC_OrderItemDetailsSummaryf_div_2_" + i)).size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFreeShippingApplied() {
        boolean isShipDiscDisplaying = waitUntilElementDisplayed(discAppliedValueLbl, 5);
        boolean isPromotionLblDisplaying = waitUntilElementDisplayed(promotionsLabel, 5);

        if (isShipDiscDisplaying && isPromotionLblDisplaying) {
            return getText(promotionsLabel).equalsIgnoreCase("Promotions") && getText(discAppliedValueLbl).equalsIgnoreCase("Free");
        }
        return false;

    }

    public boolean enterExpressCO_CVVAndSubmitOrder(String codeCVV, OrderConfirmationPageActions orderConfirmationPageActions) {

        waitUntilElementDisplayed(cvvTxtField, 30);
        clearAndFillText(cvvTxtField, codeCVV);
        return clickSubmOrderButton(orderConfirmationPageActions);
    }
    public boolean enterExpressCO_CVVAndSubmitOrderCheck(String codeCVV, OrderConfirmationPageActions orderConfirmationPageActions) {

        waitUntilElementDisplayed(cvvTxtField, 30);
        clearAndFillText(cvvTxtField, codeCVV);
        return clickSubmOrderButtonCheck(orderConfirmationPageActions);
    }

    public boolean returnToBagPage(ShoppingBagPageActions shoppingBagPageActions) {
        click(returnBagLink);
        waitUntilElementDisplayed(returnBagBtn, 30);
        click(returnBagBtn);
        staticWait();
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

    public boolean validateCartCountWithBagCount() {
        String totalItem = getText(itemTotalText).replaceAll("[^0-9]", "").substring(0, 1);
        String bagCount = getText(myBagCount).replaceAll("[^0-9.]", "");

        int totalItemCount = Integer.parseInt(totalItem);
        int totalBagCount = Integer.parseInt(bagCount);

        if (totalBagCount == totalItemCount)
            return true;
        else
            return false;
    }

    public boolean returnToPickUpPage(PickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(pickUpAccordion, 20);
        click(pickUpAccordion);
        return waitUntilElementDisplayed(pickUpPageActions.nxtBillingBtn, 30);
    }

    public boolean clickOnLogo() {
        click(logo);
        if (waitUntilElementDisplayed(messageContent, 10) &&
                waitUntilElementDisplayed(stayCheckoutBtn, 10) &&
                waitUntilElementDisplayed(returnToBagBtn, 10))
            return true;
        else
            return false;
    }

    public boolean verifyShipToHomeProduct() {
        waitUntilElementDisplayed(myBagCount, 20);
        if (isDisplayed(shippingText) &&
                isDisplayed(prodNameSTH) &&
                isDisplayed(prodColorSTH) &&
                isDisplayed(prodSizeSTH) &&
                isDisplayed(prodQuantitySTH) &&
                isDisplayed(prodOfferPriceSTH))
            return true;
        else
            return false;
    }
    public boolean verifyShipToHomeAloneProduct() {
        waitUntilElementDisplayed(myBagCount, 20);
        if (isDisplayed(shippingText) &&
                isDisplayed(prodNameSTHAlone) &&
                isDisplayed(prodColorSTHAlone) &&
                isDisplayed(prodSizeSTHAlone) &&
                isDisplayed(prodQuantitySTHAlone) &&
                isDisplayed(prodOfferPriceSTH))
            return true;
        else
            return false;
    }

    public boolean verifyBopisProduct() {
        waitUntilElementDisplayed(myBagCount, 20);
        if (isDisplayed(pickUpText) &&
                isDisplayed(prodNameBopis) &&
                isDisplayed(prodColorBopis) &&
                isDisplayed(prodSizeBopis) &&
                isDisplayed(prodQuantityBopis) &&
                isDisplayed(prodOfferPriceBopis))
            return true;
        else
            return false;
    }

    public boolean validateHybirdProdDetails() {
        waitUntilElementDisplayed(myBagCount, 20);
        if (verifyShipToHomeProduct() && verifyBopisProduct() && isFulfilmentCenterDisplay() && isQtyInBagAndHeaderMatch() && isProdUnderMyBagNotEditable())
            return true;
        else
            return false;
    }

    public boolean isProdUnderMyBagNotEditable() {
        if (!isEnabled(prodSizeBopis) && !isEnabled(prodQuantityBopis) && !isEnabled(prodQuantitySTH) && !isEnabled(prodSizeSTH)) {
            return true;
        } else {
            addStepDescription("The products Bopis and sth size and quantity are editable under mybag section");
            return false;
        }
    }

    public boolean isQtyInBagAndHeaderMatch() {
        int bopisQty = 0, sthQty = 0, bagCountHeader = 0;
        try {
            bopisQty = Integer.parseInt(getText(prodQuantityBopis).replaceAll("[^0-9]", ""));
        } catch (NullPointerException n) {
            bopisQty = 0;
        }
        try {
            sthQty = Integer.parseInt(getText(prodQuantitySTH).replaceAll("[^0-9]", ""));
        } catch (NullPointerException n) {
            sthQty = 0;
        }
        int totalQtyInBag = sthQty + bopisQty;
        try {
            bagCountHeader = Integer.parseInt(getText(myBagCount).replaceAll("[^0-9]", ""));
        } catch (NullPointerException n) {
            bagCountHeader = 0;
        }
        if (totalQtyInBag == 0 && bagCountHeader == 0) {
            addStepDescription("Total qty in bag count and my bag header qty is showing 0");
            return false;
        } else if (totalQtyInBag == bagCountHeader) {
            return true;
        } else {
            addStepDescription("The qty in header bag is showing " + bagCountHeader + " total count in bag " + totalQtyInBag);
            return false;
        }

    }

    public boolean isFulfilmentCenterDisplay() {
        if (isDisplayed(fulfilmentCentre)) {
            return true;
        } else {
            addStepDescription("fulfilment center is not displaying");
            return false;
        }

    }

    public boolean validateContentsForSTH() {
        if (verifyOrderSummarySection() &&
                verifyShippingAddressSection() &&
                verifyBillingAddressSection())
            return true;
        else
            return false;
    }

    public boolean clickOnPickUpAccordionSTH(PickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(pickUpAccordion, 20);
        click(pickUpAccordion);
        return waitUntilElementDisplayed(pickUpPageActions.nxtShippingBtn, 30);
    }

    public boolean clickOnPickUpAccordionBopis(PickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(pickUpAccordion, 20);
        click(pickUpAccordion);
        return waitUntilElementDisplayed(pickUpPageActions.nxtBillingBtn, 30);
    }

    public boolean clickOnShippingAccordion(ShippingPageActions shippingPageActions) {
        waitUntilElementDisplayed(shippingAccordion, 20);
        click(shippingAccordion);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 30);
    }

    public boolean clickOnBillingAccordion(BillingPageActions billingPageActions) {
        waitUntilElementDisplayed(billingProgressBar, 20);
        click(billingProgressBar);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 30);
    }
    public boolean clickBillAccordionWithPaypal_Payment(BillingPageActions billingPageActions) {
        waitUntilElementDisplayed(billingProgressBar, 20);
        click(billingProgressBar);
        return waitUntilElementDisplayed(billingPageActions.proceedWithPaPalButton, 30);
    }

    public boolean stayInCheckOut() {
        click(returnBagLink);
        waitUntilElementDisplayed(overlayContent, 20);
        click(stayInCheckOut);
        boolean stayOnPage = waitUntilElementDisplayed(stayInCheckOut, 10);
        return !stayOnPage;
    }

    public boolean validateOrderLedgerSection() {
        if (waitUntilElementDisplayed(itemTotalText, 10) &&
                waitUntilElementDisplayed(itemsTotalPrice, 10) &&
                waitUntilElementDisplayed(tax_Total, 10) &&
                waitUntilElementDisplayed(estimatedTotal, 10) &&
                waitUntilElementDisplayed(submitOrderBtn, 10))
            return true;
        else
            return false;
    }

    public boolean navigatePickUpPageBopis(PickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(pickUpEditLnk, 20);
        click(pickUpEditLnk);
        return waitUntilElementDisplayed(pickUpPageActions.nxtBillingBtn, 20);
    }

    public boolean navigatePickUpPageHybrid(PickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(pickUpEditLnk, 20);
        click(pickUpEditLnk);
        return waitUntilElementDisplayed(pickUpPageActions.nxtShippingBtn, 20);
    }

    public boolean validateAccordionForBopis() {
        if (waitUntilElementDisplayed(pickUpAccordion, 10) &&
                waitUntilElementDisplayed(billingProgressBar, 10) &&
                verifyElementNotDisplayed(shippingAccordion, 10))
            return true;
        else
            return false;
    }

    public boolean clickEditBillingLink(BillingPageActions billingPageActions) {
        waitUntilElementDisplayed(submOrderButton, 20);
        click(editBillingLink);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);
    }

    public String getEstimateTotal() {

        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");
        return estimatedCost;
    }

    public boolean clickEditShippingAddress(ShippingPageActions shippingPageActions) {
        staticWait(2000);
        click(editShippingAddressLink);
        staticWait(2000);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 20);
    }

    public boolean clickEditBillingAddress(BillingPageActions billingPageActions) {
        staticWait(2000);
        click(editBillingAddressLink);
        staticWait(2000);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);
    }

    public boolean clickEditBOPISBillingAddress(BillingPageActions billingPageActions) {
        staticWait(2000);
        click(editBillForBOPISReview);
        staticWait(2000);
        return waitUntilElementDisplayed(billingPageActions.nextReviewButton, 20);
    }

    public boolean checkAirmilesNo() {

        String airMilesDrawer = getAirMilesNo();
        String airMilesReviews = getText(airMilesReview).replaceAll("[^0-9]", "");

        if (airMilesDrawer.equalsIgnoreCase(airMilesReviews)){
            return true;}
        else{
            return false;}
    }

    public String getAirMilesNo() {

        String airMiles = getText(airMilesNumb).replaceAll("[^0-9]", "");
        return airMiles;
    }

    public boolean checkEmptyAirmilesNo() {
        String airMiles = "";
        String airMilesReviews = getText(airmilesField).replaceAll("[^0-9]", "");

        if (airMiles.equals(airMilesReviews))
            return true;
        else
            return false;
    }

    public boolean verifyLastNumber(String ccNum) {

        String getLast4Num = getText(last4Number).replaceAll("[^0-9]", "");
        String lastDigit = getLastFourDigitsOfCCNumber(ccNum);

        if (getLast4Num.equals(lastDigit)) {
            return true;
        } else
            return false;
    }

    public boolean expReviewCVVCheck() {
        waitUntilElementDisplayed(submitOrderBtn, 3);
        if (!isDisplayed(cvvTxtField)) {
            return true;
        } else {
            addStepDescription("CVV field is displayed in Review Page");
            return false;
        }
    }

    public boolean orderLedgerSubmitOrderButton(OrderConfirmationPageActions receiptThankYouPageActions) {
        waitUntilElementDisplayed(submitOrderBtn, 5);
        click(submitOrderBtn);
        return waitUntilElementDisplayed(receiptThankYouPageActions.confirmationTitle, 10);
    }

    public boolean verifyOrderReviewPageWithGiftCardPayCA() {
        if (verifyOrderSummarySection() && verifyShippingAddressSection() && !verifyGiftWrappingSection()
                && verifyShippingMethodSection() && verifyGiftCardSection() && waitUntilElementDisplayed(submitOrderBtn, 10)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyClickReviewProgressBar() {
        waitUntilElementDisplayed(reviewProgressBar, 4);
        click(reviewProgressBar);
        if (waitUntilElementDisplayed(submitOrderBtn, 10)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateGiftcard() {
        waitUntilElementDisplayed(addNewGC, 4);
        click(addNewGC);
        waitUntilElementDisplayed(gcContent, 4);
        if (isDisplayed(cardNumberField) && isDisplayed(pinNumberField) && isDisplayed(cancelGCButoon) && isDisplayed(saveGCToAccount)
                && isDisplayed(applyGCButton)) {
            return true;
        } else
            return false;
    }

    public boolean clickApplyButton() {
        click(applyGCButton);
        return waitUntilElementDisplayed(removeBtn);
    }

    public boolean clickTAndCLinkUnderSubmitOrder() {
        String currentWindow = getCurrentWindow();
        clickAndSwitchToWindowFrom(termsAndCondLink, currentWindow);
        if (isDisplayed(termsAndCondSection)) {
            switchBackToParentWindow(currentWindow);
            return true;
        } else {
            addStepDescription("Terms And Conditions section is not displaying in another window");
            switchBackToParentWindow(currentWindow);
            return false;
        }

    }

    public boolean clickReturnToBillingLink(BillingPageActions billingPageActions) {
        click(retToBillingLink);
        return waitUntilElementDisplayed(billingPageActions.billingAccordianActive, 30);
    }

    public boolean isProdBOPISOfferPriceDisplay() {
        if (isDisplayed(prodOfferPriceBopis)) {
            return true;
        } else {
            addStepDescription("Product offer price is not displaying");
            return false;
        }
    }

    public boolean isProdSTHOfferPriceDisplay() {
        if (isDisplayed(prodOfferPriceSTH)) {
            return true;
        } else {
            addStepDescription("Product STH offer price is not displaying");
            return false;
        }
    }

    public boolean isProdBOPISListPriceDisplay() {
        if (isDisplayed(prodListPriceBopis)) {
            return true;
        } else {
            addStepDescription("Product List price is not displaying");
            return false;
        }
    }

    public boolean isProdSTHListPriceDisplay() {
        if (isDisplayed(prodListPriceSTH)) {
            return true;
        } else {
            addStepDescription("Product STH List price is not displaying");
            return false;
        }
    }

    public boolean isProdListPriceStrikeOut() {
        if (getElementTextDecoration(prodListPrice).toString().equalsIgnoreCase("line-through")) {
            return true;
        } else {
            addStepDescription("The list price is not strike out at review page after coupon apply");
            return false;
        }
    }

    public boolean isBopisProdPriceMatchSBPrice(String priceAtSB) {
        double prodOfferPrAtRP = 0.00;
        try {
            String bopisProdPrice = getText(prodOfferPriceBopis).replaceAll("[^0-9.]", "");
            prodOfferPrAtRP = Double.valueOf(bopisProdPrice);
        } catch (NullPointerException n) {
            prodOfferPrAtRP = 0.00;
        }
        double sb_PriceExp = (double) Math.round((Double.valueOf(priceAtSB)) * 100) / 100;
        if (Math.round(sb_PriceExp) == Math.round(prodOfferPrAtRP)) {
            return true;
        } else {
            addStepDescription("Shopping bag price expected " + sb_PriceExp + " review page price actual " + prodOfferPrAtRP);
            return false;
        }
    }

    public boolean isGiftCardSummaryFormat() {
        try {
            return getText(giftCardSummary_Balance).replaceAll("[0-9$]", "").contains("Ending in | Remaining Balance:");
        } catch (NullPointerException n) {
            addStepDescription("GiftCard Summary balance is not showing or element not found");
            return false;
        }
    }

    public boolean isCCAmountNotDispaly() {
        try {
            return !getText(paymentMethodUnderPaymentLabel).contains("$");
        } catch (NullPointerException n) {
            addStepDescription("Credit card  info element not found");
            return false;
        }
    }

    public boolean isCCAndGCDisplays() {
        return isDisplayed(paymentMethodUnderPaymentLabel) && isCCAmountNotDispaly() && isGiftCardSummaryFormat();
    }

    public boolean clickSubmitOrderBtnTocheckCvvNRes(int counter, String secCode, String card) {
        boolean check = false;
        switch (card) {
            case "Discover":
                for (int i = 0; i < counter; i++) {
                    waitUntilElementDisplayed(submOrderButton, 25);
                    click(submOrderButton);
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
                    waitUntilElementDisplayed(submOrderButton, 25);
                    click(submOrderButton);
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
                    waitUntilElementDisplayed(submOrderButton, 25);
                    click(submOrderButton);
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
                    waitUntilElementDisplayed(cvvTxtField, 5);
                    clearAndFillText(cvvTxtField, "");
                    clearAndFillText(cvvTxtField, secCode);
                    click(submOrderButton);
                    waitUntilElementDisplayed(errorBox, 20);
                    if (/*!waitUntilElementDisplayed(errorBox) && */getReturnCode()) {
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
        String response = getValueOfDataElement("response_addCheckout");
        logger.info("processOLPSResponse of updateShippingMethodSelectionResponse function: " + response);
        String returnCode = "\"errorCode\":\"";
        String errocode = "\",\"errorKey\"";
        int i = response.indexOf(returnCode) + returnCode.length();
        int j = response.indexOf(errocode);
        return response.substring(i, j).trim().equalsIgnoreCase("_TCP_CVV_REQUEST_FAILED_WITH_CREDIT_CARD_CVV_ERROR");
    }
    public boolean clickSubmOrderButtonCheck(OrderConfirmationPageActions orderConfirmationPageActions) {
        waitUntilElementDisplayed(submOrderButton, 30);
        click(submOrderButton);
        if(isDisplayed(orderSubmitSpinner) && waitUntilElementsAreDisplayed(submitButtonDisabled,2)){
            waitUntilElementDisplayed(orderConfirmationPageActions.confirmationTitle,6);
            return true;
        }
//        staticWait(10000);
        else{
            addStepDescription("Spinner and Submit buttons are not displayed");
            return false;
        }

    }

    /**
     * Validate progress indicators for all in billing Page
     *
     * @return true if all matches
     */
    public boolean validateProgressbarStatus() {
        waitUntilElementDisplayed(submitOrderBtn, 10);
        String shippingProgressBarStatus = getAttributeValue(shippingProgressBar, "class");
        String billingProgressBarStatus = getAttributeValue(billingProgressHeading, "class");
        String reviewProgressBarStatus = getAttributeValue(reviewProgressBar, "class");
        String title = getText(checkoutTitle);

        return shippingProgressBarStatus.equalsIgnoreCase("completed") &&
                billingProgressBarStatus.equalsIgnoreCase("completed") &&
                reviewProgressBarStatus.equalsIgnoreCase("active") &&
                title.equalsIgnoreCase("Review");
    }

    /**
     * Validate progress indicators for all in Billingpage Page
     *
     * @return true if all matches
     */
    public boolean validateProgressbarStatusForBopis() {
        waitUntilElementDisplayed(reviewProgressBar, 10);
        String shippingProgressBarStatus = getAttributeValue(pickupProgressBar,"class");
        String billingProgressBarStatus = getAttributeValue(billingProgressHeading,"class");
        String title = getText(checkoutTitle);
        String reviewProgressBarStatus = getAttributeValue(reviewProgressBar,"class");

        return  shippingProgressBarStatus.equalsIgnoreCase("completed") &&
                billingProgressBarStatus.equalsIgnoreCase("completed") &&
                reviewProgressBarStatus.equalsIgnoreCase("active") &&
                title.equalsIgnoreCase("Review");
    }

    public boolean validateProgressbarStatusForCombinedOrder() {
        waitUntilElementDisplayed(reviewProgressBar, 10);
        String picupProgressBarStatus = getAttributeValue(pickupProgressBar,"class");
        String shippingProgressBarStatus = getAttributeValue(shippingProgressBar, "class");
        String billingProgressBarStatus = getAttributeValue(billingProgressHeading,"class");
        String title = getText(checkoutTitle);
        String reviewProgressBarStatus = getAttributeValue(reviewProgressBar,"class");

        return  picupProgressBarStatus.equalsIgnoreCase("completed") &&
                shippingProgressBarStatus.equalsIgnoreCase("completed") &&
                billingProgressBarStatus.equalsIgnoreCase("completed") &&
                reviewProgressBarStatus.equalsIgnoreCase("active") &&
                title.equalsIgnoreCase("Review");
    }
    public boolean verifyEnteredBillingAddress(String fName, String lName, String addressLine1, String city, String stateShort, String zip, String country) {
        String billingAddress = billingAddressSection.getText().toLowerCase().replaceAll("\n", "").replaceAll(" ", "");
        String enteredAddress = (fName + lName + (addressLine1.replaceAll(" ", "")) + city + (",") + stateShort.toUpperCase() + (zip.replaceAll(" ", "") + country)).replaceAll("\n", "");
        if (billingAddress.equalsIgnoreCase(enteredAddress)) {
            return true;
        } else {
            return false;
        }
    }

}
