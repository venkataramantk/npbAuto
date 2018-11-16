package tests.webDT.plcc;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.UiBase;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

public class ExpressCheckout_FieldVal extends BaseTest {

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
        headerMenuActions.deleteAllCookies();
        env = EnvironmentConfig.getEnvironmentProfile();

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

    @Test(priority = 0, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH,PROD_REGRESSION}, dataProvider = dataProviderName)
    public void expresscheckout_fieldValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. Validate fields in PLCC form");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        String coptText = getDT2TestingCellValueBySheetRowAndColumn("footerValidation","PLCCPrivacyPage","linkName");
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

        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Verify Pre-approved screen is displayed");
        if (plccActions.verifyPlCCForm()) { //if condition is for to scripts pass when rtps is disabled
            AssertFailAndContinue(plccActions.clickYesiamInterested(), "Click yes i'm interested and verify Details screen");
            AssertFailAndContinue(plccActions.validatedFieldsInPlccForm(), "Verify all the fields are displayed in plcc form");
            plccActions.compareRTPSText(coptText);
            AssertFailAndContinue(plccActions.validatevalues(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), emailAddressReg), "Verify Contact information is pre-populated with billing details");
        }
    }

    @Test(priority = 1, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH}, dataProvider = dataProviderName)
    public void errorMessage_Validation(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. Validate Error Message fields in PLCC form");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        String coptText = getDT2TestingCellValueBySheetRowAndColumn("footerValidation","PLCCPrivacyPage","linkName");
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

        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Verify Pre-approved screen is displayed");
        if (plccActions.verifyPlCCForm()) { //if condition is for to scripts pass when rtps is disabled
            AssertFailAndContinue(plccActions.clickYesiamInterested(), "Click yes i'm interested and verify Details screen");
            AssertFailAndContinue(plccActions.validatedFieldsInPlccForm(), "Verify all the fields are displayed in plcc form");
            plccActions.compareRTPSText(coptText);
            AssertFailAndContinue(plccActions.validatevalues(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), emailAddressReg), "Verify Contact information is pre-populated with billing details");
        }
    }

    @Test(priority = 1, groups = {RTPS, USONLY, REGISTEREDONLY, CHEETAH,PROD_REGRESSION}, dataProvider = dataProviderName)
    public void expresscheckout_giftcard(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. User is able to checkout with Giftcard in bag using PLCC");

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

        AssertFailAndContinue(footerActions.clickOnGiftCardsLink(giftCardsPageActions), "Click Gift cards Page and verify Gift card page is displayed");
        footerActions.click(footerActions.sendGC_Btn);
        AssertFailAndContinue(productDetailsPageActions.addGCFromPDP(headerMenuActions), "Adding the Gift Card from PDP");
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions);

        AssertFailAndContinue(plccActions.verifyPlCCForm(), "Verify Pre-approved screen uis displayed");
        if (plccActions.verifyPlCCForm()) { //if condition is for to scripts pass when rtps is disabled
            AssertFailAndContinue(plccActions.clickYesiamInterested(), "Click yes i'm interested and verify Details screen");
            AssertFailAndContinue(plccActions.enterApplicationDetails_Approve(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"),
                    UiBase.randomEmail(), "", shipDetailUS.get("birthMonth"), shipDetailUS.get("birthDate"), shipDetailUS.get("birthYear"), shipDetailUS.get("ssn")), "Enter all the required fields and verify PLCC promotion content is displayed");

            AssertFailAndContinue(plccActions.verifyCreditLimit(), "Verify Credit Card Approval confirmation");
            AssertFailAndContinue(footerMPRCreditCardActions.getRTPSResponseCode().equalsIgnoreCase("01"), "Verify Response code is 01");
            plccActions.click(plccActions.checkout);
            plccActions.staticWait(5000);//static wait to reflect changes
            AssertFailAndContinue(reviewPageActions.getAttributeValue(reviewPageActions.paymentInfo, "alt").equalsIgnoreCase("PLACE CARD"), "Click checkout button, verify plcc card details are displayed");
            if(!env.equalsIgnoreCase("prod")) {
                AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");
                getAndVerifyOrderNumber("expresscheckout_existingmodal");
            }
        }
    }

    /*@Parameters("store")
    @Test(priority = 5, groups = {"rtps", "usonly", "registeredonly", "cheetah"})
    public void expresscheckout_underReview(@Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered with a shipping address and PLCC added in My account as a default payment ad shipping address and standard shipping, verify that\n" +
                "1. User is able to checkout with Giftcard in bag using PLCC");

        //String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited("Gift Card", qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> underReviewdata = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
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

            plccActions.enterApplicationDetails_Approve(underReviewdata.get("fName"), underReviewdata.get("lName"), underReviewdata.get("addressLine1"), underReviewdata.get("city"), underReviewdata.get("stateShortName"), underReviewdata.get("zip"), underReviewdata.get("phNum"),
                    UiBase.randomEmail(), "", underReviewdata.get("birthMonth"), underReviewdata.get("birthDate"), underReviewdata.get("birthYear"), underReviewdata.get("ssn"));

            AssertFailAndContinue(plccActions.verifyUnderReviewModel(), "Enter al lthe required data verify under review model");
            AssertFailAndContinue(footerMPRCreditCardActions.getRTPSResponseCode().equalsIgnoreCase("02"), "Verify Response code is 02");

            plccActions.click(plccActions.checkout);
            plccActions.staticWait(5000);
            AssertFailAndContinue(reviewPageActions.getAttributeValue(reviewPageActions.paymentInfo, "alt").equalsIgnoreCase("VISA"), "Click checkout button, verify plcc card details are not displayed in billing section");
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");

            getAndVerifyOrderNumber("expresscheckout_underReview");
        }
    }*/
}
