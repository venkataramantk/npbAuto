package tests.webDT.globalComponents.footer;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;

/**
 * Created by AbdulazeezM on 10/26/2017.
 */
public class StaticContent_Careers extends BaseTest {
    WebDriver driver;
    ExcelReader dt2 = new ExcelReader(Config.getDataFile(Config.DT2_DETAILS));
    Logger logger = Logger.getLogger(Footer.class);
    String emailAddressReg;
    private String password;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {

        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");

        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");

        }
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }

    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION,PROD_REGRESSION})
    public void validateCorporateCareerPage(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify the guest user can navigate to career page also navigate to corporate career page and validate the fields present in the page");

        List<String> search = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "searchTerm", "validSearch"));
        List<String> errMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "searchTerm", "validSearch"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        headerMenuActions.scrollToBottom();
        footerActions.click(footerActions.link_Careers);
        staticContentCarrerPageActions.staticWait(2000);
        AssertFailAndContinue(staticContentCarrerPageActions.validateStaticContentAndBtns(), "validate the static contents and buttons present in the carrers page");
        String currentWindow = driver.getWindowHandle();
        staticContentCarrerPageActions.click(staticContentCarrerPageActions.corporateBtn);
        closeWindow();
        staticContentCarrerPageActions.staticWait(4000);
        switchToWindow(currentWindow);
        staticContentCarrerPageActions.staticWait(4000);
        if (headerMenuActions.isDisplayed(staticContentCarrerPageActions.searchField)) {
            AssertFailAndContinue(staticContentCarrerPageActions.validateCorporateCareersPage(), "Validate the contents present in the corporate career page");
            AssertFailAndContinue(staticContentCarrerPageActions.searchJobByText(search.get(1)), "Search any valid term in the search box and get the results");
            AssertFailAndContinue(staticContentCarrerPageActions.clickWelcomeLink(), "Click on welcome link and navigate to previous page");
            AssertFailAndContinue(staticContentCarrerPageActions.clickViewAllOpeningsLink(), "Click on view all opening jobs link and check user displayed with the all opening jobs");
            AssertFailAndContinue(staticContentCarrerPageActions.clickWelcomeLink(), "Click on welcome link and navigate to previous page");
            AssertFailAndContinue(staticContentCarrerPageActions.validateErrorMessage(errMsg.get(0)), "Enter invalid search term and check the appropriate error message");
        } else {
            headerMenuActions.addStepDescription("Currently No Job Openings are available");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION,PROD_REGRESSION})
    public void validateStoreCareerPage(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify the guest user can navigate to career page also navigate to store career page and validate the fields present in the page");

        List<String> search = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "searchTerm", "validSearch"));
        List<String> errMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "searchTerm", "validSearch"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        headerMenuActions.scrollToBottom();
        footerActions.click(footerActions.link_Careers);
        staticContentCarrerPageActions.staticWait(2000);
        AssertFailAndContinue(staticContentCarrerPageActions.validateStaticContentAndBtns(), "validate the static contents and buttons present in the carrers page");
        String currentWindow = driver.getWindowHandle();
        staticContentCarrerPageActions.click(staticContentCarrerPageActions.storesBtn_US);
        staticContentCarrerPageActions.staticWait(2000);
        switchToWindow(currentWindow);
        staticContentCarrerPageActions.staticWait(4000);
        if (headerMenuActions.isDisplayed(staticContentCarrerPageActions.searchField)) {
            AssertFailAndContinue(staticContentCarrerPageActions.validateStoreCareersPage(), "Validate the contents present in the store career page");
            AssertFailAndContinue(staticContentCarrerPageActions.searchJobByText(search.get(2)), "Search any valid term in the search box and get the results");
            AssertFailAndContinue(staticContentCarrerPageActions.clickWelcomeLink(), "Click on welcome link and navigate to previous page");
            AssertFailAndContinue(staticContentCarrerPageActions.clickViewAllOpeningsLink(), "Click on view all opening jobs link and check user displayed with the all opening jobs");
            AssertFailAndContinue(staticContentCarrerPageActions.clickWelcomeLink(), "Click on welcome link and navigate to previous page");
            AssertFailAndContinue(staticContentCarrerPageActions.validateErrorMessage(errMsg.get(0)), "Enter invalid search term and check the appropriate error message");
        } else {
            headerMenuActions.addStepDescription("Currently No Job Openings are available");
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION,PROD_REGRESSION})
    public void validateDistributionCareerPage(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify the guest user can navigate to career page also navigate to distribution career page and validate the fields present in the page");

        List<String> search = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "searchTerm", "validSearch"));
        List<String> errMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "searchTerm", "validSearch"));
        String textmsg = getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "storeCareerpage", "Details");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        headerMenuActions.scrollToBottom();
        footerActions.click(footerActions.link_Careers);
        staticContentCarrerPageActions.staticWait(2000);
        AssertFailAndContinue(staticContentCarrerPageActions.validateStaticContentAndBtns(), "validate the static contents and buttons present in the carrers page");
        String currentWindow = driver.getWindowHandle();
        staticContentCarrerPageActions.click(staticContentCarrerPageActions.distribution_Btn_US);
        staticContentCarrerPageActions.staticWait(4000);
        closeWindow();
        switchToWindow(currentWindow);
        staticContentCarrerPageActions.staticWait(4000);
        if (headerMenuActions.isDisplayed(staticContentCarrerPageActions.searchField)) {

            AssertFailAndContinue(staticContentCarrerPageActions.jobOpeningCheck(textmsg), "Verify the text displayed in the page");
            AssertFailAndContinue(staticContentCarrerPageActions.validateStoreCareersPage(), "Validate the contents present in the distribution career page");
            AssertFailAndContinue(staticContentCarrerPageActions.searchJobByText(search.get(0)), "Search any valid term in the search box and get the results");
            AssertFailAndContinue(staticContentCarrerPageActions.clickWelcomeLink(), "Click on welcome link and navigate to previous page");
            AssertFailAndContinue(staticContentCarrerPageActions.clickViewAllOpeningsLink(), "Click on view all opening jobs link and check user displayed with the all opening jobs");
            AssertFailAndContinue(staticContentCarrerPageActions.clickWelcomeLink(), "Click on welcome link and navigate to previous page");
            AssertFailAndContinue(staticContentCarrerPageActions.validateErrorMessage(errMsg.get(0)), "Enter invalid search term and check the appropriate error message");

        } else {
            headerMenuActions.addStepDescription("Currently No Job Openings are available");
        }
    }
}
