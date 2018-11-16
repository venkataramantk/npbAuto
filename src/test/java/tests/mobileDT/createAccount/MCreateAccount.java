package tests.mobileDT.createAccount;

import java.util.Map;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

//@Test(singleThreaded = true)
public class MCreateAccount extends MobileBaseTest {

    WebDriver mobileDriver;
    String email;
    String uscaemail, password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        email = UiBaseMobile.randomEmail();
        if (store.equalsIgnoreCase("US")) {
            createAccount(rowInExcelUS, email);
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            createAccount(rowInExcelUS, email);
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        mheaderMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
    }


    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, CREATEACCOUNT, GUESTONLY, PROD_REGRESSION})
    public void validateCreateAccountPageContent(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a guest user in " + store + " verify create account page content from Create account page" +
                "DT-43790, DT-43791");

        if (store.equalsIgnoreCase("US")) {
            String searchKeyWord = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
            String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
            addToBagBySearching(getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyWord, qty));
            AssertFailAndContinue(mshoppingBagPageActions.validateCreateAccountLink(mcreateAccountActions), "Verify Create Account Page from Create Account link in MPR Content of PDP page");
            mloginPageActions.click(mloginPageActions.closeLoginModal);

            panCakePageActions.navigateToMenu("MPR");
            mMyPlaceRewardsActions.click(mMyPlaceRewardsActions.createAccountLink);
            AssertFailAndContinue(mcreateAccountActions.isDisplayed(mcreateAccountActions.createAccountButton), "Verify Create Account Page from My Place Rewards page");
            AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.termsAndConditions).equals(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "termsAndCondition", "Value")), "Verify Terms and Condition text in Create Account Page from MPR create account");
            mloginPageActions.click(mloginPageActions.closeLoginModal);
        }
        panCakePageActions.navigateToMenu("CREATEACCOUNT");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mcreateAccountActions.isDisplayed(mcreateAccountActions.eSpotLogo), "Verify eSpot logo in Create Account Page");
            AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.subHeading).equals(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "eSpotContent", "Value")), "Verify eSPot data in Create Account Page");
        }
        AssertFailAndContinue(mcreateAccountActions.validateFieldsInPage(), "Verify All fields in Create Account Page");

        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.termsAndConditions).equals(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "termsAndCondition", "Value")), "Verify Terms and Condition text in Create Account Page");
        AssertFailAndContinue(mcreateAccountActions.verifyHelpCenterPage(), "Verify Help Center page is display in new tab upon clicking on Terms and Condition link in create account");
        mobileDriver.close();
        mcreateAccountActions.switchToParent();

        AssertFailAndContinue(mcreateAccountActions.verifyEmailUspage(), "Verify Email Us page is display in new tab upon clicking on Contact Us link in create account");
        mobileDriver.close();
        mcreateAccountActions.switchToParent();

        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.rememberMe).equals(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "rememberMe", "Value")), "Verify Remember Me text in Create Account Page");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, CREATEACCOUNT, GUESTONLY})
    public void inlineErrorValidation(String store, String user) throws Exception {
        String capEmail = mcreateAccountActions.randomEmail().toUpperCase();
        setAuthorInfo("Jagadeesh");
        panCakePageActions.navigateToMenu("CREATEACCOUNT");
        if (store.equalsIgnoreCase("US")) {
            email = mcreateAccountActions.randomEmail();
            uscaemail = email;
            AssertFailAndContinue(!mcreateAccountActions.isEnabled(mcreateAccountActions.createAccountButton), "Verify Create Account button is in disable state without entering any data");
            mcreateAccountActions.enterAccountDetails(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "FirstName"),
                    getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "LastName"),
                    getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "EmailId"),
                    getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password"),
                    getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "ZipCode"),
                    getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "PhoneNumber"));
        }
        if (store.equalsIgnoreCase("CA")) {
            email = mcreateAccountActions.randomEmail();
//            mcreateAccountActions.navigateTo(EnvironmentConfig.getCA_ApplicationUrl());
            mcreateAccountActions.enterAccountDetails(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountCA", "FirstName"),
                    getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountCA", "LastName"),
                    getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountCA", "EmailId"),
                    getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountCA", "Password"),
                    getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountCA", "ZipCode"),
                    getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountCA", "PhoneNumber"));
        }

        //DT-16351
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.firstNameField, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "fNameWithSplChar", "SpecialChar"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("fn")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "fNameWithSplChar", "ErrorMessages")), "Verify In-line error message for first name field for Numbers");

        //DT-16352
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.firstNameField, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "FirstName"));
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.lastNameField, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "lNameWithSplChar", "SpecialChar"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("ln")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "lNameWithSplChar", "ErrorMessages")), "Verify In-line error message for Last name field for Numbers");

        //DT-16353
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.lastNameField, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "LastName"));
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.emailAddrField, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emailWithSplChar", "SpecialChar"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("email")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emailWithSplChar", "ErrorMessages")), "Verify In-line error message for email field with invalid address");

        //DT-16354
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.emailAddrField, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "EmailId"));
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.confEmailAddrFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "EmailId") + "p");
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("confemail")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emailMismatch", "ErrorMessages")), "Verify In-line error message for mis-match email and confirm email");

        //DT-16357
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.emailAddrField, capEmail);
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.confEmailAddrFld, capEmail);
        AssertFailAndContinue(mcreateAccountActions.getAttributeValue(mcreateAccountActions.emailAddrField, "value").equals(capEmail), "Verify that email address entered in Email Address filed is NOT always displayed in lowercase ");
        AssertFailAndContinue(mcreateAccountActions.getAttributeValue(mcreateAccountActions.confEmailAddrFld, "value").equals(capEmail), "Verify that email address entered in Confirm Email Address filed is NOT always displayed in lowercase ");

        //DT-16379
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.emailAddrField, "jk@yopmail.com");
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.confEmailAddrFld, "jk@yopmail.com");
        mcreateAccountActions.clickCreateAccountBtn();
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.err_CreateAccount).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "createAcctWithExitingEmail", "ErrorMessages")), "Verify Error Message for existing email address");

        //DT-16355
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.confEmailAddrFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "EmailId"));
        mcreateAccountActions.click(mcreateAccountActions.showLink("Password"));
        mcreateAccountActions.click(mcreateAccountActions.showLink("Confirm Password"));

        AssertFailAndContinue(mcreateAccountActions.isDisplayed(mcreateAccountActions.hideLink("Password")), "Verify Hide Link is displayed for Password Filed");
        AssertFailAndContinue(mcreateAccountActions.isDisplayed(mcreateAccountActions.hideLink("Confirm Password")), "Verify Hide Link is displayed for Confirm Password Filed");

        AssertFailAndContinue(mcreateAccountActions.getAttributeValue(mcreateAccountActions.passwordFld, "value").equals(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password")), "Verify Password is Unmasked by clicking upon \"show\" link in Password field");
        AssertFailAndContinue(mcreateAccountActions.getAttributeValue(mcreateAccountActions.passwordFld, "value").equals(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password")), "Verify Confirm Password is Unmasked by clicking upon \"show\" link in Confirm Password field");

        mcreateAccountActions.click(mcreateAccountActions.hideLink("Password"));
        mcreateAccountActions.click(mcreateAccountActions.hideLink("Confirm Password"));

        AssertFailAndContinue(mcreateAccountActions.isDisplayed(mcreateAccountActions.showLink("Password")), "Verify Show Link is displayed for Password Filed");
        AssertFailAndContinue(mcreateAccountActions.isDisplayed(mcreateAccountActions.showLink("Confirm Password")), "Verify Show Link is displayed for Confirm Password Filed");

        //DT-16364
        mcreateAccountActions.clear(mcreateAccountActions.passwordFld);
        mcreateAccountActions.click(mcreateAccountActions.confPasswordFld);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("pwd")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySinglePwdFld", "ErrorMessages")), "Verify inline error for empty password");

        mcreateAccountActions.clear(mcreateAccountActions.confPasswordFld);
        mcreateAccountActions.click(mcreateAccountActions.passwordFld);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("confirmPwd")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleConfPwdFld", "ErrorMessages")), "Verify inline error for empty Confirm password");

        //DT-16365
        mcreateAccountActions.click(mcreateAccountActions.toolTip);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.toolTipContent).equals(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "ToolTip", "Value")), "Verify Tool tip content for Password field in Create account page");

        //DT-16366
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.passwordFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password"));
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.confPasswordFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password"));
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.zipPostalCodeFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "zipWith6Char", "SpecialChar"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("zip")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "zipWith6Char", "ErrorMessages")), "Verify inline error for zip code with more number");

        //DT-16367
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.zipPostalCodeFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "zipWith3Char", "SpecialChar"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("zip")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "zipWith3Char", "ErrorMessages")), "Verify inline error for zip code with less number");

        //DT-16368
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.zipPostalCodeFld, "");
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("zip")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "zipWith3Char", "ErrorMessages")), "Verify inline error for empty zip code");

        //DT-16370, DT-16371
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.zipPostalCodeFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "zipWithSplChar", "SpecialChar"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("zip")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "zipWithSplChar", "ErrorMessages")), "Verify inline error for zip code with special character");

        //DT-16373
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.zipPostalCodeFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "ZipCode"));
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.phNumberFld, "");
        mcreateAccountActions.click(mcreateAccountActions.zipPostalCodeFld);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("phone")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySinglePhNumFld", "ErrorMessages")), "Verify inline error for empty phone");

        //DT-16374
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.phNumberFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "phNumWithLessChar", "SpecialChar"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("phone")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "wrongPhoneNumFld", "ErrorMessages")), "Verify inline error for Less phone number");

        //DT-16375
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.phNumberFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "phNumWithSplChar", "SpecialChar"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("phone")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "wrongPhoneNumFld", "ErrorMessages")), "Verify inline error for phone number with special characters");

        //DT-16376
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.phNumberFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "PhoneNumber"));
        mcreateAccountActions.select(mcreateAccountActions.termsCheckBox);
        mcreateAccountActions.deselect(mcreateAccountActions.termsCheckBox);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("terms")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "unCheckSingleTermsCond", "ErrorMessages")), "Verify inline error for phone number with special characters");

        //DT-16380
        mcreateAccountActions.select(mcreateAccountActions.termsCheckBox);
        AssertFailAndContinue(mcreateAccountActions.verifyElementNotDisplayed(mcreateAccountActions.inlineErrorMessage("terms")), "Select terms and agreement checkbox and verify inline error message is disappear");

        //DT-16358
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.emailAddrField, email);
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.confEmailAddrFld, email);
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.confPasswordFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdCharAppearsMoreThan3Times", "PwdReq"));
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.passwordFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdCharAppearsMoreThan3Times", "PwdReq"));
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.passwordFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdCharAppearsMoreThan3Times", "PwdReq"));

        mcreateAccountActions.click(mcreateAccountActions.createAccountButton);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.err_CreateAccount).equals(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdCharAppearsMoreThan3Times", "ErrorMessages")), "Verify error message for password which has the same character appearing consecutively more than 3 times");

        //DT-16359
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.passwordFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdWithLessThan8Chars", "PwdReq"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("pwd")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdWithLessThan8Chars", "ErrorMessages")), "Verify error message for password with less then 8 characters");

        //DT-16360
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.passwordFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdWithoutSplChar", "PwdReq"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("pwd")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdWithLessThan8Chars", "ErrorMessages")), "Verify error message for password with no Special characters");

        //DT-16361
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.passwordFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdWithoutNum", "PwdReq"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("pwd")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdWithLessThan8Chars", "ErrorMessages")), "Verify error message for password without numbers");

        //DT-16362
        mcreateAccountActions.clearAndFillText(mcreateAccountActions.passwordFld, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdWithoutUpperCase", "PwdReq"));
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("pwd")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdWithLessThan8Chars", "ErrorMessages")), "Verify error message for password without uppercase");

        mhomePageActions.refreshPage();
        panCakePageActions.navigateToMenu("CREATEACCOUNT");

        mcreateAccountActions.firstNameField.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("fn")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleFName", "ErrorMessages")), "Verify In-line error message for first name field");

        mcreateAccountActions.lastNameField.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("ln")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleLName", "ErrorMessages")), "Verify In-line error message for Last name field");

        mcreateAccountActions.emailAddrField.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("email")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleEmail", "ErrorMessages")), "Verify In-line error message for email field");

        mcreateAccountActions.confEmailAddrFld.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("confemail")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleConfEmail", "ErrorMessages")), "Verify In-line error message for confirm email field");

        mcreateAccountActions.passwordFld.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("pwd")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySinglePwdFld", "ErrorMessages")), "Verify In-line error message for password field");

        mcreateAccountActions.confPasswordFld.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("confirmPwd")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleConfPwdFld", "ErrorMessages")), "Verify In-line error message for Confirm Password field");

        mcreateAccountActions.zipPostalCodeFld.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("zip")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleZipFld", "ErrorMessages")), "Verify In-line error message for Zip field");

        mcreateAccountActions.zipPostalCodeFld.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("phone")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySinglePhNumFld", "ErrorMessages")), "Verify In-line error message for Phone field");
    }

    @Test(priority = 2, dataProvider = dataProviderName, enabled = false)
    public void createAccount(String store) throws Exception {

        setAuthorInfo("Jagadeesh");
        mcreateAccountActions.navigateTo(EnvironmentConfig.getApplicationUrl());

        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryByCountry("CANADA");
        }

        panCakePageActions.navigateToMenu("CREATEACCOUNT");
        //DT-16382
        mcreateAccountActions.enterAccountDetails(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "FirstName"),
                getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "LastName"), "ca" + email,
                getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password"),
                getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountCA", "ZipCode"),
                getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "PhoneNumber"));
        mcreateAccountActions.click(mcreateAccountActions.createAccountButton);
        AssertFailAndContinue(mcreateAccountActions.isDisplayed(panCakePageActions.salutationLink), "Verify Welcome message");

        AssertFailAndContinue(panCakePageActions.getText(panCakePageActions.salutationLink).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "FirstName")), "Create An US account with CA postal code");

        //DT-16377
        mcreateAccountActions.click(panCakePageActions.salutationLink);
        mcreateAccountActions.staticWait(20000);
        mcreateAccountActions.click(mcreateAccountActions.logOutLnk);
        panCakePageActions.navigateToMenu("CREATEACCOUNT");
        mcreateAccountActions.enterAccountDetails(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "FirstName"),
                getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "LastName"), email,
                getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password"),
                getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "ZipCode"),
                getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "PhoneNumber"));
        mcreateAccountActions.click(mcreateAccountActions.createAccountButton);
        AssertFailAndContinue(panCakePageActions.getText(panCakePageActions.salutationLink).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "FirstName")), "Create An account and verify account created successfully");
        mcreateAccountActions.click(panCakePageActions.salutationLink);
        mcreateAccountActions.staticWait(20000);
        mcreateAccountActions.click(mcreateAccountActions.logOutLnk);
    }

    //To DO: This is already covered in CreateAccountFromOrderConfirmPage. Hence disabled
    @Test(priority = 3, enabled = false)
    public void createAccountCheckout() throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify US account created with CA zip code is able to checkout in US shopping bag");
        mcreateAccountActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mfooterActions.changeCountryAndLanguage("CANADA", "English");
        panCakePageActions.navigateToMenu("CREATEACCOUNT");
        AssertFailAndContinue(mcreateAccountActions.isDisplayed(mcreateAccountActions.subHeading), "Verify Create Account Page for international store");

        mcreateAccountActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mfooterActions.changeCountryAndLanguage("India", "English");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        mcreateAccountActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm("ca" + uscaemail, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password"));
        addToBagBySearching(getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty));
        mheaderMenuActions.click(mheaderMenuActions.bagLink);
        mshoppingBagPageActions.click(mshoppingBagPageActions.checkoutBtn);
        AssertFailAndContinue(mobileDriver.getCurrentUrl().contains(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "CanonicalForm", "Checkout")), "Verify Checkout URL");

        mcreateAccountActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.click(mheaderMenuActions.bagLink);
        mshoppingBagPageActions.click(mshoppingBagPageActions.paypalBtn);
        mpayPalPageActions.click(mpayPalPageActions.proceedWithPaPalButton);

        mpayPalPageActions.paypalLogin(getDT2TestingCellValueBySheetRowAndColumn("Paypal", "CanadaPaypal", "UserName"), getDT2TestingCellValueBySheetRowAndColumn("Paypal", "CanadaPaypal", "Password"), mreviewPageActions);
        mreviewPageActions.click(mreviewPageActions.submitOrderButton);
        AssertFailAndContinue(mreviewPageActions.waitUntilElementDisplayed(mreceiptThankYouPageActions.confirmationTitle), "Order with Paypal");
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {CREATEACCOUNT, GUESTONLY, PROD_REGRESSION})
    public void createAccountInlineErrFrmGuestCheckout(String store, String user) throws Exception {
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store,  in the shopping bag page and clicks on checkout and on create account in the guest login page,"
        +" Verify user displayed with following inline field errors when user just tab out from each of fields  ");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtn(mloginPageActions, mshoppingBagPageActions),"Click on checkout button");
        //DT-37974
        AssertFailAndContinue(mloginPageActions.isEmailAddressDisplayed(), "verify email address field is displayed");
        AssertFailAndContinue(mloginPageActions.isDisplayed(mloginPageActions.passwordField),"verify password field is displayed");
        AssertFailAndContinue(mloginPageActions.isDisplayed(mloginPageActions.rememberMeCheckBox),"verify remember me checkbox is displayed");
        AssertFailAndContinue(mloginPageActions.isDisplayed(mloginPageActions.forgotPasswordLink),"verify forgot password link is displayed");
        AssertFailAndContinue(mloginPageActions.isDisplayed(mloginPageActions.loginButton),"verify login button is displayed");
        AssertFailAndContinue(mloginPageActions.isDisplayed(mloginPageActions.createAccountBtnFromCheckout),"verify Create account link is displayed");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.continueAsGuest), "verify continue as guest button is displayed");
        //DT-37994

        AssertFailAndContinue(mloginPageActions.clickCreateAcctFromCheckoutSB(mcreateAccountActions), "click on Create account link from Guest login page");

        mcreateAccountActions.firstNameField.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("fn")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleFName", "ErrorMessages")), "Verify In-line error message for first name field");

        mcreateAccountActions.lastNameField.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("ln")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleLName", "ErrorMessages")), "Verify In-line error message for Last name field");

        mcreateAccountActions.emailAddrField.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("email")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleEmail", "ErrorMessages")), "Verify In-line error message for email field");

        mcreateAccountActions.confEmailAddrFld.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("confemail")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleConfEmail", "ErrorMessages")), "Verify In-line error message for confirm email field");

        mcreateAccountActions.passwordFld.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("pwd")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySinglePwdFld", "ErrorMessages")), "Verify In-line error message for password field");

        mcreateAccountActions.confPasswordFld.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("confirmPwd")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleConfPwdFld", "ErrorMessages")), "Verify In-line error message for Confirm Password field");

        mcreateAccountActions.zipPostalCodeFld.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("zip")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySingleZipFld", "ErrorMessages")), "Verify In-line error message for Zip field");

        mcreateAccountActions.zipPostalCodeFld.sendKeys(Keys.TAB);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.inlineErrorMessage("phone")).contains(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptySinglePhNumFld", "ErrorMessages")), "Verify In-line error message for Phone field");
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitBrowser() {
        mobileDriver.quit();
    }
}
