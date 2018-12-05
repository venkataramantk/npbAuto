package tests.webDT.globalComponents.createAccountDrawer;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by Venkat on 2/17/2017.
 */

public class CreateAccount extends BaseTest {

    WebDriver driver;
    ExcelReader excelReaderDT2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    String emailAddressReg;
    private String rowInExcel = "CreateAccountUS";
    private String password;
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(/*@Optional("US") String store, @Optional("guest") String user*/) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        env = EnvironmentConfig.getEnvironmentProfile();
        driver.get(EnvironmentConfig.getApplicationUrl());
//        if (store.equalsIgnoreCase("US")) {
//            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
//            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
//
//        } else if (store.equalsIgnoreCase("CA")) {
//            headerMenuActions.deleteAllCookies();
//            footerActions.changeCountryAndLanguage("CA", "English");
//            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
//            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
//        }
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser() throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        driver.navigate().refresh();
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void createNewAccount() {

        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that guest user in store, in US Store is able to create an account by clicking on 'Create Account' link from header.");
        Map<String, String> acct = excelReaderDT2.getExcelData("CreateAccount", rowInExcel);
//        createNewAccountFromHeader(acct.get("FirstName"), acct.get("LastName"), acct.get("Password"), acct.get("ZipCode"), acct.get("PhoneNumber"), acct.get("SuccessMessage"));
//        clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
    }

    @Parameters(storeXml)
    @Test(groups = {GLOBALCOMPONENT, REGRESSION, GUESTONLY, PROD_REGRESSION})
    public void createAccFieldsValidate(@Optional("US") String store) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify that guest user in " + store + " store," + " is able to validate the contents of the Create Account drawer." +
                " DT-43790");
        String tooltipContent = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "tooltipContent", "ToolTipContent");
        if (store.equalsIgnoreCase("US")) {
            List<String> txtContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "messageOnForm", "Content"));
            Map<String, String> termsTxt = excelReaderDT2.getExcelData("CreateAccount", "messageOnForm");
            String earnPtsMsg = txtContent.get(0), createMPRAcctMsg = txtContent.get(1), termsTxtMsg = termsTxt.get("TermsTextUS"), remMeMsg = txtContent.get(2), remMeSubTxtMsg = txtContent.get(3);

        }
        if (store.equalsIgnoreCase("CA")) {
            List<String> txtContent = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", "messageOnForm", "ContentCA"));
            Map<String, String> termsTxt = excelReaderDT2.getExcelData("CreateAccount", "messageOnForm");
            String earnPtsMsg1 = txtContent.get(0), createMPRAcctMsg1 = txtContent.get(1), termsTxtMsg1 = termsTxt.get("TermsTextCA"), remMeMsg1 = txtContent.get(2), remMeSubTxtMsg1 = txtContent.get(3);
        }
}

}
