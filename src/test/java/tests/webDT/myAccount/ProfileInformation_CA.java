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
public class ProfileInformation_CA extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountCA";
    String emailAddressReg;
    private String password;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("CA") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
//        driver.get(EnvironmentConfig.getCA_ApplicationUrl());
        headerMenuActions.deleteAllCookies();//to delete privacy policy cookie
        footerActions.changeCountryAndLanguage("CA", "English");
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcel, "Password");
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("CA") String store) throws Exception {
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
        } else if (store.equalsIgnoreCase("CA")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            footerActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        driver.manage().deleteAllCookies();
        //headerMenuActions.deleteAllCookies();
    }

    @Test(enabled = false)//Added the step in validateErrMsg
    public void validatePersonalInfoPage(@Optional("CA") String store, @Optional("registered") String user) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if register user can validate the contents present in the personal information link and check user can change the password");
        List<String> changePwd = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "validPassword", "Password"));

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickProfileInfoLink(), "Click on profile information link and check user navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.validateProfileInfoTabContents(), "Navigate to profile information tab in my account page and validate the contents present in the corresponding page");
        AssertFailAndContinue(myAccountPageActions.changePassword(password, changePwd.get(0), changePwd.get(0)), "Change the existing password");
    }

    @Parameters(storeXml)
    @Test(priority = 7, groups = {MYACCOUNT, REGRESSION, CAONLY, REGISTEREDONLY,PROD_REGRESSION})
    public void validateErrMsg(@Optional("CA") String store) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if register user can validate the error messages in password changing field in all possibilities");
        List<String> errMsgTab = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "passwordErrMsg", "FieldLevelErrorMsg"));
        List<String> errMsgSplChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "passwordErrMsgSplChar", "FieldLevelErrorMsg"));
        List<String> validPassword = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "validPassword", "Password"));
        List<String> changePwd = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "validPassword", "Password"));

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickProfileInfoLink(), "Click on profile information link and check user navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickChangePwdBtn(), "Click on change password button and check the user displayed with the appropriate fields");
        AssertFailAndContinue(myAccountPageActions.isDisplayed(myAccountPageActions.cancelInfoBtn) && myAccountPageActions.isDisplayed(myAccountPageActions.saveButton), "Cancel and Save Buttons displaying for Change password");

        AssertFailAndContinue(myAccountPageActions.validateErrorMessagesTab(errMsgTab.get(0), errMsgTab.get(0), errMsgTab.get(1)), "Validate the error message when tab the field");
        AssertFailAndContinue(myAccountPageActions.validateErrMsgSplChar(errMsgSplChar.get(0), errMsgSplChar.get(1)), "Validate the error message when special characters are entered");
        AssertFailAndContinue(myAccountPageActions.showHideLinkCurrentPassword(password), "Validated the actions of the Show/Hide link");
        AssertFailAndContinue(myAccountPageActions.showHideLinkNewPassword(validPassword.get(0)), "Validated the actions of the Show/Hide link");
        AssertFailAndContinue(myAccountPageActions.showHideLinkConfirmPassword(validPassword.get(0)), "Validated the actions of the Show/Hide link");
        AssertFailAndContinue(myAccountPageActions.passwordMisMatchErr(password, validPassword.get(0), validPassword.get(1), errMsgTab.get(2)), "Verify the error message when passwords are mismatched");
        AssertFailAndContinue(myAccountPageActions.enterSamePwdAndValidateErrMsg(password, password, password, errMsgTab.get(3)), "Enter the same password for all fields and check the error message");

        AssertFailAndContinue(myAccountPageActions.changePassword(password, changePwd.get(0), changePwd.get(0)), "Change the existing password");

    }

    @Parameters(storeXml)
    @Test(priority = 5, groups = {MYACCOUNT, REGRESSION, CAONLY, REGISTEREDONLY,PROD_REGRESSION})
    public void editProfileInformation(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the register user can change the personal profile information in my account page" +
                "DT-44574" +"DT-44575");
        Map<String, String> acct = excelReaderDT2.getExcelData("MyAccount", "editPersonalInfo");
        List<String> profileErrTab = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "profileInfoTabErr", "FieldLevelErrorMsg"));
        List<String> profileErrSpl = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount", "profileInfoSplCharErr", "FieldLevelErrorMsg"));
        List<String> validAssociate = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("MyAccount","InValidAssociateID","Associate ID"));
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        headerMenuActions.staticWait(4000);
        AssertFailAndContinue(myAccountPageActions.invalidAssociateIDErr(validAssociate.get(0),profileErrSpl.get(4),shipDetailUS.get("addressLine1"),shipDetailUS.get("city"), shipDetailUS.get("stateShortName"),shipDetailUS.get("zip")),"Enter invalid associate ID and check the error message");
        headerMenuActions.staticWait(5000);
        myAccountPageActions.click(myAccountPageActions.cancelPerInfoButton);
        AssertFailAndContinue(myAccountPageActions.editProfileInfo(acct.get("FirstName"), acct.get("LastName"), acct.get("MobileNo")), "Click on profile info edit link and check user displayed with appropriate fields");
        AssertFailAndContinue(myAccountPageActions.validateProfileInfoTabErrMsg(profileErrTab.get(0), profileErrTab.get(1), profileErrTab.get(2), profileErrTab.get(3), profileErrTab.get(4),profileErrTab.get(5),profileErrTab.get(6),profileErrTab.get(7)), "Validate the error message when tab the filed");
        AssertFailAndContinue(myAccountPageActions.validateProfileInfoSplCharErrMsg(profileErrSpl.get(0), profileErrSpl.get(1), profileErrSpl.get(2), profileErrSpl.get(3), profileErrTab.get(4),profileErrSpl.get(5),profileErrSpl.get(6),profileErrSpl.get(7)), "Validate the error message when special characters are entered");
        myAccountPageActions.click(myAccountPageActions.cancelPerInfoButton);
        myAccountPageActions.editProfileInfo("qwertyuipasdas","vermaq","9867556789");
        myAccountPageActions.truncatedNameDisplay("qwertyuipasdas vermaq","qwertyui...");
    }

    @Parameters(storeXml)
    @Test(priority = 1, groups = {MYACCOUNT, REGRESSION, CAONLY, REGISTEREDONLY,PROD_REGRESSION})
    public void validateBirthdaySavingsFields(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the register user validate the birthday savings in the personal profile information in my account page");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickProfileInfoLink(), "Click on profile information link and check user navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddBirthdayInfo(), "Click on add birthday button and navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddChildByPosition(1), "Click on add a child button by its position and check the fields are getting displayed");
        AssertFailAndContinue(myAccountPageActions.validateBirthdayFields(), "Validate the adding birthday fields are getting displayed");
        AssertFailAndContinue(myAccountPageActions.clickBirthCancelButton(), "Click on birth cancel button");
        myAccountPageActions.clickProfileInfoLink();
        myAccountPageActions.clickOnPersonalInfoEditButtonCA();
        AssertFailAndContinue(myAccountPageActions.enterAirmilesNumber(), "Verify the error message and Airmiles number added to the account");
    }

    @Parameters(storeXml)
    @Test(priority = 2, groups = {MYACCOUNT, REGRESSION, CAONLY, REGISTEREDONLY,PROD_REGRESSION})
    public void validateBirthdaySavingsErrMsg(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the register user validate all possible error message in the birthday savings fields in my account page");
        List<String> errMsgTab = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Birthday", "tabErrMsg", "validErrMsgContent"));
        List<String> errMsgSplChar = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Birthday", "splCharErrMsg", "validErrMsgContent"));

        clickLoginAndLoginAsRegUser(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickProfileInfoLink(), "Click on profile information link and check user navigate to appropriate page");

        AssertFailAndContinue(myAccountPageActions.clickAddBirthdayInfo(), "Click on add birthday button and navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddChildByPosition(1), "Click on add a child button by its position and check the fields are getting displayed");
        AssertFailAndContinue(myAccountPageActions.validateBirthdayFieldErrTab(errMsgTab.get(0), errMsgTab.get(1), errMsgTab.get(2), errMsgTab.get(3), errMsgTab.get(4), errMsgTab.get(5), errMsgTab.get(6)), "Validate the error emssage when tab the field");
        AssertFailAndContinue(myAccountPageActions.validateBirthdayFieldSplCharErr(errMsgSplChar.get(0), errMsgSplChar.get(1), errMsgSplChar.get(2)), "Validate the special character error message");
    }

    @Parameters(storeXml)
    @Test(priority = 4, groups = {MYACCOUNT, REGRESSION, CAONLY, REGISTEREDONLY,PROD_REGRESSION})
    public void addBirthdaySavings(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store -Verify if the register user validate all possible error message in the birthday savings fields in my account page");
        Map<String, String> acct = excelReaderDT2.getExcelData("Birthday", "validChildDetailsUS");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickProfileInfoLink(), "Click on profile information link and check user navigate to appropriate page");

        AssertFailAndContinue(myAccountPageActions.clickAddBirthdayInfo(), "Click on add birthday button and navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddChildButton(), "Click on add a child button by its position and check the fields are getting displayed");
        AssertFailAndContinue(myAccountPageActions.addOneChildBirthdaySaving(acct.get("childName"), acct.get("birthMonth"), acct.get("birthYear"), acct.get("gender"), acct.get("fName"), acct.get("lName")), "Add the birthday savings details for one child");

        myAccountPageActions.clickOnProfileInfoTab();
        AssertFailAndContinue(myAccountPageActions.validateFieldsInProfInfoAfterAdded(), "Validate the fields present in the profile information page without adding the birthday information");
    }

    @Parameters(storeXml)
    @Test(priority = 3, groups = {MYACCOUNT, REGRESSION, CAONLY, REGISTEREDONLY,PROD_REGRESSION})
    public void addAndDeleteChildBirthdaySavings(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if the register user can add the all the child birthday savings and deleted the same");
        Map<String, String> acct = excelReaderDT2.getExcelData("Birthday", "validChildDetailsUS");
        Map<String, String> acct1 = excelReaderDT2.getExcelData("Birthday", "validSecondChildInfo");
        Map<String, String> acct2 = excelReaderDT2.getExcelData("Birthday", "validThirdChildInfo");
        Map<String, String> acct3 = excelReaderDT2.getExcelData("Birthday", "validFourthChildInfo");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);

        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickProfileInfoLink(), "Click on profile information link and check user navigate to appropriate page");

        AssertFailAndContinue(myAccountPageActions.clickAddBirthdayInfo(), "Click on add birthday button and navigate to appropriate page");
        AssertFailAndContinue(myAccountPageActions.clickAddChildButton(), "Click on add a child button by its position and check the fields are getting displayed");
        AssertFailAndContinue(myAccountPageActions.addOneChildBirthdaySaving(acct.get("childName"), acct.get("birthMonth"), acct.get("birthYear"), acct.get("gender"), acct.get("fName"), acct.get("lName")), "Add the birthday savings details for one child");

        AssertFailAndContinue(myAccountPageActions.clickAddChildButton(), "Click on add a child button by its position and check the fields are getting displayed");
        AssertFailAndContinue(myAccountPageActions.addOneChildBirthdaySaving(acct1.get("childName"), acct1.get("birthMonth"), acct1.get("birthYear"), acct1.get("gender"), acct1.get("fName"), acct1.get("lName")), "Add the birthday savings details for one child");

        AssertFailAndContinue(myAccountPageActions.clickAddChildButton(), "Click on add a child button by its position and check the fields are getting displayed");
        AssertFailAndContinue(myAccountPageActions.addOneChildBirthdaySaving(acct2.get("childName"), acct2.get("birthMonth"), acct2.get("birthYear"), acct2.get("gender"), acct2.get("fName"), acct2.get("lName")), "Add the birthday savings details for one child");

        AssertFailAndContinue(myAccountPageActions.clickAddChildButton(), "Click on add a child button by its position and check the fields are getting displayed");
        AssertFailAndContinue(myAccountPageActions.addOneChildBirthdaySaving(acct3.get("childName"), acct3.get("birthMonth"), acct3.get("birthYear"), acct3.get("gender"), acct3.get("fName"), acct3.get("lName")), "Add the birthday savings details for one child");

        AssertFailAndContinue(myAccountPageActions.clickRemoveByPosition(1), "Click on remove icon and check the added child birthday savings is getting removed");
        AssertFailAndContinue(myAccountPageActions.clickRemoveByPosition(1), "Click on remove icon and check the added child birthday savings is getting removed");
    }

    @Parameters(storeXml)
    @Test(priority = 6, groups = {MYACCOUNT, REGRESSION, CAONLY, REGISTEREDONLY,PROD_REGRESSION})
    public void addAssociateIDAndPlaceOrder(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage(store + " store - Verify if register user can add the associate ID in personal information page and check the promotion added in the checkout page");
        Map<String, String> shipDetailUS = excelReaderDT2.getExcelData("ShippingDetails", "validShippingDetailsCA");
        Map<String, String> billDetailUS = excelReaderDT2.getExcelData("BillingDetails", "validBillingDetailsCA");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "Search3Prod", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "Search3Prod", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);
        Map<String, String> sm = excelReaderDT2.getExcelData("ShippingMethodCodes", "express_CA");
        String freeShip = getDT2TestingCellValueBySheetRowAndColumn("Coupons", "FreeShipping", "couponCodeCA");

        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFailAndContinue(myAccountPageActions.clickProfileInfoLink(), "Click on profile information link and check user navigate to appropriate page");
        myAccountPageActions.clickOnPersonalInfoEditButtonCA();
        AssertFailAndContinue(myAccountPageActions.addAssociateID("310620",shipDetailUS.get("addressLine1"),shipDetailUS.get("city"), shipDetailUS.get("stateShortName")), "Enter the valid associate ID");
        addToBagBySearching(searchKeywordAndQty);
        shoppingBagPageActions.validateTotalAfterAppliedAssociateID();
        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        AssertFailAndContinue(shippingPageActions.enterShippingAddressByShipMethodPos_Reg(shipDetailUS.get("fName"), shipDetailUS.get("lName"), shipDetailUS.get("addressLine1"), shipDetailUS.get("addressLine2"), shipDetailUS.get("city"), shipDetailUS.get("stateShortName"), shipDetailUS.get("zip"), shipDetailUS.get("phNum"), billingPageActions), "Entered the Shipping address and clicked on the Next Billing button.");
        billingPageActions.returnToBagPage(shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.applyCouponCode(freeShip), "Free shipping is applied");
        headerMenuActions.navigateToShoppingBagPageFromHeader(shoppingBagDrawerActions, shoppingBagPageActions);
        shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions);
        shippingPageActions.clickNextBillingButton(billingPageActions);
        AssertFailAndContinue(billingPageActions.enterPaymentDetailsAsGuestAndReg_SameAsShip(reviewPageActions, billDetailUS.get("cardNumber"), billDetailUS.get("securityCode"), billDetailUS.get("expMonthValueUnit")), "Entered the Payment details and the Billing Address and clicked on the Next Review button.");
        AssertFailAndContinue(reviewPageActions.clickOnShippingAccordion(shippingPageActions), "Click on return to Shipping link and check user navigate to shipping page section");
        shippingPageActions.clickNextBillingButton(billingPageActions);
        AssertFailAndContinue(billingPageActions.enterCvvAndClickNextReviewButton(reviewPageActions, billDetailUS.get("securityCode")), "Enter CVV and click on next Review button");
        reviewPageActions.clickSubmOrderButton(orderConfirmationPageActions);
        getAndVerifyOrderNumber("addAssociateIDAndPlaceOrder");
    }
}
