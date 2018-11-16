package tests.webDT.myAccount.paymentInformation;

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
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    private String password;
    Map<String, String> amexBillUS;
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
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcel, "Password");
        amexBillUS = excelReaderDT2.getExcelData("BillingDetails", "Amex");
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        addDefaultPaymentMethodCC(amexBillUS.get("fName"), amexBillUS.get("lName"), amexBillUS.get("addressLine1"), amexBillUS.get("city"), amexBillUS.get("stateShortName"),
                amexBillUS.get("zip"), amexBillUS.get("phNum"), amexBillUS.get("cardType"), amexBillUS.get("cardNumber"), amexBillUS.get("expMonthValueUnit"),amexBillUS.get("SuccessMessageContent"));
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {

        driver.get(EnvironmentConfig.getApplicationUrl());
        //  driver.manage().window().maximize();
        //  driver.manage().deleteAllCookies();
        //  driver.navigate().refresh();
    }
    @AfterMethod(alwaysRun = true)
    public void clearCookies(){
        //driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void addNewPaymentFieldValidations(@Optional("US") String store)throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store+" store - Validate that the User is able to view the all the fields, when adding a new payment method. Also verify that the user is able to validate the empty field error messages across the fields.");
        Map<String, String> masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
        List<String> errMsgEmptyField = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "InvalidDataPaymentInfo", "FieldLevelErrorMsg"));
        List<String> errMsgMaxChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount","addressFieldMaxCarErr","FieldLevelErrorMsg"));
        if(env.equalsIgnoreCase("prod")){
            masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

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
        AssertFailAndContinue(myAccountPageActions.validateMaxCharErrorPayment(errMsgMaxChar.get(0),errMsgMaxChar.get(1)),"Verify the Max char Error");
        //Adding new FDMS Payment method
        myAccountPageActions.click(myAccountPageActions.cancelButton);
        addDefaultPaymentMethodCC(masterBillUS.get("fName"), masterBillUS.get("lName"), masterBillUS.get("addressLine1"), masterBillUS.get("city"), masterBillUS.get("stateShortName"),
                masterBillUS.get("zip"), masterBillUS.get("phNum"), masterBillUS.get("cardType"), masterBillUS.get("cardNumber"), masterBillUS.get("expMonthValueUnit"), masterBillUS.get("SuccessMessageContent"));

    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void paymentFieldValidationsSplChar(@Optional("US") String store)throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store+" store - Validate that the User is able to view the all the fields, in the add new payment method section. Also verify that the user is able to validate the empty field error messages across the fields. Check whether the user is able to cancel adding a new payment method.");
        List<String> errMsgSplChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "billingInfoSplCharErr", "FieldLevelErrorMsg"));
        List<String> splChars = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "ErrorData", "SpecialChar"));
        List<String> invalidZipValue = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "ErrorData", "ZipCode"));

        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
