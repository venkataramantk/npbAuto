package tests.mobileDT.myAccount;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Test(singleThreaded = true)
public class MyAccountOverviewTests extends MobileBaseTest {
    WebDriver mobileDriver;
    String email, password;
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        email = UiBaseMobile.randomEmail();
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGISTEREDONLY})
    public void accountOverviewUiValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("UI validations for My Account Overview for " + store + " Store" +
                "DT--41882");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        panCakePageActions.navigateToMenu("MYACCOUNT");

        AssertFailAndContinue(!mmyAccountPageActions.waitUntilElementDisplayed(panCakePageActions.salutationLink, 10), "Verify Pancake section collapse as soon as My Account section loaded");
        // DT-23465, DT-23466
        AssertFailAndContinue(mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.offersLink), "Verify My Rewards & Offers tab in My Account Overview");
        AssertFailAndContinue(mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.ordersLnk), "Verify OnlineOrders tab in My Account Overview");
        AssertFailAndContinue(mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.addressbook), "Verify Address Book tab in My Account Overview");
        AssertFailAndContinue(mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.giftcards), "Verify Payment & Gift Cards tab in My Account Overview");
        AssertFailAndContinue(mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.profileInfoLnk), "Verify Profile Information tab in My Account Overview");

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mmyAccountPageActions.verifyExpireDate(), "Validate Expire data for coupons");

            AssertFailAndContinue(mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.previousBtn), "when navigates to the account overview section, should be able to see the previous Carousel slider buttons");
            AssertFailAndContinue(mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.nextBtn), "when navigates to the account overview section, should be able to see the Next Carousel slider buttons");

            mmyAccountPageActions.click(mmyAccountPageActions.offersLink);
            mmyAccountPageActions.click(mmyAccountPageActions.pointsHistoryLink);
            mmyAccountPageActions.scrollDownToElement(mmyAccountPageActions.custmorServiceLink);
            mmyAccountPageActions.click(mmyAccountPageActions.custmorServiceLink);
            AssertFailAndContinue(mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.emailUs), "Verify custmor service link in Points history table");

            panCakePageActions.navigateToMenu("MYACCOUNT");
            AssertFailAndContinue(mmyAccountPageActions.expandMPRInfo(), "Click ? icon verify tooltip icon open");
            AssertFailAndContinue(mmyAccountPageActions.closeMPRInfo(), "Click close icon verify tooltip is closed");

            panCakePageActions.navigateToMenu("VIEW");
            AssertFailAndContinue(mmyAccountPageActions.getText(panCakePageActions.currentPoints).contains("0"), "Verify current points is 0 for new account");
            AssertFailAndContinue(mmyAccountPageActions.getText(panCakePageActions.myRewards).contains("0"), "Verify my rewards is 0 for new account");
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageDrawerActions.clickViewMyAccount(mheaderMenuActions, mmyAccountPageActions);
        AssertFailAndContinue(mmyAccountPageDrawerActions.clickLogoutLink(mheaderMenuActions),"Click logout from my account -> overview and veriy user became as guest user and home page is displayed");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {REGISTEREDONLY, COUPONS})
    public void validateCouponAddedFrmMyAccount(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String,String> shipAndBillDetails = null;
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, DT-40281:: validate when user adds a coupon from MyAccount Drawer and able to view applied promotion on shopping/shipping/billing/review page");
        //DT-40281
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email,password);
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Qty2");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (store.equalsIgnoreCase("US")) {
            shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        }

        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }
        addToBagFromPDP(searchKeywordAndQty);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("REWARDS");
        mmyAccountPageActions.clickOffersAndCoupons();
        mmyAccountPageActions.navigateToCouponValue("AUTOMATION 10% OFF");
        mmyAccountPageActions.clickApplyToBagBtn();

        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Click shopping bag link");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.appliedCouponText), "Verify applied coupon section is displayed when coupon applied from My Account");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.removeCoupon), "Verify coupon applies successfully when applied from My Account");

        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.couponPrice), "Verify coupon line item is displayed in Order ledger");

        AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successfully checkout with registered user");

        if (mshippingPageActions.isDisplayed(mshippingPageActions.editLink_ShipAddress)) {
            mshippingPageActions.clickAddNewAddress();
            mshippingPageActions.addNewAddressFromShippingPage_SetAsDefault(shipAndBillDetails.get("fName"),
                    shipAndBillDetails.get("lName"), shipAndBillDetails.get("addressLine1"), shipAndBillDetails.get("city"), shipAndBillDetails.get("stateShortName")
                    , shipAndBillDetails.get("zip"), shipAndBillDetails.get("phNum"), false);
        } else {
            mshippingPageActions.enterShippingDetailsAndShipType(shipAndBillDetails.get("fName"), shipAndBillDetails.get("lName"), shipAndBillDetails.get("addressLine1"), shipAndBillDetails.get("addressLine2"), shipAndBillDetails.get("city"), shipAndBillDetails.get("stateShortName"), shipAndBillDetails.get("zip"), shipAndBillDetails.get("phNum"), shipAndBillDetails.get("ShippingType"), email);
        }

        mshippingPageActions.expandCouponSectionIfNot();
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.appliedCouponText), "Verify applied coupon section is displayed when coupon applied from My Account");
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.removeCoupon), "Verify coupon applies successfully when applied from My Account");
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.couponPrice), "Verify coupon line item is displayed in Order ledger");
        AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(), "Verify redirection to billing page");

        mbillingPageActions.enterCardDetails(shipAndBillDetails.get("cardNumber"), shipAndBillDetails.get("securityCode"), shipAndBillDetails.get("expMonthValueUnit"));

        mshippingPageActions.expandCouponSectionIfNot();
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.appliedCouponText), "Verify applied coupon section is displayed when coupon applied from My Account");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.removeCoupon), "Verify coupon applies successfully when applied from My Account");
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.couponPrice), "Verify coupon line item is displayed in Order ledger");

        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(), "Verify user redirected to review page");
        mreviewPageActions.expandCouponSectionIfNot();
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.appliedCouponText), "Verify applied coupon section is displayed when coupon applied from My Account");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.removeCoupon), "Verify coupon applies successfully when applied from My Account");
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.couponPrice),"Verify coupon line item is displayed in Order ledger");
        mreviewPageActions.removeCoupon();

    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {REGISTEREDONLY, COUPONS})
    public void verifyAddCouponFrmMyAccount(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, validate when user adds a promotion from MyAccount Drawer and able to view applied promotion on expresscheckout page");
        //DT-35912
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email,password);

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
        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);

        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        AssertFailAndContinue(mmyAccountPageActions.applyRewardsAndCoupons(), "Verify that the user is able to apply coupon from My Account");
        mheaderMenuActions.clickShoppingBagIcon();
        mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions);
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.ApplyCouponInOrderSummery(), "verify the coupon is applied in order summery on Expresscheckout Page");

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