package ui.pages.actions;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import ui.pages.repo.BopisRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by AbdulazeezM on 3/30/2017.
 */
public class BopisOverlayActions extends BopisRepo {
    WebDriver driver;
    boolean isStoreAvailable;
    Logger logger = Logger.getLogger(BopisOverlayActions.class);


    public BopisOverlayActions(WebDriver driver) {
        this.driver = driver;
        setDriver(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean zipFieldErrMsg(String zipErr)

    {
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

    public boolean validateEmptyZipFldErrMsg() {
        staticWait(100);
        click(searchButton);
        if(waitUntilElementDisplayed(zipErrMsg, 15)){
            return true;
        }
        else{
            addStepDescription("Check the Defect DTN-2622");
            return false;
        }
    }

    public int getAvailStrsCntAndClickAvailStoresChkBox() {
        waitUntilElementDisplayed(availableStrCheckBox, 20);
        String txtLbl = null;
        try {
            txtLbl = getText(availableStoreCountTxtLbl).replaceAll("[^0-9.]", "");
        } catch (NullPointerException ex) {
            Assert.fail("There are no available stores with selected upc and zip");
        }
        int availableStoreCount = Integer.parseInt(txtLbl);
        click(availableStrCheckBox);
        staticWait(3000);
//        clickShowMoreDetailsLink();
//        boolean checkAvailableStoreCount = (availableStoreCount == availableStoreBtn.size());
         return availableStoreCount;
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

    public int getAvailTwoStrsCnt() {

        int availableStoreCount = getAvailableTwoStr.size();
        int availableLmtCount = getLmtAvailableTwoStr.size();
        int totalStoreCount = availableStoreCount + availableLmtCount;
        staticWait(3000);
        return totalStoreCount;
    }


    public boolean enterZipAndSearchStore(String zipCode) {

        waitUntilElementDisplayed(zipCodeField, 20);
        clearAndFillText(zipCodeField, "");
        clearAndFillText(zipCodeField, zipCode);
        //staticWait(5000);
        waitUntilElementDisplayed(searchButton);
        click(searchButton);
        staticWait(1000);
        if (isAlertPresent()) {
            acceptAlert();
        }
        boolean isAvailTxt = waitUntilElementDisplayed(availableStrCheckBox, 30);
        if (!isAvailTxt) {
            click(searchButton);
        }
        return waitUntilElementDisplayed(availableStrCheckBox, 30);

    }

    public boolean verifyBopisOverlaySections() {
        boolean isShowMoreLink = isDisplayed(showMoreDetailsLink);
        boolean closestStores = storeNamesExceptFav.size() >= 5;
        boolean storeInfo = isDisplayed(storesListContainer);
        boolean availableTodayAtTxt = getText(availableStoreCountTxtLbl).replaceAll("[0-9]", "").contains("Available Today at");
        if (!isShowMoreLink) {
            addStepDescription("Show more link is not displaying");
        }
        if (!closestStores) {
            addStepDescription("closest stores are not displaying or displays less than 5 stores " + storeNamesExceptFav.size());
        }
        if (!storeInfo) {
            addStepDescription("Store Info is not displaying");
        }
        if (!availableTodayAtTxt) {
            addStepDescription("Avaialable today at x stores text is not displaying");
        }
        return closestStores && storeInfo && availableTodayAtTxt;
    }

    public boolean selectSizeAndSelectStore(String zipCode) {

        for (int i = 1; i <sizeDropDownOptions().size(); i++) {
            selectDropDownByIndex(sizeDropDown, i);
            clear(zipCodeField);
            enterZipAndSearchStore(zipCode);
            if (availStoresCount() >= 1) {
                return true;
            }

        }
        return false;

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

    public boolean selectSizeAndCheckAvailability() {
        int availableStores = 0;
        waitUntilElementDisplayed(sizeDropDown, 3);
        int sizes = sizeDropDownOptions().size();
        for (int i = 0; i < sizes; i++) {
            if (sizes == 1)
                selectDropDownByIndex(sizeDropDown, i);
            else
                selectDropDownByIndex(sizeDropDown, i + 1);

            click(checkAvailability);
            waitUntilElementsAreDisplayed(storesAvailable, 20);
            availableStores = storesAvailable.size() - addToBagDisabledForUnavailable.size();
            if (availableStores > 1) {
                break;
            }
        }
//        {
//            selectDropDownByIndex(sizeDropDown, 1);
//            staticWait();
//            click(checkAvailability);
//            staticWait(1000);
//            if (isAlertPresent()) {
//                acceptAlert();
//            }
        return waitUntilElementDisplayed(availableText, 30);


    }


    public boolean selectFromAvailableStores(HeaderMenuActions headerMenuActions, int bagCountBeforeAddToBag) {

        int availStoreCount = getAvailStrsCntAndClickAvailStoresChkBox();
        staticWait(5000);
        if (availStoreCount != 0) {
            click(addBopisToBagButtons.get(0));
            boolean viewBagConf = waitUntilElementDisplayed(headerMenuActions.viewBagButtonFromAddToBagConf);
            //validateSBConfEcom();
            waitUntilElementDisplayed(addToBagNotification, 3);
            // boolean isViewBagConf = waitUntilElementDisplayed(headerMenuActions.viewBagButtonFromAddToBagConf, 20);
            if(isDisplayed(conf_ButtonClose)){click(conf_ButtonClose);}
            staticWait(5000);
            waitUntilElementDisplayed(headerMenuActions.shoppingBagIcon, 4);
            String getBagCountAfter = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
            int bagCountAfterAddToBag = Integer.parseInt(getBagCountAfter);
            System.out.println("Bag count after the prod added to bag " + getBagCountAfter + " before " + bagCountBeforeAddToBag);

            boolean isProductAddedToBag = (bagCountBeforeAddToBag + 1) == bagCountAfterAddToBag;


            return viewBagConf;
        } else {
            return false;
        }
    }

    public boolean validateSBConfEcom() {
        waitUntilElementDisplayed(addToBagNotification, 1);
        if (isDisplayed(conf_ViewBag) && isDisplayed(conf_ContinueCheckout) && isDisplayed(conf_Checkout)
                && isDisplayed(paypalSbModal)) {
            return true;
        } else {
            addStepDescription("Buttons are not displayed in the SB confirmation modal for Ecom");
            return false;
        }
    }

    public boolean selectFromAvailableListOfTwoStores(HeaderMenuActions headerMenuActions, int bagCountBeforeAddToBag) {

        int availStoreCount = getAvailTwoStrsCnt();
        staticWait(5000);
        if (availStoreCount != 0) {
            click(addBopisToBagButtons.get(1));
            boolean viewBagConf = waitUntilElementDisplayed(headerMenuActions.shoppingBagIcon);
            staticWait(5000);
            String getBagCountAfter = getText(headerMenuActions.bagLink).replaceAll("[^0-9.]", "");
            int bagCountAfterAddToBag = Integer.parseInt(getBagCountAfter);
            System.out.println("Bag count after the prod added to bag " + getBagCountAfter + " before " + bagCountBeforeAddToBag);
            staticWait(2000);

            boolean isProductAddedToBag = (bagCountBeforeAddToBag + 1) == bagCountAfterAddToBag;


            return isProductAddedToBag || viewBagConf;
        } else {
            return false;
        }
    }

    public boolean changeAvailableListOfTwoStores(int position) {

        int sizes = sizeDropDownOptions().size();
        for (int i = 0; i < sizes; i++) {
            if (sizes == 1)
                selectDropDownByIndex(sizeDropDown, i);
            else
                selectDropDownByIndex(sizeDropDown, i);

            click(checkAvailability);
            if (getAvailTwoStrsCnt() >= 1) {
                break;
            }
        }
//        staticWait(5000);
//        selectDropDownByIndex(sizeDropDown, 2);
//        staticWait(1000);
        staticWait(4000);
        try {
            WebElement selectStore = selectStoreByPosition(position);
            click(selectStore);
        }catch(Exception e){
            addStepDescriptionWithScreenshot("Stores are not displaying by size index ");
        }

        staticWait(8000);
        try {
            return verifyElementNotDisplayed(closeOverlay, 30);
        } catch (Throwable t) {
            return true;

        }
    }


    public boolean clickSelectStoreBtnByPosition(int position) {
        int availStoresCount = getAvailStrsCntAndClickAvailStoresChkBox();
        if (availStoresCount != 0) {
            WebElement selectStore = selectStoreByPosition(position);
            click(selectStore);
            staticWait(8000);
            if(isDisplayed(conf_ButtonClose)) {
                click(conf_ButtonClose);
            }
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
                    click(selectStore);
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
        if (waitUntilElementDisplayed(showMoreDetailsLink, 20)) {
            click(showMoreDetailsLink);
            return verifyElementNotDisplayed(showMoreDetailsLink, 30);
        }
        return false;
    }

    public boolean clickShowMoreLinkAndVerifyStores() {
        int storesCountBforeClick = availableStoreNames.size();
        if (!clickShowMoreDetailsLink()) {
            return false;
        } else {
            int storesCountAfterClick = getElementsSize(availableStoreNames);
            return storesCountAfterClick > storesCountBforeClick;
        }
    }

    public boolean clickSelectStoreBtnByAvailableStores() {
        int availStoresCount = getAvailStrsCntAndClickAvailStoresChkBox();
        boolean isBopisOverlayNotPre = false, invUnAvaServErr = false;
        if (availStoresCount != 0) {
            for (int i = 1; i <= availStoresCount; i++) {
                checkAddToBagDisableValidation();
                WebElement selectStore = selectStoreByPosition(i);
                click(selectStore);
                staticWait(8000);
                if(isDisplayed((conf_ButtonClose))){
                    click(conf_ButtonClose);
                }
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

    public boolean selectAvailableStoreButton() {
        if (availableStoreBtn != null) {
            int count = 0;
            int totalSize = availableStoreBtn.size();
            isStoreAvailable = false;
            if (totalSize == 1) {
                storeButton = availableStoreBtn.get(0);
                scrollDownUntilElementDisplayed(storeButton);
                click(storeButton);
                isStoreAvailable = true;
            } else if (count == 0 && totalSize > 1) {
                storeButton = availableStoreBtn.get(randInt(0, (totalSize - 1)));
                scrollDownUntilElementDisplayed(storeButton);
                click(storeButton);
                isStoreAvailable = true;
            } else {
                storeButton = availableStoreBtn.get(count);
                scrollDownUntilElementDisplayed(storeButton);
                click(storeButton);
                isStoreAvailable = true;
            }
            count++;
        } else {
            isStoreAvailable = false;
        }
        return isStoreAvailable;
    }

    public boolean closeBopisOverlayModal() {
        waitUntilElementDisplayed(closeOverlay, 20);
        click(closeOverlay);
        return verifyElementNotDisplayed(closeOverlay);
    }

    public boolean selectSizeByValueAndSelectStore(String zipCode, String value) {

        selectDropDownByValue(sizeDropDown, value);
        clear(zipCodeField);
        enterZipAndSearchStore(zipCode);
        if (availStoresCount() >= 1) {
            return true;
        }
        addStepDescription("0 stores displaying");
        return false;


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

    public boolean changeTwoStoreBopis() {
        selectDropDownByIndex(sizeDropDown, 2);
        {
            click(checkAvailability);
            staticWait(1000);
            if (isAlertPresent()) {
                acceptAlert();
            }
            return waitUntilElementDisplayed(availableText, 30);
        }
    }

    public boolean enterZipAndCheckAvailability() {
        disableImageZoom();
        waitUntilElementDisplayed(checkAvailability, 20);
        click(checkAvailability);
        staticWait(1000);
        if (isAlertPresent()) {
            acceptAlert();
        }
        boolean isAvailTxt = waitUntilElementDisplayed(availableText, 30);
        if (!isAvailTxt) {
            closeBopisOverlayModal();
        }
        return waitUntilElementDisplayed(availableText, 30);

    }

    public boolean selectSizeAndEnterZipAndSearchStore(String zipCode) {

        waitUntilElementDisplayed(bopisSize, 20);
        //click(bopisSize);
        // selectDropDownByIndex(bopisAvailableSize.get(2),2);
        staticWait();
        clearAndFillText(zipCodeField, zipCode);
        staticWait();
        click(searchButton);
        staticWait(1000);
        if (isAlertPresent()) {
            acceptAlert();
        }
        boolean isAvailTxt = waitUntilElementDisplayed(availableStrCheckBox, 30);
        if (!isAvailTxt) {
            click(searchButton);
        }
        return waitUntilElementDisplayed(availableStrCheckBox, 30);

    }

    public boolean verifyProdAttributes() {

        return isDisplayed(itemImage) && isDisplayed(prodName) && isDisplayed(bopisSize) &&
                isDisplayed(bopisColor) && isDisplayed(bopisQuantity) && isDisplayed(itemPrice);
    }

    public boolean verifyInStoreAvailAndMsgCenterAligned() {
        return getTextAlignedValue(inStoreAvailTxt).toString().equalsIgnoreCase("center") &&
                getTextAlignedValue(messageUnderInStoreTxt).toString().equalsIgnoreCase("center");
    }

    public boolean verifyPriceMatchedWithPDP(double salePrPDP, double origPrPDP) {
        double orgPr = 0.00, salePr = 0.00;
        try {
            orgPr = Double.valueOf(getText(originalPrice).replaceAll("[^0-9.]", ""));
        } catch (NullPointerException o) {
            orgPr = 0.00;
        }
        try {
            salePr = Double.valueOf(getText(salePrice).replaceAll("[^0-9.]", ""));
        } catch (NullPointerException s) {
            salePr = 0.00;
        }
        if (origPrPDP == orgPr && salePrPDP == salePr) {
            return true;
        } else {
            return false;
        }

    }

    public boolean clickFitDropDownAndVerify(String fitValue) {
        int fitCount = bopisFitValues.size();
        return fitCount == 1 && getText(bopisFitByPos(1)).equalsIgnoreCase(fitValue);
    }

    /**
     * Created By Pooja on 11th May,2018
     * This Method stores the applied size filter value and clicks on Pick in Store icon on Product Tile and verifies the applied size in filter
     * is the default selected size in the Bopis Modal
     */

    public boolean verifyFilteredSizeDefaultSelected(SearchResultsPageActions searchResultsPageActions, BopisOverlayActions bopisOverlayActions, CategoryDetailsPageAction categoryDetailsPageAction) {
        String appliedFilter = getText(categoryDetailsPageAction.appliedSizeFilterValue);
        searchResultsPageActions.clickPickUpInStoreByPos(categoryDetailsPageAction, bopisOverlayActions, 0);
        String selectedValueInBopisModal = getText(defaultSizeInBopis);
        addStepDescription("Applied Filter =" + appliedFilter + " and Default Selected Filter is=" + selectedValueInBopisModal);
        waitUntilElementClickable(bopisOverlayActions.closeOverlay, 10);
        click(bopisOverlayActions.closeOverlay);
        return appliedFilter.equals(selectedValueInBopisModal) && verifyElementNotDisplayed(bopisOverlayActions.closeOverlay);
    }

    /**
     * Created By Pooja on 11th May,2018
     * This Method stores the applied color filter value and clicks on Pick in Store icon on Product Tile and verifies the applied color in filter
     * is the default selected color in the Bopis Modal
     */
    public boolean verifyWhiteColorDefaultSelected(SearchResultsPageActions searchResultsPageActions, BopisOverlayActions bopisOverlayActions, CategoryDetailsPageAction categoryDetailsPageAction) {
        String appliedFilter = getText(categoryDetailsPageAction.appliedColorFilterValue);
        if (appliedFilter.equalsIgnoreCase("White")) {
            List<String> colors = Arrays.asList("SNOW", "WHITE", "SIMPLYWHT");
            searchResultsPageActions.clickPickUpInStoreByPos(categoryDetailsPageAction, bopisOverlayActions, 0);
            String selectedValueInBopisModal = getText(defaultColorInBopis);
            click(bopisOverlayActions.closeOverlay);
            addStepDescription("Applied Filter =" + appliedFilter + " and Default Selected Filter is=" + selectedValueInBopisModal);
            return colors.contains(selectedValueInBopisModal);
        }
        addStepDescription("Applied Filter =" + appliedFilter + " is not White");

        return false;

    }

    public boolean checkAddToBagDisableValidation() {
        if (waitUntilElementsAreDisplayed(addToBagDisabledForUnavailable, 3)) {
            return true;
        } else {
            addStepDescription("The add to bag button is not disabled for Unavailable items");
            return false;
        }
    }

    public boolean editInTwoStoreModal(String qty){
        selectDropDownByValue(qtyDrpDown, qty);
        click(checkAvailability);
        staticWait(3000);
        int availStoreCount = getAvailTwoStrsCnt();
        staticWait(5000);
        if(availStoreCount != 0){
                click(addBopisToBagButtons.get(0));
                staticWait(9000);
                return !waitUntilElementDisplayed(closeOverlay,10);
            }
        else{
            addStepDescription("Required qty of items are not available in the 2 store");
            return false;
        }
    }

    public boolean selectedSizeDisplayFromPDP(String size, String qty){
        waitUntilElementDisplayed(zipCodeField,2);
        String selectedSize = getText(sizeSelectedInBOPISModal);
        String quntitySelected= getText(qtySelectedInBOPISModal);
        if(selectedSize.equalsIgnoreCase(size) && quntitySelected.equalsIgnoreCase(qty)){
            return true;
        }
        else{
            addStepDescription("Size and Qty Selected in PDP is not carried inside the BOPIS modal");
            return false;
        }
    }

    public boolean checkTheDistance(){
        waitUntilElementDisplayed(distanceField,3);
        String defaultValue = getText(defaultDistanceSelected);
        if(defaultValue.equalsIgnoreCase("25 miles")) {
            return getAllOptionsText(distanceField).contains("50 miles") &&
                    getAllOptionsText(distanceField).contains("75 miles");
        }
        else{
            addStepDescription("Check the values displayed in the distance dropdown");
            return false;
        }
    }
    public boolean deselectShowAvailableStore(){
        waitUntilElementDisplayed(availableStrCheckBox);
        click(availableStrCheckBox);
        if(!waitUntilElementsAreDisplayed(addToBagDisabledForUnavailable,3)) {
            click(availableStrCheckBox);
            verifyElementNotDisplayed(addToBagDisabledForUnavailable.get(0),3);
            return true;
        }
        else{
            addStepDescription("Store are not displayed after deselection");
            return false;
        }
    }
    public boolean changeTheDistanceValidation(){
        waitUntilElementDisplayed(distanceField,3);
        selectDropDownByValue(distanceField,"50");
        if(verifyElementNotDisplayed(availableStrCheckBox)){
        return true;}
        else{
            addStepDescription("After changing the distance, user is not asked to search again");
            return false;
        }
    }

    public boolean checkTwoStoreModalUI(){
        if(isDisplayed(prodName) && isDisplayed(checkAvailability) && isDisplayed(qtyDrpDown)
            && isDisplayed(itemPrice) && isDisplayed(sizeDropDown)){
            return true;
        }
        else{
            addStepDescription("Two store modal is not displayed properly");
            return false;
        }
    }

    public boolean checkTheStoreDetails(){

        if(waitUntilElementsAreDisplayed(store_Address,2) && waitUntilElementsAreDisplayed(store_PhoneNumber,3)
         && waitUntilElementsAreDisplayed(store_Timing,3)){
            return true;
        }
        else{
            addStepDescription("Check the store details displayed in the BOPIS modal");
            return false;
        }
    }
    public boolean checkTheRecentStoreDisplay(String favStore){
        waitUntilElementsAreDisplayed(addBopisToBagButtons,2);
        String storeName = getText(favStoreSelectedFirst);

        if(storeName.equalsIgnoreCase(favStore)){
            return true;
        }
        else{
            addStepDescription("The recently added store is not displayed as Fav store inside 2 store modal");
            return false;
        }

    }
    public boolean checkItemNAmeDisplayedInBOPISModal(String name){
        waitUntilElementDisplayed(itemName);
        String item = getText(itemName);
        if(item.equalsIgnoreCase(name)){
            return true;
        }
        else{
            addStepDescription("Item name is not same as that in PDP");
            return false;
        }
    }
    // Need to work on it
    //public boolean getResponseOfDistance(){
       //String response = getValueOfDataElement("response_getStoreAndProductInventoryInfo");

    public boolean checkPhoneNumFormat() {
        String phoneDigits[];
        waitUntilElementsAreDisplayed(store_PhoneNumber, 20);
        String phoneNo = getText(store_PhoneNumber.get(0));
        phoneDigits = phoneNo.split("-");
        return phoneDigits[0].length() == 3 && phoneDigits[1].length() == 3 && phoneDigits[2].length() == 4;
    }
    public boolean checkStoreNameIsBold(){
        waitUntilElementsAreDisplayed(store_name,2);
        String name = store_name.get(0).getCssValue("font-weight");
        if(name.equalsIgnoreCase("500") || name.equalsIgnoreCase("bold")){
            return true;
        }
        else{
            addStepDescription("The store name is not bold");
            return false;
        }
    }
    public boolean validateSpecialCharZipFldErrMsg(String zip, String errr) {
        staticWait(100);
        clearAndFillText(zipCodeField,zip);
        click(searchButton);
        String errorMessage = "";
        try {
            errorMessage = getText(bOPISInlineError);
        }catch (NullPointerException n){

        }
        if(errorMessage.contains(errr)){
            return true;
        }
        else{
            addStepDescription("Check the Error message copy");
            return false;
        }
    }
    public boolean validateInvalidZipFldErrMsg(String zip,String errr) {
        staticWait(100);
        clearAndFillText(zipCodeField,zip);
        click(searchButton);
        String errorMessage = getText(pageLevelError);
        if(errorMessage.contains(errr)){
            return true;
        }
        else{
            addStepDescription("Check the Error message copy");
            return false;
        }
    }
    public boolean checkDefaultQuantityAndSize(String quantity, String size){
        waitUntilElementDisplayed(selectedQuantity);
        String qty = getText(selectedQuantity);
        String sizeDefault = getText(selectedSizeByDefault);
        if(qty.equalsIgnoreCase(quantity) && sizeDefault.equalsIgnoreCase(size)){
            return true;
        }
        else{
            addStepDescription("The quantity selected is not proper");
            return false;
        }
    }
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
    public boolean sizeSelectionError(String error,String zipcode){
        enterZipAndSearchStore(zipcode);
        waitUntilElementDisplayed(bOPISInlineError);
        String sizeErr = getText(bOPISInlineError);
        if(sizeErr.contains(error)){
            return true;
        }
        else{
            addStepDescription("check the error message copy");
            return false;
        }
    }
    public int changeColorFOrBOPIS(){
        waitUntilElementsAreDisplayed(colorSwatch,2);
        if(colorsListInDropDOwn.size()>1){
            click(colorsListInDropDOwn.get(randInt(0,colorsListInDropDOwn.size()-1)));
        }
        else{
            addStepDescription("Only 1 color is available");
        }
        return colorsListInDropDOwn.size();
    }

    public String getImgSrc() {
        waitUntilElementDisplayed(itemImage);
        return getAttributeValue(itemImage, "src");
    }

    public boolean validateFitSection() {

        return waitUntilElementsAreDisplayed(bopisFitValues, 3);
    }
    public boolean verifyBadgeInBopisOverlay() {
        if(isDisplayed(productBadge)){
            addStepDescription("Badges are displayed in BOPIS modal");
            return false;
        }
        else{
            return true;
        }
    }


}
