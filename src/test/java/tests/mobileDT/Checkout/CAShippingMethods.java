package tests.mobileDT.Checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

//@Test(singleThreaded = true)
public class CAShippingMethods extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = mcreateAccountActions.randomEmail();
    String password;
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("CA") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.deleteAllCookies();
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                email = mcreateAccountActions.randomEmail();
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
    public void openBrowser(@Optional("CA") String store) throws Exception {
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
            mheaderMenuActions.addStateCookie("ON");

        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CAONLY, CHECKOUT,PROD_REGRESSION})
    public void editShippingAndVerifyShipMethod(@Optional("CA") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " initially enters normal address in the shipping page and proceed to review page by selecting shipping method as Express, again navigates back to shipping page and modifies the shipping address as PO address. " +
                "Verify that shipping method section gets updated and displays only GND" +
                "DT-31620, DT-31619");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        Map<String, String> payment = dt2MobileExcel.getExcelData("BillingDetails", "MasterCard");
        if (env.equalsIgnoreCase("prod")) {
            payment = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA_PROD");
        }
        Map<String, String> caShippingAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> caPoBoXAdd = dt2MobileExcel.getExcelData("ShippingDetails", "CAPOBoxAdd");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        if (user.equalsIgnoreCase("guest")) {
            mshoppingBagPageActions.continueCheckoutAsGuest();
        }
        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }

        mshippingPageActions.enterShippingDetailsAndShipType(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), "Express", email);
        mshippingPageActions.clickNextBillingButton();

        mbillingPageActions.payWithACreditCard(payment.get("cardNumber"), payment.get("securityCode"), payment.get("expMonthValueUnit"));

        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(), "Click Review page and verify review page is displayed");
        AssertFailAndContinue(mreviewPageActions.clickOnShippingAccordion(mshippingPageActions), "Click on shipping accordian and verify shipping page is displayed");
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 2, "Verify 2 Shipping methods are displayed in shipping page.");

        mshippingPageActions.addNewAddressFromShippingPage(caPoBoXAdd.get("fName"), caPoBoXAdd.get("lName"), caPoBoXAdd.get("addressLine1"), caPoBoXAdd.get("city"), caPoBoXAdd.get("stateShortName"), caPoBoXAdd.get("zip"), caPoBoXAdd.get("phNum"), true);
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 1, "Change shipping address to POBOX and verify only Ground shipping is displayed.");
        AssertFailAndContinue(mshippingPageActions.getText(mshippingPageActions.estimatedDays).contains("Ground 7 to 10 business days"), "Verify that by clicking on the Standard shipment method box verify that 7 to 10 days shipping days are displayed below the grid");
        mshippingPageActions.clickNextBillingButton();
        mbillingPageActions.enterCVVForExpressAndClickNextReviewBtn("333");
        mreviewPageActions.clickOnShippingAccordion(mshippingPageActions);

        mshippingPageActions.addNewAddressFromShippingPage(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), true);

        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() > 1, "Change shipping address from POBOX to normal and verify only respective shipping methods are displayed.");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {CAONLY, CHECKOUT, REGISTEREDONLY,PROD_REGRESSION})
    public void validateShippingMethods_expCheckout(@Optional("CA") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As A registered user With po box address stored in My Account for CA store, Verify" +
                "1. DT-31624, DT-31623, DT-31622, DT-31621, DT-35345");

        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("ShippingDetails", "CAPOBoxAdd");
        Map<String, String> caPoBox2 = dt2MobileExcel.getExcelData("ShippingDetails", "CAPOBoxAdd2");
        Map<String, String> caShippingAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> payment = dt2MobileExcel.getExcelData("BillingDetails", "MasterCard");
        if (env.equalsIgnoreCase("prod")) {
            payment = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA_PROD");
        }
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        //Pobox 1
        mmyAccountPageActions.addNewShippingAddress(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("city"),
                shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("country"), shipDetails.get("phNum"),panCakePageActions);

        //Pobox 2
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        mmyAccountPageActions.addNewShippingAddress(caPoBox2.get("fName"), caPoBox2.get("lName"), caPoBox2.get("addressLine1"), caPoBox2.get("city"),
                caPoBox2.get("stateShortName"), caPoBox2.get("zip"), caPoBox2.get("country"), caPoBox2.get("phNum"),panCakePageActions);

        //normal Address
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        mmyAccountPageActions.addNewShippingAddress(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("city"),
                caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("country"), caShippingAdd.get("phNum"),panCakePageActions);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");

        mmyAccountPageActions.addACreditCard(payment.get("cardNumber"), payment.get("expMonthValueUnit"), payment.get("expYear"));

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);

        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions), "Click checkout button and verify review page is displayed");
        AssertFailAndContinue(mreviewPageActions.shippingMethods.size() == 1, "Only Gnd Shipping is displayed in review page");

        mreviewPageActions.clickOnShippingAccordion(mshippingPageActions);

        mshippingPageActions.editAddressFromShipping(caPoBox2.get("addressLine1"));
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 1, "Go to shipping and replace existing POBOX address with another PO box, verify only ground shipping is displayed");

        mshippingPageActions.editAddressFromShipping(caShippingAdd.get("addressLine1"));
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() > 1, "Replace existing POBOX address with normal address, verify respective shipping methods are displayed");
        
        mshippingPageActions.returnToBagPage(mshoppingBagPageActions);
        AssertFailAndContinue(mshoppingBagPageActions.removeAllProducts(), "Remove all products from bag and verify bag count is zero");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        //Change default address
        mmyAccountPageActions.changeDefaultAddress(caShippingAdd.get("addressLine1"));

        addToBagBySearching(searchKeywordAndQty);

        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions), "Click checkout button and verify review page is displayed");
        AssertFailAndContinue(mreviewPageActions.shippingMethods.size() > 1, "respective shipping address displayed in review page");
        //DT-35345
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.shippingMethods.get(0)).contains("Ground"), "Verify Ground shipping method is present.");
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.shippingMethods.get(1)).contains("Express"), "Verify Express shipping method is present.");
        
        mreviewPageActions.clickOnShippingAccordion(mshippingPageActions);

        mshippingPageActions.editAddressFromShipping(caPoBox2.get("addressLine1"));
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 1, "Go to shipping and replace existing address with POBOX address with another PO box, verify only ground shipping is displayed");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {CAONLY, CHECKOUT, GUESTONLY,PROD_REGRESSION})
    public void changeUrlAfterBag(@Optional("CA") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a guest user in CA store, add to cart then Switch to US by changing the URL and Add to item to cart and register by clicking on checkout overlay,Logout and login again and verify if the user is able to login successfully without any error\n" +
                "1. DT-31778");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        mheaderMenuActions.deleteAllCookies();
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        addToBagBySearching(searchKeywordAndQty);

        AssertFailAndContinue(mshoppingBagPageActions.clickCheckoutAsGuest(), "Click on Checkout btn in shopping bag and verify Login modal is displayed");
        AssertFailAndContinue(mloginPageActions.clickCreateAcctFromCheckoutSB(mcreateAccountActions), "Click Create one link in login page and verify create account page is displayed");
        String email1 = mcreateAccountActions.randomEmail();
        Map<String, String> na = dt2MobileExcel.getExcelData("CreateAccount", rowInExcelCA);
        AssertFailAndContinue(!mcreateAccountActions.createAnAccount(na.get("FirstName"), na.get("LastName"), email1, na.get("Password"), na.get("ZipCode"), na.get("PhoneNumber")), "Created account");

        panCakePageActions.navigateToMenu("USER");
        mmyAccountPageDrawerActions.clickLogoutLink(mheaderMenuActions);

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        AssertFailAndContinue(true, "Verify user is logged");
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
