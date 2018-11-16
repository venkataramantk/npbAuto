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
 * Created by AbdulazeezM on 8/2/2017.
 */
public class AddressBook_CA extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    private String password;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("CA") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
//        driver.get(EnvironmentConfig.getCA_ApplicationUrl());
        headerMenuActions.deleteAllCookies();
        footerActions.changeCountryAndLanguage("CA","English");
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcel, "Password");
   //     headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("CA") String store) throws Exception {
//        driver.get(EnvironmentConfig.getCA_ApplicationUrl());
      //  driver.manage().window().maximize();
       // driver.manage().deleteAllCookies();
        headerMenuActions.deleteAllCookies();//to delete privacy policy cookie
        footerActions.changeCountryAndLanguage("CA","English");
        }
    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,REGISTEREDONLY,CAONLY,PROD_REGRESSION})
    public void addingShippingAddressAndValidateFields(@Optional("CA") String store) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage(store+" store - Verify if register user can add the default shipping address in my account page and validate the fields present in the address book section");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsUS");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
         AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickAddressLink(), "Click on address link and check user navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddNewAddressBook(), "Click on add new address button and check user displayed with the address fields");
        AssertFailAndContinue(myAccountPageActions.validateFieldsInAddress(),"validate the fields present in the shipping address page");
        AssertFailAndContinue(myAccountPageActions.clickAddressBookBreadcrumb(), "Click on addresss book breadcrumb and check user navigate back");
        AssertFailAndContinue(myAccountPageActions.addNewShippingAddress(shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"), shipDetailCA.get("addressLine2"), shipDetailCA.get("city"),
                shipDetailCA.get("country"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum")), "Entering all details in Add a New Address Overlay");
        myAccountPageActions.click(myAccountPageActions.lnk_AccountOverView);
        myAccountPageActions.updateShipAdd();
        AssertFailAndContinue(myAccountPageActions.addNewDiffCountryShippingAdd(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("country"),shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"),
                 shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum")), "Entering all details in Add a New Address Overlay");
        myAccountPageActions.click(myAccountPageActions.lnk_AccountOverView);
        myAccountPageActions.updateShipAdd();

    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,REGISTEREDONLY,CAONLY,PROD_REGRESSION})
    public void validateAddressFieldErrMsg(@Optional("CA") String store) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage(store+" store - Verify if register user can validate the error message in shipping address fields");
        List<String> errMsgTab = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "addressFieldErrTab", "FieldErrorMsg_CA"));
        List<String> errMsgSplChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "addressFieldErrSplChar", "FieldLevelErrorMsg"));
        List<String> errMsgMaxChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount","addressFieldMaxCarErr","FieldLevelErrorMsg"));

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickAddressLink(), "Click on address link and check user navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddNewAddressBook(), "Click on add new address button and check user displayed with the address fields");
        AssertFailAndContinue(myAccountPageActions.validateAddressErrMsgTab(errMsgTab.get(0), errMsgTab.get(1), errMsgTab.get(2), errMsgTab.get(3), errMsgTab.get(4), errMsgTab.get(5), errMsgTab.get(6)), "Validate the error message when tab the address field");
        AssertFailAndContinue(myAccountPageActions.validateAddressSplCharErrMsg(errMsgSplChar.get(0), errMsgSplChar.get(1), errMsgSplChar.get(2), errMsgSplChar.get(2), errMsgSplChar.get(3), errMsgSplChar.get(4), errMsgSplChar.get(5)), "Validate the error message when tab the address field");
        AssertFailAndContinue(myAccountPageActions.validateMaxCharError(errMsgMaxChar.get(0),errMsgMaxChar.get(1)),"Verify the max character error message is displayed to the user");

    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,REGISTEREDONLY,CAONLY,PROD_REGRESSION})
    public void editShippingAddress(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store+" store - Verify if register user can validate the error message in shipping address fields");
        Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> editShipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "editedShippingAddressCA");
        String header = getDT2TestingCellValueBySheetRowAndColumn("MyAccount","deleteAddress","TermsContent");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickAddressLink(), "Click on address link and check user navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddNewAddressBook(), "Click on add new address button and check user displayed with the address fields");
        AssertFailAndContinue(myAccountPageActions.clickAddressBookBreadcrumb(), "Click on address book breadcrumb and check user navigate back");
        AssertFailAndContinue(myAccountPageActions.addNewShippingAddress(shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"), shipDetailCA.get("addressLine2"), shipDetailCA.get("city"),
                shipDetailCA.get("country"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum")), "Entering all details in Add a New Address Overlay");
        AssertFailAndContinue(myAccountPageActions.clickEditLinkAddress(),"Click on edit link in shipping address");
        AssertFailAndContinue(myAccountPageActions.clickCancelButton(),"Click on cancel button and check user navigates to back");
        AssertFailAndContinue(myAccountPageActions.editShippingAddress(editShipDetailCA.get("fName"), editShipDetailCA.get("lName"), editShipDetailCA.get("addressLine1"), editShipDetailCA.get("addressLine2"), editShipDetailCA.get("city"),
                editShipDetailCA.get("country"), editShipDetailCA.get("stateShortName"), editShipDetailCA.get("zip"), editShipDetailCA.get("phNum")), "Entering all details in Add a New Address Overlay");
        AssertFailAndContinue(myAccountPageActions.deleteAddressModal(header),"Verify the content in delete address modal");

    }
    @Parameters(storeXml)
    @Test(groups = {MYACCOUNT, REGRESSION,REGISTEREDONLY,CAONLY,PROD_REGRESSION})
    public void variousShippingAddress(@Optional("CA") String store) throws Exception{
        setAuthorInfo("Srijith");
        setRequirementCoverage(store+" store - Verify the shipping methods are getting reflected properly for the saved addresses");

       Map<String, String> shipDetailCA = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
       String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search","SearchBy","Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

            AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
            AssertFailAndContinue(myAccountPageActions.addNewShippingAddress(shipDetailCA.get("fName"), shipDetailCA.get("lName"), shipDetailCA.get("addressLine1"), shipDetailCA.get("addressLine2"), shipDetailCA.get("city"),
                    shipDetailCA.get("country"), shipDetailCA.get("stateShortName"), shipDetailCA.get("zip"), shipDetailCA.get("phNum")), "Entering all details in Add a New Address Overlay");

        addToBagBySearching(searchKeywordAndQty);
        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions,reviewPageActions);
        AssertFailAndContinue(shippingPageActions.validateCAShippingMethods(),"Verify the Shipping methods for the Address");

    }
}