//        myAccountPageActions.scrollDownToElement(myAccountPageActions.btn_AddNewCard);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        myAccountPageActions.staticWait(3000);
//        myAccountPageActions.scrollDownToElement(myAccountPageActions.btnAddCard);
        myAccountPageActions.click(myAccountPageActions.btnAddCard);

        AssertFailAndContinue(myAccountPageActions.paymentDetailsNameSpecialCharError(splChars.get(0),splChars.get(0), errMsgSplChar.get(0),errMsgSplChar.get(1)),"Verify the inline error messages in the First Name and Last Name fields when special characters are entered in them.");
        AssertFailAndContinue(myAccountPageActions.paymentDetailsCitySpecialCharError(splChars.get(0),errMsgSplChar.get(2)),"Verify the inline error messages in the City field when special characters are entered in it.");
        AssertFailAndContinue(myAccountPageActions.paymentDetailsAddressSpecialCharError(splChars.get(1),errMsgSplChar.get(3)),"Verify the inline error messages in the City field when special characters are entered in it.");
        AssertFailAndContinue(myAccountPageActions.validateErrInZipField(invalidZipValue.get(0),invalidZipValue.get(1),splChars.get(0),errMsgSplChar.get(4)),"Verify the inline error messages in the Zip field when invalid zip and special characters are entered in it.");
        AssertFailAndContinue(myAccountPageActions.validateCardFieldErrWithErr(splChars.get(1),errMsgSplChar.get(5),errMsgSplChar.get(6)),"Validated the error message when special characters are entered in the credit card number field and expired card date are entered.");
        AssertFailAndContinue(myAccountPageActions.validateCardFieldErrWithoutErr(splChars.get(2),errMsgSplChar.get(5),errMsgSplChar.get(6)),"Validated the error message when invalid credit card number and expired card date are entered.");
        AssertFailAndContinue(myAccountPageActions.cancelAddPaymentMethod(),"Clicked on the Cancel button while adding new Payment Method.");
    }
    @Parameters(storeXml)
    @Test(priority = 0, groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void addPaymentCCWithExistingAddress(@Optional("US") String store)throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store+" store - Validate that the User is able to add a new FDMS card as Default Payment method with an existing address. Also verify that the Card details are displayed after the payment" +
                "method is saved.");

        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if(env.equalsIgnoreCase("prod")){
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions),"Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        AssertFailAndContinue(myAccountPageActions.cancelAddPaymentMethod(),"Clicked on the Cancel button while adding new Payment Method.");
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        //Adding another FDMS Payment method
        AssertFailAndContinue(myAccountPageActions.addCCWithoutRemovingExistingCC(visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"), visaBillUS.get("city"), visaBillUS.get("stateShortName"),
                visaBillUS.get("zip"), visaBillUS.get("phNum"), visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"),visaBillUS.get("SuccessMessageContent")),"Adding new Credit card details without removing existing details.");
        headerMenuActions.clickOnUserNameAndLogout(myAccountPageDrawerActions,headerMenuActions);
        driver.manage().deleteAllCookies();
        headerMenuActions.deleteAllCookies();
        clickLoginAndLoginAsRegUser(emailAddressReg,password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions,myAccountPageActions);
        myAccountPageActions.verifyDefaultCardDetailAfterAdd(amexBillUS.get("cardType"),amexBillUS.get("cardNumber"),amexBillUS.get("expMonth"),amexBillUS.get("expYear"));
    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void addNewPaymentWithNewAddress(@Optional("US") String store)throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store+" store - Verify if the Registered user is able add a Payment method with a new shipping address when a default shipping address already exists.");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> discoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "Discover");
        if(env.equalsIgnoreCase("prod")){
            discoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions),"Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Credit Card
        addDefaultPaymentMethodCC(discoverBillUS.get("fName"), discoverBillUS.get("lName"), discoverBillUS.get("addressLine1"), discoverBillUS.get("city"), discoverBillUS.get("stateShortName"),
                discoverBillUS.get("zip"), discoverBillUS.get("phNum"), discoverBillUS.get("cardType"), discoverBillUS.get("cardNumber"), discoverBillUS.get("expMonthValueUnit"),discoverBillUS.get("SuccessMessageContent"));

    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY},priority = 0)
    public void max5Payment(@Optional("US") String store) throws Exception{
        setAuthorInfo("Srijith");
        setRequirementCoverage(store+" store - Verify the the user is able to add max of 5 cc to account");

        Map<String, String> discoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "Discover");
        Map<String, String> masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
        Map<String, String> m2BillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard2");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        String maxError = getDT2TestingCellValueBySheetRowAndColumn("MyAccount","maxPayment","FieldLevelErrorMsg");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions),"Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Credit Card
        AssertFailAndContinue(myAccountPageActions.addCCWithoutRemovingExistingCC(discoverBillUS.get("fName"), discoverBillUS.get("lName"), discoverBillUS.get("addressLine1"), discoverBillUS.get("city"), discoverBillUS.get("stateShortName"),
                discoverBillUS.get("zip"), discoverBillUS.get("phNum"), discoverBillUS.get("cardType"), discoverBillUS.get("cardNumber"), discoverBillUS.get("expMonthValueUnit"),discoverBillUS.get("SuccessMessageContent")),"Verify the user is able to add 2 card");

        AssertFailAndContinue(myAccountPageActions.addCCWithoutRemovingExistingCC(masterBillUS.get("fName"), masterBillUS.get("lName"), masterBillUS.get("addressLine1"), masterBillUS.get("city"), masterBillUS.get("stateShortName"),
                masterBillUS.get("zip"), masterBillUS.get("phNum"), masterBillUS.get("cardType"), masterBillUS.get("cardNumber"), masterBillUS.get("expMonthValueUnit"),masterBillUS.get("SuccessMessageContent")),"Verify the user is able to add 3 card");

        AssertFailAndContinue(myAccountPageActions.addCCWithoutRemovingExistingCC(m2BillUS.get("fName"), m2BillUS.get("lName"), m2BillUS.get("addressLine1"), m2BillUS.get("city"), m2BillUS.get("stateShortName"),
                m2BillUS.get("zip"), m2BillUS.get("phNum"), m2BillUS.get("cardType"), m2BillUS.get("cardNumber"), m2BillUS.get("expMonthValueUnit"),m2BillUS.get("SuccessMessageContent")),"Verify the user is able to add 4 card");

        AssertFailAndContinue(myAccountPageActions.addCCWithoutRemovingExistingCC(visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"), visaBillUS.get("city"), visaBillUS.get("stateShortName"),
                visaBillUS.get("zip"), visaBillUS.get("phNum"), visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"),visaBillUS.get("SuccessMessageContent")),"Verify the user is able to add 5 card");

        myAccountPageActions.clickPaymentAndGCLink();
        myAccountPageActions.clickAddNewCardButton();
        myAccountPageActions.enterCC(visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"),visaBillUS.get("expYear"),maxError);


    }
}