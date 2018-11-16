package tests.webDT.globalComponents.searchBar;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

public class SearchSuggestions extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg;
    String password;
    String env;

    @Parameters({storeXml,usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store,@Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        env = EnvironmentConfig.getEnvironmentProfile();
        driver.get(EnvironmentConfig.getApplicationUrl());
        if(store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            if(user.equalsIgnoreCase("registered")) {
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
                password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
            }

        }
        else if(store.equalsIgnoreCase("CA")){
            footerActions.changeCountryAndLanguage("CA","English");
            if(user.equalsIgnoreCase("registered")) {
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
                password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
            }

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
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE, CHEETAH,PROD_REGRESSION})
    public void searchSuggestions(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" +
                "1. If user is not able to view the 'View all results' link within the Auto suggestions search drop down even when user enters valid search term in the Search text box\n" +
                "2. when the user search using the keyword that is NOT associate to the \"sales catalogue\"in search field, verify that the user is NOT displayed with that keyword in search result suggestion bar.\n" +
                "3. Verify if user is successfully navigated to the Category Landing page on clicking the searched term category from the \"Category\" section in the search auto suggest drop down. andCategory Page is displayed correctly\n" +
                "4. Verify Lazy loading spinner while sorting\n" +
                "5. Verify Global Marketing Banner is visible below Root(L1) category and search results page");
     // TODO : Need to check
        //  AssertFailAndContinue(headerMenuActions.validateGlobalNavBanner(), "Verify Global navigation bar is visible below L1 category in Home Page");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        List<String> search_Term = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        String search_Term1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchByName", "Value");

        headerMenuActions.enterTextInSearch("Shorts");
        AssertFailAndContinue(!headerMenuActions.waitUntilElementDisplayed(headerMenuActions.viewall, 3), "If user is not able to view the 'View all results' link within the Auto suggestions search drop down even when user enters valid search term in the Search text box");

        AssertFailAndContinue(headerMenuActions.selectRandomCategoryFromSearchSuggestions(), "Verify if user is successfully navigated to the Category Landing page on clicking the searched term category from the \"Category\" section in the search auto suggest drop down.");

        AssertFailAndContinue(headerMenuActions.searchAndCheckText("Shorts"),"Verify the search API contains the search term");
        AssertFailAndContinue(headerMenuActions.getText(headerMenuActions.categoryList.get(0)).contains(search_Term.get(0)), "Verify if the user is displayed with Girl suggestion dropdown with list of category links,when the user enter the " + search_Term.get(0) + " department in the search bar");

        // TODO : Need to enable after configuring the GLobal Navigation banner.
        //AssertFailAndContinue(headerMenuActions.validateGlobalNavBanner(), "Verify Global navigation bar is visible below L1 category in search results Page");
        headerMenuActions.compareCatFromSearchSuggestions();
        headerMenuActions.enterTextInSearch(search_Term.get(1));
        AssertFailAndContinue(headerMenuActions.getText(headerMenuActions.categoryList.get(0)).contains(search_Term.get(1)), "Verify if the user is displayed with Toddler Girl suggestion dropdown with list of category links,when the user enter the " + search_Term.get(1) + " department in the search bar");

        headerMenuActions.enterTextInSearch(search_Term.get(2));
        AssertFailAndContinue(headerMenuActions.getText(headerMenuActions.categoryList.get(0)).contains(search_Term.get(2)), "Verify if the user is displayed with Boy suggestion dropdown with list of category links,when the user enter the " + search_Term.get(2) + " department in the search bar");

        headerMenuActions.enterTextInSearch(search_Term.get(3));
        AssertFailAndContinue(headerMenuActions.getText(headerMenuActions.categoryList.get(0)).contains(search_Term.get(3)), "Verify if the user is displayed with Toddler BOy suggestion dropdown with list of category links,when the user enter the " + search_Term.get(3) + " department in the search bar");

        headerMenuActions.enterTextInSearch("Bab"); // Searching Bab instead of Baby
        AssertFailAndContinue(headerMenuActions.getText(headerMenuActions.categoryList.get(0)).contains(search_Term.get(4)), "Verify if the user is displayed with Baby suggestion dropdown with list of category links,when the user enter the " + search_Term.get(4) + " department in the search bar");

        headerMenuActions.enterTextInSearch(search_Term.get(5));
        AssertFailAndContinue(headerMenuActions.getText(headerMenuActions.categoryList.get(0)).contains(search_Term.get(5)), "Verify if the user is displayed with Shoes suggestion dropdown with list of category links,when the user enter the " + search_Term.get(5) + " department in the search bar");

        searchResultsPageActions.addStepDescription("UN-901");
        headerMenuActions.enterTextInSearch(search_Term.get(6));
        AssertFailAndContinue(headerMenuActions.getText(headerMenuActions.categoryList.get(0)).contains(search_Term.get(6)), "Verify if the user is displayed with Accessories suggestion dropdown with list of category links,when the user enter the " + search_Term.get(6) + " department in the search bar");
        //DT-42946
        AssertWarnAndContinue(headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,search_Term1),"Check whether the user is redirected to PDP page by searching the Name");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE,PROD_REGRESSION})
    public void departmentSearch(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" +
                "1. when the user enter the department in the search bar and click on \"Search\" icon,Verify if the user is displayed with search result page\n");
        List<String> search_Term = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        headerMenuActions.searchWithClick(search_Term.get(0));
        //searchResultsPageActions.addStepDescription("UN-562");
        //AssertFailAndContinue(searchResultsPageActions.verifyContentLandingPage(search_Term.get(0)), "When the user enter the " + search_Term.get(0) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(0) + " search result page");
        AssertFailAndContinue(searchResultsPageActions.youSearchedForText(search_Term.get(0)), "When the user enter the " + search_Term.get(0) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(0) + " search result page");

        headerMenuActions.searchWithClick(search_Term.get(1));
        //AssertFailAndContinue(searchResultsPageActions.verifyContentLandingPage(search_Term.get(1)), "When the user enter the " + search_Term.get(1) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(1) + " search result page");
        AssertFailAndContinue(searchResultsPageActions.youSearchedForText(search_Term.get(1)), "When the user enter the " + search_Term.get(1) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(1) + " search result page");

        headerMenuActions.searchWithClick(search_Term.get(2));
        //AssertFailAndContinue(searchResultsPageActions.verifyContentLandingPage(search_Term.get(2)), "When the user enter the " + search_Term.get(2) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(2) + " search result page");
        AssertFailAndContinue(searchResultsPageActions.youSearchedForText(search_Term.get(2)), "When the user enter the " + search_Term.get(2) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(1) + " search result page");

        headerMenuActions.searchWithClick(search_Term.get(3));
        //AssertFailAndContinue(searchResultsPageActions.verifyContentLandingPage(search_Term.get(3)), "When the user enter the " + search_Term.get(3) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(3) + " search result page");
        AssertFailAndContinue(searchResultsPageActions.youSearchedForText(search_Term.get(3)), "When the user enter the " + search_Term.get(3) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(1) + " search result page");

        headerMenuActions.searchWithClick(search_Term.get(4));
        //AssertFailAndContinue(searchResultsPageActions.verifyContentLandingPage(search_Term.get(4)), "When the user enter the " + search_Term.get(4) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(4) + " search result page");
        AssertFailAndContinue(searchResultsPageActions.youSearchedForText(search_Term.get(4)), "When the user enter the " + search_Term.get(2) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(1) + " search result page");


        headerMenuActions.searchWithClick(search_Term.get(5));
        //AssertFailAndContinue(searchResultsPageActions.verifyContentLandingPage(search_Term.get(5)), "When the user enter the " + search_Term.get(5) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(5) + " search result page");
        AssertFailAndContinue(searchResultsPageActions.youSearchedForText(search_Term.get(5)), "When the user enter the " + search_Term.get(2) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(1) + " search result page");


        headerMenuActions.searchWithClick("Clearance");
        //AssertFailAndContinue(searchResultsPageActions.verifyContentLandingPage("Clearance"), "When the user enter the Clearance department in the search bar and click on \"Search\" icon,Verify if the user is displayed with Clearance search result page");
        AssertFailAndContinue(searchResultsPageActions.youSearchedForText("Clearance"), "When the user enter the " + search_Term.get(2) + " department in the search bar and click on \"Search\" icon,Verify if the user is displayed with " + search_Term.get(1) + " search result page");


        headerMenuActions.searchWithClick("Denim");
        AssertFailAndContinue(searchResultsPageActions.youSearchedForText("Denim"), ",when the user enter the \"L2 category\" links  in the search bar and click on \"Search\" icon,Verify if the user is displayed with \"You searched for <L2 category name>\n" +
                "\" search result page");

        headerMenuActions.searchWithClick(getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Attribute"));
        AssertFailAndContinue(searchResultsPageActions.youSearchedForText(getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Attribute")), ",when the user enter the \"L3 category\" links  in the search bar and click on \"Search\" icon,Verify if the user is displayed with \"You searched for <L3 category name> search result page");

        headerMenuActions.enterTextInSearch("Toddler Shoes");
        AssertFailAndContinue(headerMenuActions.getText(headerMenuActions.suggestion).contains("Shoes"), "when user search keywords like \"Night Shoes\", Verify that Place shop categories are NOT displayed as suggestions within the category section of the search auto-suggesstion drop down but if the category is having either \"Night\" or \"Shoes\", verify that related category is displayed in category auto suggestion drop down. \n");

        AssertFailAndContinue(searchResultsPageActions.searchSuggestionCheck(),"Verify the suggestions are not displayed for single character");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "toops");

        AssertFailAndContinue(headerMenuActions.verifyDidYouMean(), "Search with mis-spelled word verify user is landed Null search results page and verify DID you mean is displayed");
        AssertFailAndContinue(headerMenuActions.clickDidYouMeantText(categoryDetailsPageAction, productDetailsPageActions), "Click on Did you mean text and verify Category details page is displayed");

    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE, CHEETAH,PROD_REGRESSION})
    public void searchGiftCard(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" +
                "1. when the user enter the term \"Giftcard\" keyword and clicks on magnifying glass icon,Verify if the user is displayed with the giftcard search result page for us\n" +
                "2. Verify search banner for gift card search");

        if(!env.equalsIgnoreCase("prodstaging")) {
            if (user.equalsIgnoreCase("registered")) {
                clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
            }
            if (store.equalsIgnoreCase("US")) {
                headerMenuActions.searchWithClick("Giftcards");
                AssertFailAndContinue(checkValueInList(searchResultsPageActions.getAllProductName(), "Gift Card"), "Search with Giftcards and ,Verify if the user is displayed with the giftcard search result page\n");
                headerMenuActions.searchWithClick("Gift card");
                AssertFailAndContinue(checkValueInList(searchResultsPageActions.getAllProductName(), "Gift Card"), "Search with Gift card and ,Verify if the user is displayed with the giftcard search result page\n");
                AssertWarnAndContinue(searchResultsPageActions.waitUntilElementDisplayed(categoryDetailsPageAction.searchBanner, 10), "Verify Search Banner is displayed for Giftcard page");
                headerMenuActions.searchWithClick("Gift Cards");
                AssertFailAndContinue(checkValueInList(searchResultsPageActions.getAllProductName(), "Gift Card"), "Search with Gift Cards and ,Verify if the user is displayed with the giftcard search result page\n");
                headerMenuActions.searchWithClick("Gift");
                AssertFailAndContinue(checkValueInList(searchResultsPageActions.getAllProductName(), "Gift Card"), "Search with Gift and ,Verify if the user is displayed with the giftcard search result page\n");
                headerMenuActions.searchWithClick("card");
                AssertFailAndContinue(checkValueInList(searchResultsPageActions.getAllProductName(), "Gift Card"), "Search with card and ,Verify if the user is displayed with the giftcard search result page\n");
                AssertFailAndContinue(categoryDetailsPageAction.validateInEligibleBopisIcons(0), "Verify Bopis Icons are not displayed for gift cards");
                if (user.equalsIgnoreCase("registered")) {
                    categoryDetailsPageAction.addGCToFav_PLPReg(1);
                    AssertFailAndContinue(searchResultsPageActions.getText(searchResultsPageActions.errorMessage).contains("Sorry, we are unable to add this item to your favorites."), "click fav icon and verify error message");
                }

            }

            if (store.equalsIgnoreCase("CA")) {
                headerMenuActions.searchWithClick("Gift card");
                AssertFailAndContinue(searchResultsPageActions.waitUntilElementDisplayed(searchResultsPageActions.emptySearchLblContent), "When user enters Gift card in search box, Verify that the user redirected to search results not found page.");
                headerMenuActions.searchWithClick("Card");
                AssertFailAndContinue(searchResultsPageActions.waitUntilElementDisplayed(searchResultsPageActions.emptySearchLblContent), "When user enters Card in search box, Verify that the user redirected to search results not found page.");
                headerMenuActions.searchWithClick("Giftcard");
                AssertFailAndContinue(searchResultsPageActions.waitUntilElementDisplayed(searchResultsPageActions.emptySearchLblContent), "When user enters Giftcard in search box, Verify that the user redirected to search results not found page.");
                headerMenuActions.searchWithClick("gift cards");
                AssertFailAndContinue(searchResultsPageActions.waitUntilElementDisplayed(searchResultsPageActions.emptySearchLblContent), "When user enters gift cards in search box, Verify that the user redirected to search results not found page.");
            }
        }
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE,PROD_REGRESSION})
    public void validateCategoryFilter(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" +
                "1.  when the user is in search results page,selects any department link in the filtering by section and on clicking the category drop down in the filter by section Verify if user is displayed with appropriate L2 category links ensure that L2 categories links are not repeating\n" +
                "2.  Verify user clicking on the Category filter, displays Category dropdown filter");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        List<String> search_Term = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.searchWithClick("Tops");
        AssertFailAndContinue(categoryDetailsPageAction.verifyCategoryFilter(), "when the user is in search results page,selects " + search_Term.get(0) + " department link in the filtering by section and on clicking the category drop down in the filter by section Verify if user is displayed with appropriate L2 category links ensure that L2 categories links are not repeating");
    }

}
