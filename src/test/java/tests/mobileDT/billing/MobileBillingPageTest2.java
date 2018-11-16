package tests.mobileDT.billing;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

/**
 * Created by Richa Priya
 */

public class MobileBillingPageTest2 extends MobileBaseTest {
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {CHECKOUT})
    public void verifyFieldsNotPrefilledOnCompletingCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify fields are prefilled on shipping when user completes checkout and again navigates to shipping page");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);


        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");
        Map<String, String> PaymentDetails = dt2ExcelReader.getExcelData("PaymentDetails", "billingDetails");
        if(env.equals("prod"))
            PaymentDetails = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");

        if(user.equalsIgnoreCase("registered"))
        {
            panCakePageActions.navigateToMenu("MYACCOUNT");
            mmyAccountPageActions.clickSection("ADDRESSBOOK");
            AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(),"Delete all existing address details from my account");
            panCakePageActions.navigateToMenu("MYACCOUNT");
            mmyAccountPageActions.clickSection("GIFTCARDS");
            AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(),"Delete all existing card details from my account");
        }

        mheaderMenuActions.clickShoppingBagIcon();

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }

        mbillingPageActions.enterCardDetails(PaymentDetails.get("cardNumber"), PaymentDetails.get("securityCode"), PaymentDetails.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        if(!env.equals("prod"))
        {
            mreviewPageActions.clickSubmOrderButton();
            //Now again do add to bag
            addToBagBySearching(searchKeywordAndQty);
            if(user.equalsIgnoreCase("guest"))
                AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
            else
                AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
            //DT-38994
            AddInfoStep("Navigate again to shipping page and check if the information is pre-populated.");
            if (store.equalsIgnoreCase("US")) {
                if(user.equals("guest"))
                    AssertFailAndContinue(!mshippingPageActions.verifyShippingAddressPrePopulatedInForm_Guest(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), email), "Verify shipping address is prepopulated.");
                else
                    AssertFailAndContinue(!mshippingPageActions.verifyShippingAddressPrePopulatedInForm(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum")), "Verify shipping address is prepopulated.");
            }
            if (store.equalsIgnoreCase("CA")) {
                if(user.equals("guest"))
                    AssertFailAndContinue(!mshippingPageActions.verifyShippingAddressPrePopulatedInForm_Guest(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), email), "Verify shipping address is prepopulated.");
                else
                    AssertFailAndContinue(!mshippingPageActions.verifyShippingAddressPrePopulatedInForm(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum")), "Verify shipping address is prepopulated.");
            }
        }
    }

    @Test(priority = 1, dataProvider =  dataProviderName, groups = {CHECKOUT})
    public void verifyBillingDetailFields(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String, String> shipDetails = null;
        Map<String, String> billingDetails_PlaceCard = null;
        Map<String, String> billingDetails_Visa = null;
        Map<String, String> billingDetails_Master = null;
        Map<String, String> billingDetails_Discover = null;
        Map<String, String> billingDetails_Amex = null;
        List<Map<String, String>> billingDetailsList = new ArrayList<>();

        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify fields are prefilled on shipping when" +
                " user completes checkout and again navigates to shipping page");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if(store.equalsIgnoreCase("US")){
            billingDetails_Visa =   dt2ExcelReader.getExcelData("BillingDetails", "Visa");
            billingDetails_Master =   dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            billingDetails_Amex =   dt2ExcelReader.getExcelData("BillingDetails", "Amex");
            billingDetails_Discover =   dt2ExcelReader.getExcelData("BillingDetails", "Discover");
            billingDetails_PlaceCard = dt2ExcelReader.getExcelData("BillingDetails", "PlaceCard");
            shipDetails = dt2ExcelReader.getExcelData("BillingDetails", "validBillingDetailsUS");
        }

        if(store.equalsIgnoreCase("CA")){
            billingDetails_Visa =   dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
            billingDetails_Master =   dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            billingDetails_Amex =   dt2ExcelReader.getExcelData("BillingDetails", "AmexCA");
            shipDetails = dt2ExcelReader.getExcelData("BillingDetails", "validBillingDetailsCA");
        }

        if(store.equalsIgnoreCase("US")){
            billingDetailsList.add(billingDetails_Visa);
            billingDetailsList.add(billingDetails_Master);
            billingDetailsList.add(billingDetails_Amex);
            billingDetailsList.add(billingDetails_Discover);
        }
        if(store.equalsIgnoreCase("CA")){
            billingDetailsList.add(billingDetails_Visa);
            billingDetailsList.add(billingDetails_Master);
            billingDetailsList.add(billingDetails_Amex);
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");

        } else if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");

        }
        AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(shipDetails.get("fName"), shipDetails.get("lName"), shipDetails.get("addressLine1"), shipDetails.get("addressLine2"), shipDetails.get("city"), shipDetails.get("stateShortName"), shipDetails.get("zip"), shipDetails.get("phNum"), shipDetails.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        //DT-38998
        if(user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mbillingPageActions.verifyBillingDetailsFields(), "Verify billing details fields are getting displayed on billing page");
        }

        //DT-39005
        if(store.equalsIgnoreCase("US")) {
            for (Map<String, String> BillingDetailsList : billingDetailsList) {
                AddInfoStep("Verifying payment details acceptance for ::" + BillingDetailsList.get("cardType"));

                mbillingPageActions.enterCardDetails(BillingDetailsList.get("cardNumber"), BillingDetailsList.get("securityCode"), BillingDetailsList.get("expMonthValueUnit"));
                AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(), "Verify" + BillingDetailsList.get("cardType") + " payment type is accepted successfully");
                mreviewPageActions.clickReturnToBillingLink(mbillingPageActions);
            }
        }

        if(store.equalsIgnoreCase("US")) {
            mbillingPageActions.enterPLCCDetails(billingDetails_PlaceCard.get("cardNumber"));
            AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(), "Verify" + billingDetails_PlaceCard.get("cardType") + " payment type is accepted successfully");
        }
        //DT-39006
        if(store.equalsIgnoreCase("CA")) {
            for (Map<String, String> BillingDetailsList : billingDetailsList) {
                AddInfoStep("Verifying payment details acceptance for ::" + BillingDetailsList.get("cardType"));
                mbillingPageActions.enterCardDetails(BillingDetailsList.get("cardNumber"), BillingDetailsList.get("securityCode"), BillingDetailsList.get("expMonthValueUnit"));
                AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(), "Verify" + BillingDetailsList.get("cardType") + " payment type is accepted successfully");
                mreviewPageActions.clickReturnToBillingLink(mbillingPageActions);
            }
        }
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {CHECKOUT,PROD_REGRESSION})
    public void verifyPrefilledFieldsOnExitingCheckout(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK/Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify fields are prefilled on shipping and billing page when user exits checkout and again navigates to shipping/billing page.");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchTerm1", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> PaymentDetails = dt2ExcelReader.getExcelData("PaymentDetails", "billingDetails");
        if(env.equals("prod"))
            PaymentDetails = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");

        if(user.equalsIgnoreCase("registered"))
        {
            panCakePageActions.navigateToMenu("MYACCOUNT");
            mmyAccountPageActions.clickSection("ADDRESSBOOK");
            AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(),"Delete all existing address details from my account");
            panCakePageActions.navigateToMenu("MYACCOUNT");
            mmyAccountPageActions.clickSection("GIFTCARDS");
            AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(),"Delete all existing card details from my account");
        }

        mheaderMenuActions.clickShoppingBagIcon();

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), usBillingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), caShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        }

        mbillingPageActions.enterCardDetails(PaymentDetails.get("cardNumber"), PaymentDetails.get("securityCode"), PaymentDetails.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        //DT-36674 Added by Richa Priya
        mreviewPageActions.clickBackButton();
        AssertFailAndContinue(mreviewPageActions.getReturnToBagMessage().equals("Are you sure you want to return to your Shopping Bag?"), "Verify overlay which appears on clicking back button has message: Are you sure you want to return to your Shopping Bag?");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.stayCheckoutBtn), "Verify Stay in Checkout button is displayed");
        AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.returnBagBtn), "Verify Return to Bag button is displayed");
        mreviewPageActions.clickReturnToBagBtn(mshoppingBagPageActions);

        if(user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        else
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        //DT-38995
        AddInfoStep("Navigate again to shipping and billing page and check if the information is pre-populated.");
        if (store.equalsIgnoreCase("US")) {
            if(user.equals("guest"))
                AssertFailAndContinue(mshippingPageActions.verifyShippingAddressPrePopulatedInForm_Guest(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"), email), "Verify shipping address is prepopulated.");
            else
                AssertFailAndContinue(mshippingPageActions.verifyShippingAddressPrePopulatedInForm(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("addressLine2"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum")), "Verify shipping address is prepopulated.");
        }
        if (store.equalsIgnoreCase("CA")) {
            if(user.equals("guest"))
                AssertFailAndContinue(mshippingPageActions.verifyShippingAddressPrePopulatedInForm_Guest(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"), email), "Verify shipping address is prepopulated.");
            else
                AssertFailAndContinue(mshippingPageActions.verifyShippingAddressPrePopulatedInForm(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("addressLine2"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum")), "Verify shipping address is prepopulated.");
        }
        mshippingPageActions.clickNextBillingButton();
        AssertFailAndContinue(mbillingPageActions.verifyPaymentMethodPrepopOnForm(PaymentDetails.get("Last4"), PaymentDetails.get("expMonthValueUnit")), "Verify payment method is prepopulated");
        AssertFailAndContinue(mbillingPageActions.verifyCVVEmpty(), "Verify Cvv field is empty");
    }
    @Test(priority = 3, dataProvider = dataProviderName, groups = {CHECKOUT, REGISTEREDONLY, BOPIS, USONLY,PROD_REGRESSION})
    public void verifyEditPaymentMethod_HybridBag(@Optional("US") String store, @Optional("registered") String user) throws Exception{
        setAuthorInfo("RichaK/Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify user is able to change payment method on billing page and also payment method details and billing address details should be pre-populated when bopis and ship to product is present in bag"
                + " verify accordian placement on Order Review Page ");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        Map<String, String> billingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> usShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> paymentMethod1 = dt2ExcelReader.getExcelData("PaymentDetails", "MasterCard");
        Map<String, String> paymentMethod2 = dt2ExcelReader.getExcelData("PaymentDetails", "Visa");
        if(env.equals("prod"))
        {
            paymentMethod1 = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD_2");
            paymentMethod2 = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(),"Delete all existing address details from my account");
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(),"Delete all existing card details from my account");

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

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (!(Integer.parseInt(mheaderMenuActions.getQty()) == 0)) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        addToBagBySearching(searchKeywordAndQty);

        String validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        mheaderMenuActions.clickShoppingBagIcon();

        if (user.equalsIgnoreCase("registered")) {
           AssertFailAndContinue( mshoppingBagPageActions.clickProceedToBOPISCheckout(),"Verify successful checkout with registered user");
        }

        AssertFailAndContinue(mpickUpPageActions.clickShippingBtn(mshippingPageActions),"Verify redirection to shipping page");
        AssertFailAndContinue(mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(usShippingAdd.get("fName"), usShippingAdd.get("lName"), usShippingAdd.get("addressLine1"), usShippingAdd.get("addressLine2"), usShippingAdd.get("city"), usShippingAdd.get("stateShortName"), usShippingAdd.get("zip"), usShippingAdd.get("phNum"), usShippingAdd.get("ShippingType"), email), "Verify after entering shipping detail user is redirected to Billing page successfully ");
        mbillingPageActions.enterCVVForExpressAndClickNextReviewBtn(paymentMethod1.get("securityCode"));
        mreviewPageActions.clickReturnToBillingLink(mbillingPageActions);
        AddInfoStep("Click on edit link on payment method on billing page and then click on edit link for first payment method.");
        mbillingPageActions.clickEditLinkOnBillingPage();
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.selectCardOverlayTitle), "Verify that select card page is shown");

        //check if all cards are present.
        ArrayList<String> cardNumbers = new ArrayList<String>();
        cardNumbers.add(paymentMethod1.get("cardNumber"));
        cardNumbers.add(paymentMethod2.get("cardNumber"));

        AssertFailAndContinue(mbillingPageActions.verifyExpectedCardsOnSelectCardPage(cardNumbers), "Verify all cards are present on select card page");
        //DT-38980, DT-38981
        mbillingPageActions.clickEditLinkOnSelectCardPage(0);
        AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.editPaymentMethodContainer), "Verify Edit payment method overlay is displayed.");
        AssertFailAndContinue(mbillingPageActions.verifyPaymentMethodPrepopOnForm(paymentMethod2.get("cardNumber"), paymentMethod2.get("expMonthValueUnit")), "Verify payment method information is prepopulated when clicking on edit link");
        AssertFailAndContinue(!mbillingPageActions.isDisplayed(mbillingPageActions.sameAsShippingChkBox),  "Verify same as shipping checkbox is not present.");
        AssertFailAndContinue(mbillingPageActions.verifyAddressPrepop(billingAdd.get("fName"), billingAdd.get("lName"), billingAdd.get("addressLine1"), billingAdd.get("city"), billingAdd.get("stateShortName"), billingAdd.get("zip"), billingAdd.get("phNum")), "Verify billing address is shown in edit payment method overlay");
        //DT-36634
        mbillingPageActions.clickNextReviewButton();
        int pickUp_index = mreviewPageActions.getIndexByText(mreviewPageActions.orderReviewContents,"Pickup");
        int shipping_index = mreviewPageActions.getIndexByText(mreviewPageActions.orderReviewContents,"Shipping");
        int billing_index = mreviewPageActions.getIndexByText(mreviewPageActions.orderReviewContents,"Billing");
        int myBag_index = mreviewPageActions.getIndexByText(mreviewPageActions.orderReviewContents,"My Bag (2)");
        int coupon_index = mreviewPageActions.getIndexByText(mreviewPageActions.orderReviewContents,"AVAILABLE REWARDS AND OFFERS");
        int enterCoupon_index = mreviewPageActions.getIndexByText(mreviewPageActions.orderReviewContents,"Enter Coupon Code");
        int orderSummary_index = mreviewPageActions.getIndexByText(mreviewPageActions.orderReviewContents,"Order Summary");
        AssertFailAndContinue(pickUp_index < shipping_index,"verify Shipping section is displayed below the pick-up section");
        AssertFailAndContinue(shipping_index < billing_index,"verify Billing section is displayed below the billing section");
        AssertFailAndContinue(billing_index < myBag_index,"verify myBag  is displayed below the shipping section");
        AssertFailAndContinue(coupon_index < orderSummary_index,"verify coupon is displayed above order summary");
        AssertFailAndContinue(enterCoupon_index < orderSummary_index,"verify enter coupon section is displayed above Order Ledger");
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
