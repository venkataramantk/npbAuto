package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

/**
 * Created by Pooja on 10/07/2018
 */

//@Test(singleThreaded = true)
public class SearchSuggestions extends MobileBaseTest {

    WebDriver mobileDriver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String email, password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH})
    public void verifyLazyLoad(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("Validate Lazy Loading behavior appears and works as expected on Search Result page" +
                "DT-44572");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-42930
        AssertFailAndContinue(mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value")), "When the user enter Shirts in the search bar");
        AssertFailAndContinue(mheaderMenuActions.validateHrefLangTag(),"Verify HREF Lang tag should contain www across all the pages");
        AssertFailAndContinue(msearchResultsPageActions.compareProductsCount(20), "Verify Initially 20 Products displayed");
        msearchResultsPageActions.scrollToBottom();
        //TO DO: Application is to fast to capture lazy load. hence commented
        //AssertFailAndContinue(msearchResultsPageActions.verifyLazyLoad(), "Scroll Down to Bottom and verify Lazy Loading icon is displayed");
        AssertFailAndContinue(msearchResultsPageActions.compareProductsCount(40), "Verify Initially 40 Products displayed");
        //DT-43661
        AssertFailAndContinue(mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value")), "When the user enter Shirts in the search bar");
        AssertFailAndContinue(mcategoryDetailsPageAction.sortByOptionPlp("High to Low"), "Select High to Low Sort Option and Verify the sorted Result");
        AssertFailAndContinue(msearchResultsPageActions.compareProductsCount(20), "Verify Initially 20 Products displayed");
        msearchResultsPageActions.scrollToBottom();
        //TO DO: Application is to fast to capture lazy load. hence commented
        //AssertFailAndContinue(msearchResultsPageActions.verifyLazyLoad(), "Scroll Down to Bottom and verify Lazy Loading icon is displayed");
        AssertFailAndContinue(msearchResultsPageActions.compareProductsCount(40), "Verify Initially 40 Products displayed");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH})
    public void verifyAllIconsFunctionality(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("1. Verify the Add to Bag functionality on SRP (Guest User/Logged in user). User is able to add product to bag \n" +
                "2.Verify the Add to Favorite functionality on SRP (guest user / logged in user).User is able to add item to whish list\n" +
                "3. Verify the Pick up in store functionality on SRP (Guest user / logged in user). User is able to add BOPIS items to bag");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-38250
        AssertFailAndContinue(mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm", "Value")), "When the user enter Shirts in the search bar");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementsAreDisplayed(mcategoryDetailsPageAction.addToBopisIcon, 20), "Validate bopis icons");
        if(store.equalsIgnoreCase("ca")){
            AssertFailAndContinue(!mcategoryDetailsPageAction.waitUntilElementsAreDisplayed(mcategoryDetailsPageAction.addToBopisIcon, 5), "Validate bopis icons");
        }
        int bagCountBeforeAdd = Integer.parseInt(mheaderMenuActions.getQty());
        String prodName = mcategoryDetailsPageAction.getProdName(1);
        AssertFailAndContinue(msearchResultsPageActions.addProdToBag(mcategoryDetailsPageAction, 1), "Add the Product to Bag and verify the View Bag notification");
        AssertFailAndContinue(mSearchResultsPageActions.waitUntilElementDisplayed(mSearchResultsPageActions.filterBtn), "Verify Add to Bag flip is closed and user is on Product listing page");
        int bagCountAfterAdd = Integer.parseInt(mheaderMenuActions.getQty());
        AssertFailAndContinue(msearchResultsPageActions.compareTwoCount(bagCountAfterAdd, bagCountBeforeAdd), "Verify the Item Added in the Bag");

        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Click Shopping Bag Icon");
        AssertFailAndContinue(mshoppingBagPageActions.isProdPresentByTitle(prodName), "Verify Product Title is displayed in Shopping Cart");
        AssertFailAndContinue(mshoppingBagPageActions.validateProdDetails(), "Verify All the Product Details displayed in the Cart");
        //DT-38252
        AssertFailAndContinue(mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm", "Value")), "When the user enter Shirts in the search bar");
        String favProdName = mcategoryDetailsPageAction.getProdName(1);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(1);
        if (user.equalsIgnoreCase("guest")) {
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        AssertFailAndContinue(mheaderMenuActions.moveToWishListAsReg(panCakePageActions, mobileFavouritesActions), "Move to Favourites Page");
        AssertFailAndContinue(mobileFavouritesActions.verifyProdPresentByTitle(favProdName), "Verify Item added as Favourite is displayed in favourite list");
        //DT-38254
        int bagCountBefore = Integer.parseInt(mheaderMenuActions.getQty());
        AssertFailAndContinue(addBopisItemToBag(getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm", "Value")), "Add a bopis product to bag from PLP,  Verify Add to bag notification is displayed");
        int bagCountAfter = Integer.parseInt(mheaderMenuActions.getQty());
        AssertFailAndContinue(msearchResultsPageActions.compareTwoCount(bagCountAfter, bagCountBefore), "Verify the Item Added in the Bag");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH})
    public void verifySrpResults(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("Verify if maximum of 8 suggestions are displaying which is comprised of :\n" +
                "Promoted Suggestions + Keywords.");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-42391
        AssertFailAndContinue(mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getmobileDT2CellValueBySheetRowAndColumn("Search", "Unisex", "Value")), "When the user enters Unisex in the search bar");

        AssertFailAndContinue(msearchResultsPageActions.compareResultForKeyword(getmobileDT2CellValueBySheetRowAndColumn("Search", "Unisex", "Value")), "Verify search Results Page displayed only the Unisex Products");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickProductByPosition(1), "click on Product and move to PDP");
        AssertFailAndContinue(msearchResultsPageActions.waitUntilElementDisplayed(mproductDetailsPageActions.backToResultsLink, 10), "Verify Back to Result Link is displayed as Breadcrumb");
        AssertFailAndContinue(msearchResultsPageActions.verifyElementNotDisplayed(mproductDetailsPageActions.BreadCrumb, 10), "Verify Breadcrumbs not displayed");
        //DT-43035
        AssertFailAndContinue(mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Attribute")), "When the user enters Skinny in the search bar");
        AssertFailAndContinue(msearchResultsPageActions.compareResultForKeyword(getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Attribute")), "Verify search Results Page displayed only the Skinny Products");
        //DT-42946
        AssertFailAndContinue(mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getmobileDT2CellValueBySheetRowAndColumn("Search", "ProductName", "Value")), "When the user enters Product Name in the search bar");
        AssertFailAndContinue(msearchResultsPageActions.waitUntilElementDisplayed(mproductDetailsPageActions.addToBag), "Verify for Single Item, Application landed to PDP");
        //DT-43665
        AssertFailAndContinue(mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value")), "When the user enters shirts in the search bar");
        AssertFailAndContinue(msearchResultsPageActions.waitUntilElementsAreDisplayed(msearchResultsPageActions.starRating, 5), "Verify Star Ratings displayed on SRP");
        //DT-42948
        AssertFailAndContinue(mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getmobileDT2CellValueBySheetRowAndColumn("Search", "InvalidSearch", "Value")), "When the user enters shrt in the search bar");
        AssertFailAndContinue(msearchResultsPageActions.clickDidYouMeanResultLink(), "Click on the Link with Did you mean Text");
        AssertFailAndContinue(msearchResultsPageActions.compareResultForKeyword("short"), "Verify search Results Page displayed only the Products containing Short in Product Title");
        //DT-43744
        String currentURl = mobileDriver.getCurrentUrl();
        mobileDriver.navigate().to(currentURl + "asdfassffs");
        AssertFailAndContinue(msearchResultsPageActions.waitUntilElementDisplayed(msearchResultsPageActions.errorPagePuppyBox), " navigate to a category page and append the URL with some random characters like 'abdcagdsva'\n" + "  and hit that url and verify error code 404 and message as Page Not Found");
        AssertFailAndContinue(msearchResultsPageActions.waitUntilElementDisplayed(mproductDetailsPageActions.recommendationSection, 10), "Verify Recommendation Section is displayed at the bottom");

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "toops");

        AssertFailAndContinue(mheaderMenuActions.verifyDidYouMean(), "Search with mis-spelled word verify user is landed Null search results page and verify DID you mean is displayed");
        AssertFailAndContinue(mheaderMenuActions.clickDidYouMeantText(mcategoryDetailsPageAction), "Click on Did you mean text and verify Category details page is displayed");
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {PDP, PROD_REGRESSION})
    public void validateSizesInPDP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify size is automatically not selected for the products offered in multiple sizes, but is only in-stock for one size" +
                "DT-31404, DT-31395 DT-31406");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-31404
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, getmobileDT2CellValueBySheetRowAndColumn("Search", "availMultipleSizeOneInStockUS", "Value"));
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, getmobileDT2CellValueBySheetRowAndColumn("Search", "availMultipleSizeOneInStockCA", "Value"));
        }
        AssertFailAndContinue(mproductDetailsPageActions.getSelectedSize().equalsIgnoreCase("The size is not selected"), "After searching an item with available Multiple Sizes but Only One In Stock,Verify Only Available size button is not selected");

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getmobileDT2CellValueBySheetRowAndColumn("Search", "searchOneSizeItem", "Value"));

        mcategoryDetailsPageAction.clickProductByPosition(1);
        AssertFailAndContinue(mproductDetailsPageActions.getSelectedSize().equalsIgnoreCase("The size is not selected"), "Verify size is automatically selected in PDP for the product offered in one size only.");

    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SEARCH, SMOKE, CHEETAH, PROD_REGRESSION})
    public void searchGiftCard(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user in the " + store + " Store is able to check following\n" +
                "1. when the user enter the term \"Giftcard\" keyword and clicks on magnifying glass icon,Verify if the user is displayed with the giftcard search result page for us\n" +
                "2. Verify search banner for gift card search");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Giftcards");
            AssertFailAndContinue(checkValueInList(msearchResultsPageActions.getAllProductName(), "Gift Card"), "Search with Giftcards and ,Verify if the user is displayed with the giftcard search result page\n");
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Gift card");
            AssertFailAndContinue(checkValueInList(msearchResultsPageActions.getAllProductName(), "Gift Card"), "Search with Gift card and ,Verify if the user is displayed with the giftcard search result page\n");
            AssertFailAndContinue(msearchResultsPageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.searchBanner, 10), "Verify Search Banner is displayed for Giftcard page");
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Gift Cards");
            AssertFailAndContinue(checkValueInList(msearchResultsPageActions.getAllProductName(), "Gift Card"), "Search with Gift Cards and ,Verify if the user is displayed with the giftcard search result page\n");
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Gift");
            AssertFailAndContinue(checkValueInList(msearchResultsPageActions.getAllProductName(), "Gift Card"), "Search with Gift and ,Verify if the user is displayed with the giftcard search result page\n");
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "card");
            AssertFailAndContinue(checkValueInList(msearchResultsPageActions.getAllProductName(), "Gift Card"), "Search with card and ,Verify if the user is displayed with the giftcard search result page\n");
            AssertFailAndContinue(mcategoryDetailsPageAction.validateInEligibleBopisIcons(0), "Verify Bopis Icons are not displayed for gift cards");
            if (user.equalsIgnoreCase("registered")) {
                mcategoryDetailsPageAction.addGCToFav_PLPReg(1);
                AssertFailAndContinue(msearchResultsPageActions.getText(msearchResultsPageActions.errorMessage).contains("Sorry, we are unable to add this item to your favorites."), "click fav icon and verify error message");
            }
        }

        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Gift card");
            AssertFailAndContinue(msearchResultsPageActions.waitUntilElementDisplayed(msearchResultsPageActions.emptySearchLblContent), "When user enters Gift card in search box, Verify that the user redirected to search results not found page.");
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Card");
            AssertFailAndContinue(msearchResultsPageActions.waitUntilElementDisplayed(msearchResultsPageActions.emptySearchLblContent), "When user enters Card in search box, Verify that the user redirected to search results not found page.");
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Giftcard");
            AssertFailAndContinue(msearchResultsPageActions.waitUntilElementDisplayed(msearchResultsPageActions.emptySearchLblContent), "When user enters Giftcard in search box, Verify that the user redirected to search results not found page.");
            mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "gift cards");
            AssertFailAndContinue(msearchResultsPageActions.waitUntilElementDisplayed(msearchResultsPageActions.emptySearchLblContent), "When user enters gift cards in search box, Verify that the user redirected to search results not found page.");
        }
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