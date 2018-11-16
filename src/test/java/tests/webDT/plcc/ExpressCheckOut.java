package tests.webDT.plcc;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.UiBase;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

public class ExpressCheckOut extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg = "", password = "";
    String env;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        headerMenuActions.deleteAllCookies();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(priority = 0, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH}, dataProvider = dataProviderName)
    public void expresscheckout_existingmodal(@Optional("us") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. PLCC_Express Checkout_RTPS Existing Account Found modal_User Initiating the model\n" +
                "2. Check the PLCC card is added as payment method\n" +
                "3. Check all the fields are editable");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");

        Map<String, String> plccExisting = excelReaderDT2.getExcelData("PLCC", "existingAccountDetails");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Payment
        myAccountPageActions.enterCCWithExistingShippingDetails(visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"));

        addToBagFromFlip(searchKeyword, qty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button from shopping bag page");

        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Verify Pre-approved screen uis displayed");
        AssertFailAndContinue(plccActions.clickYesiamInterested(), "Click yes i'm interested and verify Details screen");

        plccActions.enterApplicationDetails_Approve(plccExisting.get("fName"), plccExisting.get("lName"), plccExisting.get("addressLine1"), plccExisting.get("city"), plccExisting.get("stateShortName"), plccExisting.get("zip"), plccExisting.get("phNum"),
                UiBase.randomEmail(), "", plccExisting.get("birthMonth"), plccExisting.get("birthDate"), plccExisting.get("birthYear"), plccExisting.get("ssn"));

        AssertFailAndContinue(footerMPRCreditCardActions.verifyExistingAccountPopup(), "Verify Account Already exists popup is displayed when trying to apply a card");
        AssertFailAndContinue(footerMPRCreditCardActions.getRTPSResponseCode().equalsIgnoreCase("03"), "Verify Response code is 03");

        plccActions.click(plccActions.checkout);
        plccActions.staticWait(5000);
        AssertFailAndContinue(reviewPageActions.getAttributeValue(reviewPageActions.paymentInfo, "alt").equalsIgnoreCase("PLACE CARD"), "Click checkout button, verify plcc card details are displayed");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        AssertWarnAndContinueWithTextFont(myAccountPageActions.getDefaultPaymentContent().contains("ALWAYS"), "Verify PLCC card is added to Payment Section as a d");
        getAndVerifyOrderNumber("expresscheckout_existingmodal");
    }

    @Test(priority = 1, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH},dataProvider = dataProviderName)
    public void expresscheckout_approvePlcc(@Optional("us") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. Registered user with default shipping and billing(not plcc) has the ability to see RTPS model while performing Express checkout\n" +
                "2. Successfully approved Credit card\n" +
                "3. Check the PLCC card is added as payment method\n" +
                "4. Verify RTPS is not displayed when when disabled in RTPS");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Payment
        myAccountPageActions.enterCCWithExistingShippingDetails(visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"));

        addToBagFromFlip(searchKeyword, qty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button from shopping bag page");

        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Verify Pre-approved screen uis displayed");
        if (plccActions.verifyPlCCForm()) { //if condition is for to scripts pass when rtps is disabled
            AssertFailAndContinue(plccActions.clickYesiamInterested(), "Click yes i'm interested and verify Details screen");

            AssertFailAndContinue(plccActions.enterApplicationDetails_Approve(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"),
                    UiBase.randomEmail(), "", shipDetailUS.get("birthMonth"), shipDetailUS.get("birthDate"), shipDetailUS.get("birthYear"), shipDetailUS.get("ssn")), "Enter all the required fields and verify PLCC promotion content is displayed");

            AssertFailAndContinue(plccActions.verifyCreditLimit(), "Verify Credit Card Approval confirmation");
            AssertFailAndContinue(footerMPRCreditCardActions.getRTPSResponseCode().equalsIgnoreCase("01"), "Verify Response code is 01");
            plccActions.click(plccActions.checkout);
            plccActions.staticWait(5000); //this wait required to reflect plcc card on review page after approved
            AssertFailAndContinue(reviewPageActions.getAttributeValue(reviewPageActions.paymentInfo, "alt").equalsIgnoreCase("PLACE CARD"), "Click checkout button, verify plcc card details are displayed");
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");

            getAndVerifyOrderNumber("expresscheckout_approvePlcc");
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
            AssertFailAndContinue(myAccountPageActions.getDefaultPaymentContent().contains("ALWAYS"), "Verify PLCC card is added to Payment Section");
        }
    }

    @Test(priority = 2, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH,PROD_REGRESSION},dataProvider = dataProviderName)
    public void expresscheckout_processingError(@Optional("us") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. PLCC processing error for Express checkout" );

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Payment
        myAccountPageActions.enterCCWithExistingShippingDetails(visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"));

        addToBagFromFlip(searchKeyword, qty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button from shopping bag page");

        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Verify Pre-approved screen uis displayed");
        AssertFailAndContinue(plccActions.clickYesiamInterested(), "Click yes i'm interested and verify Details screen");

        plccActions.enterApplicationDetails_Approve(shipDetailUS.get("fName"), "Timeout", shipDetailUS.get("addressLine1"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"),
                UiBase.randomEmail(), "", shipDetailUS.get("birthMonth"), shipDetailUS.get("birthDate"), shipDetailUS.get("birthYear"), shipDetailUS.get("ssn"));

        AssertFailAndContinue(plccActions.verifyProcessingError(), "Enter user details and Verify Processing error popup");
        AssertFailAndContinue(footerMPRCreditCardActions.getRTPSResponseCode().equalsIgnoreCase("03") || footerMPRCreditCardActions.getRTPSResponseCode().equalsIgnoreCase("04"), "Verify Response code is 03 or 04");
    }

    @Test(priority = 3, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH},dataProvider = dataProviderName)
    public void expresscheckout_notEligible(@Optional("us") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. Registered user with in-eligible billing address" +
                        "2. DT-24418");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        String  validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");

        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default Address and Default Shipping method
        addDefaultShipMethod("Finame", "lname", shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Payment
        myAccountPageActions.enterCCWithExistingShippingDetails(visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"));
        addToBagFromFlip(searchKeyword, qty);
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button from shopping bag page");
        AssertFailAndContinue(!plccActions.verifyPlCCForm(), "Verify Pre-approved screen is not displayed for user with billing address not eligible");
        AssertFailAndContinue(reviewPageActions.enterExpressCO_CVVAndSubmitOrder("222", orderConfirmationPageActions), "Click on order submit button and place the order");
        getAndVerifyOrderNumber("expresscheckout_notEligible");
    }

    @Test(priority = 4, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH,PROD_REGRESSION}, dataProvider = dataProviderName)
    public void expresscheckout_loginfromCheckout(@Optional("us") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("1. Verify that the Guest user in the US store who has added products to their bag and clicks on the Checkout button from the shopping bag drawer and logging in with valid credentials. The RTPS Offer modal is displayed\n" +
                "2. Verify Global Nav banner in My Account Page\n");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }
        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        // TODO : Need to enable after configuring the GLobal Navigation banner.
        //AssertFailAndContinue(headerMenuActions.validateGlobalNavBanner(), "Verify Global navigation bar is visible below L1 category in My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Payment
        myAccountPageActions.enterCCWithExistingShippingDetails(visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"));

        logoutTheSession();
        addToBagFromFlip(searchKeyword, qty);

        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Click Checkout as guest and verify login pop");
        loginPageActions.loginAsRegisteredUserFromLoginForm(emailAddressReg, password);

        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Enter User Credentials in login form, Verify Pre-approved screen uis displayed");
        if (plccActions.verifyPlCCForm()) { //if condition is for to scripts pass when rtps is disabled
            AssertFailAndContinue(plccActions.clickYesiamInterested(), "Click yes i'm interested and verify Details screen");

            AssertFailAndContinue(plccActions.enterApplicationDetails_Approve(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"),
                    UiBase.randomEmail(), "", shipDetailUS.get("birthMonth"), shipDetailUS.get("birthDate"), shipDetailUS.get("birthYear"), shipDetailUS.get("ssn")), "Enter all the required fields and verify PLCC promotion content is displayed");

            AssertFailAndContinue(plccActions.verifyCreditLimit(), "Verify Credit Card Approval confirmation");
            AssertFailAndContinue(footerMPRCreditCardActions.getRTPSResponseCode().equalsIgnoreCase("01"), "Verify Response code is 01");
            plccActions.click(plccActions.checkout);
            plccActions.staticWait(5000); //this wait required to reflect plcc card on review page after approved
            AssertFailAndContinue(reviewPageActions.getAttributeValue(reviewPageActions.paymentInfo, "alt").equalsIgnoreCase("PLACE CARD"), "Click checkout button, verify plcc card details are displayed in review page");
            if(!env.equalsIgnoreCase("prod")) {
                AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");
                getAndVerifyOrderNumber("expresscheckout_loginfromCheckout");
            }
        }
    }
}
