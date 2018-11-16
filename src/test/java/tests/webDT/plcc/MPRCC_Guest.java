package tests.webDT.plcc;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by skonda on 12/5/2017.
 */
public class MPRCC_Guest extends BaseTest {
    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    private String password;

    @Parameters({storeXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        headerMenuActions.deleteAllCookies();
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcel, "Password");
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {WIC, USONLY, GUESTONLY,PROD_REGRESSION})
    public void plccFieldValidation(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Validate as the guest user can validate the PLCC landing page from the footer ");
        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        AssertFailAndContinue(footerMPRCreditCardActions.validateUpdatedReqards(getDT2TestingCellValueBySheetRowAndColumn("PLCC", "termsAndondition", "content")), "Verify updated terms and conditions in reward terms");
        AssertFailAndContinue(footerMPRCreditCardActions.validatePlaceCard(), "Validate the links and buttons present in the place card page");
        AssertFailAndContinue(footerMPRCreditCardActions.clickApplyToday(), "Click on apply today button");
        AssertFailAndContinue(footerMPRCreditCardActions.validateLegalCopy(),"Verify legal copy is displayed in bullet points in mprcc apply form page");
        AssertFailAndContinue(footerMPRCreditCardActions.validatePromoAndDisclosureStatement(getDT2TestingCellValueBySheetRowAndColumn("PLCC", "validPlccApproveDetails", "content")), "Validated the Promo image and disclosure text content");
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {WIC, USONLY, GUESTONLY,PROD_REGRESSION})
    public void addCardInPLCCFromFooterImg_Guest(@Optional("US") String store, @Optional("guest") String user) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Validate as the guest user can navigate to PLCC landing page from the footer and enter the valid values and also get the PLCC approved message");
        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        String coptText = getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "PLCCPrivacyPage", "linkName");
        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        AssertFailAndContinue(footerMPRCreditCardActions.clickApplyToday(), "Click on apply today button");
        footerMPRCreditCardActions.compareRTPSText(coptText);
        AssertFailAndContinue(footerMPRCreditCardActions.enterApplicationDetails_Approve(plccDetailsUS.get("fName"), plccDetailsUS.get("lName"), plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), emailAddressReg, plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn")), "Enter valid application details and click on submit button");
        AssertFailAndContinue(footerMPRCreditCardActions.validateApprovedPage_Guest(), "Validate the links present in the approved page");
        footerMPRCreditCardActions.clickCreateAccAndValidateFields(createAccountActions);
        createAccountActions.closeHeaderOverlay(footerMPRCreditCardActions);
        footerMPRCreditCardActions.clickLoginAndValidateFields(loginDrawerActions);
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {WIC, USONLY, GUESTONLY,PROD_REGRESSION})
    public void addPLCCAndCreateAcc(@Optional("US") String store, @Optional("guest") String user) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Validate as the guest user can navigate to PLCC landing page from the footer and enter the valid values and also get the PLCC approved message");
        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", "CreateAccount");

        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        AssertFailAndContinue(footerMPRCreditCardActions.clickApplyToday(), "Click on apply today button");
        AssertFailAndContinue(footerMPRCreditCardActions.enterApplicationDetails_Approve(plccDetailsUS.get("fName"), plccDetailsUS.get("lName"), plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), plccDetailsUS.get("emailAddress"), plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn")), "Enter valid application details and click on submit button");
        AssertFailAndContinue(footerMPRCreditCardActions.validateApprovedPage_Guest(), "Validate the links present in the approved page");
        footerMPRCreditCardActions.clickCreateAccAndValidateFields(createAccountActions);
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {WIC, USONLY, GUESTONLY,PROD_REGRESSION})
    public void validateErrMessages_PLCC(@Optional("US") String store, @Optional("guest") String user) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Validate as the guest user can navigate to PLCC landing page from the footer and check the error messages");
        List<String> errMsgTab = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("PLCC", "errMsgValidation", "validErrorMsg"));

        driver.navigate().refresh();
        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        AssertFailAndContinue(footerMPRCreditCardActions.clickApplyToday(), "Click on apply today button");
        AssertFailAndContinue(footerMPRCreditCardActions.validateErrorMsg(errMsgTab.get(0), errMsgTab.get(1), errMsgTab.get(2), errMsgTab.get(3), errMsgTab.get(4),
                errMsgTab.get(5), errMsgTab.get(6), errMsgTab.get(7), errMsgTab.get(8), errMsgTab.get(9), errMsgTab.get(10)), "Validate the error message when tab the fields");
    }

    @Test(dataProvider = dataProviderName, priority = 4, groups = {WIC, USONLY, GUESTONLY,PROD_REGRESSION})
    public void plccDecline(@Optional("US") String store, @Optional("guest") String user) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Validate as the guest user can navigate to PLCC landing page from the footer and enter the valid values and also get the PLCC approved message");
        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "plccDeclineDetails");

        driver.navigate().refresh();
        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        AssertFailAndContinue(footerMPRCreditCardActions.clickApplyToday(), "Click on apply today button");
        AssertFailAndContinue(footerMPRCreditCardActions.enterApplicationDetails_Decline(plccDetailsUS.get("fName"), plccDetailsUS.get("lName"), plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), plccDetailsUS.get("emailAddress"), plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn")), "Enter valid application details and click on submit button");
    }

    @Test(dataProvider = dataProviderName, priority = 5, groups = {WIC, USONLY, REGISTEREDONLY,PROD_REGRESSION})
    public void addCardInPLCCFromMyAcc_Reg(@Optional("US") String store, @Optional("registered") String user) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Validate as the register user can navigate to PLCC landing page on clicking the MPR Espot from the My account page and enter the valid values and also get the PLCC approved message");
        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");

        String emailAddress = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcel);
