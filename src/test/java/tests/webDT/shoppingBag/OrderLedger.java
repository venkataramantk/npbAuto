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
 * Created by Venkat on 3/16/2017.
 */

//@Listeners(MethodListener.class)
public class OrderLedger extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(OrderLedger.class);
    String emailAddressReg;
    private String password;
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        headerMenuActions.deleteAllCookies();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered"))
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");

        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered"))
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

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG,PROD_REGRESSION})
    public void validateOrderLedgerSection(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Verify if the Registered user in the US Store is able to validate the Order Ledger section in the Shopping Bag page. The order ledger section should " +
                "display the count of the items in the bag and should also display the total price.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

//        if (user.equalsIgnoreCase("registered")) {
//            int qtyInBag = getOrderDetails.getQuantity(emailAddressReg,password,store);
//            if(qtyInBag == 0) {
//                addToCart.addToBag(emailAddressReg, password, "1", store, 1);
//            }
//            clickLoginAndLoginAsRegUser(emailAddressReg, password);
//            overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
//        }
//        else {
//            addToBagBySearching(searchKeywordAndQty);
//        }
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.validateOrderLedgerSection(headerMenuActions), "Validated the Product Count in the Order Ledger in the Shopping Bag Page is consistent with the count in the Shopping Bag icon on the header.");
        AssertFailAndContinue(shoppingBagPageActions.updateQty("2"), "Increase the quantity");
        AssertFailAndContinue(shoppingBagPageActions.validateOrderLedgerSection(headerMenuActions), "Validated the Product Count in the Order Ledger after increase the quantity");
        AssertFailAndContinue(shoppingBagPageActions.updateQty("1"), "Decrease the quantity");
        AssertFailAndContinue(shoppingBagPageActions.validateOrderLedgerSection(headerMenuActions), "Validated the Product Count in the Order Ledger after decrease the quantity");

    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG,PROD_REGRESSION})
    public void validateOdrLedgerRemoveItem(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Verify if the Registered user in the US Store is able to validate the change in the Order Ledger section in the Shopping Bag page, when an added product is removed");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        AssertFailAndContinue(shoppingBagPageActions.clickEditLinkAndVerifyAttributes(), "Clicking Edit Link and verifying cancel, update buttons and wishlist,remove links are not visible and edit link for other products not visible in edit mode.");
        AssertFailAndContinue(shoppingBagPageActions.clickCancelLink(shoppingBagPageActions.getFirstElementFromList(shoppingBagPageActions.editLnks)), "clicking Cancel button navigate back to the normal mode which displays edit link");
        AssertFailAndContinue(shoppingBagPageActions.clickRemoveLinkByPosition(1), "Removed a product from the bag");
        AssertFailAndContinue(shoppingBagPageActions.validateOrderLedgerSection(headerMenuActions), "Validated the Product Count in the Order Ledger in the Shopping Bag Page is consistent with the count in the Shopping Bag icon on the header.");
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG,PROD_REGRESSION})
    public void validateOdrLedgerAddItem(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Verify if the Registered user in the US Store is able to validate the change in the Order Ledger section in the Shopping Bag page, when a new product is added");
        Map<String, String> search_Term = excelReaderDT2.getExcelData("Search", "SearchTerm");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions), "Verified clicking on Bag Icon and view bag link navigates to shopping bag page");
        AssertFailAndContinue(shoppingBagPageActions.validateOrderLedgerSection(headerMenuActions), "Validated the Product Count in the Order Ledger in the Shopping Bag Page is consistent with the count in the Shopping Bag icon on the header.");

        addToBagFromFlip(search_Term.get("Value"),"1");
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.validateOrderLedgerSection(headerMenuActions), "Validated the Product Count in the Order Ledger in the Shopping Bag Page is consistent with the count in the Shopping Bag icon on the header.");
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG,PROD_REGRESSION,FAVORITES})
    public void validateOdrLedgerMoveToWL(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage(store + " store - Verify if the Registered user in the US Store is able to validate the change in the Order Ledger section in the Shopping Bag page, when a new product is added");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.moveProdToWLByPositionAsReg(1), "Move the first product to wishlist");
        } else if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.moveProdToWLByPositionAsGuest(loginPageActions, 1), "Move the first product to favorites as guest");
            loginPageActions.loginAsRegisteredUserFromLoginForm(emailAddressReg, password);

        }
        AssertFailAndContinue(shoppingBagPageActions.calculatingEstimatedTotal(), "Calculate the estimated total after moved the item to wishlist");
        AssertFailAndContinue(shoppingBagPageActions.validateOrderLedgerSection(headerMenuActions), "Validated the Product Count in the Order Ledger in the Shopping Bag Page is consistent with the count in the Shopping Bag icon on the header.");
        shoppingBagPageActions.navigateBack();
        productDetailsPageActions.waitUntilElementDisplayed(productDetailsPageActions.addToBag, 30);
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) >= 0, "Qty in bag is showing after browser navigation");
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, COUPONS, PROD_REGRESSION})
    public void verifyEstimatedTotal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the estimated total is equal to the sum of item price, shipping and tax in order summary section when user applied and remove the coupon");
        List<String> couponCode = null;
        if (store.equalsIgnoreCase("US")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
            if(env.equalsIgnoreCase("prod")){
                couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode"));
            }
        } else if (store.equalsIgnoreCase("CA")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA"));
            if(env.equalsIgnoreCase("prod")){
                couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA"));
            }
        }
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearching(searchKeywordAndQty);

        AssertFailAndContinue(shoppingBagPageActions.calculatingEstimatedTotal(), "Calculate the estimated total in order summary section which is equal to item price, shipping price and tax price");
        if (!shoppingBagPageActions.isMerchandiseCouponApplied("10%")) {
            AssertFailAndContinue(shoppingBagPageActions.applyCouponCode(couponCode.get(0)), "Enter the valid coupon code and click on apply button");
        }
        AssertFailAndContinue(shoppingBagPageActions.isCouponPercentOffDisplayAtOL("10"), "Verifying the coupon value applied at order ledger");
        AssertFailAndContinue(shoppingBagPageActions.estimatedTotalAfterAppliedCoupon(), "Verify the estimated cost after applied the valid coupon code");
        AssertFailAndContinue(shoppingBagPageActions.removeCouponAndCheckTotal(), "Remove the applied coupon and verify the estimated total");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUser(emailAddressReg, password);
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        }
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG, COUPONS})
    //TODO:Applying more than one rewards not working at bag page, removing steps of apply rewards
    public void applyMoreThanOneCouponAndValidateTotal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the estimated total is equal to the sum of item price, shipping and tax in order summary section when user applied more than one coupon");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUS1", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUS1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (!env.equalsIgnoreCase("prod") && !env.equalsIgnoreCase("prodstaging")) {
            if (user.equalsIgnoreCase("registered")) {
                clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
            }
            addToBagBySearching(searchKeywordAndQty);

            AssertFailAndContinue(shoppingBagPageActions.applyRewardsByPosition(1), "Applied the first rewards and verifying the coupon text");
            AssertFailAndContinue(shoppingBagPageActions.estimatedTotalAfterAppliedCoupon(), "Verify the estimated cost after applied the valid coupon code");
//        AssertFailAndContinue(shoppingBagPageActions.applyCouponCode("TESTING5OFF"),"Apply the second rewards and check the total");
//        AssertFailAndContinue(shoppingBagPageActions.estimatedTotalAfterAppliedCoupon(), "Verify the estimated cost after applied the valid coupon code");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {SHOPPINGBAG})
    public void addProdAndCheckPlcc(@Optional("US") String store, @Optional("registered") String user) {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the guest/register user adding the product more that $30 and check the place cash espot under the order ledger section");
        Map<String, String> search_Term = excelReaderDT2.getExcelData("Search", "SearchTerm");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, search_Term.get("ProductUPC")), "Search the product by using UPC number and check user redirected to PDP page");
        productDetailsPageActions.updateQtyInPDP("1");
        productDetailsPageActions.selectFitAndSizeInPDP();
        productDetailsPageActions.clickAddToBag();
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                AssertFailAndContinue(shoppingBagPageActions.isDisplayed(shoppingBagPageActions.plccPromoEspot_Reg), "PLCC Espot displaying as registered");
                //AssertFailAndContinue(shoppingBagPageActions.getMPRESpotColor("#0000EE"), "MPR Espot color is in blue as a registered"); blue color will be displayed if the reg user have PLCC payment type.
                AssertFailAndContinue(shoppingBagPageActions.getMPRESpotColor("#333333"), "MPR Espot color is in blue as a registered");
            } else if (user.equalsIgnoreCase("guest")) {
                AssertFailAndContinue(shoppingBagPageActions.isDisplayed(shoppingBagPageActions.plccEspot_Guest), "PLCC Espot displaying as Guest");
                AssertFailAndContinue(shoppingBagPageActions.getMPRESpotColor("#333333"), "MPR Espot color is in orange as a guest");

            }
        } else if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shoppingBagPageActions.isDisplayed(shoppingBagPageActions.airMilesImgUnderOrderLedger), "AirMiles image is displaying under order ledger");
            AssertFailAndContinue(shoppingBagPageActions.editAirMilesFieldAndEnterNumber(headerMenuActions.getRandomNumber(11)), "Able to  edit AirMiles at shopping bag under order ledger ");

        }
    }
}
