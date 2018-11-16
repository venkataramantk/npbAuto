package tests.mobileDT.myAccount;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Test(singleThreaded = true)
public class BirthDaySavings extends MobileBaseTest {
    String email = UiBaseMobile.randomEmail();
    String password = "";
    WebDriver mobileDriver;
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));

    //DT-506
    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            createAccount(rowInExcelUS, email);
        }
        if (store.equalsIgnoreCase("CA")) {
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

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void birthdaySavingsUIValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("UI validations for Profile Information Page");
        Map<String, String> childDetails = dt2MobileExcel.getExcelData("Birthday", "validChildDetailsUS");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        //DT-23740
        AssertFailAndContinue(mmyAccountPageActions.clickAddBirthDayInfoBtn(), "Click Add birthday info button and verify Birthday Savings page is displayed");
        //DT-DT-23586
        AssertFailAndContinue(mmyAccountPageActions.validateBirthdaySavingsUI(), "Verify Birthday savings page UI");

        //DT-23571
        AssertFailAndContinue(mmyAccountPageActions.clickAddAChildBtn(), "Click add a child button and verify required fields are displayed");

        AssertFailAndContinue(mmyAccountPageActions.validateAddAChildFields(), "Verify all the fields, buttons and links in add a child page");

        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.genderDrpDownDisplay).contains(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "validChildDetailsUS", "gender")) &&
                mmyAccountPageActions.getText(mmyAccountPageActions.genderDrpDownDisplay).contains(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "validSecondChildInfo", "gender")), "Verify \"Gender\" drop-down values is displayed");

        //DT-23572
        AssertFailAndContinue(mmyAccountPageActions.clickCancelInAddaChildPage(), "Click cancel button in a add child page and verify, add a child page is closed");
        ///DT-23577

        mmyAccountPageActions.clickAddAChildBtn();
        AssertFailAndContinue(mmyAccountPageActions.addAChild(childDetails.get("childName"), childDetails.get("birthMonth"), childDetails.get("birthYear"), childDetails.get("gender"), store, childDetails.get("lName")), "Verify Success message after adding a child");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.name).equalsIgnoreCase(store + " " + childDetails.get("lName")), "Verify user Name is updated in Profile info page");

        mheaderMenuActions.clickMenuIcon();
        AssertFailAndContinue(mobileFavouritesActions.getText(panCakePageActions.salutationLink).contains(store), "Verify Username in pancake updated");
        mheaderMenuActions.clickMenuIcon();
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");
        mmyAccountPageActions.clickAddBirthDayInfoBtn();

        mmyAccountPageActions.click(mmyAccountPageActions.removeChild(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "validChildDetailsUS", "childName")));

        AssertFailAndContinue(mmyAccountPageActions.verifyRemoveBirthdayInfo(), "Click Remove button for a child and verify all the fields in remove bday info");
        AssertFailAndContinue(mmyAccountPageActions.addAChildBtns.size() == 3, "Verify child is not remove after cancel the process of removing kid from remove birthday page");
        AssertFailAndContinue(mmyAccountPageActions.removeAChild(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "validChildDetailsUS", "childName")), "verify error message after removing child");
        AssertFailAndContinue(mmyAccountPageActions.addAChildBtns.size() == 4, "Verify A maximum of 4 Add a child buttons displayed after removing child");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void inLineErrorMessages(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify inline error messages as registered user in" + store + " store ");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        mmyAccountPageActions.clickAddBirthDayInfoBtn();
        mmyAccountPageActions.clickAddAChildBtn();
        mmyAccountPageActions.clickAddChildBtn();

        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.getInline("Child")).contains(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "childName", "validErrMsgContent")), "Verify inline Error for Child's name field");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.birthdayMontherr).contains(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "monthErr", "validErrMsgContent")), "Verify inline Error for Birthday month field");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.birthdayYearerr).contains(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "yearErr", "validErrMsgContent")), "Verify inline Error for Birthday Year field");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.birthdayGendererr).contains(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "genderErr", "validErrMsgContent")), "Verify inline Error for Gender field");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.getInline("First Name")).contains(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "fnErr", "validErrMsgContent")), "Verify inline Error for FirstName field");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.getInline("Last Name")).contains(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "lnErr", "validErrMsgContent")), "Verify inline Error for Last Name field");
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.termsErr).contains(getmobileDT2CellValueBySheetRowAndColumn("Birthday", "termsError", "validErrMsgContent")), "Verify inline Error for Terms Error field");
    }

    @AfterClass(alwaysRun = true)
    public void quitBrowser() {
        mobileDriver.quit();
    }
}
