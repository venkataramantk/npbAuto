package tests.mobileDT.billing;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by RamanJha on 12/07/2018.
 */

//@Test(singleThreaded = true)
public class MobileBillingPageTest extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = UiBaseMobile.randomEmail();
    String password = "";
    ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);

        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
        email = UiBaseMobile.randomEmail();
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT, PROD_REGRESSION})
    public void redirectionToShipping(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify on billing page that if user clicks on shipping from progress tracker, he gets redirected to shipping Page, "
                + "And the shipping details are prepoulated. ");
        Map<String, String> payment = null;
        Map<String, String> usShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");

        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        if (store.equalsIgnoreCase("US")) {
            payment = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                payment = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            payment = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                payment = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }
        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
        }else{
            addToBagBySearching(searchKeywordAndQty);
        }
        //DT-37922 //DT-37920
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.CouponImg), "verify coupon image is displayed in coupon list");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.CouponName), "verify coupon name is displayed in coupon list");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.CouponExpiration), "verify coupon expiration date is displayed in coupon list");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.CouponDetailsLinkOnCouponList), "verify details link is displayed in coupon list");
        //DT-38044 
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.applyButtonFrmCouponList), "verify Apply Button is displayed along with coupon list");

        mshoppingBagPageActions.clickOnApplyBtnFromCouponList();
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.appliedCouponContainer), "Verify applied coupon section is displayed on Shopping Bag Page");
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");

        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.appliedCouponContainer), "Verify applied coupon section is displayed on shipping Page");

        if (store.equalsIgnoreCase("US"))
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingAdd.get("fName"), usShippingAdd.get("lName"), usShippingAdd.get("addressLine1"), usShippingAdd.get("addressLine2"), usShippingAdd.get("city"), usShippingAdd.get("stateShortName"), usShippingAdd.get("zip"), usShippingAdd.get("phNum"), usShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        if (store.equalsIgnoreCase("CA"))
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.appliedCouponContainer), "Verify applied coupon section is displayed on billing Page");
        //DT-38807 
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(payment.get("cardNumber"), payment.get("securityCode"), payment.get("expMonthValueUnit"));
        AssertFailAndContinue(mbillingPageActions.clickOnShipping(mshippingPageActions), "Verify user is redirected to Shipping Page");

        if (store.equalsIgnoreCase("US"))
            AssertFailAndContinue(mshippingPageActions.verifyAddressPrepop(usShippingAdd.get("fName"), usShippingAdd.get("lName"), usShippingAdd.get("addressLine1"), usShippingAdd.get("city"), usShippingAdd.get("stateShortName"), usShippingAdd.get("zip")), "Shipping address is prepopulated");
        if (store.equalsIgnoreCase("CA"))
            AssertFailAndContinue(mshippingPageActions.verifyAddressPrepop(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip")), "Shipping address is prepopulated");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, PROD_REGRESSION})
    public void verifyCreditCardLabel(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify label associated with credit card is shown on billing page");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");
        Map<String, String> PaymentDetails = null;

        if (env.equals("prod")) {
            if (store.equalsIgnoreCase("US"))
                PaymentDetails = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            if (store.equalsIgnoreCase("CA"))
                PaymentDetails = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
        } else {
            if (store.equalsIgnoreCase("US"))
                PaymentDetails = dt2ExcelReader.getExcelData("BillingDetails", "validBillingDetailsUS");
            if (store.equalsIgnoreCase("CA"))
                PaymentDetails = dt2ExcelReader.getExcelData("BillingDetails", "validBillingDetailsCA");
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
        }else{
            addToBagBySearching(searchKeywordAndQty);
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        //DT-38985
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }

        // mshippingPageActions.clickContinueOnAddressVerificationModal();
        mbillingPageActions.enterCardDetails(PaymentDetails.get("cardNumber"), PaymentDetails.get("securityCode"), PaymentDetails.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();

        mreviewPageActions.clickReturnToBillingLink(mbillingPageActions);
        //DT-36673
        AssertFailAndContinue(mbillingPageActions.getText(mbillingPageActions.currentActiveAccordion).equals("Billing"), "Verify Billing accordion is currently active.");
        mbillingPageActions.isCardImgDisplayed();
        String cardType = mbillingPageActions.getCardImgAltText();
        AssertFailAndContinue(cardType.equals(PaymentDetails.get("cardType")), "Verify label associated with credit card is shown");

        //DT-36470
        mbillingPageActions.enterCardDetails(PaymentDetails.get("cardNumber"), PaymentDetails.get("securityCode"), PaymentDetails.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        AssertFailAndContinue(mreviewPageActions.clickEditBillingLink(mbillingPageActions), "Verify redirection to billing page on clicking on Edit button on order review page");
        mbillingPageActions.isCardImgDisplayed();
        cardType = mbillingPageActions.getCardImgAltText();
        AssertFailAndContinue(cardType.equals(PaymentDetails.get("cardType")), "Verify label associated with credit card on clicking on Edit billing link displayed on Order Review Page");

    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {CHECKOUT, GIFTCARD, RECAPTCHA, PROD_REGRESSION})
    public void verifyNewGiftCardAdd(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String, String> gc = null;
        Map<String, String> mailingDetails = null;
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify the fields related to gift card when user clicks on Add New Gift Card");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        Map<String, String> associateDetails = dt2ExcelReader.getExcelData("MyAccount", "ValidAssociateID");

        if(store.equalsIgnoreCase("US")){
            mailingDetails = dt2ExcelReader.getExcelData("MyAccount", "USAddress");
        } if(store.equalsIgnoreCase("CA")){
            mailingDetails = dt2ExcelReader.getExcelData("MyAccount", "CAAddress");
        }

        makeAssociateUser(associateDetails.get("FirstName"), associateDetails.get("LastName"), associateDetails.get("Associate_ID"),mailingDetails.get("AddressLine1"),mailingDetails.get("City"),mailingDetails.get("stateShortName"));

        //makeAssociateUser(associateDetails.get("FirstName"), associateDetails.get("LastName"), associateDetails.get("Associate_ID"));

        Map<String, String> usShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");
        //Added by Richa Priya
        if (store.equalsIgnoreCase("US")) {
            gc = dt2ExcelReader.getExcelData("GiftCard", "usgc2");
            if (env.equalsIgnoreCase("prod")) {
                gc = dt2ExcelReader.getExcelData("GiftCard", "usgc1_prod");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            gc = dt2ExcelReader.getExcelData("GiftCard", "cagc2");
            if (env.equalsIgnoreCase("prod")) {
                gc = dt2ExcelReader.getExcelData("GiftCard", "cagc1_prod");
            }
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");

        if (store.equalsIgnoreCase("US"))
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingAdd.get("fName"), usShippingAdd.get("lName"), usShippingAdd.get("addressLine1"), usShippingAdd.get("addressLine2"), usShippingAdd.get("city"), usShippingAdd.get("stateShortName"), usShippingAdd.get("zip"), usShippingAdd.get("phNum"), usShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        if (store.equalsIgnoreCase("CA"))
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");

        //DT-39062 //DT-39064
        AssertFailAndContinue(mbillingPageActions.verifyNewGiftCardAddFields(), "verify that on clicking New Gift Card button, card number textbox, Pin text box, apply button is displayed");
        //AssertFailAndContinue(mbillingPageActions.verifyreCaptchaChkbox(),"Verify recaptcha checkbox is unchecked by default and contains \"I'm not a robot\" text");
        //DT-39023
        AssertFailAndContinue(mbillingPageActions.isSelected(mbillingPageActions.creditCardRadioBox), "Verify the credit card payment option is selected by default");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.billingAddressHeading), "Verify billing address is displayed in payment method");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.cardNumField), "Verify credit card field is displayed in payment method");
        //DT-37913
        AddInfoStep("Entering Gift Card details on Billing page");
        if (store.equalsIgnoreCase("US")) {
            mbillingPageActions.enterGiftCardDetails(gc.get("Card"), gc.get("Pin"));
        }
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.balanceLineItem), "Verify Balance line item is displayed on billing page");
        AssertFailAndContinue(mbillingPageActions.verifyGCBalance_Calculation(), "Verify Balance is displayed the difference between Estimated Total and Gift Card applied on billing page");

        //DT-35906
        AssertFailAndContinue(mbillingPageActions.clickOnShipping(mshippingPageActions), "Verify user is redirected to Shipping Page");
        AssertFailAndContinue(mshippingPageActions.returnToBagPage(mshoppingBagPageActions), "verify return to shopping bag");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mbillingPageActions.balanceLineItem), "Verify Balance line item is displayed on billing page");
        AssertFailAndContinue(mshoppingBagPageActions.isTotalBalanceDisplayAfterApplyingGC(), "Verify Balance is displayed the difference between Estimated Total and Gift Card applied on billing page");
        //DT-37912
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.GiftCardLineitemPrice), "verify Gift card amount is displayed as line item on shopping Bag");
        //DT-37901
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.itemPriceTtlLineItem), "Verify Item(X) Price line item is present in order ledger");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.promotionsLineItem), "Verify Promotions line item is present in order ledger");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.shipTtlLineItem), "Verify Shipping total line item is present in order ledger");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.taxTtlLineItem), "Verify Tax total line item is present in order ledger");

        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.estmdTtlLineItem), "Verify Estimated Total line item is present in order ledger");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.GiftCardLineitem), "Verify Gift Card line item is present in order ledger");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.estimatedTtlLineItem), "Verify Balance line item is present in order ledger");

    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, BOPIS})
    public void verifyEditPaymentMethod_BopisOnly(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        String validUPCNumber = null;
        Map<String, String> billingAdd = null;
        String validZip = null;
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify user is able to change payment method on billing page and also payment method details and billing address details should be pre-populated when bopis product is present in bag");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if(store.equalsIgnoreCase("US")){
            billingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");

        }
        if(store.equalsIgnoreCase("CA")){
            billingAdd    = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");

        }
        Map<String, String> paymentMethod1 = dt2ExcelReader.getExcelData("PaymentDetails", "MasterCard");
        Map<String, String> paymentMethod2 = dt2ExcelReader.getExcelData("PaymentDetails", "Visa");
        if (env.equals("prod")) {
            paymentMethod1 = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD_2");
            paymentMethod2 = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(), "Delete all existing address details from my account");
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(), "Delete all existing card details from my account");

        AddInfoStep("Add master card in my account");
        mmyAccountPageActions.addACreditCard(paymentMethod1.get("cardNumber"),
                paymentMethod1.get("expMonthValueUnit"),
                paymentMethod1.get("expYear"), true, billingAdd.get("fName"),
                billingAdd.get("lName"), billingAdd.get("addressLine1"), billingAdd.get("city"), billingAdd.get("stateShortName"), billingAdd.get("zip"),panCakePageActions);
        //Now after adding one, one billing address will already appear
        AddInfoStep("Add Visa Card in my account");
        mmyAccountPageActions.addACreditCard(paymentMethod2.get("cardNumber"),
                paymentMethod2.get("expMonthValueUnit"),
                paymentMethod2.get("expYear"));

        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        }
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
        }

        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        mheaderMenuActions.clickShoppingBagIcon();

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions);
        }
        //DT-35400
        AssertFailAndContinue(mreviewPageActions.verifyExpressCheckoutTitle(), "Verify Title is EXPRESS CHECK OUT");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.submitOrderBtn), "Verify user is on express checkout page");

        mreviewPageActions.clickReturnToBillingLink(mbillingPageActions);
        AddInfoStep("Click on edit link on payment method on billing page and then click on edit link for first payment method.");
        mbillingPageActions.clickEditLinkOnBillingPage();
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.selectCardOverlayTitle), "Verify that select card page is shown");

        //check if all cards are present.
        ArrayList<String> cardNumbers = new ArrayList<String>();
        cardNumbers.add(paymentMethod1.get("cardNumber"));
        cardNumbers.add(paymentMethod2.get("cardNumber"));

        AssertFailAndContinue(mbillingPageActions.verifyExpectedCardsOnSelectCardPage(cardNumbers), "Verify all cards are present on select card page");
        //DT-38984
        mbillingPageActions.clickEditLinkOnSelectCardPage(0);
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.editPaymentMethodContainer), "Verify Edit payment method overlay is displayed.");
        AssertFailAndContinue(mbillingPageActions.verifyPaymentMethodPrepopOnForm(paymentMethod2.get("cardNumber"), paymentMethod2.get("expMonthValueUnit")), "Verify payment method information is prepopulated when clicking on edit link");
        AssertFailAndContinue(!mbillingPageActions.isDisplayed(mbillingPageActions.sameAsShippingChkBox), "Verify same as shipping checkbox is not present.");
    }


    @Test(priority = 4, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, PLCC,USONLY})
    public void verifyFieldsOnSelectingCCPLCC(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify when user selects CC/PLCC card on billing page, then payment method, billing address section and edit link should be displayed." +
                "\n Verify that user is able to click on edit on billing payment method section and is able to add new payment method from overlay");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(), "Delete all existing address details from my account");
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(), "Delete all existing card details from my account");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.addNewShippingAddress(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("city"),
                    usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("country"), usBillingAdd.get("phNum"), panCakePageActions);
        }
        if (store.equalsIgnoreCase("CA")) {
            mmyAccountPageActions.addNewShippingAddress(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("city"),
                    caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("country"), caShippingAdd.get("phNum"), panCakePageActions);
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        String expYear = mheaderMenuActions.getFutureYearWithCurrentDate("YYYY", 7);
        mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"), expYear);

        String plccCardDetail = getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "PlaceCard", "cardNumber");

        if (store.equalsIgnoreCase("US"))
            mmyAccountPageActions.addAPLCCCard(plccCardDetail);

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        addToBagBySearching(searchKeywordAndQty);

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }

        mreviewPageActions.clickOnBillingAccordion(mbillingPageActions);
        //DT-38997
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.paymentMethodContainer), "Verify Payment method is present.");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.billingAddressContainer), "Verify billing address is present.");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.editLinkOnCard), "Verify Edit link on card is present.");

        if (store.equals("US")) {
            AddInfoStep("Now Select PLCC card and check if payment method, billing address section and edit link is present");
            mbillingPageActions.clickEditLinkOnBillingPage();
            mbillingPageActions.clickSelectThisOnEditModal(1);
            AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.paymentMethodContainer), "Verify Payment method is present.");
            AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.billingAddressContainer), "Verify billing address is present.");
            AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.editLinkOnCard), "Verify Edit link on card is present.");
        }

        //DT-39027 //DT-38999
        AddInfoStep("Start verifying if user is able to add new payment method by clicking on edit link and then add new credit card button on overlay.");
        mbillingPageActions.clickEditLinkOnBillingPage();
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.selectCardOverlayTitle), "Verify that select card page is shown");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.addNewCCFromOverlay), "Verify add a new credit card button is shown");
        mbillingPageActions.click(mbillingPageActions.addNewCCFromOverlay);

        //DT-39003
        int cardNum_index = mbillingPageActions.getIndexByClass(mbillingPageActions.addNewCardList, "input-common input-cc");
        int expMonth_index = mbillingPageActions.getIndexByClass(mbillingPageActions.addNewCardList, "select-common select-exp-mm");
        int expYear_index = mbillingPageActions.getIndexByClass(mbillingPageActions.addNewCardList, "select-common select-exp-yy");

        AssertFailAndContinue(cardNum_index < expMonth_index, "verify expiry month is displayed below the card number");
        AssertFailAndContinue(cardNum_index < expYear_index, "verify expiry Year is displayed below the card number");

        //Enter new card details now.
        String cnumber = getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "cardNumber");
        String expMonth = getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "expMonthValue");

        //DT-39000
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.cardNumField), "Verify field to enter card number is present");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.expMonDropDown), "Verify user can select expiry month");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.expYrDopDown), "Verify user can select expiry year");
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.addNewBillingaddBtn), "Verify user can add new billing address");
        mbillingPageActions.click(mbillingPageActions.addNewBillingaddBtn);
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.addNewAddressContainer), "Verify add new address container is present.");
        mbillingPageActions.click(mbillingPageActions.backButton);

        //DT-39002 (edit part is covered in verifyEditPaymentMethod)
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.addPaymentMethodOverlay), "Verify add new card page is shown to the user");
        mbillingPageActions.enterCardDetailsFromOverlay(cnumber, expMonth);
        AssertFailAndContinue(cnumber.contains(mbillingPageActions.getCardSuffix()), "Verify that card suffix contains newly added card number on billing page");
    }

    @Test(priority = 5, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, PROD_REGRESSION})
    public void verifyEditPaymentMethod(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify user is able to change payment method on billing page and also payment method details and billing address details should be pre-populated.");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        Map<String, String> usShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");

        Map<String, String> billingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (store.equalsIgnoreCase("CA"))
            billingAdd = dt2ExcelReader.getExcelData("BillingDetails", "validBillingDetailsCA");
        Map<String, String> paymentMethod1 = dt2ExcelReader.getExcelData("PaymentDetails", "MasterCard");
        Map<String, String> paymentMethod2 = dt2ExcelReader.getExcelData("PaymentDetails", "Visa");

        if (env.equals("prod")) {
            paymentMethod1 = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD_2");
            paymentMethod2 = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(), "Delete all existing address details from my account");
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(), "Delete all existing card details from my account");

        AddInfoStep("Add master card in my account");
        mmyAccountPageActions.addACreditCard(paymentMethod1.get("cardNumber"),
                paymentMethod1.get("expMonthValueUnit"),
                paymentMethod1.get("expYear"), true, billingAdd.get("fName"),
                billingAdd.get("lName"), billingAdd.get("addressLine1"), billingAdd.get("city"), billingAdd.get("stateShortName"), billingAdd.get("zip"),panCakePageActions);
        //Now after adding one, one billing address will already appear
        AddInfoStep("Add Visa Card in my account");
        mmyAccountPageActions.addACreditCard(paymentMethod2.get("cardNumber"),
                paymentMethod2.get("expMonthValueUnit"),
                paymentMethod2.get("expYear"));

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(),"click shopping bag");
        }else{
            addToBagBySearching(searchKeywordAndQty);
        }

        if (user.equalsIgnoreCase("registered")) {
            mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        }
        //DT-35326
        AssertFailAndContinue(mshippingPageActions.isShippingPageDisplayed("Shipping Details"), "Verify user is redirected to shipping page");
        if (store.equalsIgnoreCase("US"))
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingAdd.get("fName"), usShippingAdd.get("lName"), usShippingAdd.get("addressLine1"), usShippingAdd.get("addressLine2"), usShippingAdd.get("city"), usShippingAdd.get("stateShortName"), usShippingAdd.get("zip"), usShippingAdd.get("phNum"), usShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        if (store.equalsIgnoreCase("CA"))
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");

        AddInfoStep("Click on edit link on payment method on billing page and then click on edit link for first payment method.");
        mbillingPageActions.clickEditLinkOnBillingPage();
        mbillingPageActions.clickEditLinkOnSelectCardPage(0);
        //DT-38983
        AssertFailAndContinue(mbillingPageActions.verifyPaymentMethodPrepopOnForm(paymentMethod2.get("cardNumber"), paymentMethod2.get("expMonthValueUnit")), "Verify payment method information is prepopulated when clicking on edit link");
        //DT-38982
        AssertFailAndContinue(!mbillingPageActions.isDefaultTextPresentInBillAdd(), "Verify that Default text is not present after first name last name in billing address.");
        AssertFailAndContinue(mbillingPageActions.verifyAddressPrepop(billingAdd.get("fName"), billingAdd.get("lName"), billingAdd.get("addressLine1"), billingAdd.get("city"), billingAdd.get("stateShortName"), billingAdd.get("zip"), billingAdd.get("phNum")), "Verify billing address is shown in edit payment method overlay");
        mbillingPageActions.clickSaveBtnOnOverlay();
        AssertFailAndContinue(paymentMethod2.get("cardNumber").contains(mbillingPageActions.getCardSuffix()), "Verify that card suffix contains newly added card number on billing page");

        //DT-39026
        //Now verify if user can edit the card details and billing address
        mbillingPageActions.clickEditLinkOnBillingPage();
        mbillingPageActions.clickEditLinkOnSelectCardPage(0);
        AddInfoStep("Edit and enter new payment method: amex card details");
        Map<String, String> paymentMethodAmex = dt2ExcelReader.getExcelData("PaymentDetails", "Amex");
        mbillingPageActions.enterCardDetailsFromOverlay(paymentMethodAmex.get("cardNumber"), paymentMethodAmex.get("expMonthValueUnit"));
        AssertFailAndContinue(paymentMethodAmex.get("cardNumber").contains(mbillingPageActions.getCardSuffix()), "Verify that card suffix contains the edited payment method on billing page");
        mbillingPageActions.clickEditLinkOnBillingPage();
        mbillingPageActions.clickEditLinkOnSelectCardPage(0);
        //Now edit billing address
        mbillingPageActions.click(mbillingPageActions.editBillingaddBtn);
        mbillingPageActions.clickEditLinkOnSelectCardPage(0); //edit address

        mbillingPageActions.UpdateFieldOnEditScreen("FNAME", getDT2TestingCellValueBySheetRowAndColumn("BillingDetails", "editedBillingAddressUS", "fName"));
        AssertFailAndContinue(mbillingPageActions.verifyAddressPrepop(getDT2TestingCellValueBySheetRowAndColumn("BillingDetails", "editedBillingAddressUS", "fName"), billingAdd.get("lName"), billingAdd.get("addressLine1"), billingAdd.get("city"), billingAdd.get("stateShortName"), billingAdd.get("zip"), billingAdd.get("phNum")), "Verify user is able to see edited billing address");
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