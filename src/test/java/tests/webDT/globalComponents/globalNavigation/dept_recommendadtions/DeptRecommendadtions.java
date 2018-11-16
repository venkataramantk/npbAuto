package tests.webDT.globalComponents.globalNavigation.dept_recommendadtions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import tests.webDT.globalComponents.globalNavigation.categories_L1.Departments_Global;
import ui.support.EnvironmentConfig;

import java.util.List;

public class DeptRecommendadtions extends BaseTest {

    WebDriver driver;
    Logger logger = Logger.getLogger(Departments_Global.class);
    private String rowInExcel = "CreateAccountUS";
    String emailAddressReg;
    private String password;

    @Parameters(storeXml)
    @BeforeClass(alwaysRun = true)
    public void initDriver() throws Exception {
//        try {
//            if (EnvironmentConfig.isRecommendationsEnabled().equalsIgnoreCase("No")) {
//                AssertWarnAndContinue(false, "Skipping because of Recommendations disabled currently");
//                throw new SkipException("Recommendations are disabled");
//            }
//        } catch (NullPointerException n) {
//            n.getStackTrace();
//        }
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());
        emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser() throws Exception {
        driver.get(EnvironmentConfig.getApplicationUrl());
        headerMenuActions.addStateCookie("NJ");
    }

