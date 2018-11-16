package tests.mobileDT.outfits;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Test(singleThreaded = true)
public class OutfitTests extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = "", password;

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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, OUTFITS, "DT-34042"})
    public void baby_outfitTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As A " + user + " in " + store + " store, verify outfits are displayed under baby " +
                "2. Validate all fields in Outfits PDP page" +
                "DT-41922, DT-37261, DT-37263, DT-37265, DT-35369, DT-35372, DT-35374, DT-35388" +
                "DT-35389, DT-35391, DT-35401, DT-35376, DT-35402, DT-35393, DT-35394, DT-35383" +
                "DT-35384, DT-35482, DT-35464, DT-35464");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }

        panCakePageActions.selectLevelOneCategory("Baby");
        AssertFailAndContinue(panCakePageActions.storeL2Categories(mhomePageActions).contains("Outfits"), "Verify baby sub department contains Outfits category");

        panCakePageActions.selectLevelTwoCategory("Outfits");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        AssertFailAndContinue(mcategoryDetailsPageAction.getCurrentBreatcrumb().contains("Outfits"), "Click on Outfits category and verify Outfits page is displayed");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mfooterActions.signuppage), "verify signup is displayed in outfits footer");
        AssertFailAndContinue(mcategoryDetailsPageAction.validatedOutfitsProductWidth(), "verify one product per row is displayed in outfits page");

        addOutFitsToBag(searchKeywordAndQty, "Baby");

        AssertFailAndContinue(mproductDetailsPageActions.verifyProductTextInPDP(), "Verify Outfits PDP page is displayed with Product name in bold");
        AssertFailAndContinue(mproductDetailsPageActions.clickCompleteTheLook(), "Click on the complete the Look link and verify ");

        AssertFailAndContinue(mproductDetailsPageActions.validateOutfitFields(), "Verify Outfits PDP page is displayed with Product name in bold");
        AssertFailAndContinue(mproductDetailsPageActions.openSizeChardFromOutfits(), "Open Size chart link");
        mproductDetailsPageActions.closeSizeChartmodal();
        if (user.equalsIgnoreCase("Registered")) {
            AssertFailAndContinue(mproductDetailsPageActions.clickAddToWishListAsRegistered(), "Click wish list and verify item is added to wish list");
        }
        AssertFailAndContinue(mproductDetailsPageActions.clickProductDetailsLink(), "CLick on Product details link and verify user is navigated to specific product PDP page");
        AssertFailAndContinue(mproductDetailsPageActions.validateProductDescription(), "");
        AssertFailAndContinue(mproductDetailsPageActions.minimiseCompleteLook(), "Verify that the user is able to close the section manually.");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, OUTFITS})
    public void validateOutfitsPageTitle(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As A " + user + " in " + store + " store, verify outfits are displayed under baby " +
                " Validate outfits page title and home page title" +
                ", \"DT-33352\", DT-35364");
        String girlOutfits = getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-girlOutfits", "Url"),
                boyoutfits = getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-boysOutfits", "Url"),
                tboutfits = getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-toddler-boy-outfits", "Url"),
                tgoutfits = getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-toddler-girl-outfits", "Url"),
                babyoutfits = getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-baby-outfits", "Url");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }

        if (store.equalsIgnoreCase("US")) {
            String appurl = EnvironmentConfig.getApplicationUrl();
            AssertFailAndContinue(validatePageURL(EnvironmentConfig.getApplicationUrl()), "Validate " + store.toUpperCase() + "  store home page title");
            mheaderMenuActions.navigateTo(appurl.replace("/home", boyoutfits));
            AssertFailAndContinue(validatePageTitle(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-boysOutfits", "value")), "verify page title of boys outfits");

            mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
            mheaderMenuActions.navigateTo(appurl.replace("/home", girlOutfits));
            AssertFailAndContinue(validatePageTitle(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-girlOutfits", "value")), "verify page title of girls outfits");

            mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
            mheaderMenuActions.navigateTo(appurl.replace("/home", tboutfits));
            AssertFailAndContinue(validatePageTitle(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-toddler-boy-outfits", "value")), "verify page title of toddler boys outfits");

            mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
            mheaderMenuActions.navigateTo(appurl.replace("/home", tgoutfits));
            AssertFailAndContinue(validatePageTitle(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-toddler-girl-outfits", "value")), "verify page title of toddler girls outfits");

            //mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
            //mheaderMenuActions.navigateTo(appurl.replace("/home", babyoutfits));
            //AssertFailAndContinue(validatePageTitle(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-girlOutfits", "value")), "verify page title of girls outfits");
        }

        /*if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(validatePageURL(EnvironmentConfig.getCA_ApplicationUrl()), "Validate " + store.toUpperCase() + "  store home page title");

            String appurl = EnvironmentConfig.getApplicationUrl();
            AssertFailAndContinue(validatePageURL(EnvironmentConfig.getCA_ApplicationUrl()), "Validate " + store.toUpperCase() + "  store home page title");
            mheaderMenuActions.navigateTo(appurl.replace("/home", boyoutfits));
            AssertFailAndContinue(validatePageTitle(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-boysOutfits", "value")), "verify page title of boys outfits");

            mhomePageActions.navigateTo(EnvironmentConfig.getCA_ApplicationUrl());
            mheaderMenuActions.navigateTo(appurl.replace("/home", girlOutfits));
            AssertFailAndContinue(validatePageTitle(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-girlOutfits", "value")), "verify page title of girls outfits");

            mhomePageActions.navigateTo(EnvironmentConfig.getCA_ApplicationUrl());
            mheaderMenuActions.navigateTo(appurl.replace("/home", tboutfits));
            AssertFailAndContinue(validatePageTitle(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-toddler-boy-outfits", "value")), "verify page title of toddler boys outfits");

            mhomePageActions.navigateTo(EnvironmentConfig.getCA_ApplicationUrl());
            mheaderMenuActions.navigateTo(appurl.replace("/home", tgoutfits));
            AssertFailAndContinue(validatePageTitle(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-toddler-girl-outfits", "value")), "verify page title of toddler girls outfits");

            //mhomePageActions.navigateTo(EnvironmentConfig.getCA_ApplicationUrl());
            //mheaderMenuActions.navigateTo(appurl.replace("/home", babyoutfits));
            //AssertFailAndContinue(validatePageTitle(getmobileDT2CellValueBySheetRowAndColumn("CanonicalURL", "us-girlOutfits", "value")), "verify page title of girls outfits");

        }*/
    }


    @Test(priority = 2, dataProvider = dataProviderName, groups = {PDP, RECOMMENDATIONS})
    public void valitedRecommendadtions_outfits(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Guest/Registered/Remembered user is in US/CA/INT En/Es/Fr store,When the user is in Shopping bag, verify user is able to add recommendations products to fav");

        panCakePageActions.navigateToMenu("LOGIN");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }
        //DT-37539 & DT-37542
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addOutFitsToBag(searchKeywordAndQty, "Baby");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mfooterActions.fav_recommAsGuest(mloginPageActions), "Click fav icon on first recommended product verify login page is displayed");
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mfooterActions.fav_recommAsReg(), "Click fav icon on first recommended product ");
        }
    }
}
