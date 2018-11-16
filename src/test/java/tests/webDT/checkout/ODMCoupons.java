package tests.webDT.checkout;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import tests.webDT.shoppingBag.ViewProductDetails;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

public class ODMCoupons extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(ViewProductDetails.class);
    String emailAddress;
    private String password;
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            if (user.equalsIgnoreCase("registered")) {
                emailAddress = getDT2TestingCellValueBySheetRowAndColumn("ODM","user","email");
                password = getDT2TestingCellValueBySheetRowAndColumn("ODM","user", "Password");//"testb10e6b5242981@yopmail.com";//
            }

        } else if (store.equalsIgnoreCase("CA")) {
            // headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered")) {
                emailAddress = getDT2TestingCellValueBySheetRowAndColumn("ODM","user","email");
                password = getDT2TestingCellValueBySheetRowAndColumn("ODM","user", "Password");//"testb10e6b5242981@yopmail.com";//
            }
            headerMenuActions.addStateCookie("MB");
        }
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            headerMenuActions.addStateCookie("MB");
        }

    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, RHINO})
    public void odm_OnlineOrders(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " Store. Verify \n" +
                "1. DT-44927, DT-44928, DT-44945, DT-44930, DT-44931, DT-41923, DT-44929,DT-44931,DT-44930");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");

        String usedCoupon = getDT2TestingCellValueBySheetRowAndColumn("ODM", "UsedCoupon", "Value");
        String validCoupon = getDT2TestingCellValueBySheetRowAndColumn("ODM", "validCoupons", "Value");
        String usedCouponErr = getDT2TestingCellValueBySheetRowAndColumn("ODM", "UsedCoupon", "ErrorMsg");

        String expiredCoupon = getDT2TestingCellValueBySheetRowAndColumn("ODM", "expiredCoupon", "Value");
        String expiredCouponErr = getDT2TestingCellValueBySheetRowAndColumn("ODM", "expiredCoupon", "ErrorMsg");
        String otherStoreErr = getDT2TestingCellValueBySheetRowAndColumn("ODM", "validCoupons", "ErrorMsg");

        String placeCashCoupon = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "placeCashCoupon", "couponCode");

        Map<String, String> shipDetails = null;
        if (store.equalsIgnoreCase("US")) {
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        }
        else if (store.equalsIgnoreCase("CA")) {
           shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        }

        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if (store.equalsIgnoreCase("CA")) {
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA");
        }
        if (user.equalsIgnoreCase("registered")) {
           clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress,password);
        }
        if (store.equalsIgnoreCase("CA")) {
            validCoupon = getDT2TestingCellValueBySheetRowAndColumn("ODM", "validCoupons", "ValueCA");
            usedCoupon = getDT2TestingCellValueBySheetRowAndColumn("ODM", "UsedCoupon", "ValueCA");
            expiredCoupon = getDT2TestingCellValueBySheetRowAndColumn("ODM", "expiredCoupon", "ValueCA");
        }

        addToBagBySearching(searchKeywordAndQty);
        if(user.equalsIgnoreCase("registered")){
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            myAccountPageActions.clickMyRewardsLink();
            myAccountPageActions.click(myAccountPageActions.lnk_OffersAndCoupons);
            AssertFailAndContinue(!myAccountPageActions.isDisplayed(myAccountPageActions.odmOffCoupon),"Verify that the ODM coupon is not displayed in MyAccount");
            headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
            AssertFailAndContinue(shoppingBagPageActions.applyCouponCode(validCoupon), "Enter the valid coupon code and click on apply button");
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            myAccountPageActions.clickMyRewardsLink();
            myAccountPageActions.click(myAccountPageActions.lnk_OffersAndCoupons);
            AssertFailAndContinue(myAccountPageActions.isDisplayed(myAccountPageActions.odmOffCoupon),"Verify that the ODM coupon is displayed in MyAccount");
            myAccountPageActions.verifyExpireDate();
            myAccountPageActions.removeAppliedCoupon();
        }
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.applyCouponCode(validCoupon), "Enter the valid coupon code and click on apply button");
        AssertFailAndContinue(shoppingBagPageActions.validateExpiredCouponErrMsg(usedCoupon,usedCouponErr), "Verify error message for Used Coupon in bag");
        AssertFailAndContinue(shoppingBagPageActions.validateExpiredCouponErrMsg(expiredCoupon,expiredCouponErr), "Verify error message for Expired Coupon in bag");
        AssertFailAndContinue(shoppingBagPageActions.validateExpiredCouponErrMsg(placeCashCoupon,expiredCouponErr), "Verify error message for Plase cash coupon when Redemption is OFF");

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shoppingBagPageActions.validateExpiredCouponErrMsg(validCoupon,otherStoreErr), "Enter a US coupon in CA and verify Error Message");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shoppingBagPageActions.validateExpiredCouponErrMsg(validCoupon,otherStoreErr), "Enter a CA coupon in US and verify Error Message");
        }

        if (user.equalsIgnoreCase("registered")) {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions,reviewPageActions);
        }

        if (user.equalsIgnoreCase("guest")) {
            shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions,shippingPageActions);
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");

        } else {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");

        }
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetailUS.get("fName"),
                billDetailUS.get("lName"), billDetailUS.get("addressLine1"), billDetailUS.get("addressLine2"), billDetailUS.get("city"), billDetailUS.get("stateShortName"), billDetailUS.get("zip"), billDetailUS.get("cardNumber"),
                billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Verify ecomm order placed successfully");

        AssertFailAndContinue(orderConfirmationPageActions.validateODMCouponFields(), "Verify all the Fields In ODM Coupon");
        AssertFailAndContinue(orderConfirmationPageActions.validateODMHeader(), "Verify ODM coupon Header");
        AssertFailAndContinue(orderConfirmationPageActions.validateCouponsStartDate(), "Verify Now through or start-end date format based on current data");
        AssertFailAndContinue(orderConfirmationPageActions.validateDetailsLink(), "Validate ODM legal disclaimer");
    }

}
