package tests.mobileDT.Checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Test(singleThreaded = true)
public class MobilePaypalCheckout extends MobileBaseTest {
    WebDriver mobileDriver;
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String email = UiBaseMobile.randomEmail();
    String password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);

        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelCA, email);
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
            mfooterActions.changeCountryByCountry("CANADA");
            mheaderMenuActions.addStateCookie("ON");
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, PAYPAL})
    public void verifyCancelOnPayPalSandBoxPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify if user clicks on cancel button (cross icon) on paypal page, he should be redirected"
                + " to bag page and products should be present in bag");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);

        searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumber2", "Value");
        qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchUPCNumber2", "Qty");
        searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagFromPDP(searchKeywordAndQty);
        mheaderMenuActions.clickShoppingBagIcon();
        //DT-38003
        AssertFailAndContinue(mshoppingBagPageActions.getItemsCountInOL() == 6, "Verify item count is updated in the order ledger");
        AddInfoStep("2 Products have been added to bag");

        String productName1InBag = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("First Product name in bag" + productName1InBag);
        String productName2InBag = mshoppingBagPageActions.getProductNames().get(1);
        AddInfoStep("Second Product name in bag" + productName2InBag);

        //mshoppingBagPageActions.click(mshoppingBagPageActions.paypalBtn);
        mshoppingBagPageActions.clickOnPayPalIcon(mpayPalPageActions);
        //mpayPalPageActions.clickPayPalCheckout();
        //mpayPalPageActions.clickCancelPaypalPaymentLink();
        //mpayPalPageActions.closePayWithPaypal();

        String productName1InBagAfterCancelOperation = mshoppingBagPageActions.getProductNames().get(0);
        AddInfoStep("Product name in bag after cancel operation in paypal sandbox page" + productName1InBagAfterCancelOperation);
        String productName2InBagAfterCancelOperation = mshoppingBagPageActions.getProductNames().get(1);
        AddInfoStep("Product name in bag after cancel operation in paypal sandbox page" + productName2InBagAfterCancelOperation);

        //DT-36515
        AssertFailAndContinue(productName1InBag.equals(productName1InBagAfterCancelOperation), "Same Product1 is present in shopping bag");
        AssertFailAndContinue(productName2InBag.equals(productName2InBagAfterCancelOperation), "Same Product2 is present in shopping bag");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {PAYPAL, USONLY, REGISTEREDONLY})
    public void eComProd_AddedFromFavorite_Paypal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store.toUpperCase() + " store, Add an Ecomm product to the Favorites and then add this product to the Shopping Bag." +
                "Verify whether user is able to place Ecomm order with PayPal as payment method");

        String username = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "USPaypal", "UserName"),
                pwd = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "USPaypal", "Password");

        String username_CA = getmobileDT2CellValueBySheetRowAndColumn("Paypal", "CanadaPaypal", "UserName");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
           mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        addToFavoritesBySearching(searchKeywordAndQty);

        panCakePageActions.navigateToMenu("FAVORITES");
        mobileFavouritesActions.addProductToBagByPosition(1);

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
        } else {
            AssertFail(false, "Something went wrong while adding product to bag from favorite list. Aborting this method run ");
        }

        mheaderMenuActions.clickShoppingBagIcon();

        //DT-38099
        AssertFailAndContinue(mshoppingBagPageActions.clickOnPayPalIcon(mpayPalPageActions), "Click PayPal button in Shopping bag page and verify PayPal modal");AssertFailAndContinue(mpayPalPageActions.paypalLogin_InvalidCred(username_CA, pwd, mshoppingBagPageActions),"Verify user redirected back to shopping bag after entering CA creds for US flow");
        //DT-35878
        mheaderMenuActions.staticWait(1000);
        mshoppingBagPageActions.switchToParent();
        AssertFailAndContinue(mshoppingBagPageActions.clickOnPayPalIcon(mpayPalPageActions), "Click PayPal button in Shopping bag page and verify PayPal modal");
        mpayPalPageActions.paypalLogin_AfterInvalidLogin(username, pwd, mreviewPageActions);
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Click submit order from review page verify order is placed successfully");
        getAndVerifyOrderNumber("eComPayPal_AddedFromFavorite_Paypal");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {PAYPAL})
    public void verifyChangeBillingAddFromPaypal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        String username = "";
        String pwd = "";
        Map<String, String> shipAndBillDetails = null;
        Map<String, String> payPalLoginDetails = null;
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store.toUpperCase() + " store, when user changes or adds the new shipping address from PayPal ,and clicks on \"Continue\" button ," +
                "Verify that the user is redirected to \"Order Review \" info section of checkout page with the updated shipping address reflecting in order review section..");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        if (store.equalsIgnoreCase("US")) {
            payPalLoginDetails = dt2ExcelReader.getExcelData("Paypal", "USPaypal");
            username = payPalLoginDetails.get("UserName");
            pwd = payPalLoginDetails.get("Password");
            shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
        }
        if (store.equalsIgnoreCase("CA")) {
            payPalLoginDetails = dt2ExcelReader.getExcelData("Paypal", "CAPaypal");
            username = payPalLoginDetails.get("UserName");
            pwd = payPalLoginDetails.get("Password");
            shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
        }else{
            addToBagBySearching(searchKeywordAndQty);
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(shipAndBillDetails.get("fName"), shipAndBillDetails.get("lName"), shipAndBillDetails.get("addressLine1"),
                shipAndBillDetails.get("addressLine2"), shipAndBillDetails.get("city"), shipAndBillDetails.get("stateShortName"), shipAndBillDetails.get("zip"), shipAndBillDetails.get("phNum"), shipAndBillDetails.get("ShippingType"), email);


        mbillingPageActions.enterCardDetails(shipAndBillDetails.get("cardNumber"), shipAndBillDetails.get("securityCode"), shipAndBillDetails.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary();

        double estimatedTtl = Double.parseDouble(mreviewPageActions.getEstimateTotal());
        AddInfoStep("Estimated total on Order Ledger before changing payment method::" + estimatedTtl);

        String billingAdd = mreviewPageActions.getBillingAdd();
        AddInfoStep("Billing address on Order Ledger before changing payment method::" + billingAdd);

        String last4Digit = mreviewPageActions.getLast4DigitCC();
        AddInfoStep("Last 4 digits displayd on Review page::" + last4Digit);

        mreviewPageActions.clickEditBillingLink(mbillingPageActions);

        //DT-39054
        AssertFailAndContinue(mbillingPageActions.selectPayPalAsPayment(mpayPalPageActions), "Click PayPal button in Shopping bag page and verify PayPal modal");
//        AssertFailAndContinue(mbillingPageActions.clickPayPalCheckout(), "Click on PayPal Checkout button in pay with PayPal modal and verify a new window is opened ");
        mpayPalPageActions.paypalLogin(username, pwd);

        String addressUpdated = mpayPalPageActions.changeBillingAddFromPayPal();
        AddInfoStep("Address updated on Paypal Page ::" + addressUpdated);

        mpayPalPageActions.clickContinueButton(mreviewPageActions);
        String updatedAddrOnReviewPage = mreviewPageActions.getBillingAdd();
        AddInfoStep("Address updated on Order Review Page ::" + updatedAddrOnReviewPage);
        AssertFailAndContinue(addressUpdated.contains(updatedAddrOnReviewPage), "Verify billing address is updated with the one selected on PayPal page");

        //DT-36668
        String updatedLast4Digit = mreviewPageActions.getLast4DigitCC();
        AddInfoStep("Last 4 digits displayed on Review page::" + updatedLast4Digit);
        AssertFailAndContinue(!(last4Digit.equalsIgnoreCase(updatedLast4Digit)), "Verify last 4 digit is updated after changing the payment method from CC to PayPal ");

        //DT-36669
        String cardType = mreviewPageActions.getCardImgAltText();
        AssertFailAndContinue(cardType.equals("PAYPAL"), "Verify paypal image is shown");

        String paymentInfo = mreviewPageActions.getText(mreviewPageActions.paymentMethodSection);
        AssertFailAndContinue(paymentInfo.contains("ending in"), "Verify ending in text on Billing section on review page ");

        mreviewPageActions.expandOrderSummary();
        double estimatedTtlOnUpdatingPaymentMethod = Double.parseDouble(mreviewPageActions.getEstimateTotal());
        AddInfoStep("Estimated total on Order Ledger before changing payment method::" + estimatedTtlOnUpdatingPaymentMethod);

        AssertFailAndContinue(estimatedTtl == estimatedTtlOnUpdatingPaymentMethod, "Verify estimated total is same after changing the payment method from CC to PayPal ");
        AssertFailAndContinue(mreviewPageActions.validateLineItems_OrderLedgerSection(), "Verify Order Ledger remains same after changing the payment method");

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
