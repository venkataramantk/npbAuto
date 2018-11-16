package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

/**
 * Created by JKotha on 30/11/2017.
 */
//@Listeners(MethodListener.class)
//@Test(singleThreaded = true)
public class HeaderTests extends MobileBaseTest {
    String email, password;
    WebDriver mobileDriver;


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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT})
    public void shipToCountryTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("UI validations for Ship to page for " + user + "In " + store + "" +
                "DT-27457, DT-27456, DT-27454, DT-27453, DT-41916");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.bagLink), "Verify Mini Cart icon in the header");
        mfooterActions.clickOnLanguageButton();
        AssertFailAndContinue(mfooterActions.validateAllFieldsInShipToPage(), "Verify all fields in ship to page");

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mfooterActions.getSelectOptions(mfooterActions.shipToCountryDropdown).equals(getmobileDT2CellValueBySheetRowAndColumn("Country", "US", "Name")), "Verify Country Drop-down in ship to page and Default country for US store in ship to page");
            AssertFailAndContinue(mfooterActions.getSelectOptions(mfooterActions.currencyDropdown).equals(getmobileDT2CellValueBySheetRowAndColumn("Country", "US", "Currency")), "Verify Currency Drop-down in ship to page and Default Currency for US store in ship to page");
            mfooterActions.selectDropDownByVisibleText(mfooterActions.shipToCountryDropdown, getmobileDT2CellValueBySheetRowAndColumn("Country", "CA", "Name"));
            mfooterActions.selectDropDownByVisibleText(mfooterActions.langDropdown, getmobileDT2CellValueBySheetRowAndColumn("Country", "CA", "Language"));
            AssertFailAndContinue(mfooterActions.getSelectOptions(mfooterActions.langDropdown).equals(getmobileDT2CellValueBySheetRowAndColumn("Country", "CA", "Language")), "Verify if user can change the \"Language\"");
            AssertFailAndContinue(mfooterActions.getSelectOptions(mfooterActions.currencyDropdown).equals(getmobileDT2CellValueBySheetRowAndColumn("Country", "CA", "Currency")), "Verify if user can change the \"Currency\"");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mfooterActions.getSelectOptions(mfooterActions.shipToCountryDropdown).equals(getmobileDT2CellValueBySheetRowAndColumn("Country", "CA", "Name")), "Verify Country Drop-down in ship to page and Default country for CA store in ship to page");
            AssertFailAndContinue(mfooterActions.getSelectOptions(mfooterActions.langDropdown).equals(getmobileDT2CellValueBySheetRowAndColumn("Country", "CA", "Language")), "Verify Language Drop-down in ship to page and Default Language for CA store in ship to page");
            AssertFailAndContinue(mfooterActions.getSelectOptions(mfooterActions.currencyDropdown).equals(getmobileDT2CellValueBySheetRowAndColumn("Country", "CA", "Currency")), "Verify Currency Drop-down in ship to page and Default Currency for CA store in ship to page");
            mfooterActions.selectDropDownByVisibleText(mfooterActions.shipToCountryDropdown, getmobileDT2CellValueBySheetRowAndColumn("Country", "US", "Name"));
            AssertFailAndContinue(mfooterActions.getSelectOptions(mfooterActions.langDropdown).equals(getmobileDT2CellValueBySheetRowAndColumn("Country", "US", "Language")), "Verify if user can change the \"Language\"");
            AssertFailAndContinue(mfooterActions.getSelectOptions(mfooterActions.currencyDropdown).equals(getmobileDT2CellValueBySheetRowAndColumn("Country", "US", "Currency")), "Verify if user can change the \"Currency\"");
        }

        AssertFailAndContinue(mfooterActions.shipTo_termsAndConditionsLink(), "Validate Terms and Conditions link Ship To Model");

    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT})
    public void shipToModel_Int(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("DT-27455: As a int user verify ship to model");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        mfooterActions.changeCountryByCountry("India");

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "tops");
        mcategoryDetailsPageAction.clickProductByPosition(1);
        mfooterActions.clickOnLanguageButton();
        AssertFailAndContinue(mfooterActions.validateAllFieldsInShipToPage(), "Verify all fields in ship to page");
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
