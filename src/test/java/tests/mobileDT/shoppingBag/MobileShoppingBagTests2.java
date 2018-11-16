package tests.mobileDT.shoppingBag;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Test(singleThreaded = true)
public class MobileShoppingBagTests2 extends MobileBaseTest {

    WebDriver mobileDriver;
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String email = UiBaseMobile.randomEmail(), password = "";
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                email = UiBaseMobile.randomEmail();
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        mheaderMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
    }
    
    @Test(priority = 0, dataProvider = dataProviderName, groups= {SHOPPINGBAG, REGISTEREDONLY, PROD_REGRESSION} )
    public  void verifyBagRetention(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the guest user adds some products to bag and then makes bag count as 0 by removing all products. Then he logs in "
        		+ "with registered account which already had products in bag, then registered user bag should not get dumped. :DT-40357");

        AddInfoStep("Log in as registered user and add product to the bag");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        
        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        
        addToBagFromPDP(searchKeywordAndQty);
        
        String productNameInBagForRegUser = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name added in bag for registered user: " + productNameInBagForRegUser);
        
        panCakePageActions.navigateToMenu("USER");
        mmyAccountPageDrawerActions.clickLogoutLink(mheaderMenuActions);
        
        AddInfoStep("Now as a guest user add some product to the bag and then remove it to make bag count 0");
        searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Value");
        qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Qty");
        searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        String productNameInBagForGuestUser = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name added in bag for guest user: " + productNameInBagForGuestUser);
        
        AddInfoStep("Now empty the shopping bag and login as registered user which has product already added in account");
        mshoppingBagPageActions.validateEmptyShoppingCart();
        AddInfoStep("Shopping bag for guest user is now empty, now login with the same registered user account which has product present in bag");
        
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        
        mheaderMenuActions.clickShoppingBagIcon();
        String productNameInBag = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name added in bag for registered user after logging in again: " + productNameInBag);
        
        AssertFailAndContinue(productNameInBagForRegUser.equals(productNameInBag), " verify that the products that were already present in the registered user's shopping bag are retained (and not dumped).");
    }
   
    @Test(priority = 1, dataProvider = dataProviderName, groups= {SHOPPINGBAG, PDP, PROD_REGRESSION} )
    public  void verifyErrorOnAddingMoreThan15Skus(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + "Verify error message is seen when quantity limit for sku is reached.: DT-37342");

        AddInfoStep("Log in as registered user and add product with quantity 15 to the bag");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        
        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTermWithMaxQuantity", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTermWithMaxQuantity", "Qty");
        String sizeToSelect = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTermWithMaxQuantity", "Size");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        selectSizeFitQtyOnPDPPage(searchKeywordAndQty, sizeToSelect, "");
        mproductDetailsPageActions.clickAddToBag();
        mheaderMenuActions.clickShoppingBagIcon();
        AddInfoStep("Product has been added to bag with maximum sku quantity");
        
        String productNameInBag = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name added in bag: " + productNameInBag);
        
        AddInfoStep("Again add the same sku to bag");
        selectSizeFitQtyOnPDPPage(searchKeywordAndQty, sizeToSelect, "");
        mproductDetailsPageActions.clickAddToBagQtyLimErr(mproductDetailsPageActions);
        String qtyLimitMessagePDP = getmobileDT2CellValueBySheetRowAndColumn("ShoppingBagErrMessages", "QtyLimitMessagePDP", "ErrorMessages");
        AssertFailAndContinue(mcategoryDetailsPageAction.getErrorMessage().contains(qtyLimitMessagePDP), "Verify error message is seen when quantity limit for sku is reached.");
        AssertFailAndContinue(mheaderMenuActions.getQty().equals("15"), "Verify cart count remains as 15 only.");
    }
    
    @Test(priority = 2, dataProvider = dataProviderName, groups= {SHOPPINGBAG, REGISTEREDONLY, PROD_REGRESSION} )
    public  void verifyErrorOnAddingMoreThan15SkusToFav(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + "Verify error message is seen when quantity limit for sku is reached in favorites : DT-40349");

        AddInfoStep("Log in as registered user and add product with quantity 15 to the bag");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        
        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTermWithMaxQuantity", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTermWithMaxQuantity", "Qty");
        String sizeToSelect = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTermWithMaxQuantity", "Size");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        selectSizeFitQtyOnPDPPage(searchKeywordAndQty, sizeToSelect, "");
        mproductDetailsPageActions.clickAddToBag();
        mheaderMenuActions.clickShoppingBagIcon();
        AddInfoStep("Product has been added to bag with maximum sku quantity");
        
        String productNameInBag = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name added in bag: " + productNameInBag);
        
        mshoppingBagPageActions.moveProdToWLByPositionAsReg(1);
        AddInfoStep("Shopping bag is now empty");
        
        AddInfoStep("Again add the same sku to bag and add it to fav by clicking on the heart icon");
        selectSizeFitQtyOnPDPPage(searchKeywordAndQty, sizeToSelect, "");
        mproductDetailsPageActions.clickAddToBag();
        mheaderMenuActions.clickShoppingBagIcon();
        mshoppingBagPageActions.moveProdToWLByPositionErr(1);
        
        String qtyLimitErrMessage = getmobileDT2CellValueBySheetRowAndColumn("ShoppingBagErrMessages", "QtyLimitMessage", "ErrorMessages");
        AssertFailAndContinue(mshoppingBagPageActions.getErrorMessage().contains(qtyLimitErrMessage), "Verify error message is seen when more than 15 skus are getting added to favorites.");
    }
    
    @Test(priority = 3, dataProvider = dataProviderName, groups= {SHOPPINGBAG, REGISTEREDONLY, PROD_REGRESSION} )
    public  void verifyMaximumSkuQuantityAs15(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify when a guest user adds sku to bag with quantity as 15 and then logs in to registered user which already has "
        		+ "same sku added with 15 as quantity, verify that total quantity of the SKU in the shopping bag does not exceed 15.");

        AddInfoStep("Log in as registered user and add product to the bag");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        
        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTermWithMaxQuantity", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTermWithMaxQuantity", "Qty");
        String sizeToSelect = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTermWithMaxQuantity", "Size");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        selectSizeFitQtyOnPDPPage(searchKeywordAndQty, sizeToSelect, "");
        mproductDetailsPageActions.clickAddToBag();
        mheaderMenuActions.clickShoppingBagIcon();
        AddInfoStep("Product has been added to bag with maximum sku quantity in registered user");
        
        panCakePageActions.navigateToMenu("USER");
        mmyAccountPageDrawerActions.clickLogoutLink(mheaderMenuActions);
        
        AddInfoStep("Now as a guest user add same sku to bag with maximum quantity");

        selectSizeFitQtyOnPDPPage(searchKeywordAndQty, sizeToSelect, "");
        mproductDetailsPageActions.clickAddToBag();
        mheaderMenuActions.clickShoppingBagIcon();
        AddInfoStep("Product has been added to bag with maximum sku quantity in guest user");
        
        AddInfoStep("Now login as registered user which has same sku present in maximum quantity");
        
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        
        mheaderMenuActions.clickShoppingBagIcon();
        String quantity = mshoppingBagPageActions.getQuantityLabelText();
        
        AssertFailAndContinue(quantity.contains("15"), "Verify that total quantity of the SKU in the shopping bag does not exceed 15.");
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {SHOPPINGBAG, REGISTEREDONLY, USONLY, PROD_REGRESSION})
    public void verifyBagRetentionUSES_USEN(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "Verify that the shopping bag in US store is retained and that the minicart count is not set to zero when user switches from US_ES to US_EN and vice versa: DT-40377, DT-40375");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        
        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }
        
        AddInfoStep("Change language from EN to ES and items to shopping bag");
        mfooterActions.changeCountryAndLanguage("US", "Spanish");
        
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        
        mheaderMenuActions.clickShoppingBagIcon();

        String productNameInBag = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name added in bag: " + productNameInBag);
        
        AddInfoStep("Now change language from ES to EN and verify if shopping bag is retained.");
        mfooterActions.changeCountryAndLanguage("US", "English");
        mheaderMenuActions.clickShoppingBagIcon();

        String productNameInBagAfterChangingLang = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name added in bag: " + productNameInBagAfterChangingLang);
        
        AssertFailAndContinue(Integer.parseInt(mheaderMenuActions.getQty()) > 0 , "Verify mini cart count is not set to zero");
        AssertFailAndContinue(productNameInBag.equals(productNameInBagAfterChangingLang), "Shopping bag should be retained.");
        
        AddInfoStep("Add one more product to bag in US-EN");
        
        searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Value");
        qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Qty");
        searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        
        String productName1InBag = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name 1 added in bag: " + productNameInBag);
        
        String productName2InBag = mshoppingBagPageActions.getProductNames().get(1);
        AddInfoStep("Product name 2 added in bag: " + productNameInBag);
        
        AddInfoStep("Now change from US-EN to US-ES and verify that both the products should be present");
        mfooterActions.changeCountryAndLanguage("US", "Spanish");
        mheaderMenuActions.clickShoppingBagIcon();
        AssertFailAndContinue(Integer.parseInt(mheaderMenuActions.getQty()) == 2 , "Verify mini cart count is not set to zero");
        
        String productName1InBagES = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name 1 present in bag: " + productNameInBagAfterChangingLang);
        
        String productName2InBagES = mshoppingBagPageActions.getProductNames().get(1);
        AddInfoStep("Product name 2 present in bag: " + productNameInBagAfterChangingLang);
        
        AssertFailAndContinue(productName1InBag.equals(productName1InBagES), "Verify product1 is retained in bag");
        AssertFailAndContinue(productName2InBag.equals(productName2InBagES), "Verify product2 is retained in bag");
    }
    
    @Test(priority = 5, dataProvider = dataProviderName, groups = {SHOPPINGBAG, REGISTEREDONLY, CAONLY, PROD_REGRESSION})
    public void verifyBagRetentionCAEN_CAFR(@Optional("CA") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "Verify that the shopping bag in CA store is retained and that the minicart count is not set to zero when user switches from CA_EN to CA_FR and vice versa: DT-40379, DT-40381");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        
        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }
        
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "DenimProductUPCNum2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);	
        
        String productUPCInBag = mshoppingBagPageActions.getUPCNumByPosition(1);
        AddInfoStep("Product UPC added in bag: " + productUPCInBag);
        
        AddInfoStep("Now change language from EN to FR and verify if shopping bag is retained.");
        mfooterActions.changeCountryAndLanguage("CA", "French");
        mheaderMenuActions.clickShoppingBagIcon();

        String productUPCInBagAfterChangingLang = mshoppingBagPageActions.getUPCNumByPosition(1);
        AddInfoStep("Product UPC present in bag after changing language to FR: " + productUPCInBagAfterChangingLang);
        
        AssertFailAndContinue(Integer.parseInt(mheaderMenuActions.getQty()) > 0 , "Verify mini cart count is not set to zero");
        AssertFailAndContinue(productUPCInBag.equals(productUPCInBagAfterChangingLang), "Shopping bag should be retained.");
        
        AddInfoStep("Now change language from FR to EN and verify if shopping bag is retained.");
        AddInfoStep("Now one more product to bag");
        
        searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Value");
        qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Qty");
        searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        
        String productUPC1InBag = mshoppingBagPageActions.getUPCNumByPosition(1);
        AddInfoStep("Product UPC1 added in bag: " + productUPC1InBag);
        
        String productUPC2InBag = mshoppingBagPageActions.getUPCNumByPosition(2);
        AddInfoStep("Product UPC2 added in bag: " + productUPC2InBag);
        
        mfooterActions.changeCountryAndLanguage("CA", "English");
        mheaderMenuActions.clickShoppingBagIcon();
        
        String productUPC1InBagAfterChangingtoEN = mshoppingBagPageActions.getUPCNumByPosition(1);
        AddInfoStep("Product UPC1 added in bag: " + productUPC1InBagAfterChangingtoEN);
        
        String productUPC2InBagAfterChangingtoEN = mshoppingBagPageActions.getUPCNumByPosition(2);
        AddInfoStep("Product UPC2 added in bag: " + productUPC2InBagAfterChangingtoEN);
        
        AssertFailAndContinue(productUPC1InBagAfterChangingtoEN.equals(productUPC1InBag), "Verify product1 is retained in bag");
        AssertFailAndContinue(productUPC2InBagAfterChangingtoEN.equals(productUPC2InBag), "Verify product2 is retained in bag");
    }

    @Test(priority = 6, dataProvider = dataProviderName, groups = {CHECKOUT,REGISTEREDONLY})
    public void validatePromotionChkOutFrmSB(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String,String> mailingDetails = null;
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is able to see promotion details in order ledger with negative sign when checkout is performed from shopping bag drawer");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        Map<String, String> associateDetails = dt2ExcelReader.getExcelData("MyAccount", "ValidAssociateID");

        if(store.equalsIgnoreCase("US")){
            mailingDetails = dt2ExcelReader.getExcelData("MyAccount", "USAddress");
        } if(store.equalsIgnoreCase("CA")){
            mailingDetails = dt2ExcelReader.getExcelData("MyAccount", "CAAddress");
        }
        makeAssociateUser(associateDetails.get("FirstName"), associateDetails.get("LastName"), associateDetails.get("Associate_ID"),mailingDetails.get("AddressLine1"),mailingDetails.get("City"),mailingDetails.get("stateShortName"));

        //makeAssociateUser(associateDetails.get("FirstName"), associateDetails.get("LastName"), associateDetails.get("Associate_ID"));

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        }else{
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(),"Click shopping bag");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        AssertFailAndContinue(mshoppingBagPageActions.getText(mshippingPageActions.promotionsTot).contains("-"), "Verify if promotion amount is present with negative sign in shipping page");

        Map<String, String> usBillingAdd = null;
        Map<String, String> caShippingAdd = null;

        if (store.equalsIgnoreCase("US")) {
            usBillingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                usBillingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            caShippingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                caShippingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }

        mbillingPageActions.enterCardDetails(usBillingAdd.get("cardNumber"), usBillingAdd.get("securityCode"), usBillingAdd.get("expMonthValueUnit"));
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mbillingPageActions.getText(mbillingPageActions.promotionsTot).contains("-"), "Verify if promotion amount is present with negative sign in billing page");
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.promotionsTot).contains("-"), "Verify if promotion amount is present with negative sign in review page");
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.click(mreviewPageActions.submitOrderBtn);
            AssertFailAndContinue(mreceiptThankYouPageActions.getText(mreceiptThankYouPageActions.promotionsTot).contains("-"), "Verify if promotion amount is present with negative sign in confirmation page");
        }
    }


    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitBrowser() {
        mobileDriver.quit();
    }
}
