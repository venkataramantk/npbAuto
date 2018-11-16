package tests.webDT.globalComponents.globalNavigation.categories_L1;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import tests.webDT.shoppingBag.EmptyShoppingBag;
import ui.support.Config;
import ui.support.EnvironmentConfig;
import ui.utils.ExcelReader;

import java.util.List;
import java.util.Map;

/**
 * Created by Venkat on 3/1/2017.
 */
//User Story: DT-697
//@Listeners(MethodListener.class)
public class Departments_Global extends BaseTest {

    WebDriver driver;
    String emailAddressReg;
    private String password;
    String env;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        env = EnvironmentConfig.getEnvironmentProfile();
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            driver.get(EnvironmentConfig.getApplicationUrl());
            if (user.equalsIgnoreCase("registered"))
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
        } else if (store.equalsIgnoreCase("CA")) {
            headerMenuActions.deleteAllCookies();
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered"))
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
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

    @Test(dataProvider = dataProviderName, priority = 0, groups = {PLP, GLOBALCOMPONENT, REGRESSION, SMOKE,PROD_REGRESSION})
    public void validateDeptNameInHomePage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("CANADA");
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "1. Verify when the user mouse hover the L1 category global navigation, displays L2 categories drop down" +
                "2. Verify Department sizes displayed below L1 except Accessories and Clearance" +
                "3. Verify L2 categories are displayed for associated L1 categories");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        String clearance = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "otherValue")).get(0);

        AssertFailAndContinue(homePageActions.verificationAllDepartmentsDisplayed(), "Verify if all department links are getting displayed in the homepage");
        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(0)), "Verify if the mouse hover to the " + deptName.get(0) + "  department and appropriate category dropdown is displayed");
//        AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(0), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Girl", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(0) + " L1 categories");
        AssertFailAndContinue(headerMenuActions.checkOrganizedDisplay(deptName.get(0)),"Verify the Organized flyout are getting displayed");
        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(1)), "Verify if the mouse hover to the " + deptName.get(1) + " department and appropriate category dropdown is displayed");
  //      AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(1), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Toddler Girl", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(1) + " L1 categories");
        AssertFailAndContinue(headerMenuActions.checkOrganizedDisplay(deptName.get(1)),"Verify the Organized flyout are getting displayed");

        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(2)), "Verify if the mouse hover to the " + deptName.get(2) + "  department and appropriate category dropdown is displayed");
//        AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(2), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Boy", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(2) + " L1 categories");
        AssertFailAndContinue(headerMenuActions.checkOrganizedDisplay(deptName.get(2)),"Verify the Organized flyout are getting displayed");

        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(3)), "Verify if the mouse hover to the " + deptName.get(3) + "  department and appropriate category dropdown is displayed");
  //      AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(3), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Toddler Boy", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(3) + " L1 categories");
        AssertFailAndContinue(headerMenuActions.checkOrganizedDisplay(deptName.get(3)),"Verify the Organized flyout are getting displayed");

        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(4)), "Verify if the mouse hover to the " + deptName.get(4) + "  department and appropriate category dropdown is displayed");
    //    AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(4), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Baby", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(4) + " L1 categories");
        // :TODO Need to enable the same in Lower environment once the Shoes department is configured in CMC
        if(env.equalsIgnoreCase("prod")) {
            AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(5)), "Verify if the mouse hover to the " + deptName.get(5) + "  department and appropriate category dropdown is displayed");
            //  AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(5), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Shoes", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(5) + " L1 categories");
        }
        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(deptName.get(6)), "Verify if the mouse hover to the " + deptName.get(6) + "  department and appropriate category dropdown is displayed");
        //AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(deptName.get(6), convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Accessories", "Value"))), "Verify L2 categories are displayed for associated " + deptName.get(6) + " L1 categories");

        AssertFailAndContinue(headerMenuActions.hoverOnDeptName(clearance), "Verify if the mouse hover to the " + clearance + "  department and appropriate category dropdown is displayed");
        //AssertFailAndContinue(headerMenuActions.comparelevelTwoCategories(clearance, convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Clearance", "Value"))), "Verify L2 categories are displayed for associated " + clearance + " L1 categories");

