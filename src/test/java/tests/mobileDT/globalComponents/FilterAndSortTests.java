package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

//@Test(singleThreaded = true)
public class FilterAndSortTests extends MobileBaseTest {

    WebDriver mobileDriver;
    String email, password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.deleteAllCookies();
        email = UiBaseMobile.randomEmail();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {PLP, KOMODO, "DT-43082"})
    public void filterAndUiValidations_plp(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " store verify " +
                "1. Verify if \"Filter\" and \"Sort\" CTA are displayed as separate CTA in PLP and Show more/less is not displayed " +
                "2. Verify if \"Filter\" and \"Sort\" CTA colors are black " +
                "3. Verify when filter and sort by buttons are expanded color changed to Pink for CTA " +
                "4. When filter is expanded verify done button is displayed with pink color " +
                "5. Verify Clear all button is in disable state " +
                "6. Verify sore options " +
                "7. Verify Facet open and close ");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("login");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        panCakePageActions.navigateToPlpCategory("girl", null, "Bottoms", "Shop All");
        AssertFailAndContinue(mcategoryDetailsPageAction.verifyFilterAndsortCTA(), "Verify if \"Filter\" and \"Sort\" CTA are displayed as separate CTA with black color in PLP page");
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Click on filter button, verify filter and Done buttons are displayed in Pink color");
        AssertFailAndContinue(mcategoryDetailsPageAction.validateClearAllButtonState("Disable"), "verify Clear All button is in disabled state");
        mcategoryDetailsPageAction.scrollToTop();
        AssertFailAndContinue(mcategoryDetailsPageAction.openSortByCTA(), "Click on sortBy CTA, verify SortBy displayed in Pink color");
        AssertFailAndContinue(mcategoryDetailsPageAction.validateSortoptions(6), "Verify Sort options");

        msearchResultsPageActions.openFilterButton();

        AssertFailAndContinue(mcategoryDetailsPageAction.openFacet("size"), "Click on Size Facet and verify that all size lists are displayed ");
        AssertFailAndContinue(mcategoryDetailsPageAction.closeFacet("size"), "Close Size Facet and verify facet is closed");

        AssertFailAndContinue(mcategoryDetailsPageAction.openFacet("color"), "Click on color Facet and verify that all colors lists are displayed ");
        AssertFailAndContinue(mcategoryDetailsPageAction.closeFacet("color"), "Close Color Facet and verify facet is closed");

        AssertFailAndContinue(mcategoryDetailsPageAction.openFacet("fit"), "Click on Fit Facet and verify that all fits lists are displayed ");
        AssertFailAndContinue(mcategoryDetailsPageAction.closeFacet("fit"), "Close fit Facet and verify Fit facet is closed");

        AssertFailAndContinue(mcategoryDetailsPageAction.openFacet("gender"), "Click on gender Facet and verify that all gender lists are displayed ");
        AssertFailAndContinue(mcategoryDetailsPageAction.closeFacet("gender"), "Close gender Facet and verify Gender facet is closed");

        AssertFailAndContinue(mcategoryDetailsPageAction.openFacet("price"), "Click on price Facet and verify that all price lists are displayed ");
        AssertFailAndContinue(mcategoryDetailsPageAction.closeFacet("price"), "Close Price Facet and verify Price facet is closed");

        AssertFailAndContinue(msearchResultsPageActions.openFacet("size range"), "Click on age Facet and verify that all Age lists are displayed and Clear All link is not displayed beside price Facet");
        AssertFailAndContinue(msearchResultsPageActions.closeFacet("size range"), "Close on age Facet and verify Age facet is closed ");

        AssertFailAndContinue(mcategoryDetailsPageAction.selectRandomSizeRange(), "Select random age and verify Clear all button is displayed beside Age Facet");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectRandomPrice(), "Select Random Price and verify Clear All button is displayed beside Price Facet");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectGender("Girl"), "Select aRandom Gender and verify Clear ALl button is displayed beside Gender Facet");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectRandomFit(), "Select Random Fit and verify Clear All button is displayed beside Fit Facet");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectRandomColor(), "Select Random Color and verify Clear ALl button is displayed beside Color facet");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectRandomSize(), "Select Random size and Verify Clear ALl button is displayed beside Size facet");
        mcategoryDetailsPageAction.clickDoneBtn();

        AssertFailAndContinue(mcategoryDetailsPageAction.clickProductByPosition(1), "Apply multiple filters and click any product, verify PDP page is displayed");
        mcategoryDetailsPageAction.navigateBack();
        AssertFailAndContinue(mcategoryDetailsPageAction.validateClearAllButtonState("Enable"), "CLick browser back button and verify filters are retained");

        panCakePageActions.navigateToPlpCategory("accessories", null, "Girl", "Shop All");
        mcategoryDetailsPageAction.openFilterButton();
        AssertFailAndContinue(mcategoryDetailsPageAction.validateFilterOptions(5), "Verify Fit facet is not displayed under filter when searched with Boy -> tops");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {PLP, KOMODO, "DT-43082"})
    public void filterAndSortUiValidations_Search(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " store verify " +
                "1. Verify if \"Filter\" and \"Sort\" CTA are displayed as separate CTA in SRP and check show more/less are not displayed" +
                "2. Verify if \"Filter\" and \"Sort\" CTA colors are black " +
                "3. Verify when filter and sort by buttons are expanded color changed to Pink for CTA " +
                "4. When filter is expanded verify done button is displayed with pink color " +
                "5. Verify Clear all button is in disable state " +
                "6. Verify sore options " +
                "7. Verify Facet open and close ");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Bottoms");
        AssertFailAndContinue(msearchResultsPageActions.verifyFilterAndsortCTA(), "Verify if \"Filter\" and \"Sort\" CTA are displayed as separate CTA with black color in SRP");

        AssertFailAndContinue(msearchResultsPageActions.openFilterButton(), "Click on filter button, verify filter and Done buttons are displayed in Pink color");
        AssertFailAndContinue(!msearchResultsPageActions.validateClearAllButtonState(), "verify Clear All button is in disabled state");
        msearchResultsPageActions.scrollToTop();
        AssertFailAndContinue(msearchResultsPageActions.openSortByCTA(), "Click on sortBy CTA, verify SortBy displayed in Pink color");
        AssertFailAndContinue(msearchResultsPageActions.validateSortoptions(6), "Verify Sort options");

        msearchResultsPageActions.openFilterButton();
        AssertFailAndContinue(msearchResultsPageActions.openFacet("size"), "Click on Size Facet and verify that all size lists are displayed and Clear All link is not displayed beside Size Facet");
        AssertFailAndContinue(msearchResultsPageActions.closeFacet("size"), "Close Size Facet and verify facet is closed");

        AssertFailAndContinue(msearchResultsPageActions.openFacet("color"), "Click on Color Facet and verify facet is verify that all colors list are displayed, and Clear All link is not displayed beside Color Facet");
        AssertFailAndContinue(msearchResultsPageActions.closeFacet("color"), "Close Color Facet and verify facet is closed");

        AssertFailAndContinue(msearchResultsPageActions.openFacet("fit"), "Click on Fit Facet and verify that all fits lists are displayed and Clear All link is not displayed beside FIT Facet");
        AssertFailAndContinue(msearchResultsPageActions.closeFacet("fit"), "Close fit Facet and verify Fit facet is closed");

        AssertFailAndContinue(msearchResultsPageActions.openFacet("gender"), "Click on gender Facet and verify that all gender lists are displayed and Clear All link is not displayed beside Gender Facet");
        AssertFailAndContinue(msearchResultsPageActions.closeFacet("gender"), "Close gender Facet and verify Gender facet is closed");

        AssertFailAndContinue(msearchResultsPageActions.openFacet("price"), "Click on price Facet and verify that all price lists are displayed and Clear All link is not displayed beside price Facet");
        AssertFailAndContinue(msearchResultsPageActions.closeFacet("price"), "Close Price Facet and verify Price facet is closed ");

        AssertFailAndContinue(msearchResultsPageActions.openFacet("size range"), "Click on age Facet and verify that all Age lists are displayed and Clear All link is not displayed beside price Facet");
        AssertFailAndContinue(msearchResultsPageActions.closeFacet("size range"), "Close on age Facet and verify Age facet is closed ");

        AssertFailAndContinue(msearchResultsPageActions.selectRandomAge(), "Select random age and verify Clear all button is displayed beside Age Facet");
        AssertFailAndContinue(msearchResultsPageActions.selectRandomPrice(), "Select Random Price and verify Clear All button is displayed beside Price Facet");
        AssertFailAndContinue(msearchResultsPageActions.selectGender("BOY"), "Select aRandom Gender and verify Clear ALl button is displayed beside Gender Facet");
        AssertFailAndContinue(msearchResultsPageActions.selectRandomFit(), "Select Random Fit and verify Clear All button is displayed beside Fit Facet");
        AssertFailAndContinue(msearchResultsPageActions.selectRandomColor(), "Select Random Color and verify Clear ALl button is displayed beside Color facet");
        AssertFailAndContinue(msearchResultsPageActions.selectRandomSize(), "Select Random size and Verify Clear ALl button is displayed beside Size facet");

        AssertFailAndContinue(msearchResultsPageActions.validateClearAllButtonState(), "verify Clear All button is in enabled state");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {PLP, KOMODO, "DT-43082"})
    public void sortFunctionality_plp(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in " + store + " store verify" +
                "1. Verify sort functionality in plp page" +
                "DT-31563, DT-31564, DT-41921");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-38279
        panCakePageActions.navigateToPlpCategory("girl", null, "Bottoms", "Shop All");
        AssertFailAndContinue(mcategoryDetailsPageAction.sortByOptionPlp("Low_to_high"), "Verify Low to high sorting");
        //DT-38280
        AssertFailAndContinue(mcategoryDetailsPageAction.sortByOptionPlp("high_to_low"), "Verify high to sorting");
        //DT-38383
        panCakePageActions.navigateToPlpCategory("girl", null, "Bottoms", "Shop All");
        //DT-38383 & DT-43041
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Open Filter Button");
        AssertFailAndContinue(mcategoryDetailsPageAction.verifySelectedSortOption("Recommended"), "Verify Recommended Sort Option is selected");

        panCakePageActions.navigateToPlpCategory("TODDLER GIRL", null, null, "Sets");
        AssertFailAndContinue(msearchResultsPageActions.openFilterButton(),"Verify direct navigation to PLP when only 1 L2 exists");

        panCakePageActions.navigateToPlpCategory("TODDLER GIRL", null, null, "ONLINE ONLY");
        AssertFailAndContinue(msearchResultsPageActions.openFilterButton(),"Verify direct navigation to PLP for all L2 throughout the site having only single sub-category available for L2");

    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {PLP, KOMODO, "DT-43082"})
    public void sortFunctionality_Search(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in " + store + " store verify" +
                "1. Verify sort functionality in SRP page");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-43668
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Bottoms");
        AssertFailAndContinue(msearchResultsPageActions.sortByOptionPlp("Low_to_high"), "Verify Low to high sorting");
        //DT-43667
        AssertFailAndContinue(msearchResultsPageActions.sortByOptionPlp("high_to_low"), "Verify high to sorting");
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
