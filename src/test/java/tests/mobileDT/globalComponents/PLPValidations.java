package tests.mobileDT.globalComponents;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;

/**
 * Created By Pooja on 16 july,2018
 */
public class PLPValidations extends MobileBaseTest {


    WebDriver mobileDriver;
    String email, password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.deleteAllCookies();
        email = "globalsearch@yopmail.com";
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {PLP, PROD_REGRESSION})
    public void plpValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("1. Verify following icons on product tile:\n" +
                "1. Add to bag\n" +
                "2. Favorite Icon\n" +
                "3. Pick in store\n" +
                "2.Click \"Add to Bag\" icon and verify following items\n" +
                "1. Select a color\n" +
                "2. Select a size\n" +
                "3. Select a quantity\n" +
                "4. Add to bag button.\n" +
                "5. View Product Details (link).\n" +
                "3. Verify the international pricing on PLP" +
                "DT-43048");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");
        }
        panCakePageActions.navigateToPlpCategory("girl", null, null, "EXTENDED SIZES");
        //AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementsAreDisplayed(mcategoryDetailsPageAction.pickupstoreBtn, 10), "Verify Pick up in Store button is displayed for Product Card");
        AssertFailAndContinue(mproductCardViewActions.verifyProdCardItems(mcategoryDetailsPageAction), "Verify Add To Bag Button,Add to Fav Icon,Offer Price,Original Price,Product Image,Product Name are displayed on Product Card");

        AssertFailAndContinue(mproductCardViewActions.verifyOfferPriceCurrency(mcategoryDetailsPageAction, "$"), "Verify Currency of the Offer Price is $");

        mcategoryDetailsPageAction.openProdCardViewByPos(1);
        AssertFailAndContinue(mcategoryDetailsPageAction.verifyBagFlipItems(), "Verify Color and Size swatches,qunatity dd,add to bag button and product details link are on Bag Flip displayed");
        AssertFailAndContinue(mcategoryDetailsPageAction.closeBagFlip(), "Close Bag Flip by clicking on cross button");

        //This validation already performing in levelOneCategoriesTest
        //panCakePageActions.selectLevelOneCategory("Girl");
        //panCakePageActions.selectLevelTwoCategory("Outfits");
        //AssertFailAndContinue(panCakePageActions.getPlpCategory().equalsIgnoreCase("Girl > Outfits"), "verify user is redirected to associated L2 category PLP page and bread crumb is displayed on PLP page after clicking L2 Category link ");
        //AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementsAreDisplayed(mcategoryDetailsPageAction.shopTheLook, 10), "Verify Shop The Look Tiles are displayed on Outfits Page");


        //panCakePageActions.selectLevelTwoCategory("Extended Sizes");
        //AssertFailAndContinue(mproductCardViewActions.verifyProdCardItems(mcategoryDetailsPageAction), "Verify Add To Bag Button,Add to Fav Icon,Offer Price,List Price,Product Image,Product Title are displayed on Product Card");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementDisplayed(mcategoryDetailsPageAction.tag), "Verify Badge displayed on PLP");
        AssertFailAndContinue(mcategoryDetailsPageAction.getAvailableBadges().contains("EXTENDED SIZES"), "Verify Extended Sizes Inline Badge is displayed on PLP");
        AssertFailAndContinue(mcategoryDetailsPageAction.waitUntilElementsAreDisplayed(mcategoryDetailsPageAction.colourSwatch, 10), "Verify Colour Swatches displayed on PLP");

        AssertFailAndContinue(mcategoryDetailsPageAction.clickAddToWishListAsGuest(mloginPageActions, 1), "Click fav as guest user and verify login popup is displayed");
        mMyPlaceRewardsActions.click(mloginPageActions.createAccountBtnFromCheckout);
        AssertFailAndContinue(mcreateAccountActions.getText(mcreateAccountActions.termsAndConditions).equals(getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "termsAndCondition", "Value")), "Verify Terms and Condition text in Create Account Page from MPR create account");
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
