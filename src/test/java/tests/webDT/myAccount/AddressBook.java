package tests.webDT.myAccount;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by AbdulazeezM on 7/19/2017.
 */
public class AddressBook extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    private String password;
    String env;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        env = EnvironmentConfig.getEnvironmentProfile();
        driver.get(EnvironmentConfig.getApplicationUrl());
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcel, "Password");
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        // driver.manage().window().maximize();
        // driver.manage().deleteAllCookies();
        headerMenuActions.deleteAllCookies();//to delete privacy policy cookie
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
//        driver.manage().deleteAllCookies();
        headerMenuActions.deleteAllCookies();
    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION, REGISTEREDONLY, USONLY, PROD_REGRESSION})
    public void addingShippingAddressAndValidateFields(@Optional("US") String store) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if register user can add the default shipping address in my account page and validate the fields present in the address book section" +
                "DT-43802");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        List<String> splCharForMoreThan = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "moreThan50", "ErrorMessages"));

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        headerMenuActions.waitUntilElementDisplayed(myAccountPageActions.lnk_PaymentGC, 2);
        AssertFailAndContinue(myAccountPageActions.clickAddressLink(), "Click on address link and check user navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddNewAddressBook(), "Click on add new address button and check user displayed with the address fields");
        AssertFailAndContinue(myAccountPageActions.validateFieldsInAddress(), "validate the fields present in the shipping address page");
        AssertFailAndContinue(myAccountPageActions.validateMoreThan50ErrMsg(splCharForMoreThan.get(0),splCharForMoreThan.get(1),splCharForMoreThan.get(2),splCharForMoreThan.get(4),splCharForMoreThan.get(5)),"Check the maximum character error message in the address book fields");
        AssertFailAndContinue(myAccountPageActions.clickAddressBookBreadcrumb(), "Click on addresss book breadcrumb and check user navigate back");
        AssertFailAndContinue(myAccountPageActions.addNewShippingAddress(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum")), "Entering all details in Add a New Address Overlay");
        myAccountPageActions.click(myAccountPageActions.lnk_AccountOverView);
        myAccountPageActions.updateShipAdd();
        AssertFailAndContinue(myAccountPageActions.addNewDiffCountryShippingAdd((shipDetailCA.get("fName")), shipDetailCA.get("lName"), shipDetailCA.get("country"), shipDetailCA.get("addressLine1"), shipDetailCA.get("addressLine2"), shipDetailCA.get("city"),
                shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum")), "Entering all details in Add a New Address Overlay");
        myAccountPageActions.click(myAccountPageActions.lnk_AccountOverView);
        myAccountPageActions.updateShipAdd();
    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION, REGISTEREDONLY, USONLY, PROD_REGRESSION})
    public void validateAddressFieldErrMsg(@Optional("US") String store) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if register user can validate the error message in shipping address fields");
        List<String> errMsgTab = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "addressFieldErrTab", "FieldLevelErrorMsg"));
        List<String> errMsgSplChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "addressFieldErrSplChar", "FieldLevelErrorMsg"));
        List<String> errMsgMaxChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "addressFieldMaxCarErr", "FieldLevelErrorMsg"));

        clickLoginAndLoginAsRegUser(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickAddressLink(), "Click on address link and check user navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddNewAddressBook(), "Click on add new address button and check user displayed with the address fields");
        AssertFailAndContinue(myAccountPageActions.validateAddressErrMsgTab(errMsgTab.get(0), errMsgTab.get(1), errMsgTab.get(2), errMsgTab.get(3), errMsgTab.get(4), errMsgTab.get(5), errMsgTab.get(6)), "Validate the error message when tab the address field");
        AssertFailAndContinue(myAccountPageActions.validateAddressSplCharErrMsg(errMsgSplChar.get(0), errMsgSplChar.get(1), errMsgSplChar.get(2), errMsgSplChar.get(2), errMsgSplChar.get(3), errMsgSplChar.get(4), errMsgSplChar.get(5)), "Validate the error message when tab the address field");
        AssertFailAndContinue(myAccountPageActions.validateMaxCharError(errMsgMaxChar.get(0), errMsgMaxChar.get(1)), "Verify the max character error message is displayed to the user");

    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION, REGISTEREDONLY, USONLY, PROD_REGRESSION})
    public void editShippingAddress(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if register user can validate the error message in shipping address fields" +
                "DT-43802");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");
        Map<String, String> editShipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "editedShippingAddressUS");
        String header = getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "deleteAddress", "TermsContent");
        List<String> splCharForMoreThan = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CheckoutErrorMessages", "moreThan50", "ErrorMessages"));

        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickAddressLink(), "Click on address link and check user navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddNewAddressBook(), "Click on add new address button and check user displayed with the address fields");
        AssertFailAndContinue(myAccountPageActions.clickAddressBookBreadcrumb(), "Click on address book breadcrumb and check user navigate back");
        AssertFailAndContinue(myAccountPageActions.addNewShippingAddress(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum")), "Entering all details in Add a New Address Overlay");
        AssertFailAndContinue(myAccountPageActions.clickEditLinkAddress(), "Click on edit link in shipping address");
        myAccountPageActions.checkPrePopulatedAdd(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("city"), shipDetailUS.get("phNum"), shipDetailUS.get("zip"));
        AssertFailAndContinue(myAccountPageActions.validateMoreThan50ErrMsg(splCharForMoreThan.get(0),splCharForMoreThan.get(1),splCharForMoreThan.get(2),splCharForMoreThan.get(4),splCharForMoreThan.get(5)),"Check the maximum character error message in the address book fields");
        AssertFailAndContinue(myAccountPageActions.clickCancelButton(), "Click on cancel button and check user navigates to back");

        AssertFailAndContinue(myAccountPageActions.editShippingAddress(editShipDetailUS.get("fName"), editShipDetailUS.get("lName"), editShipDetailUS.get("addressLine1"), editShipDetailUS.get("addressLine2"), editShipDetailUS.get("city"),
                editShipDetailUS.get("country"), editShipDetailUS.get("stateShortName"), editShipDetailUS.get("zip"), editShipDetailUS.get("phNum")), "Entering all details in Add a New Address Overlay");
        AssertFailAndContinue(myAccountPageActions.deleteAddressModal(header), "Verify the content in delete address modal");
    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION, REGISTEREDONLY, USONLY, PROD_REGRESSION})
    public void addAndEditYouEnteredShippingAddress(@Optional("US") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if register user can validate the error message in shipping address fields" +
                "DT-43802");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "testShippingDetailsUS");
        Map<String, String> editShipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "editedShippingAddressUS");

        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.addYouEnteredShippingAddress(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum")), "Entering all details in Add a New Address Overlay");
        myAccountPageActions.click(myAccountPageActions.editDefShipAddrLink);
        AssertFailAndContinue(myAccountPageActions.editYouEnteredShippingAddress(editShipDetailUS.get("fName"), editShipDetailUS.get("lName"), editShipDetailUS.get("addressLine1"), editShipDetailUS.get("addressLine2"), editShipDetailUS.get("city"),
                editShipDetailUS.get("country"), editShipDetailUS.get("stateShortName"), editShipDetailUS.get("zip"), editShipDetailUS.get("phNum")), "Entering all details in Add a New Address Overlay");
    }

    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION, REGISTEREDONLY, USONLY, PROD_REGRESSION})
    public void variousShippingAddress(@Optional("US") String store) throws Exception {
        setAuthorInfo("Srijith");
        setRequirementCoverage(store + " store - Verify the shipping methods are getting reflected properly for the saved addresses" +
                "DT-43802" +"DT-44902" +"DT-44904");

        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsAKHIPRUS");
        Map<String, String> shipDetailUSP = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsPOBUS");
        Map<String, String> billDetails = excelReaderDT2.getExcelData("BillingDetails", "MasterCard");
        if(env.equalsIgnoreCase("prod")){
            billDetails = excelReaderDT2.getExcelData("BillingDetails", "VISA_PROD_2");
        }

        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        addDefaultShipMethod(shipDetailUSP.get("fName"), shipDetailUSP.get("lName"), shipDetailUSP.get("addressLine1"), shipDetailUSP.get("addressLine2"), shipDetailUSP.get("city"),
                shipDetailUSP.get("country"), shipDetailUSP.get("stateShortName"), shipDetailUSP.get("zip"), shipDetailUSP.get("phNum"));

        addToBagBySearching(searchKeywordAndQty);
        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);

        AssertFailAndContinue(shippingPageActions.validatePriorityShippingMethods(shoppingBagPageActions), "Verify the Shipping methods for the Address");
        //shippingPageActions.returnToBag(shoppingBagPageActions);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        //Adding default Address and Default Shipping method
        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));

        addToBagBySearching(searchKeywordAndQty);
        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        AssertFailAndContinue(shippingPageActions.validateSplShippingMethods(shoppingBagPageActions), "Verify the Shipping methods for the Address");
        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        shippingPageActions.clickNextBillingButton(billingPageActions);
        AssertFailAndContinue(billingPageActions.enterPaymentAndAddressDetails_GuestAndRegUser(reviewPageActions, billDetails.get("fName"), billDetails.get("lName"), billDetails.get("addressLine1"), billDetails.get("addressLine2"), billDetails.get("city"), billDetails.get("stateShortName"), billDetails.get("zip"), billDetails.get("cardNumber"), billDetails.get("securityCode"), billDetails.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        if(!env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions), "Clicked on the Submit Order button in the Order Review section.");
            AssertFailAndContinue(!orderConfirmationPageActions.isFreeShippingApplied(), "verifying free shipping is not displayed to the user");
            getAndVerifyOrderNumber("variousShippingAddress");
        }

    }
    //Working on it
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION, REGISTEREDONLY})
    public void removeDefaultAddCheck(@Optional("US") String store) throws Exception {
        setAuthorInfo("Srijith");
        setRequirementCoverage(store + " store - Verify the shipping methods are getting reflected properly for the saved addresses" +
                "DT-43804" +"DT-44898"+"DT-44901"+"DT-44572");
        Map<String, String> shipDetailUSP = null;
        Map<String, String> shipMet = null;
        Map<String, String> shipDetailUS = null;
        if(store.equalsIgnoreCase("US")){
            shipDetailUSP  = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsPOBUS");
            shipMet = excelReaderDT2.getExcelData("ShippingMethodsCodes","priority");
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsAKHIPRUS");
        }
        if(store.equalsIgnoreCase("CA")) {
            shipDetailUSP = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
            shipMet = excelReaderDT2.getExcelData("ShippingMethodsCodes","ground");
            shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "editedShippingAddressCA");

        }
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);

        addDefaultShipMethod(shipDetailUSP.get("fName"), shipDetailUSP.get("lName"), shipDetailUSP.get("addressLine1"), shipDetailUSP.get("addressLine2"), shipDetailUSP.get("city"),
                shipDetailUSP.get("country"), shipDetailUSP.get("stateShortName"), shipDetailUSP.get("zip"), shipDetailUSP.get("phNum"));

        addDefaultShipMethod(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                shipDetailUS.get("country"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"));
        addToBagBySearching(searchKeywordAndQty);
        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        //AssertFailAndContinue(shippingPageActions.validatePriorityShippingMethods(shoppingBagPageActions), "Verify the Shipping methods for the Address");
        shippingPageActions.defaultAddressVerification(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                 shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"),shipMet.get("shipMethod"));
        shippingPageActions.returnToBag(shoppingBagPageActions);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");

        myAccountPageActions.clickProfileInfoLink();
        myAccountPageActions.click(myAccountPageActions.lnk_AddressBook);
        myAccountPageActions.validateHrefLangTag();
        AssertFailAndContinue(myAccountPageActions.checkTheAddressDisplayInMailingSection(shipDetailUS.get("addressLine1")),"Verify the shipping address displayed in the Mailing address dropdown");
        myAccountPageActions.click(myAccountPageActions.cancelInfoBtn);
        AssertFailAndContinue(myAccountPageActions.deleteDefaultAddressFromMultipleAddress(),"Remove the current default address");
        AssertFailAndContinue(myAccountPageActions.deleteDefaultAddressFromMultipleAddress(),"Remove the second default address");
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        shippingPageActions.isDisplayed(shippingPageActions.firstNameFld);
        shippingPageActions.returnToBag(shoppingBagPageActions);
    }
}
