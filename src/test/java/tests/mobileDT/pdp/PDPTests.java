package tests.mobileDT.pdp;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import tests.web.initializer.MobileBaseTest;
import ui.support.EnvironmentConfig;
import uiMobile.UiBaseMobile;

//@Test(singleThreaded = true)
public class PDPTests extends MobileBaseTest {

    WebDriver mobileDriver;
    String email = "", password;

    @Parameters({storeXml, usersXml})
    @BeforeClass(alwaysRun = true)
    public void initDriver(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        initializeDriver();
        mobileDriver = getDriver();
        initializeMobilePages(mobileDriver);
        mhomePageActions.navigateTo(EnvironmentConfig.getApplicationUrl());
        mheaderMenuActions.deleteAllCookies();
        email = UiBaseMobile.randomEmail();
       if (store.equalsIgnoreCase("US")) {
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryByCountry("CANADA");
            if (user.equalsIgnoreCase("registered")) {
                createAccount(rowInExcelUS, email);
            }
        }
        password = getDT2TestingCellValueBySheetRowAndColumn("CreateAccount", rowInExcelUS, "Password");
        mheaderMenuActions.deleteAllCookies();
    }

    @Parameters(storeXml)
    @BeforeMethod(alwaysRun = true)
    public void openBrowser(@Optional("US") String store) throws Exception {
        mobileDriver.get(EnvironmentConfig.getApplicationUrl());
        if (store.equalsIgnoreCase("US")) {
            mheaderMenuActions.addStateCookie("NJ");
        } else if (store.equalsIgnoreCase("CA")) {
            mfooterActions.changeCountryAndLanguage("CA", "English");
            mheaderMenuActions.addStateCookie("ON");
        }
    }

    @Test(priority = 0, dataProvider = dataProviderName, groups = {PDP, PROD_REGRESSION})
    public void verifyATBNotification(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("RichaK");
        setRequirementCoverage("Verify add to bag notification disappears automatically after 3 seconds and also verify if clicking on close icon, notification gets closed: DT-37442" +
                "DT-37444");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
        }
        
        String validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Value");
        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, validUPCNumber);
        
        mproductDetailsPageActions.selectFitAndSize();
        mproductDetailsPageActions.selectDropDownByVisibleText(mproductDetailsPageActions.qtyDropDown, "1");
        
        mproductDetailsPageActions.clickAddToBagBtn();
        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.addtoBagNotification), "Verify add to bag notification is seen");
        AssertFailAndContinue(mheaderMenuActions.clickCloseIconOnATBNotification(mproductDetailsPageActions), "Verify add to bag notification disappears on clicking cross icon");
        
        mproductDetailsPageActions.clickAddToBagBtn();
        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.addtoBagNotification), "Verify add to bag notification is seen");
        mheaderMenuActions.staticWait(3000);
        AssertFailAndContinue(!mheaderMenuActions.isDisplayed(mheaderMenuActions.addtoBagNotification), "Verify add to bag notification is not seen and it disappears automatically after 3 seconds");
    }
    

	@Test(priority = 1, dataProvider = dataProviderName, groups = {CHECKOUT,PDP})
	public void validateViewBagFrmAtbNotification(@Optional("US") String store, @Optional("registered") String user) throws Exception {
		setAuthorInfo("Shubhika");
		setRequirementCoverage("As a " + user + " user in " + store + " store, Verify user is able to click on view bag button from add to bag notification"+"DT-37440");

        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
           mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);
            
        }
        
        String validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Value");
        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, validUPCNumber);
        mproductDetailsPageActions.selectFitAndSize();
        mproductDetailsPageActions.selectDropDownByVisibleText(mproductDetailsPageActions.qtyDropDown, "1");
        mproductDetailsPageActions.clickAddToBagBtn();
        AssertFailAndContinue(mheaderMenuActions.clickOnViewBagNotification(mshoppingBagPageActions), "Verify user is able to click on view bag button");
        

	}
	@Test(priority = 2, dataProvider = dataProviderName, groups = {CHECKOUT,PDP})
	public void validateExtendedSizes(@Optional("US") String store, @Optional("registered") String user) throws Exception {
		setAuthorInfo("Shubhika");
		setRequirementCoverage("As a " + user + " user in " + store + " store, Verify if the user is redirected to the PDP in which extended FIT options is available for the user " +
				"DT-37317,DT-37319,DT-37286");
		if (user.equalsIgnoreCase("registered")) {
			panCakePageActions.navigateToMenu("LOGIN");
			mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);


		}

		String extendedSizeGirls = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCNumber", "Value");
		mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, extendedSizeGirls);
		AssertFailAndContinue(mproductDetailsPageActions.isFitOptionsDisplay(), "All Girl's Sizes are displayed");
		String extendedSizeBoys = getmobileDT2CellValueBySheetRowAndColumn("Search", "SearchUPCExtendedSize", "Value");
		mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, extendedSizeBoys);
		AssertFailAndContinue(mproductDetailsPageActions.isFitOptionsDisplay(), "All Boy's Sizes are displayed");
		mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, extendedSizeGirls);
		AssertFailAndContinue(mproductDetailsPageActions.isDisplayed(mproductDetailsPageActions.colorName), "Verify Selected Color is displayed");
		AssertFailAndContinue(mproductDetailsPageActions.isDisplayed(mproductDetailsPageActions.selectColorLabel), "Verify Select a Color Label is displayed");

	}

	@Test(priority = 3, dataProvider = dataProviderName, groups = {CHECKOUT,PDP})
	public void validateAddToBagUI(@Optional("US") String store, @Optional("registered") String user) throws Exception {
        setAuthorInfo("Shubhika");
        setRequirementCoverage("As a " + user + " user in " + store + " store, Verify add to bag notification UI " + "DT-37433");
        if (user.equalsIgnoreCase("registered")) {
            panCakePageActions.navigateToMenu("LOGIN");
            mloginPageActions.loginAsRegisteredUserFromLoginForm(email, password);

        }

        String validUPCNumber = getmobileDT2CellValueBySheetRowAndColumn("Search", "DenimProductUPCNum1", "Value");
        mheaderMenuActions.searchUsingKeyWordLandOnPDP(mproductDetailsPageActions, validUPCNumber);
        mproductDetailsPageActions.selectFitAndSize();
        mproductDetailsPageActions.selectDropDownByVisibleText(mproductDetailsPageActions.qtyDropDown, "1");
        mproductDetailsPageActions.clickAddToBagBtn();

        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.addtoBagNotification), "Verify add to bag notification is seen");
        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.viewBagNotification), "Verify view Bag is seen");
        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.checkoutFrmNotification), "Verify checkout is seen");
        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.departmentName), "Verify department of item chosen");
        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.itemColor), "Verify Color of the item displayed");
        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.itemSize), "Verify size is displayed");
        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.itemQuantity), "Verify quantity is displayed");
        AssertFailAndContinue(mheaderMenuActions.isDisplayed(mheaderMenuActions.addtoBagClose), "Verify close button is displayed");
    }


    @AfterMethod(alwaysRun = true)
    public void clearCookies() {
        mheaderMenuActions.deleteAllCookies();
    }

    @AfterClass(alwaysRun = true)
    public void quitBrowser() {
        mobileDriver.quit();
    }
}