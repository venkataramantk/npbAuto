package tests.webDT.checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.Map;

/**
 * Created by Venkat on 04/05/2017.
 * Modified By Jagadeesh 07/05/2018
 */
public class BillingPage extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg = null;
    private String password = null;
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
        if (store.equalsIgnoreCase("US")) {
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, PRODUCTION})
    public void regNavigateToBillingPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage(store + " Verify if the Registered user having products in the bag, is redirected to the Billing page, after clicking on the Next:Billing button in the Shipping page and clicks Submit order places the normal checkout ship to home order");

        Map<String, String> billDetails = null;
        Map<String, String> shipDetails = null;
        Map<String, String> sm = null;

        if (store.equalsIgnoreCase("US")) {
            billDetails = excelReaderDT2.getExcelData("BillingDetails", "Visa");
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
            if (env.equalsIgnoreCase("prod")) {
                billDetails = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            }

        } else if (store.equalsIgnoreCase("CA")) {
            billDetails = excelReaderDT2.getExcelData("BillingDetails", "VisaCA");
            shipDetails = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "ground");
            if (env.equalsIgnoreCase("prod")) {
                billDetails = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        addToBagBySearching(itemsAndQty);

        //Clicked on Proceed to Checkout, land on the Checkout page, with the Shipping Accordion expanded.
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Clicked on the Checkout button to land on Checkout page with the Shipping Accordion displayed.");
        } else if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Clicked on the Checkout button to land on Checkout page with the Shipping Accordion displayed.");
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        } else if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        }
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetails.get("fName"), billDetails.get("lName"), billDetails.get("addressLine1"), billDetails.get("addressLine2"), billDetails.get("city"), billDetails.get("stateShortName"), billDetails.get("zip"), billDetails.get("cardNumber"), billDetails.get("securityCode"), billDetails.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(billingPageActions.getCouponDiscount() == 0.00, "verifying for the coupon discount not display after removing coupon at billing page");
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            AssertFailAndContinue(orderConfirmationPageActions.getCouponDiscount() == 0.00, "verifying for the coupon discount display after removing coupon at review page");
            getAndVerifyOrderNumber("regNavigateToBillingPage");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {CHECKOUT, REGRESSION, PLCC, FDMS, USONLY})
    public void placeNormalStdCheckout_PLCC(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the " + user + " user places the normal checkout ship to home order using PLCC in US Store");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> plccBillUS = excelReaderDT2.getExcelData("BillingDetails", "PlaceCard");
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        addToBagBySearching(itemsAndQty);
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button from shopping bag page as Register user");
        }
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page as Guest user");
        }
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.enterPLCCDetails(reviewPageActions, plccBillUS.get("cardNumber")), "Entered the PLCC Payment details and the same as shipping Address details and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");

    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {CHECKOUT, REGRESSION, FDMS, REGISTEREDONLY, PROD_REGRESSION})
    public void addDefaultBillingAndValidate(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify if the Registered user, having a default Billing Address (Visa) is able to navigate to billing page and validate the same");
        Map<String, String> shipDetailUS = null;
        Map<String, String> visaBillUS = null;
        String differentCountry = "";
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> sm = null;
        Map<String, String> editBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
        if (store.equalsIgnoreCase("US")) {
            differentCountry = "CA";
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
            editBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
            if (env.equalsIgnoreCase("prod")) {
                visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
                editBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD2");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            differentCountry = "US";
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "ground");
            visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
            editBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
            if (env.equalsIgnoreCase("prod")) {
                visaBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
                editBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");
            }
        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default payment and billing address
        addDefaultPaymentMethodAndBillingAddress(visaBillUS.get("fName"), visaBillUS.get("lName"), visaBillUS.get("addressLine1"), visaBillUS.get("city"), visaBillUS.get("stateShortName"),
                visaBillUS.get("zip"), visaBillUS.get("phNum"), visaBillUS.get("cardType"), visaBillUS.get("cardNumber"), visaBillUS.get("expMonthValueUnit"), visaBillUS.get("SuccessMessageContent"));

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button as a register user and check user navigate to shipping page");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.validateGiftcard(), "Verify the GC section in billing page");
        AssertFailAndContinue(billingPageActions.validateBillingWithDefaultAdd(), "Validate the billing page when user added default address in my account page");
        AssertFailAndContinue(billingPageActions.clickCCDropdown(), "Click on credit card dropdown in billing page");
        AssertFailAndContinue(billingPageActions.clickAddNewCC(), "Click on add a new credit card link and check user displayed with the new credit card details");
        AssertFailAndContinue(billingPageActions.enterPaymentAddressWithDiffCountry(reviewPageActions, editBillUS.get("fName"),
                editBillUS.get("lName"), editBillUS.get("addressLine1"), editBillUS.get("addressLine2"), editBillUS.get("city"), editBillUS.get("stateShortName"), editBillUS.get("zip"), editBillUS.get("cardNumber"),
                editBillUS.get("securityCode"), editBillUS.get("expMonthValueUnit"), differentCountry), "Add new Payment details and the Billing Address and clicked on the Next Review button.");
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            getAndVerifyOrderNumber("addDefaultBillingAndValidate");
            myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
            myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
            myAccountPageActions.verifyDefaultCreditCard();
        }

    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {CHECKOUT, REGRESSION, FDMS, GUESTONLY, PROD_REGRESSION})
    public void validateCreditCardSectionAsGuest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if the Guest user, validate the credit card details which is present in the billing page");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> sm = null;
        Map<String, String> shipDetailUS = null;
        Map<String, String> creditCardDetails = null;
        if (store.equalsIgnoreCase("US")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
            creditCardDetails = excelReaderDT2.getExcelData("PaymentDetails", "billingDetails");
            if (env.equalsIgnoreCase("prod")) {
                creditCardDetails = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "ground");
            creditCardDetails = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
            if (env.equalsIgnoreCase("prod")) {
                creditCardDetails = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickCheckoutAsGuest(loginPageActions), "Click on checkout button as a register user and check user navigate to shipping page");
        AssertFailAndContinue(loginPageActions.clickContinueAsGuestButton(shippingPageActions), "Click on continue as guest button in login overlay and check user navigate to shipping page");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        AssertFailAndContinue(billingPageActions.validateCardSection(), "Validate the credit card section fields present in billing page");
        billingPageActions.enterCardDetails(creditCardDetails.get("cardNumber"), creditCardDetails.get("securityCode"), creditCardDetails.get("expMonthValueUnit"));
        billingPageActions.validateMonAndYear();
    }

    @Test(dataProvider = dataProviderName, priority = 6, groups = {CHECKOUT, REGRESSION, FDMS, REGISTEREDONLY, PRODUCTION, SMOKE})
    public void notSavingShippingOrBillingAdd(@Optional("US") String store, @Optional("registered") String user) throws Exception {

        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify the Registered user having products in the bag, is redirected to the Billing page without checking the Save checkbox related validation");
        Map<String, String> sm = null;
        Map<String, String> shipDetailUS = null;
        Map<String, String> billDetailUS = null;

        if (store.equalsIgnoreCase("US")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "ground");
            billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }


        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        addToBagBySearching(itemsAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Clicked on the Checkout button to land on Checkout page with the Shipping Accordion displayed.");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsWithoutCHeckbox_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");

        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetailUS.get("fName"), billDetailUS.get("lName"), billDetailUS.get("addressLine1"), billDetailUS.get("addressLine2"), billDetailUS.get("city"), billDetailUS.get("stateShortName"), billDetailUS.get("zip"), billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        reviewPageActions.clickEditBillingLink(billingPageActions);
        String cardno = billDetailUS.get("cardNumber").substring(12, 16);
        AssertFailAndContinue(billingPageActions.verifyTheCheckbox(), "Check whether the set as Default Payment checkbox is unchecked."); // Checked and modified after checking with Functional team
        AssertFailAndContinue(billingPageActions.getAttributeValue(billingPageActions.cardNumber, "value").contains(cardno), "Creditcard is prepopulated");
        billingPageActions.select(billingPageActions.sameAsShippingChkBox, billingPageActions.sameAsShippingChkBoxinput);
        AssertFailAndContinue(billingPageActions.verifyAddressPrepopulated(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip")), "Billing address is prepopulated");
        billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, billDetailUS.get("securityCode"));
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            getAndVerifyOrderNumber("notSavingShippingOrBillingAdd");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {CHECKOUT, USONLY, REGISTEREDONLY, PROD_REGRESSION})
    public void defaultCABillingInUSValidation(@Optional("US") String store, @Optional("registered") String user) throws Exception {

        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify the billing Address section after saving the CA Default billing address in US Store");

        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> sm = null;
        Map<String, String> shipDetailUS = null;
        Map<String, String> mcBillDetails = null;
        if (store.equalsIgnoreCase("US")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "standard");
            mcBillDetails = excelReaderDT2.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                mcBillDetails = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "ground");
            mcBillDetails = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                mcBillDetails = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");

            }
        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        //Adding default Credit Card
        myAccountPageActions.addDefaultPaymentWithDifferentCountry(mcBillDetails.get("fName"), mcBillDetails.get("lName"), mcBillDetails.get("country"), mcBillDetails.get("addressLine1"), mcBillDetails.get("city"), mcBillDetails.get("stateShortName"),
                mcBillDetails.get("zip"), mcBillDetails.get("cardType"), mcBillDetails.get("cardNumber"), mcBillDetails.get("expMonthValueUnit"), mcBillDetails.get("SuccessMessageContent"));

        addToBagBySearching(itemsAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Clicked on the Checkout button to land on Checkout page with the Shipping Accordion displayed.");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
    }

    @Test(dataProvider = dataProviderName, priority = 5, groups = {CHECKOUT, REGRESSION, FDMS, REGISTEREDONLY})
    public void validateBillingAddressInMyAccReg(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify the address saved in My Account and Billing page");

        Map<String, String> shipDetailUS = null;
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "superSaver");
        String keyWord = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> itemsAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(keyWord, qty);
        Map<String, String> editBillUS = null;
        Map<String, String> masterBillUS = null;
        String country = null;
        if (store.equalsIgnoreCase("US")) {
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
            masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
            editBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
            country = "CA";
            if (env.equalsIgnoreCase("prod")) {
                masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD");
                editBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD2");

            }
        }
        if (store.equalsIgnoreCase("CA")) {
            editBillUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsUS");
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "MasterCardCA");
            country = "US";
            if (env.equalsIgnoreCase("prod")) {
                editBillUS = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");
                masterBillUS = excelReaderDT2.getExcelData("BillingDetails", "VisaCA_PROD");

            }
        }
        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
        myAccountPageActions.staticWait(3000);
        myAccountPageActions.click(myAccountPageActions.btn_AddNewCard);

        //Adding new FDMS Payment method
        addDefaultPaymentMethodCC(masterBillUS.get("fName"), masterBillUS.get("lName"), masterBillUS.get("addressLine1"), masterBillUS.get("city"), masterBillUS.get("stateShortName"),
                masterBillUS.get("zip"), masterBillUS.get("phNum"), masterBillUS.get("cardType"), masterBillUS.get("cardNumber"), masterBillUS.get("expMonthValueUnit"), masterBillUS.get("SuccessMessageContent"));

        addToBagBySearching(itemsAndQty);
        AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Clicked on the Checkout button to land on Checkout page with the Shipping Accordion displayed.");
        AssertFailAndContinue(shippingPageActions.enterShippingDetailsWithoutCHeckbox_Reg(billingPageActions, shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), sm.get("shipValue")), "Entered the Shipping address and clicked on the Next Billing button.");
        billingPageActions.enterExpCvvAndClickNextReviewButton(reviewPageActions);
        reviewPageActions.clickEditBillingLink(billingPageActions);
        billingPageActions.click(billingPageActions.editLinkOnCard);
        billingPageActions.unSelect(billingPageActions.sameAsShippingChkBox, billingPageActions.sameAsShippingChkBoxinput);
        AssertFailAndContinue(billingPageActions.selectAddressFromDropDown(), "Click the address from dropdown");

        billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, "123");
        reviewPageActions.clickEditBillingLink(billingPageActions);
        billingPageActions.click(billingPageActions.editLinkOnCard);

        AssertFailAndContinue(billingPageActions.enterPaymentAddressWithDiffCountry(reviewPageActions, editBillUS.get("fName"),
                editBillUS.get("lName"), editBillUS.get("addressLine1"), editBillUS.get("addressLine2"), editBillUS.get("city"), editBillUS.get("stateShortName"), editBillUS.get("zip"), editBillUS.get("cardNumber"),
                editBillUS.get("securityCode"), editBillUS.get("expMonthValueUnit"), country), "Add new Payment details and the Billing Address and clicked on the Next Review button.");
        if (!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            getAndVerifyOrderNumber("validateBillingAddressInMyAccReg");
        }
    }

}