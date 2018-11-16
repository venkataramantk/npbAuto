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
public class NewPaymentMethodCC extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    private String password;
    String env;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcel, "Password");
        /*driver.manage().deleteAllCookies();
        driver.navigate().refresh();
   */  headerMenuActions.deleteAllCookies();

    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.manage().deleteAllCookies();
        driver.get(EnvironmentConfig.getApplicationUrl());
        headerMenuActions.deleteAllCookies();
     //   driver.navigate().refresh();
        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        myAccountPageActions.verifyDefaultCreditCard();
        //driver.manage().deleteAllCookies();

    }
    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        driver.manage().deleteAllCookies();
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void addNewPaymentFieldValidations(@Optional("US") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Validate that the User is able to view the all the fields, when adding a new payment method. Also verify that the user is able to validate the empty field error messages across the fields.");
        Map<String, String> masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
        List<String> errMsgEmptyField = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "InvalidDataPaymentInfo", "FieldLevelErrorMsg"));
        List<String> splCharForMoreThan = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "moreThan50", "ErrorMessages"));
        if(env.equalsIgnoreCase("prod")){
            masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }

        /*clickLoginAndLoginAsRegUser(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        */myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
//        myAccountPageActions.scrollDownToElement(myAccountPageActions.btn_AddNewCard);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        AssertFailAndContinue(myAccountPageActions.areAllBillingFieldsDisplayed(), "Validated that all the Address fields are getting displayed.");
        AssertFailAndContinue(myAccountPageActions.clickAddPaymentAndVerifyErrorMsgOnAllFields(errMsgEmptyField.get(0), errMsgEmptyField.get(1), errMsgEmptyField.get(2), errMsgEmptyField.get(3), errMsgEmptyField.get(4), errMsgEmptyField.get(5), errMsgEmptyField.get(6), errMsgEmptyField.get(7)), "Verify the inline error messages on all the fields in when leaving the billing address section and card details blank.");
        AssertFailAndContinue(myAccountPageActions.validateMoreThan50ErrMsg(splCharForMoreThan.get(0),splCharForMoreThan.get(1),splCharForMoreThan.get(2),splCharForMoreThan.get(4),splCharForMoreThan.get(5)),"Check the maximum character error message in the address book fields");

        //Adding new FDMS Payment method
        addDefaultPaymentMethodCC(masterBillUS.get("fName"), masterBillUS.get("lName"), masterBillUS.get("addressLine1"), masterBillUS.get("city"), masterBillUS.get("stateShortName"),
                masterBillUS.get("zip"), masterBillUS.get("phNum"), masterBillUS.get("cardType"), masterBillUS.get("cardNumber"), masterBillUS.get("expMonthValueUnit"), masterBillUS.get("SuccessMessageContent"));

        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        AssertFailAndContinue(myAccountPageActions.savedCardError(masterBillUS.get("fName"), masterBillUS.get("lName"), masterBillUS.get("addressLine1"), masterBillUS.get("city"), masterBillUS.get("stateShortName"),
                masterBillUS.get("zip"), masterBillUS.get("phNum"), masterBillUS.get("cardType"), masterBillUS.get("cardNumber"), masterBillUS.get("expMonthValueUnit"), masterBillUS.get("SuccessMessageContent")), "Verify the error message displyed for saving the already saved card");


    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void paymentFieldValidationsSplChar(@Optional("US") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Validate that the User is able to view the all the fields, in the add new payment method section. Also verify that the user is able to validate the empty field error messages across the fields. Check whether the user is able to cancel adding a new payment method.");
        List<String> errMsgSplChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "billingInfoSplCharErr", "FieldLevelErrorMsg"));
        List<String> splChars = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "ErrorData", "SpecialChar"));
        List<String> invalidZipValue = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "ErrorData", "ZipCode"));
        List<String> errMsgMaxChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "addressFieldMaxCarErr", "FieldLevelErrorMsg"));

       /* clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        */myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
