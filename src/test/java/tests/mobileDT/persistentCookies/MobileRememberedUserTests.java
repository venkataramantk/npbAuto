package tests.mobileDT.persistentCookies;

import io.appium.java_client.MobileDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import ui.utils.MethodListener;
import org.openqa.selenium.remote.DesiredCapabilities;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Listeners(MethodListener.class)
//@Test(singleThreaded = true)
public class MobileRememberedUserTests extends MobileBaseTest {
    WebDriver mobileDriver;
    String emailUS = UiBaseMobile.randomEmail();
    String emailCA = UiBaseMobile.randomEmail();
    String userName = "";

    @Parameters("store")
    @BeforeClass
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        mobileDriver.manage().deleteAllCookies();
        initializeMobilePages(mobileDriver);
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        mhomePageActions.closeSurveyPopup();
        panCakePageActions.navigateToMenu("CREATEACCOUNT");
        createNewAccountAndGetEmailAddr("CreateAccountUS", emailUS);
        mobileDriver.manage().deleteAllCookies();

        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        mhomePageActions.closeSurveyPopup();
        panCakePageActions.navigateToMenu("CREATEACCOUNT");
        createNewAccountAndGetEmailAddr("CreateAccountCA", emailCA);
        mobileDriver.manage().deleteAllCookies();
    }

    @Parameters("store")
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
        mobileDriver.navigate().refresh();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = REMEMBERUSER)
    public void VefiryLoginAtChckOutOnRememberMeUnchecked(String store, String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store ,Verify for user login with remember me unchecked, user is displayed when guest user tries to checkout");
        String testName = "ValidateLoginAtCheckOutWithRememberMeUnchecked";

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        panCakePageActions.navigateToMenu("LOGIN");
        String emailId = emailUS;
        String rowName = "CreateAccountUS";
        userName = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "FirstName");
        if (store.equalsIgnoreCase("CA")) {
            emailId = emailCA;
            rowName = "CreateAccountCA";
        }
        userName = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowName, "FirstName");
        mloginPageActions.loginWithRememberMe(emailId, getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowName, "Password"), false);

        mobileDriver.close();
        initializeDriver();
        mobileDriver = getDriver();
        mobileDriver.manage().deleteAllCookies();
        initializeMobilePages(mobileDriver);

        ///////Launch site and verify user is not logged-in
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        mobileDriver.navigate().refresh();
        AssertFailAndContinue(panCakePageActions.isUserLoggedIn(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowName, "FirstName"), false), "User is validated to be not logged in when Remember me is unchecked and browser is reopened.");

        addToBagBySearching(searchKeywordAndQty);

        //////Click on Checkout button and verify if login modal is displayed
        mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions);
        boolean isLoginPageDisplayed = mloginPageActions.isEmailAddressDisplayed();
        //DT-37985
        AssertFailAndContinue(isLoginPageDisplayed, "Verify login modal is displayed for guest user at the time of checkout.");

    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }


}
