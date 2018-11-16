package tests.mobileDT;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

/**
 * Created by JKotha on 17/10/2017.
 */
//User Story: DT-853
//@Test(singleThreaded = true)
public class MyPlaceRewards extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = UiBaseMobile.randomEmail();
    String password = "";

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.deleteAllCookies();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY, USONLY})
    public void verifyMyPlaceRewards(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("VERIFY MPR");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        panCakePageActions.navigateToMenu("Rewards");
        //DT-2408
        AssertFailAndContinue(mMyPlaceRewardsActions.isAtMyPlaceRewardsPage(), "Click on Menu Icon ->My Place Rewards link. Verify My Place Rewards Page is displayed");
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Shorts");
        mheaderMenuActions.clickMenuIcon();

        //DT-2406
        AssertFailAndContinue(panCakePageActions.isLinkDisplayed("Rewards"), "verify whether \"My Place Rewards\" link is displayed within the flyout that opens up when user clicks on the pancake from within any of the pages present on the site");

        //DT-2407
//        mobileDriver.get(EnvironmentConfig.getCA_ApplicationUrl());
        mheaderMenuActions.clickMenuIcon();
        AssertFailAndContinue(!panCakePageActions.getText(panCakePageActions.panLinks).contains("My Place Rewards"), "verify whether \"My Place Rewards\" link is not displayed within the flyout for CA site");
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {MYACCOUNT, USONLY, GUESTONLY})
    public void verifyMyPlaceRewards_guest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("VERIFY MPR page as guest user DT-41920");

        panCakePageActions.navigateToMenu("Rewards");

        AssertFailAndContinue(mMyPlaceRewardsActions.clickLoginLink(mloginPageActions), "Click login in MPR page and verify login page is displayed");
        mloginPageActions.clickCloseLoginModal();

        panCakePageActions.navigateToMenu("Rewards");
        AssertFailAndContinue(mMyPlaceRewardsActions.clickCreateAccountLink(mcreateAccountActions), "Click Create Account buttin in MPR page and verify create account page is displayed");
        mloginPageActions.clickCloseLoginModal();

        panCakePageActions.navigateToMenu("Rewards");
        AssertFailAndContinue(mMyPlaceRewardsActions.clickLearnMoreLink(mplccActions), "Click Create Account buttin in MPR page and verify create account page is displayed");

        mloginPageActions.clickCloseLoginModal();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }

}
