package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MShoppingBagPageRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JKotha on 25/10/2017.
 */
public class MShoppingBagPageActions extends MShoppingBagPageRepo {
    WebDriver mobileDriver;
    Logger logger = Logger.getLogger(MShoppingBagPageActions.class);

    public MShoppingBagPageActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    /**
     * Get count of items from bag header
     *
     * @return count
     */
    public int getCountFromTitle() {
        String text = getText(heading);
        Character count = text.charAt(8);
        return (int) count;
    }

    /**
     * Get UPC no of a product
     *
     * @param productName to check
     * @return UPC no
     */
    public String getUPCNo(String productName) {
        String upc = getText(upcNo(productName));
        return upc.split(":")[1];
    }

    /**
     * Get properties of a product
     *
     * @param upc       name to check
     * @param attribute name to get
     * @return attribute value
     */
    public String getProductAttributes(String upc, String attribute) {
        WebElement ele = null;
        switch (attribute) {
            case "Color":
                ele = productProperties(upc).findElement(By.cssSelector(".text-color"));
                break;
            case "Size":
                ele = productProperties(upc).findElement(By.cssSelector(".text-size"));
                break;
            case "Quantity":
                ele = productProperties(upc).findElement(By.cssSelector(".text-qty"));
                break;
        }
        String att = getText(ele);
        return att.split(":")[1].trim();
    }

    public boolean clickRemoveLinkByPosition(int position, MobileHeaderMenuActions headerMenuActions) {
        String itemBeforeRemove = headerMenuActions.getQty();
        int countBeforeRemove = Integer.parseInt(itemBeforeRemove);
        staticWait(2000);

        staticWait(5000);
        WebElement removeElement = removeLinkByItemPosition(position);
        click(removeElement);
        staticWait(5000);

        String itemAfterProdRemove = headerMenuActions.getQty();
        int countAfterRemove = Integer.parseInt(itemAfterProdRemove);
        staticWait(2000);

        boolean isProductAddedToWL = (countBeforeRemove - 1) == countAfterRemove;

        return isProductAddedToWL;
    }

    /**
     * Remove all products from shopping bag
     *
     * @return
     */
    public boolean removeAllProducts() {
        for (WebElement product : products) {
            click(closeIcon);
            waitUntilElementDisplayed(updateMsg, 10);
        }
        return !waitUntilElementsAreDisplayed(products, 10);
    }

    public double getItemPrice() {
        String shoppingBag_ItemPrice = itemPriceSummary.getText();
        return Double.parseDouble(shoppingBag_ItemPrice.substring(shoppingBag_ItemPrice.indexOf("$")).replace("$", ""));
    }

