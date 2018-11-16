package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

public class FindAStoreTests extends MobileBaseTest {
    WebDriver mobileDriver;
    String email, password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        email = UiBaseMobile.randomEmail();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, PRODUCTION})
    public void storeLocatorTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Validate Find A Store UI and Tests" +
                "DT-20498, DT-24100, DT-24101, DT-24102, DT-24104, DT-24105, DT-24111, DT-24112, DT-24114, DT-24115, DT-27593, DT-27606" +
                "DT-24086");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        AssertFailAndContinue(mfooterActions.validateStoreLocatorLink(mstoreLocatorPageActions), "Click Store locator from footer and verify Store locator page is displayed");
        AssertFailAndContinue(mstoreLocatorPageActions.validateStoreLocatorUI(), "Validate All the links in Store Locator Page");
        AssertFailAndContinue(mstoreLocatorPageActions.validateInternationalStoresLink(), "Click on International Store link and verify in the new page Country Drop-down is displayed along with Country details in list view");
        AssertFailAndContinue(mstoreLocatorPageActions.selectACountry("#India"), "Select a countr from Drop-down and verify user is navigated to that country list");
        AssertFailAndContinue(mstoreLocatorPageActions.verifyStoreAndStateName(), "Verify Store and State name in the country list");
        mheaderMenuActions.clickOnTCPLogo();
        mfooterActions.validateStoreLocatorLink(mstoreLocatorPageActions);
        AssertFailAndContinue(mstoreLocatorPageActions.validateUSAndCanadaLinks(), "Validate Us And canada links");
        mstoreLocatorPageActions.expandStateOrProvince("Alabama");
        AssertFailAndContinue(mstoreLocatorPageActions.validateStoreNameAddressPhone(), "validate 1.Store Name 2.Store Address 3.Store Phone number");

        AssertFailAndContinue(mstoreLocatorPageActions.selectAStoreByPosition(1), "Select a store from the list and verify store data is displayed from Google Map");

        AssertFailAndContinue(mstoreLocatorPageActions.validateGetDirectionsButton(), "check Get Directions button");
        mheaderMenuActions.clickOnTCPLogo();
        mfooterActions.validateStoreLocatorLink(mstoreLocatorPageActions);
        AssertFailAndContinue(mstoreLocatorPageActions.selectRandomAutoPopulatedAddress("512"), "Select random Auto Populated Address");
        AssertFailAndContinue(mstoreLocatorPageActions.validateStoresListedDistance(), "Verify should be listed in order of closest to furthest stores in terms of distance from user's location.");
        AssertFailAndContinue(mstoreLocatorPageActions.verifyGoogelSuggestions("234"), "Verify Google Suggestions are displayed when user start typing the address in search box");

        if(store.equalsIgnoreCase("CA")){
            mheaderMenuActions.clickOnTCPLogo();
            mfooterActions.validateStoreLocatorLink(mstoreLocatorPageActions);
            mstoreLocatorPageActions.selectRandomAutoPopulatedAddress("512");
            mstoreLocatorPageActions.showDetailsOfStore();
            AssertFailAndContinue(mstoreLocatorPageActions.verifyElementNotDisplayed(mstoreLocatorPageActions.setAsFavStoreLnk,1),"Verify set as fav link is not displayed in CA store");
        }
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, PROD_REGRESSION, REGISTEREDONLY, USONLY})
    public void favStoreTest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered user in" + store + "" +
                "DT-27606: verify fav store is not displayed when user switched store from us to ca" +
                "DT-27595: Verify if the user is able to see the current store as fav store in my account page");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        mstoreLocatorPageActions.addAFavStore_Reg(mheaderMenuActions, panCakePageActions, "08830");
        String favStore = mstoreLocatorPageActions.getFavrioteStore();

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");
        AssertFailAndContinue(mmyAccountPageActions.getFavStoreNameDetails().get(0).equalsIgnoreCase(favStore), "Verify Fav store is displayed in my account page");

        mstoreLocatorPageActions.addAFavStore_Reg(mheaderMenuActions, panCakePageActions, "07730");
        favStore = mstoreLocatorPageActions.getFavrioteStore();
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");
        AssertFailAndContinue(mmyAccountPageActions.getFavStoreNameDetails().get(0).equalsIgnoreCase(favStore), "Verify initial Fav store is replaced with new fav store in my account page");

        if (store.equalsIgnoreCase("us")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");
        AssertFailAndContinue(mmyAccountPageActions.verifyElementNotDisplayed(mmyAccountPageActions.defaultFavStore), "change the store from footer and view the MY accounts page,\"My current store\" should not be shown in My accounts page.");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, PROD_REGRESSION, GUESTONLY, USONLY})
    public void favStoreTest_guest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a guest user in" + store + " add a fav store to user" +
                "DT-27589: verify after login fav store is not displayed in My account" +
                "");

        mstoreLocatorPageActions.addAFavStore_Reg(mheaderMenuActions, panCakePageActions, "08830");
        String favStore = mstoreLocatorPageActions.getFavrioteStore();

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");
        AssertFailAndContinue(mmyAccountPageActions.verifyElementNotDisplayed(mmyAccountPageActions.storeName), "Verify fav store is not displayed after suggest user logged");
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
