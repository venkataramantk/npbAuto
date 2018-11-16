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
public class LoginToAccountModal extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(LoginToAccountModal.class);
    String emailAddressReg;
    private String password;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");

        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
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
    @Test(groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void validateCreateAccFromBagPage(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " Verify if guest user displayed with create account overlay and validate the error message present in the create account fields");
        List<String> loginErrMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "loginErrMsgTab", "expectedContent"));
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        addToBagBySearching(itemsAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickWLGuestUser(loginPageActions), "Verify if the login drawer is displayed on clicking the WL icon for guest user");
        AssertFailAndContinue(loginPageActions.validateErrorMessagesTab(loginErrMsg.get(0), loginErrMsg.get(1)), "Validate Error message when Tab the Field");
        AssertFailAndContinue(loginPageActions.validateBtnLoginEnableDisable(emailAddressReg, password), "Validated if the Login button gets enabled only when credentials are entered.");
        AssertFailAndContinue(loginPageActions.clickCreateAccountLink(createAccountActions), "Verify if the create account drawer is displayed on clicking the create acc button in login drawer");
        loginPageActions.clickCloseLoginModal();
        AssertFailAndContinue(shoppingBagPageActions.clickLoginFromEspotLnk(loginPageActions), "Click on login link from MPR espot notification");
        AssertFailAndContinue(loginPageActions.validateErrorMessagesTab(loginErrMsg.get(0), loginErrMsg.get(1)), "Validate Error message when Tab the Field");
        AssertFailAndContinue(loginPageActions.clickCreateAccountLink(createAccountActions), "Verify if the create account drawer is displayed on clicking the create acc button in login drawer");
        loginPageActions.clickCloseLoginModal();
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void validateLoginInDrawerFromShoppingBag(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if guest user displayed with login overlay and check the error message and also validate the password field in the login overlay");
        List<String> loginErrMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "loginErrMsgTab", "expectedContent"));
        List<String> loginErr = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Login", "loginErr", "expectedContent"));
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        addToBagBySearching(itemsAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Verify if login drawer is displayed to the guest user on clicking checkout button in shopping bag page");
        AssertFailAndContinue(loginPageActions.validateErrorMessagesTab(loginErrMsg.get(0), loginErrMsg.get(1)), "Validate Error message when Tab the Field");
        AssertFailAndContinue(loginDrawerActions.showHideLinkPassword("P@ssw0rd"), "Validated the actions of the Show/Hide link");
        loginPageActions.closeLoginModal();

        AssertFailAndContinue(shoppingBagPageActions.clickWLIconAsGuestUser(loginPageActions), "Verify if the login drawer is displayed on clicking the WL icon for guest user");
        AssertFailAndContinue(loginPageActions.showHideLinkPassword("P@ssw0rd"), "Validated the actions of the Show/Hide link");
        loginPageActions.closeLoginModal();

        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Verify if the login drawer is displayed on clicking the checkout button");
        AssertFailAndContinue(loginPageActions.enterValidUNAndInvalidPwd(loginErr.get(0)), "verified error message while logging in to Add to Wish List login modal with valid username and invalid password");
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void loginOverlayGuest(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if guest user displayed with login overlay and validate the content present in the login overlay on moving prod to WL adn clicking checkout button");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        addToBagBySearching(itemsAndQty);
//        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickWLIconAsGuestUser(loginPageActions), "Verify if user redirect to Login page on clicking the Wishlist on the header from shopping bag page");
        if(store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(loginDrawerActions.validateLoginDrawer(), "Validating the fields present in wish list login page");
        }
        if(store.equalsIgnoreCase("CA")){
            AssertFailAndContinue(loginDrawerActions.validateLoginDrawerCA(), "Validating the fields present in wish list login page");
        }
        loginPageActions.clickCloseLoginModal();
//        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Click on login link from the header and verify if user displayed with login drawer");
        AssertFailAndContinue(loginDrawerActions.validateLoginDrawer(), "validate the fields present in the login drawer");
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions);
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Verify if user redirect to shipping checkout page on clicking continue as guest user button");
    }

    @Test(enabled = false)//#addShippingAddressReg - disabling because this scenario is covered already
    public void checkOutRegUser() throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if the register user navigate to checkout pages from the shopping bag page ");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Verify if user redirect to checkout page on clicking the checkout button from shopping bag page");
        AssertFailAndContinue(shippingPageActions.validateFieldsInShippingPage(), "Verify if user displayed with the fields in checkout shipping page");
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void loginFromHeaderInShoppingBag(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the guest user can able to login from the header menu in shopping bag page and validate the count present in the bag ");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.checkCouponDisplay(),"Verify the coupons are getting displayed after login");
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        AssertFailAndContinue(shoppingBagPageActions.validateTitleAndBagCount(), "Validate the bag count and the title count should be same");
    }

    @Parameters(storeXml)
    @Test(groups = {SHOPPINGBAG, GUESTONLY,PROD_REGRESSION})
    public void loginFromEspotLinkInShoppingBag(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if the guest user can able to login from the espot icon present in shopping bag page and validate the count present in the bag");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickLoginFromEspotLnk(loginPageActions), "Click on login link from MPR espot notification");
        AssertFailAndContinue(loginDrawerActions.validateLoginPage(), "validate the fields present in the login drawer");
        AssertFailAndContinue(loginPageActions.loginInBagPageOverlay(emailAddressReg, password), "enter valid credentials and click on login button");
//        AssertFailAndContinue(shoppingBagPageActions.validateTitleAndBagCount(),"Validate the bag count and the title count should be same");
        headerMenuActions.clickLoggedLink(myAccountPageDrawerActions);
        AssertFailAndContinue(myAccountPageDrawerActions.checkCouponDisplay(),"Verify the coupons are getting displayed after login");
        headerMenuActions.closeTheVisibleDrawer();
    }
}
