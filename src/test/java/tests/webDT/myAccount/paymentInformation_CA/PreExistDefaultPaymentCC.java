package tests.webDT.myAccount.paymentInformation_CA;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by VenkataramanT on 7/12/2017.
 */
public class PreExistDefaultPaymentCC extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountCA";
    String emailAddressReg;
    String emailAddressGuest;
    private String password;
    String env;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("CA") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
//        driver.get(EnvironmentConfig.getCA_ApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        headerMenuActions.deleteAllCookies();
        footerActions.changeCountryAndLanguage("CA", "English");
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcel, "Password");
        Map<String, String> amexBillCA = excelReaderDT2.getExcelData("BillingDetails", "AmexCA");
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        addDefaultPaymentMethodCC(amexBillCA.get("fName"), amexBillCA.get("lName"), amexBillCA.get("addressLine1"), amexBillCA.get("city"), amexBillCA.get("stateShortName"),
                amexBillCA.get("zip"), amexBillCA.get("phNum"), amexBillCA.get("cardType"), amexBillCA.get("cardNumber"), amexBillCA.get("expMonthValueUnit"), amexBillCA.get("SuccessMessageContent"));
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("CA") String store) throws Exception {
        driver.manage().deleteAllCookies();
//        driver.get(EnvironmentConfig.getCA_ApplicationUrl());
        headerMenuActions.deleteAllCookies();
        footerActions.changeCountryAndLanguage("CA", "English");
    }

   /* @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }*/

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION, CAONLY,PROD_REGRESSION})
    public void addNewPaymentFieldValidations(@Optional("CA") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Validate that the User is able to view the all the fields, when adding a new payment method. Also verify that the user is able to validate the empty field error messages across the fields.");
        Map<String, String> masterBillCA = excelReaderDT2.getExcelData("BillingDetails", "MasterCardCA");
        List<String> errMsgEmptyField = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "InvalidDataPaymentInfoCA", "FieldLevelErrorMsg"));
        if(env.equalsIgnoreCase("prod")){
            masterBillCA = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
//        myAccountPageActions.scrollDownToElement(myAccountPageActions.btn_AddNewCard);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        AssertFailAndContinue(myAccountPageActions.areAllBillingFieldsDisplayed(), "Validated that all the Address fields are getting displayed.");
        AssertFailAndContinue(myAccountPageActions.clickAddPaymentAndVerifyErrorMsgOnAllFields(errMsgEmptyField.get(0), errMsgEmptyField.get(1), errMsgEmptyField.get(2), errMsgEmptyField.get(3), errMsgEmptyField.get(4), errMsgEmptyField.get(5), errMsgEmptyField.get(6), errMsgEmptyField.get(7)), "Verify the inline error messages on all the fields in when leaving the billing address section and card details blank.");

        //Adding new FDMS Payment method
        addDefaultPaymentMethodCC(masterBillCA.get("fName"), masterBillCA.get("lName"), masterBillCA.get("addressLine1"), masterBillCA.get("city"), masterBillCA.get("stateShortName"),
                masterBillCA.get("zip"), masterBillCA.get("phNum"), masterBillCA.get("cardType"), masterBillCA.get("cardNumber"), masterBillCA.get("expMonthValueUnit"), masterBillCA.get("SuccessMessageContent"));

    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION, CAONLY,PROD_REGRESSION})
    public void paymentFieldValidationsSplChar(@Optional("CA") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Validate that the User is able to view the all the fields, in the add new payment method section. Also verify that the user is able to validate the empty field error messages across the fields. Check whether the user is able to cancel adding a new payment method.");
        List<String> errMsgSplChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "billingInfoSplCharErr", "FieldLevelErrorMsg"));
        List<String> splChars = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "ErrorData", "SpecialChar"));
        List<String> invalidZipValue = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "ErrorData", "ZipCode"));

        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.scrollDownToElement(myAccountPageActions.btn_AddNewCard);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.scrollDownToElement(myAccountPageActions.btnAddCard);
        myAccountPageActions.click(myAccountPageActions.btnAddCard);

        AssertFailAndContinue(myAccountPageActions.paymentDetailsNameSpecialCharError(splChars.get(0), splChars.get(0), errMsgSplChar.get(0), errMsgSplChar.get(1)), "Verify the inline error messages in the First Name and Last Name fields when special characters are entered in them.");
        AssertFailAndContinue(myAccountPageActions.paymentDetailsCitySpecialCharError(splChars.get(0), errMsgSplChar.get(2)), "Verify the inline error messages in the City field when special characters are entered in it.");
        AssertFailAndContinue(myAccountPageActions.paymentDetailsAddressSpecialCharError(splChars.get(1), errMsgSplChar.get(3)), "Verify the inline error messages in the City field when special characters are entered in it.");
        AssertFailAndContinue(myAccountPageActions.validateErrInZipField(invalidZipValue.get(0), invalidZipValue.get(1), splChars.get(0), errMsgSplChar.get(4)), "Verify the inline error messages in the Zip field when invalid zip and special characters are entered in it.");
        AssertFailAndContinue(myAccountPageActions.validateCardFieldErrWithErr(splChars.get(1), errMsgSplChar.get(5), errMsgSplChar.get(6)), "Validated the error message when special characters are entered in the credit card number field and expired card date are entered.");
        AssertFailAndContinue(myAccountPageActions.validateCardFieldErrWithoutErr(splChars.get(2), errMsgSplChar.get(5), errMsgSplChar.get(6)), "Validated the error message when invalid credit card number and expired card date are entered.");
        AssertFailAndContinue(myAccountPageActions.cancelAddPaymentMethod(), "Clicked on the Cancel button while adding new Payment Method.");
    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION, CAONLY,PROD_REGRESSION})
    public void addPaymentCCWithExistingAddress(@Optional("CA") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Validate that the User is able to add a new FDMS card as Default Payment method with an existing address. Also verify that the Card details are displayed after the payment" +
                "method is saved.");

        Map<String, String> visaBillCA = excelReaderDT2.getExcelData("BillingDetails", "VisaCA");
        if(env.equalsIgnoreCase("prod")){
            visaBillCA = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        AssertFailAndContinue(myAccountPageActions.cancelAddPaymentMethod(), "Clicked on the Cancel button while adding new Payment Method.");
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        //Adding another FDMS Payment method
        AssertFailAndContinue(myAccountPageActions.addCCWithoutRemovingExistingCC(visaBillCA.get("fName"), visaBillCA.get("lName"), visaBillCA.get("addressLine1"), visaBillCA.get("city"), visaBillCA.get("stateShortName"),
                visaBillCA.get("zip"), visaBillCA.get("phNum"), visaBillCA.get("cardType"), visaBillCA.get("cardNumber"), visaBillCA.get("expMonthValueUnit"), visaBillCA.get("SuccessMessageContent")), "Adding new Credit card details without removing existing details.");

    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION, CAONLY,PROD_REGRESSION})
    public void addNewPaymentWithNewAddress(@Optional("CA") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Verify if the Registered user is able add a Payment method with a new shipping address when a default shipping address already exists.");

        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> discoverBillCA = excelReaderDT2.getExcelData("BillingDetails", "DiscoverCA");
        if(env.equalsIgnoreCase("prod")){
            discoverBillCA = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"), shipDetailCA.get("addressLine2"), shipDetailCA.get("city"),
                shipDetailCA.get("country"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"));

        //Adding default Credit Card
        addDefaultPaymentMethodCC(discoverBillCA.get("fName"), discoverBillCA.get("lName"), discoverBillCA.get("addressLine1"), discoverBillCA.get("city"), discoverBillCA.get("stateShortName"),
                discoverBillCA.get("zip"), discoverBillCA.get("phNum"), discoverBillCA.get("cardType"), discoverBillCA.get("cardNumber"), discoverBillCA.get("expMonthValueUnit"), discoverBillCA.get("SuccessMessageContent"));

    }
}
