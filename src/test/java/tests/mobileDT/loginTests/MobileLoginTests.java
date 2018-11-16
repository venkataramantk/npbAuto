package tests.mobileDT.loginTests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

/**
 * Created by JKotha on 29/11/2017.
 */
//User Story: DT-650
//@Listeners(MethodListener.class)
//@Test(singleThreaded = true)
public class MobileLoginTests extends MobileBaseTest {
    WebDriver mobileDriver;
    String email, password;
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            email = UiBaseMobile.randomEmail();
            createAccount(rowInExcelUS, email);
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            email = UiBaseMobile.randomEmail();
            mfooterActions.changeCountryByCountry("CANADA");
            createAccount(rowInExcelCA, email);
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        env = EnvironmentConfig.getEnvironmentProfile();
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, GUESTONLY, PRODUCTION})
    public void loginPageUiValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("UI validations for Login page");
        panCakePageActions.navigateToMenu("LOGIN");


        AssertFailAndContinue(mloginPageActions.validateLoginPage(), "validate all fields in login Page");
        String emptyEmailErr = getmobileDT2CellValueBySheetRowAndColumn("Login", "EmailEmpty", "expectedContent");
        String emptyPwdErr = getmobileDT2CellValueBySheetRowAndColumn("Login", "PasswordEmpty", "expectedContent");
        String invalidEmail = getmobileDT2CellValueBySheetRowAndColumn("Login", "messageOnForm", "invalidEmail");

        mloginPageActions.fillLoginFieldText("email", "");
        mloginPageActions.fillLoginFieldText("password", "");
        mloginPageActions.fillLoginFieldText("email", "");
        //DT-2705
        AssertFailAndContinue(mloginPageActions.getFieldLeverError("Email").contains(emptyEmailErr), "Verify Error Message for empty email in login Page");
        AssertFailAndContinue(mloginPageActions.getFieldLeverError("PASSWORD").contains(emptyPwdErr), "Verify Error Message for empty Password in login Page");

        AssertFailAndContinue(mloginPageActions.loginWithIncorrectCredentails(invalidEmail, invalidEmail), "validate error message for invalid user");

        //This step is failing when re-captcha is disabled, Hnce, commented out
        /*mloginPageActions.loginWithIncorrectCredentails(invalidEmail, invalidEmail);
        mloginPageActions.loginWithIncorrectCredentails(invalidEmail, invalidEmail);
        AssertFailAndContinue(mloginPageActions.validateRecaptch(), "Verify re-captcha is displayed in login after 3 failed attempts");*/

        AssertFailAndContinue(mloginPageActions.clickForgotPassword(), "Verify \"Forgot Password page\" displayed when user clicks on Forgot password link in login page");
        mforgotYourPasswordPageActions.clickBackToLogin(mloginPageActions);

        mloginPageActions.loginWithIncorrectCredentails("no" + email, invalidEmail);
        AssertFailAndContinue(mloginPageActions.verifyPageLevelError(), "Verify error message, when user tries to login using account that is not registered ");

        AssertFailAndContinue(!mloginPageActions.loginWithIncorrectCredentails(email, password), "Verify if user is redirected to \"My account\" page upon \"Login\" ");
        mloginPageActions.clickCloseLoginModal();
        String validUPCNumber = "";
        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        }
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "SearchTerm2", "ValidUPCNoCA");
        }

        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, validUPCNumber);
        mproductDetailsPageActions.selectASize();
        mproductDetailsPageActions.clickAddToBag();

        panCakePageActions.navigateToMenu("USER");
        mmyAccountPageDrawerActions.clickLogoutLink(mheaderMenuActions);
        AssertFailAndContinue(mheaderMenuActions.getBagCount() == 0, "Verify if mini card becomes zero when user logged out");

        panCakePageActions.navigateToMenu("LOGIN");
        AssertFailAndContinue(mloginPageActions.clickCreateAccountLink(), " Selects Create Account button from the login modal verify create account page is displayed");
        mloginPageActions.clickCloseLoginModal();
        panCakePageActions.clickMenuIcon();
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginWithIncorrectCredentails(email, invalidEmail);
        AssertFailAndContinue(mloginPageActions.clickShowHideLink("Hide"), "Click show link and verify password is un-masked and Hide link is displayed");
        AssertFailAndContinue(mloginPageActions.clickShowHideLink("Show"), "Click Hide link and verify password is masked and show link is displayed");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, GUESTONLY})
    public void loginPageFromPDP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify Login Page from PDP page as guest user" +
                "DT-20795,DT-37219, DT-37427");

        String invalidEmail = getmobileDT2CellValueBySheetRowAndColumn("Login", "messageOnForm", "invalidEmail");
        //DT-8540
        String validUPCNumber = "";
        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        }
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "SearchTerm2", "ValidUPCNoCA");
        }
        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, validUPCNumber);
        AssertFailAndContinue(mproductDetailsPageActions.clickOnWriteAReviewLink(mloginPageActions), "Verify Login Page for Write a review link");
        AssertFailAndContinue(mproductDetailsPageActions.isDisplayed(mproductDetailsPageActions.createAccountButton), "Verify Create Account Button is present ");
        

        mloginPageActions.clickCloseLoginModal();
        AssertFailAndContinue(mproductDetailsPageActions.clickAddToWishListAsGuest(), "Verify Login Page for WishList Icon");
        mloginPageActions.clickCloseLoginModal();
        AssertFailAndContinue(mproductDetailsPageActions.verifyElementNotDisplayed(mproductDetailsPageActions.BreadCrumb, 10), "Validate BreadCrumb from PDP");
        AssertFailAndContinue(mproductDetailsPageActions.clickWriteAReviewAsGuest(mloginPageActions), "Click write a review as guest user and verify login model is displayed");
        AssertFailAndContinue(mloginPageActions.loginWithIncorrectCredentails(email, invalidEmail), "Login with incorrect credentials");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, GUESTONLY})
    public void loginPageFromCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("validate login from checkout "+"" +
                "DT-33928, DT-37311");

        String searchKeyWord = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        String email1 = mcreateAccountActions.randomEmail();
        String errorMessage=getmobileDT2CellValueBySheetRowAndColumn("Login", "loginErr", "expectedContent");
        String invalidEmail = getmobileDT2CellValueBySheetRowAndColumn("Login", "Credentials", "invalidEmail");
        String invalidPassowrd= getmobileDT2CellValueBySheetRowAndColumn("Login", "Credentials", "invalidPassword");
        String validPassowrd= getmobileDT2CellValueBySheetRowAndColumn("Login", "RegisteredUser", "Password");
        //DT-8538
        addToBagBySearching(getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyWord, qty));
        AssertFailAndContinue(mshoppingBagPageActions.clickCheckoutAsGuest(), "Click checkout button as guest and verify login page");
        AssertFailAndContinue(mloginPageActions.loginWithIncorrectCredentails(invalidEmail, validPassowrd), "Verify User is not able to login with incorrect email id");
        AssertFailAndContinue(mloginPageActions.loginWithIncorrectCredentails(email1, invalidPassowrd), "Verify User is not able to login with incorrect password");
        AssertFailAndContinue(mloginPageActions.loginWithIncorrectCredentails(invalidEmail, invalidPassowrd), "Verify User is not able to login with incorrect password and email id");
        AssertFailAndContinue(mloginPageActions.clickForgotPassword(), "Click forgot password link from login page and verify forgot password page");
        AssertFailAndContinue(mforgotYourPasswordPageActions.validateBackToLogin(), "verify login page after Clicked on back to login from Forgot password page");

        mloginPageActions.clickCloseLoginModal();
        AssertFailAndContinue(mshoppingBagPageActions.clickWLGuestUser(mloginPageActions), "Click Wishlist icon as guest and verify login page");

        mloginPageActions.clickCloseLoginModal();
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickLoginLinkInMPReSpot(mloginPageActions), "Click login link in MPR eSpot and verify Loginpage");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
            //AssertFailAndContinue(panCakePageActions.verifyRewardsBar(), "Verify user is able to login from login page");
        }
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, GUESTONLY})
    public void loginTests_Fav(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        String email1 = mcreateAccountActions.randomEmail();
        String email2 = mcreateAccountActions.randomEmail();
        String email3 = mcreateAccountActions.randomEmail();
        String errorMessage=getmobileDT2CellValueBySheetRowAndColumn("Login", "loginErr", "expectedContent");
        String invalidEmail = getmobileDT2CellValueBySheetRowAndColumn("Login", "Credentials", "invalidEmail");
        String invalidPassowrd= getmobileDT2CellValueBySheetRowAndColumn("Login", "Credentials", "invalidPassword");
        String validPassowrd= getmobileDT2CellValueBySheetRowAndColumn("Login", "RegisteredUser", "Password");
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify Login UI from wishlist page"+"DT-33926,DT-20558");
        mfooterActions.navigateTo(EnvironmentConfig.getApplicationUrl());

        if (store.equalsIgnoreCase("US")) {
            panCakePageActions.navigateToMenu("CREATEACCOUNT");
            createNewAccountAndGetEmailAddr("CreateAccountUS", email1);
        }

        if (store.equalsIgnoreCase("CA")) {
            email1 = email3;
            mfooterActions.changeCountryByCountry("CANADA");
            panCakePageActions.navigateToMenu("CREATEACCOUNT");
            createNewAccountAndGetEmailAddr("CreateAccountCA", email3);
        }

        mheaderMenuActions.deleteAllCookies();
        panCakePageActions.navigateToMenu("FAVORITES");
        AssertFailAndContinue(mwishListLoginOverlayActions.loginfromWishList(email1, getmobileDT2CellValueBySheetRowAndColumn("CreateAccount", "CreateAccountUS", "Password")), "Login from wishlist and verify wish list landing page is displayed");
        mheaderMenuActions.deleteAllCookies();

        panCakePageActions.navigateToMenu("FAVORITES");
        AssertFailAndContinue(mwishListLoginOverlayActions.validateWLLoginPage(), "Validate all fields in WishList Login Page");
        AssertFailAndContinue(mloginPageActions.loginWithIncorrectCredentails(invalidEmail, validPassowrd), "Verify User is not able to login with incorrect email id");
        AssertFailAndContinue(mloginPageActions.loginWithIncorrectCredentails(email1, email1), "Verify Error Message for invalid data");
        AssertFailAndContinue(mloginPageActions.loginWithIncorrectCredentails(invalidEmail, invalidPassowrd), "Verify User is not able to login with incorrect password and email id");
        AssertFailAndContinue(mloginPageActions.loginWithIncorrectCredentails(email2, "12345566"), "Verify Error Message for Email not associated with tcp");
        //DT-20558
        AssertFailAndContinue(mloginPageActions.getLoginErrorMsg().equals(errorMessage), "Verify whether System displays the appropriate error message When user login by providing invalid username or password ");
       
        AssertFailAndContinue(mloginPageActions.clickForgotPassword(), "Verify Forgot password link");
        mobileDriver.navigate().refresh();
        panCakePageActions.navigateToMenu("FAVORITES");
        AssertFailAndContinue(mloginPageActions.clickCreateAccountLink(), "Verify Create Account link");
        mobileDriver.navigate().refresh();
        panCakePageActions.navigateToMenu("FAVORITES");
        
        //AssertFailAndContinue(mloginPageActions.validateErrorMessageForAccountLocked(email, "jj"), " Validate Error message for locked account");
        mloginPageActions.loginWithIncorrectCredentails(email, "123");

        AssertFailAndContinue(mloginPageActions.clickShowHideLink("Hide"), "Click show link and verify password is un-massked and Hide link is displayed");
        AssertFailAndContinue(mloginPageActions.clickShowHideLink("Show"), "Click Hide link and verify password is masked and show link is displayed");
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {SHOPPINGBAG, GUESTONLY, PROD_REGRESSION})
    public void loginFromCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify cart doesn't change when user login from Checkout and able to Place order Successfully");
        Map<String, String> mc = null;
        if (store.equalsIgnoreCase("US")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        String searchKeyWord = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        //DT-38111
        addToBagBySearching(getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyWord, qty));
        AssertFailAndContinue(mshoppingBagPageActions.getItemsCountInOL() == 1, "Verify item count in order ledger is 1");
        AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtn(mloginPageActions, mshoppingBagPageActions), "Click checkout button as guest and verify login page");
        AssertFailAndContinue(mloginPageActions.loginAsRegisteredUserFromCheckout(email, password, mshippingPageActions, mreviewPageActions), "User logged in from checkout login page redirected to shipping Page");
        mshippingPageActions.returnToBagPage(mshoppingBagPageActions);
        AssertFailAndContinue(mshoppingBagPageActions.getItemsCountInOL() == 1, "Verify item count in order ledger is 1 after login as well");
        mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetails(getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "fName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "lName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "addressLine1"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "city"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "state"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "zip"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "phNum"));
        }
        if (store.equalsIgnoreCase("CA")) {
            mshippingPageActions.enterShippingDetails(getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "fName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "lName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "addressLine1"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "city"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "state"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "zip"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsCA", "phNum"));
        }

        AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(), "Verify user is redirected to Billing page");
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.click(mreviewPageActions.submitOrderBtn);
            AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageReg(), "Verify Order Confirmation Page");
            AssertFailAndContinue(mreceiptThankYouPageActions.isDisplayed(mreceiptThankYouPageActions.ordeLedger), "Verify Order Ledger Section is displayed");
        }
    }

    @Test(priority = 5, dataProvider = dataProviderName, groups = {GUESTONLY, USONLY, PROD_REGRESSION})
    public void verifyLoginFromUS_ES(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("DT-20555: As a " + user + " user in " + store + " store, verify user is able to login when he has changed language from EN to ES");

        AddInfoStep("Change language from US_EN to US_ES and perform login operation");
        mfooterActions.changeCountryAndLanguage("US", "Spanish");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        AssertFailAndContinue(!mheaderMenuActions.isUserLogOut(), "US User got successfully logged in when language was changed from EN to ES");
    }
    
    @Test(priority = 5, dataProvider = dataProviderName, groups = {GUESTONLY, CAONLY, PROD_REGRESSION})
    public void verifyLoginFromCA_FR(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("DT-20556: As a " + user + " user in " + store + " store, verify user is able to login when he has changed language from EN to FR in CA store.");
        
        AddInfoStep("Change language from CA_EN to CA_FR and perform login operation");
        mfooterActions.changeCountryAndLanguage("CA", "French");
        
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        AddInfoStep("US User got successfully logged in when language was changed from EN to FR");
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
