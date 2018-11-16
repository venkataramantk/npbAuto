package tests.webDT.globalComponents.globalNavigation.subCategories_L2;

//import com.sun.jna.platform.win32.GL;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.Env;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.EnvironmentConfig;

import java.util.List;

/**
 * Created by user on 3/7/2017.
 */
//User Story: DT-703
//@Listeners(MethodListener.class)
public class Department_LandingPage extends BaseTest {


    WebDriver driver;
    String emailAddressReg;
    private String password;
    String env;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("CA") String store) throws Exception {
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
    public void openBrowser(@Optional("CA") String store) throws Exception {
        env = EnvironmentConfig.getEnvironmentProfile();
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

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE,PRODUCTION, INTUATSTG})
    public void girlDeptValidation(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify if guest user is redirected to the Girl department landing page");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        //  List<String> urlVal = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "URL", "Value"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(0)), "Verify if the guest user redirected to the " + deptName.get(0) + " department landing page");
        AssertFailAndContinue(departmentLandingPageActions.validateDeptPageByName(deptName.get(0)), "Verify if the appropriate department name is getting displayed in the department page");
        if (store.equalsIgnoreCase("US")) {
            //   AssertFailAndContinue(departmentLandingPageActions.validateCatPageURL(urlVal.get(0)), "Verify if the corresponding department page is matching with the current URL");
           // AssertFailAndContinue(departmentLandingPageActions.validateEspots(), "Verify if the appropriate espots are getting displayed in the department page");
            AssertFailAndContinue(departmentLandingPageActions.validatesubCategories(), "Verify if Associated department's L2 categories are displayed in left hand navigation");
            if (user.equalsIgnoreCase("guest")) {
                departmentLandingPageActions.mprLinkDisplay();
            }
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE,PRODUCTION,INTUATSTG})
    public void toddlerGirlDeptValidation(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify if guest user is redirected to the Toddler Girl department landing page");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        //  List<String> urlVal = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "URL", "Value"));

        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(1)), "Verify if the guest user redirected to the " + deptName.get(1) + " department landing page");
        AssertFailAndContinue(departmentLandingPageActions.validateDeptPageByName(deptName.get(1)), "Verify if the appropriate department name is getting displayed in the department page");
        if (store.equalsIgnoreCase("US")) {
            //     AssertFailAndContinue(departmentLandingPageActions.validateCatPageURL(urlVal.get(1)), "Verify if the corresponding department page is matching with the current URL");
           // AssertFailAndContinue(departmentLandingPageActions.validateEspots(), "Verify if the appropriate espots are getting displayed in the department page");
            AssertFailAndContinue(departmentLandingPageActions.validatesubCategories(), "Verify if Associated department's L2 categories are displayed in left hand navigation");
            if (user.equalsIgnoreCase("guest")) {
                departmentLandingPageActions.mprLinkDisplay();
            }
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PRODUCTION, INTUATSTG})
    public void boyDeptValidation(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify if guest user is redirected to the Boy department landing page");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        // List<String> urlVal = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "URL", "Value"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(2)), "Verify if the guest user redirected to the " + deptName.get(2) + " department landing page");
        AssertFailAndContinue(departmentLandingPageActions.validateDeptPageByName(deptName.get(2)), "Verify if the appropriate department name is getting displayed in the department page");
        if (store.equalsIgnoreCase("US")) {
            //      AssertFailAndContinue(departmentLandingPageActions.validateCatPageURL(urlVal.get(2)), "Verify if the corresponding department page is matching with the current URL");
           // AssertFailAndContinue(departmentLandingPageActions.validateEspots(), "Verify if the appropriate espots are getting displayed in the department page");
            AssertFailAndContinue(departmentLandingPageActions.validatesubCategories(), "Verify if Associated department's L2 categories are displayed in left hand navigation");
            if (user.equalsIgnoreCase("guest")) {
                departmentLandingPageActions.mprLinkDisplay();
            }
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PRODUCTION, INTUATSTG})
    public void toddlerBoyDeptValidation(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify if guest user is redirected to the Toddler Boy department landing page");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        // List<String> urlVal = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "URL", "Value"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(3)), "Verify if the guest user redirected to the " + deptName.get(3) + " department landing page");
        AssertFailAndContinue(departmentLandingPageActions.validateDeptPageByName(deptName.get(3)), "Verify if the appropriate department name is getting displayed in the department page");
        if (store.equalsIgnoreCase("US")) {
            //   AssertFailAndContinue(departmentLandingPageActions.validateCatPageURL(urlVal.get(3)), "Verify if the corresponding department page is matching with the current URL");
           // AssertFailAndContinue(departmentLandingPageActions.validateEspots(), "Verify if the appropriate espots are getting displayed in the department page");
            AssertFailAndContinue(departmentLandingPageActions.validatesubCategories(), "Verify if Associated department's L2 categories are displayed in left hand navigation");
            if (user.equalsIgnoreCase("guest")) {
                departmentLandingPageActions.mprLinkDisplay();
            }
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PRODUCTION, INTUATSTG})
    public void babyDeptValidation(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify if guest user is redirected to the Baby department landing page");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        //  List<String> urlVal = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "URL", "Value"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(4)), "Verify if the guest user redirected to the " + deptName.get(4) + " department landing page");
        AssertFailAndContinue(departmentLandingPageActions.validateDeptPageByName(deptName.get(4)), "Verify if the appropriate department name is getting displayed in the department page");
        if (store.equalsIgnoreCase("US")) {

            //     AssertFailAndContinue(departmentLandingPageActions.validateCatPageURL(urlVal.get(4)), "Verify if the corresponding department page is matching with the current URL");
           // AssertFailAndContinue(departmentLandingPageActions.validateEspots(), "Verify if the appropriate espots are getting displayed in the department page");
            AssertFailAndContinue(departmentLandingPageActions.validatesubCategories(), "Verify if Associated department's L2 categories are displayed in left hand navigation");
            if (user.equalsIgnoreCase("guest")) {
                departmentLandingPageActions.mprLinkDisplay();
            }
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PRODUCTION},enabled = false)
    public void shoesDeptValidation(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Venkat");
        setRequirementCoverage("Verify if guest user is redirected to the Shoes department landing page");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        //  List<String> urlVal = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "URL", "Value"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(5)), "Verify if the guest user redirected to the " + deptName.get(5) + " department landing page");
        AssertFailAndContinue(departmentLandingPageActions.validateDeptPageByName(deptName.get(5)), "Verify if the appropriate department name is getting displayed in the department page");
        if (store.equalsIgnoreCase("US")) {
            //          AssertFailAndContinue(departmentLandingPageActions.validateCatPageURL(urlVal.get(5)), "Verify if the corresponding department page is matching with the current URL");
            //AssertFailAndContinue(departmentLandingPageActions.validateEspots(), "Verify if the appropriate espots are getting displayed in the department page");
            AssertFailAndContinue(departmentLandingPageActions.validatesubCategories(), "Verify if Associated department's L2 categories are displayed in left hand navigation");
            if (user.equalsIgnoreCase("guest")) {
                departmentLandingPageActions.mprLinkDisplay();
            }
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PRODUCTION,INTUATSTG})
    public void accessoriesDeptValidation(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if guest user is redirected to the Accessories department landing page");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        //     List<String> urlVal = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "URL", "Value"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(6)), "Verify if the guest user redirected to the " + deptName.get(6) + " department landing page");
        AssertFailAndContinue(departmentLandingPageActions.validateDeptPageByName(deptName.get(6)), "Verify if the appropriate department name is getting displayed in the department page");
        if (store.equalsIgnoreCase("US")) {
            //       AssertFailAndContinue(departmentLandingPageActions.validateCatPageURL(urlVal.get(6)), "Verify if the corresponding department page is matching with the current URL");
           // AssertFailAndContinue(departmentLandingPageActions.validateEspots(), "Verify if the appropriate espots are getting displayed in the department page");
            AssertFailAndContinue(departmentLandingPageActions.validatesubCategories(), "Verify if Associated department's L2 categories are displayed in left hand navigation");
            if (user.equalsIgnoreCase("guest")) {
                departmentLandingPageActions.mprLinkDisplay();
            }
        }
    }

    @Test(dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, SMOKE, PRODUCTION, INTUATSTG})
    public void clearanceDeptValidation(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if guest user is redirected to the Clearance department landing page");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "otherValue"));
        //   List<String> urlVal = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "URL", "otherValue"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(0));
        AddInfoStep("Verify if the guest user redirected to the " + deptName.get(0) + " department landing page");
        //   AssertFailAndContinue(departmentLandingPageActions.validateCatPageURL(urlVal.get(0)), "Verify if the corresponding department page is matching with the current URL");
        if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("guest")) {
                AssertFailAndContinue(departmentLandingPageActions.mprLinkDisplay(), "Verify MPR lin for US guest");
            }
        }
    }

    //Still in Progress since the Department page is not accessible yet

    @Test(enabled = false)//This category is removed in DT
    public void placeShopsValidation(@Optional("Store") String store) throws Exception {
        setAuthorInfo("Azeez");
        setRequirementCoverage("Verify if guest user is redirected to the Place Shops department landing page");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "otherValue"));
        List<String> urlVal = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "URL", "otherValue"));

        AssertFailAndContinue(homePageActions.selectOtherDeptWithName(departmentLandingPageActions, deptName.get(1)), "Verify if the guest user redirected to the " + deptName.get(1) + " department landing page");
      //  AssertFailAndContinue(departmentLandingPageActions.validateCatPageURL(urlVal.get(1)),"Verify if the corresponding department page is matching with the current URL");
        AssertFailAndContinue(departmentLandingPageActions.validateDeptPageByName(deptName.get(3)),"Verify if the appropriate department name is getting displayed in the department page");
