package tests.webDT.globalComponents.searchBar;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by Venkat on 03/09/2017.
 * Updated by JK on 03/12/2017
 */
//USer Story: DT-739
//@Listeners(MethodListener.class)
public class GlobalSearchField extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg;
    String password;

    @Parameters({storeXml,usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store,@Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);

        driver.get(EnvironmentConfig.getApplicationUrl());
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
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE, PROD_REGRESSION})
    public void searchBarValidate(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" +
                "1. When user startes to enter a search term in the search text box, verify departments links are displayed and upon clicking on department link user navigates to correct page\n" +
                "2. Verify if the Search Result Page contains the given URL\n" +
                "3. Validated that the user remains on the same page when either clicking on Search button or pressing Enter Key without entering any search term.\n" +
                "4. I am looking for category displaying in Auto suggestions box\n" +
                "5. Validate Header elements (Header Banner, find a store, departments links, Search field) in Search Results page");
        Map<String, String> search_Term = excelReaderDT2.getExcelData("Search", "SearchBy");
        List<String> searchGhostText = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "GhostTextValue"));

        //AssertFailAndContinue(headerMenuActions.waitUntilElementDisplayed(headerMenuActions.rotatingBanner), "Verify Rotating banner");
//         AssertFailAndContinue(baseApi.isSearchAPITriggered(store, "tops"),"The search API is triggered after typing with search term");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE, PROD_REGRESSION})
    public void searchUsingKeyWord(@Optional("US") String store, @Optional("registered") String user) {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" +
                "1. Verify Footer is not displayed when search results are loading" +
                "2. validate Back To Top button\n" +
                "3. Verify search results count");
        Map<String, String> search_Term = excelReaderDT2.getExcelData("Search", "SearchBy");

        AssertFailAndContinue(footerActions.validateBackToTop(), "Verify user scrolling down on search result page starts displaying Back to Top button with Back To Top text");
        AssertFailAndContinue(!footerActions.waitUntilElementDisplayed(footerActions.footerSection, 3), "Verify as still lazy loading is loading product cards, no footer is displayed");
        AssertFailAndContinue(footerActions.clickBackToTop(), "Verify user scrolling down on search result page starts displaying Back to Top button");
    }
}


