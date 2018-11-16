package tests.webDT.plcc;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.UiBase;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

public class ApproveRTPSAndPayWithOtherPayment extends BaseTest {

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

    @Parameters({storeXml, usersXml})
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (user.equalsIgnoreCase("registered")) {
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        }
        headerMenuActions.deleteAllCookies();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {RTPS, USONLY,PROD_REGRESSION})
    public void rejetRtps(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in US store, verify that \n" +
                "1. PLCC_Normal Checkout_Already Reject RTPS Form from RTPS Offer Modal_Check for RTPS modal again before 30 days from day of offer rejection\n");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        addToBagFromFlip(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page, verify shipping page is displayed");
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page, verify shipping page is displayed");

        shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue"));

        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Verify RTPS model is displayed before billing page");

        AssertFailAndContinue(plccActions.clickNoThanks(), "Click No Thanks, Verify pre-screen modal is closed");
        headerMenuActions.clickOnTCPLogo();
        AssertFailAndContinue(headerMenuActions.clickReturnToBagButton(shoppingBagPageActions), "Click return to bag from billing page");
        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page, verify shipping page is displayed");
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page, verify shipping page is displayed");
        shippingPageActions.clickNextBillingButton(billingPageActions);
        AssertFailAndContinue(!plccActions.verifyPlCCForm(), "Click Next: Billing page from Shipping page, Verify RTPS model is not displayed before billing page");
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {RTPS, USONLY,PROD_REGRESSION})
    public void rejetRtps_form(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in US store, verify that \n" +
                "1. PLCC_Normal Checkout_Already Reject RTPS Form from RTPS FORM Modal_Check for RTPS modal again before 30 days from day of offer rejection\n");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        addToBagFromFlip(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page, verify shipping page is displayed");
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page, verify shipping page is displayed");

        shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue"));

        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Verify RTPS model is displayed before billing page");
        AssertFailAndContinue(plccActions.clickYesiamInterested(), "CLick Yes i'm interested button");
        plccActions.click(plccActions.plccNothanks);
        headerMenuActions.clickOnTCPLogo();
        AssertFailAndContinue(headerMenuActions.clickReturnToBagButton(shoppingBagPageActions), "Click return to bag from billing page");
        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page, verify shipping page is displayed");
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page, verify shipping page is displayed");
        shippingPageActions.clickNextBillingButton(billingPageActions);
        AssertFailAndContinue(!plccActions.verifyPlCCForm(), "Click Next: Billing page from Shipping page, Verify RTPS model is not displayed before billing page");
    }

    @Test(priority = 2, groups = {RTPS, USONLY, REGISTEREDONLY}, dataProvider = dataProviderName)
    public void myAccountHas5Fdmscard(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered when user has 5 payment methods stored in my account, verify that\n" +
                "1. Whether the user is displayed with the error message informing user that they have reached the maximum # of cards saved to their account");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        Map<String, String> mcBillUs = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
        Map<String, String> MasterCard2 = excelReaderDT2.getExcelData("BillingDetails", "MasterCard2");
        Map<String, String> Amex = excelReaderDT2.getExcelData("BillingDetails", "Amex");
        Map<String, String> Discover = excelReaderDT2.getExcelData("BillingDetails", "Discover");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Add 5 cards
        myAccountPageActions.enterCCWithExistingShippingDetails(visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"));
        myAccountPageActions.enterCCWithExistingShippingDetails(mcBillUs.get("cardType"), mcBillUs.get("cardNumber"), mcBillUs.get("expMonthValueUnit"));
        myAccountPageActions.enterCCWithExistingShippingDetails(Amex.get("cardType"), Amex.get("cardNumber"), Amex.get("expMonthValueUnit"));
        myAccountPageActions.enterCCWithExistingShippingDetails(MasterCard2.get("cardType"), MasterCard2.get("cardNumber"), MasterCard2.get("expMonthValueUnit"));
        myAccountPageActions.enterCCWithExistingShippingDetails(Discover.get("cardType"), Discover.get("cardNumber"), Discover.get("expMonthValueUnit"));

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        addToBagFromFlip(searchKeyword, qty);

        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button from shopping bag page");
        AssertFailAndContinue(plccActions.clickYesiamInterested(), "Click yes i'm interested and verify Details screen");
        plccActions.enterApplicationDetails_Approve(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"),
                UiBase.randomEmail(), "", shipDetailUS.get("birthMonth"), shipDetailUS.get("birthDate"), shipDetailUS.get("birthYear"), shipDetailUS.get("ssn"));

        AssertFailAndContinue(plccActions.validateErrorMeesageFor5cards(), "Verify Error message Applying PLCC when user has more than or equal to 5 cards in my account");
    }

    @Test(priority = 3, groups = {RTPS, USONLY, REGISTEREDONLY,PROD_REGRESSION}, dataProvider = dataProviderName)
    public void approvePLCC_PayWithFDMS(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. PLCC_Express Checkout_After Add Card from RTPS Approved modal_Switch Payment to FDMS\n" +
                "2. Also Check that PLCC is saved to my account");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        Map<String, String> Amex = excelReaderDT2.getExcelData("BillingDetails", "Amex");
        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            Amex = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");

        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            Amex = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD2");

        }
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
            plccActions.staticWait(5000); //this wait to reflect plcc acrd on review page
            AssertFailAndContinue(reviewPageActions.getAttributeValue(reviewPageActions.paymentInfo, "alt").equalsIgnoreCase("PLACE CARD"), "Click checkout button, verify plcc card details are displayed");

            AssertFailAndContinue(reviewPageActions.clickEditBillingLink(billingPageActions), "Click Edit link in and verify billing page is displayed");
            billingPageActions.enterCardDetails(Amex.get("cardNumber"),Amex.get("cardNumber") ,Amex.get("expMonthValueUnit"));
            billingPageActions.clickNextReviewButton(reviewPageActions, Amex.get("cardNumber"));
            AssertFailAndContinue(!reviewPageActions.getAttributeValue(reviewPageActions.paymentInfo, "alt").equalsIgnoreCase("PLACE CARD"), "Change payment method to FDMS and CLick Next: review button, verify Review page is displayed with FDMS card");
            if(!env.equalsIgnoreCase("prod")) {
                AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");

                getAndVerifyOrderNumber("approvePLCC_PayWithFDMS");
                myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
                myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
                AssertFailAndContinue(!myAccountPageActions.getDefaultPaymentContent().contains("ALWAYS") && myAccountPageActions.getText(myAccountPageActions.totalCards).contains("ALWAYS"), "Verify PLCC card is added to Payment Section and Not as default payment");
            }
        }
    }

    @Test(priority = 4, groups = {RTPS, USONLY, REGISTEREDONLY, PAYPAL,PROD_REGRESSION}, dataProvider = dataProviderName)
    public void approvePLCC_PayWithpaypal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. PLCC_Express Checkout_After Add Card from RTPS Approved modal_Switch Payment to paypal\n" +
                "2. Also check if PLCC card is saved to My Account");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }
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

            AssertFailAndContinue(reviewPageActions.getAttributeValue(reviewPageActions.paymentInfo, "alt").equalsIgnoreCase("PLACE CARD"), "Click checkout button, verify plcc card details are displayed");

            AssertFailAndContinue(reviewPageActions.clickEditBillingLink(billingPageActions), "Click Edit link in and verify billing page is displayed");
            /*billingPageActions.click
            billingPageActions.clickProceedWithPaypalModalButton()*/

            AssertFailAndContinue(!reviewPageActions.getAttributeValue(reviewPageActions.paymentInfo, "alt").equalsIgnoreCase("PLACE CARD"), "Change payment method to FDMS and CLick Next: review button, verify Review page is displayed with FDMS card");
            if (!env.equalsIgnoreCase("prod")) {
                AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");

                getAndVerifyOrderNumber("approvePLCC_PayWithFDMS");

                myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
                myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
                AssertFailAndContinue(myAccountPageActions.getDefaultPaymentContent().contains("ALWAYS"), "Verify PLCC card is added to Payment Section");
            }
        }
    }
}
