package uiMobile.pages.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import uiMobile.pages.repo.MobileFavoritePageRepo;

public class MobileFavouritesActions extends MobileFavoritePageRepo {

    WebDriver driver;
    public static String itemNumVerify;
    public String copiedUrl;

    public MobileFavouritesActions(WebDriver driver) {
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
        itemNumVerify = MProductDetailsPageActions.itemNum;
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

    public int getItemQtyByPosition(int pos) {
        String itemQty = getText(qtyLabel.get(pos)).replaceAll("[^0-9.]", "");
//        int endIndex = getText(qtyLabel.get(pos)).indexOf("purchased");
//        int itemQty = Integer.parseInt(getText(qtyLabel.get(pos)).substring(startIndex, endIndex));
        return Integer.parseInt(itemQty);

    }

    public boolean clickPickUpInStoreByPosition(MobileBopisOverlayActions bopisOverlayActions, int pos) {
        click(pickUpInStoreButtonByPos(pos));
        return waitUntilElementDisplayed(bopisOverlayActions.zipCodeField);
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
            staticWait(3000);
            prodCountAfterRemove = prodImg.size();
        } else {
            return false;
        }

        return prodCountBeforeRemove > prodCountAfterRemove;
    }

    public void changeQuantity(String updatequantity) {
        selectDropDownByVisibleText(qtyDropdown, updatequantity);
        waitUntilElementDisplayed(saveProdBtn, 5);
        click(saveProdBtn);
        staticWait(10000);
        double updateQuantity = Double.parseDouble(updatequantity);
    }

