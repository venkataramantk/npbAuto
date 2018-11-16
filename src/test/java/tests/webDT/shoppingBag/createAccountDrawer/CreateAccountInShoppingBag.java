package tests.webDT.shoppingBag.createAccountDrawer;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import ui.utils.MethodListener;

import java.util.List;
import java.util.Map;

/**
 * Created by Venkat on 3/17/2017.
 */
//@Listeners(MethodListener.class)
public class CreateAccountInShoppingBag extends BaseTest{

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
    private String password;

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
        if(store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
        }
        else if(store.equalsIgnoreCase("CA")){
            footerActions.changeCountryAndLanguage("CA","English");
        }
     }

    @AfterMethod(alwaysRun = true)
    public void clearCookies(){
        headerMenuActions.deleteAllCookies();
    }


    @Test(enabled = false)//disabling since create account occuring for every class
    public void createNewAccount() throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify if a guest user in US Store is able to create an account by clicking on 'Create Account' link from header in the Shopping Bag Page.");
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions );
        createNewAccountFromHeader(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"),acct.get("SuccessMessage"));
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG,GUESTONLY,PROD_REGRESSION})
    public void createAccFieldsValidate(@Optional("US") String store) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage(store+ " store - Verify if the Guest user in the US Store is able to validate the contents of the Create Account drawer from the Shopping Bag Page.");
        List<String> txtContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "messageOnForm", "Content"));
        Map<String, String> termsTxt = excelReaderDT2.getExcelData("CreateAccount", "messageOnForm");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions );
        AssertFailAndContinue(headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions), "Clicked on the Create Account link on the Header.");
        AssertFailAndContinue(createAccountActions.validateOverlayContent(txtContent.get(0),txtContent.get(1),termsTxt.get("TermsTextUS"),txtContent.get(2),txtContent.get(3)), "Validated the fields in the Create Account drawer.");
        AssertFailAndContinue(overlayHeaderActions.clickLoginOnOverlayHeader(), "Clicked on the Login link on the Header.");
        AssertFailAndContinue(headerMenuActions.clickCreateAccountOverlay(overlayHeaderActions,createAccountActions), "Clicked on the Create Account link on the Header.");
        AssertFailAndContinue(headerMenuActions.clickWishListOverlayAsGuest(wishListDrawerActions), "Clicked on the WishList Icon on the Header.");

    }



    @Test(groups = {SHOPPINGBAG,USONLY,GUESTONLY,PROD_REGRESSION})
    public void createNewAccountCAZip(){

        setAuthorInfo("Venkat");
        setRequirementCoverage( " Verify if a guest user in US Store is able to create an account using a Canadian Address when Create Account Drawer is accessed from the Shopping Bag page.");
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", "CreateAccountCA");

        createNewAccountFromHeader(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"),acct.get("SuccessMessage"));
    }

    @Parameters("store")
    @Test(groups = {SHOPPINGBAG,GUESTONLY,PROD_REGRESSION})
    public void createAccErrMsg(@Optional("US") String store)throws Exception{

        setAuthorInfo("Venkat");
        setRequirementCoverage(store+ " store - Verify if a guest user in US Store is able to view the appropriate error message in create an account drawer when invalid values are entered across the create account fields, when Create Account is initiated from the Shopping Bag page.");

        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        List<String> splChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "specialChar", "Value"));
        List<String> expErrorMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "newErrMsgContent"));
        List<String> expErrorMsgSp = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "newExpectedContentSp"));
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions );
        AssertFailAndContinue(headerMenuActions.clickCreateAccount(myAccountPageDrawerActions,createAccountActions),"Clicked on the Create Account link on the Header.");
        AssertFailAndContinue(createAccountActions.validateEmptyFieldErrMessages(expErrorMsg.get(0), expErrorMsg.get(1), expErrorMsg.get(2), expErrorMsg.get(3), expErrorMsg.get(4), expErrorMsg.get(5), expErrorMsg.get(6), expErrorMsg.get(7), expErrorMsg.get(8)), "Validated the inline Error messages for all the mandatory fields.");
        AssertFailAndContinue(createAccountActions.validateSingleFldEmptyErrMsgs(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), expErrorMsg.get(0), expErrorMsg.get(1), expErrorMsg.get(2), expErrorMsg.get(3), expErrorMsg.get(4), expErrorMsg.get(5), expErrorMsg.get(6), expErrorMsg.get(7), expErrorMsg.get(8), ""), "Validated the presence of the error messages when any one of the fields are left empty and the Create Account button is clicked.");
        AssertFailAndContinue(createAccountActions.validateErrOnFNameFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(0), expErrorMsgSp.get(0)), "Validated the error message for the First Name field");
        AssertFailAndContinue(createAccountActions.validateErrOnLNameFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(0), expErrorMsgSp.get(1)), "Validated the error message for the Last Name field");
        AssertFailAndContinue(createAccountActions.validateErrOnEmailFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(1), expErrorMsgSp.get(2)), "Validated the Email field error message.");
        AssertFailAndContinue(createAccountActions.validateShowHideLnk("P@ssw0rd"), "Validated the Show/Hide feature of the Show/Hide Password and Show/Hide Confirm Password links");
        AssertFailAndContinue(createAccountActions.validateErrOnPhoneFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(2), expErrorMsgSp.get(3)), "Validated the error message for the Phone field when less than 10 digits are entered.");
        AssertFailAndContinue(createAccountActions.validateErrOnPhoneFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(3), expErrorMsgSp.get(3)), "Validated the error message for the Phone field when alpha characters are entered.");
