package tests.mobileDT.Checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Listeners(MethodListener.class)
//@Test(singleThreaded = true)
public class MobileIntCheckout extends MobileBaseTest {

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
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelCA, email1);
            }
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        mheaderMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {SHOPPINGBAG, INTCHECKOUT})
    public void verifyEspotNotDisplayedForIntStore(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify Place cash earn eSpot is not getting displayed for International store");
        //DT-38112
        Map<String, String> intShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validFranceShippingDetails");
        Map<String, String> intBillingDetails = dt2ExcelReader.getExcelData("PaymentDetails", "billingDetails");
        Map<String, String> mcDetails = null;
        mfooterActions.changeCountryByCountry("France");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }
        if (env.equalsIgnoreCase("prod")) {
            mcDetails = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
        }
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        mheaderMenuActions.clickShoppingBagIcon();

        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.mprContainer), "Verify Place cash earn eSpot is not getting displayed for INT on shopping bag");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuestForIntUser(mobileIntCheckoutPageActions), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsRegForIntUser(mobileIntCheckoutPageActions), "Verify successful checkout with Registered user");
        }
        mobileIntCheckoutPageActions.enterShippingDetailsAndShipMethod(email1, intShippingAdd.get("fName"), intShippingAdd.get("lName"), intShippingAdd.get("addressLine1"),
                intShippingAdd.get("city"), intShippingAdd.get("Region"), intShippingAdd.get("zip"),
                intShippingAdd.get("phNum"), intShippingAdd.get("ShippingType"));
        if (env.equalsIgnoreCase("prod")) {
            mobileIntCheckoutPageActions.enterBillingDetailsForIntCheckout(mcDetails.get("cardNumber"), mcDetails.get("securityCode"), mcDetails.get("expMonthValue"));
        }
        mobileIntCheckoutPageActions.enterBillingDetailsForIntCheckout(intBillingDetails.get("cardNumber"), intBillingDetails.get("expMonthValueUnit"),intBillingDetails.get("securityCode"));
        mobileIntCheckoutPageActions.clickPlaceOrderIntCheckout();
        AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageRegForIntStore(), "Verify Order Confirmation Page");
        AssertFailAndContinue(!mreceiptThankYouPageActions.isDisplayed(mreceiptThankYouPageActions.mprContainer), "Verify Place cash earn eSpot is not getting displayed for INT on Order confirmation page");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {SHOPPINGBAG, GUESTONLY})
    public void internationalGuestCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        //DT-6293
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Ship Order as INT user");
        //change country
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }
        addToBagBySearching(searchKeywordAndQty);
        mheaderMenuActions.click(mheaderMenuActions.bagLink);
        mshoppingBagPageActions.click(mshoppingBagPageActions.checkoutBtn);
        mshoppingBagPageActions.click(mshoppingBagPageActions.continueAsGuest);
        mshippingPageActions.enterINTShippingDetails(getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "stateShortName"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "fName"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "lName"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "addressLine1"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "zip"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "city"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "phNum"));
        mshippingPageActions.click(mshippingPageActions.iContinueBtn);
        mshippingPageActions.enterINTBillingDetails(getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "Master Card", "cardNumber"),
                getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "Master Card", "intExp"),
                getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "Master Card", "securityCode"));
        mshippingPageActions.click(mshippingPageActions.placeoder);
        AssertFailAndContinue(mreviewPageActions.waitUntilElementDisplayed(mreceiptThankYouPageActions.confirmationTitle), "Place Successful international order");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {SHOPPINGBAG, REGISTEREDONLY})
    public void internationalRegCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        //DT-6292
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Ship Order as INT Reg user");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        }

        panCakePageActions.navigateTo("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password"));
        //change country
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        mheaderMenuActions.click(mheaderMenuActions.bagLink);
        mshippingPageActions.enterINTShippingDetails(getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "stateShortName"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "fName"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "lName"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "addressLine1"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "zip"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "city"),
                getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "ValidINTAddress", "phNum"));
        mshippingPageActions.click(mshippingPageActions.iContinueBtn);
        mshippingPageActions.enterINTBillingDetails(getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "Master Card", "cardNumber"),
                getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "Master Card", "intExp"),
                getDT2TestingCellValueBySheetRowAndColumn("PaymentDetails", "Master Card", "securityCode"));
        mshippingPageActions.click(mshippingPageActions.placeoder);
        AssertFailAndContinue(mreviewPageActions.waitUntilElementDisplayed(mreceiptThankYouPageActions.confirmationTitle), "Place Successful international order");
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
