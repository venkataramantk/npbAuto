package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

/**
 * Created by JKotha on 08/11/2017.
 */
//User Story: DT-645
//@Test(singleThreaded = true)
public class PanCakeTests extends MobileBaseTest {
    WebDriver mobileDriver;
    String email;
    String password;
    String url;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);

        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        email = UiBaseMobile.randomEmail();
        if (store.equalsIgnoreCase("US")) {
            url = EnvironmentConfig.getApplicationUrl();
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryByCountry("CANADA");
//            url = EnvironmentConfig.getCA_ApplicationUrl();
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelCA, email);
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT})
    public void logoTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {

        setAuthorInfo("Jagadeesh k");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" + "" +
                "1. Verify user is navigating back to home page when clicking on TCP logo in different pages" +
                "DT-41885");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-2203
        mheaderMenuActions.clickShoppingBagIcon();
        mheaderMenuActions.clickOnTCPLogo();
        AssertFailAndContinue(mobileDriver.getCurrentUrl().equals(url), "verify that the user is redirected to the corresponding store's home page after clicking on Logo in shopping page .");

        panCakePageActions.navigateToMenu("FINDASTORE");
        mheaderMenuActions.clickOnTCPLogo();
        AssertFailAndContinue(mobileDriver.getCurrentUrl().equals(url), "verify that the user is redirected to the corresponding store's home page after clicking on Logo in find a store page.");

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value"));
        mheaderMenuActions.clickOnTCPLogo();
        AssertFailAndContinue(mobileDriver.getCurrentUrl().equals(url), "verify that the user is redirected to the corresponding store's home page after clicking on Logo in PLP.");

        //and DT-2204
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value"));
        msearchResultsPageActions.clickOnProductImageByPosition(1);
        mheaderMenuActions.clickOnTCPLogo();
        AssertFailAndContinue(mobileDriver.getCurrentUrl().equals(url), "verify that the user is redirected to the corresponding store's home page after clicking on Logo in PDP.");

        mheaderMenuActions.clickMenuIcon();
        AssertFailAndContinue(panCakePageActions.verifySizeRanges(), "Verify sizes are displayed beside departments");

    }

    //validations for my account drawer are moved to myAccountDrawerTests in the same class
    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT})
    public void navigationTests(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" + "" +
                "1. Verify user is navigating different pages from pancake" +
                "DT-44572");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        if (user.equalsIgnoreCase("guest")) {
            panCakePageActions.navigateToMenu("LOGIN");
            AssertFailAndContinue(mloginPageActions.isDisplayed(mloginPageActions.loginButton), "Verify Login Page for guest use");

            mobileDriver.navigate().refresh();
            panCakePageActions.navigateToMenu("CREATEACCOUNT");
            AssertFailAndContinue(mcreateAccountActions.isDisplayed(mcreateAccountActions.createAccountTitle), "Verify Create Account Page for guest use");
        }

        //DT-2208
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        panCakePageActions.navigateToMenu("FINDASTORE");
        AssertFailAndContinue(mheaderMenuActions.validateHrefLangTag(), "Verify HREF Lang tag should contain www across all the pages");
        AssertFailAndContinue(mstoreLocatorPageActions.isDisplayed(mstoreLocatorPageActions.searchButton), "Verify Store Locator Page");

        //DT-2216
        panCakePageActions.navigateToMenu("REWARDS");
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(mMyPlaceRewardsActions.isDisplayed(mMyPlaceRewardsActions.myPlaceRewardsBanner), "Verify My Place Rewards Page");

        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(mMyPlaceRewardsActions.isDisplayed(mMyPlaceRewardsActions.applyButton), "Verify My Place Rewards Page");

        //DT-2221, //DT-2218, //DT-2236
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        if (user.equalsIgnoreCase("guest")) {
            panCakePageActions.navigateToMenu("FAVORITES");
            AssertFailAndContinue(mobileFavouritesActions.isDisplayed(mobileFavouritesActions.emailAddrField), "Verify Favorites List as Guest User");
            mloginPageActions.loginWithIncorrectCredentails(email, password);
        }
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGISTEREDONLY})
    public void myAccountDrawerTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("validate following " +
                "DT-41677, DT-41839, DT-41840, DT-41841, DT-41673, DT-41665, DT-41666, DT-41811");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("USER");

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(!mmyAccountPageDrawerActions.validatePointsAndRewardsInPanCake(), "verify Coupons and Points in the header for CA is not displayed");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mmyAccountPageDrawerActions.validatePointsAndRewardsInPanCake(), "verify Coupons and Points in the header");
            mheaderMenuActions.refreshPage();
            panCakePageActions.navigateToMenu("USER");
            AssertFailAndContinue(mmyAccountPageDrawerActions.validateMyAccPageDrawerContent("POINTS TO YOUR NEXT $5 REWARD:", "My Rewards and Offers "), "Verify My Account drawer");
            AssertFailAndContinue(mmyAccountPageDrawerActions.validateNumbersInProgressBar(), "Validate numbers in points bar");
            AssertFailAndContinue(mmyAccountPageDrawerActions.getCurrentPoints() == 0, "Verify rewards points are zero");
        }

        AssertFailAndContinue(mmyAccountPageDrawerActions.validateOrderDetails(), "My Account Drawer_display of Order Status Preview_when no orders have been placed so far");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, CAONLY}, enabled = false)
    public void mpr_CA(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in CA store verify MPR options is not available and Points bar is not displayed if user has zero points");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        panCakePageActions.clickMenuIcon();
        AssertFailAndContinue(!panCakePageActions.getText(panCakePageActions.panLinks).contains("My Place Rewards"), "verify My Place Rewards is not available for CA pan Cake.");
        panCakePageActions.clickCloseIcon();
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("USER");
        }
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {PDP, RECOMMENDATIONS, REGISTEREDONLY})
    public void valitedRecommendadtions_fav(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Guest/Registered/Remembered user is in US/CA/INT En/Es/Fr store,When the user is in Shopping bag, verify user is able to add recommendations products to fav" +
                "DT-44572");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("FAVORITES");
        AssertFailAndContinue(mheaderMenuActions.validateHrefLangTag(), "Verify HREF Lang tag should contain www across all the pages");
        AssertFailAndContinue(mfooterActions.fav_recommAsReg(), "Click fav icon on first recommended product ");
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
