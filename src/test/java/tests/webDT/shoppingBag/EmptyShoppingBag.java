package tests.webDT.shoppingBag;

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
 * Created by AbdulazeezM on 3/22/2017.
 */
public class EmptyShoppingBag extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(EmptyShoppingBag.class);
    String emailAddressReg;
    private String password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        System.out.println("store: " + store);
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            if (user.equalsIgnoreCase("registered")) {
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
                password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
            }

        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered")) {
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
            driver.get(EnvironmentConfig.getApplicationUrl());
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }

    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }


    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, PROD_REGRESSION})
    public void validateEmptyShoppingBag(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - DT-642: Verify if guest user is redirected to shopping bag page and validate the empty message");
        List<String> emptyBagContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MiniShoppingBag", "emptyBagContent", "expectedContent"));
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        List<String> urlVal = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "URL", "Value"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        AssertFailAndContinue(headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions), "Verify if user redirected to the empty shopping bag page");
        AssertFailAndContinue(!shoppingBagPageActions.isDisplayed(shoppingBagPageActions.orderLedgerSection), "Verify if empty shopping bag page is not displaying order ledger section");
        AssertFailAndContinue(!shoppingBagPageActions.isDisplayed(shoppingBagPageActions.checkoutBtn), "Verify if empty shopping bag page is not displaying checkout button");
        AssertFail(footerActions.waitUntilElementDisplayed(footerActions.link_SizeChart), "verify Footer is displayed in empty shopping bag");
        AssertFailAndContinue(shoppingBagPageActions.validateEmptyTextInShoppingBagPage(emptyBagContent.get(0)), "Verify if the appropriate contents are present in the empty shopping bag Page");
        AssertFailAndContinue(headerMenuActions.getQtyInBag().equals("0"), "The quantity in bag is showing 0");
//        if (isRecommendationsOn()) {
//            int prodInProdRecomm = footerActions.getProductRecommProdOnEmptyBag();
//            AssertFailAndContinue(prodInProdRecomm == 4, "The products in Product Recommendation showing " + prodInProdRecomm);
//            try {
//                AssertFailAndContinue(footerActions.addRecommendationProdCard(productCardViewActions), "Adding product to cart from product Recommendations");
//                AssertFailAndContinue(productCardViewActions.selectAFitAndSizeAndAddToBag(), "Add the recommendation product in to the cart");
//                AssertFailAndContinue(footerActions.clickOnItemAtProdRecommByPos(1, productDetailsPageActions), "Clicking on prod Recomm navigates to PDP");
//            } catch (Exception e) {
//                AddInfoStep("Recommendations are not showing at empty shopping bag");
//            }
//        } else {
//            AddInfoStep("Recommendation are turned off currently");
//        }
        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.validateEmptyShoppingCart(), "Verify that the empty bag message is displayed when removing the prod from the bag");
        homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(0));
        AddInfoStep("Verify if the guest user redirected to the " + deptName.get(0) + " department landing page");
        AssertFailAndContinue(departmentLandingPageActions.validateCatPageURL(urlVal.get(0)), "Verify if the corresponding department page is matching with the current URL");
    }


    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, PROD_REGRESSION})
    public void plccOrAirMilesFromEmptySB(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if guest user is redirected to shopping bag page and validate the empty message and MPR Espot");
        System.out.println("user: " + user);

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shoppingBagPageActions.clickMPREspot(mprOverlayActions), "Click on mpr overlay and check mpr overlay is getting displayed");
//This is removed    AssertFailAndContinue(mprOverlayActions.closeMpr_Overlay(shoppingBagPageActions), "Close the mpr overlay and check user retained in shopping bag page");
        } else if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shoppingBagPageActions.clickAirMilesBannerAndValidateContent(), "Clicking on Airmiles banner navigates to the airmiles content page");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, USONLY, PROD_REGRESSION})
    public void removeGiftCardAndVerify(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if user is removes the gift card and validate the empty message");
        System.out.println("user: " + user);

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        footerActions.clickOnGiftCardsLink(giftCardsPageActions);
        giftCardsPageActions.clickSendAGiftCardsButton(productDetailsPageActions);
        productDetailsPageActions.addGCFromPDP(headerMenuActions);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickRemoveLinkByPosition(1), "Clicking on remove link removes the gift card");
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, DRAGONFLY, PROD_REGRESSION, USONLY})
    public void validateZeropoints(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("AS a " + user + " user in " + store.toUpperCase() + " When removing all items from bag, the loyalty points calculation should display zero");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty1 = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword1, qty1);
        List<String> aboutUsLinkValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "aboutUs", "linkName"));
        List<String> aboutUsURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "aboutUs", "URLValue"));

        addToBagBySearching(searchKeywordAndQty);
        addToBagBySearching(searchKeywordAndQty1);

        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
        headerMenuActions.closeTheVisibleDrawer();

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(footerActions.clickOnAboutUSLinkAndValidateURL(aboutUsLinkValidation.get(5), aboutUsURLValidation.get(4)), "ensure that if user navigates to Recall Information page on clicking the Recall Information link");
        } else {
            AssertFailAndContinue(footerActions.blogLinkNotDisplayedInCA(), "Verify that the Blog link is not displayed in CA store");
        }
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        if (user.equalsIgnoreCase("guest")) {
            shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions);
        } else {
            shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        }
        shippingPageActions.returnToBag(shoppingBagPageActions);
        AssertFail(footerActions.waitUntilElementDisplayed(footerActions.link_SizeChart), "verify Footer is displayed in empty shopping bag");
    }
}