    public boolean moveProdToWLToAnotherWL() {
        staticWait(1000);
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

    public boolean viewProductDetailsWL(MProductDetailsPageActions productDetailsPageActions, int i) {

        try {
            mouseHover(itemImg(i));
            waitUntilElementDisplayed(prod_EditLink, 10);
            click(prod_EditLink);
            staticWait(5000);
            click(viewProductDetails_Lnk);
            staticWait(2000);
        } catch (Throwable t) {
            //logger.info(" item(s) link is currently unavailable " + t.getMessage());
        }
        return waitUntilElementDisplayed(productDetailsPageActions.addToBag);
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

    public boolean clickOnPricePDPRedirection(MProductDetailsPageActions productDetailsPageActions) {
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

    public boolean clickProdPresentByTitle(String title, MProductDetailsPageActions mProductDetailsPageActions) {
        for (WebElement prodTitle : prodTitles) {
            if (getText(prodTitle).toLowerCase().contains(title.toLowerCase())) {
                click(prodTitle);
                return waitUntilElementDisplayed(mProductDetailsPageActions.addToBag);
            }
        }
        return false;
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

    /**
     * Created By Pooja,removes the Favourite Image By Title from Favourite Page
     */
    public boolean removeProdFromFavByTitle(String title) {
        click(selectedFavIconByTtile(title));
        return verifyElementNotDisplayed(selectedFavIconByTtile(title));
    }

    /**
     * Created By Pooja,verify Product present in Favourite Page by Title
     */
    public boolean verifyProdPresentByTitle(String title) {
        for (WebElement prodTitle : prodTitles) {
            if (getText(prodTitle).toLowerCase().contains(title.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a favorite product to bag
     *
     * @param i position of the product, starts from 1
     */
    public void addProductToBagByPosition(int i) {
        MobileProductCardViewActions productCardViewActions = new MobileProductCardViewActions(driver);
        MobileHeaderMenuActions action = new MobileHeaderMenuActions(driver);
        waitUntilElementDisplayed(defaultWLName);
        mouseHover(addToBagByPos(i));
        //scrollDownToElement(addToBagByPos(i));
        javaScriptClick(driver, addToBagByPos(i));

        selectAColor(productCardViewActions);
        if (productCardViewActions.selectAvailableSizes.size() > 0) {
            javaScriptClick(driver,productCardViewActions.selectAvailableSizes.get(0));
        }
        javaScriptClick(driver,productCardViewActions.addToBagBtn);
        waitUntilElementDisplayed(action.addtoBagNotification, 30);
        addStepDescription("Added "+i+" position product to bag");

        refreshPage();
    }

    public void selectAColor(MobileProductCardViewActions productCardViewActions) {
        staticWait(2000);
        if (productCardViewActions.colorImages.size() == 1) {
            click(productCardViewActions.colorImages.get(0));
        }
        if (productCardViewActions.colorImages.size() > 1) {
            click(productCardViewActions.colorImages.get(randInt(0, (productCardViewActions.colorImages.size() - 1))));
        }
    }

    /**
     * validate product card length in PLP
     *
     * @return true if it matched with expected
     */
    public boolean validatedProductWidth() {
        waitUntilElementDisplayed(productCards(1), 30);
        String productwidth = productCards(1).getCssValue("width");
        return productwidth.equalsIgnoreCase("205px");
    }

    /**
     * remove an item from favorite list
     *
     * @param i item position to remove
     */
    public boolean unFavoriteItem(int i) {
        waitUntilElementsAreDisplayed(productLists, 20);
        click(favIcons.get(i));
        return waitUntilElementDisplayed(notification);
    }

    /**
     * Select option from drop-down
     *
     * @param text
     */
    public void selectDisplayMode(String text) {
        selectDropDownByVisibleText(displaySection, text);
        staticWait(2000);
    }

    /**
     * Create a new wishlist
     *
     * @param newList name of the wih list
     */
    public boolean createANewWishList(String newList) {
        waitUntilElementDisplayed(defaultWLName, 10);
        click(defaultWLName);
        waitUntilElementDisplayed(newWishListBtn, 10);
        click(newWishListBtn);
        clearAndFillText(wishListName, newList);
        click(saveBtn);
        return waitUntilElementDisplayed(emptyWishList);
    }

    /**
     * Switch between available wish lists
     *
     * @param wishList name to switch
     */
    public void changeWishList(String wishList) {
        waitUntilElementDisplayed(defaultWLName, 10);
        click(defaultWLName);
        if (wishList.equalsIgnoreCase("default")) {
            click(defaultWishList);
        } else {
            click(wishlist(wishList));
        }

    }

    public int getInitialCountOfProductsFromWLinProdDD(String wishListName) {
        String count = getText(prodCountInWL(wishListName));
        return Integer.parseInt(count.substring(0, 1));
    }

    /**
     * Move a product from visible wishlist to another wishList
     *
     * @param i        position of the product
     * @param wishList destination wishlist
     * @return true if successful
     */
    public boolean moveProductToOtherWishList(int i, String wishList) {
        click(moveToAnotherWishList.get(i));
        int initialCount = productLists.size();
        click(wishlist(wishList));
        int afterCount;
        do {
            staticWait();
            afterCount = productLists.size();
        }
        while (afterCount != initialCount - 1);
        return afterCount == initialCount - 1;
    }

    /**
     * Share a wish list via email
     *
     * @param toAddress of receiver
     * @param subject   for email
     * @param message   for sharing
     * @return
     */
    public boolean shareWishListViaEmail(String toAddress, String subject, String message) {
        waitUntilElementDisplayed(shareDropDown, 10);
        click(shareDropDown);
        click(emailShare);
        waitUntilElementDisplayed(shareTo, 10);
        clearAndFillText(shareTo, toAddress);
        clearAndFillText(shareSubject, subject);
        clearAndFillText(shareMessage, message);
        click(sendBtn);
        return waitUntilElementDisplayed(shareDropDown, 10);
    }

    /**
     * Click add to bag product
     *
     * @param productCardViewActions
     * @param position
     * @return
     */
    public boolean clickAddToBagForProduct(MobileProductCardViewActions productCardViewActions, int position) {
        javaScriptClick(driver, addToBagByPos(position));
        click(productCardViewActions.colorImages.get(0));
        return productCardViewActions.selectAvailableSizes.size() == 0 && productCardViewActions.selectedOneSizeBtn.size() == 1;
    }

    /**
     * Click add to bag and verify add to bag overlay is displayed
     *
     * @param mobileProductCardViewActions
     * @param i                            position of the product
     * @return
     */
    public boolean clickAddToBag(MobileProductCardViewActions mobileProductCardViewActions, int i) {
        javaScriptClick(driver, addToBagByPos(i));
        return waitUntilElementDisplayed(mobileProductCardViewActions.closeIcon, 10);
    }

    /**
     * Click on edit link
     *
     * @param mobileProductCardViewActions
     * @param i                            product to perform
     * @return
     */
    public boolean clickEditLink(MobileProductCardViewActions mobileProductCardViewActions, int i) {
        javaScriptClick(driver, editLink(i));
        return waitUntilElementDisplayed(mobileProductCardViewActions.closeIcon, 10);
    }


    public void EditAFavoriteProduct(MobileProductCardViewActions mobileProductCardViewActions, int i) {
        click(editLink(i));

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
