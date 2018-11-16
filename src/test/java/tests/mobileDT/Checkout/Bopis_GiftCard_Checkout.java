package tests.mobileDT.Checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

public class Bopis_GiftCard_Checkout extends MobileBaseTest{
    WebDriver mobileDriver;
    String email = mcreateAccountActions.randomEmail();
    String password = "";
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env = EnvironmentConfig.getEnvironmentProfile();
    String validUPCNumber, validZip = null;
    Map<String, String> shipAndBillDetails = null;
    Map<String, String> gc = null;
    String username, pwd = "";

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.deleteAllCookies();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelCA, email);
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
            mheaderMenuActions.addStateCookie("ON");

        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {BOPIS, CHECKOUT, RECAPTCHA, GIFTCARD, USONLY})
    public void placeOrder_bopis_MultipleGC(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        Map<String,String> gc_2 = null;

        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify that user is able to place bopis order using multiple gift card as payment method");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (store.equalsIgnoreCase("US")) {
            gc = dt2MobileExcel.getExcelData("GiftCard", "usgc28");
            gc_2 = dt2MobileExcel.getExcelData("GiftCard", "usgc12");
            if (env.equalsIgnoreCase("prod")) {
                gc = dt2MobileExcel.getExcelData("GiftCard", "usgc1_prod");
            }
        }

        if (store.equalsIgnoreCase("CA")) {
            gc = dt2MobileExcel.getExcelData("GiftCard", "cagc28");
            gc_2 = dt2MobileExcel.getExcelData("GiftCard", "cagc12");
            if (env.equalsIgnoreCase("prod")) {
                gc = dt2MobileExcel.getExcelData("GiftCard", "cagc1_prod");
            }
        }
        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCode");
        }

        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCodeCA");
        }
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            emptyShoppingBag();
        }

        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        mheaderMenuActions.clickShoppingBagIcon();

        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
            mpickUpPageActions.enterpickUpDetails_Guest(getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "fName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "lName"),
                    email, getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "phNum"));
        }
        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickProceedToBOPISCheckout();
            mcheckoutPickUpDetailsActions.clickNextBillingButton(mbillingPageActions);
        }

        mbillingPageActions.enterGiftCardDetails(gc.get("Card"), gc.get("Pin"));
        AddInfoStep("gift card1 has been applied");

        mbillingPageActions.click(mbillingPageActions.orderSummaryToggleBtn);
        Float price = 0.00f;
        AssertFailAndContinue(!mbillingPageActions.verifyEstmdTotalOnOrderLedger(price), "Verify that Estimated balance is more than $0.00 on the Order Ledger as gift card with less amount has been applied");
        //DT-35869
        mbillingPageActions.enterGiftCardDetails(gc_2.get("Card"), gc_2.get("Pin"));
        AddInfoStep("gift card2 has been applied.");
        mbillingPageActions.clickNextReviewButton();

        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Placed order with multiple gift cards as payment method");
            AssertFailAndContinue(mreviewPageActions.waitUntilElementDisplayed(mreceiptThankYouPageActions.confirmationTitle), "Verify bopis 2 store Order is placed with multiple gift card as payment method");
            getAndVerifyOrderNumber("placeOrder_bopis_MultipleGC");
        }
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {BOPIS, CHECKOUT,GIFTCARD,RECAPTCHA})
    public void bopisItem_AddedFromFavorite_GC_CC(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store.toUpperCase() + " store, Add a bopis eligible product to the Favorites and then add this product to the Shopping Bag, " +
                "Convert the ship it product to bopis product and verify whether user is able to place order with combination of GC and CC as payment method"+"DT-37425");

        if (store.equalsIgnoreCase("US")) {
            gc = dt2MobileExcel.getExcelData("GiftCard", "usgc24");
            if (env.equalsIgnoreCase("prod")) {
                gc = dt2MobileExcel.getExcelData("GiftCard", "usgc1_prod");
            }
        }

        if (store.equalsIgnoreCase("CA")) {
            gc = dt2MobileExcel.getExcelData("GiftCard", "cagc24");
            if (env.equalsIgnoreCase("prod")) {
                gc = dt2MobileExcel.getExcelData("GiftCard", "cagc1_prod");
            }
        }
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCode");
        }
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCodeCA");
        }

        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);
        addToFavoritesFromPDP(validUPCAndZip);

        panCakePageActions.navigateToMenu("FAVORITES");
        mobileFavouritesActions.addProductToBagByPosition(1);
        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
        } else {
            AssertFail(false, "Something went wrong while adding product to bag from favorite list. Aborting this method run ");
        }

        mshoppingBagPageActions.selectPickUpButtonForShipItProduct(mbopisOverlayActions);
        mbopisOverlayActions.selectSizeAndSelectStore(validZip);
        mbopisOverlayActions.selectFromAvailableStores(mheaderMenuActions, 0);
        //Now convert the product to bopis product and then place order.
        Map<String, String> billingDetails = null;

        if(store.equalsIgnoreCase("US")){
            billingDetails = dt2MobileExcel.getExcelData("BillingDetails", "Visa");
        }
        if(store.equalsIgnoreCase("CA")){
            billingDetails = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
            mpickUpPageActions.enterpickUpDetails_Guest(billingDetails.get("fName"),billingDetails.get("lName"), email, billingDetails.get("phNum"));
        }

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickProceedToBOPISCheckout();
            mcheckoutPickUpDetailsActions.clickNextBillingButton(mbillingPageActions);
        }

        mbillingPageActions.enterGiftCardDetails(gc.get("Card"), gc.get("Pin"));

        AddInfoStep("Enter and apply gift card");

        mbillingPageActions.expandOrderSummary();
        Float price = 0.00f;
        AssertFailAndContinue(!mbillingPageActions.verifyEstmdTotalOnOrderLedger(price), "Verify that Estimated balance is more than $0.00 on the Order Ledger as gift card with less amount has been applied");

        mbillingPageActions.enterCardDetails(billingDetails.get("cardNumber"), billingDetails.get("securityCode"), billingDetails.get("expMonthValueUnit"));

        mbillingPageActions.fillPaymentAddrDetailsWithoutSameAsShip(billingDetails.get("fName"), billingDetails.get("lName"), billingDetails.get("addressLine1"),
                billingDetails.get("city"), billingDetails.get("stateShortName"), billingDetails.get("zip"));
        mbillingPageActions.clickNextReviewButton();
        //DT-35883
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Placed order with gift card and credit card as payment method");
            AssertFailAndContinue(mreviewPageActions.waitUntilElementDisplayed(mreceiptThankYouPageActions.confirmationTitle), "Verify BOPIS Order is placed with gift card and credit card as payment method");
            getAndVerifyOrderNumber("bopisItem_AddedFromFavorite_GC_CC");
        }
    }

    @Test(priority = 2,dataProvider = dataProviderName, groups = {CHECKOUT, GIFTCARD, BOPIS, PAYPAL,RECAPTCHA})
    public void bopis_GC_Paypal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store.toUpperCase() + " store, verify - <br/>" +
                "1. User is able to place BOPIS order with combination of GC and PayPal as payment method");

        if (store.equalsIgnoreCase("US")) {
            gc = dt2MobileExcel.getExcelData("GiftCard", "usgc28");
            if (env.equalsIgnoreCase("prod")) {
                gc = dt2MobileExcel.getExcelData("GiftCard", "usgc1_prod");
            }
        }

        if (store.equalsIgnoreCase("CA")) {
            gc = dt2MobileExcel.getExcelData("GiftCard", "cagc28");
            if (env.equalsIgnoreCase("prod")) {
                gc = dt2MobileExcel.getExcelData("GiftCard", "cagc1_prod");
            }
        }
        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCode");
        }

        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCodeCA");
        }
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        if(store.equalsIgnoreCase("US")){
            username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "USPayPal", "UserName");
            pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "USPayPal", "Password");

        }
        if(store.equalsIgnoreCase("CA")){
            username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "CanadaPaypal", "UserName");
            pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "CanadaPaypal", "Password");

        }

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            emptyShoppingBag();
        }

        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        mheaderMenuActions.clickShoppingBagIcon();

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
            mshippingPageActions.enterpickUpDetails_GuestForMixedBag(getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "fName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "lName"),
                    email, getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "phNum"));
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsBopisReg(mpickUpPageActions), "Verify successful checkout with Registered user");
            mpickUpPageActions.clickBillingBtn(mbillingPageActions);
        }

        AddInfoStep("Entering Gift Card details on Billing page");
        if (store.equalsIgnoreCase("US")) {
            mbillingPageActions.enterGiftCardDetails(gc.get("Card"), gc.get("Pin"));
        }

        mbillingPageActions.expandOrderSummary();
        Float price = 0.00f;
        AssertFailAndContinue(!mbillingPageActions.verifyEstmdTotalOnOrderLedger(price), "Verify that Estimated balance is more than $0.00 on the Order Ledger as gift card with less amount has been applied");
        //DT-35868
        AssertFailAndContinue(mbillingPageActions.selectPayPalAsPayment(mpayPalPageActions), "Select paypal as payment, click Proceed with paypal button. Verify that Pay with pal popup is displayed");
        mpayPalPageActions.paypalLogin(username, pwd, mreviewPageActions);
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Click submit order from review page verify order is placed successfully");
        getAndVerifyOrderNumber("bopis_GC_Paypal");
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
