package tests.webDT.plcc;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.UiBase;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

public class PLCCApprove_NavigateBack extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg = "", password = "";

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
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

    @Parameters(storeXml)
    @Test(priority = 0, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH})
    public void expresscheckout_Reject(@Optional("US") String store) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. PLCC_Express Checkout_Already Reject RTPS Form from RTPS Offer Modal_Check for RTPS modal again before 30 days from day of offer rejection\n");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Payment
        myAccountPageActions.enterCCWithExistingShippingDetails(visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"));
        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button from shopping bag page");
        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Verify Pre-approved screen uis displayed");
        if (plccActions.verifyPlCCForm()) { //if condition is for to scripts pass when rtps is disabled
            AssertFailAndContinue(plccActions.clickNoThanks(), "Click No Thanks, Verify pre-screen modal is closed");
            headerMenuActions.clickOnTCPLogo();
            AssertFailAndContinue(headerMenuActions.clickReturnToBagButton(shoppingBagPageActions), "Click return to bag from billing page");
            AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button from shopping bag page");
            AssertFailAndContinue(!plccActions.verifyPlCCForm(), "Verify Pre-approved screen is not displayed again");
            AssertFailAndContinue(reviewPageActions.enterExpressCO_CVVAndSubmitOrder(visaBillUS.get("securityCode"), orderConfirmationPageActions), "Click on order submit button and place the order");
            getAndVerifyOrderNumber("expresscheckout_existingmodal");
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
            AssertFailAndContinue(!myAccountPageActions.getDefaultPaymentContent().contains("PLCC"), "Verify PLCC card is not added to Payment Section");
        }
    }

    @Parameters(storeXml)
    @Test(priority = 1, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH})
    public void expresscheckout_RejectFormModel(@Optional("US") String store) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. PLCC_Express Checkout_Already Reject RTPS Form from RTPS Offer Modal_Check for RTPS form modal again before 30 days from day of offer rejection\n");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Payment
        myAccountPageActions.enterCCWithExistingShippingDetails(visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"));

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button from shopping bag page");

        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Verify Pre-approved screen uis displayed");
        if (plccActions.verifyPlCCForm()) { //if condition is for to scripts pass when rtps is disabled

            AssertFailAndContinue(plccActions.clickYesiamInterested(), "CLick Yes i'm interested button");
            plccActions.click(plccActions.plccNothanks);
            headerMenuActions.clickOnTCPLogo();
            AssertFailAndContinue(headerMenuActions.clickReturnToBagButton(shoppingBagPageActions), "Click return to bag from billing page");
            AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button from shopping bag page");
            AssertFailAndContinue(reviewPageActions.enterExpressCO_CVVAndSubmitOrder(visaBillUS.get("securityCode"), orderConfirmationPageActions), "Click on order submit button and place the order");
            getAndVerifyOrderNumber("expresscheckout_existingmodal");
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
            AssertFailAndContinue(!myAccountPageActions.getDefaultPaymentContent().contains("PLCC"), "Verify PLCC card is not added to Payment Section");
        }
    }

    @Parameters(storeXml)
    @Test(priority = 2, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH})
    public void expresscheckout_navigateBack(@Optional("US") String store) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. PLCC_Express Checkout_After Add Card from RTPS Approved modal_Return to Shopping Bag page\n" +
                "");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Payment
        myAccountPageActions.enterCCWithExistingShippingDetails(visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"));

        addToBagBySearching(searchKeywordAndQty);
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
            headerMenuActions.clickOnTCPLogo();
            headerMenuActions.clickReturnToBagButton(shoppingBagPageActions);
            AssertFailAndContinue(reviewPageActions.enterExpressCO_CVVAndSubmitOrder(visaBillUS.get("securityCode"), orderConfirmationPageActions), "Click on order submit button and place the order");
            AssertFailAndContinue(!plccActions.verifyPlCCForm(), "Verify Pre-approved screen is not displayed again");
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");
            getAndVerifyOrderNumber("expresscheckout_existingmodal");
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
            AssertFailAndContinue(!myAccountPageActions.getDefaultPaymentContent().contains("PLCC"), "Verify PLCC card is not added to Payment Section");
        }
    }
}
