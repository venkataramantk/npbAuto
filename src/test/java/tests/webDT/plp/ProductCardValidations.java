package tests.webDT.plp;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.EnvironmentConfig;

import java.util.List;

public class ProductCardValidations extends BaseTest {
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
        if (store.equalsIgnoreCase("US")) {
            headerMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            headerMenuActions.addStateCookie("MB");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Test(dataProvider = dataProviderName, priority = 0, groups = {PLP, PROD_REGRESSION})
    public void validateProductAttributes(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        String expPriceColor = "#990000";
        String actualPriceColor = "#999999";
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "1. Verify All fields in Product card in PLP page" +
                "2. Verify Department sizes displayed below L1 except Accessories and Clearance" +
                "3. Verify L2 categories are displayed for associated L1 categories" +
                "4. Validated the alt images in PLP (if applicable)" +
                "5. when on the PLP, should see the favorite heart icon as empty heart icon on all the products\n" +
                "6. Check Bopis Promo Content for Online only Products");
        String clearance = "Clearance";//"Pants";

        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, clearance);
        AssertFailAndContinue(footerActions.checkHeader(), "Scroll down in search results page and Check whether the header is displayed constantly at the top");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, clearance);
        AssertFailAndContinue(categoryDetailsPageAction.validateIcons(1), "Verify product cards displays following \n" + "1. Product Image 2. Fav icon 3. Color Swatch 4. Product Name 5. Add to bag");
        int imagesCount = categoryDetailsPageAction.itemContainers.size();
        int emptyfaviconts = categoryDetailsPageAction.addToFavIcon.size();
        AssertFailAndContinue(imagesCount == emptyfaviconts, "when on the PLP, should see the favorite heart icon as empty heart icon on all the products");
        String discountPriceCheckValue = categoryDetailsPageAction.discountPriceCheck();
        if (discountPriceCheckValue != "") {
            AssertFailAndContinue(discountPriceCheckValue.equalsIgnoreCase(expPriceColor), "Verify actual Price is in Brown color");
        }
        String actualPriceCheckValue = categoryDetailsPageAction.actualPriceCheck();
        AssertFailAndContinue(actualPriceCheckValue.equalsIgnoreCase(actualPriceColor), "Verify Offer Price is red color");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "Denim");
        AssertFailAndContinue(categoryDetailsPageAction.ratingDisplay(), "Verify the product rating displayed in the PLP page");
        AssertFailAndContinue(categoryDetailsPageAction.validateNextButtonOnProductImage(), "verifying for product image After Arrow button display");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "shirts");
        AssertFailAndContinue(categoryDetailsPageAction.isProdImgPrevArrowButtonDisplay(), "verifying for product image Previous Arrow button display");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "online only");
        categoryDetailsPageAction.clickProductNameByPosition(productDetailsPageActions, 1);
        if (store.equalsIgnoreCase("US")) {
            AssertFailAndContinue(!productDetailsPageActions.verifyBopisPromo(), "Verify Bopis Promo content is not displayed for Product -Online-only");
        }
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "online only");
        AssertFailAndContinue(categoryDetailsPageAction.verifyBadge(), "Verify Product Badge");

        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "uniform");
        AssertFailAndContinue(!categoryDetailsPageAction.waitUntilElementsAreDisplayed(categoryDetailsPageAction.prev_ProdImgButton, 5), "Verify product card with no alternative images are not displayed with Right arrows");
        AssertFailAndContinue(!categoryDetailsPageAction.waitUntilElementsAreDisplayed(categoryDetailsPageAction.prodImgButton_Next, 5), "Verify product card with no alternative images are not displayed with Leftarrows");
        categoryDetailsPageAction.scrollDownUntilLangButtonAppear();
        AssertFailAndContinue(footerActions.clickOnLanguageButton(), "When the user is in PLP page and navigate to bottom of the PLP page,Clicks on Ship to flag(EN),verify that \"Ship to model\" is opened up");
    }

    @Test(dataProvider = dataProviderName, priority = 1, groups = {PLP, PROD_REGRESSION})
    public void validateProductCardNavigations(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "1.Verify user clicking on Product Image redirects user to Product Details Page (PDP)" +
                "2.Verify user clicking on Product Name redirects user to Product Details Page (PDP)" +
                "3.Verify user clicking on Product Details link in Add to bag flip redirects user to Product Details Page (PDP)");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "tops");
        AssertFailAndContinue(categoryDetailsPageAction.clickImageByPosition(productDetailsPageActions, 1), "Click on product image and verify user navigates to PDP page");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "tops");
        AssertFailAndContinue(categoryDetailsPageAction.clickProductNameByPosition(productDetailsPageActions, 1), "Click on product name and verify user navigates to PDP page");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "tops");
        //productCardViewActions.addStepDescription("UN-708");
        categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, 1);
        AssertFailAndContinue(categoryDetailsPageAction.clickViewProductDetailsLink(), "Click on product details link in add to bag flip and verify user navigates to PDP page");
        driver.navigate().back();
        AssertFailAndContinue(categoryDetailsPageAction.verifyProductardsinPLP(), "When user clicks on back link in pdp, directed to the last PLP page they were browsing");
    }

    @Test(dataProvider = dataProviderName, priority = 2, groups = {PLP, PROD_REGRESSION})
    public void addToBagFlipTest(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        setRequirementCoverage("As a " + user + " user in " + store + " store, " +
                "1.Verify user clicking on the Add to Bag button from product card flips product card to display Add to Bag flip with following 1. Color Selection\n" + " 2. Size Selection \n" + " 3. Fit Selection (if applicable)\n" + " 4. Select a quantity\n" + " 5. Add to Bag button\n" + " 6. View Product Details link" +
                "2.Verify users clicking Add to Bag button with all attributes selected, \n" +
                " a. Closes the Add to Bag flip\n" +
                " b. Displays Add to Bag confirmation\n" +
                " c. Displays Bag count increased");
        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "tees");
        AssertFailAndContinue(categoryDetailsPageAction.validateAddToBagFlip(), "validate  1. Color Selection\n" + " 2. Size Selection \n" + " 3. Fit Selection (if applicable)\n" + " 4. Select a quantity\n" + " 5. Add to Bag button\n" + " 6. View Product Details link\n are displayed in add to bag flip and close the Bag Flip");
        //footerActions.clickBackToTop();
        categoryDetailsPageAction.closeAddtoBagFlip();
        AssertFailAndContinue(categoryDetailsPageAction.checkErrMsgPLP(), "Click add to bag without selecting size, verify error message is displayed");
        AssertFailAndContinue(categoryDetailsPageAction.closeAddtoBagFlip(), "click close add to bag flip verify add to bag flip is closed");
        categoryDetailsPageAction.mouseHover(categoryDetailsPageAction.itemImg(2));
        categoryDetailsPageAction.openProdCardWithItem();
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementDisplayed(categoryDetailsPageAction.addToBagBtn), "hover on the next product card ensure that previous product card size/color/qty selector is still displayed for previous image");
        categoryDetailsPageAction.click(categoryDetailsPageAction.selectQuantityDropDown);
        AssertFailAndContinue(categoryDetailsPageAction.getText(categoryDetailsPageAction.selectQuantityDropDown).contains("15"), "Verify users Quantity selection in Add to Bag flip - \n" +
                " 1. Upon quantity selection, number scroll with numbers 1 to 15 are displayed");

        headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "tees");
        int initial = Integer.parseInt(headerMenuActions.getQtyInBag());
        categoryDetailsPageAction.openProdCardWithItem();

        AssertFailAndContinue(categoryDetailsPageAction.selectRandomSizeAndAddToBag(headerMenuActions), "Verify users clicking Add to Bag button with all attributes selected, verify Displayed add to bag confirmation");
        //AssertFailAndContinue(!categoryDetailsPageAction.waitUntilElementDisplayed(categoryDetailsPageAction.addToBagBtn, 2), "Verify users clicking Add to Bag button with all attribbutes selected, verify add to bag flip is closed");
        AssertFailAndContinue(Integer.parseInt(headerMenuActions.getQtyInBag()) == initial + 1, "Verify users clicking Add to Bag button with all attributes selected, verify bag count increased");
        headerMenuActions.staticWait(4000);
        categoryDetailsPageAction.clickClose_ConfViewBag();
        AssertFailAndContinue(headerMenuActions.clickShoppingBagIcon(shoppingBagDrawerActions), "Open the Shopping Bag");
        String parentWindow = driver.getWindowHandle();
        AssertFailAndContinue(shoppingBagDrawerActions.paypalButtonCheck(), "Verify paypal button displayed");
        shoppingBagDrawerActions.clickOnPayPalButtonFromDrawerProd(paypalOrderDetailsPageActions, payPalPageActions, billingPageActions, parentWindow);
    }

    @Test(dataProvider = dataProviderName, priority = 4, groups = {PLP, PROD_REGRESSION})
    public void verifyOneSizeBtnSelectedOnBagFlip(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Pooja_Sharma");
        setRequirementCoverage("As a " + user + " user in " + store + " store, " + ", Verify size is automatically selected for the product offered in one size only.");
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        //DT-31392 & DT-31393
        //UN-570
        AssertFailAndContinue(headerMenuActions.clickBoyUnderAccessoriesCategory(categoryDetailsPageAction), "Click Accessories>Boy");
        AssertFailAndContinue(categoryDetailsPageAction.applySizeFilterByTitle("ONE SIZE"), "Select One SIze from Size Filter and apply");
        AssertFailAndContinue(categoryDetailsPageAction.openProdCardViewByPos(searchResultsPageActions, 1), "Click Add to Bag icon");
        AssertFailAndContinue(categoryDetailsPageAction.waitUntilElementDisplayed(productCardViewActions.alreadySelectedOneSizeBtnonFlip), "Hover Mouse over product tile and click add to Bag icon.Verify One Size Button is already Selected");
    }

    @Test(dataProvider = dataProviderName, priority = 3, groups = {PLP, PROD_REGRESSION})
    public void plpBadgesValidation(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Jagadeesh");
        setRequirementCoverage("Verify that the user is displaying products with badges search");

        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name1", "Value"));
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "User"), getDT2TestingCellValueBySheetRowAndColumn("PLP", "plpuser", "Password"));
        }
        checkTopPLPBadgeAndVerifyInPDP("Online Only", "ONLINE EXCLUSIVE");
        checkTopPLPBadgeAndVerifyInPDP("JUST A FEW LEFT", "CLEARANCE");
        checkTopPLPBadgeAndVerifyInPDP("Glow in the Dark", "GLOW-IN-THE-DARK");
        if (store.equalsIgnoreCase("US")) {
            checkTopPLPBadgeAndVerifyInPDP("new arrival", "NEW!");
            AssertFailAndContinue(categoryDetailsPageAction.checkInlineBadgesByBadgeName(headerMenuActions, categoryDetailsPageAction, "Extended sizes", "Extended Sizes"), "Check for Extended size inline badges");
        }
        AssertFailAndContinue(homePageActions.selectDeptWithName(departmentLandingPageActions, deptName.get(0)), "Verify if the registered user redirected to the " + deptName.get(1) + " department landing page");
    }

}
