package tests.mobileDT.myAccount;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;
import uiMobile.UiBaseMobile;

import java.util.Map;

//@Test(singleThreaded = true)
public class AddressBookTests extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = UiBaseMobile.randomEmail();
    String password = "";
    ExcelReader dt2MobileExcel = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        env = EnvironmentConfig.getEnvironmentProfile();
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
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void addressBookUiValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("jagadeesh");
        setRequirementCoverage("UI validations for Profile Information Page" +
                "DT-44898, DT-44901, DT-44572");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mheaderMenuActions.validateHrefLangTag(), "Verify HREF Lang tag should contain www across all the pages");

        //DT-23492, DT-23497
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("HEADER"), "Verify Address Book Header");
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("ADDADDRESS"), "Verify Add New Address button");

        //DT-23592, DT-23596
        mmyAccountPageActions.clickAddNewAddressBtn();
        AssertFailAndContinue(mmyAccountPageActions.verifyAllFieldsinAddNewAddress(), "clicks on \"ADD NEW ADDRESS \" button,verify that user is displayed with the following text fields in \"Add New Shipping address\" section of Address book" +
                " 1.First name\n 2.Last name\n3.Address Line 1\n 4.Address Line 2(optional)\n 5.City\n 6.State\n 7.Zip code\n 8.Country\n 9.Mobile phone number\n 10.Cancel button\n11.Add address button\n and a checkbox with text \"Set as default address\"   ");

        Map<String, String> usShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCA1");

        //Add a new address
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(mmyAccountPageActions.validateCurrentCountry("United States"), "validate United States is selected from country drop-down");
            mmyAccountPageActions.addNewShippingAddress(usShipping.get("fName"), usShipping.get("lName"), usShipping.get("addressLine1"), usShipping.get("city"), usShipping.get("StateShortcut"), usShipping.get("zip"), usShipping.get("phNum"), panCakePageActions);
        }
        if (store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(mmyAccountPageActions.validateCurrentCountry("Canada"), "validate Canada is selected from country drop-down");
            mmyAccountPageActions.addNewShippingAddress(caShipping.get("fName"), caShipping.get("lName"), caShipping.get("addressLine1"), caShipping.get("city"), caShipping.get("StateShortcut"), caShipping.get("zip"), caShipping.get("phNum"), panCakePageActions);
        }

        mmyAccountPageActions.deleteAddressByPosition(1);
        AssertFailAndContinue(mmyAccountPageActions.isDeleteAddressFieldDisplayed("POPUP"), "Click delete address button, Verify \"Delete Address\" popup");
        AssertFailAndContinue(mmyAccountPageActions.isDeleteAddressFieldDisplayed("CANCELBTN"), "Verify \"Cancel\" button");
        AssertFailAndContinue(mmyAccountPageActions.isDeleteAddressFieldDisplayed("OKBTN"), "Verify \"OK\" Button");
        AssertFailAndContinue(mmyAccountPageActions.isDeleteAddressFieldDisplayed("CLOSE"), "Verify \"Close icon\" Button");

        AssertFailAndContinue(mmyAccountPageActions.clickButtonInDeleteAddress("CANCELBTN"), "Click cancel button in delete address popup verify address is not deleted from address book");

        mmyAccountPageActions.deleteAddressByPosition(1);
        AssertFailAndContinue(mmyAccountPageActions.clickButtonInDeleteAddress("CLOSE"), "Click close button in delete address popup verify address is not deleted from address book");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("PROFILE");

        mmyAccountPageActions.clickEditButton();
        AssertFailAndContinue(mmyAccountPageActions.isMyAccountFieldDisplayed("SHIPPINGADD"), "Profile Information section should be displayed with the saved shipping address if atleaset 1 shipping address is available");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        mmyAccountPageActions.deleteAddressByPosition(1);
        AssertFailAndContinue(mmyAccountPageActions.clickButtonInDeleteAddress("OKBTN"), "Click OK button in delete address popup verify address is deleted from address book");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void editAddressUiValidations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("jagadeesh");
        setRequirementCoverage("As a registered user in " + store + " Store verify \n" +
                "1. Fields for shipping address page when in edit mode\n" +
                "2. Phone No is auto populated" +
                "DT-44573");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        String phone = "";
        //mmyAccountPageActions.click(mmyAccountPageActions.addressbook);
        Map<String, String> usShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> caShipping = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsCA");

        if (store.equalsIgnoreCase("US")) {
            mmyAccountPageActions.addNewShippingAddress(usShipping.get("fName"), usShipping.get("lName"), usShipping.get("addressLine1"), usShipping.get("city"),
                    usShipping.get("stateShortName"), usShipping.get("zip"), usShipping.get("phNum"), panCakePageActions);
            phone = usShipping.get("phNum");
        }
        if (store.equalsIgnoreCase("CA")) {
            mmyAccountPageActions.addNewShippingAddress(caShipping.get("fName"), caShipping.get("lName"), caShipping.get("addressLine1"), caShipping.get("city"),
                    caShipping.get("stateShortName"), caShipping.get("zip"), caShipping.get("phNum"), panCakePageActions);
            phone = caShipping.get("phNum");
        }

        mmyAccountPageActions.clickEditShippingAddress();

        //DT-23554, /DT-23595, DT-23608
        AssertFailAndContinue(mmyAccountPageActions.verifyAlltheFieldsInEditShippingPage(), "Verify all the fields in edit shipping page");
        AssertFailAndContinue(mmyAccountPageActions.validatePhoneNoInEditAddressPage(phone), "verify Phone no entered while creating address is displayed");

        //DT-23563        //DT-23562, /DT-23565
        AssertFailAndContinue(mmyAccountPageActions.validateCountriesInEditAddressPage(), "Verify both US and Canada country names are displayed in Country Drop-down");
        AssertFailAndContinue(mmyAccountPageActions.validateDefaultShippingAddress(), "Verify Default shipping address checkbox is displayed and selected");
        AssertFailAndContinue(mmyAccountPageActions.clickCancelBtnInEditBillingPage(), "Verify Edit Address is closed on clicking on Cancel button");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY})
    public void inlineErrorMessages(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("jagadeesh");
        setRequirementCoverage("Inline error message for address book" +
                "1. DT-31422");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        mmyAccountPageActions.clickAddNewAddressBtn();
        String specialCharacters = "!@#$%ˆˆ*()_89";

        mmyAccountPageActions.fillMyAccountField("FIRSTNAME", specialCharacters);
        mmyAccountPageActions.fillMyAccountField("LASTNAME", specialCharacters);
        mmyAccountPageActions.fillMyAccountField("ADDRESS1", specialCharacters);

        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("FIRSTNAME").contains("First name field should not contain any special characters"), "Validate Error message for First Name when special characters entered");
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("LASTNAME").contains("Last name field should not contain any special characters"), "Validate Error message for Last Name when special characters entered");
        mmyAccountPageActions.fillMyAccountField("FIRSTNAME", specialCharacters);
        AssertFailAndContinue(mmyAccountPageActions.getErrorMsg("ADDRESSLINE1").contains("The value entered in the street address has special character"), "Validate Error message for Address line1 when special characters entered");
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY, USONLY})
    public void differentAddressMappingUs(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("jagadeesh");
        setRequirementCoverage("Validations of different shipping methods depends on Shipping addresses" +
                "DT-43804, DT-43801, DT-43801 DT-44901, DT-44902, DT-44904");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        mmyAccountPageActions.deleteAllAddress();

        Map<String, String> texasAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validTXAddressDetailsUS");
        mmyAccountPageActions.addNewShippingAddress(texasAdd.get("fName"), texasAdd.get("lName"), texasAdd.get("addressLine1") + "  ", texasAdd.get("city"), texasAdd.get("stateShortName"), texasAdd.get("zip"), texasAdd.get("phNum"), panCakePageActions);

        Map<String, String> akhipr = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsAKHIPRUS");
        mmyAccountPageActions.addNewShippingAddress(akhipr.get("fName"), akhipr.get("lName"), akhipr.get("addressLine1"), akhipr.get("city"), akhipr.get("stateShortName"), akhipr.get("zip"), akhipr.get("phNum"), panCakePageActions);

        AssertFailAndContinue(mmyAccountPageActions.getDefaultShippingAddress().contains(akhipr.get("addressLine1")), "When the user has default shipping address saved in his account, Clicks on the Add new address button in the Address book section of my account page, enters the valid details in the add new address page and checks the checkbox \"Set as default shipping address\" and saves the address, verify whether the previously saved default shipping address replaced with newly added default shipping address");

        Map<String, String> priority = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsPOBUS");
        mmyAccountPageActions.addNewShippingAddress(priority.get("fName"), priority.get("lName"), priority.get("addressLine1"), priority.get("city"), priority.get("stateShortName"), priority.get("zip"), priority.get("phNum"), panCakePageActions);

        mmyAccountPageActions.clickEditShippingAddress(akhipr.get("addressLine1"));
        mmyAccountPageActions.selectDefaultShippingAddressCheckbox();
        mmyAccountPageActions.clickUpdateButton();

        AssertFailAndContinue(mmyAccountPageActions.getDefaultShippingAddress().contains(akhipr.get("addressLine1")), "store having default shipping address and address(es) saved in 'Address Book' , when user navigates to \"Edit address\" section of \"address book\" page with fields prepopulated,checks the checkbox \"set as default address\" ,Verify if the previously saved default shipping address replaced with newly added default shipping address");

        mheaderMenuActions.clickOnTCPLogo();
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        addToBagBySearching(searchKeywordAndQty);

        mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);

        boolean methods = mshippingPageActions.getAvailableShippingMethods().get(0).contains("Standard");
        AssertFailAndContinue(methods, "shipping address that is set with a Tx state and corresponding zip code, verify that  1.Standard is displayed to user in 'Shipping Methods' ");
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() >= 2, "And also verify that only 1.Standard is displayed in shipping page.");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("Priority"), "when user has default shipping address containing state and Zipcode, verify Priority shipping is not displayed");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("AK, HI, PR Express"), "when user has default shipping address containing state and Zipcode, verify AK, HI, PR Rush shipping is not displayed");


        mshippingPageActions.editAddressFromShipping(priority.get("addressLine1"));
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().get(0).contains("Priority"), "when user has default shipping address containing state and Zipcode that accepts only Priority shipping method, within 'Shipping' page verify that 'Priority' shipping method is selected by default");
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 1, "And also verify that only 'Priority' shipping method is displayed in shipping page.");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("AK, HI, PR Express"), "when user has default shipping address PO BOX, verify AK, HI, PR Rush shipping is not displayed");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("Standard"), "When user has default shipping address PO BOX, verify Standard shipping is not displayed");

        mshippingPageActions.editAddressFromShipping(akhipr.get("addressLine1"));
        boolean methods1 = mshippingPageActions.getAvailableShippingMethods().get(0).contains("AK, HI, PR Express") && mshippingPageActions.getAvailableShippingMethods().get(1).contains("AK, HI, PR Rush");
        AssertFailAndContinue(methods1, "when user has default shipping address containing state and Zipcode that accepts only AK,HI,PR shipping method,verify that within 'Shipping' page, verify that 1. AK,HI,PR Express \n 2. AK,HI,PR Rush shipping methods are displayed");
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 2, "And also verify that only 1. AK,HI,PR Express \n 2. AK,HI,PR Rush shipping method is displayed in shipping page.");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("Standard"), "When user has default shipping address AK,HI,PR Express, verify Standard shipping is not displayed");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("Priority"), "when user has default shipping address AK,HI,PR Express, verify Priority shipping is not displayed");

        mshippingPageActions.returnToBagPage(mshoppingBagPageActions);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mmyAccountPageActions.getShippingAddressCount() == 3, "Verify 3 shipping address available");

        mmyAccountPageActions.deleteDefaultShippingAddress();
        AssertFailAndContinue(mmyAccountPageActions.getShippingAddressCount() == 2, "Delete default shipping address when more than 2 shipping address available and verify default address is deleted ");
        AssertFailAndContinue(!mmyAccountPageActions.isDefautShippingAddressAvailable(), "and no address is marked as default shipping");

        mmyAccountPageActions.clickEditShippingAddress(texasAdd.get("addressLine1"));
        mmyAccountPageActions.selectDefaultShippingAddressCheckbox();
        mmyAccountPageActions.clickUpdateButton();

        AssertFailAndContinue(mmyAccountPageActions.isDefautShippingAddressAvailable(), "Make any existing address as default shipping");

        mmyAccountPageActions.deleteDefaultShippingAddress();

        AssertFailAndContinue(mmyAccountPageActions.getShippingAddressCount() == 1, "Delete default shipping address when 2 shipping address available and verify default address is deleted");
        AssertFailAndContinue(mmyAccountPageActions.isDefautShippingAddressAvailable(), "Verify existing shipping address is marked as default shipping");
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY, CAONLY})
    public void differentAddressMappingCA(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        ExcelReader dt2ExcelReader = new ExcelReader(Config.getDataFile(Config.DT2_MOBILE));
        setRequirementCoverage("Validations of different shipping methods depends on Shipping addresses for CA");
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify inline error messages as registered user in" + store + " store ");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        mmyAccountPageActions.deleteAllAddress();

        Map<String, String> texasAdd = dt2ExcelReader.getExcelData("ShippingDetails", "validShippingDetailsCA");

        mmyAccountPageActions.addNewShippingAddress(texasAdd.get("fName"), texasAdd.get("lName"), texasAdd.get("addressLine1") + "  ", texasAdd.get("city"), texasAdd.get("stateShortName"), texasAdd.get("zip"), texasAdd.get("phNum"), panCakePageActions);

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        addToBagBySearching(searchKeywordAndQty);

        mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions);
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().get(0).contains("Ground"), "when user has default shipping address containing 'Province' and 'Postalcode' that accepts only Ground,Express shipping method, within 'Shipping' page verify that  Ground \n Expres shipping methods are displayed in shipping page:");
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 1, "And also verify that only 'Priority' shipping method is displayed in shipping page.");
    }

    @Test(priority = 5, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY, USONLY})
    public void differentAddressMappingUs_EC(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a registered used in US store. verify " +
                "DT-44905");

        panCakePageActions.navigateToMenu("LOGIN");
        mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");

        mmyAccountPageActions.deleteAllAddress();

        Map<String, String> priority = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsPOBUS");
        mmyAccountPageActions.addNewShippingAddress(priority.get("fName"), priority.get("lName"), priority.get("addressLine1"), priority.get("city"), priority.get("stateShortName"), priority.get("zip"), priority.get("phNum"), panCakePageActions);

        Map<String, String> akhipr = dt2MobileExcel.getExcelData("ShippingDetails", "validShippingDetailsAKHIPRUS");
        mmyAccountPageActions.addNewShippingAddress(akhipr.get("fName"), akhipr.get("lName"), akhipr.get("addressLine1"), akhipr.get("city"), akhipr.get("stateShortName"), akhipr.get("zip"), akhipr.get("phNum"), panCakePageActions);

        Map<String, String> texasAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validTXAddressDetailsUS");
        mmyAccountPageActions.addNewShippingAddress(texasAdd.get("fName"), texasAdd.get("lName"), texasAdd.get("addressLine1") + "  ", texasAdd.get("city"), texasAdd.get("stateShortName"), texasAdd.get("zip"), texasAdd.get("phNum"), panCakePageActions);

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");

        mmyAccountPageActions.addACreditCard(getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "cardNumber"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "expMonthValue"),
                getmobileDT2CellValueBySheetRowAndColumn("PaymentDetails", "Visa", "expYear"), false, "", "", "", "", "", "", panCakePageActions);

        mheaderMenuActions.clickOnTCPLogo();
        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        addToBagBySearching(searchKeywordAndQty);

        mshoppingBagPageActions.clickProceedToCheckoutExpress(mreviewPageActions);

        mreviewPageActions.clickOnShippingAccordion(mshippingPageActions);

        boolean methods = mshippingPageActions.getAvailableShippingMethods().get(0).contains("Standard");
        AssertFailAndContinue(methods, "shipping address that is set with a Tx state and corresponding zip code, verify that  1.Standard is displayed to user in 'Shipping Methods' ");
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() >= 2, "And also verify that only 1.Standard is displayed in shipping page.");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("Priority"), "when user has default shipping address containing state and Zipcode, verify Priority shipping is not displayed");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("AK, HI, PR Express"), "when user has default shipping address containing state and Zipcode, verify AK, HI, PR Rush shipping is not displayed");

        mshippingPageActions.editAddressFromShipping(priority.get("addressLine1"));
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().get(0).contains("Priority"), "when user has default shipping address containing state and Zipcode that accepts only Priority shipping method, within 'Shipping' page verify that 'Priority' shipping method is selected by default");
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 1, "And also verify that only 'Priority' shipping method is displayed in shipping page.");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("AK, HI, PR Express"), "when user has default shipping address PO BOX, verify AK, HI, PR Rush shipping is not displayed");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("Standard"), "When user has default shipping address PO BOX, verify Standard shipping is not displayed");

        mshippingPageActions.editAddressFromShipping(akhipr.get("addressLine1"));
        boolean methods1 = mshippingPageActions.getAvailableShippingMethods().get(0).contains("AK, HI, PR Express") && mshippingPageActions.getAvailableShippingMethods().get(1).contains("AK, HI, PR Rush");
        AssertFailAndContinue(methods1, "when user has default shipping address containing state and Zipcode that accepts only AK,HI,PR shipping method,verify that within 'Shipping' page, verify that 1. AK,HI,PR Express \n 2. AK,HI,PR Rush shipping methods are displayed");
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 2, "And also verify that only 1. AK,HI,PR Express \n 2. AK,HI,PR Rush shipping method is displayed in shipping page.");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("Standard"), "When user has default shipping address AK,HI,PR Express, verify Standard shipping is not displayed");
        AssertFailAndContinue(!mshippingPageActions.getAvailableShippingMethods().get(0).contains("Priority"), "when user has default shipping address AK,HI,PR Express, verify Priority shipping is not displayed");
    }

    //This is already covered in differentAddressMappingUs disabled
    @Test(priority = 5, dataProvider = dataProviderName, groups = {MYACCOUNT, REGISTEREDONLY, USONLY}, enabled = false)
    public void validatePlaceOrderWithExistingUser(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String, String> mc = null;
        setAuthorInfo("Shilpa P ");
        setRequirementCoverage("As a " + user + " user in " + store + " store, validations order placement after adding shipping address in My Account");

        Map<String, String> texasAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShipDetailsPriorityUS");

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (store.equalsIgnoreCase("US")) {
            mc = dt2MobileExcel.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2MobileExcel.getExcelData("BillingDetails", "VISA_PROD");
            }
        }
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllAddress(), "Delete all existing address details from my account");

        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("GIFTCARDS");
        AssertFailAndContinue(mmyAccountPageActions.deleteAllcards(), "Delete all existing card details from my account");

        mmyAccountPageActions.addNewShippingAddress(texasAdd.get("fName"), texasAdd.get("lName"), texasAdd.get("addressLine1"), texasAdd.get("city"), texasAdd.get("stateShortName"), texasAdd.get("zip"), texasAdd.get("phNum"), panCakePageActions);

        addToBagBySearching(searchKeywordAndQty);

        AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with registered user");

        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 1, "And also verify that only 'Priority' shipping method is displayed in shipping page.");
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().get(0).contains("Priority"), "when user has default shipping address containing 'Province' and 'Postalcode' that accepts only Ground,Express shipping method, within 'Shipping' page verify that  Ground \n Expres shipping methods are displayed in shipping page:");
        AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(), "verify click on billing button");

        if (env.equalsIgnoreCase("prod")) {
            mbillingPageActions.enterCardDetails(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));

        } else {
            mbillingPageActions.enterCardDetails(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        }

        // mbillingPageActions.click(mbillingPageActions.nextReviewButton);
        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(), "Verify click on review button");
        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.clickSubmOrderButton();
            //DT-36488
            AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageReg(), "Verify Order Confirmation Page");
        }
    }

    //This is express checkout, must be covered in express checkout test disabled
    @Test(priority = 6, dataProvider = dataProviderName, groups = {MYACCOUNT, USONLY, REGISTEREDONLY}, enabled = false)
    public void newAccountAddressMappingUS(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        Map<String, String> mc = null;
        String email1 = UiBaseMobile.randomEmail();
        setAuthorInfo("Shilpa P");
        setRequirementCoverage("As a " + user + " user in " + store + " store, validations of Adding a new shipping Address in  My Account and place the  order successfully");
        if (store.equalsIgnoreCase("US")) {
            mc = dt2MobileExcel.getExcelData("BillingDetails", "MasterCard");
            if (env.equalsIgnoreCase("prod")) {
                mc = dt2MobileExcel.getExcelData("BillingDetails", "VISA_PROD");
            }
        }
        if (user.equalsIgnoreCase("registered")) {
            createAccount(rowInExcelUS, email1);
            driver.navigate().refresh();
        }

        mheaderMenuActions.staticWait(8000);
        panCakePageActions.navigateToMenu("MYACCOUNT");
        mmyAccountPageActions.clickSection("ADDRESSBOOK");
        Map<String, String> texasAdd = dt2MobileExcel.getExcelData("ShippingDetails", "validShipDetailsPriorityUS");
        mmyAccountPageActions.addNewShippingAddress(texasAdd.get("fName"), texasAdd.get("lName"), texasAdd.get("addressLine1"), texasAdd.get("city"), texasAdd.get("stateShortName"), texasAdd.get("zip"), texasAdd.get("phNum"), panCakePageActions);

        String searchKeyword = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        addToBagBySearching(searchKeywordAndQty);
        AssertFailAndContinue(mshoppingBagPageActions.clickOnCheckoutBtnAsReg(mshippingPageActions, mreviewPageActions), "Verify successful checkout with registered user");

        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().get(0).contains("Priority"), "when user has default shipping address containing 'Province' and 'Postalcode' that accepts only Ground,Express shipping method, within 'Shipping' page verify that  Ground \n Expres shipping methods are displayed in shipping page:");
        AssertFailAndContinue(mshippingPageActions.getAvailableShippingMethods().size() == 1, "And also verify that only 'Priority' shipping method is displayed in shipping page.");
        AssertFailAndContinue(mshippingPageActions.clickNextBillingButton(), "verify click on billing button");

        if (env.equalsIgnoreCase("prod")) {
            mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));

        } else {
            mbillingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(mc.get("cardNumber"), mc.get("securityCode"), mc.get("expMonthValueUnit"));
        }
        mbillingPageActions.clickSameAsShipAddress();
        AssertFailAndContinue(mbillingPageActions.clickNextReviewButton(), "Verify click on review button");

        if (!env.equalsIgnoreCase("prod")) {
            mreviewPageActions.clickSubmOrderButton();
            //DT-36472
            AssertFailAndContinue(mreceiptThankYouPageActions.validateTextInOrderConfirmPageReg(), "Verify Order Confirmation Page");
        }

    }

    @AfterClass(alwaysRun = true)
    public void quitDriver() {
        mobileDriver.quit();
    }
}