//        AssertFailAndContinue(createAccountActions.createAccountExistingEmail(emailAddressReg, acct.get("FirstName"), acct.get("LastName"), password, acct.get("ZipCode"), acct.get("PhoneNumber"), expErrorMsg.get(9)), "Validated the error message displayed when an existing email is used to create a new account");

    }
    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG,GUESTONLY,PROD_REGRESSION})
    public void createAccPwdErrMsg(@Optional("US") String store)throws Exception{

        setAuthorInfo("Venkat");
        setRequirementCoverage(store+ " store - Verify if a guest user in US Store is able to view the appropriate error message in create an account drawer when invalid values are entered in the Password and Confirm Password fields, from the Shopping Bag page/Script are getting failed due to the defect DT-14993");

        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        List<String> splChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "specialChar", "Value"));
        List<String> expPwdError = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "expectedPwdErrNew"));
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "AltValue");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions );
        AssertFailAndContinue(headerMenuActions.clickCreateAccount(myAccountPageDrawerActions, createAccountActions),"Clicked on the Create Account link on the Header.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(5), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has less than 8 characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(6), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no special characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(7), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(8), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the has no uppercase. Error Msg: Password must include atleast one uppercase character(s).");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(6), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no special characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(7), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrPassword(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(8), expPwdError.get(1)), "Validated the error message for the Password and Confirm Password fields when the password has no upper case alpha characters.");

    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG,GUESTONLY,PROD_REGRESSION})
    public void createAccZipErrMsg(@Optional("US") String store)throws Exception{

        setAuthorInfo("Venkat");
        setRequirementCoverage(store+ " store - Verify if a guest user in US Store is able to view the appropriate error message in create an account drawer when invalid values are entered in the Zip/Postal Code field when the Create Account Drawer is accessed from the Shopping Bag page");

        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        List<String> splChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "specialChar", "Value"));
        List<String> expZipError = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "fieldErrMessages", "expectedZipErr"));
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "AltValue");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions );
        AssertFailAndContinue(shoppingBagPageActions.clickLoginFromEspotLnk(loginPageActions),"Click on login link and check user displayed with login overlay");
        AssertFailAndContinue(loginPageActions.clickCreateAccountLink(createAccountActions),"Click on create account link from the login overlay");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(0), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered Zip value has 6 numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(9), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered Zip value has less than 5 numeric characters.");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(10), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered value is an invalid Postal Code having 7 characters.");
        AssertFailAndContinue(createAccountActions.validateErrOnZipFld_SplChar(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), splChar.get(11), expZipError.get(2)), "Validated the error message for the Zip/Postal code field when the entered Postal Code value has more than 7 alpha characters.");
    }
    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG,GUESTONLY,PROD_REGRESSION})
    public void createAccFromFooter(@Optional("US") String store) throws Exception {

        setAuthorInfo("Venkat");
        setRequirementCoverage("As a guest user on US Store, when the user clicks on 'Create account' link in the footer, verify that the 'Create Account' drawer is displayed to the user.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions );

        createAccountFromFooter(rowInExcel);
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG,GUESTONLY,PROD_REGRESSION})
    public void createAccFromForgotPwd(@Optional("US") String store) throws Exception {
        setAuthorInfo("Venkat");

        setRequirementCoverage(store+ " store - As a guest user on US Store,when the user clicks on 'create account' link from the link from Forgot password drawer,verify that 'Create account' drawer is displayed to the user.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearchingFromProductCard(searchKeywordAndQty);
//        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions );

        createAccAccessFromForgotPwd();
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG,GUESTONLY,PROD_REGRESSION})
    public void createAccFromPromo(@Optional("US") String store) throws Exception {
        setAuthorInfo("Venkat");

        setRequirementCoverage(store+ " store - As a guest user on US Store,when the user clicks on 'create account' link from the promotional content in the inline shopping bag,verify that 'Create account' drawer is displayed to the user.");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
        List<String> validText = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "MY PLACE REWARDS", "MPR"));

        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
        AssertFailAndContinue(shoppingBagDrawerActions.clickCreateAcctLinkFromEspot(createAccountActions),"Clicked on create account from promo banner");
        AssertFailAndContinue(createAccountActions.validateFieldsInPage(), "Validate the fields present in the create account modal");
        AssertFailAndContinue(createAccountActions.createNewAccount(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), acct.get("SuccessMessage")),"Created New Account "+createAccountActions.getEmailAddress());
        myAccountPageDrawerActions.closeOverlay(homePageActions);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickProfileInfoLink(), "Click on profile information link and check user navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.mprValidation(validText.get(0)),"Verify whether the MPR# is displayed");
    }
}
