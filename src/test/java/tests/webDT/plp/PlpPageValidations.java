package tests.webDT.plp;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.EnvironmentConfig;

public class PlpPageValidations extends BaseTest {
    WebDriver driver;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
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
            headerMenuActions.addStateCookie("MB");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(usersXml)
    @Test(priority = 1, groups = {PLP, CHEETAH, BOPIS, PROD_REGRESSION})
    public void plpBopisTests(@Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in us store, " +
                "1. Verify user clicking on Pick up in Store icon displays BOPIS model and user search and add product to bag from BOPIS\n" +
                "2. Verify BOPIS promo content in PDP page");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "shirts");
        AssertFailAndContinue(categoryDetailsPageAction.validateBopisIcons(bopisOverlayActions), "Click on bopis icon on any available item from plp verify bopis model is displayed");
        bopisOverlayActions.closeBopisOverlayModal();
        String validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, validUPCNumber);
        //Jagadeesh will have discussion With valli as Bopis Promo not enabled
        // TODO : Need to enable BOPIS Promo content after checking with functional team
        //AssertFailAndContinue(productDetailsPageActions.verifyBopisPromo(), "Verify BOPIS promo content slot is available and is visible above Find In Store CTA");
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {PLP,PROD_REGRESSION})
    public void lazyLoadTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("CANADA");
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "1. Verify search result page displays lazy load feature of loading product cards\n" +
                "2. Verify as still lazy loading is loading product cards, no footer is displayed\n" +
                "3. Verify search result page displays 18 products per each lazy load feature\n" +
                "4. Verify user scrolling down on search result page starts displaying Back to Top button\n" +
                "5. Verify user clicking Back to Top navigates user back to top of search result page");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "denims");
        AssertFailAndContinue(categoryDetailsPageAction.getProductsCount() == 20, "Verify search result page displays 20 products initially");
        //Product Loading is very fast and Lazy Load verification failing
        //  AssertFailAndContinue(categoryDetailsPageAction.verifyLazyLoad(), "Verify search result page displays lazy load feature of loading product cards");
        AssertFailAndContinue(!footerActions.waitUntilElementDisplayed(footerActions.footerSection, 3), "Verify as still lazy loading is loading product cards, no footer is displayed");
        //      AssertFailAndContinue(categoryDetailsPageAction.getProductsCount() == 40, "Verify search result page displays 20 products per each lazy load feature");
        AssertFailAndContinue(footerActions.validateBackToTop(), "Verify user scrolling down on search result page starts displaying Back to Top button");
        AssertFailAndContinue(footerActions.clickBackToTop(), "Verify user clicking Back to Top navigates user back to top of search result page");
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {PLP,PROD_REGRESSION})
    public void verifyFilterAppliedOnPlp(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "Browse | Default Size and Color Selection in Add to Cart, if mom applies a filter, to reduce clicks to add to cart");
        //DT_32730
        AssertFailAndContinue(headerMenuActions.clickTopsUnderGirlCategory(categoryDetailsPageAction), "Click Tops in Girl");
        AssertFailAndContinue(categoryDetailsPageAction.applySizeFilter(1), "Apply Single Size Filter");
        AssertFailAndContinue(categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, 1), "User hovers mouse over Product Tile and  clicks on Add to Bag icon on Product Tile");
        AssertFailAndContinue(categoryDetailsPageAction.verifyFilteredSizeSelectedOnProdCard(), "Verify Applied Filter is default selected on Bag Flip");
        //categoryDetailsPageAction.clickCloseProdCardView();
        //AssertFailAndContinue(categoryDetailsPageAction.removeFilters(), "Remove the applied filter");
        AssertFailAndContinue(headerMenuActions.clickShoesUnderBoyCategory(categoryDetailsPageAction), "Click Tops under Boys");
        AssertFailAndContinue(categoryDetailsPageAction.applyColorFilterByTitle("BLACK"), "Apply Only color Filter");
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementsAreDisplayed(categoryDetailsPageAction.itemContainers, 10), "Wait for page to load");
        AssertFailAndContinue(categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, 1), "User hovers mouse over Product Tile and  clicks on Add to Bag icon on Product Tile");
        AssertFailAndContinue(categoryDetailsPageAction.verifyColorSelectedOnProdCard("BLACK"), "Verify Applied Filter is default selected on Bag Flip");
        //categoryDetailsPageAction.clickCloseProdCardView();
        AssertFailAndContinue(headerMenuActions.clickShoesUnderBoyCategory(categoryDetailsPageAction), "Click Shoes Under boys");
        AssertFailAndContinue(categoryDetailsPageAction.applyColorFilterByTitle("BLACK"), "Apply Only color Filter");
        AssertFailAndContinue(categoryDetailsPageAction.applySizeFilter(2), "Apply Multiple Size Filter");
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementsAreDisplayed(categoryDetailsPageAction.itemContainers, 10), "Wait for page to load");
        AssertFailAndContinue(categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, 1), "User hovers mouse over Product Tile and  clicks on Add to Bag icon on Product Tile");
        AssertFailAndContinue(categoryDetailsPageAction.verifyColorSelectedOnProdCard("BLACK"), "Verify Applied Filter is default selected on Bag Flip");
        AssertFailAndContinue(productDetailsPageActions.verifyElementNotDisplayed(categoryDetailsPageAction.alreadySelectedSizeBtn_value), "If Multiple Size and single color filter is applied,Size should not be selected in the Bag flip");
       // categoryDetailsPageAction.clickCloseProdCardView();
      //  AssertFailAndContinue(categoryDetailsPageAction.clearFilter("WHITE"), "Clear Color Filter");
        AssertFailAndContinue(headerMenuActions.clickShoesUnderBoyCategory(categoryDetailsPageAction), "Click Shoes Under Boys");
        AssertFailAndContinue(categoryDetailsPageAction.applySizeFilter(2), "Apply Multiple Size Filter");
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementsAreDisplayed(categoryDetailsPageAction.itemContainers, 10), "Wait for page to load");
        AssertFailAndContinue(categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, 1), "User hovers mouse over Product Tile and  clicks on Add to Bag icon on Product Tile");
        AssertFailAndContinue(categoryDetailsPageAction.verifyElementNotDisplayed(categoryDetailsPageAction.alreadySelectedSizeBtn_value), "If Multiple Size filter is applied,Size should not be selected in the Bag flip");
       // categoryDetailsPageAction.clickCloseProdCardView();
       // AssertFailAndContinue(categoryDetailsPageAction.removeFilters(), "Remove the applied filter");
        AssertFailAndContinue(headerMenuActions.clickShoesUnderBoyCategory(categoryDetailsPageAction), "Click Shoes in Girl");
        AssertFailAndContinue(categoryDetailsPageAction.applyColorFilterByTitle("BLACK"), "Apply Only color Filter");
        AssertFailAndContinue(categoryDetailsPageAction.applySizeFilter(1), "Apply Only Size Filter");
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementsAreDisplayed(categoryDetailsPageAction.itemContainers, 10), "Wait for page to load");
        AssertFailAndContinue(categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, 1), "User hovers mouse over Product Tile and  clicks on Add to Bag icon on Product Tile");
        AssertFailAndContinue(categoryDetailsPageAction.verifyFilteredSizeSelectedOnProdCard(), "Verify Applied Filter is default selected on Bag Flip");
        AssertFailAndContinue(categoryDetailsPageAction.verifyColorSelectedOnProdCard("BLACK"), "Verify Applied Filter is default selected on Bag Flip");
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {PLP, BOPIS,PROD_REGRESSION})
    public void verifyFilterAppliedOnBopis(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "Browse | Default Size and Color Selection in Add to Cart, if mom applies a filter, to reduce clicks to add to cart");
        //DT_32730
        //UN-570
        AssertFailAndContinue(headerMenuActions.clickTopsUnderToddlerGirlCategory(categoryDetailsPageAction), "Click Shoes under toddler Girl");
        AssertFailAndContinue(categoryDetailsPageAction.applySizeFilter(1), "Apply Single Size Filter");
        AssertFailAndContinue(bopisOverlayActions.verifyFilteredSizeDefaultSelected(searchResultsPageActions, bopisOverlayActions, categoryDetailsPageAction), "Verify on Bopis Modal Filtered Size is default selected and close the Bopis Modal");
        AssertFailAndContinue(categoryDetailsPageAction.removeFilters(), "Remove the applied filter");
        AssertFailAndContinue(categoryDetailsPageAction.applyColorFilterByTitle("WHITE"), "Apply Only color Filter");
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementsAreDisplayed(categoryDetailsPageAction.itemContainers, 10), "Wait for page to load");
        AssertFailAndContinue(bopisOverlayActions.verifyWhiteColorDefaultSelected(searchResultsPageActions, bopisOverlayActions, categoryDetailsPageAction), "Verify on Bopis Modal Filtered Size is default selected and close the Bopis Modal");
        AssertFailAndContinue(categoryDetailsPageAction.applySizeFilter(2), "Apply Multiple Size Filter");
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementsAreDisplayed(categoryDetailsPageAction.itemContainers, 10), "Wait for page to load");
        AssertFailAndContinue(bopisOverlayActions.verifyWhiteColorDefaultSelected(searchResultsPageActions, bopisOverlayActions, categoryDetailsPageAction), "Verify on Bopis Modal Filtered Color is default selected and close the Bopis Modal");
        AssertFailAndContinue(categoryDetailsPageAction.validateBopisIcons(bopisOverlayActions), "Click on Pick up In Store Icon");
        AssertFailAndContinue(productDetailsPageActions.verifyElementNotDisplayed(bopisOverlayActions.defaultSizeInBopis), "If Multiple Size filter is applied,Size should not be selected in the Bopis");
        AssertFailAndContinue(bopisOverlayActions.closeBopisOverlayModal(), "Close Bopis Overlay");
        AssertFailAndContinue(categoryDetailsPageAction.clearFilter("WHITE"), "Clear Color Filter");
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementsAreDisplayed(categoryDetailsPageAction.itemContainers, 10), "Wait for page to load");
        AssertFailAndContinue(categoryDetailsPageAction.validateBopisIcons(bopisOverlayActions), "Click on Pick up In Store Icon");
        AssertFailAndContinue(productDetailsPageActions.verifyElementNotDisplayed(bopisOverlayActions.defaultSizeInBopis), "If Multiple Size filter is applied,Size should not be selected in the Bopis");
        AssertFailAndContinue(bopisOverlayActions.closeBopisOverlayModal(), "Close Bopis Overlay");
        AssertFailAndContinue(categoryDetailsPageAction.removeFilters(), "Remove the applied filter");
        AssertFailAndContinue(categoryDetailsPageAction.applyColorFilterByTitle("WHITE"), "Apply Only color Filter");
        AssertFailAndContinue(categoryDetailsPageAction.applySizeFilter(1), "Apply Only Size Filter");
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementsAreDisplayed(categoryDetailsPageAction.itemContainers, 10), "Wait for page to load");
        AssertFailAndContinue(bopisOverlayActions.verifyFilteredSizeDefaultSelected(searchResultsPageActions, bopisOverlayActions, categoryDetailsPageAction), "Verify on Bopis Modal Filtered Size is default selected and close the Bopis Modal");
        AssertFailAndContinue(bopisOverlayActions.verifyWhiteColorDefaultSelected(searchResultsPageActions, bopisOverlayActions, categoryDetailsPageAction), "Verify on Bopis Modal Filtered Size is default selected and close the Bopis Modal");
    }
}
