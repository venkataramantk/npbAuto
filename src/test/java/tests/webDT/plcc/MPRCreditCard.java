package tests.webDT.plcc;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

/**
 * Created by AbdulazeezM on 8/8/2017.
 * Modified by Jagadeesh 01/05/2018
 */
public class MPRCreditCard extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg = null;
    private String password;

    @Parameters({storeXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        headerMenuActions.deleteAllCookies();
    }

    @Parameters({usersXml})
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("registered") String user) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (user.equalsIgnoreCase("registered")) {
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
            driver.get(EnvironmentConfig.getApplicationUrl());
        }
        if (user.equalsIgnoreCase("guest")) {
            emailAddressReg = headerMenuActions.randomEmail();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {WIC, USONLY})
    public void addCardInPLCCFromFooterImg(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in US store, verify that \n" +
                "1. if User is able to apply PLCC from footer\n" +
                "2. Also verify response code after PLCC approved");
        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions);
        footerMPRCreditCardActions.clickApplyToday();
        footerMPRCreditCardActions.applyPrescreenCode("invalidcode");
        AssertFailAndContinue(footerMPRCreditCardActions.waitUntilElementDisplayed(footerMPRCreditCardActions.prescreencode, 10), "Verify error message for invalid prescreen code");

        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        AssertFailAndContinue(footerMPRCreditCardActions.clickApplyToday(), "Click on apply today button");

        AssertFailAndContinue(footerMPRCreditCardActions.enterApplicationDetails_Approve(plccDetailsUS.get("fName"), plccDetailsUS.get("lName"), plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), emailAddressReg, plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn")), "Enter valid application details and click on submit button");

        AssertFailAndContinue(footerMPRCreditCardActions.getResponseCode().equalsIgnoreCase("01"), "Verify Response code is 01");
        AssertFailAndContinue(footerMPRCreditCardActions.getResponseCodeDescription().contains("APPROVE"), "Verify Response code description is APPROVE");
    }

    @Parameters(storeXml)
    @Test(priority = 1, groups = {WIC, USONLY, REGISTEREDONLY})
    public void plccExpressCheckout(@Optional("US") String store) throws Exception {

        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. Verify when applying for PLCC card verify user is displayed with Existing found page" +
                "2. Shipping cost 'Free' is displayed Order Review, Shipping page, Billing page and Order Confirmation Page");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "PlaceCard");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default PLCC
        addDefaultPaymentMethodCC(visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"), visaBillUS.get("city"), visaBillUS.get("stateShortName"),
                visaBillUS.get("zip"), visaBillUS.get("phNum"), visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"), visaBillUS.get("SuccessMessageContent"));

        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        footerMPRCreditCardActions.clickApplyToday();
        AssertFailAndContinue(footerMPRCreditCardActions.verifyExistingAccountPopup(), "Verify Account Already exists popup is displayed when trying to apply a card");
        footerMPRCreditCardActions.closePopup();

        addToBagFromFlip(searchKeyword,qty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button from shopping bag page");
        AssertFailAndContinue(reviewPageActions.getText(reviewPageActions.shippingTotalPrice).equalsIgnoreCase("Free"), "Verify Shipping cost is Free in review page");

        reviewPageActions.clickOnShippingAccordion(shippingPageActions);
        AssertFailAndContinue(reviewPageActions.getText(reviewPageActions.shippingTotalPrice).equalsIgnoreCase("Free"), "Verify Shipping cost is Free in Shipping page");
        shippingPageActions.clickNextBillingButton(billingPageActions);

        AssertFailAndContinue(reviewPageActions.getText(reviewPageActions.shippingTotalPrice).equalsIgnoreCase("Free"), "Verify Shipping cost is Free in Billing page");
        billingPageActions.clickNextReviewButton(reviewPageActions, "");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Verify user is able to submit order");

        AssertFailAndContinue(reviewPageActions.getText(reviewPageActions.shippingTotalPrice).equalsIgnoreCase("Free"), "Verify Shipping cost is Free in Order Confirmation page");
        getAndVerifyOrderNumber("plccExpressCheckout");
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {WIC, USONLY})
    public void existingAccountModel(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in US store, verify that \n" +
                "1. Alredy Account existing page when trying to apply plcc");
        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "existingAccountDetails");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        AssertFailAndContinue(footerMPRCreditCardActions.clickApplyToday(), "Click on apply today button");

        footerMPRCreditCardActions.enterApplicationDetails_Approve(plccDetailsUS.get("fName"), plccDetailsUS.get("lName"), plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), emailAddressReg, plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn"));

        AssertFailAndContinue(footerMPRCreditCardActions.verifyExistingAccountPopup(), "Verify Account Already exists popup is displayed when trying to apply a card");
        AssertFailAndContinue(footerMPRCreditCardActions.getResponseCode().equalsIgnoreCase("03"), "Verify Response code is 03");
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {WIC, USONLY})
    public void applyPLCCouponForFDMS(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        String discount = "Y01905J6GWXM5X";
        setRequirementCoverage("As a " + user + " in US store, verify that \n" +
                "1. Verify Error message when user apply PLCC 30% coupon with FDMS card details with Cart order above 10$");
        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        Map<String, String> vi = excelReaderDT2.getExcelData("BillingDetails", "Visa");

        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        AssertFailAndContinue(footerMPRCreditCardActions.clickApplyToday(), "Click on apply today button");

        AssertFailAndContinue(footerMPRCreditCardActions.enterApplicationDetails_Approve(plccDetailsUS.get("fName"), plccDetailsUS.get("lName"), plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), emailAddressReg, plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn")), "Enter valid application details and click on submit button");

        if (user.equalsIgnoreCase("guest"))
            discount = footerMPRCreditCardActions.getAttributeValue(footerMPRCreditCardActions.offerCode, "value");

        addToBagFromFlip(searchKeyword,"10");
        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page");
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page");

        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        billingPageActions.enterCardDetails(vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit"));
        billingPageActions.enterCouponCodeAndApply(discount);
        // TODO : Need to cover for succesful apply the 30% coupon
        AssertFailAndContinue(shippingPageActions.waitUntilElementDisplayed(shippingPageActions.pageLevelErrBox), "Enter FDMS card details, apply PLCC gift coupon. Verify Error message is displayed");
    }

    @Parameters(storeXml)
    @Test(priority = 4, groups = {WIC, USONLY, REGISTEREDONLY})
    public void myAccountHasLessThan5Fdmscard(@Optional("US") String store) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered when user has less than 5 payment address stored in my account, verify that\n" +
                "1. PLCC card approved without any error" +
                "2. PLCC card details should override the default payment method saved earlier in the payment section of My Account page\n" +
                "3. Verify Following information is displayed in My Account Page\n1. Last 4 Digits of Place Card\n2. Address line1\n3. City\n4. " +
                "State\n5. Zip code should match with Credit card application page");

        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");

        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        Map<String, String> mcBillUs = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default PLCC
        addDefaultPaymentMethodCC(visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"), visaBillUS.get("city"), visaBillUS.get("stateShortName"),
                visaBillUS.get("zip"), visaBillUS.get("phNum"), visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"), visaBillUS.get("SuccessMessageContent"));

        myAccountPageActions.enterPaymentInfo(mcBillUs.get("fName"), mcBillUs.get("lName"), mcBillUs.get("addressLine1"), mcBillUs.get("city"), mcBillUs.get("stateShortName"),
                mcBillUs.get("zip"), mcBillUs.get("phNum"), mcBillUs.get("cardType"), mcBillUs.get("cardNumber"), mcBillUs.get("expMonthValueUnit"), mcBillUs.get("SuccessMessageContent"));

        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        footerMPRCreditCardActions.clickApplyToday();

        AssertFailAndContinue(footerMPRCreditCardActions.enterApplicationDetails_Approve(plccDetailsUS.get("fName"), plccDetailsUS.get("lName"), plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), emailAddressReg, plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn")), "Enter valid application details and click on submit button, verify PLCC card Approved");

        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        AssertFailAndContinue(myAccountPageActions.availableCounts.size() == 3, "Verify Count of Cards increased by 1");
        AssertFailAndContinue(myAccountPageActions.getDefaultPaymentContent().contains(plccDetailsUS.get("fName").toUpperCase()), "Verify PLCC is selected as default payment");
        AssertFailAndContinue(myAccountPageActions.getDefaultPaymentContent().contains(plccDetailsUS.get("fName").toUpperCase()), "Verify PLCC payment contains First Name Entered in application");
        AssertFailAndContinue(myAccountPageActions.getDefaultPaymentContent().contains(plccDetailsUS.get("lName").toUpperCase()), "Verify PLCC payment contains Last Name Entered in application");
        /*AssertFailAndContinue(myAccountPageActions.getDefaultPaymentContent().contains(plccDetailsUS.get("addressLine1").toUpperCase()), "Verify PLCC payment contains addressLine1 Entered in application");
        AssertFailAndContinue(myAccountPageActions.getDefaultPaymentContent().contains(plccDetailsUS.get("city").toUpperCase()), "Verify PLCC payment contains city Entered in application");
        AssertFailAndContinue(myAccountPageActions.getDefaultPaymentContent().contains(plccDetailsUS.get("stateShortName").toUpperCase()), "Verify PLCC payment contains state Entered in application");
        AssertFailAndContinue(myAccountPageActions.getDefaultPaymentContent().contains(plccDetailsUS.get("zip").toUpperCase()), "Verify PLCC payment contains ZIP Entered in application");*/
    }

    @Test(dataProvider = dataProviderName, priority = 5, groups = {WIC, USONLY})
    public void wicPlccUnderReview(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in US store, verify that \n" +
                "1. PLCC under review message and Response");
        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions);
        footerMPRCreditCardActions.clickApplyToday();

        footerMPRCreditCardActions.enterApplicationDetails_Approve("Jagadeesh", "Kotha", plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), emailAddressReg, plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn"));

        AssertFailAndContinue(footerMPRCreditCardActions.waitUntilElementDisplayed(footerMPRCreditCardActions.uderReviewText), "Verify Your information is under review text is displayed");
        AssertFailAndContinue(footerMPRCreditCardActions.getResponseCode().equalsIgnoreCase("02"), "Verify Response code is 02");
    }

    @Parameters("store")
    @Test(priority = 5, groups = {WIC, USONLY, REGISTEREDONLY})
    public void myAccountHas5Fdmscard(@Optional("US") String store) throws Exception {

        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered when user has 5 payment methods stored in my account, verify that\n" +
                "1. Whether the user is displayed with the error message informing user that they have reached the maximum # of cards saved to their account");

        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");

        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        Map<String, String> mcBillUs = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
        Map<String, String> MasterCard2 = excelReaderDT2.getExcelData("BillingDetails", "MasterCard2");
        Map<String, String> Amex = excelReaderDT2.getExcelData("BillingDetails", "Amex");
        Map<String, String> Discover = excelReaderDT2.getExcelData("BillingDetails", "Discover");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default PLCC
        addDefaultPaymentMethodCC(visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"), visaBillUS.get("city"), visaBillUS.get("stateShortName"),
                visaBillUS.get("zip"), visaBillUS.get("phNum"), visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"), visaBillUS.get("SuccessMessageContent"));

        myAccountPageActions.enterPaymentInfo(mcBillUs.get("fName"), mcBillUs.get("lName"), mcBillUs.get("addressLine1"), mcBillUs.get("city"), mcBillUs.get("stateShortName"),
                mcBillUs.get("zip"), mcBillUs.get("phNum"), mcBillUs.get("cardType"), mcBillUs.get("cardNumber"), mcBillUs.get("expMonthValueUnit"), mcBillUs.get("SuccessMessageContent"));

        myAccountPageActions.enterPaymentInfo(Amex.get("fName"), Amex.get("lName"), Amex.get("addressLine1"), Amex.get("city"), Amex.get("stateShortName"),
                Amex.get("zip"), Amex.get("phNum"), Amex.get("cardType"), Amex.get("cardNumber"), Amex.get("expMonthValueUnit"), Amex.get("SuccessMessageContent"));

        myAccountPageActions.enterPaymentInfo(MasterCard2.get("fName"), MasterCard2.get("lName"), MasterCard2.get("addressLine1"), MasterCard2.get("city"), MasterCard2.get("stateShortName"),
                MasterCard2.get("zip"), MasterCard2.get("phNum"), MasterCard2.get("cardType"), MasterCard2.get("cardNumber"), MasterCard2.get("expMonthValueUnit"), MasterCard2.get("SuccessMessageContent"));

        myAccountPageActions.enterPaymentInfo(Discover.get("fName"), Discover.get("lName"), Discover.get("addressLine1"), Discover.get("city"), Discover.get("stateShortName"),
                Discover.get("zip"), Discover.get("phNum"), Discover.get("cardType"), Discover.get("cardNumber"), Discover.get("expMonthValueUnit"), Discover.get("SuccessMessageContent"));

        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        footerMPRCreditCardActions.clickApplyToday();

        footerMPRCreditCardActions.enterApplicationDetails_Approve(plccDetailsUS.get("fName"), plccDetailsUS.get("lName"), plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), emailAddressReg, plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn"));

    }
}