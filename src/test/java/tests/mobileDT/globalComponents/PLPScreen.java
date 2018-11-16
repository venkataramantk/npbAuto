package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

import java.util.List;

//DT-386
//@Test(singleThreaded = true)
public class PLPScreen extends MobileBaseTest {


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
                createAccount(rowInExcelUS, email);
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {PLP})
    public void filterValidations(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify filter in PLP screen");
        //DT-2192 to DT-2202s
        //DT-43749
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Shirts");

        AssertFailAndContinue(msearchResultsPageActions.isDisplayed(msearchResultsPageActions.filterBtn), "Verify Filter button is displayed");
        msearchResultsPageActions.click(msearchResultsPageActions.filterBtn);

        AssertFailAndContinue(msearchResultsPageActions.isDisplayed(mcategoryDetailsPageAction.filterFacetCTA("category")), "Verify Category Section is displayed");
        AssertFailAndContinue(mcategoryDetailsPageAction.isDisplayed(mcategoryDetailsPageAction.filterFacetCTA("color")), "Verify Color is displayed under filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.isDisplayed(mcategoryDetailsPageAction.filterFacetCTA("size")), "Verify Size is displayed under filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.isDisplayed(mcategoryDetailsPageAction.filterFacetCTA("attributes")), "Verify Age is displayed under filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.isDisplayed(mcategoryDetailsPageAction.filterFacetCTA("gender")), "Verify Gender is displayed under filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.isDisplayed(mcategoryDetailsPageAction.filterFacetCTA("price")), "Verify Price is displayed under filter");

        AssertFailAndContinue(mcategoryDetailsPageAction.isDisplayed(mcategoryDetailsPageAction.doneBtn), "Verify Done button is displayed under filter");

        mcategoryDetailsPageAction.selectRandomCategory();
        AssertFailAndContinue(Integer.parseInt(mcategoryDetailsPageAction.getText(mcategoryDetailsPageAction.filter).substring(8, 9)) == 1, "Select any L1 category, verify filter count is increased to 1");

        mcategoryDetailsPageAction.clickFilterAndVerifyExpands();

        AssertFailAndContinue(mcategoryDetailsPageAction.isDisplayed(mcategoryDetailsPageAction.appliedFilterList), "Verify L1 category is displayed in filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickDoneBtn(), "close Filter Section");

        //AssertFailAndContinue(mcategoryDetailsPageAction.isDisplayed(mcategoryDetailsPageAction.sortFilter), "Verify Sort is displayed under filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.openSortByCTA(), "Click Sort Button");
        AssertFailAndContinue(mcategoryDetailsPageAction.getAllValues().contains(getDT2TestingCellValueBySheetRowAndColumn("Filter", "Sorts", "opt1")), "Verify Recommended option under Sort section");
        AssertFailAndContinue(mcategoryDetailsPageAction.getAllValues().contains(getDT2TestingCellValueBySheetRowAndColumn("Filter", "Sorts", "opt2")), "Verify Price: High to Low option under Sort section");
        AssertFailAndContinue(mcategoryDetailsPageAction.getAllValues().contains(getDT2TestingCellValueBySheetRowAndColumn("Filter", "Sorts", "opt3")), "Verify Price: Low to High option under Sort section");
        AssertFailAndContinue(mcategoryDetailsPageAction.getAllValues().contains(getDT2TestingCellValueBySheetRowAndColumn("Filter", "Sorts", "opt4")), "Verify Newest option under Sort section");
        AssertFailAndContinue(mcategoryDetailsPageAction.getAllValues().contains(getDT2TestingCellValueBySheetRowAndColumn("Filter", "Sorts", "opt5")), "Verify Most Favorited option under Sort section");
        AssertFailAndContinue(mcategoryDetailsPageAction.getAllValues().contains(getDT2TestingCellValueBySheetRowAndColumn("Filter", "Sorts", "opt6")), "Verify Top Rated option under Sort section");

        AssertFailAndContinue(mcategoryDetailsPageAction.getText(mcategoryDetailsPageAction.selectedSortOption).equals(getDT2TestingCellValueBySheetRowAndColumn("Filter", "Sorts", "opt1")), "Verify Recommended is selected by default");
        mcategoryDetailsPageAction.selectRandomSort();
        mcategoryDetailsPageAction.selectRandomSort();
        //AssertFailAndContinue(mcategoryDetailsPageAction.selectedSortOptions.size() == 1, "Verify Verify user is able to select only one sort option at a time");
        AssertFailAndContinue(mcategoryDetailsPageAction.openSortByCTA(), "Close Sort Button");

        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Open Filter Button");

        mcategoryDetailsPageAction.filterWith_Attribute("size");

        AssertFailAndContinue(Integer.parseInt(mcategoryDetailsPageAction.getText(mcategoryDetailsPageAction.filter).substring(8, 9)) == 2, "Filter with size and verify Filter count is increased to 2");
        mcategoryDetailsPageAction.clickFilterAndVerifyExpands();

        AssertFailAndContinue(mcategoryDetailsPageAction.isDisplayed(mcategoryDetailsPageAction.appliedSizeFilterList), "Verify Size is displayed in Applied filter list");

        mcategoryDetailsPageAction.filterWith_Attribute("color");

        AssertFailAndContinue(Integer.parseInt(mcategoryDetailsPageAction.getText(mcategoryDetailsPageAction.filter).substring(8, 9)) == 3, "Filter with size and verify Filter count is increased to 3");
        mcategoryDetailsPageAction.clickFilterAndVerifyExpands();
        AssertFailAndContinue(mcategoryDetailsPageAction.isDisplayed(mcategoryDetailsPageAction.appliedColorFilterList), "Verify Color is displayed in Applied filter list");

        mheaderMenuActions.clickOnTCPLogo();
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "ProductType"));
//        msearchResultsPageActions.click(msearchResultsPageActions.searchIcon);
//        msearchResultsPageActions.clearAndFillText(msearchResultsPageActions.searchBox, getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "ProductType"));
//        msearchResultsPageActions.click(msearchResultsPageActions.searchSubmitButton);
        mcategoryDetailsPageAction.clickFilterAndVerifyExpands();
        mcategoryDetailsPageAction.selectRandomCategory();
        // mcategoryDetailsPageAction.click(msearchResultsPageActions.filterBtn);

        mcategoryDetailsPageAction.clickFilterAndVerifyExpands();

        mcategoryDetailsPageAction.selectRandomColor();
        mcategoryDetailsPageAction.staticWait();
        mcategoryDetailsPageAction.selectRandomColor();
        mcategoryDetailsPageAction.staticWait();
        mcategoryDetailsPageAction.selectRandomColor();
        mcategoryDetailsPageAction.staticWait();
        mcategoryDetailsPageAction.selectRandomSize();
        mcategoryDetailsPageAction.staticWait();
        mcategoryDetailsPageAction.selectRandomSize();
        mcategoryDetailsPageAction.staticWait();
        mcategoryDetailsPageAction.selectRandomSize();
        mcategoryDetailsPageAction.staticWait();
        AssertFailAndContinue(mcategoryDetailsPageAction.selectedSizes.size() > 1, "Verify Size is multi select ");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectedColors.size() > 1, "Verify color is multi select ");

        mheaderMenuActions.clickOnTCPLogo();

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "ProductType"));
        AssertFailAndContinue(msearchResultsPageActions.isDisplayed(msearchResultsPageActions.filterBtn), "Verify filter is reset for the next visit of plp");
        AssertFailAndContinue(mheaderMenuActions.clickScrollToTop(), "Click scroll to top in bottom page and verify user is moved to page top");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {PLP})
    public void validatePLPFilters(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " Store validate following for filters in PLP Screen" +
                "1. 2 Done Buttons And Clear All button\n 2. Clear All button should be disabled in by default\n" +
                "3. Verify Clear ALL button is enabled when any filter applied\n" +
                "4 Verify Clear All functionality\n5. Verify Clear CTA for individual facets\n6. Verify Clear button is not displayed if no filter applied for individual facets\n" +
                "7. Verify Clear CTA functionality\n8. Verify Facets values");
        //DT-43042
        panCakePageActions.navigateToRandomPLP();
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mproductDetailsPageActions.recommendationSection), "Verify Recommended Products displayed on the PLP");

        mcategoryDetailsPageAction.openFilterButton();
        AssertFailAndContinue(mcategoryDetailsPageAction.doneBtns.size() == 1, "Verify 1 Done Button displayed");
        AssertFailAndContinue(mcategoryDetailsPageAction.validateClearAllButtonState("Disable"), "Verify Clear All Button is displayed in disabled state by Default");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectRandomSize(), "Select any random size and verify Clear button is displayed beside size facet");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectRandomColor(), "Select any random color and verify Clear button is displayed beside color facet");
        AssertFailAndContinue(mcategoryDetailsPageAction.validateClearAllButtonState("Enable"), "Verify Clear All is in enable state by after selecting any filter");
        mcategoryDetailsPageAction.scrollToTop();
        AssertFailAndContinue(!mcategoryDetailsPageAction.clearFilterSelections("size"), "Click Clear for Size, verify Size selection removed");
        AssertFailAndContinue(!mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.clearSizeFilter, 5), "Click Clear for Size selection is not displayed");
        AssertFailAndContinue(!mcategoryDetailsPageAction.clearFilterSelections("color"), "Click Clear for color, verify color selection removed");
        AssertFailAndContinue(!mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.clearColorFilter, 5), "Clear for color selection is not displayed");
        AssertFailAndContinue(mcategoryDetailsPageAction.validateClearAllButtonState("Disable"), "Verify Clear All Button is displayed in Disabled state by Default");
        int sizes = mcategoryDetailsPageAction.sizes.size();
        mcategoryDetailsPageAction.filterWith_Attribute("color");
        mcategoryDetailsPageAction.openFilterButton();
        int sizes_now = mcategoryDetailsPageAction.sizes.size();
        AssertFailAndContinue(sizes >= sizes_now, "Apply with any color filter and verify the size chart is trimmed to available size for that color");
        AssertFailAndContinue(mheaderMenuActions.clickScrollToTop(), "Click scroll to top in bottom page and verify user is moved to page top");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {PLP})
    public void verifyProductCards(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("1.As a Guest/Registered user in US/CA En/Es store, Verify product cards displays following\n" +
                "1. Product Image\n" +
                "2. Product Name\n" +
                "3. Sale Pricing\\n" +
                "4. Add to Bag button\n" +
                "5. Add to Favorites icon\n" +
                "6.Pick up in store button\n" +
                "2. As a Guest/Registered/Remembered user in US/CA En/Es/Fr store, Verify user clicking following redirects user to Product Details Page (PDP)\n" +
                "1. Product Image\n" +
                "2. Product Name\n" +
                "3. As a Guest/Registered/Remembered user in US/CA En/Es/Fr store, Verify user clicking on the Add to Bag button from product card flips product card to display Add to Bag flip with following\n" +
                "1. Color Selection\n" +
                "2. Size Selection \n" +
                "3. Fit Selection (if applicable)\n" +
                "4. Select a quantity\n" +
                "5. Add to Bag Button\n" +
                "6. View Product Details link\n" +
                "4. As a Guest/Registered user in US/CA En/Es/Fr store, Verify users Quantity selection in Add to Bag flip - \n" +
                "1. Upon quantity selection, device numer scroll with numers 1 to 15 are displayed\n" +
                "5. As a Guest/Registered user in US/CA En/Es/Fr store, Verify users clicking View Product Details link from add to ag flip opens corresponding Product Details Page (PDP)\n" +
                "6. As a Guest/Registered user in US/CA En/Es/Fr store, Verify user applying Category filter, page refreshes and products refreshes with correct count displayed in Showing counts\n " +
                "7. As a Guest/Registered user in US/CA En/Es/Fr store, Verify user applying further Size/Color filter after selecting Category filter, does not remove the applied Category filter\n");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-38235
        String searchText = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "ProductType");
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, searchText);
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementsAreDisplayed(mcategoryDetailsPageAction.pickupstoreBtn, 10), "Verify Pick up in Store button is displayed for Product Card");
        //DT-38234 & //DT-38237
        AssertFailAndContinue(mproductCardViewActions.verifyProdCardItems(mcategoryDetailsPageAction), "Verify Add To Bag Button,Add to Fav Icon,Offer Price,Original Price,Product Image,Product Name are displayed on Product Card");
        AssertFailAndContinue(mproductCardViewActions.verifyOfferPriceCurrency(mcategoryDetailsPageAction, "$"), "Verify Currency of the Offer Price is $");
        //DT-38242
        AssertFailAndContinue(mcategoryDetailsPageAction.clickProductByPosition(1), "Click Product Image");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.addToBagBtn), "Verify Application navigated to PDP as Add to Bag button is displayed");
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, searchText);
        AssertFailAndContinue(mcategoryDetailsPageAction.clickRandomProductByImage(mproductDetailsPageActions), "Click Product Name");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.addToBagBtn), "Verify Application navigated to PDP as Add to Bag button is displayed");
        //DT-38244
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, searchText);
        mcategoryDetailsPageAction.openProdCardViewByPos(1);
        AssertFailAndContinue(mcategoryDetailsPageAction.verifyBagFlipItems(), "Verify Color and Size swatches,qunatity dd,add to bag button and product details link are on Bag Flip displayed");
        //DT-38248
        AssertFailAndContinue(mproductCardViewActions.isQuantityDDAllValuesAvailable(), "Verify 1 to 15 as Options displayed under Quantity Dropdown");
        //DT-38251
        AssertFailAndContinue(mcategoryDetailsPageAction.clickProductDetails(mcategoryDetailsPageAction), "Verify Application navigated to PDP as Add to Bag button is displayed");
        //DT-38319
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, searchText);
        int count = mcategoryDetailsPageAction.getCount(msearchResultsPageActions);
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Click on Filter Button");
        String applied_Category_Filter = mcategoryDetailsPageAction.applyCategoryFilter(msearchResultsPageActions, 1);
        int countAfterFilter = mcategoryDetailsPageAction.getCount(msearchResultsPageActions);
        AssertFailAndContinue(mcategoryDetailsPageAction.compareTwoCount(count, countAfterFilter), "Verify Count of Items updated after applying Filter");
        //DT-38320
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Click on Filter Button");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectRandomSize(), "Select Random Size");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectRandomColor(), "Select Random Color");
        AssertFailAndContinue(mcategoryDetailsPageAction.verifyCategoryFilterApplied(applied_Category_Filter), "Verify Applied Category Filter is Displayed Selected");
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {PLP, PRODUCTION})
    public void addAndRemoveProdInFavourites(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("1. As a Registered user in US/CA/INT store navigate to any PLP.Add the product to favourite.Verify if the user is able to unfavourite the product from the Favourite Page .Also,ensure that the same is reflected in the corresponding PDP.\n" +
                "2. As a Guest user in US/CA/INT store navigate to any PLP.Click on the heart icon login into any valid TCP account from the login modal that is prompted.Add the product to favourite.Verify if the user is able to unfavourite the product from the Favourite Page.Also,ensure that the same is reflected in the corresponding PDP.\n" +
                "3. As a Guest/Registered user in US/CA/INT En/Es/Fr store, Verify users color selection in Add to Bag flip - \n" +
                "1. Product color swatch default selected based on the product that the user is viewing on the PLP\n" +
                "2.. Color swatch out of stock, that swatch will not be displayed to the user");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-38337
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "ProductType"));
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(1);
        //DT-38336 & DT-38252 & DT-43033
        if (user.equalsIgnoreCase("guest")) {
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        String favProdName = mcategoryDetailsPageAction.getProdName(1);
        AssertFailAndContinue(mheaderMenuActions.moveToWishListAsReg(panCakePageActions, mobileFavouritesActions), "Move to Favourites Page");
        //DT-43034
        AssertFailAndContinue(mobileFavouritesActions.removeProdFromFavByTitle(favProdName), "Remove Product from Favourites Page");
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, favProdName);
        AssertFailAndContinue(mcategoryDetailsPageAction.verifyElementNotDisplayed(mproductDetailsPageActions.addedToWishList), "Verify product removed from Favourite Page reflects on PDP");
        //DT-38245
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "tops");
        AssertFailAndContinue(mcategoryDetailsPageAction.MatchDefaultColorOnBagFlip(), "Verify Image displayed on PLP match with the Default selected image on PLP.Also verify for OOS Product sizes are grayed out.");
    }

    @Test(dataProvider = dataProviderName, priority = 5, groups = {PLP})
    public void verifyOneSizeBtnSelectedOnBagFlip(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " + ", Verify size is automatically selected for the product offered in one size only.");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-31392 & DT-31393
        // failing as on lower env,A defect:-on Bag Flip Color swatch is not Selected due to which size options does not displayed
        panCakePageActions.selectLevelOneCategory("Accessories");
        panCakePageActions.selectLevelTwoCategory("Boy");
        AssertFailAndContinue(mcategoryDetailsPageAction.applySizeFilterByTitle("ONE SIZE"), "Open Filter and select One Size Button");
        mcategoryDetailsPageAction.openProdCardViewByPos(1);
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.selectedFlipOneSize), "Click add to Bag icon.Verify One Size Button is already Selected");
    }

    @Test(dataProvider = dataProviderName, priority = 6, groups = {PLP})
    public void verifyFilterAppliedOnPlp(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "Browse | Default Size and Color Selection in Add to Cart, if mom applies a filter, to reduce clicks to add to cart");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-43039
        panCakePageActions.selectLevelOneCategory("Girl");
        panCakePageActions.selectLevelTwoCategory("Tops");
        AssertFailAndContinue(msearchResultsPageActions.waitUntilElementsAreDisplayed(msearchResultsPageActions.starRating, 5), "Verify Star Ratings displayed on PLP");
        //DT-42966
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.sizeFilterLink), "Verify Size Filter Link is displayed on PLP");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.colorFilterLink), "Verify Color Filter Link is displayed on PLP");
        //DT-32730 & DT-42967
        AssertFailAndContinue(mcategoryDetailsPageAction.applySizeFilterByTitle("XS (4)"), "Open Filter Button and Apply XS size Filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.scrollUpToElement(mcategoryDetailsPageAction.addToBagIconByPos(1)), "Scroll to Element");
        AssertFailAndContinue(mcategoryDetailsPageAction.openProdCardViewByPos(1), "User clicks on Add to Bag Button below Product Tile");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.getSelectedFlipSizebyTitle("XS (4)"), 10), "Verify applied size Filter displayed default selected on the Bag flip");
        AssertFailAndContinue(mcategoryDetailsPageAction.closeBagFlip(), "Close the Bag Flip");
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Click Filter Button");
        //DT-42968
        AssertFailAndContinue(mcategoryDetailsPageAction.selectColorByTitle("WHITE"), "Select White Color from Filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickDoneBtn(), "Click on Done Button");
        AssertFailAndContinue(mcategoryDetailsPageAction.openProdCardViewByPos(1), "User clicks on Add to Bag Button below Product Tile");

        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.getSelectedFlipSizebyTitle("XS (4)"), 10), "Verify applied Size Filter displayed default selected on the Bag flip");
        AssertFailAndContinue(mcategoryDetailsPageAction.verifySelectedFlipWhiteColor(), "Verify applied Color Filter displayed default selected on the Bag flip");
        AssertFailAndContinue(mcategoryDetailsPageAction.closeBagFlip(), "Close the Bag Flip");
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Click Filter Button");
        AssertFailAndContinue(mcategoryDetailsPageAction.applySizeFilterByTitle("M (7/8)"), "Open Filter Button and Apply one more size Filter i.e M");
        AssertFailAndContinue(mcategoryDetailsPageAction.openProdCardViewByPos(1), "User clicks on Add to Bag Button below Product Tile");
        AssertFailAndContinue(mcategoryDetailsPageAction.verifySelectedFlipWhiteColor(), "Verify applied Color Filter displayed default selected on the Bag flip");
        AssertFailAndContinue(mcategoryDetailsPageAction.verifyElementNotDisplayed(mcategoryDetailsPageAction.selectedFlipSize), "Verify Size is not selected on Bag flip");
        AssertFailAndContinue(mcategoryDetailsPageAction.closeBagFlip(), "Close the Bag Flip");
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Click Filter Button");
        AssertFailAndContinue(mcategoryDetailsPageAction.clearFilterSelections("size"), "remove all size Filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickDoneBtn(), "Click on Done BUtton");
        AssertFailAndContinue(mcategoryDetailsPageAction.openProdCardViewByPos(1), "User clicks on Add to Bag Button below Product Tile");
        AssertFailAndContinue(mcategoryDetailsPageAction.verifySelectedFlipWhiteColor(), "Verify applied Color Filter displayed default selected on the Bag flip");
    }

    @Test(dataProvider = dataProviderName, priority = 7, groups = {PLP, USONLY})
    public void verifyFilterAppliedOnBopis(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "Browse | Default Size and Color Selection in Add to Cart, if mom applies a filter, to reduce clicks to add to cart");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT_32730
        panCakePageActions.selectLevelOneCategory("Girl");
        panCakePageActions.selectLevelTwoCategory("Tops");
        AssertFailAndContinue(mcategoryDetailsPageAction.applySizeFilterByTitle("XS (4)"), "Open Filter Button and Apply XS size Filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickPickUpStoreBtn(mbopisOverlayActions, 0), "Click on Pick Up In Store Icon");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mbopisOverlayActions.selectedSizeByTitle("XS (4)"), 10), "Verify applied size is default selected Size on Bopis Overlay ");
        AssertFailAndContinue(mbopisOverlayActions.closeBopisOverlayModal(), "Close Bopis Overlay Modal");
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Click Filter Button");
        AssertFailAndContinue(mcategoryDetailsPageAction.selectColorByTitle("WHITE"), "Select White Color from Filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickDoneBtn(), "Click on Done Button");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickPickUpStoreBtn(mbopisOverlayActions, 0), "Click on Pick Up In Store Icon");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mbopisOverlayActions.selectedSizeByTitle("XS (4)"), 10), "Verify applied size is default selected Size on Bopis Overlay ");
        AssertFailAndContinue(mbopisOverlayActions.verifyWhiteColorSelected(), "Verify applied Color Filter displayed default selected on the Bopis Overlay");
        AssertFailAndContinue(mbopisOverlayActions.closeBopisOverlayModal(), "Close Bopis Overlay Modal");
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Click Filter Button");
        AssertFailAndContinue(mcategoryDetailsPageAction.applySizeFilterByTitle("M (7/8)"), "Open Filter Button and Apply one more size Filter i.e M");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickPickUpStoreBtn(mbopisOverlayActions, 0), "Click on Pick Up In Store Icon");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mbopisOverlayActions.noSizeSelected), "Verify Select is displayed Selected in the Size drop down on Bopis Overlay");
        AssertFailAndContinue(mbopisOverlayActions.verifyWhiteColorSelected(), "Verify applied Color Filter displayed default selected on the Bopis Overlay");
        AssertFailAndContinue(mbopisOverlayActions.closeBopisOverlayModal(), "Close Bopis Overlay Modal");
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Click Filter Button");
        AssertFailAndContinue(mcategoryDetailsPageAction.clearFilterSelections("size"), "remove all size Filter");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickDoneBtn(), "Click on Done BUtton");
        AssertFailAndContinue(mcategoryDetailsPageAction.clickPickUpStoreBtn(mbopisOverlayActions, 0), "Click on Pick Up In Store Icon");
        AssertFailAndContinue(mbopisOverlayActions.verifyWhiteColorSelected(), "Verify applied Color Filter displayed default selected on the Bopis Overlay");
        AssertFailAndContinue(mbopisOverlayActions.closeBopisOverlayModal(), "Close Bopis Overlay Modal");
    }


    @Test(dataProvider = dataProviderName, priority = 8, groups = {PLP})
    public void verifyAddToBagNotification(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a Guest/Registered user is in US/CA/INT En/Es/Fr store," +
                "When the user is in any PLP page ,clicks on the FILTER button below the breadcrumb,on post expanding\n" +
                "the filter by section Verify if user adds product to bag ,add to bag notification is displayed properly\n" +
                "Verify Two products are displayed in a row for PLP\n" +
                "Verify Site wide banner in plp page");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        //DT-38300
        panCakePageActions.selectLevelOneCategory("Girl");
        panCakePageActions.selectLevelTwoCategory("Tops");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.filter), "Verify Filter button is displayed");
        //DT-33455
        AssertFailAndContinue(mcategoryDetailsPageAction.validatedProductWidth(), "Verify 2 products are displayed in single row in PLP page");
        //DT-43360
        AssertFailAndContinue(mheaderMenuActions.verifySiteWideBanner(), "Verify Site wide banner in PLP page at top");
        AssertFailAndContinue(mcategoryDetailsPageAction.openFilterButton(), "Click Filter Button and verify Done Button is displayed");
        AssertFailAndContinue(mcategoryDetailsPageAction.filterWith_Attribute("size"), "Apply Size Filter and Verify Filter button is displayed");
        AssertFailAndContinue(mcategoryDetailsPageAction.addItemToBagAndVerifyNotification(1), "Click add to Bag Button on PLP,Then click add to Bag Button on Bag Flip and Verify added to bag notification is displayed");
    }

    @Test(dataProvider = dataProviderName, priority = 9, groups = {PLP})
    public void verifyBackToTopFunctionality(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("1. As a Guest/Registered/Remembered user in US/CA/INT En/Es/Fr store, Verify user scrolling down on search result page starts displaying Back to Top button+\n+" +
                "2.As a Guest/Registered/Remembered user in US/CA/INT En/Es/Fr store, Verify user clicking Back to Top navigates user back to top of search result page\n" +
                "a. User naviagted to Top of search result\n" +
                "b. Back to Top button not displayed at top of page");
        //DT-38232
        AssertFailAndContinue(panCakePageActions.navigateToRandomPLP(), "Navigate to Random PLP Page of Girl Category");
        mcategoryDetailsPageAction.scrollToBottom();
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.backToTopBtn), "Verify Back to Top Button is displayed");
        //DT-38233
        AssertFailAndContinue(mcategoryDetailsPageAction.clickBackToTopBtn(), "Click on Back to Top Button and verify Back to Top Button is not Displayed");
    }

    @Test(priority = 10, dataProvider = dataProviderName, groups = {PLP})
    public void validateL2CategOnPLP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("Verify when user clicks L1 category from Menu slider, displays L2 categories menu" +
                " and details listed on L2 PLP screen" +
                "DT-36298");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-38217
        String lOneCategory = getDT2TestingCellValueBySheetRowAndColumn("Department", "L1_Category", "Value");
        panCakePageActions.selectLevelOneCategory(lOneCategory);
        AssertFailAndContinue(panCakePageActions.leveltwos.size() > 0, "Verify L2 categories are getting displayed");
        //DT-38220
        List<String> lTwoCategory = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "L2_Category", "Value"));
        panCakePageActions.selectLevelTwoCategory(lTwoCategory.get(0));
        AssertFailAndContinue(panCakePageActions.getPlpCategory().equalsIgnoreCase(lOneCategory + ">" + lTwoCategory.get(0)), "verify user is redirected to associated L2 category PLP page and bread crumb is displayed on PLP page after clicking L2 Category link ");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementsAreDisplayed(mcategoryDetailsPageAction.productTitles, 10), "PLP displays Product Cards");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.sortyByBtn), "Verify Sort By selections are displayed");

        String prodName = mcategoryDetailsPageAction.getProdName(1);
        AssertFailAndContinue(msearchResultsPageActions.addProdToBag(mcategoryDetailsPageAction, 1), "Add the Product to Bag and verify the View Bag notification");
        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Click on Shopping Bag on header");
        AssertFailAndContinue(mshoppingBagPageActions.isProdPresentByTitle(prodName), "Verify Product is present in the Shopping Bag");

        panCakePageActions.selectLevelOneCategory(lOneCategory);
        panCakePageActions.selectLevelTwoCategory(lTwoCategory.get(0));
        String favProdName = mcategoryDetailsPageAction.getProdName(1);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(1);
        if (user.equalsIgnoreCase("guest")) {
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        AssertFailAndContinue(mheaderMenuActions.moveToWishListAsReg(panCakePageActions, mobileFavouritesActions), "Move to Favourites Page");
        AssertFailAndContinue(mobileFavouritesActions.verifyProdPresentByTitle(favProdName), "Verify Item added as Favourite is displayed in favourite list");

        panCakePageActions.selectLevelOneCategory(lOneCategory);
        panCakePageActions.selectLevelTwoCategory(lTwoCategory.get(0));
        int bagCountBefore = Integer.parseInt(mheaderMenuActions.getQty());
        AssertFailAndContinue(mcategoryDetailsPageAction.clickPickUpStoreBtn(mbopisOverlayActions, 0), "click Pick up in Store Icon");

        AssertFailAndContinue(mbopisOverlayActions.selectSizeAndSelectStore("newjesy"), "Select Size,Store and Click on Search Button");
        AssertFailAndContinue(mbopisOverlayActions.clickAddtoBag(), "click Add to Bag Button on Bopis Overlay");
        AssertFailAndContinue(mheaderMenuActions.waitUntilElementDisplayed(mheaderMenuActions.paypalFromNotification), "Verify Add to Bag Notification with Paypal button is displayed");
        int bagCountAfter = Integer.parseInt(mheaderMenuActions.getQty());
        AssertFailAndContinue(msearchResultsPageActions.compareTwoCount(bagCountAfter, bagCountBefore), "Verify the Item Added in the Bag");
    }

    @Test(priority = 11, dataProvider = dataProviderName, groups = {PLP})
    public void validateL3CategOnPLP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("Verify when user clicks L1 category from Menu slider, displays L3 categories menu" +
                " and details listed on L3 PLP screen");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-38223
        String lOneCategory = getDT2TestingCellValueBySheetRowAndColumn("Department", "L1_Category", "Value");
        panCakePageActions.selectLevelOneCategory(lOneCategory);
        String lTwoCategory = getDT2TestingCellValueBySheetRowAndColumn("Department", "L2_Category", "otherValue");
        panCakePageActions.moveToLevelThreeCategory(lTwoCategory);
        String lThreeCategory = getDT2TestingCellValueBySheetRowAndColumn("Department", "L3_Category", "otherValue");
        panCakePageActions.selectLevelThreeCategory(lThreeCategory, mcategoryDetailsPageAction);
        AddInfoStep("Bread crumb displayed on PLP" + panCakePageActions.getPlpCategory());

        //DT-42952 & DT-43741
        AssertFailAndContinue(panCakePageActions.getPlpCategory().equalsIgnoreCase(lOneCategory + ">" + lTwoCategory + ">" + lThreeCategory), "verify user is redirected to associated L3 category PLP page and bread crumb is displayed on PLP page after clicking L3 Category link ");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementsAreDisplayed(mcategoryDetailsPageAction.productTitles, 10), "PLP displays Product Cards");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.sortyByBtn), "Verify Sort By selections are displayed");
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