    @Parameters(storeXml)
    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, RECOMMENDATIONS, SMOKE,PROD_REGRESSION})
    public void recommendadtions_girl(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Validate Recommended product test cases in girl Department page");
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("CANADA");
        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(0));
        AssertFailAndContinue(productDetailsPageActions.validateSubcate(), "Verify L2 Categories in the left hand side");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the girl department page");
        AssertFailAndContinue(footerActions.prods.size() == 4, "Verify Only 4 items are displayed in recommendation layout");
        validateCarouselArrows();

        AssertFailAndContinue(footerActions.validateProduceRecommendPriceDetails(), "Verify Product was price and current price");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationImageAndName(), "Verify the recommendation products are getting displayed in the girl department page");
        if (user.equalsIgnoreCase("Guest")) {
            AssertFailAndContinue(footerActions.addProdToFav_Guest_login(emailAddressReg, password), "Add the recommendation product into wishlist as guest user and check if the color of the fav icon is changed");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist as register user and check if the color of the fav icon is changed");
        }
        footerActions.clickAddToBag();
        AssertFailAndContinue(homePageActions.selectRandomSizeAndAddToBag(headerMenuActions), "Add the recommendation product in to the cart");
        String prodName = headerMenuActions.getText(footerActions.prodNameRecommWithPrice.get(0));
        headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions);
        AssertFailAndContinue(shoppingBagDrawerActions.itemOrderDisplay(prodName), "Verify that the item is added to bag");
        headerMenuActions.click(headerMenuActions.closemodal);
        AssertFailAndContinue(footerActions.checkHeader(), "Check whether the header is displayed constantly");
    }

    @Test(priority = 1, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, RECOMMENDATIONS, SMOKE,PROD_REGRESSION})
    public void recommendadtions_Toddlergirl(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Validate Recommended product test cases in Toddler Toddlergirl Department page");
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("CANADA");
        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(1));
        AssertFailAndContinue(productDetailsPageActions.validateSubcate(), "Verify L2 Categories in the left hand side");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the Toddler girl department page");
        AssertFailAndContinue(footerActions.prods.size() == 4, "Verify Only 4 items are displayed in recommendation layout");
        validateCarouselArrows();

        AssertFailAndContinue(footerActions.validateProduceRecommendPriceDetails(), "Verify Product was price and current price");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationImageAndName(), "Verify the recommendation products are getting displayed in the Toddler girl department page");
        if (user.equalsIgnoreCase("Guest")) {
            AssertFailAndContinue(footerActions.addProdToFav_Guest_login(emailAddressReg, password), "Add the recommendation product into wishlist as guest user and check if the color of the fav icon is changed");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist as register user and check if the color of the fav icon is changed");
        }
        footerActions.clickAddToBag();
        AssertFailAndContinue(homePageActions.selectRandomSizeAndAddToBag(headerMenuActions), "Add the recommendation product in to the cart");
    }

    @Test(priority = 2, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, RECOMMENDATIONS, SMOKE,PROD_REGRESSION})
    public void recommendadtions_Boy(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Validate Recommended product test cases in Toddler Boy Department page");
        driver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("CANADA");
        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }

        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(2));
        AssertFailAndContinue(productDetailsPageActions.validateSubcate(), "Verify L2 Categories in the left hand side");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the Boy department page");
        AssertFailAndContinue(footerActions.prods.size() == 4, "Verify Only 4 items are displayed in recommendation layout");
        validateCarouselArrows();

        AssertFailAndContinue(footerActions.validateProduceRecommendPriceDetails(), "Verify Product was price and current price");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationImageAndName(), "Verify the recommendation products are getting displayed in the Boy department page");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.addProdToFav_Guest_login(emailAddressReg, password), "Add the recommendation product into wishlist as guest user and check if the color of the fav icon is changed");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist as register user and check if the color of the fav icon is changed");
        }
        footerActions.clickAddToBag();
        AssertFailAndContinue(homePageActions.selectRandomSizeAndAddToBag(headerMenuActions), "Add the recommendation product in to the cart");
    }

    @Test(priority = 3, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, RECOMMENDATIONS, SMOKE,PROD_REGRESSION})
    public void recommendadtions_ToddlerBoy(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Validate Recommended product test cases in Toddler ToddlerBoy Department page");
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("CANADA");
        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(3));
        AssertFailAndContinue(productDetailsPageActions.validateSubcate(), "Verify L2 Categories in the left hand side");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the Toddler Boy department page");
        AssertFailAndContinue(footerActions.prods.size() == 4, "Verify Only 4 items are displayed in recommendation layout");
        validateCarouselArrows();

        AssertFailAndContinue(footerActions.validateProduceRecommendPriceDetails(), "Verify Product was price and current price");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationImageAndName(), "Verify the recommendation products are getting displayed in the Toddler Boy department page");
        if (user.equalsIgnoreCase("Guest")) {
            AssertFailAndContinue(footerActions.addProdToFav_Guest_login(emailAddressReg, password), "Add the recommendation product into wishlist as guest user and check if the color of the fav icon is changed");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist as register user and check if the color of the fav icon is changed");
        }
        footerActions.clickAddToBag();
        AssertFailAndContinue(homePageActions.selectRandomSizeAndAddToBag(headerMenuActions), "Add the recommendation product in to the cart");
    }

    @Test(priority = 4, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, RECOMMENDATIONS, SMOKE,PROD_REGRESSION})
    public void recommendadtions_Baby(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Validate Recommended product test cases in Baby Department page");
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("CANADA");
        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(4));
        AssertFailAndContinue(productDetailsPageActions.validateSubcate(), "Verify L2 Categories in the left hand side");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the Baby department page");
        AssertFailAndContinue(footerActions.prods.size() == 4, "Verify Only 4 items are displayed in recommendation layout");
        validateCarouselArrows();

        AssertFailAndContinue(footerActions.validateProduceRecommendPriceDetails(), "Verify Product was price and current price");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationImageAndName(), "Verify the recommendation products are getting displayed in the Baby department page");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.addProdToFav_Guest_login(emailAddressReg, password), "Add the recommendation product into wishlist as guest user and check if the color of the fav icon is changed");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist as register user and check if the color of the fav icon is changed");
        }
        footerActions.clickAddToBag();
        AssertFailAndContinue(homePageActions.selectRandomSizeAndAddToBag(headerMenuActions), "Add the recommendation product in to the cart");
    }

    @Test(priority = 5, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, RECOMMENDATIONS, SMOKE,PROD_REGRESSION})
    public void recommendadtions_Shoes(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Validate Recommended product test cases in Shoes Department page");
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("CANADA");
        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(5));
        AssertFailAndContinue(productDetailsPageActions.validateSubcate(), "Verify L2 Categories in the left hand side");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the Shoes department page");
        AssertFailAndContinue(footerActions.prods.size() == 4, "Verify Only 4 items are displayed in recommendation layout");
        validateCarouselArrows();

        AssertFailAndContinue(footerActions.validateProduceRecommendPriceDetails(), "Verify Product was price and current price");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationImageAndName(), "Verify the recommendation products are getting displayed in the Shoes department page");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.addProdToFav_Guest_login(emailAddressReg, password), "Add the recommendation product into wishlist as guest user and check if the color of the fav icon is changed");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist as register user and check if the color of the fav icon is changed");
        }
        footerActions.clickAddToBag();
        AssertFailAndContinue(homePageActions.selectRandomSizeAndAddToBag(headerMenuActions), "Add the recommendation product in to the cart");
    }

    @Test(priority = 6, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, RECOMMENDATIONS, SMOKE,PROD_REGRESSION})
    public void recommendadtions_Accessories(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Validate Recommended product test cases in Accessories Department page");
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("CANADA");
        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(6));
        AssertFailAndContinue(productDetailsPageActions.validateSubcate(), "Verify L2 Categories in the left hand side");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the Accessories department page");
        AssertFailAndContinue(footerActions.prods.size() == 4, "Verify Only 4 items are displayed in recommendation layout");
        validateCarouselArrows();

        AssertFailAndContinue(footerActions.validateProduceRecommendPriceDetails(), "Verify Product was price and current price");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationImageAndName(), "Verify the recommendation products are getting displayed in the Accessories department page");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.addProdToFav_Guest_login(emailAddressReg, password), "Add the recommendation product into wishlist as guest user and check if the color of the fav icon is changed");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist as register user and check if the color of the fav icon is changed");

        }
        footerActions.clickAddToBag();
        AssertFailAndContinue(homePageActions.selectRandomSizeAndAddToBag(headerMenuActions), "Add the recommendation product in to the cart");
    }

    @Test(priority = 7, dataProvider = dataProviderName, groups = {GLOBALCOMPONENT, REGRESSION, RECOMMENDATIONS, SMOKE,PROD_REGRESSION})
    public void recommendadtions_Clearance(@Optional("US") String store, @Optional("guest") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Validate Recommended product test cases in Clearance Department page");
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryByCountry("CANADA");
        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(7));
        AssertFailAndContinue(productDetailsPageActions.validateSubcate(), "Verify L2 Categories in the left hand side");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationIsAvailable(), "Verify the recommendation products are getting displayed in the Clearance department page");
        AssertFailAndContinue(footerActions.prods.size() == 4, "Verify Only 4 items are displayed in recommendation layout");
        validateCarouselArrows();

        AssertFailAndContinue(footerActions.validateProduceRecommendPriceDetails(), "Verify Product was price and current price");
        AssertFailAndContinue(productDetailsPageActions.checkProdRecommendationImageAndName(), "Verify the recommendation products are getting displayed in the Clearance department page");
        if (user.equalsIgnoreCase("guest")) {
            AssertFailAndContinue(footerActions.addProdToFav_Guest_login(emailAddressReg, password), "Add the recommendation product into wishlist as guest user and check if the color of the fav icon is changed");
        }
        if (user.equalsIgnoreCase("registered")) {
            AssertFailAndContinue(footerActions.addProdToFav_Reg(), "Add the recommendation product into wishlist as register user and check if the color of the fav icon is changed");
        }
        footerActions.clickAddToBag();
        AssertFailAndContinue(homePageActions.selectRandomSizeAndAddToBag(headerMenuActions), "Add the recommendation product in to the cart");
    }
}
