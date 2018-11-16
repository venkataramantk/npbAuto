package tests.webDT.globalComponents.header.header_645;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by AbdulazeezM on 3/10/2017.
 */
//User Story - DT-645
//@Listeners(MethodListener.class)
public class Favorites_Header extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(Favorites_Header.class);
    String emailAddress;
    private String password;

    @Parameters("store")
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            emailAddress = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");

        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            emailAddress = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");

        }
        headerMenuActions.deleteAllCookies();
    }

    @Parameters("store")
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @Test(priority = 0, groups = {GLOBALCOMPONENT, SMOKE, USONLY, GUESTONLY, PROD_REGRESSION, FAVORITES})
    public void validateWLAsguestUser(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if guest user is redirected to wishlist drawer overlay when he clicks on wishlist icon from homepage");
        List<String> wishlistContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("WishListContent", "wishListContent", "expectedContent"));

        AssertFailAndContinue(headerMenuActions.clickWishListAsGuest(wishListDrawerActions), "Verify if guest user can redirect to wishlist overlay");
        AssertFailAndContinue(wishListDrawerActions.validateWishListLoginOverlayContent(wishlistContent.get(0), wishlistContent.get(1), wishlistContent.get(2), wishlistContent.get(3)), "Verify if the contents are present in the wishlist overlay");
        headerMenuActions.click(headerMenuActions.createAccountOverlay);
        AddInfoStep("\"Click on create account link\"");
        departmentLandingPageActions.mprLinkDisplay();
    }

    @Parameters(storeXml)
    @Test(priority = 1, groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION, FAVORITES})
    public void validateErrMsg(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if guest user is redirected to wishlist drawer overlay and checkout the error message present in the page");
        List<String> loginErrMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("WishListContent", "fieldErrorMsg", "expectedErrorMsg"));
        List<String> errMsgTab = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("WishListContent", "errMsgTab", "expectedErrorMsg"));

        AssertFailAndContinue(headerMenuActions.clickWishListAsGuest(wishListDrawerActions), "Verify if guest user can redirect to wishlist overlay");
        AssertFailAndContinue(wishListDrawerActions.validateErrMsg(errMsgTab.get(0), errMsgTab.get(1)), "Verify if the field level error message is getting displayed");
        AssertFailAndContinue(wishListDrawerActions.validateErrMsgSplChar(loginErrMsg.get(2)), "Validate Error message when given special characters in email field and leaving password field empty");
        AssertFailAndContinue(wishListDrawerActions.validErrMsgValidUserName(createAccountActions.randomEmail(), errMsgTab.get(1)), "Enter valid email and click on login button and check the error message");
        AssertFailAndContinue(overlayHeaderActions.closeOverlayDrawer(headerMenuActions), "Verify if user displayed with the current page when closing the header overlay");

    }

    @Parameters(storeXml)
    @Test(priority = 2, groups = {GLOBALCOMPONENT, REGRESSION, USONLY, GUESTONLY, PROD_REGRESSION, FAVORITES})
    public void mprLinksInHeader_Guest(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if guest user click on MPR drawer overlay and check it navigates to appropriate page");

        if (store.equalsIgnoreCase("US")) {
            headerMenuActions.hoverOnMPRLink();
            AssertFailAndContinue(headerMenuActions.clickOnMPRLink(), "click on MPR link in the header and validate the links present in the overlay");
            AssertFailAndContinue(headerMenuActions.clickLogin_MPR(loginDrawerActions), "Click on login link and check the fields");
            loginDrawerActions.click(loginDrawerActions.closeOverlay);
            // AssertFailAndContinue(headerMenuActions.clickOnMPRLink(),"click on MPR link in the header and validate the links present in the overlay");
            AssertFailAndContinue(headerMenuActions.clickCreateAcc_MPR(createAccountActions), "Click on create account link and check the fields");
            createAccountActions.click(createAccountActions.closeOverlay);
            //AssertFailAndContinue(headerMenuActions.clickOnMPRLink(),"click on MPR link in the header and validate the links present in the overlay");
            AssertFailAndContinue(headerMenuActions.clickLearnMore_MPR(), "Click on Learn more link and check the fields");
        }
    }

    @Parameters(storeXml)
    @Test(priority = 3, groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION, FAVORITES})
    public void favoriteModalValidation(@Optional("US") String store) throws Exception {
        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify the wishlist drawer closes and opens up inline shopping bag drawer after adding items to bag");

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        addToBagBySearching(itemsAndQty);
        AssertFailAndContinue(headerMenuActions.clickWishListAsGuest(wishListDrawerActions), "Verify if guest user can redirect to wishlist overlay");
        headerMenuActions.closeTheVisibleDrawer();
        headerMenuActions.clickOnTCPLogo();
        headerMenuActions.click(headerMenuActions.shoppingBagIcon);
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shoppingBagDrawerActions.mprEspotBannerGuestCheck(), "Check the Mpr Esopt value");
        }
        if (store.equalsIgnoreCase("CA")) {
            shoppingBagDrawerActions.mprCheckCA();
        }
        AssertFailAndContinue(shoppingBagDrawerActions.clickOnProductNameFromInlineSB(productDetailsPageActions), "Click on Product name and the PDP redirection");
    }


    @Parameters(storeXml)
    @Test(priority = 4, groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, SMOKE, PRODUCTION, FAVORITES})
    public void verifyMoveProdFromBagToWL_WLToBag(@Optional("US") String store) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("Ensure that guest user on CA store is able to successfully move a product from bag to wishlist and again back to bag from wishlist");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        addToBagBySearchingFromProductCard(itemsAndQty);
