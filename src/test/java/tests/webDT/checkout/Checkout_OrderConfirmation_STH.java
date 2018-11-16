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
 * Created by AbdulazeezM on 5/19/2017.
 */
public class Checkout_OrderConfirmation_STH extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg;
    private String password;
    private String count;
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();

        if (store.equalsIgnoreCase("US") && user.equalsIgnoreCase("registered")) {
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);

        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered"))
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }
        driver.navigate().refresh();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {CHECKOUT, REGRESSION, PRODUCTION})
    public void validateOrderConfirmPageReg(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if a " + user + " user can add multiple STH products into the cart and apply percentage off coupon and place the order successfully with master card for store " + store + "" +
                "2. Click need help link in coupon code and verify if the coupon code details are displayed<br/>" +
                "3. DT-43789 and DT-43788");

        List<String> couponCode = null;
        if(store.equalsIgnoreCase("US")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
            if(env.equalsIgnoreCase("prod")){
                couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode"));
            }
        }
        else if(store.equalsIgnoreCase("CA")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA"));
            if(env.equalsIgnoreCase("prod")){
                couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA"));
            }
        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validMAAddressDetailsUS");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        Map<String, String> mc = null;
        List<String> helpLinkValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "helpCenter", "linkName"));
        List<String> helpURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "helpCenter", "URLValue"));

        if (store.equalsIgnoreCase("US")) {
            mc = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                mc = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            }
        }
        else if (store.equalsIgnoreCase("CA")) {
            mc = excelReaderDT2.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.validateCouponCodeSection(), "Validating Coupon code fld, apply button and need help link");
        AssertFailAndContinue(shoppingBagPageActions.applyCouponAndVerify(couponCode.get(0)), "Apply coupon code");

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page");
        }
        AssertFailAndContinue(shippingPageActions.clickNeedHelpLink(), "Click on \"Need help?\" link that is displayed against the coupon code field ; Verify if the Coupon code details is displayed in the form of a overlay.");
        shippingPageActions.click(shippingPageActions.closeModal);

        AssertFailAndContinue(shippingPageActions.validateProgressbarStatus(),"Validate progress bar indicator as follows in Shipping page\nShipping: Active(Double circle)\nBilling and Review in-active");
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"),
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        AssertFailAndContinue(billingPageActions.validateProgressbarStatus(),"Validate Progress bar indicates as follows in billing page \n Shipping Page: Completed(Tick mark)\n Billing Page - Active(Double Circle)\n Review - in-active");

        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");
        AssertFailAndContinue(reviewPageActions.validateProgressbarStatus(),"Validate Progress bar indicates as follows in review page \n Shipping Page: Completed(Tick mark)\n Billing Page - Completed(Tick mark)\n Review - Active(Double Circle)");


        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");
            AssertFailAndContinue(orderConfirmationPageActions.validateTextInOrderConfirmPageReg(), "validate the text content in order confirmation page");
            //DT-43789 and DT-43788
            AssertFailAndContinue(footerActions.contactUSLinkValidation(helpLinkValidation.get(6), helpURLValidation.get(8),store), "Validate if user navigates to " + helpURLValidation.get(8) + " page on clicking the " + helpLinkValidation.get(6) + " link");
        }
    }

    @Test(dataProvider =dataProviderName, priority = 1, groups = {CHECKOUT, REGRESSION, USONLY})
    public void validateOrderConfirmPageGuest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setRequirementCoverage("Verify if " + user + " user can add multiple STH products into the cart and apply percentage coupon and place the order successfully with discover card");
        setAuthorInfo("Jagadeesh");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validTXAddressDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "express");
        Map<String, String> di = excelReaderDT2.getExcelData("PaymentDetails", "Discover");

        List<String> couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.validateCouponCodeSection(), "Validating Coupon code fld, apply button and need help link");
        AssertFailAndContinue(shoppingBagPageActions.getText(shoppingBagPageActions.shippingTotalPrice).equalsIgnoreCase("-"), "Validating the shipping price displays as hyphen (-) at shopping bag page");

        AssertFailAndContinue(shoppingBagPageActions.applyCouponCode(couponCode.get(0)), "Apply percentage off coupon code");
        if (user.contains("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page");
        }

        if (user.contains("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page");
        }
        AssertFailAndContinue(shippingPageActions.waitUntilElementDisplayed(shippingPageActions.couponCodeApplied, 30), "The coupon is applied in Shipping page ");

        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        AssertFailAndContinue(billingPageActions.waitUntilElementDisplayed(billingPageActions.couponCodeApplied, 30), "The coupon is applied in billing page ");

        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, di.get("cardNumber"), di.get("securityCode"), di.get("expMonthValue")), "Entered Payment Details and Navigates to Review page");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");
        if (user.contains("registered"))
            AssertFailAndContinue(orderConfirmationPageActions.validateOrderLedgerSectionGuestReg(), "Validate the order ledger section in order confirmation page");
        if (user.contains("guest"))
            AssertFailAndContinue(orderConfirmationPageActions.validateTextInOrderConfirmPageGuest(), "Validate the content present in the order confirmation page as a guest user");
        getAndVerifyOrderNumber("validateOrderConfirmPageGuest");
    }

    //@Test
    public void errMsgValidationInPwdField() throws Exception {
        //see the below test
    }

    @Parameters(storeXml)
    @Test(priority = 2, groups = {CHECKOUT, CREATEACCOUNT, REGRESSION,GUESTONLY, CHEETAH})
    public void createAccFromOrderConfirmPage(@Optional("US") String store) throws Exception {

        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify whether user is able to create an account after placing an Ecomm order as guest user in the " + store + " store\n" +
                "5. Verify Global Nav banner in shopping bag and Order Confirmation page" +
                "DT-44309");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        Map<String, String> vi = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        List<String> passwordErrMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "emptyAllFields", "ErrorMessages"));
        List<String> splChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "specialChar", "Value"));
        List<String> expPwdError = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount_ErrMsgs", "pwdSplCase", "ErrorMessages"));

        addToBagBySearching(searchKeywordAndQty);
        // TODO : Need to enable after configuring the GLobal Navigation banner.
        // AssertFailAndContinue(headerMenuActions.validateGlobalNavBanner(), "Verify global navigation bar in shopping bag page");

        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Click on checkout button and user displayed with login page");
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Click on continue as guest user and check user redirected to the shipping page");

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"),
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");

        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
        AssertFailAndContinue(orderConfirmationPageActions.isDisplayed(orderConfirmationPageActions.confBannerEspot), "The confirmation banner espot displays below the prod recommendations");
        getAndVerifyOrderNumber("createAccFromOrderConfirmPage");
        // TODO : Need to enable after configuring the GLobal Navigation banner.
        //AssertFailAndContinue(headerMenuActions.validateGlobalNavBanner(), "Verify global navigation bar in Order Confirmation page");

        AssertFailAndContinue(orderConfirmationPageActions.validateTextInOrderConfirmPageGuest(), "Validate the content present in the order confirmation page as a guest user");
        AssertFailAndContinue(orderConfirmationPageActions.validateErrorMessagesTab(passwordErrMsg.get(4), passwordErrMsg.get(5)), "Validate Error message when Tab the Field");
        AssertFailAndContinue(orderConfirmationPageActions.showHideLinkPassword("P@ssw0rd"), "Validated the actions of the Show/Hide link");
        AssertFailAndContinue(createAccountActions.validateToolTipContent(), "validate content of tool tip icon in the Create Account drawer.");
        AssertFailAndContinue(orderConfirmationPageActions.validateErrPassword(splChar.get(5), expPwdError.get(0)), "Validated the error message for the Password and Confirm Password fields when the password has less than 8 characters.");
        AssertFailAndContinue(orderConfirmationPageActions.validateErrPassword(splChar.get(6), expPwdError.get(0)), "Validated the error message for the Password and Confirm Password fields when the password has no special characters.");
        AssertFailAndContinue(orderConfirmationPageActions.validateErrPassword(splChar.get(7), expPwdError.get(0)), "Validated the error message for the Password and Confirm Password fields when the password has no numeric characters.");
        AssertFailAndContinue(orderConfirmationPageActions.validateErrPassword(splChar.get(8), expPwdError.get(0)), "Validated the error message for the Password and Confirm Password fields when the has no uppercase. Error Msg: Password must include atleast one uppercase character(s).");
        AssertFailAndContinue(orderConfirmationPageActions.validateErrPassword(splChar.get(6), expPwdError.get(0)), "Validated the error message for the Password and Confirm Password fields when the password has no special characters.");
        AssertFailAndContinue(orderConfirmationPageActions.validateErrPassword(splChar.get(7), expPwdError.get(0)), "Validated the error message for the Password and Confirm Password fields when the password has no numeric characters.");
        AssertFailAndContinue(orderConfirmationPageActions.validateErrPassword(splChar.get(8), expPwdError.get(0)), "Validated the error message for the Password and Confirm Password fields when the password has no upper case alpha characters.");
        AssertFailAndContinue(orderConfirmationPageActions.createAccInOrderConfirmPage(), "Enter a valid password and create a new account");
        AssertFailAndContinue(headerMenuActions.isDisplayed(headerMenuActions.welcomeCustomerLink), "Header displays with username loggedin after creating an account");
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
        myAccountPageActions.clickOrdersLink();
        myAccountPageActions.orderDisplayInHistoryGuestAndReg();
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {CHECKOUT, REGRESSION,PROD_REGRESSION})
    public void e2e_Scenario_6(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setRequirementCoverage("Verify if guest user can add multiple STH products with various quantity into the cart and place the order successfully");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchTerm2", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "express");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validHIAddress");
        List<String> couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
        Map<String, String> mc = excelReaderDT2.getExcelData("PaymentDetails", "MasterCard");

        if(env.equalsIgnoreCase("prod")){
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode"));

        }
        if (store.equalsIgnoreCase("CA")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA"));
            if (env.equalsIgnoreCase("prod")) {
                couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA"));

            }
        }

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearching(searchKeywordAndQty);

        if (user.contains("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page");
        }

        if (user.contains("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page");
        }

        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValue")), "Entered Payment Details and Navigates to Review page");
        AssertFailAndContinue(reviewPageActions.clickMerchandiseApplyCoupon("AUTOMATION 10%", couponCode.get(0)),"Applied coupon at review page and able to see Remove button");
        AssertFailAndContinue(reviewPageActions.removeAppliedCoupons(),"Able to remove applied coupons at review page");
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button and place the order");
            AssertFailAndContinue(headerMenuActions.getQtyInBag().equals("0"), "The quantity in bag is showing 0 at Confirmation page");
            getAndVerifyOrderNumber("e2e_Scenario_6");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 4, groups = {CHECKOUT, REGRESSION, "giftservice", FDMS,PROD_REGRESSION})
    public void checkoutwith_Visa(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store.toUpperCase() + " store, verify - <br/>" +
                "1. whether user is able to place an ecomm order with master card as payment method" +
                "2. Enter extra space in address line1 and check if order is able to place" +
                "3. place an order with gift wrap service for US store" +
                "4. Enter special characters in First name, Last Name and Address Line1. Verify Shipping internationally in not overlapped with country dropdown\n" +
                "5. Verify same as shipping checkbox in enabled by default in billing page" +
                "6. Sign Up email in checkout for guest user in US store should be check by default" +
                "7. DT-43826");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validMAAddressDetailsUS");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "rush");
        Map<String, String> vi = excelReaderDT2.getExcelData("BillingDetails", "Visa");
        String validText = getDT2TestingCellValueBySheetRowAndColumn("OrderStatus","OrderConfirmationText","Content");
        String errorText1 = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails","TransactionalSMSChar","phNum");
        String errorText2 = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails","TransactionalSMSNum","phNum");
        String errorMessage1 = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails","TransactionalSMSChar","Details");
        String errorMessage2 = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails","TransactionalSMSNum","Details");
        String success_msg = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "SuccessMessage", "Details");
        String subscribedNo = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "subscribedNo", "phNum");
        String subscribedNo_msg = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "alreadySubscribed", "phNum");
        String validNo = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "validNo", "phNum");

        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }

        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearchingAndCheckoutFromNotification(searchKeywordAndQty, user);

        shippingPageActions.waitUntilElementDisplayed(shippingPageActions.firstNameFld);
        shippingPageActions.clearAndFillText(shippingPageActions.firstNameFld, "!@#$%4");
        shippingPageActions.clearAndFillText(shippingPageActions.lastNameFld, "!##$5");
        shippingPageActions.clearAndFillText(shippingPageActions.addressLine1Fld, "!#$%%5");
        shippingPageActions.click(shippingPageActions.lastNameFld);
        AssertFailAndContinue(shippingPageActions.waitUntilElementDisplayed(shippingPageActions.shipInternationalLnk), "enters special character,number in First Name,Last Name and Address Line 1, verify that \"Shipping internationally\" link should not overlapped with country field.");

        if (store.equalsIgnoreCase("US")) {
            shippingPageActions.selectGiftWrappingAndGiftMessage();
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1") + "    ",
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(!shippingPageActions.isSelected(shippingPageActions.signupEmail_ChkBox, shippingPageActions.signupEmail_ChkInput), "Opt into marketing emails should be defaulted as un-selected");
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1") + "   ",
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
        }
        AssertFailAndContinue(billingPageActions.isSelected(billingPageActions.sameAsShippingChkBox, billingPageActions.sameAsShippingChkBoxinput), "Verify \"same as shipping\" checkbox is checked by default in the billing page");
        billingPageActions.orderLedgerAfterAddingGiftWrapping();
        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button, Verify order is place successfully");
           //DT-43826
            AssertFailAndContinue(orderConfirmationPageActions.validateTextInOrderConfirmPageShipIt(validText),"Verify the content displayed in Order confirmation page");

        }
        //driver.navigate().refresh();
        //AssertFailAndContinue(orderConfirmationPageActions.waitUntilElementDisplayed(orderConfirmationPageActions.orderNumber, 10), "Verify that the " + user + " user in the " + store + " store when completes the Checkout and lands on the Order Confirmation page and perform browser page refresh action, the page should reload and the user should land on the Order Confirmation page");
        /*AssertFailAndContinue(orderConfirmationPageActions.validateTransactionalSMSSignUpCheckbox(),"Verify the transactional sms display in the Order confirmation page");

        //Validating the same in checkOrderConfirmTransSMS script
        AssertFailAndContinue(orderConfirmationPageActions.checkTransactionalSMSSignUpCheckBox(),"Verify the fields displayed in the Order confirmation change");
        if(store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(orderConfirmationPageActions.getSmsPhone().equalsIgnoreCase(shipDetailUS.get("phNum")), "Verify that mobile number is prepopulated but editable on order confirmation page when user keyed in the phone number on Shipping page");
        }
        else{
            AssertFailAndContinue(orderConfirmationPageActions.getSmsPhone().equalsIgnoreCase(shipDetailCA.get("phNum")), "Verify that mobile number is prepopulated but editable on order confirmation page when user keyed in the phone number on Shipping page");
        }
        orderConfirmationPageActions.updateSmsPhone(errorText2);
        orderConfirmationPageActions.clickSubmitBtn();
        AssertFailAndContinue(orderConfirmationPageActions.validateSmsErrorMessage(errorMessage1), "Validate Error Message for invalid mobile no");
        orderConfirmationPageActions.updateSmsPhone(errorText1);
        AssertFailAndContinue(orderConfirmationPageActions.validateSmsErrorMessage(errorMessage1), "Validate Error Message for special character mobile no");
        orderConfirmationPageActions.updateSmsPhone("");
        orderConfirmationPageActions.clickSubmitBtn();
        AssertFailAndContinue(orderConfirmationPageActions.validateSmsErrorMessage(errorMessage2), "Validate Error Message for empty mobile no");
        orderConfirmationPageActions.updateSmsPhone(shipDetailUS.get("phNum"));
        orderConfirmationPageActions.clickSubmitBtn();
        AssertFailAndContinue(orderConfirmationPageActions.validatePageLevelError(errorMessage1), "Validate Error Message for when mobile no is not available");
        mheaderMenuActions.scrollToTop();
        AssertFailAndContinue(orderConfirmationPageActions.uncheckSmsSignUpCB(), "Un-select checkbox verify sms phone field is disappeared");
        orderConfirmationPageActions.checkSmsSignUpCB();
        orderConfirmationPageActions.updateSmsPhone(subscribedNo);
        orderConfirmationPageActions.clickSubmitBtn();
        AssertFailAndContinue(orderConfirmationPageActions.validatePageLevelError(subscribedNo_msg), "verify Error message when user entered Already Subscribed phone no");
        orderConfirmationPageActions.updateSmsPhone(validNo);
        orderConfirmationPageActions.clickSubmitBtn();
        AssertFailAndContinue(orderConfirmationPageActions.getSuccessMessage(success_msg), "verify Success message when user entered new phone no");
*/
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {CHECKOUT, REGRESSION,PROD_REGRESSION})
    public void checkoutwith_AMEX(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store.toUpperCase() + " store, verify whether user is able to place an ecomm order with AMEX card as payment method");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validMAAddressDetailsUS");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "rush");
        Map<String, String> vi = excelReaderDT2.getExcelData("BillingDetails", "Amex");

        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }

        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }
        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(headerMenuActions.clickMiniCartAndCheckoutAsRegUser(shoppingBagDrawerActions, shippingPageActions), "Click on checkout button from mini cart bag and verify shipping page as Registered user");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(headerMenuActions.clickMiniCartAndCheckoutAsGuestUser(shoppingBagDrawerActions, shippingPageActions), "Click on checkout button from mini cart bag and verify shipping page as guest user");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                    shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"),
                    shipDetailCA.get("addressLine2"), shipDetailCA.get("city"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum"), ""), "Entered the Shipping address and clicked on the Next Billing button.");
        }

        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");
        AssertFailAndContinue(reviewPageActions.clickOnShippingAccordion(shippingPageActions), "Verify whether " + user + " user in " + store + " Store who is in the Order Review section, is able to navigate to the Shipping section using the accordion navigation");
        shippingPageActions.clickNextBillingButton(billingPageActions);
        AssertFailAndContinue(billingPageActions.clickOnShippingAccordian(shippingPageActions), "Verify whether " + user + " user in " + store + " Store who is in the billing section, is able to navigate to the shipping section using the accordion navigation");
        AssertFailAndContinue(!shippingPageActions.getText(shippingPageActions.selectedShippingMethod).contains("FREE"), "Verify \"Standard\" radio button should be displayed with threshold amount(5$) instead of text as \"FREE\" shipping");
        shippingPageActions.clickNextBillingButton(billingPageActions);
        billingPageActions.clickNextReviewButton(reviewPageActions, vi.get("securityCode"));
        AssertFailAndContinue(reviewPageActions.clickOnBillingAccordion(billingPageActions), "Verify whether " + user + " user in " + store + " Store who is in the Review section, is able to navigate to the billing section using the accordion navigation");
        billingPageActions.clickNextReviewButton(reviewPageActions, vi.get("securityCode"));

        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button, Verify order is place successfully");
            AssertFailAndContinue(orderConfirmationPageActions.getText(orderConfirmationPageActions.shippinglabel).contains("Shipping"), "Verify Shipping label in Order edger section");
            AssertFailAndContinue(orderConfirmationPageActions.getText(orderConfirmationPageActions.taxlabel).contains("Tax"), "Verify Tax label in Order edger section");


        }
        /*if (user.equalsIgnoreCase("registered")) {
            orderConfirmationPageActions.staticWait(30000); //wait for to cancel the order
            orderConfirmationPageActions.clickOnSingleOrderNum(orderStatusActions);
            AssertFailAndContinue(orderStatusActions.getText(orderStatusActions.subTotal).contains("0.00"), "Verify order total for cancelled order in details page is $0.00");
        }*/
    }

    @Test(priority = 6, dataProvider = dataProviderName, groups = {CHECKOUT, REGRESSION, USONLY, FAVORITES})
    public void checkoutwith_Discover(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a user in " + store.toUpperCase() + " store, verify whether user is able to place an ecomm order with DISCOVER card as payment method");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validMAAddressDetailsUS");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "rush");
        Map<String, String> vi = excelReaderDT2.getExcelData("BillingDetails", "Discover");
        List<String> aboutUsLinkValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "aboutUs", "linkName"));
        List<String> aboutUsURLValidation = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "aboutUs", "URLValue"));

        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        }

        if(env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")){
            vi = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }
        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.moveProdToWLByPositionAsReg(1), "Verify if the 1st product moved to the WL page");
            headerMenuActions.clickWishListAsRegistered(favoritePageActions);
            count = headerMenuActions.getText(favoritePageActions.purchasedItemDisplay.get(0));
            favoritePageActions.addProdToBagFromWL(headerMenuActions, 1);
            AssertFailAndContinue(headerMenuActions.clickMiniCartAndCheckoutAsRegUser(shoppingBagDrawerActions, shippingPageActions), "Click on checkout button from mini cart bag and verify shipping page as Registered user");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(headerMenuActions.clickMiniCartAndCheckoutAsGuestUser(shoppingBagDrawerActions, shippingPageActions), "Click on checkout button from mini cart bag and verify shipping page as guest user");
        }

        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"),
                shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, vi.get("cardNumber"), vi.get("securityCode"), vi.get("expMonthValueUnit")), "Entered Payment Details and Navigates to Review page");

        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Click on order submit button, Verify order is place successfully");

            if (user.equalsIgnoreCase("registered")) {
                headerMenuActions.clickWishListAsRegistered(favoritePageActions);
                AssertFailAndContinue(favoritePageActions.validatePurchasedCount(count), "Verify item in favorites page labeled as purchased");
                AssertFailAndContinue(favoritePageActions.removeProdFromWL(1), "Verify that the user is able to remove the item form Favorite");
            }
            if (store.equalsIgnoreCase("US")) {
                AssertFailAndContinue(footerActions.clickOnAboutUSLinkAndValidateURL(aboutUsLinkValidation.get(5), aboutUsURLValidation.get(4)), "ensure that if user navigates to Recall Information page on clicking the Recall Information link");
            } else {
                AssertFailAndContinue(footerActions.blogLinkNotDisplayedInCA(), "Verify that the Blog link is not displayed in US store");
            }
        }
    }
    @Test(dataProvider = dataProviderName, priority = 4, groups = {CHECKOUT, REGRESSION, FDMS,USONLY})
    public void checkOrderConfirmTransSMS(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Srijith");
        setRequirementCoverage("As a " + user + " user in " + store.toUpperCase() + " store, verify - <br/>" +
                "1. whether the user is able to see and process the Transactional sms signup" +
                "2. Error message validation in Transactional Field "+
                "DT-43766");

        Map<String, String> enteredShipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> updateShipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validMAAddressDetailsUS");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        String errorText1 = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails","TransactionalSMSChar","phNum");
        String errorText2 = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails","TransactionalSMSNum","phNum");
        String errorMessage1 = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails","TransactionalSMSChar","Details");
        String errorMessage2 = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails","TransactionalSMSNum","Details");
        String success_msg = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "SuccessMessage", "Details");
        String subscribedNo = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "subscribedNo", "phNum");
        String subscribedNo_msg = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "alreadySubscribed", "phNum");
        String validNo = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "validNo", "phNum");

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        performNormalCheckout(store,user,emailAddressReg);
        AssertFailAndContinue(orderConfirmationPageActions.validateTransactionalSMSSignUpCheckbox(),"Verify the transactional sms display in the Order confirmation page");
        AssertFailAndContinue(orderConfirmationPageActions.checkTransactionalSMSSignUpCheckBox(),"Verify the fields displayed in the Order confirmation change");
        if(store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(orderConfirmationPageActions.getSmsPhone().equalsIgnoreCase(enteredShipDetailUS.get("phNum")), "Verify that mobile number is prepopulated but editable on order confirmation page when user keyed in the phone number on Shipping page");
        }
        else{
            AssertFailAndContinue(orderConfirmationPageActions.getSmsPhone().equalsIgnoreCase(shipDetailCA.get("phNum")), "Verify that mobile number is prepopulated but editable on order confirmation page when user keyed in the phone number on Shipping page");
            enteredShipDetailUS=shipDetailCA;
        }
        orderConfirmationPageActions.updateSmsPhone(errorText2);
        orderConfirmationPageActions.clickSubmitBtn();
        AssertFailAndContinue(orderConfirmationPageActions.validateSmsErrorMessage(errorMessage1), "Validate Error Message for invalid mobile no");
        orderConfirmationPageActions.updateSmsPhone(errorText1);
        AssertFailAndContinue(orderConfirmationPageActions.validateSmsErrorMessage(errorMessage1), "Validate Error Message for special character mobile no");
        orderConfirmationPageActions.updateSmsPhone("");
        orderConfirmationPageActions.clickSubmitBtn();
        AssertFailAndContinue(orderConfirmationPageActions.validateSmsErrorMessage(errorMessage2), "Validate Error Message for empty mobile no");
        orderConfirmationPageActions.updateSmsPhone(updateShipDetailUS.get("phNum"));
        orderConfirmationPageActions.clickSubmitBtn();
        AssertFailAndContinue(orderConfirmationPageActions.validatePageLevelError(errorMessage1), "Validate Error Message for when mobile no is not available");
        AssertFailAndContinue(orderConfirmationPageActions.uncheckSmsSignUpCB(), "Un-select checkbox verify sms phone field is disappeared");
        orderConfirmationPageActions.checkSmsSignUpCB();
        orderConfirmationPageActions.updateSmsPhone(subscribedNo);
        orderConfirmationPageActions.clickSubmitBtn();
        AssertFailAndContinue(orderConfirmationPageActions.validatePageLevelError(subscribedNo_msg), "verify Error message when user entered Already Subscribed phone no");
        orderConfirmationPageActions.updateSmsPhone(validNo);
        orderConfirmationPageActions.clickSubmitBtn();
        AssertFailAndContinue(orderConfirmationPageActions.getSuccessMessage(success_msg), "verify Success message when user entered new phone no");

    }

}
