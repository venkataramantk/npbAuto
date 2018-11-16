package tests.mobileDT.Checkout;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

public class TransactionalSMS extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = UiBaseMobile.randomEmail();
    String password;
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.deleteAllCookies();
        env = EnvironmentConfig.getEnvironmentProfile();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
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
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, PANTHER, ORDERSMS})
    public void transactionalSmsShippingPage_EcommOrder(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " validate transactional sms field in Shipping page in" + store + " store Verify" +
                "DT-43769");
        Map<String, String> mc = null;
        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (store.equalsIgnoreCase("US")) {
            mc = dt2MobileExcel.getExcelData("BillingDetails", "Visa");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2MobileExcel.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mc = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        mshippingPageActions.enterShippingDetailsAndShipType(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"),
                shipDetails.get("phNum"), "", email);

        AssertFailAndContinue(mshippingPageActions.verifyTransactionalSMSCheckBox(), "SMS checkbox should be displayed and not selected by default");
        AssertFailAndContinue(mshippingPageActions.selectTransactionalSMSCB(), "SMS area will be expanded by selecting the \"sign up for Transactional SMS\" checkbox on shipping page and Telephone number field and Legal verbiage should be displayed.");
        AssertFailAndContinue(mshippingPageActions.getSmsMobileNo().equalsIgnoreCase(shipDetails.get("phNum")), "Mobile number entered in shipping details should auto populate in order update phone number field and which is editable.");

        mshippingPageActions.enterSmsMobileNo("");
        mshippingPageActions.unselectTransactionalSMSCB();
        mshippingPageActions.selectTransactionalSMSCB();
        AssertFailAndContinue(mshippingPageActions.validateSmsErrorMessage("Please enter your phone number"), "validate Error message for Empty SMS field");
        mshippingPageActions.enterSmsMobileNo("#$%");
        AssertFailAndContinue(mshippingPageActions.validateSmsErrorMessage("Please enter a valid phone number"), "validate Error message for when Special Characters entered in SMS field");

        mshippingPageActions.enterSmsMobileNo("9846");
        AssertFailAndContinue(mshippingPageActions.validateSmsErrorMessage("Please enter a valid phone number"), "validate Error message for when Invalid mobile no entered in SMS field");
        mshippingPageActions.enterSmsMobileNo(shipDetails.get("phNum"));

        AssertFailAndContinue(mshippingPageActions.validateSMSPrivacyPolicyLink(), "Validate privacy policy link under SMS, clicking on privacy link navigates user to a new Window");
        mheaderMenuActions.switchToParent();
        mshippingPageActions.clickNextBillingButton();

        if (!env.equalsIgnoreCase("prod")) {
            mbillingPageActions.enterCardDetails(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"));

            mbillingPageActions.clickNextReviewButton();
            AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Verify User is able to place order successfully");
        }
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {CHECKOUT, PANTHER, ORDERSMS})
    public void transactionalSmsPickupPage_BopisOrder(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " validate transactional sms field in Shipping page in" + store + " store Verify" +
                "DT-43768");

        String validUPCNumber1 = null, validZip1 = null;
        Map<String, String> billingDetails_CC = null;
        Map<String, String> billingDetails = null;

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (store.equalsIgnoreCase("US")) {
            validUPCNumber1 = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip1 = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCode");

            billingDetails_CC = dt2MobileExcel.getExcelData("BillingDetails", "Visa");
            if (env.equalsIgnoreCase("prod")) {
                billingDetails_CC = dt2MobileExcel.getExcelData("BillingDetails", "VISA_PROD");
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber1 = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip1 = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCodeCA");

            billingDetails_CC = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA");
            if (env.equalsIgnoreCase("prod")) {
                billingDetails_CC = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber1, validZip1);
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        mheaderMenuActions.clickShoppingBagIcon();

        if (store.equalsIgnoreCase("US")) {
            billingDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        }
        if (store.equalsIgnoreCase("CA")) {
            billingDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
            mpickUpPageActions.enterpickUpDetails(billingDetails.get("fName"), billingDetails.get("lName"), email, billingDetails.get("phNum"));
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        AssertFailAndContinue(mpickUpPageActions.verifyTransactionalSMSCheckBox(), "SMS checkbox should be displayed and not selected by default");
        AssertFailAndContinue(mpickUpPageActions.selectTransactionalSMSCB(), "SMS area will be expanded by selecting the \"sign up for Transactional SMS\" checkbox on shipping page and Telephone number field and Legal verbiage should be displayed.");
        AssertFailAndContinue(mpickUpPageActions.getSmsMobileNo().equalsIgnoreCase(billingDetails.get("phNum")), "Mobile number entered in shipping details should auto populate in order update phone number field and which is editable.");

        mpickUpPageActions.enterSmsMobileNo("");
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mpickUpPageActions.validateSmsErrorMessage("Please enter your phone number"), "validate Error message for Empty SMS field");
        mpickUpPageActions.enterSmsMobileNo("#$%");
        AssertFailAndContinue(mpickUpPageActions.validateSmsErrorMessage("Please enter a valid phone number"), "validate Error message for when Special Characters entered in SMS field");

        mpickUpPageActions.enterSmsMobileNo("9846");
        AssertFailAndContinue(mpickUpPageActions.validateSmsErrorMessage("Please enter a valid phone number"), "validate Error message for when Invalid mobile no entered in SMS field");
        mpickUpPageActions.enterSmsMobileNo(billingDetails.get("phNum"));

        mcheckoutPickUpDetailsActions.clickNextBillingButton(mbillingPageActions);

        if (!env.equalsIgnoreCase("prod")) {
            mbillingPageActions.enterCardDetails(billingDetails_CC.get("cardNumber"), billingDetails_CC.get("securityCode"), billingDetails_CC.get("expMonthValueUnit"));

            mbillingPageActions.fillPaymentAddrDetailsWithoutSameAsShip(billingDetails.get("fName"), billingDetails.get("lName"), billingDetails.get("addressLine1"),
                    billingDetails.get("city"), billingDetails.get("stateShortName"), billingDetails.get("zip"));
            mbillingPageActions.clickNextReviewButton();
            AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Verify user is able to place order");
        }
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {CHECKOUT, PANTHER, ORDERSMS})
    public void transactionalSmsPickupPage_MixedOrder(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " validate transactional sms field in Shipping page in" + store + " store Verify" +
                "DT-43768");
        String validUPCNumber1 = null, validZip1 = null;
        Map<String, String> billingDetails_CC = null;
        Map<String, String> billingDetails = null;

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (store.equalsIgnoreCase("US")) {
            validUPCNumber1 = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip1 = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCode");

            billingDetails_CC = dt2MobileExcel.getExcelData("BillingDetails", "Visa");
            if (env.equalsIgnoreCase("prod")) {
                billingDetails_CC = dt2MobileExcel.getExcelData("BillingDetails", "VISA_PROD");
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber1 = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip1 = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validZipCodeCA");

            billingDetails_CC = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA");
            if (env.equalsIgnoreCase("prod")) {
                billingDetails_CC = dt2MobileExcel.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber1, validZip1);
        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);

        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
            mpickUpPageActions.enterpickUpDetails(billingDetails.get("fName"), billingDetails.get("lName"), email, billingDetails.get("phNum"));
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        AssertFailAndContinue(mpickUpPageActions.verifyTransactionalSMSCheckBox(), "SMS checkbox should be displayed and not selected by default");
        AssertFailAndContinue(mpickUpPageActions.selectTransactionalSMSCB(), "SMS area will be expanded by selecting the \"sign up for Transactional SMS\" checkbox on shipping page and Telephone number field and Legal verbiage should be displayed.");
        AssertFailAndContinue(mpickUpPageActions.getSmsMobileNo().equalsIgnoreCase(billingDetails.get("phNum")), "Mobile number entered in shipping details should auto populate in order update phone number field and which is editable.");

        mpickUpPageActions.enterSmsMobileNo("");
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mpickUpPageActions.validateSmsErrorMessage("Please enter your phone number"), "validate Error message for Empty SMS field");
        mpickUpPageActions.enterSmsMobileNo("#$%");
        AssertFailAndContinue(mpickUpPageActions.validateSmsErrorMessage("Please enter a valid phone number"), "validate Error message for when Special Characters entered in SMS field");

        mpickUpPageActions.enterSmsMobileNo("9846");
        AssertFailAndContinue(mpickUpPageActions.validateSmsErrorMessage("Please enter a valid phone number"), "validate Error message for when Invalid mobile no entered in SMS field");
        mpickUpPageActions.enterSmsMobileNo(billingDetails.get("phNum"));
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {CHECKOUT, PANTHER, ORDERSMS})
    public void marketingSMSOrderConfirmation(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " store. Verify" +
                "DT-43766");
        Map<String, String> shipDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        String invalidNo_msg = getmobileDT2CellValueBySheetRowAndColumn("OrderConfirmation", "invalidNo", "Value");
        String emptyNo_msg = getmobileDT2CellValueBySheetRowAndColumn("OrderConfirmation", "emptyNo", "Value");
        String validNo = getmobileDT2CellValueBySheetRowAndColumn("OrderConfirmation", "validNo", "Value");
        String success_msg = getmobileDT2CellValueBySheetRowAndColumn("OrderConfirmation", "SuccessMessage", "Value");
        String subscribedNo = getmobileDT2CellValueBySheetRowAndColumn("OrderConfirmation", "subscribedNo", "Value");
        String subscribedNo_msg = getmobileDT2CellValueBySheetRowAndColumn("OrderConfirmation", "alreadySubscribed", "Value");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        mshippingPageActions.enterShippingDetailsAndShipType(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"),
                shipDetails.get("phNum"), "", email);

        mshippingPageActions.clickNextBillingButton();
        if (!env.equalsIgnoreCase("prod")) {
            mbillingPageActions.enterCardDetails(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"));

            mbillingPageActions.clickNextReviewButton();
            AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Verify User is able to place order successfully");

            AssertFailAndContinue(mreceiptThankYouPageActions.validateSmsSignUpCB(), "Verify that \"Sign Up for text alerts and get $10 off!\" Checkbox is displayed on order confirmation page and un-check by default.");
            AssertFailAndContinue(mreceiptThankYouPageActions.checkSmsSignUpCB(), "Verify that SMS area is expanded and telephone number field, legal verbiage,  is displayed when user selects the checkbox");
            AssertFailAndContinue(mreceiptThankYouPageActions.getSmsPhone().equalsIgnoreCase(shipDetails.get("phNum")), "Verify that mobile number is prepopulated but editable on order confirmation page when user keyed in the phone number on Shipping page");
            mreceiptThankYouPageActions.updateSmsPhone("2452");
            mreceiptThankYouPageActions.clickSubmitBtn();
            AssertFailAndContinue(mreceiptThankYouPageActions.validateSmsErrorMessage(invalidNo_msg), "Validate Error Message for invalid mobile no");
            mreceiptThankYouPageActions.updateSmsPhone("!@#$%ˆˆ*()_+");
            AssertFailAndContinue(mreceiptThankYouPageActions.validateSmsErrorMessage(invalidNo_msg), "Validate Error Message for special character mobile no");
            mreceiptThankYouPageActions.updateSmsPhone("");
            mreceiptThankYouPageActions.clickSubmitBtn();
            AssertFailAndContinue(mreceiptThankYouPageActions.validateSmsErrorMessage(emptyNo_msg), "Validate Error Message for empty mobile no");
            mreceiptThankYouPageActions.updateSmsPhone(shipDetails.get("phNum"));
            mreceiptThankYouPageActions.clickSubmitBtn();
            AssertFailAndContinue(mreceiptThankYouPageActions.validatePageLevelError(invalidNo_msg), "Validate Error Message for when mobile no is not available");
            mheaderMenuActions.scrollToTop();
            AssertFailAndContinue(mreceiptThankYouPageActions.uncheckSmsSignUpCB(), "Un-select checkbox verify sms phone field is disappeared");
            mreceiptThankYouPageActions.checkSmsSignUpCB();
            mreceiptThankYouPageActions.updateSmsPhone(subscribedNo);
            mreceiptThankYouPageActions.clickSubmitBtn();
            AssertFailAndContinue(mreceiptThankYouPageActions.validatePageLevelError(subscribedNo_msg), "verify Error message when user entered Already Subscribed phone no");
            mreceiptThankYouPageActions.updateSmsPhone(validNo);
            mreceiptThankYouPageActions.clickSubmitBtn();
            AssertFailAndContinue(mreceiptThankYouPageActions.getSuccessMessage(success_msg), "verify Success message when user entered new phone no");
        }
    }
}
