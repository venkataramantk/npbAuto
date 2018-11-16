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
 * Created by Venkat on 3/27/2017.
 */
//DT-607
public class ExpressCheckoutSTH extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    //private String rowInExcel = "CreateAccountUS";
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
        if (store.equalsIgnoreCase("US") && user.equalsIgnoreCase("registered")) {
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");

        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
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
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, PRODUCTION, DRAGONFLY, SMOKE})
    public void expressCheckoutReg(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Registered user, having a default Shipping and Billing Address (Visa) along with a default Shipping method (Rush), is able to perform the Express Checkout for a Ship to Home Product" +
                "DT-44903" + "DT-44905");
        Map<String, String> shipDetails = null, visaBill = null, mcBill = null;
        String phoneno = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "PhoneNumber");
        if (store.equalsIgnoreCase("US")) {
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            visaBill = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
            mcBill = excelReaderDT2.getExcelData("BillingDetails", "Visa");
            if (env.equalsIgnoreCase("prod")) {
                mcBill = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
                visaBill = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");

            }
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            visaBill = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
            mcBill = excelReaderDT2.getExcelData("BillingDetails", "Visa");
            if (env.equalsIgnoreCase("prod")) {
                mcBill = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
                visaBill = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD2");

            }
        }
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        if (store.equalsIgnoreCase("US")) {
            addDefaultShipMethod(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"),
                    shipDetails.get("country"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"));
        }

        if (store.equalsIgnoreCase("CA")) {
            addDefaultShipMethod(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"),
                    shipDetails.get("country"), shipDetails.get("stateShortName"), shipDetails.get("zip"), phoneno);
        }
        //Adding default Credit Card
        addDefaultPaymentMethodCC(visaBill.get("fName"), visaBill.get("lName"), visaBill.get("addressLine1"), visaBill.get("city"), visaBill.get("stateShortName"),
                visaBill.get("zip"), phoneno, mcBill.get("cardType"), mcBill.get("cardNumber"), mcBill.get("expMonthValueUnit"), visaBill.get("SuccessMessageContent"));

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        AssertFailAndContinue(!plccActions.verifyPlCCForm(), "Verify PLCC form is not displayed for non-eligible billling address");
        AssertFailAndContinue(reviewPageActions.validateProgressbarStatus(), "Verify progress bar status as follow \n Shipping and billing -completed(tick mark)\n" +
                "Review page - active(Double circle)");
        AssertFailAndContinue(reviewPageActions.verifyOrderExpReviewPageSTH(), "Verify the order review page for ship to home products");
        AssertFailAndContinue(reviewPageActions.verifyShippingAddressSection(), "verify the shipping address section in order review page");
        AssertFailAndContinue(reviewPageActions.verifyBillingAddressSection(), "verify the billing address section in order review page");
        AssertFailAndContinue(reviewPageActions.validateOrderLedgerSection(), "verify the order ledger section in order review page");

        reviewPageActions.clickEditBillingLink(billingPageActions);
        billingPageActions.click(billingPageActions.editLinkOnCard);
        AssertFailAndContinue(billingPageActions.isSelected(billingPageActions.sameAsShippingChkBox, billingPageActions.sameAsShippingChkBoxinput), "Verify \"same as shipping\" checkbox is checked by default in the billing page");
        billingPageActions.clickOnShippingAccordian(shippingPageActions);
        shippingPageActions.editShippingAddressLink();

        AssertFailAndContinue(shippingPageActions.getAttributeValue(shippingPageActions.phNumFld, "value").equalsIgnoreCase(phoneno), "Verify Phone number is auto populated when user clicks on Edit Address in Shipping page");
        AssertFailAndContinue(reviewPageActions.clickOnLogo(), "click on the tcp logo displayed in review page");
        reviewPageActions.click(reviewPageActions.returnBagBtn);

        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions); //go to my account
        //add new address

        if (store.equalsIgnoreCase("US")) {
            addDefaultShipMethod("new default", "Address", shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"),
                    shipDetails.get("country"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"));
        }

        if (store.equalsIgnoreCase("CA")) {
            addDefaultShipMethod("new default", "Address", shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"),
                    shipDetails.get("country"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"));
        }

        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions);
        AssertFailAndContinue(!reviewPageActions.getshipAddress().contains("new default"), "add new shipping address as default address and proceed again to Express Checkout page,verify that new address is NOT getting reflected in Express Checkout/Checkout page.");
        reviewPageActions.clickOnBillingAccordion(billingPageActions);
        AssertFailAndContinue(billingPageActions.waitUntilElementDisplayed(billingPageActions.paymentMethod, 5), "Click on Billing accordion, verify default payment method is is displayed in billing page");
        AssertFailAndContinue(billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, "333"), "Back to billing page displays CVV and enters cvv");
        AssertFailAndContinue(reviewPageActions.expReviewCVVCheck(), "CVV field shouldn't display at review page after entering cvv at billing page");
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            getAndVerifyOrderNumber("expressCheckoutReg");
        }
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, DRAGONFLY, USONLY, PROD_REGRESSION})
    public void expressCheckoutDefaultPay(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("jagadeesh");
        setRequirementCoverage("Verify if the Registered user, having only default Billing Address is able to perform the Express Checkout for a Ship to Home Product");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");

        Map<String, String> shipDetailUS = null;
        Map<String, String> discoverBillUS = null;

        if (store.equalsIgnoreCase("US")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            discoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "Discover");
            if (env.equalsIgnoreCase("prod")) {
                discoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

            }
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            discoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                discoverBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Credit Card
        addDefaultPaymentMethodAndBillingAddress(discoverBillUS.get("fName"), discoverBillUS.get("lName"), discoverBillUS.get("addressLine1"), discoverBillUS.get("city"), discoverBillUS.get("stateShortName"),
                discoverBillUS.get("zip"), discoverBillUS.get("phNum"), discoverBillUS.get("cardType"), discoverBillUS.get("cardNumber"), discoverBillUS.get("expMonthValueUnit"), discoverBillUS.get("SuccessMessageContent"));

        AssertFailAndContinue(myAccountPageActions.makeDefaultShippingToNonDefault(), "Modified Default Shipping to Non Default");
        addToBagBySearching(searchKeywordAndQty);

        //Clicked on Proceed to Checkout, to initiate Express Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Clicked on the Checkout button initiating Checkout");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        billingPageActions.click(billingPageActions.editLinkOnCard);
        AssertFailAndContinue(billingPageActions.clickSaveButton(), "Verify user able to view the billing address for Default payment method, when user added only default payment method with address and not having any shipping address in my account");
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(billingPageActions.enterCVVForExpressAndClickNextReviewBtn(discoverBillUS.get("securityCode"), reviewPageActions), "Entered the CVV code and clicked Next Order Review button.");
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Order Submit button.");
            getAndVerifyOrderNumber("expressCheckoutDefaultPay");
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, DRAGONFLY, PROD_REGRESSION})
    public void expressCheckoutDefaultShip(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Registered user, having a default Shipping along with a default Shipping method, is able to perform the Express Checkout for a Ship to Home Product");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (store.equalsIgnoreCase("CA")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
        }
        if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")) {
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
        } else if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")) {
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
        }
        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Clicked on the Login Link on the Header to view the Login Drawer.");
        AssertFailAndContinue(loginDrawerActions.successfulLogin(emailAddressReg, password, headerMenuActions), "Enter the login details and click on the login button");
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        addToBagBySearching(searchKeywordAndQty);

        //Clicked on Proceed to Checkout, to initiate Express Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Clicked on the Checkout button to land on the Shipping Page.");
        AssertFailAndContinue(shippingPageActions.clickNextBillingButton(billingPageActions), "Clicked on the Next Billing button in the Shipping section.");
        AssertFailAndContinue(billingPageActions.isSelected(billingPageActions.sameAsShippingChkBox, billingPageActions.sameAsShippingChkBoxinput), "Verify \"same as shipping\" checkbox is checked by default in the billing page");
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetailUS.get("fName"), billDetailUS.get("lName"), billDetailUS.get("addressLine1"), billDetailUS.get("addressLine2"), billDetailUS.get("city"), billDetailUS.get("stateShortName"), billDetailUS.get("zip"), billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Order Submit button.");
            getAndVerifyOrderNumber("expressCheckoutDefaultShip");
        }
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, PROD_REGRESSION})
    public void expressCheckoutAfterLoginFromCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify if the Registered user, having a default Shipping and Billing Address (Amex) along with a default Shipping method (Rush), is able to perform the Express Checkout for a Ship to Home Product after logging in from the Guest Checkout overlay.");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> amexBillUS = excelReaderDT2.getExcelData("BillingDetails", "Amex");
        Map<String, String> amexcard = excelReaderDT2.getExcelData("BillingDetails", "Amex");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (store.equalsIgnoreCase("CA")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            amexBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
        }
        if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")) {
            amexBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
        } else if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")) {
            amexBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
        }

        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Clicked on the Login Link on the Header to view the Login Drawer.");
        AssertFailAndContinue(loginDrawerActions.successfulLogin(emailAddressReg, password, headerMenuActions), "Enter the login details and click on the login button " + emailAddressReg);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Credit Card
        addDefaultPaymentMethodCC(amexBillUS.get("fName"), amexBillUS.get("lName"), amexBillUS.get("addressLine1"), amexBillUS.get("city"), amexBillUS.get("stateShortName"),
                amexBillUS.get("zip"), amexBillUS.get("phNum"), amexcard.get("cardType"), amexcard.get("cardNumber"), amexcard.get("expMonthValueUnit"), amexcard.get("SuccessMessageContent"));

        AssertFailAndContinue(headerMenuActions.clickOnUserNameAndLogout(myAccountPageDrawerActions, headerMenuActions), "Clicked on the Logout link in the My Account Drawer.");
        addToBagBySearching(searchKeywordAndQty);
        //Clicked on Proceed to Checkout, to initiate Express Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Clicked on the Checkout button as Guest user. View the Checkout Login overlay.");
        AssertFailAndContinue(loginPageActions.loginAsRegisteredUserFromLoginForm(emailAddressReg, password), "Clicked on the Login button after entering valid credentials, initiating Express Checkout");
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.enterExpressCO_CVVAndSubmitOrderCheck(amexcard.get("securityCode"), orderConfirmationPageActions), "Clicked on the Order Submit button.");
            getAndVerifyOrderNumber("expressCheckoutAfterLoginFromCheckout");
        }
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, USONLY, PROD_REGRESSION})
    public void expressCheckoutAfterLoginFromBagPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Registered user, having a default Shipping and Billing Address (Master Card) along with a default Shipping method (Rush), is able to perform the Express Checkout for a Ship to Home Product after logging in from the Login overlay in the Shopping bag page in US sttore.");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        List<String> couponCode = null;
        Map<String, String> masterBill = null;
        Map<String, String> shipDetails = null;
        if (store.equalsIgnoreCase("US")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
            masterBill = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        } else if (store.equalsIgnoreCase("CA")) {
            masterBill = excelReaderDT2.getExcelData("BillingDetails", "MasterCardCA");
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA"));

        }
        if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")) {
            masterBill = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode"));

        } else if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")) {
            masterBill = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA"));

        }
        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Clicked on the Login Link on the Header to view the Login Drawer.");
        AssertFailAndContinue(loginDrawerActions.successfulLogin(emailAddressReg, password, headerMenuActions), "Enter the login details and click on the login button");
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"),
                shipDetails.get("country"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"));

        //Adding default Credit Card
        addDefaultPaymentMethodCC(masterBill.get("fName"), masterBill.get("lName"), masterBill.get("addressLine1"), masterBill.get("city"), masterBill.get("stateShortName"),
                masterBill.get("zip"), masterBill.get("phNum"), masterBill.get("cardType"), masterBill.get("cardNumber"), masterBill.get("expMonthValueUnit"), masterBill.get("SuccessMessageContent"));

        AssertFailAndContinue(headerMenuActions.clickOnUserNameAndLogout(myAccountPageDrawerActions, headerMenuActions), "Clicked on the Logout link in the My Account Drawer.");

        addToBagBySearching(searchKeywordAndQty);
        //Clicked on Proceed to Checkout, to initiate Express Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickLoginFromEspotLnk(loginPageActions), "Clicked on the Login from My Place Rewards eSpot link. The Login overlay is displayed.");
        AssertFailAndContinue(loginPageActions.loginAsRegisteredUserFromLoginForm(emailAddressReg, password), "Clicked on the Login button after entering the valid credentials.");
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        if (!env.equalsIgnoreCase("prod")) {
            if (!reviewPageActions.isMerchandiseCouponApplied("10%")) {
                reviewPageActions.clickMerchandiseApplyCoupon("AUTOMATION 10%", couponCode.get(0).toLowerCase());
            }
            AssertFailAndContinue(reviewPageActions.estimatedTotalAfterAppliedCoupon(), "The estimated total after applying the coupon in lower case");
            reviewPageActions.clickOnShippingAccordion(shippingPageActions);
            shippingPageActions.removeAppliedCoupons();
            String lowerCase = couponCode.get(0).toLowerCase();
            String camelCaesCoupon = lowerCase.replace(lowerCase.substring(6, 7), lowerCase.substring(6, 7).toUpperCase());
            AssertFailAndContinue(shippingPageActions.applyCouponCode(camelCaesCoupon), "Applu Coupon in came case and verify applie successfully");
            shippingPageActions.removeAppliedCoupons();
            AssertFailAndContinue(shippingPageActions.getCouponDiscount() == 0.00, "verifying for the coupon discount display after removing coupon at shipping page");
            shippingPageActions.clickNextBillingButton(billingPageActions);
            AssertFailAndContinue(billingPageActions.getCouponDiscount() == 0.00, "verifying for the coupon discount display at billing page after removing coupon");
            billingPageActions.enterCVVForExpressAndClickNextReviewBtn(masterBill.get("securityCode"), reviewPageActions);
            AssertFailAndContinue(reviewPageActions.getCouponDiscount() == 0.00, "verifying for the coupon discount display at review page after removing coupon");
            reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions);
            AssertFailAndContinue(orderConfirmationPageActions.getCouponDiscount() == 0.00, "verifying for the coupon discount display at order confirmation page after removing coupon");
            getAndVerifyOrderNumber("expressCheckoutAfterLoginFromBagPage");
        }
    }

    @Test(priority = 5, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, PROD_REGRESSION, FAVORITES})
    public void expressCheckoutAfterLoginFromPDP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Registered user, having a default Shipping and Billing Address (Visa) along with a default Shipping method (Rush), is able to add the product to Wishlist and again add the product to bag and do express checkout");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        Map<String, String> visaBillCA = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
        Map<String, String> editBillUS = excelReaderDT2.getExcelData("BillingDetails", "editedBillingAddressUS");
        Map<String, String> editBillCA = excelReaderDT2.getExcelData("BillingDetails", "editedBillingAddressCA");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetails = shipDetailUS;
        Map<String, String> billDetails = visaBillUS;
        Map<String, String> editBill = editBillUS;
        List<String> couponCode = null;
        if (store.equalsIgnoreCase("US")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
        }
        if (store.equalsIgnoreCase("CA")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA"));
        }
        if (store.equalsIgnoreCase("CA")) {
            shipDetails = shipDetailCA;
            billDetails = visaBillCA;
            editBill = editBillCA;
        }
        if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")) {
            billDetails = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            editBill = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode"));
        } else if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")) {
            billDetails = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            editBill = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD2");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA"));

        }
        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method

        addDefaultShipMethod(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"),
                shipDetails.get("country"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"));

        //Adding default Credit Card
        addDefaultPaymentMethodCC(billDetails.get("fName"), billDetails.get("lName"), billDetails.get("addressLine1"), billDetails.get("city"), billDetails.get("stateShortName"),
                billDetails.get("zip"), billDetails.get("phNum"), billDetails.get("cardType"), billDetails.get("cardNumber"), billDetails.get("expMonthValueUnit"), billDetails.get("SuccessMessageContent"));

        List<String> upc = addToBagBySearching(searchKeywordAndQty);
        shoppingBagPageActions.addToFavorite(upc.get(0));
        AssertFailAndContinue(headerMenuActions.clickWishListAsRegistered(favoritePageActions), "Add shopping bag item to favorites");
        AssertFailAndContinue(favoritePageActions.clickAddToBagByPosWithSelectedSize(1), "Adding product back to bag.");
        headerMenuActions.staticWait(3000);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        //Clicked on Proceed to Checkout, to initiate Express Checkout
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        String estimatedTax = reviewPageActions.getText(reviewPageActions.tax_Total);
        AssertFailAndContinue(reviewPageActions.stayInCheckOut(), "Click on return to bag link and click on stay in checkout button and check if the user retained in the review page");
        // To check Billing edit link redirection from Express checkout Review
//        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        if (!reviewPageActions.isMerchandiseCouponApplied("10%")) {
            reviewPageActions.clickMerchandiseApplyCoupon("AUTOMATION 10%", couponCode.get(0).toLowerCase());
        }
        AssertFailAndContinue(reviewPageActions.estimatedTotalAfterAppliedCoupon(), "The estimated total after applying the coupon in lower case");
        AssertFailAndContinue(reviewPageActions.clickEditBillingAddress(billingPageActions), "Verify that the user is able to click the edit link near the billing details in Express checkout Review page");
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, editBill.get("fName"), editBill.get("lName"), editBill.get("addressLine1"), editBill.get("addressLine2"), editBill.get("city"), editBill.get("stateShortName"), editBill.get("zip"),
                editBill.get("cardNumber"), editBill.get("securityCode"), editBill.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        billingPageActions.removeAppliedCoupons();
        AssertFailAndContinue(reviewPageActions.expReviewCVVCheck(), "Verify that on Review Page CVV field is not displayed");
        String estimatedTax1 = reviewPageActions.getText(reviewPageActions.tax_Total);
        AssertFailAndContinue(estimatedTax != estimatedTax1, "Validate tax is veried based on zip code");
        AssertFailAndContinue(reviewPageActions.verifyEnteredBillingAddress(editBill.get("fName"), editBill.get("lName"), editBill.get("addressLine1"), editBill.get("city"), editBill.get("stateShortName"), editBill.get("zip"), store), "Verify the pre-populated address in review page");
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Verify that on Review Page CVV field is not displayed");
            getAndVerifyOrderNumber("expressCheckoutAfterLoginFromPDP");
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            myAccountPageActions.staticWait(3000);
            myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
            AssertFailAndContinue(myAccountPageActions.verifyDefaultCardDetailsAfterOrder("VISA", editBillUS.get("cardNumber"), editBillUS.get("expMonthValueUnit"), editBillUS.get("expYear")), "Verify the card details are updated and saved in My Account");
            AssertFailAndContinue(myAccountPageActions.verifyDefaultPaymentAddress(editBill.get("fName"), editBill.get("lName"), editBill.get("addressLine1"), editBill.get("city"), editBill.get("zip")), "Verify the address pre-populated in MY account Payment Page after placing the order");
        }
    }

    @Test(priority = 6, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, PAYPAL, PROD_REGRESSION})
    public void changeShippingMethodAndPlaceOrder(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if the Registered user, can change the billing option to paypal in billing page and place the order by using the paypal method for Express Checkout in " + store + store);
        Map<String, String> search_Term = excelReaderDT2.getExcelData("Search", "SearchBy");
        Map<String, String> editShipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "editedShippingAddressUS");
        Map<String, String> sm = null;
        Map<String, String> shipDetailUS = null;
        Map<String, String> masterBillUS = null;
        Map<String, String> es = null;
        if (store.equalsIgnoreCase("US")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
            es = excelReaderDT2.getExcelData("Paypal", "USPaypal");
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCardCA");
            editShipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "editedShippingAddressCA");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "ground");
            es = excelReaderDT2.getExcelData("Paypal", "CanadaPaypal");
        }
        List<String> couponCode = null;
        if (store.equalsIgnoreCase("US")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCode"));
        }
        if (store.equalsIgnoreCase("CA")) {
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "validCode", "couponCodeCA"));
        }
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")) {
            masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCode"));

        } else if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")) {
            masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            couponCode = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Coupons", "prodCoupon", "couponCodeCA"));

        }
        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        addDefaultPaymentMethodCC(masterBillUS.get("fName"), masterBillUS.get("lName"), masterBillUS.get("addressLine1"), masterBillUS.get("city"), masterBillUS.get("stateShortName"),
                masterBillUS.get("zip"), masterBillUS.get("phNum"), masterBillUS.get("cardType"), masterBillUS.get("cardNumber"), masterBillUS.get("expMonthValueUnit"), masterBillUS.get("SuccessMessageContent"));

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        if (!reviewPageActions.isMerchandiseCouponApplied("10%")) {
            reviewPageActions.clickMerchandiseApplyCoupon("AUTOMATION 10%", couponCode.get(0).toLowerCase());
        }
        AssertFailAndContinue(reviewPageActions.estimatedTotalAfterAppliedCoupon(), "The estimated total after applying the coupon in lower case");
        reviewPageActions.returnToBagPage(shoppingBagPageActions);
        shoppingBagPageActions.removeAppliedCoupons();
        AssertFailAndContinue(shoppingBagPageActions.clickProceedToCheckoutExpress(reviewPageActions), "Clicked on the Checkout button initiating Express Checkout");
        AssertFailAndContinue(reviewPageActions.clickOnShippingAccordion(shippingPageActions), "Click on return to Shipping link and check user navigate to shipping page section");
        shippingPageActions.click(shippingPageActions.editLink_ShipAddress);
        AssertFailAndContinue(shippingPageActions.editShippingDetailsShipMethodAndContinue_Reg(billingPageActions, editShipDetailUS.get("fName"), editShipDetailUS.get("lName"), editShipDetailUS.get("addressLine1"), editShipDetailUS.get("addressLine2"), editShipDetailUS.get("city"), editShipDetailUS.get("stateShortName"), editShipDetailUS.get("zip"), editShipDetailUS.get("phNum"), sm.get("shipValue")), "Edit the Entered Shipping address and clicked on the Next Billing button.");
        String parentWindow = driver.getWindowHandle();
        AssertFailAndContinue(billingPageActions.payWithPayPal(), "Continue payment with PayPal option");