//        clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddress, pwd);
        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions), "Clicked on the View My Account link in the My Account Drawer and landed on the My Account page");
        AssertFail(myAccountPageActions.clickMPREspot(mprOverlayActions), "Click on MPR Espot from my account page");
        AssertFailAndContinue(mprOverlayActions.clickApplyToday(), "Click on apply today button");
        AssertFailAndContinue(mprOverlayActions.enterApplicationDetails_Reg(plccDetailsUS.get("fName"), plccDetailsUS.get("lName"), plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), emailAddressReg, plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn")), "Enter valid application details and click on submit button");

    }

    @Test(dataProvider = dataProviderName, priority = 6, groups = {WIC, USONLY, GUESTONLY})
    public void addPLCCAndLogin(@Optional("US") String store, @Optional("guest") String user) throws Exception {

        setAuthorInfo("Azeez");
        setRequirementCoverage("Validate as the guest user can navigate to PLCC landing page from the footer and enter the valid values and also get the PLCC approved message");
        Map<String, String> plccDetailsUS = excelReaderDT2.getExcelData("PLCC", "validPlccApproveDetails");

        AssertFailAndContinue(footerActions.clickPLCCImageFromFooter(footerMPRCreditCardActions), "Click on apply now link from the footer and check user navigate to PLCC landing page");
        AssertFailAndContinue(footerMPRCreditCardActions.clickApplyToday(), "Click on apply today button");
        AssertFailAndContinue(footerMPRCreditCardActions.enterApplicationDetails_Approve(plccDetailsUS.get("fName"), plccDetailsUS.get("lName"), plccDetailsUS.get("addressLine1"), plccDetailsUS.get("addressLine2"),
                plccDetailsUS.get("city"), plccDetailsUS.get("stateShortName"), plccDetailsUS.get("zip"), plccDetailsUS.get("phNum"), emailAddressReg, plccDetailsUS.get("alternatePhone"),
                plccDetailsUS.get("birthMonth"), plccDetailsUS.get("birthDate"), plccDetailsUS.get("birthYear"), plccDetailsUS.get("ssn")), "Enter valid application details and click on submit button");
        AssertFailAndContinue(footerMPRCreditCardActions.validateApprovedPage_Guest(), "Validate the links present in the approved page");
        footerMPRCreditCardActions.clickLoginAndValidateFields(loginDrawerActions);
        AssertFailAndContinue(loginDrawerActions.successfulLogin(emailAddressReg, password, headerMenuActions), "Login with valid credentials " + emailAddressReg);
//        AssertFailAndContinue(myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions,myAccountPageActions),"Click on view my account link and navigate to my account page");
//        myAccountPageActions.click(myAccountPageActions.lnk_PaymentGC);
//        myAccountPageActions.staticWait(3000);
        AssertFailAndContinue(myAccountPageActions.waitUntilElementDisplayed(myAccountPageActions.defaultPaymentMethodLbl), "The PLCC card is stored at my acoount by default");
    }
}
