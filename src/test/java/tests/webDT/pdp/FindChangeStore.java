package tests.webDT.pdp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import tests.web.initializer.BaseTest;
import ui.support.EnvironmentConfig;

import java.util.List;

public class FindChangeStore extends BaseTest {
    WebDriver driver;
    String emailAddressReg;
    private String password;
    String env = EnvironmentConfig.getEnvironmentProfile();

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        driver = getDriver();
        initializePages(driver);
        driver.get(EnvironmentConfig.getApplicationUrl());

        if (user.equalsIgnoreCase("registered")) {
            emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelUS);
            password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        }
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            if (user.equalsIgnoreCase("registered")) {
                emailAddressReg = clickCreateNewAcctAndCreateNewAccountByRow(rowInExcelCA);
                password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelCA, "Password");
            }
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
        }
        if (store.equalsIgnoreCase("CA")) {
            footerActions.changeCountryAndLanguage("CA", "English");
            headerMenuActions.addStateCookie("MB");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        headerMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @Test(priority = 0, groups = {PDP, BOPIS, GUESTONLY, PROD_REGRESSION})
    public void recentMultipleStoresVerifyAsGuest(@Optional("US") String store) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage("  done a pickup in store with multiple store in the previous session,Without and with clearing the cache, when the user navigates to PDP of any BOPIS eligible item through search-PDP and initiates \"Pick Up in Store\" modal. Verify that the user is able to see the lastly added to shopping bag as \"Recent store\" along with the other stores in the In store Availability modal"
        + "DT-44293" + "DT-44294" + "DT-44295" + "DT-44278");

        String zipCode = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        String upcNum = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        if(store.equalsIgnoreCase("CA")){
            upcNum = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumberCA");
            zipCode = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
        }
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum);
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        bopisOverlayActions.selectSizeAndSelectStore(zipCode);
        bopisOverlayActions.clickSelectStoreBtnByPosition(1);
        String recentStore = headerMenuActions.getText(headerMenuActions.changedFavStoreName);

        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum);
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        bopisOverlayActions.selectSizeAndSelectMoreThan_1Store(zipCode);
        AssertFailAndContinue(bopisOverlayActions.isDisplayed(bopisOverlayActions.favStoreSelectedFirst), "As a guest user, showing recent store selected");
        bopisOverlayActions.clickSelectStoreBtnByPosition(2);
        String recentStore2ndTime = headerMenuActions.getText(headerMenuActions.changedFavStoreName);
        AssertFailAndContinue(!recentStore.equalsIgnoreCase(recentStore2ndTime), "The 1st store selected has changed to 2nd selected store as recent store");
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        bopisOverlayActions.selectSizeAndCheckAvailability();
        AssertFailAndContinue(bopisOverlayActions.checkTheRecentStoreDisplay(recentStore2ndTime),"Verify the recent store is displayed proeprly in BOPIS modal");

        headerMenuActions.deleteAllCookies();
        headerMenuActions.addStateCookie("NJ");
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum);
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        bopisOverlayActions.selectSizeAndSelectMoreThan_1Store(zipCode);
        AssertFailAndContinue(!bopisOverlayActions.isDisplayed(bopisOverlayActions.favStoreSelectedFirst), "As a guest user with clearing cache, Not showing recent store ");
        bopisOverlayActions.clickSelectStoreBtnByAvailableStores();
        String recentStorelogin = headerMenuActions.getText(headerMenuActions.changedFavStoreName);
        clickLoginAndLoginAsRegUser(emailAddressReg,password);
        headerMenuActions.closeTheVisibleDrawer();
        String recentStorePostLogin = headerMenuActions.getText(headerMenuActions.changedFavStoreName);
        AssertFailAndContinue(!recentStorelogin.equalsIgnoreCase(recentStorePostLogin), "Verify that the recent store selected in the guest session is not displayed as Recent store after login");
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        bopisOverlayActions.selectSizeAndSelectMoreThan_1Store(zipCode);
        AssertFailAndContinue(!bopisOverlayActions.isDisplayed(bopisOverlayActions.favStoreSelectedFirst), "As a guest user with clearing cache, Not showing recent store ");
        bopisOverlayActions.clickSelectStoreBtnByAvailableStores();
        String recentStorePostLogin1 = headerMenuActions.getText(headerMenuActions.changedFavStoreName);
        headerMenuActions.clickOnUserNameAndLogout(myAccountPageDrawerActions,headerMenuActions);
        String recentStorePostLogout = headerMenuActions.getText(headerMenuActions.changedFavStoreName);
        AssertFailAndContinue(!recentStorePostLogin1.equalsIgnoreCase(recentStorePostLogout), "Verify that the recent store selected in the Registered session is not displayed as Recent store after logout");
       // headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum);
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        bopisOverlayActions.selectSizeAndSelectMoreThan_1Store(zipCode);
        AssertFailAndContinue(!bopisOverlayActions.isDisplayed(bopisOverlayActions.favStoreSelectedFirst), "As a guest user after logout, Not showing recent store ");

    }

    @Parameters(storeXml)
    @Test(priority = 1, groups = {PDP, BOPIS, GUESTONLY, PROD_REGRESSION})
    public void recentStoresVerifyAsGuest(@Optional("CA") String store) throws Exception {
        setAuthorInfo("Shyamala");
        setRequirementCoverage(" done a pickup in store in the previous session,Without and with clearing the cache, when the user navigates to PDP of any BOPIS eligible item through search-PDP and initiates \"Pick Up in Store\" modal and triggers search, Verify of the user is able to view the store information of Recent store in the \"Pickup in store\" modal." +
                "DT-44292" + "DT-44285" + "DT-44268" +"DT-44225" + "DT-44219"+"DT-44223");

        String zipCode = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        String upcNum = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String err = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails","SelectSize","ExpectedErrorMsg");
        if(store.equalsIgnoreCase("CA")) {
            upcNum = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumberCA");
            zipCode = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
        }
           headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum);
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        bopisOverlayActions.checkDefaultQuantityAndSize("1","Select");
        bopisOverlayActions.sizeSelectionError(err, zipCode);
        bopisOverlayActions.selectSizeAndSelectStore(zipCode);
        AssertFailAndContinue(bopisOverlayActions.checkAddToBagDisableValidation(),"Verify the add to bag CTA display for unavailable items in bopis modal");
        AssertFailAndContinue(!bopisOverlayActions.isDisplayed(bopisOverlayActions.favStoreSelectedFirst), "Verify the Favorite store is not displayed in the BOPIS modal");
        bopisOverlayActions.clickSelectStoreBtnByPosition(1);
        String recentStore = headerMenuActions.getText(headerMenuActions.changedFavStoreName);

        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum);
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        bopisOverlayActions.selectSizeAndSelectStore(zipCode);
        //Commented below steps because As a guest, favorite store won't be in the list and in the header