//        myAccountPageActions.scrollDownToElement(myAccountPageActions.btn_AddNewCard);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        myAccountPageActions.staticWait(3000);
//        myAccountPageActions.scrollDownToElement(myAccountPageActions.btnAddCard);
        myAccountPageActions.click(myAccountPageActions.btnAddCard);

        AssertFailAndContinue(myAccountPageActions.paymentDetailsNameSpecialCharError(splChars.get(0), splChars.get(0), errMsgSplChar.get(0), errMsgSplChar.get(1)), "Verify the inline error messages in the First Name and Last Name fields when special characters are entered in them.");
        AssertFailAndContinue(myAccountPageActions.paymentDetailsCitySpecialCharError(splChars.get(0), errMsgSplChar.get(2)), "Verify the inline error messages in the City field when special characters are entered in it.");
        AssertFailAndContinue(myAccountPageActions.paymentDetailsAddressSpecialCharError(splChars.get(1), errMsgSplChar.get(3)), "Verify the inline error messages in the City field when special characters are entered in it.");
        AssertFailAndContinue(myAccountPageActions.validateCardFieldErrWithErr(splChars.get(2), errMsgSplChar.get(5), errMsgSplChar.get(6)), "Validated the error message when invalid credit card number and expired card date are entered.");
        AssertFailAndContinue(myAccountPageActions.validateErrInZipField(invalidZipValue.get(0), invalidZipValue.get(1), splChars.get(0), errMsgSplChar.get(4)), "Verify the inline error messages in the Zip field when invalid zip and special characters are entered in it.");
        AssertFailAndContinue(myAccountPageActions.validateCardFieldErrWithoutErr(splChars.get(1), errMsgSplChar.get(5), errMsgSplChar.get(6)), "Validated the error message when special characters are entered in the credit card number field and expired card date are entered.");
        AssertFailAndContinue(myAccountPageActions.validateMaxCharError(errMsgMaxChar.get(0), errMsgMaxChar.get(1)), "Verify the max character error message is displayed to the user");
        AssertFailAndContinue(myAccountPageActions.cancelAddPaymentMethod(), "Clicked on the Cancel button while adding new Payment Method.");
    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void addPaymentCCWithExistingAddress(@Optional("US") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Validate that the User is able to add a new FDMS card as Default Payment method with an existing address. Also verify that the Card details are displayed after the payment" +
                "method is saved.");
        String text = getDT2TestingCellValueBySheetRowAndColumn("MyAccount","TooltipContent","TermsContent");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        if(env.equalsIgnoreCase("prod")){
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");
        }

       /* clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        */myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        AssertFailAndContinue(myAccountPageActions.cancelAddPaymentMethod(), "Clicked on the Cancel button while adding new Payment Method.");
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        //Adding another FDMS Payment method
        AssertFailAndContinue(myAccountPageActions.addCCWithoutRemovingExistingCC(visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"), visaBillUS.get("city"), visaBillUS.get("stateShortName"),
                visaBillUS.get("zip"), visaBillUS.get("phNum"), visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"), visaBillUS.get("SuccessMessageContent")), "Adding new Credit card details without removing existing details.");

        myAccountPageActions.click(myAccountPageActions.lnk_AccountOverView);
        AssertFailAndContinue(myAccountPageActions.checkThePointsToolTipContent(text),"Verify the content displayed in tooltip");

    }

    @Parameters(storeXml)
    @Test(priority = 1,groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void addNewPaymentWithNewAddress(@Optional("US") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Verify if the Registered user is able add a Payment method with a new shipping address when a default shipping address already exists.");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> dicoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "Discover");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        if(env.equalsIgnoreCase("prod")){
            dicoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name1", "Value"));
/*

        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
*/
        myAccountPageActions.staticWait(3000);
        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Credit Card
        addDefaultPaymentMethodCC(dicoverBillUS.get("fName"), dicoverBillUS.get("lName"), dicoverBillUS.get("addressLine1"), dicoverBillUS.get("city"), dicoverBillUS.get("stateShortName"),
                dicoverBillUS.get("zip"), dicoverBillUS.get("phNum"), dicoverBillUS.get("cardType"), dicoverBillUS.get("cardNumber"), dicoverBillUS.get("expMonthValueUnit"), dicoverBillUS.get("SuccessMessageContent"));

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.enterExpressCO_CVVAndSubmitOrder(dicoverBillUS.get("securityCode"), orderConfirmationPageActions), "Clicked on the Order Submit button.");
            headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
            myAccountPageDrawerActions.clickLogoutLink(headerMenuActions);

            AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(3)), "Verify if the redirected to the " + deptName.get(3) + " department landing page");
            departmentLandingPageActions.lnk_Girl_Tops.click();
            categoryDetailsPageAction.mouseHover(categoryDetailsPageAction.productImages.get(0));
            categoryDetailsPageAction.click(categoryDetailsPageAction.addToBagIcon.get(0));
            AssertFailAndContinue(categoryDetailsPageAction.selectRandomSizeAndAddToBag(headerMenuActions), "Select a random size and add item to bag");
        }
    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void editAndDeletePaymentInfo(@Optional("US") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Validate that the User is able to view the all the fields, when adding a new payment method. Also verify that the user is able to validate the empty field error messages across the fields.");
        Map<String, String> masterBillUS = null;
        Map<String, String> discoverBillUS = null;
        if(env.equalsIgnoreCase("prod")){
            masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            discoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");
        }
        else {
            masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            discoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "Discover");
        }
        List<String> splCharForMoreThan = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "moreThan50", "ErrorMessages"));

       /* clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        */myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
//        myAccountPageActions.scrollDownToElement(myAccountPageActions.btn_AddNewCard);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);
        //Adding new FDMS Payment method
        addDefaultPaymentMethodCC(masterBillUS.get("fName"), masterBillUS.get("lName"), masterBillUS.get("addressLine1"), masterBillUS.get("city"), masterBillUS.get("stateShortName"),
                masterBillUS.get("zip"), masterBillUS.get("phNum"), masterBillUS.get("cardType"), masterBillUS.get("cardNumber"), masterBillUS.get("expMonthValueUnit"), masterBillUS.get("SuccessMessageContent"));
