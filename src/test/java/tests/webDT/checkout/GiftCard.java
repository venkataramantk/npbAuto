package tests.webDT.checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;


/**
 * Created by Suresh on 3/6/2018.
 * Modified by Jagadeesh on 01/05/2018
 */

public class GiftCard extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    private String password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US") && user.equalsIgnoreCase("registered")) {
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");

        } else if (store.equalsIgnoreCase("CA") && user.equalsIgnoreCase("registered")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcel, "Password");
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
        }
        driver.navigate().refresh();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(priority = 0, groups = {CHECKOUT, GIFTCARDPRODUCT, SMOKE,PROD_REGRESSION}, dataProvider = dataProviderName)
    public void validateGiftCardLandingPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("jagadeesh");
        setRequirementCoverage("validateGiftCardLandingPage");

        List<String> helpURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "helpCenter", "URLValue"));
        if (user.equalsIgnoreCase("registered"))
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        homePageActions.click(homePageActions.link_GiftCards);
        AssertFailAndContinue(giftCardsPageActions.giftCardValidations(store), "Verify all the gift card links are displayed in the Gift Card page");
        AssertFailAndContinue(giftCardsPageActions.TermsConditionToolTip(), "Verify Terms and condition tool tip appears fr ");
        if (store.equalsIgnoreCase("US"))
            AssertFailAndContinue(giftCardsPageActions.eGiftCardValidations(), "Verify all the e-gift card links are displayed in the Gift Card page");
        giftCardsPageActions.click(giftCardsPageActions.giftCardFaqLink);
        AssertFailAndContinue(driver.getCurrentUrl().contains(helpURLValidation.get(0)), "Verify Click on FAQ link navigate to FAQ page");
    }

    @Test(priority = 1, groups = {CHECKOUT, GIFTCARDPRODUCT, SMOKE,PROD_REGRESSION}, dataProvider = dataProviderName)
    public void validateEGiftCardsBalance(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("jagadeesh");
        setRequirementCoverage("The following verify gift card balance for " + user + " user in " + store + " store");
        if (user.equalsIgnoreCase("registered"))
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        List<String> ErrMsgValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("GiftCard", "GiftCardOptions", "ErrorMessage"));
        homePageActions.click(giftCardsPageActions.link_GiftCardBalance);
        AssertFailAndContinue(giftCardsPageActions.giftCardValidations(store), "Verify all the gift card links are displayed in the Gift Card page");
        AssertFailAndContinue(giftCardsPageActions.validateGiftCardBalance(), "Verify all the links are displayed in the GiftCard Balance Modal");
        AssertFailAndContinue(giftCardsPageActions.giftCardFieldValidations(ErrMsgValidation.get(0), ErrMsgValidation.get(1), ErrMsgValidation.get(2)), "Verify all the links are displayed in the GiftCard Balance Modal");
    }

    @Test(priority = 2, groups = {CHECKOUT, GIFTCARDPRODUCT, SMOKE, USONLY,PROD_REGRESSION}, dataProvider = dataProviderName)
    public void validateEGiftCardsPDPPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("The following scenario validates Giftcard in PDP page as a " + user + "user in" + store + " store"
        + "DT-44369");
        if (user.equalsIgnoreCase("registered"))
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        List<String> helpURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "helpCenter", "URLValue"));
        homePageActions.click(giftCardsPageActions.link_GiftCardBalance);
        AssertFailAndContinue(giftCardsPageActions.eGiftCardNavigation(helpURLValidation.get(3)), "Verify user redirected to the eGift Card PDP page ");
        AssertFailAndContinue(giftCardsPageActions.giftCardPDPValidations(), "Verify all the gift card links are displayed in the Gift Card page");
        AssertFailAndContinue(giftCardsPageActions.giftCardValueUpdate(), "Verify all the gift card links are displayed in the Gift Card page");
        AssertFailAndContinue(productDetailsPageActions.isQtyDropDownAllValuesAvailable(), "Verify all the gift card links are displayed in the Gift Card page");
    }
    @Test(dataProvider = dataProviderName,groups = {CHECKOUT, GIFTCARDPRODUCT, SMOKE, USONLY,PROD_REGRESSION})
    public void editGiftCard(@Optional("US") String store, @Optional("guest") String user){
        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify the user is able to edit the GC added to bag and also verify the Drop down displayed for GC");

        if(user.equalsIgnoreCase("registered")){
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg,password);
        }
        footerActions.click(footerActions.link_GiftCards);
        AssertFailAndContinue(giftCardsPageActions.clickSendAGiftCardsButton(productDetailsPageActions),"Click on the send an E-gift card button");
        AssertFailAndContinue(giftCardsPageActions.addGiftCardToBag(),"Add an gift card to bag from GC PDP");
        headerMenuActions.staticWait(4000);
        AssertFailAndContinue(giftCardsPageActions.editAnGiftCardFromDrawer(),"Verify the user is displayed with different list of GC and user is able to edit it");
        shoppingBagDrawerActions.clickViewBagLink(shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.editAnGiftCardFromSBPage(0),"Verify that the user is displayed with Gift card list in the edit drop down in SB page");
    }
}
