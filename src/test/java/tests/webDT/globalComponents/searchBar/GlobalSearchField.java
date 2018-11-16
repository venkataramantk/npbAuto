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
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        //AssertFailAndContinue(headerMenuActions.waitUntilElementDisplayed(headerMenuActions.rotatingBanner), "Verify Rotating banner");
        headerMenuActions.enterTextInSearch("tops");
//         AssertFailAndContinue(baseApi.isSearchAPITriggered(store, "tops"),"The search API is triggered after typing with search term");
        AssertFailAndContinue(searchResultsPageActions.validateSearchBar(searchGhostText.get(0)), "Validated the presence of the Search field, the Ghost text in the field and the Search button on the Header.");
        headerMenuActions.submitSearch(searchResultsPageActions, search_Term.get("Value"));
        AssertFailAndContinue(searchResultsPageActions.searchResultsBySearchTerm(search_Term.get("Value")), "Verify if the Search Result Page contains the given URL");
        AssertFailAndContinue(headerMenuActions.validateHeaderElements(), "Validate Header elements (Header Banner, find a store, departments links, Search field) in Search Results page");
                AssertFailAndContinue(headerMenuActions.searchWithoutKeyWord(searchResultsPageActions, search_Term.get("Value")), "Validated that the user remains on the same page when either clicking on Search button or pressing Enter Key without entering any search term.");
        AssertFailAndContinue(searchResultsPageActions.verifyGhostText_SearchKeyword(search_Term.get("ProductType")), "Auto suggestions and category displaying");
        AssertFailAndContinue(searchResultsPageActions.isIamLookingForDisplayInSuggestions(), "I am looking for category displaying in Auto suggestions box");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction,"sale");
        AssertFailAndContinue(searchResultsPageActions.isListPriceDisplayBelowOfferPriceAndColor("#999999"), "The List price is displaying below the offer price and color of the list price and offer price is less than the list price");

    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE, PROD_REGRESSION})
    public void searchUsingKeyWord(@Optional("US") String store, @Optional("registered") String user) {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" +
                "1. Verify Footer is not displayed when search results are loading" +
                "2. validate Back To Top button\n" +
                "3. Verify search results count");
        Map<String, String> search_Term = excelReaderDT2.getExcelData("Search", "SearchBy");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(headerMenuActions.submitSearch(searchResultsPageActions, search_Term.get("ProductName")), "Performed Search using the product Name and landed on the Search Results page");
        AssertFailAndContinue(headerMenuActions.submitSearch(searchResultsPageActions, search_Term.get("ProductType")), "Performed Search using the product Type and landed on the Search Results page");
        AssertFailAndContinue(headerMenuActions.submitSearch(searchResultsPageActions, search_Term.get("ProductColor")), "Performed Search using the product color and landed on the Search Results page");
        AssertFailAndContinue(headerMenuActions.submitSearch(searchResultsPageActions, search_Term.get("Attribute")), "Performed Search using the attribute and landed on the Search Results page");

        headerMenuActions.submitSearch(searchResultsPageActions, "Tops");
        AssertFailAndContinue(categoryDetailsPageAction.productImages.size() >= 1, "Verify search result page displays 20 products per each lazy load feature");
        AssertFailAndContinue(headerMenuActions.submitSearch(searchResultsPageActions, "Tops"), "Verify if the Search Result Page contains the given URL");
        AssertFailAndContinue(categoryDetailsPageAction.prev_ProdImgButton.size() == categoryDetailsPageAction.prodImgButton_Next.size(), "As a Shopper enter any valid search term and navigate to search result page. Verify whether alternate images are present for products contains more than one image.");

        AssertFailAndContinue(footerActions.validateBackToTop(), "Verify user scrolling down on search result page starts displaying Back to Top button with Back To Top text");
        AssertFailAndContinue(!footerActions.waitUntilElementDisplayed(footerActions.footerSection, 3), "Verify as still lazy loading is loading product cards, no footer is displayed");
        AssertFailAndContinue(footerActions.clickBackToTop(), "Verify user scrolling down on search result page starts displaying Back to Top button");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE, PROD_REGRESSION})
    public void invalidSearch(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" +
                "1. When user starts to type in an invalid search term in 'Search' text field displayed at the top right corner of the header and when user has entered 3 characters of the invalid search term, verify that system does not display search suggestions\n" +
                "2. When user search any keywords which are not in Search catalogue Invalid keyword in search text field, Verify if user is displayed with No match message and Tips for Message Instructions text\n" +
                "3. When user provides <space> (hit space bar a couple of times within the search box) as a search term and hits the return key or the magnifying glass present next to the search text box, Verify that the system does not perform any action.\n" +
                "4. When user enters only \"**, ///, ???\" in search box, Verify that the user redirected to page not found page.");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        headerMenuActions.enterTextInSearch(getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTermInvalid", "Value"));
        AssertFailAndContinue(!headerMenuActions.verifyAutoSuggestion(), "when user starts to type in an invalid search term in 'Search' text field displayed at the top right corner of the header and when user has entered 3 characters of the invalid search term, verify that system does not display search suggestions");

        headerMenuActions.searchWithClick(getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTermInvalid", "Value"));
        AssertFailAndContinue(searchResultsPageActions.validateEmptySearchResults("", getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTermInvalid", "Value")), "when user search any keywords which are not in Search catalogue Invalid keyword in search text field, Verify if user is displayed with \"no result fetched\" message and Tips for Message Instructions text");

        AssertFailAndContinue(headerMenuActions.enterSpaceKeyInSearchAndSubmit(searchResultsPageActions), "when user provides space (hit space bar a couple of times within the search box) as a search term and hits the return key or the magnifying glass present next to the search text box, Verify that the system does not perform any action.");

        headerMenuActions.searchWithClick("**");
        //AssertFailAndContinue(searchResultsPageActions.verifySearchNotFoundPage(), "When user enters only \"**\" in search box, Verify that the user redirected to page not found page.");
        AssertFailAndContinue(searchResultsPageActions.validateEmptySearchResults("", "**"), "when user search any keywords which are not in Search catalogue Invalid keyword in search text field, Verify if user is displayed with \"no result fetched\" message and Tips for Message Instructions text");

        headerMenuActions.searchWithClick("///");
        //AssertFailAndContinue(searchResultsPageActions.verifySearchNotFoundPage(), "When user enters only \"///\" in search box, Verify that the user redirected to page not found page.");
        AssertFailAndContinue(searchResultsPageActions.validateEmptySearchResults("", "///"), "when user search any keywords which are not in Search catalogue Invalid keyword in search text field, Verify if user is displayed with \"no result fetched\" message and Tips for Message Instructions text");

        headerMenuActions.searchWithClick("???");
        //AssertFailAndContinue(searchResultsPageActions.verifySearchNotFoundPage(), "When user enters only \"???\" in search box, Verify that the user redirected to page not found page.");
        AssertFailAndContinue(searchResultsPageActions.validateEmptySearchResults("", "???"), "when user search any keywords which are not in Search catalogue Invalid keyword in search text field, Verify if user is displayed with \"no result fetched\" message and Tips for Message Instructions text");

    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE, CHEETAH, PROD_REGRESSION})
    public void emptySearchPageValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" +
                "1. When user navigates to a null search results page, subsequently enters special characters on the Search bar right below \"would you like to try another search?\", Verify that the user redirected to 404 page.\n" +
                "2. Enter a invalid search term.Verify if the user is not displayed with suggestions.\n" +
                "3. Verify Global Nav banner");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        headerMenuActions.searchWithClick("Jeep");
        searchResultsPageActions.addStepDescription("UN-988");
        searchResultsPageActions.searchfromEmptySearch("///");
        //AssertFailAndContinue(searchResultsPageActions.verifySearchNotFoundPage(), "When user enters only \"///\" in search box, Verify that the user redirected to page not found page.");
        AssertFailAndContinue(searchResultsPageActions.validateEmptySearchResults("", "///"), "when user search any keywords which are not in Search catalogue Invalid keyword in search text field, Verify if user is displayed with \"no result fetched\" message and Tips for Message Instructions text");

        // TODO : Need to enable after configuring the GLobal Navigation banner.
        //AssertFailAndContinue(headerMenuActions.validateGlobalNavBanner(), "Verify Global navigation bar in empty search results page");

        headerMenuActions.searchWithClick("Jeep");
        searchResultsPageActions.searchfromEmptySearch("???");
        //AssertFailAndContinue(searchResultsPageActions.verifySearchNotFoundPage(), "When user enters only \"???\" in search box, Verify that the user redirected to page not found page.");
        AssertFailAndContinue(searchResultsPageActions.validateEmptySearchResults("", "???"), "when user search any keywords which are not in Search catalogue Invalid keyword in search text field, Verify if user is displayed with \"no result fetched\" message and Tips for Message Instructions text");

        headerMenuActions.searchWithClick("Jeep");
        AssertFailAndContinue(!headerMenuActions.verifyAutoSuggestion(), "when the user search using the keyword that is NOT associate to the \"sales catalogue\"in search field, verify that the user is NOT displayed with that keyword in search result suggestion bar.");
    }

    @Test(dataProvider = dataProviderName, priority = 4, groups = {GLOBALCOMPONENT, SEARCH, SMOKE, "DT-32080", PROD_REGRESSION})
    public void searchFilter(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "1. Click on Search box and search for any valid keyword \n Filter works fine (There is no change in Desktop Filter)");

        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "tops");
        String appliedFilter = categoryDetailsPageAction.applyCategoryFilter(1);
        AssertFailAndContinue(categoryDetailsPageAction.verifyCount(), "Verify count in top");
        categoryDetailsPageAction.applySizeFilter(1);
        categoryDetailsPageAction.applyColorFilter(1);
        AssertFailAndContinue(categoryDetailsPageAction.verifyAppliedFiltersCount(3), "Verify Catgeory,Size and Color Filters are applied");
        AssertFailAndContinue(categoryDetailsPageAction.clearFilter(appliedFilter), "Clear the Category Filter");
        AssertFailAndContinue(categoryDetailsPageAction.verifyAppliedFiltersCount(2), "Verify Size and Color Filters are applied");
    }


    @Test(priority = 5, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE,PROD_REGRESSION})
    public void searchOneSizeItem(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " + ", Verify size automatically selected for the searched products offered in One size only");

        //DT-31407 & DT-31408
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        headerMenuActions.searchWithClick(getDT2TestingCellValueBySheetRowAndColumn("Search", "searchOneSizeItem", "Value"));
        AssertFailAndContinue(categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, 4), "when registered user clicks on Add to Bag icon on Product Tile");
        categoryDetailsPageAction.selectRandomColor();
        AssertFailAndContinue(searchResultsPageActions.waitUntilElementDisplayed(productCardViewActions.alreadySelectedOneSizeBtnonFlip, 3), "Verify One Size Button is already Selected");
        searchResultsPageActions.addStepDescription("DT-43036");
        AssertFailAndContinue(headerMenuActions.enterTextInSearch("TO"),"Verify the search suggestion is displayed for Type Ahead word 2");
        AssertFailAndContinue(headerMenuActions.checkSuggestionNotDisplayedInSearch("T"),"Verify the search suggestion is not displayed for Type Ahead word 1");

    }


    @Test(priority = 6, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE,PROD_REGRESSION})
    public void searchOneSizeSingleItem(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " + ", Verify size automatically selected for the searched products offered in One size only");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        //DT-32776
        headerMenuActions.enterTextInSearch(getDT2TestingCellValueBySheetRowAndColumn("Search", "keywordForSearchSuggestion", "Value"));
        AssertFailAndContinue(searchResultsPageActions.verifyNoDuplicatesInSearchSuggestionList(), "Verify Only Unique values displayed in Serach Suggestion List");
        //DT-31405 & DT-31406
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction,getDT2TestingCellValueBySheetRowAndColumn("Search", "searchSingleOneSizeItem", "AltValue"));
        categoryDetailsPageAction.clickRandomProductByImage(productDetailsPageActions);
        AssertFailAndContinue(productDetailsPageActions.waitUntilElementDisplayed(productDetailsPageActions.alreadySelectedOneSizeBtn, 3), "Verify One Size Button is already Selected");
    }

    @Test(priority = 7, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE, REGISTEREDONLY,PROD_REGRESSION,FAVORITES})
    public void searchOneSizeItemFromFav(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " + ", Verify size automatically selected for the searched products offered in One size only");
        //DT-31402
        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        headerMenuActions.searchWithClick(getDT2TestingCellValueBySheetRowAndColumn("Search", "searchOneSizeItem", "Value"));
        AssertFailAndContinue(categoryDetailsPageAction.addProdToFav(searchResultsPageActions, 1), "As a registered User, Click on Add to Favourite icon on Product Tile on PLP ");
        String favItemName = categoryDetailsPageAction.getProductTitle(1);
        AssertFailAndContinue(headerMenuActions.clickWishListAsRegistered(favoritePageActions), "As a registered User, Click on Wishlist icon to navigate to Wishlist Page ");
        AssertFailAndContinue(favoritePageActions.clickProdPresentByTitle(productDetailsPageActions, favItemName), "Click on Product in the wishlist and move to PDP");
        AssertFailAndContinue(searchResultsPageActions.scrollDownUntilElementDisplayed(productDetailsPageActions.alreadySelectedOneSizeBtn), "Verify One Size Button is already Selected");
    }
}


