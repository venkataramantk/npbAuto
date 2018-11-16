package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;

//@Test(singleThreaded = true)
public class StyleSquadTests extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = mcreateAccountActions.randomEmail();
    String password;
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env = EnvironmentConfig.getEnvironmentProfile();

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
                email = mcreateAccountActions.randomEmail();
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
    @AfterClass(alwaysRun = true)
    public void quitBrowser() {
        mobileDriver.quit();
    }


    @Test(dataProvider = dataProviderName, priority = 0, groups = {GLOBALCOMPONENT})
    public void styleSquadChecks(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in " + store + " store \n" +
                "1. Verify the Style squad page template\n" +
                "2. Verify Error Message for Empty Fields");
        List<String> styleSquad = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "StyleSquad", "Value"));
        List<String> styleErr = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "StyleSquad", "validErrMsg1"));
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (env.equalsIgnoreCase("prod")) {
            driver.get(EnvironmentConfig.getApplicationUrl() + "/" + store + "/content/style-squad-form");
        } else {
            driver.get("https://" + EnvironmentConfig.getEnvironmentProfile() + ".childrensplace.com/" + store + "/content/style-squad-form");
        }

        AssertFailAndContinue(mfooterActions.styleSquadCheck(), "Verify Style squad template");
        AssertFailAndContinue(mfooterActions.validateEmptyStyleSquadFields(styleErr.get(0), styleErr.get(1), styleErr.get(2), styleErr.get(3), styleErr.get(4), styleErr.get(5), styleErr.get(6), styleErr.get(7)
                , styleErr.get(8), styleErr.get(0), styleErr.get(9), styleErr.get(10), styleErr.get(11)), "Verify Error Message for empty Fields");
        mfooterActions.fillStyleSquadAndSubmit("fn", "ln", email, "kpd", "NJ", "07094", "ba", "6", "F", "ddd");
    }
}
