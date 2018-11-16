package tests.webDT.checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by AbdulazeezM on 5/18/2017.
 */
public class Checkout_OrderReview_STH extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
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
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered"))
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcel, "Password");
        headerMenuActions.deleteAllCookies();
    }

    @Parameters("store")
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            headerMenuActions.addStateCookie("MB");
        }
        driver.navigate().refresh();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {CHECKOUT, REGISTEREDONLY,SMOKE, PRODUCTION})
    public void validateSTHProdInReviewPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("Verify if register user can validate the order review page when user has only BOPIS product into the cart");

        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        if(store.equalsIgnoreCase("US")){
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        }
        else if(store.equalsIgnoreCase("CA")){
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "ground");
        }
        List<String> couponCode = null;
        couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "BOPISCode", "couponCode"));
        List<String> couponErr = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons","BOPISCode","validErrorMessage"));
        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        addToBagBySearching(searchKeywordAndQty);
        shoppingBagPageActions.validateExpiredCouponErrMsg(couponCode.get(0),couponErr.get(0));
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button and navigate to shipping page");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        if (env.equalsIgnoreCase("prod")) {
            Map<String, String> billDetailProd = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            AssertFailAndContinue(billingPageActions.enterPaymentAndAddress_GuestAndReg_SaveToAcct(reviewPageActions, billDetailProd.get("fName"), billDetailProd.get("lName"), billDetailProd.get("addressLine1"), billDetailProd.get("addressLine2"), billDetailProd.get("city"), billDetailProd.get("stateShortName"), billDetailProd.get("zip"), billDetailProd.get("cardNumber"), billDetailProd.get("securityCode"), billDetailProd.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        } else {
            AssertFailAndContinue(billingPageActions.enterPaymentAndAddress_GuestAndReg_SaveToAcct(reviewPageActions, billDetailUS.get("fName"), billDetailUS.get("lName"), billDetailUS.get("addressLine1"), billDetailUS.get("addressLine2"), billDetailUS.get("city"), billDetailUS.get("stateShortName"), billDetailUS.get("zip"), billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        }
        AssertFailAndContinue(reviewPageActions.verifyShipToHomeAloneProduct(), "Validate the products details in review page when user having ship to home product in to the cart");
        if(env.equalsIgnoreCase("prod")){
            AssertFailAndContinue(reviewPageActions.verifyOrderReviewPageSTHProd(), "validate the order review page when user contains only ship to home products ");
        }
        else {
            AssertFailAndContinue(reviewPageActions.verifyOrderReviewPageSTH(), "validate the order review page when user contains only ship to home products ");
        }AssertFailAndContinue(reviewPageActions.verifyShippingAddressSection(), "verify the shipping address section in order review page");
        AssertFailAndContinue(reviewPageActions.verifyBillingAddressSection(), "verify the billing address section in order review page");
        reviewPageActions.returnToBagPage(shoppingBagPageActions);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        myAccountPageActions.clickPaymentAndGCLink();
        Map<String, String> billDetailProd = null;
        if (env.equalsIgnoreCase("prod")) {
            billDetailProd = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            myAccountPageActions.verifyDefaultCardDetailsAfterOrder(billDetailProd.get("cardType"), billDetailProd.get("cardNumber"), billDetailProd.get("expMonthValueUnit"), billDetailProd.get("expYear"));
        }
        else{
            myAccountPageActions.verifyDefaultCardDetailsAfterOrder(billDetailUS.get("cardType"), billDetailUS.get("cardNumber"), billDetailUS.get("expMonthValueUnit"), billDetailUS.get("expYear"));
        }
        headerMenuActions.clickMiniCartAndCheckoutAsRegUser(shoppingBagDrawerActions, shippingPageActions);
        shippingPageActions.clickNextBillingButton(billingPageActions);
        if (env.equalsIgnoreCase("prod")) {
            billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, billDetailProd.get("securityCode"));

        }
        else {
            billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, billDetailUS.get("securityCode"));
        }
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            getAndVerifyOrderNumber("validateSTHProdInReviewPage");
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            myAccountPageActions.clickPaymentAndGCLink();
            AssertFailAndContinue(myAccountPageActions.verifyDefaultCardDetailsAfterOrder(billDetailUS.get("cardType"), billDetailUS.get("cardNumber"), billDetailUS.get("expMonthValueUnit"), billDetailUS.get("expYear")), "Verify the entered card details are getting displayed");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {CHECKOUT, REGISTEREDONLY,PROD_REGRESSION})
    public void editShipAndBillAddressPlaceOrder(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if register user can validate the order review page when user has only BOPIS product into the cart");

        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
        Map<String, String> editBillDetailUS = excelReaderDT2.getExcelData("BillingDetails", "editedBillingAddressUS");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        Map<String, String> editShipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "editedShippingAddressUS");
        if(store.equalsIgnoreCase("CA")){
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCardCA");
            editBillDetailUS = excelReaderDT2.getExcelData("BillingDetails", "editedBillingAddressCA");
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            editShipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "editedShippingAddressCA");
        }
        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            editBillDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");
        }
        else if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            editBillDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD2");
        }

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        addToBagBySearching(searchKeywordAndQty);
        String cartCount = shoppingBagPageActions.getBagCount();
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button and verify user redirect to the shipping page");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetailUS.get("fName"), billDetailUS.get("lName"), billDetailUS.get("addressLine1"), billDetailUS.get("addressLine2"), billDetailUS.get("city"), billDetailUS.get("stateShortName"), billDetailUS.get("zip"), billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.clickEditShippingAddress(shippingPageActions), "Click on edit shipping link and navigate to shipping page");
        AssertFailAndContinue(shippingPageActions.editShippingDetailsShipMethodAndContinue_Reg(billingPageActions, editShipDetailUS.get("fName"), editShipDetailUS.get("lName"), editShipDetailUS.get("addressLine1"), editShipDetailUS.get("addressLine2"), editShipDetailUS.get("city"), editShipDetailUS.get("stateShortName"), editShipDetailUS.get("zip"), editShipDetailUS.get("phNum"), sm.get("shipValue")), "Edit the Entered Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, billDetailUS.get("securityCode")), "Click on next review button and check user navigate to review page");
        AssertFailAndContinue(reviewPageActions.clickEditBillingAddress(billingPageActions), "Click on edit billing link and navigate to billing page");
        AssertFailAndContinue(billingPageActions.editPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, editBillDetailUS.get("fName"), editBillDetailUS.get("lName"), editBillDetailUS.get("addressLine1"), editBillDetailUS.get("addressLine2"), editBillDetailUS.get("city"), editBillDetailUS.get("stateShortName"), editBillDetailUS.get("zip"), editBillDetailUS.get("cardNumber"), editBillDetailUS.get("securityCode"), editBillDetailUS.get("expMonthValueUnit")), "Edit the entered the Payment details and the Billing Address and clicked on the Next Review button.");
        String estimatedTot = reviewPageActions.getEstimateTotal();
        AssertFailAndContinue(reviewPageActions.validateTotInShippingAndBagPage(estimatedTot), "validate the estimated total in shopping bag page with the shipping page");
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            AssertFailAndContinue(orderConfirmationPageActions.verifyTheItemCountWithBag(cartCount), "Verify the item count should be match with the cart count in shopping bag page");
            getAndVerifyOrderNumber("editShipAndBillAddressPlaceOrder");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {CHECKOUT, PAYPAL})
    public void validateOrderReviewSTHPaypal(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String, String> es = null;
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store.toUpperCase() + " store, verify whether user is able to place an ecomm order with PayPal as payment method");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "editedShippingAddressUS");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");

        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page as Register user");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page as Guest user");
        }

        if (store.equalsIgnoreCase("US")) {
            es = excelReaderDT2.getExcelData("Paypal", "USPaypal");
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        if (store.equalsIgnoreCase("CA")) {
            es = excelReaderDT2.getExcelData("Paypal", "CanadaPaypal");
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"),
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
        }
        String parentWindow = driver.getWindowHandle();
        AssertFailAndContinue(billingPageActions.payWithPayPal(), "Continue payment with PayPal option");
//        billingPageActions.clickProceedWithPaypalModalButton(payPalPageActions);
        if(!env.equalsIgnoreCase("prod")) {
            String firstemail = reviewPageActions.getText(reviewPageActions.emailID);
            reviewPageActions.clickOnShippingAccordion(shippingPageActions);
            shippingPageActions.clickNextBillingButton(billingPageActions);

            billingPageActions.click(billingPageActions.proceedWithPaPalButton);
            AssertFailAndContinue(reviewPageActions.getText(reviewPageActions.emailID).contains(firstemail), "Verify user is able edit email address by going back to shipping and latest email address is displayed in review page");

            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            getAndVerifyOrderNumber("validateOrderReviewSTHPaypal");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {CHECKOUT, GIFTCARD,REGISTEREDONLY, PRODUCTION, SMOKE})
    public void placeOrderWith_GC_AsPayment_Reg(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("Placing the order as registered user with giftcard as a paymentmethod");
        String emailAddr = "", pwd = "";
        if (env.equalsIgnoreCase("prod")) {
            emailAddr = getDT2TestingCellValueBySheetRowAndColumn("GiftCard", "prod_us", "Email");
            pwd = getDT2TestingCellValueBySheetRowAndColumn("GiftCard", "prod_us", "Password");
        } else {
            emailAddr = getDT2TestingCellValueBySheetRowAndColumn("GiftCard", "Login", "Email");
            pwd = getDT2TestingCellValueBySheetRowAndColumn("GiftCard", "Login", "Password");
        }
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        String validUPCNumber = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        if(store.equalsIgnoreCase("CA")){
            validZip = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
        }
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        clickLoginAndLoginAsRegUser(emailAddr, pwd);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        if (Integer.parseInt(headerMenuActions.getQtyInBag()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        }
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button and verify user redirect to the shipping page");
        if (!reviewPageActions.isDisplayed(reviewPageActions.submOrderButton)) {
            if (store.equalsIgnoreCase("US")) {
                enterShippingDetailsByShippingAddress_Reg("NYAddress", "standard");
            } else if (store.equalsIgnoreCase("CA")) {
                enterShippingDetailsByShippingAddress_Reg("CanadaAddress", "ground");
            }
        } else {
            reviewPageActions.clickReturnToBillingLink(billingPageActions);
        }

        AssertFail(billingPageActions.clickApplyOnGiftCard(), "Clicking Apply Button of giftcard");
        AssertFailAndContinue(billingPageActions.isRemainingBalanceNotEditable(), "The remaining balnace shouldn't editable at billing page");
        billingPageActions.returnToBagPage(shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.isDisplayed(shippingPageActions.giftCardsTotal), "Gift Cards is applied with price");
        AssertFailAndContinue(shoppingBagPageActions.isTotalBalanceDisplayAfterApplyingGC(), "The gift card is displaying at order ledger after applying at billing page");
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button and verify user redirect to the shipping page");
        if (!reviewPageActions.isDisplayed(reviewPageActions.submOrderButton)) {
            if (store.equalsIgnoreCase("US")) {
                enterShippingDetailsByShippingAddress_Reg("NYAddress", "standard");
            } else if (store.equalsIgnoreCase("CA")) {
                enterShippingDetailsByShippingAddress_Reg("CanadaAddress", "ground");
            }
        } else {
            reviewPageActions.clickReturnToBillingLink(billingPageActions);
        }
        AssertFail(billingPageActions.clickApplyOnGiftCard(), "Clicking Apply Button of giftcard at billing page");
        AssertFailAndContinue(billingPageActions.isDisplayed(billingPageActions.giftCardsTotal), "Gift Cards is applied with price at billing page");
        AssertFailAndContinue(billingPageActions.isRemainingBalanceDispalying(), "Gift Cards remaining balance is showing at billing page");
        AssertFailAndContinue(billingPageActions.isGCLast_4DigitsDisplaying(), "Gift Cards Lat 4 digits is showing at billing page");
        AssertFailAndContinue(billingPageActions.isGCAppliedNoticeDisplay(), "Verifying for heads up text under GC applied section");
        AssertFailAndContinue(billingPageActions.clickRemoveGCAndVerifyOrderToatl(), "Clicking Remove Button of giftcard at billing page and verifying the order total");

        billingPageActions.clickApplyOnGiftCard();
        billingPageActions.clickNextReviewButton(reviewPageActions, "Clicking Next Review button");
        AssertFailAndContinue(reviewPageActions.verifyOrderReviewPageWithGiftCardPay(), "Order review page info section displaying only with Applied Gift cards under \"Billing\" section NOT payment method and Billing address.");
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Placed order with gift card as payment");
            getAndVerifyOrderNumber("placeOrderWith_GC_AsPayment_Reg");
            AssertFailAndContinue(orderConfirmationPageActions.validateOrderLedgerSectionGuestReg(),"Validate the order ledger section");
        }

    }

    @Test(dataProvider = dataProviderName, priority = 4, groups = {CHECKOUT, REGISTEREDONLY, PRODUCTION,SMOKE})
    public void validatePaymentReview(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Check whether the payment card is updated properly");
        Map<String, String> editBillDetailUS = excelReaderDT2.getExcelData("BillingDetails", "Amex");
        Map<String, String> editBillDetailCA = excelReaderDT2.getExcelData("BillingDetails", "AmexCA");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetails = null, visaBill = null, mcBill = null;
        if (store.equalsIgnoreCase("US")) {
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            mcBill = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            if(env.equalsIgnoreCase("prod")){
                mcBill = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
                editBillDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            mcBill = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            if(env.equalsIgnoreCase("prod")){
                mcBill = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
                editBillDetailCA = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD2");
            }
        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        if (store.equalsIgnoreCase("US")) {
            addDefaultShipMethod(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"),
                    shipDetails.get("country"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"));
        }

        if (store.equalsIgnoreCase("CA")) {
            addDefaultShipMethod(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"),
                    shipDetails.get("country"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"));
//            editBillDetailUS = excelReaderDT2.getExcelData("BillingDetails", "editedBillingAddressCA");
        }
        //Adding default Credit Cardc
        addDefaultPaymentMethodCC(mcBill.get("fName"), mcBill.get("lName"), mcBill.get("addressLine1"), mcBill.get("city"), mcBill.get("stateShortName"),
                mcBill.get("zip"), mcBill.get("phNum"), mcBill.get("cardType"), mcBill.get("cardNumber"), mcBill.get("expMonthValueUnit"), mcBill.get("SuccessMessageContent"));

        addToBagBySearching(searchKeywordAndQty);
        String cartCount = shoppingBagPageActions.getBagCount();
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button with shipping and payment saved in account");
        AssertFailAndContinue(reviewPageActions.clickEditBillingAddress(billingPageActions), "Click on edit shipping link and navigate to shipping page");
        AssertFailAndContinue(billingPageActions.verifyPrepopulatedCard(mcBill.get("cardNumber")), "Verify the card details are pre-populated in billing");
        if(store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(billingPageActions.editPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, editBillDetailUS.get("fName"), editBillDetailUS.get("lName"), editBillDetailUS.get("addressLine1"), editBillDetailUS.get("addressLine2"), editBillDetailUS.get("city"), editBillDetailUS.get("stateShortName"), editBillDetailUS.get("zip"), editBillDetailUS.get("cardNumber"), editBillDetailUS.get("securityCode"), editBillDetailUS.get("expMonthValueUnit")), "Edit the entered the Payment details and the Billing Address and clicked on the Next Review button.");
        }
        else{
            AssertFailAndContinue(billingPageActions.editPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, editBillDetailCA.get("fName"), editBillDetailCA.get("lName"), editBillDetailCA.get("addressLine1"), editBillDetailCA.get("addressLine2"), editBillDetailCA.get("city"), editBillDetailCA.get("stateShortName"), editBillDetailCA.get("zip"), editBillDetailCA.get("cardNumber"), editBillDetailCA.get("securityCode"), editBillDetailCA.get("expMonthValueUnit")), "Edit the entered the Payment details and the Billing Address and clicked on the Next Review button.");
        }
        AssertFailAndContinue(reviewPageActions.verifyShippingAddressSection(), "verify the shipping address section in order review page");
        AssertFailAndContinue(reviewPageActions.verifyBillingAddressSection(), "verify the billing address section in order review page");
        AssertFailAndContinue(reviewPageActions.verifyLastNumber(editBillDetailUS.get("cardNumber")), "Verify the card is updated in Review page");
        AssertFailAndContinue(reviewPageActions.clickReturnToBillingLink(billingPageActions), "clicking On return to billing link navigates to billing page with billing accordian active");
        if(store.equalsIgnoreCase("US")) {
            billingPageActions.clickNextReviewButton(reviewPageActions, editBillDetailUS.get("securityCode"));
        }
        else{
            billingPageActions.clickNextReviewButton(reviewPageActions, editBillDetailCA.get("securityCode"));

        }
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            AssertFailAndContinue(orderConfirmationPageActions.verifyTheItemCountWithBag(cartCount), "Verify the item count should be match with the cart count in shopping bag page");
            getAndVerifyOrderNumber("validatePaymentReview");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 5, groups = {CHECKOUT, GIFTCARD, REGISTEREDONLY,PAYPAL})
    public void changePaypalToGCPayment(@Optional("US") String store, @Optional("registered") String user) throws Exception {

        setAuthorInfo("Shyamala");
        setRequirementCoverage("adds product to bag and proceeds to billing info section and clicks on \"pay with paypal\" button ," +
                "completes the paypal payment and landing in order review page then clicks on \"Return to Billing\" link, user is redirected " +
                "to billing info section and apply \"Gift card\" as payment whose balance amount is more than subtotal ,verify that paypal payment is NOT displayed and also ensure that \"NEXT:REVIEW\" button only displayed .");

        String emailAddr = getDT2TestingCellValueBySheetRowAndColumn("GiftCard", "Login", "Email");
        String pwd = getDT2TestingCellValueBySheetRowAndColumn("GiftCard", "Login", "Password");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        Map<String, String> es = null;
        if (store.equalsIgnoreCase("US")) {
            es = excelReaderDT2.getExcelData("Paypal", "USPaypal");
        } else if (store.equalsIgnoreCase("CA")) {
            es = excelReaderDT2.getExcelData("Paypal", "CanadaPaypal");

        }
        clickLoginAndLoginAsRegUser(emailAddr, pwd);
        overlayHeaderActions.closeOverlayDrawer(headerMenuActions);
        if (Integer.parseInt(headerMenuActions.getQtyInBag()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        }
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button and verify user redirect to the shipping page");
        if (!reviewPageActions.isDisplayed(reviewPageActions.submOrderButton)) {
            enterShippingDetailsByShippingAddress_Reg("NYAddress", "standard");
        } else {
            reviewPageActions.clickReturnToBillingLink(billingPageActions);
        }
        String parentWindow = driver.getWindowHandle();
        AssertFailAndContinue(billingPageActions.payWithPayPal(), "Continue payment with PayPal option");
//        billingPageActions.clickProceedWithPaypalModalButton(payPalPageActions);
        AssertFailAndContinue(reviewPageActions.clickReturnToBillingLink(billingPageActions), "clicking on return to billing link navigates back to the the biling page");
        AssertFailAndContinue(billingPageActions.clickApplyOnGiftCard(), "changing payment method from paypal to gift card and applied to order total at billing page");
        AssertFailAndContinue(billingPageActions.isRemainingBalanceNotEditable(), "The remaining balnace shouldn't editable at billing page");
        AssertFailAndContinue(!billingPageActions.isDisplayed(billingPageActions.proceedWithPaPalButton) && billingPageActions.isDisplayed(billingPageActions.nextReviewButton), "Proceed with paypal button is not displaying and Next Review button is displaying");

    }
    @Test(dataProvider = dataProviderName, priority = 6, groups = {CHECKOUT, REGISTEREDONLY, PROD_REGRESSION})
    public void storeSwitchAndOrderPlacement(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Check whether the payment card is updated properly");
        Map<String, String> editBillDetailUS = excelReaderDT2.getExcelData("BillingDetails", "Amex");
        Map<String, String> editBillDetailCA = excelReaderDT2.getExcelData("BillingDetails", "AmexCA");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetails = null, visaBill = null, mcBill = null;
        String url = null;
        if (store.equalsIgnoreCase("US")) {
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            mcBill = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            url = getDT2TestingCellValueBySheetRowAndColumn("CanonicalURL","ItemURLUS","ProdDetailsPage");
            if(env.equalsIgnoreCase("prod")){
                mcBill = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
                editBillDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            mcBill = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            url = getDT2TestingCellValueBySheetRowAndColumn("CanonicalURL","ItemURLCA","ProdDetailsPage");
            if(env.equalsIgnoreCase("prod")){
                mcBill = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
                editBillDetailCA = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD2");
            }
        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        if (store.equalsIgnoreCase("US")) {
            addDefaultShipMethod(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"),
                    shipDetails.get("country"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"));
        }

        if (store.equalsIgnoreCase("CA")) {
            addDefaultShipMethod(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"),
                    shipDetails.get("country"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"));
//            editBillDetailUS = excelReaderDT2.getExcelData("BillingDetails", "editedBillingAddressCA");
        }
        //Adding default Credit Cardc
        addDefaultPaymentMethodCC(mcBill.get("fName"), mcBill.get("lName"), mcBill.get("addressLine1"), mcBill.get("city"), mcBill.get("stateShortName"),
                mcBill.get("zip"), mcBill.get("phNum"), mcBill.get("cardType"), mcBill.get("cardNumber"), mcBill.get("expMonthValueUnit"), mcBill.get("SuccessMessageContent"));

//        headerMenuActions.deleteAllCookies();
  //      addToBagApi_Reg_Login(emailAddressReg,password,"1",store,1);
        addToBagBySearching(searchKeywordAndQty);
        String cartCount = shoppingBagPageActions.getBagCount();
        if(store.equalsIgnoreCase("CA")){
        footerActions.changeCountryAndLanguage("US","English");}
        else{
            footerActions.changeCountryAndLanguage("CA", "English");
        }
        driver.get(url);
        productDetailsPageActions.selectAnySizeAndClickAddToBagInPDP();
        headerMenuActions.staticWait(3000);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Click on checkout button with shipping and payment saved in account");
        AssertFailAndContinue(reviewPageActions.clickEditBillingAddress(billingPageActions), "Click on edit shipping link and navigate to shipping page");
        AssertFailAndContinue(billingPageActions.verifyPrepopulatedCard(mcBill.get("cardNumber")), "Verify the card details are pre-populated in billing");
        if(store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(billingPageActions.editPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, editBillDetailUS.get("fName"), editBillDetailUS.get("lName"), editBillDetailUS.get("addressLine1"), editBillDetailUS.get("addressLine2"), editBillDetailUS.get("city"), editBillDetailUS.get("stateShortName"), editBillDetailUS.get("zip"), editBillDetailUS.get("cardNumber"), editBillDetailUS.get("securityCode"), editBillDetailUS.get("expMonthValueUnit")), "Edit the entered the Payment details and the Billing Address and clicked on the Next Review button.");
        }
        else{
            AssertFailAndContinue(billingPageActions.editPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, editBillDetailCA.get("fName"), editBillDetailCA.get("lName"), editBillDetailCA.get("addressLine1"), editBillDetailCA.get("addressLine2"), editBillDetailCA.get("city"), editBillDetailCA.get("stateShortName"), editBillDetailCA.get("zip"), editBillDetailCA.get("cardNumber"), editBillDetailCA.get("securityCode"), editBillDetailCA.get("expMonthValueUnit")), "Edit the entered the Payment details and the Billing Address and clicked on the Next Review button.");
        }
        AssertFailAndContinue(reviewPageActions.verifyShippingAddressSection(), "verify the shipping address section in order review page");
        AssertFailAndContinue(reviewPageActions.verifyBillingAddressSection(), "verify the billing address section in order review page");
        AssertFailAndContinue(reviewPageActions.verifyLastNumber(editBillDetailUS.get("cardNumber")), "Verify the card is updated in Review page");
        AssertFailAndContinue(reviewPageActions.clickReturnToBillingLink(billingPageActions), "clicking On return to billing link navigates to billing page with billing accordian active");
        if(store.equalsIgnoreCase("US")) {
            billingPageActions.clickNextReviewButton(reviewPageActions, editBillDetailUS.get("securityCode"));
        }
        else{
            billingPageActions.clickNextReviewButton(reviewPageActions, editBillDetailCA.get("securityCode"));

        }
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            AssertFailAndContinue(orderConfirmationPageActions.verifyTheItemCountWithBag(cartCount), "Verify the item count should be match with the cart count in shopping bag page");
            getAndVerifyOrderNumber("validatePaymentReview");
        }
    }

}