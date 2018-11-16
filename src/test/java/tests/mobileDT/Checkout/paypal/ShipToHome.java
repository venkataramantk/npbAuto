package tests.mobileDT.Checkout.paypal;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

//@Test(singleThreaded = true)
public class ShipToHome extends MobileBaseTest {
    WebDriver mobileDriver;
    String email = mcreateAccountActions.randomEmail();
    String password;
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    //ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        env = EnvironmentConfig.getEnvironmentProfile();
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        email = mcreateAccountActions.randomEmail();
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
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, IMPALA, PAYPAL, PROD_REGRESSION})
    public void shipToHome_billingpage(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("AS a " + user + " user in " + store + " store Perform Checkout for Ship To Home product with paypal from billing page");
        String username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "UserName"),
                pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "Password");

        Map<String, String> payPalLoginDetailsCA = dt2MobileExcel.getExcelData("Paypal", "CA_PayPal");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }

        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
        }

        //shipping details
        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), "", email);
        }
        if (store.equalsIgnoreCase("CA")) {
            username = payPalLoginDetailsCA.get("UserName");
            pwd = payPalLoginDetailsCA.get("Password");
            shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"),
                    shipDetails.get("phNum"), "", email);
        }

        //DT-38991
        AssertFailAndContinue(mbillingPageActions.selectPayPalAsPayment(mpayPalPageActions), "Select paypal as payment, click Proceed with paypal button. Verify that Pay with pal popup is displayed");
        //DT-38103
        AssertFailAndContinue(mpayPalPageActions.paypalLogin(username, pwd, mreviewPageActions), "verify user is redirected to Order review page");
        //DT-39055
        String cardType = mreviewPageActions.getCardImgAltText();
        AssertFailAndContinue(cardType.equals("PAYPAL"), "Verify paypal image is shown");
        AssertFailAndContinue(mreviewPageActions.verifyPaymentMethodSection(), "Verify payment method section is shown");
        AssertFailAndContinue(mreviewPageActions.verifyBillingAddressSection(), "Verify billing address section is present");

        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Click submit order from review page verify order is placed successfully");
            AssertFailAndContinue(mreviewPageActions.waitUntilElementDisplayed(mreceiptThankYouPageActions.confirmationTitle), "Verify Order with Paypal is successful");
        }
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {CHECKOUT, IMPALA, PAYPAL, PROD_REGRESSION})
    public void shipToHome_FDMSToPaypal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("AS a " + user + " user in " + store + " store Perform Checkout for Ship To Home product with payment changed from FDMS to paypal from review page");
        String username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "UserName"),
                pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "Password");

        Map<String, String> payPalLoginDetailsCA = dt2MobileExcel.getExcelData("Paypal", "CA_PayPal");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }

        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
        }

        //shipping details
        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), "", email);
        }
        if (store.equalsIgnoreCase("CA")) {
            username = payPalLoginDetailsCA.get("UserName");
            pwd = payPalLoginDetailsCA.get("Password");
            shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"),
                    shipDetails.get("phNum"), "", email);
        }

        mbillingPageActions.enterCardDetails(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"));

        mbillingPageActions.clickNextReviewButton();

        mreviewPageActions.clickEditBillingLink(mbillingPageActions);

        AssertFailAndContinue(mbillingPageActions.selectPayPalAsPayment(mpayPalPageActions), "Select PayPal as payment, click Proceed with PayPal button. Verify that Pay with pal popup is displayed");
        //AssertFailAndContinue(mpayPalPageActions.closePayWithPaypal(), "Click close button in pay with pal button. veirfy pay with PayPal is closed and billing page is displayed");
        //mbillingPageActions.selectPayPalAsPayment();
//        AssertFailAndContinue(mbillingPageActions.clickPayPalCheckout(), "Click on Paypal Checkout button in pay with PayPal modal and verify a new window is opened ");
        mpayPalPageActions.paypalLogin(username, pwd, mreviewPageActions);
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Click submit order from review page verify order is placed successfully");
    }

    //Disable this test case as per new design - DTN-2237
    @Test(priority = 2, dataProvider = dataProviderName, groups = {CHECKOUT, IMPALA, PAYPAL, PROD_REGRESSION})
    public void shipToHome_AddedToBagModal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("AS a " + user + " user in " + store + " store Perform Checkout for Bopis product with PayPal from billing page" +
                "DT-37437, DT-37435");
        String username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "UserName"),
                pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "Password");

        Map<String, String> payPalLoginDetailsCA = dt2MobileExcel.getExcelData("Paypal", "CA_PayPal");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        String validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCodeCA");


        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCodeCA");

        }
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        //shipToHomepaypalCheckoutFromAddedToBag(validUPCNumber, "1");
        if (store.equalsIgnoreCase("CA")) {
            username = payPalLoginDetailsCA.get("UserName");
            pwd = payPalLoginDetailsCA.get("Password");
        }

        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);

        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
            mpickUpPageActions.enterpickUpDetails_Guest(getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "fName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "lName"),
                    email, getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "phNum"));
        }

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickProceedToBOPISCheckout();
            mpickUpPageActions.clickBillingBtn(mbillingPageActions);

        }

        AssertFailAndContinue(mbillingPageActions.selectPayPalAsPayment(mpayPalPageActions), "Select PayPal as payment, click Proceed with PayPal button. Verify that Pay with pal popup is displayed");

        //AssertFailAndContinue(mpayPalPageActions.clickPayPalCheckout(), "Click on PayPal Checkout button in pay with PayPal modal and verify a new window is opened ");
        mpayPalPageActions.paypalLogin(username, pwd, mreviewPageActions);
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Click submit order from review page verify order is placed successfully");
        }
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {CHECKOUT, IMPALA, PAYPAL, USONLY, PROD_REGRESSION})
    public void IntPayPalCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("AS a " + user + " Perform international Checkout with paypal");
        String username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "UserName"),
                pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "Password");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        mfooterActions.changeCountryAndLanguage("AU", "English");
        String searchKeyword = "Denim";
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }

        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
        }

        AssertFailAndContinue(mshippingPageActions.clickPayPayl_int(mpayPalPageActions), "Click On PayPal for int checkout and verify PayPal login page is displayed");
        AssertFailAndContinue(mpayPalPageActions.paypalLogin_int(username, pwd, mreviewPageActions), "Fill all details verify user is navigate to Review page");
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Click submit order verify order is placed");
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {CHECKOUT, IMPALA, PAYPAL, PROD_REGRESSION})
    public void valitedRecommendadtions_OrderConfirmation(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("AS a " + user + " user in " + store + " validate Fav in order confirmation page");
        String username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "UserName"),
                pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "US_PaylPal", "Password");

        Map<String, String> payPalLoginDetailsCA = dt2MobileExcel.getExcelData("Paypal", "CA_PayPal");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);


        if (Integer.parseInt(mheaderMenuActions.getQty()) >0) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }
        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }

        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
        }

        //shipping details
        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), "", email);
        }
        if (store.equalsIgnoreCase("CA")) {
            username = payPalLoginDetailsCA.get("UserName");
            pwd = payPalLoginDetailsCA.get("Password");
            shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"),
                    shipDetails.get("phNum"), "", email);
        }

        AssertFailAndContinue(mbillingPageActions.selectPayPalAsPayment(mpayPalPageActions), "Select PayPal as payment, click Proceed with PayPal button. Verify that Pay with pal popup is displayed");
        //AssertFailAndContinue(mpayPalPageActions.closePayWithPaypal(), "Click close button in pay with pal button. veirfy pay with PayPal is closed and billing page is displayed");
        //mbillingPageActions.selectPayPalAsPayment();
