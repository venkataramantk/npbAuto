package tests.webDT.plp;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.EnvironmentConfig;

import java.util.List;

public class DepartmentValidations extends BaseTest {
    WebDriver driver;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        driver.navigate().refresh();
        if (store.equalsIgnoreCase("US")) {
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {PLP,PROD_REGRESSION})
    public void validateDeptNameInHomePage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "1. Verify when the user mouse hover the L1 category global navigation, displays L2 categories drop down" +
                "2. Verify Department sizes displayed below L1 except Accessories and Clearance" +
                "3. Verify L2 categories are displayed for associated L1 categories" +
                "DT-44572");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        String clearance = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "otherValue")).get(0);

        AssertFailAndContinue(homePageActions.verificationAllDepartmentsDisplayed(), "Verify if all department links are getting displayed in the homepage");
        homePageActions.validateHrefLangTag();
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(headerMenuActions.waitUntilElementDisplayed(homePageActions.link_Shoes, 10), "Verify Shoes header is displayed");
            AssertFailAndContinue(headerMenuActions.waitUntilElementDisplayed(homePageActions.link_Clearance, 10), "Verify Clearance Header is displayed");
        }

        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(0)), "Verify if the mouse hover to the " + deptName.get(0) + "  department and appropriate category dropdown is displayed");
        //AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(0), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Girl", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(0) + " L1 categories");
        homePageActions.validateHrefLangTag();

        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(1)), "Verify if the mouse hover to the " + deptName.get(1) + " department and appropriate category dropdown is displayed");
        //AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(1), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "ToddlerGirl", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(1) + " L1 categories");
        homePageActions.validateHrefLangTag();

        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(2)), "Verify if the mouse hover to the " + deptName.get(2) + "  department and appropriate category dropdown is displayed");
        //AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(2), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Boy", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(2) + " L1 categories");
        homePageActions.validateHrefLangTag();

        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(3)), "Verify if the mouse hover to the " + deptName.get(3) + "  department and appropriate category dropdown is displayed");
        //AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(3), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "ToddlerBoy", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(3) + " L1 categories");
        homePageActions.validateHrefLangTag();

        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(4)), "Verify if the mouse hover to the " + deptName.get(4) + "  department and appropriate category dropdown is displayed");
        //AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(4), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Baby", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(4) + " L1 categories");
        homePageActions.validateHrefLangTag();

        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(6)), "Verify if the mouse hover to the " + deptName.get(6) + "  department and appropriate category dropdown is displayed");
        //AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(6), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Accessories", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(6) + " L1 categories");
        homePageActions.validateHrefLangTag();

        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(5)), "Verify if the mouse hover to the " + deptName.get(5) + "  department and appropriate category dropdown is displayed");
            //AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(5), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Shoes", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(5) + " L1 categories");

            AssertFailAndContinue(headerMenuActions.hoverOnDeptName(clearance), "Verify if the mouse hover to the " + clearance + "  department and appropriate category dropdown is displayed");
            //AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(clearance, convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Clearance", "Value"))), "Verify L2 categories are displayed for associated " + clearance + " L1 categories");
        }
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {PLP, CHEETAH,PROD_REGRESSION})
    public void departmentNavigationTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store.<br/> Verify" +
                "1. user click on L2 categories displays associated categories Product Landing Page (PLP)" +
                "2. PLP displays selected L2 category highlighted in Left Navigation" +
                "3. PLP displays Product Cards 4. Filter By and Sort By selections 5. Breadcrumb 6. Current category is highlighted in bread crumb" +
                "7. Navigate to L2 pages and verify if the user is not taken to 404 page.\n" +
                "8. Verify Global navigation bar is visible below L1 category in L2 Category Page" +
                "DT-25214, DT-25210, DT-25213");

        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(2));
        headerMenuActions.click(departmentLandingPageActions.lnk_Denim);
        AssertFailAndContinue(categoryDetailsPageAction.getText(categoryDetailsPageAction.breadCrumb).contains("Denim"), " Verify user click on L2 categories dispalys associated categories Product Landing Page (PLP)");

        // TODO : Need to enable after configuring the GLobal Navigation banner.
        //AssertFailAndContinue(headerMenuActions.validateGlobalNavBanner(), "Verify Global navigation bar is visible below L1 category in L2 category Page");
        departmentLandingPageActions.addStepDescription("UN-983");

        AssertFailAndContinue(categoryDetailsPageAction.getActiveL2().equalsIgnoreCase("Denim"), "PLP displays selected L2 category highlighted in Left Navigation");
        AssertFailAndContinue(categoryDetailsPageAction.verifyProductardsinPLP(), "PLP displays Product Cards");
        AssertFailAndContinue(categoryDetailsPageAction.verifySortAndFilter(), "Filter By and Sort By selections");
        AssertFailAndContinue(categoryDetailsPageAction.l3CategotyDisplay(), "Verify the L3 categories are getting displayed in Left Nav");
        String currentURl = driver.getCurrentUrl();
        driver.navigate().to(currentURl + "asdfassffs");
        AssertFailAndContinue(categoryDetailsPageAction.verify404Page(), " navigate to a category page and append the URL with some random characters like 'abdcagdsva'\n" + "  and hit that url and verify error code 404 and message as Page Not Found");
        //DT-43744
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementsAreDisplayed(footerActions.prodNameRecommWithPrice,10),"Verify Recommendation Section is displayed at the bottom, else check the DTN-2653");
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {PLP,PROD_REGRESSION})
    public void levelThreeNavigationTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a \" + user + \" user in \" + store + \" store.<br/> " +
                "1. Verify user clicking L1 or L2 or L3 Category link from breadcrumb redirects to associated L1 (Department) or L2 or L3 category PLP" +
                "2. Verify 404 page is displayed\n" +
                "3. Verify SEO optimization for L3 departments in CA");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(2));
        if (store.contains("CA")) {
            AssertFailAndContinue(driver.getCurrentUrl().contains("14503"), "Verify shortened form is displayed for department page in CA store");
        }
        headerMenuActions.click(departmentLandingPageActions.lnk_Denim);
        //headerMenuActions.click(departmentLandingPageActions.lnk_Graphic_Tees);
        AssertFailAndContinue(categoryDetailsPageAction.getActiveL2().equalsIgnoreCase("Denim"), "PLP displays selected L2 category highlighted in Left Navigation");
        AssertFailAndContinue(categoryDetailsPageAction.clickL3category(), "Verify user clicking L3 Category link from breadcrumb redirects to associated L3 category PLP");
        AssertFailAndContinue(categoryDetailsPageAction.verifyProductardsinPLP(), "PLP displays Product Cards");
        categoryDetailsPageAction.addStepDescription("UN-983");
        AssertFailAndContinue(categoryDetailsPageAction.verifySortAndFilter(), "Filter By and Sort By selections");
        AssertFailAndContinue(categoryDetailsPageAction.verifyL3Cat(), "Current category is highlighted in bread crumb with pink color");
        if (store.contains("CA")) {
            AssertFailAndContinue(driver.getCurrentUrl().contains("490"), "Verify shortened form is displayed for department page in CA store");
        }
        AssertFailAndContinue(categoryDetailsPageAction.clickOnBreadcrumb(), "Verify that the user is redirected to L2 page by clicking on breadcrumb");
        String currentURl = driver.getCurrentUrl();
        driver.navigate().to(currentURl + "asdfassffs");
        AssertFailAndContinue(categoryDetailsPageAction.verify404Page(), " navigate to a L3 category page and append the URL with some random characters like 'abdcagdsva'\n" + "  and hit that url and verify error code 404 and message as Page Not Found");
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {PLP, USONLY,PROD_REGRESSION})
    public void productBadgeTest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("As a " + user + " user in " + store + " store.<br/> " +
                "1. Check the Online only category Landing page \n" +
                "2. Verify if the user redirected to the department landing page");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(0));
        headerMenuActions.clickOnL2_ByL1(categoryDetailsPageAction,deptName.get(0),"Online Only");
        AssertFailAndContinue(!categoryDetailsPageAction.getText(categoryDetailsPageAction.productBadge.get(0)).contains("online"), "Verify Girl -> online only navigation will not display any products with online only badge");
        deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name1", "Value"));
        AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(0)), "Verify if the user redirected to the " + deptName.get(0) + " department landing page");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "online only");
        AssertFailAndContinue(categoryDetailsPageAction.validateOnlineOnlyL2Page(1), "Check the Online only category Landing page");
    }
}
