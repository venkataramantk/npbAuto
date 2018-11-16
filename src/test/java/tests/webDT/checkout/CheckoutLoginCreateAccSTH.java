package tests.webDT.checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

/**
 * Created by Venkat on 04/05/2017.
 */

//DT-752
public class CheckoutLoginCreateAccSTH extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    String emailAddressGuest;
    private String password;
    String env;

    @Parameters({"store", "users"})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        headerMenuActions.deleteAllCookies();
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
        headerMenuActions.deleteAllCookies();
    }

    @Parameters("store")
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        emailAddressGuest = createAccountActions.randomEmail();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {CHECKOUT,REGRESSION, GUESTONLY, PRODUCTION, USONLY})
    public void loginFromShipPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Guest user having Ship to Home products in the bag, continues Guest Checkout, is able to login from the Shipping page.");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        //Clicked on Proceed to Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Clicked on the Checkout button to land on Checkout login page.");
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Clicked on the continue as Guest button, to resume checkout as a guest user.");
        AssertFailAndContinue(shippingPageActions.loginFromESpot(loginPageActions, emailAddressReg, password), "Perform Login from the my Place Rewards eSpot.");
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {CHECKOUT, REGRESSION, GUESTONLY, USONLY,PROD_REGRESSION})
    public void createActFromShipPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Guest user having Ship to Home products in the bag, continues Guest Checkout, is able to create a new account from the Shipping page.");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);

        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        //Clicked on Proceed to Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Clicked on the Checkout button to land on Checkout login page.");
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Clicked on the continue as Guest button, to resume checkout as a guest user.");
        AssertFailAndContinue(shippingPageActions.createAccountFromESpot(createAccountActions, emailAddressGuest, acct.get("FirstName"), acct.get("LastName"), password, acct.get("ZipCode"), acct.get("PhoneNumber")), "Perform Create Account from the my Place Rewards eSpot.");
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {CHECKOUT, REGRESSION, GUESTONLY, USONLY,PROD_REGRESSION})
    public void loginFromBillPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Guest user having Ship to Home products in the bag, continues Guest Checkout, is able to login from the Billing page." +
                "DT-44905");

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");

        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        //Clicked on Proceed to Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Clicked on the Checkout button to land on Checkout login page.");
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Clicked on the continue as Guest button, to resume checkout as a guest user.");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.loginFromESpot(loginPageActions, emailAddressReg, password), "Perform Login from the my Place Rewards eSpot.");
        billingPageActions.returnToShippingPage(shippingPageActions);

    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {CHECKOUT, REGRESSION, GUESTONLY, USONLY,PROD_REGRESSION})
    public void createActFromBillPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Guest user having Ship to Home products in the bag, continues Guest Checkout, is able to create a new account from the Billing page.");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);

        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        //Clicked on Proceed to Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Clicked on the Checkout button to land on Checkout login page.");
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Clicked on the continue as Guest button, to resume checkout as a guest user.");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.createAccountFromESpot(createAccountActions, emailAddressGuest, acct.get("FirstName"), acct.get("LastName"), password, acct.get("ZipCode"), acct.get("PhoneNumber")), "Perform Create Account from the my Place Rewards eSpot.");
    }

    @Test(dataProvider = dataProviderName, priority = 4, groups = {CHECKOUT, REGRESSION, GUESTONLY, USONLY,PROD_REGRESSION})
    public void abortLoginFromShipPageCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Guest user having Ship to Home products in the bag, continues Guest Checkout, is able to initiate login from the Shipping page. Also validate that the User is able to Cancel the login and continue checkout as Guest User" +
                "DT-44905" +"DT-44908");

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> mc = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");

        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            mc = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            mc = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
        }
        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        //Clicked on Proceed to Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Clicked on the Checkout button to land on Checkout login page.");
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Clicked on the continue as Guest button, to resume checkout as a guest user.");
        AssertFailAndContinue(shippingPageActions.fillLoginDetailsCloseOverlay(loginPageActions, emailAddressReg, password), "Initiate Login from the my Place Rewards eSpot.");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            getAndVerifyOrderNumber("abortLoginFromShipPageCheckout");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 5, groups = {CHECKOUT, REGRESSION, GUESTONLY, USONLY,PROD_REGRESSION})
    public void abortCreateActFromShipPageCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Guest user having Ship to Home products in the bag, continues Guest Checkout, is able to initiate create account from the Shipping page. Also validate that the User is able to Cancel the create account and continue checkout as Guest User in " + store + " store");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);

        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        //Clicked on Proceed to Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Clicked on the Checkout button to land on Checkout login page.");
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Clicked on the continue as Guest button, to resume checkout as a guest user.");
        AssertFailAndContinue(shippingPageActions.fillCreateAccDetailsCloseOverlay(createAccountActions, emailAddressGuest, acct.get("FirstName"), acct.get("LastName"), password, acct.get("ZipCode"), acct.get("PhoneNumber")), "Initiate Create Account from the my Place Rewards eSpot.");
    }

    @Test(dataProvider = dataProviderName, priority = 6, groups = {CHECKOUT, REGRESSION, GUESTONLY, USONLY,PROD_REGRESSION})
    public void abortLoginFromBillPageCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Guest user having Ship to Home products in the bag, continues Guest Checkout, is able to initiate login from the Billing page. Also validate that the User is able to Cancel the login flow and continue checkout as Guest User in " + store + " Store");

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");

        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        //Clicked on Proceed to Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Clicked on the Checkout button to land on Checkout login page.");
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Clicked on the continue as Guest button, to resume checkout as a guest user.");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.loginFromESpot(loginPageActions, emailAddressReg, password), "Perform Login from the my Place Rewards eSpot.");
    }

    @Test(dataProvider = dataProviderName, priority = 7, groups = {CHECKOUT, REGRESSION, GUESTONLY, USONLY,PROD_REGRESSION})
    public void abortCreateActFromBillPageCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Guest user having Ship to Home products in the bag, continues Guest Checkout, is able to initiate create account from the Billing page. Also validate that the User is able to Cancel the create account flow and continue checkout as Guest User" + store + " store");

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);

        addToBagBySearching(itemsAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        //Clicked on Proceed to Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Clicked on the Checkout button to land on Checkout login page.");
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Clicked on the continue as Guest button, to resume checkout as a guest user.");

        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        AssertFailAndContinue(billingPageActions.createAccountFromESpot(createAccountActions, emailAddressGuest, acct.get("FirstName"), acct.get("LastName"), password, acct.get("ZipCode"), acct.get("PhoneNumber")), "Perform Create Account from the my Place Rewards eSpot.");
    }
}