//        billingPageActions.clickProceedWithPaypalModalButton(payPalPageActions);
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(payPalPageActions.paypalLogin(paypalOrderDetailsPageActions, billingPageActions, reviewPageActions, es.get("UserName"), es.get("Password"), parentWindow), "Enter the valid paypal credentials and click on the login");
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            getAndVerifyOrderNumber("changeShippingMethodAndPlaceOrder");
        }
    }

    @Test(priority = 7, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, PROD_REGRESSION})
    public void expressCheckoutFromDifferentLocation(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesj");
        setRequirementCoverage("Verify if the Registered user, having a default Shipping and Billing Address (Visa) along with a default Shipping method (Rush), is able to login from write a review and again add the product to bag and do express checkout");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        Map<String, String> searchTerm = excelReaderDT2.getExcelData("Search", "SearchBy");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> shipDetailUS = null;
        Map<String, String> visaBillUS = null;
        if (store.equalsIgnoreCase("US")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCardCA");
        }
        if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("US")) {
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

        } else if (env.equalsIgnoreCase("prod") && store.equalsIgnoreCase("CA")) {
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

        }
        AssertFailAndContinue(headerMenuActions.clickLoginLnk(loginDrawerActions), "Clicked on the Login Link on the Header to view the Login Drawer.");
        AssertFailAndContinue(loginDrawerActions.successfulLogin(emailAddressReg, password, headerMenuActions), "Enter the login details and click on the login button");
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        //Adding default Credit Card
        addDefaultPaymentMethodCC(visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"), visaBillUS.get("city"), visaBillUS.get("stateShortName"),
                visaBillUS.get("zip"), visaBillUS.get("phNum"), visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"), visaBillUS.get("SuccessMessageContent"));

        // Express checkout check after login from Write a Review ;
        AssertFailAndContinue(headerMenuActions.clickOnUserNameAndLogout(myAccountPageDrawerActions, headerMenuActions), "Clicked on the Logout link in the My Account Drawer.");
        AssertFailAndContinue(headerMenuActions.searchAndSubmit(categoryDetailsPageAction, searchTerm.get("Value")), "Entered the Search Term and clicked on the Search button");
        AssertFailAndContinue(categoryDetailsPageAction.clickRandomProductByImage(productDetailsPageActions), "Click on the product image and check if user navigate to the PDP page");
        AssertFailAndContinue(productDetailsPageActions.clickWriteAReviewLinkAsGuest(loginPageActions), "Check the login overlay is getting displayed");
        loginDrawerActions.successfulLogin(emailAddressReg, password, headerMenuActions);
        AssertFailAndContinue(productDetailsPageActions.writeAReview_Reg("I bought this for my little brother and he loves it."), "verified write a review overlay");
        if (!env.equalsIgnoreCase("prod")) {
            performExpCheckoutExceptDiscover(visaBillUS.get("securityCode"));
            logoutTheSession();

            // Exp Checkout after login from Mov to FAV in SB page :
            emptyShoppingBag();
            addToBagBySearching(searchKeywordAndQty);
            AssertFailAndContinue(shoppingBagPageActions.clickWLIconAsGuestUser(loginPageActions), "Click on WL icon present by items position in the shopping bag page");
            AssertFailAndContinue(loginPageActions.successfulLogin(emailAddressReg, password), "Enter valid credentials and click on login button " + emailAddressReg);
            performExpCheckoutExceptDiscover(visaBillUS.get("securityCode"));
            logoutTheSession();

            // Express Checkout after login from Favorite Login modal :
            emptyShoppingBag();
            AssertFailAndContinue(headerMenuActions.clickWishListAsGuest(wishListDrawerActions), "Verify if guest user can redirect to wishlist overlay");
            loginPageActions.successfulLogin(emailAddressReg, password);
            performExpCheckoutExceptDiscover(visaBillUS.get("securityCode"));
        }
    }
}