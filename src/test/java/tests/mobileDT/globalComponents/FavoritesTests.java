package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Test(singleThreaded = true)
public class FavoritesTests extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = "", password;
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        env = EnvironmentConfig.getEnvironmentProfile();
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
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

    @Test(dataProvider = dataProviderName, priority = 0, groups = {PLP, REGISTEREDONLY})
    public void favoritesUiValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Registered user is in US/CA/INT En/Es/Fr store," +
                "When the user is in Favorites page, verify\n" +
                "Site wide banner \n  Two images are displaying at the top");
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "shoes");
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(0);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(1);

        AssertFailAndContinue(mcategoryDetailsPageAction.favIconEnabled.size() == 2, "verify user is able to add two fav icons in the same row");
        panCakePageActions.navigateToMenu("FAVORITES");

        AssertFailAndContinue(mheaderMenuActions.verifySiteWideBanner(), "verify site wide banner in Favorites page");
        AssertFailAndContinue(mobileFavouritesActions.validatedProductWidth(), "verify two products are displaying per row in favorites page");

        AssertFailAndContinue(mobileFavouritesActions.shareWishListViaEmail("test@yopmail.com", "test", "testemail"), "Share wishlist via email");

        mobileFavouritesActions.selectDisplayMode("Available");
        AssertFailAndContinue(mobileFavouritesActions.unFavoriteItem(1), "Un-favorite an item and verify notification is displayed");
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {REGISTEREDONLY})
    public void unfav_purchasedItems(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Registered user is in US/CA/INT En/Es/Fr store," +
                "When the user is in Favorites page, verify\n" +
                "Site wide banner \n  Two images are displaying at the top");
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "shoes");
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(0);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(1);

        panCakePageActions.navigateToMenu("FAVORITES");
        int initial = mheaderMenuActions.getBagCount();
        mobileFavouritesActions.addProductToBagByPosition(1);

        mheaderMenuActions.clickShoppingBagIcon();
        int afterCount = mheaderMenuActions.getBagCount();
        AssertFailAndContinue(initial + 1 == afterCount, "Verify product is added to bag after clicking on Add to bag ");

        mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);

        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"),
                    shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), "", email);
        }
        if (store.equalsIgnoreCase("CA")) {
            shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"),
                    shipDetails.get("phNum"), "", email);
        }

        mbillingPageActions.enterCardDetails(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"));

        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.clickSubmOrderButton();

        panCakePageActions.navigateToMenu("FAVORITES");
        mobileFavouritesActions.selectDisplayMode("Purchased");
        AssertFailAndContinue(mobileFavouritesActions.unFavoriteItem(0), "Un-favorite purchased item and verify notification is displayed");
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {REGISTEREDONLY})
    public void unfav_newWishList(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Registered user in " + store + " store verify user is able to un-fav an item from different wishlist");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "shoes");
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(0);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(1);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(2);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(3);

        panCakePageActions.navigateToMenu("FAVORITES");

        AssertFailAndContinue(mobileFavouritesActions.createANewWishList("newList"), "Create a new Empty wish list");

        mobileFavouritesActions.changeWishList("default");
        panCakePageActions.navigateToMenu("FAVORITES");
        //TO DO: need to write wait condition after changing the wishlist
        AssertFailAndContinue(mobileFavouritesActions.moveProductToOtherWishList(0, "newList"), "Move a product from Default WL to new WL");
        mobileFavouritesActions.scrollToTop();
        mobileFavouritesActions.changeWishList("newList");
        AssertFailAndContinue(mobileFavouritesActions.unFavoriteItem(0), "Un-favorite item from wishlist(other than default)");
        mobileFavouritesActions.scrollToBottom();
        AssertFailAndContinue(mheaderMenuActions.clickScrollToTop(), "Verify user is able to scroll in fav page");
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {REGISTEREDONLY})
    public void oneSizeFav_tests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Registered user in " + store + " store verify user is able to perform actions on fav item with one size only" +
                "DT-42261");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        String searchItem = getmobileDT2CellValueBySheetRowAndColumn("Search", "searchOneSizeItem", "Value");
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, searchItem);

        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(0);
        panCakePageActions.navigateToMenu("FAVORITES");

        AssertFailAndContinue(mobileFavouritesActions.clickAddToBagForProduct(mproductCardViewActions, 1), "Click add to bag for product with only single size verify size selected by default from wishlist");
        mproductCardViewActions.clickCloseIcon();

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "Extended Sizes");
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(0);
        panCakePageActions.navigateToMenu("FAVORITES");
        int firstSize = mobileFavouritesActions.favoritesProducts.size();
        AssertFailAndContinue(mobileFavouritesActions.clickEditLink(mproductCardViewActions, 1), "Click edit for product with only single size verify size selected by default from wishlist");

        AssertFailAndContinue(mproductCardViewActions.selectedFits.size() == 1, "verify for fits, fit is selected by default");

        AssertFailAndContinue(mproductCardViewActions.editDetailsAndSave(), "Click on save button");

        int secondSize = mobileFavouritesActions.favoritesProducts.size();

        AssertFailAndContinue(firstSize == secondSize, "Verify products are not missing after edit from Favorites");
        AssertFailAndContinue(mheaderMenuActions.clickScrollToTop(), "Verify user is able to scroll in fav page after edit product");
    }

    @Test(dataProvider = dataProviderName, priority = 4, groups = {REGISTEREDONLY})
    public void favorites_pdp(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Registered user in " + store + " store verify size is selected by default in Edit view and clicking on Add to bag is adding product to bag when user added a " +
                "product from DPD with size selected to favorites" +
                "DT-37419, DT-37423");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "shoes");
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(0);
        mcategoryDetailsPageAction.clickProductByPosition(1);

        mproductDetailsPageActions.selectAnySize();
        mproductDetailsPageActions.scrollToTop();
        mproductDetailsPageActions.clickAddToWishListAsRegistered();

        panCakePageActions.navigateToMenu("FAVORITES");
        AssertFailAndContinue(mobileFavouritesActions.clickEditLink(mproductCardViewActions, 1), "Click edit for product verify size is selected by default");

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "shoes");
        mcategoryDetailsPageAction.clickProductByPosition(1);

        mproductDetailsPageActions.clickAddToWishListAsRegistered();

    }

    @Test(dataProvider = dataProviderName, priority = 5, groups = {REGRESSION, NARWHAL})
    public void fav_TopRated(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " in " + store + " store verify Top rated badge is not displayed in Favorites DT-43656," +
                "DT-44370");
        if (store.equalsIgnoreCase("US")) {
            panCakePageActions.addStateCookie("NJ");
        }
        if (store.equalsIgnoreCase("CA")) {
            panCakePageActions.addStateCookie("MB");

        }
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, "tops");

        AssertFailAndContinue(msearchResultsPageActions.sortByOptionPlp("top rated"), "Filter with top rated");

        String prodName = msearchResultsPageActions.getFirstProdName();
        AssertFailAndContinue(msearchResultsPageActions.clickFirstProductMatchWIthBadge(mproductDetailsPageActions, "Top Rated"), "Click on top rated badge product and verify pdp is displayed");
        mproductDetailsPageActions.clickAddToWishListAsRegistered();
        panCakePageActions.navigateToMenu("FAVORITES");

        AssertFailAndContinue(mobileFavouritesActions.isBadgesDisplayed(), "Verify no badges are displayed in Fav");

        mobileFavouritesActions.clickProdPresentByTitle(prodName, mproductDetailsPageActions);

        AssertFailAndContinue(mproductDetailsPageActions.clickPickUpStoreBtn(mbopisOverlayActions), "Verify if BOPIS CTA is available in the PDP for the favorite product");
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
