package tests.webDT.globalComponents.createAccountDrawer;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by Venkat on 2/17/2017.
 */

public class CreateAccount extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg;
    private String rowInExcel = "CreateAccountUS";
    private String password;
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        env = EnvironmentConfig.getEnvironmentProfile();
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");

        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
        }
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }
        driver.navigate().refresh();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void createNewAccount(@Optional("US") String store) {

        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that guest user in " + store + " store," + " in US Store is able to create an account by clicking on 'Create Account' link from header.");
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
//        createNewAccountFromHeader(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), acct.get("SuccessMessage"));
        clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void createAccFieldsValidate(@Optional("US") String store) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that guest user in " + store + " store," + " is able to validate the contents of the Create Account drawer." +
                " DT-43790");
        String tooltipContent = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "tooltipContent", "ToolTipContent");
        AssertFailAndContinue(headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions), "Clicked on the Create Account link on the Header, verify Create Account button is in disable state.");
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
        AssertFailAndContinue(createAccountActions.validateToolTipContent(), "validate content of tool tip icon in the Create Account drawer.");
//        createAccountActions.click(createAccountActions.toolTip);
        AssertFailAndContinue(overlayHeaderActions.clickLoginOnOverlayHeader(), "Clicked on the Login link on the Header.");
        AssertFailAndContinue(overlayHeaderActions.clickCreateAccountOnOverlayHeader(createAccountActions), "Clicked on the Create Account link on the Header.");
        AssertFailAndContinue(overlayHeaderActions.clickWishListOnOverlayHeader(), "Clicked on the WishList Icon on the Header.");
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void createNewAccountCAZip(@Optional("US") String store) {

        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that the  guest user in" + store + "store is," + " able to create an account using a Canadian Address.");
        if (store.equalsIgnoreCase("US")) {
            Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", "CreateAccountCA");
            createNewAccountFromHeader(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), acct.get("SuccessMessage"));
        }
        if (store.equalsIgnoreCase("CA")) {
            Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", "CreateAccountUS");
            createNewAccountFromHeader(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), acct.get("SuccessMessage"));
        }
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void createAccErrMsg(@Optional("US") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that guest user in " + store + " store," + " is able to view the appropriate error message in create an account drawer when invalid values are entered across the create account fields");

        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        List<String> splChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "specialChar", "Value"));
        List<String> expErrorMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "newErrMsgContent"));
        List<String> expErrorMsgSp = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "newExpectedContentSp"));


        AssertFailAndContinue(headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions), "Clicked on the Create Account link on the Header.");
        AssertFailAndContinue(createAccountActions.createAccountExistingEmail(emailAddressReg, acct.get("FirstName"), acct.get("LastName"), password, acct.get("ZipCode"), acct.get("PhoneNumber"), expErrorMsg.get(9)), "Validated the error message displayed when an existing email is used to create a new account");
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
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
//failing because: For all password error messages showing : Please enter a valid password
    public void createAccPwdErrMsg(@Optional("US") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that guest user in " + store + " store," + " is able to view the appropriate error message in create an account drawer when invalid values are entered in the Password and Confirm Password fields");

        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        List<String> splChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "specialChar", "Value"));
        List<String> expPwdError = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "expectedPwdErrNew"));

        AssertFailAndContinue(headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions), "Clicked on the Create Account link on the Header.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(5), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has less than 8 characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(6), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no special characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(7), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(8), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the has no uppercase. Error Msg: Password must include atleast one uppercase character(s).");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(6), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no special characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(7), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(8), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no upper case alpha characters.");

    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void createAccZipErrMsg(@Optional("US") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that guest user in " + store + " store," + " is able to view the appropriate error message in create an account drawer when invalid values are entered in the Zip/Postal Code field");

        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        List<String> splChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "specialChar", "Value"));
        List<String> expZipError = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "expectedZipErr"));

        AssertFailAndContinue(headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions), "Click on create account link from the header menu and check user displayed with create account drawer");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(0), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered Zip value has 6 numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(9), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered Zip value has less than 5 numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(10), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered value is an invalid Postal Code having 7 characters.");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(11), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered Postal Code value has more than 7 alpha characters.");
    }

    @Test(enabled = false)//Create account from footer navigating to create account drawer page.
    public void createAccFromFooter(@Optional("US") String store, @Optional("guest") String user) {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that " + user + " user in " + store + " store," + ", when the user clicks on 'Create account' link in the footer, verify that the 'Create Account' drawer is displayed to the user.");
        createAccountFromFooter(rowInExcel);
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void createAccFromForgotPwd(@Optional("US") String store) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that guest user in " + store + " store," + ",when the user clicks on 'create account' link from the link from Forgot password drawer,verify that 'Create account' drawer is displayed to the user.");
        createAccAccessFromForgotPwd();
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void createAccFromlogin(@Optional("US") String store) throws Exception {
        setAuthorInfo("Venkat");

        setRequirementCoverage("Verify that guest user in " + store + " store," + ",when the user clicks on 'create account' link from the link from login drawer,verify that 'Create account' drawer is displayed to the user.");
        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Clicked on the Login link on the Header.");
        AssertFailAndContinue(loginPageActions.clickCreateAccButtonFromDrawer(createAccountActions), "Clicked on the Create Account button and the Create Account Drawer is displayed.");

    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void createAccFromPromo(@Optional("US") String store) throws Exception {
        setAuthorInfo("Venkat");

        setRequirementCoverage("Verify that guest user in " + store + " store," + ",when the user clicks on 'create account' link from the promotional content in the inline shopping bag,verify that 'Create account' drawer is displayed to the user.");
        AssertFailAndContinue(headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions), "Clicked on the Shopping Bag icon on the Header.");
        createAccAccessFromPromo();
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void tAndCRedirectionValidation(@Optional("US") String store) throws Exception {
        setAuthorInfo("srijith");
        setRequirementCoverage("Verify that user in " + store + " store," + ", clicks on terms and condition link from create account drawer, page redirection validation");
        List<String> urlValue = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "termsAndConditionURL", "URLValue"));

        String currentWindow = driver.getWindowHandle();
        createAccountActions.termsAndCondtionValidate();
        headerMenuActions.click(headerMenuActions.closemodal);
        switchToWindow(currentWindow);
        AssertFailAndContinue(createAccountActions.termsAndCondtionValidateURL(urlValue.get(0)), "check the redirected URL value");
        closeWindow();
        switchBackToParentWindow(currentWindow);
        footerActions.clickOnLanguageButton();
        footerActions.click(footerActions.tAndClink);
        closeWindow();
        switchToWindow(currentWindow);
        AssertFailAndContinue(createAccountActions.termsAndCondtionValidateURL(urlValue.get(2)), "check the redirected URL value");
        if (store.equalsIgnoreCase("US")) {
            if (!env.equalsIgnoreCase("prod") && !env.equalsIgnoreCase("prodstaging")) {
                createAccountActions.rewardsEspotRedirection(headerMenuActions);
                // closeWindow();
                //switchToWindow(currentWindow);
                AssertFailAndContinue(createAccountActions.termsAndCondtionValidateURL(urlValue.get(1)), "check the redirected URL value");
                createAccountActions.rewardsRedirection();
                AssertFailAndContinue(createAccountActions.termsAndCondtionValidateURL(urlValue.get(1)), "check the redirected URL value");
                createAccountActions.creditCardRedirection();
                switchToWindow(currentWindow);
                AssertFailAndContinue(createAccountActions.termsAndCondtionValidateURL(urlValue.get(1)), "check the redirected URL value");

            }
        }
    }

    @Parameters(usersXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void validateContactUsLink(@Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify that " + user + " user in store," + ", clicks on Contact us link from create account drawer, page redirection validation");
        List<String> enterDetails = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "contactUsDetails", "Details"));
        String currentWindow = driver.getWindowHandle();
        if (user.equalsIgnoreCase("guest")) {
            createAccountActions.clickContactUs();
            switchToWindow(currentWindow);
            AssertFailAndContinue(driver.getCurrentUrl().contains("/help-center/contact-us"), "check the redirected URL value for Contact Us");
            AssertFailAndContinue(createAccountActions.validateErrMessageContactUs(enterDetails.get(0), enterDetails.get(1), emailAddressReg, enterDetails.get(2), enterDetails.get(3)), "Verify the error message displayed in Contact us landing page");
        }
    }

    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION}, enabled = false)
    public void validateResCreatAcc(@Optional("guest") String user) throws Exception {
        setAuthorInfo("srijith");
        setRequirementCoverage("Verify the Response displayed while create account from various places");
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);

        headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions);
        createAccountActions.enterAccountDetailsWithoutSubmit(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"));
        Map<String, String> errCodeAndErrMsg = createAccountActions.getResponseOfEmail();
        AssertFailAndContinue(createAccountActions.isProperResponseCodeAndMessage(errCodeAndErrMsg), "getting proper RTPS Response with error code and error messages");

    }
}
