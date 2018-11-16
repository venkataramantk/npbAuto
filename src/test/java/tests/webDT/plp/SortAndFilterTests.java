package tests.webDT.plp;

import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.EnvironmentConfig;

import java.util.List;

public class SortAndFilterTests extends BaseTest {

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
        if (store.equalsIgnoreCase("US")) {
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            headerMenuActions.addStateCookie("MB");}
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {PLP, "DT-32080",PROD_REGRESSION})
    public void plpSizeFilterTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "1. , Verify Size and Color filter types are displayed in category PLP" +
                "2. Click Size filter , verify all sizes are displayed, apply button and no size is selected by default" +
                "3. Verify select size is displayed in filter By region along with X icon" +
                "4. Verify user can select more than one size" +
                "5. verify applied category filter is displayed in filtered by along with close icon" +
                "6. verify user is able to clear filter" +
                "DT-20791");

        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(2));
        headerMenuActions.click(departmentLandingPageActions.lnk_Girl_Tops); //changed from denim, since denim returns 2 fit filters in PLP
        headerMenuActions.addStepDescription("UN-983");
        AssertFailAndContinue(categoryDetailsPageAction.verifySortAndFilter(), "Verify Size and color filters are displayed in PLP page and also 404 page is not displayed");
        AssertFailAndContinue(categoryDetailsPageAction.verifySizeFilter(), "Click Size filter , verify all sizes are displayed, apply button and no size is selected by default");
        categoryDetailsPageAction.clickOnOpenSizeFilter();
        AssertFailAndContinue(categoryDetailsPageAction.applySizeFilter(2), "Select a random size and apply filter. Verify select size is displayed in filter By region along with X icon, check if the size filter is multi selection");

        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "tops");
        String appliedFilter = categoryDetailsPageAction.applyCategoryFilter(0);
        AssertFailAndContinue(categoryDetailsPageAction.applySizeFilter(1), "Apply one size filter");
        AssertFailAndContinue(categoryDetailsPageAction.applyColorFilter(1), "Apply one color filter");
        AssertFailAndContinue(categoryDetailsPageAction.verifyAppliedFiltersCount(3), "Verify Catgeory,Size and Color Filters are applied");
        AssertFailAndContinue(categoryDetailsPageAction.clearFilter(appliedFilter), "Clear the Category Filter");
        AssertFailAndContinue(categoryDetailsPageAction.verifyAppliedFiltersCount(2), "Verify Size and Color Filters are applied");
        categoryDetailsPageAction.clickRandomProductByImage(productDetailsPageActions);
        productDetailsPageActions.clickBackToResultsLink(searchResultsPageActions, "tops");
        AssertFailAndContinue(categoryDetailsPageAction.verifyAppliedFiltersCount(2), "Verify Size and Color Filters are applied");
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {PLP, "DT-32080",PROD_REGRESSION})
    public void plpAllFilterTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "1. , Verify Color filter types are displayed in category PLP" +
                "2. Click Size filter , verify all sizes are displayed, apply button and no size is selected by default" +
                "3. Verify select size is displayed in filter By region along with X icon" +
                "4. Verify user can select more than one size" +
                "5. verify applied category filter is displayed in filterd by along with close icon" +
                "6. verify user is able to clear filter");

        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(2));
        headerMenuActions.click(departmentLandingPageActions.lnk_Denim);
        categoryDetailsPageAction.addStepDescription("UN-983");
        AssertFailAndContinue(categoryDetailsPageAction.verifySortAndFilter(), "Verify Size and color filters are displayed in PLP page");
        AssertFailAndContinue(categoryDetailsPageAction.verifyColorFilter(), "Click Size color , verify all colors are displayed, apply button and no color is selected by default");
        AssertFailAndContinue(categoryDetailsPageAction.closeColorFilterSection(), "Close the Color Filter Section");
        AssertFailAndContinue(categoryDetailsPageAction.verifyPriceFilter(), "Click Price Filter and validate the price filter display");
        AssertFailAndContinue(categoryDetailsPageAction.closePriceFilterSection(), "Close the Price Filter Section");
        categoryDetailsPageAction.addStepDescription("UN-983");
        AssertFailAndContinue(categoryDetailsPageAction.verifyFitFilter(), "Click Fit Filter and validate the price filter display");
        AssertFailAndContinue(categoryDetailsPageAction.closeFitFilterSection(), "Close the Fit Filter Section");
        AssertFailAndContinue(categoryDetailsPageAction.verifyGenderFilter(), "Click Gender Filter and validate the price filter display");
        AssertFailAndContinue(categoryDetailsPageAction.closeGenderFilterSection(), "Close the Gender Filter Section");
        AssertFailAndContinue(categoryDetailsPageAction.verifySizeRangeFilter(), "Click Size Range Filter and validate the price filter display");
        AssertFailAndContinue(categoryDetailsPageAction.closeSizeRangeFilterSection(), "Close the Size Range Filter Section");
        AssertFailAndContinue(categoryDetailsPageAction.applyColorFilter(2), "Select a random colors and apply filter. Verify selected color are displayed in filter By region along with X icon, check if the color filter is multi selection");
        //AssertFailAndContinue(categoryDetailsPageAction.applyMultipleFilter(),"Apply multiple filter one by one");
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {PLP,PROD_REGRESSION})
    public void plpSortTests(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "1. all available sort options" +
                "2. Verify sort Price: high to low and Low to sort" +
                "3. Verify user displayed with \"Best Match\" as default sort option selected" +
                "4. Verify user select only one sort option" +
                "5. Verify user selected sort does not persist on next PLP page user visits");

        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        headerMenuActions.navigateToDepartment(deptName.get(2));
        headerMenuActions.click(departmentLandingPageActions.lnk_Denim);
        AssertFailAndContinue(categoryDetailsPageAction.getSelectSort().contains("Recommended"), "Verify user displayed with \"Recommended\" as default sort option selected");
        AssertFailAndContinue(categoryDetailsPageAction.getAllSortOptions(), "Verify all available sort options");
        categoryDetailsPageAction.click(categoryDetailsPageAction.selectedSort);
        AssertFailAndContinue(categoryDetailsPageAction.sortByOptionPlp("Low to High"), "Verify Price Low to high sort");
        AssertFailAndContinue(categoryDetailsPageAction.sortByOptionPlp("High to Low"), "Verify Price high to Low sort");

        headerMenuActions.navigateToDepartment(deptName.get(2));
        headerMenuActions.click(departmentLandingPageActions.lnk_Denim);
        AssertFailAndContinue(categoryDetailsPageAction.getSelectSort().contains("Recommended"), "Verify Sort is reset to default options and not retained previous sort");
    }
}
