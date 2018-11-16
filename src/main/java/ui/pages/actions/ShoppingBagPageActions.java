package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import ui.pages.repo.ShoppingBagPageRepo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skonda on 5/19/2016.
 */
public class ShoppingBagPageActions extends ShoppingBagPageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(ShoppingBagPageActions.class);

    public ShoppingBagPageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean clickRemoveLinkByPosition(int position) {
        scrollDownToElement(titleText);
        mouseHover(titleText);
        String itemBeforeRemove = getText(titleText).replaceAll("[^0-9.]", "");
        int itemsBeforeRemove = Integer.parseInt(itemBeforeRemove);
        staticWait(2000);

        WebElement removeElement = removeLinkByItemPosition(position);
        click(removeElement);
        waitUntilElementDisplayed(notification_ItemUpdated, 20);
        staticWait(5000);
        waitUntilElementDisplayed(titleText, 30);
        int countAfterRemove = 0;
        try {
            String itemAfterProdRemove = getText(titleText).replaceAll("[^0-9.]", "");
            countAfterRemove = Integer.parseInt(itemAfterProdRemove);
        } catch (Exception e) {
            countAfterRemove = 0;
        }
        staticWait(6000);

        //   boolean isProductAddedToWL = (itemsBeforeRemove - 1) == countAfterRemove;
        boolean isProductAddedToWL = itemsBeforeRemove > countAfterRemove;

        addStepDescription("Products in cart before remove : " + itemsBeforeRemove + " products in cart after remove: " + countAfterRemove);

        return isProductAddedToWL;
    }

    /*public double getItemPrice(){
        String shoppingBag_ItemPrice = itemPriceFirst.getText();
        return Double.parseDouble(shoppingBag_ItemPrice.substring(shoppingBag_ItemPrice.indexOf("$")).replace("$",""));
    }*/
    public double getTotalPrice() {
        waitUntilElementDisplayed(totalPriceFirst, 20);
        String shoppingBag_TotalPrice = getText(totalPriceFirst);
        return Double.parseDouble(shoppingBag_TotalPrice.substring(shoppingBag_TotalPrice.indexOf("$")).replace("$", ""));
    }

    public boolean verifyPriceUpdatedWithQtyUpdate(String updateQty, double prodUnitPrice) {
        waitUntilElementDisplayed(editBtnLnk, 10);
        click(editBtnLnk);
        waitUntilElementDisplayed(qtyDropdown, 10);
//        double price = getTotalPrice();
        selectDropDownByVisibleText(qtyDropdown, updateQty);

        waitUntilElementDisplayed(updateButton, 10);
        click(updateButton);
//        staticWait(9000);
        waitUntilElementDisplayed(prodLoadingSymbol, 5);
        waitUntilElementDisplayed(notification_ItemUpdated, 20);
        verifyElementNotDisplayed(prodLoadingSymbol, 20);

        double updateQuantity = Double.parseDouble(updateQty);
        double updatedTotalPrice = prodUnitPrice * updateQuantity;

        double updatedPrice = getTotalPrice();
        if (Math.round(updatedTotalPrice) == Math.round(updatedPrice)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyItemOfferPriceWithUpdatedQty(String updateQty, double prodUnitPrice) {

        double updateQuantity = Double.parseDouble(updateQty);
        double updatedTotalPrice = prodUnitPrice * updateQuantity;

        double updatedPrice = GetPrice();
        if (Math.round(updatedTotalPrice) == Math.round(updatedPrice)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verifyItemListPriceWithUpdatedQty(String updateQty, double prodOriginalPrice) {

        double updateQuantity = Double.parseDouble(updateQty);
        double updatedTotalPrice = prodOriginalPrice * updateQuantity;

        double updatedPrice = getListPrice();
        if (updatedTotalPrice == updatedPrice) {
            return true;
        } else {
            return false;
        }
    }


    public String getFirstOccur_BopisProdQty() {
        String qtyPresentForBopis = getText(getFirstElementFromList(qtyPresentForBopisProd)).replace("Quantity:", "").trim();
        return qtyPresentForBopis;
    }

    public String getFirstOccur_NormalProdQty() {
        boolean isShipItChkBoxSelected = false;
        String qtyPresentForNormalProd = "Looks like the quantity label not present for Normal prod";
        int shipItCheckBoxesSize = shipItCheckBoxes.size();
        for (int i = 0; i < shipItCheckBoxesSize; i++) {
            isShipItChkBoxSelected = getAttributeValue(shipItCheckBoxes.get(i), "class").equalsIgnoreCase("input-radio-icon-checked");
            if (isShipItChkBoxSelected) {
                qtyPresentForNormalProd = getText(qtyPresentForNormalProdByPos(i + 1)).replace("Quantity:", "").trim();
                break;
            }
        }
        return qtyPresentForNormalProd;

    }

    public boolean updateFirstOccur_BopisProdQty(BopisOverlayActions bopisOverlayActions, String qtyToUpdate, String zip) {
        click(getFirstElementFromList(editLinksForBopisProd));

        if (waitUntilElementDisplayed(bopisOverlayActions.zipCodeField, 20)) {
            boolean isStoreAvail = bopisOverlayActions.updateQty(qtyToUpdate, zip);
            if (isStoreAvail) {
                return getFirstOccur_BopisProdQty().equalsIgnoreCase(qtyToUpdate);
            }
        } else if (isDisplayed(bopisOverlayActions.checkAvailability)) {
            boolean isStoreAvail = bopisOverlayActions.editInTwoStoreModal(qtyToUpdate);
            if (isStoreAvail) {
                return getFirstOccur_BopisProdQty().equalsIgnoreCase(qtyToUpdate);
            }
        }
        return false;
    }

    public boolean removeFirstOccur_BopisProd() {
        String prodTitleToRemove = getAttributeValue(getFirstElementFromList(removeButtonForBopisProd), "title");
        click(getFirstElementFromList(removeButtonForBopisProd));
        staticWait(5000);
        try {
            return !waitUntilElementDisplayed(getFirstElementFromList(removeButtonForBopisProd), 10);
        } catch (Throwable t) {
            return true;
        }

    }

    public boolean updateFirstOccur_NormalProdQty(String qtyToUpdate) {
        boolean isShipItChkBoxSelected = false;
        int shipItCheckBoxesSize = shipItCheckBoxes.size();
        for (int i = 0; i < shipItCheckBoxesSize; i++) {
            isShipItChkBoxSelected = getAttributeValue(shipItCheckBoxes.get(i), "class").equalsIgnoreCase("input-radio-icon-checked");
            if (isShipItChkBoxSelected) {
                click(editFromSelectedShipItRadioByPos(i + 1));
                waitUntilElementDisplayed(qtyDropdown, 20);
                selectDropDownByValue(qtyDropdown, qtyToUpdate);
                click(updateButton);
                staticWait(5000);
                verifyElementNotDisplayed(prodLoadingSymbol);
                break;
            }
        }
//        int shipItRadioButtons = shipItProdDescription.size();
//        for(int i = 0; i<shipItRadioButtons; i++) {
//            String isShipItProdSelected = getAttributeValue(shipItProdDescription.get(i), "aria-checked");
//            if(isShipItProdSelected.equalsIgnoreCase("true")){
//                click(editFromSelectedShipItRadioByPos(i));
//                waitUntilElementDisplayed(qtyDropdown);
//                selectDropDownByValue(qtyDropdown,qtyToUpdate);
//                click(updateButton);
//                waitUntilElementDisplayed(notification_ItemUpdated);
//                break;
//            }
//        }
        return getFirstOccur_NormalProdQty().equalsIgnoreCase(qtyToUpdate);
    }

    public boolean removeFirstOccur_NormalProd() {
        boolean isShipItProdRemoved = false, isShipItChkBoxSelected = false;
        int shipItCheckBoxesSize = shipItCheckBoxes.size();
        for (int i = 0; i < shipItCheckBoxesSize; i++) {
            isShipItChkBoxSelected = getAttributeValue(shipItCheckBoxes.get(i), "class").equalsIgnoreCase("input-radio-icon-checked");
            if (isShipItChkBoxSelected) {
                click(removeFromSelectedShipItProdByPos(i + 1));
                try {
                    isShipItProdRemoved = verifyElementNotDisplayed(removeFromSelectedShipItProdByPos(i + 1));
                } catch (Throwable t) {
                    isShipItProdRemoved = true;
                }
                break;
            }
        }
        return isShipItProdRemoved;
    }


    public String getErrorMessage_InvalidPaypal() {
        waitUntilElementDisplayed(wrongPaypalErrorMessage, 20);
        return getText(wrongPaypalErrorMessage);
    }

    public boolean getErrorMessage_InvalidPaypalStore(String error) {
        waitUntilElementDisplayed(WrongPaypalStoreErrorMessage, 20);
        boolean errorMsg = getText(WrongPaypalStoreErrorMessage).contains(error);
        return errorMsg;
    }

    public String errPayPalCancel() {
        waitUntilElementDisplayed(errPayPalCancel, 10);
        return getText(errPayPalCancel);
    }

    public boolean validateEmptyShoppingCart() {
        if (isDisplayed(emptyBagMessage)) {
            return true;
        } else {
            int n = itemDescElements.size();
            for (int i = 0; i < n; i++) {
                click(removeLinkByItemPosition(1));
                waitUntilElementDisplayed(notification_ItemUpdated, 20);
            }
            return waitUntilElementDisplayed(emptyBagMessage, 10);
        }
    }

    public List<String> getUPCNumbers() {
        List<String> upcNums = new ArrayList<>();
        int upcCt = upcNumbers.size();
        for (int i = 0; i < upcCt; i++) {
            upcNums.add(getUPCNumber(getText(upcNumbers.get(i))));
        }
        return upcNums;
    }

    public String getUPCNumber(String upcNumber) {
        int startIndex = upcNumber.indexOf("-");
        return upcNumber.substring(startIndex + 1);
    }

    public boolean enterInvalidCouponCodeAndApply(String couponCode) {
        clearAndFillText(couponCodeFld, couponCode);
        click(applyCouponButton);
        return waitUntilElementDisplayed(invalidErrMsg);
    }


    public boolean removeDiscountIfApplied() {
        if (waitUntilElementDisplayed(removeLinkDiscount, 10)) {
            click(removeLinkDiscount);
        }
        return verifyElementNotDisplayed(removeLinkDiscount);

    }

    public boolean isProductsAddedByUpcNums(List<String> upcNums) {
        boolean isProdAvailable = false, tempBool = true;
        for (int i = 0; i < upcNums.size(); i++) {
            try {
                isProdAvailable = tempBool && waitUntilElementDisplayed(upcNumElemByUpcNum(upcNums.get(i).replace("CUP", "")), 5);
            } catch (Throwable t) {
                isProdAvailable = false;
            }
            tempBool = isProdAvailable;
        }
        return isProdAvailable;
    }

    public List<String> getProductNames() {
        List<String> prodNames = new ArrayList<>();
        for (WebElement prodTitleElement : prodTitles) {
            String prodName = getText(prodTitleElement);
            prodNames.add(prodName);
        }
        return prodNames;
    }

    public boolean clickEspotCreateAccLnk(CreateAccountActions createAccountActions) {
        waitUntilElementDisplayed(eSpotCreateAccountLnk, 30);
        click(eSpotCreateAccountLnk);
        return waitUntilElementDisplayed(createAccountActions.createAccountButton, 5);
    }

    public boolean clickLoginFromEspotLnk(LoginPageActions loginPageActions) {
        scrollDownToElement(eSpotLoginLnk);
        waitUntilElementDisplayed(eSpotLoginLnk, 5);
        click(eSpotLoginLnk);
        return waitUntilElementDisplayed(loginPageActions.forgotPasswordLink, 5);
    }

    // Newly added
    public boolean validateEmptyTextInShoppingBagPage(String text1) {

        boolean checkEmptyMessage = getText(emptyBagTextContainer).equalsIgnoreCase(text1);

        if (checkEmptyMessage)
            return true;
        else
            return false;
    }

    //DT 2 Actions

    public boolean validateOrderLedgerSection(HeaderMenuActions headerMenuActions) {
//        staticWait(10000);
        waitUntilElementDisplayed(orderSummarySection, 10);
        String itemSummaryLbl = getText(itemPriceSummary);
        staticWait(3000);
        int productQtyCount = 0, totalProdQty = 0;
        for (int i = 0; i < quantityLblFirst.size(); i++) {
            productQtyCount = Integer.parseInt(getText(quantityLblFirst.get(i)).replaceAll("[^0-9]", ""));
            totalProdQty = totalProdQty + productQtyCount;
        }
        int itemCount = Integer.parseInt(itemSummaryLbl.substring(0, 8).replaceAll("[^0-9]", ""));
        int bagCount = Integer.parseInt(getText(headerMenuActions.shoppingCartImg));
        boolean checkCount = (itemCount == bagCount && totalProdQty == bagCount);
        return checkCount;
    }

    public boolean clickWLIconAsRegUser() {
        waitUntilElementDisplayed(wlIconByPosition(1), 10);
        click(wlIconByPosition(1));
        return waitUntilElementDisplayed(emptyBagTextContainer, 40);
    }

    public boolean clickWLAsRegUser() {
        waitUntilElementDisplayed(wlLink, 10);
        click(wlLink);
        return waitUntilElementDisplayed(emptyBagTextContainer, 30);

    }

    public boolean removeProduct() {
        waitUntilElementDisplayed(closeIcon, 10);
        click(closeIcon);
        return waitUntilElementDisplayed(emptyBagTextContainer, 20);

    }

    public boolean clickOnCheckoutBtnAsReg(ShippingPageActions shippingPageActions, ReviewPageActions reviewPageActions) {
        staticWait(1000);
        scrollUpToElement(checkoutBtn);
        click(checkoutBtn);
        if (waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 40) || reviewPageActions.isReviewPageDisplayed()) {
            return true;
        } else {
            addStepDescription("Clicking on checkout button as registered is either not navigating to shipping or review page for express");
            return false;
        }
    }

    public boolean clickWLIconAsGuestUser(LoginPageActions loginPageActions) {
        waitUntilElementDisplayed(wlIconByPosition(1), 10);
        click(wlIconByPosition(1));
        return waitUntilElementDisplayed(loginPageActions.loginButton, 20);
    }

    public boolean clickWLGuestUser(LoginPageActions loginPageActions) {
        waitUntilElementDisplayed(wlLink, 10);
        click(wlLink);
        return waitUntilElementDisplayed(loginPageActions.emailAddrField, 20);
    }

    public boolean clickCheckoutAsGuest(LoginPageActions loginPageActions) {
        scrollUpToElement(checkoutBtn);
        click(checkoutBtn);
        return waitUntilElementDisplayed(loginPageActions.continueAsGuestButton, 20);
    }

    public boolean clickEditLnk(WebElement editLink) {

        click(editLink);
        return waitUntilElementDisplayed(cancelLink, 20);

    }

    public boolean clickCancelLink(WebElement editLink) {
        click(cancelLink);
        return verifyElementNotDisplayed(editLink, 30);
    }

    public boolean clickEditLinkAndCheckoutWithoutSave() {
        clickEditLnk(getFirstElementFromList(editLnks));
        click(checkoutBtn);
        return waitUntilElementDisplayed(cartAlert, 10) && waitUntilElementDisplayed(backToBagButton, 10) && waitUntilElementDisplayed(contToCheckoutButton, 10);
    }

    public boolean clickUpdateButton() {
        click(updateButton);
        staticWait(5000);
        return waitUntilElementDisplayed(prodSize, 30);
    }

    public boolean moveProdToWLByPositionAsReg(int position) {
        int prodBagBeforeMovingToWL = prodNames.size();
        WebElement moveProdToWL = moveToWLByPosition(position);
        click(moveProdToWL);
        //wait until updated message is displayed
        waitUntilElementDisplayed(notification_ItemUpdated);
        staticWait(5000);
        int prodBagAfterMovingToWL = prodNames.size();
        return prodBagBeforeMovingToWL > prodBagAfterMovingToWL;
    }

    public boolean moveProdToWLByPositionAsGuest(LoginPageActions loginPageActions, int position) {
        WebElement moveProdToWL = moveToWLByPosition(position);
        click(moveProdToWL);
        staticWait(2000);
        return waitUntilElementDisplayed(loginPageActions.emailFldOnLoginForm);
    }

    public boolean moveProdToWLByPositionAsRemembered(LoginPageActions loginPageActions, int position) {
        WebElement moveProdToWL = moveToWLByPosition(position);
        click(moveProdToWL);
        staticWait(2000);
        return waitUntilElementDisplayed(loginPageActions.rememberedLogout);
    }

    public boolean isMoveToFavToolTipDisplayed() {
        mouseHover(moveToWLByPosition(1));
        boolean moveToFav = getText(moveToFavoritesFlag).equalsIgnoreCase("MOVE TO FAVORITES");
        return moveToFav;
    }

    public boolean isRemoveFromBagToolTipDisplayed() {
        mouseHover(removeLinkByItemPosition(1));
        boolean remFromBag = getText(removeFromBagFlag).equalsIgnoreCase("REMOVE FROM BAG");
        return remFromBag;
    }

    public String getProdTitleByPosition(int i) {
        return getText(prodTitleByPos(i));
    }

    public boolean isProdPresentByTitle(String title) {
        for (WebElement prodTitle : prodTitles) {
            if (getText(prodTitle).contains(title)) {
                return true;
            }
        }
        return false;
    }

    public boolean addToFavorite(String upcNum) {
        click(favoriteElementByUpcNum(upcNum));
        return waitUntilElementDisplayed(inlineNotification, 60);
    }

    public boolean clickProceedToCheckoutExpress(ReviewPageActions reviewPageActions) {
        scrollUpToElement(checkoutBtn);
        waitUntilElementDisplayed(checkoutBtn, 20);
        click(checkoutBtn);
        staticWait(3000);
        return waitUntilElementDisplayed(reviewPageActions.submOrderButton, 30) || waitUntilElementDisplayed(reviewPageActions.cvvTxtField, 3);
    }

    public String getPickUpInStoreName() {
        return getText(pickUpInStoreName).replaceAll("at ", "");
    }

    public boolean clickChangeStoreLink(BopisOverlayActions bopisOverlayActions) {
        waitUntilElementDisplayed(changeStoreLnk, 20);
        click(changeStoreLnk);
        return waitUntilElementDisplayed(bopisOverlayActions.zipCodeField, 20);
    }

    public boolean clickChangeStoreLinkTwoStore(BopisOverlayActions bopisOverlayActions) {
        waitUntilElementDisplayed(changeStoreLnk, 20);
        click(changeStoreLnk);
        return waitUntilElementDisplayed(bopisOverlayActions.checkAvailability, 20);
    }

    public boolean clickNormalShippingRadioButton(ShoppingBagPageActions shoppingBagPageActions) {
        click(getFirstElementFromList(shipItCheckBoxes));
        return waitUntilElementDisplayed(shoppingBagPageActions.shippingText, 20);
    }

    public boolean clickBopisShippingRadioButton(BopisOverlayActions bopisOverlayActions) {
        waitUntilElementsAreDisplayed(bopisRadioUnChkedBoxes, 30);
        click(getFirstElementFromList(bopisRadioUnChkedBoxes));
        return waitUntilElementDisplayed(bopisOverlayActions.headerContent, 20);
    }

    public boolean clickBopisRadioAndCloseOverlayAndVerify(BopisOverlayActions bopisOverlayActions) {
        clickBopisShippingRadioButton(bopisOverlayActions);
        bopisOverlayActions.closeBopisOverlayModal();
        return isShipItRadioChecked();
    }

    public boolean isProdOutOfStockOnEdit() {
        boolean isProdOutOfStock = false;

        if (isDisplayed(prodOutOfStockOnEdit)) {
            isProdOutOfStock = true;
//                click(cancelPerInfoButton);
//                waitUntilElementDisplayed(editLnkOnPersonalInfo);
        } else {
            isProdOutOfStock = false;
        }

        return isProdOutOfStock;
    }

    public boolean changeProdSize(WebElement editLnk) {
        String sizeBeforeEdit = getText(prodSize);
        String toBeSelected = sizeBeforeEdit;
        //        scrollUpToElement(editLnkOnPersonalInfo);
        clickEditLnk(editLnk);
        while (toBeSelected.equals(sizeBeforeEdit)) {
            int sizeCount = listOfSize.size();
            if (sizeCount > 0) {

                WebElement sizeSelected = listOfSize.get(randInt(0, (listOfSize.size() - 1)));
                toBeSelected = getText(sizeSelected);
                click(sizeSelected);
                staticWait();
                return !toBeSelected.equals(sizeBeforeEdit);
            }
            if (sizeCount == 0) {
                //  click(selectedSize);
                toBeSelected = getText(selectedSize);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean changeProdSize() {

        int sizeCount = listOfSize.size();
        if (sizeCount >= 1) {
            WebElement sizeSelected = listOfSize.get(randInt(0, (listOfSize.size() - 1)));
            click(sizeSelected);
            staticWait();
            return true;
        } else if (sizeCount == 1) {
            click(selectedSize);
            staticWait(5000);
            return true;
        } else {
            click(cancelLink);
            staticWait(5000);
            return false;
        }

    }

    public boolean isPickUpInStoreRadioChecked() {
//        driver.navigate().refresh();
        boolean isPickUpInStoreChkBoxSelected = false;
        staticWait(5000);
        waitUntilElementDisplayed(getFirstElementFromList(bopisRadioChkBoxes), 30);
        int pickUpInStoreCheckBoxesSize = bopisRadioChkBoxes.size();
        for (int i = 0; i < pickUpInStoreCheckBoxesSize; i++) {
            isPickUpInStoreChkBoxSelected = getAttributeValue(bopisRadioChkBoxes.get(i), "class").equalsIgnoreCase("input-radio-icon-checked");
            if (isPickUpInStoreChkBoxSelected) {
                break;
            }
        }
        return isPickUpInStoreChkBoxSelected;
    }

    public boolean isPickUpInStoreRadoCheckedByPos(int i) {
        try {
            return waitUntilElementDisplayed(bopisRadioChkBoxes.get(i), 1);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isShipItRadioChecked() {
        boolean isShipItChkBoxSelected = false;
        int shipItCheckBoxesSize = shipItCheckBoxes.size();
        for (int i = 0; i < shipItCheckBoxesSize; i++) {
            isShipItChkBoxSelected = getAttributeValue(shipItCheckBoxes.get(i), "class").equalsIgnoreCase("input-radio-icon-checked");
            if (isShipItChkBoxSelected) {
                break;
            }
        }
        return isShipItChkBoxSelected;
    }

    public boolean changeStoreLink() {
        if (isPickUpInStoreRadioChecked()) {
            click(STHCheckBox);
        }
        return verifyElementNotDisplayed(changeStoreLnk, 20);
    }

    public boolean isChangeAStoreLinkDisplaying() {
        return waitUntilElementDisplayed(getFirstElementFromList(changeAStoreLinks));
    }


//    public boolean applyCouponCode(String couponCode) {
//        waitUntilElementDisplayed(couponCodeFld, 20);
//        clearAndFillText(couponCodeFld, couponCode);
//        click(applyCouponButton);
//        boolean isButtonDisabled = !isEnabled(applyCouponButton);
//        boolean couponAppliedAtOrderLedger = waitUntilElementDisplayed(couponCodeApplied, 10);
//        return isButtonDisabled && couponAppliedAtOrderLedger && waitUntilElementDisplayed(appliedCouponText, 20);
//    }

    public boolean applyCouponCodeForGC(String couponCode) {
        waitUntilElementDisplayed(couponCodeFld, 20);
        clearAndFillText(couponCodeFld, couponCode);
        click(applyCouponButton);
        return waitUntilElementDisplayed(invalidErrMsg);
    }


    public boolean validateCouponCodeSection() {
        return isDisplayed(couponCodeFld) &&
                isDisplayed(applyCouponButton) &&
                isDisplayed(needHelpLink);
    }

    public boolean removeCoupon() {
        click(removeCoupon);
        return !waitUntilElementDisplayed(appliedCouponText, 10);
    }

    public boolean removeCouponAndCheckTotal() {
        try {
            click(removeCoupon);
        } catch (Exception e) {
            addStepDescription("Remove coupon is not displaying");
            return false;
        }
        calculatingEstimatedTotal();
        return waitUntilElementDisplayed(estimatedTotal, 20);

    }

    public boolean clickProceedToBOPISCheckout(CheckoutPickUpDetailsActions checkoutPickUpDetailsActions) {
        waitUntilElementDisplayed(checkoutBtn);
        click(checkoutBtn);
        if (isDisplayed(continueAsGuest)) {
            click(continueAsGuest);
        }
        return waitUntilElementDisplayed(checkoutPickUpDetailsActions.nxtButton);
    }

    public boolean applyCouponAndCheckTotal(String couponCode) {
        applyCouponCode(couponCode);
        String originalCost = getText(originalPrice).replaceAll("[^0-9.]", "");
        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float originalAmount = Float.parseFloat(originalCost);
        float estimatedTot = Float.parseFloat(estimatedCost);

        if (originalAmount == estimatedTot) {
            return true;
        }
        return waitUntilElementDisplayed(estimatedTotal, 20);
    }

    public boolean validateTotalAfterRemoveCoupon() {
        try {
            click(removeCoupon);
        } catch (Exception e) {
            addStepDescription("Remove button on coupon apply is not displaying");
            return false;
        }
        String offerCost = getText(offerPrice).replaceAll("[^0-9.]", "");
        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float offerAmount = Float.parseFloat(offerCost);
        float estimatedTot = Float.parseFloat(estimatedCost);

        if (offerAmount == estimatedTot) {
            return true;
        }
        return waitUntilElementDisplayed(estimatedTotal, 20);
    }


    public boolean verifyOriginalPriceElementAndColor(String oriPriceColor) {
        return isDisplayed(originalPrice) && convertRgbToTextColor(originalPrice.getCssValue("color")).equalsIgnoreCase(oriPriceColor);
    }

    public boolean verifyOfferPriceElementAndColor(String offerPriceColor) {
        return isDisplayed(offerPrice) && convertRgbToTextColor(offerPrice.getCssValue("color")).equalsIgnoreCase(offerPriceColor);

    }

    public boolean isOriginalPriceInBold() {
        waitUntilElementDisplayed(offerPr, 10);
        String s = offerPr.getCssValue("font-weight");
        return Integer.parseInt(s) >= 700;
    }

    public boolean validateTitleAndBagCount() {
        String title = getText(titleText).replaceAll("[^0-9]", "").trim();
        String bagIcon = getText(shoppingBagIcon).replaceAll("[^0-9]", "").trim();

        int titleCount = Integer.parseInt(title);
        int bagCount = Integer.parseInt(bagIcon);

        if (titleCount == bagCount)
            return true;
        else
            addStepDescription("The bad title count is " + titleCount + " the total bag count on the header is " + bagCount);
        return false;
    }

    public String getBagCount() {
        String bagIcon = getText(shoppingBagIcon).replaceAll("[^0-9]", "");
        return bagIcon;
    }

    public boolean validateProdDetails() {
        if (isDisplayed(getFirstElementFromList(prodNames)) &&
                isDisplayed(getFirstElementFromList(editLnks)) &&
                isDisplayed(upcNumber) &&
                isDisplayed(prodColor) &&
                isDisplayed(prodSize) &&
                isDisplayed(prodQty) &&
                isDisplayed(orderSummarySection) &&
                isDisplayed(prodImgsByPos(1)) &&
                isDisplayed(removeLinkByItemPosition(1)) &&
                isDisplayed(moveToWLByPosition(1))) {
//                isDisplayed(plccEspot_Guest)) {
            return true;
        }
        return false;
    }

    public boolean validateGCProdDetails() {
        boolean favIcon = false;
        try {
            favIcon = isDisplayed(moveToWLByPosition(1));
        } catch (Exception e) {
            addStepDescription("Fav Icon is not present which is true");
        }
        boolean productNames = isDisplayed(getFirstElementFromList(prodNames));
        boolean editLinks = isDisplayed(getFirstElementFromList(editLnks));
        boolean isUpc = isDisplayed(upcNumber);
        boolean isProdColor = isDisplayed(prodColor);
//        boolean isProdSize = isDisplayed(prodSize);
        boolean isProdQty = isDisplayed(prodQty);
        boolean oss = isDisplayed(orderSummarySection);
        boolean prodImg = isDisplayed(prodImgsByPos(1));
        boolean removeLnk = isDisplayed(removeLinkByItemPosition(1));
        if (productNames &&
                editLinks &&
                isUpc &&
                isProdColor &&
//                 isProdSize &&
                isProdQty &&
                oss &&
                prodImg &&
                removeLnk &&
                !favIcon)
//                isDisplayed(plccEspot_Guest)
        {
            return true;
        }
        addStepDescription("ProductNames " + productNames + " editLinks " + editLinks + " Upc Number " + isUpc + " Prod Color " + isProdColor
                + " prod qty " + isProdQty + " Order Summary section " + oss + " Prod Img " + prodImg + " remove link " + removeLnk + " fav Icon is not showing " + !favIcon);
        return false;
    }

    public boolean applyRewardsByPosition(int position) {
        int counter = 0;
        waitUntilElementDisplayed(availableRewardsTxt, 20);
        while (isDisplayed(showMoreLnk)) {
            click(showMoreLnk);
            staticWait(3000);
            counter++;
            if (waitForTextToAppear(showMoreLnk, "Show less", 30) || counter >= 5) {
                break;
            }
        }

        WebElement apply_10Percent_Rewards = applyToOrderButtonFor_10Percent(position);//applyCouponsByPosition(position);
        click(apply_10Percent_Rewards);
        return waitUntilElementDisplayed(appliedCouponText, 20);
    }

    public boolean rewardsCountDisplayByDefault() {
        int rewardsDisplayCount = rewardsContentDisplay.size();
        if (rewardsDisplayCount < 3) {
            return !isDisplayed(showMoreLnk);
        } else if (rewardsDisplayCount == 3) {
            return isDisplayed(showMoreLnk);
        }
        return false;
    }

    public boolean clickShowLessAndVerifyAvailCoupons() {
        click(showMoreLnk);
        boolean showMoreLink = waitForTextToAppear(showMoreLnk, "Show more", 30);
        int rewardsCountAfterShowLessClick = rewardsContentDisplay.size();
        return (rewardsCountAfterShowLessClick == 3) && showMoreLink;

    }

    public boolean availableRewardsCountDisplay() {
        int rewardsCount = 0;
        try {
            rewardsCount = Integer.parseInt(getText(availableRewardsTxt).replaceAll("[^0-9]", ""));
        } catch (NullPointerException n) {
            rewardsCount = 0;
        }
        return rewardsCount > 0;
    }

    public boolean validateHeaderBanner() {
        waitUntilElementDisplayed(headerBanner, 30);
        click(closeHeaderBanner);
        verifyElementNotDisplayed(headerBanner, 30);
        return true;
    }

    public boolean clickOnProdName(ProductDetailsPageActions productDetailsPageActions) {
        waitUntilElementDisplayed(getFirstElementFromList(prodNames), 20);
        click(getFirstElementFromList(prodNames));
        return waitUntilElementDisplayed(productDetailsPageActions.productName, 20);
    }

    public boolean validateBagWithProd() {
        if (validateProdDetails())
            return true;
        else
            return false;
    }

    public boolean validateBagWithoutProd() {
        if (waitUntilElementDisplayed(emptyBagMessage, 10) &&
                verifyElementNotDisplayed(plccEspot_Guest, 5) &&
                verifyElementNotDisplayed(orderSummarySection, 5))
            return true;
        else
            return false;
    }

    public boolean validateSplCharErrMsg(String splChar, String splCharErrorMsg) {
        waitUntilElementDisplayed(couponCodeFld, 30);
        clearAndFillText(couponCodeFld, splChar);
        click(applyCouponButton);
        staticWait(3000);
        waitUntilElementDisplayed(invalidErrMsg, 20);
        boolean validateSplCharErrMsg = getText(invalidErrMsg).contains(splCharErrorMsg);

        if (validateSplCharErrMsg)
            return true;
        else
            return false;
    }

    public boolean validateEmptyFiledErrMsg(String errMsg)

    {
        waitUntilElementDisplayed(couponCodeFld, 30);
        couponCodeFld.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(inlineErrMsg, 10);
        boolean validateCouponErr = getText(inlineErrMsg).contains(errMsg);
        if (validateCouponErr)
            return true;
        else
            return false;
    }

    public boolean validateExpiredCouponErrMsg(String expiredCode, String expiredErrorMsg) {
        waitUntilElementDisplayed(couponCodeFld, 30);
        clearAndFillText(couponCodeFld,"");
        clearAndFillText(couponCodeFld, expiredCode);
        click(applyCouponButton);
        waitUntilElementDisplayed(invalidErrMsg, 20);
        boolean validateExpiredPasswordErr = getText(invalidErrMsg).contains(expiredErrorMsg);

        if (validateExpiredPasswordErr == true) {
            return true;
        } else {
            addStepDescription("Check with the error copy");
            return false;
        }
    }

    public boolean applyCouponAndVerify(String couponCode) {
        clearAndFillText(couponCodeFld, couponCode);
        click(applyCouponButton);
        boolean couponAppliedAtOrderLedger = waitUntilElementDisplayed(couponCodeApplied, 10);
        return couponAppliedAtOrderLedger;
    }

    public boolean applySameCodeMoreThanOneTime(String couponCode, String appliedCouponErr) {
        applyCouponCode(couponCode);
        applyCouponAndVerify(couponCode);
        waitUntilElementDisplayed(getFirstElementFromList(errorBox), 10);
        boolean validateAppliedErrMsg = getText(getFirstElementFromList(errorBox)).contains(appliedCouponErr);
        if (validateAppliedErrMsg == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean clickOnCheckoutBtnAsBopisReg(PickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(checkoutBtn, 10);
        click(checkoutBtn);
        staticWait(2000);
        return waitUntilElementDisplayed(pickUpPageActions.nxtShippingBtn, 20);
    }

    public boolean clickCheckoutAsRegBopisOnly(PickUpPageActions pickUpPageActions) {
        scrollToTheTopHeader(checkoutBtn);
        waitUntilElementDisplayed(checkoutBtn, 10);
        click(checkoutBtn);
        staticWait(2000);
        return waitUntilElementDisplayed(pickUpPageActions.nxtBillingBtn, 20);
    }

    public boolean clickCheckoutAsGuestBopisOnly(LoginPageActions loginPageActions, PickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(checkoutBtn, 10);
        click(checkoutBtn);
        waitUntilElementDisplayed(loginPageActions.continueAsGuestButton, 20);
        click(continueAsGuest);
        return waitUntilElementDisplayed(pickUpPageActions.nxtBillingBtn, 20);
    }

    public String getProdPrice() {

        String offerPr = getText(offerPrice).replaceAll("[^0-9.]", "");
        return offerPr;
    }

    public boolean updateQty(String qty) {
        for (int i = 0; i < editLnks.size(); i++) {
            clickEditLnk(editLnks.get(i));
        }
        selectDropDownByValue(qtyDropdown, qty);

        return clickUpdateButton();
    }

    public boolean isChangedBopisStore(String pickUpStoreNameBefore) {
        String pickUpStoreNameAfterChanging = getPickUpInStoreName();

        boolean validateStoreChange = pickUpStoreNameAfterChanging.contains(pickUpStoreNameBefore);

        if (!validateStoreChange) {
            logger.info("The Store has been changed successfully");
            return true;
        } else
            return false;

        /*,"Verifying the Pickup In Store Name after selecting different store");*/

    }

    public String getUPCNumByPosition(int position) {
        return getText(getUPCNumWebElementByPosition(position)).replaceAll("[^0-9]", "");

    }

    public boolean isUPCNumPresentByUpc(String upcNum) {
        return isDisplayed(getUPCNumWebElementByUPC(upcNum));

    }

    public boolean emptyBagContent(HeaderMenuActions headerMenuActions, ShoppingBagDrawerActions shoppingBagDrawerActions, ShoppingBagPageActions shoppingBagPageActions) {
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        return validateEmptyShoppingCart();
    }

    public boolean checkAirmilesNo(ShoppingBagDrawerActions shoppingBagDrawerActions) {

        String airMilesDrawer = shoppingBagDrawerActions.getAirMilesNo();
        String airMilesBag = getText(airMilesInBag).replaceAll("[^0-9]", "");

        if (airMilesDrawer.equals(airMilesBag)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkBOPISInelegible() {
        if (isEnabled(unavailablePickUpInStoreName)) {
            return true;
        } else {
            return false;
        }
    }


    public boolean clickContinueCheckoutAsGuest(LoginPageActions loginPageActions, ShippingPageActions shippingPageActions) {
        scrollUpToElement(checkoutBtn);
        click(checkoutBtn);
        waitUntilElementDisplayed(loginPageActions.continueAsGuestButton, 20);
        click(continueAsGuest);

        if (waitUntilElementDisplayed(shippingPageActions.nextBillingButton)) {
            return true;
        } else {
            addStepDescription("Clicking on checkout button as guest not navigating to shipping page");
            return false;
        }
    }

    public boolean clickContinueCheckoutAsGuestBOPIS(LoginPageActions loginPageActions, PickUpPageActions pickUpPageActions) {
        scrollUpToElement(checkoutBtn);
        click(checkoutBtn);
        waitUntilElementDisplayed(loginPageActions.continueAsGuestButton, 20);
        click(continueAsGuest);

        if (waitUntilElementDisplayed(pickUpPageActions.nxtBillingBtn)) {
            return true;
        } else {
            addStepDescription("Clicking on checkout button as guest not navigating to shipping page");
            return false;
        }
    }

    public boolean getMPRESpotColor(String mprEspotColor) {
        return convertRgbToTextColor(myPlaceRewardsEspot.getCssValue("color")).equalsIgnoreCase(mprEspotColor);

    }

    public boolean clickDetailsOnFreeShipEveryOrder() {
        if (waitUntilElementDisplayed(seeDetailsLinkOnFreeShip, 3)) {

            click(seeDetailsLinkOnFreeShip);
            return waitUntilElementDisplayed(freeShippingDetails, 20) && driver.getCurrentUrl().contains("content/free-shipping");
        } else {
            addStepDescription("Free Shipping on every order! See details link is not displaying");
            return false;
        }
//        return headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

    }


    public boolean clickEditLinkAndVerifyAttributes() {
        WebElement lastEditElement = getLastElementFromList(editLnks);
        boolean isEditMode = clickEditLnk(getFirstElementFromList(editLnks));
        boolean favIcon = false, removeLinkIcon = false;
        try {
            favIcon = isDisplayed(wlIconByPosition(1));
        } catch (Exception e) {
            favIcon = false;
        }
        try {
            removeLinkIcon = isDisplayed(removeLinkByItemPosition(1));
        } catch (Exception e) {
            removeLinkIcon = false;
        }
        boolean isUpdateButton = false, isLastEditLink = false;
        try {
            isUpdateButton = isDisplayed(updateButton);
        } catch (Exception e) {
            addStepDescription("Update button is not displaying " + isUpdateButton);
            isUpdateButton = false;
        }
        try {
            isLastEditLink = isDisplayed(lastEditElement);
        } catch (Exception e) {
            isLastEditLink = false;
        }
        if (!isEditMode && !isUpdateButton && isLastEditLink) {
            addStepDescription("Clicking on Edit link opens Cancel link is " + isEditMode + " and showing update button " + isUpdateButton + " other products edit link is showing " + isLastEditLink);
        }
        return isEditMode && isUpdateButton && !favIcon && !removeLinkIcon &&
                !isLastEditLink;
    }

    public boolean isQtyDropDownAllValuesAvailable() {
        boolean isTrue = false;
        String[] expOptions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        click(qtyDropdown);
        waitUntilElementsAreDisplayed(quantityDropdown, 10);
        List<WebElement> qtyOptions = getDropDownOption(qtyDropdown);
        int i = 0;
        for (WebElement qtyOption : qtyOptions) {
            while (i < expOptions.length) {
                if (getText(qtyOption).equals(expOptions[i])) {
                    i++;
                    isTrue = true;
                    break;

                } else {
                    return false;
                }

            }
        }
        return isTrue;
    }

    public boolean discountItemDisplay() {
        waitUntilElementsAreDisplayed(strikeOutPrice, 3);
        waitUntilElementDisplayed(ledger_PromotionText, 3);
        String originalCost = getText(itemsTotalPrice).replaceAll("[^0-9.]", "");
        String couponCost = getText(label_Coupons).replaceAll("[^0-9.]", "");
        String estTotal = getText(estimatedTotal).replaceAll("[^0-9.]", "");
        String promotion = getText(ledger_PromotionText).replaceAll("[^0-9.]", "");

        float originalAmount = Float.parseFloat(originalCost);
        float couponAmount = Float.parseFloat(couponCost);
        float estimatedTot = Float.parseFloat(estTotal);
        float promotionAmount = Float.parseFloat(promotion);
        float estiTot = originalAmount - couponAmount - promotionAmount;

        if (estimatedTot == estiTot) {
            return true;
        }
        return waitUntilElementDisplayed(estimatedTotal, 20);
    }

    public boolean giftCardPriceCheck() {
        boolean giftCardAmount = false;
        if (!giftCardAmount) {
            waitUntilElementDisplayed(giftCardTitle, 3);
            waitUntilElementDisplayed(giftCardPrice, 3);
            verifyElementNotDisplayed(giftOfferPrice, 4);

            return true;
        } else
            return false;
    }

    public boolean checkCartCount() {
        staticWait();
        String storeCACount = getBagCount();
        int count = Integer.parseInt(storeCACount);
        if (count == 0) {
            return true;
        } else
            return false;
    }


    public boolean isTotalBalanceDisplayAfterApplyingGC() {
        double gcPrice = getGiftCardTotalApplied();
        double estimatedPrice = 0.00;
        try {
            estimatedPrice = Double.valueOf(getText(estimatedTotalPrice).replaceAll("[^0-9.]", ""));
        } catch (NullPointerException e) {
            addStepDescription("Estimated total price is not showing at order ledger");
            estimatedPrice = 0.00;
        }

        double balanceExp = (double) Math.round((estimatedPrice - gcPrice) * 100) / 100;
        double balanceActual = Double.valueOf(getEstimateTotal());

        return balanceActual == balanceExp;


    }

    public boolean verifyLTYEarnPointsMatchesWithOrderTotal() {
        int estimatedRoundUp = (int) Math.round((Double.valueOf(getEstimateTotal()) * 100)) / 100;
        int earnPoints = Integer.parseInt(getText(ltyEarnPoints));
        return ((estimatedRoundUp == earnPoints) || (estimatedRoundUp * 2 == earnPoints));
    }

    public boolean verifyLTYPtsMatchesWithOrderTotal_PLCC() {
        int estimatedRoundUp = (int) Math.round((Double.valueOf(getEstimateTotal()) * 100)) / 100;
        int earnPoints = Integer.parseInt(getText(ltyEarnPoints));
        return ((estimatedRoundUp * 2 == earnPoints) || (estimatedRoundUp * 3 == earnPoints));
    }

    public boolean isAvailableRewardsAndOffersDisplay() {
        return waitUntilElementsAreDisplayed(rewardsSavingsImgs, 1) &&
                waitUntilElementsAreDisplayed(rewardsCouponNames, 1) &&
                waitUntilElementsAreDisplayed(rewardsExpireDates, 1) &&
                waitUntilElementsAreDisplayed(rewardsDetailsLinks, 1) &&
                waitUntilElementsAreDisplayed(rewardsApplyToOrder, 1);
    }

    public boolean clickAirMilesBannerAndValidateContent() {
        click(airMilesBanner);
        return waitUntilElementDisplayed(airMilesContent, 30);
    }

    public boolean isCouponAppliedAtOrderLedger() {
        return waitUntilElementDisplayed(couponCodeApplied, 10);
    }

    public boolean clickNeedHelpLinkAndVerifyCouponTable() {
        click(needHelpLink);
        boolean couponTableDisplay = waitUntilElementDisplayed(needHelpCouponTable, 20);
        click(closeModal);
        staticWait();
        return couponTableDisplay;
    }

    public boolean editAirMilesFieldAndEnterNumber(String airMilesNumber) {
        clearAndFillText(airMilesField, airMilesNumber);
        return waitUntilElementDisplayed(airMilesEdit, 10);

    }

    public String getSelectedProdSize() {
        return getText(prodSize).replaceAll("Size", "").replaceAll(":", "").trim();
    }

    public boolean isPaypalButtonAlignedProperly() {
        return getTextAlignedValue(paypalBtnString).toString().equalsIgnoreCase("center");
    }

    public boolean airmilesLandingPage(HeaderMenuActions headerMenuActions) {
        waitUntilElementDisplayed(headerMenuActions.globalEspot, 3);
        click(headerMenuActions.globalEspot);
        waitUntilElementDisplayed(headerMenuActions.joinNowCTA, 5);
        return (getCurrentURL().contains("air-miles"));
    }

    public int inlinecheckitem() {
        click(shoppingBagIcon);
        int inlineCheck = overlayitemDescElements.size();
        return inlineCheck;
    }

    public int getViewBagCount() {
        int viewbagIcon = Integer.parseInt(getText(viewShoppingBagTitle).replaceAll("[^0-9]", ""));
        click(closeviewcart);
        return viewbagIcon;
    }

    public boolean verifyBagCountInlineItem(String bagCount, String Producttitle, int viewBagcount, int inlineCheckitemcount) {
        click(shoppingBagIcon);
        String ProductTitle = Producttitle.toLowerCase();
        int productupdateCount = Integer.parseInt(getText(productUpdateCheck(ProductTitle)).replaceAll("[^0-9]", ""));
        int viewbagCount = Integer.parseInt(bagCount);
        int currentViewbagCount = viewBagcount + 1;
        int viewbagIconCount = Integer.parseInt(getText(viewShoppingBagTitle).replaceAll("[^0-9]", ""));
        // int prevbagcount = bagCount;
        int currentbagcount = Integer.parseInt(getText(shoppingBagIcon));
        int inlineCheck = overlayitemDescElements.size();

        if ((productupdateCount > 1) &&
                (currentbagcount > viewbagCount) &&
                (inlineCheckitemcount == inlineCheck) &&
                (viewbagIconCount == currentViewbagCount))
            return true;
        else
            return false;
    }

    public boolean clickOnCheckoutAndVerifyRememberedUserLogin(LoginPageActions loginPageActions) {
        click(checkoutBtn);
        return waitUntilElementDisplayed(loginPageActions.rememberedLogout, 20);
    }

    public boolean bagCountAfterAddingGiftWrap(String count) {
        waitUntilElementDisplayed(shoppingBagIcon);
        String afterCount = getBagCount();
        if (afterCount.equalsIgnoreCase(count)) {
            return true;
        } else {
            addStepDescription("Gift wrapping is also added to bag count");
            return false;
        }
    }

    public boolean clickCheckoutAsGuestCombinedOrder(LoginPageActions loginPageActions, PickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(checkoutBtn, 10);
        click(checkoutBtn);
        waitUntilElementDisplayed(loginPageActions.continueAsGuestButton, 20);
        click(continueAsGuest);
        return waitUntilElementDisplayed(pickUpPageActions.nxtShippingBtn, 20);
    }

    public boolean bopisCouponValidation(float previousTotal, float coupon) {
        waitUntilElementDisplayed(orderLedgerSection);
        float tax = 0;
        if (isDisplayed(tax_TotalTxt)) {
            String taxtAmt = getText(tax_TotalTxt).replaceAll("[^0-9.]", "");
            tax = Float.parseFloat(taxtAmt);
        }

        if (isDisplayed(promotionApplied)) { // iF promotion is displayed in the Order Ledger
            String orderPromotion = getText(promotionApplied).replaceAll("[^0-9.]", "");
            float appliedPromo = Float.parseFloat(orderPromotion);
            String orderSubtotal = getText(itemsTotalPrice).replaceAll("[^0-9.]", "");
            float orderSubTot = Float.parseFloat(orderSubtotal);
            if (isDisplayed(giftcardAmount)) { // If Gift card is added with Promotion
                String giftCardAmt = getText(giftcardAmount).replaceAll("[^0-9.]", "");
                float giftCard = Float.parseFloat(giftCardAmt);
                float total = orderSubTot - appliedPromo - giftCard - coupon;
                float discount = (total * 5) / 100;
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String numberAsString = decimalFormat.format(discount);
                float value = Float.parseFloat(numberAsString);
                float orderTotal = Float.parseFloat(getEstimateTotal());
                float actualValue = orderSubTot - (value + coupon + appliedPromo) + (tax);
                if (orderTotal == actualValue) {
                    return true;
                }
            } else {// IF only Promotion is displayec (No Gift card Added)
                float total = orderSubTot - appliedPromo;
                float discount = (total * 5) / 100;
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String numberAsString = decimalFormat.format(discount);
                float value = Float.parseFloat(numberAsString);
                float orderTotal = Float.parseFloat(getEstimateTotal());
                float actualValue = orderSubTot - (value + coupon + appliedPromo) + tax;
                if (orderTotal == actualValue) {
                    return true;
                }
            }
        }
        String orderSubtotal = getText(itemsTotalPrice).replaceAll("[^0-9.]", "");
        float orderSubTot = Float.parseFloat(orderSubtotal);
        if (isDisplayed(giftcardAmount)) { // Only Giftcard is present without Promotion
            String giftCardAmt = getText(giftcardAmount).replaceAll("[^0-9.]", "");
            float giftCard = Float.parseFloat(giftCardAmt);
            float total = orderSubTot - giftCard - coupon;
            float discount = (total * 5) / 100;
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String numberAsString = decimalFormat.format(discount);
            float value = Float.parseFloat(numberAsString);
            float orderTotal = Float.parseFloat(getEstimateTotal());
            float actualValue = Math.round(orderSubTot - value + tax - coupon);
            if (orderTotal == actualValue) {
                return true;
            }
        } else { //No Promotions and Giftcard is present
            float orderTotal1 = Float.parseFloat(getEstimateTotal());
            float previousTotal1 = orderSubTot - coupon;
            float discount1 = (previousTotal1 * 5) / 100;
            DecimalFormat decimalFormat1 = new DecimalFormat("#.00");
            String numberAsString1 = decimalFormat1.format(discount1);
            float value1 = Float.parseFloat(numberAsString1);
            float actualValue1 = Math.round(previousTotal - value1 + tax - coupon);
            if (orderTotal1 == actualValue1) {
                return true;
            }
        }
        addStepDescription("5% discount is not applied to the order total");
        return false;

    }

    /**
     * created BY Pooja on 12th May,2018
     * This Method clicks on Product as per given title In the Shopping Bag
     */
    public boolean clickProdPresentByTitleOnSB(ProductDetailsPageActions productDetailsPageActions, String title) {
        click(productByTitle(title));
        return waitUntilElementsAreDisplayed(productDetailsPageActions.breadcrumb_values, 5);
    }

    public boolean editAnGiftCardFromSBPage(int i) {
        waitUntilElementsAreDisplayed(editLnks, 3);
        click(editLnks.get(i));
        waitUntilElementDisplayed(designDropDown, 2);
        click(designDropDown);
        waitUntilElementsAreDisplayed(listOfGC, 2);
        int availableList = listOfGC.size();
        if (availableList > 1) {
            int randomSelect = randInt(0, (availableList - 1));
            click(listOfGC.get(randomSelect));
            waitUntilElementDisplayed(updateButton, 2);
            click(updateButton);
            return waitUntilElementDisplayed(notification_ItemUpdated, 2);
        } else if(availableList==1){
            selectDropDownByValue(qtyDropdown, "1");
            click(updateButton);
            return waitUntilElementDisplayed(notification_ItemUpdated, 2);
        }else {
            addStepDescription("Available GC list is less than or equal to one");
            return false;
        }
    }

    /**
     * Get all the badges from products in mini-bag
     *
     * @return list of badges
     */
    public List<String> getBadgesForProducts() {
        List<String> badges = new ArrayList<>();
        if (inlineBadgeDisplay.size() > 0) {
            for (WebElement el : inlineBadgeDisplay) {
                badges.add(el.getText());
            }
        } else {
            addStepDescription("No badges are available for products in Mini-bag");
        }
        return badges;
    }

    public boolean checkTheItemDisplayed(String name){
        waitUntilElementDisplayed(prodNames.get(0));
        String displayedItem = getText(prodNames.get(0));
        if(displayedItem.equalsIgnoreCase(name)){
            addStepDescription("Same item is displayed after login or chekc the search terms used");
            return false;
        }
        else{
            return true;
        }
    }
    public String getProdFit() {
        waitUntilElementDisplayed(productFitSeleccted, 5);
        return getText(productFitSeleccted).replaceAll("Fit", "").replaceAll(":", "").trim();
    }
}