//        headerMenuActions.hoverOnDeptName(deptName.get(0));
       headerMenuActions.clickOnL2_ByL1(categoryDetailsPageAction,deptName.get(0),"Bottoms");
        AssertFailAndContinue(categoryDetailsPageAction.getText(categoryDetailsPageAction.breadCrumb).contains("Bottoms"), "Verify user click on L2 categories displays associated categories Product Landing Page (PLP)");

        headerMenuActions.navigateToDepartment(deptName.get(2));
        AssertFailAndContinue(footerActions.validateBackToTop(), "Verify user scrolling down at home page starts displaying Back to Top button");
        AssertFailAndContinue(footerActions.clickBackToTop(), "Verify user clicking Back to Top navigates user back to top of home page");

        if(store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(headerMenuActions.clickOnL2_ByL1(categoryDetailsPageAction, deptName.get(2), "Online Only"), "Verify BreadCrumb is displayed for Boy-> online only");
        }if(store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(categoryDetailsPageAction.getCurrentURL().contains("/ca/search/"),"The URL has to navigate to CA only after clicking on online only link");
        }
        headerMenuActions.navigateToDepartment(deptName.get(2));
        headerMenuActions.click(departmentLandingPageActions.clearance_lnk);
        // As per latest change the Breadcrumb will be displayed
        AssertFailAndContinue(!searchResultsPageActions.waitUntilElementDisplayed(categoryDetailsPageAction.searchBanner), "Verify Search Banner is displayed for Boy-> Clearance");

        headerMenuActions.navigateToDepartment(deptName.get(2));
        headerMenuActions.click(departmentLandingPageActions.newarrivals_link);
        // As per latest change the Breadcrumb will be displayed
        AssertFailAndContinue(!searchResultsPageActions.waitUntilElementDisplayed(categoryDetailsPageAction.searchBanner), "Verify Search Banner is displayed for Boy-> New Arrivals");
       // Online Only won't be availble in CA
        /*if(store.equalsIgnoreCase("CA")) {
            AssertFailAndContinue(categoryDetailsPageAction.getCurrentURL().contains("/ca/search/"),"The URL has to navigate to CA only after clicking on online only link");
        }*/
    }

    @Test(priority = 1, groups = {GLOBALCOMPONENT, REGRESSION, SMOKE,PROD_REGRESSION}, dataProvider = dataProviderName)
    public void validateDeptNameInCheckoutPage(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify as a register user if the department names are not getting displayed in the checkout pages");
        String searchKeyword = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Value");
        String qty = getDT2TestingCellValueBySheetRowAndColumn("Search", "SearchBy", "Qty");
        Map<String, String> searchKeywordAndQty = getMapOfItemNamesAndQuantityWithCommaDelimited(searchKeyword, qty);

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(0));
        if(store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(headerMenuActions.clickOnL2_ByL1(categoryDetailsPageAction, deptName.get(0), "Online Only"), "Verify BreadCrumb is displayed for Girl-> online only");
            try {
                AssertFailAndContinue(!categoryDetailsPageAction.isDisplayed(categoryDetailsPageAction.onlineOnlyBadge_PLP.get(0)), "Verify the Online Only badge is not displayed in Category landing page");
            } catch (IndexOutOfBoundsException e) {
                AssertFailAndContinue(true, "Verify the Online Only badge is not displayed in Category landing page");

            }
        }
        headerMenuActions.navigateToDepartment(deptName.get(0));
        AssertFailAndContinue(headerMenuActions.clickOnL2_ByL1(categoryDetailsPageAction,deptName.get(0),"New Arrivals"), "Verify BreadCrumb is displayed for Girl-> New Arrivals");
        try {
        AssertFailAndContinue(!categoryDetailsPageAction.isDisplayed(categoryDetailsPageAction.newBadge_PLP.get(0)),"Verify the New Badge is not displayed in Category landing page");
        }catch (IndexOutOfBoundsException e){
            AssertFailAndContinue(true, "Verify the New Badge is not displayed in Category landing page");

        }
        headerMenuActions.navigateToDepartment(deptName.get(0));
        headerMenuActions.click(departmentLandingPageActions.clearance_lnk);
        try {
        AssertFailAndContinue(!categoryDetailsPageAction.isDisplayed(categoryDetailsPageAction.badge_Clearance.get(0)),"Verify the Clearance Badge is not displayed in Category landing page");
        }catch (IndexOutOfBoundsException e){
            AssertFailAndContinue(true, "Verify the Clearance Badge is not displayed in Category landing page");

        }
        AssertFailAndContinue(searchResultsPageActions.waitUntilElementDisplayed(categoryDetailsPageAction.deptNameTitle), "Verify Search Banner is displayed for Boy-> Clearance");


        addToBagBySearching(searchKeywordAndQty);
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions, shoppingBagPageActions);
        if (user.equalsIgnoreCase("registered"))
            AssertFailAndContinue(shoppingBagPageActions.clickOnCheckoutBtnAsReg(shippingPageActions, reviewPageActions), "Click on checkout button and check user navigate to shipping page");
        if (user.equalsIgnoreCase("guest"))
            AssertFailAndContinue(shoppingBagPageActions.clickContinueCheckoutAsGuest(loginPageActions, shippingPageActions), "Click on checkout button from shopping bag page");
        AssertFailAndContinue(shippingPageActions.validateDeptNameInShippingPage(), "Validate the department name is not getting displayed in shipping page");
        AssertFailAndContinue(shippingPageActions.validateFieldsInShippingPage(), "validating the product details present in shipping page");
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {GLOBALCOMPONENT, REGRESSION, SMOKE,PROD_REGRESSION})
    public void directHittingUrl(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify as a guest user is able to navigates to different pages by direct hitting the url");
        //       String env = EnvironmentConfig.getEnvironmentProfile();
        String urlUS = EnvironmentConfig.getApplicationUrl().replaceAll("/home", "");
//        String urlCA = EnvironmentConfig.getCA_ApplicationUrl().replaceAll("/home", "");

        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("guest")) {
//                    String URL = EnvironmentConfig.getApplicationUrl().replaceAll("/home","");
                driver.get(urlUS + "/account");
                AssertFailAndContinue(headerMenuActions.waitUntilElementDisplayed(loginDrawerActions.emailAddrField, 4), "Direct hitting of my account url as guest user");
                headerMenuActions.closeTheVisibleDrawer();
            }
            if (user.equalsIgnoreCase("registered")) {
                clickLoginAndLoginAsRegUser(emailAddressReg, password);
                driver.get(urlUS + "/account");
                AssertFailAndContinue(myAccountPageActions.waitUntilElementDisplayed(myAccountPageActions.lnk_AddressBook, 5), "Direct hitting of my account url as Reg user");
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            if (user.equalsIgnoreCase("guest")) {
                footerActions.changeCountryAndLanguage("CA", "English");
//                driver.get(urlCA + "/account");
                AssertFailAndContinue(headerMenuActions.waitUntilElementDisplayed(loginDrawerActions.emailAddrField, 4), "Direct hitting of my account url as guest user");
                headerMenuActions.closeTheVisibleDrawer();
            }
            if (user.equalsIgnoreCase("registered")) {
                clickLoginAndLoginAsRegUser(emailAddressReg, password);
//                driver.get(urlCA + "/account");
                AssertFailAndContinue(myAccountPageActions.waitUntilElementDisplayed(myAccountPageActions.lnk_AddressBook, 5), "Direct hitting of my account url as Reg user");
            }

        }
    }
}