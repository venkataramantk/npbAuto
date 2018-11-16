package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

import java.util.List;

//@Test(singleThreaded = true)
public class CategoryTests extends MobileBaseTest {
    WebDriver mobileDriver;
    String email;
    String password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
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

    //@Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, PRODUCTION})
    public void levelOneCategoriesTest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("1. As a Guest/Registered/Remembered in US/CA/INT En/Es/Fr user open any category landing pages.Verify if the user is displayed with the corresponding content.Also ensure that the user is not taken to 404 page.\n" +
                "2. As a Guest/Registered/Remembered user is in US/CA/INT En/Es/Fr store,hover over shoes/accessories department links from home/PDP/PLP,clicks on any L2 category,navigates to category landing page,Verify if the \"|department name\" is not displayed in breadcrumbs.\n" +
                "3. As a Guest/Registered/Remembered user in US/CA/INT En/Es/Fr store navigate to L2 pages and verify if the user is not taken to 404 page.");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-38308
        panCakePageActions.selectLevelOneCategory("Girl");
        AssertFailAndContinue(panCakePageActions.verifyL2CatNotRepeating(), "Verifying whether L2 Categories are not repeating for Girl");
        AssertFailAndContinue(panCakePageActions.getText(panCakePageActions.lTwoHeader).equalsIgnoreCase("Girl"), "Verify L2 & L3 sub-category for Girl department");
        AssertFailAndContinue(panCakePageActions.verifySectionHeader().equalsIgnoreCase("Girl"), "Verify Header for Girl department");
        panCakePageActions.selectRandomLevelTwoCategory();
        AssertFailAndContinue(panCakePageActions.getPlpCategory().contains("Girl"), "Verify Girl Department page is displayed from L2 & L3 category");
        //DT-38310
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.validBreadcrumb, 10), "Verify | symbol not displayed in the Department Name Breadcrumbs");

        //DT-38311
        AssertFailAndContinue(mcategoryDetailsPageAction.verifyElementNotDisplayed(mheaderMenuActions.pageNotFound_Page), "Verify 404 Error is not displayed");

        mheaderMenuActions.deleteAllCookies();
        panCakePageActions.selectLevelOneCategory("Toddler Girl");
        AssertFailAndContinue(panCakePageActions.verifyL2CatNotRepeating(), "Verifying whether L2 Categories are repeating for Toddler Girl");
        AssertFailAndContinue(panCakePageActions.verifySectionHeader().equalsIgnoreCase("Toddler Girl"), "Verify Header for Toddler Girl department");
        panCakePageActions.selectRandomLevelTwoCategory();
        AssertFailAndContinue(panCakePageActions.getPlpCategory().contains("Toddler Girl"), "Verify Toddler Girl Department page is displayed from L2 & L3 category");
        //DT-38310
        AssertFailAndContinue(panCakePageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.validBreadcrumb, 10), "Verify | symbol not displayed in the Department Name Breadcrumbs");

        //DT-38311
        AssertFailAndContinue(panCakePageActions.verifyElementNotDisplayed(mheaderMenuActions.pageNotFound_Page), "Verify 404 Error is not displayed");

        mheaderMenuActions.deleteAllCookies();
        panCakePageActions.selectLevelOneCategory("Boy");
        AssertFailAndContinue(panCakePageActions.verifyL2CatNotRepeating(), "Verifying whether L2 Categories are not repeating for Boy");
        AssertFailAndContinue(panCakePageActions.verifySectionHeader().equalsIgnoreCase("Toddler Girl"), "Verify Header for Toddler Girl department");

        panCakePageActions.selectRandomLevelTwoCategory();
        AssertFailAndContinue(panCakePageActions.getPlpCategory().contains("Boy"), "Verify Boy Department page is displayed from L2 & L3 category");
        //DT-38310
        AssertFailAndContinue(panCakePageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.validBreadcrumb, 5), "Verify | symbol not displayed in the Department Name Breadcrumbs");

        //DT-38311
        AssertFailAndContinue(panCakePageActions.verifyElementNotDisplayed(mheaderMenuActions.pageNotFound_Page), "Verify 404 Error is not displayed");

        mheaderMenuActions.deleteAllCookies();
        panCakePageActions.selectLevelOneCategory("Toddler Boy");
        AssertFailAndContinue(panCakePageActions.verifyL2CatNotRepeating(), "Verifying whether L2 Categories are repeating for Toddler Boy");
        AssertFailAndContinue(panCakePageActions.verifySectionHeader().equalsIgnoreCase("Toddler Boy"), "Verify Header for Toddler Boy department");
        panCakePageActions.selectRandomLevelTwoCategory();
        AssertFailAndContinue(panCakePageActions.getPlpCategory().contains("Toddler Boy"), "Verify Toddler Boy Department page is displayed from L2 & L3 category");
        //DT-38310
        AssertFailAndContinue(panCakePageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.validBreadcrumb, 5), "Verify | symbol not displayed in the Department Name Breadcrumbs");

        //DT-38311
        AssertFailAndContinue(panCakePageActions.verifyElementNotDisplayed(mheaderMenuActions.pageNotFound_Page), "Verify 404 Error is not displayed");

        mheaderMenuActions.deleteAllCookies();
        panCakePageActions.selectLevelOneCategory("Baby");
        AssertFailAndContinue(panCakePageActions.verifyL2CatNotRepeating(), "Verifying whether L2 Categories are repeating for Baby");
        AssertFailAndContinue(panCakePageActions.verifySectionHeader().equalsIgnoreCase("Baby"), "Verify Header for Baby department");
        panCakePageActions.selectRandomLevelTwoCategory();
        AssertFailAndContinue(panCakePageActions.getPlpCategory().contains("Baby"), "Verify Baby Department page is displayed from L2 & L3 category");
        //DT-38310
        AssertFailAndContinue(panCakePageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.validBreadcrumb, 5), "Verify | symbol not displayed in the Department Name Breadcrumbs");

        //DT-38311
        AssertFailAndContinue(panCakePageActions.verifyElementNotDisplayed(mheaderMenuActions.pageNotFound_Page), "Verify 404 Error is not displayed");

        if (user.equalsIgnoreCase("US")) {
            mheaderMenuActions.deleteAllCookies();
            panCakePageActions.selectLevelOneCategory("Shoes");
            AssertFailAndContinue(panCakePageActions.verifyL2CatNotRepeating(), "Verifying whether L2 Categories are repeating for Shoes");
            AssertFailAndContinue(panCakePageActions.verifySectionHeader().equalsIgnoreCase("Shoes"), "Verify Header for Shoes department");
            panCakePageActions.selectRandomLevelTwoCategory();
            AssertFailAndContinue(panCakePageActions.getPlpCategory().contains("Shoes"), "Verify Shoes Department page is displayed from L2 & L3 category");
            //DT-38310
            AssertFailAndContinue(panCakePageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.validBreadcrumb, 5), "Verify | symbol not displayed in the Department Name Breadcrumbs");

            //DT-38311
            AssertFailAndContinue(panCakePageActions.verifyElementNotDisplayed(mheaderMenuActions.pageNotFound_Page), "Verify 404 Error is not displayed");

            mheaderMenuActions.deleteAllCookies();
            panCakePageActions.selectLevelOneCategory("Accessories");
            AssertFailAndContinue(panCakePageActions.verifyL2CatNotRepeating(), "Verifying whether L2 Categories are repeating for Accessories");
            AssertFailAndContinue(panCakePageActions.verifySectionHeader().equalsIgnoreCase("Accessories"), "Verify Header for Accessories department");
            panCakePageActions.selectRandomLevelTwoCategory();
            AssertFailAndContinue(panCakePageActions.getPlpCategory().contains("Accessories"), "Verify Accessories Department page is displayed from L2 & L3 category");
            //DT-38310
            AssertFailAndContinue(panCakePageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.validBreadcrumb, 5), "Verify | symbol not displayed in the Department Name Breadcrumbs");

            //DT-38311
            AssertFailAndContinue(panCakePageActions.verifyElementNotDisplayed(mheaderMenuActions.pageNotFound_Page), "Verify 404 Error is not displayed");
        }

        //Commenting Clearance validations as Clearance is disable sometimes
        /*panCakePageActions.selectLevelOneCategory("Clearance");
        AssertFailAndContinue(panCakePageActions.getText(panCakePageActions.lTwoHeader).equalsIgnoreCase("Clearance"), "Verify L2 & L3 sub-category for clearance department");
        panCakePageActions.selectRandomLevelTwoCategory();
        AssertFailAndContinue(panCakePageActions.getText(msearchResultsPageActions.currentSearch).contains("Clearance"), "Verify clearance Department page is displayed from L2 & L3 category");
        //DT-38310
        AssertFailAndContinue(panCakePageActions.waitUntilElementDisplayed(mcategoryDetailsPageAction.validBreadcrumb, 5), "Verify | symbol not displayed in the Department Name Breadcrumbs");

        //DT-38311
        AssertFailAndContinue(panCakePageActions.verifyElementNotDisplayed(mheaderMenuActions.pageNotFound_Page), "Verify 404 Error is not displayed");*/
    }

    //@Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, PRODUCTION})
    public void verifyDptCategoryMatchesPancake(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a Guest/Registered user navigate to the Department landing pages in mobile from the home page espot.Verify if the categories match with the pancake.Also ensure that clicking on the links the user is taken to the corresponding pages.");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-38303
        mhomePageActions.selectEspot("Girl", mheaderMenuActions);
        List<String> allCategories = panCakePageActions.getListText(mhomePageActions.subCategoriesLink);
        panCakePageActions.selectLevelOneCategory("Girl");
        panCakePageActions.verifyCategoriesMatchPancake(mhomePageActions, panCakePageActions, allCategories);
        panCakePageActions.selectRandomLevelTwoCategory();
        AssertFailAndContinue(panCakePageActions.getPlpCategory().contains("Girl"), "Verify Girl Department page is displayed from L2 & L3 category");
    }

    //@Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT})
    public void verifyL3NotMatchL2CatName(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a guest/registered/remembered user in US/CA/INT store, Verify if the L3 category Names are NOT repeated in the L2 category page");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-38340
        panCakePageActions.selectLevelOneCategory("Girl");
        List<String> allLevelTwoCategories = panCakePageActions.getListText(panCakePageActions.leveltwos);
        panCakePageActions.moveToLevelThreeCategory("Bottoms");
        List<String> allLevelThreeCategories = panCakePageActions.getListText(panCakePageActions.levelthrees);
        panCakePageActions.compareTextInTwoList(allLevelTwoCategories, allLevelThreeCategories);
    }

    //@Test(priority = 3, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT})
    public void verifyLoadingMoreNotOnCategoryPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a Guest/Registered/Remembered user is in US/CA/INT En/Es/Fr store, When the user is in the following department landing page,navigate to end of the page i.e there are no product/content to load, Verify if user is not displayed with text \"Loading More...\" Between the spotlights and the body copy \n" +
                "1.Boy\n" +
                "2.Toddler Boy\n" +
                "3.Girl\n" +
                "4.Toddler Girl\n" +
                "5.Baby\n" +
                "6.Clearance\n" +
                "7.Accessories\n" +
                "8.Shoes");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        //DT-38333
        panCakePageActions.selectLevelOneCategory("Girl");
        panCakePageActions.selectRandomLevelTwoCategory();
        AssertFailAndContinue(mcategoryDetailsPageAction.clickL1Breadcrumb(mhomePageActions), "Click on L1 Category Breacrumb Link i.e. Girl");
        panCakePageActions.scrollToBottom();
        AssertFailAndContinue(panCakePageActions.verifyElementNotDisplayed(mhomePageActions.loadingMoreLink), "Verify Loading More Link is not displayed");
    }

    //@Test(priority = 4, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, USONLY})
    public void verifyGiftCardFunctionality(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("Click on Gift Card category on L1 category. User is able to navigate to PLP");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        panCakePageActions.navigateToMenu("BUYGIFTCARDS");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mgiftCardsPageActions.gcPLPBanner), "Verify Application Navigated to PLP as Banner is being displayed");

        mgiftCardsPageActions.goToGiftCardPage("giftCard");
        AssertFailAndContinue(mproductDetailsPageActions.clickAddToBag(), "Select Quantity and Value of Gift Card and Verify add to Bag Notification displayed");

        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Click Shopping Bag Icon on Header");
        AssertFailAndContinue(mshoppingBagPageActions.getProdPrice().equalsIgnoreCase("25.00"), "Value of the gift card verified.");
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
