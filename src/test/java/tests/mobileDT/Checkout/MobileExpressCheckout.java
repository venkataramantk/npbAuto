package tests.mobileDT.Checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Test(singleThreaded = true)
public class MobileExpressCheckout extends MobileBaseTest {
    WebDriver mobileDriver;
    String productName0 = "";
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String email1 = UiBaseMobile.randomEmail();
    String password = "";
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
                createAccount(rowInExcelUS, email1);
                makeExpressCheckoutUser(store,env);

            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelCA, email1);
                makeExpressCheckoutUser(store,env);

            }
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        mheaderMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
            mheaderMenuActions.addStateCookie("ON");

        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY})
    public void verifyRemoveCouponOnExpChkout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        String mCoupon = null;
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify on shopping bag that if the coupon applied on expresscheckout page is removed on cart, "
                + "the item price should remain across the checkout flow ");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1,password);
        }

       // makeExpressCheckoutUser(store,env);

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);

        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }
        AssertFailAndContinue(true, "Verify the count of mini cart of adding items to bag:" + mheaderMenuActions.getQty());
        //DT-36715
        if (store.equalsIgnoreCase("US")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode");
        }
        //DT-36722
        else if (store.equalsIgnoreCase("CA")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA");
        }
        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions), "Verify successful checkout with Registered user");

        mreviewPageActions.expandOrderSummary();
        Float orderTotal = Float.parseFloat(mreviewPageActions.getEstimateTotal());
        AssertFailAndContinue(mreviewPageActions.applyCouponCode(mCoupon), "Enter the valid coupon code and click on apply button at billing page");
        AssertFailAndContinue(mreviewPageActions.verifyCouponFieldDisplayedOnOrderLedger(), "Verify Coupon field is present on Order ledger after applying coupon");
        AssertFailAndContinue(mreviewPageActions.validateAccordiansInReviewPage("ECOMM"), "validate Progress bar review page for ecomm order");

        AssertFailAndContinue(mreviewPageActions.validateTotalAfterApplyingCoupn(orderTotal), "Verify estimated total after applying coupon");
        AssertFailAndContinue(mreviewPageActions.removeCouponAndCheckTotal(mobileDriver), "Remove coupon and verify the estimate total");

        AssertFailAndContinue(mreviewPageActions.applyCouponCode(mCoupon.toLowerCase()), "Enter the valid coupon code and click on apply button at billing page");
        //DT-35382
        AssertFailAndContinue(mreviewPageActions.removeCouponAndCheckTotal(mobileDriver), "verify the total after removing the coupon");
        //DT-35373
        mreviewPageActions.enterExpressCO_CVVAndSubmitOrder(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"));
        //DT-36721
        AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageReg(), "Verify Order Confirmation Page");
        AssertFailAndContinue(mreceiptThankYouPageActions.verifyEstmdTotalOnOrderLedger(orderTotal), "Verify estimated total on Order Ledge on order confirmation page is equal to estimated total on express checkout review page");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {REGISTEREDONLY})
    public void redirectionOnShipingBillingPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify if user clicks on Pick up/Billing from progress bar, "
                + "user gets redirected to Pick up/Billing Page respectively ");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1,password);
        }

       // makeExpressCheckoutUser(store,env);

        String validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCode");
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);


        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);

        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(),"Click shopping bag");
        mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions);

        AssertFailAndContinue(mreviewPageActions.clickOnPickUpAccordionBopis(mpickUpPageActions), "Verify user is redirected to Pickup Page");

        mheaderMenuActions.returnToBag(mshoppingBagPageActions);
        mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions);
        //DT-35421
        AssertFailAndContinue(mreviewPageActions.clickOnBillingAccordion(mbillingPageActions), "Verify user is redirected to Billing Page");

        AssertFailAndContinue(mbillingPageActions.enterCVVForExpressAndClickNextReviewBtn("222"), "Enter CVV for billing navigate to review page");

        AssertFailAndContinue(!mreviewPageActions.isCvvFieldIsDisplayed(), "Verify CVV field is not displayed in review page");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {REGISTEREDONLY, GIFTCARD, RECAPTCHA})
    public void changePaymentFromADSToGC(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify if user place the order successfully by changing Payment method from ADS to GC");
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1,password);
/*        String emailId = getmobileDT2CellValueBySheetRowAndColumn("Login", "PLCCUserwithGC", "EmailID");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(emailId, getmobileDT2CellValueBySheetRowAndColumn("Login", "PLCCUserwithGC", "Password"));*/

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(), "Delete all existing address details from my account");
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(), "Delete all existing card details from my account");

        makeExpressCheckoutUser(store,env);

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }
        addToBagBySearching(searchKeywordAndQty);
        mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions);
        //DT-35437
        AssertFailAndContinue(mreviewPageActions.clickApplyButton(), "Verify Apply Gift Card is clicked");
        AssertFailAndContinue(mreviewPageActions.verifyElementNotDisplayed(mreviewPageActions.OopsErrorBox), "Oops an Error message is not displayed");
        AssertFailAndContinue(mreviewPageActions.verifyElementNotDisplayed(mreviewPageActions.paymentMethodSection), "card details is not displayed");
        AssertFailAndContinue(mreviewPageActions.verifyElementNotDisplayed(mreviewPageActions.cvvTxtField), "Verify CVV input filed is not displayed");
        AssertFailAndContinue(mreviewPageActions.verifyElementNotDisplayed(mreviewPageActions.billingAddressSection), "billing address section is not displayed");
        AssertFailAndContinue(mbillingPageActions.isGCLast_4DigitsDisplaying(), "Gift Cards Lat 4 digits is showing at billing page");
        AssertFailAndContinue(mbillingPageActions.isRemainingBalanceDispalying(), "Gift Cards remaining balance is showing at billing page");
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Placed order with gift card as payment");
            AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageReg(), "Verify Order Confirmation Page");
        }
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {REGISTEREDONLY, PLCC, USONLY})
    public void paywithPlcc(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered in US perform checkout with paypal" +
                "DT-43769");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(), "Delete all existing address details from my account");
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(), "Delete all existing card details from my account");


        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
     //   ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
        Map<String, String> billDetails = dt2ExcelReader.getExcelData("BillingDetails", "PlaceCard");
        Map<String, String> shipDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");

        mmyAccountPageActions.addNewShippingAddress(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("city"),
                shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("country"), shipDetails.get("phNum"),panCakePageActions);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        mmyAccountPageActions.addAPLCCCard(billDetails.get("cardNumber"));

        panCakePageActions.navigateToMenu("MYACCOUNT");
        AssertFailAndContinue(mmyAccountPageActions.expandMPRInfo(), "Click ? icon verify tooltip icon open");

        addToBagBySearching(searchKeywordAndQty);

        AssertFailAndContinue(mshoppingBagPageActions.calculatePoints(), "validate points in shopping bag page");
        //DT-35357
        mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        AssertFailAndContinue(!mreviewPageActions.isDisplayed(mreviewPageActions.cvvTxtField), "Verify CVV field is not displayed");
        mreviewPageActions.clickOnShippingAccordion(mshippingPageActions);
        AssertFailAndContinue(mshippingPageActions.verifyTransactionalSMSCheckBox(), "Verify that express checkout user is able to sign up for order updates by navigating back to shipping page.");
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
