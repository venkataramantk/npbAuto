package tests.mobileDT;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;


public class MobileSTHExpressCheckout extends MobileBaseTest {
    WebDriver mobileDriver;
    String productName0 = "";
    String email1 = UiBaseMobile.randomEmail();
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String password;
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception
    {
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
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email1);
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
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GUESTONLY,PROD_REGRESSION,CHECKOUT})
    public void loginFromFavAndVerifyECPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify user is redirected to Express checkout page when user login from favorite" +
                "and add a product to bag from favorite");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        // makeExpressCheckoutUser(store,env);

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, searchKeyword);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(0);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(1);

        AssertFailAndContinue(mcategoryDetailsPageAction.favIconEnabled.size() == 2, "verify 2 products are added to favorite");
        logout();
        AssertFailAndContinue(mheaderMenuActions.waitUntilUrlChanged("home", 20), "Wait for url to change to home page url");

        panCakePageActions.navigateToMenu("FAVORITES");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        AssertFailAndContinue(mheaderMenuActions.waitUntilUrlChanged("favorites", 20), "Wait for url to change to favorite page url");

        mobileFavouritesActions.addProductToBagByPosition(1);
        mobileFavouritesActions.addProductToBagByPosition(2);
        //DT-40385
        int bagCount = mheaderMenuActions.getBagCount();
        AssertFailAndContinue(mheaderMenuActions.getBagCount() > 0,"Verify Mini cart count is increased after adding products from favorite");
        //AssertFailAndContinue(mheaderMenuActions.getBagCount() == bagCount + 1, "Verify Mini cart count is increased after adding bopis product");
        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Verify click on shopping bag icon");
        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions), "Click checkout and verify user redirect to express checkout page");
        //DT-35333
        AssertFailAndContinue(mreviewPageActions.verifyExpressCheckoutTitle(), "Verify Title is EXPRESS CHECK OUT");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {SHOPPINGBAG, REGISTEREDONLY})
    public void verifyCouponOnExpressCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String,String> mc = null;
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify on shopping bag that if the coupon applied on expresscheckout page is removed on cart, "
                + "the item price should remain accross the checkout flow ");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }
        if (store.equalsIgnoreCase("US")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        // makeExpressCheckoutUser(store, env);

        if (Integer.valueOf(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Verify successful click on shopping bag icon");
        }

        AssertFailAndContinue(true, "Verify the count of mini cart of adding items to bag:" + mheaderMenuActions.getQty());

        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions), "Verify redirection to express checkout page");

        String mCoupon = null;
        //DT-35106 , DT-35105
        if (store.equalsIgnoreCase("US")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode");
            if (env.equalsIgnoreCase("prod")) {
                mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA");
            if (env.equalsIgnoreCase("prod")) {
                mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA");
            }
        }
        AssertFailAndContinue(mreviewPageActions.applyCouponCode(mCoupon), "Enter the valid coupon code and click on apply button at review page");
        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        AssertFailAndContinue(mshoppingBagPageActions.removeCouponAndCheckTotal(mobileDriver),"Validate estimated total after removing coupon from shopping bag.");
        //   AssertFailAndContinue(mshoppingBagPageActions.validateTotalAfterRemoveCoupon(), "verify the estimate total");
        mshoppingBagPageActions.staticWait(2000);
        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions), "Verify successful checkout with Registered user");
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.enterExpressCO_CVVAndSubmitOrder(mc.get("securityCode"));
            AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageReg(), "Verify Order Confirmation Page");
        }
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {COUPONS, REGISTEREDONLY})
    public void applyCouponOnECPageAndRemoveOnShippingPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        String mCoupon = null;
        Map<String,String> mc = null;
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify on shipping bag that if the coupon applied on expresscheckout page is removed on shipping Page, "
                + "the item price should remain accross the checkout flow ");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }

        if (store.equalsIgnoreCase("US")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.valueOf(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Verify successful click on shopping bag icon");
        }

        AssertFailAndContinue(true, "Verify the count of mini cart of adding items to bag:" + mheaderMenuActions.getQty());
        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions),"Verify redirection to express checkout page");

        if (store.equalsIgnoreCase("US")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode");
            if (env.equalsIgnoreCase("prod")) {
                mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA");
            if (env.equalsIgnoreCase("prod")) {
                mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA");
            }
        }

        mreviewPageActions.applyCouponCode(mCoupon);
        mreviewPageActions.editShipMethodLink();

        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.validateTotalAfterAppliedCoupon(), "Validate the Estimated cost after applied coupon");

        mshippingPageActions.removeCoupon();
        // AssertFailAndContinue(mshippingPageActions.RemoveCoupanInOrderSummery(), "Validate coupon has been removed");
        AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(),"Verify billing button");
        mbillingPageActions.clearAndFillText(mbillingPageActions.cvvFld, mc.get("securityCode"));
        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(),"Verify redirection to review page");
        AssertFailAndContinue(mreviewPageActions.verifyBillingAddressSection(), "Verify Billing Address section in Review Page");
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.click(mreviewPageActions.submitOrderBtn);
            AssertFailAndContinue(mreceiptThankYouPageActions.isDisplayed(mreceiptThankYouPageActions.ordeLedger), "Verify Order Ledger Section is displayed");
        }
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {COUPONS, REGISTEREDONLY, USONLY})
    public void applyCouponOnReviewPageAndRemoveOnBillingPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String,String> mc = null;
        setAuthorInfo("Shilpa P");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify on shopping bag that if the coupon applied on expresscheckout page is removed on cart, "
                + "the item price should remain across the checkout flow ");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.valueOf(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Verify successful click on shopping bag icon");
        }

        if (store.equalsIgnoreCase("US")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        }

        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions),"Verify redirection to Express checkout flow");
        String mCoupon = "";
        if (store.equalsIgnoreCase("US")) {
            mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode");
            if (env.equalsIgnoreCase("prod")) {
                mCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode");
            }
        }
        mreviewPageActions.applyCouponCode(mCoupon);
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mreviewPageActions.validateTotalAfterAppliedCoupon(), "Validation of the Total Price after applying the coupon");
        AssertFailAndContinue(mreviewPageActions.clickOnBillingAccordion(mbillingPageActions),"Verify user is redirected back to billing page");
        mbillingPageActions.removeCoupon();
        //DT-35444
        // AssertFailAndContinue(mbillingPageActions.RemoveCoupanInOrderSummery(), "Validate coupon has been removed");
        mbillingPageActions.clearAndFillText(mbillingPageActions.cvvFld, mc.get("securityCode"));
        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(),"Verify successful redirection to reviw page");
        AssertFailAndContinue(mreviewPageActions.verifyBillingAddressSection(), "Verify Billing Address section in Review Page");
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.click(mreviewPageActions.submitOrderBtn);
            AssertFailAndContinue(mreceiptThankYouPageActions.isDisplayed(mreceiptThankYouPageActions.ordeLedger), "Verify Order Ledger Section is displayed");
        }

    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }

}
