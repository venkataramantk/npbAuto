package tests.mobileDT.shoppingBag;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.List;
import java.util.Map;

public class ShoppingBagUiTests extends MobileBaseTest {

    WebDriver mobileDriver;
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String email = UiBaseMobile.randomEmail(), password = "";
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                email = UiBaseMobile.randomEmail();
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, NARWHAL})
    public void validateTopRatedBadgeInSB(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " store verify Top rated badge is not displaying for Products in SB DT-43657");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }

        String searchKeyword = "TOP Rated";
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addBadgeProductToBag(searchKeywordAndQty, "Top Rated");

        AssertFailAndContinue(mshoppingBagPageActions.inlineBadgeDisplay.size() == 0, "verify Top rated badge is not displayed in SB");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, NARWHAL})
    public void validatedBadgesInSB(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " in verify all the badges are displayed in shopping bag " +
                "DT-33570, DT-15110, DT-38574");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        String online_exclusive = getmobileDT2CellValueBySheetRowAndColumn("Search", "Online", "Value");
        String glow_in_dark = getmobileDT2CellValueBySheetRowAndColumn("Search", "glowindark", "Value");
        String new_badge = getmobileDT2CellValueBySheetRowAndColumn("Search", "New", "AltValue");
        String clearance = getmobileDT2CellValueBySheetRowAndColumn("Search", "clearance", "Value");

        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");

        addBadgeProductToBag(getMapOfItemNamesAndQuantityWithCommaDelimited(online_exclusive, qty), online_exclusive);
        addBadgeProductToBag(getMapOfItemNamesAndQuantityWithCommaDelimited(glow_in_dark, qty), glow_in_dark);
        addBadgeProductToBag(getMapOfItemNamesAndQuantityWithCommaDelimited(new_badge, qty), new_badge);
        addBadgeProductToBag(getMapOfItemNamesAndQuantityWithCommaDelimited(clearance, qty), clearance);

        mheaderMenuActions.clickShoppingBagIcon();

        List<String> badges = mshoppingBagPageActions.getProductBadges();
        System.out.println(badges);
        AssertFailAndContinue(badges.contains(online_exclusive), "Verify " + online_exclusive + " badge is displaying for products in SB");
        AssertFailAndContinue(badges.contains(glow_in_dark), "Verify " + glow_in_dark + " badge is displaying for products in SB");
        AssertFailAndContinue(badges.contains(new_badge.toUpperCase()), "Verify " + new_badge.toUpperCase() + " badge is displaying for products in SB");
        AssertFailAndContinue(badges.contains(clearance.toUpperCase()), "Verify " + clearance.toUpperCase() + " badge is displaying for products in SB");

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, new_badge);
        mcategoryDetailsPageAction.javaScriptClick(driver, mcategoryDetailsPageAction.badgeProducts(new_badge).get(1));

        mproductDetailsPageActions.selectAColor(mproductDetailsPageActions);

        AssertFailAndContinue(mproductDetailsPageActions.getProductBadge().equalsIgnoreCase("NEW!"), "Only change in color is not displayed the NEw badge in PDP");
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
