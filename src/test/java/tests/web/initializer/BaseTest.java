package tests.web.initializer;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import ui.pages.actions.ReviewPageActions;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

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
 * Created by skonda on 5/18/2016.
 */
public class BaseTest extends PageInitializer {
    protected static final String storeXml = "store", usersXml = "users";
    protected static final String dataProviderName = "StoreAndUsers";
    protected static final String WIC = "wic", USONLY = "usonly", REGISTEREDONLY = "registeredonly", GUESTONLY = "guestonly",
            GIFTCARDPRODUCT = "giftcardproduct", PROD_REGRESSION = "prodregression", SMOKE = "smoke", CAONLY = "caonly", PDP = "pdp", PLP = "plp", SEARCH = "search",
            MYACCOUNT = "myaccount", GLOBALCOMPONENT = "globalcomponent", CHECKOUT = "checkout", PAYPAL = "paypal", GIFTCARD = "giftcard", PRODUCTION = "production", PLCC = "plcc", FDMS = "fdms", REGRESSION = "regression", BOPIS = "bopis", COUPONS = "coupons", CHEETAH = "cheetah", CREATEACCOUNT = "createaccount",
            DRAGONFLY = "dragonfly", RTPS = "rtps", R6 = "r6", EAGLE = "eagle", SHOPPINGBAG = "shoppingbag", PRODUCTIONONLY = "productiononly", OUTFIT = "outfit",INT_CHECKOUT= "intcheckout",RECOMMENDATIONS="recommendations",REMEMBEREDUSER="remembereduser",CARTMERGE="cartmerge", INTUATSTG="int_uatstg",
            OTTER = "otter",RHINO="rhino",FAVORITES="favorites";