//        myAccountPageActions.click(myAccountPageActions.editLnkOnPersonalInfo);
        myAccountPageActions.clickOnEditPaymentAddressLink();
        myAccountPageActions.cancelAddPaymentMethod();
        myAccountPageActions.verifyDefaultCardDetailsAfterOrder(masterBillUS.get("cardType"), masterBillUS.get("cardNumber"), masterBillUS.get("expMonthValueUnit"), masterBillUS.get("expYear"));
        myAccountPageActions.clickAddressLink();
        myAccountPageActions.verifyDefaultShippingAddress(masterBillUS.get("fName"), masterBillUS.get("lName"), masterBillUS.get("addressLine1"), masterBillUS.get("city"), masterBillUS.get("zip"));
        //Edit added payment details
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.editDefaultPaymentDetails(discoverBillUS.get("fName"), discoverBillUS.get("lName"), discoverBillUS.get("addressLine1"), discoverBillUS.get("city"), discoverBillUS.get("stateShortName"),
                discoverBillUS.get("zip"), discoverBillUS.get("phNum"), discoverBillUS.get("cardType"), discoverBillUS.get("cardNumber"), discoverBillUS.get("expMonthValueUnit"), discoverBillUS.get("SuccessMessageContent"));
       // myAccountPageActions.click(myAccountPageActions.editPaymentAddressLnk);
        myAccountPageActions.verifyEditCardDetailsPrepopulated(discoverBillUS.get("cardType"),discoverBillUS.get("cardNumber"),discoverBillUS.get("expMonthValueUnit"),discoverBillUS.get("expYear"),discoverBillUS.get("SuccessMessageContent"));
   //     myAccountPageActions.verifyDefaultCardDetails(discoverBillUS.get("cardType"),discoverBillUS.get("cardNumber"),discoverBillUS.get("expMonthValueUnit"),discoverBillUS.get("expYear"),discoverBillUS.get("SuccessMessageContent"));
        myAccountPageActions.click(myAccountPageActions.editPaymentAddressLnk);
        myAccountPageActions.cancelDeletePayment();
        myAccountPageActions.verifyDefaultCardDetailAfterAdd(discoverBillUS.get("cardType"),discoverBillUS.get("cardNumber"),discoverBillUS.get("expMonthValueUnit"),discoverBillUS.get("expYear"));
   //     myAccountPageActions.verifyDefaultCardDetails(discoverBillUS.get("cardType"),discoverBillUS.get("cardNumber"),discoverBillUS.get("expMonthValueUnit"),discoverBillUS.get("expYear"),discoverBillUS.get("SuccessMessageContent"));
        myAccountPageActions.clickAddressLink();
        myAccountPageActions.verifyDefaultShippingAddress(masterBillUS.get("fName"), masterBillUS.get("lName"), masterBillUS.get("addressLine1"), masterBillUS.get("city"), masterBillUS.get("zip"));
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.editPaymentAddressLnk);
        myAccountPageActions.fieldErrorValidationPayment();
        myAccountPageActions.click(myAccountPageActions.editPaymentAddressLnk);
        myAccountPageActions. clickAddressDropdown();
        myAccountPageActions.clickAddNewAddress();
        AssertFailAndContinue(myAccountPageActions.validateMoreThan50ErrMsg(splCharForMoreThan.get(0),splCharForMoreThan.get(1),splCharForMoreThan.get(2),splCharForMoreThan.get(4),splCharForMoreThan.get(5)),"Check the maximum character error message in the address book fields");
        myAccountPageActions.removeExistingAddress();
    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,USONLY,REGISTEREDONLY,PROD_REGRESSION})
    public void giftCardValidation(@Optional("US") String store)throws Exception{
        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify the giftcard related validation from My Account Payment validation");

        String email = getDT2TestingCellValueBySheetRowAndColumn("GiftCard", "prod_us", "Email");
        String pwds = getDT2TestingCellValueBySheetRowAndColumn("GiftCard", "prod_us", "Password");
        List<String> gcContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount","giftCardContent","TermsContent"));
        //clickLoginAndLoginAsRegUser(emailAddressReg, password);
        Map<String,String> giftCardDetails = excelReaderDT2.getExcelData("GiftCard","usgc1");
       // AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
        AssertFailAndContinue(myAccountPageActions.validateGiftCardInMyAcc(gcContent.get(0)),"Verify the add new GC section in MyAccount ");
        myAccountPageActions.click(myAccountPageActions.cancel_Button);
        headerMenuActions.clickOnUserNameAndLogout(myAccountPageDrawerActions,headerMenuActions);
        clickLoginAndLoginAsRegUser(email,pwds);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
        AssertFailAndContinue(myAccountPageActions.addGcToAccount(giftCardDetails.get("Card"),giftCardDetails.get("Pin"),giftCardDetails.get("ErrorMessage")),"Verify the error message displayed while trying to save GC without checking re-captcha");
        myAccountPageActions.click(myAccountPageActions.cancelButton);
        myAccountPageActions.staticWait(3000);
        AssertFailAndContinue(myAccountPageActions.closeGiftCardValidation(giftCardDetails.get("Message")),"Verify the text displayed in GC delete confirmation modal");

    }
}


