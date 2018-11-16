package tests.mobileDT;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

public class MobileGuestOnlyWithCreateAccount extends MobileBaseTest {

    WebDriver mobileDriver;
    String productName0 = "";
    String email1 = UiBaseMobile.randomEmail();
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String password;
    String env;
    String validUPCNumber="";
    String validZip="";

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception
    {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("guest")) {
                createAccount(rowInExcelUS, email1);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("guest")) {
                createAccount(rowInExcelUS, email1);
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
            mheaderMenuActions.addStateCookie("ON");

        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GUESTONLY,PROD_REGRESSION,CHECKOUT})
    public void loginFromFavAndVerifyECPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify user is redirected to Express checkout page when user login from favorite" +
                "and add a product to bag from favorite");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);

        makeExpressCheckoutUser(store,env);

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, searchKeyword);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(0);
        mcategoryDetailsPageAction.clickAddToWishListAsRegistered(1);

        AssertFailAndContinue(mcategoryDetailsPageAction.favIconEnabled.size() == 2, "verify 2 products are added to favorite");
        logout();
        AssertFailAndContinue(mheaderMenuActions.waitUntilUrlChanged("home", 20), "Wait for url to change to home page url");

        panCakePageActions.navigateToMenu("FAVORITES");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        AssertFailAndContinue(mheaderMenuActions.waitUntilUrlChanged("favorites", 20), "Wait for url to change to favorite page url");

        mobileFavouritesActions.addProductToBagByPosition(1);
        mobileFavouritesActions.addProductToBagByPosition(2);
        //DT-40385
        int bagCount = mheaderMenuActions.getBagCount();
        AssertFailAndContinue(mheaderMenuActions.getBagCount() > 0,"Verify Mini cart count is increased after adding products from favorite");
        //AssertFailAndContinue(mheaderMenuActions.getBagCount() == bagCount + 1, "Verify Mini cart count is increased after adding bopis product");
        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Verify click on shopping bag icon");
        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions), "Click checkout and verify user redirect to express checkout page");
        //DT-35333
        AssertFailAndContinue(mreviewPageActions.verifyExpressCheckoutTitle(), "Verify Title is EXPRESS CHECK OUT");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GUESTONLY,PROD_REGRESSION,CHECKOUT,GIFTCARD,RECAPTCHA})
    public void loginFromPDPAndVerifyECPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String,String> gc = null;
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify user is redirected to Express checkout page when user login from write a review link displayed on PDP" +
                "and add a product to bag from PDP");

        if (store.equalsIgnoreCase("US")) {
            gc = dt2ExcelReader.getExcelData("GiftCard", "usgc11");
            if (env.equalsIgnoreCase("prod")) {
                gc = dt2ExcelReader.getExcelData("GiftCard", "usgc1_prod");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            gc = dt2ExcelReader.getExcelData("GiftCard", "cagc11");
            if (env.equalsIgnoreCase("prod")) {
                gc = dt2ExcelReader.getExcelData("GiftCard", "cagc1_prod");
            }
        }

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);

        makeExpressCheckoutUser(store,env);
        logout();
        AssertFailAndContinue(mheaderMenuActions.waitUntilUrlChanged("home",20),"Wait for url to change to home page url");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");

        mheaderMenuActions.searchAndSubmit(mcategoryDetailsPageAction, searchKeyword);
        msearchResultsPageActions.clickOnProductImageByPosition(1);

        AssertFailAndContinue(mproductDetailsPageActions.clickOnWriteAReviewLink(mloginPageActions),"Click on write a review link on PDP page");
        //DT-20564
        mloginPageActions.loginWithEmptyCredentails();
        AssertFailAndContinue(mloginPageActions.getText(mloginPageActions.inlineMessage("Email Address")).contains(getmobileDT2CellValueBySheetRowAndColumn("Login", "EmailEmpty", "expectedContent")), "Verify Error Message for empty email in login Page");
        AssertFailAndContinue(mloginPageActions.getText(mloginPageActions.inlineMessage("Password")).contains(getmobileDT2CellValueBySheetRowAndColumn("Login", "PasswordEmpty", "expectedContent")), "Verify Error Message for empty password in login Page");

        //DT-20557
        AssertFailAndContinue(mloginPageActions.loginWithIncorrectCredentails(email1, "abcd"), "Verify  System displays appropriate error message When user attempts to logs in using the modal for \"Write A Review\" with invalid username or password.");

        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);

        AssertFailAndContinue(addToBagFromPDPPage(qty) != null,"Verify product added to bag from PDP page");
        //add a validation for OOS product
        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(),"Verify click on shopping bag icon");

        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions),"Click checkout and verify user redirect to express checkout page");
        //DT-35331
        AssertFailAndContinue(mreviewPageActions.verifyExpressCheckoutTitle(), "Verify Title is EXPRESS CHECK OUT");
        //DT-35366
        AddInfoStep("Adding Gift Card on Order Review Page");
        mreviewPageActions.enterGiftCardDetails(gc.get("Card"), gc.get("Pin"));

        AddInfoStep("Validating Billing details after applying Gift Card");
        AssertFailAndContinue(!mreviewPageActions.verifyPaymentMethodSection(),"Verify Payment section is not displayed on review page");
        AssertFailAndContinue(!mreviewPageActions.verifyBillingAddressSection(),"Verify Billing address section is not displayed on review page");

        AddInfoStep("Removing Gift Card From Order Review page ");
        AssertFailAndContinue(mreviewPageActions.clickRemoveGCButton(),"Remove Gift card");

        AddInfoStep("Validating Billing details after removing Gift Card");
        AssertFailAndContinue(mreviewPageActions.verifyPaymentMethodSection(),"Verify Payment section is displayed after removing gift card on review page");
        AssertFailAndContinue(mreviewPageActions.verifyBillingAddressSection(),"Verify Billing section is displayed after removing gift card on review page");

        //DT-35352
        AddInfoStep("Verify Shipping Summary section");

        AssertFailAndContinue(mreviewPageActions.verifyShippingAddressSection(),"Verify shipping address section");
        //DT-36647
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.shippingAddLabel).equalsIgnoreCase("Shipping Address"),"Verify Shipping Address label is displayed");
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.shippingMethodLabel).equalsIgnoreCase("Shipping Method"),"Verify Shipping Method label is displayed");
        AssertFailAndContinue(mreviewPageActions.verifyGiftWrappingSection(),"Verify gift services section");
        //DT-35346
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.giftServiceLabel).equalsIgnoreCase("Gift Services"),"Verify Gift Services label is displayed");
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.giftServiceText).equalsIgnoreCase("N/A"),"Verify N/A is displayed when no gift card service is selected");

        AssertFailAndContinue(mreviewPageActions.verifyShippingMethodSection(),"Verify shipping methods section");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.editShippingAddress),"Verify edit link of shipping section");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.custEmailPhn),"Verify email link and phone number section");
        //DT-35395
        AddInfoStep("Return back to shopping bag");

        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        AssertFailAndContinue(!mshoppingBagPageActions.isDisplayed(mreviewPageActions.giftServiceLabel),"Verify Gift Services label is not displayed on shopping bag");
        AssertFailAndContinue(mshoppingBagPageActions.removeFirstOccur_NormalProd(),"Remove ship-to product from shopping bag");
        AssertFailAndContinue(mshoppingBagPageActions.clickGoShoppingLink(),"Verify home page redirection");

        String validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCode");
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        AddInfoStep("Adding BOPIS product to bag");
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        mheaderMenuActions.clickShoppingBagIcon();

        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions),"Click on checkout button");

        AssertFailAndContinue(mreviewPageActions.verifyExpressCheckoutTitle(), "Verify Title is EXPRESS CHECK OUT");
        AssertFailAndContinue(mreviewPageActions.verifyPickUpSection(),"Verify Pick-up details section is displayed on Review page");
        AssertFailAndContinue(mreviewPageActions.verifyPaymentMethodSection(),"Verify Payment section is displayed for Bopis product");
        AssertFailAndContinue(mreviewPageActions.verifyBillingAddressSection(),"Verify Billing section is displayed for Bopis product");

    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {PROD_REGRESSION,CHECKOUT,GUESTONLY})
    public void loginFromPFP_FavModel_VerifyECPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify user is redirected to Express checkout page when user login from heart icon displayed on PDP" +
                "and add a product to bag from Favorite");

        AddInfoStep("Making user as Express checkout user");
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);

        makeExpressCheckoutUser(store, env);

        logout();
        AssertFailAndContinue(mheaderMenuActions.waitUntilUrlChanged("home", 20), "Wait for url to change to home page url");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");

        AssertFailAndContinue(mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, searchKeyword),"Verify PDP page is displayed for product searched");
        AssertFailAndContinue(mproductDetailsPageActions.selectASize(),"Verify select size");
        mproductDetailsPageActions.selectQty(qty);

        AssertFailAndContinue(mproductDetailsPageActions.clickAddToWishListAsGuest(),"Verify click on heart icon displayed on PDP");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);

        AssertFailAndContinue(mproductDetailsPageActions.isDisplayed(mproductDetailsPageActions.addedToWishList),"Verify product added to favorite");

        panCakePageActions.navigateToMenu("FAVORITES");
        mobileFavouritesActions.addProductToBagByPosition(1);

        AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(), "Verify click on shopping bag icon");

        AssertFailAndContinue(mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions), "Click checkout and verify user redirect to express checkout page");
        //DT-35330
        AssertFailAndContinue(mreviewPageActions.verifyExpressCheckoutTitle(), "Verify Title is EXPRESS CHECK OUT");
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {BOPIS, GUESTONLY, PROD_REGRESSION})
    public void verifyLoginWithECUserFromCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify guest user is able to see order review page when he logs into guest checkout login modal using express checkout user credentials." +
                "DT-43768");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        makeExpressCheckoutUser(store, env);
       // panCakePageActions.navigateToMenu("USER");
        logout();

        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCode");
        }

        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCodeCA");
        }

        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        mheaderMenuActions.clickShoppingBagIcon();
        //DT-35329
        mshoppingBagPageActions.clickOnCheckoutBtn(mloginPageActions, mshoppingBagPageActions);
        mloginPageActions.loginAsRegisteredUserFromCheckout(email1, password, mshippingPageActions, mreviewPageActions);
        AssertFailAndContinue(mreviewPageActions.verifyExpressCheckoutTitle(), "Verify Title is EXPRESS CHECK OUT");

        if (store.equalsIgnoreCase("US")) {
            mreviewPageActions.clickOnPickUpAccordionBopis(mpickUpPageActions);
            AssertFailAndContinue(mpickUpPageActions.verifyTransactionalSMSCheckBox(), "Verify that express checkout user is able to sign up for order updates by navigating back to pickup page.\n");
        }
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {GUESTONLY, BOPIS, PROD_REGRESSION})
    public void verifyEC_favLoginModal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " + "Verify guest user lands to order review page when he logins via Move to Favorite login modal");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email1, password);
        makeExpressCheckoutUser(store, env);
       // panCakePageActions.navigateToMenu("USER");
       // mheaderMenuActions.logOut();
        logout();

        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        }
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCodeCA");
        }
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        //DT-35332
        mshoppingBagPageActions.moveProdToWLByPositionAsGuest(mobileFavouritesActions, 1);
        mloginPageActions.loginAsRegisteredUserFromMoveToFav(email1, password, mheaderMenuActions, panCakePageActions);
        mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        AssertFailAndContinue(mreviewPageActions.verifyExpressCheckoutTitle(), "Verify Title is EXPRESS CHECK OUT");
    }


    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }
}
