package tests.mobileDT.myAccount.paymentsAndGiftCards;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

/**
 * Created by JKotha on 14/12/2017.
 */
//DT-504No
//@Test(singleThreaded = true)
public class GiftCardsAndMerchandiseCredits extends MobileBaseTest {
    String email = UiBaseMobile.randomEmail();
    String password = "";
    WebDriver mobileDriver;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            createAccount(rowInExcelUS, email);
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            createAccount(rowInExcelCA, email);
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

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void giftCardUiTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("UI validations for add a Gift card page in" + store + " as a" + user + " user");

        String gc = getmobileDT2CellValueBySheetRowAndColumn("GiftCard", "usgc1", "Card");

        if (store.equalsIgnoreCase("CA"))
            gc = getmobileDT2CellValueBySheetRowAndColumn("GiftCard", "usca1", "Card");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");

        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("GCBTN"), "Verify ADD A GIFT CARD button is displayed");

        AssertFailAndContinue(mmyAccountPageActions.clickAddAGiftCardBtn(), "Click Add a gift card and verify Gift card add page is displayed");

        //field validations
        AssertFailAndContinue(mmyAccountPageActions.verifyGiftCardUi(), "validated all fields in Add a gift card page");

        mmyAccountPageActions.fillMyAccountField("GIFTCARDNO", "");
        mmyAccountPageActions.fillMyAccountField("GIFTCARDPIN", "");
        mmyAccountPageActions.fillMyAccountField("GIFTCARDNO", "");

        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("GIFTCARDNO").contains("Please enter a valid gift card number"), "Verify Error Message for empty gift card");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("GIFTCARDPIN").contains("Please enter your gift card pin number"), "Verify Error Message for empty gift card PIN");

        if (store.equalsIgnoreCase("CA")) {
            gc = getmobileDT2CellValueBySheetRowAndColumn("GiftCard", "cagc1", "Card");
        }

        mmyAccountPageActions.fillMyAccountField("GIFTCARDNO", gc);
        mmyAccountPageActions.fillMyAccountField("GIFTCARDPIN", "234");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("GIFTCARDPIN").contains("Please enter your gift card pin number"), "Verify Error Message for Pin doesn't meet the length");

        mmyAccountPageActions.fillMyAccountField("GIFTCARDPIN", "234333");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("GIFTCARDPIN").contains("Please enter your gift card pin number"), "Verify Error Message for Pin which exceeds the length");

        mmyAccountPageActions.clickCancelBtn();
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("GCBTN"), "Verify ADD A GIFT CARD button is displayed");
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }
}
