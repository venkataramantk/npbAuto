package tests.webDT.checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

public class ReviewPageApplyRemoveCoupons extends BaseTest {
    WebDriver driver;
    String emailAddressReg;
    private String password;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String env;

    @Parameters({"store", "users"})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US") && user.equalsIgnoreCase("registered")) {
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered")) {
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
                password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
            }
        }
        headerMenuActions.deleteAllCookies();
    }

    @Parameters("store")
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {CHECKOUT, USONLY,PROD_REGRESSION})
    public void applyCouponAndRemAtBillingAndVerifyReview(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store + " store and as a " + user + " user, initiates checkout process with coupons applied in \"Billing\"info section, navigates to \"Review\" order info section in which" +
                "the original and discounted price is available for each of the item in the bag and returns back to \"Billing\" info section and removes the applied Coupons and returns to \"Review\" order info section, " +
                "verify that the sale price gets updated for each item available in the bag with respect to the removed coupons.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "AltValue");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        List<String> couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validMAAddressDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        Map<String, String> vi = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode"));

        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA"));

        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearching(searchKeywordAndQty);
        if (user.equalsIgnoreCase("registered")) {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);

        } else if (user.equalsIgnoreCase("guest")) {
            shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions);
        }
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        AssertFailAndContinue(shippingPageActions.applyCouponCode(couponCode.get(0)), "Enter the valid coupon code and click on apply button at billing page");
        AssertFailAndContinue(billingPageActions.payWithACreditCard(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");
        AssertFailAndContinue(reviewPageActions.isProdSTHListPriceDisplay() && reviewPageActions.isProdSTHOfferPriceDisplay(), "product list price and offer price is displaying");
        reviewPageActions.clickReturnToBillingLink(billingPageActions);
        billingPageActions.removeAppliedCoupons();
        billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, vi.get("securityCode"));
        AssertFailAndContinue(!reviewPageActions.waitUntilElementsAreDisplayed(reviewPageActions.removeButtonsOfCoupons, 1), "coupons are not visible at apply coupons section");
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {CHECKOUT, USONLY,PROD_REGRESSION})
    public void applyCouponAndRemAtShippingAndVerifyReview(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store + " store and as a " + user + " user, nitiates checkout process with coupons applied in \"Shipping\"info section, " +
                "navigates to \"Review\" order info section in which the original and discounted price is available for each of the item in the bag and " +
                "returns back to \"Shipping\" info section and removes the applied Coupons and returns to \"Review\" order info section, " +
                "verify that the sale price gets updated for each item available in the bag with respect to the removed coupons.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "AltValue");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        List<String> couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validMAAddressDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        Map<String, String> vi = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode"));

        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA"));

        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        }
        if (Integer.parseInt(headerMenuActions.getQtyInBag()) == 0) {
            addToBagBySearching(searchKeywordAndQty);

        } else {
            headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        }
        if (user.equalsIgnoreCase("registered")) {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);

        } else if (user.equalsIgnoreCase("guest")) {
            shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions);
        }

        AssertFailAndContinue(shippingPageActions.applyCouponCode(couponCode.get(0)), "Enter the valid coupon code and click on apply button at billing page");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        AssertFailAndContinue(billingPageActions.payWithACreditCard(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");
        AssertFailAndContinue(reviewPageActions.isProdSTHListPriceDisplay() && reviewPageActions.isProdSTHOfferPriceDisplay(), "product list price and offer price is displayig");
        reviewPageActions.clickOnShippingAccordion(shippingPageActions);
        shippingPageActions.removeAppliedCoupons();
        shippingPageActions.clickNextBillingButton(billingPageActions);
        billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, vi.get("securityCode"));
        AssertFailAndContinue(!reviewPageActions.waitUntilElementsAreDisplayed(reviewPageActions.removeButtonsOfCoupons, 1), "coupons are not visible at apply coupons section");
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {CHECKOUT, USONLY})
    public void applyCouponAndRemAtShoppingAndVerifyReview(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store + " store and as a " + user + " user, nitiates checkout process with coupons applied in " +
                "\"Shipping\"info section, navigates to \"Review\" order info section in which the original and discounted price is available" +
                " for each of the item in the bag and returns back to \"Shipping\" info section and " +
                "removes the applied Coupons and returns to \"Review\" order info section, " +
                "verify that the sale price gets updated for each item available in the bag with respect to the removed coupons.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "AltValue");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        List<String> couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validMAAddressDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        Map<String, String> vi = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode"));

        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA"));

        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        if (Integer.parseInt(headerMenuActions.getQtyInBag()) == 0) {
            addToBagBySearching(searchKeywordAndQty);

        } else {
            headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        }
        if (user.equalsIgnoreCase("registered")) {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);

        } else if (user.equalsIgnoreCase("guest")) {
            shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions);
        }

        AssertFailAndContinue(shippingPageActions.applyCouponCode(couponCode.get(0)), "Enter the valid coupon code and click on apply button at billing page");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        AssertFailAndContinue(billingPageActions.payWithACreditCard(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");
        AssertFailAndContinue(reviewPageActions.isProdSTHListPriceDisplay() && reviewPageActions.isProdSTHOfferPriceDisplay(), "product list price and offer price is displayig");
        reviewPageActions.returnToBagPage(shoppingBagPageActions);
        shippingPageActions.removeAppliedCoupons();
        if (user.equalsIgnoreCase("registered")) {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        } else if (user.equalsIgnoreCase("guest")) {
            shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions);
        }
        shippingPageActions.clickNextBillingButton(billingPageActions);
        billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, vi.get("securityCode"));
        AssertFailAndContinue(!reviewPageActions.waitUntilElementsAreDisplayed(reviewPageActions.removeButtonsOfCoupons, 1), "coupons are not visible at apply coupons section");
        AssertFailAndContinue(reviewPageActions.applyCouponCode(couponCode.get(0)), "Enter the valid coupon code and click on apply button at Review page");
        reviewPageActions.removeAppliedCoupons();
    }
}