//        String itemNum = productDetailsPageActions.getItemNumber();
//        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
//        AssertFailAndContinue(shoppingBagPageActions.isProductsAddedByUpcNums(upcNums),"Product added to bag");
        String prodTitle = shoppingBagPageActions.getProdTitleByPosition(1);
        int qtyInBagBeforeMovingToWl = Integer.parseInt(headerMenuActions.getQtyInBag());

        AssertFailAndContinue(shoppingBagPageActions.moveProdToWLByPositionAsGuest(loginPageActions, 1), "Clicked on wishlist as a guest navigates to wishlist login page");
        loginPageActions.loginAsRegisteredUserFromLoginForm(emailAddress, password);
//        AssertFailAndContinue(shoppingBagPageActions.waitUntilElementDisplayed(shoppingBagPageActions.emptyBagMessage), "Logined as registered  user and added item " + upcNums.get(0) + " to wishlist. Reg user "+emailAddress);

        headerMenuActions.clickWishListAsRegistered(favoritePageActions);
        AssertFailAndContinue(favoritePageActions.isProdPresentByTitle(prodTitle), "Product added to wishlist from shopping bag");
        int afterMovingProdFromBagToWl = Integer.parseInt(headerMenuActions.getQtyInBag());
        AssertFailAndContinue(afterMovingProdFromBagToWl < qtyInBagBeforeMovingToWl, "products available in the bag gets updated within the minicart icon in the header after moving prod from bag to wishlist.");

        String prodTitleAtWL = favoritePageActions.getProdTitleByPos(1);
        favoritePageActions.clickAddToBagByPosWithSelectedSize(1);
        headerMenuActions.staticWait(5000);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.isProdPresentByTitle(prodTitleAtWL), "Product added to shopping bag from wishlist ");
        int afterMovingFromWlProdToBag = Integer.parseInt(headerMenuActions.getQtyInBag());
        AssertFailAndContinue(afterMovingFromWlProdToBag == qtyInBagBeforeMovingToWl, "products available in the bag gets updated within the minicart icon in the header after moving prod from wishList to Bag.");
        AssertFailAndContinue(headerMenuActions.clickLogoutFromWlandingPage(favoritePageActions), "Click logout link from Favorite landing page");
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, REGISTEREDONLY, PROD_REGRESSION, FAVORITES}, priority = 5)
    public void verifyFav_Reg(@Optional("US") String store) throws Exception {
        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify the Favorites functionality for Reg user" +
                "1. Maximum Fav list creation is 5" +
                "2. Redirection to PDP by clicking on the Image from Favorite landing page" +
                "DT-44370");
        List<String> expErrorMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("WishlistContent", "createNewFavListErr", "expectedErrorMsg"));
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);
        headerMenuActions.clickWishListAsRegistered(favoritePageActions);
        AssertFailAndContinue(favoriteOverlayActions.create5FavList("TestmaxList@!"), "Create Max Fav list");
        headerMenuActions.waitUntilElementDisplayed(favoritePageActions.settingsIcon, 3);
        AssertFailAndContinue(favoritePageActions.validateUpdateOverlay(), "Verify the various fields are getting displayed properly in update overlay");
        AssertFailAndContinue(favoritePageActions.deleteWL(), "Delete the newly created Wishlist");
        headerMenuActions.staticWait(2000);
        headerMenuActions.waitUntilElementDisplayed(headerMenuActions.shoppingBagIcon, 3);
        AssertFailAndContinue(favoriteOverlayActions.createNewFavInlineError(expErrorMsg.get(0), expErrorMsg.get(1)), "Verify the error message displayed in create Fav modal");
        favoritePageActions.clickCloseOverlay();
        //      favoritePageActions.selectWLDrpDown_MoreThan_0_Items();
        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.clickWishListAsRegistered(favoritePageActions);
        if (store.equalsIgnoreCase("US")) {
            headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
            AssertFailAndContinue(shoppingBagDrawerActions.mprEspotBannerRegCheck(), "Check the Mpr Esopt value");
            headerMenuActions.click(headerMenuActions.closemodal);
        }
        if (store.equalsIgnoreCase("CA")) {
            shoppingBagDrawerActions.mprCheckCA();
        }
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        shoppingBagPageActions.moveProdToWLByPositionAsReg(1);
        headerMenuActions.clickWishListAsRegistered(favoritePageActions);
        AssertFailAndContinue(favoritePageActions.shareWishlist(1), "Verify the share wishlist overlay display");
        AssertFailAndContinue(favoritePageActions.filterOption(2), "verify the filter by option in Fav landing page");
        // AssertFailAndContinue(favoritePageActions.clickOnPricePDPRedirection(productDetailsPageActions), "Verify that the item is redirected to PDP page by clicking on the price");
        favoritePageActions.clickOnItemImgAndPDPRed(productDetailsPageActions, 1);
        //modified the homeBreadcrumb action
        AssertFailAndContinue(productDetailsPageActions.waitUntilElementDisplayed(productDetailsPageActions.homeBreadcrumb,2), "Check the breadcrumb display in PDP");
        String pdpURL = headerMenuActions.getCurrentURL();
        headerMenuActions.click(headerMenuActions.welcomeCustomerLink);
        myAccountPageDrawerActions.clickLogoutLink(headerMenuActions);
        AssertFailAndContinue(headerMenuActions.validatePageAfterLogout(pdpURL), "verify that the user remains in the same page after logout");
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, REGISTEREDONLY, PROD_REGRESSION, FAVORITES}, priority = 5)
    public void topRated_fav(@Optional("US") String store) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Registered user in " + store + " store verify Top rated badge is not displaying in Fav page, SB drawer and SB Page" +
                "DT-43657, DT-43656");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);

        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, searchKeyword);
        categoryDetailsPageAction.sortByOptionPlp("TOP RATED");

        AssertFailAndContinue(categoryDetailsPageAction.clickFirstProductMatchWIthBadge(productDetailsPageActions, "Top Rated"), "Click on top rated badge product and verify pdp is displayed");
        productDetailsPageActions.selectASize();
        AssertFailAndContinue(productDetailsPageActions.addToWLAsReg(), "Click Fav icon as reg user");
        headerMenuActions.clickWishListAsRegistered(favoritePageActions);
        AssertFailAndContinue(favoritePageActions.isBadgesDisplayed(), "Verify no badges are displayed in Fav");

        favoritePageActions.addProdToBagFromWL(headerMenuActions, 1);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        AssertFailAndContinue(!shoppingBagDrawerActions.getBadgesForProducts().contains("Top Rated"), "Verify Top rated Badge is not displayed in SB");

        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
        AssertFailAndContinue(!shoppingBagDrawerActions.getBadgesForProducts().contains("Top Rated"), "Verify Top rated Badge is not displayed in mini-bag");
    }

}
