package uiMobile.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import uiMobile.pages.repo.MobileBopisRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by JKotha on 23/10/2017.
 */
public class MobileBopisOverlayActions extends MobileBopisRepo {
    WebDriver mobileDriver;
    boolean isStoreAvailable;
    String bopisLocationName;
    List<String> storeAddDetails;
    Logger logger = Logger.getLogger(MobileBopisOverlayActions.class);


    public MobileBopisOverlayActions(WebDriver mobileDriver) {
        this.mobileDriver = mobileDriver;
        setDriver(mobileDriver);
        PageFactory.initElements(mobileDriver, this);
    }

    public boolean zipFieldErrMsg(String zipErr) {
        zipCodeField.sendKeys(Keys.TAB);
        waitUntilElementDisplayed(zipErrMsg, 5);
        boolean validatePasswordErr = getText(zipErrMsg).contains(zipErr);

        if (validatePasswordErr)
            return true;
        else
            return false;
    }

    public boolean validateTextInBopisOverlay(String text1, String text2) {

        boolean headerContentText = getText(headerContent).contains(text1);
        boolean subHeaderContentText = waitUntilElementDisplayed(subHeaderContent, 5);//getText(subHeaderContent).contains(text2);

        if (headerContentText && subHeaderContentText && waitUntilElementDisplayed(prodName, 5))
            return true;
        else
            return false;
    }

    /**
     * Validate error message for zip code field
     *
     * @return
     */
    public boolean validateZipFieldErrMsg(String zip, String zipErr) {
        waitUntilElementDisplayed(zipCodeField, 10);
        if (zip != null)
            clearAndFillText(zipCodeField, zip);
        click(searchButton);
        selectDropDownByIndex(sizeDropDown, 1);
        waitUntilElementDisplayed(zipErrMsg, 5);
        return getText(zipErrMsg).contains(zipErr);
    }

    public boolean validateSizeErrMsg(String err) {
        waitUntilElementDisplayed(zipCodeField, 10);
        click(searchButton);
        waitUntilElementDisplayed(sizeErrorMessage, 5);
        System.out.println(getText(sizeErrorMessage));
        System.out.println(err);
        return getText(sizeErrorMessage).contains(err);
    }

    public int getAvailStrsCntAndClickAvailStoresChkBox() {
        waitUntilElementDisplayed(availableStrCheckBox, 20);
        String txtLbl = getText(availableStoreCountTxtLbl).replaceAll("[^0-9.]", "");
        int availableStoreCount = Integer.parseInt(txtLbl);
        select(availableStrCheckBox, availableStrCheckBoxInput);
        staticWait(3000);
        return availableStoreCount;
    }

    public boolean enterZipAndSearchStore(String zipCode) {
        waitUntilElementDisplayed(zipCodeField, 20);
        clearAndFillText(zipCodeField, zipCode);
        click(searchButton);
        boolean isAvailTxt = waitUntilElementDisplayed(availableText, 30);
        if (!isAvailTxt) {
            click(searchButton);
        }
        return waitUntilElementDisplayed(availableText, 30);

    }

    public boolean selectSizeAndSelectStore(String zipCode) {
        if (sizeDropDownOptions().size() == 1) {
            selectDropDownByIndex(sizeDropDown, 0);
            clear(zipCodeField);
            enterZipAndSearchStore(zipCode);
            if (availStoresCount() >= 1) {
                return true;
            }
        } else {
            for (int i = 1; i <= sizeDropDownOptions().size(); i++) {
                selectDropDownByIndex(sizeDropDown, i);
                clear(zipCodeField);
                enterZipAndSearchStore(zipCode);
                if (availStoresCount() >= 1) {
                    return true;
                }

            }
        }
        return false;
    }

    public int availStoresCount() {
        String txtLbl = "0";
        try {
            txtLbl = getText(availableStoreCountTxtLbl).replaceAll("[^0-9.]", "");
        } catch (Exception e) {

        }
        if (txtLbl.equalsIgnoreCase(".")) {
            addStepDescription("<b><font color=#8b0000>" + getText(availableStoreCountTxtLbl) + "</font></b>");
            txtLbl = "0";
        }
        return Integer.parseInt(txtLbl);

    }