//        AssertFailAndContinue(departmentLandingPageActions.validateLinksCount(deptName.get(1)),"Validated that the number of Left Nav links and the Number of links in the Department Menu are consistent.");
    //
    }
    // TODO : Need to enable when Style sqaud is configured in Prod
    @Test( dataProvider = dataProviderName,groups = {GLOBALCOMPONENT,OUTFIT,PRODUCTIONONLY,PROD_REGRESSION},enabled = false)
    public void styleSqaudCheck(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("srijith");
        setRequirementCoverage("Verify the Style squad Landing page under girl and Boy department");

        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department","Name","Value"));
        List<String> styleSquad = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department","StyleSquad","Value"));
        List<String> styleErr =convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department","StyleSquad","validErrMsg1"));
        List<String> text = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department","StyleSquad","otherValue"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        homePageActions.howerOverDeptWithName(departmentLandingPageActions,deptName.get(0));
        departmentLandingPageActions.click(departmentLandingPageActions.link_StyleSquad);
        headerMenuActions.waitUntilElementDisplayed(categoryDetailsPageAction.bodyCopyText,3);
        AssertFailAndContinue(headerMenuActions.checkTheTitleDisplayed(text.get(0)),"Verify the text displayed in browser tab in Girl Style squad page");

        // To check for Boys
        homePageActions.howerOverDeptWithName(departmentLandingPageActions,deptName.get(2));
        departmentLandingPageActions.click(departmentLandingPageActions.link_StyleSquad);
        headerMenuActions.waitUntilElementDisplayed(categoryDetailsPageAction.bodyCopyText,3);
        AssertFailAndContinue(headerMenuActions.checkTheTitleDisplayed(text.get(1)),"Verify the text displayed in browser tab in Boy style squad page");

        homePageActions.howerOverDeptWithName(departmentLandingPageActions,deptName.get(0));
        departmentLandingPageActions.click(departmentLandingPageActions.link_Outfit);
        headerMenuActions.waitUntilElementDisplayed(categoryDetailsPageAction.bodyCopyText,3);
        AssertFailAndContinue(headerMenuActions.checkTheTitleDisplayed(text.get(5)),"Verify the text displayed in browser tab in Girl Outfit landing page");

        // To check for Boys
        homePageActions.howerOverDeptWithName(departmentLandingPageActions,deptName.get(2));
        departmentLandingPageActions.click(departmentLandingPageActions.link_Outfit);
        headerMenuActions.waitUntilElementDisplayed(categoryDetailsPageAction.bodyCopyText,3);
        AssertFailAndContinue(headerMenuActions.checkTheTitleDisplayed(text.get(6)),"Verify the text displayed in browser tab in boys outfit landing page");

        //To check for Toddler Girl
        homePageActions.howerOverDeptWithName(departmentLandingPageActions,deptName.get(1));
        departmentLandingPageActions.click(departmentLandingPageActions.link_Outfit);
        headerMenuActions.waitUntilElementDisplayed(categoryDetailsPageAction.bodyCopyText,3);
        AssertFailAndContinue(headerMenuActions.checkTheTitleDisplayed(text.get(2)),"Verify the text displayed in browser tab in Toddler Girl outfit landing page");

        // To check for Toddler Boys
        homePageActions.howerOverDeptWithName(departmentLandingPageActions,deptName.get(3));
        departmentLandingPageActions.click(departmentLandingPageActions.link_Outfit);
        headerMenuActions.waitUntilElementDisplayed(categoryDetailsPageAction.bodyCopyText,3);
        AssertFailAndContinue(headerMenuActions.checkTheTitleDisplayed(text.get(3)),"Verify the text displayed in browser tab in Toddler boys outfit landing page");

        headerMenuActions.clickOnTCPLogo();
        headerMenuActions.waitUntilElementDisplayed(headerMenuActions.shoppingBagIcon,3);
        AssertFailAndContinue(headerMenuActions.checkTheTitleDisplayed(text.get(4)),"Verify the text displayed in browser tab in Homepage");

        //TODO: Need to enable after the form is enabled in Production

      // ),styleErr.get(7),styleErr.get(8),styleErr.get(9),styleErr.get(10),
               // styleErr.get(11),styleErr.get(12),styleErr.get(13));
    }
    //TODO: Need to enable later
    @Test(dataProvider = dataProviderName,groups = {GLOBALCOMPONENT, SMOKE, PRODUCTION,USONLY},enabled = false)
    public void giftCardValidation(@Optional("US") String store,@Optional("guest") String user) throws Exception {
        setAuthorInfo("Srijith");
        setRequirementCoverage("Verify if guest user is redirected to the Gift card landing page");
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "otherValue"));

        if(user.equalsIgnoreCase("registered")){
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg,password);
        }
        AssertFailAndContinue(departmentLandingPageActions.validateDeptPageByName(deptName.get(7)), "Verify if the appropriate department name is getting displayed in the department page");
        giftCardsPageActions.clickSendAGiftCardsButton(productDetailsPageActions);
        AssertFailAndContinue(giftCardsPageActions.addGiftCardToBag(),"Verify that the user is able to add Gift card to bag");
        headerMenuActions.navigateToShoppingBag(shoppingBagDrawerActions,shoppingBagPageActions);
        AssertFailAndContinue(shoppingBagPageActions.editAnGiftCardFromSBPage(0),"Verify that the user is displayed with Gift card list in the edit drop down in SB page");
    }

}
