package tests.webDT.shoppingBag;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by AbdulazeezM on 3/27/2017.
 */
public class CreateAccountModal extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(CreateAccountModal.class);
    String existingEmailAddress;
    private String password;
    String rowInExcel;

    @Parameters({storeXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            existingEmailAddress = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "EmailId");
            ;
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
            rowInExcel = rowInExcelUS;

        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            existingEmailAddress = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "EmailId");
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
            rowInExcel = rowInExcelCA;
        }
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }

    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY})
    public void createAccModal(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the guest user can validate the create acc drawer fields when adding the product to WL from shopping bag page");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        //headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickWLIconAsGuestUser(loginPageActions), "As a guest user click on WL icon in shopping bag page and verify if the login overlay is getting displayed");
        AssertFailAndContinue(loginPageActions.clickCreateAccountLink(createAccountActions), "Click on create acc button in login overlay and verify the create acc drawer is displayed");
        AssertFailAndContinue(createAccountActions.validateFieldsInPage(), "Validate the fields and button present in the create acc drawer");
        AssertFailAndContinue(createAccountActions.clickReturnToLoginBtn(loginDrawerActions), "Click on return to login button and check login drawer is displayed");
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY})
    public void createAccFromHeaderInBag(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the guest user can create an account from shopping bag page and also validate the fields present in the overlay");

        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        //headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickWLIconAsGuestUser(loginPageActions), "As a guest user click on WL icon in shopping bag page and verify if the login overlay is getting displayed");
        AssertFailAndContinue(loginPageActions.clickCreateAccountLink(createAccountActions), "Click on create account link from the login page");

        AssertFailAndContinue(createAccountActions.createNewAccount(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), acct.get("SuccessMessage")), "");
        AssertFailAndContinue(shoppingBagPageActions.validateOrderLedgerSection(headerMenuActions), "Verifying the order ledger section after creating an account from login pop up");

        //    AssertFailAndContinue(loginPageActions.loginAsRegisteredUserFromLoginForm(existingEmailAddress, password), "Entered the valid credentials and performed Login.");
        AssertFailAndContinue(headerMenuActions.clickLoggedLink(myAccountPageDrawerActions), "Click on Hi User name link and check user navigate to my account page drawer");
        AssertFailAndContinue(myAccountPageDrawerActions.clickLogoutLink(headerMenuActions), "Click on logout link and check the user become guest user");
        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions), "Click on create account link from the header and check user displayed with the create account drawer");
        AssertFailAndContinue(createAccountActions.createNewAccount(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), acct.get("SuccessMessage")), "");

    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void errMsgValidation(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the guest user can validate the create acc drawer field and verify the appropriate error message");

        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        List<String> splChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "specialChar", "Value"));
        List<String> expErrorMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "newErrMsgContent"));
        List<String> expErrorMsgSp = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "newExpectedContentSp"));
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        addToBagBySearching(itemsAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickWLIconAsGuestUser(loginPageActions), "As a guest user click on WL icon in shopping bag page and verify if the login overlay is getting displayed");
        AssertFailAndContinue(loginPageActions.clickCreateAccountLink(createAccountActions), "Verify if user displayed with create acc drawer on clicking the create acc button in login drawer");
        AssertFailAndContinue(createAccountActions.createAccountExistingEmail(existingEmailAddress, acct.get("FirstName"), acct.get("LastName"), password, acct.get("ZipCode"), acct.get("PhoneNumber"), expErrorMsg.get(9)), "Validated the error message displayed when an existing email is used to create a new account");
        AssertFailAndContinue(createAccountActions.validateEmptyFieldErrMessages(expErrorMsg.get(0), expErrorMsg.get(1), expErrorMsg.get(2), expErrorMsg.get(3), expErrorMsg.get(4), expErrorMsg.get(5), expErrorMsg.get(6), expErrorMsg.get(7), expErrorMsg.get(8)), "Validated the inline Error messages for all the mandatory fields.");
        AssertFailAndContinue(createAccountActions.validateSingleFldEmptyErrMsgs(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), expErrorMsg.get(0), expErrorMsg.get(1), expErrorMsg.get(2), expErrorMsg.get(3), expErrorMsg.get(4), expErrorMsg.get(5), expErrorMsg.get(6), expErrorMsg.get(7), expErrorMsg.get(8), ""), "Validated the presence of the error messages when any one of the fields are left empty and the Create Account button is clicked.");
        AssertFailAndContinue(createAccountActions.validateErrOnFNameFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(0), expErrorMsgSp.get(0)), "Validated the error message for the First Name field");
        AssertFailAndContinue(createAccountActions.validateErrOnLNameFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(0), expErrorMsgSp.get(1)), "Validated the error message for the Last Name field");
        AssertFailAndContinue(createAccountActions.validateErrOnEmailFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(1), expErrorMsgSp.get(2)), "Validated the Email field error message.");
        AssertFailAndContinue(createAccountActions.validateShowHideLnk("P@ssw0rd"), "Validated the Show/Hide feature of the Show/Hide Password and Show/Hide Confirm Password links");
        AssertFailAndContinue(createAccountActions.validateErrOnPhoneFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(2), expErrorMsgSp.get(3)), "Validated the error message for the Phone field when less than 10 digits are entered.");
        AssertFailAndContinue(createAccountActions.validateErrOnPhoneFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(3), expErrorMsgSp.get(3)), "Validated the error message for the Phone field when alpha characters are entered.");
        loginPageActions.clickCloseLoginModal();
        AssertFailAndContinue(headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions), "Click on createaccount link from the header and check user displayed with the create account drawer");
        AssertFailAndContinue(createAccountActions.createAccountExistingEmail(existingEmailAddress, acct.get("FirstName"), acct.get("LastName"), password, acct.get("ZipCode"), acct.get("PhoneNumber"), expErrorMsg.get(9)), "Validated the error message displayed when an existing email is used to create a new account");
        AssertFailAndContinue(loginDrawerActions.clickCreateAccButton(createAccountActions), "Verify if user displayed with create acc drawer on clicking the create acc button in login drawer");
        AssertFailAndContinue(createAccountActions.validateEmptyFieldErrMessages(expErrorMsg.get(0), expErrorMsg.get(1), expErrorMsg.get(2), expErrorMsg.get(3), expErrorMsg.get(4), expErrorMsg.get(5), expErrorMsg.get(6), expErrorMsg.get(7), expErrorMsg.get(8)), "Validated the inline Error messages for all the mandatory fields.");
        AssertFailAndContinue(createAccountActions.validateSingleFldEmptyErrMsgs(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), expErrorMsg.get(0), expErrorMsg.get(1), expErrorMsg.get(2), expErrorMsg.get(3), expErrorMsg.get(4), expErrorMsg.get(5), expErrorMsg.get(6), expErrorMsg.get(7), expErrorMsg.get(8), ""), "Validated the presence of the error messages when any one of the fields are left empty and the Create Account button is clicked.");
        AssertFailAndContinue(createAccountActions.validateErrOnFNameFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(0), expErrorMsgSp.get(0)), "Validated the error message for the First Name field");
        AssertFailAndContinue(createAccountActions.validateErrOnLNameFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(0), expErrorMsgSp.get(1)), "Validated the error message for the Last Name field");
        AssertFailAndContinue(createAccountActions.validateErrOnEmailFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(1), expErrorMsgSp.get(2)), "Validated the Email field error message.");
        AssertFailAndContinue(createAccountActions.validateShowHideLnk("P@ssw0rd"), "Validated the Show/Hide feature of the Show/Hide Password and Show/Hide Confirm Password links");
        AssertFailAndContinue(createAccountActions.validateErrOnPhoneFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(2), expErrorMsgSp.get(3)), "Validated the error message for the Phone field when less than 10 digits are entered.");
        AssertFailAndContinue(createAccountActions.validateErrOnPhoneFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(3), expErrorMsgSp.get(3)), "Validated the error message for the Phone field when alpha characters are entered.");

    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void pwdFiledErrMsg(@Optional("US") String store) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if a guest user able to view the appropriate error message in create an account drawer when invalid values are entered in the Password and Confirm Password fields/Script are getting failed due to the defect DT-14993");

        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        List<String> splChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "specialChar", "Value"));
        List<String> expPwdError = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "expectedPwdErrNew"));
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        addToBagBySearching(itemsAndQty);
        //headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickWLIconAsGuestUser(loginPageActions), "As a guest user click on WL icon in shopping bag page and verify if the login overlay is getting displayed");
        AssertFailAndContinue(loginPageActions.clickCreateAccountLink(createAccountActions), "Clicked on the Create Account link on the Header.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(5), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has less than 8 characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(6), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no special characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(7), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(8), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the has no uppercase. Error Msg: Password must include atleast one uppercase character(s).");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(6), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no special characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(7), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(8), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no upper case alpha characters.");
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void zipCodeErrValidation(@Optional("CA") String store) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if a guest user is able to view the appropriate error message in create an account drawer when invalid values are entered in the Zip/Postal Code field/Script are getting failed due to the defect DT-14993");

        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        List<String> splChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "specialChar", "Value"));
        List<String> expZipError = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "expectedZipErr"));
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
       // headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickWLIconAsGuestUser(loginPageActions), "As a guest user click on WL icon in shopping bag page and verify if the login overlay is getting displayed");
        AssertFailAndContinue(loginPageActions.clickCreateAccountLink(createAccountActions), "Clicked on the Create Account link on the Header.");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(0), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered Zip value has 6 numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(9), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered Zip value has less than 5 numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(10), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered value is an invalid Postal Code having 7 characters.");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(11), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered Postal Code value has more than 7 alpha characters.");
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void createAccFromEspotInSB(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if when the user clicks on 'create account' link from the Forgot password drawer,verify that 'Create account' drawer is displayed to the user.");
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        AssertFailAndContinue(headerMenuActions.clickBagIconOnEmptyBag(shoppingBagDrawerActions), "Verify if guest user redirected to the empty shopping bag drawer");
        AssertFailAndContinue(shoppingBagDrawerActions.clickOnViewBagOnEmptyBag(shoppingBagPageActions), "Verify if user can able to click on the view bag link from the shopping bag drawer");
        AssertFailAndContinue(shoppingBagPageActions.validateBagWithoutProd(), "validate the shopping bag page without any product");

        addToBagBySearching(searchKeywordAndQty);
        //headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        AssertFailAndContinue(shoppingBagPageActions.validateBagWithProd(), "Validate the shopping bag page with the product");
        AssertFailAndContinue(shoppingBagPageActions.clickEspotCreateAccLnk(createAccountActions), "Click on create account link from the espot notification");
        AssertFailAndContinue(createAccountActions.validateFieldsInPage(), "Validate the fields present in the create account modal");
        if (store.equalsIgnoreCase("US")) {
            List<String> txtContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "messageOnForm", "Content"));
            Map<String, String> termsTxt = excelReaderDT2.getExcelData("CreateAccount", "messageOnForm");
            String earnPtsMsg = txtContent.get(0), createMPRAcctMsg = txtContent.get(1), termsTxtMsg = termsTxt.get("TermsTextUS"), remMeMsg = txtContent.get(2), remMeSubTxtMsg = txtContent.get(3);
            AssertFailAndContinue(createAccountActions.validateOverlayContent(earnPtsMsg, createMPRAcctMsg, termsTxtMsg, remMeMsg, remMeSubTxtMsg), "Validated the fields earn points msg, create MPR Acct Msg, Terms And Conditions text, Remember Me Msg and Not recommended on shared devices in the Create Account drawer.");

        }
        if (store.equalsIgnoreCase("CA")) {
            List<String> txtContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "messageOnForm", "ContentCA"));
            Map<String, String> termsTxt = excelReaderDT2.getExcelData("CreateAccount", "messageOnForm");
            String earnPtsMsg1 = txtContent.get(0), createMPRAcctMsg1 = txtContent.get(1), termsTxtMsg1 = termsTxt.get("TermsTextCA"), remMeMsg1 = txtContent.get(2), remMeSubTxtMsg1 = txtContent.get(3);
            AssertFailAndContinue(createAccountActions.validateOverlayContent(earnPtsMsg1, createMPRAcctMsg1, termsTxtMsg1, remMeMsg1, remMeSubTxtMsg1), "Validated the fields earn points msg, create MPR Acct Msg, Terms And Conditions text, Remember Me Msg and Not recommended on shared devices in the Create Account drawer.");
        }
        AssertFailAndContinue(createAccountActions.createNewAccount(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), acct.get("SuccessMessage")), "");
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void createAccFromWL(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if user can create a new account from the shopping bag page on clicking the WL icon");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        addToBagBySearching(itemsAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickWLIconAsGuestUser(loginPageActions), "As a guest user click on WL icon in shopping bag page and verify if the login overlay is getting displayed");
        AssertFailAndContinue(loginPageActions.clickCreateAccountLink(createAccountActions), "Clicked on the Create Account link from the login overlay");
    }
}