    public boolean selectFromAvailableStores(MobileHeaderMenuActions headerMenuActions, int bagCountBeforeAddToBag) {

        int availStoreCount = getAvailStrsCntAndClickAvailStoresChkBox();
        storeAddDetails = new ArrayList<String>();
        staticWait(5000);
        if (availStoreCount != 0) {
            storeAddDetails = new ArrayList<String>();
            storeAddDetails.add(getText(bopisStoreAddress.get(0)));
            storeAddDetails.add(getText(bopisStoreName.get(0)));

            click(addBopisToBagButtons.get(0));
            waitUntilElementDisplayed(headerMenuActions.addtoBagNotification, 10);
            refreshPage();
            String getBagCountAfter = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
            int bagCountAfterAddToBag = Integer.parseInt(getBagCountAfter);
            System.out.println("Bag count after the prod added to bag " + getBagCountAfter + " before " + bagCountBeforeAddToBag);
            staticWait(2000);
            boolean isProductAddedToBag = (bagCountBeforeAddToBag + 1) == bagCountAfterAddToBag;
            return isProductAddedToBag;
        } else {
            return false;
        }
    }

    public boolean clickSelectStoreBtnByPosition(int position) {
        int availStoresCount = getAvailStrsCntAndClickAvailStoresChkBox();
        if (availStoresCount != 0) {
            WebElement selectStore = selectStoreByPosition(position);
            click(selectStore);
            try {
                return verifyElementNotDisplayed(closeOverlay, 30);
            } catch (Throwable t) {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean selectDifferentStore(String currentSelectedStore) {
        int availStoresCount = getAvailStrsCntAndClickAvailStoresChkBox();
        if (availStoresCount != 0 && availStoresCount > 1) {
            //       clickShowMoreDetailsLink();
            for (int j = 0; j < availableStoreNames.size(); j++) {
                String listedStoreName = getText(availableStoreNames.get(j)).toLowerCase();
                if (!currentSelectedStore.toLowerCase().contains(listedStoreName)) {
                    WebElement selectStore = selectStoreByPosition(j + 1);
                    javaScriptClick(mobileDriver, selectStore);
                    staticWait(8000);
//                        try {
//                            return verifyElementNotDisplayed(closeOverlay, 30);
//                        } catch (Throwable t) {
//                            return true;
//                        }

                }
            }
        } else if (availStoresCount == 1 || availStoresCount == 0) {
            closeBopisOverlayModal();
            logger.info("There is only one Store for this product or No other store is available.");

        } else {
            return false;
        }
        return verifyElementNotDisplayed(closeOverlay, 30);
    }

    public boolean clickShowMoreDetailsLink() {
        waitUntilElementDisplayed(showMoreDetailsLink, 20);
        click(showMoreDetailsLink);
        return verifyElementNotDisplayed(showMoreDetailsLink, 30);
    }

    public boolean clickAddtoBag() {
        MobileHeaderMenuActions action = new MobileHeaderMenuActions(mobileDriver);
        javaScriptClick(mobileDriver, addBopisToBagButtons.get(0));
        return waitUntilElementDisplayed(action.addtoBagNotification);
    }

    public boolean clickSelectStoreBtnByAvailableStores() {
        int availStoresCount = getAvailStrsCntAndClickAvailStoresChkBox();
        boolean isBopisOverlayNotPre = false, invUnAvaServErr = false;
        if (availStoresCount != 0) {
            for (int i = 1; i <= availStoresCount; i++) {

                WebElement selectStore = selectStoreByPosition(i);
                javaScriptClick(mobileDriver, selectStore);
                staticWait(8000);
                try {
                    isBopisOverlayNotPre = !waitUntilElementDisplayed(closeOverlay, 20);
                } catch (Throwable t) {
                    isBopisOverlayNotPre = true;
                }
                if (!isBopisOverlayNotPre) {
                    invUnAvaServErr = waitUntilElementDisplayed(invUnavailableServerErr, 15);
                    if (invUnAvaServErr) {
                        continue;
                    }
                } else {
                    break;
                }
            }
        }
//        else{
//            return false;
//        }
        return isBopisOverlayNotPre;
    }

    public boolean closeBopisOverlayModal() {
        waitUntilElementDisplayed(closeOverlay, 20);
        click(closeOverlay);
        return verifyElementNotDisplayed(closeOverlay);
    }

    public boolean updateQty(String qty, String zip) {
        selectDropDownByValue(qtyDrpDown, qty);
        boolean isAvailTextPresent = enterZipAndSearchStore(zip);
//        click(searchButton);
//        staticWait();
//        waitUntilElementDisplayed(availableText, 30);
        if (isAvailTextPresent) {
            return clickSelectStoreBtnByAvailableStores();
        } else {
            return false;
        }
    }

    public boolean changeBopisStore(String pickUpStoreName, String zipCode) {
        enterZipAndSearchStore(zipCode);
        return selectDifferentStore(pickUpStoreName);


        /*,"Verifying the Pickup In Store Name after selecting different store");*/

    }

    /**
     * Created By Pooja
     * This Method verifies that color related to applied filter is selected By default tin Bopis Overlay
     */
    public boolean verifyWhiteColorSelected() {
        List<String> colors = Arrays.asList("SNOW", "WHITE", "SIMPLYWHT");
        String defaultSelectedColor = getText(selectedSizeInBopisOverlay);
        return colors.contains(defaultSelectedColor);
    }

    public boolean clickCheckAvailabilityButton() {
        click(checkAvailability);
        return waitUntilElementDisplayed(storeInfoContainer, 20);
    }

    public List<String> getPickUpStoreNameOnBopisModal() {
        List<String> storeNameReturned = new ArrayList<>();
        for (int i = 0; i < pickUpStoreName.size(); i++) {
            addStepDescription("Store name on Bopis modal" + pickUpStoreName.get(i).getText());
            storeNameReturned.add(getText(pickUpStoreName.get(i)));
        }
        return storeNameReturned;

    }


    /**
     * Created By Pooja
     * This Method verifies that the Fav Store added is displayed at top with all the address details in Bopis Modal
     */
    public boolean verifyFavORRecent_Store(String favStore) {
        if (waitUntilElementDisplayed(favStoreSelectedFirst)) {
            if (getText(favStoreSelectedFirst).equals(favStore)) {
                return waitUntilElementDisplayed(favStoreAddressDetails, 5);
            } else {
                addStepDescription("Store added Favourite Store does not match the one displayed on Bopis");
                return false;
            }
        }
        addStepDescription("Favourite/Recent Store is not displayed");
        return false;
    }

    /**
     * Created by Pooja
     * This Method selects the Store other than the Store passed among all the available stores
     */
    public String selectAnotherStore(String currentSelectedStore) {
        int availStoresCount = getAvailStrsCntAndClickAvailStoresChkBox();
        if (availStoresCount > 1) {
            for (int j = 0; j < notSelectedAvailableStores.size(); j++) {
                String listedStoreName = getText(notSelectedAvailableStores.get(j)).toLowerCase();
                if (!currentSelectedStore.toLowerCase().contains(listedStoreName)) {
                    WebElement selectStore = selectStoreByPosition(j + 1);
                    String selectedStore = getText(notSelectedAvailableStores.get(j + 1));
                    click(selectStore);
                    return selectedStore;
                }
            }
        }
        addStepDescription("Available Stores are not Present");
        return null;
    }

    /**
     * Created by Pooja
     * This Method validates the Store Details
     */
    public boolean validateStoresDetailsInBopisModal(String store1, String store2) {
        waitUntilElementDisplayed(subHeaderContent, 5);
        String subHeaderContentText = getText(subHeaderContent);

        if (subHeaderContentText.contains(store1) && subHeaderContentText.contains(store2)) {
            click(checkAvailability);
            waitUntilElementDisplayed(storesListContainer);
            if (bopisStoreName.size() == 2 && bopisDistanceFromUserLocation.size() == 2 && bopisStoreAddress.size() == 2
                    && bopisStorePhoneNo.size() == 2 && bopisExtendedSchedule.size() == 2 && bopisAvailability.size() == 2
                    && bopisSelectStoreBtn.size() == 2 && Integer.parseInt(bopisStoreName.get(0).getCssValue("font-weight")) >= 500) {
                addStepDescription("Store Info displayed for 2 added stores");
                return true;
            }
        }
        addStepDescription("Store Info not displayed for 2 added stores");
        return false;
    }

    public List<String> getBopisStoreAddDetails() {
        return storeAddDetails;
    }

    /**
     * Get Selected size in Bopis Overlay
     *
     * @return selected size
     */
    public String getSelectedSize() {
        waitUntilElementDisplayed(sizeDropDown, 10);
        return getSelectOptions(sizeDropDown);
    }

    public void selectASize(int index) {
        waitUntilElementDisplayed(sizeDropDown, 10);
        selectDropDownByIndex(sizeDropDown, index);
    }

    public String getSelectedColor() {
        waitUntilElementDisplayed(colorDD, 10);
        return getText(colorDD);
    }

    public int getSelectedQty() {
        waitUntilElementDisplayed(qtyDrpDown, 10);
        return Integer.parseInt(getSelectOptions(qtyDrpDown));
    }

    public void selectQty(String qty) {
        waitUntilElementDisplayed(qtyDrpDown, 10);
        selectDropDownByVisibleText(qtyDrpDown, qty);
    }

    public boolean verifyMaxQuantity(int maxQty) {
        List<String> qty = getAllOptionsText(qtyDrpDown);
        return Integer.parseInt(qty.get(qty.size() - 1)) == maxQty;
    }

    public boolean validateBopisFields() {
        return waitUntilElementDisplayed(headerContent, 10) &&
                waitUntilElementDisplayed(subHeaderContent, 5) &&
                waitUntilElementDisplayed(prodName, 5) &&
                waitUntilElementDisplayed(prodImg, 5) &&
                waitUntilElementDisplayed(offerPrice, 5) &&
                waitUntilElementDisplayed(zipCodeField, 5) &&
                waitUntilElementDisplayed(searchButton, 5);
    }

    public boolean validateTwoBopisFields() {
        return waitUntilElementDisplayed(headerContent, 10) &&
                waitUntilElementDisplayed(subHeaderContent, 5) &&
                waitUntilElementDisplayed(prodName, 5) &&
                waitUntilElementDisplayed(prodImg, 5) &&
                waitUntilElementDisplayed(offerPrice, 5) &&
                waitUntilElementDisplayed(checkAvailability, 5);
    }

    public boolean validateDistanceDropDown() {
        return getSelectOptions(distanceDropdown).equalsIgnoreCase("25 miles") &&
                getAllOptionsText(distanceDropdown).contains("25 miles") &&
                getAllOptionsText(distanceDropdown).contains("50 miles") &&
                getAllOptionsText(distanceDropdown).contains("75 miles");
    }

    public String getOfferPrice() {
        waitUntilElementDisplayed(offerPrice, 10);
        return getText(offerPrice);
    }

    /**
     * verifies store distance in ascending order
     * in search
     *
     * @return
     */
    public boolean validateStoresListedDistance() {
        List<Float> distance = new ArrayList<>();
        waitUntilElementsAreDisplayed(storeDistanceLabel, 10);
        for (WebElement storeDistance : storeDistanceLabel) {
            float d = Float.valueOf(storeDistance.getText().split(" ")[0].trim());
            distance.add(d);
        }

        for (int i = 1; i < distance.size(); i++) {
            if (distance.get(i - 1) > distance.get(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean selectSizeAndSelectMoreThan_1Store(String zipCode) {
        int sizes = sizeDropDownOptions().size();
        if (sizes > 1) {
            selectDropDownByIndex(sizeDropDown, 1);
            sizes = sizeDropDownOptions().size();
        }
        for (int i = 0; i < sizes; i++) {
            if (sizes == 1)
                selectDropDownByIndex(sizeDropDown, 0);
            else
                selectDropDownByIndex(sizeDropDown, i);
            clear(zipCodeField);
            enterZipAndSearchStore(zipCode);
            if (availStoresCount() > 1) {
                return true;
            }

        }
        return false;
    }

    public boolean validateAvailableStores() {
        List<String> available = new ArrayList<>();
        waitUntilElementsAreDisplayed(bopisAvailability, 10);

        for (WebElement avail : bopisAvailability) {
            available.add(getText(avail));
        }

        for (int i = 0; i < available.size(); i++) {
            if (available.get(i).equalsIgnoreCase("UNAVAILABLE")) {
                return false;
            }
        }

        return true;
    }

    public boolean validateStoreMobileFormat() {
        List<String> storeNos = new ArrayList<>();
        waitUntilElementsAreDisplayed(bopisStorePhoneNo, 10);
        for (WebElement storePhone : bopisStorePhoneNo) {
            storeNos.add(getText(storePhone).trim());
        }

        for (String phone : storeNos) {
            if (phone.substring(0, 1).equalsIgnoreCase("(") &&
                    phone.substring(4, 5).equalsIgnoreCase(")") &&
                    phone.substring(5, 6).equalsIgnoreCase(" ") &&
                    phone.substring(9, 10).equalsIgnoreCase("-") && isInteger(phone.substring(1, 4)) &&
                    isInteger(phone.substring(6, 9)) && isInteger(phone.substring(10, 14))) ;
            return true;
        }

        return false;
    }

    public boolean checkStoreMilesBasedOnMilesSelected(String s) {
        boolean dis = false;
        selectDropDownByValue(distanceDropdown, s);
        click(searchButton);
        List<Float> distance = new ArrayList<>();
        waitUntilElementsAreDisplayed(storeDistanceLabel, 30);
        for (WebElement storeDistance : storeDistanceLabel) {
            float d = Float.valueOf(storeDistance.getText().split(" ")[0].trim());
            distance.add(d);
        }

        for (Float f : distance) {
            if (f < Float.parseFloat(s)) {
                dis = true;
            }
        }
        return dis;
    }

    /**
     * Created by Richa Priya
     * <p>
     * Select recent store for different product
     */
    public boolean selectRecentStore(String recentStore) {
        boolean flag = false;
        if (waitUntilElementDisplayed(favStoreSelectedFirst)) {
            if (getText(favStoreSelectedFirst).equals(recentStore)) {
                javaScriptClick(mobileDriver, selectStoreByPosition(1));
                flag = true;
            } else {
                addStepDescription("Store added Favourite Store does not match the one displayed on Bopis");
                flag = false;
            }
        }
        addStepDescription("Favourite/Recent Store is not displayed");
        return flag;
    }

    public boolean validateFitSection() {
        return waitUntilElementDisplayed(fitSection, 15);
    }

    /**
     * check Product Availability after adding two stores
     */
    public boolean checkProductAvailability() {
        int sizes = sizeDropDownOptions().size();
        if (sizes > 1) {
            selectDropDownByIndex(sizeDropDown, 1);
            sizes = sizeDropDownOptions().size();
        }
        for (int i = 0; i < sizes; i++) {
            if (sizes == 1)
                selectDropDownByIndex(sizeDropDown, 0);
            else
                selectDropDownByIndex(sizeDropDown, i);

            javaScriptClick(mobileDriver, checkAvailability);
            if (waitUntilElementDisplayed(emptySearchResult, 10))
                addStepDescription("Sorry, your item is not available at the selected 2 stores");
            return waitUntilElementsAreDisplayed(pickUpStoreName, 20) && pickUpStoreName.size() == 2;

        }
        return false;
    }

    public void clickCheckAvailabilityBtn() {
        javaScriptClick(mobileDriver, checkAvailability);
    }

    public void enterZip(String zip) {
        waitUntilElementDisplayed(zipCodeField, 10);
        clearAndFillText(zipCodeField, zip);
    }

    public boolean verifyAddToBagButtonDisabledForUnavailable() {
        if (waitUntilElementsAreDisplayed(unavailableStores, 10)) {
            return !isEnabled(unavailableStores.get(0));
        } else {
            addStepDescription("ther are no stores with unavailable");
            return false;
        }
    }

    /**
     * verify product badge in bopis ovelay
     *
     * @return
     */
    public boolean verifyBadgeInBopisOverlay() {
        return isDisplayed(productBadge);
    }

    public int changeColor() {
        waitUntilElementDisplayed(colorDD, 10);
        click(colorDD);
        if (colors.size() == 1) {
            addStepDescription("Product available only in one color");
        }
        if (colors.size() > 1) {
            javaScriptClick(mobileDriver, colors.get(randInt(0, (colors.size() - 1))));
            staticWait(1000);
        }
        return colors.size();
    }

    public String getImgSrc() {
        waitUntilElementDisplayed(prodImg);
        return getAttributeValue(prodImg, "src");
    }

    public boolean validateStoreOpenAndCloseTime() {
        int storeCount = notSelectedAvailableStores.size();
        boolean validation = false;
        validation = storeCount == bopisExtendedSchedule.size();
        for (WebElement bopisStore : bopisExtendedSchedule) {
            validation = getText(bopisStore).contains("pm");
        }
        return validation;
    }

    public List<String> getStoreNames() {
        ArrayList<String> storeNames = new ArrayList<String>();
        for (WebElement webElem : notSelectedAvailableStores) {
            storeNames.add(getText(webElem).toLowerCase());
        }

        return storeNames;
    }

    /**
     * Created by Richa Priya
     *
     * @return
     */
    public void selectFitAndSizeOnBopisOverlay(String fit, String size) {
        addStepDescription("Fit and size to select on bopis overlay::" + fit + "::" + size);
        if (fitDropDownOptions().size() > 1) {
            selectDropDownByVisibleText(fitDropDown, fit.trim());
        }
        if (sizeDropDownOptions().size() > 1) {
            selectDropDownByVisibleText(sizeDropDown, size.trim());
        }

    }

}
