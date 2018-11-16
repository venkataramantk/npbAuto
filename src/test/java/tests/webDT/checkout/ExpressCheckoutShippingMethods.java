package tests.webDT.checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

/**
 * Created by Srijith on 1/31/2018.
 */

public class ExpressCheckoutShippingMethods extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg;
    private String password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        headerMenuActions.deleteAllCookies();
    }

    @Parameters({storeXml, usersXml})
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
            headerMenuActions.addStateCookie("MB");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {CHECKOUT, REGISTEREDONLY, USONLY, PLCC})
    public void shippingMethodsAKHIPR_Express(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Registered user is displayed with AK HI PR Express in Review page." +
                "DT-44903");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsAKHIPRUS");
        Map<String, String> plccBillUS = excelReaderDT2.getExcelData("BillingDetails", "PlaceCard");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default PLCC Card
        addDefaultPaymentMethodCC(plccBillUS.get("fName"), plccBillUS.get("lName"), plccBillUS.get("addressLine1"), plccBillUS.get("city"), plccBillUS.get("stateShortName"),
                plccBillUS.get("zip"), plccBillUS.get("phNum"), plccBillUS.get("cardType"), plccBillUS.get("cardNumber"), plccBillUS.get("expMonthValueUnit"), plccBillUS.get("SuccessMessageContent"));

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        AssertFailAndContinue(reviewPageActions.verifyOrderExpReviewPageSTH(), "verify the shipping address section in order review page");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {CHECKOUT, REGISTEREDONLY, USONLY, PLCC})
    public void shippingMethodsAKHIPR_Rush(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Registered user is displayed with AK HI PR Express in Review page." +
                "DT-44903");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsAKHIPRUS");
        Map<String, String> plccBillUS = excelReaderDT2.getExcelData("BillingDetails", "PlaceCard");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default PLCC Card
        addDefaultPaymentMethodCC(plccBillUS.get("fName"), plccBillUS.get("lName"), plccBillUS.get("addressLine1"), plccBillUS.get("city"), plccBillUS.get("stateShortName"),
                plccBillUS.get("zip"), plccBillUS.get("phNum"), plccBillUS.get("cardType"), plccBillUS.get("cardNumber"), plccBillUS.get("expMonthValueUnit"), plccBillUS.get("SuccessMessageContent"));

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        reviewPageActions.click(reviewPageActions.method_AKHIPRRush);
        AssertFailAndContinue(reviewPageActions.verifyOrderExpReviewPageSTH(), "verify the shipping address section in order review page");
        AssertFailAndContinue(reviewPageActions.orderLedgerSubmitOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {CHECKOUT, REGISTEREDONLY, USONLY, PLCC})
    public void shippingMethod_Priority(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Registered user is displayed with AK HI PR Express in Review page." +
                "DT-44903");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsPOBUS");
        Map<String, String> plccBillUS = excelReaderDT2.getExcelData("BillingDetails", "PlaceCard");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default PLCC Card
        addDefaultPaymentMethodCC(plccBillUS.get("fName"), plccBillUS.get("lName"), plccBillUS.get("addressLine1"), plccBillUS.get("city"), plccBillUS.get("stateShortName"),
                plccBillUS.get("zip"), plccBillUS.get("phNum"), plccBillUS.get("cardType"), plccBillUS.get("cardNumber"), plccBillUS.get("expMonthValueUnit"), plccBillUS.get("SuccessMessageContent"));

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        AssertFailAndContinue(reviewPageActions.verifyOrderExpReviewPageSTH(), "verify the shipping address section in order review page");
        AssertFailAndContinue(reviewPageActions.orderLedgerSubmitOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {CHECKOUT, REGISTEREDONLY, USONLY, PLCC})
    public void shippingMethodAdd_Priority(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if Guest user in US store is able to place an order successfully using a new shipping address");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsPOBUS");
        Map<String, String> plccBillUS = excelReaderDT2.getExcelData("BillingDetails", "PlaceCard");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default PLCC Card
        addDefaultPaymentMethodCC(plccBillUS.get("fName"), plccBillUS.get("lName"), plccBillUS.get("addressLine1"), plccBillUS.get("city"), plccBillUS.get("stateShortName"),
                plccBillUS.get("zip"), plccBillUS.get("phNum"), plccBillUS.get("cardType"), plccBillUS.get("cardNumber"), plccBillUS.get("expMonthValueUnit"), plccBillUS.get("SuccessMessageContent"));

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        AssertFailAndContinue(reviewPageActions.verifyOrderExpReviewPageSTH(), "verify the shipping address section in order review page");
        AssertFailAndContinue(reviewPageActions.validateGiftcard(), "Verify the Giftcard section in Exp Review page");
        AssertFailAndContinue(reviewPageActions.validateOrderLedgerSection(), "Verify the order Ledger in Review page");
        AssertFailAndContinue(reviewPageActions.orderLedgerSubmitOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
    }
}