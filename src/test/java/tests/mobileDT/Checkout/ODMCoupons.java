package tests.mobileDT.Checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

public class ODMCoupons extends MobileBaseTest {
    WebDriver mobileDriver;
    String email, password;
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));

    @Parameters({"store", "users"})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        email = "testb543bf0147721@yopmail.com";//getmobileDT2CellValueBySheetRowAndColumn("ODM", "user", "email");
        password = "P@ssw0rd";//getmobileDT2CellValueBySheetRowAndColumn("ODM", "user", "Password");
        mheaderMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, RHINO})
    public void odm_OnlineOrders(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " Store. Verify \n" +
                "1. DT-44927, DT-44928, DT-44945, DT-44930, DT-44931, DT-41923");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm5", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");

        String usedCoupon = getmobileDT2CellValueBySheetRowAndColumn("ODM", "UsedCoupon", "Value");
        String validCoupon = getmobileDT2CellValueBySheetRowAndColumn("ODM", "validCoupons", "Value");
        String usedCouponErr = getmobileDT2CellValueBySheetRowAndColumn("ODM", "UsedCoupon", "ErrorMsg");

        String expiredCoupon = getmobileDT2CellValueBySheetRowAndColumn("ODM", "expiredCoupon", "Value");
        String expiredCouponErr = getmobileDT2CellValueBySheetRowAndColumn("ODM", "expiredCoupon", "ErrorMsg");
        String otherStoreErr = getmobileDT2CellValueBySheetRowAndColumn("ODM", "validCoupons", "ErrorMsg");

        String placeCashCoupon = getmobileDT2CellValueBySheetRowAndColumn("Coupons", "placeCashCoupon", "couponCode");


        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        if (store.equalsIgnoreCase("CA")) {
            validCoupon = getmobileDT2CellValueBySheetRowAndColumn("ODM", "validCoupons", "ValueCA");
            usedCoupon = getmobileDT2CellValueBySheetRowAndColumn("ODM", "UsedCoupon", "ValueCA");
            expiredCoupon = getmobileDT2CellValueBySheetRowAndColumn("ODM", "expiredCoupon", "ValueCA");
        }

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(mshoppingBagPageActions.applyCouponAndCheckError(usedCoupon).contains(usedCouponErr), "Verify error message for Used Coupon in bag");
        AssertFailAndContinue(mshoppingBagPageActions.applyCouponAndCheckError(expiredCoupon).contains(expiredCouponErr), "Verify error message for Expired Coupon in bag");
        AssertFailAndContinue(mshoppingBagPageActions.applyCouponAndCheckError(placeCashCoupon).contains(expiredCouponErr), "Verify error message for Plase cash coupon when Redemption is OFF");

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshoppingBagPageActions.applyCouponAndCheckError(getmobileDT2CellValueBySheetRowAndColumn("ODM", "validCoupons", "ValueCA")).contains(otherStoreErr), "Enter a US coupon in CA and verify Error Message");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshoppingBagPageActions.applyCouponAndCheckError(getmobileDT2CellValueBySheetRowAndColumn("ODM", "validCoupons", "Value")).contains(otherStoreErr), "Enter a CA coupon in US and verify Error Message");
        }

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }

        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
        }

        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest("Alert", shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), "", email);
        }
        if (store.equalsIgnoreCase("CA")) {
            shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest("Alert", shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"),
                    shipDetails.get("phNum"), "", email);
        }

        mbillingPageActions.enterCardDetails(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"));

        mbillingPageActions.clickNextReviewButton();
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Verify ecomm order placed successfully");

        AssertFailAndContinue(mreceiptThankYouPageActions.validateODMCouponFields(), "Verify all the Fields In ODM Coupon");
        AssertFailAndContinue(mreceiptThankYouPageActions.validateODMHeader(), "Verify ODM coupon Header");
        AssertFailAndContinue(mreceiptThankYouPageActions.validateCouponsStartDate(), "Verify Now through or start-end date format based on current data");
        AssertFailAndContinue(mreceiptThankYouPageActions.validateDetailsLink(), "Validate ODM legal disclaimer");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {CHECKOUT, RHINO, REGISTEREDONLY})
    public void odm_MyAccountTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setRequirementCoverage("As a " + user + " user in " + store + " Store. Verify \n" +
                "DT-44929, DT-44930, DT-44931");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm5", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        String validCoupon = getmobileDT2CellValueBySheetRowAndColumn("ODM", "validCoupons", "Value");
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("REWARDS");
        mmyAccountPageActions.clickOffersAndCoupons();

        addToBagBySearching(searchKeywordAndQty);
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshoppingBagPageActions.applyCouponCode(validCoupon), "Verify user is able to apply valid coupon in shopping bag");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshoppingBagPageActions.applyCouponCode(validCoupon), "Verify user is able to apply valid coupon in shopping bag");
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("REWARDS");
        mmyAccountPageActions.clickOffersAndCoupons();

        AssertFailAndContinue(!mmyAccountPageActions.verifyExpireDate(), "Verify Coupon expire Date is not available for ODM coupons");
        AssertFailAndContinue(mmyAccountPageActions.removeODMCoupon(), "Verify ODM coupon is able to remove from My Account");

        int availableCoupons = mmyAccountPageActions.getAvailableCouponCount();

        mheaderMenuActions.clickShoppingBagIcon();

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }

        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
        }
        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");

        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest("Alert", shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), "", email);
        }
        if (store.equalsIgnoreCase("CA")) {
            shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest("Alert", shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"),
                    shipDetails.get("phNum"), "", email);
        }

        mbillingPageActions.enterCardDetails(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"));

        mbillingPageActions.clickNextReviewButton();
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Verify ecomm order placed successfully");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("REWARDS");
        mmyAccountPageActions.clickOffersAndCoupons();

        AssertFailAndContinue(mmyAccountPageActions.getAvailableCouponCount() == availableCoupons, "Verify the coupons displayed in Order confirmation page are not available in myyaccount");

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