package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import ui.utils.MethodListener;
import uiMobile.UiBaseMobile;

import java.util.Map;

/**
 * Created by JKotha on 24/11/2017.
 */
//@Listeners(MethodListener.class)
//@Test(singleThreaded = true)
public class ForgotPasswordTests extends MobileBaseTest {
    WebDriver mobileDriver;
    String email = "test@yopmail.com";
    String password;

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
            mfooterActions.changeCountryByCountry("CANADA");
            createAccount(rowInExcelCA, email);
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, GUESTONLY})
    public void forgotPwdUiValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify Forgot Password UI ");
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.clickForgotPassword();
        AssertFailAndContinue(mforgotYourPasswordPageActions.isDisplayed(mforgotYourPasswordPageActions.resetPwdButton), "Verify Forgot Password Page");

        AssertFailAndContinue(mforgotYourPasswordPageActions.validateAllFields(), "Validate ll fields in forgot password page Instructions text");
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateSectionHeadingText(), "verify that the \"section heading\" as \"Forgot your Password?\" text is displayed to the user");
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateSectionSubHeading(), "verify that \"section sub heading\" as\" Enter your email address below and we 'll send you instruction to reset your password\" text is displayed to the user");

        AssertFailAndContinue(mforgotYourPasswordPageActions.validateCreateAccountTxt(), "Verify that the section subheading as \"\"Don't have an account? Create one now to start earning points!\" \" is displayed above the 'Create An Account'");
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateErrormEssageForEmptyEmail(), "verify Error Message for empty email");

        mforgotYourPasswordPageActions.enterEmailAddrAndSubmit(getmobileDT2CellValueBySheetRowAndColumn("Login", "loginSplCharErr", "LoginDrawerContent"));
        AssertFailAndContinue(mforgotYourPasswordPageActions.waitUntilElementDisplayed(mforgotYourPasswordPageActions.inlineerrorMsg), "verify Error Message for special character email");
        mforgotYourPasswordPageActions.enterEmailAddrAndSubmit("unknow@yopmail.com");
        AssertFailAndContinue(mforgotYourPasswordPageActions.waitUntilElementDisplayed(mforgotYourPasswordPageActions.pageLevelError), "verify Error Message for email not associated with TCP");
        mforgotYourPasswordPageActions.enterEmailAddrAndSubmit(email);
        AssertFailAndContinue(mforgotYourPasswordPageActions.waitUntilElementDisplayed(mforgotYourPasswordPageActions.successMsg), "verify Success message");
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateCustmorCareLink(), "Click on click here link in success message and verify custmore care page is displayed");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.click(mloginPageActions.forgotPasswordLink);
        mforgotYourPasswordPageActions.enterEmailAddrAndSubmit(email);
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateBackToLogin(), "verify login page after clicking on back");
        mloginPageActions.clickForgotPassword();
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateCreateAccontBtn(), "verify Create account button on forgot password page");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, GUESTONLY})
    public void forgotPwdFromFav(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify Forgot Password UI from Fav store");
        panCakePageActions.navigateToMenu("FAVORITES");
        mloginPageActions.click(mloginPageActions.forgotPasswordLink);
        AssertFailAndContinue(mforgotYourPasswordPageActions.isDisplayed(mforgotYourPasswordPageActions.resetPwdButton), "Verify Forgot Password Page from favorites page");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, GUESTONLY})
    public void checkout_forgotPwd(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify Forgot Password UI from checkout page");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        addToBagBySearching(searchKeywordAndQty);
        mshoppingBagPageActions.click(mshoppingBagPageActions.checkoutBtn);
        mloginPageActions.click(mloginPageActions.forgotPasswordLink);
        mforgotYourPasswordPageActions.enterEmailAddrAndSubmit(email);
        AssertFailAndContinue(mforgotYourPasswordPageActions.waitUntilElementDisplayed(mforgotYourPasswordPageActions.successMsg), "verify Success message");
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateBackToLogin(), "verify checkout login page after clicking on back");
    }
    
    @Test(priority = 3, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, GUESTONLY,PROD_REGRESSION})
    public void verifyNavToLPFromResetPswdDrawer(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK/Raman Jha");
        setRequirementCoverage("Verify is the user is able to navigate to login page when he clicks on login link in reset password drawer");
        
        //DT-40674
        panCakePageActions.navigateToMenu("LOGIN");
        AddInfoStep("Enter correct email address");
        mloginPageActions.clearAndFillText(mloginPageActions.emailAddrField, email);
        AddInfoStep("Click on Forgot Password link");
        AssertFailAndContinue(mloginPageActions.clickForgotPassword(), "Click forgot password link from login page and verify forgot password page");
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateBackToLogin(), "Verify user navigates to login page when clicked on login link in forgot password drawer.");;
        mloginPageActions.clickForgotPassword();
        
        //DT-40670
        String invalidEmailFormat = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "InvalidEmailFormat", "Value");
        String expectedErrMsg = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emailWithSplChar", "ErrorMessages");
        AddInfoStep("Add invalid email format and check for error");
        mforgotYourPasswordPageActions.enterEmailAddrAndSubmit(invalidEmailFormat);
        AssertFailAndContinue(mforgotYourPasswordPageActions.getText(mforgotYourPasswordPageActions.inlineErrorMessage("email")).contains(expectedErrMsg), "Verify error message is shown when user enters invalid email in the forgot password page");
        
        //DT-40679
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateCreateAccontBtn(), "Verify user navigates to Create account  page when clicked on Create account link in reset password drawer.");
        
        AddInfoStep("Click on Reset Password link");
        mcreateAccountActions.clickResetPasswordLnk(mforgotYourPasswordPageActions);
        //DT-40678
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateBackToLogin(), "Verify user navigates to login page when clicked on login link in reset password drawer.");
        mloginPageActions.clickCloseLoginModal();
        panCakePageActions.clickCloseIcon();
        
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.clickResetPassword();
        AddInfoStep("Enter valid email address");
        mforgotYourPasswordPageActions.enterEmailAddrAndSubmit(email);
        //DT-40673
        AssertFailAndContinue(mforgotYourPasswordPageActions.waitUntilElementDisplayed(mforgotYourPasswordPageActions.successMsg), "verify Success message");
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateCustmorCareLink(), "Click on click here link in success message and verify custmore care page is displayed");
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
