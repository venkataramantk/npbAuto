package tests.webDT.shoppingBag;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

public class CartMerge extends BaseTest {

        WebDriver driver;
        ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
        Logger logger = Logger.getLogger(EmptyShoppingBag.class);
        String emailAddressReg;
        private String password;

        @Parameters({"store","users"})
        @BeforeClass(alwaysRun = true)
        public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
            initializeDriver();
            driver = getDriver();
            initializePages(driver);
            System.out.println("store: "+store);
            driver.get(EnvironmentConfig.getApplicationUrl());
            if(store.equalsIgnoreCase("US")) {
                driver.get(EnvironmentConfig.getApplicationUrl());
                if(user.equalsIgnoreCase("registered")) {
                    emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
                    password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
                }

            }
            else if(store.equalsIgnoreCase("CA")){
                footerActions.changeCountryAndLanguage("CA","English");
                if(user.equalsIgnoreCase("registered")) {
                    emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
                    password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
                }

            }
            driver.manage().deleteAllCookies();
            driver.navigate().refresh();
        }

        @Parameters("store")
        @BeforeMethod(alwaysRun = true)
        public void openBrowser(@Optional("US") String store) throws Exception {
            driver.get(EnvironmentConfig.getApplicationUrl());
            if(store.equalsIgnoreCase("US")) {
                driver.get(EnvironmentConfig.getApplicationUrl());
            }
            else if(store.equalsIgnoreCase("CA")){
                footerActions.changeCountryAndLanguage("CA","English");
            }

        }
        @AfterMethod(alwaysRun = true)
        public void clearCookies(){
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.navigate().refresh();
        }

    @Test(priority = 0, dataProvider = dataProviderName,groups = {SHOPPINGBAG,REGISTEREDONLY,CARTMERGE})
    public void regUserProdDumped(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store+ " store - When a Guest user adds some products, clicks on Login link in the header and then logs into a registered account (that already has some products in the shopping bag), verify that the products already present in the registered account are dumped and that only the products added as guest are retained in the shopping bag.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc1", "ProductUPC");

        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc2", "ProductUPC");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        if(Integer.parseInt(headerMenuActions.getQtyInBag()) == 0){
            headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword);
            productDetailsPageActions.clickAddToBag();
        }
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) > 0,"Verifying for Registered user has some products in bag");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        AssertFailAndContinue(headerMenuActions.isDisplayed(headerMenuActions.welcomeCustomerLink),"Verify the user is logged out to continue with guest user");
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword1);
        AssertFailAndContinue(productDetailsPageActions.clickAddToBag(),"Added the products to bag as guest user");
        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        String upcNum = shoppingBagPageActions.getUPCNumByPosition(1);
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) == 1 && upcNum.equalsIgnoreCase(searchKeyword1),"Verifying for the registered user products dumped and guest user products replaced");


    }

    @Test(priority = 1, dataProvider = dataProviderName,groups = {SHOPPINGBAG,REGISTEREDONLY,CARTMERGE})
    public void loginFromWL_ProdDumped(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store+ " store - When a Guest user adds some products and then logs into a registered account (that already has some products in the shopping bag) using the login modal from Favourite landing page, verify that the products already present in the registered account are dumped and that only the products added as guest are retained in the shopping bag.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc2", "ProductUPC");

        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc3", "ProductUPC");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        if(Integer.parseInt(headerMenuActions.getQtyInBag()) == 0){
            headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword);
            productDetailsPageActions.clickAddToBag();
        }
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) > 0,"Verifying for Registered user has some products in bag");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        AssertFailAndContinue(headerMenuActions.isDisplayed(headerMenuActions.welcomeCustomerLink),"Verify the user is logged out to continue with guest user");
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword1);
        AssertFailAndContinue(productDetailsPageActions.clickAddToBag(),"Added the products to bag as guest user");
        headerMenuActions.clickWishListAsGuest(wishListDrawerActions);
        loginPageActions.successfulLogin(emailAddressReg, password);

        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        String upcNum = shoppingBagPageActions.getUPCNumByPosition(1);
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) == 1 && upcNum.equalsIgnoreCase(searchKeyword1),"Verifying for the registered user products dumped and guest user products replaced");

    }

    @Test(priority = 2, dataProvider = dataProviderName,groups = {SHOPPINGBAG,REGISTEREDONLY,CARTMERGE})
    public void loginFromWriteReview_ProdDumped(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store+ " store - When a Guest user adds some products and then logs into a registered account (that already has some products in the shopping bag) using the login modal from Favourite landing page, verify that the products already present in the registered account are dumped and that only the products added as guest are retained in the shopping bag.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc3", "ProductUPC");

        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc1", "ProductUPC");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        if(Integer.parseInt(headerMenuActions.getQtyInBag()) == 0){
            headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword);
            productDetailsPageActions.clickAddToBag();
        }
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) > 0,"Verifying for Registered user has some products in bag");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        AssertFailAndContinue(headerMenuActions.isDisplayed(headerMenuActions.welcomeCustomerLink),"Verify the user is logged out to continue with guest user");
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword1);
        AssertFailAndContinue(productDetailsPageActions.clickAddToBag(),"Added the products to bag as guest user");
        AssertFailAndContinue(productDetailsPageActions.clickWriteAReviewLinkAsGuest(loginPageActions),"Clicked on Write a review link as guest and verifying for login pop up");
        loginPageActions.successfulLogin(emailAddressReg, password);

        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        String upcNum = shoppingBagPageActions.getUPCNumByPosition(1);
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) == 1 && upcNum.equalsIgnoreCase(searchKeyword1),"Verifying for the registered user products dumped and guest user products replaced");


    }

    @Test(priority = 3, dataProvider = dataProviderName,groups = {SHOPPINGBAG,REGISTEREDONLY,CARTMERGE})
    public void loginFromHeartIconPDP_ProdDumped(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store+ " store - When a Guest user adds some products and then logs into a registered account (that already has some products in the shopping bag) using the login modal from Favourite landing page, verify that the products already present in the registered account are dumped and that only the products added as guest are retained in the shopping bag.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc1", "ProductUPC");

        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc2", "ProductUPC");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        if(Integer.parseInt(headerMenuActions.getQtyInBag()) == 0){
            headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword);
            productDetailsPageActions.clickAddToBag();
        }
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) > 0,"Verifying for Registered user has some products in bag");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        AssertFailAndContinue(headerMenuActions.isDisplayed(headerMenuActions.welcomeCustomerLink),"Verify the user is logged out to continue with guest user");
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword1);
        AssertFailAndContinue(productDetailsPageActions.clickAddToBag(),"Added the products to bag as guest user");
        AssertFailAndContinue(productDetailsPageActions.addToWLAsGuest(loginPageActions),"Clicking on Heart icon and navigates to login popup");
        loginPageActions.successfulLogin(emailAddressReg, password);

        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        String upcNum = shoppingBagPageActions.getUPCNumByPosition(1);
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) == 1 && upcNum.equalsIgnoreCase(searchKeyword1),"Verifying for the registered user products dumped and guest user products replaced");

    }

    @Test(priority = 4, dataProvider = dataProviderName,groups = {SHOPPINGBAG,REGISTEREDONLY,CARTMERGE})
    public void loginFromHeartIconPLP_ProdDumped(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store+ " store - When a Guest user adds some products and then logs into a registered account (that already has some products in the shopping bag) using the login modal from Favourite landing page, verify that the products already present in the registered account are dumped and that only the products added as guest are retained in the shopping bag.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc1", "ProductUPC");


        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty1 = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword1, qty1);

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        if(Integer.parseInt(headerMenuActions.getQtyInBag()) == 0){
            headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword);
            productDetailsPageActions.clickAddToBag();
        }
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) > 0,"Verifying for Registered user has some products in bag");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        AssertFailAndContinue(headerMenuActions.isDisplayed(headerMenuActions.welcomeCustomerLink),"Verify the user is logged out to continue with guest user");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction,searchKeyword1);
        clickAddToBagIconSelectAFitSizeAndAddProdToBag();
        AssertFailAndContinue(searchResultsPageActions.clickAddToFavByPosAsGuest(loginPageActions, 1),"Add to favorites as guest user");
        loginPageActions.successfulLogin(emailAddressReg, password);

        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        String upcNum = shoppingBagPageActions.getUPCNumByPosition(1);
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) == 1 && !upcNum.equalsIgnoreCase(searchKeyword),"Verifying for the registered user products dumped and guest user products replaced");

    }

    @Test(priority = 5, dataProvider = dataProviderName,groups = {SHOPPINGBAG,REGISTEREDONLY,CARTMERGE})
    public void loginFromGuestCheckout_ProdDumped(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store+ " store - When a Guest user adds some products from the PLP/Product Recommendations/PDP/ clicks on checkout from the Add to Bag notification and logins from the guest checkout login modal,verify that the products already present in the registered account are dumped and that only the product added as guest are retained in the shopping bag.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc1", "ProductUPC");

        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc2", "ProductUPC");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        if(Integer.parseInt(headerMenuActions.getQtyInBag()) == 0){
            headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword);
            productDetailsPageActions.clickAddToBag();
        }
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) > 0,"Verifying for Registered user has some products in bag");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        AssertFailAndContinue(headerMenuActions.isDisplayed(headerMenuActions.welcomeCustomerLink),"Verify the user is logged out to continue with guest user");
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword1);
        productDetailsPageActions.clickAddToBagAndVerifyCOOnConf();
        AssertFailAndContinue(headerMenuActions.clickCheckoutFromAddToBagConfGuest(loginPageActions),"Clicking Checkout from Added To Bag confirmation page as guest");
        loginPageActions.successfulLogin(emailAddressReg, password);

        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        String upcNum = shoppingBagPageActions.getUPCNumByPosition(1);
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) == 1 && upcNum.equalsIgnoreCase(searchKeyword1),"Verifying for the registered user products dumped and guest user products replaced");


    }

    @Test(priority = 6, dataProvider = dataProviderName,groups = {SHOPPINGBAG,REGISTEREDONLY,CARTMERGE})
    public void loginFromCreateAcctFooter_ProdDumped(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(store+ " store - When a Guest user adds some products and then logs into a registered account (that already has some products in the shopping bag) from the \"Create account\" footer link, verify that the products already present in the registered account are dumped and that only the products added as guest are retained in the shopping bag.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc1", "ProductUPC");

        String searchKeyword1 = getDT2TestingCellValueBySheetRowAndColumn("Search", "upc2", "ProductUPC");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        if(Integer.parseInt(headerMenuActions.getQtyInBag()) == 0){
            headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword);
            productDetailsPageActions.clickAddToBag();
        }
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) > 0,"Verifying for Registered user has some products in bag");
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();

        AssertFailAndContinue(headerMenuActions.isDisplayed(headerMenuActions.welcomeCustomerLink),"Verify the user is logged out to continue with guest user");
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions,searchKeyword1);
        productDetailsPageActions.clickAddToBagAndVerifyCOOnConf();
        AssertFailAndContinue(footerActions.clickOnCreateAnAccount(createAccountActions), "Click on Create Account link from the Footer");
        overlayHeaderActions.clickLoginOnOverlayHeader();
        loginDrawerActions.successfulLogin(emailAddressReg, password,headerMenuActions);

        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        String upcNum = shoppingBagPageActions.getUPCNumByPosition(1);
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) == 1 && upcNum.equalsIgnoreCase(searchKeyword1),"Verifying for the registered user products dumped and guest user products replaced");


    }
 }
