package tests.mobileDT.myAccount.paymentsAndGiftCards;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

/**
 * Created by JKotha on 12/12/2017.
 */
//DT-747,DT-748, DT-749
//@Test(singleThreaded = true)
public class CreditCardPayments extends MobileBaseTest {
    String email = UiBaseMobile.randomEmail();
    String password = "";
    WebDriver mobileDriver;
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            createAccount(rowInExcelUS, email);
        }
        if (store.equalsIgnoreCase("CA")) {
            mheaderMenuActions.deleteAllCookies();
            mfooterActions.changeCountryByCountry("CANADA");
            createAccount(rowInExcelCA, email);
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

    @Test(priority = 0, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void creditCardUiTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a Registered user in " + store + " verify billing page UI" +
                "DT-44572");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        AssertFailAndContinue(mheaderMenuActions.validateHrefLangTag(), "Verify HREF Lang tag should contain www across all the pages");

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("MPRBANNER"), "Verify MPR espot is displayed in US store");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(!mmyAccountPageActions.isMyAccountFieldDisplayed("MPRBANNER"), "Verify MPR espot is not displayed in CA store");
        }

        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("GCBTN"), "Verify ADD A GIFT CARD button is displayed");
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("CCBTN"), "Verify ADD A CREDIT CARD button is displayed");

        AssertFailAndContinue(mmyAccountPageActions.clickAddACreditCard(), "Click Add a credit card button and verify credit card page displayed");
        AssertFailAndContinue(mmyAccountPageActions.validateAddACreditCardPagUI(), "Verify all the fields in Add a credit card page");

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mmyAccountPageActions.getStateTitle().equalsIgnoreCase("State"), "Verify State label for US store");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mmyAccountPageActions.getStateTitle().equalsIgnoreCase("Province"), "Verify Province label for CA");
        }

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mmyAccountPageActions.getZipTitle().equals("Zip Code"), "Verify Zip Code Ghost text for US");
        }

        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mmyAccountPageActions.getZipTitle().equals("Postal Code"), "Verify Zip Code Ghost text for CA");
        }

        AssertFailAndContinue(mmyAccountPageActions.clickCancelBtn(), "Verify Cancel button is navigating back to Add cards page");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void ccInlineErrors(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Error Message validations for add a credit card page");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");

        mmyAccountPageActions.clickAddACreditCard();
        mmyAccountPageActions.clickAddCardButton();

        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("CCNO").contains("Please enter a valid credit card number"), "Verify Card Number field error message is displayed");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("EXPDATE").contains("Please enter a valid expiration date"), "Verify Exp. Month drop-down error message is displayed");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGFN").contains("Please enter a first name"), "Verify First Name field error message is displayed");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGLN").contains("Please enter a last name"), "Verify Last Name field error message is displayed");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGADD1").contains("Please enter a valid street address"), "Verify Address Line 1 error message field is displayed");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGCITY").contains("Please enter a valid city"), "Verify City field error message is displayed");
        if (store.equalsIgnoreCase("US"))
            AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGSTATE").contains("Please select a state"), "Verify State drop-down error message is displayed");

        if (store.equalsIgnoreCase("CA"))
            AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGSTATE").contains("Please select a province"), "Verify State drop-down error message is displayed");

        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGZIP").contains("Please enter your zip code"), "Verify Zip Code field error message is displayed");

        mmyAccountPageActions.fillMyAccountField("BILLINGFN", "567");
        mmyAccountPageActions.fillMyAccountField("BILLINGLN", "567");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGFN").contains("First name field should not contain any special characters"), "Verify First Name field error message for invalid data is displayed");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("BILLINGLN").contains("Last name field should not contain any special characters"), "Verify Last Name field error message for invalid data is displayed");

    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void addCCWithBilling(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered user in " + store + " Verify" +
                "DT-43803, DT-43803, DT-44901, DT-44899");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");

        Map<String, String> carddetails = dt2MobileExcel.getExcelData("PaymentDetails", "MasterCard");
        Map<String, String> billDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");

        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.addACreditCard(carddetails.get("cardNumber"), carddetails.get("expMonthValue"), carddetails.get("expYear"), true, billDetails.get("fName"),
                    billDetails.get("lName"), billDetails.get("addressLine1"), billDetails.get("city"), billDetails.get("stateShortName"), billDetails.get("zip"), panCakePageActions);
        }

        if (store.equalsIgnoreCase("CA")) {
            billDetails = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mmyAccountPageActions.addACreditCard(carddetails.get("cardNumber"), carddetails.get("expMonthValue"), carddetails.get("expYear"), true, billDetails.get("fName"),
                    billDetails.get("lName"), billDetails.get("addressLine1"), billDetails.get("city"), billDetails.get("stateShortName"), billDetails.get("zip"), panCakePageActions);
        }

        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("DEFAULTBILLING"), "Verify Default billing method is displayed");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        AssertFailAndContinue(mmyAccountPageActions.getShippingAddressCount() == 1, "Verify address added in billing page is displayed in shipping");
        AssertFailAndContinue(mmyAccountPageActions.isDefautShippingAddressAvailable(), "Verify address displayed is default shipping method");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        mmyAccountPageActions.clickEditButton();

        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("SHIPPINGADD"), "Profile Information section should be displayed with the saved shipping address if atleaset 1 shipping address is available");
    }

    //@Test(priority = 2, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void addDifferentCreditCards(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setRequirementCoverage("Validate Credit cards fields inline error message");
        Map<String, String> billingAdd = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsUS");
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.click(mmyAccountPageActions.giftcards);
        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expYear"), true, billingAdd.get("fName"),
                    billingAdd.get("lName"), billingAdd.get("addressLine1"), billingAdd.get("city"), billingAdd.get("state"), billingAdd.get("zip"), panCakePageActions);
        }
        if (store.equalsIgnoreCase("CA")) {
            billingAdd = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expYear"), true, billingAdd.get("fName"),
                    billingAdd.get("lName"), billingAdd.get("addressLine1"), "Montreal", billingAdd.get("state"), billingAdd.get("zip"), panCakePageActions);

            AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.imgMC), "Verify Master card is able to add");

            mmyAccountPageActions.click(mmyAccountPageActions.addACreditCardBtn);
            mmyAccountPageActions.clearAndFillText(mmyAccountPageActions.addPayment_CardNumber, getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "PlaceCard", "cardNumber"));
            mmyAccountPageActions.click(mmyAccountPageActions.btnAddCard);
            AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.imgPLCC), "Verify Place Card is able to add");
            mmyAccountPageActions.staticWait(3000);

            //Add amex
            mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Amex", "cardNumber"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Amex", "expMonthValue"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Amex", "expYear"), false, "", "", "", "", "", "", panCakePageActions);

            AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.imgAMEX), "Verify Amex card is able to add is able to add");

            //Add master
            mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Discover", "cardNumber"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Discover", "expMonthValue"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Discover", "expYear"), false, "", "", "", "", "", "", panCakePageActions);
            AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.imgDiscover), "Verify Success Message after adding Discover Credit card");
        }
        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Amex", "cardNumber"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Amex", "expMonthValue"),
                    getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Amex", "expYear"), true, billingAdd.get("fName"),
                    billingAdd.get("lName"), billingAdd.get("addressLine1"), billingAdd.get("city"), billingAdd.get("stateShortName"), billingAdd.get("zip"), panCakePageActions);
            AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.imgAMEX), "Verify Success Message after adding amex Credit card");
        }
        //Add master
        mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard2Bin", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard2Bin", "expMonthValue"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard2Bin", "expYear"), false, "", "", "", "", "", "", panCakePageActions);

        mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "billingDetails", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "billingDetails", "expMonthValue"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "billingDetails", "expYear"), false, "", "", "", "", "", "", panCakePageActions);

        //Add master
        mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expMonthValue"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "expYear"), false, "", "", "", "", "", "", panCakePageActions);

        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.imgMC), "Verify Master card is able to add is able to add");
        //}

        //add visa
        mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "expMonthValue"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "expYear"), false, "", "", "", "", "", "", panCakePageActions);

        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.imgVISA), "Verify Discover card is able to add is able to add");

        mmyAccountPageActions.click(mmyAccountPageActions.addACreditCardBtn);
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.addressBlock), "Verify Existing address");
        mmyAccountPageActions.clearAndFillText(mmyAccountPageActions.addPayment_CardNumber, getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa2", "cardNumber"));
        mmyAccountPageActions.selectDropDownByValue(mmyAccountPageActions.addPayment_expmonth, getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa2", "expMonthValue"));
        mmyAccountPageActions.selectDropDownByVisibleText(mmyAccountPageActions.addPayment_expyr, getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa2", "expYear"));
        mmyAccountPageActions.click(mmyAccountPageActions.btnAddCard);
        AssertFailAndContinue(mmyAccountPageActions.getText(mmyAccountPageActions.pageLeverError).contains(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "errorMessages")), "Verify Error message while adding 6th card");

        mmyAccountPageActions.click(mmyAccountPageActions.billingCancel);
        mmyAccountPageActions.click(mmyAccountPageActions.removeCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "Last4")));
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.removeCardPage), "Verify Remove Card Page");
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.cancelDeleteAdd), "Verify CANCEL button in Delete Card page");
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.okDeleteAdd), "Verify OK button in Delete Card page");

        mmyAccountPageActions.click(mmyAccountPageActions.cancelDeleteAdd);
        AssertFailAndContinue(mmyAccountPageActions.isDisplayed(mmyAccountPageActions.removeCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "Last4"))), "Verify Card is not remove after click on cance button");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        addToBagBySearching(searchKeywordAndQty);
        mshippingPageActions.click(mshoppingBagPageActions.checkoutBtn);
        if (store.equalsIgnoreCase("US")) {
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingAdd.get("fName"), billingAdd.get("lName"), billingAdd.get("addressLine1"), billingAdd.get("addressLine2"),
                    billingAdd.get("city"), billingAdd.get("state"), billingAdd.get("zip"), billingAdd.get("phNum"), "");
            AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.cardNo(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "Last4"))), "Verify Last 4 digits of default ard no displayed in billing page");
            mbillingPageActions.clearAndFillText(mbillingPageActions.cvvFld, getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "MasterCard", "securityCode"));
        }

        if (store.equalsIgnoreCase("CA")) {
            billingAdd = dt2MobileExcel.getExcelData("BillingDetails", "validBillingDetailsCA");
            mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Reg(billingAdd.get("fName"), billingAdd.get("lName"), billingAdd.get("addressLine1"),
                    billingAdd.get("addressLine2"), billingAdd.get("city"), billingAdd.get("stateShortName"), billingAdd.get("zip"), billingAdd.get("phNum"), "");
            AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.cardNo(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Amex", "Last4"))), "Verify Last 4 digits of default ard no displayed in billing page");
            mbillingPageActions.clearAndFillText(mbillingPageActions.cvvFld, getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Amex", "securityCode"));
        }
        mbillingPageActions.click(mbillingPageActions.nextReviewButton);
        AssertFailAndContinue(mreviewPageActions.clickSubmOrderButton(), "Verify Order is submit with default card");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.click(mmyAccountPageActions.giftcards);

        //edit
        mmyAccountPageActions.click(mmyAccountPageActions.EditCardLink(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Amex", "Last4")));
        AssertFailAndContinue(mmyAccountPageActions.getAttributeValue(mmyAccountPageActions.addPayment_CardNumber, "value").contains(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Amex", "Last4")), "Verify CC number is displayed only last 4 digits");
        mmyAccountPageActions.click(mmyAccountPageActions.billingCancel);

        logout();
        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, "Place@123");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.click(mmyAccountPageActions.giftcards);
        AssertFailAndContinue(mmyAccountPageActions.waitUntilElementDisplayed(mmyAccountPageActions.removeCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "Last4")), 10), "logout and login verify saved cards are avilable in my account page");

        mmyAccountPageActions.deleteAllcards();
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.click(mmyAccountPageActions.addressbook);
        mmyAccountPageActions.deleteAllAddress();
    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }
}