//        AssertFailAndContinue(mbillingPageActions.clickPayPalCheckout(), "Click on PayPal Checkout button in pay with PayPal modal and verify a new window is opened ");
        mpayPalPageActions.paypalLogin(username, pwd, mreviewPageActions);
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Click submit order from review page verify order is placed successfully");

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mfooterActions.fav_recommAsGuest(mloginPageActions), "Click fav icon on first recommended product verify login page is displayed");
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mfooterActions.fav_recommAsReg(), "Click fav icon on first recommended product ");
        }

    }

    @Test(priority = 5, dataProvider = dataProviderName, groups = {CHECKOUT, GIFTCARD, PAYPAL, RECAPTCHA, PROD_REGRESSION})
    public void placeOrderWith_GCAndPaypal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        String username = "";
        String pwd = "";
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("AS a " + user + " user in " + store + " store, Placing the order as registered user with giftcard + Paypal as a payment method");
        //DT-35860
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

      //  Map<String, String> payPalLoginDetailsCA = dt2MobileExcel.getExcelData("Paypal", "CA_PayPal");

        Map<String, String> usShippingAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCAFree");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy10Qty", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy10Qty", "Qty");

        Map<String, String> usGiftCard = dt2MobileExcel.getExcelData("GiftCard", "usgc3");
        Map<String, String> caGiftCard = dt2MobileExcel.getExcelData("GiftCard", "usgc4");

        if (store.equalsIgnoreCase("US")) {
            username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "USPayPal", "UserName");
            pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "USPayPal", "Password");
        } else if (store.equalsIgnoreCase("CA")) {
            username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "CanadaPayPal", "UserName");
            pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "CanadaPayPal", "Password");
        }

        if (env.equalsIgnoreCase("prod"))
            usGiftCard = dt2MobileExcel.getExcelData("GiftCard", "usgc1_prod");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");

        if (store.equalsIgnoreCase("US"))
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(usShippingAdd.get("fName"), usShippingAdd.get("lName"), usShippingAdd.get("addressLine1"), usShippingAdd.get("addressLine2"), usShippingAdd.get("city"), usShippingAdd.get("stateShortName"), usShippingAdd.get("zip"), usShippingAdd.get("phNum"), usShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        if (store.equalsIgnoreCase("CA"))
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), usShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");

        AddInfoStep("Enter and apply gift card");
        if (store.equalsIgnoreCase("US"))
            mbillingPageActions.enterGiftCardDetails(usGiftCard.get("Card"), usGiftCard.get("Pin"));
        if (store.equalsIgnoreCase("CA")) {
            mbillingPageActions.enterGiftCardDetails(caGiftCard.get("Card"), caGiftCard.get("Pin"));
           /* username = payPalLoginDetailsCA.get("UserName");
            pwd = payPalLoginDetailsCA.get("Password");*/
        }

        mbillingPageActions.expandOrderSummary();
        //DT-39070
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.GiftCardLineitemPrice), "Verify the Gift Card line item is displayed in order ledger");

        Float price = 0.00f;
        AssertFailAndContinue(!mbillingPageActions.verifyEstmdTotalOnOrderLedger(price), "Verify that Estimated balance is is more than $0.00 on the Order Ledger as gift card with less amount has been applied");
        AssertFailAndContinue(mbillingPageActions.selectPayPalAsPayment(mpayPalPageActions), "Select PayPal as payment, click Proceed with PayPal button. Verify that Pay with pal popup is displayed");
//        AssertFailAndContinue(mbillingPageActions.clickPayPalCheckout(), "Click on PayPal Checkout button in pay with PayPal modal and verify a new window is opened ");
        mpayPalPageActions.paypalLogin(username, pwd, mreviewPageActions);
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Click submit order from review page verify order is placed successfully");
        getAndVerifyOrderNumber("placeOrderWith_GC+PayPal_AsPayment");

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