    public double getTotalPrice() {
        waitUntilElementDisplayed(totalPriceFirst, 20);
        String shoppingBag_TotalPrice = getText(totalPriceFirst);
        return Double.parseDouble(shoppingBag_TotalPrice.substring(shoppingBag_TotalPrice.indexOf("$")).replace("$", ""));
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

    public boolean updateFirstOccur_BopisProdQty(MobileBopisOverlayActions bopisOverlayActions, String qtyToUpdate, String zip) {
        click(getFirstElementFromList(editLinksForBopisProd));
        waitUntilElementDisplayed(bopisOverlayActions.qtyDrpDown, 20);
        boolean isStoreAvail = bopisOverlayActions.updateQty(qtyToUpdate, zip);
        if (isStoreAvail) {
            return getFirstOccur_BopisProdQty().equalsIgnoreCase(qtyToUpdate);
        } else {
            addStepDescription("Problem updating quantity");
            return false;
        }
    }

    public boolean removeFirstOccur_BopisProd() {
        String prodTitleToRemove = getAttributeValue(getFirstElementFromList(removeButtonForBopisProd), "title");
        int bopisProdSize = removeButtonForBopisProd.size();
        click(getFirstElementFromList(removeButtonForBopisProd));
        staticWait(5000);
        try {
            int updatedSizeCount = removeButtonForBopisProd.size();
           // return !waitUntilElementDisplayed(getFirstElementFromList(removeButtonForBopisProd), 10);
            return bopisProdSize != updatedSizeCount;
        } catch (Throwable t) {
            return true;
        }

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

    public boolean validateEmptyShoppingCart() {
        if (isDisplayed(emptyBagMessage)) {
            return true;
        } else {
            List<WebElement> removeLinks = getDriver().findElements(By.xpath("//div[contains(@class,'container-button-remove')]//button"));
            int n = removeLinks.size();
            for (int i = 0; i < n; i++) {
                click(removeLinkByItemPosition(1));
                staticWait(3000);
            }
            waitUntilElementDisplayed(emptyBagMessage, 10);
            return true;
        }
    }

    public boolean enterInvalidCouponCodeAndApply(String couponCode) {
        clearAndFillText(couponCodeFld, couponCode);
        javaScriptClick(mobileDriver, applyCouponButton);
        return waitUntilElementDisplayed(invalidErrMsg);
    }

    public List<String> getProductNames() {
        List<String> prodNames = new ArrayList<>();
        for (WebElement prodTitleElement : prodTitles) {
            String prodName = getText(prodTitleElement);
            prodNames.add(prodName);
        }
        return prodNames;
    }

    public boolean validateOrderLedgerSection(MobileHeaderMenuActions headerMenuActions) {
        waitUntilElementDisplayed(orderSummarySection, 2);
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

    public boolean clickOnCheckoutBtnAsReg(MShippingPageActions shippingPageActions, MReviewPageActions reviewPageActions) {
        staticWait(1000);
        javaScriptClick(mobileDriver,checkoutBtn);
        staticWait(5000);
        //scrollDownUntilElementDisplayed(shippingPageActions.nextBillingButton);
        return waitUntilElementDisplayed(shippingPageActions.nextBillingButton, 40) || reviewPageActions.isReviewPageDisplayed();
    }

    public boolean clickWLIconAsGuestUser(MLoginPageActions loginPageActions) {
        waitUntilElementDisplayed(wlIconByPosition(1), 10);
        javaScriptClick(mobileDriver, wlIconByPosition(1));
        return waitUntilElementDisplayed(loginPageActions.loginButton, 20);
    }

    public boolean clickWLGuestUser(MLoginPageActions loginPageActions) {
        waitUntilElementDisplayed(wlLink, 10);
        click(wlLink);
        return waitUntilElementDisplayed(loginPageActions.emailAddrField, 20);
    }

    /**
     * Click checkout and continue as guest user
     *
     * @return true if pickup/Shipping address page displayed
     */
    public boolean continueCheckoutAsGuest() {
        if (waitUntilElementDisplayed(checkoutBtn, 10)) {
            javaScriptClick(mobileDriver, checkoutBtn);
        }
        waitUntilElementDisplayed(continueAsGuest, 20);
        javaScriptClick(mobileDriver, continueAsGuest);
        MShippingPageActions action = new MShippingPageActions(mobileDriver);
        return waitUntilElementDisplayed(action.nextBillingButton, 20) || waitUntilElementDisplayed(action.nextShippingButton);
    }

    /**
     * Click checkout button as guest in sb
     *
     * @return
     */
    public boolean clickCheckoutAsGuest() {
        click(checkoutBtn);
        return waitUntilElementDisplayed(continueAsGuest, 20);
    }

    /*
       Author Shilpa P
    *  Click on Billing Button in Checkout Page
    *  @return true if Credit Card Box is displayed
    * */

    public boolean clickBillingButton(MShippingPageActions mshippingPageActions, MBillingPageActions mbillingPageActions) {
        scrollDownUntilElementDisplayed(mshippingPageActions.nextBillingButton);
        click(mshippingPageActions.nextBillingButton);
        return waitUntilElementDisplayed(mbillingPageActions.creditCardRadioBox, 20);
    }

    public boolean moveProdToWLByPositionAsReg(int position) {
        int prodBagBeforeMovingToWL = itemsInCart.size();
        WebElement moveProdToWL = moveToWLByPosition(position);
        javaScriptClick(mobileDriver, moveProdToWL);
        waitUntilElementDisplayed(notification_ItemUpdated);
        int prodBagAfterMovingToWL = itemsInCart.size();
        return prodBagBeforeMovingToWL > prodBagAfterMovingToWL;
    }

    public boolean moveProdToWLByPositionErr(int position) {
        WebElement moveProdToWL = moveToWLByPosition(position);
        javaScriptClick(mobileDriver, moveProdToWL);
        return waitUntilElementDisplayed(invalidErrMsg);
    }


    public boolean moveProdToWLByPositionAsGuest(MobileFavouritesActions mFavActions, int position) {
        WebElement moveProdToWL = moveToWLByPosition(position);
        javaScriptClick(mobileDriver, moveProdToWL);
        staticWait(2000);
        return waitUntilElementDisplayed(mFavActions.emailAddrField);
    }


    public boolean isProdPresentByTitle(String title) {
        for (WebElement prodTitle : prodTitles) {
            if (getText(prodTitle).contains(title)) {
                return true;
            }
        }
        return false;
    }

    public boolean clickProceedToCheckoutExpress(MReviewPageActions reviewPageActions) {
        scrollUpToElement(checkoutBtn);
        waitUntilElementDisplayed(checkoutBtn, 20);
        javaScriptClick(mobileDriver, checkoutBtn);
        staticWait(3000);
        return waitUntilElementDisplayed(reviewPageActions.submitOrderBtn, 30);
    }

    public String getPickUpInStoreName() {
        return getText(pickUpInStoreName).replaceAll("at ", "");
    }


    public boolean clickChangeStoreLink(MobileBopisOverlayActions mbopisOverlayActions) {
        waitUntilElementDisplayed(changeStoreLnk, 20);
        scrollDownToElement(changeStoreLnk);
        javaScriptClick(mobileDriver,changeStoreLnk);
        return waitUntilElementDisplayed(mbopisOverlayActions.zipCodeField, 20);

    }

    /**
     * Createdby Richak
     * This method is created to click on change store link based on the index in bag
     *
     * @param mbopisOverlayActions
     * @param index
     * @return
     */
    public boolean clickChangeStoreLink(MobileBopisOverlayActions mbopisOverlayActions, int index) {
        WebElement changeStoreLink = changeAStoreLinks.get(index);
        waitUntilElementDisplayed(changeStoreLink, 20);
        javaScriptClick(mobileDriver, changeStoreLink);
        return waitUntilElementDisplayed(mbopisOverlayActions.zipCodeField, 20);
    }


    /**
     * Update quantity of product from shopping bag
     *
     * @param product        name
     * @param updatequantity quantity count
     * @return
     */
    public boolean changeQuantity(String product, String updatequantity) {
        staticWait();
        javaScriptClick(mobileDriver,editProduct(product));
        waitUntilElementDisplayed(qtyDropdown);
        selectDropDownByVisibleText(qtyDropdown, updatequantity);
        waitUntilElementDisplayed(updateButton, 2);
        javaScriptClick(mobileDriver, updateButton);
        waitUntilElementDisplayed(prodLoadingSymbol, 10);
        waitUntilElementDisplayed(notification_ItemUpdated, 20);
        verifyElementNotDisplayed(prodLoadingSymbol, 20);
        staticWait(10000);
        return waitUntilElementDisplayed(editBtnLnk, 5);
    }

    /**
     * Update size for product if more than one color available
     *
     * @param upc to update size
     * @return
     */
    public boolean changeProdSize(String upc) {
        String sizeBeforeEdit = getText(prodSize);
        String toBeSelected = sizeBeforeEdit;
        clickEditLink(upc);
        while (toBeSelected == sizeBeforeEdit) {
            int sizeCount = listOfSize.size();
            if (sizeCount > 1) {
                WebElement sizeSelected = listOfSize.get(randInt(0, (listOfSize.size() - 1)));
                toBeSelected = getText(sizeSelected);
                click(sizeSelected);
            } else if (sizeCount == 1) {
                click(selectedSize);
                toBeSelected = getText(selectedSize);
            }
        }
        click(updateButton);
        return true;
    }

    /**
     * Update color for product if more than one color available
     *
     * @param upc to update color
     * @return
     */
    public boolean changeProdColor(String upc) {
        clickEditLink(upc);
        javaScriptClick(mobileDriver,colorcombobox);

        int colorsCount = dropdownValues.size();

        if (colorsCount > 1) {
            click(unselectedcolors.get(randInt(0, (unselectedcolors.size() - 1))));
        } else {
            //do nothing
        }
        click(updateButton);
        return waitUntilElementDisplayed(updateMsg);
    }

    public String getEstimateTotalAtTop() {
        String orderTotal = getText(estimatedTotalAtTop).replace("$", "").split(":")[1].trim();
        return orderTotal;
    }

    /**
     * Author: JK
     * Click on continue shopping link
     *
     * @return true if home page displayed
     */
    public boolean clickContinueShoppingLink() {
        click(continueShoppingLink);
        return getCurrentURL().contains("/home");
    }

    public boolean isPickUpInStoreRadioChecked() {
        mobileDriver.navigate().refresh();
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
            javaScriptClick(mobileDriver, STHCheckBox);
        }
        return verifyElementNotDisplayed(changeStoreLnk, 20);
    }


    public boolean estimatedTotalAfterAppliedCoupon() {

        staticWait(3000);
        String itemCost = getText(itemPrice).replaceAll("[^0-9.]", "");
        String couponDiscount = getText(orderPromo).replaceAll("[^0-9.]", "");
        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        float itemPrice = Float.parseFloat(itemCost);
        float couponAmount = Float.parseFloat(couponDiscount);
        float estimatedTot = Float.parseFloat(estimatedCost);


        float total = itemPrice - couponAmount;
        if (total == estimatedTot) {
            return true;
        }
        return waitUntilElementDisplayed(estimatedTotal, 20);
    }

    public boolean applyCouponCode(String couponCode) {
        scrollToBottom();
        waitUntilElementDisplayed(couponCodeFld);
        clearAndFillText(couponCodeFld, couponCode);
        javaScriptClick(mobileDriver, applyCouponButton);
        waitUntilElementDisplayed(couponCodeApplied, 10);
        return waitUntilElementDisplayed(appliedCouponText, 20);
    }

    /**
     * Created by RichaK
     *
     * @return
     */
    public boolean removeCouponFromShoppingBag() {
        scrollDownToElement(removeCoupon);
        javaScriptClick(mobileDriver, removeCoupon);
        staticWait(15000);
        return !waitUntilElementDisplayed(removeCoupon, 5);
    }
    
    public boolean removeAllCoupons()
    {
    	for (WebElement webElement : removeCouponList) {
    		javaScriptClick(mobileDriver, webElement);
    		staticWait(15000);
		}
    	
    	return !waitUntilElementDisplayed(removeCoupon, 5);
    }

    public boolean clickProceedToBOPISCheckout() {
        MobileCheckoutPickUpDetailsActions checkoutPickUpDetailsActions = new MobileCheckoutPickUpDetailsActions(mobileDriver);
        waitUntilElementDisplayed(checkoutBtn);
        javaScriptClick(mobileDriver,checkoutBtn);
        return waitUntilElementDisplayed(checkoutPickUpDetailsActions.nxtButton);
    }


//    // validate the applied coupon should be equal to subtract the value from original price and offer price.(For one prod)
//    public boolean validateTotalAfterAppliedCoupon() {
//        String originalCost = getText(itemPrice).replaceAll("[^0-9.]", "");
//        String couponCost = getText(couponCodeApplied).replaceAll("[^0-9.]", "");
//        String estTotal = getText(estimatedTotal).replaceAll("[^0-9.]", "");
//
//        float originalAmount = Float.parseFloat(originalCost);
//        float couponAmount = Float.parseFloat(couponCost);
//        float estimatedTot = Float.parseFloat(estTotal);
//        float estiTot = originalAmount - couponAmount;
//
//        if (estimatedTot == estiTot) {
//            return true;
//        }
//        return waitUntilElementDisplayed(estimatedTotal, 20);
//    }

    public boolean validateTitleAndBagCount() {
        String title = getText(titleText);
        String bagIcon = getText(shoppingBagIcon).replaceAll("[^0-9]", "");

        String[] itemCountFromTitle = title.split(" ");
        String finalBagCountFromTitle = itemCountFromTitle[2].replaceAll("[^0-9]", "");

        int titleCount = Integer.parseInt(finalBagCountFromTitle);
        addStepDescription("Bag count from My bag::" +titleCount);

        int bagCount = Integer.parseInt(bagIcon);

        if (titleCount == bagCount)
            return true;
        else
            return false;
//        return waitUntilElementDisplayed(titleText, 5);
    }

    public String getBagCount() {
        String bagIcon = getText(shoppingBagIcon).replaceAll("[^0-9]", "");
        return bagIcon;
    }


    public boolean validateProdDetails() {
        if (isDisplayed(getFirstElementFromList(prodNames)) &&
                isDisplayed(editBtnLnk) &&
                isDisplayed(upcNumber) &&
                isDisplayed(prodColor) &&
                isDisplayed(prodSize) &&
                isDisplayed(prodQty) &&
                isDisplayed(orderSummarySection) &&
                isDisplayed(plccEspot)) {
            return true;
        }
        return waitUntilElementDisplayed(getFirstElementFromList(prodNames), 20);
    }


    public boolean validateBagWithoutProd() {
        if (waitUntilElementDisplayed(emptyBagMessage, 10) &&
                verifyElementNotDisplayed(plccEspot, 5) &&
                verifyElementNotDisplayed(orderSummarySection, 5))
            return true;
        else
            return false;
    }


    public boolean clickOnCheckoutBtnAsBopisReg(MobilePickUpPageActions pickUpPageActions) {
        waitUntilElementDisplayed(checkoutBtn, 10);
        click(checkoutBtn);
        staticWait(2000);
        return waitUntilElementDisplayed(pickUpPageActions.nxtShippingBtn, 20) || waitUntilElementDisplayed(pickUpPageActions.nxtBillingBtn, 20);
    }

    public String getProdPrice() {

        String offerPrice = getText(estimatedTotal).replaceAll("[^0-9.]", "");
        return offerPrice;
    }

    public boolean isChangedBopisStore(String pickUpStoreNameBefore) {
        String pickUpStoreNameAfterChanging = getPickUpInStoreName();

        boolean validateStoreChange = pickUpStoreNameAfterChanging.contains(pickUpStoreNameBefore);

        if (!validateStoreChange) {
            logger.info("The Store has been changed successfully");
            return true;
        } else
            return false;
    }

    public String getUPCNumByPosition(int position) {
        return getText(getUPCNumWebElementByPosition(position)).replaceAll("[^0-9]", "");

    }

    /**
     * Created by Richa Priya
     * Click on PayPal button displayed on shopping bag
     *
     * @return true if Proceed to Paypal is displayed successfully
     */
    public boolean clickOnPayPalIcon(MPayPalPageActions mpayPalPageActions) {
        waitUntilElementDisplayed(payPalFrame, 20);
        switchToFrame(payPalFrame);
        waitUntilElementDisplayed(payPalBtn, 20);
        javaScriptClick(mobileDriver,payPalBtn);
        switchToDefaultFrame();
        return getCurrentWindowHandles() == 2;
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

    public boolean validateAirmilesText(String airmilesText) {
        if (isDisplayed(airmiles_Text)) {
            String text = getText(airmiles_Text);
            if (text.contains(airmilesText)) {
                return true;
            }
        } else {
            addStepDescription("The text is not getting displayed properly in SB page");
            return false;
        }
        return false;
    }
    /*public boolean emptyBagContent(MobileHeaderMenuActions headerMenuActions, MobileShoppingBagDrawerActions shoppingBagDrawerActions, MShoppingBagPageActions shoppingBagPageActions) {
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        return validateEmptyShoppingCart();
    }*/

    /**
     * Created by Richa Priya
     * Click on edit button of gift card added to shipping bag
     *
     * @return wait until update button is displayed after clicking on Edit button
     */
    public boolean clickOnEditButton() {
        isDisplayed(editBtnLnk);
        click(editBtnLnk);
        return waitUntilElementDisplayed(globalUpdatedBtn);
    }

    /**
     * Created by Richa Priya
     * Update the value and calling the update quantity method to update
     * attribute of the gift card added in the shopping bag
     *
     * @return wait until update button is changed to edit after successful updation
     */
    public boolean updateValueAndQtyOfGC() {
        if (waitUntilElementDisplayed(editBtnLnk, 10)) {
            click(editBtnLnk);
            waitUntilElementDisplayed(updateButton, 20);
        }
        String selectedValue = getSelectOptions(valueDetails);
        List<WebElement> valueOptions = getDropDownOption(valueDetails);
        for (WebElement value3 : valueOptions) {
            if (!value3.getText().equalsIgnoreCase(selectedValue)) {
                selectDropDownByValue(valueDetails, value3.getText());
                break;
            }
        }
        updateGCQuantity();
        click(globalUpdatedBtn);
        return /*waitUntilElementDisplayed(updateNotification, 20) &&*/ waitUntilElementDisplayed(editBtnLnk, 10);

    }

    /**
     * Created by Richa Priya
     * Update the quantity attribute of the gift card added in the shopping bag
     *
     * @return wait until update button is changed to edit after successful updation
     */
    public boolean updateGCQuantity() {
        waitUntilElementDisplayed(qtyDetails);
        selectDropDownByVisibleText(qtyDetails, String.valueOf(randInt(2, qtyList.size())));
        staticWait(2000);
        return waitUntilElementDisplayed(globalUpdatedBtn, 10);
    }

    /**
     * Created by Richa Priya
     * verify item price and total price updated successfully after updating the value and qty
     *
     * @return true if item price and total price are equal
     */
    public boolean validatePriceUpdatedForGiftCard() {
        String getValue = getText(selectedValueDetails).replaceAll("[^0-9.]", "");
        String getQty = getText(selectedQtyDetails).replaceAll("[^0-9.]", "");
        double updateValue = Double.parseDouble(getValue);
        double updateQty = Double.parseDouble(getQty);

        double calculatedUpdatedPrice = updateValue * updateQty;

        double itemPrice = getItemPrice();
        double estimatedTax = getEstimatedTax();

        double calculatedEstimatedPrice = itemPrice + estimatedTax;

        if (calculatedUpdatedPrice == getTotalPrice() && calculatedEstimatedPrice == Double.parseDouble(getEstimateTotalAtTop())) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Created by Richa Priya
     * click on airmiles promotion banner present on shopping page
     *
     * @return true airmiles page is displayed
     */
    public boolean validateRedirectionToAirmilesPage() {
        waitUntilElementDisplayed(airmilesLink, 10);
        click(airmilesLink);
        return isDisplayed(airmilesImage);
    }

    /**
     * Created by Richa Priya
     * <p>
     * Checkout as a guest user and switch to shipping detail iframe
     * <p>
     * Return true if successfully redirected to checkout page and switched to iframe
     */
    public boolean continueCheckoutAsGuestForIntUser(MobileIntCheckoutPageActions mobileIntCheckoutPageActions) {
        click(checkoutBtn);
        waitUntilElementDisplayed(continueAsGuest, 20);
        javaScriptClick(mobileDriver, continueAsGuest);
        switchToFrame(mobileIntCheckoutPageActions.internationalCheckoutIFrame);
        return waitUntilElementDisplayed(mobileIntCheckoutPageActions.deliveryText, 20);
    }

    /**
     * Created by Richa Priya
     * <p>
     * Get pick up store value for products listed on shopping bag
     * <p>
     * Return list of pick up store value displayed onShopping bag for product listed
     */
    public List<String> getPickUpStoreNameDesplayedForProduct() {
        List<String> storeNameReturned = new ArrayList<>();
        for (int i = 0; i < pickUpInStoreNameList.size(); i++) {
            logger.info("Pick up store name displayed on shopping bag" + pickUpInStoreNameList.get(i).getText().replaceAll("at ", ""));
            storeNameReturned.add(getText(pickUpInStoreNameList.get(i)).replaceAll("at ", ""));
        }
        return storeNameReturned;
    }

    /**
     * Created by Richa Priya
     * <p>
     * select pick up store option for ship it product on shopping bag
     * <p>
     * Return true if pick up store option is selected successfully
     */
    public boolean selectPickUpButtonForShipItProduct(MobileBopisOverlayActions mbopisOverlayActions) {
        int shipItCheckBoxesSize = shipItCheckBoxes.size();
        for (int i = 0; i < shipItCheckBoxesSize; i++) {
            if (getAttributeValue(shipItCheckBoxes.get(i), "class").equalsIgnoreCase("input-radio-icon-checked")) {
                staticWait(5);
                scrollDownToElement(bopisRadioUnChkedBoxes.get(i));
                select(bopisRadioUnChkedBoxes.get(i));
                break;
            }
        }
        return waitUntilElementDisplayed(mbopisOverlayActions.twoAvailableStoreTitle, 20) || waitUntilElementDisplayed(mbopisOverlayActions.headerContent, 20);
    }


    /**
     * Created by Richa Priya
     * <p>
     * Verify pick up store and change store link is present on shopping bag
     * <p>
     * Return true if both are displayed on shopping bag
     */
    public boolean verifyPickUpStoreIsDisplayed() {
        return isDisplayed(pickUpInStoreName)
                && isDisplayed(changeStoreLnk);

    }

    /**
     * Created by Richa Priya
     * <p>
     * compare position of elements which has top, left, bottom and right value
     *
     * @param val1 accepts hashmap of position for element 1
     * @param val2 accepts hashmap of position for element 2
     *             <p>
     *             Return true if condition is true
     **/
    public boolean comparePositionOfElement(Map<String, String> val1, Map<String, String> val2) {
        return
                //Float.parseFloat(val1.get("right")) < Float.parseFloat(val2.get("left")) &&
                Float.parseFloat(val1.get("left")) > Float.parseFloat(val2.get("right")) &&
              //  Float.parseFloat(val1.get("bottom")) < Float.parseFloat(val2.get("top"));
                 Float.parseFloat(val1.get("top")) < Float.parseFloat(val2.get("bottom"));
    }

    /**
     * Created by Richa Priya
     * <p>
     * Checkout as a registered user and switch to shipping detail iframe
     * <p>
     * Return true if successfully redirected to checkout page and switched to iframe
     */
    public boolean clickOnCheckoutBtnAsRegForIntUser(MobileIntCheckoutPageActions mobileIntCheckoutPageActions) {
        staticWait(1000);
        javaScriptClick(mobileDriver,checkoutBtn);
        staticWait(20000);
        switchToFrame(mobileIntCheckoutPageActions.internationalCheckoutIFrame);
        return waitUntilElementDisplayed(mobileIntCheckoutPageActions.txt_Email, 20);
    }

    /**
     * @Author: JKotha
     * Click on edit link
     */
    public boolean clickEditLink(String upc) {
        waitUntilElementDisplayed(editButton(upc), 10);
        javaScriptClick(mobileDriver,editButton(upc));
        return waitUntilElementDisplayed(updateButton, 20) && waitUntilElementDisplayed(cancelButton, 20);
    }

    /**
     * @return
     * @Autho: JKotha
     * click cancel button
     */
    public boolean clickCancelButton() {
        waitUntilElementDisplayed(cancelButton, 20);
        click(cancelButton);
        return verifyElementNotDisplayed(cancelButton, 10);
    }

    /**
     * Created by RichaK
     *
     * @return true if notification to inform user that bopis is not available for international store.
     */
    public boolean isBopisNotAvailableNotificationPresent() {
        return isDisplayed(bopisNotAvailableNotification);
    }

    /**
     * Created by Richa Priya
     *
     * @return true all the GC related attributes are present on shopping bag page
     */
    public boolean verifyGCProductDetails() {
        return waitUntilElementDisplayed(prodTitlesSingle, 10) &&
                waitUntilElementDisplayed(upcNumber, 10) &&
                waitUntilElementDisplayed(prodImage, 10) &&
                waitUntilElementDisplayed(prodColor, 10) &&
                waitUntilElementDisplayed(prodSize, 10) &&
                waitUntilElementDisplayed(prodQty, 10) &&
                waitUntilElementDisplayed(offerPrice, 10) &&
                waitUntilElementDisplayed(editBtnLnk, 10) &&
                waitUntilElementDisplayed(closeIcon, 10);

    }

    /**
     * Created by Pooja Sharma
     *
     * @return true if attributes in shopping Bag has expected Value
     */
    public boolean validateProductAttributes(String upc, String attribute, String expectedVal) {
        return (getProductAttributes(upc, attribute).equalsIgnoreCase(expectedVal));

    }

    /**
     * Created by Richa Priya
     *
     * @return list of all products present on shopping bag page
     */
    public List<Double> getTotalPriceList() {
        List<String> shoppingBag_TotalPrice = new ArrayList<>();
        List<Double> final_shoppingBag_TotalPrice = new ArrayList<>();

        waitUntilElementsAreDisplayed(totalPriceFirstList, 20);
        for (int i = 0; i < totalPriceFirstList.size(); i++) {
            shoppingBag_TotalPrice.add(getText(totalPriceFirstList.get(i)));
            final_shoppingBag_TotalPrice.add(Double.parseDouble(shoppingBag_TotalPrice.get(i).substring(shoppingBag_TotalPrice.get(i).indexOf("$")).replace("$", "")));

        }
        return final_shoppingBag_TotalPrice;
    }


    /**
     * Created by Richa Priya
     * 1. Validate line item price is equal to sum of indiviual product item price
     * 2. Validate sum of line item plus estimated tax is equal to estimated total
     *
     * @return true if 1. and 2. are equal
     */
    public boolean validateEstimatedTotal() {
        boolean flag = false;
        List<Double> itemCost = getTotalPriceList();
        String estimatedCost = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        double estimatedTot = Double.parseDouble(estimatedCost);

        double totalCalPrice_raw = itemCost.stream().mapToDouble(Double::doubleValue).sum();
        double totalCalAmt = (double) Math.round(totalCalPrice_raw * 100) / 100;

        double totalCalAmtWithTax = getItemPrice() + getEstimatedTax();
        double roundOff_totalAmount = (double) Math.round(totalCalAmtWithTax * 100) / 100;

        Double totalItemPrice = getItemPrice();

        if (totalCalAmt == totalItemPrice && roundOff_totalAmount == estimatedTot) {
            flag = true;
        }
        return flag;
    }

    /**
     * Created by Richa Priya
     * For first occurrence of bopis product move it to Favorite list
     *
     * @return true if successfully moved to favorite list
     */
    public boolean moveFirstOccur_BopisProdToWL() {
       int bopisProdSize = WLButtonForBopisProd.size();
        click(getFirstElementFromList(WLButtonForBopisProd));
        staticWait(5000);
        try {
            int updatedSizeCount = WLButtonForBopisProd.size();
          //  return waitUntilElementDisplayed(getFirstElementFromList(WLButtonForBopisProd), 10);
            return bopisProdSize != updatedSizeCount;
        } catch (Throwable t) {
            return true;
        }
    }

    /**
     * Author: JK
     * Updates giftcard type from SB
     *
     * @return
     */
    public boolean changeGCType() {
        waitUntilElementDisplayed(editBtnLnk, 3);
        click(editBtnLnk);
        waitUntilElementDisplayed(designDropDown, 20);
        click(designDropDown);
        waitUntilElementsAreDisplayed(listOfGC, 2);
        int availableList = listOfGC.size();
        if (availableList > 1) {
            int randomSelect = randInt(0, (availableList - 1));
            click(listOfGC.get(randomSelect));
            click(updateButton);
            staticWait(3000);
            return waitUntilElementDisplayed(editBtnLnk, 10);
        } else {
            addStepDescription("Available GC list is less than or equal to one");
            return false;
        }

    }

    /**
     * Created by Richa Priya
     * clicks on Go shopping link displayed on SB
     *
     * @return true if url is changed to home page url
     */
    public boolean clickGoShoppingLink() {
        waitUntilElementDisplayed(goShoppingLink);
        click(goShoppingLink);
        return waitUntilUrlChanged("home", 20);
    }

    /**
     * Calculate points based on item price
     *
     * @return points earned
     */
    public boolean calculatePoints() {
        waitUntilElementDisplayed(estimatedTotal, 10);
        String orderTotal = getText(estimatedTotal).replaceAll("[^0-9.]", "");

        Double s1 = Double.parseDouble(orderTotal);
        int orderRoundup = (int) Math.round(s1);
        waitUntilElementDisplayed(pointsEarned, 10);
        int pointEarned = Integer.parseInt(getText(pointsEarned));

        if (orderRoundup == pointEarned) {
            addStepDescription("Single point enabled");
            return true;
        }
        if (pointEarned == orderRoundup * 2) {
            addStepDescription("Double points enabled");
            return true;
        }
        return false;
    }

    /**
     * Click login link in MPR espot
     *
     * @param loginPageActions
     * @return
     */
    public boolean clickLoginLinkInMPReSpot(MLoginPageActions loginPageActions) {
        waitUntilElementDisplayed(mprLogin, 10);
        javaScriptClick(mobileDriver, mprLogin);
        return waitUntilElementDisplayed(loginPageActions.loginButton, 10);
    }

    /**
     * Get all the badges from products in mini-bag
     *
     * @return list of badges
     */
    public List<String> getProductBadges() {
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

    /**
     * Created by Richa Priya
     * <p>
     * click on checkout button
     */
    public boolean clickOnCheckoutBtn(MLoginPageActions mLoginPageActions, MShoppingBagPageActions mshoppingBagActions) {
        waitUntilElementDisplayed(checkoutBtn);
        javaScriptClick(mobileDriver,checkoutBtn);
        return waitUntilElementDisplayed(mLoginPageActions.loginButton) || waitUntilElementDisplayed(mshoppingBagActions.cartAlert);
    }

    public boolean verifyOriginalPriceElementAndColor(String oriPriceColor) {
        return isDisplayed(originalPrice) && convertRgbToTextColor(originalPrice.getCssValue("color")).equalsIgnoreCase(oriPriceColor);
    }

    public boolean verifyOfferPriceElementAndColor(String offerPriceColor) {
        return isDisplayed(offerPrice) && convertRgbToTextColor(offerPrice.getCssValue("color")).equalsIgnoreCase(offerPriceColor);
    }

    public boolean verifyOfferPriceInBold() {
        return isDisplayed(offerPrice) && offerPrice.getCssValue("font-weight").equals("900");
    }

    /**
     * Created by Richa Priya
     *
     * @return text displayed on loyalty espot
     */
    public String getLoyaltyEspotText(String userType) {
        String loyaltyPointsText = "";
        if (userType.equalsIgnoreCase("guest")) {
            loyaltyPointsText = getText(loyaltyPointTextGuest);

        }
        if (userType.equalsIgnoreCase("registered")) {
            loyaltyPointsText = getText(loyaltyPointReg);
        }
        return loyaltyPointsText;
    }

    /**
     * Created by Richa Priya
     *
     * @return text displayed on loyalty espot
     */
    public String applyInvalidCouponByText(String couponText) {
        clear(couponCodeFld);
        waitUntilElementDisplayed(showMoreLnk, 20);
        javaScriptClick(mobileDriver, showMoreLnk);
        waitForTextToAppear(showMoreLnk, "Show less", 30);
        WebElement applyCouponCode = applyToOrderButtonForText(couponText);
        javaScriptClick(mobileDriver, applyCouponCode);
        return getText(invalidErrMsg);
    }

    /**
     * Created by RichaK
     *
     * @param couponCode
     * @return
     */
    public String applyInvalidCouponCodeByEntering(String couponCode) {
        scrollToBottom();
        waitUntilElementDisplayed(couponCodeFld);
        clearAndFillText(couponCodeFld, couponCode);
        javaScriptClick(mobileDriver, applyCouponButton);
        return getText(invalidErrMsg);
    }

    /**
     * Created by RichaK
     * This method will apply coupon from the list of coupons based on the text passed.
     *
     * @return
     */
    public boolean applyCouponByText(String couponText) {
    	waitUntilElementDisplayed(couponCodeFld);
        clear(couponCodeFld);
        waitUntilElementDisplayed(showMoreLnk, 20);
        javaScriptClick(mobileDriver, showMoreLnk);
        waitForTextToAppear(showMoreLnk, "Show less", 30);
        WebElement applyCouponCode = applyToOrderButtonForText(couponText);
        javaScriptClick(mobileDriver, applyCouponCode);
        staticWait(2000);
        return waitUntilElementDisplayed(appliedCouponText, 20);
    }

    /**
     * Created by Richa Priya
     * select ship it option for pick up store product on shopping bag
     * Return true if ship it store option is selected successfully
     */
    public boolean selectShipItButtonForBopisProduct() {
        boolean isShipItRadioBtnSelected = false;
        int shipItCheckBoxesSize = shipItCheckBoxes.size();
        for (int i = 0; i < shipItCheckBoxesSize; i++) {
            if (getAttributeValue(shipItCheckBoxes.get(i), "class").equalsIgnoreCase("input-radio-icon-unchecked")) {
                staticWait(5);
                select(shipItRadioUnChkedBoxes.get(i));
                staticWait(5000);
                isShipItRadioBtnSelected = isSelected(shipItRadioUnChkedBoxes.get(i));
            }
        }
        return isShipItRadioBtnSelected;
    }

    /**
     * Created by Richa Priya
     * <p>
     * click on free shippng detail link
     * Return true if redirected to shipping page
     */
    public boolean clickFreeShippingDetailLink() {
        waitUntilElementDisplayed(freeShipLink, 10);
        javaScriptClick(mobileDriver,freeShipLink);
        return waitUntilUrlChanged("content/free-shipping", 20) && waitUntilElementDisplayed(freeShipContent, 10);
    }

   /* public boolean clickEditLinkAndCheckoutWithoutSave() {
        clickOnEditButton(getFirstElementFromList(editLnks));
        click(checkoutBtn);
        return waitUntilElementDisplayed(cartAlert, 10) && waitUntilElementDisplayed(backToBagButton, 10) && waitUntilElementDisplayed(contToCheckoutButton, 10);
    }*/

    /**
     * Created by Richa Priya
     * <p>
     * edit quantity without saving
     */
    public boolean updateQty_STHProd_WithOutSaving(String qtyToUpdate) {
        boolean isShipItChkBoxSelected = false;
        int shipItCheckBoxesSize = shipItCheckBoxes.size();
        for (int i = 0; i < shipItCheckBoxesSize; i++) {
            isShipItChkBoxSelected = getAttributeValue(shipItCheckBoxes.get(i), "class").equalsIgnoreCase("input-radio-icon-checked");
            if (isShipItChkBoxSelected) {
                javaScriptClick(mobileDriver, editFromSelectedShipItRadioByPos(i + 1));
                waitUntilElementDisplayed(qtyDropdown, 30);
                selectDropDownByValue(qtyDropdown, qtyToUpdate);
                staticWait(5000);
                break;
            }
        }
        return (getText(selectedQty).equalsIgnoreCase(qtyToUpdate));

    }

    /**
     * Created by Richa Priya
     * Return true if unsaved confirmation modal items are present
     */
    public boolean verifyUnsavedAlertModal() {
        waitUntilElementDisplayed(cartAlert);
        return getText(cartAlert).trim().equalsIgnoreCase("You have unsaved changes in your bag. Still want to continue?") &&
                getText(updateCancelBtn).trim().equalsIgnoreCase("Back to bag") &&
                getText(btnOverlayContinue).trim().equalsIgnoreCase("Continue to Checkout");

    }

    /**
     * Created by Richa Priya
     * Click Continue Checkout from unsaved confirmation modal
     */
    public boolean clickContinueToCheckoutBtn(MShippingPageActions mShippingPageActions) {
        waitUntilElementDisplayed(btnOverlayContinue);
        javaScriptClick(mobileDriver, btnOverlayContinue);
        return waitUntilElementDisplayed(continueAsGuest) || waitUntilElementDisplayed(mShippingPageActions.nextBillingButton);
    }

    /**
     * Created by Richa Priya
     * Click on automation 10 coupon
     */
    public boolean clickOnApplyBtnFromCouponList() {
        scrollDownUntilElementDisplayed(applyButtonFrmCouponList);
        javaScriptClick(mobileDriver, applyButtonFrmCouponList);
        return waitUntilElementDisplayed(removeCoupon, 15);
    }

    /**
     * Created by RichaK
     *
     * @return price of all products present on shopping bag page
     */
    public ArrayList<Double> getItemPriceList() {
        ArrayList<Double> itemPriceList = new ArrayList<>();
        waitUntilElementsAreDisplayed(totalPriceFirstList, 20);
        for (int i = 0; i < totalPriceFirstList.size(); i++) {
            String itemPriceStr = totalPriceFirstList.get(i).getText();
            Double itemPrice = Double.parseDouble(itemPriceStr.substring(itemPriceStr.indexOf("$")).replace("$", ""));
            itemPriceList.add(itemPrice);
        }

        return itemPriceList;
    }

    /**
     * Created by RichaK
     * Verify if the amount gets deducted from product price if amount off coupon is applied.
     *
     * @param initialPriceList
     * @param finalPriceList
     * @param amountOff
     * @return
     */
    public boolean verifyAmountOffCoupon(ArrayList<Double> initialPriceList, ArrayList<Double> finalPriceList, Double amountOff) {

        boolean flag = true;
        for (int i = 0; i < initialPriceList.size(); i++) {
            Double initialPrice = initialPriceList.get(i);
            Double finalPrice = finalPriceList.get(i);
            if (!(initialPrice - finalPrice == amountOff)) {
                flag = false;
                break;
            }
        }

    	return flag;
    }

    /**
     * Created by RichaK
     *
     * @return true if the overlay opens on clicking need help link on shopping bag page.
     */
    public boolean validateOverlayOnClickingNeedHelpLink() {
        scrollUpToElement(couponHelp);
        javaScriptClick(mobileDriver, couponHelp);
        return waitUntilElementDisplayed(couponHelpPage);
    }


    /**
     * Created by RichaK
     * Close the need help overlay.
     * @return
     */
    public boolean clickNeedHelpOverlay() {
        waitUntilElementDisplayed(closeNeedHelpOverlay);
        javaScriptClick(mobileDriver, closeNeedHelpOverlay);
        return waitUntilElementDisplayed(checkoutBtn);
    }

    public String getPickUpStoreByPosition(int position) {
        String storename = getText(storeName(position));
        return storename.split("at",2)[1];
       // return storename.split("at")[1];
    }

    public String getErrorMessage() {
        waitUntilElementDisplayed(invalidErrMsg);
        return getText(invalidErrMsg);
    }

    public String getQuantityLabelText() {
        waitUntilElementDisplayed(prodQty);
        return getText(prodQty).split(":")[1].trim();
    }

    public String getSize() {
        waitUntilElementDisplayed(prodSize);
        return getText(prodSize).split(":")[1].trim();
    }

    public boolean validateCreateAccountLink(MCreateAccountActions mCreateAccountActions) {
        staticWait(3000);
        scrollDownUntilElementDisplayed(createAccountEspotLink);
        staticWait(3000);
        javaScriptClick(mobileDriver,createAccountEspotLink);
        return waitUntilElementDisplayed(mCreateAccountActions.createAccountButton);
    }

    public String getCouponPrice() {
        return getText(couponCodeApplied).replaceAll("[^0-9.]", "");
    }

    /**
     * Created by Richa Priya
     * @return
     */
    public String getProductNameForShipItProduct(){
        String prodName = "";

        boolean isShipItChkBoxSelected = false;
        int shipItCheckBoxesSize = shipItCheckBoxes.size();
        for (int i = 0; i < shipItCheckBoxesSize; i++) {
            isShipItChkBoxSelected = getAttributeValue(shipItCheckBoxes.get(i), "class").equalsIgnoreCase("input-radio-icon-checked");
            if (isShipItChkBoxSelected) {
               prodName =  getText(shipItProductName.get(i));
               break;
            }
        }
        return prodName;
    }


    /**
     * Created by Richa Priya
     * @return
     */
    public List<String> getSizeAndFitForProduct(String prodName){
        List<String> sizeAndFit = new ArrayList<>();
        List<String> finalSizeAndFit = new ArrayList<>();
        boolean isTwoProdSame = false;
        int prodSize = pickInStoreChecked.size();
        for (int i = 0; i < prodSize; i++) {
            isTwoProdSame = getText(pickInStoreProdName.get(i)).equalsIgnoreCase(prodName);
            if(isTwoProdSame){
                sizeAndFit.add(getText(getFitForPickInStoreProdName.get(i)));
                sizeAndFit.add(getText(getSizeForPickInStoreProdName.get(i)));
                break;
            }

        }
        for(int i=0; i< sizeAndFit.size(); i++){
            finalSizeAndFit.add(sizeAndFit.get(i).split(":")[1]);
        }
        return finalSizeAndFit;
    }
    /**
     * Created by Shubhika
     * @return
     */
    public int getBagCountFromShoppingBag() {
        String title = getText(titleText);

        String[] itemCountFromTitle = title.split(" ");
        String finalBagCountFromTitle = itemCountFromTitle[2].replaceAll("[^0-9]", "");

        int titleCount = Integer.parseInt(finalBagCountFromTitle);
        addStepDescription("Bag count from My bag::" +titleCount);

       return titleCount;
    } 

}