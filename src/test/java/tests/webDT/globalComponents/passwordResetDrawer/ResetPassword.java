package tests.webDT.globalComponents.passwordResetDrawer;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.EnvironmentConfig;

import java.util.List;

/**
 * Created by Venkat on 10/05/2016.
 */
//User Story: DT-854
//@Listeners(MethodListener.class)
public class ResetPassword extends BaseTest {

    WebDriver driver;
    String emailAddressReg = null;
    private String password = null;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
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
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, SMOKE, GUESTONLY, PROD_REGRESSION})
    public void resetPasswordFromLogin(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if guest user is redirected to Forgot password page when he clicks on Forgot password? hyperlink from Login page");
        List<String> forgotPwdContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("ForgotPasswordContent", "forgotPasswordContent", "expectedContent"));
        List<String> forgotPwdContent1 = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("ForgotPasswordContent", "forgotPasswordContentCA", "expectedContent"));

        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Clicked on the Login link on the Header.");
        AssertFailAndContinue(loginDrawerActions.clickForgotPasswordLnk(forgotYourPasswordPageActions), "Clicked on the Forgot Password link and landed on the Forgot Password page.");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(forgotYourPasswordPageActions.validateOverlayContent(forgotPwdContent.get(0), forgotPwdContent.get(1), forgotPwdContent.get(3)), "Verify if the content present in the forget password drawer");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(forgotYourPasswordPageActions.validateOverlayContent(forgotPwdContent1.get(0), forgotPwdContent1.get(1), forgotPwdContent1.get(3)), "Verify if the content present in the forget password drawer");

        }
        AssertFailAndContinue(forgotYourPasswordPageActions.enterEmailAddrAndSubmit(emailAddressReg), "Enter the email ID and check the message");
        AssertFailAndContinue(forgotYourPasswordPageActions.returnToLoginLink(loginDrawerActions), "Going back to login page");

        loginDrawerActions.clickForgotPasswordLnk(forgotYourPasswordPageActions);
        forgotYourPasswordPageActions.enterEmailAddrAndSubmit(emailAddressReg);
        AssertFailAndContinue(forgotYourPasswordPageActions.validateContactUsLink(), "Verify contact us link in success message");
        headerMenuActions.clickLoginLnk(loginDrawerActions);
        loginDrawerActions.clickForgotPasswordLnk(forgotYourPasswordPageActions);
        forgotYourPasswordPageActions.enterEmailAddrAndSubmit(emailAddressReg);
        headerMenuActions.click(headerMenuActions.bagIconDrawer);
        AddInfoStep("Check whether the Shopping bag drawer is displayed");
    }

    @Parameters(storeXml)
    @Test(priority = 1, groups = {GLOBALCOMPONENT, GUESTONLY, PROD_REGRESSION})
    public void forgotPasswordErrMsg(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if guest user is redirected to Forgot password page when he clicks on Forgot password? hyperlink from Login page");
        List<String> errorMsgContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("ForgotPasswordContent", "errorMessageContent", "expectedErrorMessage"));
        List<String> successMsgContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("ForgotPasswordContent", "successMsgContent", "expectedSuccessMsg"));

        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Clicked on the Login link on the Header.");
        AssertFailAndContinue(loginDrawerActions.clickForgotPasswordLnk(forgotYourPasswordPageActions), "Clicked on the Forgot Password link and landed on the Forgot Password page.");
        AssertFailAndContinue(forgotYourPasswordPageActions.validateErrorMessages(errorMsgContent.get(0)), "Validate the error message when leaving the email field empty");
        AssertFailAndContinue(forgotYourPasswordPageActions.validateErrMsgWithSpecialChar(errorMsgContent.get(1)), "Validate the error message when entering special char in email field");
        AssertFailAndContinue(forgotYourPasswordPageActions.errorMsgWithInvalidID("unknown@yopmail.com", getDT2TestingCellValueBySheetRowAndColumn("ForgotPasswordContent", "errorMessage", "expectedMessage")), "Validate the error message when entering email id not associated with TCP");
        AssertFailAndContinue(forgotYourPasswordPageActions.successMsgWithValidID(successMsgContent.get(0), emailAddressReg), "Validate the error message when entering the valid email ID");
        AssertFailAndContinue(forgotYourPasswordPageActions.returnToLoginLink(loginDrawerActions), "Verify if the user redirected to the login page on clicking the return to login button");
        AssertFailAndContinue(loginDrawerActions.showHideLinkPassword("P@ssw0rd"), "Validated the actions of the Show/Hide link");
    }
}
