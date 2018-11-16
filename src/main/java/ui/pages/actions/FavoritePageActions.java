package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import ui.pages.repo.FavoritePageRepo;

/**
 * Created by skonda on 8/5/2016.
 */
public class FavoritePageActions extends FavoritePageRepo {
    WebDriver driver;
    Logger logger = Logger.getLogger(FavoritePageActions.class);
    public static String itemNumVerify;
    public String copiedUrl;

    public FavoritePageActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isWishListPageDisplayed() {
        return waitUntilElementDisplayed(defaultWLName);
    }

    public boolean isItemAddedToWishList(String item) {
        waitUntilElementDisplayed(styleNumbers.get(0), 30);
        int itemsSize = styleNumbers.size();
        for (int i = 0; i < itemsSize; i++) {
            String styleNumber = getText(styleNumbers.get(i));
            int startIndex = styleNumber.indexOf(":");
            styleNumber = styleNumber.substring(startIndex + 2);
            if (getStyleNumber(styleNumber).equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    public String getStyleNumber(String styleNumber) {
        styleNumber = styleNumber.replace("Style #: ", "");
        return styleNumber;

    }

    public void deleteAllItems() {
        int size = 0;
        try {
            size = deleteItemElements.size();
        } catch (Throwable t) {
            size = 0;
        }
        for (int i = 0; i < size; i++) {
            click(deleteItemElements.get(0));
            waitUntilElementDisplayed(confirmYes);
            click(confirmYes);
            staticWait();
            try {
                verifyElementNotDisplayed(confirmYes);
            } catch (Throwable t) {

            }

        }

    }

    public boolean deleteFirstItem() {
        click(deleteItemElements.get(0));
        waitUntilElementDisplayed(confirmYes);
        click(confirmYes);
        staticWait();
        return verifyElementNotDisplayed(confirmYes);

    }

    public boolean deleteWishListByName(String wishListName) {
        int count = editWishListByName(wishListName).size();
        try {
            for (int i = 0; i < count; i++) {
                click(editWishListByName(wishListName).get(i));
                waitUntilElementDisplayed(deleteWishList);
                click(deleteWishList);
                waitUntilElementDisplayed(confirmYes);
                click(confirmYes);
                return verifyElementNotDisplayed(confirmYes);
            }
        } catch (Throwable t) {
            return true;
        }
        return waitUntilElementDisplayed(editDefaultWishList, 5);
    }

    public boolean clickWishListByNameAndVerifyIsItemAddedPDP(String wlName) {
        clickOnWishListByName(wlName);
        itemNumVerify = ProductDetailsPageActions.itemNum;
        return isItemAddedToWishList(itemNumVerify);
    }



    public boolean clickOnWishListByName(String wlName) {
        waitUntilElementDisplayed(wishListByName(wlName));
        click(wishListByName(wlName));
        return waitUntilElementDisplayed(styleNumbers.get(0));
    }

    public String getDefaultWishListName() {
        return getText(defaultWLNameElement);
    }

    public boolean editActiveWishList() {
        click(editActiveWishList);
        return waitUntilElementDisplayed(setAsDefaultLink);
    }

    public String getActiveWishListName() {
        return getText(activeWishListName);
    }


    public boolean clickSetAsDefaultAndVerify(String defWLName) {
        click(setAsDefaultLink);
        staticWait(4000);
        return getDefaultWishListName().equalsIgnoreCase(defWLName);
    }

    public boolean makeNonDefWL_Default() {
        String activeWLName = getActiveWishListName();
        editActiveWishList();
        return clickSetAsDefaultAndVerify(activeWLName);
    }

    public int getActiveWishListsCount() {
        return activeWishLists.size();
    }

    public boolean clickCreteNewWishList() {
        click(createNewWishListLink);
        return waitUntilElementDisplayed(newWishListTextBox);
    }

    public boolean createNewWishList(String wishListName) {
        clickCreteNewWishList();
        return enterWishListNameAndCreate(wishListName);
    }

    public boolean clickCreateNewWLAndVerifyErrorForMaxNumWLs() {
        click(createNewWishListLink);
        return waitUntilElementDisplayed(errorFor_MaxNumOfWLs);

    }

    public boolean enterWishListNameAndCreate(String wishListName) {
        clearAndFillText(newWishListTextBox, wishListName);
        click(createButton);
        staticWait(2000);
        int count = getActiveWishListsCount();
        for (int i = 0; i < count; i++) {
            if (getText(activeWishLists.get(i)).equalsIgnoreCase(wishListName)) {
                return true;
            }
        }
        return false;
    }

    public boolean editDefaultWishListAndUpdateWLName(String updateWLName) {
        clickOnEditDefaultWishList();
        return updateDefaultWishListName(updateWLName);
    }

    public boolean clickOnEditDefaultWishList() {
        click(editDefaultWishList);
        return waitUntilElementDisplayed(defaultWishListTextBox);
    }

    public boolean updateDefaultWishListName(String updateWLName) {
        clearAndFillText(defaultWishListTextBox, updateWLName);
        waitUntilElementDisplayed(updateButtonOnDefWL);
        click(updateButtonOnDefWL);
        staticWait(4000);
        for (int i = 0; i < activeWishLists.size(); i++) {
            if (getText(activeWishLists.get(i)).equalsIgnoreCase(updateWLName)) {
                return true;
            }
        }
        return false;
    }

     public boolean validateOnlyDefaultWLPresent(String wlName) {
        if (verifyElementNotDisplayed(driver.findElement(By.xpath("//a[text()='" + wlName + "']")))) {
            waitUntilElementDisplayed(defaultWLNameElement, 5);
            return true;
        } else if (!(verifyElementNotDisplayed(driver.findElement(By.xpath("//a[text()='" + wlName + "']")))))
            deleteWishListByName(wlName);
        staticWait(5000);
        waitUntilElementDisplayed(defaultWLNameElement, 5);
        return true;

    }

    public boolean clickAddToBagByPos(ProductCardViewActions productCardViewActions, int i) {
        mouseHover(addToBagByPos(i));
        click(addToBagIconByPos(i));
        boolean isAddToBagButtonVisible = waitUntilElementDisplayed(addToBagBtn.get(1), 30);
        boolean sizesAvailAndProdAddedToBag = false;
        if (isAddToBagButtonVisible) {
            sizesAvailAndProdAddedToBag = productCardViewActions.selectAFitAndSizeAndAddToBag();
        }
        return sizesAvailAndProdAddedToBag;
    }

    public boolean clickAddToBagByPosition(int i) {
        ProductCardViewActions productCardViewActions = new ProductCardViewActions(driver);
        mouseHover(addToBagByPos(i));
        click(addToBagIconByPos(i));
        if (productCardViewActions.selectAvailableSizes.size() > 0) {
            click(productCardViewActions.selectAvailableSizes.get(0));
            click(productCardViewActions.addToBagBtn);
            waitUntilElementDisplayed(addToBagNotification);
            mouseHover(addToBagNotification);
        }
        return waitUntilElementDisplayed(addToBagNotification);
    }

    public boolean clickAddToBagByPosWithSelectedSize(int i) {
        mouseHover(addToBagByPos(i));
        click(addToBagIconByPos(i));
        waitUntilElementDisplayed(addToBagNotification, 10);
        /*if(isDisplayed(continueShoppingLink)) {
            click(continueShoppingLink);
        }*/
        boolean addToBagNotifi =  validateSBConfEcom();
        clickClose_ConfViewBag();
        return addToBagNotifi;
    }

    public void clickClose_ConfViewBag(){
        click(conf_ButtonClose);
        waitUntilElementClickable(shoppingBagIcon, 15);
    }
    public boolean validateSBConfEcom() {
        //Select any size from PDP Page

        waitUntilElementDisplayed(addToBagNotification, 2);
        if (isDisplayed(conf_ViewBag) && isDisplayed(conf_ContinueCheckout) && isDisplayed(conf_Checkout)
                && waitUntilElementDisplayed(paypal_Conf, 5)) {
            return true;
        } else {
            addStepDescription("Buttons are not displayed in the SB confirmation modal for Ecom");
            return false;
        }
    }

    public boolean clickAddToBagByPosAndVerifyError(int i) {
        mouseHover(addToBagByPos(i));
        click(addToBagIconByPos(i));
        return waitUntilElementDisplayed(errorBox, 30);
    }

    public boolean clickViewBagOnSuccessMsg() {
        click(viewBagOnSuccessMsg);
        return verifyElementNotDisplayed(viewBagOnSuccessMsg);
    }

     public int getTotalQtyInWishList() {
        int totalQtyText = qtyLabel.size();
        int totalQty = 0, initialQty = 0;
        for (int i = 0; i < totalQtyText; i++) {
            int startIndex = getText(qtyLabel.get(i)).indexOf("of ");
            int endIndex = getText(qtyLabel.get(i)).indexOf("purchased");
            initialQty = Integer.parseInt(getText(qtyLabel.get(i)).substring(startIndex, endIndex));
            totalQty = totalQty + initialQty;

        }
        return totalQty;
    }

    public int getItemQtyByPosition(int pos) {
        String itemQty = getText(qtyLabel.get(pos)).replaceAll("[^0-9.]", "");
//        int endIndex = getText(qtyLabel.get(pos)).indexOf("purchased");
//        int itemQty = Integer.parseInt(getText(qtyLabel.get(pos)).substring(startIndex, endIndex));
        return Integer.parseInt(itemQty);

    }

    public boolean clickPickUpInStoreByPosition(BopisOverlayActions bopisOverlayActions, int pos) {
        click(pickUpInStoreButtonByPos(pos));
        return waitUntilElementDisplayed(bopisOverlayActions.zipCodeField);
    }

    public boolean addBopisAvailableItemToBag(BopisOverlayActions bopisOverlayActions, HeaderMenuActions headerMenuActions, int pos, String zip) {
        clickPickUpInStoreByPosition(bopisOverlayActions, pos);
        bopisOverlayActions.enterZipAndSearchStore(zip);
        boolean isStoresAvailableWithZip = bopisOverlayActions.selectFromAvailableStores(headerMenuActions, 0);
        return isStoresAvailableWithZip;
    }

    //Actions added for R5 by Azeez
    public boolean clickNewWLFromProd() {
        waitUntilElementsAreDisplayed(moveToAnotherList, 10);
        if (moveToAnotherList.size() >= 1) {
            click(moveToAnotherList.get(0));
            staticWait(1000);
        }
        click(createNewListBtn);
        return waitUntilElementDisplayed(saveButton, 10);
    }

    public boolean clickDefaultWLFromHeader(FavoriteOverlayActions favoriteOverlayActions) {
        waitUntilElementDisplayed(defaultWLName, 10);
        click(defaultWLName);
        waitUntilElementDisplayed(createNewFavListBtn, 2);
        click(createNewFavListBtn);
        return waitUntilElementDisplayed(favoriteOverlayActions.saveBtn, 10);
    }

    public boolean deleteWL() {
//        waitUntilElementDisplayed(settingsIcon,10);
//        click(settingsIcon);
        waitUntilElementDisplayed(deleteListBtn, 10);
        click(deleteListBtn);
        waitUntilElementDisplayed(confirmDeleteListBtn, 5);
        String value = "Delete list\nDeleting your list will remove all of your saved items. Are you sure you want to do this?";
        String text = getText(contentDisplay);
        value.equalsIgnoreCase(text);
        click(confirmDeleteListBtn);
        staticWait(5000);
        return waitUntilElementDisplayed(defaultWLName, 10);
    }

    public boolean isProdPresentByTitle(String title) {
        waitUntilElementsAreDisplayed(prodTitles, 5);
        for (WebElement prodTitle : prodTitles) {
            if (getText(prodTitle).toLowerCase().contains(title.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public String getProdTitleByPos(int i) {
        return getText(getProdNameByPos(i));
    }

    public boolean checkAddedItemCount() {
        waitUntilElementDisplayed(itemQty, 15);
        String itemCount = "";
        try {
            itemCount = getText(itemQty).replaceAll("[^0-9.]", "");
        } catch (NullPointerException n) {
            itemCount = "The item is not added to another wishlist";
        }
        int itemTotal = 0;
        try {
            itemTotal = Integer.parseInt(itemCount);
        } catch (NumberFormatException n) {
            itemTotal = 0;
            Assert.assertFalse(false, itemCount);
        }
        staticWait(2000);
        if (itemTotal > 0) {
            int prodImgCount = prodImg.size();
            if (itemTotal == prodImgCount)
                return true;
            else
                return false;
        }

        return false;
    }

    public boolean removeProdFromWL(int i) {
        int prodCountAfterRemove = 0;
        int prodCountBeforeRemove = prodImg.size();
        if (prodImg.size() >= 1) {
            mouseHover(itemImg(i));
            click(favIconByPos(i));
         return verifyElementNotDisplayed(favIconByPos(i),5);
//         String text = getText(updateNotification);
//         if(text.contains("Your favorites list has been updated")){
//                staticWait(3000);
//                prodCountAfterRemove = prodImg.size();
//            }

        } else {
            return false;
        }

//        return prodCountBeforeRemove > prodCountAfterRemove;
    }

    public boolean addProdToBagFromWL(HeaderMenuActions headerMenuActions, int i) {
        String getBagCountBefore = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountBeforeAddToBag = Integer.parseInt(getBagCountBefore);
        staticWait(2000);

        staticWait(1000);
        if (prodImg.size() >= 1) {
            mouseHover(itemImg(i));
            click(addToBagIconByPos(i));
            staticWait(2000);
            waitUntilElementDisplayed(viewBagButtonFromAddToBagConf, 5);
        }
//        waitUntilElementsAreDisplayed(availableColors, 5);
//        waitUntilElementsAreDisplayed(availableSizes, 5);
//        waitUntilElementDisplayed(closeAddToBaglink,5);
//        if (availableColors.size() > 1) {
//            click(availableColors.get(randInt(0, (availableColors.size() - 1))));
//            staticWait(1000);
//        }
//        if (availableSizes.size() >= 1) {
//            click(availableSizes.get(randInt(0, (availableSizes.size() - 1))));
//            staticWait(1000);
//        }

//        click(addToBagBtn);
//        staticWait(2000);
        click(conf_ButtonClose);
        String getBagCountAfter = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
        int bagCountAfterAddToBag = Integer.parseInt(getBagCountAfter);
        staticWait(2000);
        boolean isProductAddedToBag = (bagCountBeforeAddToBag + 1) == bagCountAfterAddToBag;
        return isProductAddedToBag;
    }

    public void changeQuantity(String updatequantity) {
        selectDropDownByVisibleText(qtyDropdown, updatequantity);
        waitUntilElementDisplayed(saveProdBtn, 5);
        click(saveProdBtn);
        staticWait(10000);
        double updateQuantity = Double.parseDouble(updatequantity);
    }

    public boolean editWLProdDetails(String quantity, int i) {
        mouseHover(itemImg(i));
        waitUntilElementDisplayed(prod_EditLink, 10);
        click(prod_EditLink);
        staticWait(5000);
        waitUntilElementsAreDisplayed(availableSizes, 5);
        if (availableSizes.size() >= 1) {
            click(availableSizes.get(randInt(0, (availableSizes.size() - 1))));
            staticWait(1000);
        }
        changeQuantity(quantity);
        return checkAddedItemCount();
    }

    public boolean moveProdToWLToAnotherWL() {
        staticWait(3000);
        int itemsCtBeforeMoving = getItemsCountDisplay();
        if (moveToAnotherList.size() >= 1) {
            click(moveToAnotherList.get(randInt(0, (moveToAnotherList.size() - 1))));
            waitUntilElementDisplayed(moveItemsContainer);
            click(favItemsListToMove.get(0));
            staticWait(3000);
            int itemsCtAfterMoving = getItemsCountDisplay();
            return itemsCtAfterMoving < itemsCtBeforeMoving;

        }
        return false;
    }

    public boolean verifyProductMovedToAnotherWL() {
        click(defaultWLName);
        waitUntilElementDisplayed(listContainer);
        click(itemsListDrpDown.get(1));
        staticWait(2000);
        return checkAddedItemCount();
    }

    public boolean selectWLDrpDown_MoreThan_0_Items() {
        scrollUpToElement(defaultWLName);
        click(defaultWLName);
        waitUntilElementDisplayed(listContainer);
        for (WebElement itemsCtDisplay : itemsListDrpDown) {
            int endIndex = getText(itemsCtDisplay).indexOf(" ");
            int itemsCount = Integer.parseInt(getText(itemsCtDisplay).substring(0, endIndex));
            if (itemsCount > 0) {
                click(itemsCtDisplay);
                staticWait(4000);
                return waitUntilElementDisplayed(itemQty);
            }

        }
        return false;

    }

    public int getItemsCountDisplay() {
        String items = null;
        try {
            items = getText(itemQty);
        } catch (NoSuchElementException n) {
            items = null;
        }
        if (items == null) {
            items = "0";
        }
        int itemsCount = 0;
        if (!items.equals("0")) {
            int endIndex = items.indexOf(" ");
            itemsCount = Integer.parseInt(items.substring(0, endIndex));
        }
        return itemsCount;
    }

    public boolean viewProductDetailsWL(ProductDetailsPageActions productDetailsPageActions, int i) {

        try {
            mouseHover(itemImg(i));
            waitUntilElementDisplayed(prod_EditLink, 10);
            click(prod_EditLink);
            staticWait(5000);
            click(viewProductDetails_Lnk);
            staticWait(2000);
        } catch (Throwable t) {
            logger.info(" item(s) link is currently unavailable " + t.getMessage());
        }
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }

     public boolean shareWishlist(int i) {
        waitUntilElementDisplayed(shareDropDown, 3);
        waitUntilElementDisplayed(filterOption, 3);
        click(shareDropDown);
        waitUntilElementDisplayed(shareDropDownList.get(i), 3);
        click(shareDropDownList.get(i));
        waitUntilElementDisplayed(toAddress, 3);
        if (isDisplayed(subjectAddress) && isDisplayed(cancelButton) && isDisplayed(sendButton)) {
            return closeModal();
        } else
            return false;
    }

    public boolean closeModal() {
        waitUntilElementDisplayed(Favclosemodal);
        click(Favclosemodal);
        return waitUntilElementDisplayed(defaultWLName, 4);
    }

    public boolean filterOption(int i) {
        waitUntilElementDisplayed(filterOption, 3);
        click(filterByOption.get(i));
        return waitUntilElementDisplayed(filteredPageImage.get(0), 4);
    }

    public boolean clickOnPricePDPRedirection(ProductDetailsPageActions productDetailsPageActions) {
        waitUntilElementsAreDisplayed(priceOfTheItem, 2);
        click(priceOfTheItem.get(0));
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
    }

    public boolean sortByValidation(String sortingValue, String urlValue) {


        String catPageURL = getCurrentURL();
        if (catPageURL.contains(urlValue)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateUpdateOverlay() {
        waitUntilElementDisplayed(settingsIcon, 10);
        click(settingsIcon);
        waitUntilElementDisplayed(deleteListBtn, 10);
        if (isDisplayed(updateListHeader) && isDisplayed(saveBtn) && isDisplayed(listName_Field) && isDisplayed(cancelLink)
                && isDisplayed(makeDefaultList_Checkbox) && isDisplayed(deleteButton)
                && isDisplayed(contentDisplay))
            return true;
        else
            return false;
    }

    public void clickCloseOverlay() {
        click(closeModal);
        staticWait();
    }

    public boolean validatePurchasedCount(String earlyCount) {
        waitUntilElementsAreDisplayed(purchasedItemDisplay, 3);
        String currentCount = getText(purchasedItemDisplay.get(0));
        if (earlyCount != currentCount) {
            return true;
        } else {
            addStepDescription("Purchased Count is not updated");
            return false;
        }
    }

    public boolean clickProdByNameInFav() {
        waitUntilElementDisplayed(settingsIcon, 10);
        click(settingsIcon);
        waitUntilElementDisplayed(deleteListBtn, 10);
        if (isDisplayed(updateListHeader) && isDisplayed(saveBtn) && isDisplayed(listName_Field) && isDisplayed(cancelLink)
                && isDisplayed(makeDefaultList_Checkbox) && isDisplayed(deleteButton)
                && isDisplayed(contentDisplay))
            return true;
        else
            return false;
    }

    public boolean clickProdPresentByTitle(ProductDetailsPageActions productDetailsPageActions,String title) {
        for (WebElement prodTitle : prodTitles) {
            if (getText(prodTitle).toLowerCase().contains(title.toLowerCase())) {
                click(prodTitle);
                return waitUntilElementsAreDisplayed(breadcrumb_values, 20);
            }
        }
        return false;
    }


    public boolean editWLProdColorDetails(String color, int i) {
        mouseHover(itemImg(i));
        waitUntilElementDisplayed(prod_EditLink, 10);
        click(prod_EditLink);
        staticWait(4000);
        waitUntilElementsAreDisplayed(availableColors, 3);
        int count = availableColors.size();
        if(count > 1) {
            click(availableColors.get(i));
        }
        String selectedCol = getText(selectedColor);
        ProductCardViewActions productCardViewActions = new ProductCardViewActions(driver);
        staticWait(4000);
        if (productCardViewActions.selectAvailableSizes.size() > 0) {
            click(productCardViewActions.selectAvailableSizes.get(0));
            click(saveProdDetails);
        }
        return true;
    }

    public boolean clickOnItemImgAndPDPRed(ProductDetailsPageActions productDetailsPageActions, int i) {
        waitUntilElementDisplayed(itemNameFav, 3);
        click(itemNameFav);
        staticWait();
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag, 5) && (waitUntilElementDisplayed(productDetailsPageActions.pickUpStore,3));
    }

    public boolean createNewFav(String wishListName) {
        clickCreteNewFav();
        return enterWishListNameAndCreate(wishListName);
    }

    public boolean clickCreteNewFav() {
        click(createFavButton);
        return waitUntilElementDisplayed(saveButton);
    }

    public boolean createFavList(String name) {
        waitUntilElementDisplayed(favDropDown, 3);
        click(favDropDown);
        waitUntilElementDisplayed(createFavButton, 3);
        click(createFavButton);
        waitUntilElementDisplayed(listName_Field);
        clearAndFillText(listName_Field, name);
        click(saveButton);
        return waitUntilElementDisplayed(defaultWLName, 3);
    }

    public boolean selectProductMovedToAnotherWL(int i) {
        waitUntilElementDisplayed(defaultWLName, 3);
        scrollToTheTopHeader(defaultWLName);
        click(defaultWLName);
        waitUntilElementDisplayed(listContainer);
        click(itemsListDrpDown.get(i));
        return waitUntilElementDisplayed(productDisplay, 4);
    }

    /**
     * Verify badges are displayed or not
     *
     * @return
     */
    public boolean isBadgesDisplayed() {
        return badges.size() == 0;
    }
}
