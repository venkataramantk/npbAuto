package tests.mobileDT.Shipping;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.List;
import java.util.Map;


//@Test(singleThreaded = true)
public class MobileShippingPageValidations2  extends MobileBaseTest{
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
            mheaderMenuActions.addStateCookie("ON");
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {REGISTEREDONLY, MYACCOUNT})
    public void verifyAddressVerificationOverlay(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify if the user is able to validate the address using address verification and address gets saved in my account.");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");

        //Navigate to my account and add shipping address
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        if (store.equalsIgnoreCase("US"))
            mmyAccountPageActions.addNewShippingAddress(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"),panCakePageActions);
        if (store.equalsIgnoreCase("CA"))
            mmyAccountPageActions.addNewShippingAddress(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"),panCakePageActions);

        Map<String, String> usBillingAddEdited = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShippingAddEdited = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);

        mheaderMenuActions.clickShoppingBagIcon();

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        mshippingPageActions.editAddresslink();

        //DT-38827
        if(store.equalsIgnoreCase("US"))
            mshippingPageActions.editFromSPWithAVPopUp(usBillingAddEdited.get("fName"), usBillingAddEdited.get("lName"), usBillingAddEdited.get("addressLine1"), usBillingAddEdited.get("city"), usBillingAddEdited.get("stateShortName"), usBillingAddEdited.get("zip"), usBillingAdd.get("phNum"));
        if(store.equalsIgnoreCase("CA"))
            mshippingPageActions.editFromSPWithAVPopUp(caShippingAddEdited.get("fName"), caShippingAddEdited.get("lName"), caShippingAddEdited.get("addressLine1"), caShippingAddEdited.get("city"), caShippingAddEdited.get("stateShortName"), caShippingAddEdited.get("zip"), caShippingAddEdited.get("phNum"));

        String enteredAddress = mheaderMenuActions.getText(mshippingPageActions.addressEntered);
        AddInfoStep("Edited address entered::" +enteredAddress);

        mshippingPageActions.returnToBagPage(mshoppingBagPageActions);
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mmyAccountPageActions.validateSavedAddressFromShipping(enteredAddress), "Verify edited address is shown in my account address book.");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {REGISTEREDONLY, CHECKOUT})
    public void verifyAddEditAddressFromSP(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify user is able to enter and subsequently edit shipping address from shipping page.");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        if (store.equalsIgnoreCase("US"))
            mmyAccountPageActions.addNewShippingAddress(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"),panCakePageActions);
        if (store.equalsIgnoreCase("CA"))
            mmyAccountPageActions.addNewShippingAddress(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"),panCakePageActions);

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);

        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }
        AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");

        mshippingPageActions.clickAddNewAddress();

        Map<String, String> usBillingAddNew = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShippingAddNew = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");

        //DT-38777
        if (store.equalsIgnoreCase("US"))
        {
            mshippingPageActions.addNewAddressFromShippingPage(usBillingAddNew.get("fName"), usBillingAddNew.get("lName"), usBillingAddNew.get("addressLine1"), usBillingAddNew.get("city"), usBillingAddNew.get("stateShortName"), usBillingAddNew.get("zip"), usBillingAddNew.get("phNum"), false);
            AssertFailAndContinue(mshippingPageActions.verifyAddressPrepop(usBillingAddNew.get("fName"), usBillingAddNew.get("lName"), usBillingAddNew.get("addressLine1"), usBillingAddNew.get("city"), usBillingAddNew.get("stateShortName"), usBillingAddNew.get("zip")), "Verify newly added address is shown on shipping page.");
        }
        else if (store.equalsIgnoreCase("CA"))
        {
            mshippingPageActions.addNewAddressFromShippingPage(caShippingAddNew.get("fName"), caShippingAddNew.get("lName"), caShippingAddNew.get("addressLine1"), caShippingAddNew.get("city"), caShippingAddNew.get("stateShortName"), caShippingAddNew.get("zip"), caShippingAddNew.get("phNum"), false);
            AssertFailAndContinue(mshippingPageActions.verifyAddressPrepop(caShippingAddNew.get("fName"), caShippingAddNew.get("lName"), caShippingAddNew.get("addressLine1"), caShippingAddNew.get("city"), caShippingAddNew.get("stateShortName"), caShippingAddNew.get("zip")), "Verify newly added address is shown on shipping page.");
        }

        //Now edit this same address
        mshippingPageActions.clickEditLnkOnShippingInfoPage();
        mshippingPageActions.clickEditLink(0);

        Map<String, String> usBillingAddEdited = dt2ExcelReader.getExcelData("ShippingDetails", "editedShippingAddressUS1");
        Map<String, String> caShippingAddEdited = dt2ExcelReader.getExcelData("ShippingDetails", "editedShippingAddressCA");

        if(store.equalsIgnoreCase("US"))
        {
            mshippingPageActions.editFromShippingPage(usBillingAddEdited.get("fName"), usBillingAddEdited.get("lName"), usBillingAddEdited.get("addressLine1"), usBillingAddEdited.get("city"), usBillingAddEdited.get("stateShortName"), usBillingAddEdited.get("zip"), usBillingAdd.get("phNum"));
            AssertFailAndContinue(mshippingPageActions.verifyAddressPrepop(usBillingAddEdited.get("fName"), usBillingAddEdited.get("lName"), usBillingAddEdited.get("addressLine1"), usBillingAddEdited.get("city"), usBillingAddEdited.get("stateShortName"), usBillingAddEdited.get("zip")), "Verify edited address is displayed on the shipping page");
        }
        if(store.equalsIgnoreCase("CA"))
        {
            mshippingPageActions.editFromShippingPage(caShippingAddEdited.get("fName"), caShippingAddEdited.get("lName"), caShippingAddEdited.get("addressLine1"), caShippingAddEdited.get("city"), caShippingAddEdited.get("stateShortName"), caShippingAddEdited.get("zip"), caShippingAddEdited.get("phNum"));
            AssertFailAndContinue(mshippingPageActions.verifyAddressPrepop(caShippingAddEdited.get("fName"), caShippingAddEdited.get("lName"), caShippingAddEdited.get("addressLine1"), caShippingAddEdited.get("city"), caShippingAddEdited.get("stateShortName"), caShippingAddEdited.get("zip")), "Verify edited address is displayed on the shipping page");
        }
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {REGISTEREDONLY,CAONLY, CHECKOUT})
    public void verifyProvinceValidation_CA(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify state field is labeled as Province in add new address overlay from edit address page");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);

        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }

        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }

        mshippingPageActions.enterShippingDetails(caShippingAdd.get("fName"),
                caShippingAdd.get("lName"),caShippingAdd.get("addressLine1"),caShippingAdd.get("city"),caShippingAdd.get("stateShortName")
                ,caShippingAdd.get("zip"),  caShippingAdd.get("phNum"));

        mshippingPageActions.clickNextBillingButtonWithEnteredAdd(mbillingPageActions);
        mbillingPageActions.clickOnShipping(mshippingPageActions);

        mshippingPageActions.clickAddNewAddfrmOverlay();

        //DT-38794
        AssertFailAndContinue(mshippingPageActions.getText(mshippingPageActions.provinceText).equalsIgnoreCase("Province"),"verify if user is displayed state field labeled as PROVINCE");
        mshippingPageActions.selectDropDownByValue(mshippingPageActions.stateDropDown, "ON");
    }


    @Test(priority = 3, dataProvider = dataProviderName, groups = { CHECKOUT})
    public void verifyPOBOXshippingValidation(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String,String> shippingDetails = null;
        setAuthorInfo("Raman Jha");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the User enters PO box address, shipping cost gets updated in order ledger");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);

        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        if(store.equalsIgnoreCase("US")){
            shippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsPOBUS");

        }
        if(store.equalsIgnoreCase("CA")){
            shippingDetails = dt2ExcelReader.getExcelData("ShippingDetails", "CAPOBoxAdd");

        }

        if(mshippingPageActions.isDisplayed(mshippingPageActions.editLink_ShipAddress)) {
            mshippingPageActions.clickAddNewAddress();
            mshippingPageActions.addNewAddressFromShippingPage_SetAsDefault(shippingDetails.get("fName"),
                    shippingDetails.get("lName"), shippingDetails.get("addressLine1"), shippingDetails.get("city"), shippingDetails.get("stateShortName")
                    , shippingDetails.get("zip"), shippingDetails.get("phNum"), false);
        }
        else{

            mshippingPageActions.enterShippingDetailsAndShipType(shippingDetails.get("fName"), shippingDetails.get("lName"), shippingDetails.get("addressLine1"), shippingDetails.get("addressLine2"), shippingDetails.get("city"), shippingDetails.get("stateShortName"), shippingDetails.get("zip"), shippingDetails.get("phNum"), shippingDetails.get("ShippingType"), email);

        }

        //DT-34564
        if(store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 1 && mshippingPageActions.getText(mshippingPageActions.estimatedDays).contains("Up To 15 Days"), "Verify Priority method is dispayed.");
            AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(),"Verify billing button redirection");
            // mshippingPageActions.click(mshippingPageActions.orderSummarySection);
            mbillingPageActions.clickOnShipping(mshippingPageActions);
            mshippingPageActions.expandOrderSummary();
            AssertFailAndContinue(mshippingPageActions.getText(mshippingPageActions.shippingTot).contains("10.00"), "Verify whether the shipping value in Order Ledger is displayed based on the user's shipping address for priority");
            mshippingPageActions.returnToBagPage(mshoppingBagPageActions);
            //DT-38025
            AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.shippingTot).contains("10.00"), "Verify whether the shipping value gets updated on Shopping Bag as well");
        }
        if(store.equalsIgnoreCase("CA")) {

            AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 1 && mshippingPageActions.getText(mshippingPageActions.estimatedDays).contains("7 to 10 business days - FREE"), "Verify Ground method is dispayed.");
            mshippingPageActions.click(mshippingPageActions.orderSummarySection);
            AssertFailAndContinue(mshippingPageActions.getText(mshippingPageActions.shippingTot).contains("8.00"), "Verify whether the shipping value in Order Ledger is displayed based on the user's shipping address for priority");
            mshippingPageActions.returnToBagPage(mshoppingBagPageActions);
            AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.shippingTot).contains("8.00"), "Verify whether the shipping value in Order Ledger is displayed based on the user's shipping address for priority");
        }
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {REGISTEREDONLY,PROD_REGRESSION})
    public void verifyGALookupOnEditShip(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify if the user is able to google lookup to autocomplete the address on edit shipping address");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        Map<String, String> usBillingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsUSFree");
        Map<String, String> caShippingAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCAFree");
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(), "Delete all existing address details from my account");
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(), "Delete all existing card details from my account");

        //Navigate to my account and add shipping address
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        if (store.equalsIgnoreCase("US"))
            mmyAccountPageActions.addNewShippingAddress(usBillingAdd.get("fName"), usBillingAdd.get("lName"), usBillingAdd.get("addressLine1"), usBillingAdd.get("city"), usBillingAdd.get("stateShortName"), usBillingAdd.get("zip"), usBillingAdd.get("phNum"),panCakePageActions);
        if (store.equalsIgnoreCase("CA"))
            mmyAccountPageActions.addNewShippingAddress(caShippingAdd.get("fName"), caShippingAdd.get("lName"), caShippingAdd.get("addressLine1"), caShippingAdd.get("city"), caShippingAdd.get("stateShortName"), caShippingAdd.get("zip"), caShippingAdd.get("phNum"),panCakePageActions);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }
        AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");

        String addressLine1 = "";

        if (store.equalsIgnoreCase("US"))
            addressLine1 = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "PartialAddressLine1US", "addressLine1");
        if (store.equalsIgnoreCase("CA"))
            addressLine1 = getDT2TestingCellValueBySheetRowAndColumn("ShippingDetails", "PartialAddressLine1CA", "addressLine1");

        mshippingPageActions.editAddresslink();

        mshippingPageActions.enterAddressLine1(addressLine1);
        mshippingPageActions.staticWait(3000);

        String updatedAdd = mshippingPageActions.getGoogleAddressLookupVal(0);

        AddInfoStep("First suggested value shown in google address lookup is " + updatedAdd);

        //DT-38813
        mshippingPageActions.clickGoogleAddressLookupItemByIndex(0);
        AssertFailAndContinue(mshippingPageActions.validateAddressIsPopulatedFromLookUp(updatedAdd), "Verify address gets populated with the same value selected using google address lookup");
    }

    @Test(priority = 5, dataProvider = dataProviderName, groups = {REGRESSION})
    public void validateOrderLedgerCost_ShipMethods(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String, String> shipAndBillDetails = null;
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, verify that the Shipping cost is calculated according to the Shipping method selected " +
                "and it is displayed as a separate line item named Shipping in the order Ledger section");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        List<String> shipMethodName = dt2ExcelReader.getColumnData("ShippingMethodCodes","shipName");
        List<String> shipMethodCost = dt2ExcelReader.getColumnData("ShippingMethodCodes","shipPrice");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        } else {
            mheaderMenuActions.clickShoppingBagIcon();
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with Registered user");
        }
        if (store.equalsIgnoreCase("US")) {
            shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "PlaceCard");
            if (env.equalsIgnoreCase("prod")) {
                shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        if(mshippingPageActions.isDisplayed(mshippingPageActions.editLink_ShipAddress)) {
            mshippingPageActions.clickAddNewAddress();
            mshippingPageActions.addNewAddressFromShippingPage_SetAsDefault(shipAndBillDetails.get("fName"),
                    shipAndBillDetails.get("lName"), shipAndBillDetails.get("addressLine1"), shipAndBillDetails.get("city"), shipAndBillDetails.get("stateShortName")
                    , shipAndBillDetails.get("zip"), shipAndBillDetails.get("phNum"), false);
        }
        else{
            mshippingPageActions.enterShippingDetailsAndShipType(shipAndBillDetails.get("fName"), shipAndBillDetails.get("lName"), shipAndBillDetails.get("addressLine1"), shipAndBillDetails.get("addressLine2"), shipAndBillDetails.get("city"), shipAndBillDetails.get("stateShortName"), shipAndBillDetails.get("zip"), shipAndBillDetails.get("phNum"), shipAndBillDetails.get("ShippingType"), email);
        }

       // mbillingPageActions.clickOnShipping(mshippingPageActions);
        if(store.equalsIgnoreCase("US")){
            AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 3, "Verify 3 shipping methods are displayed ");
        }
        //DT-34569, DT-34571, DT-34563,DT-40330,DT-40327
        if(store.equalsIgnoreCase("US")) {
            for(int i = 0; i< shipMethodName.size()-8;i++){
                AddInfoStep("Running for verifying Shipping cost on Order ledger for::" +shipMethodName.get(i));
                mshippingPageActions.selectShippingMethodFromRadioButton(shipMethodName.get(i));
                AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(),"Verify clicking on billing button");
                mbillingPageActions.expandOrderSummary();
                //DT-34549
                AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.itemPriceTtlLineItem),"Verify Item Price line item is displayed on order ledger on billing page");
                mbillingPageActions.getText(mbillingPageActions.itemPriceTtlLineItem);
                AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.shipTtlLineItem),"Verify Shipping method line item is displayed on order ledger on billing page");

                AssertFailAndContinue(mbillingPageActions.getText(mbillingPageActions.shippingTot).equalsIgnoreCase(shipMethodCost.get(i)),"Verify Shipping cost on Billing page for" +shipMethodName.get(i)+ " shipping type is" +shipMethodCost.get(i));
                if (env.equalsIgnoreCase("prod")){
                    mbillingPageActions.enterCardDetails(shipAndBillDetails.get("cardNumber"),shipAndBillDetails.get("securityCode"),shipAndBillDetails.get("expMonthValueUnit"));
                    mbillingPageActions.clickNextReviewButton();
                }
                else
                    mbillingPageActions.enterPLCCDetails(shipAndBillDetails.get("cardNumber"));
                AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(),"Click next review button");
                mreviewPageActions.expandOrderSummary();
                AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.shipTtlLineItem),"Verify Shipping method line item is displayed on order ledger on Review page");
                AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.shippingTot).equalsIgnoreCase(shipMethodCost.get(i)),"Verify Shipping cost on Review page for " +shipMethodName.get(i)+ "shipping type is" +shipMethodCost.get(i));
                mreviewPageActions.clickOnShippingAccordion(mshippingPageActions);
            }

        }

        if(store.equalsIgnoreCase("CA")) {
            for (int i = 9; i < shipMethodName.size(); i++) {
                AddInfoStep("Verifying Shipping cost on Order ledger for::" + shipMethodName.get(i));
                mshippingPageActions.selectShippingMethodFromRadioButton(shipMethodName.get(i));
                mshippingPageActions.clickNextBillingButton();
                mbillingPageActions.enterCardDetails(shipAndBillDetails.get("cardNumber"),shipAndBillDetails.get("securityCode"),shipAndBillDetails.get("expMonthValueUnit"));
                mbillingPageActions.expandOrderSummary();
                AssertFailAndContinue(mbillingPageActions.isDisplayed(mbillingPageActions.shipTtlLineItem),"Verify Shipping method line item is displayed on order ledger on billing page");
                AssertFailAndContinue(mbillingPageActions.getText(mbillingPageActions.shippingTot).equalsIgnoreCase(shipMethodCost.get(i)),"Verify Shipping cost on Billing page for" +shipMethodName.get(i)+ " shipping type is" +shipMethodCost.get(i));
                mbillingPageActions.clickNextReviewButton();
                mreviewPageActions.expandOrderSummary();
                AssertFailAndContinue(mreviewPageActions.isDisplayed(mreviewPageActions.shipTtlLineItem),"Verify Shipping method line item is displayed on order ledger on Review page");
                AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.shippingTot).equalsIgnoreCase(shipMethodCost.get(i)),"Verify Shipping cost on Review page for" +shipMethodName.get(i)+ " shipping type is" +shipMethodCost.get(i));
                mreviewPageActions.clickOnShippingAccordion(mshippingPageActions);
            }

        }

    }
    @Test(priority = 6, dataProvider = dataProviderName, groups = {REGRESSION,PROD_REGRESSION})
    public void validateOrderLedger_changeShipInfo(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String, String> billingAdd = null;
        String validUPCNumber="";
        String validZip="";

        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, With hybrid products in bag, verify that the user displays with the \"Shipping\" info section and when user changes the \"Shipping method\" within the \"Shipping\" info section, " +
                "verify that the order total and shipping label in order ledger gets updated");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        if(user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("MYACCOUNT");
            mmyAccountPageActions.clickSection("ADDRESSBOOK");
            AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(), "Delete all existing address details from my account");
            panCakePageActions.navigateToMenu("MYACCOUNT");
            mmyAccountPageActions.clickSection("GIFTCARDS");
            AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(), "Delete all existing card details from my account");
        }

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");

        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) > 0) {
            mheaderMenuActions.clickShoppingBagIcon();
            mshoppingBagPageActions.validateEmptyShoppingCart();
        }

        addToBagBySearching(searchKeywordAndQty);

        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        }
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
        }


        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);
        AddInfoStep("UPC details::" +validUPCNumber);

        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        mheaderMenuActions.clickShoppingBagIcon();

        Map<String, String> shipMethodDetailsRush = dt2ExcelReader.getExcelData("ShippingMethodCodes", "rush");
        Map<String, String> shipMethodDetailsFree = dt2ExcelReader.getExcelData("ShippingMethodCodes", "Standard");

        if (store.equalsIgnoreCase("US")) {
            billingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                billingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            billingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                billingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
            mshippingPageActions.enterpickUpDetails_GuestForMixedBag(getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "fName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "lName"),
                    email, getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "phNum"));
        }
        else if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsBopisReg(mpickUpPageActions), "Verify successful checkout with Registered user");
            mpickUpPageActions.clickShippingBtn(mshippingPageActions);
        }

        //DT-36632
        AssertFailAndContinue(mshippingPageActions.getText(mshippingPageActions.checkoutLabel).equalsIgnoreCase("CHECK OUT"),"Verify checkout text is displayed on Checkout page");
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.checkoutProgressBar),"Verify progress indicator is displayed on checkout page");

        mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(billingAdd.get("fName"), billingAdd.get("lName"), billingAdd.get("addressLine1"),
                billingAdd.get("addressLine2"), billingAdd.get("city"), billingAdd.get("stateShortName"), billingAdd.get("zip"), billingAdd.get("phNum"), billingAdd.get("ShippingType"), email);


        mbillingPageActions.clickOnShipping(mshippingPageActions);
        mshippingPageActions.selectShippingMethod(shipMethodDetailsFree.get("ShippingMethod"));
        mshippingPageActions.clickNextBillingButton();
        mbillingPageActions.enterCardDetails(billingAdd.get("cardNumber"), billingAdd.get("securityCode"), billingAdd.get("expMonthValueUnit"));

        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(),"Verify user redirected to review page");
        mreviewPageActions.expandOrderSummary();

        float estimatedTtl = Float.parseFloat(mreviewPageActions.getEstimateTotal());
        AddInfoStep("Estimated total with standard shipping method " +estimatedTtl);
        AssertFailAndContinue(mreviewPageActions.getShippingDetailsFromOrderSummaryText().equalsIgnoreCase(shipMethodDetailsFree.get("shipPrice")),"Verify shipping label on Order Review page ");
        mreviewPageActions.editShipMethodLink();

        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.nextBillingButton),"Verify shipping details page is displayed");
        AddInfoStep("Selecting shipping method on shipping page as::" +shipMethodDetailsRush.get("ShippingMethod"));
        mshippingPageActions.selectShippingMethod(shipMethodDetailsRush.get("ShippingMethod"));
        mshippingPageActions.clickNextBillingButton();
        mbillingPageActions.enterCVVForExpressAndClickNextReviewBtn(billingAdd.get("securityCode"));
        //DT-36656
        mreviewPageActions.expandOrderSummary();
        float updatedEstimatedTtl = Float.parseFloat(mreviewPageActions.getEstimateTotal());
        AddInfoStep("Estimated total with Rush shipping method " +updatedEstimatedTtl);

        AssertFailAndContinue(estimatedTtl != updatedEstimatedTtl,"Verify order total should not be equal after updating the shipping method on Order review page");
        AssertFailAndContinue(mreviewPageActions.getShippingDetailsFromOrderSummaryText().equalsIgnoreCase(shipMethodDetailsRush.get("shipPrice")),"Verify shipping label is updated on Order Review page");
        AssertFailAndContinue(mreviewPageActions.validateEstimatedTtlWithShipCostOnOrderLedger(),"Verify Order total calculation on updating the shipping method on order review page");

        //DT-38027
        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        AssertFailAndContinue(mshoppingBagPageActions.getShippingDetailsFromOrderSummaryText().equalsIgnoreCase(shipMethodDetailsRush.get("shipPrice")),"Verify shipping label is updated on shipping bag page for rush shipping method");
    }

    @Test(priority = 7, dataProvider = dataProviderName, groups = {REGRESSION,GUESTONLY})
    public void validateReviewPage_changeEmailAdd(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String, String> billingAdd = null;
        String validUPCNumber="";
        String validZip="";
        setAuthorInfo("Richa Priya");
        setRequirementCoverage("As a " + user + " user in " + store + " store, With hybrid products in bag, verify that the \"Email address\" gets updated within the \"Shipping\" section in the \"Review\" order info section.");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);

        if (store.equalsIgnoreCase("US")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        }
        if (store.equalsIgnoreCase("CA")) {
            validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "ValidUPCNoCA");
            validZip = getmobileDT2CellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
        }

        Map<String, String> validUPCAndZip = getMapOfItemNamesAndQuantityWithCommaDelimited(validUPCNumber, validZip);

        findBopisAvailStoresBySearchingUPCAndZip(validUPCAndZip);
        mheaderMenuActions.clickShoppingBagIcon();

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
        }

        if (store.equalsIgnoreCase("US")) {
            billingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                billingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            billingAdd = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                billingAdd = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(mshoppingBagPageActions.continueCheckoutAsGuest(), "Verify successful checkout with Guest user");
            mshippingPageActions.enterpickUpDetails_GuestForMixedBag(getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "fName"),
                    getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "lName"),
                    email, getmobileDT2CellValueBySheetRowAndColumn("BillingDetails", "validBillingDetailsUS", "phNum"));
        }

        AddInfoStep("Email address entered::" +email);
        mshippingPageActions.enterShippingDetailsShipMethodAndContinue_Guest_Reg(billingAdd.get("fName"), billingAdd.get("lName"), billingAdd.get("addressLine1"),
                billingAdd.get("addressLine2"), billingAdd.get("city"), billingAdd.get("stateShortName"), billingAdd.get("zip"), billingAdd.get("phNum"), billingAdd.get("ShippingType"), email);

        mbillingPageActions.enterCardDetails(billingAdd.get("cardNumber"), billingAdd.get("securityCode"), billingAdd.get("expMonthValueUnit"));
        mbillingPageActions.clickNextReviewButton();
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.emailIdShippingAdd).equalsIgnoreCase(email),"Verify Email address entered on shipping detail page is same on Order review page");
        mreviewPageActions.editShipMethodLink();
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.nextBillingButton),"Verify user is redirected to shipping details page");
        //DT-36654
        AddInfoStep("Email address updated on shipping page::" +billingAdd.get("emailAddress1"));
        mshippingPageActions.editEmailAdd_Guest(billingAdd.get("emailAddress1"));
        mshippingPageActions.clickNextBillingButton();
        mbillingPageActions.enterCVVForExpressAndClickNextReviewBtn(billingAdd.get("securityCode"));
        AssertFailAndContinue(mreviewPageActions.getText(mreviewPageActions.emailIdShippingAdd).equalsIgnoreCase(billingAdd.get("emailAddress1")),"Verify email address updated on Order Review page");

    }

    @Test(priority = 8, dataProvider = dataProviderName, groups= {CHECKOUT} )
    public  void validatePromotionFieldOnOrderLedger(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        String appUrl="";
        Map<String, String> shipAndBillDetails = null;
        Map<String, String> mailingDetails = null;

        setAuthorInfo("Richa Priya");

        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is able to see " +
                "promotion details in order ledger with negative sign when checkout is performed by hitting the shipping page url");

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

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (Integer.parseInt(mheaderMenuActions.getQty()) == 0) {
            addToBagBySearching(searchKeywordAndQty);
        }else{
            AssertFailAndContinue(mheaderMenuActions.clickShoppingBagIcon(),"Verify click on shopping bag");
        }

        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.itemsCount), "Verify items line item is present.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.promotionsLabel), "Verify promotion label is displayed.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.promotionsTot), "Verify promotion amount is displayed.");
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.promotionsTot).contains("-"), "Verify if promotion amount is present with negative sign in shipping page");

        appUrl = EnvironmentConfig.getApplicationUrl();
        appUrl = appUrl.replace("home","checkout/shipping");

        AddInfoStep("Hit the url on the browser" +appUrl);
        mobileDriver.get(appUrl);
        mshippingPageActions.waitUntilElementDisplayed(mshippingPageActions.nextBillingButton,30);

        if (store.equalsIgnoreCase("US")) {
            shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "MasterCardCA");
            if (env.equalsIgnoreCase("prod")) {
                shipAndBillDetails = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        if(mshippingPageActions.isDisplayed(mshippingPageActions.editLink_ShipAddress)) {
            mshippingPageActions.clickAddNewAddress();
            mshippingPageActions.addNewAddressFromShippingPage_SetAsDefault(shipAndBillDetails.get("fName"),
                    shipAndBillDetails.get("lName"), shipAndBillDetails.get("addressLine1"), shipAndBillDetails.get("city"), shipAndBillDetails.get("stateShortName")
                    , shipAndBillDetails.get("zip"), shipAndBillDetails.get("phNum"), false);
        }
        else{
            mshippingPageActions.enterShippingDetailsAndShipType(shipAndBillDetails.get("fName"), shipAndBillDetails.get("lName"), shipAndBillDetails.get("addressLine1"), shipAndBillDetails.get("addressLine2"), shipAndBillDetails.get("city"), shipAndBillDetails.get("stateShortName"), shipAndBillDetails.get("zip"), shipAndBillDetails.get("phNum"), shipAndBillDetails.get("ShippingType"), email);
        }
        Map<String, String> shipMethodDetailsExpress = dt2ExcelReader.getExcelData("ShippingMethodCodes", "express");
        mshippingPageActions.selectShippingMethodFromRadioButton(mshippingPageActions.getAvailableShippingMethods().get(1));
        Map<String, String> mc = null;
        if (store.equalsIgnoreCase("US")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "Visa");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VISA_PROD");
            }
        } else if (store.equalsIgnoreCase("CA")) {
            mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2ExcelReader.getExcelData("BillingDetails", "VisaCA_PROD");
            }
        }

        //DT-35910
        mshippingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.promotionsLabel), "Verify promotion label is displayed.");
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.promotionsTot), "Verify promotion amount is displayed.");
        AssertFailAndContinue(mshippingPageActions.getText(mshippingPageActions.promotionsTot).contains("-"), "Verify if promotion amount is present with negative sign in shipping page");
        AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(), "Verify user is redirected to Billing page");
        mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        mbillingPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.promotionsLabel), "Verify promotion label is displayed.");
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.promotionsTot), "Verify promotion amount is displayed.");
        mbillingPageActions.clickNextReviewButton();
        mreviewPageActions.expandOrderSummary();
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.promotionsLabel), "Verify promotion label is displayed.");
        AssertFailAndContinue(mshippingPageActions.isDisplayed(mshippingPageActions.promotionsTot), "Verify promotion amount is displayed.");

        mreviewPageActions.returnToBag(mshoppingBagPageActions);
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.itemsCount), "Verify items line item is present.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.promotionsLabel), "Verify promotion label is displayed.");
        AssertFailAndContinue(mshoppingBagPageActions.isDisplayed(mshoppingBagPageActions.promotionsTot), "Verify promotion amount is displayed.");
        AssertFailAndContinue(mshoppingBagPageActions.getText(mshoppingBagPageActions.shippingTot).equals(shipMethodDetailsExpress.get("shipPrice")), "Verify shipping line item has price ($15) as value");
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