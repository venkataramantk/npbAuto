package tests.web.initializer;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.pages.actions.MMyAccountPageActions;

import javax.xml.bind.JAXBException;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JKotha on 30/10/2017.
 */
public class MobileBaseTest extends PageInitializer {
    protected static final String storeXml = "store", usersXml = "users";
    public ExcelReader e2eExcelReader = new ExcelReader(Config.getDataFile(Config.E2E_TESTING_DATAFILE));
    public ExcelReader ropisExcelReader = new ExcelReader(Config.getDataFile(Config.PHASE2_DETAILS));
    ExcelReader phase2DetailsExcelReader = new ExcelReader(Config.getDataFile(Config.PHASE2_DETAILS));
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));

    protected static final String dataProviderName = "StoreAndUsers";
    protected static final String WIC = "wic", USONLY = "usonly", REGISTEREDONLY = "registeredonly", GUESTONLY = "guestonly",
            GIFTCARDPRODUCT = "giftcardproduct", PROD_REGRESSION = "prodregression", SMOKE = "smoke", CAONLY = "caonly", PDP = "pdp", PLP = "plp",
            MYACCOUNT = "myaccount", GLOBALCOMPONENT = "globalcomponent", CHECKOUT = "checkout", PAYPAL = "paypal", GIFTCARD = "giftcard", PRODUCTION = "production", PLCC = "plcc", FDMS = "fdms", REGRESSION = "regression", BOPIS = "bopis", COUPONS = "coupons", CHEETAH = "cheetah", CREATEACCOUNT = "createaccount",
            BARRACUDA = "barracuda", SHOPPINGBAG = "shoppingbag", EAGLE = "eagle", DRAGONFLY = "dragonfly", SEARCH = "search", REMEMBERUSER = "remembereduser", INTCHECKOUT = "intcheckout", RTPS = "rtps", KOMODO = "komodo", OUTFITS = "outfits", RECOMMENDATIONS = "recommendations",
            IMPALA = "impala", JAGUAR = "jaguar", RECAPTCHA = "recaptcha", NARWHAL = "narwhal", KILLSWITCH = "killswitch", PANTHER = "panther", ORDERSMS = "ordersms", RHINO = "rhino";
    public String rowInExcelUS = "CreateAccountUS", rowInExcelCA = "CreateAccountCA";

    public String orderNumber;
    public boolean createAccFlag = false;
    public Map<String,String> createUserDetailsUS = dt2ExcelReader.getExcelData("CreateAccount","CreateAccountUS");
    public Map<String,String> createUserDetailsCA = dt2ExcelReader.getExcelData("CreateAccount","CreateAccountCA");

    @DataProvider(name = dataProviderName)
    public static Object[][] getUsersAndStore(ITestContext context) {
        String store = context.getCurrentXmlTest().getLocalParameters().get(storeXml);
        String user = context.getCurrentXmlTest().getLocalParameters().get(usersXml);

        List<String> users = Arrays.asList(user.split(","));
        Object[][] storeWithUser = new Object[users.size()][1];

        for (int i = 0; i < users.size(); i++) {
            String userAs = users.get(i).trim();
            storeWithUser[i] = new Object[]{store, userAs};
        }
        return storeWithUser;
        //   return new Object[][]{{"US", "guest"}};
    }

    /*Use this data provider in case you want to unit test your
    test method. Change the data provider name to "StoreAndUsersSingleTest"
    as @Test(dataProvider = "StoreAndUsersForSingleTest", priority = 3, groups = {"plp"}.
    To run a test for single parameter remove or comment all the other parameters*/
    @DataProvider(name = "StoreAndUsersForSingleTest")
    public static Object[][] getStoreAndUserForSingleTest() {
        return new Object[][]{{"US", "registered"}/* {"US", "registered"},
                {"CA", "guest"}, {"CA", "registered"}*/};
    }

    public String domUserName() {
        return Config.getDomUsername();
    }

    public String domPassword() {
        return Config.getDomPassword();
    }

    public void validateRecommendations() {
        AssertFailAndContinue(mRecommendationsActions.verifyAllicons(), "Verify Next Button, Previous Button, Favorites icon and Add to Bag button");
        AssertFailAndContinue(mRecommendationsActions.waitUntilElementDisplayed(mRecommendationsActions.wasPrice, 10), "Verify 'WAS' text is displayed");
        /*mRecommendationsActions.click(mRecommendationsActions.addToBagBtn);
        AssertFailAndContinue(mRecommendationsActions.waitUntilElementDisplayed(mRecommendationsActions.colorSelection, 10), "Verify 'Color selection' is displayed in the flip view");
        AssertFailAndContinue(mRecommendationsActions.waitUntilElementDisplayed(mRecommendationsActions.sizeSelection, 10), "Verify 'Size selection' is displayed in the flip view");
        AssertFailAndContinue(mRecommendationsActions.waitUntilElementDisplayed(mRecommendationsActions.qtyDropDown, 10), "Verify 'Quantity Dropdown' is displayed in the flip view");
        AssertFailAndContinue(mRecommendationsActions.waitUntilElementDisplayed(mRecommendationsActions.viewProductDetailsLink, 10), "Verify 'View Product Details Link' text is displayed");
        mRecommendationsActions.click(mRecommendationsActions.closeIcon);*/
        //DT-39701
        AssertFailAndContinue(mRecommendationsActions.isDisplayed(mRecommendationsActions.onlyOneProductVisible), "Verify only one product is visible");
        mRecommendationsActions.javaScriptClick(driver, mRecommendationsActions.btnNext);
        System.out.println(mRecommendationsActions.getAttributeValue(mRecommendationsActions.activeDot, "data-index"));
        AssertFailAndContinue(mRecommendationsActions.getAttributeValue(mRecommendationsActions.activeDot, "data-index").equals("1"), "Verify second item is displayed after clicking on next navigation");
        mRecommendationsActions.javaScriptClick(driver, mRecommendationsActions.btnPrevious);
        AssertFailAndContinue(mRecommendationsActions.getAttributeValue(mRecommendationsActions.activeDot, "data-index").equals("0"), "Verify first item is displayed after clicking on previous navigation");
        mRecommendationsActions.click(mRecommendationsActions.favIcon);
        AssertFailAndContinue(mloginDrawerActions.waitUntilElementDisplayed(mloginPageActions.loginButton), "Verify Login page is displayed when clicked on fav icon");
        mloginPageActions.click(mloginPageActions.closeLoginModal);
    }

    public void createAccount(String row, String email) {
        panCakePageActions.navigateToMenu("CREATEACCOUNT");
        createNewAccountAndGetEmailAddr(row, email);
    }

    public void acceptSecurityWarning() {
        try {
            (new Robot()).keyPress(java.awt.event.KeyEvent.VK_ENTER);

            (new Robot()).keyRelease(java.awt.event.KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String searchAnItemAndAddToBag(String upcNum, String updateQty) {
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, upcNum);
//        if(mheaderMenuActions.isAlertPresent()){
//            mheaderMenuActions.acceptAlert();
//        }
        String itemNum = mproductDetailsPageActions.getItemNumber();
        logger.info("The actual Item is: " + itemNum);
        AssertFail(mproductDetailsPageActions.selectASize(), "Selected the size");
        String selectedSize = mproductDetailsPageActions.getSelectedSize();
        logger.info("The selected size is: " + selectedSize);
        mproductDetailsPageActions.updateQty(updateQty);
        AssertFailAndContinue(mproductDetailsPageActions.clickAddToBag(), "The UPC: " + upcNum + " added to bag");
        return selectedSize;
    }

    public Map<String, String> addItemsToBag(Map<String, String> itemsAndQuantity) {
        List<String> itemQty = new ArrayList<>();
        Map<String, String> selectedItemsWithSizes = new HashMap<>();
        if (itemsAndQuantity.size() > 0) {
            for (Map.Entry<String, String> entry : itemsAndQuantity.entrySet()) {
                String item = entry.getKey();
                String quantity = entry.getValue();
                itemQty.add(quantity);
                String selectedSize = searchAnItemAndAddToBag(item, quantity);
                selectedItemsWithSizes.put(item, selectedSize);
//                mshoppingBagOverlayActions.clickContinueShoppingButton();

            }

        }
        return selectedItemsWithSizes;
    }

    public String addItemToBagForBopisProdAndGetZip(Map<String, String> itemsAndQuantity, Map<String, String> upcNumWithZip) {
        List<String> itemQty = new ArrayList<>();
        String zipForSelectedUPC = null;
        if (itemsAndQuantity.size() > 0) {
            for (Map.Entry<String, String> entry : itemsAndQuantity.entrySet()) {
                String item = entry.getKey();
                String quantity = entry.getValue();
                itemQty.add(quantity);
                mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, item);
                if (mproductDetailsPageActions.isPickUpInStoreButtonAvailable()) {
                    mproductDetailsPageActions.selectASize();
                    AssertFailAndContinue(mproductDetailsPageActions.clickAddToBag(), "Added the item to bag");
                    zipForSelectedUPC = getZipCodeForSelectedUPC(upcNumWithZip, item);
                    break;
                }
            }
        }
        return zipForSelectedUPC;
    }

    public boolean addBopisItemToBag(String keyword) {
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
        int totalProducts = mcategoryDetailsPageAction.pickupstoreBtn.size();
        if (totalProducts <= 0) {
            logger.info("No Bopis products found, search with other product name");
            return false;
        } else {
            for (int i = 0; i < totalProducts; i++) {
                mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
                totalProducts = mcategoryDetailsPageAction.pickupstoreBtn.size();
                mcategoryDetailsPageAction.clickBOPISbyPos(i + 1);
                if (mbopisOverlayActions.selectSizeAndSelectStore("newjesy")) {
                    mbopisOverlayActions.clickAddtoBag();
                    break;
                } else {
                    logger.info("No Stores available with the product");
                }
            }
            return mheaderMenuActions.waitUntilElementDisplayed(mheaderMenuActions.paypalFromNotification);
        }
    }

    public String getZipCodeForSelectedUPC(Map<String, String> upcNumWithZip, String selectedUPC) {
        String zipForSelectedUPC = null;
        for (Map.Entry<String, String> upcWithZipEntry : upcNumWithZip.entrySet()) {
            String upcInSheetList = upcWithZipEntry.getKey().trim();
            if (upcInSheetList.equalsIgnoreCase(selectedUPC)) {
                zipForSelectedUPC = upcWithZipEntry.getValue().trim();
                break;
            }
        }
        return zipForSelectedUPC;
    }


//    public Map<String,String> addItemsToBagFromCategory(Map<String,String> qtyToOrderAndQtyToShip) {
//        Map<String, String> upcNumsAndQtyToShip = new HashMap<>();
//        if (qtyToOrderAndQtyToShip.size() > 0) {
//            int i =0;
//            for(Map.Entry<String, String> qtyToOrderAndShip : qtyToOrderAndQtyToShip.entrySet()){
//                String qtyToOrder = qtyToOrderAndShip.getKey();
//                String qtyToShip = qtyToOrderAndShip.getValue();
//                mheaderMenuActions.clickToddlerGirlOutfitCategory(mcategoryDetailsPageAction);
////                AssertFailAndContinue(departmentLandingPageActions.selectRandomCategory(mcategoryDetailsPageAction), "Click on the Random Category");
////                AssertFailAndContinue(mcategoryDetailsPageAction.clickOnRandomSubCategory(), "Click on the Random Category");
//                mcategoryDetailsPageAction.clickProductByPosition(mproductDetailsPageActions, i + 1);
//                String prodNames = mproductDetailsPageActions.getProductName().toLowerCase();
//                mproductDetailsPageActions.selectASize();
//                mproductDetailsPageActions.updateQty(qtyToOrder);
//                AssertFailAndContinue(mproductDetailsPageActions.clickAddToBag(mshoppingBagOverlayActions), "The Product Name: " + prodNames + " added to bag");
//                String upcNum = mshoppingBagOverlayActions.getUPCNumclickSecondImageByItemDescription(prodNames);
//                mshoppingBagOverlayActions.clickContinueShoppingButton();
//                upcNumsAndQtyToShip.put(upcNum, qtyToShip);
//                i++;
//            }
//
//        }
//        return upcNumsAndQtyToShip;
//    }

    public Map<String, String> getUpcNumAndQtyToCancel(Map<String, String> upcNumsAndQtyToShip, List<String> qtyToCancel) {
        Map<String, String> upcNumsAndQtyToCancel = new HashMap<>();
        if (upcNumsAndQtyToShip.size() > 0) {
            int i = 0;
            for (Map.Entry<String, String> qtyToOrderAndShip : upcNumsAndQtyToShip.entrySet()) {
                String upcNum = qtyToOrderAndShip.getKey();
                String cancelQty = qtyToCancel.get(i);
                if (!cancelQty.equalsIgnoreCase("0")) {
                    if (!cancelQty.equals(null) && !cancelQty.isEmpty()) {
                        upcNumsAndQtyToCancel.put(upcNum, cancelQty);
                    }
                }
                i++;
            }

        }
        return upcNumsAndQtyToCancel;
    }

    public String getRopisTestingCellValueBySheetRowAndColumn(String sheetName, String rowName, String columnName) throws Exception {
        Map<String, String> recordByRowName = ropisExcelReader.getExcelData(sheetName, rowName);
        String cellValue = recordByRowName.get(columnName);
        return cellValue;
    }

    public String getDT2TestingCellValueBySheetRowAndColumn(String sheetName, String rowName, String columnName) throws Exception {
        Map<String, String> recordByRowName = dt2ExcelReader.getExcelData(sheetName, rowName);
        String cellValue = recordByRowName.get(columnName);
        return cellValue;
    }

    public void navigateToChromeBrowserAndInitializePages() {
        if (!Config.getBrowserType().equalsIgnoreCase("chrome")) {
            navigateToChromeDriver();
            driver = getDriver();
            initializePages(driver);
        }

    }

    public void navigateToChromeBrowserWithEventDriEnabledAndInitializePages() {
        if (!Config.getBrowserType().equalsIgnoreCase("chrome")) {
            navigateToChromeDriverWithEventDriverEnabled();
            driver = getDriver();
            initializePages(driver);
        }

    }

    public void navigateToFFBrowserWithEventDriEnabledAndInitializePages() {
        if (!Config.getBrowserType().equalsIgnoreCase("firefox")) {
            navigateToFFDriverWithEventDriverEnabled();
            driver = getDriver();
            initializePages(driver);
        }

    }


    public String getmobileDT2CellValueBySheetRowAndColumn(String sheetName, String rowName, String columnName) throws Exception {
        Map<String, String> recordByRowName = dt2MobileExcel.getExcelData(sheetName, rowName);
        String cellValue = recordByRowName.get(columnName);
        return cellValue;
    }


    public Map<String, String> getMapOfItemNamesAndQuantityWithCommaDelimited(String itemNames, String quantity) {
        Map<String, String> itemsAndQuantity = new HashMap<>();
        itemsAndQuantity.clear();
        if (itemNames.contains(",")) {
            String[] itemsSet = itemNames.split(",");
            if (quantity.contains(",")) {
                String[] quantitySet = quantity.split(",");
                for (int i = 0; i < itemsSet.length; i++) {
                    itemsAndQuantity.put(itemsSet[i].trim(), quantitySet[i].trim());
                }
            }
        } else {
            itemsAndQuantity.clear();
            itemsAndQuantity.put(itemNames.trim(), quantity.trim());
        }
        return itemsAndQuantity;
    }

    public List<String> convertCommaSeparatedStringToList(String items) {
        List<String> tokensList = new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(items, ",");
        while (tokens.hasMoreTokens()) {
            String nextToken = tokens.nextToken();
            tokensList.add(nextToken.trim());
            logger.info("The token added to list: " + nextToken);
        }
        return tokensList;
    }

    /**
     * clear all the items from shopping bag
     */
    public void emptyShoppingBag() {
        mheaderMenuActions.waitUntilElementDisplayed(mheaderMenuActions.shoppingBagIcon);
        mheaderMenuActions.click(mheaderMenuActions.shoppingBagIcon);
        mheaderMenuActions.staticWait(5000);
        int qtyInBag = Integer.parseInt(mheaderMenuActions.getText(mheaderMenuActions.shoppingBagIcon));
        if (qtyInBag > 0) {
            int noOfItems = mshoppingBagPageActions.itemDescElements.size();
            for (int i = 1; i <= noOfItems; i++) {
                mshoppingBagPageActions.clickRemoveLinkByPosition(i, mheaderMenuActions);
            }
        }
    }

    public boolean isPromoNotDisplaying() {
        if (homePageActions.waitUntilElementDisplayed(homePageActions.closeLinkOnPromo, 10)) {
            homePageActions.click(homePageActions.closeLinkOnPromo);
            logger.info("The promo is displaying....clicked on close");
            try {
                return homePageActions.verifyElementNotDisplayed(homePageActions.closeLinkOnPromo, 20);
            } catch (Throwable invEx) {
                return true;
            }
        }
        return true;

    }

    public String getAndVerifyOrderNumber(String testName) {
        orderNumber = mreceiptThankYouPageActions.getOrderNum();
        logger.info("The order Number of :" + testName + " is: " + orderNumber);
        AssertFailAndContinue(orderNumber != null && !orderNumber.isEmpty(), testName + " Landing on the Order confirmation page, obtained the Order Number verification: " + orderNumber);
        return orderNumber;
    }

    public Map<String, String> getCancelItemIDWithSingleQty(Map<String, String> orderedItemIdAndQty) {
        Map<String, String> itemIDAndQtyToCancel = new HashMap<>();
        for (Map.Entry<String, String> itemNameAndQty : orderedItemIdAndQty.entrySet()) {
            String itemID = itemNameAndQty.getKey();
            itemIDAndQtyToCancel.put(itemID, "1");

        }
        return itemIDAndQtyToCancel;
    }

    public List<String> addToBagFromPDP(Map<String, String> keywordsAndQty) {
        List<String> upcNums = new ArrayList<>();
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, keyword);
            String prodName = mproductDetailsPageActions.getProductName().toLowerCase();
            if (mproductDetailsPageActions.isItemOutOfStock()) {
                logger.info("The item " + prodName + " is out of stock. Continuing with other product.");
            } else {
                mproductDetailsPageActions.selectFitAndSize();
                mproductDetailsPageActions.selectDropDownByVisibleText(mproductDetailsPageActions.qtyDropDown, qty);

                try {
                    mproductDetailsPageActions.clickAddToBag();
                    mheaderMenuActions.clickShoppingBagIcon();
                    break;
                } catch (Throwable t) {
                    logger.info("Something happened while adding the item to bag: " + t.getMessage());
                    if (mheaderMenuActions.isAlertPresent()) {
                        mheaderMenuActions.acceptAlert();
                    }
                }
            }
        }
        return upcNums;
    }

    /**
     * Updated by Richa Priya
     */
    public void addToFavoritesBySearching(Map<String, String> keywordsAndQty) {
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
            int totalProducts = mcategoryDetailsPageAction.productTitles.size();
            for (int i = 0; i < totalProducts; i++) {
                try {
                    mcategoryDetailsPageAction.openProdCardViewByPos(i + 1);
                    if (!mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.outofStock, 5)) {
                        mcategoryDetailsPageAction.click(mcategoryDetailsPageAction.closeLink);
                        msearchResultsPageActions.mouseHover(msearchResultsPageActions.productImageByPosition(i + 1));
                        msearchResultsPageActions.click(msearchResultsPageActions.addToFavIconByPos(i + 1));
                        msearchResultsPageActions.waitUntilElementsAreDisplayed(mcategoryDetailsPageAction.favIconEnabled, 20);
                        break;
                    }
                } catch (Throwable t) {
                    AddInfoStep("Something happened while searching an item ");
                }
            }
        }
    }

    /**
     * Created by Richak
     * This will add the product to favorites from PDP
     */
    public void addToFavoritesFromPDP(Map<String, String> keywordsAndQty) {
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            String keyword = keywordsQty.getKey();
            mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, keyword);
            boolean itemCountIncreased= mproductDetailsPageActions.moveProdToWLFromPDP();
            AssertFailAndContinue(itemCountIncreased, "User is able to view the Count got increased by one NOT by the Quantity count of product.");
        }
    }

    public List<String> addToBagBySearching(Map<String, String> keywordsAndQty) {
        List<String> upcNums = new ArrayList<>();
        String upcNum = "1";
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {

            int k = 0;
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
            int totalProducts = mcategoryDetailsPageAction.productTitles.size();
            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                try {
                    mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
                    mcategoryDetailsPageAction.verifyElementNotDisplayed(mcategoryDetailsPageAction.spinner);
                    totalProducts = mcategoryDetailsPageAction.productTitles.size();

                    mcategoryDetailsPageAction.clickProductByPosition(i + 1);
                    String prodName = mproductDetailsPageActions.getProductName().toLowerCase();
                    //if (mproductDetailsPageActions.isItemOutOfStock()) {
                    //logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                    //isProdAvailable = false;
                    //} else {
                    //isProdAvailable = true;
                    mproductDetailsPageActions.selectFitAndSize();
                    mproductDetailsPageActions.selectDropDownByVisibleText(mproductDetailsPageActions.qtyDropDown, qty);

                    try {
                        boolean isItemAddedToBag = mproductDetailsPageActions.clickAddToBag();
                        if (isItemAddedToBag) {
                            k++;
                            mheaderMenuActions.clickShoppingBagIcon();
                            upcNum = mshoppingBagPageActions.getUPCNumByPosition(k);
                            upcNums.add(upcNum);
                            logger.info("Found the available product " + (k) + " " + upcNum);
                            AddInfoStep("Added Product To Bag " + upcNum + " with qty " + qty);
                            break;
                        } else {
                            AddInfoStep("The Item is not able to add to the bag");
                        }
                    } catch (Throwable t) {
                        logger.info("Something happened while adding the item to bag: " + t.getMessage());
                        if (mheaderMenuActions.isAlertPresent()) {
                            mheaderMenuActions.acceptAlert();
                        }
                        //}
                    }
                } catch (Throwable t) {
                    AddInfoStep("Something happened while searching an  item ");
                }
            }
            //AssertFailAndContinue(isProdAvailable, "The product " + upcNum + "is available");
        }
        mproductDetailsPageActions.mouseHover(mheaderMenuActions.TCPLogo);
        return upcNums;
    }

    public boolean addBadgeProductToBag(Map<String, String> keywordsAndQty, String badgeName) {
        List<String> upcNums = new ArrayList<>();
        String upcNum = "1";
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {

            int k = 0;
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
            if (badgeName.equalsIgnoreCase("top rated"))
                msearchResultsPageActions.sortByOptionPlp("TOP_RATED");

            int totalProducts = mcategoryDetailsPageAction.badgeProducts(badgeName).size();
            boolean isProductOOS = false;
            for (int i = 0; i < totalProducts; i++) {
                try {
                    mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
                    mcategoryDetailsPageAction.verifyElementNotDisplayed(mcategoryDetailsPageAction.spinner);
                    if (badgeName.equalsIgnoreCase("top rated"))
                        msearchResultsPageActions.sortByOptionPlp("TOP_RATED");
                    totalProducts = mcategoryDetailsPageAction.badgeProducts(badgeName).size();

                    mcategoryDetailsPageAction.javaScriptClick(driver, mcategoryDetailsPageAction.badgeProducts(badgeName).get(i));

                    String prodName = mproductDetailsPageActions.getProductName().toLowerCase();
                    if (mproductDetailsPageActions.isItemOutOfStock()) {
                        logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                        isProductOOS = false;
                    } else {
                        isProductOOS = true;
                        mproductDetailsPageActions.selectASize();
                        mproductDetailsPageActions.selectDropDownByVisibleText(mproductDetailsPageActions.qtyDropDown, qty);

                        try {
                            boolean isItemAddedToBag = mproductDetailsPageActions.clickAddToBag();
                            if (isItemAddedToBag) {
                                k++;
                            } else {
                                AddInfoStep("The Item is not able to add to the bag");
                            }

                            mheaderMenuActions.clickShoppingBagIcon();
                            upcNum = mshoppingBagPageActions.getUPCNumByPosition(k);
                            upcNums.add(upcNum);
                            logger.info("Found the available product " + (k) + " " + upcNum);
                            AddInfoStep("Added Product To Bag " + upcNum + " with qty " + qty);
                            break;
                        } catch (Throwable t) {
                            logger.info("Something happened while adding the item to bag: " + t.getMessage());
                            if (mheaderMenuActions.isAlertPresent()) {
                                mheaderMenuActions.acceptAlert();
                            }
                        }
                    }
                } catch (Throwable t) {
                    AddInfoStep("Something happened while searching an  item ");
                }
            }
            AddInfoStep("The product " + upcNum + " is available");
        }
        mproductDetailsPageActions.mouseHover(mheaderMenuActions.TCPLogo);
        return true;
    }

    public boolean addToBagFromFlip(String keyword, String qty) {
        boolean flag = false;
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
        int totalProducts = mcategoryDetailsPageAction.productTitles.size();
        for (int i = 0; i < totalProducts; i++) {
            mcategoryDetailsPageAction.openProdCardViewByPos(i + 1);
            if (!mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.outofStock, 5)) {
                mcategoryDetailsPageAction.selectRandomSizeFromFlip();
                mcategoryDetailsPageAction.selectQtyFromFlip(qty);
                mcategoryDetailsPageAction.click(mcategoryDetailsPageAction.addToBagBtn);
//                if (mcategoryDetailsPageAction.waitUntilElementDisplayed(mheaderMenuActions.paypalFromNotification)) {
                mheaderMenuActions.clickShoppingBagIcon();
                mheaderMenuActions.click(mshoppingBagPageActions.checkoutBtn);
                flag = true;
                break;
//                }
            } else {
                mcategoryDetailsPageAction.click(mcategoryDetailsPageAction.closeLink);
            }
        }
        return flag;
    }

    /**
     * Validate Coupon Section if any coupons available
     *
     * @return
     */
    public boolean validateCouponsSection() {
        if (mshoppingBagPageActions.waitUntilElementDisplayed(mshoppingBagPageActions.couponContainerHeading, 10)) {
            return (mshoppingBagPageActions.waitUntilElementDisplayed(mshoppingBagPageActions.couponContainer, 10));
        } else {
            AddInfoStep("No Coupons available. Available coupon section is in Collapsed");
            return mshoppingBagPageActions.waitUntilElementDisplayed(mshoppingBagPageActions.couponCollapsed, 10);
        }
    }

    public Map<String, String> addToBagBySearching(Map<String, String> keywordsAndQty, Map<String, String> keyWordsAndQtyToShip, int k) {
        Map<String, String> upcNumsAndQtyToShip = new HashMap<>();
        int j = 0;
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            String qtyToShip = keyWordsAndQtyToShip.get(keyword);
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
            int totalProducts = mcategoryDetailsPageAction.productImages.size();
            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
                totalProducts = mcategoryDetailsPageAction.productImages.size();
                mcategoryDetailsPageAction.clickProductByPosition(i + 1);
                String prodName = mproductDetailsPageActions.getProductName().toLowerCase();
                if (mproductDetailsPageActions.isItemOutOfStock()) {
                    logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                    isProdAvailable = false;
                } else {
                    isProdAvailable = true;
                    mproductDetailsPageActions.selectASize();
                    mproductDetailsPageActions.updateQty(qty);
                    try {
                        boolean isItemAddedToBag = mproductDetailsPageActions.clickAddToBag();
                        if (isItemAddedToBag) {
                            k++;
                        } else {
                            AddInfoStep("The Item is not able to add to the bag");
                        }
                        String upcNum = mshoppingBagPageActions.getUPCNumByPosition(k);
                        logger.info("Found the available product " + (k) + " " + upcNum);
                        upcNumsAndQtyToShip.put(upcNum, qtyToShip);
                        AddInfoStep("Added Product To Bag " + upcNum + " with qty " + qty);
                        break;
                    } catch (Throwable t) {
                        logger.info("Something happened while adding the item to bag: " + t.getMessage());
                    }
                }
            }
            AssertFailAndContinue(isProdAvailable, "The product available for product " + j + " and added to bag");
            j++;
        }
        return upcNumsAndQtyToShip;
    }

    /*public String clickCreateNewAcctAndCreateNewAccountByRow(String rowName) {
        mheaderMenuActions.openCreateaccountPage(mcreateAccountActions, sideMenuPageActions);
        return createNewAccountAndGetEmailAddr(rowName);
    }*/

    public String createNewAccountAndGetEmailAddr(String rowName, String email) {
        Map<String, String> na = dt2ExcelReader.getExcelData("CreateAccount", rowName);
        AssertFailAndContinue(mcreateAccountActions.createAnAccount(na.get("FirstName"), na.get("LastName"), email, na.get("Password"), na.get("ZipCode"), na.get("PhoneNumber")), "Created new account " + email);
        return mcreateAccountActions.getEmailAddress();
    }

    public void clickScrollToUpIcon(MMyAccountPageActions accountPageActions) {
        accountPageActions.waitUntilElementDisplayed(accountPageActions.scrollToTop);
        accountPageActions.click(accountPageActions.scrollToTop);
    }


    public String getPhase2DetailsCellValueBySheetRowAndColumn(String sheetName, String rowName, String columnName) {
        Map<String, String> recordByRowName = phase2DetailsExcelReader.getExcelData(sheetName, rowName);
        String cellValue = recordByRowName.get(columnName);
        return cellValue;
    }

    public boolean findPickUpInStoreByUPCAndZip(String searchItem, String zip) throws Exception {
        List<String> contentValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "headerContent", "expectedheaderTextContent"));
        boolean isPickUpStoreAvail = false, isStoresAvailableWithZip = false;

        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, searchItem);
        if (mproductDetailsPageActions.waitUntilElementDisplayed(mproductDetailsPageActions.pickUpStore, 20)) {
            if (mproductDetailsPageActions.isItemOutOfStock()) {
                boolean isProdOutOfStock = mproductDetailsPageActions.clickNextSwatchForAvailInvByPos();
                AssertFailAndContinue(isProdOutOfStock, "The product inventory is available by switching to swatches");
            }

            int bagCountBeforeAddToBag = mheaderMenuActions.getBagCount();
            isPickUpStoreAvail = mproductDetailsPageActions.clickPickUpStoreBtn(mbopisOverlayActions);
            AddInfoStep("Is PickUp Store button Available for the UPC " + searchItem + " - " + isPickUpStoreAvail);
            mbopisOverlayActions.selectSizeAndSelectStore(zip);
            isStoresAvailableWithZip = mbopisOverlayActions.selectFromAvailableStores(mheaderMenuActions, bagCountBeforeAddToBag);
            AddInfoStep("Is Stores Available with this zip " + zip + " is - " + isStoresAvailableWithZip + " for the UPC " + searchItem);
            if (!isStoresAvailableWithZip) {
                mbopisOverlayActions.closeBopisOverlayModal();
                mproductDetailsPageActions.mouseHover(mproductDetailsPageActions.addToBag);
            }
        }
        return isPickUpStoreAvail && isStoresAvailableWithZip;
    }


    public String findBopisAvailStoresBySearchingUPCAndZip(Map<String, String> upcWithZipCodes) throws Exception {
        boolean storeAvailForUPCAndZip = false;
        String zip = "Probably Couldn't find UPC by zip";
        for (Map.Entry<String, String> upcWithZip : upcWithZipCodes.entrySet()) {
            String upcNum = upcWithZip.getKey();
            zip = upcWithZip.getValue();
            storeAvailForUPCAndZip = findPickUpInStoreByUPCAndZip(upcNum, zip);
            if (storeAvailForUPCAndZip) {
                AssertFailAndContinue(storeAvailForUPCAndZip, "The store is available with this upc " + upcNum + " and " + zip);
            }
        }
        if (!storeAvailForUPCAndZip) {
            AssertWarnAndContinueWithTextFont(storeAvailForUPCAndZip, "<b><font color=#8b0000> The stores are not available with the provided test data. Please update the data sheet</font></b>");
        }
        return zip;
    }

    /**
     * Select two stores for a given product from Bopis overlay
     *
     * @param zip to search
     * @return
     * @throws Exception
     */
    public boolean findPickUpInTwoStoreByUPCAndZip(String zip) throws Exception {
        boolean isPickUpStoreAvail = false, isStoresAvailableWithZip = false;

        if (mproductDetailsPageActions.isPickUpInStoreButtonAvailable()) {
            mproductDetailsPageActions.clickPickUpStoreBtn(mbopisOverlayActions);
            mbopisOverlayActions.selectSizeAndSelectMoreThan_1Store(zip);
            isStoresAvailableWithZip = mbopisOverlayActions.clickSelectStoreBtnByPosition(2);

            if (!isStoresAvailableWithZip) {
                mbopisOverlayActions.closeBopisOverlayModal();
                //mproductDetailsPageActions.mouseHover(productDetailsPageActions.addToBag);
                //productDetailsPageActions.staticWait(6000);
            }
        }

        return isPickUpStoreAvail && isStoresAvailableWithZip;
    }


    public void bopispaypalCheckoutFromAddedToBag(String searchItem, String zip) throws Exception {
        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, searchItem);
        if (mproductDetailsPageActions.waitUntilElementDisplayed(mproductDetailsPageActions.pickUpStore, 20)) {
            if (mproductDetailsPageActions.isItemOutOfStock()) {
                boolean isProdOutOfStock = mproductDetailsPageActions.clickNextSwatchForAvailInvByPos();
                AssertFailAndContinue(isProdOutOfStock, "The product inventory is available by switching to swatches");
            }

            mproductDetailsPageActions.clickPickUpStoreBtn(mbopisOverlayActions);
            mbopisOverlayActions.selectSizeAndSelectStore(zip);

            mbopisOverlayActions.getAvailStrsCntAndClickAvailStoresChkBox();
            mbopisOverlayActions.click(mbopisOverlayActions.addBopisToBagButtons.get(0));
            mheaderMenuActions.clickPayPal(mpayPalPageActions);
        }
    }

    /**
     * This method will add a ecomm prod and clicks paypal from added to bag notification
     *
     * @param upc of the product
     * @param qty qty to add
     * @throws Exception
     */
    @Deprecated // DTN-2237
    public void shipToHomepaypalCheckoutFromAddedToBag(String upc, String qty) throws Exception {
        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, upc);
        if (mproductDetailsPageActions.waitUntilElementDisplayed(mproductDetailsPageActions.pickUpStore, 20)) {
            if (mproductDetailsPageActions.isItemOutOfStock()) {
                boolean isProdOutOfStock = mproductDetailsPageActions.clickNextSwatchForAvailInvByPos();
                AssertFailAndContinue(isProdOutOfStock, "The product inventory is available by switching to swatches");
            } else {
                mproductDetailsPageActions.selectASize();
                mproductDetailsPageActions.updateQty(qty);
                mproductDetailsPageActions.addToBagFromPDPAndGetProductInfo();
                mheaderMenuActions.clickPayPal(mpayPalPageActions);
            }
        }
    }


    @BeforeSuite(alwaysRun = true)
    public void deleteOld_TestReports() throws Exception {
        System.out.println("Testing the before suites.....");
        String currentDir = System.getProperty("user.dir");
        try {
            String reportsDir = currentDir + "/Test Reports/Results/";
            if (new File(reportsDir).exists()) {
                delete_OldRunDir(reportsDir);
            } else {
                logger.info("Test Reports folder doesn't exists.");
            }
        } catch (Throwable t) {
            logger.info("For some reason, the reports deletion method threw exception ");
        }
    }

    public Comparator<String> sortResults() {
        final Pattern p = Pattern.compile("[0-9]+");
        Comparator<String> c = new Comparator<String>() {
            @Override
            public int compare(String object1, String object2) {
                Matcher m = p.matcher(object1);
                Integer number1 = null;
                if (!m.find()) {
                    return object1.compareTo(object2);
                } else {
                    Integer number2 = null;
                    number1 = Integer.parseInt(m.group());
                    m = p.matcher(object2);
                    if (!m.find()) {
                        return object1.compareTo(object2);
                    } else {
                        number2 = Integer.parseInt(m.group());
                        int comparison = number1.compareTo(number2);
                        if (comparison != 0) {
                            return comparison;
                        } else {
                            return object1.compareTo(object2);
                        }
                    }
                }
            }
        };
        return c;
    }

    public boolean delete_OldRunDir(String reportsDir) throws IOException {
        File file = new File(reportsDir);
        String[] names = file.list();
        List<String> list = new ArrayList<String>(Arrays.asList(names));
        Comparator<String> c = sortResults();
        Collections.sort(list, c);
        int runCounts = 0;
        boolean isRunFolderDeleted = false;
        for (String run : list) {
            if (new File(reportsDir + run).isDirectory()) {
                if (run.contains("Run")) {
                    runCounts = runCounts + 1;

                }
            }
        }
        System.out.println("The total existing run counts " + runCounts);
        if (runCounts >= 20) {
//            for(int j = 1; j<=runCounts; j++) {
            for (int i = 0; i < list.size(); i++) {
                if (new File(reportsDir + list.get(i)).isDirectory()) {
//                        if (names[i].equalsIgnoreCase("Run_" + j)) {
                    if (runCounts >= 20) {
                        if (new File(reportsDir + list.get(i)).exists()) {
                            FileUtils.deleteDirectory(new File(reportsDir + list.get(i)));
                            isRunFolderDeleted = !new File(reportsDir + list.get(i)).exists();
                            System.out.println("The Oldest run folder " + list.get(i) + " got deleted");
                            if (isRunFolderDeleted) {
                                runCounts--;
                                isRunFolderDeleted = true;
                            }

                        }
                    } else {
                        break;
                    }

//                    }

                }
            }
            return isRunFolderDeleted;

        }
        return isRunFolderDeleted;
    }

    //pick up page methods validate in different pages
    //Review Page and pickUp page
    public boolean validateAlternatePersonChkBox() {
        return mshippingPageActions.waitUntilElementDisplayed(mpickUpPageActions.alternatePersonChkboxTitle) &&
                mshippingPageActions.waitUntilElementDisplayed(mpickUpPageActions.alternatePersonChkboxNote) &&
                mshippingPageActions.waitUntilElementDisplayed(mpickUpPageActions.alternatePersonChkbox);
    }

    public boolean validateErrorMessageForAlternateFields() {
        return mshippingPageActions.waitUntilElementDisplayed(mshippingPageActions.getAlterNatefieldsErrors("First Name")) &&
                mshippingPageActions.waitUntilElementDisplayed(mshippingPageActions.getAlterNatefieldsErrors("Last Name")) &&
                mshippingPageActions.waitUntilElementDisplayed(mshippingPageActions.getAlterNatefieldsErrors("Email Address"));
    }

    public void logout() {
        panCakePageActions.navigateToMenu("USER");
        panCakePageActions.staticWait(10000);
        panCakePageActions.waitUntilElementDisplayed(mmyAccountPageActions.lnk_Logout);
        panCakePageActions.click(mmyAccountPageActions.lnk_Logout);
        panCakePageActions.staticWait(10000);
    }

    /**
     * Created by Richa Priya
     * Get the current window.
     *
     * @param parentWindow pass Parent window
     * @return switch to the desired window you have passed
     */
    public void switchToWindow(String parentWindow) {
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            System.out.println("Current windows" + window);
            if (!window.equals(parentWindow)) {
                logger.info("Switch to window" + window);
                driver.switchTo().window(window);
            }
        }
    }

    /*
     * Created by Richa Priya
     *
     * @param parentWindow     pass Parent window
     *
     * @return switch to the parent window
     * */
    public void switchBackToParentWindow(String parentWindow) {
        logger.info("Switching back to parent window" + parentWindow);
        driver.switchTo().window(parentWindow);

    }

    /*
     * Created by Richa Priya
     * Sort the list and check for equality
     *
     * @param list1     pass list 1
     * @param list2     pass list 2 to compare
     *
     * @return true if both the list are equal
     * */
    public boolean compareTwoList(List<String> list1, List<String> list2) {
        Collections.sort(list1);
        Collections.sort(list2);
        if (list1.equals(list2)) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Created by Richa Priya
     * covert a string into hashmap
     *
     * @param str   pass the string
     *
     * @return map of string type
     * */
    public Map<String, String> getHashMapFromString(String str) {
        Map<String, String> mapReturned = new HashMap<String, String>();
        String[] value = str.replaceAll(",", "=").split("=");

        for (int i = 0; i < value.length; i = i + 2) {
            mapReturned.put(value[i].trim(), value[i + 1].trim());
        }
        return mapReturned;
    }

    /**
     * Created by Pooja
     * Sort the list and check for equality
     *
     * @param element pass list of elements
     * @param count   pass count to be matched
     * @return true if both the size and count are equal
     */
    public boolean compareElementCount(List<WebElement> element, int count) {
        return element.size() == count;
    }

    /**
     * Created by Pooja
     * Sort the list and check for equality
     *
     * @param element pass list of elements
     * @param count   pass max count to be matched
     * @return true if element count is less or equal to Max Expected Count
     */
    public boolean checkElementMaxCount(List<WebElement> element, int count) {
        return element.size() > 0 && element.size() <= count;
    }

    public boolean addOutFitsToBag(Map<String, String> keywordsAndQty, String department) {
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            String url = "", itemId = "";
            boolean isItemAddedToBag = false;
            String keyword = keywordsQty.getKey();
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
            int totalProducts = mcategoryDetailsPageAction.productTitles.size();
            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                try {
                    mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, keyword);
                    totalProducts = mcategoryDetailsPageAction.productTitles.size();

                    if (mcategoryDetailsPageAction.clickProductByPosition(i + 1)) {
                        String prodName = mproductDetailsPageActions.getProductName().toLowerCase();
                        if (mproductDetailsPageActions.isItemOutOfStock()) {
                            logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                            isProdAvailable = false;
                        } else {
                            isProdAvailable = true;
                            mproductDetailsPageActions.selectFitAndSize();
                            mproductDetailsPageActions.selectDropDownByVisibleText(mproductDetailsPageActions.qtyDropDown, "1");

                            try {
                                isItemAddedToBag = mproductDetailsPageActions.clickAddToBag();
                            } catch (Exception e) {
                                AddInfoStep("issue while adding product to bag");
                            }
                            if (isItemAddedToBag) {
                                url = driver.getCurrentUrl();
                                itemId = url.substring(url.lastIndexOf("/") + 1, url.length());
                                panCakePageActions.selectLevelOneCategory(department);
                                panCakePageActions.selectLevelTwoCategory("Outfits");
                                mcategoryDetailsPageAction.clickOutfitsImageByPosition(0);
                                String outfitsUrl = driver.getCurrentUrl();
                                String newUrl = outfitsUrl.replace(outfitsUrl.substring(outfitsUrl.lastIndexOf("-") + 1, outfitsUrl.length()), itemId);
                                driver.get(newUrl);
                                break;
                            }
                        }
                    }
                } catch (Throwable t) {
                    AddInfoStep("Something happened while searching an  item ");
                }
            }
            AssertFailAndContinue(isProdAvailable, "The product " + itemId + "is available");
        }
        mproductDetailsPageActions.mouseHover(mheaderMenuActions.TCPLogo);
        return mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.gobackbtn);
    }

    /**
     * validated current page url against expected
     *
     * @param title of the page expected
     * @return true if page title matches with expected title
     */
    public boolean validatePageURL(String title) {
        String pageUrl = mheaderMenuActions.getCurrentURL();
        return pageUrl.equalsIgnoreCase(title);
    }

    /**
     * validated current page title against expected
     *
     * @param title of the page expected
     * @return true if page title matches with expected title
     */
    public boolean validatePageTitle(String title) {
        String pageTitle = driver.getTitle();
        return pageTitle.equalsIgnoreCase(title);
    }

    public void makeExpressCheckoutUser(String store, String env) throws Exception {
        if (panCakePageActions.isDisplayed(panCakePageActions.myAccount)) {
            panCakePageActions.javaScriptClick(driver, panCakePageActions.myAccount);
        } else {
            panCakePageActions.navigateToMenu("MYACCOUNT");
        }
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        mmyAccountPageActions.deleteAllAddress();

        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");

        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.addNewShippingAddress(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("city"),
                    usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("country"), usBillingAdd.get("phNum"), panCakePageActions);
        }
        if (store.equalsIgnoreCase("CA")) {
            mmyAccountPageActions.addNewShippingAddress(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("city"),
                    caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("country"), caShippingAdd.get("phNum"), panCakePageActions);
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        mmyAccountPageActions.deleteAllcards();
        Map<String, String> paymentDetails = null;
        if (env.equalsIgnoreCase("prod"))
            paymentDetails = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
        else
            paymentDetails = dt2ExcelReader.getExcelData("PaymentDetails", "MasterCard");

        mmyAccountPageActions.addACreditCard(paymentDetails.get("cardNumber"), paymentDetails.get("expMonthValueUnit"), paymentDetails.get("expYear"));
    }

    public void makeAssociateUser(String fname, String lastName, String associateID,String addrLine1, String city, String state) throws Exception {
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");
        mmyAccountPageActions.enterAssociateDetails(fname, lastName, associateID);
        mmyAccountPageActions.enterMailAddress(addrLine1,city,state);
        mmyAccountPageActions.clickSaveButton();
    }


    /**
     * Add product tobag through api method
     *
     * @param email    of the page expected
     * @param password of the page expected
     * @param store    of the page expected
     * @param qty      of the page expected
     * @return true on clicking on shopping bag
     */
    /**
     * Created by Richa Priya
     * Add to bag from PDP
     *
     * @param qty provide the quantity
     * @return list of upc number
     */
    public List<String> addToBagFromPDPPage(String qty) {
        List<String> upcNums = new ArrayList<>();
        String prodName = mproductDetailsPageActions.getProductName().toLowerCase();
        if (mproductDetailsPageActions.isItemOutOfStock()) {
            logger.info("The item " + prodName + " is out of stock. Continuing with other product.");
        } else {
            mproductDetailsPageActions.selectFitAndSize();
            mproductDetailsPageActions.selectDropDownByVisibleText(mproductDetailsPageActions.qtyDropDown, qty);

            try {
                mproductDetailsPageActions.clickAddToBag();
            } catch (Throwable t) {
                logger.info("Something happened while adding the item to bag: " + t.getMessage());
                if (mheaderMenuActions.isAlertPresent()) {
                    mheaderMenuActions.acceptAlert();
                }
            }
        }

        return upcNums;
    }

    /**
     * Created by RichaK
     * Select size, fit and quantity on PDP page
     */
    public void selectSizeFitQtyOnPDPPage(Map<String, String> keywordsAndQty, String size, String fit) {

        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, keyword);
            String prodName = mproductDetailsPageActions.getProductName().toLowerCase();
            if (mproductDetailsPageActions.isItemOutOfStock()) {
                logger.info("The item " + prodName + " is out of stock. Continuing with other product.");
            } else {
                mproductDetailsPageActions.selectFitAndSize(fit, size);
                mproductDetailsPageActions.selectDropDownByVisibleText(mproductDetailsPageActions.qtyDropDown, qty);
            }
        }
    }

    public boolean checkValueInList(List<String> lists, String value) {
        boolean compare = true;
        for (String list : lists) {
            if (list.contains(value)) {
                compare = true;
                break;
            }
        }
        return compare;
    }

    /**
     * Created by Richa Priya
     *
     * @return get the round off value
     */
    public int getRoundOffValue(String number) {
        double value = Double.parseDouble(number);
        return (int) (value + 0.5);
    }

    /**
     * Created by RichaK
     * This method is to check if FDMS connectivity is down/up
     *
     * @return
     * @throws Exception
     */

}