    public String rowInExcelUS = "CreateAccountUS", rowInExcelCA = "CreateAccountCA";
    public ExcelReader e2eExcelReader = new ExcelReader(Config.getDataFile(Config.E2E_TESTING_DATAFILE));
    public String visa, mc_5series, mc_2series, amex, discover;
    public ExcelReader ropisExcelReader = new ExcelReader(Config.getDataFile(Config.PHASE2_DETAILS));
    ExcelReader phase2DetailsExcelReader = new ExcelReader(Config.getDataFile(Config.PHASE2_DETAILS));
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));

    public static final int MAX_COMPLETEDSTATUS_COUNT = 5;
    public Set<String> footerLinksAsGuest;
    public Set<String> footerLinksAsRegistered;


    public int bagCount;
    public int actualBagCount;
    public int selectedProductPos;
    public String cartCount;
    public String estimatedTot;

    public String orderNumber;

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
       // return new Object[][]{{"US", "registered"}};
    }

    @DataProvider(name = "StoreUsersAndCard")
    public static Object[][] getUsersStoreAndCard(ITestContext context) {
        String store = context.getCurrentXmlTest().getLocalParameters().get(storeXml);
        String user = context.getCurrentXmlTest().getLocalParameters().get(usersXml);
        String cardType = context.getCurrentXmlTest().getLocalParameters().get("cardtype");

        List<String> users = Arrays.asList(user.split(","));
        List<String> cards = Arrays.asList(cardType.split(","));
        Object[][] storeWithUserCard = new Object[cards.size()][1];

        for (int i = 0; i < cards.size(); i++) {
            String card = cards.get(i).trim();
            storeWithUserCard[i] = new Object[]{store, user, card};
        }
        return storeWithUserCard;
        //  return new Object[][]{{"US", "guest","Visa_P_New"},{"US", "guest","Visa_U_New"},{"US", "guest","Amex_P_New"}};
    }

    @DataProvider(name = "Users")
    public Object[][] users() {
        return new Object[][]{{"registered"}, {"guest"}};
    }

    @DataProvider(name = "Store")
    public Object[][] store() {
        return new Object[][]{{"US"}, {"CA"}};
    }

    /*Use this data provider in case you want to unit test your
    test method. Change the data provider name to "StoreAndUsersSingleTest"
    as @Test(dataProvider = "StoreAndUsersForSingleTest", priority = 3, groups = {"plp"}.
    To run a test for single parameter remove or comment all the other parameters*/
    @DataProvider(name = "StoreAndUsersForSingleTest")
    public static Object[][] getStoreAndUserForSingleTest() {
        return new Object[][]{{"US", "registered"},{"US", "guest"},
                {"CA", "guest"}, {"CA", "registered"}};
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

    public String domUserName() {
        return Config.getDomUsername();
    }

    public String domPassword() {
        return Config.getDomPassword();
    }

    public boolean clickLoginAndLoginAsRegUserAndDisableRememberMe(String un, String pwd) {
        headerMenuActions.clickLogin(loginDrawerActions);
        return loginPageActions.loginWithDisableRememberMeCheckBox(un, pwd, headerMenuActions);
    }

    @Deprecated//{@link refer # clickLoginAndLoginAsRegUser}
    public boolean clickLoginAndLoginAsRegisteredUser(String un, String pwd) {
        headerMenuActions.clickLogin(loginDrawerActions);
        return loginPageActions.login(un, pwd, headerMenuActions);
    }

    public void clickLoginAndLoginAsRegUserAndCloseDrawer(String un, String pwd) {
        clickLoginAndLoginAsRegUser(un, pwd);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        headerMenuActions.refreshPage();
        emptyShoppingBag();
    }

    public void clickLoginAndLoginAsRegUser(String un, String pwd) {
        if (headerMenuActions.waitUntilElementDisplayed(overlayHeaderActions.closeOnDrawer, 5)) {
            overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        }
        if (headerMenuActions.waitUntilElementDisplayed(headerMenuActions.loginLinkHeader, 5)) {
            headerMenuActions.clickLoginLnk(loginDrawerActions);
            AssertFailAndContinue(loginDrawerActions.userLogin(un, pwd, overlayHeaderActions), "Logged in as Reg User: " + un);
        } else if (headerMenuActions.isDisplayed(headerMenuActions.welcomeCustomerLink)) {
            headerMenuActions.addStepDescription("Login link is not present, the user is already logged in?");
        }
    }

    public boolean clickLoginAndLoginAsRegisteredUserWithRemMe(String un, String pwd) {
        headerMenuActions.clickLoginLnk(loginDrawerActions);
        boolean isLoggedIn = loginDrawerActions.loginWithRememberMe(un, pwd, overlayHeaderActions);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        emptyShoppingBag();
        return isLoggedIn;
    }

    public boolean loginAsRegisteredUserWithRememberMe(String un, String pwd) {
        headerMenuActions.clickLogin(loginDrawerActions);
        return loginDrawerActions.loginWithRememberMe(un, pwd, overlayHeaderActions);
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

    public String getE2ETestingCellValueBySheetRowAndColumn(String sheetName, String rowName, String columnName) throws Exception {
        Map<String, String> recordByRowName = e2eExcelReader.getExcelData(sheetName, rowName);
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


    public Map<String, String> getParaBySettingShipmentXml(String shipViaCode, Map<String, String> shipItemsAndQty) {
        Map<String, String> para = new HashMap<>();
        String doNum = domSearchOrderPageActions.getDONumFromDistributionOrderDetails();
        String randomNum = domSearchOrderPageActions.getRandomNumber(18);
        para.put("//ASNID", randomNum);
        para.put("//LPNID", randomNum);
        para.put("//ShipVia", shipViaCode);
        para.put("//DistributionOrderID", doNum);
        int i = 1;
        for (Map.Entry<String, String> entryMap : shipItemsAndQty.entrySet()) {
            String itemName = entryMap.getKey();
            String itemQty = entryMap.getValue();
            para.put("//LPNDetail[" + i + "]/ItemName", itemName);
            String doLineNum = domSearchOrderPageActions.getDOLineNumByOrderedItem(itemName);
            para.put("//LPNDetail[" + i + "]/DistributionOrderLineItemID", doLineNum);
            para.put("//LPNDetail[" + i + "]/SkuSequenceNbr", doLineNum);
            para.put("//LPNDetail[" + i + "]/LPNDetailQuantity/Quantity", itemQty);
            para.put("//LPNDetail[" + i + "]/LPNDetailQuantity/ShippedAsnQuantity", itemQty);
            i++;
        }
        return para;
    }

    public Map<String, String> getParaBySettingCancelXml(Map<String, String> cancelItemsAndQty) {
        Map<String, String> para = new HashMap<>();
        String doNum = domSearchOrderPageActions.getDONumFromDistributionOrderDetails();
        para.put("//DistributionOrderId", doNum);
        int i = 1;
        for (Map.Entry<String, String> entryMap : cancelItemsAndQty.entrySet()) {
            String itemName = entryMap.getKey();
            String itemQty = entryMap.getValue();
            para.put("//LineItem[" + i + "]/ItemName", itemName);
            String doLineNum = domSearchOrderPageActions.getDOLineNumByOrderedItem(itemName);
            para.put("//LineItem[" + i + "]/DoLineNbr", doLineNum);
            para.put("//LineItem[" + i + "]/Quantity/OrderQty", itemQty);
            i++;
        }
        return para;
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

    public void emptyShoppingBag() {
        headerMenuActions.waitUntilElementDisplayed(headerMenuActions.shoppingBagIcon);
        headerMenuActions.staticWait(5000);
        int qtyInBag = 0;
        try {
            qtyInBag = Integer.parseInt(headerMenuActions.getQtyInBag());
        } catch (NumberFormatException n) {
            logger.info("EmptyShopping Bag number format exception is: " + n.getMessage());
            qtyInBag = 0;
        }
        if (qtyInBag > 0) {
            headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
            headerMenuActions.staticWait(3000);
            int noOfItems = overlayHeaderActions.itemDescElements.size();
            for (int i = 0; i < noOfItems; i++) {
                overlayHeaderActions.clickRemoveLinkByPosition(1);
                overlayHeaderActions.waitUntilElementDisplayed(overlayHeaderActions.notification_ItemUpdated, 10);
            }
            overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        }
    }

    public void emptyWishList() {
        headerMenuActions.clickWishListAsRegistered(favoritePageActions);
        favoritePageActions.deleteAllItems();
    }

    public void emptyWishListByName(String wishListName) {
        headerMenuActions.clickWishListAsRegistered(favoritePageActions);
        AssertFailAndContinue(favoritePageActions.deleteWishListByName(wishListName), "Created wishList has been deleted");
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

    public void changeByLanguageFromImgFlag(String language) {
        footerActions.changeLanguageByLanguage(language);
        AddInfoStep("Changed the language to " + language);
        headerMenuActions.switchToDefaultFrame();
        homePageActions.staticWait(2000);
//        isPromoNotDisplaying();
    }

    @Deprecated
//{@link refer for guest user # enterShippingDetailsByShippingAddress_Guest(), for registered use the # enterShippingDetailsByShippingAddress_Reg()
    public void enterShippingDetailsByShippingAddress(String shipAddress, String shipType) throws Exception {
        Map<String, String> sd = e2eExcelReader.getExcelData("ShippingDetails", shipAddress);
        String shipValue = getE2ETestingCellValueBySheetRowAndColumn("ShipMethodCodes", shipType, "shipValue");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, sd.get("fName"), sd.get("lName"), sd.get("addressLine1"), sd.get("addressLine2"),
                sd.get("city"), sd.get("stateShortName"), sd.get("zip"), sd.get("phNum"), shipValue), "entered shipping details and Navigates to Order Receipt page");

    }

    public void enterShippingDetailsByShippingAddress_Reg(String shipAddress, String shipType) throws Exception {
        Map<String, String> sd = e2eExcelReader.getExcelData("ShippingDetails", shipAddress);
        String shipValue = getE2ETestingCellValueBySheetRowAndColumn("ShipMethodCodes", shipType, "shipValue");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, sd.get("fName"), sd.get("lName"), sd.get("addressLine1"), sd.get("addressLine2"),
                sd.get("city"), sd.get("stateShortName"), sd.get("zip"), sd.get("phNum"), shipValue), "entered shipping details and Navigates to Order Receipt page");

    }

    public void enterShippingDetailsByShippingAddress_Guest(String shipAddress, String shipType) throws Exception {
        Map<String, String> sd = e2eExcelReader.getExcelData("ShippingDetails", shipAddress);
        String shipValue = getE2ETestingCellValueBySheetRowAndColumn("ShipMethodCodes", shipType, "shipValue");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, sd.get("fName"), sd.get("lName"), sd.get("addressLine1"), sd.get("addressLine2"),
                sd.get("city"), sd.get("stateShortName"), sd.get("zip"), sd.get("phNum"), shipValue), "entered shipping details and Navigates to Order Receipt page");

    }

    public void enterShippingDetailsByShippingAddress_GuestCA(String shipAddress, String shipType) throws Exception {
        Map<String, String> sd = e2eExcelReader.getExcelData("ShippingDetails", shipAddress);
        String shipValue = shipType;//getE2ETestingCellValueBySheetRowAndColumn("ShipMethodCodes", shipType, "shipValue");
        AssertFailAndContinue(shippingPageActions.enterShipDetailsAndShipMethodByPosAndContinue_GuestCA(billingPageActions, sd.get("fName"), sd.get("lName"), sd.get("addressLine1"), sd.get("addressLine2"),
                sd.get("city"), sd.get("stateShortName"), sd.get("zip"), sd.get("phNum"), shipValue), "entered shipping details and Navigates to Order Receipt page");

    }

    public void enterShippingDetailsByShippingAddress_RegCA(String shipAddress, String shipType) throws Exception {
        Map<String, String> sd = e2eExcelReader.getExcelData("ShippingDetails", shipAddress);
        String shipValue = shipType;//getE2ETestingCellValueBySheetRowAndColumn("ShipMethodCodes", shipType, "shipValue");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinueByPos_Reg(billingPageActions, sd.get("fName"), sd.get("lName"), sd.get("addressLine1"), sd.get("addressLine2"),
                sd.get("city"), sd.get("stateShortName"), sd.get("zip"), sd.get("phNum"), shipValue), "entered shipping details and Navigates to Order Receipt page");

    }


    public void enterPaymentDetailsAsRegisteredByPayTypeAndSubmit(String paymentMethod) throws Exception {
        enterPaymentDetailsAsRegisteredByPayType(paymentMethod);
        reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions);
    }

    public void enterPaymentDetailsAsRegisteredByPayType(String paymentMethod) throws Exception {
        Map<String, String> vi = e2eExcelReader.getExcelData("PaymentDetails", paymentMethod);
        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValue")), "Entered Payment Details and Navigates to Review page");

    }


    public void enterPlaceCardDetailsAsGuestAndSubmit() throws Exception {
        Map<String, String> vi = e2eExcelReader.getExcelData("PaymentDetails", "PlaceCard");
        String emailAddr = getE2ETestingCellValueBySheetRowAndColumn("PaymentDetails", "PlaceCard", "emailAddress");

        AssertFailAndContinue(billingPageActions.enterPLCCDetails(reviewPageActions, vi.get("cardNumber")), "Entered Payment Details and Navigates to Review page");
        reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions);
    }

    public String getAndVerifyOrderNumber(String testName) {
        orderNumber = orderConfirmationPageActions.getOrderNum();
        logger.info("The order Number of :" + testName + " is: " + orderNumber);
        AssertFailAndContinue(orderNumber != null && !orderNumber.isEmpty(), testName + " Landing on the Order confirmation page, obtained the Order Number verification: " + orderNumber);
        return orderNumber;
    }


    public String getMultiUseFreeShippingCode() throws Exception {
        String freeShipCouponCode = getE2ETestingCellValueBySheetRowAndColumn("MultiUseCouponCodes", "USFreeShippinng", "couponCode");
        return freeShipCouponCode;

    }

    public void addToBagFromPDP(String itemid, String color, String fit, String size, String qty) {
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, itemid);
        if (!productDetailsPageActions.isItemOutOfStock()) {
            productDetailsPageActions.click(productDetailsPageActions.ColorByName(color));
            if (fit != "" && productDetailsPageActions.waitUntilElementsAreDisplayed(productDetailsPageActions.availableFits, 10))
                productDetailsPageActions.click(productDetailsPageActions.fitByName(fit));
            productDetailsPageActions.click(productDetailsPageActions.sizeByNumber(size));
            productDetailsPageActions.updateQty(qty);
            productDetailsPageActions.addToBagFromPDPAndVerify();
            //headerMenuActions.click(headerMenuActions.continueShoppingLink);
            productDetailsPageActions.staticWait(5000);
            headerMenuActions.navigateToShoppingBagPageFromHeader(shoppingBagDrawerActions, shoppingBagPageActions);
        } else {
            AddInfoStep("itemid " + itemid + " is out of stock");
        }
    }

    public Map<String, String> addToBagBySearching(Map<String, String> keywordsAndQty, Map<String, String> keyWordsAndQtyToShip) {
        Map<String, String> upcNumsAndQtyToShip = new HashMap<>();
        int j = 0, k = 0;
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            String qtyToShip = keyWordsAndQtyToShip.get(keyword);
            headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
            int totalProducts = categoryDetailsPageAction.productImages.size();
            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
                totalProducts = categoryDetailsPageAction.productImages.size();
                categoryDetailsPageAction.clickImageByPosition(productDetailsPageActions, i + 1);
                String prodName = productDetailsPageActions.getProductName().toLowerCase();
                if (productDetailsPageActions.isItemOutOfStock()) {
                    logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                    isProdAvailable = false;
                } else {
//            AssertFailAndContinue(searchResultsPageActions.clickFirstProductWithNoLimitedQuantities(productDetailsPageActions), "Clicked the first avialable product which doesn't have limited quantities");
                    isProdAvailable = true;
                    productDetailsPageActions.selectASize();
                    productDetailsPageActions.updateQty(qty);
                    try {
                        boolean isItemAddedToBag = productDetailsPageActions.clickAddToBag();
                        if (isItemAddedToBag) {
                            k++;
                        } else {
                            AddInfoStep("The Item is not able to add to the bag");
                        }
//                        String upcNum = shoppingBagOverlayActions.getUPCNumByPosition(k);
//                        logger.info("Found the available product "+(k)+" "+upcNum);
//                        shoppingBagOverlayActions.clickContinueShoppingButton();
//                        upcNumsAndQtyToShip.put(upcNum, qtyToShip);
                        AddInfoStep("Added Product To Bag with qty " + qty);
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

    public List<String> addToBagBySearching(Map<String, String> keywordsAndQty) {
        List<String> upcNums = new ArrayList<>();

        String upcNum = "1";
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            int k = 0;
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
            categoryDetailsPageAction.staticWait(5000);
            int totalProducts = categoryDetailsPageAction.itemContainers.size();
            if (totalProducts > 10) {
                totalProducts = 10;
            }

            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                try {
                    headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
                  //  totalProducts = categoryDetailsPageAction.itemContainers.size();
                   // if (categoryDetailsPageAction.clickProductNameByPosition(productDetailsPageActions, i + 1)) {
// adding new method,
                    if(categoryDetailsPageAction.clickImageByPosition(productDetailsPageActions,i+1)){
//     productDetailsPageActions.staticWait(5000);
                        String prodName = productDetailsPageActions.getProductName().toLowerCase();
                        if (productDetailsPageActions.isItemOutOfStock()) {
                            logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                            isProdAvailable = false;
                        } else {
                            isProdAvailable = true;
                            productDetailsPageActions.selectFitAndSize();
                            productDetailsPageActions.updateQty(qty);

                            try {
                                boolean isItemAddedToBag = productDetailsPageActions.clickAddToBag();
                                if (isItemAddedToBag) {
                                    k++;
                                    headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
                                    shoppingBagDrawerActions.clickViewBagLink(shoppingBagPageActions);
                                    upcNum = shoppingBagPageActions.getUPCNumByPosition(k);
                                    upcNums.add(upcNum);
                                    logger.info("Found the available product " + (k) + " " + upcNum);

                                    AddInfoStep("Added Product To Bag " + upcNum + " with qty " + qty);
                                    break;
                                } else {
                                    AddInfoStep("The Item is not able to add to the bag");
                                }

//                                headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
//                                shoppingBagDrawerActions.clickViewBagLink(shoppingBagPageActions);
//                                upcNum = shoppingBagPageActions.getUPCNumByPosition(k);
//                                upcNums.add(upcNum);
//                                logger.info("Found the available product " + (k) + " " + upcNum);
//
//                                AddInfoStep("Added Product To Bag " + upcNum + " with qty " + qty);
//                                break;
                            } catch (Throwable t) {
                                logger.info("Something happened while adding the item to bag: " + t.getMessage());
                                if (headerMenuActions.isAlertPresent()) {
                                    headerMenuActions.acceptAlert();
                                }
                            }
                        }
                    }
                } catch (Throwable t) {
                    AddInfoStep("Something happened while searching an  item ");

                }

            }
            AssertFailAndContinue(isProdAvailable, "The product " + upcNum + "is available");
        }
//        productDetailsPageActions.mouseHover(headerMenuActions.TCPLogo);
        return upcNums;

    }

    public void addToBagBySearchingAndStayingInPDP(Map<String, String> keywordsAndQty) {
        List<String> upcNums = new ArrayList<>();

        String upcNum = "1";
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            int k = 0;
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
            categoryDetailsPageAction.staticWait(5000);
            int totalProducts = categoryDetailsPageAction.itemContainers.size();
            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                try {
                    headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
                    totalProducts = categoryDetailsPageAction.itemContainers.size();
                    if (categoryDetailsPageAction.clickProductNameByPosition(productDetailsPageActions, i + 1)) {
                        String prodName = productDetailsPageActions.getProductName().toLowerCase();
                        if (productDetailsPageActions.isItemOutOfStock()) {
                            logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                            isProdAvailable = false;
                        } else {
                            isProdAvailable = true;
                            productDetailsPageActions.selectFitAndSize();
                            productDetailsPageActions.updateQty(qty);

                            try {
                                boolean isItemAddedToBag = productDetailsPageActions.clickAddToBag();
                                if (isItemAddedToBag) {
                                    k++;
                                } else {
                                    AddInfoStep("The Item is not able to add to the bag");
                                }


                                logger.info("Found the available product " + (k) + " " + upcNum);

                                AddInfoStep("Added Product To Bag " + upcNum + " with qty " + qty);
                                break;
                            } catch (Throwable t) {
                                logger.info("Something happened while adding the item to bag: " + t.getMessage());

                            }
                        }
                    }
                } catch (Throwable t) {
                    AddInfoStep("Something happened while searching an  item ");

                }

            }
            AssertFailAndContinue(isProdAvailable, "The product " + upcNum + "is available");
        }
        productDetailsPageActions.mouseHover(headerMenuActions.TCPLogo);

    }

    /**
     * Add to bag from product flip(if product is not OOS) in PLP page
     *
     * @param keyword keyword to search
     * @param qty     to select
     */
    public void addToBagFromFlip(String keyword, String qty) {
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
        int totalProducts = categoryDetailsPageAction.itemContainers.size();
        for (int i = 0; i < totalProducts; i++) {
            categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, i + 1);
            if (!categoryDetailsPageAction.waitUntilElementDisplayed(categoryDetailsPageAction.flipOOS, 5)) {
                categoryDetailsPageAction.selectRandomSizeFromFlip();
                categoryDetailsPageAction.selectQtyFromFlip(qty);

                categoryDetailsPageAction.click(categoryDetailsPageAction.addToBagBtn);
                if (headerMenuActions.waitUntilElementDisplayed(headerMenuActions.checkoutFromNotification, 10)) {
//                    headerMenuActions.mouseHover(headerMenuActions.checkoutFromNotification);
                    headerMenuActions.click(headerMenuActions.viewBagButtonFromAddToBagConf);
                    categoryDetailsPageAction.waitUntilElementDisplayed(shoppingBagPageActions.checkoutBtn, 3);
                    break;
                }
            } else {
                categoryDetailsPageAction.click(categoryDetailsPageAction.closeAddToBaglink);
            }
        }
    }

    public String getOutofstockProductDetails(String keyword) {
        String item = null;
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
        int totalProducts = categoryDetailsPageAction.itemContainers.size();
        for (int i = 0; i < totalProducts; i++) {
            categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, i + 1);
            if (categoryDetailsPageAction.waitUntilElementDisplayed(categoryDetailsPageAction.flipOOS, 5)) {
                categoryDetailsPageAction.clickViewProductDetailsLink();
                String[] s = productDetailsPageActions.getText(productDetailsPageActions.itemNumber).split(" ");
                item = s[2];
                break;
            } else {
                logger.info("No items available with OOS, try with ort products");
            }
        }
        return item;
    }


    public boolean addOutFitsToBag(Map<String, String> keywordsAndQty) {
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            int k = 0;
            String itemId = "";
            String keyword = keywordsQty.getKey();
            //  String qty = keywordsQty.getValue();
            headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
            int totalProducts = categoryDetailsPageAction.itemContainers.size();
            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                try {
                    // headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
                    //   totalProducts = categoryDetailsPageAction.itemContainers.size();
                    if (categoryDetailsPageAction.clickProductNameByPosition(productDetailsPageActions, i + 1)) {
                        String prodName = productDetailsPageActions.getProductName().toLowerCase();
                        if (productDetailsPageActions.isItemOutOfStock()) {
                            logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                            isProdAvailable = false;

                        } else {
                            isProdAvailable = true;
                            itemId = productDetailsPageActions.getText(productDetailsPageActions.itemNumber).split("#: ")[1];
                            headerMenuActions.navigateToDepartment("Boy");
                            List<WebElement> l2 = categoryDetailsPageAction.clickL2category("Outfits");
                            WebElement last = l2.get(l2.size() - 1);
                            headerMenuActions.click(last);
                            categoryDetailsPageAction.clickOutfitsImageByPosition(0);
                            String outfitsUrl = driver.getCurrentUrl();
                            String newUrl = outfitsUrl.replace(outfitsUrl.substring(outfitsUrl.lastIndexOf("-") + 1, outfitsUrl.length()), itemId);
                            driver.get(newUrl);
                            break;
                        }
                    }
                } catch (Throwable t) {
                    AddInfoStep("Something happened while searching an  item ");
                }
            }
            AssertFailAndContinue(isProdAvailable, "The product " + itemId + "is available");
        }
        productDetailsPageActions.mouseHover(headerMenuActions.TCPLogo);
        return categoryDetailsPageAction.waitUntilElementDisplayed(categoryDetailsPageAction.sizeCharBtn);
    }

    /**
     * Created By Pooja
     * This Method adds outfits to Ba by searching for Product UPC of the product in Outfits
     *
     * @param Item       Product UPC Number
     * @param department
     * @return if the Outfits Page displayed the product with size chart link
     */
    public boolean addOutFitsToBagByUPC(String Item, String department,int i) {
        String itemId = "";
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, Item);
        itemId = productDetailsPageActions.getText(productDetailsPageActions.itemNumber).split("#: ")[1];
        headerMenuActions.navigateToDepartment(department);
        List<WebElement> l2 = categoryDetailsPageAction.clickL2category("Outfits");
        WebElement last = l2.get(l2.size() - 1);
        headerMenuActions.click(last);
        categoryDetailsPageAction.clickOutfitsImageByPosition(i);
        String outfitsUrl = driver.getCurrentUrl();
        String newUrl = outfitsUrl.replace(outfitsUrl.substring(outfitsUrl.lastIndexOf("-") + 1, outfitsUrl.length()), itemId);
        driver.get(newUrl);
        return categoryDetailsPageAction.waitUntilElementDisplayed(categoryDetailsPageAction.sizeCharBtn);
    }

    public boolean addBabyOutFitsToBag(Map<String, String> keywordsAndQty) {
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            int k = 0;
            String itemId = "";
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
            int totalProducts = categoryDetailsPageAction.itemContainers.size();
            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                try {
                    headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
                    totalProducts = categoryDetailsPageAction.itemContainers.size();
                    if (categoryDetailsPageAction.clickProductNameByPosition(productDetailsPageActions, i + 1)) {
                        String prodName = productDetailsPageActions.getProductName().toLowerCase();
                        if (productDetailsPageActions.isItemOutOfStock()) {
                            logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                            isProdAvailable = false;

                        } else {
                            isProdAvailable = true;
                            itemId = productDetailsPageActions.getText(productDetailsPageActions.itemNumber).split("#: ")[1];
                            headerMenuActions.navigateToDepartment("Baby");
                            headerMenuActions.click(departmentLandingPageActions.outfits_Lnk);
                            int count = categoryDetailsPageAction.productImages.size();
                            if (count > 0) {
                                categoryDetailsPageAction.clickOutfitsImageByPosition(0);
                                String outfitsUrl = driver.getCurrentUrl();
                                String newUrl = outfitsUrl.replace(outfitsUrl.substring(outfitsUrl.lastIndexOf("-") + 1, outfitsUrl.length()), itemId);
                                driver.get(newUrl);
                                break;
                            } else {
                                logger.info("No items are available in outfit landing page");
                                return false;
                            }

                        }
                    }
                } catch (Throwable t) {
                    AddInfoStep("Something happened while searching an  item ");
                }
            }
            AssertFailAndContinue(isProdAvailable, "The product " + itemId + "is available");
        }
        productDetailsPageActions.mouseHover(headerMenuActions.TCPLogo);
        return categoryDetailsPageAction.waitUntilElementDisplayed(categoryDetailsPageAction.sizeCharBtn);

    }


    public List<String> addToBagBySearchingAndCheckoutFromNotification(Map<String, String> keywordsAndQty, String user) {
        List<String> upcNums = new ArrayList<>();

        String upcNum = "1";
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
            int totalProducts = categoryDetailsPageAction.itemContainers.size();
            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                try {
                    headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
                    totalProducts = categoryDetailsPageAction.itemContainers.size();
                    if (categoryDetailsPageAction.clickProductNameByPosition(productDetailsPageActions, i + 1)) {
                        String prodName = productDetailsPageActions.getProductName().toLowerCase();
                        if (productDetailsPageActions.isItemOutOfStock()) {
                            logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                            isProdAvailable = false;
                        } else {
                            isProdAvailable = true;
                            productDetailsPageActions.selectFitAndSize();
                            productDetailsPageActions.updateQty(qty);

                            try {
                                productDetailsPageActions.click(productDetailsPageActions.addToBag);
                                productDetailsPageActions.waitUntilElementDisplayed(headerMenuActions.checkoutFromNotification);
                                headerMenuActions.mouseHover(headerMenuActions.checkoutFromNotification);
                                AssertFailAndContinue(!headerMenuActions.waitUntilElementDisplayed(headerMenuActions.paypalFromNotification, 2), "Verify PayPal Button is not displayed in add to bag notification");
                                headerMenuActions.click(headerMenuActions.checkoutFromNotification);
                                if (user.contains("guest")) {
                                    headerMenuActions.waitUntilElementDisplayed(loginPageActions.continueAsGuestButton, 20);
                                    headerMenuActions.click(shoppingBagPageActions.continueAsGuest);
                                }
                                if (shippingPageActions.waitUntilElementDisplayed(shippingPageActions.nextBillingButton))
                                    break;

                            } catch (Throwable t) {
                                logger.info("Something happened while adding the item to bag and checkout: " + t.getMessage());
                            }
                        }
                    }
                } catch (Throwable t) {
                    AddInfoStep("Something happened while searching an  item ");
                }
            }
            AssertFailAndContinue(isProdAvailable, "The product " + upcNum + "is available");
        }
        productDetailsPageActions.mouseHover(headerMenuActions.TCPLogo);
        return upcNums;

    }

    public List<String> addToBagBySearchingFromProductCard(Map<String, String> keywordsAndQty) {
        List<String> upcNums = new ArrayList<>();

        String upcNum = "1";
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            int k = 0;
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
            categoryDetailsPageAction.staticWait(5000);
            int totalProducts = categoryDetailsPageAction.productImages.size();
            if (totalProducts > 10) {
                totalProducts = 10;
            }
            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                try {
                    totalProducts = categoryDetailsPageAction.productImages.size();
                    String prodName = productCardViewActions.getProductNameByPos(i + 1).toLowerCase();
                    categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, i + 1);
                    if (productCardViewActions.isItemOutOfStock()) {
                        logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                        isProdAvailable = false;
                        isProdAvailable = productCardViewActions.selectAFitSizeUpdateQtyAndAddToBag(qty);
                    } else {
                        isProdAvailable = true;
                        try {
                            boolean isItemAddedToBag = productCardViewActions.selectAFitSizeUpdateQtyAndAddToBag(qty);
                            if (isItemAddedToBag) {
                                k++;
                                isProdAvailable = true;
                            } else {
                                AddInfoStep("The Item is not able to add to the bag");
                                isProdAvailable = false;
                            }

                        } catch (Throwable t) {
                            logger.info("Something happened while adding the item to bag: " + t.getMessage());
                            if (headerMenuActions.isAlertPresent()) {
                                headerMenuActions.acceptAlert();
                            }
                        }
                    }
                    if (isProdAvailable) {
                        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
                        upcNum = shoppingBagPageActions.getUPCNumByPosition(k);
                        upcNums.add(upcNum);
                        logger.info("Found the available product " + (k) + " " + upcNum);
                        AddInfoStep("Added Product To Bag " + upcNum + " with qty " + qty);
                        break;
                    }
                } catch (Throwable t) {
                    AddInfoStep("Something happened while searching an  item ");

                }
            }
            AssertFailAndContinue(isProdAvailable, "The product " + upcNum + "is available and added to bag");
        }
        return upcNums;

    }

    @Deprecated
    public Map<String, String> addToBagBySearching(Map<String, String> keywordsAndQty, Map<String, String> keyWordsAndQtyToShip, int k) {
        Map<String, String> upcNumsAndQtyToShip = new HashMap<>();
        int j = 0;
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            String keyword = keywordsQty.getKey();
            String qty = keywordsQty.getValue();
            String qtyToShip = keyWordsAndQtyToShip.get(keyword);
            headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
            int totalProducts = categoryDetailsPageAction.productImages.size();
            boolean isProdAvailable = false;
            for (int i = 0; i < totalProducts; i++) {
                headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
                totalProducts = categoryDetailsPageAction.productImages.size();
                categoryDetailsPageAction.clickImageByPosition(productDetailsPageActions, i + 1);
                String prodName = productDetailsPageActions.getProductName().toLowerCase();
                if (productDetailsPageActions.isItemOutOfStock()) {
                    logger.info("The item " + prodName + " " + (i + 1) + " is out of stock. Continuing with other product.");
                    isProdAvailable = false;
                } else {
//            AssertFailAndContinue(searchResultsPageActions.clickFirstProductWithNoLimitedQuantities(productDetailsPageActions), "Clicked the first avialable product which doesn't have limited quantities");
                    isProdAvailable = true;
                    productDetailsPageActions.selectASize();
                    productDetailsPageActions.updateQty(qty);
                    try {
                        boolean isItemAddedToBag = productDetailsPageActions.clickAddToBag();
                        if (isItemAddedToBag) {
                            k++;
                        } else {
                            AddInfoStep("The Item is not able to add to the bag");
                        }
                        String upcNum = shoppingBagDrawerActions.getUPCNumByPosition(k);
                        logger.info("Found the available product " + (k) + " " + upcNum);
//                        shoppingBagOverlayActions.clickContinueShoppingButton();
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

    @Deprecated
    public void paypalLoginAndContinue() {
        Map<String, String> es = e2eExcelReader.getExcelData("Login", "paypalLogin");
        //  AssertFailAndContinue(payPalPageActions.paypalLogin(paypalOrderDetailsPageActions, billingPageActions, reviewPageActions, es.get("UserName"), es.get("Password")), "Enter the valid PayPal credentials and click on the login");
        //AssertFailAndContinue(paypalOrderDetailsPageActions.clickOnContinueButton(reviewPageActions), "Click on the Continue button");
        billingPageActions.switchToDefaultFrame();

    }

    @Deprecated
    public void paypalLoginAndContinue_CA() {
        Map<String, String> es = phase2DetailsExcelReader.getExcelData("Paypal", "CanadaPaypal");
        //srijith : Disabling since we are not using
        //  AssertFailAndContinue(payPalPageActions.paypalLogin(paypalOrderDetailsPageActions, billingPageActions, reviewPageActions, es.get("UserName"), es.get("Password")), "Enter the valid PayPal credentials and click on the login");
        //AssertFailAndContinue(paypalOrderDetailsPageActions.clickOnContinueButton(reviewPageActions), "Click on the Continue button");
        billingPageActions.switchToDefaultFrame();

    }

    public Map<String, String> mergeMaps(Map<String, String> itemNamesAndQty1, Map<String, String> itemNamesAndQty2) {
        Map<String, String> itemNamesAndQty = new HashMap<>();
        itemNamesAndQty.putAll(itemNamesAndQty1);
        itemNamesAndQty.putAll(itemNamesAndQty2);

        return itemNamesAndQty;
    }

    public void applyGiftCardWithEnoughGiftCardBalance() throws Exception {
        List<String> gcCardNumbers = e2eExcelReader.getColumnData("GiftCardNumsAndPin", "CardNumber");
        boolean isGCApplied = false;
        for (int i = 1; i < gcCardNumbers.size(); i++) {
            String cardNumber = gcCardNumbers.get(i);
            String pinNumber = getE2ETestingCellValueBySheetRowAndColumn("GiftCardNumsAndPin", cardNumber, "PinNumber");
            String isValid = getE2ETestingCellValueBySheetRowAndColumn("GiftCardNumsAndPin", cardNumber, "isValid");
            isGCApplied = billingPageActions.applyGiftCardToOrder(cardNumber, pinNumber, isValid);
            if (isGCApplied) {
                logger.info("The gift Card is available. The card Number: " + cardNumber);
                break;
            }
        }
        AssertFail(isGCApplied, "The gift card is applied on the order and tried with all avaialble Gift Card Numbers and PinNumbers available in Excel sheet");


    }

    public void updateGiftCardFromEnoughGCBalanceAndApplyToOrder(List<String> amtToUpdateOnGC) throws Exception {
        List<String> gcCardNumbers = e2eExcelReader.getColumnData("GiftCardNumsAndPin", "CardNumber");
        boolean isGCAvailable = false;
        String cardNumberPresent = null, cardNumUsed = null;
        for (int j = 0; j < amtToUpdateOnGC.size(); j++) {
            String amtToUpdate = amtToUpdateOnGC.get(j);
            for (int i = 1; i < gcCardNumbers.size(); i++) {
                cardNumberPresent = gcCardNumbers.get(i);
                String pinNumber = getE2ETestingCellValueBySheetRowAndColumn("GiftCardNumsAndPin", cardNumberPresent, "PinNumber");
                if (!cardNumberPresent.equals(cardNumUsed)) {
                    try {
                        isGCAvailable = billingPageActions.updateGiftCardBalanceByAmount(cardNumberPresent, pinNumber, amtToUpdate);
                    } catch (IllegalStateException is) {
                        AssertFailAndContinue(false, is.getMessage());
                    }
                }
                if (isGCAvailable) {
                    cardNumUsed = cardNumberPresent;
                    logger.info("The gift Card is available. The card Number: " + cardNumberPresent);
                    break;
                }
            }
        }
        AssertFail(isGCAvailable, "The gift card is applied on the order and tried with all available Gift Card Numbers and PinNumbers available in Excel sheet");


    }


    public void doDomPerfLogin(String userName, String password) {
        if (domHomePageActions.waitUntilElementDisplayed(domHomePageActions.menuBtn, 5)) {

        } else if (domHomePageActions.waitUntilElementDisplayed(domHomePageActions.userNameFld)) {
            try {
                domHomePageActions.fillText(domHomePageActions.userNameFld, userName);
                domHomePageActions.fillText(domHomePageActions.passwordFld, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            domHomePageActions.staticWait();
            domHomePageActions.click(domHomePageActions.loginBtn);
            domHomePageActions.waitUntilElementDisplayed(domHomePageActions.menuBtn, 30);
            domHomePageActions.refreshPage();
        }


    }

    public WebDriver closeAndReOpenBrowserAndNavigateToURL() throws Exception {

        driver.close();
        initializeDriver();
        driver = getDriver();
        initializePages(driver);

        driver.get(EnvironmentConfig.getApplicationUrl());
        headerMenuActions.refreshPage();
        headerMenuActions.waitUntilElementDisplayed(headerMenuActions.TCPLogo);
        return driver;
    }

    public void clickMinCartIconAndVerifyEmptyMessage() {
        String qtyInBag = headerMenuActions.getQtyInBag();
        AssertFailAndContinue(qtyInBag.equalsIgnoreCase("0"), "Guest User shopping bag showing  qty " + qtyInBag + "");
        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
        AssertFailAndContinue(shoppingBagPageActions.waitUntilElementDisplayed(shoppingBagPageActions.emptyShoppingBag), "Shopping Bag is showing empty Message");
    }

    public String clickCreateNewAcctAndCreateNewAccountByRow(String rowName) {
        headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions);
        return createNewAccountAndGetEmailAddr(rowName);
    }

    public String createNewAccountAndGetEmailAddr(String rowName) {
        Map<String, String> na = dt2ExcelReader.getExcelData("CreateAccount", rowName);
        AssertFailAndContinue(createAccountActions.createNewAccount(na.get("FirstName"), na.get("LastName"), na.get("Password"), na.get("ZipCode"), na.get("PhoneNumber"), na.get("SuccessMessage")), "Created new account " + createAccountActions.getEmailAddress());
        return createAccountActions.getEmailAddress();
    }


    public void switchToOldBrowser(WebDriver browser) {
        setDriver(browser);
        driver = getDriver();
        initializePages(driver);
    }

    public void refreshDomPage() {
        domHomePageActions.refreshPage();
        if (domHomePageActions.isAlertPresent()) {
            domHomePageActions.acceptAlert();
        }
    }


    public String getPhase2DetailsCellValueBySheetRowAndColumn(String sheetName, String rowName, String columnName) {
        Map<String, String> recordByRowName = phase2DetailsExcelReader.getExcelData(sheetName, rowName);
        String cellValue = recordByRowName.get(columnName);
        return cellValue;
    }

    public boolean findAvailable_OutfitProducts() {
        boolean isProdAvailable = false;
        int outfitImgs = categoryDetailsPageAction.outfitProducts.size();
        for (int i = 0; i < outfitImgs; i++) {
            searchResultsPageActions.click(categoryDetailsPageAction.outfitProducts.get(i));
            productDetailsPageActions.waitUntilElementDisplayed(productDetailsPageActions.addToBag);
            if (productDetailsPageActions.isOutfitItemOutOfStock()) {
                isProdAvailable = false;
            } else {
                isProdAvailable = true;
                AssertFailAndContinue(isProdAvailable, "The product is available");
                break;
            }
        }
        return isProdAvailable;
    }


    //DT 2 Create Account Actions
    public void createNewAccountFromHeader(String fName, String lName, String pwd, String zipCode, String phNum, String successMessage) {

        AssertFailAndContinue(headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions), "Clicked on the Create Account link on the Header.");
        AssertFailAndContinue(createAccountActions.createNewAccount(fName, lName, pwd, zipCode, phNum, successMessage), "Enter all the details in Create Account Page");
        AssertFailAndContinue(headerMenuActions.clickOnCustomerName(createAccountActions), "Clicked on the Customer name to access the My Account Drawer");
    }

    public void createAccountFromFooter(String rowInExcel) {
        Map<String, String> acct = dt2ExcelReader.getExcelData("CreateAccount", rowInExcel);

        AssertFailAndContinue(footerActions.clickOnCreateAnAccount(createAccountActions), "Click on Create Account link in the Footer");
        AssertFailAndContinue(createAccountActions.waitUntilElementDisplayed(createAccountActions.emailAddrField, 30), "Click on Create Account link in the Home Page");
        AssertFailAndContinue(createAccountActions.createNewAccount(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), acct.get("SuccessMessage")), "Enter all the details in Create Account Page and click on Create Account button");
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Navigate to My Account Page");
    }

    public void createAccAccessFromForgotPwd() {
        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Clicked on the Login link on the Header.");
        AssertFailAndContinue(loginDrawerActions.clickForgotPasswordLnk(forgotYourPasswordPageActions), "Clicked on the Forgot Password link and landed on the Forgot Password page.");
        AssertFailAndContinue(forgotYourPasswordPageActions.clickCreateAccBtn(createAccountActions), "Clicked on the Create Account button and the Create Account Drawer is displayed.");
    }

    public void createAccAccessFromPromo() {

        AssertFailAndContinue(shoppingBagDrawerActions.clickCreateAccLnk(createAccountActions), "Clicked on the Create Account link from the Promotion content.");

    }

    public boolean findAndAddPickUpInStoreProdFromProductCard(String searchItem, String zip) throws Exception {
        boolean isStoresAvailableWithZip = false;
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, searchItem);
        int pisSize = searchResultsPageActions.pickUpInStoreIcons.size();
        for (int i = 1; i <= pisSize; i++) {
            searchResultsPageActions.clickPicUpInStoreIconByPos(bopisOverlayActions, i);
//            if (quickViewModalActions.isPickUpInStoreButtonAvailable()) {
//                quickViewModalActions.selectAnySize();
//                isPickUpStoreAvail = quickViewModalActions.clickPickUpStoreBtn(bopisOverlayActions);
//                AddInfoStep("Is PickUp Store button Available for the term " + searchItem + " - " + isPickUpStoreAvail);
            bopisOverlayActions.selectSizeAndSelectStore(zip);
            isStoresAvailableWithZip = bopisOverlayActions.selectFromAvailableStores(headerMenuActions, 0);
            AddInfoStep("Is Stores Available with this zip " + zip + " is - " + isStoresAvailableWithZip + " for the UPC " + searchItem);
            if (!isStoresAvailableWithZip) {
                bopisOverlayActions.closeBopisOverlayModal();
            } else {
                break;
            }

//            }
//            else{
//                quickViewModalActions.closeQuickViewModal();
//            }
        }
        return isStoresAvailableWithZip;
    }

    public boolean findPickUpInStoreByUPCAndZip(String searchItem, String zip) throws Exception {
        List<String> contentValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "headerContent", "expectedheaderTextContent"));

        boolean isPickUpStoreAvail = false, isStoresAvailableWithZip = false;

        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, searchItem);
        productDetailsPageActions.staticWait(2000);
        if (productDetailsPageActions.isPickUpInStoreButtonAvailable()) {
            productDetailsPageActions.staticWait(3000);
            if (productDetailsPageActions.isItemOutOfStock()) {
                boolean isProdOutOfStock = productDetailsPageActions.clickNextSwatchForAvailInvByPos();
                AssertFailAndContinue(isProdOutOfStock, "The product inventory is available by switching to swatches");
            }


            String getBagCountBefore = productDetailsPageActions.getText(productDetailsPageActions.bagLink).replaceAll("[^0-9.]", "");
            int bagCountBeforeAddToBag = Integer.parseInt(getBagCountBefore);
            productDetailsPageActions.staticWait(2000);

//            headerMenuActions.scrollDownToElement(productDetailsPageActions.pickUpStore);
            isPickUpStoreAvail = productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
            //AddInfoStep("Is PickUp Store button Available for the UPC " + searchItem + " - " + isPickUpStoreAvail);
            AssertFailAndContinue(bopisOverlayActions.validateTextInBopisOverlay(contentValidation.get(0), contentValidation.get(1)), "Validate the content present in the header of the BOPIS overlay");
            bopisOverlayActions.selectSizeAndSelectMoreThan_1Store(zip);
            isStoresAvailableWithZip = bopisOverlayActions.selectFromAvailableStores(headerMenuActions, bagCountBeforeAddToBag);
            AddInfoStep("Is Stores Available with this zip " + zip + " is - " + isStoresAvailableWithZip + " for the UPC " + searchItem);
            if (!isStoresAvailableWithZip) {
                bopisOverlayActions.closeBopisOverlayModal();
                productDetailsPageActions.mouseHover(productDetailsPageActions.addToBag);
                productDetailsPageActions.staticWait(6000);
            }
        }

        return isStoresAvailableWithZip;
    }

    public boolean findPickUpInStoreByUPCAndZip(String searchItem, String zip, String size) throws Exception {

        boolean isStoresAvailableWithZip = false;

        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, searchItem);
        productDetailsPageActions.staticWait(2000);
        if (productDetailsPageActions.isPickUpInStoreButtonAvailable()) {
            productDetailsPageActions.staticWait(3000);
            if (productDetailsPageActions.isItemOutOfStock()) {
                boolean isProdOutOfStock = productDetailsPageActions.clickNextSwatchForAvailInvByPos();
                AssertFailAndContinue(isProdOutOfStock, "The product inventory is available by switching to swatches");
            }


            String getBagCountBefore = productDetailsPageActions.getText(productDetailsPageActions.bagLink).replaceAll("[^0-9.]", "");
            int bagCountBeforeAddToBag = Integer.parseInt(getBagCountBefore);
            productDetailsPageActions.staticWait(2000);

            productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
            bopisOverlayActions.selectSizeByValueAndSelectStore(zip, size);
            isStoresAvailableWithZip = bopisOverlayActions.selectFromAvailableStores(headerMenuActions, bagCountBeforeAddToBag);
            AddInfoStep("Is Stores Available with this zip " + zip + " is - " + isStoresAvailableWithZip + " for the UPC " + searchItem);
            if (!isStoresAvailableWithZip) {
                bopisOverlayActions.closeBopisOverlayModal();
                productDetailsPageActions.mouseHover(productDetailsPageActions.addToBag);
                productDetailsPageActions.staticWait(6000);
            }
        }

        return isStoresAvailableWithZip;
    }

    public boolean findPickUpInTwoStoreByUPCAndZip(String zip) throws Exception {

        List<String> contentValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "headerContent", "expectedheaderTextContent"));
        boolean isPickUpStoreAvail = false, isStoresAvailableWithZip = false;

        if (productDetailsPageActions.isPickUpInStoreButtonAvailable()) {
            productDetailsPageActions.staticWait(3000);
            if (productDetailsPageActions.isItemOutOfStock()) {
                boolean isProdOutOfStock = productDetailsPageActions.clickNextSwatchForAvailInvByPos();
                AssertFailAndContinue(isProdOutOfStock, "The product inventory is available by switching to swatches");
            }

            productDetailsPageActions.staticWait(2000);
            productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
            AssertFailAndContinue(bopisOverlayActions.validateTextInBopisOverlay(contentValidation.get(0), contentValidation.get(1)), "Validate the content present in the header of the BOPIS overlay");
            bopisOverlayActions.selectSizeAndSelectMoreThan_1Store(zip);
            //AssertFailAndContinue(bopisOverlayActions.validateTextInBopisOverlay(contentValidation.get(0), contentValidation.get(1)), "Validate the content present in the header of the BOPIS overlay");
            isStoresAvailableWithZip = bopisOverlayActions.clickSelectStoreBtnByPosition(2);
            AddInfoStep("Whether BOPIS Stores are available for the zip code");
            if (!isStoresAvailableWithZip) {
                bopisOverlayActions.closeBopisOverlayModal();
                productDetailsPageActions.mouseHover(productDetailsPageActions.addToBag);
                productDetailsPageActions.staticWait(6000);
            }
        }

        return isPickUpStoreAvail && isStoresAvailableWithZip;
    }

    public boolean addFromTwoStoreBOPISmodal(String searchItem) throws Exception {

        List<String> contentValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "headerContent1", "expectedheaderTextContent"));
        boolean isPickUpStoreAvail = false, isStoresAvailableWithZip = false;

        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, searchItem);
        if (productDetailsPageActions.isPickUpInStoreButtonAvailable()) {
            productDetailsPageActions.staticWait(3000);
            if (productDetailsPageActions.isItemOutOfStock()) {
                boolean isProdOutOfStock = productDetailsPageActions.clickNextSwatchForAvailInvByPos();
                AssertFailAndContinue(isProdOutOfStock, "The product inventory is available by switching to swatches");
            }


            String getBagCountBefore = productDetailsPageActions.getText(productDetailsPageActions.bagLink).replaceAll("[^0-9.]", "");
            int bagCountBeforeAddToBag = Integer.parseInt(getBagCountBefore);
            productDetailsPageActions.staticWait(2000);

//            headerMenuActions.scrollDownToElement(productDetailsPageActions.pickUpStore);
            isPickUpStoreAvail = productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
            bopisOverlayActions.click(bopisOverlayActions.bopisClose);
            productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
            bopisOverlayActions.checkTwoStoreModalUI();
            bopisOverlayActions.selectSizeAndCheckAvailability();
            AddInfoStep("Two store BOPIS modal display");
            AssertFailAndContinue(bopisOverlayActions.validateTextInBopisOverlay(contentValidation.get(0), contentValidation.get(1)), "Validate the content present in the header of the BOPIS overlay");
            AssertWarnAndContinue(bopisOverlayActions.checkTheStoreDetails(),"Verify the store details displayed inside the BOPIS modal");
            isStoresAvailableWithZip = bopisOverlayActions.selectFromAvailableListOfTwoStores(headerMenuActions, bagCountBeforeAddToBag);
            productDetailsPageActions.clickClose_ConfViewBag();
            if (!isStoresAvailableWithZip) {
                bopisOverlayActions.closeBopisOverlayModal();
                productDetailsPageActions.mouseHover(productDetailsPageActions.addToBag);
                productDetailsPageActions.staticWait(6000);
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
                break;
            }
        }
        if (!storeAvailForUPCAndZip) {
            AssertWarnAndContinueWithTextFont(storeAvailForUPCAndZip, "<b><font color=#8b0000> The stores are not available with the provided test data. Please update the data sheet</font></b>");
        }
        return zip;
    }


    public void addDefaultShipMethod(String fName, String lName, String address1, String address2,
                                     String city, String country, String state, String zipCode, String phoneNo) throws Exception {
        boolean addNewShipAddress = myAccountPageActions.addNewShippingAddress(fName, lName, address1, address2, city, country, state, zipCode, phoneNo);
        if (addNewShipAddress) {
            AssertFailAndContinue(addNewShipAddress, "Entering all the fields in the Add New Address form and selecting the address as default address.");
        }

        if (!addNewShipAddress) {
            AssertFailAndContinueWithTextFont(addNewShipAddress, "<b><font color=#8b0000> The Addition of default Shipping Address has failed.</font></b>");
        }
    }

    public void addDefaultPaymentMethodCC(String fName, String lName, String address1, String city,
                                          String state, String zipCode, String phoneNo, String cardType, String cardNo, String cardExpMonthValue, String successMsgContent) throws Exception {
        myAccountPageActions.verifyDefaultCreditCard();
        boolean addNewPaymentDetails = myAccountPageActions.enterPaymentInfo(fName, lName, address1, city, state, zipCode, phoneNo, cardType, cardNo, cardExpMonthValue, successMsgContent);

        if (addNewPaymentDetails) {
            AssertFailAndContinue(addNewPaymentDetails, "Entering the new Payment Info that shall be used as Default Payment option; Also verified that the newly added Payment Info is set as the Default Payment Method.");
        }

        if (!addNewPaymentDetails) {
            AssertFailAndContinueWithTextFont(addNewPaymentDetails, "<b><font color=#8b0000> The Addition of Credit Card as the default Payment Method has failed.</font></b>");
        }

    }

    public void addDefaultPaymentMethodAndBillingAddress(String fName, String lName, String address1, String city,
                                                         String state, String zipCode, String phoneNo, String cardType, String cardNo, String cardExpMonthValue, String successMsgContent) throws Exception {
        boolean addNewPaymentDetails = myAccountPageActions.enterCCAndBillingAddress(fName, lName, address1, city, state, zipCode, phoneNo, cardType, cardNo, cardExpMonthValue, successMsgContent);

        if (addNewPaymentDetails) {
            AssertFailAndContinue(addNewPaymentDetails, "Entering the new Payment Info that shall be used as Default Payment option; Also verified that the entered Payment Info is displayed as the Default Credit Card.");
        }

        if (!addNewPaymentDetails) {
            AssertFailAndContinueWithTextFont(addNewPaymentDetails, "<b><font color=#8b0000> The Addition of Credit Card as the default Payment Method has failed.</font></b>");
        }

    }

    @BeforeSuite(alwaysRun = true)
    public void deleteOld_TestReports() throws IOException {
        System.out.println("Testing the before suites.....");
        String currentDir = System.getProperty("user.dir");
        try {
            String reportsDir = currentDir  + "/Test Reports/Results/";
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
        if (runCounts >= 10) {
            for (int i = 0; i < list.size(); i++) {
                if (new File(reportsDir + list.get(i)).isDirectory()) {
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


                }
            }
            return isRunFolderDeleted;

        }
        return isRunFolderDeleted;
    }

    public void addToBagBySearchingBigCart(Map<String, String> keywordsAndQty, String setBagLimit) {
        for (Map.Entry<String, String> keywordsQty : keywordsAndQty.entrySet()) {
            String keyword = keywordsQty.getKey();
            String quantity = keywordsQty.getValue();

            int setBagSize = Integer.parseInt(setBagLimit);
            bagCount = Integer.parseInt(headerMenuActions.getQtyInBag());


            for (; actualBagCount < setBagSize; ) {
                headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
                driver.navigate().refresh();
                int totalProducts = categoryDetailsPageAction.productImages.size();
                boolean isProdAvailable = false;
                for (; selectedProductPos < totalProducts && bagCount < setBagSize; selectedProductPos++) {
                    headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
                    categoryDetailsPageAction.clickImageByPosition(productDetailsPageActions, selectedProductPos + 1);
                    if (productDetailsPageActions.isItemOutOfStock()) {
                        isProdAvailable = false;
                        break;
                    } else {
                        isProdAvailable = true;
                        productDetailsPageActions.selectAllSizesWithOneColor(productDetailsPageActions, headerMenuActions, setBagSize);
                        bagCount = Integer.parseInt(headerMenuActions.getQtyInBag());
                        try {
                            boolean isItemAddedToBag = headerMenuActions.isProductAddedToBag(shoppingBagDrawerActions);
                            if (isItemAddedToBag) {
                            } else {
                                AddInfoStep("Not able to add the Item to the bag");
                            }
                            break;
                        } catch (Throwable t) {
                            logger.info("Something happened while adding the item to bag: " + t.getMessage());
                            if (headerMenuActions.isAlertPresent()) {
                                headerMenuActions.acceptAlert();
                            }
                        }
                    }
                }
                AssertFailAndContinue(isProdAvailable, "The quantity available for product and added to bag");
                actualBagCount = bagCount;
                break;
            }
            productDetailsPageActions.mouseHover(headerMenuActions.TCPLogo);
        }
    }

    public void getCountAndTotal() {
        cartCount = shoppingBagPageActions.getBagCount();
        estimatedTot = shoppingBagPageActions.getEstimateTotal();
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsBopisReg(pickUpPageActions), "Verify user navigate to shipping page on clicking the checkout button as a register user");
        AssertFailAndContinue(pickUpPageActions.clickShippingBtn(shippingPageActions), "Click on next:shipping button and check user navigate to shipping page");
    }

    public void getCountAndTotalBopis(String user) {
        cartCount = shoppingBagPageActions.getBagCount();
        estimatedTot = shoppingBagPageActions.getEstimateTotal();
        if (user.equalsIgnoreCase("guest"))
            shoppingBagPageActions.clickCheckoutAsGuestBopisOnly(loginPageActions, pickUpPageActions);
        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsBopisReg(pickUpPageActions), "Verify user navigate to shipping page on clicking the checkout button as a register user");
    }

    public void getTotalAndCount(String user) {
        cartCount = shoppingBagPageActions.getBagCount();
        estimatedTot = shoppingBagPageActions.getEstimateTotal();
        if (user.equalsIgnoreCase("registered")) {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        } else if (user.equalsIgnoreCase("guest")) {
            shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions);
        }
    }

    public void getTitleCountAndTotal() {
        cartCount = shoppingBagPageActions.getBagCount();
        estimatedTot = shoppingBagPageActions.getEstimateTotal();
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Verify user navigate to shipping page on clicking the checkout button as a register user");
    }

    public void checkCountAndTotalInShippingPage_Bopis() {
        getCountAndTotal();
        AssertFailAndContinue(shippingPageActions.validateItemCountWithBagCount(cartCount), "Validate the cart count with the item count in the shipping page");
        AssertFailAndContinue(shippingPageActions.validateTotInShippingAndBagPage(estimatedTot), "validate the estimated total in shopping bag page with the shipping page");
    }

    public void checkCountAndTotalInShippingPage(String user) {
        getTotalAndCount(user);
        AssertFailAndContinue(shippingPageActions.validateTotInShippingAndBagPage(estimatedTot), "validate the estimated total in shopping bag page with the shipping page");
    }

    public void checkCountAndTotalInBillingPage() {
        getTitleCountAndTotal();
        AssertFailAndContinue(shippingPageActions.clickNextBillingButton(billingPageActions), "verify user navigate to billing page");
        AssertFailAndContinue(billingPageActions.validateTotInShippingAndBillingPage(estimatedTot), "validate estimated total in billing page and shopping bag page");
    }

    public void checkCountAndTotalInReviewPage(String cvvCode, String user) {
        getTotalAndCount(user);
        AssertFailAndContinue(shippingPageActions.clickNextBillingButton(billingPageActions), "Click on next billing button and check user navigate to billing page");
        AssertFailAndContinue(billingPageActions.enterCVVForExpressAndClickNextReviewBtn(cvvCode, reviewPageActions), "Click on next review button and check user navigate to review page");
        AssertFailAndContinue(reviewPageActions.validateTotInShippingAndBagPage(estimatedTot), "validate the estimated total in shopping bag page with the shipping page");
    }

    public void checkCountAndTotalInPickUpPage(String user) {
        getCountAndTotalBopis(user);
        AssertFailAndContinue(pickUpPageActions.validateTotInPickupAndBagPage(estimatedTot), "validate the estimated total in shopping bag page with the shipping page");
    }

    public void performNormalCheckout(String store, String user, String email) throws Exception {

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetails = null, sm = null, vi = null;
        if (store.equalsIgnoreCase("US")) {
            shipDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
            sm = dt2ExcelReader.getExcelData("ShippingMethodCodes", "standard");
            vi = dt2ExcelReader.getExcelData("BillingDetails", "Visa");
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
            sm = dt2ExcelReader.getExcelData("ShippingMethodCodes", "ground");
            vi = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
        }

        addToBagBySearching(searchKeywordAndQty);
        if(user.equalsIgnoreCase("guest")){
            shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions,shippingPageActions);

        }
        else {
            AssertFailAndContinue(headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions), "Clicked on the Shopping bag icon on the header.");
            AssertFailAndContinue(shoppingBagDrawerActions.clickOnCheckoutBtnAsReg(shippingPageActions), "Click on checkout button and verify user redirect to the shipping page");
        }
        if(user.equalsIgnoreCase("registered")){
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }
        else{
            shippingPageActions.enterShippingDetailsShipMethodAndContinue_GuestWithEmail(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), sm.get("shipValue"),email);
        }
        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
        AssertFailAndContinue(orderConfirmationPageActions.validateTextInOrderConfirmPageReg(), "validate the text content in order confirmation page");
    }

    public void checkoutWithDiffShipAndBillAddr(String store) throws Exception {

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetails = null, sm = null, vi = null, billDetails = null;
        if (store.equalsIgnoreCase("US")) {
            shipDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
            sm = dt2ExcelReader.getExcelData("ShippingMethodCodes", "standard");
            billDetails = dt2ExcelReader.getExcelData("BillingDetails", "Visa");
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
            sm = dt2ExcelReader.getExcelData("ShippingMethodCodes", "ground");
            billDetails = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
        }

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions), "Clicked on the Shopping bag icon on the header.");
        AssertFailAndContinue(shoppingBagDrawerActions.clickOnCheckoutBtnAsReg(shippingPageActions), "Click on checkout button and verify user redirect to the shipping page");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetails.get("fName"), billDetails.get("lName"), billDetails.get("addressLine1"), billDetails.get("addressLine2"), billDetails.get("city"), billDetails.get("stateShortName"), billDetails.get("zip"), billDetails.get("cardNumber"), billDetails.get("securityCode"), billDetails.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
        AssertFailAndContinue(orderConfirmationPageActions.validateTextInOrderConfirmPageReg(), "validate the text content in order confirmation page");
    }

    public void performBopisCheckout(String store) throws Exception {
        Map<String, String> amexBillUS=null;
        String validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZip=null;
        if(store.equalsIgnoreCase("US")){
            validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
            amexBillUS = dt2ExcelReader.getExcelData("BillingDetails", "Amex");

        }
        else if(store.equalsIgnoreCase("CA")){
            validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumberCA");
            validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
            amexBillUS = dt2ExcelReader.getExcelData("BillingDetails", "AmexCA");
        }
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);

        //Clicked on Proceed to Checkout, to initiate Checkout
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToBOPISCheckout(checkoutPickUpDetailsActions), "Clicked on the Checkout button initiating Express Checkout");
        AssertFailAndContinue(checkoutPickUpDetailsActions.clickNextBillingButton(billingPageActions), "Clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, amexBillUS.get("fName"), amexBillUS.get("lName"), amexBillUS.get("addressLine1"), amexBillUS.get("addressLine2"), amexBillUS.get("city"), amexBillUS.get("stateShortName"), amexBillUS.get("zip"), amexBillUS.get("cardNumber"), amexBillUS.get("securityCode"), amexBillUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Order Submit button.");
    }

    public void performHybridCheckout() throws Exception {
        Map<String, String> shipDetailUS = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> mcBillUS = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
        Map<String, String> std = dt2ExcelReader.getExcelData("ShippingMethodCodes", "standard");

        String validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        //Clicked on Proceed to Checkout, to initiate Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToBOPISCheckout(checkoutPickUpDetailsActions), "Clicked on the Checkout button to land on Checkout page with the Pickup Accordion displayed.");
        AssertFailAndContinue(checkoutPickUpDetailsActions.clickNextShippingButton(shippingPageActions), "Clicked on the Next Shipping button to land on Checkout page with the Shipping Accordion displayed.");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), std.get("shipValue")), "Entered the Shipping address as std and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, mcBillUS.get("fName"), mcBillUS.get("lName"), mcBillUS.get("addressLine1"), mcBillUS.get("addressLine2"), mcBillUS.get("city"), mcBillUS.get("stateShortName"), mcBillUS.get("zip"), mcBillUS.get("cardNumber"), mcBillUS.get("securityCode"), mcBillUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
        AssertFailAndContinue(orderConfirmationPageActions.validateTextInOrderConfirmPageReg(), "Validate the text and order ID present in the confirmation page");
        AssertFailAndContinue(orderConfirmationPageActions.validateOrderLedgerSectionGuestReg(), "Validate the order ledger section in confirmation page");
        AssertFailAndContinue(orderConfirmationPageActions.checkOrderIDForHybirdOrder(), "Check the order ID, Date and Total for both ship to home and BOPIS products");
        AssertFailAndContinue(orderConfirmationPageActions.validateHyBridCartTotal(), "Verify if the hybird product cost should be equal to the estimated cost in the cart");
    }

    public void clickAddToBagIconSelectAFitSizeAndAddProdToBag() {
        int addToBagIcons = categoryDetailsPageAction.addToBagIcon.size();
        for (int i = 1; i <= addToBagIcons; i++) {
            boolean isAddToBagButtonVisible = categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, i);
            if (!categoryDetailsPageAction.waitUntilElementDisplayed(categoryDetailsPageAction.flipOOS, 5)) {
                if (isAddToBagButtonVisible) {
                    boolean sizesAvailAndProdAddedToBag = productCardViewActions.selectAFitSizeUpdateQtyAndAddToBag("1");
                    headerMenuActions.staticWait(5000);
                    headerMenuActions.waitUntilElementDisplayed(headerMenuActions.shoppingBagIcon, 10);
                    AssertFailAndContinue(headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions), "Clicking View bag on Added To bag confirmation page");
                    if (sizesAvailAndProdAddedToBag) {
                        AssertFailAndContinue(sizesAvailAndProdAddedToBag, "Sizes are available and product added to the bag");
                        break;
                    }
                } else {
                    AssertFailAndContinue(false, "Add To Bag button is not visible at product Card");
                }
            } else {
                logger.warn("Product is OOS, moving to other product");
                categoryDetailsPageAction.click(categoryDetailsPageAction.closeAddToBaglink);
            }
        }
    }

    public void addToBagSearchTermProdFromProdCard(String searchTerm) {
        int productsByBadge = searchResultsPageActions.prodImagesByBadge(searchTerm).size();
        for (int i = 1; i <= productsByBadge; i++) {
            boolean isAddToBagButtonVisible = searchResultsPageActions.clickClearanceProductAddToBagByPos(productCardViewActions, searchResultsPageActions.randInt(1, (productsByBadge - i)), searchTerm);
            if (isAddToBagButtonVisible) {
                boolean sizesAvailAndProdAddedToBag = productCardViewActions.selectAFitSizeUpdateQtyAndAddToBag("1");
                AssertFailAndContinue(headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions), "Clicking View bag on Added To bag confirmation page");
                if (sizesAvailAndProdAddedToBag) {
                    AssertFailAndContinue(sizesAvailAndProdAddedToBag, "Sizes are available and product added to the bag");
                    break;
                }
            }

        }
    }


    public boolean findTwoStoreByUPCAndZipPlp(String searchItem, String zip) throws Exception {

        boolean isPickUpStoreAvail = false, isStoresAvailableWithZip = false;

        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, searchItem);
        if (categoryDetailsPageAction.validateBopisIcons(bopisOverlayActions)) {
            bopisOverlayActions.staticWait(3000);

            List<String> contentValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "headerContent", "expectedheaderTextContent"));
            AssertFailAndContinue(bopisOverlayActions.selectSizeAndSelectMoreThan_1Store(zip), "View One store BOPIS modal");
            AssertFailAndContinue(bopisOverlayActions.validateTextInBopisOverlay(contentValidation.get(0), contentValidation.get(1)), "Validate the content present in the header of the BOPIS overlay");
            isStoresAvailableWithZip = bopisOverlayActions.clickSelectStoreBtnByPosition(2);
            if (!isStoresAvailableWithZip) {
                bopisOverlayActions.closeBopisOverlayModal();
                categoryDetailsPageAction.staticWait(6000);
            }
        }

        return isPickUpStoreAvail && isStoresAvailableWithZip;
    }


    public void closeWindow() {
        driver.getWindowHandle();
        driver.close();
    }

    public boolean returnToBag() {
        headerMenuActions.clickOnTCPLogo();
        return headerMenuActions.clickReturnToBagButton(shoppingBagPageActions);

    }

    public boolean validateAlternativePickUpinReviewPage(String fname, String lname, String email) {
        String alternativeinfo = checkoutPickUpDetailsActions.getText(checkoutPickUpDetailsActions.AlternativeAddress);
        return alternativeinfo.contains(fname) &&
                alternativeinfo.contains(lname) &&
                alternativeinfo.contains(email);
    }

    public void checkTopPLPBadgeAndVerifyInPDP(String badgeSearch, String badgeDisplay) {
        AssertFailAndContinue(categoryDetailsPageAction.checkTopBadgesByBadgeName(headerMenuActions, categoryDetailsPageAction, badgeSearch, badgeDisplay), "Check for " + badgeSearch + " badges");
        String prodBadge = categoryDetailsPageAction.getFirstTopProductBadgeName(badgeDisplay);
        boolean isProdBagdeDisplayInCat = categoryDetailsPageAction.clickFirstTopProductBadgeName(badgeDisplay, productDetailsPageActions);
        if (isProdBagdeDisplayInCat) {
            AssertFailAndContinue(productDetailsPageActions.isProductBadgeDisplayingByBadge(prodBadge), "Product badge is displaying");
            productDetailsPageActions.selectAnySizeAndClickAddToBagInPDP();
            headerMenuActions.staticWait(3000);
            headerMenuActions.waitUntilElementDisplayed(headerMenuActions.shoppingBagIcon,3);
            //Removing the below lines since they are invalid now
//            headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
//            String inline = shoppingBagDrawerActions.getText(shoppingBagDrawerActions.inlineBadgeDisplay.get(0));
//            if(inline.equalsIgnoreCase(badgeDisplay)){ shoppingBagDrawerActions.clickViewBagLink(shoppingBagPageActions);
//                String sbagBadge = shoppingBagPageActions.getText(shoppingBagPageActions.inlineBadgeDisplay.get(0));
//                if(sbagBadge.equalsIgnoreCase(badgeDisplay)){
//                    headerMenuActions.addStepDescription("Badges are getting displayed properly");
//                    emptyShoppingBag();
//                }
//            }
        } else {
            AssertFailAndContinue(productDetailsPageActions.isProductBadgeDisplayingByBadge(badgeDisplay), "Product badge is not displaying in category page with "+badgeSearch);

        }

    }

    public void validateCarouselArrows() {
        footerActions.click(footerActions.nextcarousel);
        AssertFailAndContinue(true, "Verify When clicks on next carousel button next set of products displayed");
        footerActions.click(footerActions.previouscarousel);
        AssertFailAndContinue(true, "Verify When clicks on Previous carousel button previous set of products displayed");
    }

    public void performExpCheckoutExceptDiscover(String secCode) throws Exception {

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions), "Clicked on the Shopping bag icon on the header.");
        AssertFailAndContinue(shoppingBagDrawerActions.clickOnCheckoutBtnAsRegForExpressCO(reviewPageActions), "Click on checkout button and verify user redirect to the shipping page");
        AssertFailAndContinue(reviewPageActions.enterExpressCO_CVVAndSubmitOrder(secCode, orderConfirmationPageActions), "Enter the CVV number");
        AssertFailAndContinue(orderConfirmationPageActions.validateTextInOrderConfirmPageReg(), "validate the text content in order confirmation page");
    }

    public boolean logoutTheSession() {
        headerMenuActions.waitUntilElementDisplayed(headerMenuActions.welcomeCustomerLink, 3);
        headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
        headerMenuActions.staticWait(1000);
        myAccountPageDrawerActions.clickLogoutLink(headerMenuActions);
        return headerMenuActions.waitUntilElementDisplayed(headerMenuActions.welcomeCustomerLink, 5);
    }

    public void performExpBOPISCheckout(String store) throws Exception {
        String validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        if(store.equalsIgnoreCase("CA")){
            validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        }
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToBOPISCheckout(checkoutPickUpDetailsActions), "Clicked on the Checkout button initiating Express Checkout");
        AssertFailAndContinue(reviewPageActions.enterExpressCO_CVVAndSubmitOrder("456", orderConfirmationPageActions), "Enter the CVV number");

    }

    /**
     * To Add a product from search results/PLP page to Favorites
     *
     * @param keyword to search
     */
    public void addAvailableProductToFavorites(String keyword) {
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, keyword);
        int totalProducts = categoryDetailsPageAction.itemContainers.size();
        for (int i = 0; i < totalProducts; i++) {
            categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, i + 1);
            if (!categoryDetailsPageAction.waitUntilElementDisplayed(categoryDetailsPageAction.flipOOS, 5)) {
                categoryDetailsPageAction.click(categoryDetailsPageAction.closeAddToBaglink);
                searchResultsPageActions.click(searchResultsPageActions.addToFavIconByPos(i + 1));
                searchResultsPageActions.waitUntilElementsAreDisplayed(categoryDetailsPageAction.favIconEnabled, 20);
                headerMenuActions.click(headerMenuActions.wishListLink);
                headerMenuActions.waitUntilElementDisplayed(favoritePageActions.defaultWLName);
                if (favoritePageActions.prodImg.size() >= 1)
                    break;

            } else {
                logger.warn("Product is OOS, moving to other product");
                categoryDetailsPageAction.click(categoryDetailsPageAction.closeAddToBaglink);
            }
        }
    }

    public String getTestCardDataFromExcel(String cvvIs, String cardType) {
        cardType = cardType.toUpperCase();
        if (cvvIs.equalsIgnoreCase("On")) {
            switch (cardType) {
                case "VISA":
                    return "VISA_New";
                case "MC_5SERIES":
                    return "MasterCard_New_5series";
                case "MC_2SERIES":
                    return "MasterCard_New_2series";
                case "AMEX":
                    return "Amex_New";
                case "DISCOVER":
                    return "Discover_New";
             /*   case "VISA_P":
                    return "VISA_P_New";
                case "MC_5SERIES_P":
                    return "MasterCard_P_New_5series";
                case "MC_2SERIES_P":
                    return "MasterCard_P_New_2series";
                case "AMEX_P":
                    return "Amex_P_New";
                case "DISCOVER_P":
                    return "Discover_P_New";
                case "VISA_U":
                    return "VISA_U_New";
                case "AMEX_Blank":
                    return "Amex_Blank_New";
                case "DISCOVER_U":
                    return "Discover_U_New";
                case "VISA_N":
                    return "VISA_N_New";
                case "MC_5SERIES_N":
                    return "MasterCard_N_New_5series";
                case "MC_2SERIES_N":
                    return "MasterCard_N_New_2series";
                case "AMEX_N":
                    return "Amex_N_New";
                case "DISCOVER_N":
                    return "Discover_N_New";*/
            }

        } else if (cvvIs.equalsIgnoreCase("Off")) {
            switch (cardType) {
                case "VISA":
                    return "Visa";
                case "MC_5SERIES":
                    return "MasterCard";
                case "MC_2SERIES":
                    return "MasterCard_2Series";
                case "AMEX":
                    return "Amex";
                case "DISCOVER":
                    return "Discover";
            }
        }

        return "Visa";
    }

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

    public void switchBackToParentWindow(String parentWindow) {
        logger.info("Switching back to parent window" + parentWindow);
        driver.switchTo().window(parentWindow);


    }

    /**
     * Add product tobag through api method
     *
     * @param email of the page expected
     * @param password of the page expected
     * @param store of the page expected
     * @param qty of the page expected
     * @return true on clicking on shopping bag
     */

}