//        AssertFailAndContinue(bopisOverlayActions.isDisplayed(bopisOverlayActions.favStoreSelectedFirst), "As a guest user, showing recent store selected");
//
//        String selectedStoreRecently = headerMenuActions.getText(bopisOverlayActions.favStoreSelectedFirst);
//        AssertFailAndContinue(recentStore.equalsIgnoreCase(selectedStoreRecently), "The recent store is showing as 1st in the list as a guest user without clearing cache");
//
//        headerMenuActions.deleteAllCookies();
//        headerMenuActions.addStateCookie("NJ");
//
//        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum);
//        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
//        bopisOverlayActions.selectSizeAndSelectStore(zipCode);
        AssertFailAndContinue(!bopisOverlayActions.isDisplayed(bopisOverlayActions.favStoreSelectedFirst), "As a guest user, recent store is mot showing");

    }

    @Test(dataProvider = dataProviderName,priority = 1, groups = {PDP, BOPIS, GUESTONLY,PROD_REGRESSION})
    public void favStoreDisplayPdp(@Optional("US") String store, @Optional("Guest") String user) throws Exception{

        setAuthorInfo("srijith");
        setRequirementCoverage("Verify the Favorite store display across PLP and PDP, DT-31339");

        String zipCode = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        String upcNum = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        List<String> validAddress = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "storeLocator", "validSearch"));
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
            if(store.equalsIgnoreCase("CA")){
                upcNum = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumberCA");
                zipCode = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
            }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);}
        headerMenuActions.click(headerMenuActions.findStoreLnk);
        AssertFailAndContinue(storeLocatorPageActions.searchStore(validAddress.get(0)), "Enter the valid store address and click on search button");

            AssertFailAndContinue(storeLocatorPageActions.selectFavStore(headerMenuActions), "select the favorite store in store locator page and check the same is getting displayed in the header section");
            AssertFailAndContinue(storeLocatorPageActions.favStoreDisplay(headerMenuActions), "Check whether the Fav store is displayed as first store");
            String favStore = headerMenuActions.changedFavStoreName.getText().toLowerCase();
            headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum);
            AssertFailAndContinue(productDetailsPageActions.checkFavStoreDisplay(favStore),"Check the fav store name displayed in PDP");
            headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "denims");
            headerMenuActions.waitUntilElementsAreDisplayed(categoryDetailsPageAction.productImages,4);
            AssertFailAndContinue(categoryDetailsPageAction.checkFavStoreDisplay(favStore),"Check the Favorite store displayed in Search result page");
            headerMenuActions.navigateToDepartment(deptName.get(2));
            headerMenuActions.click(departmentLandingPageActions.lnk_Denim);
            headerMenuActions.waitUntilElementsAreDisplayed(categoryDetailsPageAction.productImages,4);
            AssertFailAndContinue(categoryDetailsPageAction.checkFavStoreDisplay(favStore),"Check the Favorite store displayed in PLP result page");
            if(user.equalsIgnoreCase("registered")){
                myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions,myAccountPageActions);
                AssertFailAndContinue(myAccountPageActions.checkFavStoreDisplay(favStore),"Verify the Favorite store displayed in MyAccount Overview Page");
                myAccountPageActions.clickProfileInfoLink();
                AssertFailAndContinue(myAccountPageActions.checkFavStoreDisplay(favStore),"Verify the Favorite store displayed in MyAccount Overview Page");
            // Check the Fav Post re login as Reg user

                headerMenuActions.clickOnUserNameAndLogout(myAccountPageDrawerActions,headerMenuActions);
                clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);}
            AssertFailAndContinue(headerMenuActions.checkFavStoreDisplay(favStore),"Check the Favorite store display post login");
            headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum);
            AssertFailAndContinue(productDetailsPageActions.checkFavStoreDisplay(favStore),"Check the fav store name displayed in PDP");
            headerMenuActions.searchAndSubmit(categoryDetailsPageAction, "denims");
            headerMenuActions.waitUntilElementsAreDisplayed(categoryDetailsPageAction.productImages,4);
            AssertFailAndContinue(categoryDetailsPageAction.checkFavStoreDisplay(favStore),"Check the Favorite store displayed in Search result page");
            headerMenuActions.navigateToDepartment(deptName.get(2));
            headerMenuActions.click(departmentLandingPageActions.lnk_Denim);
            headerMenuActions.waitUntilElementsAreDisplayed(categoryDetailsPageAction.productImages,4);
            AssertFailAndContinue(categoryDetailsPageAction.checkFavStoreDisplay(favStore),"Check the Favorite store displayed in PLP result page");
            if(user.equalsIgnoreCase("registered")) {
                myAccountPageDrawerActions.clickViewMyAccount(headerMenuActions, myAccountPageActions);
                AssertFailAndContinue(myAccountPageActions.checkFavStoreDisplay(favStore), "Verify the Favorite store displayed in MyAccount Overview Page");
                myAccountPageActions.clickProfileInfoLink();
                AssertFailAndContinue(myAccountPageActions.checkFavStoreDisplay(favStore), "Verify the Favorite store displayed in MyAccount Overview Page");
            }

        }
    @Test(dataProvider = dataProviderName,priority = 1, groups = {PDP, BOPIS,PROD_REGRESSION})
    public void bopisValidation(@Optional("US") String store, @Optional("Guest") String user) throws Exception {

        setAuthorInfo("srijith");
        setRequirementCoverage("Verify the BOPIS overlay Modal and Attributes" + "DT-44212" + "DT-44227" + "DT-44229" + "DT-44230" + "DT-44236"+ "DT-44240" +"DT-44239"+"DT-44203"+ "DT-44198"+"DT-44215"+
                "DT-44199" + "DT-44202" + "DT-44245" + "DT-44197" +"DT-44213"+"DT-44365" + "DT-44366" + "DT-44246" + "DT-44262" + "DT-44256" + "DT-44208"+"DT-44244"+"DT-44224"+"DT-44254");

        String zipCode = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCode");
        String upcNum = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumber");
        String upcNum1 = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber1", "validUPCNumberLegacy");
        List<String> validAddress = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("footerValidation", "storeLocator", "validSearch"));
        List<String> deptName = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("Department", "Name", "Value"));
        List<String> errorMsg = convertCommaSeparatedStringToList(getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "BOPISZipError", "ExpectedErrorMsg"));
        if (store.equalsIgnoreCase("CA")) {
            upcNum = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "UPCNumber", "validUPCNumberCA");
            zipCode = getDT2TestingCellValueBySheetRowAndColumn("BOPISDetails", "zipCode", "validZipCodeCA");
        }
        if (user.equalsIgnoreCase("registered")) {
            clickLoginAndLoginAsRegUserAndCloseDrawer(emailAddressReg, password);
        }
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum);
        productDetailsPageActions.updateQty("2");
        String selectedSize = null;
        productDetailsPageActions.selectAnySize();
        String name = productDetailsPageActions.getText(productDetailsPageActions.productName);
        if (productDetailsPageActions.isDisplayed(productDetailsPageActions.fitOptionSelected)) {
            selectedSize = productDetailsPageActions.getText(productDetailsPageActions.selectedSizeInPDPWithFit);
        } else {
            selectedSize = productDetailsPageActions.getText(productDetailsPageActions.selectedSizeInPDP);
        }
        String selectedQuantity = productDetailsPageActions.getText(productDetailsPageActions.quantitySelectedInPDP);
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        AssertFailAndContinue(bopisOverlayActions.selectedSizeDisplayFromPDP(selectedSize, selectedQuantity), "Veify the Size and Qty selected in PDP is carried inside the BOPIS Modal");
        AssertFailAndContinue(bopisOverlayActions.checkTheDistance(), "Verify the distance field displayed inside the bopis modal contains 25,50 & 75 miles");
        bopisOverlayActions.checkItemNAmeDisplayedInBOPISModal(name);
        bopisOverlayActions.enterZipAndSearchStore(zipCode);
        AssertFailAndContinue(bopisOverlayActions.deselectShowAvailableStore(), "Verify that the unavailable stores are displayed when the user deselect the available store checkbox");
        bopisOverlayActions.checkPhoneNumFormat();
        bopisOverlayActions.checkStoreNameIsBold();
        AssertFailAndContinue(bopisOverlayActions.changeTheDistanceValidation(), "Verify the user is asked to do search after changing the distance.");
        bopisOverlayActions.checkStoreMilesBasedOnMilesSelected("25"); // taken from Mobile Script
        //bopisOverlayActions.clearAndFillText(bopisOverlayActions.zipCodeField, "");
        bopisOverlayActions.closeBopisOverlayModal();
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        //:TODO need to enable after fixing DTN-2622
       /* bopisOverlayActions.click(bopisOverlayActions.searchButton);
        AssertFailAndContinue(bopisOverlayActions.validateEmptyZipFldErrMsg(), "click on search button on empty zip code field and verify inline error");
       */ bopisOverlayActions.enterZipAndSearchStore(zipCode);
        AssertFailAndContinue(bopisOverlayActions.validateStoresListedDistance(),"Verify the distance displayed in the BOPIS modal");
        bopisOverlayActions.selectDropDownByValue(bopisOverlayActions.qtyDrpDown, "2");
        bopisOverlayActions.isDisplayed(bopisOverlayActions.availableStrCheckBox);
        bopisOverlayActions.validateSpecialCharZipFldErrMsg("!@#$%^&*",errorMsg.get(0));
        bopisOverlayActions.validateInvalidZipFldErrMsg("q1q11q",errorMsg.get(1));
        String initialImg = bopisOverlayActions.getImgSrc();
        if(bopisOverlayActions.changeColorFOrBOPIS()>1){
            AssertFailAndContinue(!bopisOverlayActions.getImgSrc().equalsIgnoreCase(initialImg),"Verify the item color is changed while changing the Color"); }
        headerMenuActions.searchUsingKeyWordLandOnPDP(productDetailsPageActions, upcNum1);
        productDetailsPageActions.clickPickUpStoreBtn(bopisOverlayActions);
        AssertFailAndContinue(!bopisOverlayActions.validateFitSection(),"Fit is not displayed in BOPIS MOdal");
        AssertFailAndContinue(bopisOverlayActions.verifyBadgeInBopisOverlay(),"Verify that the badges are not displaeyd in the BOPIS mdoal");
    }

}
