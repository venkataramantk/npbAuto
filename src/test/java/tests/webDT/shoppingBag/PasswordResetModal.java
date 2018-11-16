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
 * Created by AbdulazeezM on 3/24/2017.
 */
public class PasswordResetModal extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(PasswordResetModal.class);
    String emailAddress;
    private String password;

    @Parameters({storeXml})
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

    @Parameters(storeXml)
    @Test(priority = 1, groups = {SHOPPINGBAG,GUESTONLY, PROD_REGRESSION})
    public void loginFromShoppingBagPageGuest(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if guest user displayed with login overlay and validate the fields on clicking the login link, checkout button");
        List<String> successMsgContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("ForgotPasswordContent", "successMsgContent", "expectedSuccessMsg"));
        List<String> errorMsgContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("ForgotPasswordContent", "errorMessage", "expectedMessage"));
        List<String> email = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "messageOnForm", "invalidEmail"));

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Click on checkout button and verify if the login drawer is displayed to the user");
        AssertFailAndContinue(loginDrawerActions.validateLoginPage(), "validate the login page");
        if (store.equalsIgnoreCase("US")){
            AssertFailAndContinue(loginDrawerActions.clickCreateOneLink(createAccountActions),"Validating for Create one link on login Modal");
            loginPageActions.closeLoginModal();
            AssertFailAndContinue(shoppingBagPageActions.clickLoginFromEspotLnk(loginPageActions), "Click on login link from the loyalty espot");
            AssertFailAndContinue(loginDrawerActions.validateLoginPage(), "validate the login page");
        }

        AssertFailAndContinue(loginPageActions.clickForgotPassword(forgotYourPasswordPageActions), "Verify user displayed with the reset password drawer");
        AssertFailAndContinue(forgotYourPasswordPageActions.validateForgotPwdDrawer(), "forgot Password drawer validation: back to login, email field, reset pwd button, create acount button and text");
        AssertFailAndContinue(forgotYourPasswordPageActions.clickCreateOneAndVerify(createAccountActions), "Clicking on Create Account link displays the create account form");
        AssertFailAndContinue(forgotYourPasswordPageActions.returnToLoginLink(loginDrawerActions), "Verify if user redirect to the login drawer");
        loginPageActions.clickForgotPassword(forgotYourPasswordPageActions);
        AssertFailAndContinue(forgotYourPasswordPageActions.successMsgWithValidID(successMsgContent.get(0), emailAddress), "Verify the success message after entering the valid email ID");
        forgotYourPasswordPageActions.returnToLoginLink(loginDrawerActions);
        AssertFailAndContinue(loginPageActions.clickForgotPassword(forgotYourPasswordPageActions), "Verify user displayed with the reset password drawer");
        AssertFailAndContinue(forgotYourPasswordPageActions.errorMsgWithInvalidID(email.get(0), errorMsgContent.get(0)), "Verify the error message when user entered unregistered TCP account");
        AssertFailAndContinue(forgotYourPasswordPageActions.closeLoginOverlay(shoppingBagPageActions), "Verify if user displayed with shopping bag page after closing the forget password overlay");
    }

    @Parameters(storeXml)
    @Test(priority = 0, groups = {SHOPPINGBAG, REGISTEREDONLY, PROD_REGRESSION, FAVORITES})
    public void overLoadingSB_FromFavList_ErrMsg(@Optional("US") String store) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store + " store - Verify whether the user is displayed with an error message in the front end when an item is added to the SB from favorites list, when the Bag already has 15 qty");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, password);

        addToBagBySearchingAndStayingInPDP(searchKeywordAndQty);
        productDetailsPageActions.updateQty("15");
        productDetailsPageActions.clickAddToBag();
        productDetailsPageActions.scrollToTheTopHeader(productDetailsPageActions.addToWishList);
        productDetailsPageActions.addToWLAsReg();
        headerMenuActions.clickWishListAsRegistered(favoritePageActions);
        AssertFailAndContinue(favoritePageActions.clickAddToBagByPosAndVerifyError(1), "verifying for an error, when an item is added to the SB from favorites list, when the bag is already has 15 qty");

    }
}
